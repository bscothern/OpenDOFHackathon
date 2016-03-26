package opendof.sql;

/**
 * This interface should not be implemented directly. Any class that needs to implements this should extend the SQLConstructor class.
 * @see SQLConstructor
 */
public interface SQLConstructorInterface
{
	/**
	 * 
	 * @param args This is the requested "method" and "arguments" that are needed to create a SQL Query.
	 * 		The pattern for this is defined by the user and all valid patterns should come from SQLConstructor objects getQueryTypes()
	 * @return A SQL query created from args.
	 */
	String construction(String args);
}
