package rxreddit.model;

public class ApplicationAccessToken extends AccessToken {

    public ApplicationAccessToken(String token, String tokenType, long secondsToExpiration, String scope, String refreshToken) {
        super(token, tokenType, secondsToExpiration, scope, refreshToken);
    }

    @Override
    public boolean isUserAccessToken() {
        return false;
    }
}
