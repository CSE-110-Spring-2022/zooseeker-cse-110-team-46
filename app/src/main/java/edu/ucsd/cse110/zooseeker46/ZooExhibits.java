package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZooExhibits extends AppCompatActivity {
    Map<String, ZooData.VertexInfo> vertexInfoMap;

    public ZooExhibits(Map<String, ZooData.VertexInfo> vertexInfoMap){
        this.vertexInfoMap = vertexInfoMap;
    }

    public Map<String, Exhibit> nameToVertexMap() {
        Map<String, Exhibit> map = new HashMap<>();
        for (Map.Entry<String, ZooData.VertexInfo> entry: vertexInfoMap.entrySet()){
            ZooData.VertexInfo item = entry.getValue();
            if (item.kind == ZooData.VertexInfo.Kind.EXHIBIT) {
                Exhibit e = new Exhibit(item.id, item.name, item.tags);
                map.put(item.name, e);
            }
        }
        return map;
    }

    public ArrayList<String> getIDList(ArrayList<String> selected){
        Map<String, Exhibit> nameInfoMap = this.nameToVertexMap();
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++){
            Exhibit e = nameInfoMap.get(selected.get(i));
            idList.add(e.getId());
        }
        return idList;
    }
}

