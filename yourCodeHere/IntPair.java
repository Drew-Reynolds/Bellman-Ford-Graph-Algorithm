//TODO: Nothing, all done.

/**
 *  A pair of integers (used by Graph).
 *  
 *  @author Y. Zhong
 */

class IntPair{
	/**
	 *  First member of the pair.
	 */
	private int first;

	/**
	 *  Second member of the pair.
	 */
	private int second;
	
	/**
	 *  Constructs a pair with the two incoming integers.
	 *  
	 *  @param f the first member of the pair
	 *  @param s the second member of the pair
	 */
	public IntPair(int f, int s){
		first = f;
		second = s;
	}
	
	/**
	 *  Fetch the first member of this pair.
	 *  
	 *  @return the first member of this pair
	 */
	public int getFirst(){
		return first;
	}

	/**
	 *  Fetch the second member of this pair.
	 *  
	 *  @return the second member of this pair
	 */
	public int getSecond(){
		return second;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public String toString(){
		return "("+first+","+second+")";
	}
}