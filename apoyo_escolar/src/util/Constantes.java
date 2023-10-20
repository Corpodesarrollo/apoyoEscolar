package util;

public class Constantes {
	
	/**
	 * Constructor privado
	 */
	private Constantes(){
		
	}
	
	/**
	 * True: Indica que se va a trabajar con ISIS
	 * False: La autenticacinn y seguridad la maneja directamente APOYO_ESCOLAR 
	 */
	private static boolean isis = false;

	public static boolean isIsis() {
		return isis;
	}

	public static void setIsis(boolean isis) {
		Constantes.isis = isis;
	}
	
	

}
