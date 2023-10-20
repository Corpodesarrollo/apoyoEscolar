package siges.gestionAcademica.inasistencia.vo;

/**
 * 4/09/2007 
 * @author Latined
 * @version 1.2
 */
public class FallaEstudiante {
	private int faDia;
	private int faDiaSemana;
	private int faChecked;
	private long faMotivo=-99;
	private String faAsig;
	private int faDisabled;
	
	/**
	 * @return Return the faAsig.
	 */
	public String getFaAsig() {
		return faAsig;
	}
	/**
	 * @param faAsig The faAsig to set.
	 */
	public void setFaAsig(String faAsig) {
		this.faAsig = faAsig;
	}
	/**
	 * @return Return the faDia.
	 */
	public int getFaDia() {
		return faDia;
	}
	/**
	 * @param faDia The faDia to set.
	 */
	public void setFaDia(int faDia) {
		this.faDia = faDia;
	}
	/**
	 * @return Return the faMotivo.
	 */
	public long getFaMotivo() {
		return faMotivo;
	}
	/**
	 * @param faMotivo The faMotivo to set.
	 */
	public void setFaMotivo(long faMotivo) {
		this.faMotivo = faMotivo;
	}
	/**
	 * @return Return the faDiaSemana.
	 */
	public int getFaDiaSemana() {
		return faDiaSemana;
	}
	/**
	 * @param faDiaSemana The faDiaSemana to set.
	 */
	public void setFaDiaSemana(int faDiaSemana) {
		this.faDiaSemana = faDiaSemana;
	}
	/**
	 * @return Return the faChecked.
	 */
	public int getFaChecked() {
		return faChecked;
	}
	/**
	 * @param faChecked The faChecked to set.
	 */
	public void setFaChecked(int faChecked) {
		this.faChecked = faChecked;
	}
	/**
	 * @return Return the faDisabled.
	 */
	public int getFaDisabled() {
		return faDisabled;
	}
	/**
	 * @param faDisabled The faDisabled to set.
	 */
	public void setFaDisabled(int faDisabled) {
		this.faDisabled = faDisabled;
	}
}
