package com.example.zhangruirui.lifetips.vitas_log.model;

import java.util.List;

public class PostTranslation {
  private String mType;
  private String mErrorCode;
  private String mElasedTime;

  private List<List<TranslationResultBean>> mTranslateResult;

  public class TranslationResultBean {
    /*
     * src：   target
     * tgt：   target clause
     */

    private String src;
    private String tgt;

    public String getSrc() {
      return src;
    }

    public void setSrc(String src) {
      this.src = src;
    }

    public String getTgt() {
      return tgt;
    }

    public void setTgt(String tgt) {
      this.tgt = tgt;
    }
  }

  public List<List<TranslationResultBean>> getTranslateResult() {
    return mTranslateResult;
  }

  public void setTranslateResult(List<List<TranslationResultBean>> translateResult) {
    this.mTranslateResult = translateResult;
  }

  public String getType() {
    return mType;
  }

  public void setType(String type) {
    this.mType = type;
  }

  public String getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(String errorCode) {
    this.mErrorCode = errorCode;
  }

  public String getElasedTime() {
    return mElasedTime;
  }

  public void setElasedTime(String elasedTime) {
    this.mElasedTime = elasedTime;
  }
}