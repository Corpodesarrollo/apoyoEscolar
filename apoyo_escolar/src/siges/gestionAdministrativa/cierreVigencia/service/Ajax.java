/**
 * 
 */
package siges.gestionAdministrativa.cierreVigencia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.acta.dao.ActaDAO;
import participacion.acta.vo.ParamsVO;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.cierreVigencia.dao.CierreVigenciaDAO;
import siges.gestionAdministrativa.cierreVigencia.vo.ItemVO;
import siges.login.beans.Login;

/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Ajax extends Service{

	/**
	 * 
	 */
	public String FICHA_ACTA;
	/**
	 * 
	 */
	private ActaDAO actaDAO=new ActaDAO(new Cursor());
	public String FICHA_CONSULTAR_CIERRE;
	private CierreVigenciaDAO cierreVigenciaDAO;
	private Login usuVO;

	/*
	 * @function: 
	 * @param config
	 * @throws ServletException
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cierreVigenciaDAO = new CierreVigenciaDAO(new Cursor());
		FICHA_ACTA=config.getInitParameter("FICHA_ACTA");
		FICHA_CONSULTAR_CIERRE = config.getInitParameter("FICHA_CONSULTAR_CIERRE");
	}
	
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		String FICHA=null;
		HttpSession session = request.getSession();
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		usuVO = (Login) session.getAttribute("login");
		System.out.println("entro al segundo "+TIPO);
		switch (TIPO){
			case ParamsVO.FICHA_ACTA:
				FICHA=FICHA_ACTA;
				switch (CMD){
					case ParamsVO.CMD_AJAX_INSTANCIA: case ParamsVO.CMD_AJAX_INSTANCIA0:
						actaInstancia(request, session,CMD);	
					break;	
					case ParamsVO.CMD_AJAX_RANGO: case ParamsVO.CMD_AJAX_RANGO0:
						actaRango(request, session,CMD);	
					break;	
					case ParamsVO.CMD_AJAX_COLEGIO: case ParamsVO.CMD_AJAX_COLEGIO0:
						actaColegio(request, session,CMD);	
					break;
					case ParamsVO.CMD_AJAX_PARTICIPANTE:
						actaParticipante(request, session,CMD);	
					break;
				}
			break;
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}

	public void actaInstancia(HttpServletRequest request, HttpSession session,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaInstanciaVO", actaDAO.getListaInstancia(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void actaRango(HttpServletRequest request, HttpSession session,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaRangoVO", actaDAO.getListaRango(Integer.parseInt(params[0])));
			request.setAttribute("listaRolVO", actaDAO.getListaRol(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void actaColegio(HttpServletRequest request, HttpSession session,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<1 || params[0]==null) return;
			request.setAttribute("listaColegioVO", actaDAO.getListaColegio(Integer.parseInt(params[0])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void actaParticipante(HttpServletRequest request, HttpSession session,int cmd) throws ServletException {
		try {
			String params[]=request.getParameterValues("ajax");
			if(params==null || params.length<3 || params[0]==null || params[1]==null || params[2]==null) return;
			request.setAttribute("listaParticipanteVO", actaDAO.getListaParticipante(Integer.parseInt(params[0]),Long.parseLong(params[1]),Integer.parseInt(params[2])));
			request.setAttribute("ajaxParam",String.valueOf(cmd));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
}

