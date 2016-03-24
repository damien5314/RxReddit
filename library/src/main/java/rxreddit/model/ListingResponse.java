package rxreddit.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ListingResponse {
  @Expose
  String kind;
  @Expose
  ListingResponseData data;

  public ListingResponse() { }

  public ListingResponse(List<Listing> messageList) {
    data = new ListingResponseData(messageList);
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public ListingResponseData getData() {
    return data;
  }

  public void setData(ListingResponseData data) {
    this.data = data;
  }
}
