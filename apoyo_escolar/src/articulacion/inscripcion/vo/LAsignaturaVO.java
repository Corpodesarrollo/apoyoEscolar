package articulacion.inscripcion.vo;

import java.util.List;

public class LAsignaturaVO {
	private List listaDia;
	private String identificacion;
	private long codigo;
	private String nombre;
	private long codigoGrupo;
	private String grupo;
	private String docente;
	private String creditos;
	private int cupo;
	private int filas;
	private String checked;
	private int cupoGeneral;
	private int nivelado;
	private int cupoNivelado;
	private int cupoNoNivelado;
	private boolean cupoLibre;
	private String descripcion;
	private String nombreEstado;
	private int estado;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getFilas() {
		return listaDia!=null?listaDia.size():1;
	}
	public void setFilas(int filas) {
		this.filas = filas;
	}
	public String getCreditos() {
		return creditos;
	}
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public List getListaDia() {
		return listaDia;
	}
	public void setListaDia(List listaDia) {
		this.listaDia = listaDia;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public long getCodigoGrupo() {
		return codigoGrupo;
	}
	public void setCodigoGrupo(long codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return Returns the checked.
	 */
	public String getChecked() {
		return checked;
	}
	/**
	 * @param checked The checked to set.
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public boolean isCupoLibre() {
		return cupoLibre;
	}
	public void setCupoLibre(boolean cupoLibre) {
		this.cupoLibre = cupoLibre;
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	/**
	 * @return Return the cupo.
	 */
	public int getCupo() {
		return cupo;
	}
	/**
	 * @param cupo The cupo to set.
	 */
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}
	/**
	 * @return Return the cupoGeneral.
	 */
	public int getCupoGeneral() {
		return cupoGeneral;
	}
	/**
	 * @param cupoGeneral The cupoGeneral to set.
	 */
	public void setCupoGeneral(int cupoGeneral) {
		this.cupoGeneral = cupoGeneral;
	}
	/**
	 * @return Return the cupoNivelado.
	 */
	public int getCupoNivelado() {
		return cupoNivelado;
	}
	/**
	 * @param cupoNivelado The cupoNivelado to set.
	 */
	public void setCupoNivelado(int cupoNivelado) {
		this.cupoNivelado = cupoNivelado;
	}
	/**
	 * @return Return the cupoNoNivelado.
	 */
	public int getCupoNoNivelado() {
		return cupoNoNivelado;
	}
	/**
	 * @param cupoNoNivelado The cupoNoNivelado to set.
	 */
	public void setCupoNoNivelado(int cupoNoNivelado) {
		this.cupoNoNivelado = cupoNoNivelado;
	}
	/**
	 * @return Return the nivelado.
	 */
	public int getNivelado() {
		return nivelado;
	}
	/**
	 * @param nivelado The nivelado to set.
	 */
	public void setNivelado(int nivelado) {
		this.nivelado = nivelado;
	}
}
