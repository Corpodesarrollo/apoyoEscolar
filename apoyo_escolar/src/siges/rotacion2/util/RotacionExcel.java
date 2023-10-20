package siges.rotacion2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.rotacion2.beans.Validacion;
import siges.util.beans.ValidacionRotacion;

/**
 * siges.rotacion2.util<br>
 * Funcinn: <br>
 */

public class RotacionExcel {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;
	private HSSFCell cell;
	private String mensaje;

	public boolean plantillaValidacion(String[] config, Validacion v) {
		FileInputStream input;
		try {
			input = new FileInputStream(config[0] + config[1]);// TRAER EL
																// ARCHIVO
			workbook = new HSSFWorkbook(input);// TRAER UN ARCHIVO DE PLANTILLA
			if (workbook == null) {
				setMensaje("LIBRO NO EXISTE");
				return false;
			}
			// PONER HOJA 1
			sheet = workbook.getSheetAt(0);// OBTENER HOJA 1
			setEncabezado(v);
			setHoja1(v);
			// PONER HOJA 2
			sheet = workbook.getSheetAt(1);// OBTENER HOJA 1
			setEncabezado(v);
			setHoja2(v);
			// PONER HOJA 3
			sheet = workbook.getSheetAt(2);// OBTENER HOJA 1
			setEncabezado(v);
			setHoja3(v);
			File f = new File(config[2]);
			if (!f.exists())
				FileUtils.forceMkdir(f);
			FileOutputStream fileOut = new FileOutputStream(config[2]
					+ config[3]);// CREAR UN ARCHIVO
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			setMensaje(e.toString());
			System.out.println("Error Validacion: " + e.toString());
			return false;
		}
		return true;
	}

	public void setEncabezado(Validacion v) {
		java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		int numFila = 1;
		if (v != null) {
			// institucion
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Institucion][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Institucion][ValidacionRotacion.COL]);
			cell.setCellValue((String) v.getInstitucion_());
			// SEDE
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Sede][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Sede][ValidacionRotacion.COL]);
			cell.setCellValue((String) v.getSede_());
			// JORNADA
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Jornada][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Jornada][ValidacionRotacion.COL]);
			cell.setCellValue((String) v.getJornada_());
			// GRADO
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Grado][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Grado][ValidacionRotacion.COL]);
			cell.setCellValue((String) v.getNombreGrado());
			// GRUPO
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Grupo][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Grupo][ValidacionRotacion.COL]);
			cell.setCellValue((String) v.getNombreGrupo());
			// FECHA
			numFila = ValidacionRotacion.Validacion[ValidacionRotacion.Fecha][ValidacionRotacion.FIL];
			row = sheet.getRow(numFila);
			cell = row
					.getCell((short) ValidacionRotacion.Validacion[ValidacionRotacion.Fecha][ValidacionRotacion.COL]);
			cell.setCellValue((String) d.toString());
		}
	}

	public void setHoja1(Validacion v) {
		int numFila = 1;
		int numCol1 = 1;
		int numCol2 = 1;
		if (v != null) {
			Collection a = v.getHoja1();
			if (a != null) {
				if (!a.isEmpty()) {
					numFila = ValidacionRotacion.Validacion[ValidacionRotacion.IAsignatura][ValidacionRotacion.FIL];
					numCol1 = ValidacionRotacion.Validacion[ValidacionRotacion.IAsignatura][ValidacionRotacion.COL];
					numCol2 = ValidacionRotacion.Validacion[ValidacionRotacion.IGrado][ValidacionRotacion.COL];
					Object[] o;
					Iterator iterator = a.iterator();
					while (iterator.hasNext()) {
						o = ((Object[]) iterator.next());
						// fila
						row = sheet.createRow(numFila++);
						// columna 1
						cell = row.createCell((short) numCol1);
						cell.setCellValue((String) (o[4]));
						// columna 2
						cell = row.createCell((short) numCol2);
						cell.setCellValue((String) (o[5]));
					}
				}
			}
		}
	}

	public void setHoja3(Validacion v) {
		int numFila = 1;
		int numCol1 = 1;
		int numCol2 = 1;
		if (v != null) {
			Collection a = v.getHoja3();
			if (a != null) {
				if (!a.isEmpty()) {
					numFila = ValidacionRotacion.Validacion[ValidacionRotacion.IIIDocumento][ValidacionRotacion.FIL];
					numCol1 = ValidacionRotacion.Validacion[ValidacionRotacion.IIIDocumento][ValidacionRotacion.COL];
					numCol2 = ValidacionRotacion.Validacion[ValidacionRotacion.IIINombre][ValidacionRotacion.COL];
					Object[] o;
					Iterator iterator = a.iterator();
					while (iterator.hasNext()) {
						o = ((Object[]) iterator.next());
						// fila
						row = sheet.createRow(numFila++);
						// columna 1
						cell = row.createCell((short) numCol1);
						cell.setCellValue((String) (o[0]));
						// columna 2
						cell = row.createCell((short) numCol2);
						cell.setCellValue((String) (o[1]));
					}
				}
			}
		}
	}

	public void setHoja2(Validacion v) {
		int numFila = 1;
		if (v != null) {
			Collection a = v.getHoja2();
			if (a != null) {
				if (!a.isEmpty()) {
					numFila = ValidacionRotacion.Validacion[ValidacionRotacion.IIGrado][ValidacionRotacion.FIL];
					int[] zz = {
							ValidacionRotacion.Validacion[ValidacionRotacion.IIGrado][ValidacionRotacion.COL],
							ValidacionRotacion.Validacion[ValidacionRotacion.IIAsignatura][ValidacionRotacion.COL],
							ValidacionRotacion.Validacion[ValidacionRotacion.IIEspacio][ValidacionRotacion.COL],
							ValidacionRotacion.Validacion[ValidacionRotacion.IICapacidad][ValidacionRotacion.COL],
							ValidacionRotacion.Validacion[ValidacionRotacion.IIGrupo][ValidacionRotacion.COL],
							ValidacionRotacion.Validacion[ValidacionRotacion.IICupo][ValidacionRotacion.COL] };
					Object[] o;
					Iterator iterator = a.iterator();
					while (iterator.hasNext()) {
						o = ((Object[]) iterator.next());
						// fila
						row = sheet.createRow(numFila++);
						for (int t = 0; t < zz.length; t++) {
							// columna
							cell = row.createCell((short) zz[t]);
							cell.setCellValue((String) (o[t]));
						}
					}
				}
			}
		}
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		mensaje += s;
	}

	/**
	 * Funcinn: retorna la lista de mensajes a <BR>
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}

}
