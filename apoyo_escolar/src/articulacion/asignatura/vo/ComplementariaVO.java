package articulacion.asignatura.vo;

import siges.common.vo.Vo;

public class ComplementariaVO extends Vo{
	private long asigCodigo;
	private long asigComCodigo;
	private String asigNombre;
	private long [] nuevoComplemento;
	private int asigChecked;
	private String asigChecked_;
	
	/**
	 * @return Return the asigChecked.
	 */
	public final int getAsigChecked() {
		return asigChecked;
	}
	/**
	 * @param asigChecked The asigChecked to set.
	 */
	public final void setAsigChecked(int asigChecked) {
		this.asigChecked = asigChecked;
	}
	/**
	 * @return Return the asigChecked_.
	 */
	public final String getAsigChecked_() {
		return asigChecked_;
	}
	/**
	 * @param asigChecked_ The asigChecked_ to set.
	 */
	public final void setAsigChecked_(String asigChecked_) {
		this.asigChecked_ = asigChecked_;
	}
	/**
	 * @return Return the asigCodigo.
	 */
	public final long getAsigCodigo() {
		return asigCodigo;
	}
	/**
	 * @param asigCodigo The asigCodigo to set.
	 */
	public final void setAsigCodigo(long asigCodigo) {
		this.asigCodigo = asigCodigo;
	}
	/**
	 * @return Return the asigComCodigo.
	 */
	public final long getAsigComCodigo() {
		return asigComCodigo;
	}
	/**
	 * @param asigComCodigo The asigComCodigo to set.
	 */
	public final void setAsigComCodigo(long asigComCodigo) {
		this.asigComCodigo = asigComCodigo;
	}
	/**
	 * @return Return the asigNombre.
	 */
	public final String getAsigNombre() {
		return asigNombre;
	}
	/**
	 * @param asigNombre The asigNombre to set.
	 */
	public final void setAsigNombre(String asigNombre) {
		this.asigNombre = asigNombre;
	}
	/**
	 * @return Return the nuevoComplemento.
	 */
	public final long[] getNuevoComplemento() {
		return nuevoComplemento;
	}
	/**
	 * @param nuevoComplemento The nuevoComplemento to set.
	 */
	public final void setNuevoComplemento(long[] nuevoComplemento) {
		this.nuevoComplemento = nuevoComplemento;
	}
	

	
	
}
