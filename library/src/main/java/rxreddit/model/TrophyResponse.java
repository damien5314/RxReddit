package rxreddit.model;

public class TrophyResponse {

  private String kind;
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
