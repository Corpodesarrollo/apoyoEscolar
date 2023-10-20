package siges.plantilla.beans;

/** siges.plantilla.beans<br>
 * Funcinn: Objeto de datos que contiene la informacion solicitada en los formularios de filtro de plantillas   
 * <br>
 */
public class FiltroPlantilla implements Cloneable{
	private String municipio;
	private String localidad;
	private String institucion;
	private String institucion_;
	private String sede;//1
	private String sede_;//1
	private String jornada;//2
	private String jornada_;//2
	private String grado;//3
	private String grado_;
	private String grupo;//4
	private String grupo_;
	private String area;//10
	private String area_;
	private String asignatura;//10
	private String asignatura_;
	private String asignaturaAbre_="";
	private String logro;//11
	private String periodo;//12
	private String periodo_;
	private String periodo2;//12
	private String periodo2_;
	private String metodologia;
	private String metodologia_;
	private String docente;
	private String docente_;
	private String descriptor;//13
	private String descriptor_;//13
	private String vigencia;//10
	private String periodoAbrev;
	private String plantilla;
	private String orden;
	private String valor;
	private String cerrar;
	private String lectura;
	private String jerarquiagrupo;
	private String[] nota;
	private String[] nota2;
	private String[] indicador;
	private String[] ausjus;
	private String[] ausnojus;
	private String[] motivo;
	private String[] porcentaje;
	private String[] fort;
	private String[] difi;
	private String[] reco;
	private String[] est;
	private String[] actualizar;
	
	private String tipoPlantilla;
	private String nivelLogro;
	private String jerarquiaLogro;
	private String estadoPlantilla;
	private String usuarioPlantilla;
	private String nombrePlantilla;

	private int filPlanEstudios;
	private int filNivelPerfil;
	private long filCodNivelEvalucaion;
	private long filCodTipoEval;
	private int rangosCompletos; // 0 si no esta completos 1 si si
	private long filCodModoEval;
	private long filCodTipoEvalPres;
	
	private boolean filBandEvalArea;
	
