package opendof.sql;

/**
 * This abstract class is used to create a delegate relationship for SQLProvider objects.
 * It is very important that you call setQueryType() on all SQLConstructor objects.
 * @see SQLConstructorInterface
 */
public abstract class SQLConstructor implements SQLConstructorInterface
{
	private String[]  queryType = null;
	
	/**
	 * This will return null unless the SQLConstructor object has had setQueryType() called.
	 * @return The supported SQLProvider "methods" and their "arguments" that function with this objects construction().
	 */
	public  String[] getQueryType()
	{
		return queryType;
	}

	/**
	 * This method should be called before the SQLConstructor is called
	 * @param queryTypes - This should contain a user defined format for SQL query "methods" and their "arguments". 
	 */
	public void setQueryType(String[] queryTypes)
	{
		if(queryType == null)
		{
			queryType = queryTypes;
		}
		else
		{
			System.err.println("QueryType is immutable once set");
		}
	}
}
