/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.vo;

import siges.common.vo.FiltroCommonVO;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class FiltroRepEstadisticoVO extends FiltroCommonVO  {
   
	
	private int tipoReporte;
    private String fecha;
    private String nombre_zip;
    private String nombre_xls;
    private String nombre_pdf;
    private String usuario;
    private int vig;
    private int nivelEval;
    private int zona;
    private String asigCodigos;
    private String asigNombres;
    private long periodo;
    private double valorIni;
    private double valorFin;
    private int escala;
    private String escalaNombre;
    private long ordenReporte;
    private int numPer;
    private String nomPerFinal; 
    private   long filest;
    private   String filestNom;
    private   String filestNumDoc;
    private   int filestTipoDoc;
    
    
	public String getAsigNombres() {
		return asigNombres;
	}

	public void setAsigNombres(String asigNombres) {
		this.asigNombres = asigNombres;
	}

	public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

 

	public String getNomPerFinal() {
		return nomPerFinal;
	}

	public void setNomPerFinal(String nomPerFinal) {
		this.nomPerFinal = nomPerFinal;
	}

	public int getNumPer() {
		return numPer;
	}

	public void setNumPer(int numPer) {
		this.numPer = numPer;
	}

	public long getOrdenReporte() {
		return ordenReporte;
	}

	public void setOrdenReporte(long ordenReporte) {
		this.ordenReporte = ordenReporte;
	}

	public long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}

	public double getValorFin() {
		return valorFin;
	}

	public void setValorFin(double valorFin) {
		this.valorFin = valorFin;
	}

	public double getValorIni() {
		return valorIni;
	}

	public void setValorIni(double valorIni) {
		this.valorIni = valorIni;
	}

	public int getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getNivelEval() {
		return nivelEval;
	}

	public void setNivelEval(int nivelEval) {
		this.nivelEval = nivelEval;
	}

	public int getVig() {
		return vig;
	}

	public void setVig(int vig) {
		this.vig = vig;
	}

    public int getZona() {
		return zona;
	}

	public void setZona(int zona) {
		this.zona = zona;
	}

	public String getAsigCodigos() {
		return asigCodigos;
	}

	public void setAsigCodigos(String asigCodigos) {
		this.asigCodigos = asigCodigos;
	}

	public String getEscalaNombre() {
		return escalaNombre;
	}

	public void setEscalaNombre(String escalaNombre) {
		this.escalaNombre = escalaNombre;
	}

	public long getFilest() {
		return filest;
	}

	public void setFilest(long filest) {
		this.filest = filest;
	}

	public String getFilestNom() {
		return filestNom;
	}

	public void setFilestNom(String filestNom) {
		this.filestNom = filestNom;
	}

	public String getFilestNumDoc() {
		return filestNumDoc;
	}

	public void setFilestNumDoc(String filestNumDoc) {
		this.filestNumDoc = filestNumDoc;
	}

	public int getFilestTipoDoc() {
		return filestTipoDoc;
	}

	public void setFilestTipoDoc(int filestTipoDoc) {
		this.filestTipoDoc = filestTipoDoc;
	} 
}