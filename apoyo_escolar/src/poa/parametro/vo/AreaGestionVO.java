/**
 * 
 */
package poa.parametro.vo;

import siges.common.vo.Vo;

/**
 * 14/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class AreaGestionVO extends Vo{
	private int areCodigo;
	private String areNombre;
	private String areDescripcion;
	private float arePonderador;
	private int areOrden;
	
	
	/**
	 * @return Return the areCodigo.
	 */
	public int getAreCodigo() {
		return areCodigo;
	}
	/**
	 * @param areCodigo The areCodigo to set.
	 */
	public void setAreCodigo(int areCodigo) {
		this.areCodigo = areCodigo;
	}
	/**
	 * @return Return the areDescripcion.
	 */
	public String getAreDescripcion() {
		return areDescripcion;
	}
	/**
	 * @param areDescripcion The areDescripcion to set.
	 */
	public void setAreDescripcion(String areDescripcion) {
		this.areDescripcion = areDescripcion;
	}
	/**
	 * @return Return the areNombre.
	 */
	public String getAreNombre() {
		return areNombre;
	}
	/**
	 * @param areNombre The areNombre to set.
	 */
	public void setAreNombre(String areNombre) {
		this.areNombre = areNombre;
	}
	/**
	 * @return Return the areOrden.
	 */
	public int getAreOrden() {
		return areOrden;
	}
	/**
	 * @param areOrden The areOrden to set.
	 */
	public void setAreOrden(int areOrden) {
		this.areOrden = areOrden;
	}
	/**
	 * @return Return the arePonderador.
	 */
	public float getArePonderador() {
		return arePonderador;
	}
	/**
	 * @param arePonderador The arePonderador to set.
	 */
	public void setArePonderador(float arePonderador) {
		this.arePonderador = arePonderador;
	}
	
}
