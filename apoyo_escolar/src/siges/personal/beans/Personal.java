package siges.personal.beans;

import java.io.*;

public class Personal implements Serializable, Cloneable{
	
	public static int GENERO_MASCULINO = 1;
   public static int GENERO_FEMENINO = 2;
	
	
   private String percodjerar;
   private String pertipo;
   private String percodigo;
   private String perexpdoccoddep;
   private String perexpdoccodmun;
   private String pertipdocum;
   private String pernumdocum;
   private String pernombre1;
   private String pernombre2;
   private String perapellido1;
   private String perapellido2;
   private String perfechanac;
   private String perlugnaccoddep;
   private String perlugnaccodmun;
   private String peredad;
   private String pergenero;
   private String peretnia;
   private String perdireccion;
   private String pertelefono;
   private String perzona;
   private String perlocvereda;
   private String perbarcorreg;
   private String[] sedejornada;
   private String estado;
   private int perVigencia;
   private String peremail;

   /**
    * asigna el campo percodjerar
    *	@param String s
    */
   	public void setSedejornada(String []s){
   		this.sedejornada=s;
   	}	

   /**
    * retorna el campo percodjerar
    *	@return String
    */
   	public String[] getSedejornada(){
   		return sedejornada;
   	}

   	/**
 * asigna el campo percodjerar
 *	@param String s
 */
	public void setPercodjerar(String s){
		this.percodjerar=s;
	}	

/**
 * retorna el campo percodjerar
 *	@return String
 */
	public String getPercodjerar(){
		return percodjerar !=null ? percodjerar : "";
	}

	/**
 * asigna el campo pertipo
 *	@param String s
 */
	public void setPertipo(String s){
		this.pertipo=s;
	}	

/**
 * retorna el campo pertipo
 *	@return String
 */
	public String getPertipo(){
		return pertipo !=null ? pertipo : "";
	}

	/**
 * asigna el campo percodigo
 *	@param String s
 */
	public void setPercodigo(String s){
		this.percodigo=s;
	}	

/**
 * retorna el campo percodigo
 *	@return String
 */
	public String getPercodigo(){
		return percodigo !=null ? percodigo : "";
	}

	/**
 * asigna el campo perexpdoccoddep
 *	@param String s
 */
	public void setPerexpdoccoddep(String s){
		this.perexpdoccoddep=s;
	}	

/**
 * retorna el campo perexpdoccoddep
 *	@return String
 */
	public String getPerexpdoccoddep(){
		return perexpdoccoddep !=null ? perexpdoccoddep : "";
	}

	/**
 * asigna el campo perexpdoccodmun
 *	@param String s
 */
	public void setPerexpdoccodmun(String s){
		this.perexpdoccodmun=s;
	}	

/**
 * retorna el campo perexpdoccodmun
 *	@return String
 */
	public String getPerexpdoccodmun(){
		return perexpdoccodmun !=null ? perexpdoccodmun : "";
	}

	/**
 * asigna el campo pertipdocum
 *	@param String s
 */
	public void setPertipdocum(String s){
		this.pertipdocum=s;
	}	

/**
 * retorna el campo pertipdocum
 *	@return String
 */
	public String getPertipdocum(){
		return pertipdocum !=null ? pertipdocum : "";
	}

	/**
 * asigna el campo pernumdocum
 *	@param String s
 */
	public void setPernumdocum(String s){
		this.pernumdocum=s;
	}	

/**
 * retorna el campo pernumdocum
 *	@return String
 */
	public String getPernumdocum(){
		return pernumdocum !=null ? pernumdocum : "";
	}

	/**
 * asigna el campo pernombre1
 *	@param String s
 */
	public void setPernombre1(String s){
		this.pernombre1=s;
	}	

/**
 * retorna el campo pernombre1
 *	@return String
 */
	public String getPernombre1(){
		return pernombre1 !=null ? pernombre1 : "";
	}

	/**
 * asigna el campo pernombre2
 *	@param String s
 */
	public void setPernombre2(String s){
		this.pernombre2=s;
	}	

/**
 * retorna el campo pernombre2
 *	@return String
 */
	public String getPernombre2(){
		return pernombre2 !=null ? pernombre2 : "";
	}

	/**
 * asigna el campo perapellido1
 *	@param String s
 */
	public void setPerapellido1(String s){
		this.perapellido1=s;
	}	

/**
 * retorna el campo perapellido1
 *	@return String
 */
	public String getPerapellido1(){
		return perapellido1 !=null ? perapellido1 : "";
	}

