package articulacion.asigTutor.vo;

import siges.common.vo.Vo;

public class DocenteVO extends Vo{
	private int codigo;
	private String nombre;
	private String ApellTutor;
	private String nomTutor;
	
	public String getApellTutor() {
		return (ApellTutor!=null)? ApellTutor : "";
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return (nombre!=null)? nombre : "";
	}
	public String getNomTutor() {
		return (nomTutor!=null)? nomTutor : "";
	}
	public void setApellTutor(String apellTutor) {
		ApellTutor = apellTutor;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setNomTutor(String nomTutor) {
		this.nomTutor = nomTutor;
	}
	
	
	
}
