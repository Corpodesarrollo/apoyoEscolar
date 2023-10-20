/**
 * 
 */
package siges.recuperacion.vo;

import siges.common.vo.Vo;

/**
 * 12/02/2009 
 * @author Latined
 * @version 1.2
 */
public class RecuperacionVO  extends Vo{
	private int recConsecutivo;
	private long recCodigo; 
	private String recApellido;
	private String recNombre;
	private String recNotaFinal;
	private int recNotaRecuperacion;
	private String recFecha;
	private long []recCodigos;
	private String [] recFechas;
	private int []recNotas;
	
	/**
	 * @return Return the recApellido.
	 */
	public String getRecApellido() {
		return recApellido;
	}
	/**
	 * @param recApellido The recApellido to set.
	 */
	public void setRecApellido(String recApellido) {
		this.recApellido = recApellido;
	}
	/**
	 * @return Return the recCodigo.
	 */
	public long getRecCodigo() {
		return recCodigo;
	}
	/**
	 * @param recCodigo The recCodigo to set.
	 */
	public void setRecCodigo(long recCodigo) {
		this.recCodigo = recCodigo;
	}
	/**
	 * @return Return the recFecha.
	 */
	public String getRecFecha() {
		return recFecha;
	}
	/**
	 * @param recFecha The recFecha to set.
	 */
	public void setRecFecha(String recFecha) {
		this.recFecha = recFecha;
	}
	/**
	 * @return Return the recNombre.
	 */
	public String getRecNombre() {
		return recNombre;
	}
	/**
	 * @param recNombre The recNombre to set.
	 */
	public void setRecNombre(String recNombre) {
		this.recNombre = recNombre;
	}
	/**
	 * @return Return the recNotaFinal.
	 */
	public String getRecNotaFinal() {
		return recNotaFinal;
	}
	/**
	 * @param recNotaFinal The recNotaFinal to set.
	 */
	public void setRecNotaFinal(String recNotaFinal) {
		this.recNotaFinal = recNotaFinal;
	}
	/**
	 * @return Return the recNotaRecuperacion.
	 */
	public int getRecNotaRecuperacion() {
		return recNotaRecuperacion;
	}
	/**
	 * @param recNotaRecuperacion The recNotaRecuperacion to set.
	 */
	public void setRecNotaRecuperacion(int recNotaRecuperacion) {
		this.recNotaRecuperacion = recNotaRecuperacion;
	}
	/**
	 * @return Return the recConsecutivo.
	 */
	public int getRecConsecutivo() {
		return recConsecutivo;
	}
	/**
	 * @param recConsecutivo The recConsecutivo to set.
	 */
	public void setRecConsecutivo(int recConsecutivo) {
		this.recConsecutivo = recConsecutivo;
	}
	/**
	 * @return Return the recCodigos.
	 */
	public long[] getRecCodigos() {
		return recCodigos;
	}
	/**
	 * @param recCodigos The recCodigos to set.
	 */
	public void setRecCodigos(long[] recCodigos) {
		this.recCodigos = recCodigos;
	}
	/**
	 * @return Return the recFechas.
	 */
	public String[] getRecFechas() {
		return recFechas;
	}
	/**
	 * @param recFechas The recFechas to set.
	 */
	public void setRecFechas(String[] recFechas) {
		this.recFechas = recFechas;
	}
	/**
	 * @return Return the recNotas.
	 */
	public int[] getRecNotas() {
		return recNotas;
	}
	/**
	 * @param recNotas The recNotas to set.
	 */
	public void setRecNotas(int[] recNotas) {
		this.recNotas = recNotas;
	}
}
