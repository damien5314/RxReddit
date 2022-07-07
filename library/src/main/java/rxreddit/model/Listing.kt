package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public abstract class Listing {

    @SerializedName("kind")
    String kind;

    public abstract String getId();

    public String getFullName() {
        return getKind() + "_" + getId();
    }

    public String getKind() {
        return kind;
    }
}
