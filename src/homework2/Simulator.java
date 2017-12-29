package homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements generic class that simulate system of pipes and filters,
 * the topology of the pipes & filters systems is represented by bipartite graph
 * T is the label type.
 * the simulator has name simulatorName, the graph that represent the system,
 * and list of all edges.
 */

public class Simulator<T, O> {

    private String simulatorName;
    private BipartiteGraph<T> graph;
    private List<T> edgeList;

    /**
     * @requires simulatorName != null
     * @modifies this
     * @effects Constructs a new simulator.
     */
    public Simulator(String simulatorName) {
        this.simulatorName = simulatorName;
        graph = new BipartiteGraph<T>(simulatorName);
        edgeList = new ArrayList<>();
    }

    /**
     * @return the edge list
     */
    public List<T> getEdgeList() {
        return Collections.unmodifiableList(edgeList);
    }

    /**
     * @requires filterLabel!=null, addFilter(filterLabel) and
     * addPipe(filterLabel) have'nt been called before
     * @return true if succeeded, false if failed
     * @effect add new filter to the graph
     */
    public boolean addFilter(T filterLabel, Object obj) {

        try {
            this.graph.addWhiteNode(filterLabel, obj);
        }
        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        catch (UnsupportedOperationException exception){
            throw new UnsupportedOperationException(exception);
        }

        return true;
    }

    /**
     * @requires pipeLabel!=null, addFilter(pipeLabel) and
     * addPipe(pipeLabel) have'nt been called before
     * @return true if succeeded, false if failed
     * @effect add new pipe to the graph
     */
    public boolean addPipe(T pipeLabel, Object obj) {
        try {
            this.graph.addBlackNode(pipeLabel, obj);
        }

        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        catch (UnsupportedOperationException exception){
            throw new UnsupportedOperationException(exception);
        }

        return true;
    }

    /**
     * @requires ((addPipe(parentName) && addFilter(childName)) ||
     *            (addFilter(parentName) && addPipe(childName))) &&
     *            edgeLabel != null &&
     *            node named parentName has no other outgoing edge labeled edgeLabel
     *           && node named childName has no other incoming edge labeled edgeLabel
     * @modifies graph named simName
     * @effects Adds an edge from the node named parentName to the node named
     *          childName in the graph. The new edge's label
     *          is the String edgeLabel.
     */
    public void addEdge(T parentName, T childName, T edgeLabel){
        try {
            this.graph.addEdge(parentName, childName, edgeLabel);
        }

        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        catch (UnsupportedOperationException exception){
            throw new UnsupportedOperationException(exception);
        }

        edgeList.add(edgeLabel);
    }

    public void sendTransaction(T pipeLabel, O opObj){
        if(pipeLabel == null || opObj == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        Pipe<T,O> pipe = (Pipe<T, O>) graph.getNodeObj(pipeLabel);
        pipe.passOperationObj(opObj);
    }

    public Object getPipeObj(T label){
        if (label == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        return graph.getNodeObj(label);
    }

    public Object getFilterObj(T label){
        Object obj;
        if (label == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        try{
            obj = graph.getNodeObj(label);
        }

        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        catch (UnsupportedOperationException exception){
            throw new UnsupportedOperationException(exception);
        }

        return obj;
    }

    /**
     * @modifies this
     * @effects runs simulator for a single time slice.
     */
    public void simulate(){
        List<Object> pipeList = graph.getBlackNodeObj();
        List<Object> filterList = graph.getWhiteNodeObj();

        for (Object pipeObj: pipeList) {

            Pipe<T,O> pipe = (Pipe)pipeObj;
            try {
                pipe.simulate(graph);
            }
            catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Illegal arguments");
            }
        }

        for (Object filterObj: filterList) {
            Filter<T,O> filter = (Filter<T,O>)filterObj;
            try {
                filter.simulate(graph);
            }
            catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Illegal arguments");
            }
        }
    }
}

