package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {
  @SerializedName("note")
  String mNote;

  public Friend(String note) {
    mNote = note;
  }
}
