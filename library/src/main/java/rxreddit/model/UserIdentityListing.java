package rxreddit.model;

import com.google.gson.annotations.Expose;

public class UserIdentityListing extends Listing {
  @Expose
  UserIdentity data;

  public UserIdentity getUser() {
    return data;
  }

  @Override
  public String getId() {
    return data.getId();
  }

  @Override
  public String toString() {
    return getId() + " - " + getFullName() + " - Gold: " + data.isGold();
  }
}
