package com.aurick.mongodbmobile.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class JacksonUtils {

    private static final String TAG = "JacksonUtils";

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ObjectWriter prettyMapper;

    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(sdf);

        SimpleModule module = new SimpleModule("ObjectId", new Version(1, 0, 0, null, null, null));

        module.addDeserializer(ObjectId.class, new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//                Log.e("TAG", "deserialize: ");

                JsonNode oid = ((JsonNode) p.readValueAsTree()).get("$oid");
                return new ObjectId(oid.asText());
            }
        });

        module.addSerializer(ObjectId.class, new JsonSerializer<ObjectId>() {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                Log.e(TAG, "serialize: " + value.toHexString());

                /*Document document = new Document() {
                    {
                        put("$oid", value.toString());
                    }
                };
                gen.writeString(document.toJson());*/

                gen.writeStartObject();
                gen.writeStringField("$oid", value.toHexString());
                gen.writeEndObject();
            }
        });

        mapper.registerModule(module);

        prettyMapper = mapper.writerWithDefaultPrettyPrinter();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toPrettyJson(Object value) {
        try {
            return prettyMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
