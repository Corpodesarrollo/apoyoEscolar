package siges.io;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Iterator;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;

import org.apache.commons.validator.*;
import org.apache.commons.io.FileUtils;
import siges.util.Recursos;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;


/**
*	Nombre:	Excel<BR>
*	Descripcinn:	Servicio que accede a los archivos de excel para leerlos 	o para generar plantillas<BR>
*	Funciones de la pngina:	Validar los datos de los archivos excel y generar archivos excel con las plantillas del sistema	<BR>
*	Entidades afectadas:		<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/
public class Excel{
	private String pathBajar;
	private String pathTemporal;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2;
	private	HSSFRow row,row2;
	private	HSSFCell cell,cell2;
	private HSSFWorkbook workbookNuevo;
	private HSSFSheet sheetNuevo;
	private	HSSFRow rowNuevo;
	private	HSSFCell cellNuevo;
	private String mensaje;//mensaje en caso de error
	private ResourceBundle rb,rb2;
	private Iterator iterator,iterator2,iterator3;
	private Object[] o=null;
	private Object[] o2=null;
	private boolean error;
	private Collection list;
	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	private Integer entero=new Integer(java.sql.Types.INTEGER);
	private Integer fecha=new Integer(java.sql.Types.DATE);
	private Integer nulo=new Integer(java.sql.Types.NULL);
	private Integer doble=new Integer(java.sql.Types.DOUBLE);
	private Integer caracter=new Integer(java.sql.Types.CHAR);
	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
	private String enca[][];
	private String estudiantes[];
	private String escala[][];
	private String nota[][];
	private String log[];
	private String motivo[][];
	private String logro[][];
	private String descFor[][];
	private String descDif[][];
	private String descRec[][];
	private String descEst[][];	
	private String nivel;
	private Util util;
	private GenericValidator validator;
	
	/**
	 * Constructor
	 */
	public Excel(){
		rb=ResourceBundle.getBundle("path");
		rb2=ResourceBundle.getBundle("preparedstatements");
		mensaje="";
		error=true;
		o=new Object[2];
		validator=new GenericValidator();
	}
	
	/**
	 * Constructor
	 * @param u
	 */
	public Excel(Util u){
		this.util=u;
		rb=ResourceBundle.getBundle("path");
		rb2=ResourceBundle.getBundle("preparedstatements");
		mensaje="";
  	error=true;
		o=new Object[2];
		validator=new GenericValidator();
  }
	
	/**
	*	Funcinn: Retorna el valor de la valriable de error en datos de usuario <BR>
	*	@return	boolean 
	**/
	public boolean getError(){
		return error;
	}
	
	/**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public void setMensaje(String s){
		mensaje+=s;
	}

	/**
	*	Funcinn: retorna la lista de mensajes a <BR>
	*	@return	String 
	**/
	public String getMensaje(){
		return mensaje;
	}

	/**
	*	Funcinn: pone un archivo como zip en el destino indicado <BR>
	*	@param	String destino
	*	@param	int BUFFER_SIZE
	*	@param	Collection files
	*	@return boolean	
	**/
	public boolean ponerZip(String ruta,String archivo,int BUFFER_SIZE,Collection files){
	  String filename;
	  int count;
	  ZipOutputStream out=null;
	  FileOutputStream dest=null;
	  BufferedInputStream origin=null;
	  FileInputStream fi=null;
	  try{      
			File f=new File(ruta); if(!f.exists()) FileUtils.forceMkdir(f);
      dest = new FileOutputStream(ruta+archivo);// Reference to our zip file
      out = new ZipOutputStream( new BufferedOutputStream( dest ) );// Wrap our destination zipfile with a ZipOutputStream
      File f2; 
      byte[] data = new byte[ BUFFER_SIZE ];// Create a byte[] buffer that we will read data from the source files into and then transfer it to the zip file
      for(Iterator i=files.iterator(); i.hasNext(); ){// Iterate over all of the files in our list
        filename = (String)i.next();// Get a BufferedInputStream that we can use to read the source file
        fi = new FileInputStream( filename );
        origin = new BufferedInputStream( fi, BUFFER_SIZE );
        f2 = new File(filename);
        ZipEntry entry = new ZipEntry(f2.getName());// Setup the entry in the zip file
        out.putNextEntry(entry);
        while( ( count = origin.read(data, 0, BUFFER_SIZE ) ) != -1 ){
          out.write(data, 0, count);
        }
        origin.close();// Close the source file
        fi.close();
        f2=null;
      }
      out.close();
    }catch(Exception e){
      System.out.println("Error Haciendo ZIP: "+e);
      e.printStackTrace();
      setMensaje("Error Haciendo ZIP: "+e);
    	return false;
    }
    finally{
      try{
        if(out!=null);out.close();
        if(dest!=null);dest.close();
        if(origin!=null);origin.close();
        if(fi!=null);fi.close();
        File zz;
        for(Iterator i=files.iterator(); i.hasNext(); ){			
    	      zz=new File((String)i.next());
    				if(zz.exists()) FileUtils.forceDelete(zz);
          }
        }catch(Exception ex){System.out.println("error finally zip"+ex);}
    }
    return true;
	}
}

