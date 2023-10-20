package siges.batch.importar;  

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import siges.dao.Cursor;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.util.Properties;

/**
 *	Nombre:	ControllerImportarSave<BR>
 *	Descripcinn:	Maneja la negociacion para importar los datos de las plantillas excel de importacion de logros, descriptores y de evaluacion de logros, descriptores,areas,asignaturas y promocion	<BR>
 *	Funciones de la pngina:	Subir los	archivos al servidor y leerlos para ingresar o actualizar los datos de importacion y de evaluaacion y redirigir el control a la vista de resultados de importacinn<BR>
 *	Entidades afectadas:	Todas las plantillas de evaluacion, de promocion	y de logros y descriptores<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */
public class ControllerImportarSave extends HttpServlet{	
	private static final long serialVersionUID = 1L;
	private Cursor cursor;//objeto que maneja las sentencias sql
	private Util util;
	private String mensaje;//mensaje en caso de error
	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	private String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
	private String nombre[];
	private ResourceBundle rb;
	private java.sql.Timestamp f2;
	private final String[] tipos={"application/vnd.ms-excel","application/zip","application/x-zip-compressed","application/x-msexcel", "application/x-msword", "application/pdf"};
	private final String[] ext={".xls",".zip",".zip",".xls", ".doc", "pdf"};
	private final int EXCEL=0;
	private final int ZIP=1;
	private final int PDF=2;
	private final int DOC=3;
	
