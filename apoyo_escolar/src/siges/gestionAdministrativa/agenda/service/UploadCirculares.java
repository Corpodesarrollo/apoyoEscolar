/**
 * Paquete que atiende las solicitudes para el mndulo de AGENDA
 */
package siges.gestionAdministrativa.agenda.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;





import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.gestionAdministrativa.agenda.dao.GestionTareasDAO;
import siges.gestionAdministrativa.agenda.vo.CircularVO;
import siges.gestionAdministrativa.agenda.vo.FiltroCircularesVO;
import siges.gestionAdministrativa.agenda.vo.ParamsVO;
import siges.login.beans.Login;
/**
 * 26/07/2013 
 * @author Mauricio A. Coral Lozada
 * @version 1.1
 */
public class UploadCirculares extends Service{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String FICHA_CIRCULARES;
	private Login usuVO;
	private java.sql.Timestamp f2;
	private ResourceBundle rPath;
	private GestionTareasDAO tareaDAO = null;
  
	 
	 /**
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_CIRCULARES = config.getInitParameter("FICHA_CIRCULARES");
		f2=new java.sql.Timestamp(new java.util.Date().getTime());
		rPath = ResourceBundle.getBundle("path");
		tareaDAO = new GestionTareasDAO(new Cursor());
	}
	
	/*
	 * Procesa la peticinn
	 * @param request peticinn
	 * @param response respuesta
	 * @return Ruta de redireccinn
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String dispatcher[]=new String[2];
		context=config.getServletContext();
		FICHA_CIRCULARES = config.getInitParameter("FICHA_CIRCULARES");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		String idCircular = (String)session.getAttribute("idCircular");
		FiltroCircularesVO circularFiltro = null;
		
		if(circularFiltro == null)
			circularFiltro = new FiltroCircularesVO();
		try{
			/**
			 * Circulares
			 */
				circularFiltro = (FiltroCircularesVO)request.getSession().getAttribute("circularFiltro");
			} catch (Exception e){}
		circularFiltro.setNivelUsuario(Integer.parseInt(usuVO.getPerfil()));
		
		
			String pathSubir=Ruta.get(context,
					rPath.getString("resultados.PathResultados")) + "uploadFiles/";
			try{	
				File fichero=null;
	
				List items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				Iterator i = items.iterator();
		        while (i.hasNext()) {
		        	FileItem item = (FileItem)i.next();
		            if (!item.isFormField()) {
		                // Process form file field (input type="file").
		                File f=new File(pathSubir);	
		                String fn = item.getName();
		                String[] splitted = fn.split("\\.");
		                if(!f.exists()) FileUtils.forceMkdir(f);
		                String nombre = getNombre(usuVO, splitted[splitted.length-1]);
						fichero = new  File(pathSubir+nombre);
						item.write(fichero);
						
						//Update Circular row in Database
						CircularVO c = tareaDAO.getCircular(Long.parseLong(idCircular), session);
						c.setArchivo(nombre);
						tareaDAO.updateCircular(request, session, usuVO, c);
						request.setAttribute(ParamsVO.SMS,
								"Se agrego archivo a la circular exitosamente");
		            }
		        }
		        buscarCircular(request,session, usuVO, circularFiltro);
			}
			catch(FileUploadBase.InvalidContentTypeException e){
			  e.printStackTrace();
			}
			catch(FileUploadBase.SizeLimitExceededException e) {
			  e.printStackTrace();
			}
			catch(FileNotFoundException e) {
			  e.printStackTrace();
			}
			catch(FileUploadException e) {
			  e.printStackTrace();  
			}
			catch(Exception e){
			  e.printStackTrace(); 
			}

		
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA_CIRCULARES;
		return dispatcher;
	}
	
	/**
	 * Trae el listado de Tareas según los criterios seleccionados en el filtr
	 * @param request
	 * @param session
	 * @param usuVO2
	 * @param filtroGestion
	 */
	private void buscarCircular(HttpServletRequest request,
			HttpSession session, Login usuVO2, FiltroCircularesVO c) {
		try {
			request.setAttribute("listaCirculares", tareaDAO.getCircular(c, request, usuVO2)); //filtroTareasVO
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS,
					"Ocurrio un problema procesando tu solicitud.");
		}
	}
	
	
	/**
	 *	Funcion: Pone el nombre al archivo de inconsistencias respectivo si ocurrieron errores con los datos del archivo <BR>
	 *	@return String 	
	 **/
	public String getNombre(Login login, String ext){
		String nom="";
		String extencion="."+ext;
		nom="Circular"+"_"+login.getInstId()+"_"+f2.toString().replace(':','-').replace('.','-')+extencion;
		return nom;
	}
	
	
}
