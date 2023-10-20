package siges.institucion.correoLider.beans;

import java.util.List;

import siges.common.vo.Vo;

/**
 * 24/08/2007 
 * @author Latined
 * @version 1.2
 */
public class CorreoLiderVO extends Vo{
	private int corrLocalidad=-99;
	private long corrInstitucion;
	private int corrTipoCargo;
	private long corrCargo;
	private String corrAsunto;
	private String corrMensaje;
	private String corrAdjunto;
	private String corrNombreAdjunto;
	private int corrBandera;
	private List corrCorreos;
	private ParamsMail paramsMail;
	private int corrEstado=1;
	
	private int corrTotal;
	private int corrEnviados;

	/**
	 * @return Return the corrAdjunto.
	 */
	public String getCorrAdjunto() {
		return corrAdjunto;
	}
	/**
	 * @param corrAdjunto The corrAdjunto to set.
	 */
	public void setCorrAdjunto(String corrAdjunto) {
		this.corrAdjunto = corrAdjunto;
	}
	/**
	 * @return Return the corrAsunto.
	 */
	public String getCorrAsunto() {
		return corrAsunto;
	}
	/**
	 * @param corrAsunto The corrAsunto to set.
	 */
	public void setCorrAsunto(String corrAsunto) {
		this.corrAsunto = corrAsunto;
	}
	/**
	 * @return Return the corrBandera.
	 */
	public int getCorrBandera() {
		return corrBandera;
	}
	/**
	 * @param corrBandera The corrBandera to set.
	 */
	public void setCorrBandera(int corrBandera) {
		this.corrBandera = corrBandera;
	}
	/**
	 * @return Return the corrCargo.
	 */
	public long getCorrCargo() {
		return corrCargo;
	}
	/**
	 * @param corrCargo The corrCargo to set.
	 */
	public void setCorrCargo(long corrCargo) {
		this.corrCargo = corrCargo;
	}
	/**
	 * @return Return the corrInstitucion.
	 */
	public long getCorrInstitucion() {
		return corrInstitucion;
	}
	/**
	 * @param corrInstitucion The corrInstitucion to set.
	 */
	public void setCorrInstitucion(long corrInstitucion) {
		this.corrInstitucion = corrInstitucion;
	}
	/**
	 * @return Return the corrLocalidad.
	 */
	public int getCorrLocalidad() {
		return corrLocalidad;
	}
	/**
	 * @param corrLocalidad The corrLocalidad to set.
	 */
	public void setCorrLocalidad(int corrLocalidad) {
		this.corrLocalidad = corrLocalidad;
	}
	/**
	 * @return Return the corrMensaje.
	 */
	public String getCorrMensaje() {
		return corrMensaje;
	}
	/**
	 * @param corrMensaje The corrMensaje to set.
	 */
	public void setCorrMensaje(String corrMensaje) {
		this.corrMensaje = corrMensaje;
	}
	/**
	 * @return Return the corrTipoCargo.
	 */
	public int getCorrTipoCargo() {
		return corrTipoCargo;
	}
	/**
	 * @param corrTipoCargo The corrTipoCargo to set.
	 */
	public void setCorrTipoCargo(int corrTipoCargo) {
		this.corrTipoCargo = corrTipoCargo;
	}
	/**
	 * @return Return the corrCorreos.
	 */
	public List getCorrCorreos() {
		return corrCorreos;
	}
	/**
	 * @param corrCorreos The corrCorreos to set.
	 */
	public void setCorrCorreos(List corrCorreos) {
		this.corrCorreos = corrCorreos;
	}
	/**
	 * @return Return the paramsMail.
	 */
	public ParamsMail getParamsMail() {
		return paramsMail;
	}
	/**
	 * @param paramsMail The paramsMail to set.
	 */
	public void setParamsMail(ParamsMail paramsMail) {
		this.paramsMail = paramsMail;
	}
	/**
	 * @return Return the corrEnviados.
	 */
	public int getCorrEnviados() {
		return corrEnviados;
	}
	/**
	 * @param corrEnviados The corrEnviados to set.
	 */
	public void setCorrEnviados(int corrEnviados) {
		this.corrEnviados = corrEnviados;
	}
	/**
	 * @return Return the corrTotal.
	 */
	public int getCorrTotal() {
		return corrTotal;
	}
	/**
	 * @param corrTotal The corrTotal to set.
	 */
	public void setCorrTotal(int corrTotal) {
		this.corrTotal = corrTotal;
	}
	/**
	 * @return Return the corrEstado.
	 */
	public int getCorrEstado() {
		return corrEstado;
	}
	/**
	 * @param corrEstado The corrEstado to set.
	 */
	public void setCorrEstado(int corrEstado) {
		this.corrEstado = corrEstado;
	}
	/**
	 * @return Return the corrNombreAdjunto.
	 */
	public String getCorrNombreAdjunto() {
		return corrNombreAdjunto;
	}
	/**
	 * @param corrNombreAdjunto The corrNombreAdjunto to set.
	 */
	public void setCorrNombreAdjunto(String corrNombreAdjunto) {
		this.corrNombreAdjunto = corrNombreAdjunto;
	}
}
