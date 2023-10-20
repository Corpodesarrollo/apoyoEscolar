package articulacion.grupoArt.vo;

import siges.common.vo.Vo;

public class GrupoArtVO extends Vo{
	private int artGruCodInst;
	private int artGruCodSede;
	private int artGruCodJornada;
	private int artGruAnoVigencia;
	private int artGruPerVigencia;
	private int artGruPerEsp;
	private int artGruComponente;
	private int artGruCodEsp;
	private long artGruCodigo;
	private int artGruConsecutivo;
	private boolean artGruRepite;
	private int artGruOrden;
	private int artGruCupoNivel;
	private int artGruCupoNoNivel;
	
	private int artGruCupoGeneral;
	private int artGruInscNivel;
	private int artGruInscNoNivel;
	private int artGruInscGeneral;
	
	private int artGruCodAsig;
	private String artGruCodAsigNombre;
	
	private long[] asignaturas;
	
	private boolean check;
	
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public int getArtGruAnoVigencia() {
		return artGruAnoVigencia;
	}
	public void setArtGruAnoVigencia(int artGruAnoVigencia) {
		this.artGruAnoVigencia = artGruAnoVigencia;
	}
	public int getArtGruCodEsp() {
		return artGruCodEsp;
	}
	public void setArtGruCodEsp(int artGruCodEsp) {
		this.artGruCodEsp = artGruCodEsp;
	}
	public long getArtGruCodigo() {
		return artGruCodigo;
	}
	public void setArtGruCodigo(long artGruCodigo) {
		this.artGruCodigo = artGruCodigo;
	}
	public int getArtGruCodInst() {
		return artGruCodInst;
	}
	public void setArtGruCodInst(int artGruCodInst) {
		this.artGruCodInst = artGruCodInst;
	}
	public int getArtGruCodJornada() {
		return artGruCodJornada;
	}
	public void setArtGruCodJornada(int artGruCodJornada) {
		this.artGruCodJornada = artGruCodJornada;
	}
	public int getArtGruCodSede() {
		return artGruCodSede;
	}
	public void setArtGruCodSede(int artGruCodSede) {
		this.artGruCodSede = artGruCodSede;
	}
	public int getArtGruComponente() {
		return artGruComponente;
	}
	public void setArtGruComponente(int artGruComponente) {
		this.artGruComponente = artGruComponente;
	}
	public int getArtGruConsecutivo() {
		return artGruConsecutivo;
	}
	public void setArtGruConsecutivo(int artGruConsecutivo) {
		this.artGruConsecutivo = artGruConsecutivo;
	}
	public int getArtGruCupoNivel() {
		return artGruCupoNivel;
	}
	public void setArtGruCupoNivel(int artGruCupoNivel) {
		this.artGruCupoNivel = artGruCupoNivel;
	}
	public int getArtGruOrden() {
		return artGruOrden;
	}
	public void setArtGruOrden(int artGruOrden) {
		this.artGruOrden = artGruOrden;
	}
	public int getArtGruPerEsp() {
		return artGruPerEsp;
	}
	public void setArtGruPerEsp(int artGruPerEsp) {
		this.artGruPerEsp = artGruPerEsp;
	}
	public int getArtGruPerVigencia() {
		return artGruPerVigencia;
	}
	public void setArtGruPerVigencia(int artGruPerVigencia) {
		this.artGruPerVigencia = artGruPerVigencia;
	}
	public boolean getArtGruRepite() {
		return artGruRepite;
	}
	public void setArtGruRepite(boolean artGruRepite) {
		this.artGruRepite = artGruRepite;
	}
	public int getArtGruCupoNoNivel() {
		return artGruCupoNoNivel;
	}
	public void setArtGruCupoNoNivel(int artGruCupoNoNivel) {
		this.artGruCupoNoNivel = artGruCupoNoNivel;
	}
	public int getArtGruCupoGeneral() {
		return artGruCupoGeneral;
	}
	public void setArtGruCupoGeneral(int artGruCupoGeneral) {
		this.artGruCupoGeneral = artGruCupoGeneral;
	}
	public int getArtGruInscGeneral() {
		return artGruInscGeneral;
	}
	public void setArtGruInscGeneral(int artGruInscGeneral) {
		this.artGruInscGeneral = artGruInscGeneral;
	}
	public int getArtGruInscNivel() {
		return artGruInscNivel;
	}
	public void setArtGruInscNivel(int artGruInscNivel) {
		this.artGruInscNivel = artGruInscNivel;
	}
	public int getArtGruInscNoNivel() {
		return artGruInscNoNivel;
	}
	public void setArtGruInscNoNivel(int artGruInscNoNivel) {
		this.artGruInscNoNivel = artGruInscNoNivel;
	}
	public long[] getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(long[] asignaturas) {
		this.asignaturas = asignaturas;
	}
	public int getArtGruCodAsig() {
		return artGruCodAsig;
	}
	public void setArtGruCodAsig(int artGruCodAsig) {
		this.artGruCodAsig = artGruCodAsig;
	}
	public String getArtGruCodAsigNombre() {
		return artGruCodAsigNombre;
	}
	public void setArtGruCodAsigNombre(String artGruCodAsigNombre) {
		this.artGruCodAsigNombre = artGruCodAsigNombre;
	}

}
