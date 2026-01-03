//TODO: Nothing, all done.

import org.apache.commons.collections15.Factory;

import java.awt.Color;

/**
 *  A node representation for the graph simulation.
 *  
 *  @author Katherine (Raven) Russell
 *  @author Y. Zhong (Updated Spring 24)
 */
class GraphNode extends GraphComp {
	/**
	 *  The number of nodes created so far (for gerating unique ids
	 *  from the factory method).
	 */
	public static int nodeCount = 0;
	
	/**
	 *  The text on this node.
	 */
	private String text;
	
	/**
	 *  Fetches the text of the node.
	 *  
	 *  @return the text of the node
	 */
	public String getText() { return text; }
	
	/**
	 *  Sets the text of the node.
	 *  
	 *  @param text the text of the node
	 */
	public void setText(String text) { this.text = text; }
		
	/**
	 *  The integer cost associated with each node.
	 *  Used to support certain graph algorithms.
	 */
	private int cost;
	
	/**
	 *  Report the cost of this node.
	 *  
	 *  @return the cost of this node
	 */
	public int getCost() { return this.cost; }

	/**
	 *  Set the cost of this node to be the incoming value.
	 *  
	 *  @param cost the cost to set for this node
	 */
	public void setCost(int cost) { this.cost = cost; }

	/**
	 *  The node associated with this node as parent / predecessor.
	 *  Used to support certain graph algorithms.
	 */
	private GraphNode parent;
	
	/**
	 *  Report the parent of this node.
	 *  
	 *  @return the parent of this node
	 */	
	public GraphNode getParent(){ return this.parent; }

	/**
	 *  Set the parent of this node to be the given node.
	 *  
	 *  @param parent the parent to set for this node
	 */
	public void setParent(GraphNode parent){ this.parent = parent;}

	/**
	 *  Constructs a node with a given id.
	 *  
	 *  @param id the unique id of the node
	 */
	public GraphNode(int id) { 
		this.id = id; 
		this.color = Color.WHITE; 
		this.text = ""+id;
		this.cost = Integer.MAX_VALUE;
		this.parent = null;
	}
	

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int compareTo(GraphComp n) { 
		if (!(n instanceof GraphNode)){
			return super.compareTo(n);
		}
		GraphNode node = (GraphNode) n;
		if (this.cost!=node.cost)
			return this.cost - node.cost;
		else
			return this.id-n.id; //use id to break the tie
	}

	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public String toString() {
		return text;
	}
	
	/**
	 *  Generates a new node with a (probably) unique id.
	 *  
	 *  @return a new node
	 */
	public static Factory<GraphNode> getFactory() { 
		return new Factory<GraphNode> () {
			public GraphNode create() {
				return new GraphNode(nodeCount++);
			}
		};
	}
}
