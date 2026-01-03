//TODO: Nothing, all done.

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;


/**
 *  Interface for graph algorithms in the simulator.
 *  
 *  @author Katherine (Raven) Russell
 *  @author Y. Zhong (Updated Spring 24)
 */
interface GraphAlg {
	/**
	 *  Indicates the edge type required by the algotithm.
	 *  
	 *  @return the edge type expected by the algorithm
	 */
	public EdgeType graphEdgeType();
	
	/**
	 *  Indicates if the algorithm has been started.
	 *  
	 *  @return true if the algorithm has been started
	 */
	public boolean isStarted();
	
	/**
	 *  What to do before the simulator begins.
	 */
	public void start();
		
	/**
	 *  Indicates if the algorithm has been completed.
	 *  
	 *  @return true if there was another step 
	 */
	public boolean isCompleted();
	
	/**
	 *  What to actually do during a step.
	 *  
	 */
	public void doNextStep();

	/**
	 *  What to do after the simulator finishes all steps.
	 */
	public void finish();
	

	/**
	 *  What to do when the simulator is stepped.
	 *  
	 *  @return whether or not there are more steps.
	 */
	public default boolean step() {
		if(!isStarted()) {
			start();
			return true;
		}
		
		doNextStep();		

		if(isCompleted()) {
			finish();
			return false;
		}
		else
			return true;
	}
	
	/**
	 *  Resets the algorithm for a new graph.
	 *  
	 *  @param graph the new graph
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph);
	
	
}