/**
 * 
 */
package participacion.instancia.vo;

import siges.common.vo.Vo;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class RangoVO extends Vo{
	private int ranNivel;
	private int ranInstancia;
	private int ranCodigo;
	private String ranNombre;
	private String ranFechaIni;
	private String ranFechaFin;
	private int ranEstado;
	
	/**
	 * @return Return the ranCodigo.
	 */
	public final int getRanCodigo() {
		return ranCodigo;
	}
	/**
	 * @param ranCodigo The ranCodigo to set.
	 */
	public final void setRanCodigo(int ranCodigo) {
		this.ranCodigo = ranCodigo;
	}
	/**
	 * @return Return the ranEstado.
	 */
	public final int getRanEstado() {
		return ranEstado;
	}
	/**
	 * @param ranEstado The ranEstado to set.
	 */
	public final void setRanEstado(int ranEstado) {
		this.ranEstado = ranEstado;
	}
	/**
	 * @return Return the ranFechaFin.
	 */
	public final String getRanFechaFin() {
		return ranFechaFin;
	}
	/**
	 * @param ranFechaFin The ranFechaFin to set.
	 */
	public final void setRanFechaFin(String ranFechaFin) {
		this.ranFechaFin = ranFechaFin;
	}
	/**
	 * @return Return the ranFechaIni.
	 */
	public final String getRanFechaIni() {
		return ranFechaIni;
	}
	/**
	 * @param ranFechaIni The ranFechaIni to set.
	 */
	public final void setRanFechaIni(String ranFechaIni) {
		this.ranFechaIni = ranFechaIni;
	}
	/**
	 * @return Return the ranInstancia.
	 */
	public final int getRanInstancia() {
		return ranInstancia;
	}
	/**
	 * @param ranInstancia The ranInstancia to set.
	 */
	public final void setRanInstancia(int ranInstancia) {
		this.ranInstancia = ranInstancia;
	}
	/**
	 * @return Return the ranNombre.
	 */
	public final String getRanNombre() {
		return ranNombre;
	}
	/**
	 * @param ranNombre The ranNombre to set.
	 */
	public final void setRanNombre(String ranNombre) {
		this.ranNombre = ranNombre;
	}
	/**
	 * @return Return the ranNivel.
	 */
	public final int getRanNivel() {
		return ranNivel;
	}
	/**
	 * @param ranNivel The ranNivel to set.
	 */
	public final void setRanNivel(int ranNivel) {
		this.ranNivel = ranNivel;
	}
}
