package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserReport {

    @SerializedName("text") String text;
    @SerializedName("quantity") int quantity;

    public UserReport(String text, int quantity) {
        this.text = text;
        this.quantity = quantity;
    }

    public String getText() {
        return text;
    }

    public int getQuantity() {
        return quantity;
    }
}
