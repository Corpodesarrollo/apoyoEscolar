package articulacion.asignatura.vo;

import siges.common.vo.Vo;

public class ListaEspecialidadVO extends Vo{
	private int codigo;
	private String nombre;
	private String tipoPeriodo;
	private String codTipoPeriodo;
	private int periodos;
	
	public String getCodTipoPeriodo() {
		return codTipoPeriodo;
	}
	public void setCodTipoPeriodo(String codTipoPeriodo) {
		this.codTipoPeriodo = codTipoPeriodo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPeriodos() {
		return periodos;
	}
	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}
	public String getTipoPeriodo() {
		return tipoPeriodo;
	}
	public void setTipoPeriodo(String tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
}
