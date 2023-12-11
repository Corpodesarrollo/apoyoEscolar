package util;

import com.google.gson.annotations.SerializedName;

public class LogPromocionDto extends LogCerrarPeriodoDto {

	@SerializedName("Nombre Asignatura")
	private String asignatura;
	
	public void setAsignatura(String asignatura){
		this.asignatura=asignatura;
	}
	public String getAsignatura(){
		return this.asignatura;
	}
	
	@SerializedName("Porcentaje")
	private String porcentaje;
	
	public void setPorcentaje(String porcentaje){
		this.porcentaje=porcentaje;
	}
	public String getPorcentaje(){
		return this.porcentaje;
	}
	
	@SerializedName("&Aacute;rea")
	private String area;
	
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return this.area;
	}
}
