package rxreddit.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import rxreddit.model.UserReport;

class UserReportDeserializer implements JsonDeserializer<UserReport> {

    @Override
    public UserReport deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray array = json.getAsJsonArray();

        // Extract text element
        JsonElement textElement = array.get(0);
        String text = textElement instanceof JsonNull ? null : textElement.getAsString();

        // Extract quantity element
        int quantity = array.get(1).getAsInt();

        return new UserReport(text, quantity);
    }
}
