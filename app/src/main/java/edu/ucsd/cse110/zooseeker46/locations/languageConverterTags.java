package edu.ucsd.cse110.zooseeker46.locations;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
/*
    Allows Room to store list of tags
    Source: https://stackoverflow.com/questions/44582397/
    android-room-persistent-library-typeconverter-error-of-error-cannot-figure-ou
    Title: stackoverflow Android room persistent library - TypeConverter error of error:
    Cannot figure out how to save field to database"
    Date: 05/08/22
    Use: created similar language converter
 */
public class languageConverterTags {
    @TypeConverter
    public tags storedStringToLanguages(String value) {
        List<String> tag = Arrays.asList(value.split("\\s*,\\s*"));
        return new tags(tag);
    }

    @TypeConverter
    public String languagesToStoredString(tags t) {
        String value = "";

        for (String tag :t.getTags())
            value += tag + ",";

        return value;
    }
}
