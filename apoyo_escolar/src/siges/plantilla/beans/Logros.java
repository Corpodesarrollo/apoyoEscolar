package siges.plantilla.beans;

/** siges.plantilla.beans<br>
 * Funcinn: Objeto de datos que contiene la informacion solicitada en los formularios de filtro de plantillas   
 * <br>
 */
public class Logros {
	private String plantillaInstitucion;
	private String plantillaInstitucion_;
	private String plantillaGrado;
	private String plantillaGrado_;
	private String plantillaAsignatura;
	private String plantillaAsignatura_;
	private String plantillaMetodologia;
	private String plantillaMetodologia_;
	private String tipoPlantilla;
	private String nivelLogro;
	private String jerarquiaLogro;
	private int estadoPlanEstudios;	
	private int plantillaVigencia;
	private long plantillaDocente;
	private String plantillaDocente_;

	
	/**
	 * @return Returns the jerarquiaLogro.
	 */
	public String getJerarquiaLogro() {
		return jerarquiaLogro!=null?jerarquiaLogro:"";
	}
	/**
	 * @param jerarquiaLogro The jerarquiaLogro to set.
	 */
	public void setJerarquiaLogro(String jerarquiaLogro) {
		this.jerarquiaLogro = jerarquiaLogro;
	}
	/**
	 * @return Returns the nivelLogro.
	 */
	public String getNivelLogro() {
		return nivelLogro!=null?nivelLogro:"";
	}
	/**
	 * @param nivelLogro The nivelLogro to set.
	 */
	public void setNivelLogro(String nivelLogro) {
		this.nivelLogro = nivelLogro;
	}
	/**
	 * @return Returns the tipoPlantilla.
	 */
	public String getTipoPlantilla() {
		return tipoPlantilla!=null?tipoPlantilla:"";
	}
	/**
	 * @param tipoPlantilla The tipoPlantilla to set.
	 */
	public void setTipoPlantilla(String tipoPlantilla) {
		this.tipoPlantilla = tipoPlantilla;
	}
	
	/**
	 * @return Returns the plantillaAsignatura.
	 */
	public String getPlantillaMetodologia() {
		return plantillaMetodologia!=null?plantillaMetodologia:"";
	}
	/**
	 * @param plantillaAsignatura The plantillaAsignatura to set.
	 */
	public void setPlantillaMetodologia(String plantillaMetodologia) {
		this.plantillaMetodologia = plantillaMetodologia;
	}

	/**
	 * @return Returns the plantillaAsignatura.
	 */
	public String getPlantillaAsignatura() {
		return plantillaAsignatura!=null?plantillaAsignatura:"";
	}
	/**
	 * @param plantillaAsignatura The plantillaAsignatura to set.
	 */
	public void setPlantillaAsignatura(String plantillaAsignatura) {
		this.plantillaAsignatura = plantillaAsignatura;
	}
	/**
	 * @return Returns the plantillaGrado.
	 */
	public String getPlantillaGrado() {
		return plantillaGrado!=null?plantillaGrado:"";
	}
	/**
	 * @param plantillaGrado The plantillaGrado to set.
	 */
	public void setPlantillaGrado(String plantillaGrado) {
		this.plantillaGrado = plantillaGrado;
	}
	/**
	 * @return Returns the plantillaInstitucion.
	 */
	public String getPlantillaInstitucion() {
		return plantillaInstitucion!=null?plantillaInstitucion:"";
	}
	/**
	 * @param plantillaInstitucion The plantillaInstitucion to set.
	 */
	public void setPlantillaInstitucion(String plantillaInstitucion) {
		this.plantillaInstitucion = plantillaInstitucion;
	}
/**
 * @return Returns the plantillaAsignatura.
 */
public String getPlantillaMetodologia_() {
	return plantillaMetodologia_!=null?plantillaMetodologia_:"";
}
/**
 * @param plantillaAsignatura The plantillaAsignatura to set.
 */
public void setPlantillaMetodologia_(String plantillaMetodologia_) {
	this.plantillaMetodologia_ = plantillaMetodologia_;
}

/**
 * @return Returns the plantillaAsignatura.
 */
public String getPlantillaAsignatura_() {
	return plantillaAsignatura_!=null?plantillaAsignatura_:"";
}
/**
 * @param plantillaAsignatura The plantillaAsignatura to set.
 */
public void setPlantillaAsignatura_(String plantillaAsignatura_) {
	this.plantillaAsignatura_ = plantillaAsignatura_;
}
/**
 * @return Returns the plantillaGrado.
 */
public String getPlantillaGrado_() {
	return plantillaGrado_!=null?plantillaGrado_:"";
}
/**
 * @param plantillaGrado The plantillaGrado to set.
 */
public void setPlantillaGrado_(String plantillaGrado_) {
	this.plantillaGrado_ = plantillaGrado_;
}
	/**
	 * @return Returns the plantillaInstitucion_.
	 */
	public String getPlantillaInstitucion_() {
		return plantillaInstitucion_!=null?plantillaInstitucion_:"";
	}
	/**
	 * @param plantillaInstitucion_ The plantillaInstitucion_ to set.
	 */
	public void setPlantillaInstitucion_(String plantillaInstitucion_) {
		this.plantillaInstitucion_ = plantillaInstitucion_;
	}
	/**
	 * @return Return the estadoPlanEstudios.
	 */
	public int getEstadoPlanEstudios() {
		return estadoPlanEstudios;
	}
	/**
	 * @param estadoPlanEstudios The estadoPlanEstudios to set.
	 */
	public void setEstadoPlanEstudios(int estadoPlanEstudios) {
		this.estadoPlanEstudios = estadoPlanEstudios;
	}
	/**
	 * @return Return the plantillaDocente.
	 */
	public long getPlantillaDocente() {
		return plantillaDocente;
	}
	/**
	 * @param plantillaDocente The plantillaDocente to set.
	 */
	public void setPlantillaDocente(long plantillaDocente) {
		this.plantillaDocente = plantillaDocente;
	}
	/**
	 * @return Return the plantillaVigencia.
	 */
	public int getPlantillaVigencia() {
		return plantillaVigencia;
	}
	/**
	 * @param plantillaVigencia The plantillaVigencia to set.
	 */
	public void setPlantillaVigencia(int plantillaVigencia) {
		this.plantillaVigencia = plantillaVigencia;
	}
	/**
	 * @return Return the plantillaDocente_.
	 */
	public String getPlantillaDocente_() {
		return plantillaDocente_;
	}
	/**
	 * @param plantillaDocente_ The plantillaDocente_ to set.
	 */
	public void setPlantillaDocente_(String plantillaDocente_) {
		this.plantillaDocente_ = plantillaDocente_;
	}
}

