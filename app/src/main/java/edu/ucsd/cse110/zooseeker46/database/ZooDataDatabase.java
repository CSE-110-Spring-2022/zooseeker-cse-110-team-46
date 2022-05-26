package edu.ucsd.cse110.zooseeker46.database;

import static edu.ucsd.cse110.zooseeker46.ZooData.VertexInfo.Kind;
import static edu.ucsd.cse110.zooseeker46.ZooData.VertexInfo.Kind.EXHIBIT;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import edu.ucsd.cse110.zooseeker46.ZooData;
import edu.ucsd.cse110.zooseeker46.ZooExhibits;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;
import edu.ucsd.cse110.zooseeker46.locations.languageConverterTags;


@Database(entities = {Exhibit.class, Gate.class, Intersection.class, Exhibit_Group.class}, version = 1, exportSchema = false)
@TypeConverters({languageConverterTags.class})
public abstract class ZooDataDatabase extends RoomDatabase {
    private static ZooDataDatabase singleton = null;

    static ZooData zooDataObj = new ZooData();

    public static void setShouldForceRepopulate() {
        ZooDataDatabase.shouldForceRepopulate = true;
    }

    private static boolean shouldForceRepopulate = false;

    public abstract ExhibitDao exhibitDao();

    public abstract GateDao gateDao();

    public abstract IntersectionDao intersectionDao();

    public abstract ExhibitGroupDao exhibitGroupDao();


    //creating/getting singleton
    public synchronized static ZooDataDatabase getSingleton(Context context) {
        if (singleton == null) {
            String msg = "Singleton is null!";
            Log.d("! + ", msg);
            singleton = ZooDataDatabase.makeDatabase(context);
        }
        return singleton;
    }

    //making db from "sample_node_info.json" and adding to db with the appropriate DAO depending on "kind"
    private static ZooDataDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, ZooDataDatabase.class, "ZooData.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            ZooDataDatabase zb = ZooDataDatabase.getSingleton(context);
                            Map<String, ZooData.VertexInfo> info = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
                            populateDatabase(zb, info);
                        });
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        if (shouldForceRepopulate) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                ZooDataDatabase zb = ZooDataDatabase.getSingleton(context);
                                zb.clearAllTables();
                                Map<String, ZooData.VertexInfo> info = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
                                populateDatabase(zb, info);
                            });
                        }
                    }
                })
                .build();
    }

    private static void populateDatabase(ZooDataDatabase zb, Map<String, ZooData.VertexInfo> info) {
        //Single thread for singleton
        Log.d("size of map --- : ", String.valueOf(info.size()));
        //checking format and calling constructor accordingly to add object
        for (Map.Entry<String, ZooData.VertexInfo> entry : info.entrySet()) {
            ZooData.VertexInfo curr = entry.getValue();
            //if (entry.getValue().kind.equals("exhibit")) {

            Kind kind = entry.getValue().kind;
            if (kind == Kind.EXHIBIT) {
                Exhibit exhibitObj = new Exhibit(curr.id, curr.name, curr.parent_id, curr.tags, 0, 0);
                //getSingleton(context).exhibitDao().insert(exhibitObj);
                zb.exhibitDao().insert(exhibitObj);
            } else if (kind == Kind.GATE) {
                Gate gateObj = new Gate(curr.id, curr.name, curr.tags, 0, 0);
                //getSingleton(context).gateDao().insert(gateObj);
                zb.gateDao().insert(gateObj);
            } else if (kind == Kind.INTERSECTION) {
                Intersection intersectionObj = new Intersection(curr.id, curr.name, curr.tags, 0, 0);
                //getSingleton(context).intersectionDao().insert(intersectionObj);
                zb.intersectionDao().insert(intersectionObj);
            } else if (kind == Kind.EXHIBIT_GROUP) {
                Exhibit_Group exhibit_groupObj = new Exhibit_Group(curr.id, curr.name, 0, 0);
                //getSingleton(context).exhibitGroupDao().insert(exhibit_groupObj);
                zb.exhibitGroupDao().insert(exhibit_groupObj);
            } else {
                // "unreachable" error.
                throw new RuntimeException("Unknown kind! What have you done!?");
            }
        }
    }

    @VisibleForTesting
    public static void injectTestDatabase(ZooDataDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}

