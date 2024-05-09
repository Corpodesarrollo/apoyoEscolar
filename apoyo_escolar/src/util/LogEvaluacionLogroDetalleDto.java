package util;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionLogroDetalleDto {

	@SerializedName("Logro")
	private String logro;

	public void setLogro(String logro){
		this.logro=logro;
	}

	public String getLogro(){
		return this.logro;
	}
	
	@SerializedName("Abreviatura")
	private String abreviatura;

	public void setAbreviatura(String abreviatura){
		this.abreviatura=abreviatura;
	}

	public String getAbreviatura(){
		return this.abreviatura;
	}
	
	@SerializedName("Respuesta")
	private String respuesta;

	public void setRespuesta(String respuesta){
		this.respuesta=respuesta;
	}

	public String getRespuesta(){
		return this.respuesta;
	}
	
}
