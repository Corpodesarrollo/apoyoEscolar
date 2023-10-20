package siges.adminParamsInst.vo;

import siges.common.vo.Vo;

public class PeriodoPrgfechasVO extends Vo {
	   public final static int DIA_HORA = 23;
	   public final static int ALERTA_ACTIVA = 1;
	   public final static int ALERTA_NONE = 0;
	
	   private int prfcodinst; 
	   private int prfvigencia; 
	   private String prf_ini_per1; 
	   private String prf_fin_per1; 
	   private int prf_cierrep1; 
	   private int prf_alertap1; 
	   private int prf_diasp1; 
	   private String prf_ini_per2; 
	   private String prf_fin_per2; 
	   private int prf_cierrep2; 
	   private int prf_alertap2; 
	   private int prf_diasp2; 
	   private String prf_ini_per3; 
	   private String prf_fin_per3; 
	   private int prf_cierrep3; 
	   private int prf_alertap3; 
	   private int prf_diasp3; 
	   private String prf_ini_per4; 
	   private String prf_fin_per4; 
	   private int prf_cierrep4; 
	   private int prf_alertap4; 
	   private int prf_diasp4; 
	   private String prf_ini_per5; 
	   private String prf_fin_per5; 
	   private int prf_cierrep5; 
	   private int prf_alertap5; 
	   private int prf_diasp5; 
	   private String prf_ini_per6; 
	   private String prf_fin_per6; 
	   private int prf_cierrep6; 
	   private int prf_alertap6; 
	   private int prf_diasp6; 
	   private String prf_ini_per7; 
	   private String prf_fin_per7; 
	   private int prf_cierrep7; 
	   private int prf_alertap7; 
	   private int prf_diasp7; 
	   private long prf_usuario; 
	   private String prf_fecha;
	   
	   
	   private String prf_ini_perCerrar; 
	   private String prf_fin_perCerrar; 
	   private int prf_cierrepCerrar;
	   private int prf_periodo;
	   private int prf_alertapCerrar; 
	   private int prf_diaspCerrar; 
	   
	   // ESTADO
	   
	   private int perestado1;  
	   private int perestado2; 
	   private int perestado3;  
	   private int perestado4; 
	   private int perestado5;  
	   private int perestado6;  
	   private int perestado7;

	   
	   
