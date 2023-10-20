package siges.ruta.vo;

public class GestacionVO  implements Cloneable {

	private String [] gesEstudiante;
	private String [] gesGestante;
	private String [] gesGenero;
	private String [] gesLactante;
	private String [] gesPadre;
	private String [] gesJerarquia;
	
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

	public String[] getGesEstudiante() {
		return gesEstudiante;
	}

	public void setGesEstudiante(String[] gesEstudiante) {
		this.gesEstudiante = gesEstudiante;
	}

	public String[] getGesGestante() {
		return gesGestante;
	}

	public void setGesGestante(String[] gesGestante) {
		this.gesGestante = gesGestante;
	}

	public String[] getGesJerarquia() {
		return gesJerarquia;
	}

	public void setGesJerarquia(String[] gesJerarquia) {
		this.gesJerarquia = gesJerarquia;
	}

	public String[] getGesLactante() {
		return gesLactante;
	}

	public void setGesLactante(String[] gesLactante) {
		this.gesLactante = gesLactante;
	}

	public String[] getGesPadre() {
		return gesPadre;
	}

	public void setGesPadre(String[] gesPadre) {
		this.gesPadre = gesPadre;
	}

	public String[] getGesGenero() {
		return gesGenero;
	}

	public void setGesGenero(String[] gesGenero) {
		this.gesGenero = gesGenero;
	}
}
