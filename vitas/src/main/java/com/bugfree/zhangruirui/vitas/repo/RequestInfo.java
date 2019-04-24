package com.bugfree.zhangruirui.vitas.repo;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestInfo implements Parcelable {

  private int mId;

  //Request message
  private String mUrl;
  private String mRequestHeaders;
  private String method;
  private String mRequestBody;
  private String mRequestContentType;
  private String mRequestTime;

  //Response message
  private int mResponseCode;
  private String mResponseHeaders;
  private long mTookTimeMS;
  private String mResponseContentType;
  private String mResponseBody;

  public RequestInfo() {
  }

  public RequestInfo(int mId, String mUrl, String requestHeaders, String method, String
      requestBody, int responseCode, String responseHeaders, long tookTimeMS, String contentType,
                     String responseBody, String requestTime) {
    this.mId = mId;
    this.mUrl = mUrl;
    this.mRequestHeaders = requestHeaders;
    this.method = method;
    this.mRequestBody = requestBody;
    this.mResponseCode = responseCode;
    this.mResponseHeaders = responseHeaders;
    this.mTookTimeMS = tookTimeMS;
    this.mResponseContentType = contentType;
    this.mResponseBody = responseBody;
    this.mRequestTime = requestTime;
  }

  protected RequestInfo(Parcel in) {
    mId = in.readInt();
    mUrl = in.readString();
    mRequestHeaders = in.readString();
    method = in.readString();
    mRequestBody = in.readString();
    mRequestContentType = in.readString();
    mRequestTime = in.readString();
    mResponseCode = in.readInt();
    mResponseHeaders = in.readString();
    mTookTimeMS = in.readLong();
    mResponseContentType = in.readString();
    mResponseBody = in.readString();
  }

  public int getId() {
    return mId;
  }

  public String getUrl() {
    return mUrl;
  }

  public String getRequestHeaders() {
    return mRequestHeaders;
  }

  public String getMethod() {
    return method;
  }

  public String getRequestBody() {
    return mRequestBody;
  }

  public int getResponseCode() {
    return mResponseCode;
  }

  public String getResponseHeaders() {
    return mResponseHeaders;
  }

  public long getTookTimeMS() {
    return mTookTimeMS;
  }

  public String getResponseContentType() {
    return mResponseContentType;
  }

  public String getResponseBody() {
    return mResponseBody;
  }

  public String getRequestContentType() {
    return mRequestContentType;
  }

  public void setId(int id) {
    mId = id;
  }

  public void setUrl(String url) {
    mUrl = url;
  }

  public void setRequestHeaders(String requestHeaders) {
    mRequestHeaders = requestHeaders;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setRequestBody(String requestBody) {
    mRequestBody = requestBody;
  }

  public void setRequestContentType(String requestContentType) {
    mRequestContentType = requestContentType;
  }

  public void setResponseCode(int responseCode) {
    mResponseCode = responseCode;
  }

  public void setResponseHeaders(String responseHeaders) {
    mResponseHeaders = responseHeaders;
  }

  public void setTookTimeMS(long tookTimeMS) {
    mTookTimeMS = tookTimeMS;
  }

  public void setResponseContentType(String responseContentType) {
    mResponseContentType = responseContentType;
  }

  public void setResponseBody(String responseBody) {
    mResponseBody = responseBody;
  }

  public String getRequestTime() {
    return mRequestTime;
  }

  public void setRequestTime(String requestTime) {
    mRequestTime = requestTime;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

    dest.writeInt(mId);
    dest.writeString(mUrl);
    dest.writeString(mRequestHeaders);
    dest.writeString(method);
    dest.writeString(mRequestBody);
    dest.writeString(mRequestContentType);
    dest.writeString(mRequestTime);
    dest.writeInt(mResponseCode);
    dest.writeString(mResponseHeaders);
    dest.writeLong(mTookTimeMS);
    dest.writeString(mResponseContentType);
    dest.writeString(mResponseBody);
  }

  public static final Creator<RequestInfo> CREATOR = new Creator<RequestInfo>() {
    @Override
    public RequestInfo createFromParcel(Parcel source) {
      RequestInfo requestInfo = new RequestInfo();
      requestInfo.setId(source.readInt());
      requestInfo.setUrl(source.readString());
      requestInfo.setRequestHeaders(source.readString());
      requestInfo.setMethod(source.readString());
      requestInfo.setRequestBody(source.readString());
      requestInfo.setRequestContentType(source.readString());
      requestInfo.setRequestTime(source.readString());
      requestInfo.setResponseCode(source.readInt());
      requestInfo.setResponseHeaders(source.readString());
      requestInfo.setTookTimeMS(source.readLong());
      requestInfo.setResponseContentType(source.readString());
      requestInfo.setResponseBody(source.readString());
      return requestInfo;
    }

    @Override
    public RequestInfo[] newArray(int size) {
      return new RequestInfo[0];
    }
  };
}