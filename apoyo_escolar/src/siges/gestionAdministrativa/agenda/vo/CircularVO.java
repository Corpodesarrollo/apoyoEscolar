package siges.gestionAdministrativa.agenda.vo;


import siges.common.vo.Vo;

/**
 * Objeto de tipo circular.
 * AGE_CIRCULAR
 * @author Camaleon
 *
 */
public class CircularVO extends Vo{
	
	private int estado;
	private int nivel;
	private int codjerar;
	private long codigo;
	private String asunto;
	private String descripcion;
	private String fechaPublicacion;
	private String responsable;
	private String archivo;
	private long usuario;
	private String fechaRegistro;
	private String dato;
	
	private int nivelUsuario;
	
	/**
	 * Getters and Setters
	 * @return
	 */
	public int getEstado() {
		return estado;
	}
	public int getNivelUsuario() {
		return nivelUsuario;
	}
	public void setNivelUsuario(int nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getDato() {
		return dato;
	}
	public void setDato(String dato) {
		this.dato = dato;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getCodjerar() {
		return codjerar;
	}
	public void setCodjerar(int codjerar) {
		this.codjerar = codjerar;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public long getUsuario() {
		return usuario;
	}
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
}
