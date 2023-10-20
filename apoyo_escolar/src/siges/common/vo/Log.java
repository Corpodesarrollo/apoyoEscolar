package siges.common.vo;

/**
 * 19/07/2007 
 * @author Latined
 * @version 1.2
 */
public class Log {
	private int tipo;
	private int nivel;
	private long usuario;
	private long colegio;
	private long param1;
	private long param2;
	private long param3;
	private long param4;
	private long param5;
	private String mensaje;
	
	/**
	 * @param tipo
	 * @param nivel
	 * @param usuario
	 */
	public Log(int tipo, int nivel, long usuario) {
		this.tipo = tipo;
		this.nivel = nivel;
		this.usuario = usuario;
	}
	
	/**
	 * @param tipo
	 * @param nivel
	 * @param usuario
	 */
	public Log(int tipo, int nivel, long usuario,long colegio) {
		this.tipo = tipo;
		this.nivel = nivel;
		this.usuario = usuario;
		this.colegio = colegio;
	}
	
	/**
	 * @return Returns the mensaje.
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
	 * @return Returns the nivel.
	 */
	public int getNivel() {
		return nivel;
	}
	/**
	 * @param nivel The nivel to set.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return Returns the param1.
	 */
	public long getParam1() {
		return param1;
	}
	/**
	 * @param param1 The param1 to set.
	 */
	public void setParam1(long param1) {
		this.param1 = param1;
	}
	/**
	 * @return Returns the param2.
	 */
	public long getParam2() {
		return param2;
	}
	/**
	 * @param param2 The param2 to set.
	 */
	public void setParam2(long param2) {
		this.param2 = param2;
	}
	/**
	 * @return Returns the param3.
	 */
	public long getParam3() {
		return param3;
	}
	/**
	 * @param param3 The param3 to set.
	 */
	public void setParam3(long param3) {
		this.param3 = param3;
	}
	/**
	 * @return Returns the param4.
	 */
	public long getParam4() {
		return param4;
	}
	/**
	 * @param param4 The param4 to set.
	 */
	public void setParam4(long param4) {
		this.param4 = param4;
	}
	/**
	 * @return Returns the param5.
	 */
	public long getParam5() {
		return param5;
	}
	/**
	 * @param param5 The param5 to set.
	 */
	public void setParam5(long param5) {
		this.param5 = param5;
	}
	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Returns the usuario.
	 */
	public long getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return Return the colegio.
	 */
	public long getColegio() {
		return colegio;
	}

	/**
	 * @param colegio The colegio to set.
	 */
	public void setColegio(long colegio) {
		this.colegio = colegio;
	}
}
