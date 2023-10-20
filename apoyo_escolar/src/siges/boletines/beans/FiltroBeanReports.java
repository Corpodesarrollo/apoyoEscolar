package siges.boletines.beans;

import java.io.*;

/**
*	Nombre: FiltroBeanReports.java	<BR>
*	Descripcinn:	Contiene los datos referentes al formulario de peticinn del Boletin<BR>
*	Funciones de la pngina:	Almacenar temporalmente los datos del formulario de peticinn del boletin a generar <BR>
*	Entidades afectadas:	No aplica <BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/

public class FiltroBeanReports implements Serializable, Cloneable{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String institucion;
   private String instituciondane;
   private String sede;
   private String jornada;
   private String metodologia;
   private String grado;
   private String grupo;
   private String periodo;
   private String periodonom;
   private String id;
   private String orden;
   private String usuarioid;
   private String nombreboletin;
   private String nombreboletinpre;
   private String nombreboletinzip;
   
   private boolean conTotLogros;
   private boolean conAusenciasT;
   private boolean conAusenciasD;
   private boolean conFirmaRector;   
   private boolean conFirmaDirector;
   private boolean formatoUno;
   private boolean formatoDos;
   private boolean conAreas;   
   private boolean conAsignaturas;
   private boolean estudianteActivo;
   
   private String mostrarAreas;   
   private String mostrarAsignaturas;
   private String mostrarDescriptores;
   private String mostrarLogros;
   private String mostrarLogrosP;
   
   private String mostrarFirmaDirector;
   private String mostrarFirmaRector;
   
   private long filVigencia;
   
   private long filInst;
   
   private int filTipoDoc;
   
   private int tipoProm;
   
   private long numPer;
   private String nomPerFinal;
   private int nivelEval;

   


/**
 * @return Returns the estudianteActivo.
 */
public boolean isEstudianteActivo() {
	return estudianteActivo;
}

/**
 * @param estudianteActivo The estudianteActivo to set.
 */
public void setEstudianteActivo(boolean estudianteActivo) {
	this.estudianteActivo = estudianteActivo;
}

/**
  * asigna el campo institucion
  *	@param String s
  */
 	public void setInstitucion(String s){
 		this.institucion=s;
 	}	

	/**
  * retorna el campo institucion 
  *	@return String
  */
 	public String getInsitucion(){
 		return institucion !=null ? institucion : "";
 	}
   
   
/**
 * asigna el campo sede
 *	@param String s
 */
	public void setSede(String s){
		this.sede=s;
	}	

/**
 * retorna el campo sede 
 *	@return String
 */
	public String getSede(){
		return sede !=null ? sede : "";
	}

/**
 * asigna el campo jornada
 *	@param String s
 */
	public void setJornada(String s){
		this.jornada=s;
	}	

/**
 * retorna el campo jornada
 *	@return String
 */
	public String getJornada(){
		return jornada !=null ? jornada : "";
	}

/**
 * asigna el campo metodologia
 *	@param String s
 */
	public void setMetodologia(String s){
		this.metodologia=s;
	}	

/**
 * retorna el campo metodologia
 *	@return String
 */
	public String getMetodologia(){
	    return metodologia !=null ? metodologia : "";
	}

		
/**
 * asigna el campo 
 *	@param String s
 */
	public void setGrado(String s){
		this.grado=s;
	}	

/**
 * retorna el campo 
 *	@return String
 */
	public String getGrado(){
		return grado !=null ? grado : "";
	}

