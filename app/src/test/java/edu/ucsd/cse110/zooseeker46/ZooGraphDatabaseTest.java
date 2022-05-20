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

import edu.ucsd.cse110.zooseeker46.database.IntersectionDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.ZooNode;
import edu.ucsd.cse110.zooseeker46.zoographdatabase.ZooGraphDatabase;
import edu.ucsd.cse110.zooseeker46.zoographdatabase.ZooGraphNodeDao;

@RunWith(AndroidJUnit4.class)
public class ZooGraphDatabaseTest {
    private ZooGraphDatabase db;
    private ZooGraphNodeDao zooGraphNodeDao;
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @Before
    public void CreateDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, ZooGraphDatabase.class)
                .allowMainThreadQueries()
                .build();
        zooGraphNodeDao = db.zooNodeDao();
    }
    @After
    public void CloseDb(){
        db.close();
    }

    @Test
    public void testGraphInsert(){
        ZooNode z = new ZooNode("gorillas");
        long id1 = zooGraphNodeDao.insert(z);
        ZooNode z1 = zooGraphNodeDao.get(id1);
        assertEquals("gorillas", z1.id);

    }

    @Test
    public void testGraphGet(){
        ZooNode z = new ZooNode("gorillas");
        ZooNode z2 = new ZooNode("lions");
        long id1 = zooGraphNodeDao.insert(z);
        ZooNode z1 = zooGraphNodeDao.get(id1);
        assertEquals("gorillas", z1.id);

    }
}
