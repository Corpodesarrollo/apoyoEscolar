package articulacion.horarioEstudiante.vo;

import siges.common.vo.Vo;

public class FiltroInscripcionVO extends Vo{
	private int filComponente;
	private long filEspecialidad;
	private long filSemestre;
	private long filInstitucion;
	private int filSede;
	private int filJornada;
	private long filEstudiante;
	
	public int getFilComponente() {
		return filComponente;
	}
	public void setFilComponente(int filComponente) {
		this.filComponente = filComponente;
	}
	public long getFilEspecialidad() {
		return filEspecialidad;
	}
	public void setFilEspecialidad(long filEspecialidad) {
		this.filEspecialidad = filEspecialidad;
	}
	public long getFilEstudiante() {
		return filEstudiante;
	}
	public void setFilEstudiante(long filEstudiante) {
		this.filEstudiante = filEstudiante;
	}
	public long getFilInstitucion() {
		return filInstitucion;
	}
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	public int getFilJornada() {
		return filJornada;
	}
	public void setFilJornada(int filJornada) {
		this.filJornada = filJornada;
	}
	public int getFilSede() {
		return filSede;
	}
	public void setFilSede(int filSede) {
		this.filSede = filSede;
	}
	public long getFilSemestre() {
		return filSemestre;
	}
	public void setFilSemestre(long filSemestre) {
		this.filSemestre = filSemestre;
	}
}
