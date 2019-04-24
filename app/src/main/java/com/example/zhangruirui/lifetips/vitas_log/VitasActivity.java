package com.example.zhangruirui.lifetips.vitas_log;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bugfree.zhangruirui.vitas.Vitas;
import com.bugfree.zhangruirui.vitas.interceptor.KeepLoggerInterceptor;
import com.bugfree.zhangruirui.vitas.view.ShowLogActivity;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.vitas_log.model.GetTranslation;
import com.example.zhangruirui.lifetips.vitas_log.model.PostTranslation;
import com.example.zhangruirui.lifetips.vitas_log.presenter.IGetRequest;
import com.example.zhangruirui.lifetips.vitas_log.presenter.IPostRequest;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VitasActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity:xiong:";
  private TextView mTvCurrentRequest;
  private Button mBtnGetRequest;
  private Button mBtnClearLog;
  private Button mBtnGoToShowActivity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.module_activity_main);
    mTvCurrentRequest = findViewById(R.id.tv_current_equest);
    mBtnGetRequest = findViewById(R.id.btn_get_a_request);
    mBtnClearLog = findViewById(R.id.btn_clear);
    mBtnGoToShowActivity = findViewById(R.id.btn_show);
    mBtnGetRequest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        postRequest();
      }
    });
    mBtnClearLog.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Vitas.getInstance().getLogRepository().clearDB();
      }
    });
    mBtnGoToShowActivity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(VitasActivity.this, ShowLogActivity.class);
        startActivity(intent);
      }
    });
    Vitas.getInstance().init(this);
    Vitas.getInstance().setEnable(true);
  }

  /**
   * post request
   */
  public void postRequest() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    KeepLoggerInterceptor logging = new KeepLoggerInterceptor(new KeepLoggerInterceptor.Logger() {
      @Override
      public void log(String message) {
        Log.d("HttpLogInfo:xiong:", message);
      }
    });

    httpClient.addInterceptor(logging);


    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
        .addConverterFactory(GsonConverterFactory.create()) //Use Gson
        .client(httpClient.build())
        .build();
    final IPostRequest request = retrofit.create(IPostRequest.class);
    Call<PostTranslation> call = request.getCall("I love you");
    call.enqueue(new Callback<PostTranslation>() {
      @Override
      public void onResponse(Call<PostTranslation> call, Response<PostTranslation> response) {
        String json = new Gson().toJson(response.body());
        Log.d(TAG, "onResponse: responseBody的值:" + json);
        mTvCurrentRequest.setText(String.format("response: \r\n %s", response.raw()));
      }

      @Override
      public void onFailure(Call<PostTranslation> call, Throwable t) {
        Log.d(TAG, "onFailure: Connect Failed");
        mTvCurrentRequest.setText("Connect Failed");
      }
    });
  }

  /**
   * get A Request
   */
  public void getRequest() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    HttpLoggingInterceptor logging2 = new HttpLoggingInterceptor(new HttpLoggingInterceptor
        .Logger() {
      @Override
      public void log(String message) {
        Log.d("log:_xiong", message);
      }
    });
    logging2.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpClient.addNetworkInterceptor(logging2);

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://fy.iciba.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    final IGetRequest request = retrofit.create(IGetRequest.class);
    Call<GetTranslation> call = request.getCall();
    call.enqueue(new Callback<GetTranslation>() {
      @Override
      public void onResponse(Call<GetTranslation> call, retrofit2.Response<GetTranslation>
          response) {
        assert response.body() != null;
        response.body().show();
      }

      @Override
      public void onFailure(Call<GetTranslation> call, Throwable t) {
        Log.d(TAG, "onFailure: Connect Failed");

      }
    });
  }
}
