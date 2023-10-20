package pei.registro.vo;

import siges.common.vo.Vo;

public class CriterioVO extends Vo {
	private long critInstitucion;
	private int critTipo;
	private int critCodigo;
	private int critValor;
	private int critValorMaximo;
	
	private String critNombre;
	private String critAbreviatura;
	private String critDescripcion;
	
	/**
	 * @return the critInstitucion
	 */
	public long getCritInstitucion() {
		return critInstitucion;
	}
	/**
	 * @param critInstitucion the critInstitucion to set
	 */
	public void setCritInstitucion(long critInstitucion) {
		this.critInstitucion = critInstitucion;
	}
	/**
	 * @return the critTipo
	 */
	public int getCritTipo() {
		return critTipo;
	}
	/**
	 * @param critTipo the critTipo to set
	 */
	public void setCritTipo(int critTipo) {
		this.critTipo = critTipo;
	}
	/**
	 * @return the critCodigo
	 */
	public int getCritCodigo() {
		return critCodigo;
	}
	/**
	 * @param critCodigo the critCodigo to set
	 */
	public void setCritCodigo(int critCodigo) {
		this.critCodigo = critCodigo;
	}
	/**
	 * @return the critValor
	 */
	public int getCritValor() {
		return critValor;
	}
	/**
	 * @param critValor the critValor to set
	 */
	public void setCritValor(int critValor) {
		this.critValor = critValor;
	}
	/**
	 * @return the critNombre
	 */
	public String getCritNombre() {
		return critNombre;
	}
	/**
	 * @param critNombre the critNombre to set
	 */
	public void setCritNombre(String critNombre) {
		this.critNombre = critNombre;
	}
	/**
	 * @return the critAbreviatura
	 */
	public String getCritAbreviatura() {
		return critAbreviatura;
	}
	/**
	 * @param critAbreviatura the critAbreviatura to set
	 */
	public void setCritAbreviatura(String critAbreviatura) {
		this.critAbreviatura = critAbreviatura;
	}
	/**
	 * @return the critValorMaximo
	 */
	public int getCritValorMaximo() {
		return critValorMaximo;
	}
	/**
	 * @param critValorMaximo the critValorMaximo to set
	 */
	public void setCritValorMaximo(int critValorMaximo) {
		this.critValorMaximo = critValorMaximo;
	}
	/**
	 * @return the critDescripcion
	 */
	public String getCritDescripcion() {
		return critDescripcion;
	}
	/**
	 * @param critDescripcion the critDescripcion to set
	 */
	public void setCritDescripcion(String critDescripcion) {
		this.critDescripcion = critDescripcion;
	}
	
}
