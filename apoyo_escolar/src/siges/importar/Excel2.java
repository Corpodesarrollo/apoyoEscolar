/**
 * 
 */
package siges.importar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.common.vo.ItemVO;  
import siges.importar.beans.ParamsVO;
import siges.importar.beans.UrlImportar;
import siges.login.beans.Login;
import siges.plantilla.beans.EvaluacionEstudiante;
import siges.plantilla.beans.FiltroComportamiento;
import siges.plantilla.comportamiento.dao.ComportamientoDAO;
import siges.util.beans.Comportamiento;

/**
 * 15/09/2007 
 * @author Latined
 * @version 1.2
 */
public class Excel2 {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2;
	private	HSSFRow row,row2;
	private	HSSFCell cell,cell2;
	private boolean band=true;
	ComportamientoDAO comportamientoDAO=null;
	/**
	 * Constructor
	 *  
	 */
	public Excel2(ComportamientoDAO comportamientoDAO) {
		this.comportamientoDAO=comportamientoDAO;
	}
	
	public UrlImportar importarComportamiento(UrlImportar url,List escala,Login login) throws Exception{
		url.setEstado(0);
		File f = null;
		band=true;
		boolean band2=true;
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		FiltroComportamiento filtro =null;
		try{
			input=new FileInputStream(url.getPathPlantilla()+url.getNombrePlantilla());//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){
				throw new Exception("No hay libro");
			}
			//hoja ooculta
			sheet2 = workbook.getSheetAt(2);//OBTENER HOJA DE ESCALAS
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE ESCALAS
			//validar el tipo de plantilla
			if (!validarTipoComportamiento()) {
				//setMensaje("No es una plantilla de evaluacinn de Asignatura-Logro");
				url.setEstado(-1);
				return url;
			}
			//validar el encabezado de la plantilla
			  filtro = getEncabezadoComportamiento( filtro);
			if (filtro==null) {
				url.setEstado(-2);//error en filtro
				return url;
			}
			//validar los estudiantes
			escala = comportamientoDAO.getEscalaNivelEval(filtro);
			 
			List listaEstudiantes = getEstudianteComportamiento(escala, filtro);
			  
			if (listaEstudiantes==null) {
			System.out.println("ESTADO -3");
				url.setEstado(-3);//error en estudiantes
				if (!band) {
					System.out.println("ESCRIBIR ACRCHVIO " + url.getPathDescarga());
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
			url=comportamientoDAO.insertarEvalComportamiento(filtro,listaEstudiantes,url);
		}catch(IOException e){
			e.printStackTrace();
	    	System.out.println("Error Plantilla Comportamiento: "+e.toString());
	    	band2=false;
			throw new Exception(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
	    	System.out.println("Error Plantilla Comportamiento: "+e.toString());
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
	    	}catch(IOException e){}
		}
		return url;
	}
	
	/**
	 * @function:  
	 * @param escala
	 * @return
	 */
	/**
	 * @function:  
	 * @param escala
	 * @param filtroPlantilla
	 * @return
	 * @throws Exception
	 */
	private List getEstudianteComportamiento(List escala, FiltroComportamiento filtroPlantilla ) throws Exception{
		
		List l=null;
		EvaluacionEstudiante estudiante=null;
		ItemVO item=null;
		band=true;
		boolean bandera=false;
		try {
			l=new ArrayList();
			int num1 = sheet.getPhysicalNumberOfRows();
			int num2 = sheet2.getPhysicalNumberOfRows();
			 
			
			
			if(num1!=num2){//no son la misma cantidad de filas
				return null;
			}
			num2 = num2 - Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.FIL];
		
			for (int i = Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.FIL]; i < num1; i++) {
				estudiante=new EvaluacionEstudiante();
				row = sheet.getRow(i);
				row2 = sheet2.getRow(i);
				if (row==null || row2==null ){
					return null;
				}
				//codigo
				cell2 = row2.getCell((short)Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.COL]);
				

				estudiante.setEvalCodigo((long)cell2.getNumericCellValue());
				 
				cell = row.getCell((short)Comportamiento.plantilla[Comportamiento.Apellido][Comportamiento.COL]);
				cell2 = row2.getCell((short)Comportamiento.plantilla[Comportamiento.Apellido][Comportamiento.COL]);
				if(cell==null || cell.getStringCellValue()==null || !cell.getStringCellValue().equals(cell2.getStringCellValue())){
					return null;
				}
				//nom
				cell = row.getCell((short)Comportamiento.plantilla[Comportamiento.Nombre][Comportamiento.COL]);
				cell2 = row2.getCell((short)Comportamiento.plantilla[Comportamiento.Nombre][Comportamiento.COL]);
				if(cell==null || cell.getStringCellValue()==null || !cell.getStringCellValue().equals(cell2.getStringCellValue())){
					return null;
				}
				//nota con
				cell = row.getCell((short)Comportamiento.plantilla[Comportamiento.EvaluacionConceptual][Comportamiento.COL]);
				if(cell!=null){
					estudiante.setEvalNotaCon(getValorString(cell));
				 
					 
					if(estudiante.getEvalNotaCon()!=null && !estudiante.getEvalNotaCon().equals("")){
						bandera=false;
						for(int j=0;j<escala.size();j++){
							item=(ItemVO)escala.get(j);
						 	
							// Si es conceptual comparar dos string si no validar rango del numero
							if(filtroPlantilla.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL ){
								if(item.getNombre().trim().equals(estudiante.getEvalNotaCon().trim() )){
									//estudiante.setEvalNota((int)item.getCodigo());
									estudiante.setEvalNotaConCodigo((int)item.getCodigo());
									bandera=true;
								}
							}else{
								if(GenericValidator.isDouble(estudiante.getEvalNotaCon().trim())){
									
									double valorMin = Double.valueOf( item.getNombre().trim()).doubleValue();
									double valorMax = Double.valueOf( item.getPadre2().trim()).doubleValue();
									double valorNota = Double.valueOf( estudiante.getEvalNotaCon().trim()).doubleValue();
									
								 
									if( (valorMin   <= valorNota)  &&  (valorMax   >= valorNota) ){
										//estudiante.setEvalNota((int)item.getCodigo());
										estudiante.setEvalNotaConCodigo(valorNota); 
										bandera=true;
									}
									
								} 
							}
						}
						System.out.println("band " + band);
						if(!bandera){
							band=false;
							cell = row.createCell((short)(Comportamiento.plantilla[Comportamiento.Observacion][Comportamiento.COL]+1));
							cell.setCellValue("Campo 'evaluacinn'  no corresponde a los posibles valores");
							
						}
					}	
				}
				//obs
				cell = row.getCell((short)Comportamiento.plantilla[Comportamiento.Observacion][Comportamiento.COL]);
				if(cell!=null){
					estudiante.setEvalObservacion(getValorString(cell));
				}
				l.add(estudiante);
			}
			System.out.println("band " + band);
			if(!band){
				
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage() ); 
			throw new Exception(e.getMessage());
		} 
		System.out.println("l " + l.size() );
		return l;
	}
	
	/**
	 * @function:  
	 * @param filtro
	 * @return
	 */
	private FiltroComportamiento getEncabezadoComportamiento(FiltroComportamiento filtro){
		
		try {
			filtro=new FiltroComportamiento();
			filtro.setFilTipo(ParamsVO.FICHA_COMPORTAMIENTO);
			//Institucion
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Institucion1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Institucion1][Comportamiento.COL]);
				filtro.setFilInstitucion((long)cell2.getNumericCellValue());
			}
			//Sede
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Sede1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Sede1][Comportamiento.COL]);
				filtro.setFilSede((int)cell2.getNumericCellValue());
			}
			//Jornada
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Jornada1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Jornada1][Comportamiento.COL]);
				filtro.setFilJornada((int)cell2.getNumericCellValue());
			}
			
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
			
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Metodologia1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Metodologia1][Comportamiento.COL]);
				filtro.setFilMetodologia((int)cell2.getNumericCellValue());
			}
			
			
			
			
			//Grado
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grado1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grado1][Comportamiento.COL]);
				filtro.setFilGrado((int)cell2.getNumericCellValue());
			}
			//Grupo
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grupo1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grupo1][Comportamiento.COL]);
				filtro.setFilGrupo((int)cell2.getNumericCellValue());
			}
			//periodo
			row = sheet.getRow(Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.FIL]);
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.FIL]);
			cell = row.getCell((short) Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.COL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.COL]);
			if (!getValorString(cell).equals(cell2.getStringCellValue())){
				return null;
			}else{
				row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Periodo1][Comportamiento.FIL]);
				cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Periodo1][Comportamiento.COL]);
				filtro.setFilPeriodo((int)cell2.getNumericCellValue());
			}
			//jerarquia de grupo
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.JerarquiaGrupo][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.JerarquiaGrupo][Comportamiento.COL]);
			filtro.setFilJerarquia((long)cell2.getNumericCellValue());
		} catch (Exception e) {e.printStackTrace();
			return null;
		}
		return filtro;
	}
	
	private boolean validarTipoComportamiento() {
		try {
			long val;
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Tipo][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Tipo][Comportamiento.COL]);
			val = (long)cell2.getNumericCellValue();
			if (val != ParamsVO.FICHA_COMPORTAMIENTO)
				return false;
		} catch (Exception e) {e.printStackTrace();
			return false;
		}
		return true;
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

	
	

	/**
	 * @function:  Funcinn que carga los datos que viene en el archivo 
	 *            de excel
	 * @param filtro
	 * @return
	 */
	public FiltroComportamiento getCargarFiltro(FiltroComportamiento filtro){
		
		try {
			filtro=new FiltroComportamiento();
			filtro.setFilTipo(ParamsVO.FICHA_COMPORTAMIENTO);
			//Institucion
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.COL]);
			filtro.setFilInstitucion((long)cell2.getNumericCellValue());
			
			//Sede
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.COL]);
				filtro.setFilSede((int)cell2.getNumericCellValue());
			
				
			//Jornada
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.COL]);
			filtro.setFilJornada((int)cell2.getNumericCellValue());
			
				
			//Grado
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.COL]);
			filtro.setFilGrado((int)cell2.getNumericCellValue());
			
				
			//Grupo
			
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.COL]);
			filtro.setFilGrupo((int)cell2.getNumericCellValue());
			
				
			//periodo
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.Periodo1][Comportamiento.COL]);
			filtro.setFilPeriodo((int)cell2.getNumericCellValue());
			
			//jerarquia de grupo
			row2 = sheet2.getRow(Comportamiento.plantilla[Comportamiento.JerarquiaGrupo][Comportamiento.FIL]);
			cell2 = row2.getCell((short) Comportamiento.plantilla[Comportamiento.JerarquiaGrupo][Comportamiento.COL]);
			filtro.setFilJerarquia((long)cell2.getNumericCellValue());
		} catch (Exception e) {e.printStackTrace();
			return null;
		}
		return filtro;
	}
	
	
	
}
