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
import siges.recuperacion.vo.FiltroRecuperacionVO;
import siges.recuperacion.vo.ParamsVO;
import siges.recuperacion.vo.RecuperacionVO;

/**
 * 12/02/2009 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service{
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
		FiltroRecuperacionVO filtro=(FiltroRecuperacionVO)session.getAttribute("filtroRecuperacionVO");
		RecuperacionVO recuperacion=(RecuperacionVO)session.getAttribute("recuperacionVO");
		switch (TIPO){
			case ParamsVO.FICHA_RECUPERACION_AREA:
				FICHA=FICHA_RECUPERACION_AREA;
				switch (CMD){
					case ParamsVO.CMD_BUSCAR:
						recuperacionAreaBuscar(request, session, usuVO,filtro);
						break;
					case ParamsVO.CMD_GUARDAR:
						recuperacionAreaGuardar(request, session, usuVO,filtro,recuperacion);
						//recuperacionAreaBuscar(request, session, usuVO,filtro);
						break;
					case ParamsVO.CMD_CANCELAR:
						recuperacionAreaCancelar(request, session, usuVO,filtro);
						break;
				}
				recuperacionAreaInit(request, session, usuVO,filtro);
			break;
			case ParamsVO.FICHA_RECUPERACION_ASIGNATURA:
				FICHA=FICHA_RECUPERACION_ASIG;
				switch (CMD){
					case ParamsVO.CMD_BUSCAR:
						recuperacionAsigBuscar(request, session, usuVO,filtro);
						break;
					case ParamsVO.CMD_GUARDAR:
						recuperacionAsigGuardar(request, session, usuVO,filtro,recuperacion);
						//recuperacionAsigBuscar(request, session, usuVO,filtro);
						break;
					case ParamsVO.CMD_CANCELAR:
						recuperacionAsigCancelar(request, session, usuVO,filtro);
						break;
				}
				recuperacionAsigInit(request, session, usuVO,filtro);
			break;
		}
		dispatcher[0]=String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	public void recuperacionAreaInit(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		try {
			if(filtro==null){
				filtro=new FiltroRecuperacionVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				if(!usuVO.getSedeId().equals("")) filtro.setFilSede(Integer.parseInt(usuVO.getSedeId()));
				if(!usuVO.getJornadaId().equals("")) filtro.setFilJornada(Integer.parseInt(usuVO.getJornadaId()));
				if(!usuVO.getMetodologiaId().equals("")) filtro.setFilMetodologia(Integer.parseInt(usuVO.getMetodologiaId()));
				session.setAttribute("filtroRecuperacionVO",filtro);
			}
			request.setAttribute("listaMetodologia", recuperacionDAO.getListaMetodologia(filtro.getFilInstitucion()));
			request.setAttribute("listaOrden", recuperacionDAO.getListaOrden());
			request.setAttribute("listaVigencia", recuperacionDAO.getListaVigencias());
			if(filtro.getFilVigencia()>0){
				request.setAttribute("listaGrado", recuperacionDAO.getListaGrado(filtro.getFilInstitucion(),filtro.getFilMetodologia()));
			}
			if(filtro.getFilGrado()>0){
				request.setAttribute("listaGrupo", recuperacionDAO.getListaGrupo(filtro.getFilInstitucion(),filtro.getFilSede(),filtro.getFilJornada(),filtro.getFilMetodologia(),filtro.getFilGrado()));
				request.setAttribute("listaArea", recuperacionDAO.getListaArea(filtro.getFilInstitucion(),filtro.getFilMetodologia(),filtro.getFilGrado(),filtro.getFilVigencia()));
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void recuperacionAreaBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaRecuperacion", recuperacionDAO.getListaRecuperacionArea(filtro));
			request.setAttribute("listaNota", recuperacionDAO.getListaNota());
			request.setAttribute("recuperacionBuscar","1");
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void recuperacionAreaCancelar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		session.removeAttribute("filtroRecuperacionVO");
		session.removeAttribute("recuperacionVO");
	}
	
	public void recuperacionAsigCancelar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		session.removeAttribute("filtroRecuperacionVO");
		session.removeAttribute("recuperacionVO");
	}
	
	public void recuperacionAreaGuardar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro,RecuperacionVO recuperacion) throws ServletException {
		try {
			recuperacionDAO.guardarRecuperacionArea(filtro,recuperacion);
			request.setAttribute(ParamsVO.SMS, "La recuperacinn fue ingresada satisfactoriamente");
			request.setAttribute("recuperacionBuscar","0");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La recuperacinn no fue ingresada: "+e.getMessage());
		}
	}

	public void recuperacionAsigInit(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		try {
			if(filtro==null){
				filtro=new FiltroRecuperacionVO();
				filtro.setFilInstitucion(Long.parseLong(usuVO.getInstId()));
				if(!usuVO.getSedeId().equals("")) filtro.setFilSede(Integer.parseInt(usuVO.getSedeId()));
				if(!usuVO.getJornadaId().equals("")) filtro.setFilJornada(Integer.parseInt(usuVO.getJornadaId()));
				if(!usuVO.getMetodologiaId().equals("")) filtro.setFilMetodologia(Integer.parseInt(usuVO.getMetodologiaId()));
				session.setAttribute("filtroRecuperacionVO",filtro);
			}
			request.setAttribute("listaMetodologia", recuperacionDAO.getListaMetodologia(filtro.getFilInstitucion()));
			request.setAttribute("listaOrden", recuperacionDAO.getListaOrden());
			request.setAttribute("listaVigencia", recuperacionDAO.getListaVigencias());
			if(filtro.getFilVigencia()>0){
				request.setAttribute("listaGrado", recuperacionDAO.getListaGrado(filtro.getFilInstitucion(),filtro.getFilMetodologia()));
			}
			if(filtro.getFilGrado()>0){
				request.setAttribute("listaGrupo", recuperacionDAO.getListaGrupo(filtro.getFilInstitucion(),filtro.getFilSede(),filtro.getFilJornada(),filtro.getFilMetodologia(),filtro.getFilGrado()));
				//System.out.println("calcula lo de asig::"+filtro.getFilInstitucion()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilVigencia());
				request.setAttribute("listaAsignatura", recuperacionDAO.getListaAsignatura(filtro.getFilInstitucion(),filtro.getFilMetodologia(),filtro.getFilGrado(),filtro.getFilVigencia()));
			}
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	public void recuperacionAsigBuscar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro) throws ServletException {
		try {
			request.setAttribute("listaRecuperacion", recuperacionDAO.getListaRecuperacionAsignatura(filtro));
			request.setAttribute("listaNota", recuperacionDAO.getListaNota());
			request.setAttribute("recuperacionBuscar","1");
		} catch (Exception e) {
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	public void recuperacionAsigGuardar(HttpServletRequest request, HttpSession session, Login usuVO,FiltroRecuperacionVO filtro,RecuperacionVO recuperacion) throws ServletException {
		try {
			recuperacionDAO.guardarRecuperacionAsignatura(filtro,recuperacion);
			request.setAttribute(ParamsVO.SMS, "La recuperacinn fue ingresada satisfactoriamente");
			request.setAttribute("recuperacionBuscar","0");
		} catch (Exception e) {
			request.setAttribute(ParamsVO.SMS, "La recuperacinn no fue ingresada: "+e.getMessage());
		}
	}
}
