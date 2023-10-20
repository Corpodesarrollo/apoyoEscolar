package articulacion.importarEvaluacion.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.dao.Cursor;
import siges.importar.dao.ImportarDAO;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import articulacion.plantillaEvaluacion.vo.datosPruebasVO;
import siges.util.Logger;
import siges.util.Properties;
import siges.util.Recursos;
import siges.util.beans.Area;
import siges.util.beans.Asignatura;
import siges.util.beans.BateriaDescriptor;
import siges.util.beans.BateriaLogro;
import siges.util.beans.Preescolar;

import articulacion.plantillaEvaluacion.vo.plantilla.ExcelVO;
import articulacion.importarEvaluacion.dao.ImportarArticulacionDAO;
import articulacion.importarEvaluacion.objetos.DatosVO;
import articulacion.importarEvaluacion.objetos.EspecialidadesVO;
import articulacion.importarEvaluacion.objetos.EstudianteVO;
import articulacion.importarEvaluacion.objetos.LimitesVO;
import articulacion.importarEvaluacion.objetos.RangosVO;
import articulacion.importarEvaluacion.objetos.ResultadoVO;
import articulacion.importarEvaluacion.objetos.VaciosVO;
import articulacion.importarEvaluacion.objetos.subPruebasVO;

/**
 * siges.importar<br>
 * Funcinn: Clase que se encarga de accesar el archivo fisico y accesar a los
 * datos de usuario para validar dicha información e instanciar a la clase que
 * importa a la base de datos <br>
 */
public class Excel {
	private String pTotal = "";

	private double tot = 0;

	private HSSFWorkbook workbook;

	private HSSFSheet sheet, sheet2;

	private HSSFRow row, row2;

	private HSSFCell cell, cell2;

	private String mensaje = "";// mensaje en caso de error

	private String advertencia = null;// mensaje en caso de error

	private boolean error;

	// private String estudiantes[];
	private String escala[][];

	// private String escala2[][];
	// private String nota[][];
	// private String nota2[][];
	// private String log[];
	// private String motivo[][];
	// private String logro[][];
	// private String desc[][];
	// private String descFor[][];
	// private String descDif[][];
	// private String descRec[][];
	// private String descEst[][];
	// private ImportarDAO importarDAO;
	private ImportarArticulacionDAO importarArtDAO;

	// private Logros filtro;
	// private FiltroPlantilla filtro2;
	private DatosVO filtroPlantilla;

	private List estudiantesVO;

	private List notasFinales;

	private List rango;

	Collection estNoBD;

	/**
	 * @param u
	 */
	public Excel(ImportarArticulacionDAO importarADAO) {
		Cursor c = new Cursor();
		this.importarArtDAO = importarADAO;

		/*
		 * importarDAO=new ImportarDAO(c);
		 * rb2=ResourceBundle.getBundle("importar");
		 */
	}

	/**
	 * @param n
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarTipoPlantilla(int n) {
		try {
			int fila = 0;
			int col = 0;
			long val;

			fila = ExcelVO.plantilla[ExcelVO.llave][ExcelVO.FIL];
			row2 = sheet2.getRow(fila);
			cell2 = row2
					.getCell((short) ExcelVO.plantilla[ExcelVO.llave][ExcelVO.COL]);

			val = (long) cell2.getNumericCellValue();

			if (val != n) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param par
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEncabezadoAsignatura(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);

			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// sede
			fila = ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// jornada
			fila = ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// Asignatura
			fila = ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// /* Docente
			fila = ExcelVO.plantilla[ExcelVO.docente][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.docente][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// Grupo
			fila = ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			// prueba
			fila = ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				return false;
			}
			int filaSubPruebas = ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.FIL];
			int columnaSubPruebas = ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.COL];
			// subprueba1
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// subprueba2
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// subprueba3
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// subprueba4
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// subprueba5
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			filaSubPruebas = ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.FIL];
			columnaSubPruebas = ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.COL];

			// porcentaje1
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// porcentaje2
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// porcentaje2
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// porcentaje2
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
			// porcentaje2
			fila = filaSubPruebas;
			col = columnaSubPruebas++;
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (cell2 != null) {
				if (!cell.getStringCellValue().equals(
						cell2.getStringCellValue())) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */

