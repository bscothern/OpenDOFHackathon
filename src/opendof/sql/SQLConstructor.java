package opendof.sql;


public abstract class SQLConstructor implements SQLConstructorInterface
{
	
	private String[]  queryType = null;
	
	public  String[] getQueryType()
	{
		return queryType;
	}

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
