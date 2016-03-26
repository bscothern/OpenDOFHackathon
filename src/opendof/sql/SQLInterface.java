package opendof.sql;

import org.opendof.core.oal.DOFInterface;
import org.opendof.core.oal.DOFInterfaceID;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.value.DOFString;

public class SQLInterface {
	
	
	public static final DOFType QUERY = new DOFString.Type(3, 2000);
	public static final DOFType QUERY_COLUMN = new DOFString.Type(3, 2000);
	
	public static final DOFInterface DEF;
	public static final DOFInterfaceID IID = DOFInterfaceID.create("[63:{87654321}]");
	
	public static final int USER_QUERY_ID = 6;
	public static final int NAME_QUERY_ID = 7;
	public static final int METHOD_SELECT_ID = 8;	
	
	public static final DOFInterface.Property PROPERTY_SELECT_USERQUERY;
	public static final DOFInterface.Property PROPERTY_SELECT_NAMEQUERY;
	public static final DOFInterface.Method METHOD_SELECT_QUERY;
	
	//public static final DOFInterface.Exception EXCEPTION_BAD_QUERY;
	
	static {
    	DEF = new DOFInterface.Builder(IID)
 
    			.addProperty(6, true, true, QUERY)
    			.addProperty(7, true, true, QUERY)
    			.addMethod(8, new DOFType[] { QUERY }, new DOFType[] { QUERY_COLUMN })
    			.build();
        
        PROPERTY_SELECT_USERQUERY = DEF.getProperty(USER_QUERY_ID);
        PROPERTY_SELECT_NAMEQUERY = DEF.getProperty(NAME_QUERY_ID);
        METHOD_SELECT_QUERY = DEF.getMethod(METHOD_SELECT_ID);
	}
}
