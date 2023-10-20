package siges.estudiante.beans;

/**
*	Nombre:	Basica <BR>
*	Descripcion:	Datos de informacion basica del egresado	<BR>
*	Funciones de la pagina:	Almacenar los datos del egresado para un nuevo registro o para editar	<BR>
*	Entidades afectadas:	Estudiante	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class Basica implements Cloneable{
   private String estgrupo;
   private String estcodigo;
   private String estexpdoccoddep;
   private String estexpdoccodmun;
   private String esttipodoc;
   private String estnumdoc;
   private String estnombre1;
   private String estnombre2;
   private String estapellido1;
   private String estapellido2;
   private String estfechanac;
   private String estlugnaccoddep;
   private String estlugnaccodmun;
   private String estedad;
   private String estgenero;
   private String estetnia;
   private String estcondicion;
   private String estcodconv;
   private String estdireccion;
   private String esttelefono;
   private String estzona;
   private String estlocvereda;
   private String estbarcorreg;
   private String estestrato;
   private String estsisben;
   private String estcodfamilia;
   private String estado;
   private String estsede;
   private String estjor;
   private String estmet;
   private String estgra;
   private String estgru;

/**
 * asigna el campo Estgrupo
 *	@param String s
 */
	public void setEstgrupo(String s){
		this.estgrupo=s;
	}	

/**
 * retorna el campo estgrupo
 *	@return String
 */
	public String getEstgrupo(){
		return estgrupo !=null ? estgrupo : "";
	}

/**
 * asigna el campo estcodigo
 *	@param String s
 */
	public void setEstcodigo(String s){
		this.estcodigo=s;
	}	

/**
 * retorna el campo Estcodigo
 *	@return String
 */
	public String getEstcodigo(){
		return estcodigo !=null ? estcodigo : "";
	}

/**
 * asigna el campo Estexpdoccoddep
 *	@param String s
 */
	public void setEstexpdoccoddep(String s){
		this.estexpdoccoddep=s;
	}	

/**
 * retorna el campo estexpdoccoddep
 *	@return String
 */
	public String getEstexpdoccoddep(){
		return estexpdoccoddep !=null ? estexpdoccoddep : "";
	}

/**
 * asigna el campo Estexpdoccodmun
 *	@param String s
 */
	public void setEstexpdoccodmun(String s){
		this.estexpdoccodmun=s;
	}	

/**
 * retorna el campo estexpdoccodmun
 *	@return String
 */
	public String getEstexpdoccodmun(){
		return estexpdoccodmun !=null ? estexpdoccodmun : "";
	}

/**
 * asigna el campo Esttipodoc
 *	@param String s
 */
	public void setEsttipodoc(String s){
		this.esttipodoc=s;
	}	

/**
 * retorna el campo esttipodoc
 *	@return String
 */
	public String getEsttipodoc(){
		return esttipodoc !=null ? esttipodoc : "";
	}

/**
 * asigna el campo Estnumdoc
 *	@param String s
 */
	public void setEstnumdoc(String s){
		this.estnumdoc=s;
	}	

/**
 * retorna el campo estnumdoc
 *	@return String
 */
	public String getEstnumdoc(){
		return estnumdoc !=null ? estnumdoc : "";
	}

/**
 * asigna el campo Estnombre1
 *	@param String s
 */
	public void setEstnombre1(String s){
		this.estnombre1=s;
	}	

/**
 * retorna el campo estnombre1
 *	@return String
 */
	public String getEstnombre1(){
		return estnombre1 !=null ? estnombre1 : "";
	}

/**
 * asigna el campo Estnombre2
 *	@param String s
 */
	public void setEstnombre2(String s){
		this.estnombre2=s;
	}	

/**
 * retorna el campo estnombre2
 *	@return String
 */
	public String getEstnombre2(){
		return estnombre2 !=null ? estnombre2 : "";
	}

/**
 * asigna el campo Estapellido1
 *	@param String s
 */
	public void setEstapellido1(String s){
		this.estapellido1=s;
	}	

/**
 * retorna el campo estapellido1
 *	@return String
 */
	public String getEstapellido1(){
		return estapellido1 !=null ? estapellido1 : "";
	}

/**
 * asigna el campo Estapellido2
 *	@param String s
 */
	public void setEstapellido2(String s){
		this.estapellido2=s;
	}	

/**
 * retorna el campo estapellido2
 *	@return String
 */
	public String getEstapellido2(){
		return estapellido2 !=null ? estapellido2 : "";
	}

/**
 * asigna el campo Estfechanac
 *	@param String s
 */
	public void setEstfechanac(String s){
		this.estfechanac=s;
	}	

/**
 * retorna el campo estfechanac
 *	@return String
 */
	public String getEstfechanac(){
		return estfechanac !=null ? estfechanac : "";
	}

/**
 * asigna el campo Estlugnaccoddep
 *	@param String s
 */
	public void setEstlugnaccoddep(String s){
		this.estlugnaccoddep=s;
	}	

/**
 * retorna el campo estlugnaccoddep
 *	@return String
 */
	public String getEstlugnaccoddep(){
		return estlugnaccoddep !=null ? estlugnaccoddep : "";
	}

/**
 * asigna el campo Estlugnaccodmun
 *	@param String s
 */
	public void setEstlugnaccodmun(String s){
		this.estlugnaccodmun=s;
	}	

