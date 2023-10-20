package siges.conflictoCerrar.beans;

import java.io.Serializable;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ConflictoCerrar implements Serializable, Cloneable{
    
    private String sede;
    private String jornada;
    private String metodologia;
    private String grado;
    private String grupo;
    private String periodo;
    private String estper;
    private String jergrado;
    
    /**
     * @return Devuelve estper.
     */
    public String getEstper() {
        return (estper != null) ? estper : "";
    }
    /**
     * @param estper El estper a establecer.
     */
    public void setEstper(String estper) {
        this.estper = estper;
    }
    /**
     * @return Devuelve jergrado.
     */
    public String getJergrado() {
        return (jergrado != null) ? jergrado : "";
    }
    /**
     * @param jergrado El jergrado a establecer.
     */
    public void setJergrado(String jergrado) {
        this.jergrado = jergrado;
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
   	*	Hace una copia del objeto mismo
   	*	@return Object 
   	*/
    public Object clone() {
        Object o = null;
        try{
            o = super.clone();
        }
        catch(CloneNotSupportedException e) {
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
