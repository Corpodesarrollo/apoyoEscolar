package siges.filtro.vo;


public class FichaVO implements Cloneable{
	private String fichaId;
	private String fichaNombre;
	private String fichaUrl;
	private String fichaUrl2;
	public static final int RECURSO_FOTOGRAFIA_ESTUDIANTE=1;
	public static final int RECURSO_FOTOGRAFIA_PERSONAL=2;
	
	/**
	 * @return Returns the fichaId.
	 */
	public String getFichaId() {
		return fichaId;
	}
	/**
	 * @param fichaId The fichaId to set.
	 */
	public void setFichaId(String fichaId) {
		this.fichaId = fichaId;
	}
	/**
	 * @return Returns the fichaNombre.
	 */
	public String getFichaNombre() {
		return fichaNombre;
	}
	/**
	 * @param fichaNombre The fichaNombre to set.
	 */
	public void setFichaNombre(String fichaNombre) {
		this.fichaNombre = fichaNombre;
	}
	/**
	 * @return Returns the fichaUrl.
	 */
	public String getFichaUrl() {
		return fichaUrl;
	}
	/**
	 * @param fichaUrl The fichaUrl to set.
	 */
	public void setFichaUrl(String fichaUrl) {
		this.fichaUrl = fichaUrl;
	}
	/**
	 * @return Returns the fichaUrl2.
	 */
	public String getFichaUrl2() {
		return fichaUrl2;
	}
	/**
	 * @param fichaUrl2 The fichaUrl2 to set.
	 */
	public void setFichaUrl2(String fichaUrl2) {
		this.fichaUrl2 = fichaUrl2;
	}
	/**
	 * @return Return the rECURSO_FOTOGRAFIA_ESTUDIANTE.
	 */
	public int getRECURSO_FOTOGRAFIA_ESTUDIANTE() {
		return RECURSO_FOTOGRAFIA_ESTUDIANTE;
	}
	/**
	 * @return Return the rECURSO_FOTOGRAFIA_PERSONAL.
	 */
	public int getRECURSO_FOTOGRAFIA_PERSONAL() {
		return RECURSO_FOTOGRAFIA_PERSONAL;
	}
}
