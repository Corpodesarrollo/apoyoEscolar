package util;

import com.google.gson.annotations.SerializedName;

public class LogCierreSesionDto {
	@SerializedName("Tiempo de sesión")
	private String tiempoSesion;
	
	public void setTiempoSesion(String tiempoSesion){
		this.tiempoSesion=tiempoSesion;
	}
	
	public String getTiempoSesion(){
		return this.tiempoSesion;
	}
}
