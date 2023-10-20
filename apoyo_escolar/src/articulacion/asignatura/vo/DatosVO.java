package articulacion.asignatura.vo;

import siges.common.vo.Vo;

public class DatosVO extends Vo{

	private String componente;
	private long inst;
	private int especialidad;
	private int area;
	private int periodo;
	private int complementaria;
	private long anoVigencia;
	private int perVigencia;
	private int asignatura;
		
	public int getArea() {
		return area;
	}

	public String getComponente() {
		return componente;
	}

	public int getEspecialidad() {
		return especialidad;
	}

	public int getPeriodo() {
		return periodo;
	}

	/**
	 * @param area The area to set.
	 */
	public void setArea(int area) {
		this.area = area;
	}

	/**
	 * @param componente The componente to set.
	 */
	public void setComponente(String componente) {
		this.componente = componente;
	}

	/**
	 * @param especialidad The especialidad to set.
	 */
	public void setEspecialidad(int especialidad) {
		this.especialidad = especialidad;
	}

	/**
	 * @param periodo The periodo to set.
	 */
	public void setPeriodo(int periodo) {
		
		this.periodo = periodo;
	}

	public int getComplementaria() {
		return complementaria;
	}

	public void setComplementaria(int complementaria) {
		this.complementaria = complementaria;
	}

	public long getAnoVigencia() {
		return anoVigencia;
	}

	public void setAnoVigencia(long anoVigencia) {
		this.anoVigencia = anoVigencia;
	}

	public int getPerVigencia() {
		return perVigencia;
	}

	public void setPerVigencia(int perVigencia) {
		this.perVigencia = perVigencia;
	}

	public long getInst() {
		return inst;
	}

	public void setInst(long inst) {
		this.inst = inst;
	}

	public int getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(int asignatura) {
		this.asignatura = asignatura;
	}

	
}
