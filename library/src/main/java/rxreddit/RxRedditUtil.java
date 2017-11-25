package rxreddit;

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

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
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

    public static String getCommaDelimitedString(Iterable<String> string) {
        StringBuilder commaDelimited = new StringBuilder();
        for (String subreddit : string) {
            commaDelimited.append(subreddit).append(",");
        }
        commaDelimited.deleteCharAt(commaDelimited.length()-1);
        return commaDelimited.toString();
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
