/**
 * 
 */
package poa.seguimientoSED.vo;

import siges.common.vo.Vo;

/**
 * 23/02/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ColegioPoaVO extends Vo{
	private long colColegio;
	private String colNombre;
	private int colCodigo;
	private int colConsecutivo;
	private int colVigencia;
	private int colEstado;
	
	/**
	 * @return Return the colEstado.
	 */
	public int getColEstado() {
		return colEstado;
	}
	/**
	 * @param colEstado The colEstado to set.
	 */
	public void setColEstado(int colEstado) {
		this.colEstado = colEstado;
	}
	/**
	 * @return Return the colConsecutivo.
	 */
	public int getColConsecutivo() {
		return colConsecutivo;
	}
	/**
	 * @param colConsecutivo The colConsecutivo to set.
	 */
	public void setColConsecutivo(int colConsecutivo) {
		this.colConsecutivo = colConsecutivo;
	}
	/**
	 * @return Return the colNombre.
	 */
	public String getColNombre() {
		return colNombre;
	}
	/**
	 * @param colNombre The colNombre to set.
	 */
	public void setColNombre(String colNombre) {
		this.colNombre = colNombre;
	}
	/**
	 * @return Return the colVigencia.
	 */
	public int getColVigencia() {
		return colVigencia;
	}
	/**
	 * @param colVigencia The colVigencia to set.
	 */
	public void setColVigencia(int colVigencia) {
		this.colVigencia = colVigencia;
	}
	/**
	 * @return Return the colCodigo.
	 */
	public int getColCodigo() {
		return colCodigo;
	}
	/**
	 * @param colCodigo The colCodigo to set.
	 */
	public void setColCodigo(int colCodigo) {
		this.colCodigo = colCodigo;
	}
	/**
	 * @return Return the colColegio.
	 */
	public long getColColegio() {
		return colColegio;
	}
	/**
	 * @param colColegio The colColegio to set.
	 */
	public void setColColegio(long colColegio) {
		this.colColegio = colColegio;
	}
}