/**
 * retorna el campo estlugnaccodmun
 *	@return String
 */
	public String getEstlugnaccodmun(){
		return estlugnaccodmun !=null ? estlugnaccodmun : "";
	}

/**
 * asigna el campo Estedad
 *	@param String s
 */
	public void setEstedad(String s){
		this.estedad=s;
	}	

/**
 * retorna el campo estedad
 *	@return String
 */
	public String getEstedad(){
		return estedad !=null ? estedad : "";
	}

/**
 * asigna el campo Estgenero
 *	@param String s
 */
	public void setEstgenero(String s){
		this.estgenero=s;
	}	

/**
 * retorna el campo estgenero
 *	@return String
 */
	public String getEstgenero(){
		return estgenero !=null ? estgenero : "";
	}

/**
 * asigna el campo Estetnia
 *	@param String s
 */
	public void setEstetnia(String s){
		this.estetnia=s;
	}	

/**
 * retorna el campo estetnia
 *	@return String
 */
	public String getEstetnia(){
		return estetnia !=null ? estetnia : "";
	}

/**
 * asigna el campo Estcondicion
 *	@param String s
 */
	public void setEstcondicion(String s){
		this.estcondicion=s;
	}	

/**
 * retorna el campo estcondicion
 *	@return String
 */
	public String getEstcondicion(){
		return estcondicion !=null ? estcondicion : "";
	}

/**
 * asigna el campo Estcodconv
 *	@param String s
 */
	public void setEstcodconv(String s){
		this.estcodconv=s;
	}	

/**
 * retorna el campo estcodconv
 *	@return String
 */
	public String getEstcodconv(){
		return estcodconv !=null ? estcodconv : "";
	}

/**
 * asigna el campo Estdireccion
 *	@param String s
 */
	public void setEstdireccion(String s){
		this.estdireccion=s;
	}	

/**
 * retorna el campo estdireccion
 *	@return String
 */
	public String getEstdireccion(){
		return estdireccion !=null ? estdireccion : "";
	}

/**
 * asigna el campo Esttelefono
 *	@param String s
 */
	public void setEsttelefono(String s){
		this.esttelefono=s;
	}	

/**
 * retorna el campo esttelefono
 *	@return String
 */
	public String getEsttelefono(){
		return esttelefono !=null ? esttelefono : "";
	}

/**
 * asigna el campo Estzona
 *	@param String s
 */
	public void setEstzona(String s){
		this.estzona=s;
	}	

/**
 * retorna el campo estzona
 *	@return String
 */
	public String getEstzona(){
		return estzona !=null ? estzona : "";
	}

/**
 * asigna el campo Estlocvereda
 *	@param String s
 */
	public void setEstlocvereda(String s){
		this.estlocvereda=s;
	}	

/**
 * retorna el campo estlocvereda
 *	@return String
 */
	public String getEstlocvereda(){
		return estlocvereda !=null ? estlocvereda : "";
	}

/**
 * asigna el campo Estbarcorreg
 *	@param String s
 */
	public void setEstbarcorreg(String s){
		this.estbarcorreg=s;
	}	

/**
 * retorna el campo estbarcorreg
 *	@return String
 */
	public String getEstbarcorreg(){
		return estbarcorreg !=null ? estbarcorreg : "";
	}

/**
 * asigna el campo Estestrato
 *	@param String s
 */
	public void setEstestrato(String s){
		this.estestrato=s;
	}	

/**
 * retorna el campo estestrato
 *	@return String
 */
	public String getEstestrato(){
		return estestrato !=null ? estestrato : "";
	}

/**
 * asigna el campo Estsisben
 *	@param String s
 */
	public void setEstsisben(String s){
		this.estsisben=s;
	}	

/**
 * retorna el campo estsisben
 *	@return String
 */
	public String getEstsisben(){
		return estsisben !=null ? estsisben : "";
	}

/**
 * asigna el campo EstcodFamilia
 *	@param String s
 */
	public void setEstcodfamilia(String s){
		this.estcodfamilia=s;
	}	

/**
 * retorna el campo EstcodFamilia
 *	@return String
 */
	public String getEstcodfamilia(){
		return estcodfamilia !=null ? estcodfamilia : "";
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
 * @return Returns the estgra.
 */
public String getEstgra() {
	return estgra!=null?estgra:"";
}
/**
 * @param estgra The estgra to set.
 */
public void setEstgra(String estgra) {
	this.estgra = estgra;
}
/**
 * @return Returns the estgru.
 */
public String getEstgru() {
	return estgru!=null?estgru:"";
}
/**
 * @param estgru The estgru to set.
 */
public void setEstgru(String estgru) {
	this.estgru = estgru;
}
/**
 * @return Returns the estmet.
 */
public String getEstmet() {
	return estmet!=null?estmet:"";
}
/**
 * @param estmet The estmet to set.
 */
public void setEstmet(String estmet) {
	this.estmet = estmet;
}
/**
 * @return Returns the estsede.
 */
public String getEstsede() {
	return estsede!=null?estsede:"";
}
/**
 * @param estsede The estsede to set.
 */
public void setEstsede(String estsede) {
	this.estsede = estsede;
}
/**
 * @return Returns the estjor.
 */
public String getEstjor() {
	return estjor!=null?estjor:"";
}
/**
 * @param estjor The estjor to set.
 */
public void setEstjor(String estjor) {
	this.estjor = estjor;
}
}