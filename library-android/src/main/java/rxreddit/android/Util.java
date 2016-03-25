package rxreddit.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.UUID;

public class Util {

  public static String getDeviceId(Context context) {
    final String PREFS_DEVICE_ID = "prefs_device_id";
    final String PREF_DEVICE_ID = "device_id";
    SharedPreferences sp = context.getSharedPreferences(PREFS_DEVICE_ID, Context.MODE_PRIVATE);
    String deviceId = sp.getString(PREF_DEVICE_ID, null);
    if (deviceId == null) {
      deviceId = UUID.randomUUID().toString();
      SharedPreferences.Editor e = sp.edit().putString(PREF_DEVICE_ID, deviceId);
      if (Build.VERSION.SDK_INT >= 9) e.apply(); else e.commit();
    }
    return deviceId;
  }

  public static String getUserAgent(
      String platform, String pkgName, String versionName, String username) {
    return String.format("%s:%s:%s (by /u/%s)", platform, pkgName, versionName, username);
  }
}
