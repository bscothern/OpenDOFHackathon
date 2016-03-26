package opendof.sql;

public class DemoValidator extends SQLValidator
{
	@Override
	public boolean validateQuery(String query)
	{
		return ( !(query == null)&&!query.isEmpty());	
	}
}
