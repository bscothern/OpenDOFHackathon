package opendof.sql;

import com.sun.org.apache.xpath.internal.operations.And;

public class DemoValidator implements SQLValidator
{

	@Override
	public boolean validateQuery(String query)
	{
		
		return ( !(query == null)&&!query.isEmpty());
		
	}

}
