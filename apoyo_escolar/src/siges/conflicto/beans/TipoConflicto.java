package siges.conflicto.beans;

public class TipoConflicto implements Cloneable{
  
    private String[][] tipoconflicto;

    /**
     * @return Devuelve tipoconflicto.
     */
    public String[][] getTipoconflicto() {
        return tipoconflicto;
    }
    /**
     * @param tipoconflicto El tipoconflicto a establecer.
     */
    public void setTipoconflicto(String[][] tipoconflicto) {
        this.tipoconflicto = tipoconflicto;
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
