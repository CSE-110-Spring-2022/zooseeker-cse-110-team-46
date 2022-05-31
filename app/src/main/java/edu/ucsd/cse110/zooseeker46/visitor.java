package edu.ucsd.cse110.zooseeker46;

import android.util.Log;

import edu.ucsd.cse110.zooseeker46.locations.Exhibit;
import edu.ucsd.cse110.zooseeker46.tracking.MockDirections;

public class visitor {
    ZooData.VertexInfo currentNode;

    public void setCurrentNode(ZooData.VertexInfo currentNode) {
        this.currentNode = currentNode;
        Log.d("Visitor current Node", this.currentNode.name);
    }

    public ZooData.VertexInfo getCurrentNode() {
        return currentNode;
    }
}
