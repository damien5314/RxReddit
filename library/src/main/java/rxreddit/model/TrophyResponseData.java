package rxreddit.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class TrophyResponseData {
  @Expose
  private List<Listing> trophies;

  public List<Listing> getTrophies() {
    return trophies;
  }

}