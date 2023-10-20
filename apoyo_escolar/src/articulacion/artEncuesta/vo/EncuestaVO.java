/**
 * 
 */
package articulacion.artEncuesta.vo;

import siges.common.vo.Vo;

/**
 * 5/12/2007 
 * @author Latined
 * @version 1.2
 */
public class EncuestaVO extends Vo{
	private long plaInstitucion;
	private int plaMetodologia;
	private int plaSede;
	private int plaJornada;
	private int plaGrado=-99;
	private int plaGrupo=-99;
	private int plaOrden;
	private int plaEstado;
	private long plaJerarquia;
	private long plaLocalidad=-99;	
	private int habilitarLocalidad;
	private int habilitarColegio;
	private int numeroPregunta;
	private int filOpcion;
	private int filAsignatura;
	

	private String plaMensaje;

	/**
	 * @return Return the plaGrado.
	 */
	public int getPlaGrado() {
		return plaGrado;
	}

	/**
	 * @param plaGrado The plaGrado to set.
	 */
	public void setPlaGrado(int plaGrado) {
		this.plaGrado = plaGrado;
	}

	/**
	 * @return Return the plaGrupo.
	 */
	public int getPlaGrupo() {
		return plaGrupo;
	}

	/**
	 * @param plaGrupo The plaGrupo to set.
	 */
	public void setPlaGrupo(int plaGrupo) {
		this.plaGrupo = plaGrupo;
	}

	/**
	 * @return Return the plaInstitucion.
	 */
	public long getPlaInstitucion() {
		return plaInstitucion;
	}

	/**
	 * @param plaInstitucion The plaInstitucion to set.
	 */
	public void setPlaInstitucion(long plaInstitucion) {
		this.plaInstitucion = plaInstitucion;
	}

	/**
	 * @return Return the plaJerarquia.
	 */
	public long getPlaJerarquia() {
		return plaJerarquia;
	}

	/**
	 * @param plaJerarquia The plaJerarquia to set.
	 */
	public void setPlaJerarquia(long plaJerarquia) {
		this.plaJerarquia = plaJerarquia;
	}

	/**
	 * @return Return the plaJornada.
	 */
	public int getPlaJornada() {
		return plaJornada;
	}

	/**
	 * @param plaJornada The plaJornada to set.
	 */
	public void setPlaJornada(int plaJornada) {
		this.plaJornada = plaJornada;
	}

	/**
	 * @return Return the plaMetodologia.
	 */
	public int getPlaMetodologia() {
		return plaMetodologia;
	}

	/**
	 * @param plaMetodologia The plaMetodologia to set.
	 */
	public void setPlaMetodologia(int plaMetodologia) {
		this.plaMetodologia = plaMetodologia;
	}

	/**
	 * @return Return the plaOrden.
	 */
	public int getPlaOrden() {
		return plaOrden;
	}

	/**
	 * @param plaOrden The plaOrden to set.
	 */
	public void setPlaOrden(int plaOrden) {
		this.plaOrden = plaOrden;
	}

	/**
	 * @return Return the plaSede.
	 */
	public int getPlaSede() {
		return plaSede;
	}

	/**
	 * @param plaSede The plaSede to set.
	 */
	public void setPlaSede(int plaSede) {
		this.plaSede = plaSede;
	}


	/**
	 * @return Return the plaMensaje.
	 */
	public String getPlaMensaje() {
		return plaMensaje;
	}

	/**
	 * @param plaMensaje The plaMensaje to set.
	 */
	public void setPlaMensaje(String plaMensaje) {
		this.plaMensaje = plaMensaje;
	}
	/**
	 * @return Return the plaOrden.
	 */
	public int getPlaEstado() {
		return plaEstado;
	}

	/**
	 * @param plaOrden The plaOrden to set.
	 */
	public void setPlaEstado(int plaEst) {
		this.plaEstado = plaEst;
	}
	
	public long getPlaLocalidad() {
		return plaLocalidad;
	}

	public void setPlaLocalidad(long plaLocalidad) {
		this.plaLocalidad = plaLocalidad;
	}

	public int getHabilitarColegio() {
		return habilitarColegio;
	}

	public void setHabilitarColegio(int habilitarColegio) {
		this.habilitarColegio = habilitarColegio;
	}

	public int getHabilitarLocalidad() {
		return habilitarLocalidad;
	}

	public void setHabilitarLocalidad(int habilitarLocalidad) {
		this.habilitarLocalidad = habilitarLocalidad;
	}
	public int getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(int numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}
	
	public int getOpcion() {
		return filOpcion;
	}

	public void setOpcion(int opc) {
		this.filOpcion= opc;
	}
	
	public int getAsignatura() {
		return filAsignatura;
	}

	public void setAsignatura(int asig) {
		this.filAsignatura= asig;
	}
}
