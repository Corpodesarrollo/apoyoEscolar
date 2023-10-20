
package siges.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
*	Nombre:	InternalErrorException 
*	Descripcion:	Controlador de exepciones
*	Parametro de entrada:	no aplica
*	Parametro de salida:	no aplica
*	Funciones de la pagina:	Maneja las excepciones 
*	Entidades afectadas:	no aplica
*	Fecha de modificacinn:	Feb-05
*	@author Athenea
*	@version $v 1.3 $
*/
public class InternalErrorException extends Exception{
	private Exception encapsulatedException;
	private String ex;

/**
*	constructor de la clase
**/
	public InternalErrorException(Exception exception){
  	encapsulatedException = exception;
  }

	public InternalErrorException(String exe){
	  	ex = (exe!=null?exe:"");
	}

  public String getMensaje(){
    return ex;
  }
  
	/**
 *	Retorna el mensaje de errror de la excepcion	
 *	@return String 
 */
  public String getMessage(){
    if(encapsulatedException==null)
      return ex;
    else
      return encapsulatedException.getMessage();
  }

/**
 *	Retorna la excepcion 
 *	@return Exception 
 */
  public Exception getEncapsulatedException(){
  	return encapsulatedException;
  }
    
/**
	Imprime la pila de excepciones
 */
  public void printStackTrace(){
  	printStackTrace(System.err);
  }
    
/**
 *	Imprime la pila de excepciones
 *	@param PrintStream printStream
 */
  public void printStackTrace(PrintStream printStream){
  	super.printStackTrace(printStream);
    printStream.println("***Information about encapsulated exception***");
    encapsulatedException.printStackTrace(printStream);
  }
    
/**
 *	Imprime la pila de errores
 *	@param PrintWriter printWriter
 */
  public void printStackTrace(PrintWriter printWriter){
  	super.printStackTrace(printWriter);
    printWriter.println("***Information about encapsulated exception***");
    encapsulatedException.printStackTrace(printWriter);
  }
}