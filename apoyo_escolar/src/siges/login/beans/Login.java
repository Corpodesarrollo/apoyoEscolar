package siges.login.beans;

/**
*	Nombre:	Login
*	Descripcion:	bean para subir datos de la persona Logeada 
*	Fecha de modificacinn:	
*	@author Pasantes UD
*	@version $v 1.2 $
*/
public class Login implements Cloneable{
	private String depId;
	private String dep;
	private String munId;
	private String mun;
	private String locId;
	private String loc;
	private String instId;
	private String inst;
	private String usuarioJerar;
	private String usuarioId;
	private String usuario;
	private String perfil;
	private String nomPerfil;
	private String tipo;
	private String sedeId;
	private String sede;
	private String jornadaId;
	private String jornada;
	private String nivel;
	private String metodologiaId;
	private String metodologia;
	private String gradoId;
	private String grado;
	private String grupoId;
	private String grupo;
	private String codigoEst;
	private String artEspId;
	private String artSemId;
	private String artGrupoId;
	
	//MODIFICACION ADICION VARIABLES NUEVAS DE PARAMETROS INSTITUCION DE PERIODOS
	private long logNumPer;
	private long logTipoPer;
	private String logNomPerDef;
	private long logNivelEval;
	private long logTipoEval;
	private String logSubTitBol;
	private long vigencia_inst;
	
