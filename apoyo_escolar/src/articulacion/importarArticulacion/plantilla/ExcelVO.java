package articulacion.importarArticulacion.plantilla;

/**
 * 9/08/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class ExcelVO {
	public static int i = 0;

	public static final int tipoDocumento = i++;

	public static final int numeroId = i++;

	public static final int apellidos = i++;

	public static final int apellidos2 = i++;

	public static final int nombres = i++;

	public static final int nombres2 = i++;

	public static final int institucion = i++;

	public static final int grado = i++;

	public static final int sede = i++;

	public static final int jornada = i++;

	public static final int fecha = i++;

	public static final int llave = i++;

	public static final int grupo = i++;

	public static final int grupo_ = i++;

	public static final int esp = i++;

	public static final int sem = i++;

	public static final int gru = i++;

	public static final int nivelado = i++;

	public static final int ins = i++;

	public static final int sed = i++;

	public static final int grad = i++;

	public static final int jor = i++;

	public static final int idEspecialidad = i++;

	public static final int especialidad = i++;

	public static int[][] plantilla = new int[i][2];

	public static int[][] plantilla2 = new int[i][2];

	public static final int FIL = 0;

	public static final int COL = 1;

	static public void Cargar() {
		plantilla[tipoDocumento][FIL] = 7;
		plantilla[tipoDocumento][COL] = 0;

		plantilla[numeroId][FIL] = 7;
		plantilla[numeroId][COL] = 1;

		plantilla[apellidos][FIL] = 7;
		plantilla[apellidos][COL] = 2;

		plantilla[apellidos2][FIL] = 7;
		plantilla[apellidos2][COL] = 3;

		plantilla[nombres][FIL] = 7;
		plantilla[nombres][COL] = 4;

		plantilla[nombres2][FIL] = 7;
		plantilla[nombres2][COL] = 5;

		plantilla[institucion][FIL] = 1;
		plantilla[institucion][COL] = 1;

		plantilla[sede][FIL] = 2;
		plantilla[sede][COL] = 1;

		plantilla[jornada][FIL] = 2;
		plantilla[jornada][COL] = 4;

		plantilla[grado][FIL] = 3;
		plantilla[grado][COL] = 1;

		plantilla[grupo][FIL] = 1;
		plantilla[grupo][COL] = 4;

		plantilla[fecha][FIL] = 2;
		plantilla[fecha][COL] = 8;

		plantilla[nivelado][FIL] = 7;
		plantilla[nivelado][COL] = 8;

		plantilla[esp][FIL] = 7;
		plantilla[esp][COL] = 6;

		plantilla[sem][FIL] = 7;
		plantilla[sem][COL] = 7;

		plantilla[gru][FIL] = 7;
		plantilla[gru][COL] = 8;

		// Especialidades Hoja 1
		plantilla2[idEspecialidad][FIL] = 19;
		plantilla2[idEspecialidad][COL] = 0;

		plantilla2[especialidad][FIL] = 19;
		plantilla2[especialidad][COL] = 1;

		plantilla2[llave][FIL] = 0;
		plantilla2[llave][COL] = 0;

		// hoja oculta

		plantilla[ins][FIL] = 0;
		plantilla[ins][COL] = 1;

		plantilla[sed][FIL] = 0;
		plantilla[sed][COL] = 2;

		plantilla[grad][FIL] = 0;
		plantilla[grad][COL] = 3;

		plantilla[jor][FIL] = 0;
		plantilla[jor][COL] = 4;

		plantilla[grupo_][FIL] = 0;
		plantilla[grupo_][COL] = 5;

	}

	static {
		ExcelVO.Cargar();
	}
}
