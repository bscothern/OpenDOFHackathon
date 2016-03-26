package opendof.sql;

import java.util.ArrayList;
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

	// TODO: remove this constructor
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
		mySystem = system;
		init();
		
		try{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			con = DriverManager.getConnection (url, userName, password);

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
//			Date usableResult = DOFType.asDate(parameters.get(0));
//			alarmTime = usableResult;
//
//			DOFBoolean myDOFBoolean = new DOFBoolean(isActive);
//			request.respond(myDOFBoolean);
//			lastOp = "invoke";
			System.out.println("Method invoked");
			String query = DOFType.asString(parameters.get(0));
			String results = sqlConsturctor.construction(query);
			boolean valid = sqlValidator.validateQuery(results);
			if (valid)
			{
//				List<String> table = null;
				String record = "";
				try
				{
					PreparedStatement preparedStatement = con.prepareStatement(results);
					ResultSet resultSet = preparedStatement.executeQuery();
					
					ResultSetMetaData rsmd = resultSet.getMetaData();
//					table = new ArrayList<String>();
//					int currentRow = 0;
					while(resultSet.next())
					{
						
						for (int col = 1; col < rsmd.getColumnCount(); col++)
							record += resultSet.getString(col) + "\t";
						record += "\n";
//						table.add(record);
//						currentRow++;
					}
					preparedStatement.close();
					
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
//				List<DOFValue> response = new ArrayList<DOFValue>();
//				for (int i = 0; i < table.size(); i++)
//				{
//					DOFString s = new DOFString(table.get(i));
//					response.add(s);
//				}
				System.out.println("Sent back proper response");
//				request.respond(response.get(0));
				request.respond(new DOFString(record));
			}
			else
				System.out.println("Sent back exception");
				request.respond(new DOFErrorException("Invalid query"));
		}
	}

	// Default SQLProvider Delgates
	private class DefaultSQLConstructor extends SQLConstructor {
		
		public DefaultSQLConstructor() {
			setQueryType(new String[] {});
		}

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
