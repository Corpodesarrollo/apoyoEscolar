/**
 * 
 */
package siges.rotacion.beans.forma;

/**
 * 11/08/2007 
 * @author Latined
 * @version 1.2
 */
public class JornadaVO {
	private int sede;
	private int codigo;
	private String nombre;
	
	/**
	 * @return Return the codigo.
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Return the sede.
	 */
	public int getSede() {
		return sede;
	}
	/**
	 * @param sede The sede to set.
	 */
	public void setSede(int sede) {
		this.sede = sede;
	}
}
