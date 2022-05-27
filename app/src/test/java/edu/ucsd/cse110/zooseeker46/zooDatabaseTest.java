package edu.ucsd.cse110.zooseeker46;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import edu.ucsd.cse110.zooseeker46.locations.tags;

@RunWith(AndroidJUnit4.class)
public class zooDatabaseTest {
    private ExhibitDao exhibitDao;
    private GateDao gateDao;
    private ZooDataDatabase db;
    private IntersectionDao intersectionDao;
    private ExhibitGroupDao exhibitGroupDao;

    private ExhibitDao exhibitDao2;
    private GateDao gateDao2;
    private ZooDataDatabase db2;
    private IntersectionDao intersectionDao2;
    private ExhibitGroupDao exhibitGroupDao2;
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Before
    public void CreateDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db2 = ZooDataDatabase.getSingleton(context);
        exhibitDao2 = db2.exhibitDao();
        gateDao2 = db2.gateDao();
        intersectionDao2 = db2.intersectionDao();
        exhibitGroupDao2 = db2.exhibitGroupDao();

        db = Room.inMemoryDatabaseBuilder(context, ZooDataDatabase.class)
                .allowMainThreadQueries()
                .build();
        exhibitDao = db.exhibitDao();
        intersectionDao = db.intersectionDao();
        gateDao = db.gateDao();
        exhibitGroupDao = db.exhibitGroupDao();
    }

    @After
    public void CloseDb(){
        db.close();
    }

