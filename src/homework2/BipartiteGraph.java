package homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * the BipartiteGraph class is sn abstraction for a directed black and white graph with Nodes and Edges
 * with labels of the general type T.
 * it's properties are: blackNodes, whiteNodes
 **/
public class BipartiteGraph<T>{
    //private ArrayList<Node<T>> blackNodes;
    //private ArrayList<Node<T>> whiteNodes;
    private String name;
    private Map<T,Node<T>> blackNodes;
    private Map<T,Node<T>> whiteNodes;
    /**
 * Abs. Function: represents a directed black and white graph built of Nodes and conected by Edges.
 *
 * Rep. Invariant: every white Node can only have Edges connecting it to blackNodes and viceversa
     * there is no duplication of Node labels in the graph
     * every node can have 0 or 1 edges connectiong it to another node.
     * no duplicate edge labels for edges comming out or going in to the same node
     *edges from white nodes can only go to black nodes and viceversa
 **/
    public void checkRep(){
        Iterator<Map.Entry<T,Node<T>>> iter = blackNodes.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry<T, Node<T>> entry = iter.next();
                assert(!entry.getValue().checkDupLabel());
                assert(!entry.getValue().checkDupParentOrChildNode());
            }
         iter = whiteNodes.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry<T, Node<T>> entry = iter.next();
            assert (!entry.getValue().checkDupLabel());
            assert(!entry.getValue().checkDupParentOrChildNode());
        }
    }
 /**
 * @effects Initializes this
 */
  public BipartiteGraph(String name){
      this.name = name;
      blackNodes = new HashMap<>();
      whiteNodes = new HashMap<>();
    }
    /**
     * @requires label != null
     * @return true if the Node was succefully added to the graph,false if it wasn't
     *
     */
   public boolean addBlackNode(T label){
        if(nodeLabelExists(label)){
            System.out.println("trying to add a black Node, but its label already exists in the graph");
            return false;
        }
        Node<T> node = new Node(label);
        blackNodes.put(label,node);
        checkRep();
        return true;
   }
    /**
     * @requires label != null
     * @return true if the Node was succefully added to the graph,false if it wasn't
     *
     */
    public boolean addWhiteNode(T label){
        if(nodeLabelExists(label)){
            System.out.println("trying to add a white Node, but its label allready exists in the graph");
            return false;
        }
        Node<T> node = new Node<>(label);
        whiteNodes.put(label,node);
        checkRep();
        return true;
    }
    /**
     * @requires label != null
     * @return true if the label already exists in the graph, false if it doesn't
     */
   public boolean nodeLabelExists(T label){
       return (whiteNodes.containsKey(label) || blackNodes.containsKey(label));
   }
    /**
     * @requires (blackNodes.containsKey(parentName) && whiteNode.containsKey(childName)) ||
     *           (whiteNode.contain(parentName) && blackNodes.contain(childName)) &&
     *           edgeLabel != null &&
     *           !parentName.cointainsOutgoingEdge(edgeLable) &&
     *           !childName.cointainsIngoingEdge(edgeLable)
     * @modifies this
     * @effects Adds an edge from the node parentName to the node childName, label it edgeLabel and set its beginNode and endNode accordingly
     */
    public  void addEdge(T edgeLabel, T parentLabel, T childLabel){
        checkRep();
       if(childLabel == null || edgeLabel == null || parentLabel == null || parentLabel.equals(childLabel)){
           return;
       }
       Node<T> childNode, parentNode;
       //find if parent is black and child is white or opposite, if not return
       if(blackNodes.containsKey(parentLabel) && whiteNodes.containsKey(childLabel)){
           parentNode = blackNodes.get(parentLabel);
           childNode = whiteNodes.get(childLabel);
       }
       else if (whiteNodes.containsKey(parentLabel) && blackNodes.containsKey(childLabel)){
           parentNode = whiteNodes.get(parentLabel);
           childNode = blackNodes.get(childLabel);
       }
       else{
           return;
       }
       if (parentNode.cointainsOutgoingEdge(edgeLabel) || childNode.cointainsIngoingEdge(edgeLabel)){
           return;
       }
       Edge<T> edge = new Edge(edgeLabel,childNode,parentNode);
       parentNode.addOutgoingEdge(edge);
       childNode.addIngoingEdge(edge);
       checkRep();
    }
    /**
     * @returns whiteNodes as a List;
     */
    public List<Node<T>> getWhiteNodeList(){
        checkRep();
        return new ArrayList<>(whiteNodes.values());
    }
    /**
     * @returns blackNodes as a List;
     */
    public List<Node<T>> getBlackNodeList(){
        checkRep();
        return new ArrayList<>(blackNodes.values());
    }
    /**
     * @requires (blackNodes.containsKey(nodeLabel) || whiteNode.containsKey(nodeLabel)) ||
     *           nodeLabel != null
     * @returns  null if the node doesn'e exist else returns a list of all the child nodes of a node labeld nodeLabel
     *
     * */
    public List<Node<T>> getNodeChildren(T nodeLabel){
        checkRep();
        if(nodeLabel == null || !nodeLabelExists(nodeLabel)){
            return null;
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(nodeLabel)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        List<Node<T>> retList = new ArrayList<>();
        Node curNode = nodes.get(nodeLabel);
        Iterator<Edge> iter = curNode.getOutgoingEdges().iterator();
        while (iter.hasNext()){
            retList.add(nodes.get(iter.next().getEndNode()));
        }
        return retList;
    }
    /**
     * @requires (blackNodes.containsKey(nodeLabel) || whiteNode.containsKey(nodeLabel)) ||
     *           nodeLabel != null
     * @returns  null if the node doesn'e exist else returns a list of all the Parents nodes of a node labeld nodeLabel
     *
     * */
    public List<Node<T>> getNodeParents(T nodeLabel){
        checkRep();
        if(nodeLabel == null || !nodeLabelExists(nodeLabel)){
            return null;
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(nodeLabel)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        List<Node<T>> retList = new ArrayList<>();
        Node curNode = nodes.get(nodeLabel);
        Iterator<Edge> iter = curNode.getIngoingEdges().iterator();
        while (iter.hasNext()){
            retList.add(nodes.get(iter.next().getBeginNode()));
        }
        return retList;
    }
    /**
     * @requires addEdge(edgeLabel,parentLabel ,childLable)
     * @return the name of the parent of childName that is connected by the
     * 		   edge labeled edgeLabel, in the graph
     */
    public T getParentByEdgeLabel(T childName, T edgeLabel) {
        if(!nodeLabelExists(childName)){
            System.out.println("child Label does not exist in the graph");
            return null;
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(childName)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        if(!nodes.get(childName).cointainsIngoingEdge(edgeLabel)) {
            System.out.println("child Label does not have this outGoing Edge");
            return null;
        }
        return nodes.get(childName).getParentNodeByLabel(edgeLabel).getLabel();
    }


}
