package edu.ucsd.cse110.zooseeker46;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.plan.PlanExhibitsAdapter;
import edu.ucsd.cse110.zooseeker46.search.ExhibitSelectAdapter;

@RunWith(AndroidJUnit4.class)
public class SearchPageUnitTest {
//should update later with a database
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    List<String> testArrayZoo = new ArrayList<>();
    PlanExhibitsAdapter adapter = new PlanExhibitsAdapter();
    ArrayList<Exhibit> totalExhibits;
    ExhibitSelectAdapter customAdapter;
    ZooExhibits ze = new ZooExhibits(ZooData.loadVertexInfoJSON(context, "sample_node_info.json"));

    @Before
    public void createAdapter() {
        Map<String, Exhibit> mapExhibits =  ze.nameToVertexMap();
        totalExhibits = new ArrayList<>();

        for(Map.Entry<String, Exhibit> curr: mapExhibits.entrySet()){
            totalExhibits.add(curr.getValue());
        }

        customAdapter = new ExhibitSelectAdapter(context, totalExhibits);
    }
    @Test
    public void orderIsCorrect() {
        createAdapter();
        ArrayList<Exhibit> trueorder = totalExhibits;
        Collections.sort(trueorder, Exhibit.ExhibitNameComparator);

        for(int i = 0; i < trueorder.size(); i++){
            assertEquals(trueorder.get(i).getName(), customAdapter.getExhibit(i).getName());
        }
    }

    @Test
    public void baseCountisZero(){
        createAdapter();
        assertEquals(0, customAdapter.getSelectedExhibitsCount());
    }

}


