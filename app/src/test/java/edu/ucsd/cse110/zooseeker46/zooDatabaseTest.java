package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

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

import edu.ucsd.cse110.zooseeker46.database.ExhibitDao;
import edu.ucsd.cse110.zooseeker46.database.ZooDataDatabase;
import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

@RunWith(AndroidJUnit4.class)
public class zooDatabaseTest {
    private ExhibitDao exhibitDao;
    private ZooDataDatabase db;
    @Before
    public void CreateDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, ZooDataDatabase.class)
                .allowMainThreadQueries()
                .build();
        exhibitDao = db.exhibitDao();
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

        assertNotEquals(id1,id2);
        Exhibit item = exhibitDao.get(id1);
        System.out.println(item);

    }

    @Test
    public void testExhibitGet() {
        String[] ex1Tags = {"lions", "cats", "mammal", "africa"};
        List<String> ex1TagsList = new ArrayList<>();
        Collections.addAll(ex1TagsList,ex1Tags);
        Exhibit ex1 = new Exhibit("lions","Lions", ex1TagsList);

        long id = exhibitDao.insert(ex1);

        Exhibit item = exhibitDao.get(id);

        assertNotNull(id);
        assertNotNull(item);
        assertEquals(id, item.long_id);
        assertEquals(ex1.name, item.name);
        assertEquals(ex1.id, item.id);
        assertEquals(ex1.tags, item.tags);
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
}
