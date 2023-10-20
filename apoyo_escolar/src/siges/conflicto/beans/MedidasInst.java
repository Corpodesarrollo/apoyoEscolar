package siges.conflicto.beans;

public class MedidasInst implements Cloneable{
  
    private String[][] medtexto;

    /**
     * @return Devuelve medtexto.
     */
    public String[][] getMedtexto() {
        return medtexto;
    }
    /**
     * @param medtexto El medtexto a establecer.
     */
    public void setMedtexto(String[][] medtexto) {
        this.medtexto = medtexto;
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