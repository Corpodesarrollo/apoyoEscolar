/**
 * 
 */
package articulacion.artRotacion.vo;

import siges.common.vo.Vo;

/**
 * 9/10/2007 
 * @author Latined
 * @version 1.2
 */
public class FiltroRecesoVO extends Vo{
	private long filInstitucion;
	private int filSede;
	private int filJornada;
	private int filAnhoVigencia;
	private int filPerVigencia;
	private long filEstructura;
	
	/**
	 * @return Return the filAnhoVigencia.
	 */
	public int getFilAnhoVigencia() {
		return filAnhoVigencia;
	}
	/**
	 * @param filAnhoVigencia The filAnhoVigencia to set.
	 */
	public void setFilAnhoVigencia(int filAnhoVigencia) {
		this.filAnhoVigencia = filAnhoVigencia;
	}
	/**
	 * @return Return the filEstructura.
	 */
	public long getFilEstructura() {
		return filEstructura;
	}
	/**
	 * @param filEstructura The filEstructura to set.
	 */
	public void setFilEstructura(long filEstructura) {
		this.filEstructura = filEstructura;
	}
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
	 * @return Return the filPerVigencia.
	 */
	public int getFilPerVigencia() {
		return filPerVigencia;
	}
	/**
	 * @param filPerVigencia The filPerVigencia to set.
	 */
	public void setFilPerVigencia(int filPerVigencia) {
		this.filPerVigencia = filPerVigencia;
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

}
