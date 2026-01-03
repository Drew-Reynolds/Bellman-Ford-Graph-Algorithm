//TODO:
//  (1) Implement the graph!
//  (2) Update this code to meet the style and JavaDoc requirements.
//			Why? So that you get experience with the code for a graph!
//			Also, this happens a lot in industry (updating old code
//			to meet your new standards).

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.DirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator; 

/**
 * Backend of a graph that can be manipulated.
 */
class FlexGraph implements Graph<GraphNode,GraphEdge>, DirectedGraph<GraphNode,GraphEdge> {
	//HINTS:
	//1 -- Learn about what methods are available in Java's LinkedList
	//	 class before trying this, a lot of them will come in handy...
	//2 -- You may want to become friendly with the ListIterator as well. 
	//	 This iterator support things beyond Iterator, e.g. removal...

	
	//you may not have any other class variables, only this one
	//if you make more class variables your graph class will receive a 0,
	//no matter how well it works
	/** Max number of nodes in the FlexGraph. */
	private static final int MAX_NUMBER_OF_NODES = 200;

	//you may not have any other instance variables, only this one
	//if you make more instance variables your graph class will receive a 0,
	//no matter how well it works
	/** List repersentation of the FlegGraph. */
	private LinkedList<Connection>[] adjList = null;
	
	
	//a (destination,edge) to store in the adjacency list
	//note: source is indicated by the first node of each list
	//you may not edit this inner private class	
	/**
	 * Connection of a node and edge. The node is the edge's destination.
	 */
	private class Connection {
		/** Destination of the connection. */
		GraphNode node;
		/** Connector of the connection. */
		GraphEdge edge;
		Connection(GraphNode n, GraphEdge e) { this.node = n; this.edge = e; }
		
	}
	
