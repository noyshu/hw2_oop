package homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements generic class that simulate system of pipes and filters,
 * T is label type.
 * the simulator has name simulatorName, a graph 
 * and list of the edges.
 */

public class Simulator<T, E> {

	private List<T> edgesList;
    private BipartiteGraph<T> graph;
    private String simName;

    /**
     * @requires simName != null
     * @modifies this
     * @effects Constructs a new simulator.
     */
    public Simulator(String simulatorName) {
        graph = new BipartiteGraph<T>(simulatorName);
        edgesList = new ArrayList<>();
        this.simName = simulatorName;
    }

    /**
     * @return the list of edges
     */
    public List<T> getEdgeList() {
        return Collections.unmodifiableList(edgesList);
    }

    /**
     * @requires filterLabel!=null, addFilter(filterLabel) and
     * make sure addPipe(filterLabel) have'nt been called before
     * @return true if succeeded 
     * @effect add new filter to the graph
     */
    public boolean addFilter(T filtLabel, Object obj) {

        try {
            this.graph.addWhiteNode(filtLabel, obj);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        return true;
    }

    /**
     * @requires ((addPipe(parentName) && addFilter(childName)) ||
     *            (addFilter(parentName) && addPipe(childName))) &&
     *           
     *            edgeLabel != null &&
     *            node named parentName doesnt have outgoing edge labeled edgeLabel
     *           && node named childName doesnt have incoming edge labeled edgeLabel
     * @modifies graph named simName
     * @effects Adds an edge from node parentName to node 
     *          childName in the graph. The new label
     *          is the String edgeLabel.
     */
    public void addEdge(T parentName, T childName, T edgeLabel){
        try {
            this.graph.addEdge(edgeLabel,parentName, childName);
        }

        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        catch (UnsupportedOperationException exception){
            throw new UnsupportedOperationException(exception);
        }

        edgesList.add(edgeLabel);
    }

    /**
     * @requires pipeLabel!=null, addFilter(pipeLabel) and
     * addPipe with this pipeLabel wasn't called before
     * @return true if succeeded, false if failed
     * @effect add new pipe to the graph
     */
    public boolean addPipe(T pipeLabel, Object obj) {
        try {
            this.graph.addBlackNode(pipeLabel, obj);
        }

        catch (Exception exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        return true;
    }


    /**
     * 
     * @requires pipeLabel != null && opObj != null
     * @effects send transaction to the pipe named pipeLabel
     */
    public void sendTransaction(T pipeLabel, E obj){
    	
        if(pipeLabel == null || obj == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        Pipe<T,E> pipe = (Pipe<T, E>) graph.getNodeObj(pipeLabel);
        pipe.passOperationObj(obj);
    }

    /**
     * 
     * @param filterLabel != null
     * @return filter node named filterLabel 
     */
    public Object getFilterObj(T filterLabel){
        Object obj;
        if (filterLabel == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        try{
            obj = graph.getNodeObj(filterLabel);
        }

        catch (Exception exception) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        

        return obj;
    }

    /**
     * 
     * @param pipeLabel != null
     * @return pipe node named pipeLabel
     */
    public Object getPipeObj(T pipeLabel){
        if (pipeLabel == null){
            throw new IllegalArgumentException("Illegal argument");
        }

        return graph.getNodeObj(pipeLabel);
    }


    /**
     * @modifies this
     * @effects runs simulator for one time slice.
     */
    public void simulate(){
    	// extract pipes and filters
        List<Object> pipesList = graph.getBlackNodeObj();
        List<Object> filtersList = graph.getWhiteNodeObj();

        // running over pipes and simulate them
        for (Object pipeObj: pipesList) {

            Pipe<T,E> pipe = (Pipe)pipeObj;
            try {
                pipe.simulate(graph);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Illegal arguments");
            }
        }

        // running over filters and simulates them
        for (Object filterObj: filtersList) {
            Filter<T,E> filter = (Filter<T,E>)filterObj;
            try {
                filter.simulate(graph);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Illegal arguments");
            }
        }
    }
}

