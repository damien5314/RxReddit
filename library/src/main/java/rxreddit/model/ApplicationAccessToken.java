package rxreddit.model;

public class ApplicationAccessToken extends AccessToken {

    @Override
    public boolean isUserAccessToken() {
        return false;
    }
}
