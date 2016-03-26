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

import java.sql.*;

public class SQLProvider
{
	// DOF Fields
	DOFSystem mySystem;
	DOFObject myObject;
	DOFObjectID myOID = DOFObjectID.create("[63:{12345678}]");
	String lastOp = "undefined";
	boolean isActive = false;
	Date alarmTime;

	// DB Fields
	Connection con;
	SQLConstructor sqlConsturctor = null;
	SQLValidator sqlValidator = null;

	public SQLProvider(DOFSystem system)
	{
		mySystem = system;
		init();
	}
	

	public SQLProvider(DOFSystem system, String url, String userName, String password) throws Exception {
		this(system, url, userName, password, null, null);
	}

	public SQLProvider(DOFSystem system, String url, String userName, String password,
			SQLConstructor _sqlConstructor, SQLValidator _sqlValidator) throws Exception {
		// TODO: Connect to DB
		try{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			con = DriverManager.getConnection (url, userName, password);

			//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//stmt = con.createStatement();
			//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch(Exception e) {
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
			System.err.println(e.getMessage());
			throw(e);
		}

		if (_sqlConstructor != null) {
			sqlConsturctor = _sqlConstructor;
		}
		else {
			sqlConsturctor = new DefaultSQLConstructor();
		}
		
		if (_sqlValidator != null) {
			sqlValidator = _sqlValidator;
		}
		else {
			sqlValidator = new DefaultSQLValidator();
		}
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
	
	// Default SQLProvider Delgates
	private class DefaultSQLConstructor extends SQLConstructor {
		@Override
		public String construction(String args) {
			return args;
		}
	}

	private class DefaultSQLValidator implements SQLValidator {
		public boolean validateQuery(String query) {
			// TODO: finish validation
			return true;
		}
	}
}
