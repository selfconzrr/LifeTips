package com.example.zhangruirui.lifetips.notes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.BaseActivity;
import com.example.zhangruirui.lifetips.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class TimeDiaryActivity extends BaseActivity {

  public static final int TAKE_PHOTO = 1;//拍照

  public static final int CROP_PHOTO = 2;//裁剪

  public static final int CHOOSE_PHOTO = 3;//选择照片

  public static final int CODE_FOR_WRITE_PERMISSION = 0;

  public static boolean hasWritePermimssion;

  private Uri imageUri;

  public NotepadAdapter mAdapter;
  public ArrayList<Map<String, Object>> mItemList;
  public ListView mListView;
  public int mNumber;
  public Button mNumberButton;
  public Button mTopButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_diary);
    mNumberButton = findViewById(R.id.number);
    mTopButton = findViewById(R.id.add_event);
    mListView = findViewById(R.id.listview);
    mListView.setDivider(null);
    mListView.setOnItemClickListener(new ItemClick());
    mTopButton.setOnClickListener(v -> {
      final Intent intent = new Intent(TimeDiaryActivity.this, WriteActivity.class);
      startActivity(intent);
    });

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);

    /*
     * 这段代码需要访问外部存储（相册图片），属于危险级别的权限，直接使用会造成应用崩溃
     * Permission Denial: reading com.android.providers.media.MediaProvider
     *
     */
    int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission
        .WRITE_EXTERNAL_STORAGE);
    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
              .WRITE_EXTERNAL_STORAGE},
          CODE_FOR_WRITE_PERMISSION);
    } else {
      hasWritePermimssion = true;
    }

    /**
     * 对于面向 Android 7.0 的应用，Android 框架执行的 StrictMode API 政策禁止在您的应用外部公开
     * file:// URI。如果一项包含文件 URI 的 intent 离开您的应用，
     * 则应用出现故障，并出现 FileUriExposedException 异常。
     *
     * @see {@link com.example.zhangruirui.utils.FileProviders}
     */
    // TODO: 2019/1/31 替换成 FileProvider，已添加 FileProviders

    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    StrictMode.setVmPolicy(builder.build());
    builder.detectFileUriExposure();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  /**
   * 增加从相册选取和拍照的功能
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.change_bg_from_album:
        Intent intentAlbum = new Intent("android.intent.action.GET_CONTENT");
        intentAlbum.setType("image/*");
        startActivityForResult(intentAlbum, CHOOSE_PHOTO); // 打开相册
        break;

      case R.id.change_bg_take_photo:
        // 创建 File 对象，用于存储拍照后的图片，存放在 SD 卡的根目录下
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        try {
          if (outputImage.exists()) {
            outputImage.delete();
          }
          outputImage.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
        // android 7.0 用下面的 api 方法时是有问题的
        // TODO: 2019/1/31 替换成 FileProvider 
        imageUri = Uri.fromFile(outputImage); // 将 File 对象转换成 Uri 对象

        Intent intentPhoto = new Intent("android.media.action.IMAGE_CAPTURE"); // 隐式 Intent
        intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定图片的输出地址
        startActivityForResult(intentPhoto, TAKE_PHOTO); // 启动相机程序
        // 因为拍完照后会有结果返回到 onActivityResult() 方法中
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    switch (requestCode) {
      case TAKE_PHOTO:
        if (resultCode == RESULT_OK) { // 如果拍照成功
          Intent intent = new Intent("com.android.camera.action.CROP"); // 准备启动裁剪程序
          intent.setDataAndType(imageUri, "image/*");
          intent.putExtra("scale", true);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
          startActivityForResult(intent, CROP_PHOTO);
        }
        break;

      case CROP_PHOTO:
        if (resultCode == RESULT_OK) {
          try {
            // 将照片解析成 Bitmap 对象
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                (imageUri));

            mListView.setBackground(new BitmapDrawable(bitmap)); // 将裁剪后的照片显示出来

          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        break;

      case CHOOSE_PHOTO:
        Log.e("zhangrr", "onActivityResult() called with: requestCode = [" + requestCode + "], " +
            "resultCode = [" + resultCode + "], hasWritePermimssion = [" + hasWritePermimssion + "]");
        if (resultCode == RESULT_OK && hasWritePermimssion) {
          // 判断手机系统版本号
          if (Build.VERSION.SDK_INT >= 19) {
            // 4.4 及以上系统使用这个方法处理图片
            handleImageOnKitKat(data);
          } else {
            // 4.4 以下系统使用这个方法处理图片
            handleImageBeforeKitKat(data);
          }
        }
        break;

      default:
        break;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    showUpdate();
  }

  @SuppressLint("SetTextI18n")
  public void showUpdate() {
    mItemList = new ArrayList<>();
    SQLiteDatabase localSqLiteDatabase = new SQLiteHelper(this
    ).getReadableDatabase();
    Iterator<Notepad> localIterator = new SqliteOperation().query(
        localSqLiteDatabase).iterator();
    while (true) {
      if (!localIterator.hasNext()) {
        Collections.reverse(mItemList);
        mAdapter = new NotepadAdapter(this, mItemList);
        mListView.setAdapter(mAdapter);
        if (mItemList.size() == 0) {
          mNumber = 0;
          mNumberButton.setText("");
        }
        return;
      }
      Notepad localNotepad = localIterator.next();
      HashMap<String, Object> localHashMap = new HashMap<>();
      localHashMap.put("titleItem", localNotepad.getTitle());
      localHashMap.put("dateItem", localNotepad.getData());
      localHashMap.put("contentItem", localNotepad.getContent());
      localHashMap.put("idItem", localNotepad.getId());
      localHashMap.put("EXPANDED", Boolean.TRUE);
      mItemList.add(localHashMap);
      mNumber = this.mItemList.size();
      this.mNumberButton.setText("(" + this.mNumber + ")");
    }
  }

  class ItemClick implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView,
                            View paramView, int paramInt, long paramLong) {
      Map<String, Object> localMap = TimeDiaryActivity.this.mItemList
          .get(paramInt);
      if ((Boolean) localMap.get("EXPANDED")) {
        localMap.put("EXPANDED", Boolean.FALSE);
      } else {
        localMap.put("EXPANDED", Boolean.TRUE);
      }
      TimeDiaryActivity.this.mAdapter.notifyDataSetChanged();
    }
  }

  @TargetApi(19)
  private void handleImageOnKitKat(Intent data) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(this, uri)) {
      // 如果是 document 类型的 Uri，则通过 document id 处理
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1]; // 解析出数字格式的id
        String selection = MediaStore.Images.Media._ID + "=" + id;
        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri = ContentUris.withAppendedId(Uri.parse
            ("content://downloads/public_downloads"), Long.valueOf(docId));
        imagePath = getImagePath(contentUri, null);
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
      // 如果不是 document 类型的 uri，则使用普通方式处理
      imagePath = getImagePath(uri, null);
    }
    displayImage(imagePath); // 根据图片路径显示图片
  }

  private void handleImageBeforeKitKat(Intent data) {
    Uri uri = data.getData();
    String imagePath = getImagePath(uri, null);
    displayImage(imagePath);
  }

  private String getImagePath(Uri uri, String selection) {
    String path = null;
    // 通过 Uri 和 selection 来获取真实的图片路径

    Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
      }
      cursor.close();
    }
    return path;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
      int[] grantResults) {
    switch (requestCode) {
      case CODE_FOR_WRITE_PERMISSION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          hasWritePermimssion = true;
        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
          hasWritePermimssion = false;
        }
      }

    }
  }

  private void displayImage(String imagePath) {
    if (imagePath != null) {
      Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
      mListView.setBackground(new BitmapDrawable(bitmap)); // 将选择的照片显示出来

    } else {
      Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
    }
  }
}
