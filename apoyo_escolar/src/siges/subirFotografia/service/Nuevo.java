/**
 * 
 */
package siges.subirFotografia.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import jxl.write.DateTime;
import siges.common.service.Service;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.subirFotografia.dao.SubirFotografiaDAO;
import siges.subirFotografia.io.SubirFotografiaIO;
import siges.subirFotografia.vo.ParamsVO;
import siges.subirFotografia.vo.SubirFotografiaVO;
import util.BitacoraCOM;
import util.LogPersonalDto;
import siges.login.beans.Login;
import siges.estudiante.beans.Basica;
import siges.exceptions.InternalErrorException;
import siges.personal.beans.Personal;
import java.util.ResourceBundle;
import java.io.ByteArrayInputStream;
import java.nio.file.StandardCopyOption;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
/**
 * 24/09/2008 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service{

	private String FICHA_SUBIR_ESTUDIANTE;
	private String FICHA_SUBIR_PERSONAL;
	private SubirFotografiaDAO subirFotografiaDAO=new SubirFotografiaDAO(new Cursor());
	private String []tipos={"image/jpeg","image/pjpeg"};
	private ResourceBundle rb;
	String contextoTotal;
	
	
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
				nuevoSubir(request,usuVO);
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

	private void nuevoSubir(HttpServletRequest request,Login usuVO)throws Exception{
	}

	public String processMultipart(HttpServletRequest request, HttpSession session, Login usuVO, Basica basica,Personal personal) throws ServletException, IOException {
		int CMD;
		int TIPO;
		Iterator i;
		List fileItems;
		FileItem item = null;
		CMD = ParamsVO.CMD_NUEVO;
		TIPO = ParamsVO.FICHA_DEFAULT;
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("cmd")) {
						CMD = Integer.parseInt(item.getString());
					}
					if (item.getFieldName().equals("tipo")) {
						TIPO = Integer.parseInt(item.getString());
					}
				}
			}
			switch (TIPO) {
				case ParamsVO.FICHA_SUBIR:
					switch (CMD) {
						case ParamsVO.CMD_GUARDAR:
							System.out.println("***guardarSubir Estudiante***");
							i = fileItems.iterator();
							guardarSubir(request, session,usuVO, i,basica);
						break;
					}
				break;
				
				case ParamsVO.FICHA_SUBIR_PERSONAL:
					switch (CMD) {
						case ParamsVO.CMD_GUARDAR:
							System.out.println("***guardarSubirPersonal***");
							i = fileItems.iterator();
							guardarSubirPersonal(request, session,usuVO, i,personal);
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

	public void guardarSubir(HttpServletRequest request, HttpSession session, Login usuVO,Iterator i,Basica basica) throws Exception {
		FileItem item = null;
		SubirFotografiaVO subirFotografiaVO=null;
		int longitud= Integer.parseInt(rb.getString("foto.longitud"));
		
		try {
			subirFotografiaVO=new SubirFotografiaVO(); 
			if(basica!=null){
				while (i.hasNext()) {
					item = (FileItem) i.next();
					if (!item.isFormField()) {
						if (item.getFieldName().equals("filArchivo")) {
							System.out.println(item.getContentType());
							if (!validarTipo(item.getContentType())) {
								request.setAttribute(ParamsVO.SMS, "El archivo no corresponde a los tipos de contenido permitidos");
								return;
							}
							subirFotografiaVO.setAyuArchivo(item.get());
							subirFotografiaVO.setEstCodigo(Long.parseLong(basica.getEstcodigo()));
							System.out.println("en NUEVO: subirFotografiaVO.getAyuArchivo().length: "+subirFotografiaVO.getAyuArchivo().length);
							if(subirFotografiaVO.getAyuArchivo().length>longitud){
								request.setAttribute(ParamsVO.SMS, "El archivo excede el tamano permitido ["+longitud+"]");
								return;
							}else{
								
								System.out.println("ENTRO A SUBIR FOTO A DD *****************");
								if(subirFoto(item,  contextoTotal, basica.getEstcodigo())){
									subirFotografiaDAO.actualizarFoto(1, Long.parseLong(basica.getEstcodigo()));
								}else
									subirFotografiaDAO.actualizarFoto(0, Long.parseLong(basica.getEstcodigo()));
								
							}
							
						}
					}
				}
				request.setAttribute(ParamsVO.SMS, "El archivo fue importado satisfactoriamente");
			}else{
				request.setAttribute(ParamsVO.SMS, "bean de Basica es nulo");
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El archivo no fue importado : "+e.getMessage());
		}
	}

	
	public void guardarSubirPersonal(HttpServletRequest request, HttpSession session, Login usuVO,Iterator i,Personal personal) throws Exception {
		FileItem item = null;
		SubirFotografiaVO subirFotografiaVO=null;
		try {
			subirFotografiaVO=new SubirFotografiaVO(); 
			if(personal!=null){
				while (i.hasNext()) {
					item = (FileItem) i.next();
					if (!item.isFormField()) {
						if (item.getFieldName().equals("filArchivo")) {
							System.out.println(item.getContentType());
							if (!validarTipo(item.getContentType())) {
								request.setAttribute(ParamsVO.SMS, "El archivo no corresponde a los tipos de contenido permitidos");
								return;
							}
							subirFotografiaVO.setAyuArchivo(item.get());
							subirFotografiaVO.setEstCodigo(Long.parseLong(personal.getPernumdocum()));
							subirFotografiaDAO.actualizarFotoPersonal(subirFotografiaVO,personal.getPertipdocum());
							//bitacora
							try
							{
								LogPersonalDto log = new LogPersonalDto();
								byte[] encoded = Base64.getEncoder().encode(item.get());
							    String encodedString = new String(encoded,StandardCharsets.US_ASCII);
								log.setFoto(encodedString);;
								log.setFecha(LocalDateTime.now().toString());
								BitacoraCOM.insertarBitacora(
										Long.parseLong(usuVO.getInstId()), 
										Integer.parseInt(usuVO.getJornadaId()),
										2 ,
										usuVO.getPerfil(), 
										Integer.parseInt(usuVO.getSede()), 
										20001, 
										2, 
										usuVO.getUsuarioId(), 
										new Gson().toJson(log)
										);
							}catch(Exception e){
								e.printStackTrace();
								System.out.println("Error " + this + ":" + e.toString());
							}
						}
					}
				}
				request.setAttribute(ParamsVO.SMS, "El archivo fue importado satisfactoriamente");
			}else{
				request.setAttribute(ParamsVO.SMS, "bean de Personal es nulo");
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
	
	
	
	public boolean subirFoto(FileItem item,String context1, String estudiante) {

		Connection con = null;
		try {
			//con = cursor.getConnection();
			String archivosalida = Ruta.get(context1, rb.getString("foto.pathSubir"));
			String fileName = null;
			fileName = item.getName();
			java.io.File f = new java.io.File(archivosalida);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
			f = new java.io.File(archivosalida + estudiante+".jpg");
			//System.out.println("SUBIRFOTOGRAFIA"+": escribiendo jpg");
			//item.write(f);
			InputStream in  = new ByteArrayInputStream(item.get());
            Files.copy(in, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeConnection(con);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	

}
