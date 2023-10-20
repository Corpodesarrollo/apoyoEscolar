package pei.registro.vo;

import siges.common.vo.Vo;

public class DesarrolloAdministrativoVO extends Vo{
	private long desInstitucion;

	private int desPoaConstruccion;
	private int desPoaComunidad;
	private int desPoaDecisiones;

	private int desEiConstruccion;
	private int desEiDecisiones;
	private int desEiFrecuencia;
	private int desEiOrientacion;
	private int desEiDocumento;
	private String desEiDocumentoCual;

	private int desEtapaAdmin;
	private String desDificultadAdmin;
	
	private int desEtapaManual;
	private String desDificultadManual;

	private int desEtapaInventario;
	private String desDificultadInventario;
	
	private int desEtapaEscolar;
	private String desDificultadEscolar;

	private int desEtapaComunicacion;
	private String desDificultadComunicacion;
	/* Ajustes Junio 2013 */
	private int desManualConvivencia;
	private int desReglamentoDocentes;
	private int desGobiernoEscolar;
	private int desDocumentoMatricula;
	private int desRelacionOrganizaciones;
	private String desOtrasOrganizaciones;
	private int desPorcentajeMecanismos;
	private int desPorcentajeExpresiones;
	private String desExpresiones;
	private int desPorcentajeCriteriosOrgAdmin;
	private String desfechaUltimaActualizacion;
	/* ****************** */
	private int desEstado;
	private String desEstadoNombre;
	private boolean desDisabled;
	private String desDisabled_;
	
	
	public int getDesManualConvivencia() {
		return desManualConvivencia;
	}
	public void setDesManualConvivencia(int desManualConvivencia) {
		this.desManualConvivencia = desManualConvivencia;
	}
	public int getDesReglamentoDocentes() {
		return desReglamentoDocentes;
	}
	public void setDesReglamentoDocentes(int desReglamentoDocentes) {
		this.desReglamentoDocentes = desReglamentoDocentes;
	}
	public int getDesGobiernoEscolar() {
		return desGobiernoEscolar;
	}
	public void setDesGobiernoEscolar(int desGobiernoEscolar) {
		this.desGobiernoEscolar = desGobiernoEscolar;
	}
	public int getDesDocumentoMatricula() {
		return desDocumentoMatricula;
	}
	public void setDesDocumentoMatricula(int desDocumentoMatricula) {
		this.desDocumentoMatricula = desDocumentoMatricula;
	}
	public int getDesRelacionOrganizaciones() {
		return desRelacionOrganizaciones;
	}
	public void setDesRelacionOrganizaciones(int desRelacionOrganizaciones) {
		this.desRelacionOrganizaciones = desRelacionOrganizaciones;
	}
	public String getDesOtrasOrganizaciones() {
		return desOtrasOrganizaciones;
	}
	public void setDesOtrasOrganizaciones(String desOtrasOrganizaciones) {
		this.desOtrasOrganizaciones = desOtrasOrganizaciones;
	}
	public int getDesPorcentajeMecanismos() {
		return desPorcentajeMecanismos;
	}
	public void setDesPorcentajeMecanismos(int desPorcentajeMecanismos) {
		this.desPorcentajeMecanismos = desPorcentajeMecanismos;
	}
	public int getDesPorcentajeExpresiones() {
		return desPorcentajeExpresiones;
	}
	public void setDesPorcentajeExpresiones(int desPorcentajeExpresiones) {
		this.desPorcentajeExpresiones = desPorcentajeExpresiones;
	}
	public String getDesExpresiones() {
		return desExpresiones;
	}
	public void setDesExpresiones(String desExpresiones) {
		this.desExpresiones = desExpresiones;
	}
	public int getDesPorcentajeCriteriosOrgAdmin() {
		return desPorcentajeCriteriosOrgAdmin;
	}
	public void setDesPorcentajeCriteriosOrgAdmin(int desPorcentajeCriteriosOrgAdmin) {
		this.desPorcentajeCriteriosOrgAdmin = desPorcentajeCriteriosOrgAdmin;
	}
	public String getDesfechaUltimaActualizacion() {
		return desfechaUltimaActualizacion;
	}
	public void setDesfechaUltimaActualizacion(String desfechaUltimaActualizacion) {
		this.desfechaUltimaActualizacion = desfechaUltimaActualizacion;
	}
	
	
	/**
	 * @return the desInstitucion
	 */
	public long getDesInstitucion() {
		return desInstitucion;
	}
	/**
	 * @param desInstitucion the desInstitucion to set
	 */
	public void setDesInstitucion(long desInstitucion) {
		this.desInstitucion = desInstitucion;
	}
	/**
	 * @return the desPoaConstruccion
	 */
	public int getDesPoaConstruccion() {
		return desPoaConstruccion;
	}
	/**
	 * @param desPoaConstruccion the desPoaConstruccion to set
	 */
	public void setDesPoaConstruccion(int desPoaConstruccion) {
		this.desPoaConstruccion = desPoaConstruccion;
	}
	/**
	 * @return the desPoaComunidad
	 */
	public int getDesPoaComunidad() {
		return desPoaComunidad;
	}
	/**
	 * @param desPoaComunidad the desPoaComunidad to set
	 */
	public void setDesPoaComunidad(int desPoaComunidad) {
		this.desPoaComunidad = desPoaComunidad;
	}
	/**
	 * @return the desPoaDecisiones
	 */
	public int getDesPoaDecisiones() {
		return desPoaDecisiones;
	}
	/**
	 * @param desPoaDecisiones the desPoaDecisiones to set
	 */
	public void setDesPoaDecisiones(int desPoaDecisiones) {
		this.desPoaDecisiones = desPoaDecisiones;
	}
	/**
	 * @return the desEiConstruccion
	 */
	public int getDesEiConstruccion() {
		return desEiConstruccion;
	}
	/**
	 * @param desEiConstruccion the desEiConstruccion to set
	 */
	public void setDesEiConstruccion(int desEiConstruccion) {
		this.desEiConstruccion = desEiConstruccion;
	}
	/**
	 * @return the desEiDecisiones
	 */
	public int getDesEiDecisiones() {
		return desEiDecisiones;
	}
	/**
	 * @param desEiDecisiones the desEiDecisiones to set
	 */
	public void setDesEiDecisiones(int desEiDecisiones) {
		this.desEiDecisiones = desEiDecisiones;
	}
	/**
	 * @return the desEiFrecuencia
	 */
	public int getDesEiFrecuencia() {
		return desEiFrecuencia;
	}
	/**
	 * @param desEiFrecuencia the desEiFrecuencia to set
	 */
	public void setDesEiFrecuencia(int desEiFrecuencia) {
		this.desEiFrecuencia = desEiFrecuencia;
	}
	/**
	 * @return the desEiOrientacion
	 */
	public int getDesEiOrientacion() {
		return desEiOrientacion;
	}
	/**
	 * @param desEiOrientacion the desEiOrientacion to set
	 */
	public void setDesEiOrientacion(int desEiOrientacion) {
		this.desEiOrientacion = desEiOrientacion;
	}
	/**
	 * @return the desEiDocumento
	 */
	public int getDesEiDocumento() {
		return desEiDocumento;
	}
	/**
	 * @param desEiDocumento the desEiDocumento to set
	 */
	public void setDesEiDocumento(int desEiDocumento) {
		this.desEiDocumento = desEiDocumento;
	}
	/**
	 * @return the desEiDocumentoCual
	 */
	public String getDesEiDocumentoCual() {
		return desEiDocumentoCual;
	}
	/**
	 * @param desEiDocumentoCual the desEiDocumentoCual to set
	 */
	public void setDesEiDocumentoCual(String desEiDocumentoCual) {
		this.desEiDocumentoCual = desEiDocumentoCual;
	}
	/**
	 * @return the desEtapaAdmin
	 */
	public int getDesEtapaAdmin() {
		return desEtapaAdmin;
	}
	/**
	 * @param desEtapaAdmin the desEtapaAdmin to set
	 */
	public void setDesEtapaAdmin(int desEtapaAdmin) {
		this.desEtapaAdmin = desEtapaAdmin;
	}
	/**
	 * @return the desDificultadAdmin
	 */
	public String getDesDificultadAdmin() {
		return desDificultadAdmin;
	}
	/**
	 * @param desDificultadAdmin the desDificultadAdmin to set
	 */
	public void setDesDificultadAdmin(String desDificultadAdmin) {
		this.desDificultadAdmin = desDificultadAdmin;
	}
	/**
	 * @return the desEtapaManual
	 */
	public int getDesEtapaManual() {
		return desEtapaManual;
	}
	/**
	 * @param desEtapaManual the desEtapaManual to set
	 */
	public void setDesEtapaManual(int desEtapaManual) {
		this.desEtapaManual = desEtapaManual;
	}
	/**
	 * @return the desDificultadManual
	 */
	public String getDesDificultadManual() {
		return desDificultadManual;
	}
	/**
	 * @param desDificultadManual the desDificultadManual to set
	 */
	public void setDesDificultadManual(String desDificultadManual) {
		this.desDificultadManual = desDificultadManual;
	}
	/**
	 * @return the desEtapaInventario
	 */
	public int getDesEtapaInventario() {
		return desEtapaInventario;
	}
	/**
	 * @param desEtapaInventario the desEtapaInventario to set
	 */
	public void setDesEtapaInventario(int desEtapaInventario) {
		this.desEtapaInventario = desEtapaInventario;
	}
	/**
	 * @return the desDificultadInventario
	 */
	public String getDesDificultadInventario() {
		return desDificultadInventario;
	}
	/**
	 * @param desDificultadInventario the desDificultadInventario to set
	 */
	public void setDesDificultadInventario(String desDificultadInventario) {
		this.desDificultadInventario = desDificultadInventario;
	}
	/**
	 * @return the desEtapaEscolar
	 */
	public int getDesEtapaEscolar() {
		return desEtapaEscolar;
	}
	/**
	 * @param desEtapaEscolar the desEtapaEscolar to set
	 */
	public void setDesEtapaEscolar(int desEtapaEscolar) {
		this.desEtapaEscolar = desEtapaEscolar;
	}
	/**
	 * @return the desDificultadEscolar
	 */
	public String getDesDificultadEscolar() {
		return desDificultadEscolar;
	}
	/**
	 * @param desDificultadEscolar the desDificultadEscolar to set
	 */
	public void setDesDificultadEscolar(String desDificultadEscolar) {
		this.desDificultadEscolar = desDificultadEscolar;
	}
	/**
	 * @return the desEtapaComunicacion
	 */
	public int getDesEtapaComunicacion() {
		return desEtapaComunicacion;
	}
	/**
	 * @param desEtapaComunicacion the desEtapaComunicacion to set
	 */
	public void setDesEtapaComunicacion(int desEtapaComunicacion) {
		this.desEtapaComunicacion = desEtapaComunicacion;
	}
	/**
	 * @return the desDificultadComunicacion
	 */
	public String getDesDificultadComunicacion() {
		return desDificultadComunicacion;
	}
	/**
	 * @param desDificultadComunicacion the desDificultadComunicacion to set
	 */
	public void setDesDificultadComunicacion(String desDificultadComunicacion) {
		this.desDificultadComunicacion = desDificultadComunicacion;
	}
	/**
	 * @return the desEstado
	 */
	public int getDesEstado() {
		return desEstado;
	}
	/**
	 * @param desEstado the desEstado to set
	 */
	public void setDesEstado(int desEstado) {
		this.desEstado = desEstado;
	}
	/**
	 * @return the desEstadoNombre
	 */
	public String getDesEstadoNombre() {
		return desEstadoNombre;
	}
	/**
	 * @param desEstadoNombre the desEstadoNombre to set
	 */
	public void setDesEstadoNombre(String desEstadoNombre) {
		this.desEstadoNombre = desEstadoNombre;
	}
	/**
	 * @return the desDisabled
	 */
	public boolean isDesDisabled() {
		return desDisabled;
	}
	/**
	 * @param desDisabled the desDisabled to set
	 */
	public void setDesDisabled(boolean desDisabled) {
		this.desDisabled = desDisabled;
	}
	/**
	 * @return the desDisabled_
	 */
	public String getDesDisabled_() {
		return desDisabled_;
	}
	/**
	 * @param desDisabled the desDisabled_ to set
	 */
	public void setDesDisabled_(String desDisabled) {
		desDisabled_ = desDisabled;
	}
}
