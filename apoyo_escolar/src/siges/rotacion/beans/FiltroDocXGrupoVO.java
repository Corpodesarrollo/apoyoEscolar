/**
 * 
 */
package siges.rotacion.beans;

import siges.common.vo.Vo;

/**
 * 11/08/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroDocXGrupoVO extends Vo{
	private long filInstitucion;
	private int filMetodologia;
	private int filSede;
	private int filJornada;
	private long filDocente;
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
	 * @return Return the filDocente.
	 */
	public long getFilDocente() {
		return filDocente;
	}
	/**
	 * @param filDocente The filDocente to set.
	 */
	public void setFilDocente(long filDocente) {
		this.filDocente = filDocente;
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
