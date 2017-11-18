package rxreddit.model;

import java.util.Date;

public class UserAccessToken extends AccessToken {

    @Override
    public boolean isUserAccessToken() {
        return true;
    }

    @Override
    public String toString() {
        return "Access Token: " + (isUserAccessToken() ? "User" : "Application")
                + " - Expires: " + new Date(getExpirationMs());
    }
}
