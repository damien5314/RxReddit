package rxreddit.model;

import java.util.Date;

public class UserAccessToken extends AccessToken {

    public UserAccessToken(String token, String tokenType, long secondsToExpiration, String scope, String refreshToken) {
        super(token, tokenType, secondsToExpiration, scope, refreshToken);
    }

    @Override
    public boolean isUserAccessToken() {
        return true;
    }

    @Override
    public String toString() {
        return "Access Token: " + (isUserAccessToken() ? "User" : "Application")
                + " - Expires: " + new Date(getExpiration());
    }
}
