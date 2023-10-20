package articulacion.plantillaEvaluacion.vo;

import siges.common.vo.Vo;

public class datosPruebasVO extends Vo{

	private long codigoPrueba;
	private long codigoSubPrueba;
	private String nombrePrueba;
	private String abreviatura;
	private long porcentaje;
	
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public long getCodigoPrueba() {
		return codigoPrueba;
	}
	public void setCodigoPrueba(long codigoPrueba) {
		this.codigoPrueba = codigoPrueba;
	}
	public long getCodigoSubPrueba() {
		return codigoSubPrueba;
	}
	public void setCodigoSubPrueba(long codigoSubPrueba) {
		this.codigoSubPrueba = codigoSubPrueba;
	}
	public String getNombrePrueba() {
		return nombrePrueba;
	}
	public void setNombrePrueba(String nombrePrueba) {
		this.nombrePrueba = nombrePrueba;
	}
	public long getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(long porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	
}