	/**
	 * This is the only allowed constructor.
	 */
	@SuppressWarnings("unchecked")
	public FlexGraph() {
		//reminder: you can NOT do this: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new Object[10];
		//you must use this format instead: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new ClassWithGeneric[10];

		adjList = (LinkedList<Connection>[]) new LinkedList[MAX_NUMBER_OF_NODES];
	}	


	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the edges within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<GraphEdge> getEdges() {
		//Hint: this method returns a Collection, look at the imports for
		//what collections you could return here.

		//O(n+e) where e is the number of edges in the graph and 
		//n is the max number of nodes in the graph
						
		
		Collection<GraphEdge> edgeList = new LinkedList<>();

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.edge != null) {
					edgeList.add(connection.edge);
				}
			}
		}

		return edgeList;
	}
	
	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<GraphNode> getVertices() {
		//Hint: this method returns a Collection, look at the imports for
		//what collections you could return here.

		//O(n) where n is the max number of nodes.
						
		Collection<GraphNode> nodeList = new LinkedList<>();

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null || currList.element() == null) {
				continue;
			}
			nodeList.add(currList.element().node);
		}

		return nodeList;
		
	}
	
	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		//O(n) where n is the max number of nodes in the graph
		//note: this is NOT O(n+e), just O(n)
		
		//Note: this runtime is not a mistake, think about how
		//you could find out the number of edges *without*
		//looking at each one
		
		int count = 0;

		for (LinkedList<Connection> currList: adjList) {
			if (currList != null) {
				count += currList.size() - 1;
			}
		}

		return count;
	}

	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		//O(n) where n is the max number of nodes in the graph

		int count = 0;
				
		for (LinkedList<Connection> currList: adjList) {
			if (currList != null) {
				count++;
			}
		}

		return count;
	}


	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(GraphNode vertex) {
		//O(1) -- NOT O(n)!
		
		//Note: this runtime is not a mistake, look at
		//the storage overview in the project description for ideas
		
		if (vertex != null && vertex.id < MAX_NUMBER_OF_NODES && vertex.id >= 0 && adjList[vertex.id] != null) {
			return true;
		}

		return false;
		
	}
	 
	
	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose incoming edges are to be returned
	 * @return  a Collection view of the incoming edges incident 
	 to vertex in this graph
	 */
	public Collection<GraphEdge> getInEdges(GraphNode vertex) {
		//if vertex not present in graph, return null

		//O(n+e) where e is the number of edges in the graph and 
		//n is the max number of nodes in the graph
				
		if (!containsVertex(vertex)) {
			return null;
		}

		Collection<GraphEdge> edgeList = new LinkedList<>();

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.node.equals(vertex) && connection.edge != null) {
					edgeList.add(connection.edge);
				}
			}
		}

		return edgeList;
		
	}
	
	/**
	 * Gets the outgoing edges of the given vertex.
	 * @param vertex Given vertex
	 * @return Collection of outgoing edges of the vertex
	 */
	public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
		//if vertex not present in graph, return null

		//O(e) where e is the number of edges in the graph

		if (!containsVertex(vertex)) {
			return null;
		}
		
		Collection<GraphEdge> edgeList = new LinkedList<>();

		for (Connection connection: adjList[vertex.id]) {
			if (connection.edge != null) {
				edgeList.add(connection.edge);
			}
		}

		return edgeList;

	}

	/**
	 * Returns the number of incoming edges incident to vertex.
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(GraphNode vertex) {
		//if vertex not present in graph, return -1
		
		//O(n+e) where e is the number of edges in the graph and 
		//n is the max number of nodes in the graph

		Collection<GraphEdge> inEdgeList = getInEdges(vertex);

		if (inEdgeList == null) {
			return -1;
		}

		return inEdgeList.size();

	}
	
	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(GraphNode vertex) {
		//if vertex not present in graph, return -1
		//O(1) 

		if (!containsVertex(vertex)) {
			return -1;
		}

		return adjList[vertex.id].size() - 1 ;
	}


	/**
	 * Gets the predecessor nodes of the given vertex.
	 * @param vertex Given vertex
	 * @return Collection of Predecessor nodes
	 */
	public Collection<GraphNode> getPredecessors(GraphNode vertex) {
		//if vertex not present in graph, return null

		//O(n+e) where e is the number of edges in the graph and 
		//n is the max number of nodes in the graph

		if (!containsVertex(vertex)) {
			return null;
		}

		Collection<GraphNode> preList = new LinkedList<>();
		
		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.node.equals(vertex) && connection.edge != null) {
					preList.add(currList.element().node);
				}
			}
		}

		return preList;
		
	}
	
	/**
	 * Gets the successors of the given vertex.
	 * @param vertex Given vertex
	 * @return Collection of successor nodes
	 */
	public Collection<GraphNode> getSuccessors(GraphNode vertex) {
		//if vertex not present in graph, return null
		//O(e) where e is the number of edges in the graph

		if (!containsVertex(vertex)) {
			return null;
		}
		
		Collection<GraphNode> sucList = new LinkedList<>();

		for (Connection connection: adjList[vertex.id]) {
			if (connection.edge != null) {
				sucList.add(connection.node);
			}
		}

		return sucList;
		
	}
	
	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(GraphNode v1, GraphNode v2) {
		//O(e) where e is the number of edges in the graph

		if (v1 == null || v2 == null) {
			return false;
		}

		Collection<GraphNode> sucList = getSuccessors(v1);
		
		if (sucList == null) {
			return false;
		}
		
		return sucList.contains(v2);
		
	}
	
	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(GraphNode v1, GraphNode v2) {
		//O(e) where e is the number of edges in the graph

		if (v1 == null || v2 == null) {
			return false;
		}
		
		Collection<GraphNode> sucList = getSuccessors(v2);

		if (sucList == null) {
			return false;
		}
		
		return sucList.contains(v1);

	}
	
	/**
	 * Gets the neighboring vertecies of the given vertex.
	 * @param vertex Given vertx
	 * @return Collection of neighboring vertecies
	 */
	public Collection<GraphNode> getNeighbors(GraphNode vertex) {
		//O(n^2) where n is the max number of vertices in the graph
		
		//NOTE: there should be no duplicates in the neighbor list.

		Collection<GraphNode> neighborList = getSuccessors(vertex);
		Collection<GraphNode> preList = getPredecessors(vertex);

		if (preList == null || neighborList == null) {
			return null;
		}

		for (GraphNode node: preList) {
			if (!neighborList.contains(node)) {
				neighborList.add(node);
			}
		}

		return neighborList;

	}


	/**
	 * If directed_edge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge given edge
	 * @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public GraphNode getSource(GraphEdge directedEdge) {
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph

		if (directedEdge == null || !getEdgeType(directedEdge).equals(EdgeType.DIRECTED)) {
			return null;
		}

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.edge != null && connection.edge.equals(directedEdge)) {
					return currList.element().node;
				}
			}
		}

		return null;

	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directed_edge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge Given edge
	 * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public GraphNode getDest(GraphEdge directedEdge) {
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
		
		if (directedEdge == null || !getEdgeType(directedEdge).equals(EdgeType.DIRECTED)) {
			return null;
		}

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.edge != null && connection.edge.equals(directedEdge)) {
					return connection.node;
				}
			}
		}

		return null; 	

	}
  
	
	/**
	 * Finds the edge connecting the given nodes.
	 * @param v1 First node (Source)
	 * @param v2 Second node (Destination)
	 * @return The edge connectin the two, null if unable
	 */
	public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
		//O(e) where e is the number of edges in the graph
		
		if (!containsVertex(v1) || !containsVertex(v2)) {
			return null;
		}

		for (Connection connection: adjList[v1.id]) {
			if (connection.node.equals(v2)) {
				return connection.edge;
			}
		}
		for (Connection connection: adjList[v2.id]) {
			if (connection.node.equals(v1)) {
				return connection.edge;
			}
		}

		return null;
		
	}

	/**
	 * Sees if a vertex and edge are connected.
	 * @param vertex The given vertx
	 * @param edge The given edge
	 * @return true if incident, false otherwise
	 */
	public boolean isIncident(GraphNode vertex, GraphEdge edge) {
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			for (Connection connection: currList) {
				if (connection.edge != null && connection.edge.equals(edge) && ( connection.node.equals(vertex) || currList.element().node.equals(vertex) )) {
					return true;
				}
			}
		}

		return false;
		
	}



	/**
	 * Adds an edge to the graph.
	 * @param e Egde to be added
	 * @param v1 First node to be connected
	 * @param v2 Second node to be connected
	 * @return true if added, false otherwise
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
		//remember we do not allow self-loops nor parallel edges
		
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
		
		if (e == null || v1 == null || v2 == null || !containsVertex(v1) || !containsVertex(v2) || v1.equals(v2) || containsEdge(e) || isPredecessor(v1, v2)) {
			throw new IllegalArgumentException();
		}

		adjList[v1.id].add(new Connection(v2, e));

		return true;
 
	}
	
	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(GraphNode vertex) {
		//O(1) 
		
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		
		if (vertex.id >= MAX_NUMBER_OF_NODES || vertex.id < 0 || adjList[vertex.id] != null) {
			return false;
		}

		LinkedList<Connection> newList = new LinkedList<>();
		newList.add(new Connection(vertex, null));
		adjList[vertex.id] = newList;

		return true;

	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(GraphEdge edge) {
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph

		if (edge == null) {
			return false;
		}
		
		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			int i = 0;
			int indexOfEdge = -1;
			for (Connection connection: currList) {
				if (connection.edge != null && connection.edge.equals(edge)) {
					indexOfEdge = i;
				}
				i++;
			}
			if (indexOfEdge > 0) {
				currList.remove(indexOfEdge);
				return true;
			}
		}

		return false;

	}
	
	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.) 
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(GraphNode vertex) {
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
		
		if (vertex.id >= MAX_NUMBER_OF_NODES || adjList[vertex.id] == null) {
			return false;
		}

		adjList[vertex.id] = null;

		for (LinkedList<Connection> currList: adjList) {
			if (currList == null) {
				continue;
			}
			int i = 0;
			int indexOfVertex = -1;
			for (Connection connection: currList) {
				if (connection.node.equals(vertex)) {
					indexOfVertex = i;
				}
				i++;
			}
			if (indexOfVertex > 0) {
				currList.remove(indexOfVertex);
			}
		}

		return true;
				
	}
	


	
	/**
	 * Reads every node that's reachable from the given node as a String.
	 * @param vertex Given node
	 * @return String of the ids of every reachable node, seperated by spaces
	 */
	public String depthFirstTraversal(GraphNode vertex){
	
		//Hint: feel free to define private helper method
		//Use StringBuilder!
		
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph

		if (vertex == null || !containsVertex(vertex)) {
			return "";
		}

		// Stop-gap to prevent duplicates
		LinkedList<GraphNode> usedNodes = new LinkedList<>();
		usedNodes.add(vertex);

		// Create a LinkedList of vertex's successors
		LinkedList<Connection> availableConnections = new LinkedList<>();
		for (Connection successor: adjList[vertex.id]) {
			if (successor.edge != null && !usedNodes.contains(successor.node)) {
				availableConnections.add(successor);
			}
		}

		// Create the string
		String string = vertex.id + " ";
		while (availableConnections.size() > 0) {

			// Search for the next path to go down
			Connection nextPath = availableConnections.element();
			for (Connection connection: availableConnections) {
				if (connection.edge.id < nextPath.edge.id) {
					nextPath = connection;
				}
			}

			// Remove the next path
			availableConnections.remove(nextPath);
			usedNodes.add(nextPath.node);

			// Add the next path's successors
			for (Connection successor: adjList[nextPath.node.id]) {
				if (successor.edge != null && !usedNodes.contains(successor.node)) {
					availableConnections.add(successor);
				}
			}

			// Add to the string
			string += nextPath.node.id + " ";
		}

		// Chop off the hanging " "
		return string.substring(0, string.length() - 1);
		
	}


	/**
	 * Counts the how many nodes are reachable from and to the given vertex.
	 * @param vertex Given vertex
	 * @return (Reachable from the vertex, Reachable to the vertex)
	 */
	public IntPair countReachable(GraphNode vertex){
		//O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
		
		//Note: big-O is not O(n(n+e))
		//Re-check textbook for graph traversals if you need more hints ...

		if (vertex == null || !containsVertex(vertex)) {
			return null;
		}

		// Get the first number, aka the number of nodes reachable from vertex
		int first = 0;

		// Stop-gap to prevent duplicates
		LinkedList<GraphNode> usedNodes = new LinkedList<>();
		usedNodes.add(vertex);

		// Create a LinkedList of vertex's successors
		LinkedList<Connection> availableConnections = new LinkedList<>();
		for (Connection successor: adjList[vertex.id]) {
			if (successor.edge != null && !usedNodes.contains(successor.node)) {
				availableConnections.add(successor);
			}
		}

		// Counting all the nodes
		while (availableConnections.size() > 0) {

			// Search for the next path to go down
			Connection nextPath = availableConnections.element();
			for (Connection connection: availableConnections) {
				if (connection.edge.id < nextPath.edge.id) {
					nextPath = connection;
				}
			}

			// Remove the next path
			availableConnections.remove(nextPath);
			usedNodes.add(nextPath.node);

			// Add the next path's successors
			for (Connection successor: adjList[nextPath.node.id]) {
				if (successor.edge != null && !usedNodes.contains(successor.node)) {
					availableConnections.add(successor);
				}
			}
			
			// Add to the count
			first++;

		}

		// Reachable to vertex
		
		return new IntPair(first, 0);
	}
	
		
	
	//********************************************************************************
	//   testing code goes here... edit this as much as you want!
	//********************************************************************************
	
	/**
	 * Main method.
	 * @param args cmd arguments
	 */
	public static void main(String[] args) {
		//create a set of nodes and edges to test with
		GraphNode[] nodes = {
			new GraphNode(0), 
			new GraphNode(1), 
			new GraphNode(2), 
			new GraphNode(3), 
			new GraphNode(4), 
			new GraphNode(5), 
			new GraphNode(6), 
			new GraphNode(7), 
			new GraphNode(8), 
			new GraphNode(9)
		};
		
		GraphEdge[] edges = {
			new GraphEdge(0), 
			new GraphEdge(1), 
			new GraphEdge(2),
			new GraphEdge(3), 
			new GraphEdge(4), 
			new GraphEdge(5),
			new GraphEdge(6),
			new GraphEdge(7)
		};
		
		//constructs a graph
		FlexGraph graph = new FlexGraph();
		for(GraphNode n : nodes) {
			graph.addVertex(n);
		}
		
		graph.addEdge(edges[0],nodes[0],nodes[1]);
		graph.addEdge(edges[1],nodes[1],nodes[2]); //1-->2
		graph.addEdge(edges[2],nodes[3],nodes[6]);
		graph.addEdge(edges[3],nodes[6],nodes[7]);
		graph.addEdge(edges[4],nodes[8],nodes[9]);
		graph.addEdge(edges[5],nodes[9],nodes[0]);
		graph.addEdge(edges[6],nodes[2],nodes[7]);
		graph.addEdge(edges[7],nodes[1],nodes[8]); //1-->8		
		
		if(graph.getVertexCount() == 10 && graph.getEdgeCount() == 8) {
			System.out.println("Yay 1");
		}
		
		if(graph.inDegree(nodes[0]) == 1 && graph.outDegree(nodes[1]) == 2) {
			System.out.println("Yay 2");
		}
		
		//lots more testing here...
		//If your graph "looks funny" you probably want to check:
		//getVertexCount(), getVertices(), getInEdges(vertex),
		//and getIncidentVertices(incomingEdge) first. These are
		//used by the layout class.


		//some testing for the advanced graph operations:

		if(graph.depthFirstTraversal(nodes[9]).trim().equals("9 0 1 2 7 8")) {
			System.out.println("Yay 3");
		}
		
		//NOTE: in traversal, after node 1, we visited node 2 before node 8
		//	  since edge 1-->2 was added into graph before edge 1-->8
		
		//System.out.println(graph.depthFirstTraversal(nodes[9]));

		IntPair counts = graph.countReachable(nodes[1]);
		if (counts.getFirst() == 5 && counts.getSecond() == 3){
			System.out.println("Yay 4");
		}
		//System.out.println(graph.countReachable(nodes[0]));
		
		//again, many more testing by yourself...
		
	}
	

	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	//   NOTE: you do need to fix JavaDoc issues if there is any in this section.
	//********************************************************************************



	/**
	 * Gets the degree of the vertex.
	 * @param vertex Given vertex
	 * @return The vertex's degree
	 */
	public int degree(GraphNode vertex) {
		return inDegree(vertex) + outDegree(vertex);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(GraphNode v1, GraphNode v2) {
		return (findEdge(v1, v2) != null || findEdge(v2, v1)!=null);
	}
	
	/**
	 * Get's the nodes connected to an edge.
	 * @param edge Given edge
	 * @return Pair of nodes connected to the edge
	 */
	public Pair<GraphNode> getEndpoints(GraphEdge edge) {
		if (edge==null) return null;
		
		GraphNode v1 = getSource(edge);
		if (v1==null)
			return null;
			
		GraphNode v2 = getDest(edge);
		if (v2==null)
			return null;
			
		return new Pair<GraphNode>(v1, v2);
	}


	/**
	 * gets the edges connected to the given node.
	 * @param vertex The given node
	 * @return Collection of edges connected to the node
	 */
	public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.addAll(getInEdges(vertex));
		ret.addAll(getOutEdges(vertex));
		return ret;
	}
	
	/**
	 * Gets the nodes connected to an edge.
	 * @param edge Given edge
	 * @return All nodes connected to said edge
	 */
	public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		LinkedList<GraphNode> ret = new LinkedList<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}


	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}
	
	/**
	 * Gets all edges of a edgeType.
	 * @param edgeType The type of edge
	 * @return Collection of all adges with said type
	 */
	public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of edges of type edge_type in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edge_type in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(GraphNode vertex) {
		return inDegree(vertex);
	}
	
	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(GraphNode vertex) {
		return outDegree(vertex);
	}
	
	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(GraphNode vertex) {		
		if (!containsVertex(vertex))
			return -1;

		return getNeighbors(vertex).size();
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		if (getSource(edge).equals(vertex)){
			return getDest(edge);
		}
		else if (getDest(edge).equals(vertex)){
			return getSource(edge);
		}
		else
			return null;			
	}
	
	/**
	 * Finds all edges connecting the nodes.
	 * @param v1 First node
	 * @param v2 Second node
	 * @return Collection of all edges connecting the nodes
	 */
	public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(GraphNode vertex, GraphEdge edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(GraphNode vertex, GraphEdge edge) {
		return getDest(edge).equals(vertex);
	}
	
	/**
	 * Adds an edge to the graph.
	 * @param e Edge to be added
	 * @param v1 First node to be connected
	 * @param v2 Second node to be connected
	 * @param edgeType EdgeType of the edge
	 * @return true if added, false otherwise
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
	
	/**
	 * Adds an edge to the graph.
	 * @param edge Edge to be created
	 * @param vertices Collection of vertices to be connected
	 * @return true if added, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Add's an edge to the graph.
	 * @param edge Edge to be created
	 * @param vertices Collection of vertices to be connected
	 * @param edgeType The type of edge
	 * @return True if added, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
	//********************************************************************************
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> Unused generic
	 * @param <E> Unused generic
	 * @return New FlexGraph
	 */
	 
	public static <V,E> Factory<Graph<GraphNode,GraphEdge>> getFactory() { 
		return new Factory<Graph<GraphNode,GraphEdge>> () {
			@SuppressWarnings("unchecked")
			public Graph<GraphNode,GraphEdge> create() {
				return (Graph<GraphNode,GraphEdge>) new FlexGraph();
			}
		};
	}
	


	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge Given edge
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). 
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(GraphEdge edge) {
		return 2;
	}
	


}
