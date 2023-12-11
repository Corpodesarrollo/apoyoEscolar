package util;

import com.google.gson.annotations.SerializedName;

public class EstudianteEvalDto {

	@SerializedName("C&oacute;digo")
	private Long codigo;

	public void setCodigo(Long codigo){
		this.codigo=codigo;
	}

	public Long getCodigo(){
		return this.codigo;
	}

	@SerializedName("Tipo de identificaci&oacute;n")
	private String tipoIdentificacion;

	public void setTipoIdentificacion(String tipoIdentificacion){
		this.tipoIdentificacion=tipoIdentificacion;
	}

	public String getTipoIdentificacion(){
		return this.tipoIdentificacion;
	}

	@SerializedName("N&uacute;mero de identificaci&oacute;n")
	private String numeroIdentificacion;

	public void setNumeroIdentificacion(String numeroIdentificacion){
		this.numeroIdentificacion=numeroIdentificacion;
	}

	public String getNumeroIdentificacion(){
		return this.numeroIdentificacion;
	}

	@SerializedName("Nombre completo")
	private String nombreCompleto;

	public void setNombreCompleto(String nombreCompleto){
		this.nombreCompleto=nombreCompleto;
	}

	public String getNombreCompleto(){
		return this.nombreCompleto;
	}

	@SerializedName("Nota")
	private Float nota;

	public void setNota(Float nota){
		this.nota=nota;
	}

	public Float getNota(){
		return this.nota;
	}
		
}
