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
public class DimensionesVO extends Vo{
	
	private long dimcodinst; 
	private long dimcodmetod; 
	private long dimcodigo; 
	private String dimnombre; 
	private long  dimorden; 
	private String dimabrev; 
	private long dimvigencia;
	public String getDimabrev() {
		return dimabrev;
	}
	public void setDimabrev(String dimabrev) {
		this.dimabrev = dimabrev;
	}
	public long getDimcodigo() {
		return dimcodigo;
	}
	public void setDimcodigo(long dimcodigo) {
		this.dimcodigo = dimcodigo;
	}
	public long getDimcodinst() {
		return dimcodinst;
	}
	public void setDimcodinst(long dimcodinst) {
		this.dimcodinst = dimcodinst;
	}
	public long getDimcodmetod() {
		return dimcodmetod;
	}
	public void setDimcodmetod(long dimcodmetod) {
		this.dimcodmetod = dimcodmetod;
	}
	public String getDimnombre() {
		return dimnombre;
	}
	public void setDimnombre(String dimnombre) {
		this.dimnombre = dimnombre;
	}
	public long getDimorden() {
		return dimorden;
	}
	public void setDimorden(long dimorden) {
		this.dimorden = dimorden;
	}
	public long getDimvigencia() {
		return dimvigencia;
	}
	public void setDimvigencia(long dimvigencia) {
		this.dimvigencia = dimvigencia;
	}


}
