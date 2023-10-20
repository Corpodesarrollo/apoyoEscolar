/**
 * 
 */
package articulacion.artPlantillaFinal.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import articulacion.artPlantillaFinal.dao.PlantillaFinalDAO;
import articulacion.artPlantillaFinal.vo.AsignaturaVO;
import articulacion.artPlantillaFinal.vo.EscalaVO;
import articulacion.artPlantillaFinal.vo.EstudianteVO;
import articulacion.artPlantillaFinal.vo.PlantillaFinalVO;
import articulacion.artPlantillaFinal.vo.UrlImportar;
import articulacion.artPlantillaFinal.vo.plantilla.ExcelVO;
import siges.common.vo.ItemVO;
import siges.common.vo.Url;
import articulacion.artPlantillaFinal.vo.ParamsVO;
import siges.login.beans.Login;

/**
 * 6/12/2007 
 * @author Latined
 * @version 1.2
 */
public class Excel {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2;
	private	HSSFRow row,row2;
	private	HSSFCell cell,cell2;
	private	PlantillaFinalDAO plantillaFinalDAO;
	private boolean band=true;
	/**
	 * Constructor
	 *  
	 */
	public Excel(PlantillaFinalDAO plantilla) {
		this.plantillaFinalDAO=plantilla;
	}
	public Excel() {
	}


