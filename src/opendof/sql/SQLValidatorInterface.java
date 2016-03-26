package opendof.sql;

/**
 * This interface should not be implemented directly. Any class that needs to implement this should extend the SQLValidator class.
 */
public interface SQLValidatorInterface {
	public boolean validateQuery(String query);
}
