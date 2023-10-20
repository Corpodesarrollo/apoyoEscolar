package siges.gestionAdministrativa.gestionActividades.vo;

import siges.common.vo.Vo;

public class GestionActVO extends Vo{
	
	private int nivel;
	private String nombre;
	private int estado;
	private String fecha;
	private String descripcion;
	private String horaInicial;
	private String horaFinal;
	private String lugar;
	private String participantes;
	private long codJerarquia;
	private String usuario;
	private long codigo;
	
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public long getCodJerarquia() {
		return codJerarquia;
	}
	public void setCodJerarquia(long codJerarquia) {
		this.codJerarquia = codJerarquia;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getHoraInicial() {
		return horaInicial;
	}
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}
	public String getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getParticipantes() {
		return participantes;
	}
	public void setParticipantes(String participantes) {
		this.participantes = participantes;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
