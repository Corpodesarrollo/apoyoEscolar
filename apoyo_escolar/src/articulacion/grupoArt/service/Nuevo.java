package articulacion.grupoArt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.grupoArt.dao.GrupoArtDAO;
import articulacion.grupoArt.vo.DatosVO;
import articulacion.grupoArt.vo.GrupoArtVO;
import articulacion.grupoArt.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;


public class Nuevo extends Service {
	
	private Cursor c;
	private String FICHA_NUEVO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		c = new Cursor();
		//***Administracion de grupo
		GrupoArtVO grupoVO=(GrupoArtVO)session.getAttribute("grupoVO");
		GrupoArtVO grupoVO2=(GrupoArtVO)session.getAttribute("grupoVO2");
		DatosVO datosVO =(DatosVO)session.getAttribute("filtroGrupoVO");
		GrupoArtDAO grupoDAO=new GrupoArtDAO(c);
		
		//****************************************************************
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");

		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codGrupo=request.getParameter("id");
		request.setAttribute("listaAsignVO",grupoDAO.getListaAsignatura(datosVO));
		request.setAttribute("listaInscVO",grupoDAO.getAsigIncritas(codGrupo));
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//actualizacion******************************************************
				if(grupoVO.getFormaEstado().equals("1")){
					if(grupoVO.getArtGruCodigo()==grupoVO2.getArtGruCodigo()){
			/*			System.out.println("general="+grupoVO.getArtGruCupoGeneral());
						System.out.println("nivelados="+grupoVO.getArtGruCupoNivel());
						System.out.println("no nivelados="+grupoVO.getArtGruCupoNoNivel());*/
						if(grupoDAO.actualizar(grupoVO,grupoVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("grupoVO");
							session.removeAttribute("grupoVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada "+grupoDAO.getMensaje());
							session.setAttribute("grupoVO",grupoVO);
							session.removeAttribute("grupoVO2");
						}
					}
				}
				//********insercion****
				else{
					if(grupoDAO.insertar(grupoVO)){
						request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
						session.removeAttribute("grupoVO");
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada "+grupoDAO.getMensaje());
					}
				}
			break;
			case Params.CMD_EDITAR:
				grupoVO=grupoDAO.asignar(codGrupo);
				if(grupoVO!=null){
					session.setAttribute("grupoVO",grupoVO);
					session.setAttribute("grupoVO2",grupoVO.clone());
					request.setAttribute("guia",codGrupo);
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(/*como hago para ver si el usuario tiene permisos?*/true){
					if(grupoDAO.eliminar(codGrupo)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						//session.removeAttribute("datosVO");
						session.removeAttribute("grupoVO");
						session.removeAttribute("grupoVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada "+grupoDAO.getMensaje());
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
			case Params.CMD_NUEVO:
				session.removeAttribute("grupoVO");
				session.removeAttribute("grupoVO2");
			break;
		}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA_NUEVO;
		
		return dispatcher;
	}
}
