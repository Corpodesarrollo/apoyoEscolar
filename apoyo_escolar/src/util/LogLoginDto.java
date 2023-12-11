package util;

import com.google.gson.annotations.SerializedName;

public class LogLoginDto {
	@SerializedName("Fecha inicio sesi&oacute;n")
	private String fechaLogin;
	
	public void setFechaLogin(String fechaLogin){
		this.fechaLogin=fechaLogin;
	}
	
	public String getFechaLogin(){
		return this.fechaLogin;
	}
	
}
