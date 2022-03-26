package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class V2MapFactoryTest {

    @Test
    void createGraph() {
        V2MapFactory factory2 = new V2MapFactory(2);
        V2MapFactory factory3 = new V2MapFactory(3);
        V2MapFactory factory4 = new V2MapFactory(4);
        V2MapFactory factory5 = new V2MapFactory(5);
        HashMap<String, HashSet<String>> graph2 = factory2.createGraph();
        HashMap<String, HashSet<String>> graph3 = factory3.createGraph();
        HashMap<String, HashSet<String>> graph4 = factory4.createGraph();
        HashMap<String, HashSet<String>> graph5 = factory5.createGraph();
        assertEquals(graph2.get("a1").contains("a3"), true);
        assertEquals(graph2.get("a1").contains("a2"), true);
        assertEquals(graph2.get("a1").contains("b1"), true);
    }
}