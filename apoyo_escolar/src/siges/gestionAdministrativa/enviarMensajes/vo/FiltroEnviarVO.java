/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.vo;

import java.util.ArrayList;
import java.util.List;

import siges.common.vo.FiltroCommonVO;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroEnviarVO extends FiltroCommonVO  {

	private String fecha1;
	private String fecha2;
	
	private String filFechaDesde;
	private String filFechaHasta;
	private long filperfil;
	private long filenviadopor;
	
	
	private long codigo;
	private String asunto;
	private String fecha;
	private String formaEstado;
	private String fechaIni;
	private String fechaFin;
	private String contenido;
	private int enviado;
	private String perfiles;
	private String localidades;
	private String colegios;
	private String sedes;
	private String jornadas;
	
	private List listaPerfiles = new ArrayList();
	private List listaLocalidades = new ArrayList();
	private List listaColegios = new ArrayList();
	private List listaSedes = new ArrayList();
	private List listaJornadas = new ArrayList();
	
	private int estado;
	
	private int hab_loc=1;
	private int hab_inst=1;
	
	private long loc;
	private long inst;
	
	private String usuario;







	public String getFecha1() {
		return fecha1;
	}







	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}







	public String getFecha2() {
		return fecha2;
	}







	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
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







	public String getFecha() {
		return fecha;
	}







	public void setFecha(String fecha) {
		this.fecha = fecha;
	}







	public String getFechaIni() {
		return fechaIni;
	}







	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}







	public String getFechaFin() {
		return fechaFin;
	}







	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}







	public String getContenido() {
		return contenido;
	}







	public void setContenido(String contenido) {
		this.contenido = contenido;
	}







	public int getEnviado() {
		return enviado;
	}







	public void setEnviado(int enviado) {
		this.enviado = enviado;
	}







	public String getPerfiles() {
		return perfiles;
	}







	public void setPerfiles(String perfiles) {
		this.perfiles = perfiles;
	}







	public String getLocalidades() {
		return localidades;
	}







	public void setLocalidades(String localidades) {
		this.localidades = localidades;
	}







	public String getColegios() {
		return colegios;
	}







	public void setColegios(String colegios) {
		this.colegios = colegios;
	}







	public String getSedes() {
		return sedes;
	}







	public void setSedes(String sedes) {
		this.sedes = sedes;
	}







	public String getJornadas() {
		return jornadas;
	}







	public void setJornadas(String jornadas) {
		this.jornadas = jornadas;
	}







	public List getListaPerfiles() {
		return listaPerfiles;
	}







	public void setListaPerfiles(List listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}







	public List getListaLocalidades() {
		return listaLocalidades;
	}







	public void setListaLocalidades(List listaLocalidades) {
		this.listaLocalidades = listaLocalidades;
	}







	public List getListaColegios() {
		return listaColegios;
	}







	public void setListaColegios(List listaColegios) {
		this.listaColegios = listaColegios;
	}







	public List getListaSedes() {
		return listaSedes;
	}







	public void setListaSedes(List listaSedes) {
		this.listaSedes = listaSedes;
	}







	public List getListaJornadas() {
		return listaJornadas;
	}







	public void setListaJornadas(List listaJornadas) {
		this.listaJornadas = listaJornadas;
	}







	public int getEstado() {
		return estado;
	}







	public void setEstado(int estado) {
		this.estado = estado;
	}







	public int getHab_loc() {
		return hab_loc;
	}







	public void setHab_loc(int habLoc) {
		hab_loc = habLoc;
	}







	public int getHab_inst() {
		return hab_inst;
	}







	public void setHab_inst(int habInst) {
		hab_inst = habInst;
	}







	public long getLoc() {
		return loc;
	}







	public void setLoc(long loc) {
		this.loc = loc;
	}







	public long getInst() {
		return inst;
	}







	public void setInst(long inst) {
		this.inst = inst;
	}







	public String getUsuario() {
		return usuario;
	}







	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}







	public String getFormaEstado() {
		return formaEstado;
	}







	public void setFormaEstado(String formaEstado) {
		this.formaEstado = formaEstado;
	}







	public String getFilFechaDesde() {
		return filFechaDesde;
	}







	public void setFilFechaDesde(String filFechaDesde) {
		this.filFechaDesde = filFechaDesde;
	}







	public String getFilFechaHasta() {
		return filFechaHasta;
	}







	public void setFilFechaHasta(String filFechaHasta) {
		this.filFechaHasta = filFechaHasta;
	}







	public long getFilperfil() {
		return filperfil;
	}







	public void setFilperfil(long filperfil) {
		this.filperfil = filperfil;
	}







	public long getFilenviadopor() {
		return filenviadopor;
	}







	public void setFilenviadopor(long filenviadopor) {
		this.filenviadopor = filenviadopor;
	}







 
	

}

