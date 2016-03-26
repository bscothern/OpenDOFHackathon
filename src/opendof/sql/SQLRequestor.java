package opendof.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendof.core.oal.DOFErrorException;
import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFOperation;
import org.opendof.core.oal.DOFProviderException;
import org.opendof.core.oal.DOFResult;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.DOFValue;
import org.opendof.core.oal.value.DOFBoolean;
import org.opendof.core.oal.value.DOFDateTime;
import org.opendof.core.oal.value.DOFString;


public class SQLRequestor {

	DOFSystem mySystem;
	DOFObject providerObject;
	DOFObjectID providerObjectID = DOFObjectID.create("[63:{12345678}]");

	Map<String, DOFObject> objectMap = new HashMap<String, DOFObject>(2);

	DOFOperation.Get activeGetOperation = null;
	DOFOperation.Set activeSetOperation = null;
	DOFOperation.Invoke activeInvokeOperation = null;

	int TIMEOUT = 1000;
	int MIN_PERIOD = 2000;

	public SQLRequestor(DOFSystem system){
		mySystem = system;
		providerObject = mySystem.createObject(providerObjectID);
	}

	public void setCurrentRequestor(String _oidString){
		providerObject = objectMap.get(_oidString);  
	}

	public List<DOFValue> sendSelectQuery(String _query) throws DOFException{

		if(mySystem.getState().isAuthorized())
		{
			try{
				DOFString query = new DOFString(_query);
				DOFResult<List<DOFValue>> myResults = providerObject.invoke(SQLInterface.METHOD_SELECT_QUERY, 5000, query);        
				List<DOFValue> myValueList = myResults.get();
				for(DOFValue dv: myValueList){
					System.out.println(dv);
				}
				return myValueList;
			} catch (DOFException e) {
				System.err.println("Invoke failed: " + e.getMessage());
				throw e;
			} 
		} else
		{
			//The system is not ready yet.
			return null;
		}
	} 
}
