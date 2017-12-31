package homework2;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * BipartiteGraphTest contains JUnit block-box unit tests for BipartiteGraph.
 */
public class BipartiteGraphTest {

	@Test
    public void testExample() {
        BipartiteGraphTestDriver driver = new BipartiteGraphTestDriver();
        
        //create a graph
        driver.createGraph("graph1");
        
        //add a pair of nodes
        driver.addBlackNode("graph1", "b1");
        driver.addWhiteNode("graph1", "w2");

        
        //add an edge
        driver.addEdge("graph1", "b1", "w2", "edge1");
        
        //check neighbors
        assertEquals("wrong black nodes", "b1", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "w2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong children", "w2", driver.listChildren ("graph1", "b1"));
        assertEquals("wrong children", "", driver.listChildren ("graph1", "w2"));
        assertEquals("wrong parents", "", driver.listParents ("graph1", "b1"));
        assertEquals("wrong parents", "b1", driver.listParents ("graph1", "w2"));

        driver.addBlackNode("graph1","b3");
        driver.addBlackNode("graph1","b2");
        driver.addWhiteNode("graph1", "w1");
        driver.addWhiteNode("graph1", "w4");
        driver.addWhiteNode("graph1", "w3");
        //check adding nodes
        assertEquals("wrong black nodes", "b1 b2 b3", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "w1 w2 w3 w4", driver.listWhiteNodes("graph1"));

        driver.addEdge("graph1", "b1", "w3", "edge2");
        driver.addEdge("graph1", "b1", "w4", "edge3");
        driver.addEdge("graph1", "b1", "w1", "edge4");
        driver.addEdge("graph1", "b2", "w3", "edge5");
        driver.addEdge("graph1", "b3", "w3", "edge6");
        //check adding edges
        assertEquals("wrong children", "w1 w2 w3 w4", driver.listChildren ("graph1", "b1"));
        assertEquals("wrong parents", "b1 b2 b3", driver.listParents ("graph1", "w3"));

        assertEquals("wrong child node", "w3",driver.getChildByEdgeLabel("graph1","b3" , "edge6"));
        assertEquals("wrong parent node", "b2",driver.getParentByEdgeLabel("graph1","w3" , "edge5"));

        driver.addWhiteNode("graph1", "a");
        driver.addWhiteNode("graph1", "c");
        driver.addWhiteNode("graph1", "ab");
        driver.addWhiteNode("graph1", "z");
        assertEquals("wrong white nodes", "a ab c w1 w2 w3 w4 z", driver.listWhiteNodes("graph1"));


        /////Same for another graph

        //create a graph
        driver.createGraph("graph2");

        //add a pair of nodes
        driver.addBlackNode("graph2", "b1");
        driver.addWhiteNode("graph2", "w2");


        //add an edge
        driver.addEdge("graph2", "b1", "w2", "edge1");

        //check neighbors
        assertEquals("wrong black nodes", "b1", driver.listBlackNodes("graph2"));
        assertEquals("wrong white nodes", "w2", driver.listWhiteNodes("graph2"));
        assertEquals("wrong children", "w2", driver.listChildren ("graph2", "b1"));
        assertEquals("wrong children", "", driver.listChildren ("graph2", "w2"));
        assertEquals("wrong parents", "", driver.listParents ("graph2", "b1"));
        assertEquals("wrong parents", "b1", driver.listParents ("graph2", "w2"));

        driver.addBlackNode("graph2","b3");
        driver.addBlackNode("graph2","b2");
        driver.addWhiteNode("graph2", "w1");
        driver.addWhiteNode("graph2", "w4");
        driver.addWhiteNode("graph2", "w3");
        //check adding nodes
        assertEquals("wrong black nodes", "b1 b2 b3", driver.listBlackNodes("graph2"));
        assertEquals("wrong white nodes", "w1 w2 w3 w4", driver.listWhiteNodes("graph2"));

        driver.addEdge("graph2", "b1", "w3", "edge2");
        driver.addEdge("graph2", "b1", "w4", "edge3");
        driver.addEdge("graph2", "b1", "w1", "edge4");
        driver.addEdge("graph2", "b2", "w3", "edge5");
        driver.addEdge("graph2", "b3", "w3", "edge6");
        //check adding edges
        assertEquals("wrong children", "w1 w2 w3 w4", driver.listChildren ("graph2", "b1"));
        assertEquals("wrong parents", "b1 b2 b3", driver.listParents ("graph2", "w3"));

        assertEquals("wrong child node", "w3",driver.getChildByEdgeLabel("graph2","b3" , "edge6"));
        assertEquals("wrong parent node", "b2",driver.getParentByEdgeLabel("graph2","w3" , "edge5"));

        //check something from first graph again
        assertEquals("wrong white nodes", "a ab c w1 w2 w3 w4 z", driver.listWhiteNodes("graph1"));

        //add white node with the same name
        driver.addWhiteNode("graph1", "w4");
        assertEquals("wrong white nodes", "a ab c w1 w2 w3 w4 z", driver.listWhiteNodes("graph1"));

        //add the same edge again
        driver.addEdge("graph2", "b1", "w3", "edge2");
        //add the same edge with different label
        driver.addEdge("graph2", "b1", "w3", "fail edge");
        assertEquals("wrong children", "w1 w2 w3 w4", driver.listChildren ("graph2", "b1"));
        assertEquals("wrong parents", "b1 b2 b3", driver.listParents ("graph2", "w3"));

    }

}
