/**
 * 
 */
package navegador.exception;

/**
 * 4/03/2008 
 * @author Latined
 * @version 1.2
 */
public class NavegadorException  extends Exception{

	/**
	 * Constructor with error message.
	 * 
	 * @param msg the error message associated with the exception
	 */
	public NavegadorException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg the error message associated with the exception
	 * @param cause the root cause of the exception
	 */
	public NavegadorException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
