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

    /**
     * No-op implementation
     */
    AccessTokenManager NONE = new AccessTokenManager() {
        @Override
        public UserAccessToken getUserAccessToken() {
            return null;
        }

        @Override
        public ApplicationAccessToken getApplicationAccessToken() {
            return null;
        }

        @Override
        public void saveUserAccessToken(UserAccessToken token) {
        }

        @Override
        public void saveApplicationAccessToken(ApplicationAccessToken token) {
        }

        @Override
        public void clearSavedUserAccessToken() {
        }

        @Override
        public void clearSavedApplicationAccessToken() {
        }
    };
}
