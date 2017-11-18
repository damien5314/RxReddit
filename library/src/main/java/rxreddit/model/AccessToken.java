package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public abstract class AccessToken {

    private long created = new Date().getTime();

    @SerializedName("access_token")
    protected String token;

    @SerializedName("token_type")
    protected String tokenType;

    @SerializedName("expires_in")
    protected long secondsToExpiration;
    protected long expirationUtc;

    @SerializedName("scope")
    protected String scope;

    @SerializedName("refresh_token")
    protected String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiration() {
        if (expirationUtc == 0) expirationUtc = secondsToExpiration * 1000 + created;
        return expirationUtc;
    }

    public void setExpiration(long expiration) {
        this.expirationUtc = expiration;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long secondsUntilExpiration() {
        return Math.max(0, (getExpiration() - System.currentTimeMillis()) / 1000);
    }

    public abstract boolean isUserAccessToken();
}