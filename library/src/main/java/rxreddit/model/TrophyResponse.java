package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class TrophyResponse {

    // FIXME Shouldn't this be a Listing?
    @SerializedName("kind") String kind;
    @SerializedName("data") TrophyResponseData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public TrophyResponseData getData() {
        return data;
    }

    public void setData(TrophyResponseData data) {
        this.data = data;
    }
}
