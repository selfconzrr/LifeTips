package com.example.zhangruirui.lifetips.demo_learning.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangruirui.lifetips.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/05/18
 */
public class DialogShowHelper extends Dialog implements View.OnClickListener {
  @BindView(R.id.img_picture)
  SimpleDraweeView mPicture;
  @BindView(R.id.btn_allow_access)
  Button confirm;
  @BindView(R.id.btn_not_now)
  Button cancel;
  @BindView(R.id.btn_close)
  Button close;
  @BindView(R.id.tv_show_title)
  TextView dialogTitle;
  @BindView(R.id.tv_description)
  TextView dialogDescription;

  private String mURL;
  private Context mContext;

  public DialogShowHelper(Context context) {
    super(context);
    this.mContext = context;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    showDialog();
  }

  // 第一步
  private void showDialog() {
    // 创建一个Dialog对象，如果是AlertDialog对象的话，弹出的自定义布局四周会有一些阴影，效果不好
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    LayoutInflater inflater = LayoutInflater.from(mContext);
    View dialogView = inflater.inflate(R.layout.new_dialog, null);
    setContentView(dialogView);
    // 必需放在此处，否则会提示找不到对应的控件
    ButterKnife.bind(this, dialogView);

    initDialogView(dialogView);

    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    Window window = getWindow();
    WindowManager windowManager = window.getWindowManager();
    // 如果你的背景是带圆角的对话框，必需加这句话
    // 否则有个白底在那儿，你的dialog也是白色的话是看不到圆角的
    window.setBackgroundDrawableResource(android.R.color.transparent);
    Display display = windowManager.getDefaultDisplay();
    lp.copyFrom(window.getAttributes());
    lp.width = display.getWidth() - 105;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    window.setAttributes(lp);
    // 设置点击其它地方不让消失弹窗
//    setCancelable(false);
  }

  // 第二步
  private void initDialogView(View dialogView) {

    mPicture = dialogView.findViewById(R.id.img_picture);
    try {
      InputStreamReader isr = new InputStreamReader(mContext.getAssets().open("dialog.json"),
          "UTF-8");
      BufferedReader br = new BufferedReader(isr);
      String line;
      StringBuilder builder = new StringBuilder();
      while ((line = br.readLine()) != null) {
        builder.append(line);
      }
      br.close();
      isr.close();

      Gson gson = new Gson();
      DialogModel dialogModel = gson.fromJson(builder.toString(), DialogModel.class);

      int typeWindow = dialogModel.getType();// 窗口类型处理
      if (typeWindow == 0) { // 0代表无图弹窗
        mPicture.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
      } else if (typeWindow == 1) { // 1代表有图弹窗
        final String picURL = dialogModel.getPicture();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(picURL))
            .setProgressiveRenderingEnabled(true)
            .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setOldController(mPicture.getController())
            .build();
        mPicture.setController(controller);// 实现渐进式图片
//        mPicture.setImageURI(Uri.parse(picURL));
      } else {
        // LEFT NOTHING
      }

      for (int i = 0; i < dialogModel.getBtns().size(); i++) {

        int index = dialogModel.getBtns().get(i).getType();
        if (index == 0) {
          confirm.setText(dialogModel.getBtns().get(i).getText());
          mURL = dialogModel.getBtns().get(i).getUrl().getValue();
        } else if (index == 1) {
          cancel.setText(dialogModel.getBtns().get(i).getText());
        } else {
          // LOOP THROUGH
        }
      }

      dialogTitle.setText(dialogModel.getTitle());
      dialogDescription.setText(dialogModel.getDesc());
      confirm.setOnClickListener(this);
      cancel.setOnClickListener(this);
      close.setOnClickListener(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 第四步
  private void launchGooglePlay(String appPkg) {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      // intent.setData(Uri.parse("market://details?id=" + appPkg));
      // 通过包名好像找不到
      intent.setData(Uri.parse(appPkg)); // 打开手机的应用市场，通过搜索关键词
      if (intent.resolveActivity(mContext.getPackageManager()) != null) {
        mContext.startActivity(intent);
      } else { // 如果没有安装Google play APP，通过浏览器跳转
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" +
            "packageName"));// getPackageName()
        Toast.makeText(mContext, "天啊，居然没安装Google Play，您买安卓手机干啥？", Toast.LENGTH_SHORT).show();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_allow_access) {
      launchGooglePlay(mURL);
    } else if (id == R.id.btn_not_now) {
      dismiss();
    } else if (id == R.id.btn_close) {
      dismiss();
    }
  }
}
