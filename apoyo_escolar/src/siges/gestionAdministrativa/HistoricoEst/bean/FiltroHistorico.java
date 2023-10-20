package siges.gestionAdministrativa.HistoricoEst.bean;

public class FiltroHistorico implements Cloneable {
	private String pertipdocum;
	private String pernumdocum;
	private String nombrecompleto;

	/**
	 * asigna el campo pertipdocum
	 * 
	 * @param String
	 *            s
	 */

	public void setpertipdocum(String s) {
		this.pertipdocum = s;
	}

	/**
	 * retorna el campo pertipdocum
	 * 
	 * @return String
	 */
	public String getpertipdocum() {
		return pertipdocum != null ? pertipdocum : "";
	}

	/**
	 * asigna el campo pernumdocum
	 * 
	 * @param String
	 *            s
	 */

	public void setpernumdocum(String s) {
		this.pernumdocum = s;
	}

	/**
	 * retorna el campo pernumdocum
	 * 
	 * @return String
	 */
	public String getpernumdocum() {
		return pernumdocum != null ? pernumdocum : "";
	}

	/**
	 * asigna el campo nombrecompleto
	 * 
	 * @param String
	 *            s
	 */

	public void setnombrecompleto(String s) {
		this.nombrecompleto = s;
	}

	/**
	 * retorna el campo nombrecompleto
	 * 
	 * @return String
	 */
	public String getnombrecompleto() {
		return nombrecompleto != null ? nombrecompleto : "";
	}

}
