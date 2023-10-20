package siges.adminGrupo.vo;

import siges.common.vo.Vo;

/**
 * Value Object para el manejo de parametros del modulo
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class GrupoVO  extends Vo{
	
	private long codInst;
	private long codMetodo;	
	private long codSede;
	private long codJorn;
	private long codGrado;
	private long codJerGrado;
	private long codEspacio;
	private long codTipoEspacio;
	private long codigo;	
	private String docDirector;
	private String nombre;	 
	private long orden;
	private int cupo;	
	private long codJerGrupo;
	private int insertar;
	
	private String nombreDirector;
	private String nombreGrado;
	
	public long getCodInst() {
		return codInst;
	}
	public void setCodInst(long codInst) {
		this.codInst = codInst;
	}
	public long getCodMetodo() {
		return codMetodo;
	}
	public void setCodMetodo(long codMetodo) {
		this.codMetodo = codMetodo;
	}
	public long getCodSede() {
		return codSede;
	}
	public void setCodSede(long codSede) {
		this.codSede = codSede;
	}
	public long getCodJorn() {
		return codJorn;
	}
	public void setCodJorn(long codJorn) {
		this.codJorn = codJorn;
	}
	public long getCodGrado() {
		return codGrado;
	}
	public void setCodGrado(long codGrado) {
		this.codGrado = codGrado;
	}
	public long getCodJerGrado() {
		return codJerGrado;
	}
	public void setCodJerGrado(long codJerGrado) {
		this.codJerGrado = codJerGrado;
	}
	public long getCodEspacio() {
		return codEspacio;
	}
	public void setCodEspacio(long codEspacio) {
		this.codEspacio = codEspacio;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDocDirector() {
		return docDirector;
	}
	public void setDocDirector(String docDirector) {
		this.docDirector = docDirector;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getOrden() {
		return orden;
	}
	public void setOrden(long orden) {
		this.orden = orden;
	}
	public int getCupo() {
		return cupo;
	}
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}
	public long getCodJerGrupo() {
		return codJerGrupo;
	}
	public void setCodJerGrupo(long codJerGrupo) {
		this.codJerGrupo = codJerGrupo;
	}
	public int getInsertar() {
		return insertar;
	}
	public void setInsertar(int insertar) {
		this.insertar = insertar;
	}
	public String getNombreDirector() {
		return nombreDirector;
	}
	public void setNombreDirector(String nombreDirector) {
		this.nombreDirector = nombreDirector;
	}
	public String getNombreGrado() {
		return nombreGrado;
	}
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}
	public long getCodTipoEspacio() {
		return codTipoEspacio;
	}
	public void setCodTipoEspacio(long codTipoEspacio) {
		this.codTipoEspacio = codTipoEspacio;
	}	
 
 }
