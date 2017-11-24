package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserReport {

    @SerializedName("text") public String text;
    @SerializedName("quantity") public int quantity;

    public UserReport(String text, int quantity) {
        this.text = text;
        this.quantity = quantity;
    }
}
