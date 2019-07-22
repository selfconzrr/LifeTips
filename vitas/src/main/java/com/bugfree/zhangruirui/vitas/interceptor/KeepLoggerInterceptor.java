package com.bugfree.zhangruirui.vitas.interceptor;

import com.bugfree.zhangruirui.vitas.Vitas;
import com.bugfree.zhangruirui.vitas.repo.RequestInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * LoggerInterceptor，Convenient for developers to get http request and response status.
 * <p>
 * we present url、headers、content-type、response、request and so on.
 */
public class KeepLoggerInterceptor implements Interceptor {
  private static final Charset UTF8 = Charset.forName("UTF-8");

  public interface Logger {
    void log(String message);

    Logger DEFAULT = new Logger() {
      boolean isLogEnable = Vitas.getInstance().isEnable();

      @Override
      public void log(String message) {
        if (this.isLogEnable) {
          Platform.get().log(INFO, message, null);
        }
      }
    };
  }

  public KeepLoggerInterceptor() {
    this(Logger.DEFAULT);
  }

  public KeepLoggerInterceptor(Logger logger) {
    this.logger = logger;
  }

  private final Logger logger;

  @Override
  public Response intercept(Chain chain) throws IOException {
    // BuildConfig.DEBUG is a variable to see if we are in Debug mode.
    // If is't Debug mode, we do nothing.
    Request request = chain.request();
    RequestInfo requestInfo = new RequestInfo();
    if (!Vitas.getInstance().isEnable()) {
      Response noDebugResponse;
      noDebugResponse = chain.proceed(request);
      return noDebugResponse;
    }

    RequestBody requestBody = request.body();

    boolean hasRequestBody = requestBody != null;
    Connection connection = chain.connection();
    String requesStartMessage = "-->" +
        request.method() +
        " " + request.url()
        + (connection != null ? " " + connection.protocol() : "");
    requestInfo.setUrl(request.url().toString());
    requestInfo.setMethod(request.method());
    if (hasRequestBody) {
      String subtype = requestBody.contentType().subtype();
      if (subtype.contains("json") || subtype.contains("form")) {
        requestInfo.setRequestBody(bodyToString(requestBody, request));
      }
      requesStartMessage += "(requestBody:" + requestInfo.getRequestBody() + ")";
    }

    requestInfo.setRequestTime(getCurrentTime());
    requesStartMessage += "\r\n requestTime:" + requestInfo.getRequestTime();
    logger.log(requesStartMessage);

    if (requestBody != null && requestBody.contentType() != null) {
      requestInfo.setRequestContentType(requestBody.contentType().toString());
      logger.log("Content-Type:" + requestInfo.getRequestContentType());
    }

    // Headers
    Headers headers = request.headers();
    String headersFormatterString = headers.toString();
    headersFormatterString = headersFormatterString.replaceAll("\n", "<br>");
    requestInfo.setRequestHeaders(headersFormatterString + " HAHA");
    logger.log(headers.toString());

    // Response
    long startNS = System.nanoTime();
    Response response;
    try {
      response = chain.proceed(request);
    } catch (Exception e) {
      logger.log("<-- HTTP FAILED:" + e);
      throw e;
    }
    // tookTime
    requestInfo.setTookTimeMS(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNS));

    // ResponseCode
    requestInfo.setResponseCode(response.code());
    // responseBody
    ResponseBody responseBody = response.body();

    // ResponseContentType
    if (responseBody != null && responseBody.contentType() != null) {
      requestInfo.setResponseContentType(responseBody.contentType().toString());
    }

    //
    if (HttpHeaders.hasBody(response) && (requestInfo.getResponseContentType().contains
        ("text/html") || requestInfo.getResponseContentType().contains("text/plain")
        || requestInfo.getResponseContentType().contains("application/json"))) {
      assert responseBody != null;
      BufferedSource source = responseBody.source();
      source.request(Long.MAX_VALUE);
      Buffer buffer = source.buffer();

      if ("gzip".equalsIgnoreCase(response.header("Content-Encoding")) || "gzip".equals(response
          .header("content-encoding"))) {
        GzipSource gzippedResponseBody = null;
        try {
          gzippedResponseBody = new GzipSource(buffer.clone());
          buffer = new Buffer();
          buffer.writeAll(gzippedResponseBody);
        } finally {
          if (gzippedResponseBody != null) {
            gzippedResponseBody.close();
          }
          requestInfo.setResponseBody(buffer.clone().readString(UTF8).replaceAll("%20", " "));
        }
      }

      logger.log("ResponseBody:" + requestInfo.getResponseBody());

    }// If we have responseBody

    //responseHeaders
    headersFormatterString = response.headers().toString();
    headersFormatterString = headersFormatterString.replaceAll("\n", "<br>");

    requestInfo.setResponseHeaders(headersFormatterString);
    logger.log("<-- Response status:");
    logger.log("responseCode:" + requestInfo.getResponseCode());
    logger.log("tookTimeMS:" + requestInfo.getTookTimeMS());
    logger.log(requestInfo.getResponseHeaders());
    logger.log("<--END HTTP ");

    Vitas.getInstance().getLogRepository().saveToDB(requestInfo, null);
    return response;
  }

  /**
   * get Formatted SystemDate;
   */
  private String getCurrentTime() {
    long time = System.currentTimeMillis();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(time);
    return format.format(date);
  }

  /**
   * turn RequestBody to string.
   */
  private String bodyToString(final RequestBody requestBody, Request request) {
    try {
      final RequestBody copy = requestBody;

      final Buffer buffer = new Buffer();
      if (copy != null) {
        copy.writeTo(buffer);
        // Judge if requestBody is gzipped.
        GzipSource source = null;
        if ("gzip".equalsIgnoreCase(request.header("Content-Encoding")) || "gzip".equals(request
            .header("content-encoding"))) {
          try {
            source = new GzipSource(buffer.clone());
            buffer.writeAll(source);
          } finally {
            if (source != null) {
                source.close();
            }
          }
        }
      } else {
        return "";
      }
      return buffer.readUtf8().replaceAll("%20", " ");
    } catch (IOException e) {
      return "did not work";
    }

  }
}