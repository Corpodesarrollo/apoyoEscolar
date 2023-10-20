package articulacion.asigTutor.vo;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{
	private int codigo;
	private String documento;
	private String nombre;
	private long codTutor;
	private String NomTutor;
	

	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public String getNomTutor() {
		return (NomTutor!=null)? NomTutor : "";
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setNomTutor(String nomTutor) {
		if(!nomTutor.equals("null null"))
			NomTutor = nomTutor;
	}
	public long getCodTutor() {
		return codTutor;
	}
	public void setCodTutor(long codTutor) {
		this.codTutor = codTutor;
	}
}
