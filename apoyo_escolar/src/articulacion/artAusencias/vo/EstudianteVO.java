package articulacion.artAusencias.vo;

import java.util.List;

import siges.common.vo.Vo;

public class EstudianteVO extends Vo{

	private long codigo;
	private String documento;
	private String nombre;
	private String apellido;
	private List dias;
	
	public List getDias() {
		return dias;
	}
	public void setDias(List dias) {
		this.dias = dias;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
