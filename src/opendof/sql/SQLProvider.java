package opendof.sql;

import java.util.Date;
import java.util.List;

import org.opendof.core.oal.DOFObject;
import org.opendof.core.oal.DOFObjectID;
import org.opendof.core.oal.DOFRequest;
import org.opendof.core.oal.DOFSystem;
import org.opendof.core.oal.DOFType;
import org.opendof.core.oal.DOFValue;
import org.opendof.core.oal.DOFInterface.Method;
import org.opendof.core.oal.DOFInterface.Property;
import org.opendof.core.oal.DOFOperation.Provide;
import org.opendof.core.oal.value.DOFBoolean;

public class SQLProvider
{

	DOFSystem mySystem;
	DOFObject myObject;
	DOFObjectID myOID = DOFObjectID.create("[3:provider@opendof.org]");
	String lastOp = "undefined";
    boolean isActive = false;
    Date alarmTime;

	public SQLProvider(DOFSystem system)
	{
		mySystem = system;
		init();
	}

	private void init()
	{
		myObject = mySystem.createObject(myOID);
		myObject.beginProvide(TBAInterface.DEF, new ProviderListener());
	}

	public boolean getActive()
	{
		return isActive;
	}

	public void setActive(boolean _active)
	{
		isActive = _active;
	}

	public Date getAlarmTime()
	{
		return alarmTime;
	}

	public void setAlarmTime(Date _alarmTime)
	{
		alarmTime = _alarmTime;
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
			DOFBoolean myDOFBoolean = new DOFBoolean(isActive);
			request.respond(myDOFBoolean);
			lastOp = "get";
			System.out.println("Requestor requested: " + isActive);
		}

		@Override
		public void set(Provide operation, DOFRequest.Set request, Property property, DOFValue value)
		{
			isActive = DOFType.asBoolean(value);
			request.respond();
			lastOp = "set";
			System.out.println("Requestor set: " + value);
		}

		@Override
		public void invoke(Provide operation, DOFRequest.Invoke request, Method method, List<DOFValue> parameters)
		{
			Date usableResult = DOFType.asDate(parameters.get(0));
			alarmTime = usableResult;

			DOFBoolean myDOFBoolean = new DOFBoolean(isActive);
			request.respond(myDOFBoolean);
			lastOp = "invoke";
		}
	}

}
