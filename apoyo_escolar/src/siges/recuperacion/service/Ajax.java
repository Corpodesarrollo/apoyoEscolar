/**
 * 
 */
package siges.recuperacion.service;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.recuperacion.dao.RecuperacionDAO;
import siges.recuperacion.vo.ParamsVO;

/**
 * 12/02/2009 
 * @author Latined
 * @version 1.2
 */
public class Ajax extends Service{
	private String FICHA_RECUPERACION_AREA;
	private String FICHA_RECUPERACION_ASIG;
	private RecuperacionDAO recuperacionDAO=new RecuperacionDAO(new Cursor());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FICHA_RECUPERACION_AREA= config.getInitParameter("FICHA_RECUPERACION_AREA");
		FICHA_RECUPERACION_ASIG= config.getInitParameter("FICHA_RECUPERACION_ASIG");
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String FICHA=null;
		HttpSession session = request.getSession();
		String dispatcher[]=new String[2];
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		switch (TIPO){
			case ParamsVO.FICHA_RECUPERACION_AREA:
				FICHA=FICHA_RECUPERACION_AREA;
				switch (CMD){
					case ParamsVO.CMD_AJAX_GRADO:
						recuperacionAreaGrado(request, session, usuVO,CMD);
						break;
					case ParamsVO.CMD_AJAX_GRUPO:
						recuperacionAreaGrupo(request, session, usuVO,CMD);
						break;
				}
			break;
			case ParamsVO.FICHA_RECUPERACION_ASIGNATURA:
				FICHA=FICHA_RECUPERACION_ASIG;
				switch (CMD){
					case ParamsVO.CMD_AJAX_GRADO:
						recuperacionAsigGrado(request, session, usuVO,CMD);
						break;
					case ParamsVO.CMD_AJAX_GRUPO:
						recuperacionAsigGrupo(request, session, usuVO,CMD);
						break;
				}
			break;
		}
		dispatcher[0]=String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	public void recuperacionAreaGrado(HttpServletRequest request, HttpSession session, Login usuVO,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<2 || params[0]==null || params[1]==null) return;
			request.setAttribute("listaGrado", recuperacionDAO.getListaGrado(Long.parseLong(params[0]),Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void recuperacionAsigGrado(HttpServletRequest request, HttpSession session, Login usuVO,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<2 || params[0]==null || params[1]==null) return;
			request.setAttribute("listaGrado", recuperacionDAO.getListaGrado(Long.parseLong(params[0]),Integer.parseInt(params[1])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void recuperacionAreaGrupo(HttpServletRequest request, HttpSession session, Login usuVO,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null) return;
			request.setAttribute("listaGrupo", recuperacionDAO.getListaGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4])));
			request.setAttribute("listaArea", recuperacionDAO.getListaArea(Long.parseLong(params[0]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void recuperacionAsigGrupo(HttpServletRequest request, HttpSession session, Login usuVO,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<6 || params[0]==null || params[1]==null || params[2]==null || params[3]==null || params[4]==null || params[5]==null) return;
			request.setAttribute("listaGrupo", recuperacionDAO.getListaGrupo(Long.parseLong(params[0]),Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4])));
			request.setAttribute("listaAsignatura", recuperacionDAO.getListaAsignatura(Long.parseLong(params[0]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}
