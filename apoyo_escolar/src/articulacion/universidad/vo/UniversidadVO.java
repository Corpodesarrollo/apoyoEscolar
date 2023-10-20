package articulacion.universidad.vo;

import siges.common.vo.Vo;

public class UniversidadVO extends Vo{
	private int guniCodigo;
	private String guniNombre;
	private String guniDireccionsp;
	private String guniTelefono;
	
	private String guniContacto;
	private String guniTelcontacto;
	private String guniCorreo;
	private int guniEstado;
	
	public String getGuniContacto() {
		return guniContacto;
	}
	public void setGuniContacto(String guniContacto) {
		this.guniContacto = guniContacto;
	}
	public String getGuniTelcontacto() {
		return guniTelcontacto;
	}
	public void setGuniTelcontacto(String guniTelcontacto) {
		this.guniTelcontacto = guniTelcontacto;
	}
	public String getGuniCorreo() {
		return guniCorreo;
	}
	public void setGuniCorreo(String guniCorreo) {
		this.guniCorreo = guniCorreo;
	}
	public int getGuniEstado() {
		return guniEstado;
	}
	public void setGuniEstado(int guniEstado) {
		this.guniEstado = guniEstado;
	}
	public int getGuniCodigo() {
		return guniCodigo;
	}
	public void setGuniCodigo(int guniCodigo) {
		this.guniCodigo = guniCodigo;
	}
	public String getGuniDireccionsp() {
		return (guniDireccionsp!=null)? guniDireccionsp : "";
	}
	public void setGuniDireccionsp(String guniDireccionsp) {
		this.guniDireccionsp = guniDireccionsp;
	}
	public String getGuniNombre() {
		return (guniNombre!=null)? guniNombre : "";
	}
	public void setGuniNombre(String guniNombre) {
		this.guniNombre = guniNombre;
	}
	public String getGuniTelefono() {
		return (guniTelefono!=null)? guniTelefono : "";
	}
	public void setGuniTelefono(String guniTelefono) {
		this.guniTelefono = guniTelefono;
	}
	
	
	
}
