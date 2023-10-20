/**
 * 
 */
package siges.integracion.beans;

/**
 * 24/06/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Colegio {
	private long id;
	private long dane11;
	private long dane12;
	private int departamento=25;
	private int localidad;
	private String nombre;
	private String estado;
	private String tipo;
	private int zona;
	private String direccion;
	private long dane11Old;
	private long dane12Old;

	private int categoria=Params.C_INSTITUCION;
	private int funcion;
	
	/**
	 * @return Return the dane11.
	 */
	public final long getDane11() {
		return dane11;
	}
	/**
	 * @param dane11 The dane11 to set.
	 */
	public final void setDane11(long dane11) {
		this.dane11 = dane11;
	}
	/**
	 * @return Return the dane11Old.
	 */
	public final long getDane11Old() {
		return dane11Old;
	}
	/**
	 * @param dane11Old The dane11Old to set.
	 */
	public final void setDane11Old(long dane11Old) {
		this.dane11Old = dane11Old;
	}
	/**
	 * @return Return the dane12.
	 */
	public final long getDane12() {
		return dane12;
	}
	/**
	 * @param dane12 The dane12 to set.
	 */
	public final void setDane12(long dane12) {
		this.dane12 = dane12;
	}
	/**
	 * @return Return the dane12Old.
	 */
	public final long getDane12Old() {
		return dane12Old;
	}
	/**
	 * @param dane12Old The dane12Old to set.
	 */
	public final void setDane12Old(long dane12Old) {
		this.dane12Old = dane12Old;
	}
	/**
	 * @return Return the direccion.
	 */
	public final String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion The direccion to set.
	 */
	public final void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return Return the estado.
	 */
	public final String getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public final void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return Return the localidad.
	 */
	public final int getLocalidad() {
		return localidad;
	}
	/**
	 * @param localidad The localidad to set.
	 */
	public final void setLocalidad(int localidad) {
		this.localidad = localidad;
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
	/**
	 * @return Return the tipo.
	 */
	public final String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public final void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Return the id.
	 */
	public final long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public final void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Return the departamento.
	 */
	public final int getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento The departamento to set.
	 */
	public final void setDepartamento(int departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return Return the zona.
	 */
	public final int getZona() {
		return zona;
	}
	/**
	 * @param zona The zona to set.
	 */
	public final void setZona(int zona) {
		this.zona = zona;
	}
	/**
	 * @return Return the categoria.
	 */
	public final int getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria The categoria to set.
	 */
	public final void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return Return the funcion.
	 */
	public final int getFuncion() {
		return funcion;
	}
	/**
	 * @param funcion The funcion to set.
	 */
	public final void setFuncion(int funcion) {
		this.funcion = funcion;
	}
}
