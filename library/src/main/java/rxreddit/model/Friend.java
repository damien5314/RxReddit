package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {
  @Expose @SerializedName("note")
  String mNote;

  public Friend(String note) {
    mNote = note;
  }
}
