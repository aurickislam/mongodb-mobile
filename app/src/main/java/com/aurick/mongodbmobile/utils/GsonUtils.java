package com.aurick.mongodbmobile.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class GsonUtils {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:sssZ")
                .registerTypeAdapter(ObjectId.class, new JsonSerializer<ObjectId>() {
                    @Override
                    public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
//                        return new JsonPrimitive(src.toHexString());
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("$oid", src.toHexString());
                        return jsonObject;
                    }
                })
                .registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {
                    @Override
                    public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                        return new ObjectId(json.getAsString());
                        return new ObjectId(json.getAsJsonObject().get("$oid").getAsString());
                    }
                })
                .create();
    }

    private GsonUtils() {
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

}
