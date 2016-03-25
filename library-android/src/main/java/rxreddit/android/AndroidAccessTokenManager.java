package rxreddit.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import rxreddit.api.AccessTokenManager;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

public class AndroidAccessTokenManager implements AccessTokenManager {
  private static final String PREFS_USER_ACCESS_TOKEN = "prefs_user_access_token";
  private static final String PREFS_APPLICATION_ACCESS_TOKEN = "prefs_application_access_token";
  private static final String PREF_ACCESS_TOKEN = "pref_access_token";
  private static final String PREF_TOKEN_TYPE = "pref_token_type";
  private static final String PREF_EXPIRATION = "pref_expiration";
  private static final String PREF_SCOPE = "pref_scope";
  private static final String PREF_REFRESH_TOKEN = "pref_refresh_token";

  private Context mContext;

  public AndroidAccessTokenManager(Context context) {
    mContext = context.getApplicationContext();
  }

  @Override
  public UserAccessToken getUserAccessToken() {
    return getSavedUserAccessToken();
  }

  private UserAccessToken getSavedUserAccessToken() {
    SharedPreferences sp =  mContext.getSharedPreferences(
        PREFS_USER_ACCESS_TOKEN, Context.MODE_PRIVATE);
    if (!sp.contains(PREF_ACCESS_TOKEN)) return null;
    UserAccessToken token = new UserAccessToken();
    token.setToken(sp.getString(PREF_ACCESS_TOKEN, null));
    token.setTokenType(sp.getString(PREF_TOKEN_TYPE, null));
    token.setExpiration(sp.getLong(PREF_EXPIRATION, 0));
    token.setScope(sp.getString(PREF_SCOPE, null));
    token.setRefreshToken(sp.getString(PREF_REFRESH_TOKEN, null));
    return token;
  }

  @Override
  public ApplicationAccessToken getApplicationAccessToken() {
    return getSavedApplicationAccessToken();
  }

  private ApplicationAccessToken getSavedApplicationAccessToken() {
    SharedPreferences sp =  mContext.getSharedPreferences(
        PREFS_APPLICATION_ACCESS_TOKEN, Context.MODE_PRIVATE);
    if (!sp.contains(PREF_ACCESS_TOKEN)) return null;
    ApplicationAccessToken token = new ApplicationAccessToken();
    token.setToken(sp.getString(PREF_ACCESS_TOKEN, null));
    token.setTokenType(sp.getString(PREF_TOKEN_TYPE, null));
    token.setExpiration(sp.getLong(PREF_EXPIRATION, 0));
    token.setScope(sp.getString(PREF_SCOPE, null));
    token.setRefreshToken(sp.getString(PREF_REFRESH_TOKEN, null));
    return token;
  }

  @Override
  public void saveUserAccessToken(UserAccessToken token) {
    UserAccessToken saved = getSavedUserAccessToken();
    // Swap in the saved refresh token if we didn't get a new one
    if (token.getRefreshToken() == null && saved != null) {
      token.setRefreshToken(saved.getRefreshToken());
    }
    SharedPreferences.Editor editor =
        mContext.getSharedPreferences(PREFS_USER_ACCESS_TOKEN, Context.MODE_PRIVATE).edit();
    editor.putString(PREF_ACCESS_TOKEN, token.getToken())
        .putString(PREF_REFRESH_TOKEN, token.getRefreshToken())
        .putString(PREF_TOKEN_TYPE, token.getTokenType())
        .putLong(PREF_EXPIRATION, token.getExpiration())
        .putString(PREF_SCOPE, token.getScope());
    if (Build.VERSION.SDK_INT >= 9) editor.apply(); else editor.commit();
  }

  @Override
  public void saveApplicationAccessToken(ApplicationAccessToken token) {
    SharedPreferences.Editor editor = mContext.getSharedPreferences(
        PREFS_APPLICATION_ACCESS_TOKEN, Context.MODE_PRIVATE).edit();
    editor
        .putString(PREF_ACCESS_TOKEN, token.getToken())
        .putString(PREF_TOKEN_TYPE, token.getTokenType())
        .putLong(PREF_EXPIRATION, token.getExpiration())
        .putString(PREF_SCOPE, token.getScope())
        .putString(PREF_REFRESH_TOKEN, token.getRefreshToken());
    if (Build.VERSION.SDK_INT >= 9) editor.apply(); else editor.commit();
  }

  @Override
  public void clearSavedUserAccessToken() {
    SharedPreferences.Editor editor =
        mContext.getSharedPreferences(PREFS_USER_ACCESS_TOKEN, Context.MODE_PRIVATE)
            .edit()
            .clear();
    if (Build.VERSION.SDK_INT >= 9) editor.apply(); else editor.commit();
  }

  @Override
  public void clearSavedApplicationAccessToken() {
    SharedPreferences.Editor editor =
        mContext.getSharedPreferences(PREFS_APPLICATION_ACCESS_TOKEN, Context.MODE_PRIVATE)
            .edit()
            .clear();
    if (Build.VERSION.SDK_INT >= 9) editor.apply(); else editor.commit();
  }
}
