package siges.login.beans;

import siges.common.vo.Vo;

public class AlertaVO extends Vo {
	
	private String alerAsunto;
	private String alerMensaje;
	private String alerEnviarPor;
	private String alerFechCaduca;
	public String getAlerAsunto() {
		return alerAsunto;
	}
	public void setAlerAsunto(String alerAsunto) {
		this.alerAsunto = alerAsunto;
	}
	public String getAlerEnviarPor() {
		return alerEnviarPor;
	}
	public void setAlerEnviarPor(String alerEnviarPor) {
		this.alerEnviarPor = alerEnviarPor;
	}
	public String getAlerFechCaduca() {
		return alerFechCaduca;
	}
	public void setAlerFechCaduca(String alerFechCaduca) {
		this.alerFechCaduca = alerFechCaduca;
	}
	public String getAlerMensaje() {
		return alerMensaje;
	}
	public void setAlerMensaje(String alerMensaje) {
		this.alerMensaje = alerMensaje;
	}
	

}
