package siges.conflicto.beans;

public class InfluenciaConflictos implements Cloneable{
  
    private String[][] inftexto;

    /**
     * @return Devuelve inftexto.
     */
    public String[][] getInftexto() {
        return inftexto;
    }
    /**
     * @param inftexto El inftexto a establecer.
     */
    public void setInftexto(String[][] inftexto) {
        this.inftexto = inftexto;
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
