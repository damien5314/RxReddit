package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubredditRules {

    @SerializedName("rules")
    List<SubredditRule> rules;

    @SerializedName("site_rules")
    List<String> siteRules;

    public List<SubredditRule> getRules() {
        return rules;
    }

    public List<String> getSiteRules() {
        return siteRules;
    }

}
