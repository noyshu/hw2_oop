package homework2;

import java.util.*;

/**
 * This class implements a testing driver for BipartiteGraph. The driver
 * manages BipartiteGraphs whose nodes and edges are Strings.
 */
public class BipartiteGraphTestDriver {

    private Map<String, BipartiteGraph<String>> graphs;

    /**
     * @modifies this
     * @effects Constructs a new test driver.
     */
    public BipartiteGraphTestDriver () {
    	graphs = new HashMap<String, BipartiteGraph<String>>();
    }

    
    /**
     * @requires graphName != null
     * @modifies this
     * @effects Creates a new graph named graphName. The graph is initially
     * 			empty.
     */
    public void createGraph(String graphName) {
    	BipartiteGraph bGraph = new BipartiteGraph(graphName);        
    	graphs.put(graphName, bGraph);
    }

    /**
     * @requires createGraph(graphName)
     *           && nodeName != null
     *           && neither addBlackNode(graphName,nodeName) 
     *                  nor addWhiteNode(graphName,nodeName)
     *                      has already been called on this
     * @modifies graph named graphName
     * @effects Adds a black node represented by the String nodeName to the
     * 			graph named graphName.
     */
    public void addBlackNode(String graphName, String nodeName) {
    	if(graphName == null || !(graphs.containsKey(graphName))) {
            System.out.println("Illegal arguments");
            return;
    	}

    	try {
            graphs.get(graphName).addBlackNode(nodeName, null);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments");
        }
        catch (UnsupportedOperationException ex){
            System.out.println("Unsupported operation");
        }


    }

    /**
     * @requires createGraph(graphName)
     *           && nodeName != null
     *           && neither addBlackNode(graphName,nodeName) 
     *                  nor addWhiteNode(graphName,nodeName)
     *                      has already been called on this
     * @modifies graph named graphName
     * @effects Adds a white node represented by the String nodeName to the
     * 			graph named graphName.
     */
    public void addWhiteNode(String graphName, String nodeName) {
        if(graphName == null || !(graphs.containsKey(graphName))) {
            System.out.println("Illegal arguments");
            return;
        }

        try {
            graphs.get(graphName).addWhiteNode(nodeName, null);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments");
        }
        catch (UnsupportedOperationException ex){
            System.out.println("Unsupported operation");
        }
    }

