/**
 * 
 */
package siges.integracion.beans;

/**
 * 7/08/2007 
 * @author Latined
 * @version 1.2
 */
public class Estudiante {
	private long id;
	private long codigo;
	private int vigencia;
	private int tipoId;
	private String numId;
	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private long daneInst;
	private long sede;
	private long jornada;
	private long grado;
	private long grupo;
	private int metodologia;
	private int oldMetodologia;
	private String nomGrupo;
	private long ddExp;
	private long dmExp;
	private long ddNaci;
	private long dmNaci;
	private int genero;
	private int estado;
	private String fechaNaci;
	private long oldDaneInst;
	private long oldSede;
	private long oldJornada;
	private long oldGrado;
	private long oldGrupo;
	private int oldTipoId;
	private String oldNumId;
	
	private int categoria=Params.C_ESTUDIANTE;
	private int funcion;
	
	/**
	 * @return Return the apellido1.
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 The apellido1 to set.
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 * @return Return the apellido2.
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * @param apellido2 The apellido2 to set.
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return Return the daneInst.
	 */
	public long getDaneInst() {
		return daneInst;
	}
	/**
	 * @param daneInst The daneInst to set.
	 */
	public void setDaneInst(long daneInst) {
		this.daneInst = daneInst;
	}
	/**
	 * @return Return the ddExp.
	 */
	public long getDdExp() {
		return ddExp;
	}
	/**
	 * @param ddExp The ddExp to set.
	 */
	public void setDdExp(long ddExp) {
		this.ddExp = ddExp;
	}
	/**
	 * @return Return the ddNaci.
	 */
	public long getDdNaci() {
		return ddNaci;
	}
	/**
	 * @param ddNaci The ddNaci to set.
	 */
	public void setDdNaci(long ddNaci) {
		this.ddNaci = ddNaci;
	}
	/**
	 * @return Return the dmExp.
	 */
	public long getDmExp() {
		return dmExp;
	}
	/**
	 * @param dmExp The dmExp to set.
	 */
	public void setDmExp(long dmExp) {
		this.dmExp = dmExp;
	}
	/**
	 * @return Return the dmNaci.
	 */
	public long getDmNaci() {
		return dmNaci;
	}
	/**
	 * @param dmNaci The dmNaci to set.
	 */
	public void setDmNaci(long dmNaci) {
		this.dmNaci = dmNaci;
	}
	/**
	 * @return Return the estado.
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * @return Return the fechaNaci.
	 */
	public String getFechaNaci() {
		return fechaNaci;
	}
	/**
	 * @param fechaNaci The fechaNaci to set.
	 */
	public void setFechaNaci(String fechaNaci) {
		this.fechaNaci = fechaNaci;
	}
	/**
	 * @return Return the genero.
	 */
	public int getGenero() {
		return genero;
	}
	/**
	 * @param genero The genero to set.
	 */
	public void setGenero(int genero) {
		this.genero = genero;
	}
	/**
	 * @return Return the grado.
	 */
	public long getGrado() {
		return grado;
	}
	/**
	 * @param grado The grado to set.
	 */
	public void setGrado(long grado) {
		this.grado = grado;
	}
	/**
	 * @return Return the grupo.
	 */
	public long getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo The grupo to set.
	 */
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return Return the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Return the jornada.
	 */
	public long getJornada() {
		return jornada;
	}
	/**
	 * @param jornada The jornada to set.
	 */
	public void setJornada(long jornada) {
		this.jornada = jornada;
	}
	/**
	 * @return Return the nombre1.
	 */
	public String getNombre1() {
		return nombre1;
	}
	/**
	 * @param nombre1 The nombre1 to set.
	 */
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	/**
	 * @return Return the nombre2.
	 */
	public String getNombre2() {
		return nombre2;
	}
	/**
	 * @param nombre2 The nombre2 to set.
	 */
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	/**
	 * @return Return the nomGrupo.
	 */
	public String getNomGrupo() {
		return nomGrupo;
	}
	/**
	 * @param nomGrupo The nomGrupo to set.
	 */
	public void setNomGrupo(String nomGrupo) {
		this.nomGrupo = nomGrupo;
	}
	/**
	 * @return Return the numId.
	 */
	public String getNumId() {
		return numId;
	}
	/**
	 * @param numId The numId to set.
	 */
	public void setNumId(String numId) {
		this.numId = numId;
	}
	/**
	 * @return Return the sede.
	 */
	public long getSede() {
		return sede;
	}
	/**
	 * @param sede The sede to set.
	 */
	public void setSede(long sede) {
		this.sede = sede;
	}
	/**
	 * @return Return the tipoId.
	 */
	public int getTipoId() {
		return tipoId;
	}
	/**
	 * @param tipoId The tipoId to set.
	 */
	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
	}
	/**
	 * @return Return the oldDaneInst.
	 */
	public long getOldDaneInst() {
		return oldDaneInst;
	}
	/**
	 * @param oldDaneInst The oldDaneInst to set.
	 */
	public void setOldDaneInst(long oldDaneInst) {
		this.oldDaneInst = oldDaneInst;
	}
	/**
	 * @return Return the oldGrado.
	 */
	public long getOldGrado() {
		return oldGrado;
	}
	/**
	 * @param oldGrado The oldGrado to set.
	 */
	public void setOldGrado(long oldGrado) {
		this.oldGrado = oldGrado;
	}
	/**
	 * @return Return the oldGrupo.
	 */
	public long getOldGrupo() {
		return oldGrupo;
	}
	/**
	 * @param oldGrupo The oldGrupo to set.
	 */
	public void setOldGrupo(long oldGrupo) {
		this.oldGrupo = oldGrupo;
	}
	/**
	 * @return Return the oldJornada.
	 */
	public long getOldJornada() {
		return oldJornada;
	}
	/**
	 * @param oldJornada The oldJornada to set.
	 */
	public void setOldJornada(long oldJornada) {
		this.oldJornada = oldJornada;
	}
	/**
	 * @return Return the oldSede.
	 */
	public long getOldSede() {
		return oldSede;
	}
	/**
	 * @param oldSede The oldSede to set.
	 */
	public void setOldSede(long oldSede) {
		this.oldSede = oldSede;
	}
	/**
	 * @return Return the vigencia.
	 */
	public int getVigencia() {
		return vigencia;
	}
	/**
	 * @param vigencia The vigencia to set.
	 */
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}
	/**
	 * @return Return the metodologia.
	 */
	public int getMetodologia() {
		return metodologia;
	}
	/**
	 * @param metodologia The metodologia to set.
	 */
	public void setMetodologia(int metodologia) {
		this.metodologia = metodologia;
	}
	/**
	 * @return Return the oldMetodologia.
	 */
	public int getOldMetodologia() {
		return oldMetodologia;
	}
	/**
	 * @param oldMetodologia The oldMetodologia to set.
	 */
	public void setOldMetodologia(int oldMetodologia) {
		this.oldMetodologia = oldMetodologia;
	}
	/**
	 * @return Return the oldNumId.
	 */
	public String getOldNumId() {
		return oldNumId;
	}
	/**
	 * @param oldNumId The oldNumId to set.
	 */
	public void setOldNumId(String oldNumId) {
		this.oldNumId = oldNumId;
	}
	/**
	 * @return Return the oldTipoId.
	 */
	public int getOldTipoId() {
		return oldTipoId;
	}
	/**
	 * @param oldTipoId The oldTipoId to set.
	 */
	public void setOldTipoId(int oldTipoId) {
		this.oldTipoId = oldTipoId;
	}
	/**
	 * @return Return the codigo.
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo The codigo to set.
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return Return the categoria.
	 */
	public final int getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria The categoria to set.
	 */
	public final void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return Return the funcion.
	 */
	public final int getFuncion() {
		return funcion;
	}
	/**
	 * @param funcion The funcion to set.
	 */
	public final void setFuncion(int funcion) {
		this.funcion = funcion;
	}
}
