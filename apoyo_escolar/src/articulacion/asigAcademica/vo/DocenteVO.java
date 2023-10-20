package articulacion.asigAcademica.vo;

import siges.common.vo.Vo;

public class DocenteVO extends Vo{
	private long codigo;
	private String nombre;
	
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
	 * @return Return the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
		
	
}
