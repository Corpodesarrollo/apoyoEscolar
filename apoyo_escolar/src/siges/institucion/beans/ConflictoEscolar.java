package siges.institucion.beans;

public class ConflictoEscolar implements Cloneable{
  
    private String cesede;
    private String ceperiodo;
    private String cesednom;
    private String cepernom;
    
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
}
