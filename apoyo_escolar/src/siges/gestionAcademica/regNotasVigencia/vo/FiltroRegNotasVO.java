/**
 * 
 */
package siges.gestionAcademica.regNotasVigencia.vo;

import siges.common.vo.FiltroCommonVO;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroRegNotasVO extends FiltroCommonVO {
	private int filTipoDoc;
	private String filNumDoc;
	private String filNom1;
	private String filNom2;
	private String filApell1;
	private String filApell2;
	private int filprom;
	private String filobs;
	private int filtipoprom;
	private int filtipo;
	private int cantPags;
	private int numPag = 1;
	private int numFila;
	private long totalPag;

	private String busquedaAvanzada = "0";

	public String getFilApell1() {
		return filApell1 == null ? filApell1 : filApell1.trim();
	}

	public void setFilApell1(String filApell1) {
		this.filApell1 = filApell1;
	}

	public String getFilApell2() {
		return filApell2 == null ? filApell2 : filApell2.trim();
	}

	public void setFilApell2(String filApell2) {
		this.filApell2 = filApell2;
	}

	public String getFilNom1() {
		return filNom1 == null ? filNom1 : filNom1.trim();
	}

	public void setFilNom1(String filNom1) {
		this.filNom1 = filNom1;
	}

	public String getFilNom2() {
		return filNom2 == null ? filNom2 : filNom2.trim();
	}

	public void setFilNom2(String filNom2) {
		this.filNom2 = filNom2;
	}

	public String getFilNumDoc() {
		return filNumDoc == null ? filNumDoc : filNumDoc.trim();
	}

	public void setFilNumDoc(String filNumDoc) {
		this.filNumDoc = filNumDoc;
	}

	public int getFilTipoDoc() {
		return filTipoDoc;
	}

	public void setFilTipoDoc(int filTipoDoc) {
		this.filTipoDoc = filTipoDoc;
	}

	public String getBusquedaAvanzada() {
		return busquedaAvanzada;
	}

	public void setBusquedaAvanzada(String busquedaAvanzada) {
		this.busquedaAvanzada = busquedaAvanzada;
	}

	public int getCantPags() {
		return cantPags;
	}

	public void setCantPags(int cantPags) {
		this.cantPags = cantPags;
	}

	public int getNumPag() {
		return numPag;
	}

	public void setNumPag(int numPag) {
		this.numPag = numPag;
	}

	public int getNumFila() {
		return numFila;
	}

	public void setNumFila(int numFila) {
		this.numFila = numFila;
	}

	public long getTotalPag() {
		return totalPag;
	}

	public void setTotalPag(long totalPag) {
		this.totalPag = totalPag;
	}

	public int getFilprom() {
		return filprom;
	}

	public void setFilprom(int filprom) {
		this.filprom = filprom;
	}

	public String getFilobs() {
		return filobs;
	}

	public void setFilobs(String filobs) {
		this.filobs = filobs;
	}

	public int getFiltipoprom() {
		return filtipoprom;
	}

	public void setFiltipoprom(int filtipoprom) {
		this.filtipoprom = filtipoprom;
	}

	public int getFiltipo() {
		return filtipo;
	}

	public void setFiltipo(int filtipo) {
		this.filtipo = filtipo;
	}

}
