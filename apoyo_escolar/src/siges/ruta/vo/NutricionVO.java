package siges.ruta.vo;

public class NutricionVO implements Cloneable {

	private String [] nutEstudiante;
	private String [] nutEstatura;
	private String [] nutPeso;
	private String [] nutJerarquia;
	
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
	 * @return Returns the nutEstatura.
	 */
	public String[] getNutEstatura() {
		return nutEstatura;
	}

	/**
	 * @param nutEstatura The nutEstatura to set.
	 */
	public void setNutEstatura(String[] nutEstatura) {
		this.nutEstatura = nutEstatura;
	}

	/**
	 * @return Returns the nutEstudiante.
	 */
	public String[] getNutEstudiante() {
		return nutEstudiante;
	}

	/**
	 * @param nutEstudiante The nutEstudiante to set.
	 */
	public void setNutEstudiante(String[] nutEstudiante) {
		this.nutEstudiante = nutEstudiante;
	}

	/**
	 * @return Returns the nutPeso.
	 */
	public String[] getNutPeso() {
		return nutPeso;
	}

	/**
	 * @param nutPeso The nutPeso to set.
	 */
	public void setNutPeso(String[] nutPeso) {
		this.nutPeso = nutPeso;
	}

	public String[] getNutJerarquia() {
		return nutJerarquia;
	}

	public void setNutJerarquia(String[] nutJerarquia) {
		this.nutJerarquia = nutJerarquia;
	}
}
