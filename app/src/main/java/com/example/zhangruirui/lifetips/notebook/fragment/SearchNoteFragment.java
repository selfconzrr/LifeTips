package com.example.zhangruirui.lifetips.notebook.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.notebook.activity.NoteDetailActivity;
import com.example.zhangruirui.lifetips.notebook.adapter.ShowNoteAdapter;
import com.example.zhangruirui.lifetips.notebook.db.NoteDAO;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/24/18
 * <p>
 * 根据标题查找所有匹配的 notes
 */
public class SearchNoteFragment extends Fragment {
  private EditText mEtSearch;
  private ListView mLvResult;
  private CursorAdapter mAdapter;
  private NoteDAO mNoteDAO;
  private Cursor mCursor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mNoteDAO = new NoteDAO(getActivity());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_search, container, false);
    mEtSearch = root.findViewById(R.id.id_et_search_title);
    mLvResult = root.findViewById(R.id.id_lv_found_note);
    Button btnQuery = root.findViewById(R.id.id_btn_search);
    // 查询操作
    btnQuery.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String title = mEtSearch.getText().toString();
        if (title.length() > 0) {
          mCursor = mNoteDAO.queryNote("title like ? ", new String[]{"%" + title + "%"});
        }
        if (!mCursor.moveToNext()) {
          Toast.makeText(getActivity(), "没有这个结果", Toast.LENGTH_SHORT).show();
        }
        mAdapter = new ShowNoteAdapter(getActivity(), mCursor);
        mLvResult.setAdapter(mAdapter);
      }
    });
    mLvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = (Cursor) mAdapter.getItem(position); // CursorAdapter中getItem()返回特定的cursor对象
        int itemID = c.getInt(c.getColumnIndex("_id"));
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.SENDED_NOTE_ID, itemID);
        startActivity(intent);
      }
    });
    return root;
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mCursor != null) {
      mCursor.close();
    }
  }
}
