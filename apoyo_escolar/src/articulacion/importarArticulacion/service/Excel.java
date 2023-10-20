package articulacion.importarArticulacion.service;

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
import siges.util.Logger;
import siges.util.Properties;
import siges.util.Recursos;
import siges.util.beans.Area;
import siges.util.beans.Asignatura;
import siges.util.beans.BateriaDescriptor;
import siges.util.beans.BateriaLogro;
import siges.util.beans.Preescolar;
import articulacion.importarArticulacion.plantilla.ExcelVO;

import articulacion.importarArticulacion.dao.ImportarArticulacionDAO;
import articulacion.importarArticulacion.objetos.DatosVO;
import articulacion.importarArticulacion.objetos.EspecialidadesVO;
import articulacion.importarArticulacion.objetos.EstudianteVO;
import articulacion.importarArticulacion.objetos.ResultadoVO;
import articulacion.importarArticulacion.objetos.VaciosVO;

/**
 * siges.importar<br>
 * Funcinn: Clase que se encarga de accesar el archivo fisico y accesar a los
 * datos de usuario para validar dicha información e instanciar a la clase que
 * importa a la base de datos <br>
 */
public class Excel {

	private HSSFWorkbook workbook;
	private HSSFSheet sheet, sheet2;
	private HSSFRow row, row2;
	private HSSFCell cell, cell2;
	private String mensaje = "";// mensaje en caso de error
	private String advertencia = null;// mensaje en caso de error
	private boolean error;
	private String estudiantes[];
	private String escala[][];
	private String escala2[][];
	private String nota[][];
	private String nota2[][];
	private String log[];
	private String motivo[][];
	private String logro[][];
	private String desc[][];
	private String descFor[][];
	private String descDif[][];
	private String descRec[][];
	private String descEst[][];
	private ImportarDAO importarDAO;
	private ImportarArticulacionDAO importarArtDAO;
	private Logros filtro;
	private FiltroPlantilla filtro2;
	private DatosVO filtroPlantilla;
	private List estudiantesVO;
	private List especialidades;
	Collection estNoBD;

	/**
	 * @param u
	 */
	public Excel(ImportarArticulacionDAO importarADAO) {
		// Cursor c=new Cursor();
		this.importarArtDAO = importarADAO;

		// importarDAO=new ImportarDAO(c);
		// rb2=ResourceBundle.getBundle("importar");
	}

	/**
	 * @param n
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarTipoPlantilla(int n) {
		// System.out.println("llego al metodo del tipn");
		try {
			int fila = 0;
			int col = 0;
			long val;

			fila = ExcelVO.plantilla2[ExcelVO.llave][ExcelVO.FIL];
			row2 = sheet2.getRow(fila);
			cell2 = row2
					.getCell((short) ExcelVO.plantilla2[ExcelVO.llave][ExcelVO.COL]);

			// System.out.println("La cuestion en la hoja es "+cell2.getNumericCellValue());
			val = (long) cell2.getNumericCellValue();

			if (val != n) {
				// System.out.println("esa cosa es diferente");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("eception"+e);
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
		// System.out.println("Llega a la funcion de evaluar plantillas");
		try {
			// institucion
			fila = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			col = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);

			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue())) {
				// System.out.println("La institucion de las casillas es diferente");
				return false;
			}
			/*
			 * //sede fila=ExcelVO.plantilla[ExcelVO.sed][ExcelVO.FIL];
			 * col=ExcelVO.plantilla[ExcelVO.sed][ExcelVO.COL];
			 * row=sheet.getRow(fila); row2=sheet2.getRow(fila);
			 * cell=row.getCell((short)col); cell2=row2.getCell((short)col);
			 * if(!cell.getStringCellValue().equals(cell2.getStringCellValue()))
			 * {
			 * System.out.println("La institucion de las casillas es diferente"
			 * ); return false; } else
			 * System.out.println("La institucion de las casillas es igual");
			 * 
			 * //institucion con respecto a login
			 * fila=Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(!cell2.getStringCellValue().equals(par[0])){ error=false;
			 * return false; }
			 * 
			 * 
			 * //sede fila=Asignatura.Asig[Asignatura.Sede2][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Sede2][Asignatura.COL];
			 * row=sheet.getRow(fila); row2=sheet2.getRow(fila);
			 * cell=row.getCell((short)col); cell2=row2.getCell((short)col);
			 * if(!cell.getStringCellValue().equals(cell2.getStringCellValue()))
			 * return false; //jornada
			 * fila=Asignatura.Asig[Asignatura.Jornada2][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Jornada2][Asignatura.COL];
			 * row=sheet.getRow(fila); row2=sheet2.getRow(fila);
			 * cell=row.getCell((short)col); cell2=row2.getCell((short)col);
			 * if(!cell.getStringCellValue().equals(cell2.getStringCellValue()))
			 * return false; //grado
			 * fila=Asignatura.Asig[Asignatura.Grado2][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Grado2][Asignatura.COL];
			 * row=sheet.getRow(fila); row2=sheet2.getRow(fila);
			 * cell=row.getCell((short)col); cell2=row2.getCell((short)col);
			 * if(!cell.getStringCellValue().equals(cell2.getStringCellValue()))
			 * return false;
			 * 
			 * //metodologia
			 * fila=Asignatura.Asig[Asignatura.Metodologia2][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Metodologia2][Asignatura.COL];
			 * row=sheet.getRow(fila); row2=sheet2.getRow(fila);
			 * cell=row.getCell((short)col); cell2=row2.getCell((short)col);
			 * if(!cell.getStringCellValue().equals(cell2.getStringCellValue()))
			 * return false;
			 */
			/*
			 * //ID DE LAS CABECERAS OCULTO //institu cion
			 * fila=Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //sede
			 * fila=Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Sede1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //jornada
			 * fila=Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //grado
			 * fila=Asignatura.Asig[Asignatura.Grado1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Grado1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //grupo
			 * fila=Asignatura.Asig[Asignatura.Grupo1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Grupo1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //asignatura
			 * fila=Asignatura.Asig[Asignatura.Asignatura1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Asignatura1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false; //periodo
			 * fila=Asignatura.Asig[Asignatura.Periodo1][Asignatura.FIL];
			 * col=Asignatura.Asig[Asignatura.Periodo1][Asignatura.COL];
			 * row2=sheet2.getRow(fila); cell2=row2.getCell((short)col);
			 * if(getValorSql(cell2)==null) return false;
			 */
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEstudiantesAsignatura(FiltroPlantilla filtro) {
		error = true;
		int z = 0, y = 0;
		String val1, val2;
		int posicion;
		try {
			/* validar que no se halla cambiado los datos de los estudiantes */
			int pos[] = { Asignatura.Asig[Asignatura.Codigo][Asignatura.COL],
					Asignatura.Asig[Asignatura.Apellido][Asignatura.COL],
					Asignatura.Asig[Asignatura.Nombre][Asignatura.COL] };
			String val[] = { "Codigo", "Apellidos", "Nombres" };
			int num = sheet2.getPhysicalNumberOfRows();
			estudiantes = new String[num
					- Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]];
			for (int i = Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]; i < num; i++) {
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2.getCell((short) pos[0]);
				estudiantes[z++] = getValorSql(cell2);
				if (row != null) {
					for (int j = 1; j < val.length; j++) {
						cell = row.getCell((short) pos[j]);
						cell2 = row2.getCell((short) pos[j]);
						val1 = getValorSql(cell);
						val2 = getValorSql(cell2);
						if (!(val1 != null ? val1 : "")
								.equals((val2 != null ? val2 : ""))) {
							HSSFRow row3 = sheet2
									.getRow(Asignatura.Asig[Asignatura.Logros1][Asignatura.FIL]);
							posicion = row3.getPhysicalNumberOfCells();
							cell = row.createCell((short) posicion);
							cell.setCellValue("Campo '"
									+ val[j]
									+ "' no corresponde a los datos originales de la plantilla");
							error = false;
						}
					}
				}
			}
			/*
			 * validar que los estudiantes sean los que estan en la base de
			 * datos
			 */
			/*
			 * String[][] r=importarDAO.getEstudiantes(filtro); estNoBD=new
			 * ArrayList(); z=y=0; for(int j=0;j<estudiantes.length;j++){ y=0;
			 * for(int i=0;i<r.length;i++){ if(r[i][0].equals(estudiantes[j])){
			 * y++; break; } } if(y>0){ z++; }else{ estNoBD.add(""+j); } }
			 * estudiantes=importarDAO.setCodigosDuplicados(estudiantes);
			 */
			if (!error)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error validar Estudiantes= " + e);
			return false;
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */

