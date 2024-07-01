package util;

import com.google.gson.annotations.SerializedName;

public class LogEvaluacionDescripDetalleDto {

	@SerializedName("Descriptor")
	private String descriptor;

	public void setDescriptor(String descriptor){
		this.descriptor=descriptor;
	}

	public String getDescriptor(){
		return this.descriptor;
	}
	
	@SerializedName("Abreviatura")
	private String abreviatura;

	public void setAbreviatura(String abreviatura){
		this.abreviatura=abreviatura;
	}

	public String getAbreviatura(){
		return this.abreviatura;
	}
	
}
