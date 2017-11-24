package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public abstract class AccessToken {

    @SerializedName("access_token") String token;
    @SerializedName("token_type") String tokenType;
    @SerializedName("expires_in") long secondsToExpiration;
    @SerializedName("scope") String scope;
    @SerializedName("refresh_token") String refreshToken;

    private final long createdMs = System.currentTimeMillis();
    private long expirationUtcMs;

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

    public long getExpirationMs() {
        if (expirationUtcMs == 0) {
            expirationUtcMs = secondsToExpiration * 1000 + createdMs;
        }
        return expirationUtcMs;
    }

    public void setExpiration(long expiration) {
        this.expirationUtcMs = expiration;
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
        final long secondsUntilExpiration = (getExpirationMs() - System.currentTimeMillis()) / 1000;
        return Math.max(0, secondsUntilExpiration);
    }

    public abstract boolean isUserAccessToken();
}