	/**
 * asigna el campo perapellido2
 *	@param String s
 */
	public void setPerapellido2(String s){
		this.perapellido2=s;
	}	

/**
 * retorna el campo perapellido2
 *	@return String
 */
	public String getPerapellido2(){
		return perapellido2 !=null ? perapellido2 : "";
	}

	/**
 * asigna el campo perfechanac
 *	@param String s
 */
	public void setPerfechanac(String s){
		this.perfechanac=s;
	}	

/**
 * retorna el campo perfechanac
 *	@return String
 */
	public String getPerfechanac(){
		return perfechanac !=null ? perfechanac : "";
	}

	/**
 * asigna el campo 
 *	@param String s
 */
	public void setPerlugnaccoddep(String s){
		this.perlugnaccoddep=s;
	}	

/**
 * retorna el campo perlugnaccoddep
 *	@return String
 */
	public String getPerlugnaccoddep(){
		return perlugnaccoddep !=null ? perlugnaccoddep : "";
	}

	/**
 * asigna el campo perlugnaccodmun
 *	@param String s
 */
	public void setPerlugnaccodmun(String s){
		this.perlugnaccodmun=s;
	}	

/**
 * retorna el campo perlugnaccodmun
 *	@return String
 */
	public String getPerlugnaccodmun(){
		return perlugnaccodmun !=null ? perlugnaccodmun : "";
	}

	/**
 * asigna el campo peredad
 *	@param String s
 */
	public void setPeredad(String s){
		this.peredad=s;
	}	

/**
 * retorna el campo peredad
 *	@return String
 */
	public String getPeredad(){
		return peredad !=null ? peredad : "";
	}

	/**
 * asigna el campo pergenero
 *	@param String s
 */
	public void setPergenero(String s){
		this.pergenero=s;
	}	

/**
 * retorna el campo pergenero
 *	@return String
 */
	public String getPergenero(){
		return pergenero !=null ? pergenero : "";
	}

	/**
 * asigna el campo peretnia
 *	@param String s
 */
	public void setPeretnia(String s){
		this.peretnia=s;
	}	

/**
 * retorna el campo peretnia
 *	@return String
 */
	public String getPeretnia(){
		return peretnia !=null ?  peretnia : "";
	}

	/**
 * asigna el campo perdireccion
 *	@param String s
 */
	public void setPerdireccion(String s){
		this.perdireccion=s;
	}	

/**
 * retorna el campo perdireccion
 *	@return String
 */
	public String getPerdireccion(){
		return perdireccion !=null ? perdireccion : "";
	}

	/**
 * asigna el campo pertelefono
 *	@param String s
 */
	public void setPertelefono(String s){
		this.pertelefono=s;
	}	

/**
 * retorna el campo pertelefono
 *	@return String
 */
	public String getPertelefono(){
		return pertelefono !=null ? pertelefono : "";
	}

	/**
 * asigna el campo perzona
 *	@param String s
 */
	public void setPerzona(String s){
		this.perzona=s;
	}	

/**
 * retorna el campo perzona
 *	@return String
 */
	public String getPerzona(){
		return perzona !=null ? perzona : "";
	}

	/**
 * asigna el campo perlocvereda
 *	@param String s
 */
	public void setPerlocvereda(String s){
		this.perlocvereda=s;
	}	

/**
 * retorna el campo perlocvereda
 *	@return String
 */
	public String getPerlocvereda(){
		return perlocvereda !=null ? perlocvereda : "";
	}

	/**
 * asigna el campo perbarcorreg
 *	@param String s
 */
	public void setPerbarcorreg(String s){
		this.perbarcorreg=s;
	}	

/**
 * retorna el campo perbarcorreg
 *	@return String
 */
	public String getPerbarcorreg(){
		return perbarcorreg !=null ? perbarcorreg : "";
	}

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

	public String seleccion(String s){
		//System.out.println("la vaina "+s);
		if(sedejornada!=null){
			for(int i=0;i<sedejornada.length;++i){
				//System.out.println("si "+sedejornada[i]);
				if(sedejornada[i].equals(s)){
					//System.out.println("si check");
					return "CHECKED";					
				}	
			}
			//System.out.println("--**");
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

/**
 * @return Return the perVigencia.
 */
public final int getPerVigencia() {
	return perVigencia;
}

/**
 * @param perVigencia The perVigencia to set.
 */
public final void setPerVigencia(int perVigencia) {
	this.perVigencia = perVigencia;
}

public String getPeremail() {
	return peremail;
}

public void setPeremail(String peremail) {
	this.peremail = peremail;
}	
}