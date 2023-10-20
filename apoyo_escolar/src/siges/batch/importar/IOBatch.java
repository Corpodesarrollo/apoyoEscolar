/*
 * Creado el 24/10/2005
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
package siges.batch.importar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import siges.util.Properties;
import siges.util.beans.Area;
import siges.util.beans.Asignatura;
import siges.util.beans.BateriaDescriptor;
import siges.util.beans.BateriaLogro;
import siges.util.beans.Preescolar;


/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class IOBatch {
  	private String mensaje;//mensaje en caso de error
  	private boolean error;
  	private HSSFWorkbook workbook;
  	private HSSFSheet sheet,sheet2;
  	private HSSFRow row,row2;
  	private HSSFCell cell,cell2;
  	private final int BUFFER_SIZE=8192;

	public IOBatch(){
	    mensaje="";
	    error=false;
	}

  public boolean validarZip(int tipo,String[] archivo,String[] params){
  		File f=null,f2=null;
  		FileOutputStream fos=null;
  		FileInputStream fis=null;
  		ZipInputStream zis=null,zis2=null;
  		Iterator iterator;
      Collection col=new ArrayList();
      Collection col2=new ArrayList();
      Object o[];
      try{
        int count;
        byte data[] = new byte[ BUFFER_SIZE ];
        ZipEntry entry;
        // Create a ZipInputStream to read the zip file
        BufferedOutputStream dest = null;
        f=new File(archivo[0]+archivo[1]);
        //iterar sobre el zip para ver si tiene carpetas o archivos de extenxion diferente a excels
        fis = new FileInputStream(f);
        zis2 = new ZipInputStream( new BufferedInputStream( fis ) );
        int nn=0;
        while((entry=zis2.getNextEntry())!= null){
          nn++;
          if(entry.isDirectory()){              
            setMensaje("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de plantilla directamente en la raiz del archivo comprimido y no deje carpetas.");
            return false;
          }
          String entryName = entry.getName();
          //System.out.println(""+entryName);
          if(entryName.indexOf("/")!=-1 || entryName.indexOf("\\")!=-1){
            setMensaje("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de plantilla directamente en la raiz del archivo comprimido y no deje carpetas.");
            return false;
          }
          if(!entryName.endsWith(".xls")){
            setMensaje("El archivo contiene archivos que no son plantillas de Excel, eliminelos e importe de nuevo");
            return false;
          }            
        }
        if(nn==0){
            setMensaje("El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar en 'Winzip'");
            return false;
        }
        zis2.close();fis.close();
        fis = new FileInputStream(f);
        zis = new ZipInputStream( new BufferedInputStream( fis ) );        
        while((entry=zis.getNextEntry())!= null){
        String entryName = entry.getName();        
        if(!entry.isDirectory() ){             
          String destFN = archivo[0] + File.separator + entry.getName();
          fos = new FileOutputStream( destFN );
          dest = new BufferedOutputStream( fos, BUFFER_SIZE );
          while( (count = zis.read( data, 0, BUFFER_SIZE ) ) != -1 ){
            dest.write( data, 0, count );
          }
          o=new Object[2];
          o[0]=archivo[0];o[1]=entry.getName();
          col.add(o);
          dest.flush();
          dest.close();
        }
       }
       fis.close();
       zis2.close();
       zis.close();
	   		iterator =col.iterator();
	   		boolean band=false;
	   		String archivoPaila=null;
	   		String archivoCambio=null;
	   		int cont=0;
	   		java.sql.Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
	  		while (iterator.hasNext()){
	  		  o=((Object[])iterator.next());
	  		  String[] val={(String)o[0],(String)o[1]};
	  		  if(!validarFormato(tipo,val,params)){
	  		     archivoPaila=val[1];
	  		     band=true;
	  		     break;
	  		  }else{
	  		    f2=new File(val[0]+val[1]);
	  		    archivoCambio=getNombre(tipo,cont++,fecha,val[1],params[0]);
	  		    o=new Object[2];o[0]=val[0];o[1]=archivoCambio;
	  		    col2.add(o);
	  		    f2.renameTo(new File(val[0]+archivoCambio));
	  		  }
	  		}
	  		if(band){
	  	   		iterator =col.iterator();
	  	  		while (iterator.hasNext()){	  		  
	  		  		o=((Object[])iterator.next());
	  		  		f2=new File((String)o[0]+(String)o[1]);
	  		  		if(f2.exists())FileUtils.forceDelete(f2);
	  	  		} 
	  	   		iterator =col2.iterator();
	  	  		while (iterator.hasNext()){	  		  
	  		  		o=((Object[])iterator.next());
	  		  		f2=new File((String)o[0]+(String)o[1]);
	  		  		if(f2.exists())FileUtils.forceDelete(f2);
	  	  		}
	  	  		String msg=getMensaje();
	  	  		borrarMensaje();
            setMensaje("El archivo '"+archivoPaila+"' \n dentro del zip devolvin este mensaje: '"+msg+"'.\n y no es posible evaluar todos los archivos Excel hasta que se corrija este problema.\n Corrijalo e intente importar el zip de nuevo.");
            return false;
	  		}
       }catch(Exception e ){
          e.printStackTrace();
          borrarMensaje();
	        setMensaje("Ocurrio un error mientras se evaluava los archivos para importacinn: '"+e);
          e.printStackTrace();
	        return false;       	
       }finally{
        	try{
        		if(fis!=null)fis.close();if(fos!=null)fos.close();
        		if(zis!=null)zis.close();if(zis2!=null)zis2.close();
        		FileUtils.forceDelete(f);
        	}catch(Exception e){}
       }
      return true;
  }

   public boolean validarFormato(int tipo,String[] archivo,String[] params){
        FileInputStream input=null;
    		File f=null;
    		error=true;
    		boolean fallo=false;
    		try{
    		  f= new File(archivo[0]+archivo[1]);
    			input=new FileInputStream(f);
    			workbook= new HSSFWorkbook(input);
    			//validar tipo
    			int hoja=2;
    			switch(tipo){
  				case Properties.PLANTILLABATLOGRO:
  					hoja=2;
  				break;
  				case Properties.PLANTILLABATDESCRIPTOR:
  					hoja=5;
  				break;
  				case Properties.PLANTILLALOGROASIG: 
  					hoja=2;
  				break;
  				case Properties.PLANTILLAAREADESC:
  					hoja=6;
  				break;
  				case Properties.PLANTILLAPREE: 
  					hoja=5;
  				break;
    			}
  				sheet2 = workbook.getSheetAt(hoja);
    			if(!validarTipoPlantilla(tipo)){
    				setMensaje("No es una plantilla adecuada para el tipo de información que se prentende importar");
    				input.close();
    				FileUtils.forceDelete(f);
    				return false;
    			}
    			switch(tipo){
  				case Properties.PLANTILLABATLOGRO:
  					hoja=1;
  				break;
  				case Properties.PLANTILLABATDESCRIPTOR:
  					hoja=1;
  				break;
  				case Properties.PLANTILLALOGROASIG: 
  					hoja=1;
  				break;
  				case Properties.PLANTILLAAREADESC:
  					hoja=5;
  				break;
  				case Properties.PLANTILLAPREE: 
  					hoja=4;
  				break;
    			}
  				sheet = workbook.getSheetAt(hoja);
    			boolean band=true;
			    band=validarEncabezado(tipo, params);
    			if(!band){
     				setMensaje("El encabezado de la(s) plantillas no corresponde a los datos del usuario que esta importando");
    				input.close();
     				FileUtils.forceDelete(f);
     				return false;
    			}
        }catch(NullPointerException e){
          e.printStackTrace();
          fallo=true;
        	setMensaje("No es un archivo de plantilla valido, Faltan filas importantes");
        	System.out.println("Error validar:  "+e.toString());
        	return false;
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
          fallo=true;
        	setMensaje("No es un archivo de plantilla valido, Faltan hojas en el libro");
        	System.out.println("Error validar:  "+e.toString());
        	return false;
        }
        catch(Exception e){
            e.printStackTrace();
          fallo=true;
        	setMensaje(e.toString());
        	System.out.println("Error validar:  "+e.toString());
        	return false;
        }finally{
        	try{ if(input!=null) input.close(); if(fallo) FileUtils.forceDelete(f);  }catch(IOException e){}
        }
    		return true;
    	}


  	/**
  	*	Funcinn: Valida el encabezado de la bateria de logros <BR>
  	*	@return boolean 	
  	**/
  	public boolean validarEncabezadoBateria(String params[]){
  		  String inst=params[0];
  			//institucion
  			row=sheet2.getRow(1);
  			cell=row.getCell((short)0);
  			if(!cell.getStringCellValue().equals(inst))
  				return false;
  		return true;	
  	}

  	/**
  	*	Funcinn: Valida el encabezado de la evaluacion de logros <BR>
  	*	@return boolean 	
  	**/
  	public boolean validarEncabezado(int tipo,String params[]){
  		  String inst=params[0];
  		  String sede=params[1];
  		  String jor=params[2];
  			int fila=0;
  			int col=0;
  			switch(tipo){
				case Properties.PLANTILLALOGROASIG:
					//institucion con respecto a login
					fila=Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
					col=Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL];
					row2=sheet2.getRow(fila);
					cell2=row2.getCell((short)col);
					if(!cell2.getStringCellValue().equals(inst)){
					  error=false;
						return false;
					}			
					//sede con respecto a login
					if(!sede.equals("")){
						fila=Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
						col=Asignatura.Asig[Asignatura.Sede1][Asignatura.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(sede)){
						  error=false;
							return false;
						}			
					}
					//jornada con respecto a login
					if(!jor.equals("")){
						fila=Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
						col=Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(jor)){
						  error=false;
							return false;
						}			
					}
				break;	
				case Properties.PLANTILLAAREADESC:
					//institucion con respecto a login
					fila=Area.Area[Area.Institucion1][Area.FIL];
					col=Area.Area[Area.Institucion1][Area.COL];
					row2=sheet2.getRow(fila);
					cell2=row2.getCell((short)col);
					if(!cell2.getStringCellValue().equals(inst)){
					  error=false;
						return false;
					}			
					//sede con respecto a login
					if(!sede.equals("")){
						fila=Area.Area[Area.Sede1][Area.FIL];
						col=Area.Area[Area.Sede1][Area.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(sede)){
						  error=false;
							return false;
						}			
					}
					//jornada con respecto a login
					if(!jor.equals("")){
						fila=Area.Area[Area.Jornada1][Area.FIL];
						col=Area.Area[Area.Jornada1][Area.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(jor)){
						  error=false;
							return false;
						}			
					}
				break;	
				case Properties.PLANTILLAPREE:
					//institucion con respecto a login
					fila=Preescolar.Pree[Preescolar.Institucion1][Preescolar.FIL];
					col=Preescolar.Pree[Preescolar.Institucion1][Preescolar.COL];
					row2=sheet2.getRow(fila);
					cell2=row2.getCell((short)col);
					if(!cell2.getStringCellValue().equals(inst)){
					  error=false;
						return false;
					}			
					//sede con respecto a login
					if(!sede.equals("")){
						fila=Preescolar.Pree[Preescolar.Sede1][Preescolar.FIL];
						col=Preescolar.Pree[Preescolar.Sede1][Preescolar.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(sede)){
						  error=false;
							return false;
						}			
					}
					//jornada con respecto a login
					if(!jor.equals("")){
						fila=Preescolar.Pree[Preescolar.Jornada1][Preescolar.FIL];
						col=Preescolar.Pree[Preescolar.Jornada1][Preescolar.COL];
						row2=sheet2.getRow(fila);
						cell2=row2.getCell((short)col);
						if(!cell2.getStringCellValue().equals(jor)){
						  error=false;
							return false;
						}			
					}
				break;	
				case Properties.PLANTILLABATLOGRO:
					//Institucion Codigo contra los valores del usuario		
					fila=BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.FIL];
					col=BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.COL];
					row2=sheet2.getRow(fila);
					cell2=row2.getCell((short)col);
					if(!cell2.getStringCellValue().equals(inst)){
					  error=false;
						return false;
					}
				break;	
				case Properties.PLANTILLABATDESCRIPTOR:
					//Institucion Codigo contra los valores del usuario		
					fila=BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.FIL];
					col=BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.COL];
					row2=sheet2.getRow(fila);
					cell2=row2.getCell((short)col);
					if(!cell2.getStringCellValue().equals(inst)){
					  error=false;
						return false;
					}
				break;	
			}
  		return true;
  	}
  	
  	/**
  	*	Funcinn: vALIDA que la plantilla que se desee subir pertenesca al tipo de archivo a subir <BR>
  	*	@param	
  	*	@return	
  	**/
  	public boolean validarTipoPlantilla(int n){
	try{
  			int fila=0;
  			int col=0;
  			long val;
  			switch(n){
  				case Properties.PLANTILLALOGROASIG:
  					fila=Asignatura.Asig[Asignatura.Tipo][Asignatura.FIL];
  					col=Asignatura.Asig[Asignatura.Tipo][Asignatura.COL];					
  				break;	
  				case Properties.PLANTILLAAREADESC:
  					fila=Area.Area[Area.Tipo][Area.FIL];
  					col=Area.Area[Area.Tipo][Area.COL];					
  				break;	
  				case Properties.PLANTILLAPREE:
  					fila=Preescolar.Pree[Preescolar.Tipo][Preescolar.FIL];
  					col=Preescolar.Pree[Preescolar.Tipo][Preescolar.COL];					
  				break;	
  				case Properties.PLANTILLABATLOGRO:
  					fila=BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.FIL];
  					col=BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.COL];
  				break;	
  				case Properties.PLANTILLABATDESCRIPTOR:
  					fila=BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.FIL];
  					col=BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.COL];					
  				break;	
  			}
  			row2=sheet2.getRow(fila);
  			cell2=row2.getCell((short)col);
  			val=Long.parseLong(cell2.getStringCellValue());
  			if(val!=n) return false;
  		}catch(Exception e){return false;}
  		return true;
  	}
  	
    /**
    *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
    *	@param	String s
    **/
    	public void setMensaje(String s){
    		mensaje+=s;
    	}

    	public void borrarMensaje(){
      		mensaje="";
      	}
    	/**
    	*	Funcinn: retorna la lista de mensajes a <BR>
    	*	@return	String 
    	**/
    	public String getMensaje(){
    		return mensaje;
    	}
    	
    	public String getNombre(int tipo,int contador,Timestamp f2,String nombre,String inst){
    		String nom="";
    		String extencion=".xls";    			
    		nom=tipo+"_"+inst+"_"+f2.toString().replace(':','-').replace('.','-')+"("+contador+")"+extencion;
    		return nom;
    	}
}
/*
 * Tipos para las plantillas
 * 1=logro
 * 2=Asignatura
 * 3=Area
 * 4=Descriptor
 * 
 * 5=Barteria logro
 * 7=Barteria descriptor
 * 8=Preescolar
 */
