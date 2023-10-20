package siges.conflictoGrupo.beans;

public class ConflictoGrupo implements Cloneable{
  
    private String[][] numalumnos;
    private String[][] numeventos;
    private String[][] genero;
    private String cupo;

    /**
     * @return Devuelve numeventos.
     */
    public String[][] getNumeventos() {
        return numeventos;
    }
    /**
     * @param numeventos El numeventos a establecer.
     */
    public void setNumeventos(String[][] numeventos) {
        this.numeventos = numeventos;
    }
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
     * @return Devuelve numalumnos.
     */
    public String[][] getNumalumnos() {
        return numalumnos;
    }
    /**
     * @param numalumnos El numalumnos a establecer.
     */
    public void setNumalumnos(String[][] numalumnos) {
        this.numalumnos = numalumnos;
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
	 * @return Return the genero.
	 */
	public String[][] getGenero() {
		return genero;
	}
	/**
	 * @param genero The genero to set.
	 */
	public void setGenero(String[][] genero) {
		this.genero = genero;
	}
}
