package opendof.fileIO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFRequest;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.DOFValue;
import org.opendof.core.oal.DOFErrorException;
import org.opendof.core.oal.DOFInterface.Method;
import org.opendof.core.oal.DOFInterface.Property;
import org.opendof.core.oal.DOFOperation.Provide;
import org.opendof.core.oal.value.DOFBoolean;
import org.opendof.core.oal.value.DOFString;

public class DOFWriter
{
	// DOF Fields
	DOFSystem mySystem;
	DOFObject myObject;
	DOFObjectID myOID = DOFObjectID.create("[63:{12345678}]");
	String lastOp = "undefined";
	
	private String path;

	public DOFWriter(DOFSystem system, String _path) throws IOException {
		mySystem = system;
		init();

		path = _path;
	}

	private void init()
	{
		myObject = mySystem.createObject(myOID);
		myObject.beginProvide(DOFIOInterface.DEF, new ProviderListener());
	}

	public String getPath() {
		return path;
	}

	public String getLastOperation()
	{
		if (lastOp != null)
			return lastOp;
		else
			return "undefined";
	}

	public class ProviderListener extends DOFObject.DefaultProvider
	{
		@Override
		public void get(Provide operation, DOFRequest.Get request, Property property)
		{
			request.respond(myDOFBoolean);
			lastOp = "get";
		}

		@Override
		public void set(Provide operation, DOFRequest.Set request, Property property, DOFValue value)
		{
			request.respond();
			lastOp = "set";
			System.out.println("Requestor set: " + value);
		}

		@Override
		public void invoke(Provide operation, DOFRequest.Invoke request, Method method, List<DOFValue> parameters)
		{
			System.out.println("Method invoked");
			try {
				
			}
			catch (Exception e) {
				request.respond(new DOFErrorException(e.getMessage()));
				return;
			}
		}
	}
}
