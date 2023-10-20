package articulacion.plantillaEvaluacion.vo;

import siges.common.vo.Vo;

public class DatosPlanEvalVO extends Vo{

	private long institucion;
	private long sede;
	private long jornada;
	private long metodologia;
	private int anVigencia;
	private int perVigencia;
	private long componente;
	private long especialidad;
	private long grupo;
	private long asignatura;
	private int prueba;
	private int ordenado;
	private int bimestre;
	
	private long porcentaje0;
	
	private String  institucion_;
	private String  sede_;
	private String  jornada_;
	private String  asignatura_;
	private String  grupo_;
	private String  prueba_;
	private String  fecha_;
	
	private String  abreviatura;
	private String  nombre;
	
	public int getAnVigencia() {
		return anVigencia;
	}
	public void setAnVigencia(int anVigencia) {
		this.anVigencia = anVigencia;
	}
	public long getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(long asignatura) {
		this.asignatura = asignatura;
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
	public long getGrupo() {
		return grupo;
	}
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	public long getInstitucion() {
		return institucion;
	}
	public void setInstitucion(long institucion) {
		this.institucion = institucion;
	}
	public long getJornada() {
		return jornada;
	}
	public void setJornada(long jornada) {
		this.jornada = jornada;
	}
	public long getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(long metodologia) {
		this.metodologia = metodologia;
	}
	public int getOrdenado() {
		return ordenado;
	}
	public void setOrdenado(int ordenado) {
		this.ordenado = ordenado;
	}
	public int getPerVigencia() {
		return perVigencia;
	}
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}
	public int getPrueba() {
		return prueba;
	}
	public void setPrueba(int prueba) {
		this.prueba = prueba;
	}
	public long getSede() {
		return sede;
	}
	public void setSede(long sede) {
		this.sede = sede;
	}
	public String getAsignatura_() {
		return asignatura_;
	}
	public void setAsignatura_(String asignatura_) {
		this.asignatura_ = asignatura_;
	}
	public String getFecha_() {
		return fecha_;
	}
	public void setFecha_(String fecha_) {
		this.fecha_ = fecha_;
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
	public String getPrueba_() {
		return prueba_;
	}
	public void setPrueba_(String prueba_) {
		this.prueba_ = prueba_;
	}
	public String getSede_() {
		return sede_;
	}
	public void setSede_(String sede_) {
		this.sede_ = sede_;
	}
	public long getPorcentaje0() {
		return porcentaje0;
	}
	public void setPorcentaje0(long porcentage0) {
		this.porcentaje0 = porcentage0;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Return the bimestre.
	 */
	public int getBimestre() {
		return bimestre;
	}
	/**
	 * @param bimestre The bimestre to set.
	 */
	public void setBimestre(int bimestre) {
		this.bimestre = bimestre;
	}
	
	
}
