package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class EscalaConceptualVO  extends Vo{
	
	private long insconvigencia; 
	private long insconcodinst; 
	private long insconniveval; 
	private long insconcodsede = -99;
	private long insconcodjorn = -99; 
	private long insconcodmetod = -99; 
	private long insconcodnivel = -99;
	private long insconcodgrado = -99; 
	private long insconcodigo; 
	private String insconliteral; 
	private String insconnombre; 
	private String insconliteralAntes; 
	private String insconnombreAntes;
	
	private String msgValidarLiteral;
	private String msgRetricionEliminar;
	
	
	private String inscondescripcion; 
	private long insconorden; 
	private double insconvalnum; 
	private long insconequmen;
	private String insconequmenNom;
	private boolean edicion;
	private int yaFueUtilizado = 0;
	
	
	public long getInsconcodgrado() {
		return insconcodgrado;
	}
	public void setInsconcodgrado(long insconcodgrado) {
		this.insconcodgrado = insconcodgrado;
	}
	public long getInsconcodigo() {
		return insconcodigo;
	}
	public void setInsconcodigo(long insconcodigo) {
		this.insconcodigo = insconcodigo;
	}
	public long getInsconcodinst() {
		return insconcodinst;
	}
	public void setInsconcodinst(long insconcodinst) {
		this.insconcodinst = insconcodinst;
	}
	public long getInsconcodjorn() {
		return insconcodjorn;
	}
	public void setInsconcodjorn(long insconcodjorn) {
		this.insconcodjorn = insconcodjorn;
	}
	public long getInsconcodmetod() {
		return insconcodmetod;
	}
	public void setInsconcodmetod(long insconcodmetod) {
		this.insconcodmetod = insconcodmetod;
	}
	public long getInsconcodnivel() {
		return insconcodnivel;
	}
	public void setInsconcodnivel(long insconcodnivel) {
		this.insconcodnivel = insconcodnivel;
	}
	public long getInsconcodsede() {
		return insconcodsede;
	}
	public void setInsconcodsede(long insconcodsede) {
		this.insconcodsede = insconcodsede;
	}
	public String getInscondescripcion() {
		return inscondescripcion;
	}
	public void setInscondescripcion(String inscondescripcion) {
		this.inscondescripcion = inscondescripcion;
	}
 
	public String getInsconliteral() {
		return insconliteral;
	}
	public void setInsconliteral(String insconliteral_) {
		this.insconliteral = insconliteral_==null?insconliteral_:insconliteral_.toUpperCase();
	}
	public long getInsconniveval() {
		return insconniveval;
	}
	public void setInsconniveval(long insconniveval) {
		this.insconniveval = insconniveval;
	}
	public String getInsconnombre() {
		return insconnombre;
	}
	public void setInsconnombre(String insconnombre) {
		this.insconnombre = insconnombre==null?insconnombre:insconnombre.toUpperCase();
	}
	public long getInsconorden() {
		return insconorden;
	}
	public void setInsconorden(long insconorden) {
		this.insconorden = insconorden;
	}
 
 
	public long getInsconvigencia() {
		return insconvigencia;
	}
	public void setInsconvigencia(long insconvigencia) {
		this.insconvigencia = insconvigencia;
	}
	public double getInsconvalnum() {
		return insconvalnum;
	}
	public void setInsconvalnum(double insconvalnum) {
		this.insconvalnum = insconvalnum;
	}
	public boolean isEdicion() {
		return edicion;
	}
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}
	public long getInsconequmen() {
		return insconequmen;
	}
	public void setInsconequmen(long insconequmen) {
		this.insconequmen = insconequmen;
	}
	public String getInsconequmenNom() {
		return insconequmenNom;
	}
	public void setInsconequmenNom(String insconequmenNom) {
		this.insconequmenNom = insconequmenNom;
	}
	public int getYaFueUtilizado() {
		return yaFueUtilizado;
	}
	public void setYaFueUtilizado(int yaFueUtilizado) {
		this.yaFueUtilizado = yaFueUtilizado;
	}
	public String getInsconliteralAntes() {
		return insconliteralAntes;
	}
	public void setInsconliteralAntes(String insconliteralAntes) {
		this.insconliteralAntes = insconliteralAntes;
	}
	public String getInsconnombreAntes() {
		return insconnombreAntes;
	}
	public void setInsconnombreAntes(String insconnombreAntes) {
		this.insconnombreAntes = insconnombreAntes;
	}
	public String getMsgRetricionEliminar() {
		return msgRetricionEliminar;
	}
	public void setMsgRetricionEliminar(String msgRetricionEliminar) {
		this.msgRetricionEliminar = msgRetricionEliminar;
	}
	public String getMsgValidarLiteral() {
		return msgValidarLiteral;
	}
	public void setMsgValidarLiteral(String msgValidarLiteral) {
		this.msgValidarLiteral = msgValidarLiteral;
	}
 
	
	
}
