package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class FriendInfo {

    @SerializedName("date")
    Long date;
    @SerializedName("note")
    String note;
    @SerializedName("name")
    String name;
    @SerializedName("id")
    String id;

    public Long getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
