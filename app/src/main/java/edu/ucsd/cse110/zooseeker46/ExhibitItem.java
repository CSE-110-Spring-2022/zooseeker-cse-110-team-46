package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExhibitItem {
    public String id;
    public String itemType;
    public String[] tags;

    ExhibitItem(String id, String itemType, String[] tags){
        this.id = id;
        this.itemType = itemType;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ExhibitItem{" +
                "id='" + id + '\'' +
                ", itemType='" + itemType + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static List<ExhibitItem> loadJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ExhibitItem>>(){}.getType();
            return gson.fromJson(reader, type);

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
}
