package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubredditRules {

    @SerializedName("rules")
    List<Rule> rules;

    @SerializedName("site_rules")
    List<String> siteRules;

    public List<Rule> getRules() {
        return rules;
    }

    public List<String> getSiteRules() {
        return siteRules;
    }

    static class Rule {

        @SerializedName("kind")
        String kind;

        @SerializedName("description")
        String description;

        @SerializedName("short_name")
        String shortName;

        @SerializedName("created_utc")
        Long createdUtc;

        @SerializedName("priority")
        Integer priority;

        @SerializedName("description_html")
        String descriptionHtml;

        public String getKind() {
            return kind;
        }

        public String getDescription() {
            return description;
        }

        public String getShortName() {
            return shortName;
        }

        public Long getCreatedUtc() {
            return createdUtc;
        }

        public Integer getPriority() {
            return priority;
        }

        public String getDescriptionHtml() {
            return descriptionHtml;
        }
    }
}
