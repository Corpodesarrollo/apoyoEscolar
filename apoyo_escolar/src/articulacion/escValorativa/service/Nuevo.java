package articulacion.escValorativa.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.escValorativa.dao.EscValorativaDAO;
import articulacion.escValorativa.vo.DatosVO;
import articulacion.escValorativa.vo.EscValorativaVO;
import articulacion.escValorativa.vo.ParamsVO;


public class Nuevo extends Service {
	
	private Cursor c;
	private String FICHA_NUEVO;
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		HttpSession session = request.getSession();
		c = new Cursor();
		//***Administracion de grupo
		EscValorativaVO escValorativaVO=(EscValorativaVO)session.getAttribute("escValorativaVO");
		EscValorativaVO escValorativaVO2=(EscValorativaVO)session.getAttribute("escValorativaVO2");
		EscValorativaDAO escValorativaDAO=new EscValorativaDAO(c);
		DatosVO datosVO=(DatosVO)session.getAttribute("filtroEscalasVO");
		//****************************************************************
		FICHA_NUEVO=config.getInitParameter("FICHA_NUEVO");

		int CMD=getCmd(request,session,Params.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		String codEscala=request.getParameter("id");
		request.setAttribute("listaAbreviaturaVO",escValorativaDAO.getListaAbreviatura());
		
		switch (CMD){			
			case Params.CMD_GUARDAR:
				//actualizacion******************************************************
				if(escValorativaVO.getFormaEstado().equals("1")){
					if(escValorativaDAO.validarRangos(datosVO,escValorativaVO,0)){
						if(escValorativaDAO.actualizar(escValorativaVO,escValorativaVO2)){
							request.setAttribute(Params.SMS,"La información fue actualizada satisfactoriamente");
							session.removeAttribute("escValorativaVO");
							session.removeAttribute("escValorativaVO2");
						}else{
							request.setAttribute(Params.SMS,"La información no fue actualizada "+escValorativaDAO.getMensaje());
							session.setAttribute("escValorativaVO",escValorativaVO);
							session.removeAttribute("escValorativaVO2");
						}
					}else{
						request.setAttribute(Params.SMS,"La información no fue actualizada: "+escValorativaDAO.getMensaje());
					}
				}
				//********insercion****
				else{
					if(escValorativaDAO.validarRangos(datosVO,escValorativaVO,1)){
						if(escValorativaDAO.insertar(escValorativaVO)){
							request.setAttribute(Params.SMS,"La información fue ingresada satisfactoriamente");
							session.removeAttribute("escValorativaVO");
						}else{
							request.setAttribute(Params.SMS,"La información no fue ingresada "+escValorativaDAO.getMensaje());
						}
					}else{
						request.setAttribute(Params.SMS,"La información no fue ingresada: "+escValorativaDAO.getMensaje());
					}
				}
			break;
			case Params.CMD_EDITAR:
				escValorativaVO=escValorativaDAO.asignar(datosVO.getInstitucion(),
						datosVO.getMetodologia(),datosVO.getAnVigencia(),datosVO.getPerVigencia(),codEscala);
				if(escValorativaVO!=null){
					session.setAttribute("escValorativaVO",escValorativaVO);
					session.setAttribute("escValorativaVO2",escValorativaVO.clone());
					request.setAttribute("guia",codEscala);
				}else{
					request.setAttribute(Params.SMS,"No se logro obtener los datos del objeto seleccionado");
				}
			break;
			case Params.CMD_ELIMINAR:
				//*************************************************************
				if(/*como hago para ver si el usuario tiene permisos?*/true){
					if(escValorativaDAO.eliminar(datosVO.getInstitucion(),
							datosVO.getMetodologia(),datosVO.getAnVigencia(),datosVO.getPerVigencia(),codEscala)){
						request.setAttribute(Params.SMS,"La información fue eliminada satisfactoriamente");
						session.removeAttribute("escValorativaVO");
						session.removeAttribute("escValorativaVO2");
					}else{
						request.setAttribute(Params.SMS,"La información no fue eliminada "+escValorativaDAO.getMensaje());
					}
				}else{
					request.setAttribute(Params.SMS,"No tiene los permisos necesarios para realizar esta accinn");
				}
			break;
			case Params.CMD_NUEVO:
				session.removeAttribute("escValorativaVO");
				session.removeAttribute("escValorativaVO2");
			break;
		}
		dispatcher[0]=String.valueOf(Params.FORWARD);
		dispatcher[1]=FICHA_NUEVO;
		
		return dispatcher;
	}
}
