package siges.estudiante.beans;

import java.io.Serializable;

/**
 * Nombre: FiltroBean<BR>
 * Descripcion: Bean de formulario de filtro de estudiantes <BR>
 * Funciones de la pngina: Bean de datos del formularios de filtro de
 * estudiantes <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class FiltroBean implements Serializable, Cloneable {

	private String sede;// 1
	private String jornada;// 2
	private String grado;// 3
	private String grupo;// 4
	private String id;// 5
	private String nombre1;// 6
	private String nombre2;// 7
	private String apellido1;// 8
	private String apellido2;// 9
	private String orden;// 10
	private String metodologia;// 11
	private Long institucion;// 12
	private Long institucionDane;// 13
	private String fecha;// 13
	private String usuario;// 13
	private String nomInst;// 13
	private String modcon;
	private String filgrupo;
	private String tipoDocumento;

	public Long getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Long institucion) {
		this.institucion = institucion;
	}

	public Long getInstitucionDane() {
		return institucionDane;
	}

	public void setInstitucionDane(Long institucionDane) {
		this.institucionDane = institucionDane;
	}

	/**
	 * asigna el campo sede
	 * 
	 * @param String
	 *            s
	 */
	public void setSede(String s) {
		this.sede = s;
	}

	/**
	 * retorna el campo sede
	 * 
	 * @return String
	 */
	public String getSede() {
		return sede != null ? sede : "";
	}

	/**
	 * asigna el campo jornada
	 * 
	 * @param String
	 *            s
	 */
	public void setJornada(String s) {
		this.jornada = s;
	}

	/**
	 * retorna el campo jornada
	 * 
	 * @return String
	 */
	public String getJornada() {
		return jornada != null ? jornada : "";
	}

	/**
	 * asigna el campo
	 * 
	 * @param String
	 *            s
	 */
	public void setGrado(String s) {
		this.grado = s;
	}

	/**
	 * retorna el campo
	 * 
	 * @return String
	 */
	public String getGrado() {
		return grado != null ? grado : "";
	}

	/**
	 * asigna el campo grupo
	 * 
	 * @param String
	 *            s
	 */
	public void setGrupo(String s) {
		this.grupo = s;
	}

	/**
	 * retorna el campo grupo
	 * 
	 * @return String
	 */
	public String getGrupo() {
		return grupo != null ? grupo : "";
	}

	/**
	 * asigna el campo id
	 * 
	 * @param String
	 *            s
	 */
	public void setId(String s) {
		this.id = s;
	}

	/**
	 * retorna el campo id
	 * 
	 * @return String
	 */
	public String getId() {
		return id != null ? id.trim() : "";
	}

	/**
	 * asigna el campo nombre1
	 * 
	 * @param String
	 *            s
	 */
	public void setNombre1(String s) {
		this.nombre1 = s;
	}

	/**
	 * retorna el campo nombre1
	 * 
	 * @return String
	 */
	public String getNombre1() {
		return nombre1 != null ? nombre1.trim() : "";
	}

	/**
	 * asigna el campo nombre2
	 * 
	 * @param String
	 *            s
	 */
	public void setNombre2(String s) {
		this.nombre2 = s;
	}

	/**
	 * retorna el campo nombre2
	 * 
	 * @return String
	 */
	public String getNombre2() {
		return nombre2 != null ? nombre2.trim() : "";
	}

	/**
	 * asigna el campo apellido1
	 * 
	 * @param String
	 *            s
	 */
	public void setApellido1(String s) {
		this.apellido1 = s;
	}

	/**
	 * retorna el campo apellido1
	 * 
	 * @return String
	 */
	public String getApellido1() {
		return apellido1 != null ? apellido1.trim() : "";
	}

	/**
	 * asigna el campo apellido2
	 * 
	 * @param String
	 *            s
	 */
	public void setApellido2(String s) {
		this.apellido2 = s;
	}

	/**
	 * retorna el campo
	 * 
	 * @return String
	 */
	public String getApellido2() {
		return apellido2 != null ? apellido2.trim() : "";
	}

	/**
	 * asigna el campo orden
	 * 
	 * @param String
	 *            s
	 */
	public void setOrden(String s) {
		this.orden = s;
	}

	/**
	 * retorna el campo orden
	 * 
	 * @return String
	 */
	public String getOrden() {
		return orden != null ? orden : "";
	}

	/**
	 * Hace una copia del objeto mismo
	 * 
	 * @return Object
	 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}

	/**
	 * @return Devuelve metodologia.
	 */
	public String getMetodologia() {
		return metodologia != null ? metodologia : "";
	}

	/**
	 * @param metodologia
	 *            El metodologia a establecer.
	 */
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNomInst() {
		return nomInst;
	}

	public void setNomInst(String nomInst) {
		this.nomInst = nomInst;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getmodcon() {
		return modcon;
	}

	public void setmodcon(String estado) {
		this.modcon = estado;
	}

	public String getFilgrupo() {
		return filgrupo;
	}

	public void setFilgrupo(String filgrupo) {
		this.filgrupo = filgrupo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
}