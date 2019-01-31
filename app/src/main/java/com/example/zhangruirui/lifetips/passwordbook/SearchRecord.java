package com.example.zhangruirui.lifetips.passwordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangruirui.lifetips.R;

public class SearchRecord extends AppCompatActivity {

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_record);
    Button button_search = findViewById(R.id.search_button01);

    final EditText searchText = findViewById(R.id.search_edittext01);

    button_search.setOnClickListener(v -> {
      Intent intent = new Intent();

      Bundle bundle = new Bundle();
      bundle.putString("searchKey", searchText.getText().toString());
      intent.setClass(SearchRecord.this, SearchResult.class);
      intent.putExtras(bundle);
      startActivity(intent);
      SearchRecord.this.finish();
    });
  }
}
