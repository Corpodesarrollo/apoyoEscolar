package siges.subirFotografia.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.subirFotografia.dao.SubirFotografiaDAO;



import siges.common.service.Service;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.subirFotografia.dao.SubirFotografiaDAO;
import siges.subirFotografia.io.SubirFotografiaIO;
import siges.subirFotografia.vo.ParamsVO;
import siges.subirFotografia.vo.SubirFotografiaVO;
import sun.misc.BASE64Decoder;
import siges.login.beans.Login;
import siges.estudiante.beans.Basica;
import siges.exceptions.InternalErrorException;
import siges.personal.beans.Personal;
import java.util.ResourceBundle;


import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;



public class PhotoUploader extends Service{

	private String FICHA_SUBIR_ESTUDIANTE;
	private String FICHA_SUBIR_PERSONAL;
	private SubirFotografiaDAO subirFotografiaDAO=new SubirFotografiaDAO(new Cursor());
	private String []tipos={"image/jpeg","image/pjpeg","application/octet-stream"};
	private ResourceBundle rb;
	String contextoTotal;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//System.out.println("UPLOADER: ENTRO A INIT**** ");

	}

	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		Basica basica;
		Personal personal;
		HttpSession session = request.getSession();
		contextoTotal=context.getRealPath("/");
		String dispatcher[]=new String[2];
		String FICHA;
		rb = ResourceBundle.getBundle("siges.subirFotografia.bundle.subirFotografia");
		System.out.println("ENTRO A SERVLET SUBIRFOTOGRAFIA ............ ");
		try{
			FICHA_SUBIR_ESTUDIANTE=config.getInitParameter("FICHA_SUBIR_ESTUDIANTE");
			FICHA_SUBIR_PERSONAL=config.getInitParameter("FICHA_SUBIR_PERSONAL");
			Login usuVO=(Login)session.getAttribute("login");
			basica=(Basica)session.getAttribute("nuevoBasica2");
			personal = (Personal) request.getSession().getAttribute("nuevoPersonal2");
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				FICHA = processForm(request, session,usuVO);
				System.out.println("PROCESS FORM ");
			} else {
				System.out.println("MULTI PART FORM ");
				FICHA = processMultipart(request, session,usuVO,basica,personal);
				
			}
		}catch(Exception e){e.printStackTrace(); throw new ServletException(e.getMessage());}
		dispatcher[0]=String.valueOf(ParamsVO.FORWARD);
		dispatcher[1]=FICHA;
		return dispatcher;
}
	
	
	public String processForm(HttpServletRequest request, HttpSession session,Login usuVO) throws ServletException, IOException {
		int CMD;
		int TIPO;
		String FICHA=null;
		CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		/*if(TIPO!=ParamsVO.FICHA_SUBIR){
			TIPO=ParamsVO.FICHA_DEFAULT;	
		}*/
		
		//PRUEBA DATA
		ServletContext sc =(ServletContext)request.getSession().getServletContext();
		try
        {
        String fileName = "prueba.jpeg";
        String path = sc.getRealPath(File.separator)+fileName;
       
        File yourFile = new File(path);
        FileOutputStream toFile = new FileOutputStream( yourFile );
        DataInputStream fromClient = new DataInputStream( request.getInputStream() );
        
        
        InputStream is=request.getInputStream();
		
		int length = is.available();
		
	
        byte[] buff = new byte[10240];
        int cnt = 0;
            while( (cnt = fromClient.read( buff )) > -1 ) {
            	System.out.println("ENTRO A ESCRIBIR IMGAN SERVIDOR");
            toFile.write( buff, 0, cnt );
        }
        toFile.flush();
        toFile.close();
        fromClient.close();
       
    }
    catch(Exception e)
        {
        e.printStackTrace();
    }
    
		
		//
		try {
			switch (TIPO) {
			case ParamsVO.FICHA_SUBIR:
				FICHA=FICHA_SUBIR_ESTUDIANTE;
				//nuevoSubir(request,usuVO);
				break;
				
			case ParamsVO.FICHA_CAPTURA:
				FICHA=FICHA_SUBIR_ESTUDIANTE;
				
				
				InputStream in = request.getInputStream();				
				//FileOutputStream out = new FileOutputStream("abc.jpg");
				
				byte[] buf = new byte[4096];
				System.out.println("in read "+in.read(buf));
				int nread = 0;
				while (-1!=(nread = in.read(buf)))
				{
				   //out.write(buf, 0, nread);
					System.out.println("ENTRO Y RECIBIO ARRAY IMG");
				}
				//out.close();
		         in.close();
				String nom_par=""; 
				 for ( Enumeration e= request.getParameterNames(); e.hasMoreElements(); ) {
					 nom_par= (String) e.nextElement();					 
		        }
				System.out.println("ENTRO A CAPTURA FOTOGRAFIA, ENVIO BIEN LOS PARAMETROS : CMD : "+CMD);
				//nuevoSubir(request,usuVO);
				break;
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return FICHA;
		}
		return FICHA;
	}
	
	
	public String processMultipart(HttpServletRequest request, HttpSession session, Login usuVO, Basica basica,Personal personal) throws ServletException, IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		String strCaptura=null;

		
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("cmd")) {
						System.out.println("item.getFieldName():"+item.getFieldName());
						System.out.println("item.getString():"+item.getString());
						
						CMD = Integer.parseInt(item.getString());
					}
					if (item.getFieldName().equals("tipo")) {
						System.out.println("item.getFieldName():"+item.getFieldName());
						System.out.println("item.getString():"+item.getString());
						TIPO = Integer.parseInt(item.getString());
					}
					
					if (item.getFieldName().equals("filArchivo2")) {
						System.out.println("item.getFieldName():"+item.getFieldName());
						//System.out.println("item.getString():"+item.getString());
						strCaptura= item.getString();
					}
				}
			}
			switch (TIPO) {
				case ParamsVO.FICHA_SUBIR:
					switch (CMD) {
						case ParamsVO.CMD_GUARDAR:
							System.out.println("***guardarSubir Estudiante***");
							i = fileItems.iterator();
							//guardarSubir(request, session,usuVO, i,basica);
							guardarSubir(request, session,usuVO, i,basica,strCaptura);
						break;
					}
				break;
				
				case ParamsVO.FICHA_SUBIR_PERSONAL:
					switch (CMD) {
						case ParamsVO.CMD_GUARDAR:
							System.out.println("***guardarSubirPersonal***");
							i = fileItems.iterator();
							//guardarSubirPersonal(request, session,usuVO, i,personal);
							FICHA_SUBIR_ESTUDIANTE=FICHA_SUBIR_PERSONAL;
						break;
					}
				break;
				
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			e.printStackTrace();
			return FICHA_SUBIR_ESTUDIANTE;
		} catch (Exception e) {
			e.printStackTrace();
			return FICHA_SUBIR_ESTUDIANTE;
		}
		return FICHA_SUBIR_ESTUDIANTE;
	}

	
	public void guardarSubir(HttpServletRequest request, HttpSession session, Login usuVO,Iterator i,Basica basica, String octetStream) throws Exception {
		FileItem item = null;
		SubirFotografiaVO subirFotografiaVO=null;
		int longitud= Integer.parseInt(rb.getString("foto.longitud"));
		
		
		
		
		
		try {
			subirFotografiaVO=new SubirFotografiaVO(); 
			if(basica!=null){
				//while (i.hasNext()) {
					//item = (FileItem) i.next();
					//if (!item.isFormField()) {
						//if (item.getFieldName().equals("filArchivo")) {
							//System.out.println(item.getContentType());
//							if (!validarTipo(item.getContentType())) {
//								request.setAttribute(ParamsVO.SMS, "El archivo no corresponde a los tipos de contenido permitidos");
//								return;
//							}
//							subirFotografiaVO.setAyuArchivo(item.get());
//							subirFotografiaVO.setEstCodigo(Long.parseLong(basica.getEstcodigo()));
//							System.out.println("en NUEVO: subirFotografiaVO.getAyuArchivo().length: "+subirFotografiaVO.getAyuArchivo().length);
//							if(subirFotografiaVO.getAyuArchivo().length>longitud){
//								request.setAttribute(ParamsVO.SMS, "El archivo excede el tamano permitido ["+longitud+"]");
//								return;
//							}else{
//								
								System.out.println("ENTRO A SUBIR FOTO A DD *****************");
								
								
								  
								
								
								//if(subirFoto(item,  contextoTotal, basica.getEstcodigo())){
								
								if(saveFile( contextoTotal, octetStream, basica.getEstcodigo())){
									subirFotografiaDAO.actualizarFoto(1, Long.parseLong(basica.getEstcodigo()));
								}else
									subirFotografiaDAO.actualizarFoto(0, Long.parseLong(basica.getEstcodigo()));
								
							//}
							
						//}
					//}
				//}
				request.setAttribute(ParamsVO.SMS, "El archivo fue importado satisfactoriamente");
			}else{
				request.setAttribute(ParamsVO.SMS, "bean de Basica es nulo");
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El archivo no fue importado : "+e.getMessage());
		}
	}
	
	
	public boolean validarTipo(String a) {
		boolean aceptado = false;
		for (int n = 0; n < tipos.length; n++) {
			if(a.equals((String) tipos[n])){
				aceptado = true;
				break;
			}
		}
		return aceptado;
	}  
	
	private boolean saveFile(String context1 ,String octetStream , String estudiante) throws IOException{
//	    byte[] bytes = Base64.getDecoder().decode(octetStream);
//
//	    String archivosalida = Ruta.get(context1, rb.getString("foto.pathSubir"));
//	    
//	    try(FileOutputStream fos = new FileOutputStream(archivosalida)){
//	        fos.write(bytes);
//	    }
		
		//String data =octetStream;
	    
		 String base64String = octetStream;
	        String[] strings = base64String.split(",");
	        String extension;
	        switch (strings[0]) {//check image's extension
	            case "data:image/jpeg;base64":
	                extension = "jpeg";
	                break;
	            case "data:image/png;base64":
	                extension = "png";
	                break;
	            default://should write cases for more images types
	                extension = "jpg";
	                break;
	        }
	        //convert base64 string to binary data
	        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
	        //String path = "C:\\Users\\Ene\\Desktop\\test_image." + extension;

	        
	       
	
	        String archivosalida = Ruta.get(context1, rb.getString("foto.pathSubir"));
//			String fileName = null;
//			fileName = item.getName();
			java.io.File f = new java.io.File(archivosalida);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			//fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());
			//fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
	        
			
	        //File file = new File(archivosalida+"\\"+estudiante+".jpg");
			
			System.out.println("***ruta***"+archivosalida + estudiante+".jpg");
	        
	        f = new java.io.File(archivosalida + estudiante+".jpg");
	        
	        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f))) {
	        	
	        	
					
	        	
	        	
	        	
	            outputStream.write(data);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	    
	    
	    
	    return true;
	}
}	
