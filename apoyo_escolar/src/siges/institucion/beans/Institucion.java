package siges.institucion.beans;

import java.io.*;

public class Institucion implements Serializable, Cloneable{  
  private String inscodigo;
  private String inscoddane;
  private String inscoddaneanterior;
  private String inscoddepto;
  private String inscodlocal;
  private String inscodmun;
  private String insnombre;
  private String inssector;
  private String inszona;
  private String inscalendario;
  private String inscalendariootro;
  private String insestado;

  private String rector;
  private String insrectornombre;
  private String insrectorcc;
  private String insrectoranoposesion;
  private String insrectortel;
  private String insrectorcorreo ;

  private String representante;
  private String insreplegalnombre ;
  private String insreplegalcc ;
  private String insreplegaltel ;
  private String insreplegalcorreo ;

  private String inspaginaweb ;
  private String inspropiedad ;
  private String insnumsedes ;
  private String insgenero ;
  private String inssubsidio ;
  private String insdiscapacidad ;
  private String inscapexcepcional ;
  private String insetnia;

  private String insnucleocodigo;

  private String inshimno;
  private String inslema;
  private String insbandera;
  private String insescudo;
  private String inshistoria; 

  private String insidioma ;
  private String insasocprivada;
  private String insasocprivadaotro;
  private String insregimencostos;
  private String instarifacostos;
  private String insinformepei;
  private String inscodmetodologia;
 

  private String insmetodologia;
  private String insnivellogro;

