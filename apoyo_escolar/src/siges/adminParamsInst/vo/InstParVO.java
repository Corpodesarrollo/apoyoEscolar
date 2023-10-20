package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class InstParVO  extends Vo{
  
	private long insparvigencia; 
	private long insparcodinst; 
	private long insparnumper;
	private long insparnumperAntes;
	private long inspartipper; 
	private long insparniveval;
	private long insparnivevalAntes;
	private double porcentajeperdida;
	
	private String insparnumperNom; 
	private String inspartipperNom; 
	private String insparcodcombNom;
	
	private String msgValidarNivelEval;
	private String msgRetricionNivelEval;
	
	
	private String insparnomperdef;
	//se reutiliza como evaluacinn logros
	private String insparsubtitbol;
	private String insparevaldescriptores;	
	private int eliminarCascada;
	
	
	
	public long getInsparcodinst() {
		return insparcodinst;
	}
	public void setInsparcodinst(long insparcodinst) {
		this.insparcodinst = insparcodinst;
	}
 
	public long getInsparnumper() {
		return insparnumper;
	}
	public void setInsparnumper(long insparnumper) {
		this.insparnumper = insparnumper;
	}
	public long getInspartipper() {
		return inspartipper;
	}
	public void setInspartipper(long inspartipper) {
		this.inspartipper = inspartipper;
	}
	public long getInsparvigencia() {
		return insparvigencia;
	}
	public void setInsparvigencia(long insparvigencia) {
		this.insparvigencia = insparvigencia;
	}
	public String getInsparnomperdef() {
		return insparnomperdef;
	}
	public void setInsparnomperdef(String insparnomperdef) {
		this.insparnomperdef = insparnomperdef;
	}
 
	public String getInsparcodcombNom() {
		return insparcodcombNom;
	}
	public void setInsparcodcombNom(String insparcodcombNom) {
		this.insparcodcombNom = insparcodcombNom;
	}
	public String getInsparnumperNom() {
		return insparnumperNom;
	}
	public void setInsparnumperNom(String insparnumperNom) {
		this.insparnumperNom = insparnumperNom;
	}
	public String getInspartipperNom() {
		return inspartipperNom;
	}
	public void setInspartipperNom(String inspartipperNom) {
		this.inspartipperNom = inspartipperNom;
	}
	public long getInsparniveval() {
		return insparniveval;
	}
	public void setInsparniveval(long insparniveval) {
		this.insparniveval = insparniveval;
	}
	public String getMsgRetricionNivelEval() {
		return msgRetricionNivelEval;
	}
	public void setMsgRetricionNivelEval(String msgRetricionNivelEval) {
		this.msgRetricionNivelEval = msgRetricionNivelEval;
	}
	public String getMsgValidarNivelEval() {
		return msgValidarNivelEval;
	}
	public void setMsgValidarNivelEval(String msgValidarNivelEval) {
		this.msgValidarNivelEval = msgValidarNivelEval;
	}
	public long getInsparnivevalAntes() {
		return insparnivevalAntes;
	}
	public void setInsparnivevalAntes(long insparnivevalAntes) {
		this.insparnivevalAntes = insparnivevalAntes;
	}
	public int getEliminarCascada() {
		return eliminarCascada;
	}
	public void setEliminarCascada(int eliminarCascada) {
		this.eliminarCascada = eliminarCascada;
	}
	public long getInsparnumperAntes() {
		return insparnumperAntes;
	}
	public void setInsparnumperAntes(long insparnumperAntes) {
		this.insparnumperAntes = insparnumperAntes;
	}
	public String getInsparsubtitbol() {
		return insparsubtitbol;
	}
	public void setInsparsubtitbol(String insparsubtitbol) {
		this.insparsubtitbol = insparsubtitbol;
	}
	public double getPorcentajeperdida() {
		return porcentajeperdida;
	}
	public void setPorcentajeperdida(double porcentajeperdida) {
		this.porcentajeperdida = porcentajeperdida;
	}
	/**
	 * @return the insparevaldescriptores
	 */
	public String getInsparevaldescriptores() {
		return insparevaldescriptores;
	}
	/**
	 * @param insparevaldescriptores the insparevaldescriptores to set
	 */
	public void setInsparevaldescriptores(String insparevaldescriptores) {
		this.insparevaldescriptores = insparevaldescriptores;
	}
	
	
	
 
 }
