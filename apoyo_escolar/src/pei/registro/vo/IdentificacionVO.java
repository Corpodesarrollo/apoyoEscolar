package pei.registro.vo;

import siges.common.vo.Vo;

public class IdentificacionVO extends Vo{
	private long idenInstitucion;
	private long idenEstudiantes;
	private int idenJornadas;
	private int idenSedes;
	private int idenExiste;
	private String idenNombre;
	private int idenEtapa;
	private int idenEnfasis;
	private String idenEnfasisOtro;
	private int idenEnfoque;
	private String idenEnfoqueOtro;
	private int idenEstado;
	private String idenEstadoNombre;
	private boolean idenDisabled;
	private String idenDisabled_;
	
	private String idenRector;
	private String idenTelefono;
	private String idenCorreo;
	
	/*Cambios PEI 2013*/
	private int idenCaracter = 1; // 1 = Urbano - 2 Rural
	private int idenEducacionFormalAdulos = 0; //1 = Si - 2 = No
	private int idenCantidadEducacionFormalAdultos = 0;
	private int idenAceleracionAprendizaje = 0; //1 = Si - 2 = No
	private int idenCantidadAceleracionAprendizaje = 0;
	
	
	/**
	 * @return the idenInstitucion
	 */
	public long getIdenInstitucion() {
		return idenInstitucion;
	}
	/**
	 * @param idenInstitucion the idenInstitucion to set
	 */
	public void setIdenInstitucion(long idenInstitucion) {
		this.idenInstitucion = idenInstitucion;
	}
	/**
	 * @return the idenEstudiantes
	 */
	public long getIdenEstudiantes() {
		return idenEstudiantes;
	}
	/**
	 * @param idenEstudiantes the idenEstudiantes to set
	 */
	public void setIdenEstudiantes(long idenEstudiantes) {
		this.idenEstudiantes = idenEstudiantes;
	}
	/**
	 * @return the idenJornadas
	 */
	public int getIdenJornadas() {
		return idenJornadas;
	}
	/**
	 * @param idenJornadas the idenJornadas to set
	 */
	public void setIdenJornadas(int idenJornadas) {
		this.idenJornadas = idenJornadas;
	}
	/**
	 * @return the idenExiste
	 */
	public int getIdenExiste() {
		return idenExiste;
	}
	/**
	 * @param idenExiste the idenExiste to set
	 */
	public void setIdenExiste(int idenExiste) {
		this.idenExiste = idenExiste;
	}
	/**
	 * @return the idenNombre
	 */
	public String getIdenNombre() {
		return idenNombre;
	}
	/**
	 * @param idenNombre the idenNombre to set
	 */
	public void setIdenNombre(String idenNombre) {
		this.idenNombre = idenNombre;
	}
	/**
	 * @return the idenEtapa
	 */
	public int getIdenEtapa() {
		return idenEtapa;
	}
	/**
	 * @param idenEtapa the idenEtapa to set
	 */
	public void setIdenEtapa(int idenEtapa) {
		this.idenEtapa = idenEtapa;
	}
	/**
	 * @return the idenEnfasis
	 */
	public int getIdenEnfasis() {
		return idenEnfasis;
	}
	/**
	 * @param idenEnfasis the idenEnfasis to set
	 */
	public void setIdenEnfasis(int idenEnfasis) {
		this.idenEnfasis = idenEnfasis;
	}
	/**
	 * @return the idenEnfasisOtro
	 */
	public String getIdenEnfasisOtro() {
		return idenEnfasisOtro;
	}
	/**
	 * @param idenEnfasisOtro the idenEnfasisOtro to set
	 */
	public void setIdenEnfasisOtro(String idenEnfasisOtro) {
		this.idenEnfasisOtro = idenEnfasisOtro;
	}
	/**
	 * @return the idenEnfoque
	 */
	public int getIdenEnfoque() {
		return idenEnfoque;
	}
	/**
	 * @param idenEnfoque the idenEnfoque to set
	 */
	public void setIdenEnfoque(int idenEnfoque) {
		this.idenEnfoque = idenEnfoque;
	}
	/**
	 * @return the idenEnfoqueOtro
	 */
	public String getIdenEnfoqueOtro() {
		return idenEnfoqueOtro;
	}
	/**
	 * @param idenEnfoqueOtro the idenEnfoqueOtro to set
	 */
	public void setIdenEnfoqueOtro(String idenEnfoqueOtro) {
		this.idenEnfoqueOtro = idenEnfoqueOtro;
	}
	/**
	 * @return the idenRector
	 */
	public String getIdenRector() {
		return idenRector;
	}
	/**
	 * @param idenRector the idenRector to set
	 */
	public void setIdenRector(String idenRector) {
		this.idenRector = idenRector;
	}
	/**
	 * @return the idenTelefono
	 */
	public String getIdenTelefono() {
		return idenTelefono;
	}
	/**
	 * @param idenTelefono the idenTelefono to set
	 */
	public void setIdenTelefono(String idenTelefono) {
		this.idenTelefono = idenTelefono;
	}
	/**
	 * @return the idenCorreo
	 */
	public String getIdenCorreo() {
		return idenCorreo;
	}
	/**
	 * @param idenCorreo the idenCorreo to set
	 */
	public void setIdenCorreo(String idenCorreo) {
		this.idenCorreo = idenCorreo;
	}
	/**
	 * @return the idenEstado
	 */
	public int getIdenEstado() {
		return idenEstado;
	}
	/**
	 * @param idenEstado the idenEstado to set
	 */
	public void setIdenEstado(int idenEstado) {
		this.idenEstado = idenEstado;
	}
	/**
	 * @return the idenEstadoNombre
	 */
	public String getIdenEstadoNombre() {
		return idenEstadoNombre;
	}
	/**
	 * @param idenEstadoNombre the idenEstadoNombre to set
	 */
	public void setIdenEstadoNombre(String idenEstadoNombre) {
		this.idenEstadoNombre = idenEstadoNombre;
	}
	/**
	 * @return the idenDisabled
	 */
	public boolean isIdenDisabled() {
		return idenDisabled;
	}
	/**
	 * @param idenDisabled the idenDisabled to set
	 */
	public void setIdenDisabled(boolean idenDisabled) {
		this.idenDisabled = idenDisabled;
	}
	/**
	 * @return the idenDisabled_
	 */
	public String getIdenDisabled_() {
		return idenDisabled_;
	}
	/**
	 * @param idenDisabled the idenDisabled_ to set
	 */
	public void setIdenDisabled_(String idenDisabled) {
		idenDisabled_ = idenDisabled;
	}
	/**
	 * @return the idenSedes
	 */
	public int getIdenSedes() {
		return idenSedes;
	}
	/**
	 * @param idenSedes the idenSedes to set
	 */
	public void setIdenSedes(int idenSedes) {
		this.idenSedes = idenSedes;
	}
	
