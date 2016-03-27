package opendof.sql;

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
	SQLConstructor sqlConstructor = null;
	SQLValidator sqlValidator = null;

	public SQLProvider(DOFSystem system, String url, String userName, String password) throws Exception {
		this(system, url, userName, password, null, null);
	}

	public SQLProvider(DOFSystem system, String url, String userName, String password,
			SQLConstructor _sqlConstructor, SQLValidator _sqlValidator) throws Exception {
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
			sqlConstructor = _sqlConstructor;
		}
		else {
			sqlConstructor = new DefaultSQLConstructor();
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
		myObject.beginProvide(SQLInterface.DEF, new ProviderListener());
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
			System.out.println("Method invoked");
			String query = DOFType.asString(parameters.get(0));
			String results = sqlConstructor.construction(query);
			boolean valid = sqlValidator.validateQuery(results);
			if (valid)
			{
				String record = "";
				try
				{
					PreparedStatement preparedStatement = con.prepareStatement(results);
					ResultSet resultSet = preparedStatement.executeQuery();

					ResultSetMetaData rsmd = resultSet.getMetaData();
					for (int col = 1; col < rsmd.getColumnCount(); col++)
						record += rsmd.getColumnName(col) + "\t";

					while(resultSet.next())
					{
						for (int col = 1; col < rsmd.getColumnCount(); col++)
							record += resultSet.getString(col) + "\t";
						record += "\n";
					}
					preparedStatement.close();

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				System.out.println("Sent back proper response");
				request.respond(new DOFString(record));
			}
			else
				System.out.println("Sent back exception");
			request.respond(new DOFErrorException("Invalid query"));
		}
	}

	// Default SQLProvider Delegates
	/**
	 * This is the default SQLConstructor object that is used as the SQLProvider's SQLConstructor when not provided.
	 * It expects the connected SQLRequestor to be sending valid SQL queries rather than being constructed from the
	 * SQLProvider's call to it's SQLConstructor object. This is not secure but is convenient.
	 */
	private class DefaultSQLConstructor extends SQLConstructor {

		/**
		 * This will fully initialize the SQLConstructor with an empty String[] for the queryType.
		 */
		public DefaultSQLConstructor() {
			setQueryType(new String[] {});
		}

		@Override
		/**
		 * There is no typical SQLContructor implementation to create a SQL query in this method.
		 * 
		 * @param args A SQL query.
		 * @return args is assumed to be valid SQL query and returned immediately
		 */
		public String construction(String args) {
			return args;
		}
	}

	/**
	 * This is the default SQLValidator object that is used as the SQLProvider's SQLValidator when not provided.
	 * It assumes that everything is valid which is not secure but is convenient.
	 */
	private class DefaultSQLValidator extends SQLValidator {

		/**
		 * There is no typical SQLValidator implementation to validate a SQL query in this method.
		 * 
		 * @param query The query that is to be validated.
		 * @return This is always true. It assumes that the query is valid regardless of its contents.
		 */
		public boolean validateQuery(String query) {
			return true;
		}
	}
}
