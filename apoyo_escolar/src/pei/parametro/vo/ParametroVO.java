package pei.parametro.vo;

import siges.common.vo.Vo;

public class ParametroVO extends Vo{
	private int parTipo;
	private int parCodigo;
	private int parOrden;
	private String parNombre;
	private String parDescripcion;
	private String parAbreviatura;
	private int parEstado=1;
	private int parValor;
	private int parEdicion;
	private String parNombreOld;
	private String parNombreEstado;
	private boolean parDisabled;
	private String parDisabled_;
	
	/**
	 * @return the parTipo
	 */
	public int getParTipo() {
		return parTipo;
	}
	/**
	 * @param parTipo the parTipo to set
	 */
	public void setParTipo(int parTipo) {
		this.parTipo = parTipo;
	}
	/**
	 * @return the parCodigo
	 */
	public int getParCodigo() {
		return parCodigo;
	}
	/**
	 * @param parCodigo the parCodigo to set
	 */
	public void setParCodigo(int parCodigo) {
		this.parCodigo = parCodigo;
	}
	/**
	 * @return the parOrden
	 */
	public int getParOrden() {
		return parOrden;
	}
	/**
	 * @param parOrden the parOrden to set
	 */
	public void setParOrden(int parOrden) {
		this.parOrden = parOrden;
	}
	/**
	 * @return the parNombre
	 */
	public String getParNombre() {
		return parNombre;
	}
	/**
	 * @param parNombre the parNombre to set
	 */
	public void setParNombre(String parNombre) {
		this.parNombre = parNombre;
	}
	/**
	 * @return the parDescripcion
	 */
	public String getParDescripcion() {
		return parDescripcion;
	}
	/**
	 * @param parDescripcion the parDescripcion to set
	 */
	public void setParDescripcion(String parDescripcion) {
		this.parDescripcion = parDescripcion;
	}
	/**
	 * @return the parAbreviatura
	 */
	public String getParAbreviatura() {
		return parAbreviatura;
	}
	/**
	 * @param parAbreviatura the parAbreviatura to set
	 */
	public void setParAbreviatura(String parAbreviatura) {
		this.parAbreviatura = parAbreviatura;
	}
	/**
	 * @return the parEstado
	 */
	public int getParEstado() {
		return parEstado;
	}
	/**
	 * @param parEstado the parEstado to set
	 */
	public void setParEstado(int parEstado) {
		this.parEstado = parEstado;
	}
	/**
	 * @return the parValor
	 */
	public int getParValor() {
		return parValor;
	}
	/**
	 * @param parValor the parValor to set
	 */
	public void setParValor(int parValor) {
		this.parValor = parValor;
	}
	/**
	 * @return the parEdicion
	 */
	public int getParEdicion() {
		return parEdicion;
	}
	/**
	 * @param parEdicion the parEdicion to set
	 */
	public void setParEdicion(int parEdicion) {
		this.parEdicion = parEdicion;
	}
	/**
	 * @return the parNombreOld
	 */
	public String getParNombreOld() {
		return parNombreOld;
	}
	/**
	 * @param parNombreOld the parNombreOld to set
	 */
	public void setParNombreOld(String parNombreOld) {
		this.parNombreOld = parNombreOld;
	}
	/**
	 * @return the parNombreEstado
	 */
	public String getParNombreEstado() {
		return parNombreEstado;
	}
	/**
	 * @param parNombreEstado the parNombreEstado to set
	 */
	public void setParNombreEstado(String parNombreEstado) {
		this.parNombreEstado = parNombreEstado;
	}
	/**
	 * @return the parDisabled
	 */
	public boolean isParDisabled() {
		return parDisabled;
	}
	/**
	 * @param parDisabled the parDisabled to set
	 */
	public void setParDisabled(boolean parDisabled) {
		this.parDisabled = parDisabled;
	}
	/**
	 * @return the parDisabled_
	 */
	public String getParDisabled_() {
		return parDisabled_;
	}
	/**
	 * @param parDisabled the parDisabled_ to set
	 */
	public void setParDisabled_(String parDisabled) {
		parDisabled_ = parDisabled;
	}
}