	/**
	 * @return Returns the institucion_.
	 */
	public String getInstitucion_() {
		return institucion_!=null?institucion_:"";
	}
	/**
	 * @param institucion_ The institucion_ to set.
	 */
	public void setInstitucion_(String institucion_) {
		this.institucion_ = institucion_;
	}
	/**
	 * @return Returns the jornada_.
	 */
	public String getJornada_() {
		return jornada_!=null?jornada_:"";
	}
	/**
	 * @param jornada_ The jornada_ to set.
	 */
	public void setJornada_(String jornada_) {
		this.jornada_ = jornada_;
	}
	/**
	 * @return Returns the sede_.
	 */
	public String getSede_() {
		return sede_!=null?sede_:"";
	}
	/**
	 * @param sede_ The sede_ to set.
	 */
	public void setSede_(String sede_) {
		this.sede_ = sede_;
	}

/**
 * asigna el campo institucion
 *	@param String s
 */
	public void setMetodologia(String s){
		this.metodologia=s;
	}	

/**
* retorna el campo institucion
*	@return String
*/
	public String getMetodologia(){
		return metodologia !=null ? metodologia : "";
	}
	/**
	 * asigna el campo institucion
	 *	@param String s
	 */
		public void setMetodologia_(String s){
			this.metodologia_=s;
		}	
		/**
	 * retorna el campo institucion
	 *	@return String
	 */
		public String getMetodologia_(){
			return metodologia_ !=null ? metodologia_ : "";
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
	public String getInstitucion(){
		return institucion !=null ? institucion : "";
	}
/**
 * asigna el campo municipio
 *	@param String s
 */
	public void setMunicipio(String s){
		this.municipio=s;
	}	

/**
 * retorna el campo municipio
 *	@return String
 */
	public String getMunicipio(){
		return municipio !=null ? municipio : "";
	}
/**
 * asigna el campo localidad
 *	@param String s
 */
	public void setLocalidad(String s){
		this.localidad=s;
	}	

/**
 * retorna el campo localidad
 *	@return String
 */
	public String getLocalidad(){
		return localidad !=null ? localidad : "";
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
 * asigna el campo grado 
 *	@param String s
 */
	public void setGrado(String s){
		this.grado=s;
	}	

/**
 * retorna el campo  Grado
 *	@return String
 */
	public String getGrado(){
		return grado !=null ? grado : "";
	}

/**
 * asigna el campo Grado_
 *	@param String s
 */
	public void setGrado_(String s){
		this.grado_=s;
	}	

/**
 * retorna el campo  Grado_
 *	@return String
 */
	public String getGrado_(){
		return grado_ !=null ? grado_ : "";
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
	public void setGrupo_(String s){
		this.grupo_=s;
	}	

/**
 * retorna el campo grupo
 *	@return String
 */
	public String getGrupo_(){
		return grupo_ !=null ? grupo_ : "";
	}

/**
 * asigna el campo area
 *	@param String s
 */
	public void setArea(String s){
		this.area=s;
	}	

/**
 * retorna el campo area
 *	@return String
 */
	public String getArea(){
		return area !=null ? area : "";
	}

/**
 * asigna el campo area
 *	@param String s
 */
	public void setArea_(String s){
		this.area_=s;
	}	

/**
 * retorna el campo area
 *	@return String
 */
	public String getArea_(){
		return area_ !=null ? area_ : "";
	}

	/**
 * asigna el campo asignatura
 *	@param String s
 */
	public void setAsignatura(String s){
		this.asignatura=s;
	}	

/**
 * retorna el campo asignatura
 *	@return String
 */
	public String getAsignatura(){
		return asignatura !=null ? asignatura : "";
	}

	/**
 * asigna el campo asignatura
 *	@param String s
 */
	public void setAsignatura_(String s){
		this.asignatura_=s;
		if(s != null && s.length() > 35){
		this.asignaturaAbre_ = s.substring(0,35);
		}
	}

/**
 * retorna el campo asignatura
 *	@return String
 */
	public String getAsignatura_(){
		return asignatura_ !=null ? asignatura_ : "";
	}

/**
 * retorna el campo logro
 *	@return String
 */
	public String getLogro(){
		return logro !=null ? logro : "";
	}

	/**
 * asigna el campo logro
 *	@param String s
 */
	public void setLogro(String s){
		this.logro=s;
	}	


	/**
 * asigna el campo periodo
 *	@param String s
 */
	public void setPeriodo(String s){
		this.periodo=s;
	}	

/**
 * retorna el campo periodo
 *	@return String
 */
	public String getPeriodo(){
		return periodo !=null ? periodo : "";
	}

	/**
 * asigna el campo periodo
 *	@param String s
 */
	public void setPeriodo_(String s){
		this.periodo_=s;
	}	

/**
 * retorna el campo periodo
 *	@return String
 */
	public String getPeriodo_(){
		return periodo_ !=null ? periodo_ : "";
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
 * asigna el campo orden
 *	@param String s
 */
	public void setPlantilla(String s){
		this.plantilla=s;
	}	

/**
 * retorna el campo orden
 *	@return String
 */
	public String getPlantilla(){
		return plantilla !=null ? plantilla : "";
	}

/**
 * asigna el campo valor
 *	@param String s
 */
	public void setValor(String s){
		this.valor=s;
	}	

/**
 * retorna el campo valor
 *	@return String
 */
	public String getValor(){
		return valor !=null ? valor : "";
	}

/**
 * asigna el campo Cerrar
 *	@param String s
 */
	public void setCerrar(String s){
		this.cerrar=s;
	}	

/**
 * retorna el campo Cerrar
 *	@return String
 */
	public String getCerrar(){
		return cerrar !=null ? cerrar : "";
	}

/**
 * asigna el campo jerarquiagrupo
 *	@param String s
 */
	public void setJerarquiagrupo(String s){
		this.jerarquiagrupo=s;
	}	

/**
 * retorna el campo jerarquiagrupo
 *	@return String
 */
	public String getJerarquiagrupo(){
		return jerarquiagrupo !=null ? jerarquiagrupo : "0";
	}

/**
 * asigna el campo nota
 *	@param String s
 */
	public void setNota(String[] s){
		this.nota=s;
	}	

/**
 * retorna el campo nota
 *	@return String
 */
	public String[] getNota(){
		return nota;
	}

/**
 * asigna el campo actualizar
 *	@param String s
 */
	public void setActualizar(String[] s){
		this.actualizar=s;
	}	

/**
 * retorna el campo actualizar
 *	@return String
 */
	public String[] getActualizar(){
		return actualizar;
	}

/**
 * asigna el campo indicador
 *	@param String s
 */
	public void setIndicador(String[] s){
		this.indicador=s;
	}	

/**
 * retorna el campo indicador
 *	@return String
 */
	public String[] getIndicador(){
		return indicador;
	}

/**
 * asigna el campo ausjus
 *	@param String s
 */
	public void setAusjus(String[] s){
		this.ausjus=s;
	}	

/**
 * retorna el campo ausjus
 *	@return String
 */
	public String[] getAusjus(){
		return ausjus;
	}

/**
 * asigna el campo indicador
 *	@param String s
 */
	public void setAusnojus(String[] s){
		this.ausnojus=s;
	}	

/**
 * retorna el campo indicador
 *	@return String
 */
	public String[] getAusnojus(){
		return ausnojus;
	}

/**
 * asigna el campo fort
 *	@param String s
 */
	public void setFort(String[] s){
		this.fort=s;
	}	
/**
 * retorna el campo fort
 *	@return String
 */
	public String[] getFort(){
		return fort;
	}

/**
 * asigna el campo difi
 *	@param String s
 */
	public void setDifi(String[] s){
		this.difi=s;
	}	
/**
 * retorna el campo difi
 *	@return String
 */
	public String[] getDifi(){
		return difi;
	}

/**
 * asigna el campo reco
 *	@param String s
 */
	public void setReco(String[] s){
		this.reco=s;
	}	
/**
 * retorna el campo reco
 *	@return String
 */
	public String[] getReco(){
		return reco;
	}

/**
 * asigna el campo est
 *	@param String s
 */
	public void setEst(String[] s){
		this.est=s;
	}	
/**
 * retorna el campo est
 *	@return String
 */
	public String[] getEst(){
		return est;
	}

	/**
 * asigna el campo motivo
 *	@param String s
 */
	public void setMotivo(String[] s){
		this.motivo=s;
	}	
/**
 * retorna el campo motivo
 *	@return String
 */
	public String[] getMotivo(){
		return motivo;
	}

	/**
 * asigna el campo porcentaje
 *	@param String s
 */
	public void setPorcentaje(String[] s){
		this.porcentaje=s;
	}	
/**
 * retorna el campo porcentaje
 *	@return String
 */
	public String[] getPorcentaje(){
		return porcentaje;
	}
	
		
	/**
	 * Valida el parametro con la lista de notas para establecer el valor por defecto de la vista 
 *	@param String nivel del segundo check
 *	@param String jornada invisible
 */
public String seleccion(String s){
	if(nota!=null){
		for(int i=0;i<nota.length;i++){
			if(nota[i].equals(s)){
				//System.out.println(s);
				return "SELECTED";
			}
		}
	}
	return "";
}

/*
public String seleccionRecuperacion(String s,String n){
  	if(nota!=null){
  		for(int i=0;i<nota.length;i++){
  			if(nota[i].equals(s)){
  				//System.out.println(s);
  				if(!n.equalsIgnoreCase("n")) return "SELECTED";
  				return "";
  			}
  		}
  	}
  	return "";
  }
*/
/**
 * Valida el parametro con la lista de notas para establecer el valor por defecto de la vista 
*	@param String nivel del segundo check
*	@param String jornada invisible
*/
public String seleccionRecuperacion(String s){
if(nota!=null){
	for(int i=0;i<nota.length;i++){
		if(nota[i].equals(s)){
		  if(motivo!=null){
		    System.out.println("si hay motivos");
		    String a=null;
		  	for(int j=0;j<motivo.length;j++){
		  	  System.out.println(""+nota[i]+"="+motivo[j].substring(0,motivo[j].lastIndexOf("|")));  
		  	  if(nota[i].equals(motivo[j].substring(0,motivo[j].lastIndexOf("|")))){
		  	    a=null;
		  	    a=motivo[j].substring(motivo[j].lastIndexOf("|"),motivo[j].length());
		  	    if(a!=null){
		  	       return "SELECTED DISABLED";
		  	    }
		  	  }
		  	}
		  	return "SELECTED";
		  }
		}
	}
}
return "";
}

/**
*	Funcion: Valida el parametro con la lista de descriptores para establecer el valor por defecto de la vista <BR>
*	@param	String s
*	@return	String
**/
public String seleccionDescriptor(String s){
	//System.out.println("buscar "+s);
	for(int i=0;i<(nota!=null?nota.length:0);i++){
		//System.out.println(nota[i].lastIndexOf("|"));
		if(nota[i].lastIndexOf("|")!=-1){			
			if(nota[i].substring(0,nota[i].lastIndexOf("|")).equals(s)){
				//System.out.println("lo encontro");
				return nota[i];
			}
		}
	}
	return "";
}

/**
*	Funcion: Valida el parametro con la lista de evaluciones de preescolar para establecer el valor por defecto de la vista <BR>
*	@param	String s
*	@return	String
**/
public String seleccionPreescolar(String s){
	//System.out.println("dimension "+(nota!=null?nota.length:0)+" buscar "+s);
	String[] a=null;
	for(int i=0;i<(nota!=null?nota.length:0);i++){
		a=nota[i].replace('|',':').split(":");		
		//System.out.println("nota ="+a[1]);
		if(a!=null && a.length>1){			
			if(a[0].equals(s)){
				//System.out.println("lo encontro");
				return a[1];
			}
		}
	}
	return "";
}

/**
*	Funcion: Valida el parametro con la lista de evaluciones de promocion para establecer el valor por defecto de la vista <BR>
*	@param	String s
*	@return	String
**/
public String seleccionPromocion(String s,int n){
	switch(n){
		case 1:
			String[] a=null;
			for(int i=0;i<(nota!=null?nota.length:0);i++){
				if(nota[i].equals(s)){
					return "SELECTED";
				}
			}
		break;
		case 2:
			//System.out.println("longitud de motivo"+motivo.length);
			for(int i=0;i<(motivo!=null?motivo.length:0);i++){
				//System.out.println(":"+motivo[i]);
				a=motivo[i].replace('|',':').split(":");		
				if(a!=null && a.length>1){			
					if(a[0].equals(s)){
						return a[1];
					}
				}
			}
		break;		
	}
	return "";
}

/**
*	Funcion: Valida el parametro con la lista de motivos de ausencia para establecer el valor por defecto de la vista <BR>
*	@param	String s
*	@return	String
**/
public String seleccionDescriptor_(String s){
	//System.out.println("buscar "+s);
	for(int i=0;i<(motivo!=null?motivo.length:0);i++){
		//System.out.println(nota[i].lastIndexOf("|"));
		if(motivo[i].lastIndexOf("|")!=-1){			
			if(motivo[i].substring(0,motivo[i].lastIndexOf("|")).equals(s)){
				//System.out.println("lo encontro");
				return motivo[i].substring(motivo[i].lastIndexOf("|")+1,motivo[i].length());
			}
		}
	}
	return "";
}

/**
*	Funcion: Valida el parametro con la lista de indicadores de evaluacion para establecer el valor por defecto de la vista <BR>
*	@param	String s
*	@return	String
**/
public String seleccion2(String s){
	if(indicador!=null){
		for(int i=0;i<indicador.length;i++){
			if(indicador[i].equals(s)){
				return "SELECTED";
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

	/**
	 * @return Returns the descriptor.
	 */
	public String getDescriptor() {
		return descriptor!=null?descriptor:"";
	}
	/**
	 * @param descriptor The descriptor to set.
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	/**
	 * @return Returns the descriptor_.
	 */
	public String getDescriptor_() {
		return descriptor_!=null?descriptor_:"";
	}
	/**
	 * @param descriptor_ The descriptor_ to set.
	 */
	public void setDescriptor_(String descriptor_) {
		this.descriptor_ = descriptor_;
	}
	/**
	 * @return Returns the periodo2.
	 */
	public String getPeriodo2() {
		return periodo2!=null?periodo2:"";
	}
	/**
	 * @param periodo2 The periodo2 to set.
	 */
	public void setPeriodo2(String periodo2) {
		this.periodo2 = periodo2;
	}
	/**
	 * @return Returns the vigencia.
	 */
	public String getVigencia() {
		return vigencia!=null?vigencia:"";
	}
	/**
	 * @param vigencia The vigencia to set.
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
    public String getPeriodoAbrev() {
        return periodoAbrev!=null?periodoAbrev:"";
    }
    public void setPeriodoAbrev(String periodoAbrev) {
        this.periodoAbrev = periodoAbrev;
    }
	/**
	 * @return Returns the lectura.
	 */
	public String getLectura() {
		return lectura!=null?lectura:"";
	}
	/**
	 * @param lectura The lectura to set.
	 */
	public void setLectura(String lectura) {
		this.lectura = lectura;
	}
	/**
	 * @return Returns the nota2.
	 */
	public String[] getNota2() {
		return nota2;
	}
	/**
	 * @param nota2 The nota2 to set.
	 */
	public void setNota2(String[] nota2) {
		this.nota2 = nota2;
	}
	/**
	 * @return Returns the jerarquiaLogro.
	 */
	public String getJerarquiaLogro() {
		return jerarquiaLogro!=null?jerarquiaLogro:"";
	}
	/**
	 * @param jerarquiaLogro The jerarquiaLogro to set.
	 */
	public void setJerarquiaLogro(String jerarquiaLogro) {
		this.jerarquiaLogro = jerarquiaLogro;
	}
	/**
	 * @return Returns the nivelLogro.
	 */
	public String getNivelLogro() {
		return nivelLogro!=null?nivelLogro:"";
	}
	/**
	 * @param nivelLogro The nivelLogro to set.
	 */
	public void setNivelLogro(String nivelLogro) {
		this.nivelLogro = nivelLogro;
	}
	/**
	 * @return Returns the tipoPlantilla.
	 */
	public String getTipoPlantilla() {
		return tipoPlantilla!=null?tipoPlantilla:"";
	}
	/**
	 * @param tipoPlantilla The tipoPlantilla to set.
	 */
	public void setTipoPlantilla(String tipoPlantilla) {
		this.tipoPlantilla = tipoPlantilla;
	}
	/**
	 * @return Returns the docente.
	 */
	public String getDocente() {
		return docente!=null?docente:"";
	}
	/**
	 * @param docente The docente to set.
	 */
	public void setDocente(String docente) {
		this.docente = docente;
	}
	/**
	 * @return Returns the docente_.
	 */
	public String getDocente_() {
		return docente_!=null?docente_:"";
	}
	/**
	 * @param docente_ The docente_ to set.
	 */
	public void setDocente_(String docente_) {
		this.docente_ = docente_;
	}
	/**
	 * @return Returns the estadoPlantilla.
	 */
	public String getEstadoPlantilla() {
		return estadoPlantilla!=null?estadoPlantilla:"";
	}
	/**
	 * @param estadoPlantilla The estadoPlantilla to set.
	 */
	public void setEstadoPlantilla(String estadoPlantilla) {
		this.estadoPlantilla = estadoPlantilla;
	}
	/**
	 * @return Returns the nombrePlantilla.
	 */
	public String getNombrePlantilla() {
		return nombrePlantilla!=null?nombrePlantilla:"";
	}
	/**
	 * @param nombrePlantilla The nombrePlantilla to set.
	 */
	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
	/**
	 * @return Returns the usuarioPlantilla.
	 */
	public String getUsuarioPlantilla() {
		return usuarioPlantilla!=null?usuarioPlantilla:"";
	}
	/**
	 * @param usuarioPlantilla The usuarioPlantilla to set.
	 */
	public void setUsuarioPlantilla(String usuarioPlantilla) {
		this.usuarioPlantilla = usuarioPlantilla;
	}
	/**
	 * @return Returns the periodo2_.
	 */
	public String getPeriodo2_() {
		return periodo2_!=null?periodo2_:"";
	}
	/**
	 * @param periodo2_ The periodo2_ to set.
	 */
	public void setPeriodo2_(String periodo2_) {
		this.periodo2_ = periodo2_;
	}
	/**
	 * @return Return the filNivelPerfil.
	 */
	public int getFilNivelPerfil() {
		return filNivelPerfil;
	}
	/**
	 * @param filNivelPerfil The filNivelPerfil to set.
	 */
	public void setFilNivelPerfil(int filNivelPerfil) {
		this.filNivelPerfil = filNivelPerfil;
	}
	/**
	 * @return Return the filPlanEstudios.
	 */
	public int getFilPlanEstudios() {
		return filPlanEstudios;
	}
	/**
	 * @param filPlanEstudios The filPlanEstudios to set.
	 */
	public void setFilPlanEstudios(int filPlanEstudios) {
		this.filPlanEstudios = filPlanEstudios;
	}
	public long getFilCodNivelEvalucaion() {
		return filCodNivelEvalucaion;
	}
	public void setFilCodNivelEvalucaion(long filCodNivelEvalucaion) {
		this.filCodNivelEvalucaion = filCodNivelEvalucaion;
	}
	public long getFilCodTipoEval() {
		return filCodTipoEval;
	}
	public void setFilCodTipoEval(long filCodTipoEval) {
		this.filCodTipoEval = filCodTipoEval;
	}
	public int getRangosCompletos() {
		return rangosCompletos;
	}
	public void setRangosCompletos(int rangosCompletos) {
		this.rangosCompletos = rangosCompletos;
	}
	public long getFilCodModoEval() {
		return filCodModoEval;
	}
	public void setFilCodModoEval(long filCodModoEval) {
		this.filCodModoEval = filCodModoEval;
	}
	public long getFilCodTipoEvalPres() {
		return filCodTipoEvalPres;
	}
	public void setFilCodTipoEvalPres(long filCodTipoEvalPres) {
		this.filCodTipoEvalPres = filCodTipoEvalPres;
	}
	public boolean isFilBandEvalArea() {
		return filBandEvalArea;
	}
	public void setFilBandEvalArea(boolean filBandEvalArea) {
		this.filBandEvalArea = filBandEvalArea;
	}
	public String getAsignaturaAbre_() {
		if(this.asignatura_ != null ){
			if(this.asignatura_.length() > 5){
				this.asignaturaAbre_ = this.asignatura_.substring(0,5);
			}else{
				this.asignaturaAbre_ = this.asignatura_;
			}
		}
		return asignaturaAbre_;
	}
	public void setAsignaturaAbre_(String asignaturaAbre_) {
		this.asignaturaAbre_ = asignaturaAbre_;
	}
 
}