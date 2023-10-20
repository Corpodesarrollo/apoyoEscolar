/**
 * 
 */
package articulacion.plantillaEvaluacion.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import siges.login.beans.Login;
import articulacion.plantillaEvaluacion.vo.EstudianteVO;
import articulacion.plantillaEvaluacion.vo.datosPruebasVO;
import siges.perfil.vo.Url;
import articulacion.plantillaEvaluacion.vo.DatosPlanEvalVO;
import articulacion.plantillaEvaluacion.vo.plantilla.ExcelVO;
import siges.util.beans.Asignatura;

/**
 * 9/08/2007 
 * @author Latined
 * @version 1.2
 */
public class ExcelIO {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2,sheet3;
	private	HSSFRow row,row2,row3;
	
	private	HSSFCell cell,cell2,cell3;
	private HSSFCellStyle estilo;
	
	public void plantilla(DatosPlanEvalVO datosVO,List estudiantes, Url url,String docente,List pruebas) throws Exception{
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(url.getPathPlantilla()+url.getNombrePlantilla());//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){
				throw new Exception("No hay libro");
			}
			
			//hoja de escalas
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE ESCALAS
			sheet2 = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
			sheet3 = workbook.getSheetAt(2);//OBTENER HOJA DE ESCALAS
			setEscala(estudiantes);
			EncabezadoEstudiantes(datosVO,docente,pruebas);
			setParametros(pruebas);
			
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
	    	System.out.println("Error Plantilla prueba: "+e.toString());
			throw new Exception(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
	    	System.out.println("Error Plantilla prueba: "+e.toString());
			throw new Exception(e.getMessage());
		}finally{
	    	try{
	    		if(input!=null)input.close();
	    		if(fileOut!=null)fileOut.close();
	    	}catch(IOException e){}
			
		}
	}
	
	private void setEscala(List estudiantes) throws Exception{
		if(estudiantes!=null && estudiantes.size()>0){
			EstudianteVO estudianteVO=null;
			int autonum=1;
			int numFilaE=1;
			boolean cerrado=false;
			int numColumna=0;
			int numFila=ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.FIL];
			for(int i=0;i<estudiantes.size();i++){
				estudianteVO=(EstudianteVO)estudiantes.get(i);
				row=sheet.getRow(numFila++);
				//codigo
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.COL]);
	 			cell.setCellValue(autonum++);	
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
	 			cell.setCellValue(estudianteVO.getApellido()+" "+ estudianteVO.getApellido1());
	 			cell=row.createCell((short)ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
	 			cell.setCellValue(estudianteVO.getNombre() +" "+ estudianteVO.getNombre1());
	 			numColumna=ExcelVO.plantilla[ExcelVO.nota][ExcelVO.COL];

	 			cell=row.createCell((short)numColumna++);
	 			cell.setCellValue(estudianteVO.getNotaNum1()==0?"":String.valueOf(estudianteVO.getNotaNum1()));
	 			cell=row.createCell((short)numColumna++);
	 			cell.setCellValue(estudianteVO.getNotaNum2()==0?"":String.valueOf(estudianteVO.getNotaNum2()));
	 			cell=row.createCell((short)numColumna++);
	 			cell.setCellValue(estudianteVO.getNotaNum3()==0?"":String.valueOf(estudianteVO.getNotaNum3()));
	 			cell=row.createCell((short)numColumna++);
	 			cell.setCellValue(estudianteVO.getNotaNum4()==0?"":String.valueOf(estudianteVO.getNotaNum4()));
	 			cell=row.createCell((short)numColumna++);
	 			cell.setCellValue(estudianteVO.getNotaNum5()==0?"":String.valueOf(estudianteVO.getNotaNum5()));
	 			
	 			if(estudianteVO.isEstado()){
	 				cerrado=true;
	 			}
			}
 			if(cerrado){
 				numFilaE=ExcelVO.plantilla[ExcelVO.estado][ExcelVO.FIL];
 				row=sheet.getRow(numFilaE);
 				cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.estado][ExcelVO.COL]);
 				cell.setCellValue("LA EVALUACInN SE ENCUENTRA CERRADA");
 			}
			
			int numFila2=ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.FIL];
			 	for(int y=0;y<estudiantes.size();y++){
						estudianteVO=(EstudianteVO)estudiantes.get(y);
						row3=sheet3.createRow(numFila2++);
			 			
						cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.autonumerico][ExcelVO.COL]);
			 			cell3.setCellValue(estudianteVO.getCodigo());	
			 			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.apellidos][ExcelVO.COL]);
			 			cell3.setCellValue(estudianteVO.getApellido()+" "+ estudianteVO.getApellido1());
			 			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.nombres][ExcelVO.COL]);
			 			cell3.setCellValue(estudianteVO.getNombre() +" "+ estudianteVO.getNombre1());
							
					}
		}
	}
	
	private void EncabezadoEstudiantes(DatosPlanEvalVO datosVO,String docente,List pruebas) throws Exception{
		int numFila=1;
		if(datosVO!=null){
			//ano de vigencia Oculto
			numFila=ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.anhoVigencia][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getAnVigencia());
			//periodo de vigencia Oculto
			numFila=ExcelVO.plantilla[ExcelVO.periodoVigencia][ExcelVO.FIL];
			row=sheet3.getRow(numFila);
			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.periodoVigencia][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getPerVigencia());
			//Metodologia Oculta
			numFila=ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.metodologia][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getMetodologia());
			//BImestre Oculta
			numFila=ExcelVO.plantilla[ExcelVO.bimestre][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.bimestre][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getBimestre());
			
			//ano de vigencia Visible
			numFila=ExcelVO.plantilla[ExcelVO.anhoVigencia2][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.anhoVigencia2][ExcelVO.COL]);
			cell.setCellValue(datosVO.getAnVigencia());
			//periodo de vigencia Visible
			numFila=ExcelVO.plantilla[ExcelVO.periodoVigencia2][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.periodoVigencia2][ExcelVO.COL]);
			cell.setCellValue(datosVO.getPerVigencia());
			//bimestre Visible
			numFila=ExcelVO.plantilla[ExcelVO.bimestre2][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.bimestre2][ExcelVO.COL]);
			cell.setCellValue(datosVO.getBimestre());
			//institucion
			numFila=ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			cell.setCellValue(datosVO.getInstitucion_());
			
			//hoja oculta institucion 2 
			numFila=ExcelVO.plantilla[ExcelVO.ins][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.ins][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getInstitucion());
			
			numFila=ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.institucion][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getInstitucion_());
			
           //sede
			numFila=ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			cell.setCellValue(datosVO.getSede_());
			
           //hoja oculta sede 2
			numFila=ExcelVO.plantilla[ExcelVO.sed][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.sed][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getSede());
			
			numFila=ExcelVO.plantilla[ExcelVO.sede][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.sede][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getSede_());
			
		    //jornada
			numFila=ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			cell.setCellValue(datosVO.getJornada_());
			
			 //hoja oculta jornada
			numFila=ExcelVO.plantilla[ExcelVO.jor][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.jor][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getJornada());
			
			numFila=ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.jornada][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getJornada_());
			
		    //asignatura
			numFila=ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL]);
			cell.setCellValue(datosVO.getAsignatura_());
			
			 //hoja oculta asignatura
			numFila=ExcelVO.plantilla[ExcelVO.asig][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.asig][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getAsignatura());
			
			numFila=ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.asignatura][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getAsignatura_());
			
            //fecha
			numFila=ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.fecha][ExcelVO.COL]);
			cell.setCellValue(datosVO.getFecha_());
			
			//Grupo
			numFila=ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL]);
			cell.setCellValue(datosVO.getGrupo_());
			
			 //hoja oculta grupo
			numFila=ExcelVO.plantilla[ExcelVO.grup][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.grup][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getGrupo());
			
			numFila=ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.grupo][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getGrupo_());
			
