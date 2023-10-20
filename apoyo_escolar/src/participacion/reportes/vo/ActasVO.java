package participacion.reportes.vo;

import siges.common.vo.Vo;

/**
 * Value Object del manejo del formulario de ingreso/edicinn de actividades
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ActasVO  extends Vo{

	private int itemNivel;
	private String daneColegio;
	private String nombreColegio;
	private String nombreInstancia;	
	private String fecha;	
	private String lugar;	
	private String asunto;	
	private String fechaElaboracion;	
	private String actividades;
	private String acuerdos;
	private String compromisos;
	private String responsabilidades;
	
	public String getActividades() {
		return actividades;
	}
	public void setActividades(String actividades) {
		this.actividades = actividades;
	}
	public String getAcuerdos() {
		return acuerdos;
	}
	public void setAcuerdos(String acuerdos) {
		this.acuerdos = acuerdos;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCompromisos() {
		return compromisos;
	}
	public void setCompromisos(String compromisos) {
		this.compromisos = compromisos;
	}
	public String getDaneColegio() {
		return daneColegio;
	}
	public void setDaneColegio(String daneColegio) {
		this.daneColegio = daneColegio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFechaElaboracion() {
		return fechaElaboracion;
	}
	public void setFechaElaboracion(String fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}
	public int getItemNivel() {
		return itemNivel;
	}
	public void setItemNivel(int itemNivel) {
		this.itemNivel = itemNivel;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getNombreColegio() {
		return nombreColegio;
	}
	public void setNombreColegio(String nombreColegio) {
		this.nombreColegio = nombreColegio;
	}
	public String getNombreInstancia() {
		return nombreInstancia;
	}
	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}
	public String getResponsabilidades() {
		return responsabilidades;
	}
	public void setResponsabilidades(String responsabilidades) {
		this.responsabilidades = responsabilidades;
	}
	
}
