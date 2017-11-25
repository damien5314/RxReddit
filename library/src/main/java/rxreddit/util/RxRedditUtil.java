package rxreddit.util;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import retrofit2.HttpException;
import retrofit2.Response;

public class RxRedditUtil {

    /**
     * Returns a string formed from a random UUID
     */
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a proper user agent string from the provided parameters
     *
     * @param platform    Platform for which application is being developed
     * @param pkgName     Package name for the application
     * @param versionName Version of the application
     * @param username    reddit username of the application developer
     * @return Formatted user agent string
     */
    public static String getUserAgent(
            String platform, String pkgName, String versionName, String username) {
        return String.format("%s:%s:%s (by /u/%s)", platform, pkgName, versionName, username);
    }

    public static Map<String, String> getQueryParametersFromUrl(String url)
            throws IllegalArgumentException {
        if (url == null) throw new IllegalArgumentException("url == null");
        URI uri = URI.create(url);
        String query = uri.getQuery();
        if (query == null) return Collections.emptyMap();
        String[] params = query.split("&");
        Map<String, String> paramMap = new HashMap<>();
        int mid;
        for (String param : params) {
            mid = param.indexOf('=');
            if (mid != -1) paramMap.put(param.substring(0, mid), param.substring(mid + 1));
        }
        return paramMap;
    }

    public static <T> Observable<T> responseToBody(Response<T> response) {
        if (!response.isSuccessful()) {
            return Observable.error(new HttpException(response));
        }
        return Observable.just(response.body());
    }

    public static <T> Observable<Response<T>> checkResponse(Response<T> response) {
        if (!response.isSuccessful()) {
            return Observable.error(new HttpException(response));
        }
        return Observable.just(response);
    }
}