  private String estado;

/**
 * asigna el campo Inscodigo
 *	@param String s
 */
	public void setInscodigo(String s){
		this.inscodigo=s;
	}	

/**
 * retorna el campo Inscodigo
 *	@return String
 */
	public String getInscodigo(){
		return inscodigo !=null ? inscodigo : "";
	}
/////
/**
 * asigna el campo Inscoddane
 *	@param String s
 */
	public void setInscoddane(String s){
		this.inscoddane=s;
	}	

/**
 * retorna el campo Inscoddane
 *	@return String
 */
	public String getInscoddane(){
		return inscoddane !=null ? inscoddane : "";
	}
/////
/**
 * asigna el campo Inscoddaneanterior
 *	@param String s
 */
	public void setInscoddaneanterior(String s){
		this.inscoddaneanterior=s;
	}	

/**
 * retorna el campo Inscoddaneanterior
 *	@return String
 */
	public String getInscoddaneanterior(){
		return inscoddaneanterior !=null ? inscoddaneanterior : "";
	}
/////
/**
 * asigna el campo Inscoddepto
 *	@param String s
 */
	public void setInscoddepto(String s){
		this.inscoddepto=s;
	}	

/**
 * retorna el campo Inscoddepto
 *	@return String
 */
	public String getInscoddepto(){
		return inscoddepto !=null ? inscoddepto : "";
	}
/////
/**
 * asigna el campo Inscodmun
 *	@param String s
 */
	public void setInscodmun(String s){
		this.inscodmun=s;
	}	

/**
 * retorna el campo Inscodmun
 *	@return String
 */
	public String getInscodmun(){
		return inscodmun !=null ? inscodmun : "";
	}
/////
/**
 * asigna el campo inscodlocal
 *	@param String s
 */
	public void setInscodlocal(String s){
		this.inscodlocal=s;
	}	

/**
 * retorna el campo inscodlocal
 *	@return String
 */
	public String getInscodlocal(){
		return inscodlocal !=null ? inscodlocal : "";
	}
/////

/**
 * asigna el campo Insnombre
 *	@param String s
 */
	public void setInsnombre(String s){
		this.insnombre=s;
	}	

/**
 * retorna el campo Insnombre
 *	@return String
 */
	public String getInsnombre(){
		return insnombre !=null ? insnombre : "";
	}
/////
/**
 * asigna el campo Inssector
 *	@param String s
 */
	public void setInssector(String s){
		this.inssector=s;
	}	

/**
 * retorna el campo Inssector
 *	@return String
 */
	public String getInssector(){
		return inssector !=null ? inssector : "";
	}
/////
/**
 * asigna el campo Inszona
 *	@param String s
 */
	public void setInszona(String s){
		this.inszona=s;
	}	

/**
 * retorna el campo Inszona
 *	@return String
 */
	public String getInszona(){
		return inszona !=null ? inszona : "";
	}
/////
/**
 * asigna el campo Inscalendario
 *	@param String s
 */
	public void setInscalendario(String s){
		this.inscalendario=s;
	}	

/**
 * retorna el campo Inscalendario
 *	@return String
 */
	public String getInscalendario(){
		return inscalendario !=null ? inscalendario : "";
	}
/////
/**
 * asigna el campo Inscalendariootro 
 *	@param String s
 */
	public void setInscalendariootro (String s){
		this.inscalendariootro =s;
	}	

/**
 * retorna el campo Inscalendariootro 
 *	@return String
 */
	public String getInscalendariootro (){
		return inscalendariootro  !=null ? inscalendariootro : "";
	}
/////
/**
 * asigna el campo rector
 *	@param String s
 */
	public void setRector(String s){
		this.rector=s;
	}	

/**
 * retorna el campo rector
 *	@return String
 */
	public String getRector(){
		return rector !=null ? rector : "";
	}
/////
/**
 * asigna el campo Insrectornombre 
 *	@param String s
 */
	public void setInsrectornombre (String s){
		this.insrectornombre =s;
	}	

/**
 * retorna el campo Insrectornombre 
 *	@return String
 */
	public String getInsrectornombre (){
		return insrectornombre  !=null ? insrectornombre  : "";
	}
/////
/**
 * asigna el campo Insrectorcc 
 *	@param String s
 */
	public void setInsrectorcc (String s){
		this.insrectorcc=s;
	}	

/**
 * retorna el campo Insrectorcc 
 *	@return String
 */
	public String getInsrectorcc (){
		return insrectorcc !=null ? insrectorcc : "";
	}
/////
/**
 * asigna el campo Insrectoranoposesion 
 *	@param String s
 */
	public void setInsrectoranoposesion (String s){
		this.insrectoranoposesion=s;
	}	

/**
 * retorna el campo Insrectoranoposesion 
 *	@return String
 */
	public String getInsrectoranoposesion (){
		return insrectoranoposesion !=null ?  insrectoranoposesion: "";
	}
/////
/**
 * asigna el campo Insrectortel 
 *	@param String s
 */
	public void setInsrectortel (String s){
		this.insrectortel =s;
	}	

/**
 * retorna el campo Insrectortel 
 *	@return String
 */
	public String getInsrectortel (){
		return insrectortel  !=null ? insrectortel  : "";
	}
/////
/**
 * asigna el campo Insrectorcorreo 
 *	@param String s
 */
	public void setInsrectorcorreo (String s){
		this.insrectorcorreo =s;
	}	

/**
 * retorna el campo Insrectorcorreo 
 *	@return String
 */
	public String getInsrectorcorreo (){
		return insrectorcorreo  !=null ? insrectorcorreo  : "";
	}
/////
/**
 * asigna el campo representante
 *	@param String s
 */
	public void setRepresentante(String s){
		this.representante=s;
	}	

/**
 * retorna el campo representante
 *	@return String
 */
	public String getRepresentante(){
		return representante !=null ? representante : "";
	}
/////
/**
 * asigna el campo Insreplegalnombre 
 *	@param String s
 */
	public void setInsreplegalnombre (String s){
		this.insreplegalnombre =s;
	}	

/**
 * retorna el campo Insreplegalnombre 
 *	@return String
 */
	public String getInsreplegalnombre (){
		return insreplegalnombre  !=null ? insreplegalnombre  : "";
	}
/////
/**
 * asigna el campo Insreplegalcc 
 *	@param String s
 */
	public void setInsreplegalcc (String s){
		this.insreplegalcc =s;
	}	

/**
 * retorna el campo Insreplegalcc 
 *	@return String
 */
	public String getInsreplegalcc (){
		return insreplegalcc  !=null ? insreplegalcc  : "";
	}
/////
/**
 * asigna el campo Insreplegaltel 
 *	@param String s
 */
	public void setInsreplegaltel (String s){
		this.insreplegaltel =s;
	}	

/**
 * retorna el campo Insreplegaltel 
 *	@return String
 */
	public String getInsreplegaltel (){
		return insreplegaltel  !=null ? insreplegaltel  : "";
	}
/////
/**
 * asigna el campo Insreplegalcorreo 
 *	@param String s
 */
	public void setInsreplegalcorreo (String s){
		this.insreplegalcorreo =s;
	}	

/**
 * retorna el campo Insreplegalcorreo 
 *	@return String
 */
	public String getInsreplegalcorreo (){
		return insreplegalcorreo  !=null ? insreplegalcorreo  : "";
	}
/////
/**
 * asigna el campo Inspaginaweb
 *	@param String s
 */
	public void setInspaginaweb(String s){
		this.inspaginaweb =s;
	}	

/**
 * retorna el campo Inspaginaweb
 *	@return String
 */
	public String getInspaginaweb(){
		return inspaginaweb  !=null ? inspaginaweb  : "";
	}
/////
/**
 * asigna el campo Inspropiedad 
 *	@param String s
 */
	public void setInspropiedad (String s){
		this.inspropiedad =s;
	}	

/**
 * retorna el campo Inspropiedad 
 *	@return String
 */
	public String getInspropiedad (){
		return inspropiedad  !=null ? inspropiedad  : "";
	}
/////
/**
 * asigna el campo Insnumsedes 
 *	@param String s
 */
	public void setInsnumsedes (String s){
		this.insnumsedes =s;
	}	

/**
 * retorna el campo Insnumsedes 
 *	@return String
 */
	public String getInsnumsedes (){
		return insnumsedes  !=null ? insnumsedes  : "";
	}
/////
/**
 * asigna el campo Insgenero 
 *	@param String s
 */
	public void setInsgenero (String s){
		this.insgenero =s;
	}	

/**
 * retorna el campo Insgenero 
 *	@return String
 */
	public String getInsgenero (){
		return insgenero  !=null ? insgenero  : "";
	}
/////
/**
 * asigna el campo Inssubsidio 
 *	@param String s
 */
	public void setInssubsidio (String s){
		this.inssubsidio =s;
	}	

/**
 * retorna el campo Inssubsidio 
 *	@return String
 */
	public String getInssubsidio (){
		return inssubsidio  !=null ? inssubsidio  : "";
	}
/////
/**
 * asigna el campo Insdiscapacidad 
 *	@param String s
 */
	public void setInsdiscapacidad (String s){
		this.insdiscapacidad =s;
	}	

/**
 * retorna el campo Insdiscapacidad 
 *	@return String
 */
	public String getInsdiscapacidad (){
		return insdiscapacidad  !=null ? insdiscapacidad  : "";
	}
/////
/**
 * asigna el campo Inscapexcepcional 
 *	@param String s
 */
	public void setInscapexcepcional (String s){
		this.inscapexcepcional =s;
	}	

/**
 * retorna el campo Inscapexcepcional 
 *	@return String
 */
	public String getInscapexcepcional (){
		return inscapexcepcional  !=null ? inscapexcepcional  : "";
	}
/////
/**
 * asigna el campo Insetnia
 *	@param String s
 */
	public void setInsetnia(String s){
		this.insetnia=s;
	}	

/**
 * retorna el campo Insetnia
 *	@return String
 */
	public String getInsetnia(){
		return insetnia !=null ? insetnia : "";
	}
/////
/**
 * asigna el campo Insnucleocodigo 
 *	@param String s
 */
	public void setInsnucleocodigo (String s){
		this.insnucleocodigo =s;
	}	

/**
 * retorna el campo Insnucleocodigo 
 *	@return String
 */
	public String getInsnucleocodigo (){
		return insnucleocodigo  !=null ? insnucleocodigo  : "";
	}
/////
/**
 * asigna el campo Inshimno 
 *	@param String s
 */
	public void setInshimno (String s){
		this.inshimno=s;
	}	

/**
 * retorna el campo Inshimno 
 *	@return String
 */
	public String getInshimno (){
		return inshimno !=null ? inshimno : "";
	}
/////
/**
 * asigna el campo Inslema 
 *	@param String s
 */
	public void setInslema (String s){
		this.inslema=s;
	}	

/**
 * retorna el campo Inslema 
 *	@return String
 */
	public String getInslema (){
		return inslema !=null ? inslema : "";
	}
/////
/**
 * asigna el campo Insbandera 
 *	@param String s
 */
	public void setInsbandera (String s){
		this.insbandera=s;
	}	

/**
 * retorna el campo Insbandera 
 *	@return String
 */
	public String getInsbandera (){
		return insbandera !=null ? insbandera : "";
	}
/////
/**
 * asigna el campo Insescudo 
 *	@param String s
 */
	public void setInsescudo (String s){
		this.insescudo=s;
	}	

/**
 * retorna el campo Insescudo 
 *	@return String
 */
	public String getInsescudo (){
		return insescudo !=null ? insescudo : "";
	}
/////
/**
 * asigna el campo Inshistoria 
 *	@param String s
 */
	public void setInshistoria (String s){
		this.inshistoria=s;
	}	

/**
 * retorna el campo Inshistoria 
 *	@return String
 */
	public String getInshistoria (){
		return inshistoria !=null ? inshistoria : "";
	}
/////
/**
 * asigna el campo Insidioma 
 *	@param String s
 */
	public void setInsidioma (String s){
		this.insidioma=s;
	}	

/**
 * retorna el campo Insidioma 
 *	@return String
 */
	public String getInsidioma (){
		return insidioma !=null ? insidioma : "";
	}
/////
/**
 * asigna el campo Insasocprivada 
 *	@param String s
 */
	public void setInsasocprivada (String s){
		this.insasocprivada=s;
	}	

/**
 * retorna el campo Insasocprivada 
 *	@return String
 */
	public String getInsasocprivada (){
		return insasocprivada !=null ? insasocprivada : "";
	}
/////
/**
 * asigna el campo Insasocprivadaotro 
 *	@param String s
 */
	public void setInsasocprivadaotro (String s){
		this.insasocprivadaotro=s;
	}	

/**
 * retorna el campo Insasocprivadaotro 
 *	@return String
 */
	public String getInsasocprivadaotro (){
		return insasocprivadaotro !=null ? insasocprivadaotro : "";
	}
/////
/**
 * asigna el campo Insregimencostos 
 *	@param String s
 */
	public void setInsregimencostos (String s){
		this.insregimencostos=s;
	}	

/**
 * retorna el campo Insregimencostos 
 *	@return String
 */
	public String getInsregimencostos (){
		return insregimencostos !=null ? insregimencostos : "";
	}
/////
/**
 * asigna el campo Instarifacostos 
 *	@param String s
 */
	public void setInstarifacostos (String s){
		this.instarifacostos=s;
	}	

/**
 * retorna el campo Instarifacostos 
 *	@return String
 */
	public String getInstarifacostos (){
		return instarifacostos !=null ? instarifacostos : "";
	}
/////
/**
 * asigna el campo Insinformepei 
 *	@param String s
 */
	public void setInsinformepei (String s){
		this.insinformepei=s;
	}	

/**
 * retorna el campo Insinformepei 
 *	@return String
 */
	public String getInsinformepei (){
		return insinformepei !=null ? insinformepei : "";
	}
/////
/**
 * asigna el campo insmetodologia
 *	@param String s
 */
	public void setInsmetodologia(String s){
		this.insmetodologia=s;
	}	

/**
 * retorna el campo insmetodologia
 *	@return String
 */
	public String getInsmetodologia(){
		return insmetodologia !=null ? insmetodologia : "";
	}
/////

/**
 * asigna el campo inscodmetodologia
 *	@param String s
 */
	public void setInscodmetodologia(String s){
		this.inscodmetodologia=s;
	}	

/**
 * retorna el campo inscodmetodologia
 *	@return String
 */
	public String getInscodmetodologia(){
		return inscodmetodologia !=null ? inscodmetodologia : "";
	}
/////


/**
 * asigna el campo insnivellogro
 *	@param String s
 */
	public void setInsnivellogro(String s){
		this.insnivellogro=s;
	}	

/**
 * retorna el campo insnivellogro
 *	@return String
 */
	public String getInsnivellogro(){
		return insnivellogro !=null ? insnivellogro : "";
	}
/////
/**
 * asigna el campo estado
 *	@param String s
 */
	public void setEstado(String s){
		this.estado=s;
	}	

/**
 * retorna el campo estado
 *	@return String
 */
	public String getEstado(){
		return estado !=null ? estado : "";
	}
/////
/**
 *	Hace una copia del objeto mismo
 *	@return Object 
 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
    }	
	/**
	 * @return Devuelve insestado.
	 */
	public String getInsestado() {
	    return insestado!=null?insestado:"";
	}
	/**
	 * @param insestado El insestado a establecer.
	 */
	public void setInsestado(String insestado) {
	    this.insestado = insestado;
	}
}