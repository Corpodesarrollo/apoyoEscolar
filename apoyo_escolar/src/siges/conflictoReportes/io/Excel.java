package siges.conflictoReportes.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import siges.login.beans.Login;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import siges.conflictoReportes.beans.*;

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
   	
   	public boolean plantilla1(String[] config,Collection c,ConflictoFiltro cf, Login login){
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
   	  		    setEncabezado1(c,cf,login);
   	  		    setReporte1(c,cf);
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
   	
   	public boolean plantilla2(String[] config,Collection c,ConflictoFiltro cf, Login login){
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
   	  		    setEncabezado2(c,cf,login);
   	  		    setReporte2(c,cf);
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
   	
   	private void setEncabezado2(Collection c,ConflictoFiltro fr, Login l){
   	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null){
   	        //institucion
   	        numFila=Reporte2.Rep2[Reporte2.Institucion][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Institucion][Reporte2.COL]);
   	        cell.setCellValue(l.getInst());
   	        //sede
   	        numFila=Reporte2.Rep2[Reporte2.Sede][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Sede][Reporte2.COL]);
   	        cell.setCellValue(fr.getNomsede2());
   	        //jornada
   	        numFila=Reporte2.Rep2[Reporte2.Jornada][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Jornada][Reporte2.COL]);
   	        cell.setCellValue(fr.getNomjorn2());
   	        //PERIODO
   	        numFila=Reporte2.Rep2[Reporte2.Periodo][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Periodo][Reporte2.COL]);
   	        cell.setCellValue(fr.getNomperiodo2());
   	        //fecha
   	        numFila=Reporte2.Rep2[Reporte2.Fecha][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Fecha][Reporte2.COL]);
   	        cell.setCellValue(d.toString());
   	        //usuario
   	        numFila=Reporte2.Rep2[Reporte2.Usuario][Reporte2.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte2.Rep2[Reporte2.Usuario][Reporte2.COL]);
   	        cell.setCellValue(l.getUsuario());
   	    }		
   		}
   	
   	private void setEncabezado1(Collection c,ConflictoFiltro fr, Login l){
   	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null){
   	        //LOCALIDAD
   	        numFila=Reporte1.Rep1[Reporte1.Localidad][Reporte1.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte1.Rep1[Reporte1.Localidad][Reporte1.COL]);
   	        cell.setCellValue(fr.getNomlocal1());
   	        //PERIODO
   	        numFila=Reporte1.Rep1[Reporte1.Periodo][Reporte1.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte1.Rep1[Reporte1.Periodo][Reporte1.COL]);
   	        cell.setCellValue(fr.getNomperiodo1());
   	        //usuario
   	        numFila=Reporte1.Rep1[Reporte1.Usuario][Reporte1.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte1.Rep1[Reporte1.Usuario][Reporte1.COL]);
   	        cell.setCellValue(l.getUsuario());
   	        //fecha
   	        numFila=Reporte1.Rep1[Reporte1.Fecha][Reporte1.FIL];
   	        row=sheet.getRow(numFila);
   	        cell=row.getCell((short)Reporte1.Rep1[Reporte1.Fecha][Reporte1.COL]);
   	        cell.setCellValue(d.toString());
   	    }		
   		}
   	
   	private void setReporte2(Collection c,ConflictoFiltro fr){
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null && !c.isEmpty()){
   	        iterator=c.iterator();
   	        numFila=Reporte2.Rep2[Reporte2.Ininumdocum][Reporte2.FIL];
   	        int []numColumna={
   	                Reporte2.Rep2[Reporte2.Ininumdocum][Reporte2.COL],
   	                Reporte2.Rep2[Reporte2.Iniapellido][Reporte2.COL],
   	                Reporte2.Rep2[Reporte2.Ininombre][Reporte2.COL],
   	                Reporte2.Rep2[Reporte2.Inigrado][Reporte2.COL],
   	                Reporte2.Rep2[Reporte2.Inigrupo][Reporte2.COL]};
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
   	
   	private void setReporte1(Collection c,ConflictoFiltro fr){
   	    int numFila=1;
   	    int contador=1;
   	    if(c!=null && !c.isEmpty()){
   	        iterator=c.iterator();
   	        numFila=Reporte1.Rep1[Reporte1.Inicolegio][Reporte1.FIL];
   	        int []numColumna={
   	                Reporte1.Rep1[Reporte1.Inicolegio][Reporte1.COL],
   	                Reporte1.Rep1[Reporte1.Inisede][Reporte1.COL],
   	                Reporte1.Rep1[Reporte1.Inijornada][Reporte1.COL]};
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