	public List getEstudiantesArticulacion() {
		error = true;
		boolean x = true;// ****************************************************
		int num = sheet2.getPhysicalNumberOfRows();
		String val1, val2, val3, val4, val5, val6;
		List l = new ArrayList();

		EstudianteVO estudianteVO = null;
		boolean especialidadE = false;
		boolean semestreE = false;
		boolean niveladoE = false;
		try {
			/* validar que no se halla cambiado los datos de los estudiantes */
			for (int i = ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]; i < num; i++) {
				estudianteVO = new EstudianteVO();
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2
						.getCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
				// System.out.println(num+"estudiante=="+getValorSql(cell2));
				// codigo
				estudianteVO.setCodigo(Long.parseLong(getValorSql(cell2)));
				// abreviatura
				cell2 = row2
						.getCell((short) ExcelVO.plantilla[ExcelVO.tipoDocumento][ExcelVO.COL]);
				estudianteVO.setAbreviaturaCodigo(Long
						.parseLong(getValorSql(cell2)));
				// index
				estudianteVO.setIndex(i);
				if (row != null) {
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
								.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
						cell.setCellValue("Campo 'nombre' no corresponde a los datos originales de la plantilla");
						error = false;
					}
					// validamo slo sapellidos
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
					cell2 = row2
							.getCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
					val1 = getValorSql(cell);
					val2 = getValorSql(cell);
					val3 = getValorSql(cell);
					val4 = getValorSql(cell);
					val5 = getValorSql(cell);
					val6 = getValorSql(cell);

					if (!(val1 != null ? val1 : "").equals((val2 != null ? val2
							: ""))) {
						HSSFRow row3 = sheet2.getRow(i);
						cell = row
								.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
						cell.setCellValue("Campo 'apellido' no corresponde a los datos originales de la plantilla");
						error = false;
					}
					// traernos especialidad
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.esp][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					else {
						// System.out.println("Entra a donde no quiero pero si");
						estudianteVO.setEspecialidad(GenericValidator
								.isLong(val1) ? Long.parseLong(val1) : 0);
						especialidadE = true;
					}

					// traernos semestre
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.sem][ExcelVO.COL]);
					val2 = getValorSql(cell);
					if (val2 == null || val2 == "")
						x = false;
					else {
						estudianteVO
								.setSemestre(GenericValidator.isLong(val2) ? Long
										.parseLong(val2) : 0);
						// semestreE=true;
					}

					/*
					 * //traernos grupo
					 * cell=row.getCell((short)ExcelVO.plantilla
					 * [ExcelVO.gru][ExcelVO.COL]); val3=getValorSql(cell);
					 * if(val3==null||val3=="")x=false;
					 * estudianteVO.setGrupo(GenericValidator
					 * .isLong(val3)?Long.parseLong(val3):0);
					 */

					// if(val4==""||val4=="X"||val4=="x")

					// estudianteVO.set

					// {
					// System.out.println("JOHANNITA ESTA ENTRANDO A NIVELADO");

					// }

