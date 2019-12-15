package com.aurick.mongodbmobile.utils;

import com.google.gson.Gson;

public class GsonUtils {

    private static Gson gson = new Gson();

    private GsonUtils() {
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

}
