/**
 * 
 */
package articulacion.artRotacion.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.artRotacion.dao.ArtRecesoDAO;
import articulacion.artRotacion.dao.ArtRotacionDAO;
import articulacion.artRotacion.vo.EstructuraVO;
import articulacion.artRotacion.vo.FiltroHorarioVO;
import articulacion.artRotacion.vo.FiltroRecesoVO;
import articulacion.artRotacion.vo.HorarioVO;
import articulacion.artRotacion.vo.ParamsVO;
import articulacion.artRotacion.vo.RecesoVO;
import siges.common.service.Service;
import siges.dao.Cursor;
import siges.login.beans.Login;

/**
 * 6/10/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service {

	private String FICHA;

	private String FICHA_ESTRUCTURA;
	private String FICHA_RECESO;
	private String FICHA_ESPACIO_ASIGNATURA;
	private String FICHA_ASIGNATURA_BLOQUE;
	private String FICHA_ESPACIO_DOCENTE;
	private String FICHA_INHABILITAR_ESPACIO;
	private String FICHA_INHABILITAR_DOCENTE;
	private String FICHA_INHABILITAR_HORA;
	private String FICHA_DOCENTE_ASIGNATURA_GRUPO;
	private String FICHA_HORARIO;
	private String FICHA_BORRAR_HORARIO;

	private Cursor cursor;

	private ArtRotacionDAO artRotacionDAO;
	private ArtRecesoDAO artRecesoDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		// try{
		cursor = new Cursor();
		artRotacionDAO = new ArtRotacionDAO(cursor);
		artRecesoDAO = new ArtRecesoDAO(cursor);
		FICHA_ESTRUCTURA = config.getInitParameter("FICHA_ESTRUCTURA");
		FICHA_RECESO = config.getInitParameter("FICHA_RECESO");
		FICHA_HORARIO = config.getInitParameter("FICHA_HORARIO");
		FICHA_BORRAR_HORARIO = config.getInitParameter("FICHA_BORRAR_HORARIO");
		FICHA_ESPACIO_ASIGNATURA=config.getInitParameter("FICHA_ESPACIO_ASIGNATURA");
		FICHA_ASIGNATURA_BLOQUE=config.getInitParameter("FICHA_ASIGNATURA_BLOQUE");
		FICHA_ESPACIO_DOCENTE=config.getInitParameter("FICHA_ESPACIO_DOCENTE");
		FICHA_INHABILITAR_ESPACIO=config.getInitParameter("FICHA_INHABILITAR_ESPACIO");
		FICHA_INHABILITAR_DOCENTE=config.getInitParameter("FICHA_INHABILITAR_DOCENTE");
		FICHA_INHABILITAR_HORA=config.getInitParameter("FICHA_INHABILITAR_HORA");
		FICHA_DOCENTE_ASIGNATURA_GRUPO=config.getInitParameter("FICHA_DOCENTE_ASIGNATURA_GRUPO");
		Login usuVO = (Login) session.getAttribute("login");
		EstructuraVO artRotEstructuraVO = (EstructuraVO) session.getAttribute("artRotEstructuraVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		FiltroHorarioVO filtro = null;
		FiltroRecesoVO filtroRecesoVO = null;
		switch (TIPO) {
		case ParamsVO.FICHA_ESTRUCTURA:
			initParams(request, usuVO);
			FICHA = FICHA_ESTRUCTURA;
			switch (CMD) {
			case ParamsVO.CMD_GUARDAR:
				artRotEstructuraVO.setParInstitucion(Long.parseLong(usuVO.getInstId()));
				if (artRotEstructuraVO.getFormaEstado().equals("1")) {// actualiza
					if (artRotacionDAO.actualizar(artRotEstructuraVO, artRotEstructuraVO)) {
						request.setAttribute(ParamsVO.SMS, "La información fue actualizada satisfactoriamente");
						session.removeAttribute("artRotEstructuraVO");
					} else {
						request.setAttribute(ParamsVO.SMS, "La información no fue actualizada: " + artRotacionDAO.getMensaje());
					}
				} else {// guarda
					if (artRotacionDAO.insertar(artRotEstructuraVO)) {
						request.setAttribute(ParamsVO.SMS, "La información fue ingresada satisfactoriamente");
						session.removeAttribute("artRotEstructuraVO");
					} else {
						request.setAttribute(ParamsVO.SMS, "La información no fue ingresada: " + artRotacionDAO.getMensaje());
					}
				}
				break;
			case ParamsVO.CMD_EDITAR:
				artRotEstructuraVO = new EstructuraVO();
				artRotEstructuraVO.setParInstitucion(Long.parseLong(getRequest(request, "id")));
				artRotEstructuraVO.setParSede(Integer.parseInt(getRequest(request, "id2")));
				artRotEstructuraVO.setParJornada(Integer.parseInt(getRequest(request, "id3")));
				artRotEstructuraVO.setParComponente(Integer.parseInt(getRequest(request, "id4")));
				artRotEstructuraVO.setParAnhoVigencia(Integer.parseInt(getRequest(request, "id5")));
				artRotEstructuraVO.setParPerVigencia(Integer.parseInt(getRequest(request, "id6")));
				artRotEstructuraVO = artRotacionDAO.getParametroVO(artRotEstructuraVO);
				if (artRotEstructuraVO != null) {
					session.setAttribute("artRotEstructuraVO", artRotEstructuraVO);
					request.setAttribute("guia", getRequest(request, "id"));
					request.setAttribute("guia2", getRequest(request, "id2"));
					request.setAttribute("guia3", getRequest(request, "id3"));
					request.setAttribute("guia4", getRequest(request, "id4"));
					request.setAttribute("guia5", getRequest(request, "id5"));
					request.setAttribute("guia6", getRequest(request, "id6"));
				} else {
					request.setAttribute(ParamsVO.SMS, "No se logrn obtener los datos " + artRotacionDAO.getMensaje());
				}
				break;
			case ParamsVO.CMD_ELIMINAR:
				artRotEstructuraVO = new EstructuraVO();
				artRotEstructuraVO.setParInstitucion(Long.parseLong(getRequest(request, "id")));
				artRotEstructuraVO.setParSede(Integer.parseInt(getRequest(request, "id2")));
				artRotEstructuraVO.setParJornada(Integer.parseInt(getRequest(request, "id3")));
				artRotEstructuraVO.setParComponente(Integer.parseInt(getRequest(request, "id4")));
				artRotEstructuraVO.setParAnhoVigencia(Integer.parseInt(getRequest(request, "id5")));
				artRotEstructuraVO.setParPerVigencia(Integer.parseInt(getRequest(request, "id6")));
				if (artRotacionDAO.eliminar(artRotEstructuraVO)) {
					request.setAttribute(ParamsVO.SMS, "La información fue eliminada satisfactoriamente");
					session.removeAttribute("artRotEstructuraVO");
				} else {
					request.setAttribute(ParamsVO.SMS, "La información no fue eliminada: " + artRotacionDAO.getMensaje());
				}
				break;
			case ParamsVO.CMD_NUEVO:
				try {
					artRotEstructuraVO = new EstructuraVO();
					artRotEstructuraVO.setParAnhoVigencia((int) artRotacionDAO.getVigenciaNumerico());
					artRotEstructuraVO.setParPerVigencia((int) artRotacionDAO.getPeriodoNumerico());
					request.getSession().setAttribute("artRotEstructuraVO", artRotEstructuraVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO:
			FICHA = FICHA_HORARIO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("artRotHorarioVO");
				break;
			case ParamsVO.CMD_BUSCAR:
				filtro = (FiltroHorarioVO) session.getAttribute("artRotFiltroHorarioVO");
				if (!artRotacionDAO.validarHorario(filtro)) {
					request.setAttribute(ParamsVO.SMS, "No se puede generar la plantilla de horario, falta configurar los parnmetros de la sede - jornada");
					break;
				}
				HorarioVO horarioVO = artRotacionDAO.getHorario(filtro);
				session.setAttribute("artRotHorarioVO", horarioVO);
				filtro.setFormaEstado("1");
				break;
			case ParamsVO.CMD_GUARDAR:
				String[] a = request.getParameterValues("checkHor");
				// if(a!=null){
				// for(int i=0;i<a.length;i++){
				// System.out.println("VALOR CHECK "+a[i]);
				// }
				// }
				String[] b = request.getParameterValues("checkEsp");
				// if(b!=null){
				// for(int i=0;i<b.length;i++){
				// System.out.println("VALOR ESP"+b[i]);
				// }
				// }
				filtro = (FiltroHorarioVO) session.getAttribute("artRotFiltroHorarioVO");
				if (!artRotacionDAO.guardarHorario(filtro, a, b)) {
					request.setAttribute(ParamsVO.SMS, "Error guardando horario: " + artRotacionDAO.getMensaje());
				} else {
					request.setAttribute(ParamsVO.SMS, "Horario guardado satisfactoriamente");
					session.removeAttribute("artRotHorarioVO");
				}
				break;
			}
			break;
		case ParamsVO.FICHA_BORRAR_HORARIO:
			FICHA = FICHA_BORRAR_HORARIO;
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				break;
			case ParamsVO.CMD_ELIMINAR:
				filtro = (FiltroHorarioVO) session.getAttribute("artRotFiltroHorarioVO");
				borrarHorarioEliminar(request, usuVO, filtro);
				break;
			}
			break;
		case ParamsVO.FICHA_RECESO:
			FICHA = FICHA_RECESO;
			filtroRecesoVO = (FiltroRecesoVO) session.getAttribute("artRotFiltroRecesoVO");
			recesoNuevo(request,session,filtroRecesoVO);
			switch (CMD) {
				case ParamsVO.CMD_NUEVO:
				break;
				case ParamsVO.CMD_BUSCAR:
					recesoBuscar(request,session,filtroRecesoVO);
					break;
				case ParamsVO.CMD_EDITAR:
					recesoBuscar(request,session,filtroRecesoVO);
					recesoEditar(request,session,filtroRecesoVO);
					break;
				case ParamsVO.CMD_ELIMINAR:
					recesoEliminar(request,session,filtroRecesoVO);
					recesoBuscar(request,session,filtroRecesoVO);
					break;
				case ParamsVO.CMD_GUARDAR:
					recesoGuardar(request,session,filtroRecesoVO);
					recesoBuscar(request,session,filtroRecesoVO);
					break;
			}
			break;
		}
		// }catch(Exception e){e.printStackTrace();}
		dispatcher[0] = String.valueOf(ParamsVO.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void recesoNuevo(HttpServletRequest request, HttpSession session, FiltroRecesoVO filtro) throws ServletException {
		try {
			request.setAttribute("lTipoReceso", artRecesoDAO.getAllTipoReceso());
			if(filtro!=null && filtro.getFilEstructura()!=0){
				request.setAttribute("lEstructuraVO",artRotacionDAO.getAjaxEstructura(filtro.getFilInstitucion(),filtro.getFilSede(),filtro.getFilJornada(),filtro.getFilAnhoVigencia(),filtro.getFilPerVigencia()));
				request.setAttribute("lClase", artRecesoDAO.getAllClase(filtro.getFilEstructura()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void recesoBuscar(HttpServletRequest request, HttpSession session, FiltroRecesoVO filtro) throws ServletException {
		try {
			request.setAttribute("listaRecesoVO", artRecesoDAO.buscarReceso(filtro));
			session.removeAttribute("artRotRecesoVO");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void recesoEditar(HttpServletRequest request, HttpSession session, FiltroRecesoVO filtro) throws ServletException {
		try {
			long est=Long.parseLong(request.getParameter("id"));
			int cod=Integer.parseInt(request.getParameter("id2"));
			session.setAttribute("artRotRecesoVO", artRecesoDAO.editarReceso(est,cod));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void recesoEliminar(HttpServletRequest request, HttpSession session, FiltroRecesoVO filtro) throws ServletException {
		try {
			long est=Long.parseLong(request.getParameter("id"));
			int cod=Integer.parseInt(request.getParameter("id2"));
			artRecesoDAO.eliminarReceso(est,cod);
			session.removeAttribute("artRotRecesoVO");
			request.setAttribute(ParamsVO.SMS, "Receso eliminado satisfactoriamente");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "El receso no se pudo eliminar: "+e.getMessage());
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	private void recesoGuardar(HttpServletRequest request, HttpSession session, FiltroRecesoVO filtro) throws ServletException {
		try {
			RecesoVO recesoVO=(RecesoVO)session.getAttribute("artRotRecesoVO");
			if(recesoVO.getFormaEstado().equals("1")){
				artRecesoDAO.actualizarReceso(recesoVO);
			}else{
				artRecesoDAO.ingresarReceso(recesoVO);
			}
			request.setAttribute(ParamsVO.SMS, "Receso guardado satisfactoriamente");
			session.removeAttribute("artRotRecesoVO");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(ParamsVO.SMS, "No se pudo registrar el receso: "+e.getMessage());
		}
	}
	
	private void borrarHorarioEliminar(HttpServletRequest request, Login usuVO, FiltroHorarioVO filtro) throws ServletException {
//		System.out.println("Borrar horario: " + filtro.getFilAsignatura());
		try {
			if (!artRotacionDAO.eliminarHorario(filtro)) {
				request.setAttribute(ParamsVO.SMS, "Error eliminando horario: " + artRotacionDAO.getMensaje());
			} else {
				request.setAttribute(ParamsVO.SMS, "Horario eliminado satisfactoriamente");
				request.getSession().removeAttribute("artRotHorarioVO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	private void initParams(HttpServletRequest request, Login usuVO) throws ServletException {
		try{
			long inst = Long.parseLong(usuVO.getInstId());
			int sede = -99;
			int jor = -99;
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// System.out.println("valors:"+inst+"//"+sede+"//"+jor);
			request.setAttribute("lSedeVO", artRotacionDAO.getSede(inst, sede));
			request.setAttribute("lJornadaVO", artRotacionDAO.getJornada(inst, sede, jor));
			int vig = (int) artRotacionDAO.getVigenciaNumerico();
			// ano vigencia
			List l = new ArrayList();
			// periodos de vigencia
			l.add("1");l.add("2");
			request.setAttribute("lPeriodoVO",l);
			request.setAttribute("listaVigencia",artRotacionDAO.getListaVigencia());
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException("Errror nterno: "+e.getMessage());
		}
	}
}
