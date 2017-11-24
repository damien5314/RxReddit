package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class ListingResponseData {

    @SerializedName("children") List<Listing> children;
    @SerializedName("after") String after;
    @SerializedName("before") String before;
    @SerializedName("modhash") String modhash;

    public ListingResponseData() {
    }

    public ListingResponseData(List<Listing> messageList) {
        children = messageList;
    }

    public String getModhash() {
        return modhash;
    }

    public List<Listing> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }
}
