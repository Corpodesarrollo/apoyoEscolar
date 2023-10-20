/**
 * 
 */
package siges.gestionAdministrativa.repCarnes.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroRepCarnesVO  {

	private long consec;
	
	private long vig=-99;
	private long loc=-99;
	private long zona=-99;
	private long inst=-99;
	private long sede=-99;
	private long jornd=-99;
	private long metodo=-99;
	private long grado=-99;
	private long grupo=-99;
	private long escala=-99;
	private long tipoCol=-99;
	
	
	private String vig_nombrenombre;
	private String loc_nombre;
	private String zona_nombre;
	private String inst_nombre;
	private String sede_nombre;
	private String jornd_nombre;
	private String metodo_nombre;
	private String grado_nombre;
	private String grupo_nombre;
	private String escala_nombre;
	private String fecha;
	private String usuario;
	private String rutaBase;
	
	private String nombre_zip;
	private String nombre_pdf;
	private String nombre_xls;
	
	private String grupoNom;
	private long periodo=-99;
	private String asigCodigos;
	private String asigNombres;
	private long orden=-99;
	private long numPer;
	private String nomPerFinal;
	private int tipoReporte=-99;
	private int ordenReporte=-99;
	private int nivelEval;
	private int tipoEscala;
	
	private int estado;
	
	private double valorIni;
	private double valorFin;
	
	private int hab_loc=1;
	private int hab_inst=1;
	private int hab_sede=1;
	
	private String convInts;
	private String convMen;
	
	private String fechaGen;
	private String fechaFin;
	
	private int numAsig;
	
	public long getVig() {
		return vig;
	}
	public void setVig(long vig) {
		this.vig = vig;
	}
	public long getLoc() {
		return loc;
	}
	public void setLoc(long loc) {
		this.loc = loc;
	}
	public long getZona() {
		return zona;
	}
	public void setZona(long zona) {
		this.zona = zona;
	}
	public long getInst() {
		return inst;
	}
	public void setInst(long inst) {
		this.inst = inst;
	}
	public long getSede() {
		return sede;
	}
	public void setSede(long sede) {
		this.sede = sede;
	}
	public long getJornd() {
		return jornd;
	}
	public void setJornd(long jornd) {
		this.jornd = jornd;
	}
	public long getMetodo() {
		return metodo;
	}
	public void setMetodo(long metodo) {
		this.metodo = metodo;
	}
	public long getGrado() {
		return grado;
	}
	public void setGrado(long grado) {
		this.grado = grado;
	}
	public long getGrupo() {
		return grupo;
	}
	public void setGrupo(long grupo) {
		this.grupo = grupo;
	}
	public String getGrupoNom() {
		return grupoNom;
	}
	public void setGrupoNom(String grupoNom) {
		this.grupoNom = grupoNom;
	}
	public long getPeriodo() {
		return periodo;
	}
	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}
	public String getAsigCodigos() {
		return asigCodigos;
	}
	public void setAsigCodigos(String asigCodigos) {
		this.asigCodigos = asigCodigos;
	}
	public String getAsigNombres() {
		return asigNombres;
	}
	public void setAsigNombres(String asigNombres) {
		this.asigNombres = asigNombres;
	}
	public long getOrden() {
		return orden;
	}
	public void setOrden(long orden) {
		this.orden = orden;
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
	public int getHab_loc() {
		return hab_loc;
	}
	public void setHab_loc(int habLoc) {
		hab_loc = habLoc;
	}
	public int getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public int getHab_inst() {
		return hab_inst;
	}
	public void setHab_inst(int habInst) {
		hab_inst = habInst;
	}
	public int getHab_sede() {
		return hab_sede;
	}
	public void setHab_sede(int habSede) {
		hab_sede = habSede;
	}
	public int getOrdenReporte() {
		return ordenReporte;
	}
	public void setOrdenReporte(int ordenReporte) {
		this.ordenReporte = ordenReporte;
	}
	public int getNivelEval() {
		return nivelEval;
	}
	public void setNivelEval(int nivelEval) {
		this.nivelEval = nivelEval;
	}
	public String getVig_nombrenombre() {
		return vig_nombrenombre;
	}
	public void setVig_nombrenombre(String vigNombrenombre) {
		vig_nombrenombre = vigNombrenombre;
	}
	public String getLoc_nombre() {
		return loc_nombre;
	}
	public void setLoc_nombre(String locNombre) {
		loc_nombre = locNombre;
	}
	public String getZona_nombre() {
		return zona_nombre;
	}
	public void setZona_nombre(String zonaNombre) {
		zona_nombre = zonaNombre;
	}
	public String getInst_nombre() {
		return inst_nombre;
	}
	public void setInst_nombre(String instNombre) {
		inst_nombre = instNombre;
	}
	public String getSede_nombre() {
		return sede_nombre;
	}
	public void setSede_nombre(String sedeNombre) {
		sede_nombre = sedeNombre;
	}
	public String getJornd_nombre() {
		return jornd_nombre;
	}
	public void setJornd_nombre(String jorndNombre) {
		jornd_nombre = jorndNombre;
	}
	public String getMetodo_nombre() {
		return metodo_nombre;
	}
	public void setMetodo_nombre(String metodoNombre) {
		metodo_nombre = metodoNombre;
	}
	public String getGrado_nombre() {
		return grado_nombre;
	}
	public void setGrado_nombre(String gradoNombre) {
		grado_nombre = gradoNombre;
	}
	public String getGrupo_nombre() {
		return grupo_nombre;
	}
	public void setGrupo_nombre(String grupoNombre) {
		grupo_nombre = grupoNombre;
	}
	public double getValorIni() {
		return valorIni;
	}
	public void setValorIni(double valorIni) {
		this.valorIni = valorIni;
	}
	public double getValorFin() {
		return valorFin;
	}
	public void setValorFin(double valorFin) {
		this.valorFin = valorFin;
	}
	public long getEscala() {
		return escala;
	}
	public void setEscala(long escala) {
		this.escala = escala;
	}
	public String getEscala_nombre() {
		return escala_nombre;
	}
	public void setEscala_nombre(String escalaNombre) {
		escala_nombre = escalaNombre;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombre_zip() {
		return nombre_zip;
	}
	public void setNombre_zip(String nombreZip) {
		nombre_zip = nombreZip;
	}
	public String getNombre_pdf() {
		return nombre_pdf;
	}
	public void setNombre_pdf(String nombrePdf) {
		nombre_pdf = nombrePdf;
	}
	public String getNombre_xls() {
		return nombre_xls;
	}
	public void setNombre_xls(String nombreXls) {
		nombre_xls = nombreXls;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public long getConsec() {
		return consec;
	}
	public void setConsec(long consec) {
		this.consec = consec;
	}
	public String getConvInts() {
		return convInts;
	}
	public void setConvInts(String convInts) {
		this.convInts = convInts;
	}
	public String getConvMen() {
		return convMen;
	}
	public void setConvMen(String convMen) {
		this.convMen = convMen;
	}
	public String getFechaGen() {
		return fechaGen;
	}
	public void setFechaGen(String fechaGen) {
		this.fechaGen = fechaGen;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getTipoEscala() {
		return tipoEscala;
	}
	public void setTipoEscala(int tipoEscala) {
		this.tipoEscala = tipoEscala;
	}
	public final int getNumAsig() {
		return numAsig;
	}
	public final void setNumAsig(int numAsig) {
		this.numAsig = numAsig;
	}
	public final String getRutaBase() {
		return rutaBase;
	}
	public final void setRutaBase(String rutaBase) {
		this.rutaBase = rutaBase;
	}
	public long getTipoCol() {
		return tipoCol;
	}
	public void setTipoCol(long tipoCol) {
		this.tipoCol = tipoCol;
	}

}