/**
 * 
 */
package siges.rotacion.beans;

import siges.common.vo.Vo;

/**
 * 13/08/2007 
 * @author Latined
 * @version 1.2
 */
public class EspXGradoVO extends Vo{
	private long espGraInstitucion; 
	private int espGraSede; 
	private int espGraJornada; 
	private int espGraMetodologia; 
	private int espGraEspacio; 
	private long[] espGraGrado;
	private String espGraNombreEspacio;
	private int espGraVigencia;
	
	/**
	 * @return Return the espGraEspacio.
	 */
	public int getEspGraEspacio() {
		return espGraEspacio;
	}
	/**
	 * @param espGraEspacio The espGraEspacio to set.
	 */
	public void setEspGraEspacio(int espGraEspacio) {
		this.espGraEspacio = espGraEspacio;
	}
	/**
	 * @return Return the espGraGrado.
	 */
	public long[] getEspGraGrado() {
		return espGraGrado;
	}
	/**
	 * @param espGraGrado The espGraGrado to set.
	 */
	public void setEspGraGrado(long[] espGraGrado) {
		this.espGraGrado = espGraGrado;
	}
	/**
	 * @return Return the espGraInstitucion.
	 */
	public long getEspGraInstitucion() {
		return espGraInstitucion;
	}
	/**
	 * @param espGraInstitucion The espGraInstitucion to set.
	 */
	public void setEspGraInstitucion(long espGraInstitucion) {
		this.espGraInstitucion = espGraInstitucion;
	}
	/**
	 * @return Return the espGraJornada.
	 */
	public int getEspGraJornada() {
		return espGraJornada;
	}
	/**
	 * @param espGraJornada The espGraJornada to set.
	 */
	public void setEspGraJornada(int espGraJornada) {
		this.espGraJornada = espGraJornada;
	}
	/**
	 * @return Return the espGraMetodologia.
	 */
	public int getEspGraMetodologia() {
		return espGraMetodologia;
	}
	/**
	 * @param espGraMetodologia The espGraMetodologia to set.
	 */
	public void setEspGraMetodologia(int espGraMetodologia) {
		this.espGraMetodologia = espGraMetodologia;
	}
	/**
	 * @return Return the espGraSede.
	 */
	public int getEspGraSede() {
		return espGraSede;
	}
	/**
	 * @param espGraSede The espGraSede to set.
	 */
	public void setEspGraSede(int espGraSede) {
		this.espGraSede = espGraSede;
	}
	/**
	 * @return Return the espGraNombreEspacio.
	 */
	public String getEspGraNombreEspacio() {
		return espGraNombreEspacio;
	}
	/**
	 * @param espGraNombreEspacio The espGraNombreEspacio to set.
	 */
	public void setEspGraNombreEspacio(String espGraNombreEspacio) {
		this.espGraNombreEspacio = espGraNombreEspacio;
	}
	/**
	 * @return Return the espGraVigencia.
	 */
	public int getEspGraVigencia() {
		return espGraVigencia;
	}
	/**
	 * @param espGraVigencia The espGraVigencia to set.
	 */
	public void setEspGraVigencia(int espGraVigencia) {
		this.espGraVigencia = espGraVigencia;
	} 

}
