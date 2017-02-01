package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitPostResponse {

    @SerializedName("json")
    Json json;

    public List<String> getErrors() {
        return json.errors;
    }

    public String getUrl() {
        return json.data.url;
    }

    public String getId() {
        return json.data.id;
    }

    public String getFullname() {
        return json.data.fullname;
    }

    static class Json {

        @SerializedName("errors")
        List<String> errors;
        @SerializedName("data")
        Data data;

        static class Data {

            @SerializedName("url")
            String url;
            @SerializedName("id")
            String id;
            @SerializedName("name")
            String fullname;
        }
    }
}
