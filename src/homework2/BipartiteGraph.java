package homework2;
import java.util.*;

/**
 * This class implements a bipartite graph.
 * Each graph has a name and contain list of all his nodes- black & white
 */

public class BipartiteGraph<T> {

	private String graphName;
	private List<Node<T>> blackNodes;
	private List<Node<T>> whiteNodes;

	// Abstraction Function:
	// This object represent bipartite graph:
	// graphName- string unique to the graph
	// blackNodes- list of all black nodes
	// whiteNodes- list of all white nodes
	// Representation invariant for every BipartiteGraph p:
	// all node in p has unique label,
	// all edges in the graph p connect two nodes with different color
	// all edges in p leaving/entering a specific node has a unique name
	//
	//

	/*********************** private methods for internal use **********************/

	/**
	 * @requires nodeList != null; nodeLabel != null
	 * @return the node with the label nodeLabel from list nodeList, null if not found
	 */
	private Node containsNode(List<Node<T>> nodeList, T nodeLabel) {
		for (Node node: nodeList) {
			if (node.getLabel().equals(nodeLabel))
				return node;
		}

		return null;
	}

	/**
	 * @requires graph != null; nodeLabel != null
	 * @return the node with the label nodeLabel from graph, null if not found
	 */
	private Node containsNodeGraph(T nodeLabel) {
		List<Node<T>> blackNode = blackNodes;
		List<Node<T>> whiteNode = whiteNodes;

		for (Node node: blackNode) {
			if (node.getLabel().equals(nodeLabel))
				return node;
		}

		for (Node node: whiteNode) {
			if (node.getLabel().equals(nodeLabel))
				return node;
		}

		return null;
	}

	/**
	 * @requires list != null; label != null
	 * @return true if there is no edge with label - label in the list, false otherwise
	 */
	private boolean checkUniqueEdge(T label, List<Edge<T>> list){
		int i = 0;

		for (Edge edge: list) {
			if (i > 1)
				return false;

			if(edge.getLabel().equals(label))
				i++;
		}

		return true;
	}

	/**
	 * @requires label != null
	 * @return true if there is no node with label - label in the graph, false otherwise
	 */
	private boolean checkUniqueLabel(T label){
		int i = 0;
		int j = 0;
		for (Node node: blackNodes) {
			if (i > 1)
				return false;

			if(node.getLabel().equals(label))
				i++;
		}

		for (Node node: whiteNodes) {
			if (j > 1)
				return false;

			if(node.getLabel().equals(label))
				j++;
		}

		return true;
	}

	/**
	 * @requires edgeList != null; edgeLabel != null
	 * @return return the edge if edge with edge name in list null otherwise
	 */
	private Edge containsEdge(List<Edge<T>> edgeList, T edgeLabel) {
		for (Edge edge: edgeList) {
			if (edge.getLabel().equals(edgeLabel))
				return edge;
		}

		return null;
	}

