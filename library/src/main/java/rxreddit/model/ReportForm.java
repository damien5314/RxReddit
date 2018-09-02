package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportForm {

    @SerializedName("json")
    Response json;

    public List<String> getErrors() {
        return json.errors;
    }

    public List<SubredditRule> getRules() {
        return json.data.rules;
    }

    public String getSubredditName() {
        return json.data.subredditName;
    }

    static class Response {

        @SerializedName("errors")
        List<String> errors;
        @SerializedName("data")
        Data data;

        static class Data {

            @SerializedName("rules")
            List<SubredditRule> rules;
            @SerializedName("sr_name")
            String subredditName;
        }
    }
}
