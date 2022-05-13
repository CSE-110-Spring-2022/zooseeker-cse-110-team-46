package edu.ucsd.cse110.zooseeker46;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.GateDao;
import edu.ucsd.cse110.zooseeker46.database.IntersectionDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.locations.Gate;
import edu.ucsd.cse110.zooseeker46.locations.Intersection;

@RunWith(AndroidJUnit4.class)
public class zooDatabaseTest {
    private ExhibitDao exhibitDao;
    private GateDao gateDao;
    private ZooDataDatabase db;
    private IntersectionDao intersectionDao;
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void CreateDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, ZooDataDatabase.class)
                .allowMainThreadQueries()
                .build();
        exhibitDao = db.exhibitDao();
        gateDao = db.gateDao();
        intersectionDao = db.intersectionDao();
    }
    @After
    public void CloseDb(){
        db.close();
    }

    @Test
    public void testExhibitInsert(){
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        String[] ex2Tags = {"gorilla", "monkey", "ape", "mammal"};
        List<String> ex2TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex2Tags);

        //Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        Exhibit ex2 = new Exhibit("gorillas","Gorillas", ex2TagsList);

        long id1 = exhibitDao.insert(new Exhibit("lions","Lions", ex1TagsList));
        long id2 = exhibitDao.insert(ex2);

        Exhibit item1 = exhibitDao.get(id1);
        Exhibit item2 = exhibitDao.get(id2);

        assertNotEquals(id1,id2);
        Exhibit item = exhibitDao.get(id1);
        System.out.println(item);

        // Nitya added these two tests to ensure long_id generated properly:
        assertEquals(id1, item1.long_id);
        assertEquals(id2, item2.long_id);
    }

    @Test
    public void testExhibitGet() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        long gen_id = exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get(gen_id);

        assertNotNull(gen_id);
        assertNotNull(item);
        assertEquals(gen_id, item.long_id);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.tags.getTags(), item.tags.getTags());
    }

    @Test
    public void testUpdate() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);

        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);

        long id = exhibitDao.insert(ex1);

        ex1 = exhibitDao.get(id);

        ex1.name = "Super Lions";

        int itemsUpdate = exhibitDao.update(ex1);

        assertEquals(1, itemsUpdate);

        ex1 = exhibitDao.get(id);
        assertNotNull(ex1);
        assertEquals("Super Lions", ex1.name);
    }

    @Test
    public void testExhibitGetAll(){
        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        List<Exhibit> actList = zoo.getExhibits();
        for (Exhibit ex : actList){
            exhibitDao.insert(ex);
        }
        List<Exhibit> exhibitList = exhibitDao.getAll();
        assertEquals(actList.size(),exhibitList.size());
    }

    @Test
    public void testGateInsert(){
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);
        Gate item1 = gateDao.get(id1);
        assertEquals(id1, item1.long_id);
    }

    @Test
    public void testGateGet() {
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);

        Gate item1 = gateDao.get(id1);

        assertNotNull(id1);
        assertNotNull(item1);
        assertEquals(id1, item1.long_id);
        assertEquals(gate.name, item1.name);
        assertEquals(gate.id, item1.id);
        assertEquals(gate.tags.getTags(), item1.tags.getTags());
    }

    /*@Test
    public void testGateUpdate() {
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);

        gate.setName("Super Main Entrance");

        int itemsUpdate = gateDao.update(gate);

        //assertEquals(1, itemsUpdate);

        Gate gate1 = gateDao.get(id1);
        assertNotNull(gate);
        assertEquals("Super Main Entrance", gate1.getName());
    }*/

    @Test
    public void testGateGetAll(){
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);
        List<Gate> gateList = gateDao.getAll();
        assertEquals(1, gateList.size());
    }

    @Test
    public void testIntersectionInsert(){
        //String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        //Collections.addAll(ex1TagsList,ex1Tags);
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza", ex1TagsList);
        long id1 = intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get(id1);
        assertEquals(id1, item1.long_id);
    }

    @Test
    public void testIntersectionGet(){
        List<String> ex1TagsList = new ArrayList<>();
        //Collections.addAll(ex1TagsList,ex1Tags);
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza", ex1TagsList);
        long id1 = intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get(id1);
        assertNotNull(item1);
        assertEquals(id1, item1.long_id);
    }

    @Test
    public void testIntersectionGetAll(){
        List<String> ex1TagsList = new ArrayList<>();
        //Collections.addAll(ex1TagsList,ex1Tags);
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza", ex1TagsList);
        long id1 = intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get(id1);
        List<Intersection> interList = intersectionDao.getAll();
        //assertNotNull(item1);
        assertEquals(1, interList.size());
    }
}
