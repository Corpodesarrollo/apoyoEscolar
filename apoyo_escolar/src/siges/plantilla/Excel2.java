/**
 * 
 */
package siges.plantilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import siges.common.vo.ItemVO;
import siges.common.vo.Url; 
import siges.plantilla.beans.EvaluacionEstudiante;
import siges.plantilla.beans.FiltroComportamiento;
import siges.plantilla.beans.ParamsVO;
import siges.util.beans.Comportamiento;

/**
 * 14/09/2007 
 * @author Latined
 * @version 1.2
 */
public class Excel2 {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2;
	private	HSSFRow row,row2;
	private	HSSFCell cell,cell2;
	
	public void plantillaComportamiento(FiltroComportamiento filtro,List estudiante,List escala,Url url) throws Exception{
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(url.getPathPlantilla()+url.getNombrePlantilla());//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){
				throw new Exception("No hay libro");
			}
			//hoja de escalas
			sheet = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
			setEscalaComportamiento(escala, filtro.getFilCodTipoEval());
			//hoja de estudiantes
			sheet = workbook.getSheetAt(1);//OBTENER HOJA NOTAS Y OBSERVACIONES
			setEstudianteComportamiento(filtro, escala, estudiante);
			setEncabezadoComportamiento(filtro);
			//hoja de oculta
			sheet = workbook.getSheetAt(2);//OBTENER HOJA DE ESCALAS
			setEstudianteComportamientoOculto(estudiante);
			setEncabezadoComportamientoOculto(filtro);
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
			System.out.println("Error Plantilla Comportamiento: "+e.toString());
			throw new Exception(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error Plantilla Comportamiento: "+e.toString());
			throw new Exception(e.getMessage());
		}finally{
			try{
				if(input!=null)input.close();
				if(fileOut!=null)fileOut.close();
			}catch(IOException e){}
			
		}
	}
	
	/**
	 * @function:  
	 * @param datos
	 * @throws Exception
	 */
	private void setEscalaComportamiento(List datos, long codTipoEscala) throws Exception{
		if(datos!=null && datos.size()>0){
			ItemVO escalaVO=null;
			
			
			int numFila=Comportamiento.plantilla[Comportamiento.EscalaComportamiento1][Comportamiento.FIL];
			
			/** MODIFICADO 29-03-09
			 * */
			
			for(int i=0;i<datos.size();i++){
				escalaVO=(ItemVO)datos.get(i);
				if(i == 0){
					row=sheet.createRow(numFila-1);
					
					if(codTipoEscala != ParamsVO.ESCALA_CONCEPTUAL ){
						cell = row.createCell((short)Comportamiento.plantilla[Comportamiento.EscalaComportamiento1][Comportamiento.COL]);
						cell.setCellValue((String) ("VALOR MINIMO"));
						HSSFCellStyle cellstyle1 = workbook.createCellStyle();
						
						cellstyle1.setBorderBottom(cellstyle1.BORDER_THIN); 
						cellstyle1.setFillForegroundColor(HSSFColor.WHITE.index);
						cellstyle1.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
						cellstyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						
						HSSFFont cellfont   = workbook.createFont(); 
						cellfont.setColor(HSSFColor.DARK_TEAL.index);
						cellfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						cellstyle1.setFont(cellfont);
						cell.setCellStyle(cellstyle1);
						
						cell = row.createCell((short)(Comportamiento.plantilla[Comportamiento.EscalaComportamiento1][Comportamiento.COL] + 1));
						cell.setCellValue((String) ("VALOR MnXIMO"));
						HSSFCellStyle cellstyle2 = workbook.createCellStyle();
						cellstyle2.setFont(cellfont);
						
						cellstyle2.setBorderBottom(cellstyle1.BORDER_THIN); 
						cellstyle2.setFillForegroundColor(HSSFColor.WHITE.index);
						cellstyle2.setFillForegroundColor(HSSFColor.WHITE.index);
						cellstyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cell.setCellStyle(cellstyle2);
					}	
				}
				/** --
				 * */
				
				row=sheet.createRow(numFila++);
				//codigo
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.EscalaComportamiento1][Comportamiento.COL]);
				cell.setCellValue(escalaVO.getNombre());	
				//nombre
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.EscalaComportamiento2][Comportamiento.COL]);
				cell.setCellValue(escalaVO.getPadre2());	
			}
		}
	}
	
	/**
	 * @function:  
	 * @param filtro
	 * @param escala
	 * @param datos
	 * @throws Exception
	 */
	private void setEstudianteComportamiento(FiltroComportamiento filtro, List escala, List datos) throws Exception{
		//System.out.println("setEstudianteComportamiento");
		
		if(datos!=null && datos.size()>0){
			
			
			//Si es escala conceptual se debe colocar su equivalencia en letra
			//System.out.println("filtro.getFilCodTipoEval() " + filtro.getFilCodTipoEval());
			if (filtro.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL ) {
				datos = colocarLiteral(escala, datos);	
			}
			
			
			
			EvaluacionEstudiante estudiante=null;
			int numFila=Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.FIL];
			for(int i=0;i<datos.size();i++){
				estudiante=(EvaluacionEstudiante)datos.get(i);
				row=sheet.createRow(numFila++);
				//consecutivo
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalConsecutivo());	
				//apellidos
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Apellido][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalApellido());	
				//nombres
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Nombre][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalNombre());	
				//Evaluacion
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.EvaluacionConceptual][Comportamiento.COL]);
				if(estudiante.getEvalNotaConCodigo() >0){
						
					if (filtro.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL ) {
						
						cell.setCellValue(estudiante.getEvalNotaCon());
					}else{
						cell.setCellValue(estudiante.getEvalNotaConCodigo());	
					}
					
				}else{
					cell.setCellValue("");
				}
				//Observacion
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Observacion][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalObservacion());	
			}
		}
	}
	private void setEstudianteComportamientoOculto(List datos) throws Exception{
		if(datos!=null && datos.size()>0){
			EvaluacionEstudiante estudiante=null;
			int numFila=Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.FIL];
			for(int i=0;i<datos.size();i++){
				estudiante=(EvaluacionEstudiante)datos.get(i);
				row=sheet.createRow(numFila++);
				//codigo
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Codigo][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalCodigo());	
				//apellidos
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Apellido][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalApellido());	
				//nombres
				cell=row.createCell((short)Comportamiento.plantilla[Comportamiento.Nombre][Comportamiento.COL]);
				cell.setCellValue(estudiante.getEvalNombre());	
			}
		}
	}
	
	private void setEncabezadoComportamiento(FiltroComportamiento filtro) throws Exception{
		if(filtro!=null){
			Date d = new Date(new java.util.Date().getTime());
			//FILA 1
			//institucion
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreInstitucion());
			//FILA 2
			//Sede
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreSede());
			//jornada
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreJornada());
			
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreMetodologia());
			
			
			
			//FILA 3
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.FIL]);
			//grado
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreGrado());
			//grupo
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreGrupo());
			//fecha
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Fecha2][Comportamiento.COL]);
			cell.setCellValue(d.toString());
			//FILA 4
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.FIL]);
			//periodo
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombrePeriodo());
		}
	}
	
	
	private void setEncabezadoComportamientoOculto(FiltroComportamiento filtro) throws Exception{
		if(filtro!=null){
			//fila 0
			//tipo
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Tipo][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Tipo][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilTipo());
			//jerarquia
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.JerarquiaGrupo][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilJerarquia());
			//FILA 1
			//institucion
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Institucion2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreInstitucion());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Institucion1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilInstitucion());
			//FILA 2
			//Sede
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Sede2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreSede());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Sede1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilSede());
			//jornada
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Jornada2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreJornada());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Jornada1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilJornada());
			
			//Metodologia
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.FIL]);
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Metodologia2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreMetodologia());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Metodologia1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilMetodologia());
			
			//FILA 3
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.FIL]);
			//grado
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grado2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreGrado());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grado1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilGrado());
			
			
			//grupo
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grupo2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombreGrupo());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Grupo1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilGrupo());
			//FILA 4
			row=sheet.getRow((short)Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.FIL]);
			//periodo
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Periodo2][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilNombrePeriodo());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Periodo1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilPeriodo());
			cell=row.getCell((short)Comportamiento.plantilla[Comportamiento.Metodologia1][Comportamiento.COL]);
			cell.setCellValue(filtro.getFilMetodologia());
		}
	}
	
	/**
	 * @function:  Metodo que coloca la nota equivalente segun el codigo
	 * @param escala
	 * @param datos
	 * @return
	 * @throws Exception
	 */
	private List colocarLiteral(List escala, List datos) throws Exception {
		try{
			/*System.out.println("colocarLiteral "  );
			 System.out.println("escala.size() " + escala.size());
			 System.out.println("datos " + datos.size()  );*/
			for(int i=0; i< escala.size();i++){
				
				ItemVO escalaVO  =(ItemVO)escala.get(i);
				for(int ii=0; ii< datos.size();ii++){
					
					EvaluacionEstudiante	estudiante=(EvaluacionEstudiante)datos.get(ii);
					
					long codNota =    (int)estudiante.getEvalNotaConCodigo();
					long codEscala = escalaVO.getCodigo();
					if ( codNota  ==   	codEscala) {
						estudiante.setEvalNotaCon(escalaVO.getNombre());
					}
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("" + e.getMessage() );
			throw new Exception(e.getMessage() );
		}
		return datos;
	}
}
