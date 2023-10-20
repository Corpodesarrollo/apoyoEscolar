/**
 * 
 */
package siges.permisos.vo;

import siges.common.vo.Vo;


public class BitacoraVO  extends Vo{
	 
	private String usuario;
	private String fechainicio;
	private String fechafin;
	
	private int generado = 0;
	
	private String fileName;
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getGenerado() {
		return generado;
	}
	public void setGenerado(int generado) {
		this.generado = generado;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
}
