package edu.ucsd.cse110.zooseeker46.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;
import edu.ucsd.cse110.zooseeker46.locations.languageConverterTags;

@Database(entities = {Exhibit.class, Gate.class, Intersection.class}, version = 1, exportSchema = false)
@TypeConverters({languageConverterTags.class})
public abstract class ZooDataDatabase extends RoomDatabase {
    public abstract ExhibitDao exhibitDao();
    public abstract GateDao gateDao();
    public abstract IntersectionDao intersectionDao();
}
