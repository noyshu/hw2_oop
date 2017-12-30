package homework2;
import java.util.*;
import java.util.stream.Collectors;



/**
 * the node class is sn abstraction for a directed graph node with a label of the
 * general type T.
 * it's properties are: label, ingoingEdges, outgoingEdges.
 **/
public class Node<T> {
    private T label;
    private Object object;
    private ArrayList<Edge<T>> ingoingEdges;
    private ArrayList<Edge<T>> outgoingEdges;
    /**
     * Abs. Function: represents a directed graph node with a label and lists on in going edges and out going edges
     *
     * Rep. Invariant: for every i != j ingoingEdges[i] != ingoingEdges[j], outgoingEdges[i] != outgoingEdges[j]
     *


     * /**
     *
     * @effects Initializes this with a given label.
     */
    public Node(T label, Object obj){
        this.label = label;
        object = obj;
        ingoingEdges = new ArrayList<>();
        outgoingEdges = new  ArrayList<>();
    }
     /**
     *
     * @returns a node's object
     */
    public Object getObject() {
        return object;
    }
    /**
     *@requiers edgeLable != Null
     * @returns true if an edge with the same label exists in this list and false otherwise
     */

    public boolean containsOutgoingEdge(T edgeLabel){
        Iterator<Edge<T>> iter = outgoingEdges.iterator();
        while (iter.hasNext()){
            if (iter.next().getLabel().equals(edgeLabel)){
                return true;
            }
        }
        return false;
    }
    /**
     *@requiers edgeLable != Null
     * @returns true if an edge with the same label exists in this list and false otherwise
     */
    public boolean containsIngoingEdge (T edgeLabel){
        Iterator<Edge<T>> iter = ingoingEdges.iterator();
        while (iter.hasNext()){
            if (iter.next().getLabel().equals(edgeLabel)){
                return true;
            }
        }
        return false;
    }
    /**
     *@requiers edge != Null && cointainsOutgoingEdge(edge.getLable) == false
     * @moddifies adds edge to outgoingEdges
     */
    public void addOutgoingEdge(Edge<T> edge){
        if(edge == null || containsOutgoingEdge(edge.getLabel())){
            return;
        }
        if(!edge.getBeginNode().getLabel().equals(getLabel())){
            System.out.println("trying to add edge with wrong begin node");
            return;
        }
        outgoingEdges.add(edge);
    }
    /**
     *@requiers edge != Null && cointainsIngoingEdge(edge.getLable) == false
     * @moddifies adds edge to  ingoingEdges
     */
    public void addIngoingEdge(Edge<T> edge){
        if(edge == null || containsIngoingEdge(edge.getLabel())){
            return;
        }
        if(!edge.getEndNode().getLabel().equals(getLabel())){
            System.out.println("trying to add edge with wrong begin node");
            return;
        }
        ingoingEdges.add(edge);
    }
    /**
     *@returns ingoingEdges
     */
    public ArrayList<Edge<T>> getIngoingEdges() {
        return ingoingEdges;
    }
    /**
     *@returns outgoingEdges
     */
    public ArrayList<Edge<T>> getOutgoingEdges() {
        return outgoingEdges;
    }
    /**
     *@returns a Parent node of this node connected by edge labled edgeLabel or null if doesn't exists
     */
    public Node<T> getParentNodeByLabel(T edgeLable){
        Iterator<Edge<T>> iter = ingoingEdges.iterator();
        while (iter.hasNext()){
            Edge tempEdge =  iter.next();
            if (tempEdge.getLabel().equals(edgeLable)){
                return tempEdge.getBeginNode();
            }
        }
        return null;
    }
    /**
     *@returns a Child node of this node connected by edge labled edgeLabel or null if doesn't exists
     */
    public Node<T> getChildNodeByLabel(T edgeLable){
        Iterator<Edge<T>> iter = outgoingEdges.iterator();
        while (iter.hasNext()){
            Edge tempEdge =  iter.next();
            if (tempEdge.getLabel().equals(edgeLable)){
                return tempEdge.getEndNode();
            }
        }
        return null;
    }
    /**
     *@returns label
     */
    public T getLabel() {
        return label;
    }

    /**
     * @modifies makes the edges in ingoing edges and outgoing edges unique
     *@returns true if there are multiple outgoing or ingoing edges with the same label
     */
    public boolean checkDupLabel(){
        int size = ingoingEdges.size();
        Set<Edge<T>> hs = new HashSet<>();
        hs.addAll(ingoingEdges);
        ingoingEdges.clear();
        ingoingEdges.addAll(hs);
            if(size != ingoingEdges.size()){
                System.out.println("duplicate ingoing Edge label");
                return true;
            }
        size = outgoingEdges.size();
            hs.clear();
        hs.addAll(outgoingEdges);
        outgoingEdges.clear();
        outgoingEdges.addAll(hs);
        if(size != outgoingEdges.size()){
            System.out.println("duplicate outgoing Edge label");
            return true;
        }
        return false;
    }
    /**
     * @modifies makes the edges in ingoing edges and outgoing edges unique
     *@returns true if there are multiple outgoing or ingoing edges going to of from the same label
     */
    public boolean checkDupParentOrChildNode(){
        List<T> outLabels = outgoingEdges.stream().map(u -> u.getEndNode().getLabel()).collect(Collectors.toList());
        List<T> inLabels = ingoingEdges.stream().map(u -> u.getBeginNode().getLabel()).collect(Collectors.toList());
        int size = inLabels.size();
        Set<T> hs = new HashSet<>();
        hs.addAll(inLabels);
        inLabels.clear();
        inLabels.addAll(hs);
        if(size != inLabels.size()){
            System.out.println("duplicate Edge to the same node ");
            return true;
        }
        size = outLabels.size();
        hs.clear();
        hs.addAll(outLabels);
        outLabels.clear();
        outLabels.addAll(hs);
        if(size != outLabels.size()){
            System.out.println("duplicate Edge from the same node ");
            return true;
        }
        return false;
    }
}
