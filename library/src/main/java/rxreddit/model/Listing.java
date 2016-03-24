package rxreddit.model;

import com.google.gson.annotations.Expose;

public abstract class Listing {
  @Expose
  protected String kind;

  public abstract String getId();

  public String getFullName() {
    return getKind() + "_" + getId();
  }

  public String getKind() {
    return kind;
  }
}
