package articulacion.apcierArtic.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

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
	private long bimestre;

	
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
	public int getPerVigencia() {
		return perVigencia;
	}
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}
	public long getSede() {
		return sede;
	}
	public void setSede(long sede) {
		this.sede = sede;
	}
	public long getBimestre() {
		return bimestre;
	}
	public void setBimestre(long bimestre) {
		this.bimestre = bimestre;
	}
	
	
}
