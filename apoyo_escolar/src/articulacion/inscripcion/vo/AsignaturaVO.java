package articulacion.inscripcion.vo;

import siges.common.vo.Vo;

public class AsignaturaVO extends Vo{
	private int codigo;
	private String nombre;
	private int creditos;
	private int grupo;
	private int cupos;
	private int cupogeneral;
	private int clave;
	public int getClave() {
		return clave;
	}
	public void setClave(int clave) {
		this.clave = clave;
	}
	public int getCupos() {
		return cupos;
	}
	public void setCupos(int cupos) {
		this.cupos = cupos;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
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
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public int getCupogeneral() {
		return cupogeneral;
	}
	public void setCupogeneral(int cupogeneral) {
		this.cupogeneral = cupogeneral;
	}
}
