/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.vo;

import java.util.List;
import java.util.Map;

/**
 * 3/07/2008 
 * @author Latined
 * @version 1.2
 */
public class MailVO {
	private String mailAsunto;
	private String mailMensaje;
	private String mailAdjunto;
	private String mailNombreAdjunto;
	private int mailBandera;
	private List mailCorreos;
	private Map mailParams;
	private int mailEstado=1;
	private int mailTotal;
	private int mailEnviados;
	
	/**
	 * @return Return the mailAdjunto.
	 */
	public String getMailAdjunto() {
		return mailAdjunto;
	}
	/**
	 * @param mailAdjunto The mailAdjunto to set.
	 */
	public void setMailAdjunto(String mailAdjunto) {
		this.mailAdjunto = mailAdjunto;
	}
	/**
	 * @return Return the mailAsunto.
	 */
	public String getMailAsunto() {
		return mailAsunto;
	}
	/**
	 * @param mailAsunto The mailAsunto to set.
	 */
	public void setMailAsunto(String mailAsunto) {
		this.mailAsunto = mailAsunto;
	}
	/**
	 * @return Return the mailBandera.
	 */
	public int getMailBandera() {
		return mailBandera;
	}
	/**
	 * @param mailBandera The mailBandera to set.
	 */
	public void setMailBandera(int mailBandera) {
		this.mailBandera = mailBandera;
	}
	/**
	 * @return Return the mailCorreos.
	 */
	public List getMailCorreos() {
		return mailCorreos;
	}
	/**
	 * @param mailCorreos The mailCorreos to set.
	 */
	public void setMailCorreos(List mailCorreos) {
		this.mailCorreos = mailCorreos;
	}
	/**
	 * @return Return the mailEnviados.
	 */
	public int getMailEnviados() {
		return mailEnviados;
	}
	/**
	 * @param mailEnviados The mailEnviados to set.
	 */
	public void setMailEnviados(int mailEnviados) {
		this.mailEnviados = mailEnviados;
	}
	/**
	 * @return Return the mailEstado.
	 */
	public int getMailEstado() {
		return mailEstado;
	}
	/**
	 * @param mailEstado The mailEstado to set.
	 */
	public void setMailEstado(int mailEstado) {
		this.mailEstado = mailEstado;
	}
	/**
	 * @return Return the mailMensaje.
	 */
	public String getMailMensaje() {
		return mailMensaje;
	}
	/**
	 * @param mailMensaje The mailMensaje to set.
	 */
	public void setMailMensaje(String mailMensaje) {
		this.mailMensaje = mailMensaje;
	}
	/**
	 * @return Return the mailNombreAdjunto.
	 */
	public String getMailNombreAdjunto() {
		return mailNombreAdjunto;
	}
	/**
	 * @param mailNombreAdjunto The mailNombreAdjunto to set.
	 */
	public void setMailNombreAdjunto(String mailNombreAdjunto) {
		this.mailNombreAdjunto = mailNombreAdjunto;
	}
	/**
	 * @return Return the mailParams.
	 */
	public Map getMailParams() {
		return mailParams;
	}
	/**
	 * @param mailParams The mailParams to set.
	 */
	public void setMailParams(Map mailParams) {
		this.mailParams = mailParams;
	}
	/**
	 * @return Return the mailTotal.
	 */
	public int getMailTotal() {
		return mailTotal;
	}
	/**
	 * @param mailTotal The mailTotal to set.
	 */
	public void setMailTotal(int mailTotal) {
		this.mailTotal = mailTotal;
	}

}
