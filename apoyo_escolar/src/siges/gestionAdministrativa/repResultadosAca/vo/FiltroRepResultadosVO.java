/**
 * 
 */
package siges.gestionAdministrativa.repResultadosAca.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroRepResultadosVO  {

	private long consec;
	
	private long vig=-99;
	private long loc=-99;
	private long inst=-99;	
	private long tipoCol=-99;
	
	
	private String vig_nombrenombre;
	private String loc_nombre;	
	private String inst_nombre;
	private String fecha;
	private String usuario;
	private String rutaBase;
	
	private String nombre_zip;
	private String nombre_pdf;
	private String nombre_xls;
	
	
	private long periodo=-99;
	private long orden=-99;
	private long numPer;
	private String nomPerFinal;
	private int tipoReporte=-99;
	private int ordenReporte=-99;
	private int nivelEval;
	private int tipoEscala;
	private int tipoPer;
	
	private int estado;
	
	private int hab_loc=1;
	private int hab_inst=1;
	private int hab_sede=1;
	
	private String fechaGen;
	private String fechaFin;
	
	private boolean todas;
	
	public boolean isTodas() {
		return todas;
	}
	public void setTodas(boolean todas) {
		this.todas = todas;
	}
	public long getConsec() {
		return consec;
	}
	public void setConsec(long consec) {
		this.consec = consec;
	}
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
	public long getInst() {
		return inst;
	}
	public void setInst(long inst) {
		this.inst = inst;
	}
	public long getTipoCol() {
		return tipoCol;
	}
	public void setTipoCol(long tipoCol) {
		this.tipoCol = tipoCol;
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
	public String getInst_nombre() {
		return inst_nombre;
	}
	public void setInst_nombre(String instNombre) {
		inst_nombre = instNombre;
	}
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
	public String getRutaBase() {
		return rutaBase;
	}
	public void setRutaBase(String rutaBase) {
		this.rutaBase = rutaBase;
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
	public long getPeriodo() {
		return periodo;
	}
	public void setPeriodo(long periodo) {
		this.periodo = periodo;
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
	public int getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
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
	public int getTipoEscala() {
		return tipoEscala;
	}
	public void setTipoEscala(int tipoEscala) {
		this.tipoEscala = tipoEscala;
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
	public int getHab_sede() {
		return hab_sede;
	}
	public void setHab_sede(int habSede) {
		hab_sede = habSede;
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
	public int getTipoPer() {
		return tipoPer;
	}
	public void setTipoPer(int tipoPer) {
		this.tipoPer = tipoPer;
	}
	
		
}