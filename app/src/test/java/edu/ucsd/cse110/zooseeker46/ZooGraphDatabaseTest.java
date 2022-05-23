package edu.ucsd.cse110.zooseeker46;


import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.ucsd.cse110.zooseeker46.database.IntersectionDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.ZooEdge;
import edu.ucsd.cse110.zooseeker46.locations.ZooNode;
import edu.ucsd.cse110.zooseeker46.zoographdatabase.ZooGraphDatabase;
import edu.ucsd.cse110.zooseeker46.zoographdatabase.ZooGraphEdgeDao;
import edu.ucsd.cse110.zooseeker46.zoographdatabase.ZooGraphNodeDao;

@RunWith(AndroidJUnit4.class)
public class ZooGraphDatabaseTest {
    private ZooGraphDatabase db;
    private ZooGraphNodeDao zooGraphNodeDao;
    private ZooGraphEdgeDao zooGraphEdgeDao;
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void CreateDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, ZooGraphDatabase.class)
                .allowMainThreadQueries()
                .build();
        zooGraphNodeDao = db.zooNodeDao();
        zooGraphEdgeDao = db.zooEdgeDao();
    }
    @After
    public void CloseDb(){
        db.close();
    }

    @Test
    public void testGraphNodeInsert(){
        ZooNode z = new ZooNode("gorillas");
        long id1 = zooGraphNodeDao.insert(z);
        ZooNode z1 = zooGraphNodeDao.get(id1);
        assertEquals("gorillas", z1.id);

    }

    @Test
    public void testGraphNodeGet(){
        ZooNode z = new ZooNode("gorillas");
        ZooNode z2 = new ZooNode("lions");
        long id1 = zooGraphNodeDao.insert(z);
        long id2 = zooGraphNodeDao.insert(z2);
        ZooNode z1 = zooGraphNodeDao.get(id1);
        assertEquals("gorillas", z1.id);

    }

    @Test
    public void testGraphNodeGetAll(){
        ZooNode z = new ZooNode("gorillas");
        ZooNode z2 = new ZooNode("lions");
        ZooNode z3 = new ZooNode("arctic_foxes");
        long id1 = zooGraphNodeDao.insert(z);
        long id2 = zooGraphNodeDao.insert(z2);
        long id3 = zooGraphNodeDao.insert(z3);
        List<ZooNode> list = zooGraphNodeDao.getAll();
        assertEquals(3,list.size());
    }

    @Test
    public void testGraphEdgeGet(){
        ZooEdge z = new ZooEdge("edge-0", "entrance_exit_gate", "entrance_plaza", 10.0);
        ZooEdge z2 = new ZooEdge("edge-1", "entrance_plaza", "gorillas", 20.0);
        long id1 = zooGraphEdgeDao.insert(z);
        long id2 = zooGraphEdgeDao.insert(z2);
        ZooEdge z1 = zooGraphEdgeDao.get(id1);
        assertEquals("edge-0", z1.id);

    }

    @Test
    public void testGraphEdgeInsert(){
        ZooEdge z = new ZooEdge("edge-0", "entrance_exit_gate", "entrance_plaza", 10.0);
        long id1 = zooGraphEdgeDao.insert(z);
        ZooEdge z1 = zooGraphEdgeDao.get(id1);
        assertEquals("edge-0", z1.id);

    }

    @Test
    public void testGraphEdgeGetAll(){
        ZooEdge z = new ZooEdge("edge-0", "entrance_exit_gate", "entrance_plaza", 10.0);
        ZooEdge z2 = new ZooEdge("edge-1", "entrance_plaza", "gorillas", 20.0);
        long id1 = zooGraphEdgeDao.insert(z);
        long id2 = zooGraphEdgeDao.insert(z2);
        List<ZooEdge> list = zooGraphEdgeDao.getAll();
        assertEquals(2, list.size());

    }

}
