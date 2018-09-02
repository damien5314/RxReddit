package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListingResponse {

    @SerializedName("kind")
    String kind;
    @SerializedName("data")
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