	public List getEstudiantesArticulacion(List notasFinales,
			double notaMaxima, List rango) {
		error = true;
		boolean x = true;// ****************************************************
		int num = sheet2.getPhysicalNumberOfRows();
		String val1 = "";
		String val2 = "";
		String val3 = "";
		String val4 = "";
		String val5 = "";
		List l = new ArrayList();
		int fila = 0;
		EstudianteVO estudianteVO = null;
		subPruebasVO subPruebas = null;

		try {
			// SELECT ARTPARNOTAFIN FROM ART_PARAMETRO
			/* validar que no se halla cambiado los datos de los estudiantes */
			for (int i = ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.FIL]; i < num; i++) {
				estudianteVO = new EstudianteVO();
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2
						.getCell((short) ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.COL]);
				// codigo
				estudianteVO.setCodigo(Long.parseLong(getValorSql(cell2)));
				// index
				estudianteVO.setIndex(i);
				if (row2 != null) {
					// SUBPRUEBA 1
					// validamos el nombre
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
					cell2 = row2
							.getCell((short) ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
					val1 = getValorSql(cell);
					val2 = getValorSql(cell2);
					if (!(val1 != null ? val1 : "").equals((val2 != null ? val2
							: ""))) {
						HSSFRow row3 = sheet2.getRow(i);
						cell = row
								.createCell((short) (ExcelVO.plantilla[ExcelVO.error][ExcelVO.COL] + 1));
						cell.setCellValue("Campo 'Nombres' no corresponde a los datos originales de la plantilla");
						error = false;
					}
					// validamo slo sapellidos
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
					cell2 = row2
							.getCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
					val1 = getValorSql(cell);
					val2 = getValorSql(cell2);
					if (!(val1 != null ? val1 : "").equals((val2 != null ? val2
							: ""))) {
						HSSFRow row3 = sheet2.getRow(i);
						cell = row
								.createCell((short) (ExcelVO.plantilla[ExcelVO.error][ExcelVO.COL] + 1));
						cell.setCellValue("Campo 'Apellidos' no corresponde a los datos originales de la plantilla");
						error = false;
					}

					if (!notasFinales.isEmpty()) {
						int columnaNota = ExcelVO.plantilla[ExcelVO.nota][ExcelVO.COL];
						cell = row.getCell((short) columnaNota++);
						val1 = getValorSql(cell);
						if (val1 == null || val1 == "")
							x = false;
						estudianteVO
								.setNotanum0(GenericValidator.isFloat(val1) ? Float
										.parseFloat(val1) : 0);
						estudianteVO.setNotanumBasico(GenericValidator
								.isFloat(val1) ? Float.parseFloat(val1) : 0);

						subPruebas = (subPruebasVO) notasFinales.get(0);
						double resultado1 = (subPruebas.getPrueba1() / 100)
								* estudianteVO.getNotanum0();
						estudianteVO.setCalculada1(resultado1);
						// System.out.println(estudianteVO.getNotanum0()+"la nota calculada1 "
						// + estudianteVO.getCalculada1());

						// SUB PRUEBA 2

						if (notasFinales.size() >= 2) {
							cell = row.getCell((short) columnaNota++);
							val2 = getValorSql(cell);
							if (val2 == null || val2 == "")
								x = false;
							estudianteVO
									.setNotanum1(GenericValidator.isFloat(val1) ? Float
											.parseFloat(val2) : 0);
							estudianteVO
									.setNotanumBasico1(GenericValidator
											.isFloat(val1) ? Float
											.parseFloat(val2) : 0);

							subPruebas = (subPruebasVO) notasFinales.get(1);
							// adicionalemyte a eso
							double resultado2 = (subPruebas.getPrueba1() / 100)
									* estudianteVO.getNotanum1();
							estudianteVO.setCalculada2(resultado2);
							// System.out.println("la nota calculada2 " +
							// estudianteVO.getCalculada2());

						}
						// SUB PRUEBA 3

						if (notasFinales.size() >= 3) {
							cell = row.getCell((short) columnaNota++);
							val3 = getValorSql(cell);
							if (val3 == null || val3 == "")
								x = false;
							estudianteVO
									.setNotanum2(GenericValidator.isFloat(val1) ? Float
											.parseFloat(val3) : 0);
							estudianteVO
									.setNotanumBasico2(GenericValidator
											.isFloat(val1) ? Float
											.parseFloat(val3) : 0);

							subPruebas = (subPruebasVO) notasFinales.get(2);
							double resultado3 = (subPruebas.getPrueba1() / 100)
									* estudianteVO.getNotanum2();
							estudianteVO.setCalculada3(resultado3);
							// System.out.println("la nota calculada3 " +
							// estudianteVO.getCalculada3());

						}
						// SUB PRUEBA 4

						if (notasFinales.size() >= 4) {
							cell = row.getCell((short) columnaNota++);
							val4 = getValorSql(cell);
							if (val4 == null || val4 == "")
								x = false;
							estudianteVO
									.setNotanum3(GenericValidator.isFloat(val1) ? Float
											.parseFloat(val4) : 0);
							estudianteVO
									.setNotanumBasico3(GenericValidator
											.isFloat(val1) ? Float
											.parseFloat(val4) : 0);

							subPruebas = (subPruebasVO) notasFinales.get(3);
							double resultado4 = (subPruebas.getPrueba1() / 100)
									* estudianteVO.getNotanum3();
							estudianteVO.setCalculada4(resultado4);
							// System.out.println("la nota calculada4 " +
							// estudianteVO.getCalculada4());
						}
						// SUB PRUEBA 5

						if (notasFinales.size() >= 5) {
							cell = row.getCell((short) columnaNota++);
							val5 = getValorSql(cell);
							if (val5 == null || val5 == "")
								x = false;
							estudianteVO
									.setNotanum4(GenericValidator.isFloat(val1) ? Float
											.parseFloat(val5) : 0);
							estudianteVO
									.setNotanumBasico4(GenericValidator
											.isFloat(val1) ? Float
											.parseFloat(val5) : 0);

							subPruebas = (subPruebasVO) notasFinales.get(4);
							double resultado5 = (subPruebas.getPrueba1() / 100)
									* estudianteVO.getNotanum4();
							estudianteVO.setCalculada5(resultado5);
							// System.out.println("la nota calculada5 " +
							// estudianteVO.getCalculada5());
						}
						// estudianteVO.setNotaC(notaConceptual(estudianteVO.getNotanum()));
						// if(val5==null||val5==""||val4==null||val4==""||val3==null||val2==null||val2==""||val1==null||val1=="")x=false;
						double notaFinalNumerica = 0;
						notaFinalNumerica = estudianteVO.getCalculada1()
								+ estudianteVO.getCalculada2()
								+ estudianteVO.getCalculada3()
								+ estudianteVO.getCalculada4()
								+ estudianteVO.getCalculada5();
						notaFinalNumerica = (notaFinalNumerica * 100)
								/ notaMaxima;
						estudianteVO.setNotanum(notaFinalNumerica);
						// aca falta mirar porque no se convierta a la nota real
						RangosVO rangosVO = null;
						for (int a = 0; a < rango.size(); a++) {
							rangosVO = (RangosVO) rango.get(a);
							double ini = rangosVO.getInicio();
							double fin = rangosVO.getFin();
							if (estudianteVO.getNotanum() >= ini
									&& estudianteVO.getNotanum() <= fin) {
								estudianteVO.setNotaC(rangosVO.getConceptual());
								// System.out.println(estudianteVO.getNotanum()+" la nota conceptual "
								// + estudianteVO.getNotaC());
								break;
							}
						}
					}
				}
				if (x) {
					l.add(estudianteVO);
				}
				x = true;
			}
		} catch (Exception e) {
			System.out.println("Error validar Estudiantes= " + e);
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * @param a
	 * @param n
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void ponerFila(String a, int n) {
		cell = row.createCell((short) n);
		cell.setCellValue("Campo " + a
				+ " no corresponde a los posibles valores");
		error = false;
	}

	/**
	 * @param n
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNotaReal(String n) {
		if (n == null)
			return null;
		for (int i = 0; i < escala.length; i++) {
			if (escala[i][1].equals(n.trim()))
				return escala[i][0];
		}
		return "";
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean getError() {
		return error;
	}

	/**
	 * @return<br> Return Type: int[]<br>
	 *             Version 1.1.<br>
	 */
	/*
	 * public int[] getResultado(){ return importarDAO.getResultado(); }
	 */
	/**
	 * @return<br> Return Type: int[]<br>
	 *             Version 1.1.<br>
	 */
	/*
	 * public int[] getResultado2(){ return importarDAO.getResultado2(); }
	 */

	public ResultadoVO importarEstudiante(int tipo, String[] archivo,
			String plantilla, String download, String us, String metodologia) {
		error = true;
		ResultadoVO res = null;
		try {

			res = importarArtDAO.importarEstudiante(filtroPlantilla,
					estudiantesVO, metodologia);
			if (res == null) {
				setMensaje("No se pudo importar los registros de evaluacinn: "
						+ importarArtDAO.getMensaje());
				return null;
			}
		} catch (NullPointerException e) {
			setMensaje("No es un archivo de plantilla valido, Faltan filas importantes");
			System.out.println("Error Importar Asignatura:  " + e.toString());
			return null;
		} catch (IndexOutOfBoundsException e) {
			setMensaje("No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error Importar Asignatura:  " + e.toString());
			return null;
		} catch (Exception e) {
			setMensaje(e.toString());
			System.out.println("Error Importar Asignatura:  " + e.toString());
			return null;
		} finally {
			try {
				File f = new File(archivo[0] + archivo[1]);
				if (f != null && f.exists())
					FileUtils.forceDelete(f); // System.out.println("FUE
												// BORRADA LA IMPORTACION");
			} catch (IOException e) {
			}
		}
		return res;
	}

	public boolean validarFormatoEstudiante(int tipo, String[] archivo,
			String plantilla, String download, String par[], String usu,
			String perfil) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		error = true;
		boolean bandera = true;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet = workbook.getSheetAt(1);
			sheet2 = workbook.getSheetAt(2);
			int tipo1 = 11;
			if (!validarTipoPlantilla(tipo1)) {
				setMensaje("Esta plantilla no corresponde a la plantilla de Evaluacion Estudiantes");
				bandera = false;
				return false;
			}

			if (!validarEncabezadoAsignatura(par)) {
				setMensaje("El encabezado ha sido modificado y no es posible importar la Evaluacinn");
				error = true;
				bandera = false;
				return false;
			}
			// El limite se halla asi 5*40/100 el maximo * el que viene dividido
			// el 100%

			try {
				pTotal = getPorcentaje();
				tot = Double.parseDouble(pTotal);
			} catch (Exception e) {
				System.out.println("el error es " + e);
			}
			LimitesVO limites = importarArtDAO.getLimites();
			double notaMax = notaMaxima(tot, limites);
			filtroPlantilla = getEncabezadoArticulacion();
			rango = importarArtDAO.getListaRangos(filtroPlantilla);
			notasFinales = getNotasFinales();
			estudiantesVO = getEstudiantesArticulacion(notasFinales, notaMax,
					rango);
			/*
			 * if(!getDatosEvaluacion(estudiantesVO,limites,filtroPlantilla)){
			 * setMensaje("No se puede importar la evaluacinn porque los datos
			 * ingresados tienen errores, revise el archivo de
			 * inconsistencias"); if(!error){//poner el archivo f=new
			 * File(download); if(!f.exists()) FileUtils.forceMkdir(f); fileOut=
			 * new FileOutputStream(download+archivo[1]);//CREAR UN ARCHIVO
			 * workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			 * fileOut.flush();//CERRAR fileOut.close(); return false; }
			 */

			// LimitesVO limites =importarArtDAO.getLimites();

			// especialidades=importarArtDAO.getEspecialidades(filtroPlantilla.getInstitucion());
			/*
			 * if(!error){//poner el archivo setMensaje("No se puede importar la
			 * evaluacinn porque la informaci{no de los estudiantes fue
			 * alterada, revise el archivo de inconsistencias"); f=new
			 * File(download); if(!f.exists()) FileUtils.forceMkdir(f); fileOut=
			 * new FileOutputStream(download+archivo[1]);//CREAR UN ARCHIVO
			 * workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			 * fileOut.flush();//CERRAR fileOut.close(); return false; }
			 */
			/*
			 * ME TOCO PONERLO PORQUE AHORA HAY NO VA EL CIERRE
			 * if(!validarCierre(estudiantesVO)){ setMensaje("Las notas se
			 * encuentran cerradas lo sentimos no se puede hacer la
			 * Importacinn"); bandera=false; return false; }
			 */

			if (!getDatosEvaluacion(estudiantesVO, limites, filtroPlantilla,
					notasFinales)) {
				setMensaje("No se puede importar la evaluacinn porque los datos ingresados tienen errores, revise el archivo de inconsistencias");
				if (!error) {// poner el archivo
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(download + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();
					return false;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			setMensaje("No es un archivo de plantilla vnlido, Faltan filas importantes");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			setMensaje("No es un archivo de plantilla vnlido, Faltan hojas en el libro");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			setMensaje(e.toString());
			System.out.println("Error validar Asignatura:" + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
				// elminar archivo
				if (!error || !bandera) {
					f = new File(archivo[0] + archivo[1]);
					if (f.exists())
						FileUtils.forceDelete(f);
				}
			} catch (IOException e) {
			}
		}
		return true;
	}

	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param celda
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getValorSql(HSSFCell celda) {
		String valor = null;
		if (celda == null)
			return null;
		switch (celda.getCellType()) {
		case 0: // numerico
			valor = String.valueOf((double) celda.getNumericCellValue());
			if (valor.endsWith(".0"))
				valor = valor.substring(0, valor.length() - 2);
			break;
		case 1:// string
			if (celda.getStringCellValue().trim().equals(""))
				valor = null;
			else
				valor = celda.getStringCellValue().replace('\'', ' ').trim();
			break;
		case 2:// formula
			if (celda.getStringCellValue().trim().equals(""))
				valor = null;
			else
				valor = celda.getStringCellValue().trim();
			break;
		case 3:
			valor = null;
			break;// blank
		case 4:// boolean
			if (celda.getStringCellValue().trim().equals(""))
				valor = null;
			else
				valor = celda.getStringCellValue().trim();
			break;
		case 5: // error
			if (celda.getStringCellValue().trim().equals(""))
				valor = null;
			else
				valor = celda.getStringCellValue().trim();
			break;
		}
		return valor;
	}

	/**
	 * @return Returns the advertencia.
	 */
	public String getAdvertencia() {
		return advertencia;
	}

	/**
	 * @param advertencia
	 *            The advertencia to set.
	 */
	public void setAdvertencia(String advertencia) {
		this.advertencia = advertencia;
	}

	public DatosVO getEncabezadoArticulacion() {
		DatosVO encabezado = new DatosVO();
		int fila = 0;
		int col = 0;

		// *************INSTITUCION*******************
		// codigo institucion
		fila = ExcelVO.plantilla[ExcelVO.ins][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.ins][ExcelVO.COL]);
		encabezado.setInstitucion(getValorSql(cell2));
		// *************SEDE*******************
		// codigo SEDE
		fila = ExcelVO.plantilla[ExcelVO.sed][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.sed][ExcelVO.COL]);
		encabezado.setSede(getValorSql(cell2));
		// *************JORNADA*******************
		// codigo jornada
		fila = ExcelVO.plantilla[ExcelVO.jor][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.jor][ExcelVO.COL]);
		encabezado.setJornada(getValorSql(cell2));
		// *************GRUPO*******************
		// grado
		fila = ExcelVO.plantilla[ExcelVO.grup][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.grup][ExcelVO.COL]);
		encabezado.setGrupo(getValorSql(cell2));

		// PRUEBA
		fila = ExcelVO.plantilla[ExcelVO.pru][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.pru][ExcelVO.COL]);
		encabezado.setPrueba(getValorSql(cell2));

		// ASIGNATURA
		fila = ExcelVO.plantilla[ExcelVO.asig][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.asig][ExcelVO.COL]);
		encabezado.setAsignatura(getValorSql(cell2));

		// METODOLOGIA
		fila = ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.COL]);
		encabezado.setMetodologia(getValorSql(cell2));
		// ANHO VIGENCIA
		fila = ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.COL]);
		encabezado.setAnVigencia(Integer.parseInt(getValorSql(cell2)));
		// PERIODO VIGENCIA
		fila = ExcelVO.plantilla[ExcelVO.periodoVigencia][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.periodoVigencia][ExcelVO.COL]);
		encabezado.setPerVigencia(Integer.parseInt(getValorSql(cell2)));
		// BIMESTRE
		fila = ExcelVO.plantilla[ExcelVO.bimestre][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.bimestre][ExcelVO.COL]);
		encabezado.setBimestre(Integer.parseInt(getValorSql(cell2)));
		return encabezado;
	}

	public boolean getDatosEvaluacion(List estudiantes, LimitesVO limites,
			DatosVO filtroPlantilla, List notasFinales) {
		boolean band = false;
		EspecialidadesVO especi = null;
		int num = sheet2.getPhysicalNumberOfRows();
		EstudianteVO estu = null;
		try {
			// **********************************EVALUAMOS EL
			// SEMESTRE****************
			for (int i = 0; i < estudiantes.size(); i++) {
				String mensaje = "";
				band = false;
				estu = (EstudianteVO) estudiantes.get(i);
				// grupo
				float min = limites.getLimitA();
				float max = limites.getLimitB();

				if (notasFinales.isEmpty()) {
					double numeric = estu.getNotanumBasico();
					if (numeric > max || numeric < min) {
						mensaje = "Campo 'Nota Nnmerica' no corresponde a los limites permitidos";
						band = false;
						// break;
					} else {
						band = true;
					}
				}

				if (!notasFinales.isEmpty()) {
					band = true;

					double numeric0 = estu.getNotanumBasico();
					double numeric1 = estu.getNotanumBasico1();
					double numeric2 = estu.getNotanumBasico2();
					double numeric3 = estu.getNotanumBasico3();
					double numeric4 = estu.getNotanumBasico4();

					if (numeric0 > max || numeric0 < min) {
						mensaje = "Campo 'Nota Nnmerica 1' no corresponde a los limites permitidos";
						band = false;
					}

					if (notasFinales.size() == 2) {
						if (numeric1 > max || numeric1 < min) {
							mensaje = "Campo 'Nota Nnmerica 2' no corresponde a los limites permitidos";
							band = false;
						}
					}

					if (notasFinales.size() == 3) {
						if (numeric2 > max || numeric2 < min) {
							mensaje = "Campo 'Nota Nnmerica 3' no corresponde a los limites permitidos";
							band = false;
						}
					}

					if (notasFinales.size() == 4) {
						if (numeric3 > max || numeric3 < min) {
							mensaje = "Campo 'Nota Nnmerica 4' no corresponde a los limites permitidos";
							band = false;
						}
					}

					if (notasFinales.size() == 5) {
						if (numeric4 > max || numeric4 < min) {
							mensaje = "Campo 'Nota Nnmerica 5' no corresponde a los limites permitidos";
							band = false;
						}
					}

				}
				if (!band) {
					// error
					HSSFRow row3 = sheet.getRow(estu.getIndex());
					cell = row3
							.createCell((short) (ExcelVO.plantilla[ExcelVO.error][ExcelVO.COL]));
					cell.setCellValue(mensaje);
					error = false;
				}

			}
			if (!error)
				return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return error;

	}

	public String getPorcentaje() {
		int fila = 0;
		int col = 0;
		String numero = "";
		try {
			fila = ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.FIL];
			row2 = sheet2.getRow(fila);
			cell2 = row2
					.getCell((short) ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.COL]);
			numero = (getValorSql(cell2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exeption" + e);
		}
		return numero;
	}

	public double notaMaxima(double max, LimitesVO limites) {

		double maximo = 0;
		double ma = limites.getLimitB();
		if (limites != null && max != 0) {
			maximo = (ma / 100) * max;
		}

		return maximo;
	}

	public List getNotasFinales() {
		error = true;
		boolean x = true;// ****************************************************
		int num3 = sheet2.getPhysicalNumberOfRows();
		String val1;
		List l = new ArrayList();
		subPruebasVO subPruebas = null;

		try {

			/* validar que no se halla cambiado los datos de los estudiantes */
			for (int i = ExcelVO.plantilla[ExcelVO.porcentajesOculta][ExcelVO.FIL]; i < ExcelVO.plantilla[ExcelVO.porcentajesOculta][ExcelVO.FIL] + 5; i++) {
				subPruebas = new subPruebasVO();
				row2 = sheet2.getRow(i);

				if (row2 != null) {

					// traernos PORCENTAJE EQUIVALENTE
					cell2 = row2
							.getCell((short) ExcelVO.plantilla[ExcelVO.porcentajesOculta][ExcelVO.COL]);
					val1 = getValorSql(cell2);
					if (val1 == null || val1 == "")
						x = false;
					subPruebas
							.setPrueba1(GenericValidator.isFloat(val1) ? Float
									.parseFloat(val1) : 0);

					if (x)
						l.add(subPruebas);
					x = true;

				}
			}
			/*
			 * String[][] r=importarDAO.getEstudiantes(filtro); estNoBD=new
			 * ArrayList(); z=y=0; for(int j=0;j<estudiantes.length;j++){ y=0;
			 * for(int i=0;i<r.length;i++){ if(r[i][0].equals(estudiantes[j])){
			 * y++; break; } } if(y>0){ z++; }else{ estNoBD.add(""+j); } }
			 * estudiantes=importarDAO.setCodigosDuplicados(estudiantes);
			 */
			// if(!error) return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error validar Estudiantes= " + e);
			// return false;
		}
		return l;
	}

}