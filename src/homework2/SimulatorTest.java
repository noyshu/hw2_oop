package homework2;

import static org.junit.Assert.*;
import org.junit.Test;



/**
 * BipartiteGraphTest contains JUnit block-box unit tests for BipartiteGraph.
 */
public class SimulatorTest {

    @Test
    public void testExample() {
        SimulatorTestDriver driver = new SimulatorTestDriver();

        //create a simulator
        driver.createSimulator("sim1");

        //add 4 nodes
        driver.addChannel("sim1", "C1", 500);
        driver.addParticipant("sim1", "P1", 20);
        driver.addChannel("sim1", "C2", 400);
        driver.addParticipant("sim1", "P2", 1);


        //add an edge
        driver.addEdge("sim1", "C1", "P1", "edge1");
        driver.addEdge("sim1", "C1", "P1", "failEdge");
        driver.addEdge("sim1", "P1", "C2", "edge2");
        driver.addEdge("sim1", "C2", "P2", "edge3");
        driver.addEdge("sim1", "P2", "P1", "failEdge");

        // print edges:
        driver.printAllEdges("sim1");

        // create transactions
        Transaction tx1 = new Transaction("P2" ,15);
        Transaction tx2 = new Transaction("P2" ,35);
        Transaction tx3 = new Transaction("P2" ,20);
        Transaction tx4 = new Transaction("P2" ,30);

        // add transactions
        driver.sendTransaction("sim1", "C1", tx1);
        driver.sendTransaction("sim1", "C1", tx2);
        driver.sendTransaction("sim1", "C2", tx3);
        driver.sendTransaction("sim1", "C2", tx4);

        // check participant balance and channel values
        assertEquals("wrong transaction values", "15.0 35.0", driver.listContents("sim1", "C1"));
        assertEquals("wrong transaction values", "20.0 30.0", driver.listContents("sim1", "C2"));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim1", "P1")));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim1", "P2")));

        //simulate first round
        driver.simulate("sim1");

        // check participant balance and channel values
        assertEquals("wrong transaction values", "", driver.listContents("sim1", "C1"));
        assertEquals("wrong transaction values", "15.0", driver.listContents("sim1", "C2"));
        assertEquals("wrong participant balance", "15.0", String.valueOf(driver.getParticipantBalace("sim1", "P1")));
        assertEquals("wrong participant balance", "50.0", String.valueOf(driver.getParticipantBalace("sim1", "P2")));

        /*********************************************************** second graph *************************************/

        //create a simulator
        driver.createSimulator("sim2");

        //add 4 nodes
        driver.addChannel("sim2", "C1", 500);
        driver.addParticipant("sim2", "P1", 1);
        driver.addChannel("sim2", "C2", 50);
        driver.addParticipant("sim2", "P2", 1);
        driver.addChannel("sim2", "C3", 700);

        //add an edge
        driver.addEdge("sim2", "C1", "P1", "edge1");
        driver.addEdge("sim2", "C1", "C2", "failEdge");
        driver.addEdge("sim2", "P1", "C2", "edge2");
        driver.addEdge("sim2", "C2", "P2", "edge3");
        driver.addEdge("sim2", "P2", "C3", "edge4");
        driver.addEdge("sim2", "P2", "P1", "failEdge");

        // print edges:
        driver.printAllEdges("sim2");

        // create transactions
        Transaction tx_1 = new Transaction("P2" ,25);
        Transaction tx_2 = new Transaction("P2" ,35);
        Transaction tx_3 = new Transaction("P2" ,20);
        Transaction tx_4 = new Transaction("P4" ,30);
        Transaction tx_5 = new Transaction("P2" ,3000);

        // add transactions
        driver.sendTransaction("sim2", "C1", tx_1);
        driver.sendTransaction("sim2", "C1", tx_2);
        driver.sendTransaction("sim2", "C2", tx_3);
        driver.sendTransaction("sim2", "C2", tx_4);
        driver.sendTransaction("sim2", "C3", tx_5);


        // check participant balance and channel values
        assertEquals("wrong transaction values", "25.0 35.0", driver.listContents("sim2", "C1"));
        assertEquals("wrong transaction values", "20.0 30.0", driver.listContents("sim2", "C2"));
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C3"));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim2", "P1")));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim2", "P2")));

        //simulate first round
        driver.simulate("sim2");

        // check participant balance and channel values
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C1"));
        assertEquals("wrong transaction values", "24.0", driver.listContents("sim2", "C2"));
        assertEquals("wrong transaction values", "29.0", driver.listContents("sim2", "C3"));
        assertEquals("wrong participant balance", "35.0", String.valueOf(driver.getParticipantBalace("sim2", "P1")));
        assertEquals("wrong participant balance", "20.0", String.valueOf(driver.getParticipantBalace("sim2", "P2")));

        //simulate first round
        driver.simulate("sim2");

        // check participant balance and channel values
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C1"));
        assertEquals("wrong transaction values", "34.0", driver.listContents("sim2", "C2"));
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C3"));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim2", "P1")));
        assertEquals("wrong participant balance", "44.0", String.valueOf(driver.getParticipantBalace("sim2", "P2")));

        //simulate first round
        driver.simulate("sim2");

        // check participant balance and channel values
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C1"));
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C2"));
        assertEquals("wrong transaction values", "", driver.listContents("sim2", "C3"));
        assertEquals("wrong participant balance", "0.0", String.valueOf(driver.getParticipantBalace("sim2", "P1")));
        assertEquals("wrong participant balance", "78.0", String.valueOf(driver.getParticipantBalace("sim2", "P2")));

    }

}
