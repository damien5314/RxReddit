package rxreddit.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Listing;

final class CommentDeserializer implements JsonDeserializer<Listing> {

    @Override
    public Listing deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject o = json.getAsJsonObject();
        String kind = o.get("kind").getAsString();
        Listing listing;
        switch (kind) {
            case "t1":
                listing = context.deserialize(json, Comment.class);
                return listing;
            case "more":
                listing = context.deserialize(json, CommentStub.class);
                return listing;
            default:
                return null;
        }
    }
}
