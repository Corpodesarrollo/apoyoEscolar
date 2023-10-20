package siges.conflicto.beans;

public class ResolucionConflictos implements Cloneable{
  
    private String[][] resconflicto;
    private String[] resotro;

    /**
     * @return Devuelve resotro.
     */
    public String[] getResotro() {
        return resotro;
    }
    /**
     * @param resotro El resotro a establecer.
     */
    public void setResotro(String[] resotro) {
        this.resotro = resotro;
    }
    /**
     * @return Devuelve resconflicto.
     */
    public String[][] getResconflicto() {
        return resconflicto;
    }
    /**
     * @param resconflicto El resconflicto a establecer.
     */
    public void setResconflicto(String[][] resconflicto) {
        this.resconflicto = resconflicto;
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
