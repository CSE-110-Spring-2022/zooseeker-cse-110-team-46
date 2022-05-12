package edu.ucsd.cse110.zooseeker46;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.internal.bytecode.ClassHandler;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

@RunWith(AndroidJUnit4.class)
public class ZooExhibitsTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void ZooExhibitsNameMap() {
        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        Map<String, Exhibit> map2 = zoo.nameToVertexMap();
        Set<String> keyMap = map2.keySet();
        assertEquals(5, keyMap.size());
    }
    @Test
    public void ZooExhibitsConstruct(){
        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        ArrayList<String> selected = new ArrayList<>();
        selected.add("Arctic Foxes");
        selected.add("Lions");
        ArrayList<String> idList = zoo.getIDList(selected);

        assertEquals("lions", idList.get(1));
    }

    @Test
    public void ZooExhibitsList(){
        Map<String, ZooData.VertexInfo> map = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        ZooExhibits zoo = new ZooExhibits(map);
        ArrayList<String> namesList = zoo.exhibitList();
        assertEquals(5, namesList.size());
    }
}