	/**
	 *	Recibe la peticion por el metodo Post de HTTP
	 *	@param	HttpServletRequest request
	 *	@param	HttpServletResponse response
	 **/
	public String process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session= request.getSession();
		int tipo;
		mensaje="";
		Login login;
		f2=new java.sql.Timestamp(new java.util.Date().getTime());
		nombre=new String[2];
		ant="/batch/importar/ControllerImportarEdit.do";
		mensaje=null;
		cursor=new Cursor();
		try{
			util=new Util(cursor);
			login=(Login)session.getAttribute("login");
			rb=ResourceBundle.getBundle("batch");
			int[] aa=subirArchivo(request,login);
			tipo=aa[0];
			if(tipo<0){
				request.setAttribute("mensaje",getMensaje());
				return (ant+="?tipo="+(tipo*-1));
			}
			int formatoArchivo=aa[2];
			rb=ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
			if(!validarArchivo(request,tipo,login,formatoArchivo)){
				request.setAttribute("mensaje",getMensaje());
				return (ant+="?tipo="+tipo);
			}
			setMensaje("El archivo fue recibido y en este momento esta en espera de ser procesado. \n " +
					       "Podrn descargar manana un reporte de las plantillas que tuvieron problemas al ser importadas. \n" +
					       "El reporte estarn en el vinculo 'Reportes Generados' en el menu lateral denominado como\n" +
					       "'Archivos_No_Procesados_De_Importacion_Batch(Fecha).zip'.");
			request.setAttribute("mensaje",getMensaje());
		}catch(Exception e){
		  e.printStackTrace();
			System.out.println("Error "+this+":"+e.toString());
			throw new	ServletException(e);
		}finally{
			if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}
		return(ant+="?tipo="+tipo);
	}
	
	/**
	 *	Funcion: Sube el archivo al servidor con un nombre unico para que no sobreeesciba archivos de otros usuarios <BR>
	 *	@param	HttpServletRequest request
	 *	@return	boolean 
	 **/
	public int [] subirArchivo(HttpServletRequest request,Login login) throws ServletException, IOException{
		int []tipo=new int[3];
		tipo[0]=-1;
		String path=rb.getString("importar.carpeta")+"."+login.getUsuarioId();
		String pathSubir=Ruta2.get(getServletContext(),path);
		String fileName="";
		String nombreCampo,valorCampo;
		int cantidad=0;
		Iterator i;
		List fileItems;
		File fichero=null;
		FileItem item = null;
		try{		   
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			/*TRAER LOS CAMPOS QUE NO SON FILE*/
			i = fileItems.iterator();
			while(i.hasNext()){
				fileName=null;
				item = (FileItem)i.next();
				if(item.isFormField()){
					nombreCampo = item.getFieldName();
					valorCampo = item.getString();
					if(nombreCampo.equals("tipo")){
    				try{
    				  tipo[0]=Integer.parseInt(valorCampo);
    				}catch(NumberFormatException e){
    				  tipo[0]=Integer.parseInt(valorCampo.substring(valorCampo.length()-1,valorCampo.length()));
    				}						
					}
				}
			}
			/*VALIDAR TIPO DE LOS ARCHIVOS*/
			i = fileItems.iterator();
			int a[]=validarTipo(i);
			if(a==null){
			  tipo[0]=tipo[0]*-1;
				return (tipo);
			}
			tipo[1]=a[0];
			tipo[2]=a[1];
			/*SUBIR LOS ARCHIVOS*/
			i = fileItems.iterator();
			while (i.hasNext()){
				cantidad++;
				fileName=null;
				item = (FileItem)i.next();
				if(!item.isFormField()){
					fileName = item.getName();
					if(fileName.trim().compareTo("")!=0){
						if(item.getFieldName().equals("archivo")){
							File f=new File(pathSubir);
							if(!f.exists()) FileUtils.forceMkdir(f);
							nombre[1]=getNombre(tipo[0],login,a[0]);
							nombre[0]=pathSubir;
							fichero = new  File(nombre[0]+nombre[1]);
							item.write(fichero);
						}
					}
				}
			}
		}
		catch(FileUploadBase.InvalidContentTypeException e){
		  e.printStackTrace();
			setMensaje("El archivo no es del tipo solicitado:"+e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		catch(FileUploadBase.SizeLimitExceededException e) {
		  e.printStackTrace();
			setMensaje("El archivo se pasa del tamano permitido :"+e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		catch(FileUploadBase.UnknownSizeException e) {
		  e.printStackTrace();
			setMensaje("El archivo no tiene un tamano especificado :"+e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		catch(FileNotFoundException e) {
		  e.printStackTrace();
			setMensaje("No hay un archivo especificado :"+e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		catch(FileUploadException e) {
		  e.printStackTrace();  
			setMensaje("Error tratando de subir el archivo: "+e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		catch(Exception e){
		  e.printStackTrace(); 
			setMensaje("Error de Aplicacinn : "+ e.toString());
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		if(cantidad==0){
			setMensaje("No se han selecionado archivos a importar");
			tipo[0]=tipo[0]*-1;
			return (tipo);
		}
		return tipo;
	}
	
	/**
	 *	Funcion: Valida el contenido del archivo y si no tiene errores importar los datos a la base de datos<BR>
	 *	@param	HttpServletRequest request
	 *	@return	boolean 
	 **/
	public boolean validarArchivo(HttpServletRequest request,int tipo,Login login,int formatoArchivo) throws ServletException, IOException{
		IOBatch io=new IOBatch(); 
		String []params={login.getInstId(),login.getSedeId(),login.getJornadaId()};
		switch(formatoArchivo){
		case EXCEL:
			if(!io.validarFormato(tipo,nombre,params)){
				setMensaje(io.getMensaje());
				return false;
			}
			break;    
		case ZIP:
		    boolean a=io.validarZip(tipo,nombre,params);
			if(!a){
				setMensaje(io.getMensaje());
				return false;
			}			      
		break;    
		}
		return true;
	}
	
	
	
	/**
	 *	Funcion: valida que el content type  del archivo sea del tipo excel <BR>
	 *	@param	Iterator it
	 *	@return	boolean 
	 **/
	public int[] validarTipo(Iterator it){
		int []band=new int[2];
		String fileName;
		String a;
		FileItem item;
		while (it.hasNext()){
			fileName=null;
			item = (FileItem)it.next();
			if(!item.isFormField()){
				fileName = item.getName();
				if(fileName.trim().compareTo("")!=0){
					a=item.getContentType();
					//System.out.println(a);
					boolean aceptado=false;
					for(int n=0;n<tipos.length;n++){
						if(a.equals(tipos[n])){
							aceptado=true;
							//tipoArchivo=n;
							band[0]=n;
							break;
						}
					}				  
					if(!aceptado){
						a="Error con el archivo . posible problema : ";
						a+="\n1. No es un archivo de tipo ";
						for(int n=0;n<tipos.length;n++)a+=tipos[n]+", ";			  
						a+="\n2. El archivo esta abierto(cierrelo e intente de nuevo)";
						setMensaje(a);
						return null;
					}else{					    
						if(band[0]==0) band[1]=EXCEL;
						if(band[0]==1 || band[0]==2) band[1]=ZIP;
						if(band[0]==4) band[1]=PDF;
						if(band[0]==4) band[1]=DOC;
					}
				}
			}
		}
		return band;
	}
	
	/**
	 *	Funcion: Pone el nombre al archivo de inconsistencias respectivo si ocurrieron errores con los datos del archivo <BR>
	 *	@return String 	
	 **/
	public String getNombre(int tipo,Login login,int tipoArchivo){
		String nom="";
		String extencion="";
		extencion=ext[tipoArchivo];
		switch(tipo){
		case Properties.PLANTILLALOGROASIG:
			nom=Properties.PLANTILLALOGROASIG+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
			break;
		case Properties.PLANTILLAAREADESC:
			nom=Properties.PLANTILLAAREADESC+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
			break;
		case Properties.PLANTILLABATLOGRO:
			nom=Properties.PLANTILLABATLOGRO+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
			break;
		case Properties.PLANTILLABATDESCRIPTOR:
			nom=Properties.PLANTILLABATDESCRIPTOR+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
			break;
		case Properties.PLANTILLAPREE:
			nom=Properties.PLANTILLAPREE+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
			break;
		}
		return nom;
	}
	
	/**
	 *	Recibe la peticion por el metodo get de HTTP
	 *	@param	HttpServletRequest request
	 *	@param	HttpServletResponse response
	 **/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}
	
	/**
	 *	Recibe la peticion por el metodo Post de HTTP
	 *	@param	HttpServletRequest request
	 *	@param	HttpServletResponse response
	 **/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String s=process(request, response);
		if(s!=null && !s.equals(""))
			ir(1,s,request,response);
	}
	
	/**
	 *	Redirige el control a otro servlet
	 *	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 *	@param	String s
	 *	@param	HttpServletRequest request
	 *	@param	HttpServletResponse response
	 **/
	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(cursor!=null)cursor.cerrar();
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(request, response);
		else rd.forward(request, response);
	}
	
	/**
	 *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	 *	@param	String s
	 **/
	public void setMensaje(String s){
		if (!err){
			err=true;
			mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}	
		mensaje+="  - "+s+"\n";
	}
	
	/**
	 *	Retorna una variable que contiene los mensajes que se van a enviar a la vista
	 *	@return String	
	 **/
	public String getMensaje(){
		return mensaje;
	}
}
