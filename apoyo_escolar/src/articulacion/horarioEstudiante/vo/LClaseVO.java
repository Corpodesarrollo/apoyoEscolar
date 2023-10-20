package articulacion.horarioEstudiante.vo;

import java.util.List;

public class LClaseVO {
	private int clase;
	private String hora;
	private List dia;
	
	/**
	 * @return Returns the clase.
	 */
	public int getClase() {
		return clase;
	}
	/**
	 * @param clase The clase to set.
	 */
	public void setClase(int clase) {
		this.clase = clase;
	}
	/**
	 * @return Returns the dia.
	 */
	public List getDia() {
		return dia;
	}
	/**
	 * @param dia The dia to set.
	 */
	public void setDia(List dia) {
		this.dia = dia;
	}
	/**
	 * @return Returns the hora.
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * @param hora The hora to set.
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	
}
