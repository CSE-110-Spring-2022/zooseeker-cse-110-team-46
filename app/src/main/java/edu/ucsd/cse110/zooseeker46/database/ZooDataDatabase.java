package edu.ucsd.cse110.zooseeker46.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;

import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;
import edu.ucsd.cse110.zooseeker46.locations.languageConverterTags;

@Database(entities = {Exhibit.class, Gate.class, Intersection.class, Exhibit_Group.class}, version = 1, exportSchema = false)
@TypeConverters({languageConverterTags.class})
public abstract class ZooDataDatabase extends RoomDatabase {
//    private static ZooDataDatabase singleton = null;

    public abstract ExhibitDao exhibitDao();
    public abstract GateDao gateDao();
    public abstract IntersectionDao intersectionDao();

//    public synchronized static ZooDataDatabase getSingleton(Context context) {
//        if (singleton == null) {
//            singleton = ZooDataDatabase.makeDatabase(context);
//        }
//        return singleton;
//    }
//
//    private static ZooDataDatabase makeDatabase(Context context) {
//        return Room.databaseBuilder(context, ZooDataDatabase.class, "ZooData.db")
//                .allowMainThreadQueries()
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Executors.newSingleThreadExecutor().execute(() -> {
//                            //not sure about this part, kinda forgot what each db has inside
//                            //ask about dis tmr smh
//                            List<Exhibit> exhibits = ZooExhibits
//                                    .loadJSON(context, "sample_node_info.json");
//                            //add a for loop here or insertAll method in DAO
//                            getSingleton(context).exhibitDao().insert(exhibits);
//                        });
//                    }
//                })
//                .build();
//    }

    public abstract ExhibitGroupDao exhibitGroupDao();
}

