package siges.conflictoGrupo.beans;

public class ConflictoFiltro implements Cloneable{
  
    private String docente;
    private String sede;
    private String jornada;
    private String metodologia;
    private String grado;
    private String grupo;
    private String periodo;
    private String jergrupo;
    private String cupo;
    
    /**
     * @return Devuelve cupo.
     */
    public String getCupo() {
        return (cupo != null) ? cupo : "";
    }
    /**
     * @param cupo El cupo a establecer.
     */
    public void setCupo(String cupo) {
        this.cupo = cupo;
    }
    /**
     * @return Devuelve docente.
     */
    public String getDocente() {
        return (docente != null) ? docente : "";
    }
    /**
     * @param docente El docente a establecer.
     */
    public void setDocente(String docente) {
        this.docente = docente;
    }
    /**
     * @return Devuelve grado.
     */
    public String getGrado() {
        return (grado != null) ? grado : "";
    }
    /**
     * @param grado El grado a establecer.
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }
    /**
     * @return Devuelve grupo.
     */
    public String getGrupo() {
        return (grupo != null) ? grupo : "";
    }
    /**
     * @param grupo El grupo a establecer.
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    /**
     * @return Devuelve jornada.
     */
    public String getJornada() {
        return (jornada != null) ? jornada : "";
    }
    /**
     * @param jornada El jornada a establecer.
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }
    /**
     * @return Devuelve periodo.
     */
    public String getPeriodo() {
        return (periodo != null) ? periodo : "";
    }
    /**
     * @param periodo El periodo a establecer.
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    /**
     * @return Devuelve sede.
     */
    public String getSede() {
        return (sede != null) ? sede : "";
    }
    /**
     * @param sede El sede a establecer.
     */
    public void setSede(String sede) {
        this.sede = sede;
    }
    
    /**
     * @return Devuelve jergrupo.
     */
    public String getJergrupo() {
        return (jergrupo != null) ? jergrupo : "";
    }
    /**
     * @param jergrupo El jergrupo a establecer.
     */
    public void setJergrupo(String jergrupo) {
        this.jergrupo = jergrupo;
    }
    /**
     * @return Devuelve numalumnos.
     */

    /**
     *	Hace una copia del objeto mismo
     *	@return Object 
     */
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException e) {
            System.err.println("MyObject can't clone");
        }
        return o;
    }
	/**
	 * @return Return the metodologia.
	 */
	public final String getMetodologia() {
		return metodologia;
	}
	/**
	 * @param metodologia The metodologia to set.
	 */
	public final void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
}
