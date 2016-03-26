package rxreddit;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rxreddit.model.AbsComment;
import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;

public class RxRedditUtil {

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
   * Sets depth for comments in a flat comments list
   */
  public static void setDepthForCommentsList(List<Listing> comments, int parentDepth) {
    HashMap<String, Integer> depthMap = new HashMap<>();

    for (Listing listing : comments) {
      AbsComment comment = (AbsComment) listing;
      String name = comment.getFullName();
      String parentId = comment.getParentId();
      if (depthMap.containsKey(parentId)) {
        comment.setDepth(depthMap.get(parentId) + 1);
      } else {
        comment.setDepth(parentDepth);
      }
      depthMap.put(name, comment.getDepth());
    }
  }

  /**
   * Returns a string containing the tokens joined by delimiters.
   * @param tokens an array objects to be joined. Strings will be formed from
   *     the objects by calling object.toString().
   */
  public static String join(CharSequence delimiter, Object[] tokens) {
    StringBuilder sb = new StringBuilder();
    boolean firstTime = true;
    for (Object token: tokens) {
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
   * @param tokens an array objects to be joined. Strings will be formed from
   *     the objects by calling object.toString().
   */
  public static String join(CharSequence delimiter, Iterable tokens) {
    StringBuilder sb = new StringBuilder();
    boolean firstTime = true;
    for (Object token: tokens) {
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

  public static String getRandomString() {
    return UUID.randomUUID().toString();
  }

  public static Map<String, String> getQueryParametersFromUrl(String url) {
    URI uri = URI.create(url);
    String query = uri.getQuery();
    String[] params = query.split("&");
    Map<String, String> paramMap = new HashMap<>();
    int mid;
    for (String param : params) {
      mid = param.indexOf('=');
      paramMap.put(param.substring(0, mid), param.substring(mid+1));
    }
    return paramMap;
  }

  public static String getUserAgent(
      String platform, String pkgName, String versionName, String username) {
    return String.format("%s:%s:%s (by /u/%s)", platform, pkgName, versionName, username);
  }
}
