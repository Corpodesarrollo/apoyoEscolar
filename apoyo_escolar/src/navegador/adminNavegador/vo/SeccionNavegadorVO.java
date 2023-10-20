package navegador.adminNavegador.vo;

import siges.common.vo.Vo;

public class SeccionNavegadorVO extends Vo {

	private int codigo;
	private String nombre;
	private int orden;
	private String descripcion;
	private String tipoMime;
	private String nombreArchivo;
	private byte[] archivo;
	private String tipoMimeFondo;
	private String nombreArchivoFondo;
	private byte[] archivoFondo;
	private int estado;
	
	/**
	 * @return Returns the archivo.
	 */
	public byte[] getArchivo() {
		return archivo;
	}
	/**
	 * @param archivo The archivo to set.
	 */
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	/**
	 * @return Returns the archivoFondo.
	 */
	public byte[] getArchivoFondo() {
		return archivoFondo;
	}
	/**
	 * @param archivoFondo The archivoFondo to set.
	 */
	public void setArchivoFondo(byte[] archivoFondo) {
		this.archivoFondo = archivoFondo;
	}
	/**
	 * @return Returns the codigo.
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
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * @return Returns the nombreArchivo.
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	/**
	 * @param nombreArchivo The nombreArchivo to set.
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	/**
	 * @return Returns the nombreArchivoFondo.
	 */
	public String getNombreArchivoFondo() {
		return nombreArchivoFondo;
	}
	/**
	 * @param nombreArchivoFondo The nombreArchivoFondo to set.
	 */
	public void setNombreArchivoFondo(String nombreArchivoFondo) {
		this.nombreArchivoFondo = nombreArchivoFondo;
	}
	/**
	 * @return Returns the orden.
	 */
	public int getOrden() {
		return orden;
	}
	/**
	 * @param orden The orden to set.
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}
	/**
	 * @return Returns the tipoMime.
	 */
	public String getTipoMime() {
		return tipoMime;
	}
	/**
	 * @param tipoMime The tipoMime to set.
	 */
	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}
	/**
	 * @return Returns the tipoMimeFondo.
	 */
	public String getTipoMimeFondo() {
		return tipoMimeFondo;
	}
	/**
	 * @param tipoMimeFondo The tipoMimeFondo to set.
	 */
	public void setTipoMimeFondo(String tipoMimeFondo) {
		this.tipoMimeFondo = tipoMimeFondo;
	}
	/**
	 * @return Return the estado.
	 */
	public final int getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public final void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	

	
	
	
}
