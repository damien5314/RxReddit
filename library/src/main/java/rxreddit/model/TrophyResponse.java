package rxreddit.model;

import com.google.gson.annotations.Expose;

public class TrophyResponse {
  @Expose
  private String kind;
  @Expose
  private TrophyResponseData data;

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public TrophyResponseData getData() {
    return data;
  }

  public void setData(TrophyResponseData data) {
    this.data = data;
  }
}
