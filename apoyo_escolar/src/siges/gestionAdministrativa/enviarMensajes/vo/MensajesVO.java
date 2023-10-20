package siges.gestionAdministrativa.enviarMensajes.vo;

import siges.common.vo.Vo;

public class MensajesVO extends Vo {
	public static final int EMAIL_EN_COLAR = -1;
	public static final int EMAIL_ENVIANDO = 0;
	public static final int EMAIL_ENVIADO = 1;
	public static final int EMAIL_ERROR = 2;
	public static final int EMAIL_MODIFICADO = 3;
	public static final int EMAIL_MODIFICADO_X_SISTEMA = 4;
	
	
	public static final String MSJ_RAYITA = " - ";
	public static final String MSJ_ABRE_PARENTESIS ="(";
	public static final String MSJ_CIERRE_PARENTESIS =").";
	
	public static final String MSJ_ASUNTO = "Cierre automntico del periodo ";
	
	public static final String MSJ_CONTENIDO_01 = "Se informa a los interesados que el periodo ";
	public static final String MSJ_CONTENIDO_02 = " del colegio ";
	
	public static final String MSJ_CONTENIDO_1_03 = " sern cerrado de forma automntico el dna ";
	public static final String MSJ_CONTENIDO_1_04 = ". Tenga en cuenta que falta(n) ";
	public static final String MSJ_CONTENIDO_1_05 = " dia(s) para realizar este proceso.";
	
	public static final String MSJ_CONTENIDO_2_03 = " sern cerrado de forma automntico el dna de hoy ";
	public static final String MSJ_CONTENIDO_2_04 = " Tenga en cuenta que falta(n) menos de ";
	public static final String MSJ_CONTENIDO_2_05 = " hora(s) para realizar este proceso.";
	
	
	
	
	
	
	private int msjcodigo; 
	private String msjasunto; 
	private String msjfecha; 
	private String msjfechaini; 
	private String msjfechafin; 
	private String msjcontenido; 
	private int msjenviadopor; 
	private String msjenviadoaperfil; 
	private String msjenviadoalocal; 
	private String msjenviadoacoleg; 
	private String msjenviadoasede; 
	private String msjenviadoajorn; 
	private String msjusuario;
	private int msjestado;
	
	public static String formatoInst(String codInst){
		String[] array = codInst.split("\\,");
		String resul ="";
		
		if(codInst == null){
			System.out.println("codInst es null");
			return null;
		}
		
		for(int i= 0;i< array.length;i++ ){
			String array2[] = ((String)array[i]).split("\\|"); 
			resul += array2[array2.length-1] + ",";
		}
		if(resul.length()> 0){
			resul = resul.substring(0, resul.length()-1);
		}
		return resul;
	}
	
	
	public static String formatoSede(String codSede){
		String[] array = codSede.split("\\,");
		String resul ="";
		
		if(codSede == null){
			System.out.println("codSede es null");
			return null;
		}
		
		for(int i= 0;i< array.length;i++ ){
			String array2[] = ((String)array[i]).split("\\|");
			resul += array2[1] + ",";
		}
		if(resul.length()> 0){
			resul = resul.substring(0, resul.length()-1);
		}
		return resul;
	}
	
	
	public static String[] formatoSedeArray(String codSede){
		if(codSede == null){
			System.out.println("formatoSedeArray codSede es null");
			return null;
		}
		String[] array = codSede.split("\\,");
		return array;
	}
	
	public String getMsjasunto() {
		return msjasunto;
	}
	public void setMsjasunto(String msjasunto) {
		this.msjasunto = msjasunto;
	}
	public int getMsjcodigo() {
		return msjcodigo;
	}
	public void setMsjcodigo(int msjcodigo) {
		this.msjcodigo = msjcodigo;
	}
	public String getMsjcontenido() {
		return msjcontenido;
	}
	public void setMsjcontenido(String msjcontenido) {
		this.msjcontenido = msjcontenido;
	}
	public String getMsjenviadoacoleg() {
		return msjenviadoacoleg;
	}
	public void setMsjenviadoacoleg(String msjenviadoacoleg) {
		this.msjenviadoacoleg = msjenviadoacoleg;
	}
	public String getMsjenviadoajorn() {
		return msjenviadoajorn;
	}
	public void setMsjenviadoajorn(String msjenviadoajorn) {
		this.msjenviadoajorn = msjenviadoajorn;
	}
	public String getMsjenviadoalocal() {
		return msjenviadoalocal;
	}
	public void setMsjenviadoalocal(String msjenviadoalocal) {
		this.msjenviadoalocal = msjenviadoalocal;
	}
	public String getMsjenviadoaperfil() {
		return msjenviadoaperfil;
	}
	public void setMsjenviadoaperfil(String msjenviadoaperfil) {
		this.msjenviadoaperfil = msjenviadoaperfil;
	}
	public String getMsjenviadoasede() {
		return msjenviadoasede;
	}
	public void setMsjenviadoasede(String msjenviadoasede) {
		this.msjenviadoasede = msjenviadoasede;
	}
	public int getMsjenviadopor() {
		return msjenviadopor;
	}
	public void setMsjenviadopor(int msjenviadopor) {
		this.msjenviadopor = msjenviadopor;
	}
	public String getMsjfecha() {
		return msjfecha;
	}
	public void setMsjfecha(String msjfecha) {
		this.msjfecha = msjfecha;
	}
	public String getMsjfechafin() {
		return msjfechafin;
	}
	public void setMsjfechafin(String msjfechafin) {
		this.msjfechafin = msjfechafin;
	}
	public String getMsjfechaini() {
		return msjfechaini;
	}
	public void setMsjfechaini(String msjfechaini) {
		this.msjfechaini = msjfechaini;
	}
	public String getMsjusuario() {
		return msjusuario;
	}
	public void setMsjusuario(String msjusuario) {
		this.msjusuario = msjusuario;
	}
	
	
	public int getMsjestado() {
		return msjestado;
	}
	
	
	public void setMsjestado(int msjestado) {
		this.msjestado = msjestado;
	}
	
	
	public boolean equals(MensajesVO msjNew) throws Exception{
		if(msjNew == null  ){
			return false;
		}
		
		if( ! this.msjasunto.equals(msjNew.getMsjasunto())){
			return false;
		}
		
		if( ! this.msjcontenido.equals(msjNew.getMsjcontenido())){
			return false;
		}
			
			return true;
		}
	
}