	public int getIdenCaracter() {
		return idenCaracter;
	}
	public void setIdenCaracter(int dominio) {
		this.idenCaracter = dominio;
	}
	public int getIdenEducacionFormalAdulos() {
		return idenEducacionFormalAdulos;
	}
	public void setIdenEducacionFormalAdulos(int educacionFormalAdulos) {
		this.idenEducacionFormalAdulos = educacionFormalAdulos;
	}
	public int getIdenCantidadEducacionFormalAdultos() {
		return idenCantidadEducacionFormalAdultos;
	}
	public void setIdenCantidadEducacionFormalAdultos(int cantidadEducacionFormalAdultos) {
		this.idenCantidadEducacionFormalAdultos = cantidadEducacionFormalAdultos;
	}
	public int getIdenAceleracionAprendizaje() {
		return idenAceleracionAprendizaje;
	}
	public void setIdenAceleracionAprendizaje(int aceleracionAprendizaje) {
		this.idenAceleracionAprendizaje = aceleracionAprendizaje;
	}
	public int getIdenCantidadAceleracionAprendizaje() {
		return idenCantidadAceleracionAprendizaje;
	}
	public void setIdenCantidadAceleracionAprendizaje(int cantidadAceleracionAprendizaje) {
		this.idenCantidadAceleracionAprendizaje = cantidadAceleracionAprendizaje;
	}
}
