package genericsearch;

public interface Operator 
{
	/**
	 * This method applies this operator to the given state. It then returns the new state after the operator has been applied
	 * @param oldState The state that this operator is going to be applied to
	 * @return The state after applying this operator on the old state.
	 */
	public State apply(State oldState);
	
	/**
	 * 
	 * @return
	 */
	public String getOperatorName();
}
