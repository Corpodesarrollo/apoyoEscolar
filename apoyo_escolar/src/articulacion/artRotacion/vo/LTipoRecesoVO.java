/**
 * 
 */
package articulacion.artRotacion.vo;

import siges.common.vo.Vo;

/**
 * 10/03/2008 
 * @author Latined
 * @version 1.2
 */
public class LTipoRecesoVO extends Vo{
	private int codigo;
	private String nombre;
	
	/**
	 * @return Return the codigo.
	 */
	public final int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public final void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the nombre.
	 */
	public final String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
