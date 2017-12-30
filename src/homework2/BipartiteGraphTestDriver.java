package homework2;
import java.util.*;
import java.util.stream.Collectors;


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
       graphs = new HashMap<>();
    }

    
    /**
     * @requires graphName != null
     * @modifies this
     * @effects Creates a new graph named graphName. The graph is initially
     * 			empty.
     */
    public void createGraph(String graphName) {
        if(graphName == null){
            System.out.println("graph name is null");
            return;
        }
        if(graphs.containsKey(graphName)){
            System.out.println("graph named " + graphName + "already exists, not adding graph");
            return;
        }
        graphs.put(graphName,new BipartiteGraph<>(graphName));
        return;
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
        if(graphName == null || nodeName == null ){
            System.out.println("graph name or node name is null");
            return;
        }
        if(!graphs.containsKey(graphName)){
            System.out.println("graph name or node name is null");
            return;
        }
        graphs.get(graphName).addBlackNode(nodeName, null);
        return;
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
        if(graphName == null || nodeName == null ){
            System.out.println("graph name or node name is null");
            return;
        }
        if(!graphs.containsKey(graphName)){
            System.out.println("graph name or node name is null");
            return;
        }
        graphs.get(graphName).addWhiteNode(nodeName,null);
        return;
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
    	if (graphName == null || parentName == null || childName == null || edgeLabel == null){
    	    System.out.println("one of the labels is null");
    	    return;
        }
        if (!graphs.containsKey(graphName)){
            System.out.println("graph label doesn't exists");
            return;
        }
        graphs.get(graphName).addEdge(edgeLabel,parentName,childName);
    	return;
    }

    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the black nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listBlackNodes(String graphName) {
        if (graphName == null){
            System.out.println("graph name is null");
            return null ;
        }
        if (!graphs.containsKey(graphName)){
            System.out.println("graph with this label does not exist");
            return null;
        }
        List list =graphs.get(graphName).getBlackNodeList().stream().map(u->u.getLabel()).collect(Collectors.toList());
        Collections.sort(list);
        return String.join(" ",list);
    }
    /**
     * @requires createGraph(graphName)
     * @return a space-separated list of the names of all the white nodes
     * 		   in the graph graphName, in alphabetical order.
     */
    public String listWhiteNodes(String graphName) {
        if (graphName == null){
            System.out.println("graph name is null");
            return null ;
        }
        if (!graphs.containsKey(graphName)){
            System.out.println("graph with this label does not exist");
            return null;
        }
        List list =graphs.get(graphName).getWhiteNodeList().stream().map(u->u.getLabel()).collect(Collectors.toList());
        Collections.sort(list);
        return String.join(" ",list);
    }
    /**
     * @requires createGraph(graphName) && createNode(parentName)
     * @return a space-separated list of the names of the children of
     * 		   parentName in the graph graphName, in alphabetical order.
     */
    public String listChildren(String graphName, String parentName) {
        if (graphName == null || parentName == null){
            System.out.println("one of the labels is null");
            return null;
        }
        if(!graphs.containsKey(graphName)){
            System.out.println("graph does not exist");
            return null;
        }
        List<String> nameList = graphs.get(graphName).getNodeChildren(parentName).stream().map(Node::getLabel).collect(Collectors.toList());
        Collections.sort(nameList);
        return String.join(" ",nameList);
    }

    
    /**
     * @requires createGraph(graphName) && createNode(childName)
     * @return a space-separated list of the names of the parents of
     * 		   childName in the graph graphName, in alphabetical order.
     */
    public String listParents(String graphName, String childName) {
        if (graphName == null || childName == null){
            System.out.println("one of the labels is null");
            return null;
        }
        if(!graphs.containsKey(graphName)){
            System.out.println("graph does not exist");
            return null;
        }
        List<String> nameList =  graphs.get(graphName).getNodeParents(childName).stream().map(Node::getLabel).collect(Collectors.toList());
        Collections.sort(nameList);
        return String.join(" ",nameList);
    }

    
    /**
     * @requires addEdge(graphName, parentName, str, edgeLabel) for some
     * 			 string str
     * @return the name of the child of parentName that is connected by the
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getChildByEdgeLabel(String graphName, String parentName,
    								   String edgeLabel) {
    	if(graphName == null || parentName == null || edgeLabel == null){
            System.out.println("one of the labels is null");
            return null;
        }
        return graphs.get(graphName).getChildByEdgeLabel(parentName,edgeLabel);
    }

    
    /**
     * @requires addEdge(graphName, str, childName, edgeLabel) for some
     * 			 string str
     * @return the name of the parent of childName that is connected by the 
     * 		   edge labeled edgeLabel, in the graph graphName.
     */
    public String getParentByEdgeLabel(String graphName, String childName,
    									String edgeLabel) {
        if(graphName == null || childName == null || edgeLabel == null){
            System.out.println("one of the labels is null");
            return null;
        }
        return graphs.get(graphName).getChildByEdgeLabel(childName,edgeLabel);
    	
    	
    }
}
