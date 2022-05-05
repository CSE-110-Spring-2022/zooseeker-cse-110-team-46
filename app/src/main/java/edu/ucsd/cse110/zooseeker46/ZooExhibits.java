package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ZooExhibits extends AppCompatActivity {
    Map<String, Exhibit> exhibitMap = Collections.emptyMap();
    List<String> idList = new ArrayList<>();
    ArrayList<Exhibit> exhibitArrayList = new ArrayList<>();
    ArrayList<String> selectedExhibits = new ArrayList<>();

    public ZooExhibits(){
        Map<String, ZooData.VertexInfo> vertexInfoMap = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");
        for(Map.Entry<String, ZooData.VertexInfo> entry : vertexInfoMap.entrySet()){
            ZooData.VertexInfo item = entry.getValue();
            String name = entry.getValue().name;
            Exhibit exhibit = new Exhibit(item.id, item.name, item.tags);
            exhibitMap.put(name, exhibit);
            exhibitArrayList.add(exhibit);
        }
    }

    public void getID(String name) {
        for (Map.Entry<String, Exhibit> entry: exhibitMap.entrySet()){
            Exhibit exhibit = entry.getValue();
            if ((exhibit.getName().equals(name)) && (exhibit.getisSelected())){
                idList.add(exhibit.getId());
                selectedExhibits.add(exhibit.getName());
            }
        }
    }
}

