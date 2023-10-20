package siges.admincursos.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import siges.admincursos.beans.*;

public class Excel {
    private HSSFWorkbook workbook;
   	private HSSFSheet sheet,sheet2;
   	private	HSSFRow row,row2;
   	private	HSSFCell cell,cell2;
   	private Iterator iterator;
   	private Object[] o=null;
   	private String mensaje;

   	public Excel(){
   	}
   	
   	public boolean plantilla1(String[] config,Collection c,AdminCursos ac){
   	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
   	    int numFila=2;
   	  		int numCelda=-2;
   	  		HSSFSheet sheet2;
   	  		FileInputStream input=null;
   	  		FileOutputStream fileOut=null;
   	  		try{
   	  		    input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
   	  		    workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
   	  		    if(workbook==null){ return false; }
   	  		    //hoja de escalas
   	  		    sheet = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
   	  		    setEncabezado(c);
   	  		    setReporte(c,ac);
   	  		    File f=new File(config[2]);
   	  		    if(!f.exists()) FileUtils.forceMkdir(f);
   	  		    fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
   	  		    workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
   	  		    fileOut.flush();//CERRAR
   	  		    fileOut.close();
   	  		    input.close();
   	  		}catch(IOException e){
   	  		    e.printStackTrace();
   	  		    System.out.println("Error Rep1: "+e.toString());
   	  		    return false;
   	  		}finally{
   	  		    try{
   	  		        if(input!=null)input.close();
   	  		        if(fileOut!=null)fileOut.close();
   	  		    }catch(IOException e){}
   	  		}
   	  		return true;
   	}
   	
   	private void setEncabezado(Collection c){
   	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null){
   	        //usuario
   	        /*numFila=Reporte.Rep[Reporte.Usuario][Reporte.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte.Rep[Reporte.Usuario][Reporte.COL]);
   	        System.out.println("fecha: "+d.toString());
   	        cell.setCellValue(d.toString());*/
   	        //fecha
   	        numFila=Reporte.Rep[Reporte.Fecha][Reporte.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte.Rep[Reporte.Fecha][Reporte.COL]);
   	        cell.setCellValue(d.toString());
   	    }		
   		}
   	
   	private void setReporte(Collection c,AdminCursos ac){
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null && !c.isEmpty()){
   	        iterator=c.iterator();
   	        numFila=Reporte.Rep[Reporte.PRIMERAPELLIDO][Reporte.FIL];
   	        int []numColumna={
   	                Reporte.Rep[Reporte.PRIMERAPELLIDO][Reporte.COL],
   	                Reporte.Rep[Reporte.SEGUNDOAPELLIDO][Reporte.COL],
   	                Reporte.Rep[Reporte.PRIMERNOMBRE][Reporte.COL],
   	        								Reporte.Rep[Reporte.SEGUNDONOMBRE][Reporte.COL],
   	        								Reporte.Rep[Reporte.TIPODOCUM][Reporte.COL],
   	        								Reporte.Rep[Reporte.NUMDOCUM][Reporte.COL],
   	        								Reporte.Rep[Reporte.EDAD][Reporte.COL],
   	        								Reporte.Rep[Reporte.DIRECCION][Reporte.COL],
   	        								Reporte.Rep[Reporte.TELEFONO][Reporte.COL],
   	        								Reporte.Rep[Reporte.CORREOPER][Reporte.COL],
   	        								Reporte.Rep[Reporte.CORREOINS][Reporte.COL],
   	        								Reporte.Rep[Reporte.LOCALIDAD][Reporte.COL],
   	        								Reporte.Rep[Reporte.CODIGODANE][Reporte.COL],
   	        								Reporte.Rep[Reporte.NOMBRECOLEGIO][Reporte.COL],
   	        								Reporte.Rep[Reporte.NOMBRESEDE][Reporte.COL],
   	        								Reporte.Rep[Reporte.NOMBREJORNADA][Reporte.COL],
   	        								Reporte.Rep[Reporte.TIEMPOSERVICIO][Reporte.COL],
   	        								Reporte.Rep[Reporte.ESCALAFON][Reporte.COL],
   	        								Reporte.Rep[Reporte.G0][Reporte.COL],
   	        								Reporte.Rep[Reporte.G1][Reporte.COL],
   	        								Reporte.Rep[Reporte.G2][Reporte.COL],
   	        								Reporte.Rep[Reporte.G3][Reporte.COL],
   	        								Reporte.Rep[Reporte.G4][Reporte.COL],
   	        								Reporte.Rep[Reporte.G5][Reporte.COL],
   	        								Reporte.Rep[Reporte.G6][Reporte.COL],
   	        								Reporte.Rep[Reporte.G7][Reporte.COL],
   	        								Reporte.Rep[Reporte.G8][Reporte.COL],
   	        								Reporte.Rep[Reporte.G9][Reporte.COL],
   	        								Reporte.Rep[Reporte.G10][Reporte.COL],
   	        								Reporte.Rep[Reporte.G11][Reporte.COL],
   	        								Reporte.Rep[Reporte.NORMALISTA][Reporte.COL],
   	        								Reporte.Rep[Reporte.BACHILLER][Reporte.COL],
   	        								Reporte.Rep[Reporte.TECNICO][Reporte.COL],
   	        								Reporte.Rep[Reporte.TITTECNICO][Reporte.COL],
   	        								Reporte.Rep[Reporte.LICENCIATURA][Reporte.COL],
   	        								Reporte.Rep[Reporte.TITLICENCIATURA][Reporte.COL],
   	        								Reporte.Rep[Reporte.OTRALICENCIATURA][Reporte.COL],
   	        								Reporte.Rep[Reporte.TITOTRALICENCIATURA][Reporte.COL],
   	        								Reporte.Rep[Reporte.MAESTRIA][Reporte.COL],
   	        								Reporte.Rep[Reporte.ESPECIALIZACION][Reporte.COL],
   	        								Reporte.Rep[Reporte.DOCTORADO][Reporte.COL],
   	        								Reporte.Rep[Reporte.NIVEL][Reporte.COL],
   	        								Reporte.Rep[Reporte.AREADESEMP][Reporte.COL],
   	        								Reporte.Rep[Reporte.PROYECTO1][Reporte.COL],
   	        								Reporte.Rep[Reporte.PROYECTO2][Reporte.COL],
   	        								Reporte.Rep[Reporte.PROYECTO3][Reporte.COL],
   	        								Reporte.Rep[Reporte.FECHAINSCRIPCION][Reporte.COL],
   	        								Reporte.Rep[Reporte.NOMBRECURSO][Reporte.COL],
   	        								Reporte.Rep[Reporte.JORNADACURSO][Reporte.COL]};
   	        while(iterator.hasNext()){
   	            row=sheet.createRow(numFila++);
   	            o=(Object[])iterator.next();
   	            for(int j=0;j<o.length;j++){
   	                cell=row.createCell((short)numColumna[j]);
   	                cell.setCellValue((String)o[j]);    
   	            }
   	        }
   	    	}
   	}
   	
   	/**
   	*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
   	*	@param	String s
   	**/
   		public void setMensaje(String s){
   			mensaje+=s;
   		}
   	
}