					// niveladoE=true;
					// }
					// traernos tipo documento
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.tipoDocumento][ExcelVO.COL]);
					val5 = getValorSql(cell);
					if (val5 == null || val5 == "")
						x = false;
					estudianteVO.setTipoDoc(val5);

					// traernos numero de documento
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
					val6 = getValorSql(cell);
					if (val6 == null || val6 == "")
						x = false;
					estudianteVO.setDocumento(val6);

					// traernos nivelados
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL]);
					val4 = getValorSql(cell);
					if (val4 == null || val4 == "") {
						val4 = "";
						estudianteVO.setNivelado(val4);
					} else {
						estudianteVO.setNivelado(val4);
					}

					if (especialidadE && val2 == null || val2 == "")

					{
						// System.out.println("Falta alguna de las cuestiones obligatorias");
						cell = row
								.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
						cell.setCellValue("Hacen falta campos requeridos, Por favor completarlos para realizar importacinn");
						error = false;
					}
					/*
					 * if(semestreE&&val4==null||val4=="")
					 * 
					 * { //System.out.println(
					 * "Falta alguna de las cuestiones obligatorias");
					 * cell=row.createCell
					 * ((short)(ExcelVO.plantilla[ExcelVO.nivelado
					 * ][ExcelVO.COL]+1)); cell.setCellValue(
					 * "Hacen falta campos requeridos, Por favor completarlos para realizar importacinn"
					 * ); error=false; }
					 */

					if (!especialidadE && niveladoE)

					{
						// System.out.println("Falta alguna de las cuestiones obligatorias");
						cell = row
								.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
						cell.setCellValue("Hacen falta campos requeridos, Por favor completarlos para realizar importacinn");
						error = false;
					}

					/*
					 * if(especialidadE&&semestreE&&!niveladoE)
					 * 
					 * { //System.out.println(
					 * "Falta alguna de las cuestiones obligatorias");
					 * cell=row.createCell
					 * ((short)(ExcelVO.plantilla[ExcelVO.nivelado
					 * ][ExcelVO.COL]+1)); cell.setCellValue(
					 * "Hacen falta campos requeridos, Por favor completarlos para realizar importacinn"
					 * ); error=false; }
					 */

					especialidadE = false;
					semestreE = false;
					niveladoE = false;

				}

				if (x)
					l.add(estudianteVO);

				x = true;

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
			// System.out.println("Error validar Estudiantes= "+e);
			// return false;
		}
		return l;
	}

	public List getEstudiantesArticulacionVacios() {
		error = true;
		int z = 0, y = 0;
		int posicion;
		boolean x = true;// ****************************************************
		int num = sheet2.getPhysicalNumberOfRows();
		String val1;
		List lvacios = new ArrayList();
		VaciosVO vaciosVO = null;
		try {
			/* validar que no se halla cambiado los datos de los estudiantes */
			for (int i = ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]; i < num; i++) {
				vaciosVO = new VaciosVO();
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2
						.getCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
				// System.out.println(num+"estudiante=="+getValorSql(cell2));
				// codigo

				vaciosVO.setCodigo(Long.parseLong(getValorSql(cell2)));
				if (row != null) {

					// traernos especialidad
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.esp][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					// estudianteVO.setEspecialidad(GenericValidator.isLong(val1)?Long.parseLong(val1):0);
					// traernos semestre
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.sem][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					// estudianteVO.setSemestre(GenericValidator.isLong(val1)?Long.parseLong(val1):0);
					// traernos grupo
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.gru][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					// estudianteVO.setGrupo(GenericValidator.isLong(val1)?Long.parseLong(val1):0);
					// traernos nivelados
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL]);
					val1 = getValorSql(cell);
					// estudianteVO.setNivelado(val1);
					// traernos tipo documento
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.tipoDocumento][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					// estudianteVO.setTipoDoc(val1);
					// traernos numero de documento
					cell = row
							.getCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
					val1 = getValorSql(cell);
					if (val1 == null || val1 == "")
						x = false;
					// estudianteVO.setDocumento(val1);
				}
				if (x)
					System.out.println("esto no esta vacio");
				else
					lvacios.add(vaciosVO);
				x = true;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error validar Estudiantes= " + e);

		}
		return lvacios;
	}

	public boolean validarEstudiantesArea(FiltroPlantilla filtro) {
		error = true;
		int z = 0, y = 0;
		String val1, val2;
		int posicion;
		try {
			/* validar que no se halla cambiado los datos de los estudiantes */
			int pos[] = { Area.Area[Area.Codigo][Area.COL],
					Area.Area[Area.Apellido][Area.COL],
					Area.Area[Area.Nombre][Area.COL] };
			String val[] = { "Codigo", "Apellidos", "Nombres" };
			int num = sheet2.getPhysicalNumberOfRows();
			estudiantes = new String[num - Area.Area[Area.Codigo][Area.FIL]];
			for (int i = Area.Area[Area.Codigo][Area.FIL]; i < num; i++) {
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2.getCell((short) pos[0]);
				estudiantes[z++] = getValorSql(cell2);
				if (row != null) {
					for (int j = 1; j < val.length; j++) {
						cell = row.getCell((short) pos[j]);
						cell2 = row2.getCell((short) pos[j]);
						val1 = getValorSql(cell);
						val2 = getValorSql(cell2);
						if (!(val1 != null ? val1 : "")
								.equals((val2 != null ? val2 : ""))) {
							// HSSFRow
							// row3=sheet2.getRow(Area.Area[Area.EvalFortaleza][Area.FIL]);
							// posicion=row3.getPhysicalNumberOfCells();
							posicion = Area.Area[Area.EvalEstrategia][Area.COL] + 1;
							cell = row.createCell((short) posicion);
							cell.setCellValue("Campo '"
									+ val[j]
									+ "' no corresponde a los datos originales de la plantilla");
							error = false;
						}
					}
				}
			}
			/*
			 * validar que los estudiantes sean los que estan en la base de
			 * datos
			 */
			/*
			 * String[][] r=importarDAO.getEstudiantes(filtro); z=y=0;
			 * estNoBD=new ArrayList(); z=y=0; for(int
			 * j=0;j<estudiantes.length;j++){ y=0; for(int i=0;i<r.length;i++){
			 * if(r[i][0].equals(estudiantes[j])){ y++; break; } } if(y>0){ z++;
			 * }else{ estNoBD.add(""+j); } }
			 * estudiantes=importarDAO.setCodigosDuplicados(estudiantes);
			 */
			if (!error)
				return false;
		} catch (Exception e) {
			System.out.println("Error validar Estudiantes= " + e);
			return false;
		}
		return true;
	}

	/**
	 * @param filtro
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEstudiantesPreescolar(FiltroPlantilla filtro) {
		error = true;
		int z = 0, y = 0;
		String val1, val2;
		int posicion;
		try {
			/* validar que no se halla cambiado los datos de los estudiantes */
			int pos[] = { Preescolar.Pree[Preescolar.Codigo][Preescolar.COL],
					Preescolar.Pree[Preescolar.Apellido][Preescolar.COL],
					Preescolar.Pree[Preescolar.Nombre][Preescolar.COL] };
			String val[] = { "Codigo", "Apellidos", "Nombres" };
			int num = sheet2.getPhysicalNumberOfRows();
			estudiantes = new String[num
					- Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL]];
			for (int i = Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL]; i < num; i++) {
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				cell2 = row2.getCell((short) pos[0]);
				estudiantes[z++] = getValorSql(cell2);
				if (row != null) {
					for (int j = 1; j < val.length; j++) {
						cell = row.getCell((short) pos[j]);
						cell2 = row2.getCell((short) pos[j]);
						val1 = getValorSql(cell);
						val2 = getValorSql(cell2);
						if (!(val1 != null ? val1 : "")
								.equals((val2 != null ? val2 : ""))) {
							posicion = Preescolar.Pree[Preescolar.Evaluacion][Preescolar.COL] + 1;
							cell = row.createCell((short) posicion);
							cell.setCellValue("Campo '"
									+ val[j]
									+ "' no corresponde a los datos originales de la plantilla");
							error = false;
						}
					}
				}
			}
			/*
			 * validar que los estudiantes sean los que estan en la base de
			 * datos
			 */
			/*
			 * String[][] r=importarDAO.getEstudiantes(filtro); z=y=0; estNoBD=
			 * new ArrayList(); z=y=0; for(int j=0;j<estudiantes.length;j++){
			 * y=0; for(int i=0;i<r.length;i++){
			 * if(r[i][0].equals(estudiantes[j])){ y++; break; } } if(y>0){ z++;
			 * }else{ estNoBD.add(""+j); } }
			 * estudiantes=importarDAO.setCodigosDuplicados(estudiantes);
			 */
			if (!error)
				return false;
		} catch (Exception e) {
			System.out.println("Error validar Estudiantes Preescolar= " + e);
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarLogrosAsignatura() {
		try {
			boolean band = true;
			int j = 0, z = 0, y = 0;
			String log1, log2;
			/* VALIDAR LOS NOMBRES DE LAS DOS HOJAS */
			row = sheet
					.getRow(Asignatura.Asig[Asignatura.Logros2][Asignatura.FIL]);
			row2 = sheet2
					.getRow(Asignatura.Asig[Asignatura.Logros2][Asignatura.FIL]);
			int celdas = row2.getPhysicalNumberOfCells();
			int celdaInicial = Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL];
			for (int i = celdaInicial; i < celdas; i++) {
				cell = row.getCell((short) i);
				cell2 = row2.getCell((short) i);
				log1 = getValorSql(cell);
				log2 = getValorSql(cell2);
				if (log1 != null && log2 != null) {
					j++;
					if (!log1.equals(log2)) {
						band = false;
					}
				}
			}
			// linea siguiente fue puesta a lo agreste haber si funca
			if (!band)
				return false;
			/* VALIDAR LOS CODIGO DE LOS LOGROS QUE ESTEN EN LA HOJA OCULTA */
			log = new String[j];
			row2 = sheet2
					.getRow(Asignatura.Asig[Asignatura.Logros1][Asignatura.FIL]);
			for (int i = Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL]; i < j
					+ Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL]; i++) {
				cell2 = row2.getCell((short) i);
				log1 = getValorSql(cell2);
				if (log1 == null || log1.equals("")) {
					return false;
				} else {
					log[z++] = log1;
				}
			}
			if (!band)
				return false;
			/* VALIDAR LOS CODIGO DE LOGROS CON LA BASE DE DATOS */
			// traer los codigos de logro
			String[][] r = importarDAO.getLogros(filtro2);
			if (r != null) {
				if (r.length != log.length) {
					return false;
				}
				z = y = 0;
				for (int x = 0; x < r.length; x++) {
					y = 0;
					for (int m = 0; m < log.length; m++) {
						if (r[x][0].equals(log[m])) {
							y++;
							break;
						}
					}
					if (y > 0)
						z++;
				}
				if (z != r.length) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEscalaMotivoAsignatura() {
		error = true;
		int z = 0, y = 0, x = 0;
		String valor = null;
		try {
			// escala valorativa de la base de datos
			int[] n = { 0 };
			escala = importarDAO.getEscala(2);
			// Motivo de la base de datos
			motivo = importarDAO.getFiltroMatriz(Recursos
					.getRecurso(Recursos.AUSENCIA));
			// validar que no se halla cambiado la escala valorativa
			int num = sheet2.getPhysicalNumberOfRows();
			nota = new String[num
					- Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]][5];
			int p = -1, r = 0, posi = 12;
			HSSFRow row3 = sheet2
					.getRow(Asignatura.Asig[Asignatura.Logros1][Asignatura.FIL]);
			int posicionInicial = row3.getPhysicalNumberOfCells();
			for (int i = Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]; i < num; i++) {// iterar
																							// por
																							// cada
																							// fila
																							// de
																							// estudiantes
				p++;
				posi = posicionInicial;
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				// obtener nota
				if (row != null) {
					x = 0;
					cell = row
							.getCell((short) Asignatura.Asig[Asignatura.Evaluacion][Asignatura.COL]);
					valor = getValorSql(cell);
					nota[p][0] = getNotaReal(valor);
					if (valor != null && nota[p][0] != null
							&& !nota[p][0].equals("")) {// digito y si
														// corresponde
						x = 1;
					}
					if (valor == null) {// no digito nada
						x = 1;
					}
					if (x == 0) {
						ponerFila("'Evaluacinn'", posi++);
						// break;
					}
					// obtener ausencia justificada
					x = 0;
					cell = row
							.getCell((short) Asignatura.Asig[Asignatura.AusenciaJus][Asignatura.COL]);
					nota[p][1] = getValorSql(cell);
					if (nota[p][1] != null) {
						if (nota[p][1].length() < 4
								&& GenericValidator.isInt(nota[p][1])
								&& (Integer.parseInt(nota[p][1])) > 0)
							x = 1;
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Ausencia justificada", posi++);
						// break;
					}
					// ausencia no justificada
					x = 0;
					cell = row
							.getCell((short) Asignatura.Asig[Asignatura.AusenciaNoJus][Asignatura.COL]);
					nota[p][2] = getValorSql(cell);
					if (nota[p][2] != null) {
						if (nota[p][2].length() < 4
								&& GenericValidator.isInt(nota[p][2])
								&& (Integer.parseInt(nota[p][2])) > 0)
							x = 1;
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Ausencia no justificada", posi++);
						// break;
					}
					// motivo
					x = 0;
					cell = row
							.getCell((short) Asignatura.Asig[Asignatura.Motivo][Asignatura.COL]);
					nota[p][3] = getValorSql(cell);
					if (nota[p][3] != null && motivo != null) {
						for (int k = 0; k < motivo.length; k++) {
							if (nota[p][3].equals(motivo[k][0])) {
								x = 1;
								break;
							}
						}
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Motivo de ausencia", posi++);
						// break;
					}
					// porcentaje de ausencia
					x = 0;
					cell = row
							.getCell((short) Asignatura.Asig[Asignatura.Porcentaje][Asignatura.COL]);
					nota[p][4] = getValorSql(cell);
					if (nota[p][4] != null) {
						if (nota[p][4].length() < 4
								&& GenericValidator.isInt(nota[p][4])
								&& GenericValidator
										.isInRange(
												Integer.parseInt((!nota[p][4]
														.equals("") ? nota[p][4]
														: "0")), 1, 100)) {
							x = 1;
						}
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Porcentaje", posi++);
						// break;
					}
				}
			}
			if (!error)
				return false;
		} catch (Exception e) {
			System.out.println("Error validar Escala y motivo= " + e);
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEscalaMotivoArea() {
		error = true;
		int z = 0, y = 0, x = 0;
		String valor = null;
		try {
			// escala valorativa de la base de datos
			int[] n = { 0 };
			escala = importarDAO.getEscala(2);
			// Motivo de la base de datos
			motivo = importarDAO.getFiltroMatriz(Recursos
					.getRecurso(Recursos.AUSENCIA));
			// validar que no se halla cambiado la escala valorativa
			int num = sheet2.getPhysicalNumberOfRows();
			nota = new String[num - Area.Area[Area.Codigo][Area.FIL]][5];
			int p = -1, r = 0, posi = 12;
			int posicionInicial = Area.Area[Area.EvalEstrategia][Area.COL] + 1;
			for (int i = Area.Area[Area.Codigo][Area.FIL]; i < num; i++) {// iterar
																			// por
																			// cada
																			// fila
																			// de
																			// estudiantes
				p++;
				posi = posicionInicial;
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				if (row != null) {
					// obtener nota
					x = 0;
					cell = row
							.getCell((short) Area.Area[Area.Evaluacion][Area.COL]);
					valor = getValorSql(cell);
					nota[p][0] = getNotaReal(valor);
					if (valor != null && nota[p][0] != null
							&& !nota[p][0].equals("")) {// digito y si
														// corresponde
						x = 1;
					}
					if (valor == null) {// no digito nada
						x = 1;
					}
					if (x == 0) {
						ponerFila("'Evaluacinn'", posi++);
						// break;
					}
					// obtener ausencia justificada
					x = 0;
					cell = row
							.getCell((short) Area.Area[Area.AusenciaJus][Area.COL]);
					nota[p][1] = getValorSql(cell);
					if (nota[p][1] != null) {
						if (nota[p][1].length() < 4
								&& GenericValidator.isInt(nota[p][1])
								&& (Integer.parseInt(nota[p][1])) > 0)
							x = 1;
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Ausencia justificada", posi++);
						// break;
					}
					// ausencia no justificada
					x = 0;
					cell = row
							.getCell((short) Area.Area[Area.AusenciaNoJus][Area.COL]);
					nota[p][2] = getValorSql(cell);
					if (nota[p][2] != null) {
						if (nota[p][2].length() < 4
								&& GenericValidator.isInt(nota[p][2])
								&& (Integer.parseInt(nota[p][2])) > 0)
							x = 1;
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Ausencia no justificada", posi++);
						// break;
					}
					// motivo
					x = 0;
					cell = row
							.getCell((short) Area.Area[Area.Motivo][Area.COL]);
					nota[p][3] = getValorSql(cell);
					if (nota[p][3] != null && motivo != null) {
						for (int k = 0; k < motivo.length; k++) {
							if (nota[p][3].equals(motivo[k][0])) {
								x = 1;
								break;
							}
						}
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Motivo de ausencia", posi++);
						// break;
					}
					// porcentaje de ausencia
					x = 0;
					cell = row
							.getCell((short) Area.Area[Area.Porcentaje][Area.COL]);
					nota[p][4] = getValorSql(cell);
					if (nota[p][4] != null) {
						if (nota[p][4].length() < 4
								&& GenericValidator.isInt(nota[p][4])
								&& GenericValidator
										.isInRange(
												Integer.parseInt((!nota[p][4]
														.equals("") ? nota[p][4]
														: "0")), 1, 100)) {
							x = 1;
						}
					} else
						x = 1;
					if (x == 0) {
						ponerFila("Porcentaje", posi++);
						// break;
					}
				}
			}
			if (!error)
				return false;
		} catch (Exception e) {
			System.out.println("Error validar Escala y motivo= " + e);
			return false;
		}
		return true;
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
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarEscalaLogro() {
		error = true;
		int z = 0, y = 0, x = 0;
		String log1;
		try {
			/* escala valorativa de la base de datos */
			escala2 = importarDAO.getEscala(1);
			/* validar que no se halla cambiado la escala valorativa */
			int num = sheet2.getPhysicalNumberOfRows();
			HSSFRow row3 = sheet2
					.getRow(Asignatura.Asig[Asignatura.Logros1][Asignatura.FIL]);
			int cel = row3.getPhysicalNumberOfCells();
			// row = sheet2.getRow(6);
			// int cel=row.getPhysicalNumberOfCells();
			nota2 = new String[num
					- Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.FIL]][cel
					- Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL]];
			int p = -1, q = 0;
			for (int i = Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.FIL]; i < num; i++) {
				p++;
				q = 0;
				row2 = sheet2.getRow(i);
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL]; j < cel; j++) {
						x = 0;
						cell = row.getCell((short) j);
						log1 = getValorSql(cell);
						nota2[p][q++] = getNotaRealLogro(log1);
						if (log1 != null && !log1.equals("")
								&& nota2[p][q - 1] != null
								&& !nota2[p][q - 1].equals("")) {// digito y si
																	// corresponde
							x = 1;
						}
						if (log1 == null) {// no digito nada
							x = 1;
						}
						if (x == 0) {
							// cell=row.createCell((short)(row.getPhysicalNumberOfCells()+1));
							cell = row.createCell((short) cel);
							cell.setCellValue("Campo 'Evaluacinn' no corresponde a los posibles valores (campo Abreviatura  de logro en la hoja 'Escala valorativa')");
							error = false;
							break;
						}
					}
				}
			}
			if (!error) {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error validar Escala=" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param num
	 * @param kk
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarNotaPreescolar(int num, int kk) {
		error = true;
		int y = 0, x = 0;
		try {
			int p = 0;
			/* PONER LOS DATOS DEL USUARIO EN LA MATRIZ DE DATOS */
			for (int i = Preescolar.Pree[Preescolar.Evaluacion][Preescolar.FIL]; i < num; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row
							.getCell((short) Preescolar.Pree[Preescolar.Evaluacion][Preescolar.COL]);
					nota[p++][kk] = getValorSql(cell);
				}
			}
			if (!error)
				return false;
		} catch (Exception e) {
			System.out.println("Error validando notas en preescolar=" + e);
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarCodigoDescriptorArea() {
		error = true;
		int y = 0, x = 0, i, j, k, l, m;
		String temp = null;
		String[] desc;
		String[] fichas = { "Fortalezas", "Dificultades", "Recomendaciones",
				"Estrategias" };
		try {
			int num = sheet2.getPhysicalNumberOfRows();
			nota2 = new String[num - Area.Area[Area.Codigo][Area.FIL]][4];
			int p = -1, q = 0;
			/* PONER LOS DATOS DEL USUARIO EN LA MATRIZ DE DATOS */
			for (i = Area.Area[Area.Codigo][Area.FIL]; i < num; i++) {
				p++;
				q = 0;
				row2 = sheet2.getRow(i);
				row = sheet.getRow(i);
				if (row != null) {
					for (j = Area.Area[Area.EvalFortaleza][Area.COL]; j < (Area.Area[Area.EvalEstrategia][Area.COL] + 1); j++) {
						cell = row.getCell((short) j);
						temp = getValorSql(cell);
						if (temp != null) {
							if (cell.getCellType() == 0)
								temp = temp.replace('.', ',');
							if (temp.startsWith(","))
								temp = temp.substring(1, temp.length());
							if (temp.endsWith(","))
								temp = temp.substring(0, temp.length() - 1);
							temp = temp.trim();
						}
						nota2[p][q++] = temp;
					}
				}
			}
			// for(i=0;i<nota2.length;i++){System.out.println("DATOS DE USUARIO="+nota2[i][0]+"--"+nota2[i][1]+"--"+nota2[i][2]+"--"+nota2[i][3]);}

			/* VERIFICAR QUE LOS CODIGOS CORRESPONDAN CON LA BASE DE DATOS */
			for (i = 0; i < nota2.length; i++) {
				// System.out.println("entra estudinate "+i);
				row = sheet.getRow(i + Area.Area[Area.Codigo][Area.FIL]);
				for (j = 0; j < nota2[i].length; j++) {
					if (nota2[i][j] != null && !nota2[i][j].equals("")) {
						desc = nota2[i][j].split(",");
						desc = getDescriptorReal(desc, "" + (j + 1));
						// System.out.println("entra notas "+j+"/longitud desc"+desc.length+" / ");
						if (desc != null) {
							nota2[i][j] = "";
							for (int zx = 0; zx < desc.length; zx++) {
								nota2[i][j] += (desc[zx] != null ? desc[zx]
										: "") + ",";
							}
							nota2[i][j] = nota2[i][j].substring(0,
									nota2[i][j].lastIndexOf(","));
							for (l = 0; l < desc.length; l++) {
								for (m = (l + 1); m < desc.length; m++) {
									// System.out.println("repetidos compara "+desc[m]+" con "+desc[l]);
									if (desc[m] != null && desc[l] != null
											&& desc[m].equals(desc[l])) {
										cell = row
												.createCell((short) (Area.Area[Area.EvalEstrategia][Area.COL] + 1));
										cell.setCellValue("La abreviatura de descriptor en '"
												+ fichas[j]
												+ "' se repite para el mismo estudiante y tipo de descriptor");
										error = false;
										break;
									}
								}
							}
							if (!error)
								return false;
							// System.out.println("next------------- ");
							for (k = 0; k < desc.length; k++) {
								x = 0;
								if (desc[k] == null)
									break;
								for (l = 0; l < (escala2 != null ? escala2.length
										: 0); l++) {
									// System.out.println("compara "+escala2[l][2]+" con "+""+(j+1)+" y "+escala2[l][1]+" con "+""+desc[k]);
									if (escala2[l][2].equals("" + (j + 1))
											&& escala2[l][1].equals(desc[k])) {
										// System.out.println("entro bien bd"+i);
										x = 1;
										break;
									}
								}
							}
							if (x == 0) {
								// System.out.println("pone error");
								cell = row
										.createCell((short) (Area.Area[Area.EvalEstrategia][Area.COL] + 1));
								cell.setCellValue("La abreviatura de descriptor en '"
										+ fichas[j]
										+ "' no existe o no pertenece al tipo de descriptor de la columna");
								error = false;
								break;
							}
						}
					}
				}
			}
			if (!error)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Error validando Descriptor en Evaluacinn de descriptor="
							+ e);
			return false;
		}
		return true;
	}

	/**
	 * @param a
	 * @param tipo
	 * @return<br> Return Type: String[]<br>
	 *             Version 1.1.<br>
	 */
	public String[] getDescriptorReal(String[] a, String tipo) {
		int i, l;
		String[] b = null;
		String tipoBD, nomBD;
		String nom;
		if (a != null) {
			b = new String[a.length];
			if (escala2 != null) {
				// for(l=0;l<escala2.length;l++){System.out.println("__="+escala2[l][1]+"//"+escala2[l][2]+"//"+escala2[l][3]);}
				for (i = 0; i < a.length; i++) {
					nom = a[i].trim();
					for (l = 0; l < escala2.length; l++) {
						// if(tipo.equals("3")){System.out.println("-"+escala2[l][2]+"-"+tipo+"-"+escala2[l][3]+"-"+a[i]+"-");}
						tipoBD = escala2[l][2].trim();
						nomBD = escala2[l][3].trim();
						if (tipoBD.equals(tipo) && nomBD.equals(nom)) {
							// System.out.println(""+escala2[l][2]+"//"+tipo+"//"+escala2[l][3]+"//"+a[i]);
							b[i] = escala2[l][1];
							break;
						}
					}
				}
			} else
				return null;
		}
		return b;
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
	 * @param n
	 * @return<br> Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String getNotaRealLogro(String n) {
		if (n == null)
			return null;
		for (int i = 0; i < escala2.length; i++) {
			if (escala2[i][1].equals(n.trim()))
				return escala2[i][0];
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
	public int[] getResultado() {
		return importarDAO.getResultado();
	}

	/**
	 * @return<br> Return Type: int[]<br>
	 *             Version 1.1.<br>
	 */
	public int[] getResultado2() {
		return importarDAO.getResultado2();
	}

	public ResultadoVO importarEstudiante(int tipo, String[] archivo,
			String plantilla, String download, String us, String metodologia) {
		error = true;
		ResultadoVO res = null;
		try {

			res = importarArtDAO.importarEstudiante(filtroPlantilla,
					estudiantesVO, metodologia);
			// System.out.println("ingresados" + res.getIngresados());
			// System.out.println("eliminados " + res.getEliminados());
			// System.out.println("actualizados " + res.getActualizados());

			if (res == null) {
				setMensaje("No se pudo importar los registros de estudiantes: "
						+ importarDAO.getMensaje());
				return null;
			}
			/*
			 * Logger.print(us,
			 * "Importacion Logro/Asig Inst:"+filtro2.getInstitucion
			 * ()+" Sede:"+filtro2
			 * .getSede()+" Jorn:"+filtro2.getJornada()+" Met:"
			 * +filtro2.getMetodologia
			 * ()+" Gra:"+filtro2.getGrado()+" Grupo:"+filtro2
			 * .getGrupo()+" Asig:"
			 * +filtro2.getAsignatura()+" Per:"+filtro2.getPeriodo(),
			 * "Importacinn de Plantilla de Logros/Asignatura: Periodo '"
			 * +filtro2
			 * .getPeriodo_()+"', Metodologia '"+filtro2.getMetodologia_(
			 * )+"', Asignatura '"
			 * +filtro2.getAsignatura_()+"', Grado '"+filtro2.
			 * getGrado_()+"', Grupo '"+filtro2.getGrupo_()+"'",
			 * filtro2.getPeriodo(), 6,1,this.toString());
			 */
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
					FileUtils.forceDelete(f); // System.out.println("FUE BORRADA LA IMPORTACION");
			} catch (IOException e) {
			}
		}
		return res;
	}

	/**
	 * @param tipo
	 * @param archivo
	 * @param plantilla
	 * @param download
	 * @param par
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarFormatoEstudiante(int tipo, String[] archivo,
			String plantilla, String download, String par[], String usu,
			String perfil) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		error = true;
		boolean bandera = true;
		// System.out.println("llega al metdo de ValidarFormatoEstudiante");
		// System.out.println("archivo 0" + archivo[0]);
		// System.out.println("archivo 1" + archivo[1]);
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet = workbook.getSheetAt(1);
			sheet2 = workbook.getSheetAt(2);
			int tipo1 = 10;
			if (!validarTipoPlantilla(tipo1)) {
				setMensaje("No es una plantilla de Estudiantes Articulacion");
				bandera = false;
				return false;
			}

			if (!validarEncabezadoAsignatura(par)) {
				if (!error)
					setMensaje("Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio, sede y jornada.");
				else
					setMensaje("El encabezado ha sido modificado y no es posible importar la plantilla Estudiantes Articulacion");
				error = true;
				bandera = false;
				return false;
			}

			filtroPlantilla = getEncabezadoArticulacion();
			estudiantesVO = getEstudiantesArticulacion();
			especialidades = importarArtDAO.getEspecialidades(filtroPlantilla
					.getInstitucion());
			// System.out.println("tengo el list de especialidades");
			if (!error) {// poner el archivo
				setMensaje("No se puede importar la plantilla Estudiantes porque la información de las filas esta incompleta o fue alterada, revise el archivo de inconsistencias");
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
			if (!getDatosArticulacion(estudiantesVO, especialidades,
					filtroPlantilla)) {
				setMensaje("No se puede importar la pantilla Estudiantes porque los datos ingresados tienen errores, revise el archivo de inconsistencias");
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
			// System.out.println("tamano nino=="+estudiantesVO.size());
			// VALIDAR QUE CORRESPONDA CON LA HOJA OCULTA
			// sheet = workbook.getSheetAt(1);

			/*
			 * if(!validarNumeroEstudiantesAsignatura()){ setAdvertencia("-"); }
			 * filtro2=getEncabezadoAsignatura(); //VALIDAR SI ESTA CERRADO EL
			 * PERIODO if(!importarDAO.getCierreAsignatura(filtro2)){
			 * setMensaje(
			 * "No se puede importar la evaluacinn porque el grupo esta cerrado para esta Asignatura y Periodo\n El grupo solo puede ser abierto por el Rector del colegio"
			 * ); bandera=false; return false; } //RESTRICCIONES DE HORARIO PARA
			 * EL ASIG.
			 * if(!importarDAO.getValidacionHorarioAsig(filtro2,usu,perfil)){
			 * setMensaje(
			 * "No se puede importar la evaluacinn porque no tiene permisos de horario"
			 * ); bandera=false; return false; } //VALIDAR ESTUDIANTES/
			 */
			/*
			 * if(!validarEstudiantesAsignatura(filtro2)){ setMensaje(
			 * "Los datos de los estudiantes han sido modificados y no es posible importar la evaluacinn.\n Posiblemente la información fue modificada despues de la generacinn de la plantilla"
			 * ); if(!error){ f=new File(download); if(!f.exists())
			 * FileUtils.forceMkdir(f); fileOut= new
			 * FileOutputStream(download+archivo[1]);//CREAR UN ARCHIVO
			 * workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			 * fileOut.flush();//CERRAR fileOut.close(); } return false; }/*
			 * //VALIDAR ESCALA VALORATIVA y LOS DEMAS DE EVALUACION EN CELDA DE
			 * EVALUACION/ if(!validarEscalaMotivoAsignatura()){ setMensaje(
			 * "La escala valorativa no corresponde a los datos de la hoja 'Escala_valorativa'. \n Posiblemente la información fue modificada despues de la generacinn de la plantilla"
			 * ); if(!error){ f=new File(download); if(!f.exists())
			 * FileUtils.forceMkdir(f); fileOut= new
			 * FileOutputStream(download+archivo[1]);//CREAR UN ARCHIVO
			 * workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			 * fileOut.flush();//CERRAR fileOut.close(); } return false; }
			 * //VALIDAR LOGROS if(!validarLogrosAsignatura()){ setMensaje(
			 * "La información de los logros fue modificada y no es posible importar los datos. \n Posiblemente la información fue modificada despues de la generacinn de la plantilla"
			 * ); bandera=false; return false; } //VALIDAR ESCALA DE LOGROS
			 * if(!validarEscalaLogro()){ setMensaje(
			 * "La escala valorativa de logros no corresponde a los datos de la hoja 'Escala_valorativa'. \n Posiblemente la informacion fue modificada despues de la generacinn de la plantilla"
			 * ); if(!error){ f=new File(download); if(!f.exists())
			 * FileUtils.forceMkdir(f); fileOut= new
			 * FileOutputStream(download+archivo[1]);//CREAR UN ARCHIVO
			 * workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			 * fileOut.flush();//CERRAR fileOut.close(); } return false; }
			 */
			// BORRAR LA NOTA DE LOS ESTUDIANTES BASURA
			// validarEstudiantesFaltantes();
		} catch (NullPointerException e) {
			setMensaje("No es un archivo de plantilla vnlido, Faltan filas importantes");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			setMensaje("No es un archivo de plantilla vnlido, Faltan hojas en el libro");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
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

	public void validarEstudiantesFaltantes() {
		int t = -1;
		if (estNoBD != null) {
			if (!estNoBD.isEmpty()) {
				Iterator iterator = estNoBD.iterator();
				while (iterator.hasNext()) {
					t = Integer.parseInt((String) iterator.next());
					if (nota != null && t < nota.length) {
						nota[t][0] = null;
					}
					if (nota2 != null && t < nota2.length) {
						nota2[t][0] = null;
					}
				}
			}
		}
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarNumeroEstudiantesAsignatura() {
		int num1 = sheet.getPhysicalNumberOfRows();
		int num2 = sheet2.getPhysicalNumberOfRows();
		if (num1 == num2)
			return true;
		num2 = num2 - Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL];
		int cont = 0;
		int band = 0;
		int pos[] = { Asignatura.Asig[Asignatura.Codigo][Asignatura.COL],
				Asignatura.Asig[Asignatura.Apellido][Asignatura.COL],
				Asignatura.Asig[Asignatura.Nombre][Asignatura.COL] };
		int j = 0;
		String valor = null;
		for (int i = Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]; i < num1; i++) {
			band = 0;
			row = sheet.getRow(i);
			if (row == null)
				break;
			for (j = 0; j < pos.length; j++) {
				cell = row.getCell((short) pos[j]);
				valor = getValorSql(cell);
				if (valor != null && !valor.equals("")) {
					band++;
				}
			}
			if (band > 0)
				cont++;
		}
		if (cont < num2) {
			return false;
		}
		if (cont > num2) {
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarNumeroEstudiantesArea() {
		int num1 = sheet.getPhysicalNumberOfRows();
		int num2 = sheet2.getPhysicalNumberOfRows();
		if (num1 == num2)
			return true;
		num2 = num2 - Area.Area[Area.Codigo][Area.FIL];
		int cont = 0;
		int band = 0;
		int pos[] = { Area.Area[Area.Codigo][Area.COL],
				Area.Area[Area.Apellido][Area.COL],
				Area.Area[Area.Nombre][Area.COL] };
		int j = 0;
		String valor = null;
		for (int i = Area.Area[Area.Codigo][Area.FIL]; i < num1; i++) {
			band = 0;
			row = sheet.getRow(i);
			if (row == null)
				break;
			for (j = 0; j < pos.length; j++) {
				cell = row.getCell((short) pos[j]);
				valor = getValorSql(cell);
				if (valor != null && !valor.equals("")) {
					band++;
				}
			}
			if (band > 0)
				cont++;
		}
		if (cont < num2) {
			return false;
		}
		if (cont > num2) {
			return false;
		}
		return true;
	}

	/**
	 * @return<br> Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean validarNumeroEstudiantesPreescolar() {
		int num1 = sheet.getPhysicalNumberOfRows();
		int num2 = sheet2.getPhysicalNumberOfRows();
		if (num1 == num2)
			return true;
		num2 = num2 - Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL];
		int cont = 0;
		int band = 0;
		int pos[] = { Preescolar.Pree[Preescolar.Codigo][Preescolar.COL],
				Preescolar.Pree[Preescolar.Apellido][Preescolar.COL],
				Preescolar.Pree[Preescolar.Nombre][Preescolar.COL] };
		int j = 0;
		String valor = null;
		for (int i = Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL]; i < num1; i++) {
			band = 0;
			row = sheet.getRow(i);
			if (row == null)
				break;
			for (j = 0; j < pos.length; j++) {
				cell = row.getCell((short) pos[j]);
				valor = getValorSql(cell);
				if (valor != null && !valor.equals("")) {
					band++;
				}
			}
			if (band > 0)
				cont++;
		}
		if (cont < num2) {
			return false;
		}
		if (cont > num2) {
			return false;
		}
		return true;
	}

	/**
	 * @param s
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
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
		// nombre institucion
		fila = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
		encabezado.setInstitucion_(getValorSql(cell2));

		// *************SEDE*******************

		// codigo SEDE
		fila = ExcelVO.plantilla[ExcelVO.sed][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.sed][ExcelVO.COL]);
		encabezado.setSede(getValorSql(cell2));
		// nombre sede
		fila = ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
		encabezado.setSede_(getValorSql(cell2));

		// *************JORNADA*******************
		// codigo jornada
		fila = ExcelVO.plantilla[ExcelVO.jor][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.jor][ExcelVO.COL]);
		encabezado.setJornada(getValorSql(cell2));
		// nombre jornada
		fila = ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
		encabezado.setJornada_(getValorSql(cell2));

		// *************GRADO*******************
		// grado
		fila = ExcelVO.plantilla[ExcelVO.grad][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.grad][ExcelVO.COL]);
		encabezado.setGrado(getValorSql(cell2));

		// grupo
		fila = ExcelVO.plantilla[ExcelVO.grupo_][ExcelVO.FIL];
		row2 = sheet2.getRow(fila);
		cell2 = row2
				.getCell((short) ExcelVO.plantilla[ExcelVO.grupo_][ExcelVO.COL]);
		encabezado.setGrupo(getValorSql(cell2));

		// System.out.println("Institucion 1" + encabezado.getInstitucion());
		// System.out.println("Institucion 2" + encabezado.getInstitucion_());
		// System.out.println("Sede 1 " + encabezado.getSede());
		// System.out.println("Sede 2 " + encabezado.getSede_());
		// System.out.println("Grado 1 " + encabezado.getGrado());
		// System.out.println("Jornada 1 " + encabezado.getJornada());
		// System.out.println("Jornada 2 " + encabezado.getJornada_());
		return encabezado;
	}

	public boolean getDatosArticulacion(List estudiantes, List especialidades,
			DatosVO filtroPlantilla) {
		boolean band = false;
		EspecialidadesVO especi = null;
		int num = sheet2.getPhysicalNumberOfRows();
		EstudianteVO estu = null;
		try {
			for (int i = 0; i < estudiantes.size(); i++) {
				band = false;
				estu = (EstudianteVO) estudiantes.get(i);
				// System.out.println("Especialidad nueva "+
				// estu.getEspecialidad());
				// System.out.println("Tanano de especialidades"+
				// especialidades.size());

				for (int j = 0; j < especialidades.size(); j++) {
					especi = (EspecialidadesVO) especialidades.get(j);
					if (especi.getCodigo() == estu.getEspecialidad()) {
						band = true;
						// System.out.println("Especialidad antigua "+
						// especi.getCodigo());
						break;
					}

				}
				if (!band) {
					// error
					// System.out.println("index " + estu.getIndex());
					// System.out.println("ofila"+
					// ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]);
					HSSFRow row3 = sheet.getRow(estu.getIndex());
					cell = row3
							.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
					cell.setCellValue("Campo 'Especialidad' no corresponde a los datos originales de la plantilla");
					error = false;
				}
			}
			if (!error)
				return false;
			// **********************************EVALUAMOS EL SEMESTRE el
			// nuevo****************
			for (int i = 0; i < estudiantes.size(); i++) {
				band = false;
				estu = (EstudianteVO) estudiantes.get(i);
				// grupo
				String grado = filtroPlantilla.getGrado();
				// System.out.println("Grado = " + grado);

				if (estu.getSemestre() == 1 || estu.getSemestre() == 2
						|| estu.getSemestre() == 3 || estu.getSemestre() == 4) {
					// System.out.println("esta bien la cosa");
					band = true;

				}

				if (!band) {
					// error
					// System.out.println("index " + estu.getIndex());
					// System.out.println("ofila"+
					// ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]);
					HSSFRow row3 = sheet.getRow(estu.getIndex());
					cell = row3
							.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
					cell.setCellValue("Campo 'Semestre' no corresponde a los admitidos por este Grado");
					error = false;
				}
			}
			if (!error)
				return false;

			/*
			 * // **********************************EVALUAMOS EL
			 * SEMESTRE**************** for(int i=0;i<estudiantes.size();i++) {
			 * band=false; estu=(EstudianteVO)estudiantes.get(i); //grupo String
			 * grado=filtroPlantilla.getGrado();
			 * System.out.println("Grado = "+grado); if (grado.equals("10")) {
			 * System.out.println("entro a que el grado es 10");
			 * System.out.println("Semestre 10= "+estu.getSemestre());
			 * if(estu.getSemestre()==1||estu.getSemestre()==2) {
			 * System.out.println("esta bien la cosa"); band=true; //break; } }
			 * if (grado.equals("11")) {
			 * System.out.println("entro a que el grado es 11");
			 * System.out.println("Semestre 11 = "+estu.getSemestre());
			 * if(estu.getSemestre()==3||estu.getSemestre()==4) {
			 * System.out.println("esta bien la cosa en que es once");
			 * band=true; //break; } } if(!band){ //error
			 * System.out.println("index "+estu.getIndex());
			 * System.out.println("ofila"
			 * +ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]); HSSFRow
			 * row3=sheet.getRow(estu.getIndex());
			 * cell=row3.createCell((short)(ExcelVO
			 * .plantilla[ExcelVO.nivelado][ExcelVO.COL]+1)); cell.setCellValue(
			 * "Campo 'Semestre' no corresponde a los admitidos por este Grado"
			 * ); error=false; } } if(!error) return false;
			 */
			// ***********************EVALUAMOS EL
			// GURPO*****************************
			/*
			 * for(int i=0;i<estudiantes.size();i++) { String evaluacion="";
			 * band=false; estu=(EstudianteVO)estudiantes.get(i); //grupo String
			 * institucion=filtroPlantilla.getInstitucion(); List
			 * l=importarArtDAO.getAllgrupo(institucion,estu.getEspecialidad());
			 * long grupo=0; for(int j=0;j<l.size();j++){
			 * grupo=((Long)l.get(j)).longValue();
			 * System.out.println("grupos traidos "+grupo);
			 * 
			 * if(grupo==estu.getGrupo()) { //band=true;
			 * if(estu.getSemestre()==1) { long uno=100; long dos=199;
			 * 
			 * System.out.println("esta en grupo semestre 1"+estu.getGrupo());
			 * if(estu.getGrupo()>uno&&estu.getGrupo()<dos) {
			 * System.out.println("entro a los n umeritos"); band=true; break; }
			 * else band=false; } if(estu.getSemestre()==2) { long tres=200;
			 * long cuatro=299; System.out.println("esta en grupo semestre 2");
			 * if(estu.getGrupo()>tres&&estu.getGrupo()<cuatro) { band=true;
			 * break; } }
			 * 
			 * }
			 * 
			 * 
			 * 
			 * } if(!band){ //error
			 * System.out.println("index "+estu.getIndex());
			 * System.out.println("ofila"
			 * +ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]); HSSFRow
			 * row3=sheet.getRow(estu.getIndex());
			 * cell=row3.createCell((short)(ExcelVO
			 * .plantilla[ExcelVO.nivelado][ExcelVO.COL]+1)); cell.setCellValue(
			 * "Campo 'Grupo' no corresponde al semestre indicado o a los Parnmetros"
			 * ); error=false; } } if(!error) return false;
			 */
			// **********************************EVALUAMOS EL
			// nivelado****************
			for (int i = 0; i < estudiantes.size(); i++) {
				band = false;
				estu = (EstudianteVO) estudiantes.get(i);
				// nivelado

				// System.out.println("Grado = " + estu.getNivelado());
				if (estu.getNivelado().equals("X")
						|| estu.getNivelado().equals("x")
						|| estu.getNivelado().equals("")) {

					// System.out.println("Nivelado si esta bien");
					band = true;
					// break;

				}

				if (!band) {
					// error
					// System.out.println("index " + estu.getIndex());
					// System.out.println("ofila"+
					// ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL]);
					HSSFRow row3 = sheet.getRow(estu.getIndex());
					cell = row3
							.createCell((short) (ExcelVO.plantilla[ExcelVO.nivelado][ExcelVO.COL] + 1));
					cell.setCellValue("Campo 'Nivelado' no corresponde al Nivel indicado en los Pnrametros");
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
}