package siges.boletines.beans;

import java.io.Serializable;

/**
 * Nombre: FiltroBeanConstancias.java <BR>
 * Descripcinn: Contiene los datos referentes al formulario de peticinn de la
 * Constancia<BR>
 * Funciones de la pngina: Almacenar temporalmente los datos del formulario de
 * peticinn de la constancia a generar <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class FiltroBeanConstancias implements Serializable, Cloneable {

	private String institucion;
	private String institucionid;
	private String sede;
	private String jornada;
	private String metodologia;
	private String grado;
	private String grupo;
	private String id;
	private String ano;
	private String motivo;
	private String estudiante;
	private String orden;
	private String usuarioid;;
	private String ano_actual;
	private String tiporep;

	/**
	 * asigna el campo tiporep
	 * 
	 * @param String
	 *            s
	 */
	public void settiporep(String s) {
		this.tiporep = s;
	}

	/**
	 * retorna el campo tiporep
	 * 
	 * @return String
	 */
	public String gettiporep() {
		return tiporep != null ? tiporep : "";
	}

	/**
	 * asigna el campo institucion
	 * 
	 * @param String
	 *            s
	 */
	public void setInstitucion(String s) {
		this.institucion = s;
	}

	/**
	 * retorna el campo institucion
	 * 
	 * @return String
	 */
	public String getInstitucion() {
		return institucion != null ? institucion : "";
	}

	/**
	 * asigna el campo institucionid
	 * 
	 * @param String
	 *            s
	 */
	public void setInstitucionid(String s) {
		this.institucionid = s;
	}

	/**
	 * retorna el campo institucionid
	 * 
	 * @return String
	 */
	public String getInstitucionid() {
		return institucionid != null ? institucionid : "";
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
	 * asigna el campo metodologia
	 * 
	 * @param String
	 *            s
	 */
	public void setMetodologia(String s) {
		this.metodologia = s;
	}

	/**
	 * retorna el campo metodologia
	 * 
	 * @return String
	 */
	public String getMetodologia() {
		return metodologia != null ? metodologia : "";
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
	 * asigna el campo ano
	 * 
	 * @param String
	 *            s
	 */
	public void setAno(String s) {
		this.ano = s;
	}

	/**
	 * retorna el campo ano
	 * 
	 * @return String
	 */
	public String getAno() {
		return ano != null ? ano : "";
	}

	/**
	 * asigna el campo motivo
	 * 
	 * @param String
	 *            s
	 */
	public void setMotivo(String s) {
		this.motivo = s;
	}

	/**
	 * retorna el campo ano
	 * 
	 * @return String
	 */
	public String getMotivo() {
		return motivo != null ? motivo : "";
	}

	/**
	 * asigna el campo estudiante
	 * 
	 * @param String
	 *            s
	 */
	public void setEstudiante(String s) {
		this.estudiante = s;
	}

	/**
	 * retorna el campo estudiante
	 * 
	 * @return String
	 */
	public String getEstudiante() {
		return estudiante != null ? estudiante : "";
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
	 * asigna el campo usuarioid
	 * 
	 * @param String
	 *            s
	 */
	public void setUsuarioid(String s) {
		this.usuarioid = s;
	}

	/**
	 * retorna el campo usuarioid
	 * 
	 * @return String
	 */
	public String getUsuarioid() {
		return usuarioid != null ? usuarioid : "";
	}

	/**
	 * asigna el campo ano_actual
	 * 
	 * @param String
	 *            s
	 */
	public void setAno_actual(String s) {
		this.ano_actual = s;
	}

	/**
	 * retorna el campo ano_actual
	 * 
	 * @return String
	 */
	public String getAno_actual() {
		return ano_actual != null ? ano_actual : "";
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

}