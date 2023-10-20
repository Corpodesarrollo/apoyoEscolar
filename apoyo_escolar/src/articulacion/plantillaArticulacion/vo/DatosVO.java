package articulacion.plantillaArticulacion.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private long institucion;
	private String  institucion_;
	private long sede;
	private String  sede_;
	private long jornada;
	private String  jornada_;
	private long grado;
	private long grupo;
	private String  grupo_;
	private String  metodologia;
	private String  fecha;
	private long tipo;
	
	/**
	 * @return Return the grupo.
	 */
	public long getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return Return the grado.
	 */
	public long getGrado() {
		return grado;
	}
	/**
	 * @param grado The grado to set.
	 */
	public void setGrado(long grado) {
		this.grado = grado;
	}
	/**
	 * @return Return the institucion.
	 */
	public long getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion The institucion to set.
	 */
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	/**
	 * @return Return the jornada.
	 */
	public long getJornada() {
		return jornada;
	}
	/**
	 * @param jornada The jornada to set.
	 */
	public void setJornada(long jornada) {
		this.jornada = jornada;
	}
	/**
	 * @return Return the sede.
	 */
	public long getSede() {
		return sede;
	}
	/**
	 * @param sede The sede to set.
	 */
	public void setSede(long sede) {
		this.sede = sede;
	}
	/**
	 * @return Return the metodologia.
	 */
	public String getMetodologia() {
		return metodologia;
	}
	/**
	 * @param metodologia The metodologia to set.
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
	public String getGrupo_() {
		return grupo_;
	}
	public void setGrupo_(String grupo_) {
		this.grupo_ = grupo_;
	}
	public String getInstitucion_() {
		return institucion_;
	}
	public void setInstitucion_(String institucion_) {
		this.institucion_ = institucion_;
	}
	public String getJornada_() {
		return jornada_;
	}
	public void setJornada_(String jornada_) {
		this.jornada_ = jornada_;
	}
	public String getSede_() {
		return sede_;
	}
	public void setSede_(String sede_) {
		this.sede_ = sede_;
	}
	public long getTipo() {
		return tipo;
	}
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
		

	
	
}
