package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrophyResponseData {

    @SerializedName("trophies") List<Listing> trophies;

    public List<Listing> getTrophies() {
        return trophies;
    }
}
