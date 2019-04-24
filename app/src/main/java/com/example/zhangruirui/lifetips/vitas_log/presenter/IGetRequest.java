package com.example.zhangruirui.lifetips.vitas_log.presenter;

import com.example.zhangruirui.lifetips.vitas_log.model.GetTranslation;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetRequest {
  @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20erveybody")
  Call<GetTranslation> getCall();
}
