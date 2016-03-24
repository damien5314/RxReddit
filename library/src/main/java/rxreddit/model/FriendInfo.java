package rxreddit.model;

import com.google.gson.annotations.Expose;

public class FriendInfo {
  @Expose
  Long date;
  @Expose
  String note;
  @Expose
  String name;
  @Expose
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
