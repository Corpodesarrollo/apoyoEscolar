/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.vo;

import siges.common.vo.Vo;

/**
 * 28/10/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class AsignaturaVO extends Vo{
	private long asiInstitucion;
	private int asiMetodologia;
	private int asiVigencia; 
	private long asiArea;
	private long asiCodigo;
	private String asiNombre;
	private String asiAbreviatura;
	private long asiOrden;
	private String []asiGrado;
	private String asiEstado;
	private double plan1;
	private double plan2;
	private double plan3;
	private double planciclo1;
	private double planciclo2;
	private double planciclo3;
	private int tipoponderacion;
	
	/**
	 * @return Return the asiAbreviatura.
	 */
	public final String getAsiAbreviatura() {
		return asiAbreviatura;
	}
	/**
	 * @param asiAbreviatura The asiAbreviatura to set.
	 */
	public final void setAsiAbreviatura(String asiAbreviatura) {
		this.asiAbreviatura = asiAbreviatura;
	}
	/**
	 * @return Return the asiArea.
	 */
	public final long getAsiArea() {
		return asiArea;
	}
	/**
	 * @param asiArea The asiArea to set.
	 */
	public final void setAsiArea(long asiArea) {
		this.asiArea = asiArea;
	}
	/**
	 * @return Return the asiCodigo.
	 */
	public final long getAsiCodigo() {
		return asiCodigo;
	}
	/**
	 * @param asiCodigo The asiCodigo to set.
	 */
	public final void setAsiCodigo(long asiCodigo) {
		this.asiCodigo = asiCodigo;
	}
	/**
	 * @return Return the asiGrado.
	 */
	public final String[] getAsiGrado() {
		return asiGrado;
	}
	/**
	 * @param asiGrado The asiGrado to set.
	 */
	public final void setAsiGrado(String[] asiGrado) {
		this.asiGrado = asiGrado;
	}
	/**
	 * @return Return the asiInstitucion.
	 */
	public final long getAsiInstitucion() {
		return asiInstitucion;
	}
	/**
	 * @param asiInstitucion The asiInstitucion to set.
	 */
	public final void setAsiInstitucion(long asiInstitucion) {
		this.asiInstitucion = asiInstitucion;
	}
	/**
	 * @return Return the asiMetodologia.
	 */
	public final int getAsiMetodologia() {
		return asiMetodologia;
	}
	/**
	 * @param asiMetodologia The asiMetodologia to set.
	 */
	public final void setAsiMetodologia(int asiMetodologia) {
		this.asiMetodologia = asiMetodologia;
	}
	/**
	 * @return Return the asiNombre.
	 */
	public final String getAsiNombre() {
		return asiNombre;
	}
	/**
	 * @param asiNombre The asiNombre to set.
	 */
	public final void setAsiNombre(String asiNombre) {
		this.asiNombre = asiNombre;
	}
	/**
	 * @return Return the asiOrden.
	 */
	public final long getAsiOrden() {
		return asiOrden;
	}
	/**
	 * @param asiOrden The asiOrden to set.
	 */
	public final void setAsiOrden(long asiOrden) {
		this.asiOrden = asiOrden;
	}
	/**
	 * @return Return the asiVigencia.
	 */
	public final int getAsiVigencia() {
		 return asiVigencia;
	}
	/**
	 * @param asiVigencia The asiVigencia to set.
	 */
	public final void setAsiVigencia(int asiVigencia) {
		this.asiVigencia = asiVigencia;
	}
	public String getAsiEstado() {
		return asiEstado;
	}
	public void setAsiEstado(String asiEstado) {
		this.asiEstado = asiEstado;
	}
	/**
	 * @return the plan1
	 */
	public double getPlan1() {
		return plan1;
	}
	/**
	 * @param plan1 the plan1 to set
	 */
	public void setPlan1(double plan1) {
		this.plan1 = plan1;
	}
	/**
	 * @return the plan2
	 */
	public double getPlan2() {
		return plan2;
	}
	/**
	 * @param plan2 the plan2 to set
	 */
	public void setPlan2(double plan2) {
		this.plan2 = plan2;
	}
	/**
	 * @return the plan3
	 */
	public double getPlan3() {
		return plan3;
	}
	/**
	 * @param plan3 the plan3 to set
	 */
	public void setPlan3(double plan3) {
		this.plan3 = plan3;
	}
	/**
	 * @return the tipoponderacion
	 */
	public int getTipoponderacion() {
		return tipoponderacion;
	}
	/**
	 * @param tipoponderacion the tipoponderacion to set
	 */
	public void setTipoponderacion(int tipoponderacion) {
		this.tipoponderacion = tipoponderacion;
	}
	/**
	 * @return the planciclo1
	 */
	public double getPlanciclo1() {
		return planciclo1;
	}
	/**
	 * @param planciclo1 the planciclo1 to set
	 */
	public void setPlanciclo1(double planciclo1) {
		this.planciclo1 = planciclo1;
	}
	/**
	 * @return the planciclo2
	 */
	public double getPlanciclo2() {
		return planciclo2;
	}
	/**
	 * @param planciclo2 the planciclo2 to set
	 */
	public void setPlanciclo2(double planciclo2) {
		this.planciclo2 = planciclo2;
	}
	/**
	 * @return the planciclo3
	 */
	public double getPlanciclo3() {
		return planciclo3;
	}
	/**
	 * @param planciclo3 the planciclo3 to set
	 */
	public void setPlanciclo3(double planciclo3) {
		this.planciclo3 = planciclo3;
	}
	
	
	
	
 
}
