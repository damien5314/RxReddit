package rxreddit.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MoreChildrenResponse {
  private MoreChildrenResponseJson json;

  private static class MoreChildrenResponseJson {
    private List<String> errors;
    private MoreChildrenResponseData data;

    private static class MoreChildrenResponseData {
      private List<Listing> things;
    }
  }

  public List<Listing> getChildComments() {
    if (json.data == null) return null;
    return json.data.things;
  }
}