	/**
	 * @requires parentLabel != null; childLabel != null
	 * @return return true if there is edge between parent with parentLabel
	 * and child with the childLabel, false otherwise
	 */
	private boolean edgeExist(T parentLabel, T childLabel){
		Node parent = containsNodeGraph(parentLabel);
		List<Edge> listEdge = parent.getOutgoingEdges();

		for (Edge edge: listEdge) {
			if (edge.getEndNode().getLabel().equals(childLabel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *  @throws AssertionError if representation invariant is violated.
	 */
	private void checkRep() {

		// for all black nodes check:
		for (Node node: blackNodes) {
			// node has unique label
			assert (checkUniqueLabel((T) node.getLabel()));

			//each edge from incoming edge has unique label, and start node is white
			for (Edge edge: (List<Edge<T>>)node.getIncomingEdges()) {
				assert (checkUniqueEdge((T) edge.getLabel(), node.getIncomingEdges()));
				assert (containsNode(blackNodes, (T) edge.getStartNode().getLabel()) == null);
			}

			//each edge from outgoing edge has unique label, and end node is white
			for (Edge edge: (List<Edge<T>>)node.getOutgoingEdges()) {
				assert(containsNode(blackNodes, (T)edge.getEndNode().getLabel()) == null);
				assert (checkUniqueEdge((T) edge.getLabel(), node.getOutgoingEdges()));
			}
		}

		//for all white black nodes
		for (Node node: whiteNodes) {
			//check node has unique label
			assert (checkUniqueLabel((T) node.getLabel()));
			//each edge from incoming edge has unique label, and start node is black
			for (Edge edge : (List<Edge<T>>) node.getIncomingEdges()) {
				assert (checkUniqueEdge((T) edge.getLabel(), node.getIncomingEdges()));
				assert (containsNode(whiteNodes, (T) edge.getStartNode().getLabel()) == null);
			}

			//each edge from outgoing edge has unique label, and end node is black
			for (Edge edge : (List<Edge<T>>) node.getOutgoingEdges()) {
				assert (containsNode(whiteNodes, (T) edge.getEndNode().getLabel()) == null);
				assert (checkUniqueEdge((T) edge.getLabel(), node.getOutgoingEdges()));
			}
		}
	}

	/**
	 * @requires graphName != null
	 * @modifies this
	 * @effects Constructs a new graph.
	 */
	public BipartiteGraph(String graphName) {

		this.graphName = graphName;
		this.blackNodes = new ArrayList<Node<T>>();
		this.whiteNodes = new ArrayList<Node<T>>();
		checkRep();
	}

	/**
	 * @requires list != null && nodeLabel != null
	 * @return node with label nodeLabel
	 */
	public Node getNodeByLabel(List<Node<T>> list, T nodeLabel){
		checkRep();

		if (nodeLabel == null || list == null){
			checkRep();
			throw new IllegalArgumentException(String.format("can't search for if null node label"));
		}

		Node newNode = containsNode(list , nodeLabel);
		checkRep();
		return newNode;
	}

	/**
	 * @return the black nodes list.
	 */
	public List<Node<T>> getBlackNodes() {

		checkRep();
		return blackNodes;
	}

	/**
	 * @return the white nodes list.
	 */
	public List<Node<T>> getWhiteNodes() {

		checkRep();
		return whiteNodes;
	}

	/**
	 * @return the white nodes list.
	 */
	public Object getNodeObj(T label) {

		checkRep();

		if (label == null){
			throw new IllegalArgumentException(String.format("label is null"));
		}

		Node node = this.containsNodeGraph(label);
		if (node == null){
			throw new UnsupportedOperationException("No node named: "+ label);
		}

		checkRep();
		return node.getObj();
	}

	/**
	 * @requires nodes nodeLabel!=null
	 * neither addBlackNode(nodeName)
	 * nor addWhiteNode(nodeName)
	 * has already been called on this
	 * @modify this
	 */
	public boolean addBlackNode(T nodeLabel, Object obj) {

		checkRep();

		if(nodeLabel == null)
			throw new IllegalArgumentException("Illegal arguments to addBlackNode");
		if (containsNode(this.getBlackNodes(), nodeLabel) != null ||
			containsNode(this.getWhiteNodes(), nodeLabel) != null) {
			throw new UnsupportedOperationException("Black node: " + nodeLabel + " already exist in graph named: " + graphName);
		}

		Node newNode = new Node(nodeLabel, obj);
		blackNodes.add(newNode);

		checkRep();
		return true;
	}

	/**
	 * @requires nodes nodeLabel!=null
	 * neither addBlackNode(nodeName)
	 * nor addWhiteNode(nodeName)
	 * has already been called on this
	 * @modify this
	 */
	public boolean addWhiteNode(T nodeLabel, Object obj) {

		checkRep();

		if(nodeLabel == null)
			throw new IllegalArgumentException("Illegal arguments to addWhiteNode");

		if (containsNode(this.getBlackNodes(), nodeLabel) != null ||
				containsNode(this.getWhiteNodes(), nodeLabel) != null) {
			throw new UnsupportedOperationException("White node: " + nodeLabel + " already exist in graph named: " + graphName);
		}

		Node newNode = new Node(nodeLabel, obj);
		whiteNodes.add(newNode);

		checkRep();
		return true;
	}

	/**
	 * @requires (blackNodes.contain(parentName) && whiteNode.contain(childName)) ||
	 * (whiteNode.contain(parentName) && blackNodes.contain(childName))
	 * && edgeLabel != null
	 * && node named parentName has no other outgoing edge labeled
	 * edgeLabel
	 * && node named childName has no other incoming edge labeled
	 * edgeLabel
	 * @modifies this
	 * @effects Adds an edge from the node parentName to the node childName
	 * in the graph. The new edge's label is the String
	 * edgeLabel.
	 */
	public boolean addEdge(T parentLabel, T childLabel, T edgeLabel) {

		Edge newEdge;
		Node parent, child;
		checkRep();

		// check for requires - label edge not null
		if (edgeLabel == null || childLabel == null || parentLabel == null) {
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));
		}

		// check if parent is black and child is white or otherwise- and get the node
		parent = containsNode(this.getBlackNodes(), parentLabel);
		if (parent != null){
			child = containsNode(this.getWhiteNodes(), childLabel);
			if (child == null){
				throw new UnsupportedOperationException("can't add edge between two node of the same color");
			}
		} else {
			parent = containsNode(this.getWhiteNodes(), parentLabel);
			if (parent == null) {
				throw new UnsupportedOperationException("can't add edge - parent doesn't exist");
			}

			child = containsNode(this.getBlackNodes(), childLabel);
			if (child == null) {
				throw new UnsupportedOperationException("can't add edge - child doesn't exist");
			}
		}

		// check if there is already edge from parent to child
		if (edgeExist(parentLabel, childLabel)) {
			throw new UnsupportedOperationException("can't add edge - edge already exist");
		}

		// check if child/parent doesn't have edge with edgeName
		if ((containsEdge(child.getIncomingEdges(), edgeLabel)) != null ||
				(containsEdge(parent.getOutgoingEdges(), edgeLabel)) != null) {
			throw new UnsupportedOperationException("can't add edge - withe the same label already exist");
		}

		// after checking all parameters- it is safe to add the edge:
		newEdge = new Edge(edgeLabel, parent, child);
		try {
			parent.addOutgoingEdge(newEdge);
			child.addIncomingEdge(newEdge);
			parent.addChildren(child.getLabel());
			child.addParent(parent.getLabel());
		}

		catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("Illegal arguments");
		}
		catch (UnsupportedOperationException exception){
			throw new UnsupportedOperationException(exception);
		}

		checkRep();
		return true;
	}

	/**
	 * @return list of child nodes of the parent
	 * @requires parentName != NULL
	 */
	public List<T> getChildList(T parentLabel) {

		checkRep();
		List<T> childList;

		if (parentLabel == null) {
			checkRep();
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));
		}

