package siges.admincursos.beans;

import java.util.ResourceBundle;

/**
 * @author Administrador
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class Reporte {

	public Reporte() {
	}

	public static int i = 0;
	public static final int Usuario = i++;
	public static final int Fecha = i++;
	public static final int PRIMERAPELLIDO = i++;
	public static final int SEGUNDOAPELLIDO = i++;
	public static final int PRIMERNOMBRE = i++;
	public static final int SEGUNDONOMBRE = i++;
	public static final int TIPODOCUM = i++;
	public static final int NUMDOCUM = i++;
	public static final int EDAD = i++;
	public static final int DIRECCION = i++;
	public static final int TELEFONO = i++;
	public static final int CORREOPER = i++;
	public static final int CORREOINS = i++;
	public static final int LOCALIDAD = i++;
	public static final int CODIGODANE = i++;
	public static final int NOMBRECOLEGIO = i++;
	public static final int NOMBRESEDE = i++;
	public static final int NOMBREJORNADA = i++;
	public static final int TIEMPOSERVICIO = i++;
	public static final int ESCALAFON = i++;
	public static final int G0 = i++;
	public static final int G1 = i++;
	public static final int G2 = i++;
	public static final int G3 = i++;
	public static final int G4 = i++;
	public static final int G5 = i++;
	public static final int G6 = i++;
	public static final int G7 = i++;
	public static final int G8 = i++;
	public static final int G9 = i++;
	public static final int G10 = i++;
	public static final int G11 = i++;
	public static final int NORMALISTA = i++;
	public static final int BACHILLER = i++;
	public static final int TECNICO = i++;
	public static final int TITTECNICO = i++;
	public static final int LICENCIATURA = i++;
	public static final int TITLICENCIATURA = i++;
	public static final int OTRALICENCIATURA = i++;
	public static final int TITOTRALICENCIATURA = i++;
	public static final int MAESTRIA = i++;
	public static final int ESPECIALIZACION = i++;
	public static final int DOCTORADO = i++;
	public static final int NIVEL = i++;
	public static final int AREADESEMP = i++;
	public static final int PROYECTO1 = i++;
	public static final int PROYECTO2 = i++;
	public static final int PROYECTO3 = i++;
	public static final int FECHAINSCRIPCION = i++;
	public static final int NOMBRECURSO = i++;
	public static final int JORNADACURSO = i++;

	public static final int FIL = 0;
	public static final int COL = 1;

	public static String[] propiedades = { "rep.usuario", "rep.fecha",
			"rep.primerapellido", "rep.segundoapellido", "rep.primernombre",
			"rep.segundonombre", "rep.tipodocum", "rep.numdocum", "rep.edad",
			"rep.direccion", "rep.telefono", "rep.correoper", "rep.correoins",
			"rep.localidad", "rep.codigodane", "rep.nombrecolegio",
			"rep.nombresede", "rep.nombrejornada", "rep.tiemposervicio",
			"rep.escalafon", "rep.grado0", "rep.grado1", "rep.grado2",
			"rep.grado3", "rep.grado4", "rep.grado5", "rep.grado6",
			"rep.grado7", "rep.grado8", "rep.grado9", "rep.grado10",
			"rep.grado11", "rep.normalista", "rep.bachiller", "rep.tecnico",
			"rep.tittecnico", "rep.licenciatura", "rep.titlicenciatura",
			"rep.otralicenciatura", "rep.titotralicenciatura", "rep.maestria",
			"rep.especializacion", "rep.doctorado", "rep.nivel",
			"rep.areadesempeno", "rep.proyecto1", "rep.proyecto2",
			"rep.proyecto3", "rep.fechainscripcion", "rep.nombrecurso",
			"rep.jornadacurso", };

	public static int[][] Rep = new int[propiedades.length][2];

	static public void Cargar() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("admincursos");
			String[] p;
			for (int i = 0; i < propiedades.length; i++) {
				p = rb.getString(propiedades[i]).replace('.', ':').split(":");
				Rep[i][FIL] = Integer.parseInt(p[FIL]);
				Rep[i][COL] = Integer.parseInt(p[COL]);
			}
		} catch (Exception e) {
			System.out
					.println("-SIGES- Error Cargando Bean de Reporte de cursos:"
							+ e);
		}
	}

	static {
		Cargar();
	}

}