	public int getPrf_alertap1() {
		return prf_alertap1;
	}
	public void setPrf_alertap1(int prf_alertap1) {
		this.prf_alertap1 = prf_alertap1;
	}
	public int getPrf_alertap2() {
		return prf_alertap2;
	}
	public void setPrf_alertap2(int prf_alertap2) {
		this.prf_alertap2 = prf_alertap2;
	}
	public int getPrf_alertap3() {
		return prf_alertap3;
	}
	public void setPrf_alertap3(int prf_alertap3) {
		this.prf_alertap3 = prf_alertap3;
	}
	public int getPrf_alertap4() {
		return prf_alertap4;
	}
	public void setPrf_alertap4(int prf_alertap4) {
		this.prf_alertap4 = prf_alertap4;
	}
	public int getPrf_alertap5() {
		return prf_alertap5;
	}
	public void setPrf_alertap5(int prf_alertap5) {
		this.prf_alertap5 = prf_alertap5;
	}
	public int getPrf_alertap6() {
		return prf_alertap6;
	}
	public void setPrf_alertap6(int prf_alertap6) {
		this.prf_alertap6 = prf_alertap6;
	}
	public int getPrf_alertap7() {
		return prf_alertap7;
	}
	public void setPrf_alertap7(int prf_alertap7) {
		this.prf_alertap7 = prf_alertap7;
	}
	public int getPrf_cierrep1() {
		return prf_cierrep1;
	}
	public void setPrf_cierrep1(int prf_cierrep1) {
		this.prf_cierrep1 = prf_cierrep1;
	}
	public int getPrf_cierrep2() {
		return prf_cierrep2;
	}
	public void setPrf_cierrep2(int prf_cierrep2) {
		this.prf_cierrep2 = prf_cierrep2;
	}
	public int getPrf_cierrep3() {
		return prf_cierrep3;
	}
	public void setPrf_cierrep3(int prf_cierrep3) {
		this.prf_cierrep3 = prf_cierrep3;
	}
	public int getPrf_cierrep4() {
		return prf_cierrep4;
	}
	public void setPrf_cierrep4(int prf_cierrep4) {
		this.prf_cierrep4 = prf_cierrep4;
	}
	public int getPrf_cierrep5() {
		return prf_cierrep5;
	}
	public void setPrf_cierrep5(int prf_cierrep5) {
		this.prf_cierrep5 = prf_cierrep5;
	}
	public int getPrf_cierrep6() {
		return prf_cierrep6;
	}
	public void setPrf_cierrep6(int prf_cierrep6) {
		this.prf_cierrep6 = prf_cierrep6;
	}
	public int getPrf_cierrep7() {
		return prf_cierrep7;
	}
	public void setPrf_cierrep7(int prf_cierrep7) {
		this.prf_cierrep7 = prf_cierrep7;
	}
	public int getPrf_diasp1() {
		return prf_diasp1;
	}
	public void setPrf_diasp1(int prf_diasp1) {
		this.prf_diasp1 = prf_diasp1;
	}
	public int getPrf_diasp2() {
		return prf_diasp2;
	}
	public void setPrf_diasp2(int prf_diasp2) {
		this.prf_diasp2 = prf_diasp2;
	}
	public int getPrf_diasp3() {
		return prf_diasp3;
	}
	public void setPrf_diasp3(int prf_diasp3) {
		this.prf_diasp3 = prf_diasp3;
	}
	public int getPrf_diasp4() {
		return prf_diasp4;
	}
	public void setPrf_diasp4(int prf_diasp4) {
		this.prf_diasp4 = prf_diasp4;
	}
	public int getPrf_diasp5() {
		return prf_diasp5;
	}
	public void setPrf_diasp5(int prf_diasp5) {
		this.prf_diasp5 = prf_diasp5;
	}
	public int getPrf_diasp6() {
		return prf_diasp6;
	}
	public void setPrf_diasp6(int prf_diasp6) {
		this.prf_diasp6 = prf_diasp6;
	}
	public int getPrf_diasp7() {
		return prf_diasp7;
	}
	public void setPrf_diasp7(int prf_diasp7) {
		this.prf_diasp7 = prf_diasp7;
	}
	public String getPrf_fecha() {
		return prf_fecha;
	}
	public void setPrf_fecha(String prf_fecha) {
		this.prf_fecha = prf_fecha;
	}
	public String getPrf_fin_per1() {
		return prf_fin_per1;
	}
	public void setPrf_fin_per1(String prf_fin_per1) {
		this.prf_fin_per1 = prf_fin_per1;
	}
	public String getPrf_fin_per2() {
		return prf_fin_per2;
	}
	public void setPrf_fin_per2(String prf_fin_per2) {
		this.prf_fin_per2 = prf_fin_per2;
	}
	public String getPrf_fin_per3() {
		return prf_fin_per3;
	}
	public void setPrf_fin_per3(String prf_fin_per3) {
		this.prf_fin_per3 = prf_fin_per3;
	}
	public String getPrf_fin_per4() {
		return prf_fin_per4;
	}
	public void setPrf_fin_per4(String prf_fin_per4) {
		this.prf_fin_per4 = prf_fin_per4;
	}
	public String getPrf_fin_per5() {
		return prf_fin_per5;
	}
	public void setPrf_fin_per5(String prf_fin_per5) {
		this.prf_fin_per5 = prf_fin_per5;
	}
	public String getPrf_fin_per6() {
		return prf_fin_per6;
	}
	public void setPrf_fin_per6(String prf_fin_per6) {
		this.prf_fin_per6 = prf_fin_per6;
	}
	public String getPrf_fin_per7() {
		return prf_fin_per7;
	}
	public void setPrf_fin_per7(String prf_fin_per7) {
		this.prf_fin_per7 = prf_fin_per7;
	}
	public String getPrf_ini_per1() {
		return prf_ini_per1;
	}
	public void setPrf_ini_per1(String prf_ini_per1) {
		this.prf_ini_per1 = prf_ini_per1;
	}
	public String getPrf_ini_per2() {
		return prf_ini_per2;
	}
	public void setPrf_ini_per2(String prf_ini_per2) {
		this.prf_ini_per2 = prf_ini_per2;
	}
	public String getPrf_ini_per3() {
		return prf_ini_per3;
	}
	public void setPrf_ini_per3(String prf_ini_per3) {
		this.prf_ini_per3 = prf_ini_per3;
	}
	public String getPrf_ini_per4() {
		return prf_ini_per4;
	}
	public void setPrf_ini_per4(String prf_ini_per4) {
		this.prf_ini_per4 = prf_ini_per4;
	}
	public String getPrf_ini_per5() {
		return prf_ini_per5;
	}
	public void setPrf_ini_per5(String prf_ini_per5) {
		this.prf_ini_per5 = prf_ini_per5;
	}
	public String getPrf_ini_per6() {
		return prf_ini_per6;
	}
	public void setPrf_ini_per6(String prf_ini_per6) {
		this.prf_ini_per6 = prf_ini_per6;
	}
	public String getPrf_ini_per7() {
		return prf_ini_per7;
	}
	public void setPrf_ini_per7(String prf_ini_per7) {
		this.prf_ini_per7 = prf_ini_per7;
	}
	 
 
	public long getPrf_usuario() {
		return prf_usuario;
	}
	public void setPrf_usuario(long prf_usuario) {
		this.prf_usuario = prf_usuario;
	}
	public int getPrfcodinst() {
		return prfcodinst;
	}
	public void setPrfcodinst(int prfcodinst) {
		this.prfcodinst = prfcodinst;
	}
	public int getPrfvigencia() {
		return prfvigencia;
	}
	public void setPrfvigencia(int prfvigencia) {
		this.prfvigencia = prfvigencia;
	}
	public int getPerestado1() {
		return perestado1;
	}
	public void setPerestado1(int perestado1) {
		this.perestado1 = perestado1;
	}
	public int getPerestado2() {
		return perestado2;
	}
	public void setPerestado2(int perestado2) {
		this.perestado2 = perestado2;
	}
	public int getPerestado3() {
		return perestado3;
	}
	public void setPerestado3(int perestado3) {
		this.perestado3 = perestado3;
	}
	public int getPerestado4() {
		return perestado4;
	}
	public void setPerestado4(int perestado4) {
		this.perestado4 = perestado4;
	}
	public int getPerestado5() {
		return perestado5;
	}
	public void setPerestado5(int perestado5) {
		this.perestado5 = perestado5;
	}
	public int getPerestado6() {
		return perestado6;
	}
	public void setPerestado6(int perestado6) {
		this.perestado6 = perestado6;
	}
	public int getPerestado7() {
		return perestado7;
	}
	public void setPerestado7(int perestado7) {
		this.perestado7 = perestado7;
	}
	public int getPrf_alertapCerrar() {
		return prf_alertapCerrar;
	}
	public void setPrf_alertapCerrar(int prf_alertapCerrar) {
		this.prf_alertapCerrar = prf_alertapCerrar;
	}
	public int getPrf_cierrepCerrar() {
		return prf_cierrepCerrar;
	}
	public void setPrf_cierrepCerrar(int prf_cierrepCerrar) {
		this.prf_cierrepCerrar = prf_cierrepCerrar;
	}
	public int getPrf_diaspCerrar() {
		return prf_diaspCerrar;
	}
	public void setPrf_diaspCerrar(int prf_diaspCerrar) {
		this.prf_diaspCerrar = prf_diaspCerrar;
	}
	public String getPrf_fin_perCerrar() {
		return prf_fin_perCerrar;
	}
	public void setPrf_fin_perCerrar(String prf_fin_perCerrar) {
		this.prf_fin_perCerrar = prf_fin_perCerrar;
	}
	public String getPrf_ini_perCerrar() {
		return prf_ini_perCerrar;
	}
	public void setPrf_ini_perCerrar(String prf_ini_perCerrar) {
		this.prf_ini_perCerrar = prf_ini_perCerrar;
	}
	public int getPrf_periodo() {
		return prf_periodo;
	}
	public void setPrf_periodo(int prf_periodo) {
		this.prf_periodo = prf_periodo;
	}


}
