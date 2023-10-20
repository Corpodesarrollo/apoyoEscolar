package articulacion.asignatura.vo;

import siges.common.vo.Vo;


public class PruebaVO extends Vo{
	
	private long artPruCodAsig;
	private long artPruCodigo;
	private String artPruNombre;
	private String artPruAbreviatura;
	private double artPruPorcentaje;
	private int artPruOrden;
	private long pruebaPrincipal;
	private String tipo;
	private int artPruBimestre;
	private double artPruTotal;
	
	public long getPruebaPrincipal() {
		return pruebaPrincipal;
	}
	public void setPruebaPrincipal(long pruebaPrincipal) {
		this.pruebaPrincipal = pruebaPrincipal;
	}
	public long getArtPruCodigo() {
		return artPruCodigo;
	}
	public void setArtPruCodigo(long artPruCodigo) {
		this.artPruCodigo = artPruCodigo;
	}
	public int getArtPruOrden() {
		return artPruOrden;
	}
	public String getArtPruAbreviatura() {
		return artPruAbreviatura;
	}
	public long getArtPruCodAsig() {
		return artPruCodAsig;
	}
	public String getArtPruNombre() {
		return artPruNombre;
	}
	public double getArtPruPorcentaje() {
		return artPruPorcentaje;
	}
	public void setArtPruOrden(int artPruOrden) {
		this.artPruOrden = artPruOrden;
	}
	public void setArtPruAbreviatura(String artPruAbreviatura) {
		this.artPruAbreviatura = artPruAbreviatura;
	}
	public void setArtPruCodAsig(long artPruCodAsig) {
		this.artPruCodAsig = artPruCodAsig;
	}
	public void setArtPruNombre(String artPruNombre) {
		this.artPruNombre = artPruNombre;
	}
	public void setArtPruPorcentaje(double artPruPorcentaje) {
		this.artPruPorcentaje = artPruPorcentaje;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Return the artPruBimestre.
	 */
	public int getArtPruBimestre() {
		return artPruBimestre;
	}
	/**
	 * @param artPruBimestre The artPruBimestre to set.
	 */
	public void setArtPruBimestre(int artPruBimestre) {
		this.artPruBimestre = artPruBimestre;
	}
	/**
	 * @return Return the artPruTotal.
	 */
	public double getArtPruTotal() {
		return artPruTotal;
	}
	/**
	 * @param artPruTotal The artPruTotal to set.
	 */
	public void setArtPruTotal(double artPruTotal) {
		this.artPruTotal = artPruTotal;
	}
}