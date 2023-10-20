package siges.gestionAdministrativa.cierreVigencia.vo;

public class CierreHilosVO  {

	public  int usuario = -99;
	public  int vigencia = -99;
	public  int codInst = -99;
	public String estado = null;
	public String fecha = null;
	public String fechaSistema = null;
	public String mensaje = null;
	public int codigoEstado = 0;

	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public int getVigencia() {
		return vigencia;
	}
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(String fechaSistema) {
		this.fechaSistema = fechaSistema;
	}
	/**
	 * @return Return the mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje The mensaje to set.
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return Return the codigoEstado.
	 */
	public int getCodigoEstado() {
		return codigoEstado;
	}
	/**
	 * @param codigoEstado The codigoEstado to set.
	 */
	public void setCodigoEstado(int codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	public int getCodInst() {
		return codInst;
	}
	public void setCodInst(int codInst) {
		this.codInst = codInst;
	}
}
