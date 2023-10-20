/**
 * 
 */
package siges.observacion.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.observacion.dao.ObservacionDAO;
import siges.observacion.vo.ObservacionAsignaturaVO;
import siges.observacion.vo.ObservacionEstudianteVO;
import siges.observacion.vo.ObservacionGrupoVO;
import siges.observacion.vo.ObservacionPeriodoVO;
import siges.observacion.vo.ParamsVO;

/**
 * 25/11/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Nuevo extends Service {

	private String FICHA;
	private String FICHA_OBSERVACION_PERIODO;
	private String FICHA_OBSERVACION_GRUPO;
	private String FICHA_OBSERVACION_ASIGNATURA;
	private String FICHA_OBSERVACION_PERIODO2;
	private String FICHA_OBSERVACION_GRUPO2;
	private String FICHA_OBSERVACION_ASIGNATURA2;
	private String FICHA_OBSERVACION_ESTUDIANTE;
	private String FICHA_OBSERVACION_ESTUDIANTE2;

	private ObservacionDAO observacionDAO;

	private Cursor cursor = new Cursor();;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		observacionDAO = new ObservacionDAO(cursor);
		FICHA_OBSERVACION_PERIODO = config
				.getInitParameter("FICHA_OBSERVACION_PERIODO");
		FICHA_OBSERVACION_GRUPO = config
				.getInitParameter("FICHA_OBSERVACION_GRUPO");
		FICHA_OBSERVACION_ASIGNATURA = config
				.getInitParameter("FICHA_OBSERVACION_ASIGNATURA");
		FICHA_OBSERVACION_PERIODO2 = config
				.getInitParameter("FICHA_OBSERVACION_PERIODO2");
		FICHA_OBSERVACION_GRUPO2 = config
				.getInitParameter("FICHA_OBSERVACION_GRUPO2");
		FICHA_OBSERVACION_ASIGNATURA2 = config
				.getInitParameter("FICHA_OBSERVACION_ASIGNATURA2");
		FICHA_OBSERVACION_ESTUDIANTE = config
				.getInitParameter("FICHA_OBSERVACION_ESTUDIANTE");
		FICHA_OBSERVACION_ESTUDIANTE2 = config
				.getInitParameter("FICHA_OBSERVACION_ESTUDIANTE2");

		Login usuVO = (Login) session.getAttribute("login");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);

		ObservacionPeriodoVO observacionPeriodoVO = null;
		ObservacionGrupoVO observacionGrupoVO = null;
		ObservacionAsignaturaVO observacionAsignaturaVO = null;

		ObservacionEstudianteVO observacionEstudianteVO = null;

		FICHA = FICHA_OBSERVACION_PERIODO;
		switch (TIPO) {
		case ParamsVO.FICHA_OBSERVACION_PERIODO:

			observacionPeriodoVO = (ObservacionPeriodoVO) session
					.getAttribute("observacionPeriodoVO");
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("observacionGrupoVO");
				periodoNuevo(request, session, usuVO, observacionPeriodoVO);
				FICHA = FICHA_OBSERVACION_PERIODO;
				break;
			case ParamsVO.CMD_BUSCAR:
				periodoBuscar(request, session, usuVO, observacionPeriodoVO);
				FICHA = FICHA_OBSERVACION_PERIODO2;
				break;
			case ParamsVO.CMD_GUARDAR:
				periodoGuardar(request, session, usuVO, observacionPeriodoVO);
				FICHA = FICHA_OBSERVACION_PERIODO;
				break;
			}
			break;
		case ParamsVO.FICHA_OBSERVACION_GRUPO:
			observacionGrupoVO = (ObservacionGrupoVO) session
					.getAttribute("observacionGrupoVO");
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				session.removeAttribute("observacionPeriodoVO");
				grupoNuevo(request, session, usuVO, observacionGrupoVO);
				FICHA = FICHA_OBSERVACION_GRUPO;
				break;
			case ParamsVO.CMD_BUSCAR:
				grupoBuscar(request, session, usuVO, observacionGrupoVO);
				FICHA = FICHA_OBSERVACION_GRUPO2;
				break;
			case ParamsVO.CMD_GUARDAR:
				grupoGuardar(request, session, usuVO, observacionGrupoVO);
				FICHA = FICHA_OBSERVACION_GRUPO;
				break;
			}
			break;
		case ParamsVO.FICHA_OBSERVACION_ASIGNATURA:
			observacionAsignaturaVO = (ObservacionAsignaturaVO) session
					.getAttribute("observacionAsignaturaVO");
			session.removeAttribute("observacionEstudianteVO");
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				asignaturaNuevo(request, session, usuVO,
						observacionAsignaturaVO);
				FICHA = FICHA_OBSERVACION_ASIGNATURA;
				break;
			case ParamsVO.CMD_BUSCAR:
				asignaturaBuscar(request, session, usuVO,
						observacionAsignaturaVO);
				FICHA = FICHA_OBSERVACION_ASIGNATURA2;
				break;
			case ParamsVO.CMD_GUARDAR:
				asignaturaGuardar(request, session, usuVO,
						observacionAsignaturaVO);
				FICHA = FICHA_OBSERVACION_ASIGNATURA;
				break;
			}
			break;
		case ParamsVO.FICHA_OBSERVACION_ESTUDIANTE:
			session.removeAttribute("observacionAsignaturaVO");
			observacionEstudianteVO = (ObservacionEstudianteVO) session
					.getAttribute("observacionEstudianteVO");
			switch (CMD) {
			case ParamsVO.CMD_NUEVO:
				estudianteNuevo(request, session, usuVO,
						observacionEstudianteVO);
				FICHA = FICHA_OBSERVACION_ESTUDIANTE;
				break;
			case ParamsVO.CMD_BUSCAR:
				estudianteBuscar(request, session, usuVO,
						observacionEstudianteVO);
				FICHA = FICHA_OBSERVACION_ESTUDIANTE2;
				break;
			case ParamsVO.CMD_GUARDAR:
				estudianteGuardar(request, session, usuVO,
						observacionEstudianteVO);
				FICHA = FICHA_OBSERVACION_ESTUDIANTE;
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void periodoNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, ObservacionPeriodoVO observacionPeriodoVO) {
		try {
			// request.setAttribute("listaPeriodo",observacionDAO.getAllPeriodo());
			request.setAttribute(
					"listaPeriodo",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void periodoBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, ObservacionPeriodoVO observacionPeriodoVO) {
		try {
			request.setAttribute("listaObservacionPeriodo", observacionDAO
					.getAllObservacionPeriodo(observacionPeriodoVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void periodoGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionPeriodoVO observacionPeriodoVO) {
		try {
			observacionDAO.guardarObservacionPeriodo(observacionPeriodoVO);
			periodoNuevo(request, session, usuVO, observacionPeriodoVO);
			request.setAttribute(Params.SMS,
					"La información fue registrada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(Params.SMS, "Error registrando información: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void grupoNuevo(HttpServletRequest request, HttpSession session,
			Login usuVO, ObservacionGrupoVO observacionGrupoVO) {
		try {
			long inst = Long.parseLong(usuVO.getInstId());
			request.setAttribute("listaSede", observacionDAO.getSede(inst));
			request.setAttribute("listaJornada",
					observacionDAO.getJornada(inst));
			// request.setAttribute("listaPeriodo",observacionDAO.getAllPeriodo());
			request.setAttribute(
					"listaPeriodo",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			request.setAttribute("listaMetodologia",
					observacionDAO.getMetodologia(inst));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void grupoBuscar(HttpServletRequest request, HttpSession session,
			Login usuVO, ObservacionGrupoVO observacionGrupoVO) {
		try {
			request.setAttribute("listaObservacionGrupo",
					observacionDAO.getAllObservacionGrupo(observacionGrupoVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void grupoGuardar(HttpServletRequest request, HttpSession session,
			Login usuVO, ObservacionGrupoVO observacionGrupoVO) {
		try {
			observacionDAO.guardarObservacionGrupo(observacionGrupoVO);
			grupoNuevo(request, session, usuVO, observacionGrupoVO);
			request.setAttribute(Params.SMS,
					"La información fue registrada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(Params.SMS, "Error registrando información: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void asignaturaNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionAsignaturaVO observacionAsignaturaVO) {
		try {
			long inst = Long.parseLong(usuVO.getInstId());
			request.setAttribute("listaSede", observacionDAO.getSede(inst));
			request.setAttribute("listaJornada",
					observacionDAO.getJornada(inst));
			request.setAttribute("listaMetodologia",
					observacionDAO.getMetodologia(inst));
			// request.setAttribute("listaPeriodo",observacionDAO.getAllPeriodo());
			request.setAttribute(
					"listaPeriodo",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			if (observacionAsignaturaVO != null
					&& observacionAsignaturaVO.getObsMetodologia() > 0) {
				request.setAttribute("listaGrado", observacionDAO.getAllGrado(
						inst, observacionAsignaturaVO.getObsMetodologia(),
						observacionAsignaturaVO.getObsSede(),
						observacionAsignaturaVO.getObsJornada()));
				request.setAttribute("listaGrupo", observacionDAO.getAllGrupo(
						inst, observacionAsignaturaVO.getObsMetodologia(),
						observacionAsignaturaVO.getObsSede(),
						observacionAsignaturaVO.getObsJornada(),
						observacionAsignaturaVO.getObsGrado()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void asignaturaBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionAsignaturaVO observacionAsignaturaVO) {
		try {
			request.setAttribute("listaObservacionAsignatura", observacionDAO
					.getAllObservacionAsignatura(observacionAsignaturaVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void asignaturaGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionAsignaturaVO observacionAsignaturaVO) {
		try {
			observacionDAO
					.guardarObservacionAsignatura(observacionAsignaturaVO);
			asignaturaNuevo(request, session, usuVO, observacionAsignaturaVO);
			request.setAttribute(Params.SMS,
					"La información fue registrada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(Params.SMS, "Error registrando información: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	// Observacinn estudiante
	private void estudianteNuevo(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionEstudianteVO observacionEstudianteVO) {
		try {
			long inst = Long.parseLong(usuVO.getInstId());

			request.setAttribute("listaMetodologia",
					observacionDAO.getMetodologia(inst));
			// request.setAttribute("listaPeriodo",observacionDAO.getAllPeriodo());
			request.setAttribute(
					"listaPeriodo",
					getListaPeriodo(usuVO.getLogNumPer(),
							usuVO.getLogNomPerDef()));
			if (observacionEstudianteVO != null
					&& observacionEstudianteVO.getObsMetodologia() > 0) {
				request.setAttribute("listaGrado", observacionDAO.getAllGrado(
						inst, observacionEstudianteVO.getObsMetodologia(),
						observacionEstudianteVO.getObsSede(),
						observacionEstudianteVO.getObsJornada()));
				request.setAttribute("listaGrupo", observacionDAO.getAllGrupo(
						inst, observacionEstudianteVO.getObsMetodologia(),
						observacionEstudianteVO.getObsSede(),
						observacionEstudianteVO.getObsJornada(),
						observacionEstudianteVO.getObsGrado()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void estudianteBuscar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionEstudianteVO observacionEstudianteVO) {
		try {
			request.setAttribute("listaObservacionEstudiante", observacionDAO
					.getAllObservacionEstudiante(observacionEstudianteVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void estudianteGuardar(HttpServletRequest request,
			HttpSession session, Login usuVO,
			ObservacionEstudianteVO observacionEstudianteVO) {
		try {
			observacionDAO
					.guardarObservacionEstudiante(observacionEstudianteVO);
			estudianteNuevo(request, session, usuVO, observacionEstudianteVO);
			request.setAttribute(Params.SMS,
					"La información fue registrada satisfactoriamente");
		} catch (Exception e) {
			request.setAttribute(Params.SMS, "Error registrando información: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * MODIFICACION PERIODOS EVVALUACION EN LINEA 13-03-10 WG
	 */

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		// System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}
}
