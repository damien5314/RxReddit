package rxreddit.api;

import rx.functions.Action1;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

public interface AccessTokenManager {
  UserAccessToken getUserAccessToken();
  ApplicationAccessToken getApplicationAccessToken();
  Action1<UserAccessToken> saveUserAccessToken();
  Action1<ApplicationAccessToken> saveApplicationAccessToken();
  void clearSavedUserAccessToken();
  void clearSavedApplicationAccessToken();
}