    /**
     * @requires createGraph(graphName)
     *           && ((addBlackNode(parentName) && addWhiteNode(childName))
     *              || (addWhiteNode(parentName) && addBlackNode(childName)))
     *           && edgeLabel != null
     *           && node named parentName has no other outgoing edge labeled
     * 				edgeLabel
     *           && node named childName has no other incoming edge labeled
     * 				edgeLabel
     * @modifies graph named graphName
     * @effects Adds an edge from the node parentName to the node childName
     * 			in the graph graphName. The new edge's label is the String
     * 			edgeLabel.
     */
    public void addEdge(String graphName,
    					String parentName, String childName, 
                        String edgeLabel) {

        Node parent, child;
        BipartiteGraph graph;
        if (edgeLabel == null || parentName == null || childName == null) {
            System.out.println("Illegal arguments");
            return;
        }

        if (!graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return;
        }

        graph = graphs.get(graphName);

        try {
            graph.addEdge(parentName, childName, edgeLabel);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments");
        }
        catch (UnsupportedOperationException ex){
            System.out.println("Unsupported operation");
        }
    }

    
    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the black nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listBlackNodes(String graphName) {
    	if (graphName == null || !graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return "";
        }

        List<Node<String>> blackNodes = graphs.get(graphName).getBlackNodes();
        List<String> blackListString = new ArrayList<String>();
        String blackNodesNames = new String("");

        for (int i = 0; i < blackNodes.size(); i++) {
            blackListString.add(blackNodes.get(i).getLabel());
        }

        Collections.sort(blackListString, String.CASE_INSENSITIVE_ORDER);
        
        for (int i = 0; i < blackListString.size(); i++) {
        	if (i < blackListString.size() - 1)
        		blackNodesNames = blackNodesNames.concat(blackListString.get(i)+" ");
        	else
        		blackNodesNames = blackNodesNames.concat(blackListString.get(i));
        }
        return blackNodesNames;
    }

    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the white nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listWhiteNodes(String graphName) {
    	if (graphName == null || !graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return "";
        }

        List<Node<String>> whiteNode = graphs.get(graphName).getWhiteNodes();
        List<String> whiteListString = new ArrayList<String>();
        String whiteNodesNames = new String("");

        for (int i = 0; i < whiteNode.size(); i++) {
            whiteListString.add(whiteNode.get(i).getLabel());
        }

        Collections.sort(whiteListString, String.CASE_INSENSITIVE_ORDER);

        for (int i = 0; i < whiteListString.size(); i++) {
        	if (i<whiteListString.size()-1)
        		whiteNodesNames = whiteNodesNames.concat(whiteListString.get(i)+" ");
        	else
        		whiteNodesNames = whiteNodesNames.concat(whiteListString.get(i));
        }
            
        return whiteNodesNames;
    }

    
    /**
     * @requires createGraph(graphName) && createNode(parentName)
     * @return a space-separated list of the names of the children of
     * 		   parentName in the graph graphName, in alphabetical order.
     */
    public String listChildren(String graphName, String parentName) {

        if (graphName == null || !graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return null;
        }

        BipartiteGraph graph = graphs.get(graphName);

        List<String> childList = graphs.get(graphName).getChildList(parentName);
        List<String> childListCpy = new ArrayList<>(childList);
        String childNames;

        Collections.sort(childListCpy, String.CASE_INSENSITIVE_ORDER);
        childNames = String.join(" ", childListCpy);

        return childNames;
    }

    /**
     * @requires createGraph(graphName) && createNode(childName)
     * @return a space-separated list of the names of the parents of
     * 		   childName in the graph graphName, in alphabetical order.
     */
    public String listParents(String graphName, String childName) {
        if (graphName == null || !graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return "";
        }

        BipartiteGraph graph = graphs.get(graphName);

        List<String> parentList = graphs.get(graphName).getParentList(childName);
        List<String> parentListCpy = new ArrayList<String>(parentList);
        String parentNames;

        Collections.sort(parentListCpy, String.CASE_INSENSITIVE_ORDER);
        parentNames = String.join(" ", parentListCpy);

        return parentNames;
    }

    /**
     * @requires addEdge(graphName, parentName, str, edgeLabel) for some
     * 			 string str
     * @return the name of the child of parentName that is connected by the
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getChildByEdgeLabel(String graphName, String parentName,
    								   String edgeLabel) {
        String childName;

        if (graphName == null || !graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
        }

        if (parentName == null) {
            System.out.println("Illegal arguments");
        }

        BipartiteGraph graph = graphs.get(graphName);

        try {
            childName = (String) (graph.getChildByEdgeLabel(parentName, edgeLabel));
        }
        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments");
            return "";
        }
        catch (UnsupportedOperationException ex){
            System.out.println("Unsupported operation");
            return "";
        }

        return childName;
    }

    
    /**
     * @requires addEdge(graphName, str, childName, edgeLabel) for some
     * 			 string str
     * @return the name of the parent of childName that is connected by the 
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getParentByEdgeLabel(String graphName, String childName,
    									String edgeLabel) {
        String parentName;
        if (graphName == null){
            System.out.println("Illegal arguments");
            return "";
        }

        if (!graphs.containsKey(graphName)) {
            System.out.println("Illegal arguments");
            return "";
        }

        if (childName == null) {
            System.out.println("child name is null");
            return "";
        }

        BipartiteGraph graph = graphs.get(graphName);

        try {
            parentName = (String)(graph.getParentByEdgeLabel(childName, edgeLabel));
        }
        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments");
            return "";
        }
        catch (UnsupportedOperationException ex){
            System.out.println("Unsupported operation");
            return "";
        }

        return parentName;
    }
}
