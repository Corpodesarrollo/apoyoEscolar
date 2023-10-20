/**
 * 
 */
package articulacion.artPlantillaFinal.vo.plantilla;

/**
 * 6/12/2007 
 * @author Latined
 * @version 1.2
 */
public class ExcelVO {
	public static int i = 0;

	public static final int tipoPlantilla = i++;

	public static final int institucion = i++;
	public static final int sede = i++;
	public static final int jornada = i++;
	public static final int metodologia = i++;
	public static final int grado = i++;
	public static final int grupo = i++;
	public static final int componente = i++;
	public static final int especialidad = i++;
	public static final int anhoVigencia = i++;
	public static final int perVigencia = i++;
	public static final int semestre = i++;
	public static final int fecha=i++;
	public static final int jerarquiaGrupo=i++;
	public static final int totalAsignaturas=i++;
	public static final int totalEstudiantes=i++;
	//
	public static final int nombreInstitucion = i++;
	public static final int nombreSede = i++;
	public static final int nombreJornada = i++;
	public static final int nombreMetodologia = i++;
	public static final int nombreGrado = i++;
	public static final int nombreGrupo = i++;
	public static final int nombreComponente = i++;
	public static final int nombreEspecialidad = i++;
	public static final int nombreAnhoVigencia = i++;
	public static final int nombrePerVigencia = i++;
	public static final int nombreSemestre = i++;
	
	public static final int asignatura=i++;
	public static final int nombreAsignatura=i++;
	
	public static final int codigo=i++;
	public static final int apellido=i++;
	public static final int nombre=i++;
	public static final int nota=i++;

	public static final int escala=i++;
	public static final int nombreEscala=i++;
	
	public static int[][] plantilla = new int[i][2];
	
	public static final int FIL = 0;

	public static final int COL = 1;

	static public void Cargar() {
		plantilla[tipoPlantilla][FIL] = 0;
		plantilla[tipoPlantilla][COL] = 0;

		plantilla[institucion][FIL] = 0;
		plantilla[institucion][COL] = 1;
		plantilla[sede][FIL] = 0;
		plantilla[sede][COL] = 2;
		plantilla[jornada][FIL] = 0;
		plantilla[jornada][COL] = 3;
		plantilla[metodologia][FIL] = 0;
		plantilla[metodologia][COL] = 4;
		plantilla[grado][FIL] = 0;
		plantilla[grado][COL] = 5;
		plantilla[grupo][FIL] = 0;
		plantilla[grupo][COL] = 6;
		plantilla[componente][FIL] = 0;
		plantilla[componente][COL] = 7;
		plantilla[especialidad][FIL] = 0;
		plantilla[especialidad][COL] = 8;
		plantilla[anhoVigencia][FIL] = 0;
		plantilla[anhoVigencia][COL] = 9;
		plantilla[perVigencia][FIL] = 0;
		plantilla[perVigencia][COL] = 10;
		plantilla[semestre][FIL] = 0;
		plantilla[semestre][COL] = 11;
		plantilla[jerarquiaGrupo][FIL] = 0;
		plantilla[jerarquiaGrupo][COL] = 12;
		plantilla[totalAsignaturas][FIL] = 0;
		plantilla[totalAsignaturas][COL] = 13;
		plantilla[totalEstudiantes][FIL] = 0;
		plantilla[totalEstudiantes][COL] = 14;
		

		plantilla[nombreInstitucion][FIL] = 3;
		plantilla[nombreInstitucion][COL] = 1;
		plantilla[nombreSede][FIL] = 4;
		plantilla[nombreSede][COL] = 1;
		plantilla[nombreJornada][FIL] = 4;
		plantilla[nombreJornada][COL] = 4;
		plantilla[nombreMetodologia][FIL] = 4;
		plantilla[nombreMetodologia][COL] = 8;
		plantilla[nombreGrado][FIL] = 5;
		plantilla[nombreGrado][COL] = 1;
		plantilla[nombreGrupo][FIL] = 5;
		plantilla[nombreGrupo][COL] = 4;
		plantilla[nombreComponente][FIL] = 6;
		plantilla[nombreComponente][COL] = 1;
		plantilla[nombreEspecialidad][FIL] = 6;
		plantilla[nombreEspecialidad][COL] = 4;
		plantilla[nombreAnhoVigencia][FIL] = 7;
		plantilla[nombreAnhoVigencia][COL] = 1;
		plantilla[nombrePerVigencia][FIL] = 7;
		plantilla[nombrePerVigencia][COL] = 1;
		plantilla[nombreSemestre][FIL] = 7;
		plantilla[nombreSemestre][COL] = 4;
		plantilla[fecha][FIL] = 7;
		plantilla[fecha][COL] = 8;
			
		plantilla[asignatura][FIL] = 9;
		plantilla[asignatura][COL] = 3;
		plantilla[nombreAsignatura][FIL] = 10;
		plantilla[nombreAsignatura][COL] = 3;
			
		plantilla[codigo][FIL] = 11;
		plantilla[codigo][COL] = 0;
		plantilla[apellido][FIL] = 11;
		plantilla[apellido][COL] = 1;
		plantilla[nombre][FIL] = 11;
		plantilla[nombre][COL] = 2;
		plantilla[nota][FIL] = 11;
		plantilla[nota][COL] = 3;
	}

	static {
		ExcelVO.Cargar();
	}
}
