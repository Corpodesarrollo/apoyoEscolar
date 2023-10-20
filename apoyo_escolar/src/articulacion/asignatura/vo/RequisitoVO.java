package articulacion.asignatura.vo;

import siges.common.vo.Vo;

public class RequisitoVO extends Vo{
	private long asigCodigo;
	private int asigReCodigo;
	private String asigNombre;
	private long [] nuevoRequisito;
	private String asigChecked_;
	private int asigChecked;
	
	public String getAsigNombre() {
		return asigNombre;
	}
	public int getAsigReCodigo() {
		return asigReCodigo;
	}
	public void setAsigNombre(String asigNombre) {
		this.asigNombre = asigNombre;
	}
	public void setAsigReCodigo(int asigReCodigo) {
		this.asigReCodigo = asigReCodigo;
	}
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
	 * @return Return the nuevoRequisito.
	 */
	public final long[] getNuevoRequisito() {
		return nuevoRequisito;
	}
	/**
	 * @param nuevoRequisito The nuevoRequisito to set.
	 */
	public final void setNuevoRequisito(long[] nuevoRequisito) {
		this.nuevoRequisito = nuevoRequisito;
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
	
}
