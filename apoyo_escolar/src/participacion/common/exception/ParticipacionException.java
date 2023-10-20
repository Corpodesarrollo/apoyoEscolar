/**
 * 
 */
package participacion.common.exception;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ParticipacionException  extends Exception{

	/**
	 * Constructor
	 *  
	 */
	public ParticipacionException() {
		super();
	}

	/**
	 * Constructor
	 *  
	 * @param arg0
	 * @param arg1
	 */
	public ParticipacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 *  
	 * @param arg0
	 */
	public ParticipacionException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor
	 *  
	 * @param arg0
	 */
	public ParticipacionException(Throwable arg0) {
		super(arg0);
	}

}
