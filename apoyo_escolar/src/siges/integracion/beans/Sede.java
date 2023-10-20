/**
 * 
 */
package siges.integracion.beans;

/**
 * 24/06/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Sede {
	private long id;
	private long daneColegio;
	private long daneSede;
	private long codigo;
	private String nombre;
	private String direccion;
	private String telefono;
	private String estado;
	private long daneColegioOld;
	private long daneSedeOld;
	private long codigoOld;

	private int categoria=Params.C_JORNADA;
	private int funcion;
	
	/**
	 * @return Return the codigo.
	 */
	public final long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public final void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the codigoOld.
	 */
	public final long getCodigoOld() {
		return codigoOld;
	}
	/**
	 * @param codigoOld The codigoOld to set.
	 */
	public final void setCodigoOld(long codigoOld) {
		this.codigoOld = codigoOld;
	}
	/**
	 * @return Return the daneColegio.
	 */
	public final long getDaneColegio() {
		return daneColegio;
	}
	/**
	 * @param daneColegio The daneColegio to set.
	 */
	public final void setDaneColegio(long daneColegio) {
		this.daneColegio = daneColegio;
	}
	/**
	 * @return Return the daneColegioOld.
	 */
	public final long getDaneColegioOld() {
		return daneColegioOld;
	}
	/**
	 * @param daneColegioOld The daneColegioOld to set.
	 */
	public final void setDaneColegioOld(long daneColegioOld) {
		this.daneColegioOld = daneColegioOld;
	}
	/**
	 * @return Return the daneSede.
	 */
	public final long getDaneSede() {
		return daneSede;
	}
	/**
	 * @param daneSede The daneSede to set.
	 */
	public final void setDaneSede(long daneSede) {
		this.daneSede = daneSede;
	}
	/**
	 * @return Return the daneSedeOld.
	 */
	public final long getDaneSedeOld() {
		return daneSedeOld;
	}
	/**
	 * @param daneSedeOld The daneSedeOld to set.
	 */
	public final void setDaneSedeOld(long daneSedeOld) {
		this.daneSedeOld = daneSedeOld;
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
	 * @return Return the telefono.
	 */
	public final String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono The telefono to set.
	 */
	public final void setTelefono(String telefono) {
		this.telefono = telefono;
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
