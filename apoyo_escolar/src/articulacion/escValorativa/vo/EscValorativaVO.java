package articulacion.escValorativa.vo;

import siges.common.vo.Vo;

public class EscValorativaVO extends Vo{
	public long artEscValCodInst;
	public int artEscValCodMetod;
	public int artEscValAnoVigencia;
	public int artEscValPerVigencia;
	public String artEscValConceptual;
	public String artEscValNombre;
	public float artEscValRangoIni;
	public float artEscValRangoFin;
	
	public int getArtEscValAnoVigencia() {
		return artEscValAnoVigencia;
	}
	public void setArtEscValAnoVigencia(int artEscValAnoVigencia) {
		this.artEscValAnoVigencia = artEscValAnoVigencia;
	}
	public long getArtEscValCodInst() {
		return artEscValCodInst;
	}
	public void setArtEscValCodInst(long artEscValCodInst) {
		this.artEscValCodInst = artEscValCodInst;
	}
	public int getArtEscValCodMetod() {
		return artEscValCodMetod;
	}
	public void setArtEscValCodMetod(int artEscValCodMetod) {
		this.artEscValCodMetod = artEscValCodMetod;
	}
	public String getArtEscValConceptual() {
		return artEscValConceptual;
	}
	public void setArtEscValConceptual(String artEscValConceptual) {
		this.artEscValConceptual = artEscValConceptual;
	}
	public String getArtEscValNombre() {
		return artEscValNombre;
	}
	public void setArtEscValNombre(String artEscValNombre) {
		this.artEscValNombre = artEscValNombre;
	}
	public int getArtEscValPerVigencia() {
		return artEscValPerVigencia;
	}
	public void setArtEscValPerVigencia(int artEscValPerVigencia) {
		this.artEscValPerVigencia = artEscValPerVigencia;
	}
	public float getArtEscValRangoFin() {
		return artEscValRangoFin;
	}
	public void setArtEscValRangoFin(float artEscValRangoFin) {
		this.artEscValRangoFin = artEscValRangoFin;
	}
	public float getArtEscValRangoIni() {
		return artEscValRangoIni;
	}
	public void setArtEscValRangoIni(float artEscValRangoIni) {
		this.artEscValRangoIni = artEscValRangoIni;
	}

}
