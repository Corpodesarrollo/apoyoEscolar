package siges.reporte;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GenerarCarnet {
	private String pathBajar;
	private String pathTemporal;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;
	private HSSFCell cell;
	private HSSFWorkbook workbookNuevo;
	private HSSFSheet sheetNuevo;
	private HSSFRow rowNuevo;
	private HSSFCell cellNuevo;
	private String mensaje;// mensaje en caso de error

	public GenerarCarnet() {
		mensaje = "";
	}

	public boolean generar(String a, String b, String[][] c) {
		// System.out.println("a="+a);
		// System.out.println("b="+b);
		FileInputStream input;
		try {
			input = new FileInputStream(a);// TRAER EL ARCHIVO
			workbook = new HSSFWorkbook(input);// TRAER UN ARCHIVO DE PLANTILLA
			if (workbook == null) {
				setMensaje("LIBRO NO EXISTE");
				return false;
			}
			sheet = workbook.getSheetAt(0);// OBTENER HOJA DE LOS DATOS
			if (sheet == null) {
				setMensaje("HOJA NO EXISTE");
				return false;
			}
			row = sheet.getRow(0);// OBTENER LA FILA EN LA QUE ESTA LOS TITULOS
			if (row == null) {
				setMensaje("FILA NO EXISTE");
				return false;
			}
			cell = row.getCell((short) 0);// OBTENER LA CELDA
			if (cell == null) {
				setMensaje("CELDA NO EXISTE");
				return false;
			}

			workbookNuevo = new HSSFWorkbook();// TRAER UN ARCHIVO DE PLANTILLA
			HSSFSheet sheetNuevo = workbookNuevo.createSheet("HOJA 1");

			HSSFRow rowNuevo = sheetNuevo.createRow((short) 0);

			HSSFCell cellNuevo = rowNuevo.createCell((short) 0);
			cellNuevo.setCellValue(1);

			FileOutputStream fileOut = new FileOutputStream(b);// CREAR UN
																// ARCHIVO
			workbookNuevo.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();// CERRAR
			fileOut.close();
			input.close();
		} catch (Exception e) {
			setMensaje(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		mensaje += "  - " + s + "\n";
	}

	public String getMensaje() {
		return mensaje;
	}
}