//    @Test
//    public void testDatabaseSimple(){
//        List<Exhibit> allexhibits = exhibitDao2.getAll();
//        int size = allexhibits.size();
//        Log.d("size of exhibit list: ", String.valueOf(size));
//        assertNotEquals(0, size);
//    }

    @Test
    public void testExhibitOne(){
        String[] koi_tags = {"koi", "fish", "japan"};
        List<String> koi_tags_list = new ArrayList<>();
        Collections.addAll(koi_tags_list,koi_tags);
        Exhibit koi = new Exhibit("koi", "Koi Fish", koi_tags_list);

        exhibitDao.insert(koi);
        Exhibit returnedexhibit = exhibitDao.get("koi");

        assertEquals("koi", returnedexhibit.getId());
        assertEquals("Koi Fish", returnedexhibit.getName());
        assertEquals(koi_tags_list, returnedexhibit.getTags());
        assertNull(returnedexhibit.getParent_id());
        assertFalse(returnedexhibit.getIsSelected());
        assertNull(returnedexhibit.getLongitude());
        assertNull(returnedexhibit.getLatitude());
    }

    @Test
    public void testExhibitInsert(){
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);

        String[] ex2Tags = {"gorilla", "monkey", "ape", "mammal"};
        List<String> ex2TagsList = new ArrayList<>();
        Collections.addAll(ex2TagsList,ex2Tags);

        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        Exhibit ex2 = new Exhibit("gorillas","Gorillas", ex2TagsList);

        //exhibitDao.insert(new Exhibit("lions","Lions", ex1TagsList));
        exhibitDao.insert(ex1);
        exhibitDao.insert(ex2);

        Exhibit item2 = exhibitDao.get("gorillas");
        Exhibit item1 = exhibitDao.get("lions");

        assertNotEquals(item1.getName(), item2.getName());
        assertEquals(ex1TagsList, item1.getTags());
        assertEquals(ex2TagsList, item2.getTags());
    }

    @Test
    public void testExhibitGet() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);

        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
         exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get("lions");

        assertNotNull(item);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.tags.getTags(), item.tags.getTags());
    }

    @Test
    public void testExhibitGetWithValidParent() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);

        String parent_id = "Avery_pkw";

        Exhibit ex1 = new Exhibit("lions","Lions", parent_id, ex1TagsList);
        exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get("lions");

        assertNotNull(item);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.getTags(), item.getTags());
        assertEquals(ex1.getParent_id(), item.getParent_id());
        assertNull(item.getLongitude());
        assertNull(item.getLatitude());
    }

    @Test
    public void testExhibitGroupGet() {
        List<String> ex1TagsList = new ArrayList<>();
        String name = "Scripps Aviary";
        String id = "scripps_aviary";
        double lat = 32.748538318135594;
        double lng = -117.17255093386991;
        Exhibit_Group ex1 = new Exhibit_Group(id,name, lat, lng);
        exhibitGroupDao.insert(ex1);

        Exhibit_Group item = exhibitGroupDao.get(id);

        assertNotNull(item);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.getLatitude(), item.getLatitude(), 0.001);
        assertEquals(ex1.getLongitude(), item.getLongitude(), 0.001);
    }

    @Test
    public void testExhibitGetWithNoParent() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        double lng = -117.17255093386991;
        double lat = 32.748538318135594;
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList, lat, lng);
        long gen_id = exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get("lions");

        assertNotNull(item);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.tags.getTags(), item.tags.getTags());
        assertEquals(ex1.getParent_id(), item.getParent_id());
        assertEquals(ex1.getLatitude(), item.getLatitude(), 0.001);
        assertEquals(ex1.getLongitude(), item.getLongitude(), 0.001);
    }

    @Test
    public void testUpdate() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);

        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);

        exhibitDao.insert(ex1);

        ex1 = exhibitDao.get("lions");

        ex1.name = "Super Lions";

        int itemsUpdate = exhibitDao.update(ex1);

        assertEquals(1, itemsUpdate);

        //ex1 = exhibitDao.get(id);
        Exhibit e = exhibitDao.get("lions");
        assertNotNull(e);
        assertEquals("Super Lions", e.name);
        Exhibit e2 = exhibitDao.get("Super Lions");
        assertNull(e2);
    }

    @Test
    public void testExhibitGetAll(){
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        exhibitDao.insert(ex1);
        ex1 = exhibitDao.get("lions");
        ex1.isSelected = true;
        int smt = exhibitDao.update(ex1);
        String[] ex2Tags = {"gorilla", "monkey", "ape", "mammal"};
        List<String> ex2TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex2Tags);
        Exhibit ex2 = new Exhibit("gorillas","Gorillas", ex2TagsList);
        long id2 = exhibitDao.insert(ex2);
        Exhibit return2 = exhibitDao.get("lions");
        Exhibit return1 = exhibitDao.get("gorillas");
        List<Exhibit> exhibitList = exhibitDao.getAll();
        assertEquals(false, return1.getIsSelected());
        assertEquals(true, return2.getIsSelected());
        //assertEquals(1, exhibitList);
    }

    @Test
    public void testGateInsert(){
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);
        Gate item1 = gateDao.get("entrance_exit_gate");
        assertEquals(gate.getName(), item1.getName());
    }

    @Test
    public void testGateGet() {
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);

        Gate item1 = gateDao.get("entrance_exit_gate");

        assertNotNull(item1);
        assertEquals(gate.getName(), item1.getName());
        assertEquals(gate.name, item1.name);
        assertEquals(gate.id, item1.id);
        assertEquals(gate.tags.getTags(), item1.tags.getTags());
    }

    @Test
    public void testGateUpdate() {
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);
        gate = gateDao.get("entrance_exit_gate");
        gate.setName("Super Main Entrance");

        int itemsUpdate = gateDao.update(gate);

        assertEquals(1, itemsUpdate);

        Gate gate1 = gateDao.get("entrance_exit_gate");
        assertNotNull(gate);
        assertEquals("Super Main Entrance", gate1.getName());
    }

    @Test
    public void testGateGetAll(){
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        gateDao.insert(gate);
        List<Gate> gateList = gateDao.getAll();
        assertEquals(1, gateList.size());
    }

    @Test
    public void testIntersectionInsert(){
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza");
        intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get("entrance_plaza");
        assertEquals(inter.getName(), item1.getName());
    }

    @Test
    public void testIntersectionGetAll(){
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza");
        intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get("entrance_plaza");
        List<Intersection> interList = intersectionDao.getAll();
        assertEquals(1, interList.size());
    }

    @Test
    public void testIntersectionUpdate(){
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza");
        intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get("entrance_plaza");
        item1.name = "Enter here!";
        int smt = intersectionDao.update(item1);
        item1 = intersectionDao.get("entrance_plaza");
        assertNotNull(item1);
        assertEquals("Enter here!", item1.name);
    }

    @Test
    public void testExhibitSelectOne() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        exhibitDao.insert(ex1);
        ex1 = exhibitDao.get("lions");

        assertEquals(0, exhibitDao.getSelectedExhibits().size());

        ex1.setSelected(true);
        int itemsUpdate = exhibitDao.update(ex1);
        assertEquals(1, itemsUpdate);
        assertTrue(exhibitDao.get("lions").getIsSelected());
        assertEquals(1, exhibitDao.getSelectedExhibits().size());
        assertEquals(ex1.getName(), exhibitDao.getSelectedExhibits().get(0).getName());
    }

