package articulacion.plantillaEvaluacion.vo.plantilla;

/**
 * 9/08/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class ExcelVO {

	public static int i = 0;
	//los ocultos
	public static final int anhoVigencia = i++;
	public static final int periodoVigencia = i++;
	public static final int metodologia = i++;
	public static final int bimestre = i++;

	//los visibles
	public static final int anhoVigencia2 = i++;
	public static final int periodoVigencia2 = i++;
	public static final int metodologia2 = i++;
	public static final int bimestre2 = i++;
	
	public static final int porcentahePruebaSola = i++;

	public static final int institucion = i++;

	public static final int sede = i++;

	public static final int jornada = i++;

	public static final int asignatura = i++;

	public static final int fecha = i++;

	public static final int grupo = i++;

	public static final int prueba = i++;

	public static final int docente = i++;

	public static final int porcentaje = i++;

	public static final int estado = i++;

	public static final int parametrosAbrev = i++;

	public static final int parametrosNomb = i++;

	public static final int subPruebas = i++;

	public static final int porcentajeSubpruebas = i++;

	// public static final int subPruebas1=i++;
	// public static final int porcentajeSubpruebas1=i++;

	public static final int demas = i++;

	public static final int autonumerico = i++;

	public static final int numeroId = i++;

	public static final int apellidos = i++;

	public static final int apellidos2 = i++;

	public static final int nombres = i++;

	public static final int nombres2 = i++;

	public static final int nota = i++;

	public static final int porcentajesOculta = i++;

	public static final int ins = i++;

	public static final int sed = i++;

	public static final int jor = i++;

	public static final int asig = i++;

	public static final int grup = i++;

	public static final int pru = i++;

	public static final int llave = i++;

	public static final int error = i++;

	public static int[][] plantilla = new int[i][2];

	/*
	public static int[][] plantilla2 = new int[i][2];
	*/
	public static final int FIL = 0;

	public static final int COL = 1;

	static public void Cargar() {

		plantilla[anhoVigencia][FIL] = 0;
		plantilla[anhoVigencia][COL] = 6;
		plantilla[periodoVigencia][FIL] = 0;
		plantilla[periodoVigencia][COL] = 7;
		plantilla[metodologia][FIL] = 0;
		plantilla[metodologia][COL] = 8;
		plantilla[bimestre][FIL] = 0;
		plantilla[bimestre][COL] = 9;

		plantilla[anhoVigencia2][FIL] = 7;
		plantilla[anhoVigencia2][COL] = 4;
		plantilla[periodoVigencia2][FIL] = 7;
		plantilla[periodoVigencia2][COL] = 6;
		plantilla[bimestre2][FIL] = 5;
		plantilla[bimestre2][COL] = 6;
		
		// estado
		plantilla[estado][FIL] = 0;
		plantilla[estado][COL] = 2;

		plantilla[porcentajesOculta][FIL] = 3;
		plantilla[porcentajesOculta][COL] = 8;
		// Hoja Principal
		plantilla[institucion][FIL] = 3;
		plantilla[institucion][COL] = 0;

		plantilla[sede][FIL] = 4;
		plantilla[sede][COL] = 1;

		plantilla[jornada][FIL] = 5;
		plantilla[jornada][COL] = 1;

		plantilla[asignatura][FIL] = 6;
		plantilla[asignatura][COL] = 1;

		plantilla[docente][FIL] = 7;
		plantilla[docente][COL] = 1;

		plantilla[fecha][FIL] = 4;
		plantilla[fecha][COL] = 4;

		plantilla[grupo][FIL] = 5;
		plantilla[grupo][COL] = 4;

		plantilla[prueba][FIL] = 6;
		plantilla[prueba][COL] = 4;

		plantilla[porcentaje][FIL] = 6;
		plantilla[porcentaje][COL] = 6;

		plantilla[subPruebas][FIL] = 8;
		plantilla[subPruebas][COL] = 3;

		plantilla[porcentajeSubpruebas][FIL] = 9;
		plantilla[porcentajeSubpruebas][COL] = 3;

		// plantilla[subPruebas1][FIL]=8;
		// plantilla[subPruebas1][COL]=4;
		//             
		// plantilla2[porcentajeSubpruebas1][FIL]=10;
		// plantilla2[porcentajeSubpruebas1][COL]=3;

		plantilla[demas][FIL] = 10;
		plantilla[demas][COL] = 3;
		// Hoja Principal Datos
		plantilla[autonumerico][FIL] = 10;
		plantilla[autonumerico][COL] = 0;

		plantilla[apellidos][FIL] = 10;
		plantilla[apellidos][COL] = 1;

		/*
		 * plantilla[apellidos2][FIL]=10; plantilla[apellidos2][COL]=2;
		 */

		plantilla[nombres][FIL] = 10;
		plantilla[nombres][COL] = 2;

		/*
		 * plantilla[nombres2][FIL]=10; plantilla[nombres2][COL]=4;
		 */

		plantilla[nota][FIL] = 10;
		plantilla[nota][COL] = 3;

		// Hoja oculta

		plantilla[ins][FIL] = 0;
		plantilla[ins][COL] = 1;

		plantilla[sed][FIL] = 0;
		plantilla[sed][COL] = 2;

		plantilla[jor][FIL] = 0;
		plantilla[jor][COL] = 3;

		plantilla[asig][FIL] = 0;
		plantilla[asig][COL] = 4;

		plantilla[grup][FIL] = 0;
		plantilla[grup][COL] = 5;

		plantilla[pru][FIL] = 1;
		plantilla[pru][COL] = 0;

		// Parametros

		plantilla[parametrosAbrev][FIL] = 8;
		plantilla[parametrosAbrev][COL] = 0;

		plantilla[parametrosNomb][FIL] = 8;
		plantilla[parametrosNomb][COL] = 1;

		plantilla[error][FIL] = 10;
		plantilla[error][COL] = 8;
	}

	static {
		ExcelVO.Cargar();
	}
}
