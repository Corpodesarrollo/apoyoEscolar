package articulacion.artPlantillaFinal.vo;

public class EvaluacionFinSemestreVO {
	
	private long codigoEstudiante;
	private long codigoAsignatura;
	
	private float notaNumericaFinal;
	private String notaConceptualFinal;
	
	private float notaNumericaNivelacion;
	private String notaConceptualNivelacion;
	
	private float notaNumericaSuperacion;
	private String notaConceptualSuperacion;
	
	public long getCodigoEstudiante() {
		return codigoEstudiante;
	}
	public void setCodigoEstudiante(long codigoEstudiante) {
		this.codigoEstudiante = codigoEstudiante;
	}
	public long getCodigoAsignatura() {
		return codigoAsignatura;
	}
	public void setCodigoAsignatura(long codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}
	
	public float getNotaNumericaFinal() {
		return notaNumericaFinal;
	}
	public void setNotaNumericaFinal(float notaNumericaFinal) {
		this.notaNumericaFinal = notaNumericaFinal;
	}
	public String getNotaConceptualFinal() {
		return notaConceptualFinal;
	}
	public void setNotaConceptualFinal(String notaConceptualFinal) {
		this.notaConceptualFinal = notaConceptualFinal;
	}
	public float getNotaNumericaNivelacion() {
		return notaNumericaNivelacion;
	}
	public void setNotaNumericaNivelacion(float notaNumericaNivelacion) {
		this.notaNumericaNivelacion = notaNumericaNivelacion;
	}
	public String getNotaConceptualNivelacion() {
		return notaConceptualNivelacion;
	}
	public void setNotaConceptualNivelacion(String notaConceptualNivelacion) {
		this.notaConceptualNivelacion = notaConceptualNivelacion;
	}
	public float getNotaNumericaSuperacion() {
		return notaNumericaSuperacion;
	}
	public void setNotaNumericaSuperacion(float notaNumericaSuperacion) {
		this.notaNumericaSuperacion = notaNumericaSuperacion;
	}
	public String getNotaConceptualSuperacion() {
		return notaConceptualSuperacion;
	}
	public void setNotaConceptualSuperacion(String notaConceptualSuperacion) {
		this.notaConceptualSuperacion = notaConceptualSuperacion;
	}
}
