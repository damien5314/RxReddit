package rxreddit;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.HttpException;
import retrofit2.Response;
import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;

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
     * Flattens list of comments, marking each comment with depth
     */
    public static void flattenCommentList(List<Listing> commentList) {
        int i = 0;
        while (i < commentList.size()) {
            Listing listing = commentList.get(i);
            if (listing instanceof Comment) {
                Comment comment = (Comment) listing;
                ListingResponse repliesListing = comment.getReplies();
                if (repliesListing != null) {
                    List<Listing> replies = repliesListing.getData().getChildren();
                    flattenCommentList(replies);
                }
                comment.setDepth(comment.getDepth() + 1); // Increase depth by 1
                if (comment.getReplies() != null) {
                    // Add all of the replies to commentList
                    commentList.addAll(i + 1, comment.getReplies().getData().getChildren());
                    comment.setReplies(null); // Remove replies for comment
                }
            } else { // Listing is a CommentStub
                CommentStub moreComments = (CommentStub) listing;
                moreComments.setDepth(moreComments.getDepth() + 1); // Increase depth by 1
            }
            i++;
        }
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

    public static <T> Function<Response<T>, Observable<T>> responseToBody() {
        return response -> {
            if (!response.isSuccessful()) {
                return Observable.error(new HttpException(response));
            }
            return Observable.just(response.body());
        };
    }

    public static <T> Function<Response<T>, Observable<Response<T>>> checkResponse() {
        return response -> {
            if (!response.isSuccessful()) {
                return Observable.error(new HttpException(response));
            }
            return Observable.just(response);
        };
    }
}
