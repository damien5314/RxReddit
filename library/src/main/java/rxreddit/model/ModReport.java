package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class ModReport {

    @SerializedName("text")
    public String text;
    @SerializedName("username")
    public String username;

    public ModReport(String text, String username) {
        this.text = text;
        this.username = username;
    }
}
