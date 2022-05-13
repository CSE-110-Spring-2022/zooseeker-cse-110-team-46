package edu.ucsd.cse110.zooseeker46.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.ucsd.cse110.zooseeker46.selectedExhibit;

@Database(entities = {selectedExhibit.class}, version = 1)
public abstract class SelectedExhibitDatabase extends RoomDatabase{
    private static SelectedExhibitDatabase singleton = null;

    public abstract ExhibitDao exhibitDao();

    public synchronized static SelectedExhibitDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = SelectedExhibitDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static SelectedExhibitDatabase makeDatabase(Context context) {
        //TO-DO
        return null;
    }
}
