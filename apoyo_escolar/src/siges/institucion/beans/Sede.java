package siges.institucion.beans;

import java.io.*;

public class Sede implements Serializable, Cloneable{
  private String sedcodins;
  private String sedcodigo;
  private String sedcoddaneanterior;
  private String sedtipo;
  private String sednombre;
  private String sedzona;
  private String sedvereda;
  private String sedbarrio;
  private String sedresguardo;
  private String seddireccion;
  private String sedtelefono;
  private String sedfax;
  private String sedcorreo;
  private String sedagua;
  private String sedluz;
  private String sedtel;
  private String sedalcantarillado;
  private String sedgas;
  private String sedinternet;
  private String sedinternettipo;
  private String sedinternetprov;

  private String[] sedjorcodjor;
  private String[] sedjornivcodnivel;
  private String estado;


/**
 * asigna el campo Sedcodins
 *	@param String s
 */
	public void setSedcodins(String s){
		this.sedcodins=s;
	}	

/**
 * retorna el campo Sedcodins
 *	@return String
 */
	public String getSedcodins(){
		return sedcodins !=null ? sedcodins : "";
	}
/////

/**
 * asigna el campo Sedcodigo
 *	@param String s
 */
	public void setSedcodigo(String s){
		this.sedcodigo=s;
	}	

/**
 * retorna el campo Sedcodigo
 *	@return String
 */
	public String getSedcodigo(){
		return sedcodigo !=null ? sedcodigo : "";
	}
/////
/**
 * asigna el campo Sedcoddaneanterior
 *	@param String s
 */
	public void setSedcoddaneanterior(String s){
		this.sedcoddaneanterior=s;
	}	

/**
 * retorna el campo Sedcoddaneanterior
 *	@return String
 */
	public String getSedcoddaneanterior(){
		return sedcoddaneanterior !=null ? sedcoddaneanterior : "";
	}
/////
/**
 * asigna el campo Sedtipo
 *	@param String s
 */
	public void setSedtipo(String s){
		this.sedtipo=s;
	}	

/**
 * retorna el campo Sedtipo
 *	@return String
 */
	public String getSedtipo(){
		return sedtipo !=null ? sedtipo : "";
	}
/////
/**
 * asigna el campo Sednombre
 *	@param String s
 */
	public void setSednombre(String s){
		this.sednombre=s;
	}	

/**
 * retorna el campo Sednombre
 *	@return String
 */
	public String getSednombre(){
		return sednombre !=null ? sednombre : "";
	}
/////
/**
 * asigna el campo Sedzona
 *	@param String s
 */
	public void setSedzona(String s){
		this.sedzona=s;
	}	

/**
 * retorna el campo Sedzona
 *	@return String
 */
	public String getSedzona(){
		return sedzona !=null ? sedzona : "";
	}
/////
/**
 * asigna el campo Sedvereda
 *	@param String s
 */
	public void setSedvereda(String s){
		this.sedvereda=s;
	}	

/**
 * retorna el campo Sedvereda
 *	@return String
 */
	public String getSedvereda(){
		return sedvereda !=null ? sedvereda : "";
	}
/////
/**
 * asigna el campo Sedbarrio
 *	@param String s
 */
	public void setSedbarrio(String s){
		this.sedbarrio=s;
	}	

/**
 * retorna el campo Sedbarrio
 *	@return String
 */
	public String getSedbarrio(){
		return sedbarrio !=null ? sedbarrio : "";
	}
/////
/**
 * asigna el campo Sedresguardo
 *	@param String s
 */
	public void setSedresguardo(String s){
		this.sedresguardo=s;
	}	

/**
 * retorna el campo Sedresguardo
 *	@return String
 */
	public String getSedresguardo(){
		return sedresguardo !=null ? sedresguardo : "";
	}
/////
/**
 * asigna el campo Seddireccion
 *	@param String s
 */
	public void setSeddireccion(String s){
		this.seddireccion=s;
	}	

/**
 * retorna el campo Seddireccion
 *	@return String
 */
	public String getSeddireccion(){
		return seddireccion !=null ? seddireccion : "";
	}
/////
/**
 * asigna el campo Sedtelefono
 *	@param String s
 */
	public void setSedtelefono(String s){
		this.sedtelefono=s;
	}	

/**
 * retorna el campo Sedtelefono
 *	@return String
 */
	public String getSedtelefono(){
		return sedtelefono !=null ? sedtelefono : "";
	}
/////
/**
 * asigna el campo Sedfax
 *	@param String s
 */
	public void setSedfax(String s){
		this.sedfax=s;
	}	

/**
 * retorna el campo Sedfax
 *	@return String
 */
	public String getSedfax(){
		return sedfax !=null ? sedfax : "";
	}
/////
/**
 * asigna el campo Sedcorreo
 *	@param String s
 */
	public void setSedcorreo(String s){
		this.sedcorreo=s;
	}	

/**
 * retorna el campo Sedcorreo
 *	@return String
 */
	public String getSedcorreo(){
		return sedcorreo !=null ? sedcorreo : "";
	}
/////

/**
 * asigna el campo sedagua
 *	@param String s
 */
	public void setSedagua(String s){
		this.sedagua=s;
	}	
/**
 * retorna el campo sedagua
 *	@return String
 */
	public String getSedagua(){
		return sedagua !=null ? sedagua: "";
	}
/////

/**
 * asigna el campo sedluz
 *	@param String s
 */
	public void setSedluz(String s){
		this.sedluz=s;
	}	
/**
 * retorna el campo sedluz
 *	@return String
 */
	public String getSedluz(){
		return sedluz !=null ? sedluz: "";
	}
/////
/**
 * asigna el campo sedtel
 *	@param String s
 */
	public void setSedtel(String s){
		this.sedtel=s;
	}	
/**
 * retorna el campo sedtel
 *	@return String
 */
	public String getSedtel(){
		return sedtel !=null ? sedtel: "";
	}
/////
/**
 * asigna el campo sedalcantarillado
 *	@param String s
 */
	public void setSedalcantarillado(String s){
		this.sedalcantarillado=s;
	}	
/**
 * retorna el campo sedalcantarillado
 *	@return String
 */
	public String getSedalcantarillado(){
		return sedalcantarillado !=null ?sedalcantarillado : "";
	}
/////
/**
 * asigna el campo sedgas
 *	@param String s
 */
	public void setSedgas(String s){
		this.sedgas=s;
	}	
/**
 * retorna el campo sedgas
 *	@return String
 */
	public String getSedgas(){
		return sedgas !=null ? sedgas: "";
	}
/////
/**
 * asigna el campo sedinternet
 *	@param String s
 */
	public void setSedinternet(String s){
		this.sedinternet=s;
	}	
/**
 * retorna el campo sedinternet
 *	@return String
 */
	public String getSedinternet(){
		return sedinternet !=null ? sedinternet: "";
	}
/////
/**
 * asigna el campo sedinternettipo
 *	@param String s
 */
	public void setSedinternettipo(String s){
		this.sedinternettipo=s;
	}	
/**
 * retorna el campo sedinternettipo
 *	@return String
 */
	public String getSedinternettipo(){
		return  sedinternettipo!=null ? sedinternettipo: "";
	}
/////
/**
 * asigna el campo sedinternetprov
 *	@param String s
 */
	public void setSedinternetprov(String s){
		this.sedinternetprov=s;
	}	
/**
 * retorna el campo sedinternetprov
 *	@return String
 */
	public String getSedinternetprov(){
		return sedinternetprov !=null ?sedinternetprov : "";
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
 * asigna el campo sedjorcodjor
 *	@param String s
 */
	public void setSedjorcodjor(String[] s){
		this.sedjorcodjor=s;
	}	

/**
 * retorna el campo sedjorcodjor
 *	@return String
 */
	public String[] getSedjorcodjor(){
		return sedjorcodjor;
	}
/////
/**
 * asigna el campo sedjornivcodnivel
 *	@param String s
 */
	public void setSedjornivcodnivel(String[] s){
		this.sedjornivcodnivel=s;
	}	

/**
 * retorna el campo sedjorespcodjor
 *	@return String
 */
	public String[] getSedjornivcodnivel(){
		return sedjornivcodnivel;
	}
/////
public String seleccion(String s){
	if(sedjorcodjor!=null){
		for(int i=0;i<sedjorcodjor.length;++i){
			if(sedjorcodjor[i].equals(s))
				return "CHECKED";
		}
	}
	return "";
}

/**
 *	@param String nivel del segundo check
 *	@param String jornada invisible
 */
public String seleccion2(String s){
	if(sedjornivcodnivel!=null){
		for(int i=0;i<sedjornivcodnivel.length;i++){
			if(sedjornivcodnivel[i].equals(s)){
				return "CHECKED";
			}
		}
	}
	return "";
}


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
}

