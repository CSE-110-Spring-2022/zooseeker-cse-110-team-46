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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void testExhibitGetWithValidParent() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        String parent_id = "Avery_pkw";
        Exhibit ex1 = new Exhibit("lions","Lions", parent_id, ex1TagsList);
        long gen_id = exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get(gen_id);

        assertNotNull(gen_id);
        assertNotNull(item);
        assertEquals(gen_id, item.long_id);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.tags.getTags(), item.tags.getTags());
        assertEquals(ex1.getParent_id(), item.parent_id);
    }

    @Test
    public void testExhibitGetWithNoParent() {
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
        assertEquals(ex1.getParent_id(), item.parent_id);
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
        Exhibit e = exhibitDao.get(id);
        assertNotNull(ex1);
        assertEquals("Super Lions", e.name);
    }

    @Test
    public void testExhibitGetAll(){
        /*Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        List<Exhibit> actList = zoo.getExhibits();
        for (Exhibit ex : actList){
            exhibitDao.insert(ex);
        }*/
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        long gen_id = exhibitDao.insert(ex1);
        ex1 = exhibitDao.get(gen_id);
        ex1.isSelected = true;
        int smt = exhibitDao.update(ex1);
        String[] ex2Tags = {"gorilla", "monkey", "ape", "mammal"};
        List<String> ex2TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex2Tags);
        Exhibit ex2 = new Exhibit("gorillas","Gorillas", ex2TagsList);
        long id2 = exhibitDao.insert(ex2);
        Exhibit return2 = exhibitDao.get(gen_id);
        Exhibit return1 = exhibitDao.get(id2);
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

    @Test
    public void testGateUpdate() {
        String[] ex1Tags = {"enter", "leave", "start", "begin", "entrance", "exit"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Gate gate = new Gate("entrance_exit_gate", "Entrance and Exit Gate", ex1TagsList);
        long id1 = gateDao.insert(gate);
        gate = gateDao.get(id1);
        gate.name = "Super Main Entrance";


        int itemsUpdate = gateDao.update(gate);

        assertEquals(1, itemsUpdate);

        Gate gate1 = gateDao.get(id1);
        assertNotNull(gate);
        assertEquals("Super Main Entrance", gate1.getName());
    }

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

    @Test
    public void testIntersectionUpdate(){
        List<String> ex1TagsList = new ArrayList<>();
        //Collections.addAll(ex1TagsList,ex1Tags);
        Intersection inter = new Intersection("entrance_plaza", "Entrance Plaza", ex1TagsList);
        long id1 = intersectionDao.insert(inter);
        Intersection item1 = intersectionDao.get(id1);
        item1.name = "Enter here!";
        int smt = intersectionDao.update(item1);
        item1 = intersectionDao.get(id1);
        assertNotNull(item1);
        assertEquals("Enter here!", item1.name);
    }

    @Test
    public void testExhibitSelectOne() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);
        long id = exhibitDao.insert(ex1);
        ex1 = exhibitDao.get(id);

        assertEquals(0, exhibitDao.getSelectedExhibits().size());

        ex1.setSelected(true);
        int itemsUpdate = exhibitDao.update(ex1);
        assertEquals(1, itemsUpdate);
        assertTrue(exhibitDao.get(id).getIsSelected());
        assertEquals(1, exhibitDao.getSelectedExhibits().size());
        assertEquals(ex1.getName(), exhibitDao.getSelectedExhibits().get(0).getName());
    }

    @Test
    public void testExhibitSelectMany() {
        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        List<Exhibit> actList = zoo.getExhibits();
        List<Long> ids = new ArrayList<>();
        for (Exhibit ex : actList){
            ids.add(exhibitDao.insert(ex));
        }

        // Check if all exhibits inserted correctly:
        for(int i = 0; i < actList.size(); i++){
            assertEquals(actList.get(i).getName(),exhibitDao.get(ids.get(i)).getName());
        }

        // Select every few exhibits
        List<Exhibit> ExpectedSelectedExhibit = new ArrayList<>();
        for(int i = 0; i < actList.size(); i += 2){
            Exhibit curr = exhibitDao.get(ids.get(i));
            curr.setSelected(true);
            ExpectedSelectedExhibit.add(curr);
            int itemsUpdate = exhibitDao.update(curr);

            assertEquals(1, itemsUpdate);
        }

        List<Exhibit> daoSelectedExhibit = exhibitDao.getSelectedExhibits();
        assertEquals(ExpectedSelectedExhibit.size(), daoSelectedExhibit.size());

        for(int i = 0; i < ExpectedSelectedExhibit.size(); i++){
            assertEquals(ExpectedSelectedExhibit.get(i).getName(), daoSelectedExhibit.get(i).getName());
            assertEquals(ExpectedSelectedExhibit.get(i).getTags(), daoSelectedExhibit.get(i).getTags());
        }

        // Deselect a few of the selected exhibits
        Map<String, Long> m = new HashMap<String, Long>();
        for(int i = 0; i < actList.size(); i++){
            m.put(actList.get(i).getName(), ids.get(i));
        }
        List<Long> selectedIDS = new ArrayList<>();
        for(Exhibit curr: ExpectedSelectedExhibit){
            selectedIDS.add(m.get(curr.getName()));
            System.out.println("before: " + curr.getName());
        }

        Map<String, Exhibit> previouslySelectedm = new HashMap<>();
        for(Exhibit curr: ExpectedSelectedExhibit){
            previouslySelectedm.put(curr.getName(), curr);
        }

        // Deselect a few of the selected items
        for(int i = 1; i < selectedIDS.size(); i++){
            Exhibit curr = exhibitDao.get(selectedIDS.get(i));
            curr.setSelected(false);
            previouslySelectedm.remove(curr.getName());
            int itemsUpdate = exhibitDao.update(curr);

            assertEquals(1, itemsUpdate);
        }

        List<Exhibit> NewExpectedSelected = new ArrayList<>(previouslySelectedm.values());
        List<Exhibit> daoNewSelectedExhibit = exhibitDao.getSelectedExhibits();
        assertEquals(NewExpectedSelected.size(), daoNewSelectedExhibit.size());
    }
}
