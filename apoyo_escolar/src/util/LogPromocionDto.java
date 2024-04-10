package util;

import com.google.gson.annotations.SerializedName;

public class LogPromocionDto {

	@SerializedName("Metodolog&iacute;a")
	private String metodologia;
	
	public void setMetodologia(String metodologia){
		this.metodologia=metodologia;
	}
	public String getMetodologia(){
		return this.metodologia;
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
	
	@SerializedName("Tipo Documento")
	private String tipoDocumento;

	public void setTipodocumento(String tipoDocumento){
		this.tipoDocumento=tipoDocumento;
	}

	public String getTipodocumento(){
		return this.tipoDocumento;
	}
	
	@SerializedName("N&uacute;mero Identificaci&oacute;n")
	private String numeroIdentificacion;

	public void setNumeroIdentificacion(String numeroIdentificacion){
		this.numeroIdentificacion=numeroIdentificacion;
	}

	public String getNumeroIdentificacion(){
		return this.numeroIdentificacion;
	}
	
	@SerializedName("Nombres y Apellidos")
	private String nombreCompleto;

	public void setNombreCompleto(String nombreCompleto){
		this.nombreCompleto=nombreCompleto;
	}
	
	public String getNombreCompleto(){
		return this.nombreCompleto;
	}
	
	@SerializedName("Promoci&oacute;n")
	private String promocion;

	public void setPromocion(String promocion){
		this.promocion=promocion;
	}

	public String getPromocion(){
		return this.promocion;
	}
	
	@SerializedName("Fecha")
	private String fecha;

	public void setFecha(String fecha){
		this.fecha=fecha;
	}

	public String getFecha(){
		return this.fecha;
	}
	
	@SerializedName("Observaci&oacute;n")
	private String observacion;

	public void setObservacion(String observacion){
		this.observacion=observacion;
	}

	public String getObservacion(){
		return this.observacion;
	}
	
}