//			Prueba
			numFila=ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.COL]);
			cell.setCellValue(datosVO.getPrueba_());
			
			 //hoja oculta prueba
			numFila=ExcelVO.plantilla[ExcelVO.pru][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.pru][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getPrueba());
		
			numFila=ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.prueba][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getPrueba_());
	
//			docentes
			numFila=ExcelVO.plantilla[ExcelVO.docente][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.docente][ExcelVO.COL]);
			cell.setCellValue(docente);
			
			numFila=ExcelVO.plantilla[ExcelVO.docente][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.docente][ExcelVO.COL]);
			cell3.setCellValue(docente);

//			Porcentaje PurebaGrande
			numFila=ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.COL]);
			cell.setCellValue(datosVO.getPorcentaje0()+"%");
			
			numFila=ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.porcentaje][ExcelVO.COL]);
			cell3.setCellValue(datosVO.getPorcentaje0());
			
			//tipo es la llave para poder ver si puedo luego importar el archivo
			numFila=ExcelVO.plantilla[ExcelVO.llave][ExcelVO.FIL];
			row3=sheet3.getRow(numFila);
			cell3=row3.getCell((short)ExcelVO.plantilla[ExcelVO.llave][ExcelVO.COL]);
			cell3.setCellValue(11);
			
			
//SUBPRUEBAS
			if(pruebas!=null && pruebas.size()>0 && pruebas.size()<5){
			  // datosPruebasVO datosPruebasVO=null;
			   
				/*for(int i=0;i<pruebas.size();i++){
					datosPruebasVO=(datosPruebasVO)pruebas.get(i);
					*/
					datosPruebasVO datosPruebasVO=null;
	                int numFilaPrueba=ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.FIL ];
	                int numFilaPorcentaje=ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.FIL];
	                int numColumnaPrueba=ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.COL];
	                int numColumnaPorcentaje= ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.COL];
	                for(int i=0;i<pruebas.size();i++)
	                {
	                    datosPruebasVO=(datosPruebasVO)pruebas.get(i);
	                    //poner la abrev
	                    
	                    row=sheet.getRow(numFilaPrueba);
	                    cell=row.getCell((short)numColumnaPrueba++);
	                    cell.setCellValue(datosPruebasVO.getAbreviatura());
	                    //poner el porcentaje
	                    row=sheet.getRow(numFilaPorcentaje);
	                    cell=row.getCell((short)numColumnaPorcentaje++);
	                    cell.setCellValue(datosPruebasVO.getPorcentaje()+"%");
	                  
	                }  
	               
	            	datosPruebasVO datosPruebasVO1=null;
	            	int numFilaP=ExcelVO.plantilla[ExcelVO.porcentajesOculta][ExcelVO.FIL];
	                for(int j=0;j<pruebas.size();j++){
	                	datosPruebasVO1=(datosPruebasVO)pruebas.get(j);
	    				row3=sheet3.createRow(numFilaP++);
	    				
			 			cell3=row3.createCell((short)ExcelVO.plantilla[ExcelVO.porcentajesOculta][ExcelVO.COL]);
			 			cell3.setCellValue(datosPruebasVO1.getPorcentaje());
	                }
	                
	                int numFilaPrueba1=ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.FIL ];
	                int numFilaPorcentaje1=ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.FIL];
	                int numColumnaPrueba1=ExcelVO.plantilla[ExcelVO.subPruebas][ExcelVO.COL];
	                int numColumnaPorcentaje1= ExcelVO.plantilla[ExcelVO.porcentajeSubpruebas][ExcelVO.COL];
	                for(int j=0;j<pruebas.size();j++)
	                {
	                    datosPruebasVO=(datosPruebasVO)pruebas.get(j);
	                    //poner la abrev
	                    row3=sheet3.createRow(numFilaPrueba1);
	                    cell3=row3.createCell((short)numColumnaPrueba1++);
	                    cell3.setCellValue(datosPruebasVO.getAbreviatura());
	                    //poner el porcentaje
	                    row3=sheet3.createRow(numFilaPorcentaje1);
	                    cell3=row3.createCell((short)numColumnaPorcentaje1++);
	                    cell3.setCellValue(datosPruebasVO.getPorcentaje()+"%");
	                } 
				}	
			}
	}
	
	
	private void setParametros(List pruebas) throws Exception{
		if(pruebas!=null && pruebas.size()>0){
			datosPruebasVO datosPruebasVO=null;
			int numFila=ExcelVO.plantilla[ExcelVO.parametrosAbrev][ExcelVO.FIL];
			for(int i=0;i<pruebas.size();i++){
				datosPruebasVO=(datosPruebasVO)pruebas.get(i);
				row2=sheet2.createRow(numFila++);
				
	 			cell2=row2.createCell((short)ExcelVO.plantilla[ExcelVO.parametrosAbrev][ExcelVO.COL]);
	 			cell2.setCellValue(datosPruebasVO.getAbreviatura());	
	 			cell2=row2.createCell((short)ExcelVO.plantilla[ExcelVO.parametrosNomb][ExcelVO.COL]);
	 			cell2.setCellValue(datosPruebasVO.getNombrePrueba());
	 			
			}
		}
	}
	public String formatear(String a)
	{
		   return (a.replace(' ','_').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','n').replace('n','N').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','c').replace(':','_').replace('.','_').replace('/','_').replace('\\','_'));
	}
}
