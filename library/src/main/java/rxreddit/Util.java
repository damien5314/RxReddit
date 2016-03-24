package rxreddit;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import rxreddit.model.AbsComment;
import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;

public class Util {

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
          commentList.addAll(i + 1, comment.getReplies().getData().getChildren()); // Add all of the replies to commentList
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

  public static String getUserAuthCodeFromRedirectUri(String url) {
    URI uri = URI.create(url);
    String query = uri.getQuery();
    String[] params = query.split("&");

    // Verify state parameter is correct
    String returnedState = getValueFromQuery(params[0]);
    // TODO Handle incorrect state
//    if (!returnedState.equals(RedditAuthService.STATE)) {
//      Timber.e("STATE does not match: %s (EXPECTED: %s)",
//          returnedState, RedditAuthService.STATE);
//      return null;
//    }

    // If successfully authorized, params[1] will be a grant code
    // Otherwise, params[1] is an error message
    String name = getNameFromQuery(params[1]);
    if (name.equals("code")) {
      return getValueFromQuery(params[1]);
    } else { // User declined to authorize application, or an error occurred
      String error = getValueFromQuery(params[1]);
      // TODO Log this error somewhere
      return null;
    }
  }

  private static String getNameFromQuery(String query) {
    return query.substring(0, query.indexOf("="));
  }

  private static String getValueFromQuery(String query) {
    return query.substring(query.indexOf("=") + 1);
  }
}
