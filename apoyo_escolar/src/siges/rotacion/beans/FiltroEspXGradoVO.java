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
public class FiltroEspXGradoVO extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filSede;
	private int filJornada;
	private int filVigencia;
	
	/**
	 * @return Return the filInstitucion.
	 */
	public long getFilInstitucion() {
		return filInstitucion;
	}
	/**
	 * @param filInstitucion The filInstitucion to set.
	 */
	public void setFilInstitucion(long filInstitucion) {
		this.filInstitucion = filInstitucion;
	}
	/**
	 * @return Return the filJornada.
	 */
	public int getFilJornada() {
		return filJornada;
	}
	/**
	 * @param filJornada The filJornada to set.
	 */
	public void setFilJornada(int filJornada) {
		this.filJornada = filJornada;
	}
	/**
	 * @return Return the filMetodologia.
	 */
	public int getFilMetodologia() {
		return filMetodologia;
	}
	/**
	 * @param filMetodologia The filMetodologia to set.
	 */
	public void setFilMetodologia(int filMetodologia) {
		this.filMetodologia = filMetodologia;
	}
	/**
	 * @return Return the filSede.
	 */
	public int getFilSede() {
		return filSede;
	}
	/**
	 * @param filSede The filSede to set.
	 */
	public void setFilSede(int filSede) {
		this.filSede = filSede;
	}
	/**
	 * @return Return the filVigencia.
	 */
	public int getFilVigencia() {
		return filVigencia;
	}
	/**
	 * @param filVigencia The filVigencia to set.
	 */
	public void setFilVigencia(int filVigencia) {
		this.filVigencia = filVigencia;
	}
	
}
