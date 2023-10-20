package articulacion.artAusencias.vo;

import siges.common.vo.Vo;

public class EstDiasVO extends Vo{

	private long[] artAusCodEst;
	private String[] estDia;
	
	public long[] getArtAusCodEst() {
		return artAusCodEst;
	}
	public void setArtAusCodEst(long[] artAusCodEst) {
		this.artAusCodEst = artAusCodEst;
	}
	public String[] getEstDia() {
		return estDia;
	}
	public void setEstDia(String[] estDia) {
		this.estDia = estDia;
	}

	
}
