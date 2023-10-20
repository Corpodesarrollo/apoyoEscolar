package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de datos del modulo
 * 26/01/2010 
 * @author Athenea TA
 * @version 1.1
 */
public class NivelEvaluacionVO  extends Vo{
	   private long  g_nivevacodigo; 
	   private long  g_nivevasede; 
	   private long  g_nivevajorn; 
	   private long  g_nivevametod; 
	   private long  g_nivevanivel; 
	   private long  g_nivevagrado; 
	   private String  g_nivevanombre; 
	   private long  g_nivevaorden;
	   
	   
	public long getG_nivevacodigo() {
		return g_nivevacodigo;
	}
	public void setG_nivevacodigo(long g_nivevacodigo) {
		this.g_nivevacodigo = g_nivevacodigo;
	}
	public long getG_nivevagrado() {
		return g_nivevagrado;
	}
	public void setG_nivevagrado(long g_nivevagrado) {
		this.g_nivevagrado = g_nivevagrado;
	}
	public long getG_nivevajorn() {
		return g_nivevajorn;
	}
	public void setG_nivevajorn(long g_nivevajorn) {
		this.g_nivevajorn = g_nivevajorn;
	}
	public long getG_nivevametod() {
		return g_nivevametod;
	}
	public void setG_nivevametod(long g_nivevametod) {
		this.g_nivevametod = g_nivevametod;
	}
	public long getG_nivevanivel() {
		return g_nivevanivel;
	}
	public void setG_nivevanivel(long g_nivevanivel) {
		this.g_nivevanivel = g_nivevanivel;
	}
	public String getG_nivevanombre() {
		return g_nivevanombre;
	}
	public void setG_nivevanombre(String g_nivevanombre) {
		this.g_nivevanombre = g_nivevanombre;
	}
	public long getG_nivevaorden() {
		return g_nivevaorden;
	}
	public void setG_nivevaorden(long g_nivevaorden) {
		this.g_nivevaorden = g_nivevaorden;
	}
	public long getG_nivevasede() {
		return g_nivevasede;
	}
	public void setG_nivevasede(long g_nivevasede) {
		this.g_nivevasede = g_nivevasede;
	}
 
 }
