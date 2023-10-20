/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ObjetivoVO extends Vo{
	private int objInstancia;
	private int objCodigo;
	private String objDescripcion;
	
	/**
	 * @return Return the objCodigo.
	 */
	public final int getObjCodigo() {
		return objCodigo;
	}
	/**
	 * @param objCodigo The objCodigo to set.
	 */
	public final void setObjCodigo(int objCodigo) {
		this.objCodigo = objCodigo;
	}
	/**
	 * @return Return the objDescripcion.
	 */
	public final String getObjDescripcion() {
		return objDescripcion;
	}
	/**
	 * @param objDescripcion The objDescripcion to set.
	 */
	public final void setObjDescripcion(String objDescripcion) {
		this.objDescripcion = objDescripcion;
	}
	/**
	 * @return Return the objInstancia.
	 */
	public final int getObjInstancia() {
		return objInstancia;
	}
	/**
	 * @param objInstancia The objInstancia to set.
	 */
	public final void setObjInstancia(int objInstancia) {
		this.objInstancia = objInstancia;
	}
}
