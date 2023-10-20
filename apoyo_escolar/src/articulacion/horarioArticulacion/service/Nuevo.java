package articulacion.horarioArticulacion.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.horarioArticulacion.dao.HorarioArticulacionDAO;
import articulacion.horarioArticulacion.vo.FiltroHorarioVO;
import articulacion.horarioArticulacion.vo.HorarioVO;
import articulacion.horarioArticulacion.vo.ParametroVO;
import articulacion.horarioArticulacion.vo.ParamsVO;
import siges.login.beans.Login;

public class Nuevo extends Service {

	private String FICHA_NUEVO;
	private String FICHA_NUEVO_PARAMS;
	private String FICHA_NUEVO_HORARIO;
	private String FICHA_NUEVO_HORARIO_GRUPO;
	private String FICHA_NUEVO_HORARIO_DOCENTE;
	private HorarioArticulacionDAO horarioArticulacionDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		// try{
		cursor = new Cursor();
		horarioArticulacionDAO = new HorarioArticulacionDAO(cursor);
		FICHA_NUEVO_PARAMS = config.getInitParameter("FICHA_NUEVO_PARAMS");
		FICHA_NUEVO_HORARIO = config.getInitParameter("FICHA_NUEVO_HORARIO");
		FICHA_NUEVO_HORARIO_GRUPO = config
				.getInitParameter("FICHA_NUEVO_HORARIO_GRUPO");
		FICHA_NUEVO_HORARIO_DOCENTE = config
				.getInitParameter("FICHA_NUEVO_HORARIO_DOCENTE");
		Login usuVO = (Login) session.getAttribute("login");
		ParametroVO horArtParamVO = (ParametroVO) session
				.getAttribute("horArtParamVO");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		FiltroHorarioVO filtro = null;
		switch (TIPO) {
		case ParamsVO.FICHA_PARAMETROS:
			initParams(request, usuVO);
			FICHA_NUEVO = FICHA_NUEVO_PARAMS;
			switch (CMD) {
			case Params.CMD_GUARDAR:
				horArtParamVO.setParInstitucion(Long.parseLong(usuVO
						.getInstId()));
				if (horArtParamVO.getFormaEstado().equals("1")) {// actualiza
					// System.out.println("Entor a actualizar la bvaian");
					if (horarioArticulacionDAO.actualizar(horArtParamVO,
							horArtParamVO)) {
						request.setAttribute(Params.SMS,
								"La información fue actualizada satisfactoriamente");
						session.removeAttribute("horArtParamVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue actualizada: "
										+ horarioArticulacionDAO.getMensaje());
					}
				} else {// guarda
						// System.out.println("Entor a guardar la bvaian");
					if (horarioArticulacionDAO.insertar(horArtParamVO)) {
						request.setAttribute(Params.SMS,
								"La información fue ingresada satisfactoriamente");
						session.removeAttribute("horArtParamVO");
					} else {
						request.setAttribute(Params.SMS,
								"La información no fue ingresada: "
										+ horarioArticulacionDAO.getMensaje());
					}
				}
				break;
			case Params.CMD_EDITAR:
				horArtParamVO = new ParametroVO();
				horArtParamVO.setParInstitucion(Long.parseLong(getRequest(
						request, "id")));
				horArtParamVO.setParSede(Integer.parseInt(getRequest(request,
						"id2")));
				horArtParamVO.setParJornada(Integer.parseInt(getRequest(
						request, "id3")));
				horArtParamVO.setParComponente(Integer.parseInt(getRequest(
						request, "id4")));
				horArtParamVO.setParAnhoVigencia(Integer.parseInt(getRequest(
						request, "id5")));
				horArtParamVO.setParPerVigencia(Integer.parseInt(getRequest(
						request, "id6")));
				horArtParamVO = horarioArticulacionDAO
						.getParametroVO(horArtParamVO);
				if (horArtParamVO != null) {
					session.setAttribute("horArtParamVO", horArtParamVO);
					request.setAttribute("guia", getRequest(request, "id"));
					request.setAttribute("guia2", getRequest(request, "id2"));
					request.setAttribute("guia3", getRequest(request, "id3"));
					request.setAttribute("guia4", getRequest(request, "id4"));
					request.setAttribute("guia5", getRequest(request, "id5"));
					request.setAttribute("guia6", getRequest(request, "id6"));
				} else {
					request.setAttribute(Params.SMS,
							"No se logrn obtener los datos "
									+ horarioArticulacionDAO.getMensaje());
				}
				break;
			case Params.CMD_ELIMINAR:
				horArtParamVO = new ParametroVO();
				horArtParamVO.setParInstitucion(Long.parseLong(getRequest(
						request, "id")));
				horArtParamVO.setParSede(Integer.parseInt(getRequest(request,
						"id2")));
				horArtParamVO.setParJornada(Integer.parseInt(getRequest(
						request, "id3")));
				horArtParamVO.setParComponente(Integer.parseInt(getRequest(
						request, "id4")));
				horArtParamVO.setParAnhoVigencia(Integer.parseInt(getRequest(
						request, "id5")));
				horArtParamVO.setParPerVigencia(Integer.parseInt(getRequest(
						request, "id6")));
				if (horarioArticulacionDAO.eliminar(horArtParamVO)) {
					request.setAttribute(Params.SMS,
							"La información fue eliminada satisfactoriamente");
					session.removeAttribute("horArtParamVO");
				} else {
					request.setAttribute(Params.SMS,
							"La información no fue eliminada: "
									+ horarioArticulacionDAO.getMensaje());
				}
				break;
			case Params.CMD_NUEVO:
				try {
					horArtParamVO = new ParametroVO();
					horArtParamVO
							.setParAnhoVigencia((int) horarioArticulacionDAO
									.getVigenciaNumerico());
					horArtParamVO
							.setParPerVigencia((int) horarioArticulacionDAO
									.getPeriodoNumerico());
					request.getSession().setAttribute("horArtParamVO",
							horArtParamVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO:
			FICHA_NUEVO = FICHA_NUEVO_HORARIO;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("horArtVO");
				break;
			case Params.CMD_BUSCAR:
				filtro = (FiltroHorarioVO) session
						.getAttribute("filtrohorArtVO");
				if (!horarioArticulacionDAO.validarHorario(filtro)) {
					request.setAttribute(
							Params.SMS,
							"No se puede generar la plantilla de horario, falta configurar los parnmetros de la sede - jornada");
					break;
				}
				HorarioVO horarioVO = horarioArticulacionDAO.getHorario(filtro);
				session.setAttribute("horArtVO", horarioVO);
				filtro.setFormaEstado("1");
				break;
			case Params.CMD_GUARDAR:
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
				filtro = (FiltroHorarioVO) session
						.getAttribute("filtrohorArtVO");
				if (!horarioArticulacionDAO.guardarHorario(filtro, a, b)) {
					request.setAttribute(Params.SMS,
							"Error guardando horario: "
									+ horarioArticulacionDAO.getMensaje());
				} else {
					request.setAttribute(Params.SMS,
							"Horario guardado satisfactoriamente");
					session.removeAttribute("horArtVO");
				}
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO_GRUPO:
			FICHA_NUEVO = FICHA_NUEVO_HORARIO_GRUPO;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("horArtVO");
				break;
			case Params.CMD_BUSCAR:
				filtro = (FiltroHorarioVO) session
						.getAttribute("filtrohorArtVO");
				if (!horarioArticulacionDAO.validarHorario(filtro)) {
					request.setAttribute(
							Params.SMS,
							"No se puede generar la plantilla de horario, falta configurar los parnmetros de la sede - jornada");
					break;
				}
				HorarioVO horarioVO = horarioArticulacionDAO
						.getHorarioGrupo(filtro);
				session.setAttribute("horArtVO", horarioVO);
				filtro.setFormaEstado("1");
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO_DOCENTE:
			FICHA_NUEVO = FICHA_NUEVO_HORARIO_DOCENTE;
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("horArtVO");
				break;
			case Params.CMD_BUSCAR:
				filtro = (FiltroHorarioVO) session
						.getAttribute("filtrohorArtVO");
				if (!horarioArticulacionDAO.validarHorario(filtro)) {
					request.setAttribute(
							Params.SMS,
							"No se puede generar la plantilla de horario, falta configurar los parnmetros de la sede - jornada");
					break;
				}
				HorarioVO horarioVO = horarioArticulacionDAO
						.getHorarioDocente(filtro);
				session.setAttribute("horArtVO", horarioVO);
				filtro.setFormaEstado("1");
				break;
			}
			break;
		}
		// }catch(Exception e){e.printStackTrace();}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;
		return dispatcher;
	}

	private void initParams(HttpServletRequest request, Login usuVO) {
		long inst = Long.parseLong(usuVO.getInstId());
		int sede = -99;
		int jor = -99;
		if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
			sede = Integer.parseInt(usuVO.getSedeId());
		if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
			jor = Integer.parseInt(usuVO.getJornadaId());
		// System.out.println("valors:"+inst+"//"+sede+"//"+jor);
		request.setAttribute("lSedeVO",
				horarioArticulacionDAO.getSede(inst, sede));
		request.setAttribute("lJornadaVO",
				horarioArticulacionDAO.getJornada(inst, sede, jor));
	}
}