/**
 * asigna el campo grupo
 *	@param String s
 */
	public void setGrupo(String s){
		this.grupo=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getGrupo(){
		return grupo !=null ? grupo : "";
	}

/**
 * asigna el campo grupo
 *	@param String s
 */
	public void setPeriodo(String s){
		this.periodo=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getPeriodo(){
		return periodo !=null ? periodo : "";
	}

	
/**
 * asigna el campo id
 *	@param String s
 */
	public void setId(String s){
		this.id=s;
	}	

/**
 * retorna el campo id
 *	@return String
 */
	public String getId(){
		return id !=null ? id.trim() : "";
	}

/**
 * asigna el campo usuarioid
 *	@param String s
 */
	public void setUsuarioid(String s){
		this.usuarioid=s;
	}	
	/**
 * retorna el campo usuarioid
 *	@return String
 */
	public String getUsuarioid(){
		return usuarioid !=null ? usuarioid.trim() : "";
	}

	
/**
 * asigna el campo orden
 *	@param String s
*/
	public void setOrden(String s){
		this.orden=s;
	}	

/**
 * retorna el campo orden
 *	@return String
*/
	public String getOrden(){
		return orden !=null ? orden : "";
	}

/**
 * asigna el campo periodo
 *	@param String s
*/
	public void setPeriodonom(String s){
		this.periodonom=s;
	}	

/**
 * retorna el campo periodo
 *	@return String
*/
	public String getPeriodonom(){
		return periodonom !=null ? periodonom : "";
	}


/**
 * asigna el campo nombreboletin
 *	@param String s
 */
	public void setNombreboletin(String s){
		this.nombreboletin=s;
	}	

/**
 * retorna el campo nombreboletin 
 *	@return String
 */
	public String getNombreboletin(){
		return nombreboletin !=null ? nombreboletin : "";
	}
		

/**
 * asigna el campo nombreboletinpre
 *	@param String s
*/
	public void setNombreboletinpre(String s){
	   this.nombreboletinpre=s;
	}	

/**
 * retorna el campo nombreboletinpre 
 *	@return String
*/
	public String getNombreboletinpre(){
	   return nombreboletinpre !=null ? nombreboletinpre : "";
	}
	
/**
 * asigna el campo nombreboletinzip
 *	@param String s
*/
	public void setNombreboletinzip(String s){
	   this.nombreboletinzip=s;
	}	

/**
 * retorna el campo nombreboletinzip 
 *	@return String
*/
	public String getNombreboletinzip(){
	   return nombreboletinzip !=null ? nombreboletinzip : "";
	}
	


	/**
	 * @return Returns the conTotLogros
	 */
	public boolean isConTotLogros() {
		return conTotLogros;
	}

	/**
	 * @param conTotLogros The conTotLogros to set.
	 */
	public void setConTotLogros(boolean conTotLogros) {
		this.conTotLogros = conTotLogros;
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
 * @return Returns the instituciondane.
 */
public String getInstituciondane() {
	return instituciondane;
}

/**
 * @param instituciondane The instituciondane to set.
 */
public void setInstituciondane(String instituciondane) {
	this.instituciondane = instituciondane;
}

/**
 * @return Returns the institucion.
 */
public String getInstitucion() {
	return institucion;
}

public FiltroBeanReports() {
}

/**
 * @return Returns the conAusenciasD.
 */
public boolean isConAusenciasD() {
	return conAusenciasD;
}

/**
 * @param conAusenciasD The conAusenciasD to set.
 */
public void setConAusenciasD(boolean conAusenciasD) {
	this.conAusenciasD = conAusenciasD;
}

/**
 * @return Returns the conAusenciasT.
 */
public boolean isConAusenciasT() {
	return conAusenciasT;
}

/**
 * @param conAusenciasT The conAusenciasT to set.
 */
public void setConAusenciasT(boolean conAusenciasT) {
	this.conAusenciasT = conAusenciasT;
}

/**
 * @return Returns the conFirmaDirector.
 */
public boolean isConFirmaDirector() {
	return conFirmaDirector;
}

/**
 * @param conFirmaDirector The conFirmaDirector to set.
 */
public void setConFirmaDirector(boolean conFirmaDirector) {
	this.conFirmaDirector = conFirmaDirector;
}

/**
 * @return Returns the conFirmaRector.
 */
public boolean isConFirmaRector() {
	return conFirmaRector;
}

/**
 * @param conFirmaRector The conFirmaRector to set.
 */
public void setConFirmaRector(boolean conFirmaRector) {
	this.conFirmaRector = conFirmaRector;
}

/**
 * @return Returns the formatoDos.
 */
public boolean isFormatoDos() {
	return formatoDos;
}

/**
 * @param formatoDos The formatoDos to set.
 */
public void setFormatoDos(boolean formatoDos) {
	this.formatoDos = formatoDos;
}

/**
 * @return Returns the formatoUno.
 */
public boolean isFormatoUno() {
	return formatoUno;
}

/**
 * @param formatoUno The formatoUno to set.
 */
public void setFormatoUno(boolean formatoUno) {
	this.formatoUno = formatoUno;
}

/**
 * @return Returns the conAreas.
 */
public boolean isConAreas() {
	return conAreas;
}

/**
 * @param conAreas The conAreas to set.
 */
public void setConAreas(boolean conAreas) {
	this.conAreas = conAreas;
}

/**
 * @return Returns the conAsignaturas.
 */
public boolean isConAsignaturas() {
	return conAsignaturas;
}

/**
 * @param conAsignaturas The conAsignaturas to set.
 */
public void setConAsignaturas(boolean conAsignaturas) {
	this.conAsignaturas = conAsignaturas;
}

public long getFilVigencia() {
	return filVigencia;
}

public void setFilVigencia(long filVigencia) {
	this.filVigencia = filVigencia;
}

public int getFilTipoDoc() {
	return filTipoDoc;
}

public void setFilTipoDoc(int filTipoDoc) {
	this.filTipoDoc = filTipoDoc;
}

public String getMostrarAreas() {
	return mostrarAreas;
}

public void setMostrarAreas(String mostrarAreas) {
	this.mostrarAreas = mostrarAreas;
}

public String getMostrarAsignaturas() {
	return mostrarAsignaturas;
}

public void setMostrarAsignaturas(String mostrarAsignaturas) {
	this.mostrarAsignaturas = mostrarAsignaturas;
}

public String getMostrarDescriptores() {
	return mostrarDescriptores;
}

public void setMostrarDescriptores(String mostrarDescriptores) {
	this.mostrarDescriptores = mostrarDescriptores;
}

public String getMostrarLogros() {
	return mostrarLogros;
}

public void setMostrarLogros(String mostrarLogros) {
	this.mostrarLogros = mostrarLogros;
}

public String getMostrarLogrosP() {
	return mostrarLogrosP;
}

public void setMostrarLogrosP(String mostrarLogrosP) {
	this.mostrarLogrosP = mostrarLogrosP;
}

public int getTipoProm() {
	return tipoProm;
}

public void setTipoProm(int tipoProm) {
	this.tipoProm = tipoProm;
}

public long getNumPer() {
	return numPer;
}

public void setNumPer(long numPer) {
	this.numPer = numPer;
}

public String getNomPerFinal() {
	return nomPerFinal;
}

public void setNomPerFinal(String nomPerFinal) {
	this.nomPerFinal = nomPerFinal;
}

public int getNivelEval() {
	return nivelEval;
}

public void setNivelEval(int nivelEval) {
	this.nivelEval = nivelEval;
}

public long getFilInst() {
	return filInst;
}

public void setFilInst(long filInst) {
	this.filInst = filInst;
}

public String getMostrarFirmaDirector() {
	return mostrarFirmaDirector;
}

public void setMostrarFirmaDirector(String mostrarFirmaDirector) {
	this.mostrarFirmaDirector = mostrarFirmaDirector;
}

public String getMostrarFirmaRector() {
	return mostrarFirmaRector;
}

public void setMostrarFirmaRector(String mostrarFirmaRector) {
	this.mostrarFirmaRector = mostrarFirmaRector;
}




}