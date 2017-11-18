package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class Trophy extends Listing {

    @SerializedName("data") Data data;

    @Override
    public String getId() {
        return data.id;
    }

    public String getName() {
        return data.name;
    }

    public String getIcon70() {
        return data.icon70;
    }

    public String getIcon40() {
        return data.icon40;
    }

    public String getDescription() {
        return data.description;
    }

    public String getUrl() {
        return data.url;
    }

    public String getAwardId() {
        return data.awardId;
    }

    public static class Data extends ListingData {

        @SerializedName("icon_70") String icon70;
        @SerializedName("icon_40") String icon40;
        @SerializedName("description") String description;
        @SerializedName("url") String url;
        @SerializedName("name") String name;
        @SerializedName("award_id") String awardId;
    }
}
