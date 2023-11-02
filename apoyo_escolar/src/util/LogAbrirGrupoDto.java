package util;

import com.google.gson.annotations.SerializedName;

public class LogAbrirGrupoDto extends LogCerrarPeriodoDto{
	@SerializedName("Metodologia")
	private String metodologia;
	
	public void setMetodologia(String metodologia){
		this.metodologia=metodologia;
	}
	public String getMetodologia(){
		return this.metodologia;
	}
	
	@SerializedName("Area")
	private String area;
	
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return this.area;
	}
	
	@SerializedName("Dimension")
	private String dimension;
	
	public void setDimension(String dimension){
		this.dimension=dimension;
	}
	public String getDimension(){
		return this.dimension;
	}
	
	@SerializedName("Asignatura")
	private String asignatura;
	
	public void setAsignatura(String asignatura){
		this.asignatura=asignatura;
	}
	public String getAsginatura(){
		return this.asignatura;
	}
	
	@SerializedName("Abrir grupo por")
	private String abrirPor;
	
	public void setAbrirPor(String abrirPor){
		this.abrirPor=abrirPor;
	}
	public String getbrirPor(){
		return this.abrirPor;
	}
	
	@SerializedName("Grado")
	private String grado;
	
	public void setGrado(String grado){
		this.grado=grado;
	}
	public String getGrado(){
		return this.grado;
	}
	
	@SerializedName("Grupo")
	private String grupo;
	
	public void setGrupo(String grupo){
		this.grupo=grupo;
	}
	public String getGrupo(){
		return this.grupo;
	}
	
}