	private long vigencia_actual;
	
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
 * @return Return the artEspId.
 */
public String getArtEspId() {
	return artEspId;
}

/**
 * @return Return the artGrupoId.
 */
public String getArtGrupoId() {
	return artGrupoId;
}
/**
 * @return Return the artSemId.
 */
public String getArtSemId() {
	return artSemId;
}
/**
 * @return Returns the codigoEst.
 */
public String getCodigoEst() {
	return codigoEst;
}
/**
 * retorna el campo dep
 *	@return String
 */
	public String getDep(){
		return dep !=null ? dep : "";
	}
/**
 * retorna el campo depId
 *	@return String
 */
	public String getDepId(){
		return depId !=null ? depId : "";
	}
/**
 * @return Returns the grado.
 */
public String getGrado() {
	return grado;
}
/**
 * @return Returns the gradoId.
 */
public String getGradoId() {
	return gradoId;
}
/**
 * @return Returns the grupo.
 */
public String getGrupo() {
	return grupo;
}

/**
 * @return Returns the grupoId.
 */
public String getGrupoId() {
	return grupoId;
}	
/**
 * retorna el campo institucion
 *	@return String
 */
	public String getInst(){
		return inst !=null ? inst : "";
	}

/**
 * retorna el campo institucionid
 *	@return String
 */
	public String getInstId(){
		return instId !=null ? instId : "";
	}	
/**
 * retorna el campo jornada
 *	@return String
 */
	public String getJornada(){
		return jornada !=null ? jornada : "";
	}

/**
 * retorna el campo jornadaId
 *	@return String
 */
	public String getJornadaId(){
		return jornadaId !=null ? jornadaId : "";
	}	
/**
 * retorna el campo localidad 
 *	@return String
 */
	public String getLoc(){
		return loc !=null ? loc : "";
	}

/**
 * retorna el campo localidadId 
 *	@return String
 */
	public String getLocId(){
		return locId !=null ? locId : "";
	}	
public long getLogNivelEval() {
	return logNivelEval;
}

public String getLogNomPerDef() {
	return logNomPerDef;
}	
public long getLogNumPer() {
	return logNumPer;
}

public String getLogSubTitBol() {
	return logSubTitBol;
}	
public long getLogTipoEval() {
	return logTipoEval;
}

public long getLogTipoPer() {
	return logTipoPer;
}	
/**
 * retorna el campo metodologia
 *	@return String
*/
	public String getMetodologia(){
		return metodologia !=null ? metodologia : "";
	}
/**
 * retorna el campo metodologiaId
 *	@return String
 */
	public String getMetodologiaId(){
		return metodologiaId !=null ? metodologiaId : "";
	}	
/**
 * retorna el campo mun
 *	@return String
 */
	public String getMun(){
		return mun !=null ? mun : "";
	}
/**
 * retorna el campo munId
 *	@return String
 */
	public String getMunId(){
		return munId !=null ? munId : "";
	}
/**
 * retorna el campo nivel
 *	@return String
 */
	public String getNivel(){
		return nivel !=null ? nivel : "";
	}
/**
 * @return Return the nomPerfil.
 */
public String getNomPerfil() {
	return nomPerfil;
}
/**
 * retorna el campo perfil
 *	@return String
 */
	public String getPerfil(){
		return perfil !=null ? perfil : "";
	}
/**
 * retorna el campo sede
 *	@return String
 */
	public String getSede(){
		return sede !=null ? sede : "";
	}
/**
 * retorna el campo sedeId
 *	@return String
 */
	public String getSedeId(){
		return sedeId !=null ? sedeId : "";
	}

/**
 * retorna el campo tipo
 *	@return String
 */
	public String getTipo(){
		return tipo !=null ? tipo : "";
	}
/**
 * retorna el campo usuario
 *	@return String
 */
	public String getUsuario(){
		return usuario !=null ? usuario : "";
	}
/**
 * retorna el campo usuario
 *	@return String
 */
	public String getUsuarioId(){
		return usuarioId !=null ? usuarioId : "";
	}
/**
 * retorna el campo usuarioJerar
 *	@return String
 */
	public String getUsuarioJerar(){
		return usuarioJerar !=null ? usuarioJerar : "";
	}
	
public long getVigencia_actual() {
	return vigencia_actual;
}
	
/**
 * @return the vigencia_inst
 */
public long getVigencia_inst() {
	return vigencia_inst;
}

/**
 * @param artEspId The artEspId to set.
 */
public void setArtEspId(String artEspId) {
	this.artEspId = artEspId;
}
/**
 * @param artGrupoId The artGrupoId to set.
 */
public void setArtGrupoId(String artGrupoId) {
	this.artGrupoId = artGrupoId;
}

	
	/**
	 * @param artSemId The artSemId to set.
	 */
	public void setArtSemId(String artSemId) {
		this.artSemId = artSemId;
	}
	/**
	 * @param codigoEst The codigoEst to set.
	 */
	public void setCodigoEst(String codigoEst) {
		this.codigoEst = codigoEst;
	}
	/**
	 * asigna el campo dep
	 *	@param String s
	 */
		public void setDep(String s){
			this.dep=s;
		}
	/**
	 * asigna el campo depId
	 *	@param String s
	 */
		public void setDepId(String s){
			this.depId=s;
		}
	/**
	 * @param grado The grado to set.
	 */
	public void setGrado(String grado) {
		this.grado = grado;
	}
	/**
	 * @param gradoId The gradoId to set.
	 */
	public void setGradoId(String gradoId) {
		this.gradoId = gradoId;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	/**
	 * @param grupoId The grupoId to set.
	 */
	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}
	/**
	 * asigna el campo institucion
	 *	@param String s
	 */
		public void setInst(String s){
			this.inst=s;
		}
	/**
	 * asigna el campo institucionid
	 *	@param String s
	 */
		public void setInstId(String s){
			this.instId=s;
		}
	/**
	 * asigna el campo jornada
	 *	@param String s
	 */
		public void setJornada(String s){
			this.jornada=s;
		}
	/**
	 * asigna el campo jornadaId
	 *	@param String s
	 */
		public void setJornadaId(String s){
			this.jornadaId=s;
		}
	/**
	 * asigna el campo localidad
	 *	@param String s
	 */
		public void setLoc(String s){
			this.loc=s;
		}
	/**
	 * asigna el campo localidadId
	 *	@param String s
	 */
		public void setLocId(String s){
			this.locId=s;
		}
	public void setLogNivelEval(long logNivelEval) {
		this.logNivelEval = logNivelEval;
	}
	public void setLogNomPerDef(String logNomPerDef) {
		this.logNomPerDef = logNomPerDef;
	}
	public void setLogNumPer(long logNumPer) {
		this.logNumPer = logNumPer;
	}
	public void setLogSubTitBol(String logSubTitBol) {
		this.logSubTitBol = logSubTitBol;
	}
	public void setLogTipoEval(long logTipoEval) {
		this.logTipoEval = logTipoEval;
	}
	
	
	public void setLogTipoPer(long logTipoPer) {
		this.logTipoPer = logTipoPer;
	}
	/**
	 * asigna el campo metodologia
	 *	@param String s
	 */
		public void setMetodologia(String s){
			this.metodologia=s;
		}
	/**
	 * asigna el campo metodologiaId
	 *	@param String s
	 */
		public void setMetodologiaId(String s){
			this.metodologiaId=s;
		}
	/**
	 * asigna el campo mun
	 *	@param String s
	 */
		public void setMun(String s){
			this.mun=s;
		}
	/**
	 * asigna el campo munId
	 *	@param String s
	 */
		public void setMunId(String s){
			this.munId=s;
		}
	/**
	 * asigna el campo nivel
	 *	@param String s
	 */
		public void setNivel(String s){
			this.nivel=s;
		}
	/**
	 * @param nomPerfil The nomPerfil to set.
	 */
	public void setNomPerfil(String nomPerfil) {
		this.nomPerfil = nomPerfil;
	}
	/**
	 * asigna el campo perfil
	 *	@param String s
	 */
		public void setPerfil(String s){
			this.perfil=s;
		}
	/**
	 * asigna el campo sede
	 *	@param String s
	 */
		public void setSede(String s){
			this.sede=s;
		}
	/**
	 * asigna el campo sedeId
	 *	@param String s
	 */
		public void setSedeId(String s){
			this.sedeId=s;
		}
	/**
	 * asigna el campo tipo
	 *	@param String s
	 */
		public void setTipo(String s){
			this.tipo=s;
		}
	/**
	 * asigna el campo usuario
	 *	@param String s
	 */
		public void setUsuario(String s){
			this.usuario=s;
		}
	/**
	 * asigna el campo usuario
	 *	@param String s
	 */
		public void setUsuarioId(String s){
			this.usuarioId=s;
		}
	/**
	 * asigna el campo usuarioJerar
	 *	@param String s
	 */
		public void setUsuarioJerar(String s){
			this.usuarioJerar=s;
		}
	
	public void setVigencia_actual(long vigencia_actual) {
		this.vigencia_actual = vigencia_actual;
	}
	/**
	 * @param vigenciaInst the vigencia_inst to set
	 */
	public void setVigencia_inst(long vigenciaInst) {
		vigencia_inst = vigenciaInst;
	}
}