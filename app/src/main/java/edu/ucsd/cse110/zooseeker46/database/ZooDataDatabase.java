package edu.ucsd.cse110.zooseeker46.database;

import static edu.ucsd.cse110.zooseeker46.ZooData.VertexInfo.Kind;
import static edu.ucsd.cse110.zooseeker46.ZooData.VertexInfo.Kind.EXHIBIT;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
    public abstract ExhibitDao exhibitDao();
    public abstract GateDao gateDao();
    public abstract IntersectionDao intersectionDao();
    public abstract ExhibitGroupDao exhibitGroupDao();


    public synchronized static ZooDataDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = ZooDataDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static ZooDataDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, ZooDataDatabase.class, "ZooData.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        //Single thread for singleton
                        Executors.newSingleThreadExecutor().execute(() -> {
                            Map<String, ZooData.VertexInfo> info =
                                    zooDataObj.loadVertexInfoJSON(context,"sample_node_info.json");
                            //checking format and calling constructor accordingly to add object
                            for (Map.Entry<String,ZooData.VertexInfo> entry : info.entrySet()) {
                                ZooData.VertexInfo curr = entry.getValue();
                                //if (entry.getValue().kind.equals("exhibit")) {
                                if (entry.getValue().kind == EXHIBIT) {
                                    String pid = "";
                                    if(!(curr.parent_id.equals(""))){
                                        pid = curr.parent_id;
                                    }
                                    Exhibit exhibitObj = new Exhibit(curr.id, curr.name, pid, curr.tags,0 , 0);
                                    getSingleton(context).exhibitDao().insert(exhibitObj);
                                } else if (entry.getValue().kind.equals("gate")) {
                                    Gate gateObj = new Gate(curr.id, curr.name, curr.tags,0 , 0);
                                    getSingleton(context).gateDao().insert(gateObj);

                                } else if (entry.getValue().kind.equals("intersection")) {
                                    Intersection intersectionObj = new Intersection(curr.id, curr.name, curr.tags,0 , 0);
                                    getSingleton(context).intersectionDao().insert(intersectionObj);

                                } else {
                                    Exhibit_Group exhibit_groupObj = new Exhibit_Group(curr.id, curr.name, 0 ,0);
                                }
                            }
                        });
                    }
                })
                .build();
    }

    @VisibleForTesting
    public static void injectTestDatabase(ZooDataDatabase testDatabase){
        if(singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}

