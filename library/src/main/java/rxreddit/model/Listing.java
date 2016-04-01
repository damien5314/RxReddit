package rxreddit.model;

public abstract class Listing {
  protected String kind;

  public abstract String getId();

  public String getFullName() {
    return getKind() + "_" + getId();
  }

  public String getKind() {
    return kind;
  }
}
