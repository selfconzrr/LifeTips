package com.example.zhangruirui.ks_usefulcode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Locale;

public final class NetWorkUtils {

  private NetWorkUtils() {
  }

  public static boolean isNetworkConnected(Context context) {
    try {
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
          .CONNECTIVITY_SERVICE);
      if (cm == null) {
        return false;
      } else {
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
      }
    } catch (Exception e) {
      return false;
    }
  }

  public static NetworkInfo getActiveNetworkInfo(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
        .CONNECTIVITY_SERVICE);
    return cm == null ? null : cm.getActiveNetworkInfo();
  }

  public static String getActiveNetworkTypeName(Context context) {
    NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
    if (activeNetworkInfo != null) {
      switch (activeNetworkInfo.getType()) {
        case 0: // 移动数据网络
          String typeName = activeNetworkInfo.getSubtypeName();
          if (TextUtils.isEmpty(typeName)) {
            typeName = activeNetworkInfo.getTypeName();
          }

          return typeName;
        case 1: // Wifi 网络
          return activeNetworkInfo.getTypeName();
      }
    }

    return "unknown";
  }

  public static boolean isMobileNetworkConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
        .CONNECTIVITY_SERVICE);
    if (cm == null) {
      return false;
    } else {
      NetworkInfo networkInfo = cm.getNetworkInfo(0);
      return networkInfo != null && networkInfo.isConnected();
    }
  }

  public static boolean isWifiConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
        .CONNECTIVITY_SERVICE);
    if (cm == null) {
      return false;
    } else {
      NetworkInfo networkInfo = cm.getNetworkInfo(1);
      return networkInfo != null && networkInfo.isConnected();
    }
  }

  public static String getCurrentWifiName(Context context) {
    WifiInfo wifiInfo = getWifiInfo(context);
    return wifiInfo != null ? wifiInfo.getSSID() : null;
  }

  private static WifiInfo getWifiInfo(Context context) {
    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService
        (Context.WIFI_SERVICE);
    if (wifiManager == null) {
      return null;
    } else {
      try {
        return wifiManager.getConnectionInfo();
      } catch (Exception e) {
        return null;
      }
    }
  }

  @NonNull
  public static String getCurrentWifiBSSID(Context context) {
    WifiInfo wifiInfo = getWifiInfo(context);
    return wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getBSSID()) ? wifiInfo.getBSSID() : "";
  }

  public static String getIsp(Context context) {
    try {
      TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context
          .TELEPHONY_SERVICE);
      String isp = "";
      String operator = telManager != null ? telManager.getSimOperator() : null;
      if (operator != null) {
        if (!operator.equals("46000") && !operator.equals("46002") && !operator.equals("46007")) {
          if (!operator.equals("46001") && !operator.equals("46009")) {
            if (!operator.equals("46003") && !operator.equals("46005") && !operator.equals
                ("46011")) {
              isp = telManager.getSimOperatorName();
            } else {
              isp = "中国电信";
            }
          } else {
            isp = "中国联通";
          }
        } else {
          isp = "中国移动";
        }
      }

      return isp;
    } catch (Exception e) {
      return "";
    }
  }

  public static String getNetworkType(Context context) {
    TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context
        .TELEPHONY_SERVICE);
    int networkType = mTelephonyManager.getNetworkType();
    switch (networkType) {
      case 1:
      case 2:
      case 4:
      case 7:
      case 11:
        return "2g";
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 12:
      case 14:
      case 15:
        return "3g";
      case 13:
        return "4g";
      default:
        return "Unknown";
    }
  }

  public static String getHost(String url) {
    try {
      return Uri.parse(url).getHost().toLowerCase(Locale.US);
    } catch (Exception e) {
      throw new RuntimeException("Illegal url:" + url, e);
    }
  }

  @WorkerThread
  public static String getIp(String url) {
    if (TextUtils.isEmpty(url)) {
      return "";
    } else {
      String address = "";

      try {
        String host = Uri.parse(url).getHost();
        address = InetAddress.getByName(host).getHostAddress();
      } catch (Throwable e) {
        e.printStackTrace();
      }

      return address;
    }
  }

  public static String encode(String url) {
    try {
      return URLEncoder.encode(url, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Error encoding url", e);
    }
  }

  public static String decode(String url) {
    try {
      return URLDecoder.decode(url, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Error decoding url", e);
    }
  }

  public static void getRssi(final Context context, final NetWorkUtils.NetworkRssiFetchListener
      listener) {
    if (isWifiConnected(context)) {
      WifiInfo wifiInfo = getWifiInfo(context);
      if (wifiInfo != null) {
        listener.onFetchFinish(wifiInfo.getRssi(), false);
      } else {
        listener.onFetchFinish(2147483647, false);
      }
    } else if (isMobileNetworkConnected(context)) {
      KSUtils.runOnUiThread(new Runnable() {
        public void run() {
          final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
              (Context.TELEPHONY_SERVICE);

          try {
            PhoneStateListener signalStrengthListener = new PhoneStateListener() {
              int mCallbackTimes;

              public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                telephonyManager.listen(this, 0);
                if (this.mCallbackTimes <= 0) {
                  ++this.mCallbackTimes;
                  if (telephonyManager.getNetworkType() == 13) {
                    String signalinfo = signalStrength.toString();
                    String[] parts = signalinfo.split(" ");
                    int ltedbm = 2147483647;

                    try {
                      ltedbm = Integer.parseInt(parts[9]);
                    } catch (NumberFormatException e) {
                      e.printStackTrace();
                    }

                    listener.onFetchFinish(ltedbm, true);
                  } else if (signalStrength.isGsm()) {
                    int asu = signalStrength.getGsmSignalStrength();
                    if (asu == 99) {
                      listener.onFetchFinish(2147483647, true);
                    } else {
                      int dbm = -113 + 2 * asu;
                      listener.onFetchFinish(dbm, true);
                    }
                  } else if (telephonyManager.getNetworkType() != 5 && telephonyManager
                      .getNetworkType() != 6 && telephonyManager.getNetworkType() != 12) {
                    listener.onFetchFinish(signalStrength.getCdmaDbm(), true);
                  } else {
                    listener.onFetchFinish(signalStrength.getEvdoDbm(), true);
                  }

                  super.onSignalStrengthsChanged(signalStrength);
                }
              }
            };
            telephonyManager.listen(signalStrengthListener, 256);
          } catch (Exception e) {
            listener.onFetchFinish(2147483647, true);
            e.printStackTrace();
          }

        }
      });
    } else {
      listener.onFetchFinish(2147483647, false);
    }

  }

  public static int getMCC(Context context) {
    if (!isMobileNetworkConnected(context)) {
      return -1;
    } else {
      TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      String networkOperator = tel.getNetworkOperator();
      int mcc = -1;
      if (!TextUtils.isEmpty(networkOperator)) {
        try {
          mcc = Integer.parseInt(networkOperator.substring(0, 3));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }

      return mcc;
    }
  }

  public static int getMNC(Context context) {
    if (!isMobileNetworkConnected(context)) {
      return -1;
    } else {
      TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      String networkOperator = tel.getNetworkOperator();
      int mnc = -1;
      if (!TextUtils.isEmpty(networkOperator)) {
        try {
          mnc = Integer.parseInt(networkOperator.substring(3));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }

      return mnc;
    }
  }

  @Nullable
  public static String privateIpAddress() {
    try {
      Enumeration enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces();

      while (enumerationNetworkInterface.hasMoreElements()) {
        NetworkInterface networkInterface = (NetworkInterface) enumerationNetworkInterface
            .nextElement();
        Enumeration enumerationInetAddress = networkInterface.getInetAddresses();

        while (enumerationInetAddress.hasMoreElements()) {
          InetAddress inetAddress = (InetAddress) enumerationInetAddress.nextElement();
          String ipAddress = inetAddress.getHostAddress();
          if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
            return ipAddress;
          }
        }
      }

      return null;
    } catch (Exception e) {
      return null;
    }
  }

  public static boolean isPoorerThan2G(Context context) {
    String netType = getActiveNetworkTypeName(context);
    return netType.equals("2g") || netType.equals("Notfound") || netType.equals("unknown");
  }

  public static String getBaseStateId(Context context) {
    TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context
        .TELEPHONY_SERVICE);

    try {
      if (mTelephonyManager.getCellLocation() instanceof GsmCellLocation) {
        GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
        if (location != null) {
          return String.valueOf(location.getCid());
        }
      } else if (mTelephonyManager.getCellLocation() instanceof CdmaCellLocation) {
        CdmaCellLocation location = (CdmaCellLocation) mTelephonyManager.getCellLocation();
        if (location != null) {
          return String.valueOf(location.getBaseStationId());
        }
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    }

    return "";
  }

  public static boolean hasMore(String cursor) {
    return TextUtils.isEmpty(cursor) && !"no_more".equals(cursor);
  }

  public static int getLAC(Context context) {
    if (!isMobileNetworkConnected(context)) {
      return -1;
    } else {
      TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context
          .TELEPHONY_SERVICE);

      try {
        if (mTelephonyManager.getCellLocation() instanceof GsmCellLocation) {
          GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
          if (location != null) {
            return location.getLac();
          }
        } else if (mTelephonyManager.getCellLocation() instanceof CdmaCellLocation) {
          CdmaCellLocation location = (CdmaCellLocation) mTelephonyManager.getCellLocation();
          if (location != null) {
            return location.getNetworkId();
          }
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      }

      return -1;
    }
  }

  public static int getCid(Context context) {
    if (!isMobileNetworkConnected(context)) {
      return -1;
    } else {
      TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context
          .TELEPHONY_SERVICE);

      try {
        if (mTelephonyManager.getCellLocation() instanceof GsmCellLocation) {
          GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
          if (location != null) {
            return location.getCid();
          }
        } else if (mTelephonyManager.getCellLocation() instanceof CdmaCellLocation) {
          CdmaCellLocation location = (CdmaCellLocation) mTelephonyManager.getCellLocation();
          if (location != null) {
            return location.getBaseStationId();
          }
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      }

      return -1;
    }
  }

  public interface NetworkRssiFetchListener {
    void onFetchFinish(int var1, boolean var2);
  }
}
