package com.example.tva_projekt.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Helper {
    public static <T> T parseJson(String json, Class<T> type) {
        if (json.equals("{}")) return null;
        else {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, type);
        }
    }
}
