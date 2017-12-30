package homework2;
import java.util.*;

/**
 * This class implements a node for bipartite graph, each node contains
 * list of outgoing & incoming edges, label, list of parents & children,
 * and obj that will define the node type.
 */
public class Node<T> {


    private T label;
    private Object obj;
    private List<Edge<T>> outgoingEdges;
    private List<Edge<T>> incomingEdges;
    private List<T> childrenList;
    private List<T> parentList;

    /**
     * @modifies this
     * @effects Constructs a new node.
     */
    public Node (T label, Object obj) {
        this.label = label;
        this.obj = obj;
        this.outgoingEdges = new ArrayList<>();
        this.incomingEdges = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.parentList = new ArrayList<>();
    }

    /**
     * @return the label of this
     */
    public T getLabel() {
        return label;
    }

    /**
     * @return the object of this
     */
    public Object getObj() {
        return obj;
    }

    /**
     * @return the list of the Incoming edges of the node
     */
    public List<Edge<T>> getIncomingEdges() {
        List<Edge<T>> cpyList = new ArrayList<>(this.incomingEdges);
        return cpyList;
    }

    /**
     * @return the list of the edges leaving the node
     */
    public List<Edge<T>> getOutgoingEdges() {
        List<Edge<T>> cpyList = new ArrayList<>(this.outgoingEdges);
        return cpyList;
    }

    /**
     * @requires label != null
     * @modifies this
     * @effects set this label
     */
    public void setLabel(T label) {
        if(label == null)
            throw new IllegalArgumentException(String.format("Can't set label to null"));
        this.label = label;
    }

    /**
     * @requires parentLabel != null
     * @modifies this
     * @effects add new parent to parent label list
     */
    public void addParent(T parentLabel){
        if(parentLabel ==null)
            throw new IllegalArgumentException(String.format("Can't add parent with null label"));
        this.parentList.add(parentLabel);
    }

    /**
     * @requires childLabel != null
     * @modifies this
     * @effects add new child to child label list
     */
    public void addChildren(T childLabel){
        if(childLabel ==null)
            throw new IllegalArgumentException(String.format("Can't add child with null label"));
        this.childrenList.add(childLabel);
    }

    /**
     * @return list of parent label
     */
    public List<T> getParentList() {
        return Collections.unmodifiableList(parentList);
    }

    /**
     * @return list of children label
     */
    public List<T> getChildrenList() {
        return Collections.unmodifiableList(childrenList);
    }

    /**
     * @requires newEdge != null
     * @modifies this
     * @effects add new edge incoming edge to the node
     */
    public void addIncomingEdge(Edge<T> newEdge){
        if (newEdge == null)
            throw new IllegalArgumentException(String.format("can't add null edge to incoming edge list"));
    	for (int i=0;i<incomingEdges.size();i++) {
        	if(incomingEdges.get(i).getLabel().equals(newEdge.getLabel())) {
        		throw new UnsupportedOperationException("Incoming edge with label: " + newEdge.getLabel() + " Already exist");
        	}        		
        }
    	this.incomingEdges.add(newEdge);
    }

    /**
     * @requires newEdge != null
     * @modifies this
     * @effects add new edge leaving the node
     */
    public void addOutgoingEdge(Edge<T> newEdge){
        if (newEdge == null)
            throw new IllegalArgumentException(String.format("can't add null edge to outgoing edge list"));
        for (int i=0;i<outgoingEdges.size();i++) {
            if(outgoingEdges.get(i).getLabel().equals(newEdge.getLabel())) {
                throw new UnsupportedOperationException("Outgoing edge with label: "+newEdge.getLabel()+"Already exist");
            }
        }
        this.outgoingEdges.add(newEdge);
    }
}
