package siges.conflicto.beans;

public class ConflictoEscolar implements Cloneable{
  
    private String cesede;
    private String cejornada;
    private String ceperiodo;
    private String cemetodologia;
    private String cesednom;
    private String cejornom;
    private String cepernom;
    private String cejerjorn;
    
    /**
     * @return Devuelve cejerjorn.
     */
    public String getCejerjorn() {
        return (cejerjorn != null) ? cejerjorn : "";
    }
    /**
     * @param cejerjorn El cejerjorn a establecer.
     */
    public void setCejerjorn(String cejerjorn) {
        this.cejerjorn = cejerjorn;
    }
    /**
     * @return Devuelve cejornada.
     */
    public String getCejornada() {
        return (cejornada != null) ? cejornada : "";
    }
    /**
     * @param cejornada El cejornada a establecer.
     */
    public void setCejornada(String cejornada) {
        this.cejornada = cejornada;
    }
    /**
     * @return Devuelve cejornom.
     */
    public String getCejornom() {
        return (cejornom != null) ? cejornom : "";
    }
    /**
     * @param cejornom El cejornom a establecer.
     */
    public void setCejornom(String cejornom) {
        this.cejornom = cejornom;
    }
    /**
     * @return Devuelve cepernom.
     */
    public String getCepernom() {
        return (cepernom != null) ? cepernom : "";
    }
    /**
     * @param cepernom El cepernom a establecer.
     */
    public void setCepernom(String cepernom) {
        this.cepernom = cepernom;
    }
    /**
     * @return Devuelve cesednom.
     */
    public String getCesednom() {
        return (cesednom != null) ? cesednom : "";
    }
    /**
     * @param cesednom El cesednom a establecer.
     */
    public void setCesednom(String cesednom) {
        this.cesednom = cesednom;
    }
    /**
     * @return Devuelve ceperiodo.
     */
    public String getCeperiodo() {
        return (ceperiodo != null) ? ceperiodo : "";
    }
    /**
     * @param ceperiodo El ceperiodo a establecer.
     */
    public void setCeperiodo(String ceperiodo) {
        this.ceperiodo = ceperiodo;
    }
    /**
     * @return Devuelve cesede.
     */
    public String getCesede() {
        return (cesede != null) ? cesede : "";
    }
    /**
     * @param cesede El cesede a establecer.
     */
    public void setCesede(String cesede) {
        this.cesede = cesede;
    }
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
 * @return Return the cemetodologia.
 */
public final String getCemetodologia() {
	return cemetodologia;
}
/**
 * @param cemetodologia The cemetodologia to set.
 */
public final void setCemetodologia(String cemetodologia) {
	this.cemetodologia = cemetodologia;
}
}
