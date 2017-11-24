package rxreddit.model;

import java.util.Date;

public class UserAccessToken extends AccessToken {

    @Override
    public boolean isUserAccessToken() {
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Access Token: %s - Expires: %s",
                (isUserAccessToken() ? "User" : "Application"),
                new Date(getExpirationMs())
        );
    }
}
