package rxreddit.model;

public class Listing {

  protected String kind;

  public String getId() {
    return null;
  }

  public String getFullName() {
    return getKind() + "_" + getId();
  }

  public String getKind() {
    return kind;
  }
}
