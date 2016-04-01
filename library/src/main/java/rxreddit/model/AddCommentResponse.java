package rxreddit.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AddCommentResponse {
  private Json json;

  public List<String> getErrors() {
    if (json == null || json.errors == null) return new ArrayList<>();
    return json.errors;
  }

  public Comment getComment() {
    if (json == null
        || json.data == null
        || json.data.things == null
        || json.data.things.size() == 0)
      return null;
    return json.data.things.get(0);
  }

  private static class Json {
    private List<String> errors;
    private Data data;
  }

  private static class Data {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Comment> things;
  }
}