//    @Test
//    public void testExhibitSelectMany() {
//        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
//        ZooExhibits zoo = new ZooExhibits(map);
//        List<Exhibit> actList = zoo.getExhibits();
//       // List<Long> ids = new ArrayList<>();
//        List<String> ids = new ArrayList<>();
//        for (Exhibit ex : actList){
//            exhibitDao.insert(ex);
//            ids.add(ex.getName());
//        }
//
//        // Check if all exhibits inserted correctly:
//        for(int i = 0; i < actList.size(); i++){
//            assertEquals(actList.get(i).getName(), exhibitDao.get(ids.get(i)).getName());
//        }
//
//        // Select every few exhibits
//        List<Exhibit> ExpectedSelectedExhibit = new ArrayList<>();
//        for(int i = 0; i < actList.size(); i += 2){
//            Exhibit curr = exhibitDao.get(ids.get(i));
//            curr.setSelected(true);
//            ExpectedSelectedExhibit.add(curr);
//            int itemsUpdate = exhibitDao.update(curr);
//
//            assertEquals(1, itemsUpdate);
//        }
//
//        List<Exhibit> daoSelectedExhibit = exhibitDao.getSelectedExhibits();
//        assertEquals(ExpectedSelectedExhibit.size(), daoSelectedExhibit.size());
//
//        for(int i = 0; i < ExpectedSelectedExhibit.size(); i++){
//            assertEquals(ExpectedSelectedExhibit.get(i).getName(), daoSelectedExhibit.get(i).getName());
//            assertEquals(ExpectedSelectedExhibit.get(i).getTags(), daoSelectedExhibit.get(i).getTags());
//        }
//
//        // Deselect a few of the selected exhibits
//        Map<String, String> m = new HashMap<String, String>();
//        for(int i = 0; i < actList.size(); i++){
//            m.put(actList.get(i).getName(), ids.get(i));
//        }
//        List<String> selectedIDS = new ArrayList<>();
//        for(Exhibit curr: ExpectedSelectedExhibit){
//            selectedIDS.add(m.get(curr.getName()));
//            System.out.println("before: " + curr.getName());
//        }
//
//        Map<String, Exhibit> previouslySelectedm = new HashMap<>();
//        for(Exhibit curr: ExpectedSelectedExhibit){
//            previouslySelectedm.put(curr.getName(), curr);
//        }
//
//        // Deselect a few of the selected items
//        for(int i = 1; i < selectedIDS.size(); i++){
//            Exhibit curr = exhibitDao.get(selectedIDS.get(i));
//            curr.setSelected(false);
//            previouslySelectedm.remove(curr.getName());
//            int itemsUpdate = exhibitDao.update(curr);
//
//            assertEquals(1, itemsUpdate);
//        }
//
//        List<Exhibit> NewExpectedSelected = new ArrayList<>(previouslySelectedm.values());
//        List<Exhibit> daoNewSelectedExhibit = exhibitDao.getSelectedExhibits();
//        assertEquals(NewExpectedSelected.size(), daoNewSelectedExhibit.size());
//    }
}
