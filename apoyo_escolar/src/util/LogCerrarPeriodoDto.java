package util;

import com.google.gson.annotations.SerializedName;

public class LogCerrarPeriodoDto {
	@SerializedName("Colegio")
	private String institucion;
	
	public void setInstitucion(String institucion){
		this.institucion=institucion;	
	}
	public String getInstitucion(){
		return this.institucion;
	}

	@SerializedName("Sede")
	private String sede;
	
	public void setSede(String sede){
		this.sede=sede;	
	}
	public String getSede(){
		return this.sede;
	}
	
	@SerializedName("Jornada")
	private String jornada;
	
	public void setJornada(String jornada){
		this.jornada=jornada;	
	}
	public String getJornada(){
		return this.jornada;
	}
	
	@SerializedName("Periodo")
	private String periodo;
	
	public void setPeriodo(String periodo){
		this.periodo=periodo;	
	}
	public String getPeriodo(){
		return this.periodo;
	}
	
	@SerializedName("Tipo")
	private String tipo;
	
	public void setTipo(String tipo){
		this.tipo=tipo;	
	}
	public String getTipo(){
		return this.tipo;
	}
	
}
