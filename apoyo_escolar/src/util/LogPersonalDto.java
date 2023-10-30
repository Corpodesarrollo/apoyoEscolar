package util;

import com.google.gson.annotations.SerializedName;

public class LogPersonalDto {
	@SerializedName("Fotografia")
	private String foto;

	public void setFoto(String foto){
		this.foto=foto;
	}

	public String getFoto(){
		return this.foto;
	}

	@SerializedName("Correo")
	private String correo;

	public void setCorreo(String correo){
		this.correo=correo;
	}

	public String getCorreo(){
		return this.correo;
	}

	@SerializedName("Perfil")
	private String perfil;

	public void setPerfil(String perfil){
		this.perfil=perfil;
	}

	public String getPerfil(){
		return this.perfil;
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
	
	@SerializedName("Fecha")
	private String fecha;

	public void setFecha(String fecha){
		this.fecha=fecha;
	}

	public String getFecha(){
		return this.fecha;
	}

}