	public void plantillaFinal(PlantillaFinalVO filtro,List estudiante,List asignatura,Url url) throws Exception{
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(url.getPathPlantilla()+url.getNombrePlantilla());//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){
				throw new Exception("No hay libro");
			}
			//hoja de estudiantes
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE datos
			setEstudiante(estudiante);
			setAsignatura(asignatura);
			setEncabezado(filtro);
			//hoja de oculta
			sheet = workbook.getSheetAt(2);//OBTENER HOJA oculta
			setEstudianteOculto(estudiante);
			setAsignaturaOculto(asignatura);
			setEncabezadoOculto(filtro);
			//poner el archivo
			File f=new File(url.getPathDescarga());
			if(!f.exists()) FileUtils.forceMkdir(f);
			fileOut= new FileOutputStream(url.getPathDescarga()+url.getNombreDescarga());//CREAR UN ARCHIVO
			workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();//CERRAR
			fileOut.close();
			input.close();
		}catch(IOException e){
			e.printStackTrace();
//	    	System.out.println("Error Plantilla Final: "+e.toString());
			throw new Exception(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
//	    	System.out.println("Error Plantilla Final: "+e.toString());
			throw new Exception(e.getMessage());
		}finally{
	    	try{
	    		if(input!=null)input.close();
	    		if(fileOut!=null)fileOut.close();
	    	}catch(IOException e){}
		}
	}
	
	private void setEstudiante(List datos) throws Exception{
		List nota=null;
		if(datos!=null && datos.size()>0){
			EstudianteVO estudiante=null;
			int numFila=ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.FIL];
			int numColumnaNota=0;
			for(int i=0;i<datos.size();i++){
				numColumnaNota=ExcelVO.plantilla[ExcelVO.nota][ExcelVO.COL];
				estudiante=(EstudianteVO)datos.get(i);
				nota=estudiante.getEstNota();
				row=sheet.createRow(numFila++);
				//consecutivo
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstConsecutivo());	
				//apellidos
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.apellido][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstApellido());	
				//nombres
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.nombre][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstNombre());
				if(nota!=null){
					for(int j=0;j<nota.size();j++){
			 			cell=row.createCell((short)numColumnaNota++);
						cell.setCellValue((String)nota.get(j));
					}
				}
			}
		}
	}

	private void setAsignatura(List datos) throws Exception{
		if(datos!=null && datos.size()>0){
			AsignaturaVO asignatura=null;
			int numFila=ExcelVO.plantilla[ExcelVO.nombreAsignatura][ExcelVO.FIL];
			int numColumna=ExcelVO.plantilla[ExcelVO.nombreAsignatura][ExcelVO.COL];
			for(int i=0;i<datos.size();i++){
				asignatura=(AsignaturaVO)datos.get(i);
				row=sheet.createRow(numFila);
				//consecutivo
	 			cell=row.createCell((short)numColumna++);
				cell.setCellValue(asignatura.getAsiNombre());	
			}
		}
	}
	
	private void setAsignaturaOculto(List datos) throws Exception{
		if(datos!=null && datos.size()>0){
			AsignaturaVO asignatura=null;
			int numFila=ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.FIL];
			int numColumna=ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL];
			for(int i=0;i<datos.size();i++){
				asignatura=(AsignaturaVO)datos.get(i);
				row=sheet.createRow(numFila);
				//consecutivo
	 			cell=row.createCell((short)numColumna++);
				cell.setCellValue(asignatura.getAsiCodigo());	
			}
		}
	}
	
	private void setEstudianteOculto(List datos) throws Exception{
		if(datos!=null && datos.size()>0){
			EstudianteVO estudiante=null;
			int numFila=ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.FIL];
			for(int i=0;i<datos.size();i++){
				estudiante=(EstudianteVO)datos.get(i);
				row=sheet.createRow(numFila++);
				//consecutivo
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstCodigo());	
				//apellidos
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.apellido][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstApellido());	
				//nombres
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.nombre][ExcelVO.COL]);
				cell.setCellValue(estudiante.getEstNombre());	
			}
		}
	}

	private void setEncabezado(PlantillaFinalVO filtro) throws Exception{
		if(filtro!=null){
			Date d = new Date(new java.util.Date().getTime());
			//institucion
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreInstitucion][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreInstitucion][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreInstitucion());
			//Sede
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreSede][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreSede][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreSede());
			//jornada
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreJornada][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreJornada][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreJornada());
			//grado
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreGrado][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreGrado][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreGrado());
			//grupo
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreGrupo][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreGrupo][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreGrupo());
			//componente
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreComponente][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreComponente][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreComponente());
			//especialidad
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreEspecialidad][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreEspecialidad][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreEspecialidad());
			//vigencia
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreAnhoVigencia][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreAnhoVigencia][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreAnhoVigencia()+" - "+filtro.getPlaNombrePerVigencia());
			//semestre
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.nombreSemestre][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.nombreSemestre][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaNombreSemestre());
		}
	}


	private void setEncabezadoOculto(PlantillaFinalVO filtro) throws Exception{
		if(filtro!=null){
			//tipo
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.tipoPlantilla][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.tipoPlantilla][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaTipo());
			//jerarquia
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.jerarquiaGrupo][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaJerarquia());
			//institucion
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaInstitucion());
			//Sede
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaSede());
			//jornada
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaJornada());
			//grado
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.grado][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.grado][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaGrado());
			//grupo
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaGrupo());
			//metodologia
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaMetodologia());
			//componente
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.componente][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.componente][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaComponente());
			//especialidad
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.especialidad][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.especialidad][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaEspecialidad());
			//ano vigencia
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaAnhoVigencia());
			//per vigencia
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.perVigencia][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.perVigencia][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaPerVigencia());
			//semestre
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.semestre][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.semestre][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaSemestre());
			//total Estudiantes
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.totalEstudiantes][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.totalEstudiantes][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaTotalEstudiantes());
			//total Asignaturas
			row=sheet.getRow((short)ExcelVO.plantilla[ExcelVO.totalAsignaturas][ExcelVO.FIL]);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.totalAsignaturas][ExcelVO.COL]);
			cell.setCellValue(filtro.getPlaTotalAsignaturas());
		}
	}

	public UrlImportar importarPlantilla(UrlImportar url,Login login) throws Exception{
		url.setEstado(0);
		File f = null;
		band=true;
		boolean band2=true;
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		List escala=null;
		try{
			input=new FileInputStream(url.getPathPlantilla()+url.getNombrePlantilla());//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){
				throw new Exception("No hay libro");
			}
			//hoja ooculta
			sheet2 = workbook.getSheetAt(2);//OBTENER HOJA oculta
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE datos
			//validar el tipo de plantilla
			if (!validarTipo()) {
				url.setEstado(-1);
				return url;
			}	
			//validar el encabezado de la plantilla
			PlantillaFinalVO filtro=getEncabezado();
			if (filtro==null) {
				url.setEstado(-2);//error en filtro
				return url;
			}
			//traer la escala y el valor maximo de nota
			escala=plantillaFinalDAO.getEscala(filtro);
			filtro.setPlaNotaMax(plantillaFinalDAO.getNotaMax());
			//validar los estudiantes
			List listaAsignaturas=getAsignatura(filtro);
			if (listaAsignaturas==null) {
				url.setEstado(-3);//error en estudiantes
				return url;
			}
			List listaEstudiantes=getEstudiante(filtro,escala);
			if (listaEstudiantes==null) {
				url.setEstado(-3);//error en estudiantes
				if (!band) {
					f = new File(url.getPathDescarga());
					if (!f.exists())
						FileUtils.forceMkdir(f);
					fileOut = new FileOutputStream(url.getPathDescarga()+ url.getNombreDescarga());// CREAR UN ARCHIVO
					workbook.write(fileOut);// ESCRIBIR EL ARCHIVO A DISCO
					fileOut.flush();// CERRAR
					fileOut.close();
				}
				return url;
			}
			url=plantillaFinalDAO.insertarEvaluacionFinal(filtro,listaEstudiantes,listaAsignaturas,url);
			//temporal
			url.setEstado(1);
			//temporal
		}catch(IOException e){
			e.printStackTrace();
//	    	System.out.println("Error Plantilla Comportamiento: "+e.toString());
	    	band2=false;
			throw new Exception(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
//	    	System.out.println("Error Plantilla Comportamiento: "+e.toString());
	    	band2=false;
			throw new Exception(e.getMessage());
		}finally{
	    	try{
	    		if(input!=null)input.close();
	    		if(fileOut!=null)fileOut.close();
				if (!band || !band2) {
					f = new File(url.getPathPlantilla()+url.getNombrePlantilla());
					if (f.exists()) FileUtils.forceDelete(f);
				}
	    	}catch(IOException e){e.printStackTrace();}
		}
		return url;
	}
	
	private boolean validarTipo() {
		try {
			long val;
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.tipoPlantilla][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.tipoPlantilla][ExcelVO.COL]);
			val = (long)cell2.getNumericCellValue();
			if (val != ParamsVO.PLANTILLA_FINAL)
				return false;
		} catch (Exception e) {e.printStackTrace();
			return false;
		}
		return true;
	}

	private PlantillaFinalVO getEncabezado(){
		PlantillaFinalVO filtro=null;
		try {
			filtro=new PlantillaFinalVO();
			filtro.setPlaTipo(ParamsVO.PLANTILLA_FINAL);
			//Institucion
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaInstitucion((long)cell2.getNumericCellValue());
			}
			//Sede
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaSede((int)cell2.getNumericCellValue());
			}
			//Jornada
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaJornada((int)cell2.getNumericCellValue());
			}
			//Grado
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.grado][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.grado][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaGrado((int)cell2.getNumericCellValue());
			}
			//Grupo
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaGrupo((int)cell2.getNumericCellValue());
			}
			//Metodologia
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaMetodologia((int)cell2.getNumericCellValue());
			}
			//Componente
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.componente][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.componente][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaComponente((int)cell2.getNumericCellValue());
			}
			//Especialidad
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.especialidad][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.especialidad][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaEspecialidad((long)cell2.getNumericCellValue());
			}
			//Anho Vigencia
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaAnhoVigencia((int)cell2.getNumericCellValue());
			}
			//Per Vigencia
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.perVigencia][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.perVigencia][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaPerVigencia((int)cell2.getNumericCellValue());
			}
			//Semestre
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.semestre][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.semestre][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaSemestre((int)cell2.getNumericCellValue());
			}
			//Jerarquia
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.jerarquiaGrupo][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.jerarquiaGrupo][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaJerarquia((long)cell2.getNumericCellValue());
			}
			//Total Estudiantes
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.totalEstudiantes][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.totalEstudiantes][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaTotalEstudiantes((int)cell2.getNumericCellValue());
			}
			//Total Asignaturas
			row2 = sheet2.getRow(ExcelVO.plantilla[ExcelVO.totalAsignaturas][ExcelVO.FIL]);
			cell2 = row2.getCell((short) ExcelVO.plantilla[ExcelVO.totalAsignaturas][ExcelVO.COL]);
			if (getValorString(cell2)==null){
				return null;
			}else{
				filtro.setPlaTotalAsignaturas((int)cell2.getNumericCellValue());
			}
		} catch (Exception e) {e.printStackTrace();
			return null;
		}
		return filtro;
	}
	
	public String getValorString(HSSFCell celda) {
		String valor = null;
		if (celda == null) return null;
		switch (celda.getCellType()) {
		case 0: // numerico
			valor = String.valueOf((double) celda.getNumericCellValue());
			break;
		case 1:// string
			valor = celda.getStringCellValue();
			break;
		case 2:// formula
			valor = celda.getStringCellValue().trim();
			break;
		case 3:
			valor = null;
			break;// blank
		case 4:// boolean
			valor = celda.getStringCellValue().trim();
			break;
		case 5: // error
			valor = celda.getStringCellValue().trim();
			break;
		}
		return valor;
	}
	
	private List getEstudiante(PlantillaFinalVO filtro,List escala){
		List l=null;
		EstudianteVO estudiante=null;
		String valorNota=null;
		float valorNotaNum=0;
		List listaNota=null;
		boolean bandera=false;
		try {
			l=new ArrayList();
			int num1 = sheet.getPhysicalNumberOfRows()-ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.FIL];
			int num2 = filtro.getPlaTotalEstudiantes();
			if(num1!=num2){//no son la misma cantidad de filas
				return null;
			}
			num2 = ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.FIL]+filtro.getPlaTotalEstudiantes();
			
			for (int i = ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.FIL]; i < num2; i++) {
				listaNota=new ArrayList();
				estudiante=new EstudianteVO();
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				if (row==null || row2==null ){
					return null;
				}
				//codigo
				cell2 = row2.getCell((short)ExcelVO.plantilla[ExcelVO.codigo][ExcelVO.COL]);
				estudiante.setEstCodigo((long)cell2.getNumericCellValue());
				//System.out.println("ESTUDIANTE: "+estudiante.getEstCodigo());
				//ape
				cell = row.getCell((short)ExcelVO.plantilla[ExcelVO.apellido][ExcelVO.COL]);
				cell2 = row2.getCell((short)ExcelVO.plantilla[ExcelVO.apellido][ExcelVO.COL]);
				if(cell==null || cell.getStringCellValue()==null || !cell.getStringCellValue().equals(cell2.getStringCellValue())){
					return null;
				}
				//nom
				cell = row.getCell((short)ExcelVO.plantilla[ExcelVO.nombre][ExcelVO.COL]);
				cell2 = row2.getCell((short)ExcelVO.plantilla[ExcelVO.nombre][ExcelVO.COL]);
				if(cell==null || cell.getStringCellValue()==null || !cell.getStringCellValue().equals(cell2.getStringCellValue())){
					return null;
				}
				//nota 
				for(int j=ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL];j<(ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL]+filtro.getPlaTotalAsignaturas());j++){
					cell = row.getCell((short)j);
					valorNota=getValorString(cell);
					//System.out.println("NOTA: "+valorNota);
					if(valorNota==null) {
						listaNota.add(null);
					}else{
						if(!GenericValidator.isFloat(valorNota)){
							band=false;
							cell = row.createCell((short)(ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL]+filtro.getPlaTotalAsignaturas()));
							cell.setCellValue("Campo evaluacinn debe ser numnrico y no puede superar el rango 0.0 - "+filtro.getPlaNotaMax());
							return null;
						}
						valorNotaNum=Float.parseFloat(valorNota);
						if(valorNotaNum<0 || valorNotaNum>filtro.getPlaNotaMax()){
							band=false;
							cell = row.createCell((short)(ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL]+filtro.getPlaTotalAsignaturas()));
							cell.setCellValue("Campo evaluacinn debe ser numnrico y no puede superar el rango 0.0 - "+filtro.getPlaNotaMax());
							return null;
						}
						listaNota.add(valorNota);
					}
				}
				estudiante.setEstNota(listaNota);
				l.add(estudiante);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return l;
	}
	
	private List getAsignatura(PlantillaFinalVO filtro){
		List l=null;
		AsignaturaVO asignatura=null;
		try {
			l=new ArrayList();
			int filaAsignatura = ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.FIL];
			int columnaAsignatura = ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL];
			row2 = sheet2.getRow(filaAsignatura);
			if (row2==null ){
				return null;
			}
			for (int i = columnaAsignatura; i < (columnaAsignatura+filtro.getPlaTotalAsignaturas()); i++) {
				asignatura=new AsignaturaVO();
				//codigo
				cell2 = row2.getCell((short)i);
				asignatura.setAsiCodigo((long)cell2.getNumericCellValue());
				//System.out.println("ASIGNATURA: "+asignatura.getAsiCodigo());
				l.add(asignatura);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return l;
	}
	
	/**
	 * @return Return the band.
	 */
	public boolean isBand() {
		return band;
	}
	/**
	 * @param band The band to set.
	 */
	public void setBand(boolean band) {
		this.band = band;
	}


}
