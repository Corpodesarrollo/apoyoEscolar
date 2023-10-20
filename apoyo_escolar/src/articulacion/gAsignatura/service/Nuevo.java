package articulacion.gAsignatura.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.gAsignatura.dao.GAsignaturaDAO;
import articulacion.gAsignatura.vo.AreaVO;
import articulacion.gAsignatura.vo.GAsignaturaVO;
import articulacion.gAsignatura.vo.ParamsVO;
import siges.login.beans.Login;



public class Nuevo extends Service {
	
	private Cursor c;
	private String FICHA_NUEVO;
	private GAsignaturaDAO gAsignaturaDAO;
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		
		Login usuVO=(Login)session.getAttribute("login");
		//**********
		c = new Cursor();
		gAsignaturaDAO=new GAsignaturaDAO(c);
		AreaVO areaVO=(AreaVO)session.getAttribute("FareaVO"); 
		
		GAsignaturaVO gAsignaturaVO=(GAsignaturaVO)session.getAttribute("gAsignaturaVO");
		GAsignaturaVO gAsignaturaVO2=(GAsignaturaVO)session.getAttribute("gAsignaturaVO2");
		GAsignaturaDAO gAsignaturaDAO=new GAsignaturaDAO(c);
		
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");
		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codGAsignatura=request.getParameter("id");
		request.setAttribute("listaAreaVO",gAsignaturaDAO.getListaAreas());
		
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//Guardar*******************************
				if(gAsignaturaVO.getFormaEstado().equals("1")){
					//Actualizar****************************
					if(gAsignaturaDAO.actualizar(gAsignaturaVO,gAsignaturaVO2)){
						request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
						session.removeAttribute("gAsignaturaVO");
						session.removeAttribute("gAsignaturaVO2");
					}else{
						request.setAttribute(Params.SMS,"La No se puede actualizar este Registro "+gAsignaturaDAO.getMensaje());
						session.setAttribute("gAsignaturaVO",gAsignaturaVO);
						session.removeAttribute("gAsignaturaVO2");
					}
				}	
				else{
					if(gAsignaturaDAO.insertar(gAsignaturaVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
						session.removeAttribute("gAsignaturaVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada "+gAsignaturaDAO.getMensaje());
					}
				}
				
			break;
			
			case Params.CMD_EDITAR:
				gAsignaturaVO=gAsignaturaDAO.asignar(codGAsignatura,areaVO.getCodigo());
				
				if(gAsignaturaVO!=null){
					session.setAttribute("gAsignaturaVO",gAsignaturaVO);
					session.setAttribute("gAsignaturaVO2",gAsignaturaVO.clone());
					request.setAttribute("guia",codGAsignatura);
					
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado "+gAsignaturaDAO.getMensaje());
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(/*como hago para ver si el usuario tiene permisos?*/true){
					if(gAsignaturaDAO.eliminar(codGAsignatura)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("gAsignaturaVO");
						session.removeAttribute("gAsignaturaVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada "+gAsignaturaDAO.getMensaje());
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
	case Params.CMD_NUEVO:
		session.removeAttribute("gAsignaturaVO");
		session.removeAttribute("gAsignaturaVO2");
	break;
	}
	dispatcher[0]=String.valueOf(Params.FORWARD);
	dispatcher[1]=FICHA_NUEVO;
	return dispatcher;
	}
}
