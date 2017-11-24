package rxreddit.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Link;
import rxreddit.model.Listing;
import rxreddit.model.PrivateMessage;
import rxreddit.model.Subreddit;
import rxreddit.model.Trophy;

final class ListingDeserializer implements JsonDeserializer<Listing> {

    @Override
    public Listing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject o = json.getAsJsonObject();

        String kind = o.get("kind").getAsString();

        switch (kind) {
            case "t1":
                return context.deserialize(json, Comment.class);
            case "t3":
                Link link = context.deserialize(json, Link.class);
                if (link.isScoreHidden()) link.setScore(null);
                return link;
            case "t4":
                return context.deserialize(json, PrivateMessage.class);
            case "t5":
                return context.deserialize(json, Subreddit.class);
            case "t6":
                return context.deserialize(json, Trophy.class);
            case "more":
                return context.deserialize(json, CommentStub.class);
            default:
                return null;
        }
    }
}
