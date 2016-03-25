package rxreddit.api;

import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

public interface AccessTokenManager {
  UserAccessToken getUserAccessToken();
  ApplicationAccessToken getApplicationAccessToken();
  void saveUserAccessToken(UserAccessToken token);
  void saveApplicationAccessToken(ApplicationAccessToken token);
  void clearSavedUserAccessToken();
  void clearSavedApplicationAccessToken();
}
