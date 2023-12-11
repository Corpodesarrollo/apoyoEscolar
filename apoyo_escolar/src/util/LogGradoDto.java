package util;

import com.google.gson.annotations.SerializedName;

public class LogGradoDto {
	@SerializedName("Grado")
	private String grado;
	
	@SerializedName("Intensidad horaria")
	private String intensidadHoraria;
	
	@SerializedName("Estado")
	private String estado;
	
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	public String getIntensidadHoraria() {
		return intensidadHoraria;
	}
	public void setIntensidadHoraria(String intensidadHoraria) {
		this.intensidadHoraria = intensidadHoraria;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
