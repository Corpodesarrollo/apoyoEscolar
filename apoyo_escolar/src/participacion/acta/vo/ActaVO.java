/**
 * 
 */
package participacion.acta.vo;

import siges.common.vo.Vo;

/**
 * 30/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ActaVO extends Vo{
	private int actNivel;
	private int actInstancia;
	private int actRango;
	private int actCodigo;
	private int actLocalidad=-1;
	private long actColegio;
	private String actFecha;
	private String actHoraIni;
	private String actMinIni;
	private String actHoraFin;
	private String actMinFin;
	private String actFecha2;
	private String actLugar;
	private int actAsunto;
	private String actAsistentes;
	private String actAsistentesExt;
	private String actElaborado;
	private String actElaboradoRol;
	private String actFechaProxima;
	
	private String actDescripcion;
	private String actDecision;
	private String actCompromiso;
	private String actResponsabilidad;
	private String actDocumento;
	private String actObservacion;
	
	private int actRol;
	private long actParticipante;
	
	private int actTieneLocalidad;
	private int actTieneColegio;
	//para la lista
	private String actNombreAsunto;
	private String actHora1;
	private String actHora2;
	
	/**
	 * @return Return the actAsistentes.
	 */
	public final String getActAsistentes() {
		return actAsistentes;
	}
	/**
	 * @param actAsistentes The actAsistentes to set.
	 */
	public final void setActAsistentes(String actAsistentes) {
		this.actAsistentes = actAsistentes;
	}
	/**
	 * @return Return the actAsistentesExt.
	 */
	public final String getActAsistentesExt() {
		return actAsistentesExt;
	}
	/**
	 * @param actAsistentesExt The actAsistentesExt to set.
	 */
	public final void setActAsistentesExt(String actAsistentesExt) {
		this.actAsistentesExt = actAsistentesExt;
	}
	/**
	 * @return Return the actAsunto.
	 */
	public final int getActAsunto() {
		return actAsunto;
	}
	/**
	 * @param actAsunto The actAsunto to set.
	 */
	public final void setActAsunto(int actAsunto) {
		this.actAsunto = actAsunto;
	}
	/**
	 * @return Return the actCodigo.
	 */
	public final int getActCodigo() {
		return actCodigo;
	}
	/**
	 * @param actCodigo The actCodigo to set.
	 */
	public final void setActCodigo(int actCodigo) {
		this.actCodigo = actCodigo;
	}
	/**
	 * @return Return the actColegio.
	 */
	public final long getActColegio() {
		return actColegio;
	}
	/**
	 * @param actColegio The actColegio to set.
	 */
	public final void setActColegio(long actColegio) {
		this.actColegio = actColegio;
	}
	/**
	 * @return Return the actCompromiso.
	 */
	public final String getActCompromiso() {
		return actCompromiso;
	}
	/**
	 * @param actCompromiso The actCompromiso to set.
	 */
	public final void setActCompromiso(String actCompromiso) {
		this.actCompromiso = actCompromiso;
	}
	/**
	 * @return Return the actDecision.
	 */
	public final String getActDecision() {
		return actDecision;
	}
	/**
	 * @param actDecision The actDecision to set.
	 */
	public final void setActDecision(String actDecision) {
		this.actDecision = actDecision;
	}
	/**
	 * @return Return the actDescripcion.
	 */
	public final String getActDescripcion() {
		return actDescripcion;
	}
	/**
	 * @param actDescripcion The actDescripcion to set.
	 */
	public final void setActDescripcion(String actDescripcion) {
		this.actDescripcion = actDescripcion;
	}
	/**
	 * @return Return the actDocumento.
	 */
	public final String getActDocumento() {
		return actDocumento;
	}
	/**
	 * @param actDocumento The actDocumento to set.
	 */
	public final void setActDocumento(String actDocumento) {
		this.actDocumento = actDocumento;
	}
	/**
	 * @return Return the actElaborado.
	 */
	public final String getActElaborado() {
		return actElaborado;
	}
	/**
	 * @param actElaborado The actElaborado to set.
	 */
	public final void setActElaborado(String actElaborado) {
		this.actElaborado = actElaborado;
	}
	/**
	 * @return Return the actFecha.
	 */
	public final String getActFecha() {
		return actFecha;
	}
	/**
	 * @param actFecha The actFecha to set.
	 */
	public final void setActFecha(String actFecha) {
		this.actFecha = actFecha;
	}
	/**
	 * @return Return the actFecha2.
	 */
	public final String getActFecha2() {
		return actFecha2;
	}
	/**
	 * @param actFecha2 The actFecha2 to set.
	 */
	public final void setActFecha2(String actFecha2) {
		this.actFecha2 = actFecha2;
	}
	/**
	 * @return Return the actFechaProxima.
	 */
	public final String getActFechaProxima() {
		return actFechaProxima;
	}
	/**
	 * @param actFechaProxima The actFechaProxima to set.
	 */
	public final void setActFechaProxima(String actFechaProxima) {
		this.actFechaProxima = actFechaProxima;
	}
	/**
	 * @return Return the actHora1.
	 */
	public final String getActHora1() {
		return actHora1;
	}
	/**
	 * @param actHora1 The actHora1 to set.
	 */
	public final void setActHora1(String actHora1) {
		this.actHora1 = actHora1;
	}
	/**
	 * @return Return the actHora2.
	 */
	public final String getActHora2() {
		return actHora2;
	}
	/**
	 * @param actHora2 The actHora2 to set.
	 */
	public final void setActHora2(String actHora2) {
		this.actHora2 = actHora2;
	}
	/**
	 * @return Return the actHoraFin.
	 */
	public final String getActHoraFin() {
		return actHoraFin;
	}
	/**
	 * @param actHoraFin The actHoraFin to set.
	 */
	public final void setActHoraFin(String actHoraFin) {
		this.actHoraFin = actHoraFin;
	}
	/**
	 * @return Return the actHoraIni.
	 */
	public final String getActHoraIni() {
		return actHoraIni;
	}
	/**
	 * @param actHoraIni The actHoraIni to set.
	 */
	public final void setActHoraIni(String actHoraIni) {
		this.actHoraIni = actHoraIni;
	}
	/**
	 * @return Return the actInstancia.
	 */
	public final int getActInstancia() {
		return actInstancia;
	}
	/**
	 * @param actInstancia The actInstancia to set.
	 */
	public final void setActInstancia(int actInstancia) {
		this.actInstancia = actInstancia;
	}
	/**
	 * @return Return the actLocalidad.
	 */
	public final int getActLocalidad() {
		return actLocalidad;
	}
	/**
	 * @param actLocalidad The actLocalidad to set.
	 */
	public final void setActLocalidad(int actLocalidad) {
		this.actLocalidad = actLocalidad;
	}
	/**
	 * @return Return the actLugar.
	 */
	public final String getActLugar() {
		return actLugar;
	}
	/**
	 * @param actLugar The actLugar to set.
	 */
	public final void setActLugar(String actLugar) {
		this.actLugar = actLugar;
	}
	/**
	 * @return Return the actMinFin.
	 */
	public final String getActMinFin() {
		return actMinFin;
	}
	/**
	 * @param actMinFin The actMinFin to set.
	 */
	public final void setActMinFin(String actMinFin) {
		this.actMinFin = actMinFin;
	}
	/**
	 * @return Return the actMinIni.
	 */
	public final String getActMinIni() {
		return actMinIni;
	}
	/**
	 * @param actMinIni The actMinIni to set.
	 */
	public final void setActMinIni(String actMinIni) {
		this.actMinIni = actMinIni;
	}
	/**
	 * @return Return the actNivel.
	 */
	public final int getActNivel() {
		return actNivel;
	}
	/**
	 * @param actNivel The actNivel to set.
	 */
	public final void setActNivel(int actNivel) {
		this.actNivel = actNivel;
	}
	/**
	 * @return Return the actNombreAsunto.
	 */
	public final String getActNombreAsunto() {
		return actNombreAsunto;
	}
	/**
	 * @param actNombreAsunto The actNombreAsunto to set.
	 */
	public final void setActNombreAsunto(String actNombreAsunto) {
		this.actNombreAsunto = actNombreAsunto;
	}
	/**
	 * @return Return the actObservacion.
	 */
	public final String getActObservacion() {
		return actObservacion;
	}
	/**
	 * @param actObservacion The actObservacion to set.
	 */
	public final void setActObservacion(String actObservacion) {
		this.actObservacion = actObservacion;
	}
	/**
	 * @return Return the actParticipante.
	 */
	public final long getActParticipante() {
		return actParticipante;
	}
	/**
	 * @param actParticipante The actParticipante to set.
	 */
	public final void setActParticipante(long actParticipante) {
		this.actParticipante = actParticipante;
	}
	/**
	 * @return Return the actRango.
	 */
	public final int getActRango() {
		return actRango;
	}
	/**
	 * @param actRango The actRango to set.
	 */
	public final void setActRango(int actRango) {
		this.actRango = actRango;
	}
	/**
	 * @return Return the actResponsabilidad.
	 */
	public final String getActResponsabilidad() {
		return actResponsabilidad;
	}
	/**
	 * @param actResponsabilidad The actResponsabilidad to set.
	 */
	public final void setActResponsabilidad(String actResponsabilidad) {
		this.actResponsabilidad = actResponsabilidad;
	}
	/**
	 * @return Return the actRol.
	 */
	public final int getActRol() {
		return actRol;
	}
	/**
	 * @param actRol The actRol to set.
	 */
	public final void setActRol(int actRol) {
		this.actRol = actRol;
	}
	/**
	 * @return Return the actTieneColegio.
	 */
	public final int getActTieneColegio() {
		return actTieneColegio;
	}
	/**
	 * @param actTieneColegio The actTieneColegio to set.
	 */
	public final void setActTieneColegio(int actTieneColegio) {
		this.actTieneColegio = actTieneColegio;
	}
	/**
	 * @return Return the actTieneLocalidad.
	 */
	public final int getActTieneLocalidad() {
		return actTieneLocalidad;
	}
	/**
	 * @param actTieneLocalidad The actTieneLocalidad to set.
	 */
	public final void setActTieneLocalidad(int actTieneLocalidad) {
		this.actTieneLocalidad = actTieneLocalidad;
	}
	/**
	 * @return Return the actElaboradoRol.
	 */
	public final String getActElaboradoRol() {
		return actElaboradoRol;
	}
	/**
	 * @param actElaboradoRol The actElaboradoRol to set.
	 */
	public final void setActElaboradoRol(String actElaboradoRol) {
		this.actElaboradoRol = actElaboradoRol;
	}
}
