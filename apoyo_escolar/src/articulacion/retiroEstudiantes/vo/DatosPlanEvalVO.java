package articulacion.retiroEstudiantes.vo;

import siges.common.vo.Vo;

public class DatosPlanEvalVO extends Vo{

	private long institucion;
	private long sede;
	private long semestre;
	private long jornada;
	private long metodologia;
	private int anVigencia;
	private int perVigencia;
	private long componente;
	private long especialidad;
	private long grupo;
	private long estudiante;
	
	
	
	private String  institucion_;
	private String  sede_;
	private String  jornada_;
	private String  asignatura_;
	private String  grupo_;
	private String  prueba_;
	private String  fecha_;
	public int getAnVigencia() {
		return anVigencia;
	}
	public void setAnVigencia(int anVigencia) {
		this.anVigencia = anVigencia;
	}
	public String getAsignatura_() {
		return asignatura_;
	}
	public void setAsignatura_(String asignatura_) {
		this.asignatura_ = asignatura_;
	}
	public long getComponente() {
		return componente;
	}
	public void setComponente(long componente) {
		this.componente = componente;
	}
	public long getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(long especialidad) {
		this.especialidad = especialidad;
	}
	public long getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(long estudiante) {
		this.estudiante = estudiante;
	}
	public String getFecha_() {
		return fecha_;
	}
	public void setFecha_(String fecha_) {
		this.fecha_ = fecha_;
	}
	public long getGrupo() {
		return grupo;
	}
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	public String getGrupo_() {
		return grupo_;
	}
	public void setGrupo_(String grupo_) {
		this.grupo_ = grupo_;
	}
	public long getInstitucion() {
		return institucion;
	}
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	public String getInstitucion_() {
		return institucion_;
	}
	public void setInstitucion_(String institucion_) {
		this.institucion_ = institucion_;
	}
	public long getJornada() {
		return jornada;
	}
	public void setJornada(long jornada) {
		this.jornada = jornada;
	}
	public String getJornada_() {
		return jornada_;
	}
	public void setJornada_(String jornada_) {
		this.jornada_ = jornada_;
	}
	public long getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(long metodologia) {
		this.metodologia = metodologia;
	}
	public int getPerVigencia() {
		return perVigencia;
	}
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}
	public String getPrueba_() {
		return prueba_;
	}
	public void setPrueba_(String prueba_) {
		this.prueba_ = prueba_;
	}
	public long getSede() {
		return sede;
	}
	public void setSede(long sede) {
		this.sede = sede;
	}
	public String getSede_() {
		return sede_;
	}
	public void setSede_(String sede_) {
		this.sede_ = sede_;
	}
	public long getSemestre() {
		return semestre;
	}
	public void setSemestre(long semestre) {
		this.semestre = semestre;
	}
	
	
}
