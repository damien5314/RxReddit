package rxreddit.model;

import java.util.List;

public class ListingResponse {

    String kind;
    ListingResponseData data;

    public ListingResponse() {
    }

    public ListingResponse(List<Listing> messageList) {
        data = new ListingResponseData(messageList);
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ListingResponseData getData() {
        return data;
    }

    public void setData(ListingResponseData data) {
        this.data = data;
    }
}
