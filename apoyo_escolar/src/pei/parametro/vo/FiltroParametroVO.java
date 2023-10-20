package pei.parametro.vo;

import siges.common.vo.Vo;

public class FiltroParametroVO extends Vo{
	private int filTipo;
	private int filEstado=-99;
	private int filCodigo;
	/**
	 * @return the filTipo
	 */
	public int getFilTipo() {
		return filTipo;
	}
	/**
	 * @param filTipo the filTipo to set
	 */
	public void setFilTipo(int filTipo) {
		this.filTipo = filTipo;
	}
	/**
	 * @return the filEstado
	 */
	public int getFilEstado() {
		return filEstado;
	}
	/**
	 * @param filEstado the filEstado to set
	 */
	public void setFilEstado(int filEstado) {
		this.filEstado = filEstado;
	}
	/**
	 * @return the filCodigo
	 */
	public int getFilCodigo() {
		return filCodigo;
	}
	/**
	 * @param filCodigo the filCodigo to set
	 */
	public void setFilCodigo(int filCodigo) {
		this.filCodigo = filCodigo;
	}
}
