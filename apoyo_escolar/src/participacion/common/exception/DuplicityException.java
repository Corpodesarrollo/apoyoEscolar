/**
 * 
 */
package participacion.common.exception;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class DuplicityException extends ParticipacionException{

	/**
	 * Constructor
	 *  
	 * @param arg0
	 * @param arg1
	 */
	public DuplicityException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 *  
	 * @param arg0
	 */
	public DuplicityException(String arg0) {
		super(arg0);
	}

}
