package rxreddit.model;

public class UserIdentityListing extends Listing {
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
