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
        driver.addBlackNode("graph1", "n1");
        driver.addWhiteNode("graph1", "n2");

        //add an edge
        driver.addEdge("graph1", "n1", "n2", "edge");
        driver.addEdge("graph1", "n1", "n2", "failEdge");

        //check neighbors
        assertEquals("wrong black nodes", "n1", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "n2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong children", "n2", driver.listChildren ("graph1", "n1"));
        assertEquals("wrong children", "", driver.listChildren ("graph1", "n2"));
        assertEquals("wrong parents", "", driver.listParents ("graph1", "n1"));
        assertEquals("wrong parents", "n1", driver.listParents ("graph1", "n2"));
        assertEquals("wrong parents", "", driver.listParents ("graph1", "n1"));
        assertEquals("wrong child", "", driver.getChildByEdgeLabel ("graph1", "n2","edge"));
        assertEquals("Two edges from n1 to n2", "", driver.getChildByEdgeLabel ("graph1", "n1","failEdge"));



        //try to add an existing node - supposed to fail
        driver.addBlackNode("graph1", "n2");
        assertEquals("add 2 nodes with the same label", "n1", driver.listBlackNodes("graph1"));
        assertEquals("add 2 nodes with the same label", "n2", driver.listWhiteNodes("graph1"));

        //try to add illegal edges

        driver.addBlackNode("graph1", "n3");
        //add edge from a black node to a black node;
        driver.addEdge("graph1", "n1", "n3", "edge2");
        assertEquals("wrong children", "n2", driver.listChildren ("graph1", "n1"));
        assertEquals("wrong children", "", driver.listChildren ("graph1", "n2"));
        assertEquals("wrong child", "", driver.listChildren ("graph1", "n3"));
        assertEquals("wrong child", "", driver.getChildByEdgeLabel ("graph1", "n1","edge2"));
        //add another graph
        driver.createGraph("graph2");

        assertEquals("wrong black nodes", "", driver.listBlackNodes("graph2"));
        assertEquals("wrong white nodes", "", driver.listWhiteNodes("graph2"));

        //add nodes with the same names as in graph 1
        driver.addWhiteNode("graph2", "n1");
        driver.addBlackNode("graph2", "n2");
        //add a node with a new name to graph2
        driver.addWhiteNode("graph2", "n4");

        //add an edge to the new graph
        driver.addEdge("graph2", "n1", "n2", "edge");

        //make sure both graphs are still ok
        assertEquals("wrong white nodes", "n1 n4", driver.listWhiteNodes("graph2"));
        assertEquals("wrong Black nodes", "n2", driver.listBlackNodes("graph2"));
        assertEquals("wrong black nodes", "n1 n3", driver.listBlackNodes("graph1"));
        assertEquals("wrong white nodes", "n2", driver.listWhiteNodes("graph1"));
        assertEquals("wrong child", "n2", driver.getChildByEdgeLabel ("graph2", "n1","edge"));
        assertEquals("wrong child", "n2", driver.getChildByEdgeLabel ("graph1", "n1","edge"));
        assertEquals("wrong child", "", driver.getChildByEdgeLabel ("graph2", "n4","edge"));
        assertEquals("wrong parent", "n1", driver.getParentByEdgeLabel ("graph2", "n2","edge"));
        assertEquals("wrong parent", "", driver.getParentByEdgeLabel ("graph2", "n4","edge"));
        assertEquals("wrong parent", "", driver.getParentByEdgeLabel ("graph2", "n1","edge2"));

        driver.addBlackNode("graph2", "n3");
        driver.addEdge("graph2", "n1", "n3", "edge3");

        assertEquals("wrong children", "n2 n3", driver.listChildren("graph2", "n1"));

        driver.addEdge("graph2", "n2", "n4", "edge3");
        assertEquals("wrong parents", "n2", driver.listParents("graph2", "n4"));

        //check alphabetic order
        driver.addBlackNode("graph2", "a");
        assertEquals("wrong Black nodes", "a n2 n3", driver.listBlackNodes("graph2"));

    }

}
