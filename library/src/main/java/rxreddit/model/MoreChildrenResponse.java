package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreChildrenResponse {

    @SerializedName("json") MoreChildrenResponseJson json;

    static class MoreChildrenResponseJson {

        @SerializedName("errors") List<String> errors;
        @SerializedName("data") MoreChildrenResponseData data;

        static class MoreChildrenResponseData {

            @SerializedName("things") List<Listing> listings;
        }
    }

    public List<Listing> getChildComments() {
        if (json.data == null) return null;
        return json.data.listings;
    }
}
