package rxreddit.android;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class AndroidUtil {

  public static String getDeviceId(Context context) {
    final String PREFS_DEVICE_ID = "prefs_device_id";
    final String PREF_DEVICE_ID = "device_id";
    SharedPreferences sp = context.getSharedPreferences(PREFS_DEVICE_ID, Context.MODE_PRIVATE);
    String deviceId = sp.getString(PREF_DEVICE_ID, null);
    if (deviceId == null) {
      deviceId = UUID.randomUUID().toString();
      sp.edit().putString(PREF_DEVICE_ID, deviceId).apply();
    }
    return deviceId;
  }
}
