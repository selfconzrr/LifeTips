package com.bugfree.zhangruirui.vitas.view;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.bugfree.zhangruirui.vitas.R;
import com.bugfree.zhangruirui.vitas.repo.RequestInfo;

public class LogDetailActivity extends BaseActivity {

  private static final String TAG = "LogDetailActivity:";
  public static final String REQUEST_INFO = "requestInfo";

  private RequestInfo mRequestInfo;
  private TextView mTvDetail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.moudle_activity_log_detail);
    initView();
    mRequestInfo = getIntent().getParcelableExtra(REQUEST_INFO);
    showInWindows();
    Log.d(TAG, "onCreate: " + mRequestInfo.getUrl());
  }


  @Override
  protected void initView() {
    super.initView();
    mTvDetail = findViewById(R.id.tv_http_detail);
  }

  /**
   * Format the mRequestInfo and display it in two TextViews respectively.
   */
  private void showInWindows() {
    String tagH1Start = getResources().getString(R.string.tag_h1_start);
    String tagH1End = getResources().getString(R.string.tag_h1_end);
    String tagFontStart = getResources().getString(R.string.tag_font_strong_start);
    String tagFontEnd = getResources().getString(R.string.tag_font_strong_end);
    String tagBr = getResources().getString(R.string.tag_br);

    String detail = String.format(getString(R.string.show_detial_template),
        tagH1Start, tagH1End + tagFontStart, tagFontEnd + mRequestInfo.getUrl() + tagBr +
            tagFontStart, tagFontEnd + mRequestInfo.getRequestTime() + tagBr + tagFontStart,
        tagFontEnd + mRequestInfo.getRequestHeaders() + tagFontStart, tagFontEnd + mRequestInfo
            .getMethod() + tagBr + tagFontStart, tagFontEnd + mRequestInfo.getRequestBody() +
            tagBr + tagFontStart + tagH1Start,
        tagH1End + tagFontStart, tagFontEnd + mRequestInfo.getResponseCode() + tagBr +
            tagFontStart, tagFontEnd + mRequestInfo.getTookTimeMS() + tagBr + tagFontStart,
        tagFontEnd + mRequestInfo.getResponseHeaders() + tagFontStart,
        tagFontEnd + mRequestInfo.getResponseBody());

    mTvDetail.setText(Html.fromHtml(detail));
  }
}

