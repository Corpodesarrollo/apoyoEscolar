/**
 * 
 */
package siges.gestionAdministrativa.RepPuestoEstudiante.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroReportesVO  {

	private long consec;
	
	private long vig=-9;
	private long prov=-9;
	private long loc=-9;
	private long zona=-9;
	private long inst=-9;
	private long sede=-9;
	private long jornd=-9;
	private long metodo=-9;
	private long grado=-9;
	private long grupo=-9;
	private long escala=-9;
	private long asig=-9;
	
	
	private String vig_nombrenombre;
	private String prov_nombre;
	private String loc_nombre;
	private String zona_nombre;
	private String inst_nombre;
	private String sede_nombre;
	private String jornd_nombre;
	private String metodo_nombre;
	private String grado_nombre;
	private String grupo_nombre;
	private String escala_nombre;
	private String per_nombre;
	private String fecha;
	private String usuario;
	private String docente;
	
	private String nombre_zip;
	private String nombre_pdf;
	private String nombre_xls;
	
	private String grupoNom;
	private long periodo=-9;
	private String asigCodigos;
	private String asigNombres;
	private long orden=-9;
	private long numPer;
	private String nomPerFinal;
	private int tipoReporte=-9;
	private int ordenReporte=-9;
	private int nivelEval;
	private int tipoEscala;
	private String areNombre;
	private String resolInst;
	private String fechaReporte;
	
	private int criterioEval;
	
	private String instCodigos;
	private String gradosCodigos;
	
	private String instNombres="Todos";
	private String gradosNombres="Todos";
	
	private int estado;
	
	private int conAreAsi;//CRITERIO PARA REPORTE POR AREAS O ASIGNATURAS
	
	private int conGraGru;
	
	private double valorIni;
	private double valorFin;
	
	private String rango;
	private int hab_loc=1;
	private int hab_inst=1;
	private int hab_sede=1;
	
	private String convInts;
	private String convMen;
	
	private String fechaGen;
	private String fechaFin;
	
	private String fechaProm;
	private int fechaPromValida;
	
	
	private int numAsig;
	
	private int dacompromediar;
	
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
	public  int getNumAsig() {
		return numAsig;
	}
	public  void setNumAsig(int numAsig) {
		this.numAsig = numAsig;
	}
	public  long getProv() {
		return prov;
	}
	public  void setProv(long prov) {
		this.prov = prov;
	}
	public  int getConAreAsi() {
		return conAreAsi;
	}
	public  void setConAreAsi(int conAreAsi) {
		this.conAreAsi = conAreAsi;
	}
	public final String getRango() {
		return rango;
	}
	public final void setRango(String rango) {
		this.rango = rango;
	}
	public String getProv_nombre() {
		return prov_nombre;
	}
	public void setProv_nombre(String provNombre) {
		prov_nombre = provNombre;
	}
	public String getPer_nombre() {
		return per_nombre;
	}
	public void setPer_nombre(String perNombre) {
		per_nombre = perNombre;
	}
	public int getConGraGru() {
		return conGraGru;
	}
	public void setConGraGru(int conGraGru) {
		this.conGraGru = conGraGru;
	}
	public String getFechaProm() {
		return fechaProm;
	}
	public void setFechaProm(String fechaProm) {
		this.fechaProm = fechaProm;
	}
	public int getDacompromediar() {
		return dacompromediar;
	}
	public void setDacompromediar(int dacompromediar) {
		this.dacompromediar = dacompromediar;
	}
	public int getFechaPromValida() {
		return fechaPromValida;
	}
	public void setFechaPromValida(int fechaPromValida) {
		this.fechaPromValida = fechaPromValida;
	}
	/**
	 * @return the docente
	 */
	public String getDocente() {
		return docente;
	}
	/**
	 * @param docente the docente to set
	 */
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public long getAsig() {
		return asig;
	}
	public void setAsig(long asig) {
		this.asig = asig;
	}
	public String getAreNombre() {
		return areNombre;
	}
	public void setAreNombre(String areNombre) {
		this.areNombre = areNombre;
	}
	public String getResolInst() {
		return resolInst;
	}
	public void setResolInst(String resolInst) {
		this.resolInst = resolInst;
	}
	public String getFechaReporte() {
		return fechaReporte;
	}
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	public int getCriterioEval() {
		return criterioEval;
	}
	public void setCriterioEval(int criterioEval) {
		this.criterioEval = criterioEval;
	}
	public String getInstCodigos() {
		return instCodigos;
	}
	public void setInstCodigos(String instCodigos) {
		this.instCodigos = instCodigos;
	}
	public String getGradosCodigos() {
		return gradosCodigos;
	}
	public void setGradosCodigos(String gradosCodigos) {
		this.gradosCodigos = gradosCodigos;
	}
	public String getInstNombres() {
		return instNombres;
	}
	public void setInstNombres(String instNombres) {
		this.instNombres = instNombres;
	}
	public String getGradosNombres() {
		return gradosNombres;
	}
	public void setGradosNombres(String gradosNombres) {
		this.gradosNombres = gradosNombres;
	}

}