package articulacion.inscripcion.vo;

import siges.common.vo.Vo;

public class LDiaVO extends Vo{
	private String dia;
	private int hora;
	private String horaIni;
	private String horaFin;
	private String espacioFisico;
	
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getEspacioFisico() {
		return espacioFisico;
	}
	public void setEspacioFisico(String espacioFisico) {
		this.espacioFisico = espacioFisico;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public String getHoraIni() {
		return horaIni;
	}
	public void setHoraIni(String horaIni) {
		this.horaIni = horaIni;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
}
