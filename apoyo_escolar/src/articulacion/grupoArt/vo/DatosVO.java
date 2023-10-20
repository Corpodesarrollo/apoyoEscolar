package articulacion.grupoArt.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private int institucion;
	private int sede;
	private int jornada;
	private int anVigencia;
	private int perVigencia;
	private int periodo;
	private int componente;
	private int especialidad;
	

	
	public int getAnVigencia() {
		return anVigencia;
	}
	public void setAnVigencia(int anVigencia) {
		this.anVigencia = anVigencia;
	}
	public int getComponente() {
		return componente;
	}
	public void setComponente(int componente) {
		this.componente = componente;
	}
	public int getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(int especialidad) {
		this.especialidad = especialidad;
	}
	public int getJornada() {
		return jornada;
	}
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getPerVigencia() {
		return perVigencia;
	}
	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}
	public int getSede() {
		return sede;
	}
	public void setSede(int sede) {
		this.sede = sede;
	}
	public int getInstitucion() {
		return institucion;
	}
	public void setInstitucion(int institucion) {
		this.institucion = institucion;
	}
	

	
}
