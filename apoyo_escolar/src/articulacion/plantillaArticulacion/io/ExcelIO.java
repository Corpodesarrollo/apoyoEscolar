/**
 * 
 */
package articulacion.plantillaArticulacion.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.login.beans.Login;
import articulacion.plantillaArticulacion.vo.DatosVO;
import articulacion.plantillaArticulacion.vo.EspecialidadesVO;
import articulacion.plantillaArticulacion.vo.EstudianteVO;
import siges.perfil.vo.Url;
import articulacion.plantillaArticulacion.vo.plantilla.ExcelVO;
import siges.util.beans.Asignatura;

/**
 * 9/08/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class ExcelIO {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet, sheet2, sheet3;
	private HSSFRow row, row2, row3;
	private HSSFCell cell, cell2, cell3;

	public void plantilla(List datos, String usuVO, long grado,
			DatosVO datosVO, List especialidades, Url url, long[] tipos)
			throws Exception {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		try {
			input = new FileInputStream(url.getPathPlantilla()
					+ url.getNombrePlantilla());// TRAER EL ARCHIVO
			workbook = new HSSFWorkbook(input);// TRAER UN ARCHIVO DE PLANTILLA
			if (workbook == null) {
				throw new Exception("No hay libro");
			}

			// hoja de escalas
			sheet = workbook.getSheetAt(1);// OBTENER HOJA DE ESCALAS
			sheet2 = workbook.getSheetAt(0);// OBTENER HOJA DE ESCALAS
			sheet3 = workbook.getSheetAt(2);// OBTENER HOJA DE ESCALAS
			setEscala(datos);
			EncabezadoEstudiantes(usuVO, grado, datosVO, tipos);
			setEspecialidades(especialidades);

			// poner el archivo
			File f = new File(url.getPathDescarga());
			if (!f.exists())
				FileUtils.forceMkdir(f);
			fileOut = new FileOutputStream(url.getPathDescarga()
					+ url.getNombreDescarga());// CREAR UN ARCHIVO
			workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();// CERRAR
			fileOut.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Plantilla prueba: " + e.toString());
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Plantilla prueba: " + e.toString());
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}

		}
	}

	private void setEscala(List datos) throws Exception {
		if (datos != null && datos.size() > 0) {
			EstudianteVO estudianteVO = null;
			int numFila = ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL];
			for (int i = 0; i < datos.size(); i++) {
				estudianteVO = (EstudianteVO) datos.get(i);
				row = sheet.createRow(numFila++);
				// System.out.println("pinta estudiante "+estudianteVO.getEspecialidad()+"//"+estudianteVO.getSemestre()+"//"+estudianteVO.getNivelado());
				// codigo
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.tipoDocumento][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getAbreviatura());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getDocumento());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getApellidos1() + " "
						+ estudianteVO.getApellidos2());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getNombre1() + " "
						+ estudianteVO.getNombre2());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.nomEspecialidad][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getEspecialidad());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.nomSemestre][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getSemestre());
				cell = row
						.createCell((short) ExcelVO.plantilla[ExcelVO.nomNivelado][ExcelVO.COL]);
				cell.setCellValue(estudianteVO.getNivelado());
			}
			int numFila2 = ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.FIL];
			for (int y = 0; y < datos.size(); y++) {
				estudianteVO = (EstudianteVO) datos.get(y);
				row3 = sheet3.createRow(numFila2++);
				cell3 = row3
						.createCell((short) ExcelVO.plantilla[ExcelVO.numeroId][ExcelVO.COL]);
				cell3.setCellValue(estudianteVO.getCodigo());
				cell3 = row3
						.createCell((short) ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
				cell3.setCellValue(estudianteVO.getApellidos1() + " "
						+ estudianteVO.getApellidos2());
				cell3 = row3
						.createCell((short) ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
				cell3.setCellValue(estudianteVO.getNombre1() + " "
						+ estudianteVO.getNombre2());
				// Abreviatura
				cell3 = row3
						.createCell((short) ExcelVO.plantilla[ExcelVO.tipoDocumento][ExcelVO.COL]);
				cell3.setCellValue(estudianteVO.getAbreviaturaCodigo());

			}
		}
	}

	private void setEspecialidades(List especialidades) throws Exception {
		if (especialidades != null && especialidades.size() > 0) {
			EspecialidadesVO especialidadesVO = null;
			int numFila1 = ExcelVO.plantilla2[ExcelVO.idEspecialidad][ExcelVO.FIL];
			for (int i = 0; i < especialidades.size(); i++) {

				especialidadesVO = (EspecialidadesVO) especialidades.get(i);
				row2 = sheet2.createRow(numFila1++);
				// codigo
				cell2 = row2
						.createCell((short) ExcelVO.plantilla2[ExcelVO.idEspecialidad][ExcelVO.COL]);
				cell2.setCellValue(especialidadesVO.getCodigo());
				cell2 = row2
						.createCell((short) ExcelVO.plantilla2[ExcelVO.especialidad][ExcelVO.COL]);
				cell2.setCellValue(especialidadesVO.getEspecialidad());

			}
		}
	}

	private void EncabezadoEstudiantes(String usuVO, long grado,
			DatosVO datosVO, long[] tipos) throws Exception {
		int numFila = 1;
		if (usuVO != null && grado != 0 && datosVO != null) {
			// institucion
			numFila = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			cell.setCellValue(usuVO);

			// hoja oculta institucion 2
			numFila = ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			cell3.setCellValue(usuVO);

			// sede
			numFila = ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			cell.setCellValue(datosVO.getSede_());

			// hoja oculta sede 2
			numFila = ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getSede_());

			// jornada
			numFila = ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			// System.out.println("JORNADA= "+datosVO.getJornada_());
			cell.setCellValue(datosVO.getJornada_());

			// hoja oculta jornada
			numFila = ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			// System.out.println("JORNADA= "+datosVO.getJornada_());
			cell3.setCellValue(datosVO.getJornada_());

			// fecha
			numFila = ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.COL]);
			cell.setCellValue(datosVO.getFecha());

			// hoja oculta fecha
			numFila = ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getFecha());

			// grado
			numFila = ExcelVO.plantilla[ExcelVO.grado][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.grado][ExcelVO.COL]);
			if (grado == 10)
				cell.setCellValue("Dncimo");
			else
				cell.setCellValue("Undncimo");

			// hoja oculta grado
			numFila = ExcelVO.plantilla[ExcelVO.grado][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.grado][ExcelVO.COL]);
			if (grado == 10)
				cell3.setCellValue("Dncimo");
			else
				cell3.setCellValue("Undncimo");

			// tipo es la llave para poder ver si puedo luego importar el
			// archivo
			numFila = ExcelVO.plantilla[ExcelVO.llave][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.llave][ExcelVO.COL]);
			cell3.setCellValue(10);

			// datos hoja oculta

			// ins
			numFila = ExcelVO.plantilla[ExcelVO.ins][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.ins][ExcelVO.COL]);
			cell3.setCellValue(tipos[0]);

			// sed
			numFila = ExcelVO.plantilla[ExcelVO.sed][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.sed][ExcelVO.COL]);
			cell3.setCellValue(tipos[1]);

			// jor
			numFila = ExcelVO.plantilla[ExcelVO.jor][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.jor][ExcelVO.COL]);
			cell3.setCellValue(tipos[2]);

			// grad
			numFila = ExcelVO.plantilla[ExcelVO.grad][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.grad][ExcelVO.COL]);
			cell3.setCellValue(tipos[3]);

			// grupo oculto
			numFila = ExcelVO.plantilla[ExcelVO.grupo_][ExcelVO.FIL];
			row3 = sheet3.getRow(numFila);
			cell3 = row3
					.getCell((short) ExcelVO.plantilla[ExcelVO.grupo_][ExcelVO.COL]);
			cell3.setCellValue(tipos[4]);

			// El grupo
			// System.out.println("La cosa que voy a insertar es "+datosVO.getGrupo_());
			numFila = ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL]);
			cell.setCellValue(datosVO.getGrupo_());

		}
	}

	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'n')
				.replace('n', 'N').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'c')
				.replace(':', '_').replace('.', '_').replace('/', '_').replace(
				'\\', '_'));
	}
}
