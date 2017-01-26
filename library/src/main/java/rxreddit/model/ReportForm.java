package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportForm {

    @SerializedName("json") Response json;

    static class Response {

        @SerializedName("errors") List<String> errors;
        @SerializedName("data") Data data;

        static class Data {

            @SerializedName("rules") List<SubredditRules.Rule> rules;
            @SerializedName("sr_name") String subredditName;
        }
    }

    public List<String> getErrors() {
        return json.errors;
    }

    public List<SubredditRules.Rule> getRules() {
        return json.data.rules;
    }

    public String getSubredditName() {
        return json.data.subredditName;
    }
}
