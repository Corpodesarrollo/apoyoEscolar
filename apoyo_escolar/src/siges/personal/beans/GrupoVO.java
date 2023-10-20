package siges.personal.beans;

public class GrupoVO {
	private String gruNombre;
	private int gruCodigo;
	private Long gruCodigoJerarquiaGrupo;
	private Long gruCodigoJerarquia;
	private int gruGrado;
	private Long gruInstitucion;
	private int gruSede;
	private int gruJornada;
	
	public String getGruNombre() {
		return gruNombre;
	}
	public void setGruNombre(String gruNombre) {
		this.gruNombre = gruNombre;
	}
	public int getGruCodigo() {
		return gruCodigo;
	}
	public Long getGruCodigoJerarquiaGrupo() {
		return gruCodigoJerarquiaGrupo;
	}
	public void setGruCodigoJerarquiaGrupo(Long gruCodigoJerarquiaGrupo) {
		this.gruCodigoJerarquiaGrupo = gruCodigoJerarquiaGrupo;
	}
	public void setGruCodigo(int gruCodigo) {
		this.gruCodigo = gruCodigo;
	}
	public Long getGruCodigoJerarquia() {
		return gruCodigoJerarquia;
	}
	public void setGruCodigoJerarquia(Long gruCodigoJerarquia) {
		this.gruCodigoJerarquia = gruCodigoJerarquia;
	}
	public int getGruGrado() {
		return gruGrado;
	}
	public void setGruGrado(int gruGrado) {
		this.gruGrado = gruGrado;
	}
	public Long getGruInstitucion() {
		return gruInstitucion;
	}
	public void setGruInstitucion(Long gruInstitucion) {
		this.gruInstitucion = gruInstitucion;
	}
	public int getGruSede() {
		return gruSede;
	}
	public void setGruSede(int gruSede) {
		this.gruSede = gruSede;
	}
	public int getGruJornada() {
		return gruJornada;
	}
	public void setGruJornada(int gruJornada) {
		this.gruJornada = gruJornada;
	}

}
