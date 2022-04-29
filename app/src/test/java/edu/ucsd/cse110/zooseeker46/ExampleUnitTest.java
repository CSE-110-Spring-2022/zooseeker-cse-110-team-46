package edu.ucsd.cse110.zooseeker46;

import org.junit.Test;

import static org.junit.Assert.*;

import static edu.ucsd.cse110.zooseeker46.ZooData.loadVertexInfoJSON;

import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSize() {
        //Map<String, ZooData.VertexInfo> mapVertex = loadVertexInfoJSON(this, "sample_node_info.json");
    }
}