package siges.batch.importar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.importar.dao.ImportarDAO;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import siges.util.Logger;
import siges.util.Properties;
import siges.util.beans.Area;
import siges.util.beans.Asignatura;
import siges.util.beans.BateriaDescriptor;
import siges.util.beans.BateriaLogro;
import siges.util.beans.Preescolar;

/**
 * Nombre: Excel<BR>
 * Descripcinn: Servicio que accede a los archivos de excel para leerlos o para
 * generar plantillas<BR>
 * Funciones de la pngina: Validar los datos de los archivos excel y generar
 * archivos excel con las plantillas del sistema <BR>
 * Entidades afectadas: <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class Excel {
	private String pathBajar;
	private String pathTemporal;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet, sheet2;
	private HSSFRow row, row2;
	private HSSFCell cell, cell2;
	private HSSFWorkbook workbookNuevo;
	private HSSFSheet sheetNuevo;
	private HSSFRow rowNuevo;
	private HSSFCell cellNuevo;
	private String mensaje;// mensaje en caso de error
	private ResourceBundle rb2;
	private Iterator iterator, iterator2, iterator3;
	private Object[] o = null;
	private Object[] o2 = null;
	private boolean error;
	private Collection list;
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer fecha = new Integer(java.sql.Types.DATE);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private String enca[][];
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
	private String nivel;
	private ImportarDAO importarDAO;
	private GenericValidator validator;
	Logros filtro;
	FiltroPlantilla filtro2;
	Collection estNoBD;

	/**
	 * Constructor
	 * 
	 * @param u
	 */
	public Excel(ImportarDAO u) {
		this.importarDAO = u;
		rb2 = ResourceBundle.getBundle("siges.importar.bundle.importar");
		mensaje = "";
		error = true;
		o = new Object[2];
		validator = new GenericValidator();
	}

	/**
	 * Funcinn: vALIDA que la plantilla que se desee subir pertenesca al tipo de
	 * archivo a subir <BR>
	 * 
	 * @param
	 * @return
	 **/
	public boolean validarTipoPlantilla(int n) {
		try {
			int fila = 0;
			int col = 0;
			long val;
			switch (n) {
			case Properties.PLANTILLALOGROASIG:
				fila = Asignatura.Asig[Asignatura.Tipo][Asignatura.FIL];
				col = Asignatura.Asig[Asignatura.Tipo][Asignatura.COL];
				break;
			case Properties.PLANTILLAAREADESC:
				fila = Area.Area[Area.Tipo][Area.FIL];
				col = Area.Area[Area.Tipo][Area.COL];
				break;
			case Properties.PLANTILLAPREE:
				fila = Preescolar.Pree[Preescolar.Tipo][Preescolar.FIL];
				col = Preescolar.Pree[Preescolar.Tipo][Preescolar.COL];
				break;
			case Properties.PLANTILLABATLOGRO:
				fila = BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.FIL];
				col = BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.COL];
				break;
			case Properties.PLANTILLABATDESCRIPTOR:
				fila = BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.FIL];
				col = BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.COL];
				break;
			}
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			val = Long.parseLong(cell2.getStringCellValue());
			// System.out.println("------------------------------------------------< "
			// +val+" != "+n );
			if (val != n)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida el encabezado de la evaluacion de logros <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarEncabezadoAsignatura(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = Asignatura.Asig[Asignatura.Institucion2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Institucion2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// institucion con respecto a login
			fila = Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (!cell2.getStringCellValue().equals(par[0])) {
				error = false;
				return false;
			}
			// sede con respecto a login
			if (!par[1].equals("")) {
				fila = Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
				col = Asignatura.Asig[Asignatura.Sede1][Asignatura.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[1])) {
					error = false;
					return false;
				}
			}
			// jornada con respecto a login
			if (!par[2].equals("")) {
				fila = Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
				col = Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[2])) {
					error = false;
					return false;
				}
			}
			// sede
			fila = Asignatura.Asig[Asignatura.Sede2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Sede2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// jornada
			fila = Asignatura.Asig[Asignatura.Jornada2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Jornada2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grado
			fila = Asignatura.Asig[Asignatura.Grado2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Grado2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grupo
			fila = Asignatura.Asig[Asignatura.Grupo2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Grupo2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// asignatura
			fila = Asignatura.Asig[Asignatura.Asignatura2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Asignatura2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// periodo
			fila = Asignatura.Asig[Asignatura.Periodo2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Periodo2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// docente
			fila = Asignatura.Asig[Asignatura.Docente2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Docente2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// metodologia
			fila = Asignatura.Asig[Asignatura.Metodologia2][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Metodologia2][Asignatura.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// ID DE LAS CABECERAS OCULTO
			// institu cion
			fila = Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// sede
			fila = Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Sede1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// jornada
			fila = Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grado
			fila = Asignatura.Asig[Asignatura.Grado1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Grado1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grupo
			fila = Asignatura.Asig[Asignatura.Grupo1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Grupo1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// asignatura
			fila = Asignatura.Asig[Asignatura.Asignatura1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Asignatura1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// periodo
			fila = Asignatura.Asig[Asignatura.Periodo1][Asignatura.FIL];
			col = Asignatura.Asig[Asignatura.Periodo1][Asignatura.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean validarEncabezadoArea(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = Area.Area[Area.Institucion2][Area.FIL];
			col = Area.Area[Area.Institucion2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// institucion con respecto a login
			fila = Area.Area[Area.Institucion1][Area.FIL];
			col = Area.Area[Area.Institucion1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (!cell2.getStringCellValue().equals(par[0])) {
				error = false;
				return false;
			}
			// sede con respecto a login
			if (!par[1].equals("")) {
				fila = Area.Area[Area.Sede1][Area.FIL];
				col = Area.Area[Area.Sede1][Area.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[1])) {
					error = false;
					return false;
				}
			}
			// jornada con respecto a login
			if (!par[2].equals("")) {
				fila = Area.Area[Area.Jornada1][Area.FIL];
				col = Area.Area[Area.Jornada1][Area.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[2])) {
					error = false;
					return false;
				}
			}
			// sede
			fila = Area.Area[Area.Sede2][Area.FIL];
			col = Area.Area[Area.Sede2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// jornada
			fila = Area.Area[Area.Jornada2][Area.FIL];
			col = Area.Area[Area.Jornada2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grado
			fila = Area.Area[Area.Grado2][Area.FIL];
			col = Area.Area[Area.Grado2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grupo
			fila = Area.Area[Area.Grupo2][Area.FIL];
			col = Area.Area[Area.Grupo2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// Area
			fila = Area.Area[Area.Area2][Area.FIL];
			col = Area.Area[Area.Area2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// periodo
			fila = Area.Area[Area.Periodo2][Area.FIL];
			col = Area.Area[Area.Periodo2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// docente
			fila = Area.Area[Area.Docente2][Area.FIL];
			col = Area.Area[Area.Docente2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// metodologia
			fila = Area.Area[Area.Metodologia2][Area.FIL];
			col = Area.Area[Area.Metodologia2][Area.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// ID DE LAS CABECERAS OCULTO
			// institu cion
			fila = Area.Area[Area.Institucion1][Area.FIL];
			col = Area.Area[Area.Institucion1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// sede
			fila = Area.Area[Area.Sede1][Area.FIL];
			col = Area.Area[Area.Sede1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// jornada
			fila = Area.Area[Area.Jornada1][Area.FIL];
			col = Area.Area[Area.Jornada1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grado
			fila = Area.Area[Area.Grado1][Area.FIL];
			col = Area.Area[Area.Grado1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grupo
			fila = Area.Area[Area.Grupo1][Area.FIL];
			col = Area.Area[Area.Grupo1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// Area
			fila = Area.Area[Area.Area1][Area.FIL];
			col = Area.Area[Area.Area1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// periodo
			fila = Area.Area[Area.Periodo1][Area.FIL];
			col = Area.Area[Area.Periodo1][Area.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida el encabezado de la evaluacion de logros <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarEncabezadoPreescolar(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = Preescolar.Pree[Preescolar.Institucion2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Institucion2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// institucion con respecto a login
			fila = Preescolar.Pree[Preescolar.Institucion1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Institucion1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (!cell2.getStringCellValue().equals(par[0])) {
				error = false;
				return false;
			}
			// sede con respecto a login
			if (!par[1].equals("")) {
				fila = Preescolar.Pree[Preescolar.Sede1][Preescolar.FIL];
				col = Preescolar.Pree[Preescolar.Sede1][Preescolar.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[1])) {
					error = false;
					return false;
				}
			}
			// jornada con respecto a login
			if (!par[2].equals("")) {
				fila = Preescolar.Pree[Preescolar.Jornada1][Preescolar.FIL];
				col = Preescolar.Pree[Preescolar.Jornada1][Preescolar.COL];
				row2 = sheet2.getRow(fila);
				cell2 = row2.getCell((short) col);
				if (!cell2.getStringCellValue().equals(par[2])) {
					error = false;
					return false;
				}
			}
			// sede
			fila = Preescolar.Pree[Preescolar.Sede2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Sede2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// jornada
			fila = Preescolar.Pree[Preescolar.Jornada2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Jornada2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grado
			fila = Preescolar.Pree[Preescolar.Grado2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Grado2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// grupo
			fila = Preescolar.Pree[Preescolar.Grupo2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Grupo2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// periodo
			fila = Preescolar.Pree[Preescolar.Periodo2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Periodo2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// docente
			fila = Preescolar.Pree[Preescolar.Docente2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Docente2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// metodologia
			fila = Preescolar.Pree[Preescolar.Metodologia2][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Metodologia2][Preescolar.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// ID DE LAS CABECERAS OCULTO
			// institu cion
			fila = Preescolar.Pree[Preescolar.Institucion1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Institucion1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// sede
			fila = Preescolar.Pree[Preescolar.Sede1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Sede1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// jornada
			fila = Preescolar.Pree[Preescolar.Jornada1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Jornada1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grado
			fila = Preescolar.Pree[Preescolar.Grado1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Grado1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grupo
			fila = Preescolar.Pree[Preescolar.Grupo1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Grupo1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// periodo
			fila = Preescolar.Pree[Preescolar.Periodo1][Preescolar.FIL];
			col = Preescolar.Pree[Preescolar.Periodo1][Preescolar.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida el encabezado de la bateria de logros <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarEncabezadoBateriaLogro(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// Institucion Codigo contra los valores del usuario
			fila = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (!cell2.getStringCellValue().equals(par[0])) {
				error = false;
				return false;
			}
			// grado
			fila = BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// metodologia
			fila = BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// asignatura
			fila = BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// ID DE LAS CABECERAS OCULTO
			// Institucion oculta
			fila = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grado oculta
			fila = BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// metodologia oculta
			fila = BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// asignatura oculta
			fila = BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.FIL];
			col = BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida el encabezado de la bateria de logros <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarEncabezadoBateriaDescriptor(String par[]) {
		int fila = 0;
		int col = 0;
		try {
			// institucion
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// Institucion Codigo contra los valores del usuario
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (!cell2.getStringCellValue().equals(par[0])) {
				error = false;
				return false;
			}
			// grado
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// metodologia
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// asignatura
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.COL];
			row = sheet.getRow(fila);
			row2 = sheet2.getRow(fila);
			cell = row.getCell((short) col);
			cell2 = row2.getCell((short) col);
			if (!cell.getStringCellValue().equals(cell2.getStringCellValue()))
				return false;
			// ID DE LAS CABECERAS OCULTO
			// Institucion oculta
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// grado oculta
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// metodologia oculta
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
			// asignatura oculta
			fila = BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.FIL];
			col = BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.COL];
			row2 = sheet2.getRow(fila);
			cell2 = row2.getCell((short) col);
			if (getValorSql(cell2) == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Genera la lista de datos del encabezado del archivo excel <BR>
	 **/
	public FiltroPlantilla getEncabezadoAsignatura() {
		FiltroPlantilla filtro = new FiltroPlantilla();
		int fila = 0;
		int col = 0;
		// codigo institucion
		fila = Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion(getValorSql(cell2));
		// nombre institucion
		fila = Asignatura.Asig[Asignatura.Institucion2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Institucion2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion_(getValorSql(cell2));
		// sede
		fila = Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Sede1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede(getValorSql(cell2));
		// nombre sede
		fila = Asignatura.Asig[Asignatura.Sede2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Sede2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede_(getValorSql(cell2));
		// jornada
		fila = Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada(getValorSql(cell2));
		// nombre jornada
		fila = Asignatura.Asig[Asignatura.Jornada2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Jornada2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada_(getValorSql(cell2));
		// grado
		fila = Asignatura.Asig[Asignatura.Grado1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Grado1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado(getValorSql(cell2));
		// nombre grado
		fila = Asignatura.Asig[Asignatura.Grado2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Grado2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado_(getValorSql(cell2));
		// grupo
		fila = Asignatura.Asig[Asignatura.Grupo1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Grupo1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo(getValorSql(cell2));
		// nombre grupo
		fila = Asignatura.Asig[Asignatura.Grupo2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Grupo2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo_(getValorSql(cell2));
		// asignatura
		fila = Asignatura.Asig[Asignatura.Asignatura1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Asignatura1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setAsignatura(getValorSql(cell2));
		// nombre asignatura
		fila = Asignatura.Asig[Asignatura.Asignatura2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Asignatura2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setAsignatura_(getValorSql(cell2));
		// periodo
		fila = Asignatura.Asig[Asignatura.Periodo1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Periodo1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo(getValorSql(cell2));
		// nombre periodo
		fila = Asignatura.Asig[Asignatura.Periodo2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Periodo2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo_(getValorSql(cell2));
		// docente
		fila = Asignatura.Asig[Asignatura.Docente1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Docente1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente(getValorSql(cell2));
		// nombre docente
		fila = Asignatura.Asig[Asignatura.Docente2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Docente2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente_(getValorSql(cell2));
		// metodologia
		fila = Asignatura.Asig[Asignatura.Metodologia1][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Metodologia1][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia(getValorSql(cell2));
		// nombre metodologia
		fila = Asignatura.Asig[Asignatura.Metodologia2][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Metodologia2][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia_(getValorSql(cell2));
		// CABECERA
		// tipo
		fila = Asignatura.Asig[Asignatura.Tipo][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Tipo][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setTipoPlantilla(getValorSql(cell2));
		// cerrado
		fila = Asignatura.Asig[Asignatura.Cerrado][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.Cerrado][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setCerrar(getValorSql(cell2));
		// jerarquia de logro
		fila = Asignatura.Asig[Asignatura.JerarquiaLogro][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.JerarquiaLogro][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJerarquiaLogro(getValorSql(cell2));
		// nivel de logro
		fila = Asignatura.Asig[Asignatura.NivelLogro][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.NivelLogro][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setNivelLogro(getValorSql(cell2));
		// jerarquia de grupo
		fila = Asignatura.Asig[Asignatura.JerarquiaGrupo][Asignatura.FIL];
		col = Asignatura.Asig[Asignatura.JerarquiaGrupo][Asignatura.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJerarquiagrupo(getValorSql(cell2));
		return filtro;
	}

	/**
	 * Funcinn: Genera la lista de datos del encabezado del archivo excel <BR>
	 **/
	public FiltroPlantilla getEncabezadoArea() {
		FiltroPlantilla filtro = new FiltroPlantilla();
		int fila = 0;
		int col = 0;
		// codigo institucion
		fila = Area.Area[Area.Institucion1][Area.FIL];
		col = Area.Area[Area.Institucion1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion(getValorSql(cell2));
		// nombre institucion
		fila = Area.Area[Area.Institucion2][Area.FIL];
		col = Area.Area[Area.Institucion2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion_(getValorSql(cell2));
		// sede
		fila = Area.Area[Area.Sede1][Area.FIL];
		col = Area.Area[Area.Sede1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede(getValorSql(cell2));
		// nombre sede
		fila = Area.Area[Area.Sede2][Area.FIL];
		col = Area.Area[Area.Sede2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede_(getValorSql(cell2));
		// jornada
		fila = Area.Area[Area.Jornada1][Area.FIL];
		col = Area.Area[Area.Jornada1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada(getValorSql(cell2));
		// nombre jornada
		fila = Area.Area[Area.Jornada2][Area.FIL];
		col = Area.Area[Area.Jornada2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada_(getValorSql(cell2));
		// grado
		fila = Area.Area[Area.Grado1][Area.FIL];
		col = Area.Area[Area.Grado1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado(getValorSql(cell2));
		// nombre grado
		fila = Area.Area[Area.Grado2][Area.FIL];
		col = Area.Area[Area.Grado2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado_(getValorSql(cell2));
		// grupo
		fila = Area.Area[Area.Grupo1][Area.FIL];
		col = Area.Area[Area.Grupo1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo(getValorSql(cell2));
		// nombre grupo
		fila = Area.Area[Area.Grupo2][Area.FIL];
		col = Area.Area[Area.Grupo2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo_(getValorSql(cell2));
		// Area
		fila = Area.Area[Area.Area1][Area.FIL];
		col = Area.Area[Area.Area1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setArea(getValorSql(cell2));
		// nombre Area
		fila = Area.Area[Area.Area2][Area.FIL];
		col = Area.Area[Area.Area2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setArea_(getValorSql(cell2));
		// periodo
		fila = Area.Area[Area.Periodo1][Area.FIL];
		col = Area.Area[Area.Periodo1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo(getValorSql(cell2));
		// nombre periodo
		fila = Area.Area[Area.Periodo2][Area.FIL];
		col = Area.Area[Area.Periodo2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo_(getValorSql(cell2));
		// docente
		fila = Area.Area[Area.Docente1][Area.FIL];
		col = Area.Area[Area.Docente1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente(getValorSql(cell2));
		// nombre docente
		fila = Area.Area[Area.Docente2][Area.FIL];
		col = Area.Area[Area.Docente2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente_(getValorSql(cell2));
		// metodologia
		fila = Area.Area[Area.Metodologia1][Area.FIL];
		col = Area.Area[Area.Metodologia1][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia(getValorSql(cell2));
		// nombre metodologia
		fila = Area.Area[Area.Metodologia2][Area.FIL];
		col = Area.Area[Area.Metodologia2][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia_(getValorSql(cell2));
		// CABECERA
		// tipo
		fila = Area.Area[Area.Tipo][Area.FIL];
		col = Area.Area[Area.Tipo][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setTipoPlantilla(getValorSql(cell2));
		// cerrado
		fila = Area.Area[Area.Cerrado][Area.FIL];
		col = Area.Area[Area.Cerrado][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setCerrar(getValorSql(cell2));
		// jerarquia de logro
		fila = Area.Area[Area.JerarquiaArea][Area.FIL];
		col = Area.Area[Area.JerarquiaArea][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJerarquiaLogro(getValorSql(cell2));
		// nivel de logro
		fila = Area.Area[Area.NivelLogro][Area.FIL];
		col = Area.Area[Area.NivelLogro][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setNivelLogro(getValorSql(cell2));
		// jerarquia de grupo
		fila = Area.Area[Area.JerarquiaGrupo][Area.FIL];
		col = Area.Area[Area.JerarquiaGrupo][Area.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJerarquiagrupo(getValorSql(cell2));
		return filtro;
	}

	/**
	 * Funcinn: Genera la lista de datos del encabezado del archivo excel <BR>
	 **/
	public FiltroPlantilla getEncabezadoPreescolar() {
		FiltroPlantilla filtro = new FiltroPlantilla();
		int fila = 0;
		int col = 0;
		// codigo institucion
		fila = Preescolar.Pree[Preescolar.Institucion1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Institucion1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion(getValorSql(cell2));
		// nombre institucion
		fila = Preescolar.Pree[Preescolar.Institucion2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Institucion2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setInstitucion_(getValorSql(cell2));
		// sede
		fila = Preescolar.Pree[Preescolar.Sede1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Sede1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede(getValorSql(cell2));
		// nombre sede
		fila = Preescolar.Pree[Preescolar.Sede2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Sede2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setSede_(getValorSql(cell2));
		// jornada
		fila = Preescolar.Pree[Preescolar.Jornada1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Jornada1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada(getValorSql(cell2));
		// nombre jornada
		fila = Preescolar.Pree[Preescolar.Jornada2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Jornada2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJornada_(getValorSql(cell2));
		// grado
		fila = Preescolar.Pree[Preescolar.Grado1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Grado1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado(getValorSql(cell2));
		// nombre grado
		fila = Preescolar.Pree[Preescolar.Grado2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Grado2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrado_(getValorSql(cell2));
		// grupo
		fila = Preescolar.Pree[Preescolar.Grupo1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Grupo1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo(getValorSql(cell2));
		// nombre grupo
		fila = Preescolar.Pree[Preescolar.Grupo2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Grupo2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setGrupo_(getValorSql(cell2));
		// Preescolar
		fila = Preescolar.Pree[Preescolar.Dimension1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Dimension1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setArea(getValorSql(cell2));
		// nombre Preescolar
		fila = Preescolar.Pree[Preescolar.Dimension2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Dimension2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setArea_(getValorSql(cell2));
		// periodo
		fila = Preescolar.Pree[Preescolar.Periodo1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Periodo1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo(getValorSql(cell2));
		// nombre periodo
		fila = Preescolar.Pree[Preescolar.Periodo2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Periodo2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPeriodo_(getValorSql(cell2));
		// docente
		fila = Preescolar.Pree[Preescolar.Docente1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Docente1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente(getValorSql(cell2));
		// nombre docente
		fila = Preescolar.Pree[Preescolar.Docente2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Docente2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setDocente_(getValorSql(cell2));
		// metodologia
		fila = Preescolar.Pree[Preescolar.Metodologia1][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Metodologia1][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia(getValorSql(cell2));
		// nombre metodologia
		fila = Preescolar.Pree[Preescolar.Metodologia2][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Metodologia2][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setMetodologia_(getValorSql(cell2));
		// CABECERA
		// tipo
		fila = Preescolar.Pree[Preescolar.Tipo][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Tipo][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setTipoPlantilla(getValorSql(cell2));
		// cerrado
		fila = Preescolar.Pree[Preescolar.Cerrado][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.Cerrado][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setCerrar(getValorSql(cell2));
		// nivel de logro
		fila = Preescolar.Pree[Preescolar.NivelLogro][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.NivelLogro][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setNivelLogro(getValorSql(cell2));
		// jerarquia de grupo
		fila = Preescolar.Pree[Preescolar.JerarquiaGrupo][Preescolar.FIL];
		col = Preescolar.Pree[Preescolar.JerarquiaGrupo][Preescolar.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setJerarquiagrupo(getValorSql(cell2));
		return filtro;
	}

	/**
	 * Funcinn: Genera la lista de datos del encabezado del archivo de bateria <BR>
	 * 
	 * @return boolean
	 **/
	public Logros getEncabezadoBateriaLogro() {
		Logros filtro = new Logros();
		int fila = 0;
		int col = 0;
		// codigo institucion
		fila = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaInstitucion(getValorSql(cell2));
		// nombre institucion
		fila = BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaInstitucion_(getValorSql(cell2));
		// codigo grado
		fila = BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaGrado(getValorSql(cell2));
		// nombre grado
		fila = BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaGrado_(getValorSql(cell2));
		// codigo metodologia
		fila = BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaMetodologia(getValorSql(cell2));
		// nombre metodologia
		fila = BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaMetodologia_(getValorSql(cell2));
		// codigo asignatura
		fila = BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaAsignatura(getValorSql(cell2));
		// nombre asignatura
		fila = BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaAsignatura_(getValorSql(cell2));
		// tipo
		fila = BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setTipoPlantilla(getValorSql(cell2));
		// nivel
		fila = BateriaLogro.BatLog[BateriaLogro.Nivel][BateriaLogro.FIL];
		col = BateriaLogro.BatLog[BateriaLogro.Nivel][BateriaLogro.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setNivelLogro(getValorSql(cell2));
		return filtro;
	}

	/**
	 * Funcinn: Genera la lista de datos del encabezado del archivo de bateria <BR>
	 * 
	 * @return boolean
	 **/
	public Logros getEncabezadoBateriaDescriptor() {
		Logros filtro = new Logros();
		int fila = 0;
		int col = 0;
		// codigo institucion
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaInstitucion(getValorSql(cell2));
		// nombre institucion
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaInstitucion_(getValorSql(cell2));
		// codigo grado
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaGrado(getValorSql(cell2));
		// nombre grado
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaGrado_(getValorSql(cell2));
		// codigo metodologia
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaMetodologia(getValorSql(cell2));
		// nombre metodologia
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaMetodologia_(getValorSql(cell2));
		// codigo asignatura
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaAsignatura(getValorSql(cell2));
		// nombre asignatura
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setPlantillaAsignatura_(getValorSql(cell2));
		// tipo
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setTipoPlantilla(getValorSql(cell2));
		// nivel
		fila = BateriaDescriptor.BatDes[BateriaDescriptor.Nivel][BateriaDescriptor.FIL];
		col = BateriaDescriptor.BatDes[BateriaDescriptor.Nivel][BateriaDescriptor.COL];
		row2 = sheet2.getRow(fila);
		cell2 = row2.getCell((short) col);
		filtro.setNivelLogro(getValorSql(cell2));
		return filtro;
	}

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
			String[][] r = importarDAO.getEstudiantes(filtro);
			/*
			 * estNoBD=new ArrayList(); z=y=0; for(int
			 * j=0;j<estudiantes.length;j++){ y=0; for(int i=0;i<r.length;i++){
			 * if(r[i][0].equals(estudiantes[j])){ y++; break; } } if(y>0){ z++;
			 * }else{ estNoBD.add(""+j); } }
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
	 * Funcinn: vALIDA la escala del motivo de ausencia para ver si coincide con
	 * los posibles valores <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarEscalaMotivoAsignatura_(FiltroPlantilla filtro2) {
		error = true;
		int z = 0, y = 0, x = 0;
		String valor = null;
		try {
			// escala valorativa de la base de datos
			int[] n = { 0 };
			// escala=importarDAO.getEscala(2);
			/**
			 * MODIFICADO Escala asignatura 20-03-10
			 * */
			// col[1] = plantillaDAO.getEscala(2);
			escala = importarDAO.getEscalaNivelEval(filtro2);

			/**
			 * FIN
			 * **/
			// Motivo de la base de datos
			// motivo=importarDAO.getFiltroMatriz(Recursos.getRecurso(Recursos.AUSENCIA));
			// validar que no se halla cambiado la escala valorativa
			int num = sheet2.getPhysicalNumberOfRows();
			nota = new String[num
					- Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL]][1];
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
				if (row != null) {
					// obtener nota
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
					/*
					 * //obtener ausencia justificada x=0;
					 * cell=row.getCell((short
					 * )Asignatura.Asig[Asignatura.AusenciaJus
					 * ][Asignatura.COL]); nota[p][1]=getValorSql(cell);
					 * if(nota[p][1]!=null){ if(nota[p][1].length()<4 &&
					 * GenericValidator.isInt(nota[p][1]) &&
					 * (Integer.parseInt(nota[p][1]))>0) x=1; }else x=1;
					 * if(x==0){ ponerFila("Ausencia justificada",posi++);
					 * //break; } //ausencia no justificada x=0;
					 * cell=row.getCell
					 * ((short)Asignatura.Asig[Asignatura.AusenciaNoJus
					 * ][Asignatura.COL]); nota[p][2]=getValorSql(cell);
					 * if(nota[p][2]!=null){ if(nota[p][2].length()<4 &&
					 * GenericValidator.isInt(nota[p][2]) &&
					 * (Integer.parseInt(nota[p][2]))>0) x=1; }else x=1;
					 * if(x==0){ ponerFila("Ausencia no justificada",posi++);
					 * //break; } //motivo x=0;
					 * cell=row.getCell((short)Asignatura
					 * .Asig[Asignatura.Motivo][Asignatura.COL]);
					 * nota[p][3]=getValorSql(cell); if(nota[p][3]!=null &&
					 * motivo!=null){ for(int k=0;k<motivo.length;k++){
					 * if(nota[p][3].equals(motivo[k][0])){ x=1; break; } }
					 * }else x=1; if(x==0){
					 * ponerFila("Motivo de ausencia",posi++); //break; }
					 * //porcentaje de ausencia x=0;
					 * cell=row.getCell((short)Asignatura
					 * .Asig[Asignatura.Porcentaje][Asignatura.COL]);
					 * nota[p][4]=getValorSql(cell); if(nota[p][4]!=null){
					 * if(nota[p][4].length()<4 &&
					 * GenericValidator.isInt(nota[p][4]) &&
					 * GenericValidator.isInRange
					 * (Integer.parseInt((!nota[p][4].equals
					 * ("")?nota[p][4]:"0")),1,100)){ x=1; } }else x=1;
					 * if(x==0){ ponerFila("Porcentaje",posi++); //break; }
					 */
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

	public boolean validarEscalaMotivoArea() {
		error = true;
		int z = 0, y = 0, x = 0;
		String valor = null;
		try {
			// escala valorativa de la base de datos
			int[] n = { 0 };
			escala = importarDAO.getEscala(2);
			// Motivo de la base de datos
			// motivo=importarDAO.getFiltroMatriz(Recursos.getRecurso(Recursos.AUSENCIA));
			// validar que no se halla cambiado la escala valorativa
			int num = sheet2.getPhysicalNumberOfRows();
			nota = new String[num - Area.Area[Area.Codigo][Area.FIL]][1];
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
					/*
					 * //obtener ausencia justificada x=0;
					 * cell=row.getCell((short
					 * )Area.Area[Area.AusenciaJus][Area.COL]);
					 * nota[p][1]=getValorSql(cell); if(nota[p][1]!=null){
					 * if(nota[p][1].length()<4 &&
					 * GenericValidator.isInt(nota[p][1]) &&
					 * (Integer.parseInt(nota[p][1]))>0) x=1; }else x=1;
					 * if(x==0){ ponerFila("Ausencia justificada",posi++);
					 * //break; } //ausencia no justificada x=0;
					 * cell=row.getCell
					 * ((short)Area.Area[Area.AusenciaNoJus][Area.COL]);
					 * nota[p][2]=getValorSql(cell); if(nota[p][2]!=null){
					 * if(nota[p][2].length()<4 &&
					 * GenericValidator.isInt(nota[p][2]) &&
					 * (Integer.parseInt(nota[p][2]))>0) x=1; }else x=1;
					 * if(x==0){ ponerFila("Ausencia no justificada",posi++);
					 * //break; } //motivo x=0;
					 * cell=row.getCell((short)Area.Area
					 * [Area.Motivo][Area.COL]); nota[p][3]=getValorSql(cell);
					 * if(nota[p][3]!=null && motivo!=null){ for(int
					 * k=0;k<motivo.length;k++){
					 * if(nota[p][3].equals(motivo[k][0])){ x=1; break; } }
					 * }else x=1; if(x==0){
					 * ponerFila("Motivo de ausencia",posi++); //break; }
					 * //porcentaje de ausencia x=0;
					 * cell=row.getCell((short)Area
					 * .Area[Area.Porcentaje][Area.COL]);
					 * nota[p][4]=getValorSql(cell); if(nota[p][4]!=null){
					 * if(nota[p][4].length()<4 &&
					 * GenericValidator.isInt(nota[p][4]) &&
					 * GenericValidator.isInRange
					 * (Integer.parseInt((!nota[p][4].equals
					 * ("")?nota[p][4]:"0")),1,100)){ x=1; } }else x=1;
					 * if(x==0){ ponerFila("Porcentaje",posi++); //break; }
					 */
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
	 * Funcinn: Pone una celda con un mensaje error<BR>
	 * 
	 * @param String
	 *            a
	 * @param int n
	 **/
	public void ponerFila(String a, int n) {
		cell = row.createCell((short) n);
		cell.setCellValue("Campo " + a
				+ " no corresponde a los posibles valores");
		error = false;
	}

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
	 * Funcinn: Valida qu elos codigos de periodo coincidan con los posibles
	 * valores <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarPeriodoBateriaLogro(Logros filtro) {
		error = true;
		int y = 0, x = 0;
		Object o[];
		Collection col;
		try {
			// TRAER UNA VERSION DE Los Logros//
			logro = importarDAO.getLogrosActuales(filtro);
			// PONER LOS DATOS DEL USUARIO EN LA MATRIZ DE DATOS/
			int filas = sheet.getPhysicalNumberOfRows();
			int cel = BateriaLogro.BatLog[BateriaLogro.Descripcion][BateriaLogro.COL] + 1;
			int filasReales = filas
					- BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL];
			nota = new String[filasReales][cel];
			int p = -1, q = 0;
			for (int i = BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL]; i < filas; i++) {
				p++;
				q = 0;
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.COL]; j < cel; j++) {
						cell = row.getCell((short) j);
						nota[p][q++] = getValorSql(cell);
					}
				}
			}
			// for(int
			// i=0;i<nota.length;i++){System.out.println("logros="+nota[i][0]+"--"+nota[i][1]+"--"+nota[i][2]+"--"+nota[i][3]+"--"+nota[i][4]);}
			col = new ArrayList();
			for (int i = 0; i < nota.length; i++) {
				x = 0;
				for (int j = 0; j < nota[i].length; j++) {
					if (nota[i][j] == null)
						x++;
				}
				if (x != nota[i].length) {
					o = new Object[nota[i].length];
					for (int j = 0; j < nota[i].length; j++) {
						o[j] = nota[i][j];
					}
					col.add(o);
				}
			}
			nota = importarDAO.getFiltroMatriz(col);
			if (nota != null) {
				// for(int
				// i=0;i<nota.length;i++){System.out.println("logros="+nota[i][0]+"--"+nota[i][1]+"--"+nota[i][2]+"--"+nota[i][3]+"--"+nota[i][4]);}
				// VERIFICAR LOS CAMPOS REQUERIDOS/
				for (int i = 0; i < nota.length; i++) {
					row = sheet
							.getRow(i
									+ BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL]);
					for (int j = 0; j < nota[i].length - 1; j++) {// valida
																	// todos
																	// menos
																	// descripcion
						String[] campos = { "Periodo inicial", "Periodo final",
								"Nombre", "Abreviatura" };
						if (nota[i][j] == null || nota[i][j].trim().equals("")) {
							cell = row.createCell((short) (cel));
							cell.setCellValue("El campo " + campos[j]
									+ " es requerido.");
							error = false;
							break;
						}
					}
				}
				if (!error)
					return false;
				// VERIFICAR QUE NO SE REPITAN NOMBRES O ABREVIATURAS
				for (int i = 0; i < nota.length; i++) {
					row = sheet
							.getRow(i
									+ BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL]);
					for (int j = i + 1; j < nota.length; j++) {
						// System.out.println(nota[i][2]+"=="+nota[j][2]);
						if (nota[i][2].equalsIgnoreCase(nota[j][2])
								|| nota[i][3].equalsIgnoreCase(nota[j][3])) {
							cell = row.createCell((short) (cel));
							cell.setCellValue("Campo Nombre o Abreviatura esta repetido en la hoja y no es posible insertarlo");
							error = false;
							break;
						}
					}
				}
				if (!error)
					return false;
				// verificar que no se repita nombre o abreviatura para
				// diferente periodo inicial o final/
				/*
				 * Nota[i][0]=per Inicial Nota[i][1]=per final Nota[i][2]=per
				 * nombre Nota[i][3]=per abrev Nota[i][4]=per Desc
				 * logro[k][0]=LOGCODJERAR logro[k][1]=LOGCODIGO
				 * logro[k][2]=LOGCODPERINICIAL logro[k][3]=LOGCODPERFINAL
				 * logro[k][4]=LOGNOMBRE logro[k][5]=LOGABREV
				 * logro[k][6]=LOGDESCRIPCION
				 */
				if (logro != null) {
					String nom, abrev, perIni, perFin, perIniBD, perFinBD, nomBD, abrevBD;
					for (int i = 0; i < nota.length; i++) {
						row = sheet
								.getRow(i
										+ BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL]);
						perIni = (nota[i][0] != null ? nota[i][0] : "").trim();
						perFin = (nota[i][1] != null ? nota[i][1] : "").trim();
						nom = (nota[i][2] != null ? nota[i][2] : "").trim();
						abrev = (nota[i][3] != null ? nota[i][3] : "").trim();
						// System.out.println(nota[i][2]+"=="+nota[j][2]);
						for (int k = 0; k < logro.length; k++) {
							perIniBD = logro[k][2];
							perFinBD = logro[k][3];
							nomBD = logro[k][4];
							abrevBD = logro[k][5];
							// System.out.println("abrev="+abrev+" abrevBD="+abrevBD+"__"+perIni+"="+perIniBD+"__"+perFin+"="+perFinBD);
							if (nom.equalsIgnoreCase(nomBD)
									&& (!perIni.equalsIgnoreCase(perIniBD) || !perFin
											.equals(perFinBD))) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Nombre ya existe para otro Periodo inicial y final y no es posible ingresarlo");
								error = false;
								break;
							}
							if (abrev.equalsIgnoreCase(abrevBD)
									&& (!perIni.equalsIgnoreCase(perIniBD) || !perFin
											.equals(perFinBD))) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Abreviatura ya existe para otro Periodo inicial y final y no es posible ingresarlo");
								error = false;
								break;
							}
							if (abrev.equalsIgnoreCase(abrevBD)
									&& !nom.equalsIgnoreCase(nomBD)) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Abreviatura ya existe para otro logro y no es posible ingresarlo");
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
			System.out.println("Error validando periodos=" + e);
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida qu elos codigos de periodo coincidan con los posibles
	 * valores <BR>
	 * 
	 * @return boolean
	 **/
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
	 * Funcinn: Valida qu elos codigos de periodo coincidan con los posibles
	 * valores <BR>
	 * 
	 * @param int a
	 * @return boolean
	 **/
	public boolean validarPeriodoBateriaDescriptor(int a, String[][] desc) {
		error = true;
		int y = 0, x = 0;
		try {
			int filas = sheet.getPhysicalNumberOfRows();
			int cel = BateriaDescriptor.BatDes[BateriaDescriptor.Descripcion][BateriaDescriptor.COL] + 1;
			// row.getPhysicalNumberOfCells();
			// System.out.println(num+"/"+cel+"/"+(num-6));
			// nota=new String[filas-6][cel];
			int filasReales = filas
					- BateriaDescriptor.BatDes[BateriaDescriptor.PerInicial][BateriaDescriptor.FIL];
			nota = new String[filasReales][cel];

			int p = -1, q = 0;
			/* PONER LOS DATOS DEL USUARIO EN LA MATRIZ DE DATOS */
			for (int i = BateriaDescriptor.BatDes[BateriaDescriptor.PerInicial][BateriaDescriptor.FIL]; i < filas; i++) {
				p++;
				q = 0;
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = BateriaDescriptor.BatDes[BateriaDescriptor.PerInicial][BateriaDescriptor.COL]; j < cel; j++) {
						cell = row.getCell((short) j);
						nota[p][q++] = getValorSql(cell);
					}
				}
			}
			Collection col = new ArrayList();
			Object o[];
			for (int i = 0; i < nota.length; i++) {
				x = 0;
				for (int j = 0; j < nota[i].length; j++) {
					if (nota[i][j] == null)
						x++;
				}
				if (x != nota[i].length) {
					o = new Object[nota[i].length];
					for (int j = 0; j < nota[i].length; j++) {
						o[j] = nota[i][j];
					}
					col.add(o);
				}
			}
			nota = importarDAO.getFiltroMatriz(col);
			if (nota != null) {
				// for(int
				// i=0;i<nota.length;i++){System.out.println("Desc ="+nota[i][0]+"--"+nota[i][1]+"--"+nota[i][2]+"--"+nota[i][3]+"--"+nota[i][4]);}
				// VERIFICAR LOS CAMPOS REQUERIDOS/
				for (int i = 0; i < nota.length; i++) {
					row = sheet
							.getRow(i
									+ BateriaDescriptor.BatDes[BateriaDescriptor.PerInicial][BateriaDescriptor.FIL]);
					for (int j = 0; j < nota[i].length - 1; j++) {// valida
																	// todos
																	// menos
																	// descripcion
						String[] campos = { "Periodo inicial", "Periodo final",
								"Nombre", "Abreviatura" };
						if (nota[i][j] == null || nota[i][j].trim().equals("")) {
							cell = row.createCell((short) (cel));
							cell.setCellValue("El campo " + campos[j]
									+ " es requerido.");
							error = false;
							break;
						}
					}
				}
				if (!error)
					return false;
				// VERIFICAR QUE NO SE REPITAN NOMBRES O ABREVIATURAS
				for (int i = 0; i < nota.length; i++) {
					row = sheet
							.getRow(i
									+ BateriaDescriptor.BatDes[BateriaDescriptor.PerInicial][BateriaDescriptor.FIL]);
					for (int j = i + 1; j < nota.length; j++) {
						// System.out.println(nota[i][2]+"=="+nota[j][2]);
						if (nota[i][2].equalsIgnoreCase(nota[j][2])
								|| nota[i][3].equalsIgnoreCase(nota[j][3])) {
							cell = row.createCell((short) (cel));
							cell.setCellValue("Campo Nombre o Abreviatura esta repetido en la hoja y no es posible insertarlo");
							error = false;
							break;
						}
					}
				}
				if (!error)
					return false;
				// verificar que no se repita nombre o abreviatura para
				// diferente periodo inicial o final/
				/*
				 * Nota[i][0]=per Inicial Nota[i][1]=per final Nota[i][2]=per
				 * nombre Nota[i][3]=per abrev Nota[i][4]=per Desc
				 * desc[k][0]=DESCODJERAR desc[k][1]=DESCODIGO
				 * desc[k][2]=DESCODPERINICIAL desc[k][3]=DESCODPERFINAL
				 * desc[k][4]=DESNOMBRE desc[k][5]=DESABREV
				 * desc[k][6]=DESDESCRIPCION desc[k][7]=DESCODTIPDES
				 */
				if (desc != null) {
					String nom, abrev, perIni, perFin, perIniBD, perFinBD, nomBD, abrevBD, tipoBD;
					for (int i = 0; i < nota.length; i++) {
						row = sheet
								.getRow(i
										+ BateriaLogro.BatLog[BateriaLogro.PerInicial][BateriaLogro.FIL]);
						perIni = (nota[i][0] != null ? nota[i][0] : "").trim();
						perFin = (nota[i][1] != null ? nota[i][1] : "").trim();
						nom = (nota[i][2] != null ? nota[i][2] : "").trim();
						abrev = (nota[i][3] != null ? nota[i][3] : "").trim();
						// System.out.println(nota[i][2]+"=="+nota[j][2]);
						for (int k = 0; k < desc.length; k++) {
							perIniBD = desc[k][2];
							perFinBD = desc[k][3];
							nomBD = desc[k][4];
							abrevBD = desc[k][5];
							tipoBD = desc[k][7];
							// System.out.println("abrev="+abrev+" abrevBD="+abrevBD+"__"+perIni+"="+perIniBD+"__"+perFin+"="+perFinBD);
							if (nom.equalsIgnoreCase(nomBD)
									&& ("" + a).equalsIgnoreCase(tipoBD)
									&& (!perIni.equalsIgnoreCase(perIniBD) || !perFin
											.equals(perFinBD))) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Nombre ya existe para otro Periodo inicial y final y no es posible ingresarlo");
								error = false;
								break;
							}
							if (abrev.equalsIgnoreCase(abrevBD)
									&& ("" + a).equalsIgnoreCase(tipoBD)
									&& (!perIni.equalsIgnoreCase(perIniBD) || !perFin
											.equals(perFinBD))) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Abreviatura ya existe para otro Periodo inicial y final y no es posible ingresarlo");
								error = false;
								break;
							}
							if (abrev.equalsIgnoreCase(abrevBD)
									&& !nom.equalsIgnoreCase(nomBD)) {
								cell = row.createCell((short) (cel));
								cell.setCellValue("campo Abreviatura ya existe para otro Descriptor y no es posible ingresarlo");
								error = false;
								break;
							}
						}
					}
				}
				if (!error)
					return false;
			}
			switch (a) {
			case 1:
				descFor = nota;
				break;
			case 2:
				descDif = nota;
				break;
			case 3:
				descRec = nota;
				break;
			case 4:
				descEst = nota;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Error validando periodos en bateria de descriptor="
							+ e);
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Valida que los codigos de descriptores coincidan con los
	 * posibles valores <BR>
	 * 
	 * @return boolean
	 **/
	public boolean validarCodigoDescriptorArea() {
		error = true;
		int y = 0, x = 0, i, j, k, l, m;
		String temp = null;
		String[] desc;
		String[] fichas = { "Fortalezas", "Dificultades", "Recomendaciones",
				"Estrategias" };
		try {
			int num = sheet.getPhysicalNumberOfRows();
			nota2 = new String[num - Area.Area[Area.Codigo][Area.FIL]][4];
			int p = -1, q = 0;
			/* PONER LOS DATOS DEL USUARIO EN LA MATRIZ DE DATOS */
			for (i = Area.Area[Area.Codigo][Area.FIL]; i < num; i++) {
				p++;
				q = 0;
				row2 = sheet2.getRow(i);
				row = sheet.getRow(i);
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
	 * Funcinn: Retorna el codigo de la escala valorativa si este corresponde a
	 * los posibles datos, de lo contrario arroja null <BR>
	 * 
	 * @param String
	 *            n
	 * @return String
	 **/
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
	 * Funcinn: Retorna el codigo de la escala valorativa si este corresponde a
	 * los posibles datos, de lo contrario arroja null <BR>
	 * 
	 * @param String
	 *            n
	 * @return String
	 **/
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
	 * Funcinn: Retorna el valor de la valriable de error en datos de usuario <BR>
	 * 
	 * @return boolean
	 **/
	public boolean getError() {
		return error;
	}

	public int[] getResultado() {
		return importarDAO.getResultado();
	}

	public int[] getResultado2() {
		return importarDAO.getResultado2();
	}

	/**
	 * Funcinn: Importa los datos extraidos del archivo de importacinn para la
	 * evaluacion de asignatura y de area<BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean importarAsignatura(int tipo, String[] archivo,
			String plantilla, String download, String us) {
		error = true;
		boolean bandera = true;
		FileOutputStream fileOut = null;
		try {
			/* importar la evaluacion */
			// estudiantes: array de codigos de estudiante
			// matriz de motivos de ausencia
			// matriz de escala valorativa
			// matriz de notas de alumno
			// estudiantes,log,escala,nota
			if (!importarDAO.importarEvalAsignatura(filtro2, estudiantes,
					motivo, escala, nota, log, escala2, nota2)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se pudo importar los registros de evaluacinn: "
								+ importarDAO.getMensaje());
				bandera = false;
				return false;
			}
			Logger.print(
					us,
					"ImpBatch Logro/Asig Inst:" + filtro2.getInstitucion()
							+ " Sede:" + filtro2.getSede() + " Jorn:"
							+ filtro2.getJornada() + " Met:"
							+ filtro2.getMetodologia() + " Gra:"
							+ filtro2.getGrado() + " Grupo:"
							+ filtro2.getGrupo() + " Asig:"
							+ filtro2.getAsignatura() + " Per:"
							+ filtro2.getPeriodo(),
					"Importar Plantilla en Batch de Logros/Asignatura: Periodo '"
							+ filtro2.getPeriodo_() + "', Metodologia '"
							+ filtro2.getMetodologia_() + ",' Asignatura '"
							+ filtro2.getAsignatura_() + " Grado '"
							+ filtro2.getGrado_() + "', Grupo '"
							+ filtro2.getGrupo_() + "'", filtro2.getPeriodo(),
					6, 1, this.toString());
		} catch (NullPointerException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan filas importantes");
			System.out.println("Error Importar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error Importar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error Importar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (bandera) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Importa los datos extraidos del archivo de importacinn para la
	 * evaluacion de asignatura y de area<BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean importarArea(int tipo, String[] archivo, String plantilla,
			String download, String us) {
		error = true;
		boolean bandera = true;
		FileOutputStream fileOut = null;
		try {
			if (!importarDAO.importarEvalArea(filtro2, estudiantes, motivo,
					escala, nota, escala2, nota2)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se pudo importar los registros de evaluacion: "
								+ importarDAO.getMensaje());
				bandera = false;
				return false;
			}
			Logger.print(
					us,
					"ImpBatch Area/Desc Inst:" + filtro2.getInstitucion()
							+ " Sede:" + filtro2.getSede() + " Jorn:"
							+ filtro2.getJornada() + " Met:"
							+ filtro2.getMetodologia() + " Gra:"
							+ filtro2.getGrado() + " Grupo:"
							+ filtro2.getGrupo() + " Area:" + filtro2.getArea()
							+ " Per:" + filtro2.getPeriodo(),
					"Importacinn de Plantilla en Batch de Descriptor/Area: Periodo '"
							+ filtro2.getPeriodo_() + "', Metodologia '"
							+ filtro2.getMetodologia_() + "', Area '"
							+ filtro2.getArea_() + "', Grado '"
							+ filtro2.getGrado_() + "', Grupo '"
							+ filtro2.getGrupo_() + "'", filtro2.getPeriodo(),
					6, 1, this.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan filas importantes");
			bandera = false;
			System.out.println("Error Importar Asignatura:  " + e.toString());
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			bandera = false;
			System.out.println("Error Importar Asignatura:  " + e.toString());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			bandera = false;
			System.out.println("Error Importar Area:  " + e.toString());
			return false;
		} finally {
			try {
				if (bandera) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Importa los datos extraidos del archivo de importacinn para la
	 * evaluacion de asignatura y de area<BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean importarPreescolar(int tipo, String[] archivo,
			String plantilla, String download, String us) {
		error = true;
		boolean bandera = true;
		FileOutputStream fileOut = null;
		try {
			/* importar la evaluacion */
			/*
			 * if(!importarDAO.importarEvalPreescolar(filtro2,estudiantes,nota)){
			 * ponerEncabezado(Properties.FILAMENSAJE,Properties.COLUMNAMENSAJE,
			 * "No se pudo importar los registros de evaluacion: "
			 * +importarDAO.getMensaje()); bandera=false; return false; }
			 */
			Logger.print(
					us,
					"ImpBatch Pree Inst:" + filtro2.getInstitucion() + " Sede:"
							+ filtro2.getSede() + " Jorn:"
							+ filtro2.getJornada() + " Met:"
							+ filtro2.getMetodologia() + " Gra:"
							+ filtro2.getGrado() + " Grupo:"
							+ filtro2.getGrupo() + " Per:"
							+ filtro2.getPeriodo(),
					"Importacinn de Plantilla Batch de Preescolar: Periodo '"
							+ filtro2.getPeriodo_() + "', Metodologia '"
							+ filtro2.getMetodologia_() + "', Grado '"
							+ filtro2.getGrado_() + "', Grupo '"
							+ filtro2.getGrupo_() + "'", filtro2.getPeriodo(),
					6, 1, this.toString());
		} catch (NullPointerException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan filas importantes");
			System.out.println("Error Importar Preescolar:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error Importar Preescolar:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error Importar Preescolar:  " + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (bandera) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Importa los datos extraidos del archivo de importacinn para la
	 * bateria de logros<BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean importarBateriaLogro(int tipo, String[] archivo,
			String plantilla, String download, String us) {
		error = true;
		boolean bandera = true;
		FileOutputStream fileOut = null;
		try {
			/* importar la evaluacion */
			if (!importarDAO.importarBateriaLogro(filtro, nota, logro)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se pudo importar la bateria de logros: "
								+ importarDAO.getMensaje());
				bandera = false;
				return false;
			}
			Logger.print(
					us,
					"ImpBatch Bat Logro Inst:"
							+ filtro.getPlantillaInstitucion() + " Met:"
							+ filtro.getPlantillaMetodologia() + " Gra:"
							+ filtro.getPlantillaGrado() + " Asig:"
							+ filtro.getPlantillaAsignatura(),
					"Importar Plantilla en Batch de Bateria de Logros: Metodologia '"
							+ filtro.getPlantillaMetodologia_()
							+ "', Asignatura '"
							+ filtro.getPlantillaAsignatura_() + "', Grado '"
							+ filtro.getPlantillaGrado_() + "'", 6, 1,
					this.toString());
		} catch (NullPointerException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla de importacinn valido, Faltan filas importantes");
			System.out.println("Error BateriaLogro:  " + e.toString());
			// e.printStackTrace();
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error BateriaLogro:  " + e.toString());
			// e.printStackTrace();
			bandera = false;
			return false;
		} catch (Exception e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error BateriaLogro:  " + e.toString());
			// e.printStackTrace();
			bandera = false;
			return false;
		} finally {
			try {
				if (bandera) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Importa los datos extraidos del archivo de importacinn para la
	 * bateria de descriptores<BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean importarBateriaDescriptor(int tipo, String[] archivo,
			String plantilla, String download, String us) {
		error = true;
		boolean bandera = true;
		FileOutputStream fileOut = null;
		try {
			/* importar la evaluacion */
			if (!importarDAO.importarBateriaDescriptor(filtro, descFor,
					descDif, descRec, descEst, desc)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se pudo importar la bateria de Descriptores: "
								+ importarDAO.getMensaje());
				bandera = false;
				return false;
			}
			Logger.print(
					us,
					"ImpBatch Bat Desc Inst:"
							+ filtro.getPlantillaInstitucion() + " Met:"
							+ filtro.getPlantillaMetodologia() + " Gra:"
							+ filtro.getPlantillaGrado() + " Area:"
							+ filtro.getPlantillaAsignatura(),
					"Importar Plantilla en Batch de Bateria de Descriptores: Metodologia '"
							+ filtro.getPlantillaMetodologia_() + "', Area '"
							+ filtro.getPlantillaAsignatura_() + "', Grado '"
							+ filtro.getPlantillaGrado_() + "'", 6, 1,
					this.toString());
		} catch (NullPointerException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla de importacinn valido, Faltan filas importantes");
			System.out.println("Error importar bateria descriptor:  "
					+ e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error importar bateria descriptor:  "
					+ e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error importar bateria descriptor:  "
					+ e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (bandera) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: valida que todos los datos de la plantilla de evaluacinn de
	 * asignatura sean correctos <BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean validarFormatoPreescolar(int tipo, String[] archivo,
			String plantilla, String download, String par[]) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		error = true;
		boolean bandera = true;
		int num = 0;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet2 = workbook.getSheetAt(6);
			sheet = workbook.getSheetAt(1);
			// System.out.println("tipoPlantilla="+tipo);
			if (!validarTipoPlantilla(tipo)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No es una plantilla de evaluacinn..  de Preescolar");
				bandera = false;
				return false;
			}
			/* VALIDAR QUE CORRESPONDA CON LA HOJA OCULTA */
			String[] tipos = { "Corporal", "Comunicativa", "Cognitiva",
					"ntica", "Estntica" };
			for (int y = 1; y < 6; y++) {
				sheet = workbook.getSheetAt(y);
				if (!validarEncabezadoPreescolar(par)) {
					if (!error)
						ponerEncabezado(
								Properties.FILAMENSAJE,
								Properties.COLUMNAMENSAJE,
								"Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio, sede y jornada.");
					else
						ponerEncabezado(
								Properties.FILAMENSAJE,
								Properties.COLUMNAMENSAJE,
								"El encabezado de la ficha '"
										+ tipos[y - 1]
										+ "' ha sido modificado y no es posible importar la evaluacinn");
					error = true;
					bandera = false;
					return false;
				}
				// TRAER EL ENCABEZADO DE PAGINA
				if (y == 1) {
					filtro2 = getEncabezadoPreescolar();
					num = sheet.getPhysicalNumberOfRows();
					nota = new String[num
							- Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL]][5];
					// VALIDAR SI ESTA CERRADO EL PERIODO
					if (!importarDAO.getCierrePreescolar(filtro2)) {
						ponerEncabezado(
								Properties.FILAMENSAJE,
								Properties.COLUMNAMENSAJE,
								"No se puede importar la evaluacinn porque el grupo esta cerrado para alguna Dimensinn y Periodo\n El grupo solo puede ser abierto por el Rector del colegio");
						bandera = false;
						return false;
					}
				}
				/* VALIDAR DE CANTIDAD DE ESTUDIANTES */
				/*
				 * if(!validarNumeroEstudiantesPreescolar()){
				 * ponerEncabezado(Properties
				 * .FILAMENSAJE,Properties.COLUMNAMENSAJE,
				 * "El nnmero de estudiantes indicado excede o es menor que el de los alumnos generados por la plantilla"
				 * ); bandera=false; return false; }
				 */
				/* VALIDAR ESTUDIANTES */
				if (!validarEstudiantesPreescolar(filtro2)) {
					ponerEncabezado(
							Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"Los datos de los estudiantes han sido modificados en la ficha '"
									+ tipos[y]
									+ "' y no es posible importar la evaluacinn.\n Posiblemente la información fue modificada despues de la generacinn de la plantilla");
					bandera = false;
					if (!error) {
						f = new File(download);
						if (!f.exists())
							FileUtils.forceMkdir(f);
						fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																				// UN
																				// ARCHIVO
						workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
						fileOut.flush();// CERRAR
						fileOut.close();// CERRAR
					}
					return false;
				}
				if (!validarNotaPreescolar(num, y + 1)) {
					bandera = false;
					return false;
				}
			}
			validarEstudiantesFaltantes();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan filas importantes");
			System.out.println("Error validar Preescolar:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan hojas en el libro");
			System.out.println("Error validar Preescolar:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error validar Preescolar:" + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (!bandera && workbook != null) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: valida que todos los datos de la plantilla de evaluacinn de
	 * asignatura sean correctos <BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean validarFormatoAsignatura(int tipo, String[] archivo,
			String plantilla, String download, String par[]) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		error = true;
		boolean bandera = true;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet2 = workbook.getSheetAt(2);
			sheet = workbook.getSheetAt(1);
			if (!validarTipoPlantilla(tipo)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No es una plantilla de evaluacinn de Asignatura-Logro");
				bandera = false;
				return false;
			}
			/* VALIDAR QUE CORRESPONDA CON LA HOJA OCULTA */
			if (!validarEncabezadoAsignatura(par)) {
				if (!error)
					ponerEncabezado(
							Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio, sede y jornada.");
				else
					ponerEncabezado(Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"El encabezado ha sido modificado y no es posible importar la evaluacinn");
				error = true;
				bandera = false;
				return false;
			}
			/*
			 * if(!validarNumeroEstudiantesAsignatura()){
			 * ponerEncabezado(Properties.FILAMENSAJE,Properties.COLUMNAMENSAJE,
			 * "El nnmero de estudiantes indicado excede o es menor que el de los alumnos generados por la plantilla"
			 * ); bandera=false; return false; }
			 */
			filtro2 = getEncabezadoAsignatura();
			// VALIDAR SI ESTA CERRADO EL PERIODO
			if (!importarDAO.getCierreAsignatura(filtro2)) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se puede importar la evaluacinn porque el grupo esta cerrado para esta Asignatura y Periodo\n El grupo solo puede ser abierto por el Rector del colegio");
				bandera = false;
				return false;
			}
			// VALIDAR ESTUDIANTES/
			if (!validarEstudiantesAsignatura(filtro2)) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"Los datos de los estudiantes han sido modificados y no es posible importar la evaluacinn.\n Posiblemente la información fue modificada despues de la generacinn de la plantilla");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
				}
				return false;
			}
			// VALIDAR ESCALA VALORATIVA y LOS DEMAS DE EVALUACION EN CELDA DE
			// EVALUACION/
			// System.out.println("validarEscalaMotivoAsignatura BACTH");
			if (!validarEscalaMotivoAsignatura_(filtro2)) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"La escala valorativa no corresponde a los datos de la hoja 'Escala_valorativa'. \n Posiblemente la información fue modificada despues de la generacinn de la plantilla");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();
					fileOut.close();// CERRAR
				}
				return false;
			}
			/* VALIDAR LOGROS */
			if (!validarLogrosAsignatura()) {
				bandera = false;
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"La información de los logros fue modificada y no es posible importar los datos. \n Posiblemente la información fue modificada despues de la generacinn de la plantilla");
				return false;
			}
			/* VALIDAR ESCALA DE LOGROS */
			if (!validarEscalaLogro()) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"La escala valorativa de logros no corresponde a los datos de la hoja 'Escala_valorativa'. \n Posiblemente la informacion fue modificada despues de la generacinn de la plantilla");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
				}
				return false;
			}
			validarEstudiantesFaltantes();
		} catch (NullPointerException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan filas importantes");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan hojas en el libro");
			System.out.println("Error validar Asignatura:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error validar Asignatura:" + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (!bandera && workbook != null) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	public boolean validarFormatoArea(int tipo, String[] archivo,
			String plantilla, String download, String par[]) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		error = true;
		boolean bandera = true;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet2 = workbook.getSheetAt(6);
			sheet = workbook.getSheetAt(5);
			if (!validarTipoPlantilla(tipo)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No es una plantilla de evaluacinn de Area-Descriptor");
				bandera = false;
				return false;
			}
			/* VALIDAR QUE CORRESPONDA CON LA HOJA OCULTA */
			if (!validarEncabezadoArea(par)) {
				if (!error) {
					ponerEncabezado(
							Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio, sede y jornada.");
				} else {
					ponerEncabezado(Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"El encabezado ha sido modificado y no es posible importar la evaluacinn");
				}
				error = true;
				bandera = false;
				return false;
			}
			/*
			 * SE QUITA PARA QUE NO VALIDE LOS NUMEROS EXACTOS
			 * if(!validarNumeroEstudiantesArea()){
			 * ponerEncabezado(Properties.FILAMENSAJE,Properties.COLUMNAMENSAJE,
			 * "El nnmero de estudiantes indicado excede o es menor que el de los alumnos generados por la plantilla"
			 * ); bandera=false; return false; }
			 */
			filtro2 = getEncabezadoArea();
			// VALIDAR SI ESTA CERRADO EL PERIODO
			if (!importarDAO.getCierreArea(filtro2)) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No se puede importar la evaluación porque el grupo esta cerrado para esta área y Periodo\n El grupo solo puede ser abierto por el Rector del colegio");
				bandera = false;
				return false;
			}
			// VALIDAR ESTUDIANTES/
			if (!validarEstudiantesArea(filtro2)) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"Los datos de los estudiantes han sido modificados y no es posible importar la evaluacinn.\n Posiblemente la información fue modificada despues de la generación de la plantilla");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
				}
				return false;
			}
			// VALIDAR ESCALA VALORATIVA y LOS DEMAS DE EVALUACION EN CELDA DE
			// EVALUACION/
			if (!validarEscalaMotivoArea()) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"La escala valorativa no corresponde a los datos de la hoja 'Escala_valorativa'. \n Posiblemente la información fue modificada despues de la generacinn de la plantilla");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
				}
				return false;
			}
			escala2 = importarDAO.getDescriptores(filtro2);
			// validar los codigos de descriptor
			if (!validarCodigoDescriptorArea()) {
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"Las abreviaturas de descriptor indicadas en la asignacinn \n no corresponden a los datos de las hojas de Descriptores. \n Posiblemente la información fue modificada despues de la generacinn de la plantillas");
				bandera = false;
				if (!error) {
					f = new File(download);
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
				}
				return false;
			}
			/* SE QUITO PORQUE NO SE VALIDA CODIGO S DUPLICADOS */
			// validarEstudiantesFaltantes();
		} catch (NullPointerException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan filas importantes");
			System.out.println("Error validar Area:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla vnlido, Faltan hojas en el libro");
			System.out.println("Error validar Area:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error validar Area:" + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (!bandera && workbook != null) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
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
		return (cont == num2 ? true : false);
	}

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
		return (cont == num2 ? true : false);
	}

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
		return (cont == num2 ? true : false);
	}

	/**
	 * Funcinn: valida que todos los datos de la plantilla de bateria de logro
	 * sean correctos <BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean validarFormatoBateriaLogro(int tipo, String[] archivo,
			String plantilla, String download, String usuario[]) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		boolean bandera = true;
		error = true;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet2 = null;
			sheet2 = workbook.getSheetAt(2);
			sheet = workbook.getSheetAt(1);
			if (sheet2 == null) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"-No es una plantilla de importacinn de baterna de logros");
				bandera = false;
				return false;
			}
			if (!validarTipoPlantilla(tipo)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No es una plantilla de importacinn de baterna de logros");
				bandera = false;
				return false;
			}
			/* VALIDAR QUE EL ENCABEZADO CORRESPONDA CON LA HOJA OCULTA */
			sheet = workbook.getSheetAt(1);
			// System.out.println("Encabezado");
			if (!validarEncabezadoBateriaLogro(usuario)) {
				if (!error)
					ponerEncabezado(
							Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio");
				else
					ponerEncabezado(Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"El encabezado ha sido modificado y no es posible importar la evaluacinn");
				error = true;
				bandera = false;
				return false;
			}
			filtro = getEncabezadoBateriaLogro();
			/* VALIDAR LOS DATOS DEL USUARIO */
			if (!validarPeriodoBateriaLogro(filtro)) {
				// System.out.println("pone error de validacinn");
				ponerEncabezado(
						Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"Los datos indicados en la plantilla tienen errores, verifique el archivo de inconsistencias");
				bandera = false;
				if (!error) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																			// UN
																			// ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();// CERRAR
					/* voy aca */
					bandera = true;
				}
				return false;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan filas importantes");
			System.out.println("Error validar bateria Logro:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error validar bateria Logro:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(
					Properties.FILAMENSAJE,
					Properties.COLUMNAMENSAJE,
					"Error al validar plantilla de bateria de Logros: "
							+ e.toString());
			System.out.println("Error validar bateria Logro:  " + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (!bandera && workbook != null) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: valida que todos los datos de la plantilla de bateria de
	 * descriptor sean correctos <BR>
	 * 
	 * @param int tipo
	 * @param String
	 *            [] archivo
	 * @param String
	 *            plantilla
	 * @param String
	 *            download
	 * @return boolean
	 **/
	public boolean validarFormatoBateriaDescriptor(int tipo, String[] archivo,
			String plantilla, String download, String par[]) {
		FileInputStream input = null;
		FileOutputStream fileOut = null;
		File f = null;
		boolean bandera = true;
		error = true;
		try {
			input = new FileInputStream(archivo[0] + archivo[1]);
			workbook = new HSSFWorkbook(input);
			/* VALIDAR QUE SEA DEL TIPO DE PLANTILLA */
			sheet2 = workbook.getSheetAt(5);
			sheet = workbook.getSheetAt(1);
			if (!validarTipoPlantilla(tipo)) {
				ponerEncabezado(Properties.FILAMENSAJE,
						Properties.COLUMNAMENSAJE,
						"No es una plantilla de importacinn de baterna de Descriptores");
				bandera = false;
				return false;
			}
			/* VALIDAR QUE CORRESPONDA CON LA HOJA OCULTA */
			String[] fichas = { "", "Fortalezas", "Dificultades",
					"Recomendacinnes", "Estrategias" };
			for (int j = 1; j < 5; j++) {
				sheet = workbook.getSheetAt(j);
				if (!validarEncabezadoBateriaDescriptor(par)) {
					if (!error)
						ponerEncabezado(
								Properties.FILAMENSAJE,
								Properties.COLUMNAMENSAJE,
								"Los datos del encabezado de la plantilla no corresponden con los datos del usuario que inicin sesinn.\n Solo se permite importar a usuarios del mismo colegio, sede y jornada.");
					else
						ponerEncabezado(
								Properties.FILAMENSAJE,
								Properties.COLUMNAMENSAJE,
								"El encabezado de la ficha '"
										+ fichas[j]
										+ "' ha sido modificado y no es posible importar la bateria");
					error = true;
					bandera = false;
					return false;
				}
			}
			filtro = getEncabezadoBateriaDescriptor();
			desc = importarDAO.getDescriptoresActuales(filtro);
			// VALIDAR LOS DATOS DEL USUARIO
			int z;
			for (int j = 1; j < 5; j++) {
				z = 0;
				sheet = workbook.getSheetAt(j);
				if (!validarPeriodoBateriaDescriptor(j, desc)) {
					z = 1;
				}
				if (z == 1) {
					error = false;
					ponerEncabezado(
							Properties.FILAMENSAJE,
							Properties.COLUMNAMENSAJE,
							"Los datos indicados en la ficha '"
									+ fichas[j]
									+ "' tienen errores, verifique el archivo de inconsistencias.");
					bandera = false;
					if (!error) {
						f = new File(download);
						if (!f.exists())
							FileUtils.forceMkdir(f);
						fileOut = new FileOutputStream(archivo[0] + archivo[1]);// CREAR
																				// UN
																				// ARCHIVO
						workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
						fileOut.flush();// CERRAR
						fileOut.close();// CERRAR
					}
					return false;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan filas importantes");
			System.out.println("Error bateria descriptor:  " + e.toString());
			bandera = false;
			return false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					"No es un archivo de plantilla valido, Faltan hojas en el libro");
			System.out.println("Error bateria descriptor:  " + e.toString());
			bandera = false;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ponerEncabezado(Properties.FILAMENSAJE, Properties.COLUMNAMENSAJE,
					e.toString());
			System.out.println("Error bateria descriptor:  " + e.toString());
			bandera = false;
			return false;
		} finally {
			try {
				if (!bandera && workbook != null) {
					fileOut = new FileOutputStream(archivo[0] + archivo[1]);
					workbook.write(fileOut);
					fileOut.flush();
				}
				if (input != null)
					input.close();
				if (fileOut != null)
					fileOut.close();
			} catch (IOException e) {
			}
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

	/**
	 * Funcinn: Devuelve el valor real de un acelda del archivo<BR>
	 * 
	 * @param HSSFCell
	 *            celda
	 * @return String
	 **/
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

	public void ponerEncabezado(int fila, int col, String mensaje) {
		try {
			if (sheet != null) {
				row = sheet.getRow((short) fila);
				if (row == null)
					row = sheet.createRow((short) fila);
				cell = row.getCell((short) col);
				if (cell == null)
					cell = row.createCell((short) col);
				cell.setCellValue(mensaje);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}