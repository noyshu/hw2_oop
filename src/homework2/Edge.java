package homework2;

/**
 * the edge class is sn abstraction for a directed graph edge with a label of the
 * general type T.
 * it's properties are: label,  beginNode, endNode.
 **/
public class Edge<T> {
    private T label;
    private Node<T>  beginNode;
    private Node<T>  endNode;

    /**
     * Abs. Function: represents a directed graph  edge  with a label a beginning node and end node
     *
     * Rep. Invariant: beginNode != endNode
     *

     * /**
     *
     * @effects Initializes this with a given label.
     */
    public Edge(T Label, Node beginNode, Node endMode){
        this.label = Label;
        this.beginNode = beginNode;
        this.endNode = endMode;
    }

    public T getLabel() {
        return label;
    }
    public Node<T> getBeginNode() {
        return beginNode;
    }
    public Node<T> getEndNode() {
        return endNode;
    }
}
