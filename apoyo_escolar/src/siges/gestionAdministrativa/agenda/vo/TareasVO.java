package siges.gestionAdministrativa.agenda.vo;

import java.util.List;

import siges.common.vo.Vo;

public class TareasVO extends Vo{
	
	private String metodologia;
	private String grado;
	private String grupo;
	private String asignaturas;
	private long codigo = 0;
	private String nombre;
	private String descripcion;
	private String fechaPublicacion;
	private String fechaEntrega;
	private String observaciones;
	private String usuario;
	private int estado;
	private List listDoc;
	
	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(String asignaturas) {
		this.asignaturas = asignaturas;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public List getListDoc() {
		return listDoc;
	}

	public void setListDoc(List listDoc) {
		this.listDoc = listDoc;
	}

	
}
