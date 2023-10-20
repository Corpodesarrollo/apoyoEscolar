package articulacion.asigAcademica.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asigAcademica.dao.AsignacionDAO;
import articulacion.asigAcademica.vo.AsignacionVO;
import articulacion.asigAcademica.vo.ComponenteVO;
import articulacion.asigAcademica.vo.DatosVO;
import articulacion.asignatura.dao.AsignaturaDAO;
import articulacion.asignatura.dao.FiltroDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asigAcademica.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.grupoArt.dao.FiltroGrupoDAO;
import siges.login.beans.Login;

public class Nuevo extends Service {

	private Cursor c;

	private String FICHA = null;

	private String FICHA_ASIGNACION;

	private AsignacionDAO asignacionDAO;

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		String params[] = null;
		HttpSession session = request.getSession();
		c = new Cursor();
		asignacionDAO = new AsignacionDAO(c);
		// ***Administracion de asignatura
		AsignacionVO asignacionVO = (AsignacionVO) session.getAttribute("asignacionVO");
		if (asignacionVO != null){
			asignacionVO.setAsigComponente(2);
		}
		DatosVO filtro = (DatosVO) request.getSession().getAttribute("filAsigAcaVO");
		AsignacionDAO asignacionDAO = new AsignacionDAO(c);
		FICHA_ASIGNACION = config.getInitParameter("FICHA_ASIGNACION");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		Login usuVO = (Login) session.getAttribute("login");
		FICHA = FICHA_ASIGNACION;
		switch (CMD) {
		case Params.CMD_BUSCAR:
			buscar(request, filtro);
			initAsignacion(request, usuVO, filtro, asignacionVO);
			request.setAttribute("lEspecialidadVO",asignacionDAO.getAjaxEspecialidad(2));
			break;
		case Params.CMD_GUARDAR:
			if (asignacionVO.getFormaEstado().equals("1")) {// actualizar
				if (asignacionDAO.actualizar(asignacionVO, filtro)) {
					request.setAttribute(Params.SMS, "La información fue actualizada satisfactoriamente");
					session.removeAttribute("asignacionVO");
				} else {
					request.setAttribute(Params.SMS, "La información no fue actualizada: " + asignacionDAO.getMensaje());
				}
			} else {// ingresar
				if (!asignacionDAO.validar(asignacionVO)) {
					request.setAttribute(Params.SMS, "NO se puede ingresar la información, ya existe esta asignacinn");
				} else {
					if (asignacionDAO.ingresar(asignacionVO, filtro)) {
						request.setAttribute(Params.SMS, "La información fue ingresada satisfactoriamente");
						session.removeAttribute("asignacionVO");
					} else {
						request.setAttribute(Params.SMS, "La información no fue actualizada: " + asignacionDAO.getMensaje());
					}
				}
			}
			buscar(request, filtro);
			initAsignacion(request, usuVO, filtro, asignacionVO);
			break;
		case Params.CMD_EDITAR:
			buscar(request, filtro);
			params = request.getParameterValues("id");
			if (params == null || params[0] == null || params[1] == null || params[2] == null) {
				break;
			}
			asignacionVO = asignacionDAO.getAsignacion(Long.parseLong(params[0]), Long.parseLong(params[1]), Long.parseLong(params[2]), filtro.getInstitucion(), filtro.getSede(), filtro.getJornada(),filtro);
			if (asignacionVO != null) {
				session.setAttribute("asignacionVO", asignacionVO);
				request.setAttribute("guia", params[0]);
				request.setAttribute("guia2", params[1]);
				request.setAttribute("guia3", params[2]);
			} else {
				request.setAttribute(Params.SMS, "No se pudo obtener la asignacinn: " + asignacionDAO.getMensaje());
			}
			initAsignacion(request, usuVO, filtro, asignacionVO);
			break;
		case Params.CMD_ELIMINAR:
			params = request.getParameterValues("id");
			if (params == null || params[0] == null || params[1] == null || params[2] == null) {
				break;
			}
			if (asignacionDAO.eliminar(Long.parseLong(params[0]), Long.parseLong(params[1]), Long.parseLong(params[2]))) {
				request.setAttribute(Params.SMS, "La información fue eliminada satisfactoriamente");
				session.removeAttribute("asignacionVO");
			}else{
				request.setAttribute(Params.SMS, "La información no fue eliminada");
			}
			buscar(request, filtro);
			initAsignacion(request, usuVO, filtro, asignacionVO);
			break;
		case Params.CMD_NUEVO:
			session.removeAttribute("asignacionVO");
			request.setAttribute("lEspecialidadVO",asignacionDAO.getAjaxEspecialidad(2));
			buscar(request, filtro);
			initAsignacion(request, usuVO, filtro, asignacionVO);
			break;
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void buscar(HttpServletRequest request, DatosVO filtro) {
		if(filtro!=null){
			List l = asignacionDAO.getAllAsignacion(filtro);
			//if (l != null && l.size() > 0) {
			if (l != null) {
				filtro.setBuscado(true);
				filtro.setFormaEstado("1");
				request.setAttribute("lAsignacionVO", l);
			}
		}	
	}

	private void initAsignacion(HttpServletRequest request, Login usuVO, DatosVO filtro, AsignacionVO asignacionVO) {
		try {
			if (asignacionVO != null/* && asignacionVO.getFormaEstado().equals("1")*/) {
				request.setAttribute("lEspecialidadVO", asignacionDAO.getAjaxEspecialidad(filtro.getInstitucion()));
				request.setAttribute("lAsignaturaVO", asignacionDAO.getAjaxAsignatura(filtro.getInstitucion(), filtro.getAnho(), filtro.getPeriodo(), asignacionVO.getAsigComponente(), asignacionVO.getAsigEspecialidad(), asignacionVO.getAsigSemestre()));
			} else {
				request.getSession().removeAttribute("asignacionVO");
			}
			// componente
			List l = new ArrayList();
			ComponenteVO comp = new ComponenteVO(1, "Acadnmico");
			l.add(comp);
			comp = new ComponenteVO(2, "Tncnico");
			l.add(comp);
			request.setAttribute("lComponenteVO", l);
			// semestre
			l = new ArrayList();
			l.add("1");
			l.add("2");
			l.add("3");
			l.add("4");
			request.setAttribute("lSemestreVO", l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
