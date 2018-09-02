package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserIdentityListing extends Listing {

    @SerializedName("data")
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
        return String.format("%s - %s - Gold: %b", getId(), getFullName(), data.isGold());
    }
}
