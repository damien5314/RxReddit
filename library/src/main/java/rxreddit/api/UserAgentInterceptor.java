package rxreddit.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* This interceptor adds a custom User-Agent. */
final class UserAgentInterceptor implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", userAgent)
                .build();
        return chain.proceed(newRequest);
    }
}