		Node parent = containsNodeGraph(parentLabel);

		if (parent == null) {
			checkRep();
			throw new UnsupportedOperationException("There is no node with name: " + parentLabel);
		}

		childList = parent.getChildrenList();
		checkRep();
		return childList;
	}

	/**
	 * @return list of parent nodes of the parent
	 * @requires childNode != NULL
	 */
	public List<T> getParentList(T childLabel) {

		checkRep();
		List<T> parentList;

		if (childLabel == null) {
			checkRep();
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));
		}

		Node child = containsNodeGraph(childLabel);

		if (child == null) {
			checkRep();
			throw new UnsupportedOperationException("There is no node with name: " + childLabel);
		}

		parentList = child.getParentList();
		checkRep();

		return parentList;
	}

	/**
	 * @requires addEdge(graphName, parentName, str, edgeLabel) for some
	 * 			 string str
	 * @return the name of the child of parentName that is connected by the
	 * 		   edge labeled edgeLabel, in the graph graphName.
	 */
	public T getChildByEdgeLabel(T parentLabel,
									  T edgeLabel) {

		checkRep();

		if (parentLabel == null || edgeLabel == null) {
			checkRep();
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));
		}

		// find parent node in graph
		Node parent = containsNodeGraph(parentLabel);
		if (parent == null) {
			checkRep();
			throw new UnsupportedOperationException("parent doesn't exist");
		}

		// find edge from out going edge of parent
		Edge edge = containsEdge(parent.getOutgoingEdges(), edgeLabel);
		if (edge == null) {
			throw new UnsupportedOperationException("Edge doesn't exist");
		}

		return (T)edge.getEndNode().getLabel();
	}


	/**
	 * @requires addEdge(graphName, str, childName, edgeLabel) for some
	 * 			 string str
	 * @return the name of the parent of childName that is connected by the
	 * 		   edge labeled edgeLabel, in the graph graphName.
	 */
	public T getParentByEdgeLabel(T childLabel,
								  T edgeLabel) {
		checkRep();

		if (childLabel == null || edgeLabel == null) {
			checkRep();
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));
		}

		// find parent node in graph

		Node child = containsNodeGraph(childLabel);
		if (child == null) {
			checkRep();
			throw new UnsupportedOperationException("child doesn't exist");
		}

		// find edge from out going edge of parent
		Edge edge = containsEdge(child.getIncomingEdges(), edgeLabel);
		if (edge == null) {
			checkRep();
			throw new UnsupportedOperationException("parent doesn't exist");
		}

		checkRep();
		return (T)edge.getStartNode().getLabel();

	}

	/**
	 * @requires label != null && ContainNode(label)
	 * @return list of all objects of child nodes
	 */
	public List<Object> getChildObj(T label){

		checkRep();
		if (label == null)
			throw new IllegalArgumentException(String.format("trying to pass null arguments"));

		List<Object> listObj = new ArrayList<>();
		List<T> childList = this.getChildList(label);

		if (childList.isEmpty()) {
			checkRep();
			return null;
		}

		for (T child: childList) {
			Object object = this.getNodeObj(child);
			listObj.add(object);
		}

		checkRep();
		return listObj;
	}

	/**
	 * @return list of all objects of black nodes
	 */
	public List<Object> getBlackNodeObj(){
		checkRep();

		List<Object> blackObj = new ArrayList<>();
		for (Node node: blackNodes){
			blackObj.add(node.getObj());
		}

		checkRep();
		return blackObj;
	}

	/**
	 * @return list of all objects of white nodes
	 */
	public List<Object> getWhiteNodeObj(){
		checkRep();

		List<Object> whiteObj = new ArrayList<>();
		for (Node node: whiteNodes){
			whiteObj.add(node.getObj());
		}

		checkRep();
		return whiteObj;
	}
}
