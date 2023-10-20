/**
 * 
 */
package articulacion.artEncuesta.vo;

import java.util.List;

import siges.common.vo.Vo;

/**
 * 1/09/2007 
 * @author Latined
 * @version 1.2
 */
public class ConsolidadoVO extends Vo{
	
	
	private int estConsecutivo;	
	private String conEstado;
	private double conNumero;
	private double conPorcentaje;	
	
	
	public String getConEstado() {
		return conEstado;
	}
	public void setConEstado(String conEstado) {
		this.conEstado = conEstado;
	}

	public double getConNumero() {
		return conNumero;
	}
	public double getConPorcentaje() {
		return conPorcentaje;
	}
	public void setConNumero(double conNumero) {
		this.conNumero = conNumero;
	}
	public void setConPorcentaje(double conPorcentaje) {
		this.conPorcentaje = conPorcentaje;
	}
	public int getEstConsecutivo() {
		return estConsecutivo;
	}
	public void setEstConsecutivo(int estConsecutivo) {
		this.estConsecutivo = estConsecutivo;
	}
	
	
	

}
