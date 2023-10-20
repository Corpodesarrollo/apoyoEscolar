package siges.importar.comportamiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2; 
import siges.importar.Excel2;
import siges.importar.beans.FiltroComportamiento;
import siges.importar.beans.ParamsVO;
import siges.importar.beans.UrlImportar;
import siges.importar.dao.ImportarDAO;
import siges.login.beans.Login;
import siges.plantilla.comportamiento.dao.ComportamientoDAO;

/**  
 * 24/08/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service {
	private String FICHA;

	private String FICHA_COMPORTAMIENTO;
	private ComportamientoDAO comportamientoDAO; 
	
	ResourceBundle rb = ResourceBundle.getBundle("siges.importar.bundle.importar");
	private final String[] tipos = { "application/vnd.ms-excel", "application/x-msexcel", "application/zip" };

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FICHA_COMPORTAMIENTO = config.getInitParameter("FICHA_COMPORTAMIENTO");
		String params[] = null;
		Login usuVO =null;
		String prefijo="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n  - ";
		comportamientoDAO=new ComportamientoDAO(new Cursor());
		try {
			FICHA = FICHA_COMPORTAMIENTO;
			usuVO = (Login) session.getAttribute("login");
			//String file = 
			UrlImportar url=subirFile(request, usuVO);
			if(url!=null){
				switch(url.getEstado()){
					case -2:
						//System.out.println("Error intentando subir el archivo. Archivo no encontrado");
						//request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Archivo no encontrado");
						break;
					case -1:
						System.out.println("Error intentando subir el archivo. Tipo de contenido no permitido n el archivo esta abierto");
						request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Tipo de contenido no permitido n el archivo esta abierto");
						break;
					case 0:
						System.out.println("no hay archivo");
						request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Archivo no encontrado");
						break;
					case 1:
						//System.out.println("Se procesa el archivo" + url.getNombrePlantilla());
						switch(url.getTipo()){
							case ParamsVO.FICHA_COMPORTAMIENTO:
								System.out.println("entor a inmportar");
								url=importarComportamiento(request,url,usuVO);
								switch(url.getEstado()){
									case -1://tipo no es
										System.out.println("Tipo de plantilla no es");
										request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Tipo de plantilla no es");
									break;
									case -2://encabezado no es
										System.out.println("Encabezado de plantilla no es");
										request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. El encabezado de plantilla no corresponde");
									break;
									case -3://estudiantes no son
										System.out.println("Estudiantes de plantilla no son");
										request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Los estudiantes de la plantilla o las notas no corresponden");
									break;
									case 0://error interno
										request.setAttribute("mensaje", prefijo+"Error intentando subir el archivo. Error interno");
									break;
									case 1://Bien
										request.setAttribute("mensaje", prefijo+"Plantilla importada satisfactoriamente");
									break;
								}
							break;
						}
						break;
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mensaje", prefijo+"Error procesando peticion: "+e.getMessage());
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	
	private UrlImportar importarComportamiento(HttpServletRequest request, UrlImportar url,Login login) throws Exception{
		String pathPlantilla = null;
		String pathDownload = null;
		String relativo = null;
		String path = null;
		System.out.println("entor a inmportar 2 ");
		FiltroComportamiento filtro = null;
		path = rb.getString("comportamiento.PathDescarga") + "." + login.getUsuarioId();// ruta de carpetas
		relativo = Ruta.getRelativo(context, path);
		pathPlantilla = Ruta2.get(context, rb.getString("comportamiento.PathPlantilla")) + rb.getString("comportamiento.Plantilla");// archivo de plantilla
		pathDownload = Ruta.get(context, path);// path del nuevo archivo
		Excel2 excel = new Excel2(comportamientoDAO);
		try{
			
			
			//List listaEscala=comportamientoDAO.getEscalaComportamiento();
			 //filtro =	excel.getCargarFiltro(filtro);
			 List listaEscala = null; 
			// comportamientoDAO.getEscalaNivelEval(filtro);
			url.setPathDescarga(pathDownload);
			url.setNombreDescarga(url.getNombrePlantilla());
			url.setPathRelativo(relativo+url.getNombrePlantilla());
			url=excel.importarComportamiento(url,listaEscala,login);
		
			
			if (!excel.isBand()){//hay plantilla
				request.setAttribute("plantilla", url.getPathRelativo());
				request.setAttribute("formaEstado","1");
			}
			List r=url.getResultado();
			if(r!=null && r.size()>0){
				request.setAttribute("resultadoImportacion", r);
				request.setAttribute("formaEstado","2");
				url.setEstado(1);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("error " + e.getMessage()  );
			url.setEstado(0);
		}
		return url;
	}
	
	private UrlImportar subirFile(HttpServletRequest request, Login login) throws Exception{
		String path = rb.getString("importarLogro.pathSubir") + "." + login.getUsuarioId();
		String pathSubir = Ruta.get(context, path);
		String pathTemporal = Ruta.get(context, rb.getString("importarLogro.temporal"));
		String nombreCampo, valorCampo;
		UrlImportar url=new UrlImportar();
		url.setPathPlantilla(pathSubir);
		int TIPO=0;
		//int CMD=0;
		List fileItems;
		Iterator i;
		String fileName = null;
		FileItem item = null;
		try {
//			DiskFileUpload fu = new DiskFileUpload();
//			fileItems = fu.parseRequest(request, 4096, 1024 * 1024, pathTemporal);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			fileItems = upload.parseRequest(request);
			//traer el tipo
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					nombreCampo = item.getFieldName();
					valorCampo = item.getString();
					if (nombreCampo.equals("tipo")) {
						TIPO = Integer.parseInt(valorCampo);
					}
				}
			}
			/*
			//traer el cmd
			i = fileItems.iterator();
			while (i.hasNext()) {
				item = (FileItem) i.next();
				if (item.isFormField()) {
					nombreCampo = item.getFieldName();
					valorCampo = item.getString();
					if (nombreCampo.equals("cmd")) {
						CMD = Integer.parseInt(valorCampo);
					}
				}
			}
			*/
			//validar el tipo MIME
			i = fileItems.iterator();
			if(!validarMime(i)){
				url.setEstado(-1);
				return url;
			}
			/* SUBIR LOS ARCHIVOS */
			i = fileItems.iterator();
			while (i.hasNext()) {				
				fileName = null;
				item = (FileItem) i.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					if (fileName.trim().compareTo("") != 0) {
						if (item.getFieldName().equals("archivo")) {
							java.io.File f = new java.io.File(pathSubir);
							if (!f.exists()) {
								FileUtils.forceMkdir(f);
							}
							//fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());
							//fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
							fileName = getNombre();
							url.setNombrePlantilla(fileName);
							f = new java.io.File(pathSubir + fileName);
							item.write(f);
						}
					}
				}
			}
			if(fileName==null){
				url.setEstado(0);
				return url;
			}
		} catch (FileUploadBase.InvalidContentTypeException e) {
			//throw new Exception("Contenido invalido");
			url.setEstado(-2);
			return url;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			throw new Exception("Tamano de archivo excedido");
		} catch (FileUploadBase.UnknownSizeException e) {
			throw new Exception("Tamano de archivo desconocido");
		} catch (FileNotFoundException e) {
			throw new Exception("Archivo no encontrado");
		} catch (FileUploadException e) {
			throw new Exception("Error de archivo: "+e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error interno: "+e.getMessage());
		}
		url.setTipo(TIPO);
		url.setEstado(1);
		return url;
	}

	private boolean validarMime(Iterator it) {
		String fileName;
		String a=null;
		FileItem item;
		while (it.hasNext()) {
			fileName = null;
			item = (FileItem) it.next();
			if (!item.isFormField()) {
				fileName = item.getName();
				if (fileName.trim().compareTo("") != 0) {
					a = item.getContentType();
					// System.out.println("TipoArchivo="+a);
					boolean aceptado = false;
					for (int n = 0; n < tipos.length; n++) {
						if (a.equals(tipos[n])) {
							aceptado = true;
							break;
						}
					}
					if (!aceptado) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private String getNombre(){
		Timestamp f2 = new Timestamp(new java.util.Date().getTime());
		return "Inconsistencias_Evaluacion_Comportamiento_" + f2.toString().replace(':', '-').replace('.', '-').replace(' ', '_') + ".xls";
	}
}
