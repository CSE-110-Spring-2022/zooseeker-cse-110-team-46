package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;

public class ZooExhibits extends AppCompatActivity {
    Map<String, ZooData.VertexInfo> vertexInfoMap;
    //private Context context;

    public ZooExhibits(Map<String, ZooData.VertexInfo> vertexInfoMap){
        //this.vertexInfoMap = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        //this.context = context;
        this.vertexInfoMap = vertexInfoMap;
    }

    /*
    Returns a list of exhibit names to use
     */
    public ArrayList<String> exhibitList() {
        ArrayList<String> exhibits = new ArrayList<>();
        for (Map.Entry<String, ZooData.VertexInfo> entry: vertexInfoMap.entrySet()){
            ZooData.VertexInfo item = entry.getValue();
            if (item.kind == ZooData.VertexInfo.Kind.EXHIBIT) {
                String name = item.name;
                exhibits.add(name);
            }
        }
        return exhibits;
    }

    /*
    Returns a map where exhibit name is mapped to its information
    Can use if name is given and need to find id.
     */
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

    /*
    returns an ArrayList of Exhibits for ExhibitSelectAdapter to use
     */
    public ArrayList<Exhibit> getExhibits() {
        Map<String, Exhibit> nameInfoMap = this.nameToVertexMap();
        Collection<Exhibit> collection = nameInfoMap.values();
        ArrayList<Exhibit> exhibits = new ArrayList<>(collection);
        return exhibits;
    }

    /*
    Gives a list of IDs for given exhibit names.
     */
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

