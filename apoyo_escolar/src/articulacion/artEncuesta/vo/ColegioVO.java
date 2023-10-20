package articulacion.artEncuesta.vo;

import java.util.List;

public class ColegioVO {

	private long codigo;
	private int size;
	private String nombre;
	private String localidad;
	private List listaEspecialidad;
	
	/**
	 * @return Returns the codigo.
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
	/**
	 * @return Returns the listaEspecialidad.
	 */
	public List getListaEspecialidad() {
		return listaEspecialidad;
	}
	/**
	 * @param listaEspecialidad The listaEspecialidad to set.
	 */
	public void setListaEspecialidad(List listaEspecialidad) {
		this.listaEspecialidad = listaEspecialidad;
	}
	/**
	 * @return Returns the localidad.
	 */
	public String getLocalidad() {
		return localidad;
	}
	/**
	 * @param localidad The localidad to set.
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	/**
	 * @return Returns the nombre.
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
	 * @return Return the size.
	 */
	public int getSize() {
		return listaEspecialidad!=null?(listaEspecialidad.size()+1):1;
	}
	/**
	 * @param size The size to set.
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
