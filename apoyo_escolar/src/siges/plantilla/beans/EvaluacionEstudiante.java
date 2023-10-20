/**
 * 
 */
package siges.plantilla.beans;

import siges.common.vo.Vo;

/**
 * 1/09/2007 
 * @author Latined
 * @version 1.2
 */
public class EvaluacionEstudiante extends Vo{
	private long evalCodigo;
	private int evalConsecutivo;
	private String evalNombre;
	private String evalApellido;
	private int evalNota;
	private String evalNotaCon;
	private double evalNotaConCodigo = -1;
	private String evalObservacion;
	
	/**
	 * @return Return the evalApellido.
	 */
	public String getEvalApellido() {
		return evalApellido;
	}
	/**
	 * @param evalApellido The evalApellido to set.
	 */
	public void setEvalApellido(String evalApellido) {
		this.evalApellido = evalApellido;
	}
	/**
	 * @return Return the evalCodigo.
	 */
	public long getEvalCodigo() {
		return evalCodigo;
	}
	/**
	 * @param evalCodigo The evalCodigo to set.
	 */
	public void setEvalCodigo(long evalCodigo) {
		this.evalCodigo = evalCodigo;
	}
	/**
	 * @return Return the evalConsecutivo.
	 */
	public int getEvalConsecutivo() {
		return evalConsecutivo;
	}
	/**
	 * @param evalConsecutivo The evalConsecutivo to set.
	 */
	public void setEvalConsecutivo(int evalConsecutivo) {
		this.evalConsecutivo = evalConsecutivo;
	}
	/**
	 * @return Return the evalNombre.
	 */
	public String getEvalNombre() {
		return evalNombre;
	}
	/**
	 * @param evalNombre The evalNombre to set.
	 */
	public void setEvalNombre(String evalNombre) {
		this.evalNombre = evalNombre;
	}
	/**
	 * @return Return the evalNota.
	 */
	public int getEvalNota() {
		return evalNota;
	}
	/**
	 * @param evalNota The evalNota to set.
	 */
	public void setEvalNota(int evalNota) {
		this.evalNota = evalNota;
	}
	/**
	 * @return Return the evalObservacion.
	 */
	public String getEvalObservacion() {
		return evalObservacion;
	}
	/**
	 * @param evalObservacion The evalObservacion to set.
	 */
	public void setEvalObservacion(String evalObservacion) {
		this.evalObservacion = evalObservacion;
	}
	/**
	 * @return Return the evalNotaCon.
	 */
	public String getEvalNotaCon() {
		return evalNotaCon;
	}
	/**
	 * @param evalNotaCon The evalNotaCon to set.
	 */
	public void setEvalNotaCon(String evalNotaCon) {
		this.evalNotaCon = evalNotaCon;
	}
	public double getEvalNotaConCodigo() {
		return evalNotaConCodigo;
	}
	public void setEvalNotaConCodigo(double evalNotaConCodigo) {
		this.evalNotaConCodigo = evalNotaConCodigo;
	}
	 
}
