package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public abstract class AccessToken {

    private long created = new Date().getTime();
    private long expirationUtc;

    @SerializedName("access_token") String token;
    @SerializedName("token_type") String tokenType;
    @SerializedName("expires_in") long secondsToExpiration;
    @SerializedName("scope") String scope;
    @SerializedName("refresh_token") String refreshToken;

    public AccessToken(String token, String tokenType, long secondsToExpiration, String scope, String refreshToken) {
        this.token = token;
        this.tokenType = tokenType;
        this.secondsToExpiration = secondsToExpiration;
        this.scope = scope;
        this.refreshToken = refreshToken;
    }

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
        if (expirationUtc == 0) {
            expirationUtc = secondsToExpiration * 1000 + created;
        }
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