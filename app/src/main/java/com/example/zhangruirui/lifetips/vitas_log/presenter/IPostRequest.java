package com.example.zhangruirui.lifetips.vitas_log.presenter;

import com.example.zhangruirui.lifetips.vitas_log.model.PostTranslation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IPostRequest {
  @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen" +
      "=&ssid=&network=&abtest=")

  // why use @FormUrlEncoded：Content-Type: application/x-www-form-urlencoded
  @FormUrlEncoded
  Call<PostTranslation> getCall(@Field("i") String targetSentence);

  // postBody has a argument "i"
}
