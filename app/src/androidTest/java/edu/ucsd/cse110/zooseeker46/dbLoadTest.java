package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ExhibitGroupDao;
import edu.ucsd.cse110.zooseeker46.database.GateDao;
import edu.ucsd.cse110.zooseeker46.database.IntersectionDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;

@RunWith(AndroidJUnit4.class)
public class dbLoadTest {
    ZooDataDatabase testDb;
    public ExhibitDao exhibitDao;
    public GateDao gateDao;
    public IntersectionDao intersectionDao;
    public ExhibitGroupDao exhibitGroupDao;
    static ZooData zooDataObj = new ZooData();


    @Before
    public void resetDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, ZooDataDatabase.class)
                .allowMainThreadQueries()
                .build();
        ZooDataDatabase.injectTestDatabase(testDb);
        ExhibitDao exhibitDao_new = testDb.exhibitDao();

        Map<String, ZooData.VertexInfo> info =
                zooDataObj.loadVertexInfoJSON(context,"sample_node_info.json");
        //checking format and calling constructor accordingly to add object
        for (Map.Entry<String,ZooData.VertexInfo> entry : info.entrySet()) {
            ZooData.VertexInfo curr = entry.getValue();
            if (entry.getValue().kind.equals("exhibit")) {
                String pid = "";
                if(!(curr.parent_id.equals(""))){
                    pid = curr.parent_id;

                }
                Exhibit exhibitObj = new Exhibit(curr.id, curr.name, pid, curr.tags,0 , 0);
                exhibitDao_new.insert(exhibitObj);
            }
        }
        exhibitDao = exhibitDao_new;
    }
    @Test
    public void testLoad() {

    }
}
