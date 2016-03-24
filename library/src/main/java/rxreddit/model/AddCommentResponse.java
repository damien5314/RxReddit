package rxreddit.model;

import com.google.gson.annotations.Expose;

import java.util.List;

@SuppressWarnings("unused")
public class AddCommentResponse {
  @Expose
  private Json json;

  private static class Json {
    private List<String> errors;
    @Expose private Data data;
  }

  private static class Data {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Expose private List<Comment> things;
  }

  public Comment getComment() {
    return json.data.things.get(0);
  }
}
