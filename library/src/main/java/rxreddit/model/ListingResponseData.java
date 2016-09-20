package rxreddit.model;

import java.util.List;

@SuppressWarnings("unused")
public class ListingResponseData {

    List<Listing> children;
    String after;
    String before;
    String modhash;

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
