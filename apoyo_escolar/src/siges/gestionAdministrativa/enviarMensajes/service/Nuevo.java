/**
 * 
 */
package siges.gestionAdministrativa.enviarMensajes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.enviarMensajes.dao.EnviarMensajesDAO;
import siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO;
import siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO;
import siges.gestionAdministrativa.enviarMensajes.vo.ParamsVO;
import siges.login.beans.Login;
import siges.login.dao.LoginDAO;
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	public String FICHA_LISTA;
	public String FICHA_MENSAJE;
	private ResourceBundle rb;
	private String contextoTotal;
	private EnviarMensajesDAO enviarDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;
	
	/**
	 *  
	 
	 /*
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		enviarDAO = new EnviarMensajesDAO(new Cursor());
		FICHA_LISTA= config.getInitParameter("FICHA_LISTA");
		FICHA_MENSAJE= config.getInitParameter("FICHA_MENSAJE");
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
		String FICHA=null;
		rb=ResourceBundle.getBundle("siges.gestionAdministrativa.enviarMensajes.bundle.enviarMensajes");
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		FiltroEnviarVO filtroVO = (FiltroEnviarVO) session.getAttribute("filtroMensajeVO");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_LISTA); 
		
		System.out.println("ENVIAR MENSAES TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO){
		case ParamsVO.FICHA_LISTA:		
			FICHA = FICHA_LISTA;				
			switch (CMD){		
			case ParamsVO.CMD_NUEVO: 
				request.getSession().removeAttribute("filtroMensajeVO");
				request.getSession().removeAttribute("filtroMensajeVO");
				request.getSession().removeAttribute("mensajesVO");
				break;
			case ParamsVO.CMD_BUSCAR:
				buscar(request, session, usuVO, filtroVO);
				break;	
			case ParamsVO.CMD_EDITAR:
				cargarFiltroMensajes(request,session, usuVO, filtroVO);
				editarMensaje(request, session, usuVO, filtroVO);
				
				FICHA = FICHA_MENSAJE;	
				break;
			case ParamsVO.CMD_ELIMINAR:
				eliminarMensaje(request, session, usuVO, filtroVO);
				buscar(request, session, usuVO, filtroVO);
				FICHA = FICHA_LISTA;	
				break;	
				
				
			}	
			cargarFiltroMensajes(request,session, usuVO, filtroVO);	
			break;			
			
		case ParamsVO.FICHA_MSJ:			
			FICHA = FICHA_MENSAJE;		
			switch (CMD){			
			case 10:
			case ParamsVO.CMD_NUEVO:
				request.getSession().removeAttribute("filtroMensajeVO");
				request.getSession().removeAttribute("mensajesVO");
				
				break;
			case ParamsVO.CMD_GUARDAR:
				guardar(request, session, usuVO, filtroVO);
				break;
			}
			cargarFiltroMensajes(request,session, usuVO, filtroVO);
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		System.out.println("APOYO:ENVIAR MENSAJES FICHA " + FICHA);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	
	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroMensajes(HttpServletRequest request, HttpSession session,Login usuVO, FiltroEnviarVO filtroVO ) throws ServletException {
		try {
			
			int nivelUsuario=Integer.parseInt(usuVO.getNivel());			
			if(filtroVO==null){
				filtroVO =  new FiltroEnviarVO();
			}
			filtroVO.setUsuario(usuVO.getUsuarioId());
			filtroVO.setEnviado(ParamsVO.ENV_SED);
			if(nivelUsuario>=2){
				if(usuVO.getLocId()!=null && usuVO.getLocId().length()>0){
					filtroVO.setLoc(Long.parseLong(usuVO.getLocId()));
					filtroVO.setHab_loc(0);
					filtroVO.setEnviado(ParamsVO.ENV_LOC);
					if(nivelUsuario>=4){						
						if(usuVO.getLocId()!=null && usuVO.getLocId().length()>0){
							filtroVO.setInst(Long.parseLong(usuVO.getInstId()));							
							filtroVO.setHab_inst(0);
							filtroVO.setEnviado(ParamsVO.ENV_INST);
						}						
					}
				}
			}			
			
			request.setAttribute("filListaPerfil", enviarDAO.getListaPerfil(Long.parseLong(usuVO.getPerfil())));
			request.setAttribute("filLlistaLocalidad", enviarDAO.getListaLocal());
			
			
			request.setAttribute("listaPerfil_", enviarDAO.getListaPerfil(Long.parseLong(usuVO.getPerfil())));
			request.setAttribute("listaLocalidad", enviarDAO.getListaLocal());
			
			if(filtroVO.getFillocal()>=0){				 
				request.setAttribute("listaColegio", enviarDAO.getListaInst(filtroVO.getFillocal()));
				request.setAttribute("fiListaColegio", enviarDAO.getListaInst(filtroVO.getFillocal()));
				if(filtroVO.getFilinst()>=0){
					request.setAttribute("filListaSede", enviarDAO.getListaSede(filtroVO.getFilinst()));					
					if(filtroVO.getFilsede()>=0){
						request.setAttribute("filListaJord", enviarDAO.getListaJornd( filtroVO.getFilinst(), filtroVO.getFilsede()));					
					}
				}
			}	 
			request.getSession().setAttribute("filtroMensajeVO",filtroVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	
	public String buscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroEnviarVO filtro) throws ServletException {
		try {			
			if(filtro!=null){
				
				int per_nivel = enviarDAO.getNivelPerfil(Long.parseLong(usuVO.getPerfil()));
				System.out.println("per_nivel " + per_nivel);
				if( per_nivel == 1){ //sec
					filtro.setFilenviadopor(ParamsVO.ENV_SED);
				}
				if( per_nivel == 2 ){ //local 
					filtro.setFilenviadopor(ParamsVO.ENV_LOC);
				}
				
				if( per_nivel == 4){ // colegio
					filtro.setFilenviadopor( ParamsVO.ENV_INST); 
				}
				
				request.setAttribute("listaMensajes", enviarDAO.listaMensajes(filtro));
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_LISTA;
	}
	
	
	
	
	
	public List getListaPeriodo(long numPer, String nomPerDef){
		List l=new ArrayList();
		ItemVO item=null;
		for(int i=1;i<=numPer;i++){
			item=new ItemVO(i,""+i); l.add(item);
		}		
		item=new ItemVO(7,nomPerDef); l.add(item);
		return l;
	}
	
	
	/**
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtro
	 * @return
	 * @throws ServletException
	 */
	public String guardar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroEnviarVO filtro) throws ServletException {
		 
		try {	
			MensajesVO mensajesVO = (MensajesVO)request.getSession().getAttribute("mensajesVO");
			
			String  perfiles = request.getParameter("perfiles");
			String  localidades = request.getParameter("localidades");
			String  colegios = request.getParameter("colegios");
			String  sedes = request.getParameter("sedes");
			String  jornadas = request.getParameter("jornadas");
			
			mensajesVO.setMsjenviadoaperfil(perfiles);
			mensajesVO.setMsjenviadoalocal(localidades);
			mensajesVO.setMsjenviadoacoleg(MensajesVO.formatoInst(colegios));
			mensajesVO.setMsjenviadoasede(sedes);
			mensajesVO.setMsjenviadoajorn(jornadas);
			mensajesVO.setMsjusuario(usuVO.getUsuarioId());
			int per_nivel = enviarDAO.getNivelPerfil(Long.parseLong(usuVO.getPerfil()));
			System.out.println("per_nivel " + per_nivel);
			if( per_nivel == 1){ //sec
				mensajesVO.setMsjenviadopor( ParamsVO.ENV_SED);
			}
			if( per_nivel == 2 ){ //local 
				mensajesVO.setMsjenviadopor(ParamsVO.ENV_LOC);
			}
			
			if( per_nivel == 4){ // colegio
				mensajesVO.setMsjenviadopor( ParamsVO.ENV_INST); 
			}
			
			
			
			if(mensajesVO.getFormaEstado().equals("1") ){
				mensajesVO = enviarDAO.updateMensajes(mensajesVO);
				request.setAttribute(ParamsVO.SMS, "información actualizada  satisfactoriamente.");
			}else{
				mensajesVO.setFormaEstado("1");
				mensajesVO = enviarDAO.guardarMensajes(mensajesVO);
				request.setAttribute(ParamsVO.SMS, "información registrada satisfactoriamente.");
			}
			
			mensajesVO = enviarDAO.obtenerMensajes(mensajesVO.getMsjcodigo());
			cargarListas(request, mensajesVO);
			
			
			request.getSession().setAttribute("mensajesVO",mensajesVO);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_LISTA;
	}
	
	
	public String editarMensaje(HttpServletRequest request, HttpSession session, Login usuVO,FiltroEnviarVO filtro) throws ServletException {
		try {	
			String id = request.getParameter("id");
			MensajesVO mensajesVO = new MensajesVO();
			
			if (GenericValidator.isInt(id)) {
				mensajesVO = enviarDAO.obtenerMensajes(Integer.parseInt(id));
				cargarListas(request, mensajesVO);
				request.getSession().setAttribute("mensajesVO",mensajesVO);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_MENSAJE;
	}
	
	
	public String eliminarMensaje(HttpServletRequest request, HttpSession session, Login usuVO,FiltroEnviarVO filtro) throws ServletException {
		try {	
			String id = request.getParameter("id"); 
			
			if (GenericValidator.isInt(id)) {
				 enviarDAO.eliminarMensajes(Integer.parseInt(id));
				//cargarListas(request, mensajesVO);
				request.setAttribute(ParamsVO.SMS, "información eliminada satisfactoriamente.");
			}
		 } catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, e.getMessage());
		}
		return FICHA_MENSAJE;
	}
	
	
	private void cargarListas(HttpServletRequest request, MensajesVO mensajesVO) throws Exception{
		if(mensajesVO.getMsjenviadoaperfil() != null && mensajesVO.getMsjenviadoaperfil().length() > 0){
			request.setAttribute("listaPerfiles",enviarDAO.obtenerPefiles(mensajesVO.getMsjenviadoaperfil()));
		}
	 	if(mensajesVO.getMsjenviadoalocal() != null && mensajesVO.getMsjenviadoalocal().length() > 0){
			request.setAttribute("listaLocal",enviarDAO.obtenerLocal(mensajesVO.getMsjenviadoalocal()));
		}
	 	
		if(mensajesVO.getMsjenviadoacoleg() != null && mensajesVO.getMsjenviadoacoleg().length() > 0){
			 request.setAttribute("listaInst",enviarDAO.obtenerInst( mensajesVO.getMsjenviadoacoleg()));
		}
		
		if(mensajesVO.getMsjenviadoasede() != null && mensajesVO.getMsjenviadoasede().length() > 0){
			List lista = enviarDAO.obtenerSede( MensajesVO.formatoSedeArray( mensajesVO.getMsjenviadoasede())  ) ;
			System.out.println("datos  " + lista.size() );
			request.setAttribute("listaSedess",lista);
		}
		
		if(mensajesVO.getMsjenviadoajorn() != null && mensajesVO.getMsjenviadoajorn().length() > 0){
			request.setAttribute("listaJorddd",enviarDAO.obtenerJord(mensajesVO.getMsjenviadoajorn()));
		}
	}
	
	
}
