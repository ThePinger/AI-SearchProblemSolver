package genericsearch;

public interface State
{
	/**
	 * This method generates a String ID for the state to be stored in the StateSpace
	 * @return StateID as String
	 */
	public String generateStateID();
}
