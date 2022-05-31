package edu.ucsd.cse110.zooseeker46;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit_Group;

public class ZooData {
    public static class VertexInfo {
        public static enum Kind {
            // The SerializedName annotation tells GSON how to convert
            // from the strings in our JSON to this Enum.
            @SerializedName("gate") GATE,
            @SerializedName("exhibit") EXHIBIT,
            @SerializedName("intersection") INTERSECTION,
            @SerializedName("exhibit_group") EXHIBIT_GROUP
        }

        public String id;
        public Kind kind;
        public String name;
        public List<String> tags;
        public String group_id;

        public double lat;
        public double lng;

    }

    public static class EdgeInfo {
        public String id;
        public String street;
    }

    public static Map<String, ZooData.VertexInfo> loadVertexInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooData.VertexInfo>>(){}.getType();
            List<ZooData.VertexInfo> zooData = gson.fromJson(reader, type);

            // This code is equivalent to:
            //
            //Map<String, ZooData.VertexInfo> indexedZooData = new HashMap();
            //for (ZooData.VertexInfo datum : zooData) {
            //indexedZooData[datum.id] = datum;
            //}
            //
            Map<String, ZooData.VertexInfo> indexedZooData = zooData
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));

            return indexedZooData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    public static Map<String, ZooData.EdgeInfo> loadEdgeInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooData.EdgeInfo>>(){}.getType();
            List<ZooData.EdgeInfo> zooData = gson.fromJson(reader, type);

            Map<String, ZooData.EdgeInfo> indexedZooData = zooData
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));
            return indexedZooData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    public static Graph<String, IdentifiedWeightedEdge> loadZooGraphJSON(Context context, String path) {
        try {
            // Create an empty graph to populate.
            Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);

            // Create an importer that can be used to populate our empty graph.
            JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();

            // We don't need to convert the vertices in the graph, so we return them as is.
            importer.setVertexFactory(v -> v);

            // We need to make sure we set the IDs on our edges from the 'id' attribute.
            // While this is automatic for vertices, it isn't for edges. We keep the
            // definition of this in the IdentifiedWeightedEdge class for convenience.
            importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);

            // On Android, you would use context.getAssets().open(path) here like in Lab 5.
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            // And now we just import it!
            importer.importGraph(g, reader);

            return g;
        } catch (IOException e){
            e.printStackTrace();
            return new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);
        }

    }
}
