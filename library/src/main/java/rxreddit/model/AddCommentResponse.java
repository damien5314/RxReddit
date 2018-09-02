package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AddCommentResponse {

    @SerializedName("json")
    Json json;

    public List<String> getErrors() {
        if (json == null || json.errors == null) return new ArrayList<>();
        return json.errors;
    }

    public Comment getComment() {
        final List<Comment> comments = json.data.getComments();
        if (json == null || json.data == null || comments == null || comments.size() == 0) {
            return null;
        } else {
            return comments.get(0);
        }
    }

    static class Json {

        @SerializedName("data")
        Data data;
        @SerializedName("errors")
        List<String> errors;

        public Data getData() {
            return data;
        }

        public List<String> getErrors() {
            return errors;
        }
    }

    static class Data {

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        @SerializedName("things")
        List<Comment> comments;

        public List<Comment> getComments() {
            return comments;
        }
    }
}
