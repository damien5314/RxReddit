package rxreddit.api;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

interface RedditAuthAPI {

  @FormUrlEncoded
  @POST("/api/v1/access_token")
  Observable<Response<ApplicationAccessToken>> getApplicationAuthToken(
      @Field("grant_type") String grantType,
      @Field("device_id") String deviceId);

  @FormUrlEncoded @POST("/api/v1/access_token")
  Observable<Response<UserAccessToken>> getUserAuthToken(
      @Field("grant_type") String grantType,
      @Field("code") String code,
      @Field("redirect_uri") String redirectUri);

  @FormUrlEncoded @POST("/api/v1/access_token")
  Observable<Response<UserAccessToken>> refreshUserAuthToken(
      @Field("grant_type") String grantType,
      @Field("refresh_token") String refreshToken);

  @FormUrlEncoded @POST("/api/v1/revoke_token")
  Observable<Void> revokeUserAuthToken(
      @Field("token") String token,
      @Field("token_type_hint") String tokenTypeHint);

}
