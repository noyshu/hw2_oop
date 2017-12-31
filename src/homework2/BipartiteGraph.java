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
        Iterator<Map.Entry<T,Node<T>>> blackIter = blackNodes.entrySet().iterator();
        while (blackIter.hasNext()){
            Map.Entry<T, Node<T>> entry = blackIter.next();
                assert(!entry.getValue().checkDupLabel());
                assert(!entry.getValue().checkDupParentOrChildNode());
            }
        Iterator<Map.Entry<T,Node<T>>> whiteIter= whiteNodes.entrySet().iterator();
        while (whiteIter.hasNext()){
            Map.Entry<T, Node<T>> entry = whiteIter.next();
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
   public void addBlackNode(T label, Object obj) throws UnsupportedOperationException{
        if(nodeLabelExists(label)){
            throw new UnsupportedOperationException("can't add black node");
        }
        Node<T> node = new Node(label,obj);
        blackNodes.put(label,node);
        checkRep();
   }
    /**
     * @requires label != null
     * @return true if the Node was succefully added to the graph,false if it wasn't
     *
     */
    public boolean addWhiteNode(T label, Object obj) throws UnsupportedOperationException{
        if(nodeLabelExists(label)){
            throw new UnsupportedOperationException("can't add white node");
        }
        Node<T> node = new Node<>(label, obj);
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
    public  void addEdge(T edgeLabel, T parentLabel, T childLabel) throws UnsupportedOperationException{
        checkRep();
       if(childLabel == null || edgeLabel == null || parentLabel == null || parentLabel.equals(childLabel)){
           throw new UnsupportedOperationException("can't add edge");
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
           throw new UnsupportedOperationException("in node label exist");
       }
       if (parentNode.containsOutgoingEdge(edgeLabel) || childNode.containsIngoingEdge(edgeLabel)){
           throw new UnsupportedOperationException("edge is in the graph");
       }
       if (childNode.isParentNode(parentLabel) || parentNode.isChildNode(childLabel)){
           throw new UnsupportedOperationException("nodes are allready connected");
       }

       Edge<T> edge = new Edge(edgeLabel,parentNode,childNode);
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
    public List<Node<T>> getNodeChildren(T nodeLabel) throws  UnsupportedOperationException{
        checkRep();
        if(nodeLabel == null || !nodeLabelExists(nodeLabel)){
            throw new UnsupportedOperationException("node label is null or doesn't exist");
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
            retList.add(iter.next().getEndNode());
        }
        return retList;
    }
    /**
     * @requires (blackNodes.containsKey(nodeLabel) || whiteNode.containsKey(nodeLabel)) ||
     *           nodeLabel != null
     * @returns  null if the node doesn'e exist else returns a list of all the Parents nodes of a node labeld nodeLabel
     *
     * */
    public List<Node<T>> getNodeParents(T nodeLabel) throws UnsupportedOperationException{
        checkRep();
        if(nodeLabel == null || !nodeLabelExists(nodeLabel)){
            throw new UnsupportedOperationException("node label is null or doesn't exist");
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
            retList.add(iter.next().getBeginNode());
        }
        return retList;
    }
    /**
     * @requires addEdge(edgeLabel,parentLabel ,childLable)
     * @return the name of the parent of childName that is connected by the
     * 		   edge labeled edgeLabel, in the graph
     */
    public T getParentByEdgeLabel(T childName, T edgeLabel) throws UnsupportedOperationException{
        if(!nodeLabelExists(childName)){
            throw new UnsupportedOperationException("childName is null or doesn't exist");
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(childName)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        if(!nodes.get(childName).containsIngoingEdge(edgeLabel)) {
            throw new UnsupportedOperationException("child name does not contain this edge");
        }
        return nodes.get(childName).getParentNodeByLabel(edgeLabel).getLabel();
    }
    /**
     * @requires addEdge(edgeLabel,parentName ,childName)
     * @return the name of the child of ParentName that is connected by the
     * 		   edge labeled edgeLabel, in the graph
     */
    /**
     * @requires addEdge(edgeLabel,parentLabel ,childLable)
     * @return the name of the parent of childName that is connected by the
     * 		   edge labeled edgeLabel, in the graph
     */
    public T getChildByEdgeLabel(T parentName, T edgeLabel) throws UnsupportedOperationException{
        if(!nodeLabelExists(parentName)){
            throw new UnsupportedOperationException("parent label is null or doesn't exist");
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(parentName)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        if(!nodes.get(parentName).containsOutgoingEdge(edgeLabel)) {
            throw new UnsupportedOperationException("Parent Label does not have this outGoing Edge");
        }
        return nodes.get(parentName).getChildNodeByLabel(edgeLabel).getLabel();
    }

    /**
     * @requires nodeLabel != null and node names nodeLabel exists in the graph
     * @return the name of the child of ParentName that is connected by the
     * 		   edge labeled edgeLabel, in the graph
     */
    public Node<T> getNode(T nodeLabel) {
        if(!nodeLabelExists(nodeLabel)){
            throw new UnsupportedOperationException("label is null or doesn't exist");
        }
        Map<T, Node<T>> nodes;
        if(blackNodes.containsKey(nodeLabel)){
            nodes = blackNodes;
        }
        else{
            nodes = whiteNodes;
        }
        return nodes.get(nodeLabel);
    }
    /**
     * @requires nodeLabel != null and node names nodeLabel exists in the graph
     * @return the object of the node named nodeLabel
     */
    public Object getNodeObj(T nodeLabel){
        return getNode(nodeLabel).getObject();
    }

    /**
     * @returns list of all  black nodes's objects
     */
    public List<Object> getBlackNodeObj() {
        checkRep();
        return getBlackNodeList().stream().map(u -> u.getObject()).collect(Collectors.toList());
    }
    /**
     * @returns list of all  white nodes's objects
     */
    public List<Object> getWhiteNodeObj() {
        checkRep();
        return getWhiteNodeList().stream().map(u -> u.getObject()).collect(Collectors.toList());
    }
    /**
     * @returns list of all  of the objects that the node named nodeLabel's children nodes hold
     */
    public List<Object> getChildObj(T nodeLabel){
        checkRep();
        return getNodeChildren(nodeLabel).stream().map(u -> u.getObject()).collect(Collectors.toList());
    }
    /**
     * @returns list of all  of the objects that the node named nodeLabel's children nodes hold
     */
    public  List<Object> getParentObj(T nodeLabel){
        checkRep();
        return getNodeParents(nodeLabel).stream().map(u -> u.getObject()).collect(Collectors.toList());
    }
}
