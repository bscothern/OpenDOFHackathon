package opendof.fileIO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendof.core.oal.DOF;
import org.opendof.core.oal.DOFException;
import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFOperation;
import org.opendof.core.oal.DOFResult;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.DOFValue;
import org.opendof.core.oal.value.DOFBoolean;
import org.opendof.core.oal.value.DOFInt32;
import org.opendof.core.oal.value.DOFString;

public class Requestor
{

	DOFSystem mySystem;
	DOFObject providerObject;
	DOFObjectID providerObjectID = DOFObjectID.create("[63:{12345678}]");

	Map<String, DOFObject> objectMap = new HashMap<String, DOFObject>(2);

	DOFOperation.Invoke activeInvokeOperation = null;

	int TIMEOUT = 1000;
	int MIN_PERIOD = 2000;

	public Requestor(DOFSystem system)
	{
		mySystem = system;
		providerObject = mySystem.createObject(providerObjectID);
	}

	public void setCurrentRequestor(String _oidString)
	{
		providerObject = objectMap.get(_oidString);
	}
	
	public boolean writeFileContents(String fname, int op, String cont) throws DOFException
	{
		if (mySystem.getState().isAuthorized())
		{
			DOFString filename = new DOFString(fname);
			DOFString contents = new DOFString(cont);
			DOFInt32 operation = new DOFInt32(op);
			
			DOFResult<List<DOFValue>> results = providerObject.invoke(DOFIOInterface.METHOD_WRITE_FILE, DOF.TIMEOUT_NEVER, filename, operation, contents);
			List<DOFValue> valList = results.get();
			boolean success = DOFBoolean.Type.asBoolean(valList.get(0));
			return success;
		}
		return false;
	}

	public List<DOFValue> getFileContents(String fname) throws DOFException
	{

		if (mySystem.getState().isAuthorized())
		{
			try
			{
				DOFString filename = new DOFString(fname);
				
				DOFResult<List<DOFValue>> myResults = providerObject.invoke(DOFIOInterface.METHOD_READ_FILE, DOF.TIMEOUT_NEVER, filename);
				List<DOFValue> myValueList = myResults.get();
				return myValueList;
			}
			catch (DOFException e)
			{
				System.err.println("Invoke failed: " + e.getMessage());
				throw e;
			}
		}
		else
		{
			// The system is not ready yet.
			return null;
		}
	}
}
