package util;

import com.google.gson.annotations.SerializedName;

public class LogPlanEstudioDto {

	@SerializedName("identificador de registro")
	private String IdentificadorRegistro;
	private String vigencia;
	private String metodología;
	@SerializedName("criterio de evaluacion")
	private String criterioEvaluacion;
	@SerializedName("procedimiento de evaluacion")
	private String procedimientoEvaluacion;
	@SerializedName("planes de apoyo")
	private String planesApoyo;
	
	public String getIdentificadorRegistro() {
		return IdentificadorRegistro;
	}
	public void setIdentificadorRegistro(String identificadorRegistro) {
		IdentificadorRegistro = identificadorRegistro;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public String getMetodología() {
		return metodología;
	}
	public void setMetodología(String metodología) {
		this.metodología = metodología;
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
