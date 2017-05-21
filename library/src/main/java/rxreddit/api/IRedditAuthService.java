package rxreddit.api;

import io.reactivex.Observable;
import rxreddit.model.AccessToken;
import rxreddit.model.UserAccessToken;

interface IRedditAuthService {

    String getRedirectUri();

    String getAuthorizationUrl();

    Observable<UserAccessToken> onAuthCodeReceived(String authCode, String state);

    boolean isUserAuthorized();

    AccessToken getAccessToken();

    Observable<AccessToken> refreshAccessToken();

    Observable<UserAccessToken> refreshUserAccessToken();

    Observable<Void> revokeUserAuthentication();

}
