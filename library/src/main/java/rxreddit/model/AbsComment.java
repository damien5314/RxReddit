package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class AbsComment extends Listing {
  private int depth;
  private boolean isVisible = true;

  public abstract boolean isCollapsed();
  public abstract void setCollapsed(boolean b);
  public abstract String getParentId();

  public int getDepth() {
    return this.depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public boolean isVisible() {
    return this.isVisible;
  }

  public void setVisible(boolean b) {
    this.isVisible = b;
  }

  public static abstract class Data extends ListingData {
    @Expose @SerializedName("parent_id") String parentId;
  }
}
