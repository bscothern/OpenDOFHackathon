package opendof.sql;

import java.util.HashMap;
import java.util.Map;

import org.opendof.core.oal.DOFErrorException;
import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFOperation;
import org.opendof.core.oal.DOFProviderException;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.value.DOFBoolean;



public class SQLRequestor {

	DOFSystem mySystem;
	DOFObject providerObject;
	DOFObjectID providerObjectID = DOFObjectID.create("[3:provider@opendof.org]");
	
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
	 
	 public boolean sendSetRequest(boolean _active){
	        try{
	        	
	            DOFBoolean setValue = new DOFBoolean(_active);
	            
	            if(providerObject != null)
	            {
	            	System.out.println("After");
	            	providerObject.set(TBAInterface.PROPERTY_ALARM_ACTIVE, setValue, TIMEOUT);
	            	System.out.println("Before");
	            	return true;
	            }
	           
	            
	            return false;
	        } catch (DOFProviderException e) {
	            return false;
	        } catch (DOFErrorException e) {
	            return false;
	        } catch (DOFException e) {
	            return false;
	        }
	  }
	
	
	
}
