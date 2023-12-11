package util;

import com.google.gson.annotations.SerializedName;

public class LogPlanEstudioDto {

	@SerializedName("Identificador de registro")
	private long IdentificadorRegistro;
	
	@SerializedName("Vigencia")
	private String vigencia;
	
	@SerializedName("Metodolog&iacute;a")
	private String metodologia;
	
	@SerializedName("Criterio de evaluaci&oacute;n")
	private String criterioEvaluacion;
	
	@SerializedName("Procedimiento de evaluaci&oacute;n")
	private String procedimientoEvaluacion;
	
	@SerializedName("Planes de apoyo")
	private String planesApoyo;
	
	
	public long getIdentificadorRegistro() {
		return IdentificadorRegistro;
	}
	public void setIdentificadorRegistro(long identificadorRegistro) {
		IdentificadorRegistro = identificadorRegistro;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	public String getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	public String getCriterioEvaluacion() {
		return criterioEvaluacion;
	}
	public void setCriterioEvaluacion(String criterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacion;
	}
	public String getProcedimientoEvaluacion() {
		return procedimientoEvaluacion;
	}
	public void setProcedimientoEvaluacion(String procedimientoEvaluacion) {
		this.procedimientoEvaluacion = procedimientoEvaluacion;
	}
	public String getPlanesApoyo() {
		return planesApoyo;
	}
	public void setPlanesApoyo(String planesApoyo) {
		this.planesApoyo = planesApoyo;
	}
}
