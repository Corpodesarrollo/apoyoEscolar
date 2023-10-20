/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class ReporteVO  {
	
	private String fecha;
	private String usuario;
	private String recurso;
	private String tipo;
	private int tipoReporte;
	private String nombre;
	private String nombre_xls;
	private String nombre_zip;
	private String nombre_pdf;
	private String mensaje;
	private int estado;
	private int modulo;
	private long consec;
	private int puesto;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getRecurso() {
		return recurso;
	}
	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getModulo() {
		return modulo;
	}
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
	public String getNombre_pdf() {
		return nombre_pdf;
	}
	public void setNombre_pdf(String nombre_pdf) {
		this.nombre_pdf = nombre_pdf;
	}
	public String getNombre_xls() {
		return nombre_xls;
	}
	public void setNombre_xls(String nombre_xls) {
		this.nombre_xls = nombre_xls;
	}
	public String getNombre_zip() {
		return nombre_zip;
	}
	public void setNombre_zip(String nombre_zip) {
		this.nombre_zip = nombre_zip;
	}
	public int getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public long getConsec() {
		return consec;
	}
	public void setConsec(long consec) {
		this.consec = consec;
	}
	public int getPuesto() {
		return puesto;
	}
	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}	

}