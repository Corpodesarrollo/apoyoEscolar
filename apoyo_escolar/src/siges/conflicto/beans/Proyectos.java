package siges.conflicto.beans;

public class Proyectos implements Cloneable{
  
    private String[][] proyvalor;

    /**
     * @return Devuelve proyvalor.
     */
    public String[][] getProyvalor() {
        return proyvalor;
    }
    /**
     * @param proyvalor El proyvalor a establecer.
     */
    public void setProyvalor(String[][] proyvalor) {
        this.proyvalor = proyvalor;
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
