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
	
	@SerializedName("Evaluaci&oacute;n")
	private String evaluacion;

	public void setEvaluacion(String evaluacion){
		this.evaluacion=evaluacion;
	}

	public String getEvaluacion(){
		return this.evaluacion;
	}
	
}
