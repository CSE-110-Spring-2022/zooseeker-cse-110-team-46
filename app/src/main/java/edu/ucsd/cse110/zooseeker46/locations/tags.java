package edu.ucsd.cse110.zooseeker46.locations;

import java.util.ArrayList;
import java.util.List;

public class tags {
    private List<String> tags;
    boolean isEmpty = false;

    public tags(List<String> tags) {
        if (tags.equals(null)) {
            this.tags = null;
            return;
        }
        if(tags.size() == 0){
            isEmpty = true;
        }
        this.tags = tags;
    }

    public List<String> getTags() {
        if(isEmpty){
            return new ArrayList<>();
        }
        return this.tags;
    }

    public void tags(List<String> tags) {
        this.tags = tags;
    }
}
