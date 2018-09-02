package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("note")
    String note;

    public Friend(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
