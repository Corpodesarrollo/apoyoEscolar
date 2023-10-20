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
import siges.exceptions.InternalErrorException;
import articulacion.horarioArticulacion.dao.HorarioArticulacionDAO;
import articulacion.horarioArticulacion.vo.FiltroHorarioVO;
import articulacion.horarioArticulacion.vo.LComponenteVO;
import articulacion.horarioArticulacion.vo.ParametroVO;
import articulacion.horarioArticulacion.vo.ParamsVO;
import siges.login.beans.Login;

public class Lista extends Service {

	private String FICHA_LISTA;

	private String FICHA_LISTA_PARAMS;

	private String FICHA_LISTA_HORARIO;

	private String FICHA_LISTA_HORARIO_GRUPO;

	private String FICHA_LISTA_HORARIO_DOCENTE;

	private Cursor cursor;

	private HorarioArticulacionDAO horarioArticulacionDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		// try{
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		cursor = new Cursor();
		horarioArticulacionDAO = new HorarioArticulacionDAO(cursor);
		FICHA_LISTA_PARAMS = config.getInitParameter("FICHA_LISTA_PARAMS");
		FICHA_LISTA_HORARIO = config.getInitParameter("FICHA_LISTA_HORARIO");
		FICHA_LISTA_HORARIO_GRUPO = config
				.getInitParameter("FICHA_LISTA_HORARIO_GRUPO");
		FICHA_LISTA_HORARIO_DOCENTE = config
				.getInitParameter("FICHA_LISTA_HORARIO_DOCENTE");
		ParametroVO horArtParamVO = (ParametroVO) session
				.getAttribute("horArtParamVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_BUSCAR);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		long inst = -99;
		int sede = -99;
		int jor = -99;
		// System.out.println("HORARIOART=Tipo=" + TIPO + " CMD=" + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_PARAMETROS:
			FICHA_LISTA = FICHA_LISTA_PARAMS;
			initParams(request, usuVO, horArtParamVO);
			break;
		case ParamsVO.FICHA_HORARIO:
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
			case ParamsVO.CMD_GUARDAR:
				FICHA_LISTA = FICHA_LISTA_HORARIO;
				initHorario(request, usuVO);
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO_GRUPO:
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
			case ParamsVO.CMD_GUARDAR:
				FICHA_LISTA = FICHA_LISTA_HORARIO_GRUPO;
				initHorarioGrupo(request, usuVO);
				break;
			}
			break;
		case ParamsVO.FICHA_HORARIO_DOCENTE:
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
			case ParamsVO.CMD_GUARDAR:
				FICHA_LISTA = FICHA_LISTA_HORARIO_DOCENTE;
				initHorarioDocente(request, usuVO);
				break;
			}
			break;
		}
		// }catch(Exception e){e.printStackTrace();}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA_LISTA;
		return dispatcher;
	}

	private void initParams(HttpServletRequest request, Login usuVO,
			ParametroVO parametroVO) {
		long inst = -99;
		int sede = -99;
		int jor = -99;
		inst = Long.parseLong(usuVO.getInstId());
		// if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
		// sede = Integer.parseInt(usuVO.getSedeId());
		// if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
		// jor = Integer.parseInt(usuVO.getJornadaId());
		request.setAttribute("listaHorarioParamVO",
				horarioArticulacionDAO.getAllParam(inst, sede, jor));
	}

	private void initHorario(HttpServletRequest request, Login usuVO) {
		try {
			FiltroHorarioVO filtro = (FiltroHorarioVO) request.getSession()
					.getAttribute("filtrohorArtVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lDocenteVO", horarioArticulacionDAO
						.getAjaxDocente(filtro.getFilInstitucion(),
								filtro.getFilSede(), filtro.getFilJornada(),
								filtro.getFilSemestre(),
								filtro.getFilAsignatura()));
				request.setAttribute("lEspecialidadVO", horarioArticulacionDAO
						.getAjaxEspecialidad(filtro.getFilInstitucion()));
				request.setAttribute(
						"lAsignaturaVO",
						horarioArticulacionDAO.getAjaxAsignatura(
								filtro.getFilInstitucion(),
								filtro.getFilAnhoVigencia(),
								filtro.getFilPerVigencia(),
								filtro.getFilComponente(),
								filtro.getFilEspecialidad(),
								filtro.getFilSemestre(), filtro.getFilGrupo()));
				request.setAttribute("lGrupoVO", horarioArticulacionDAO
						.getAjaxGrupo(filtro.getFilInstitucion(),
								filtro.getFilSede(), filtro.getFilJornada(),
								filtro.getFilAnhoVigencia(),
								filtro.getFilPerVigencia(),
								filtro.getFilComponente(),
								filtro.getFilEspecialidad(),
								filtro.getFilSemestre()));
			} else {
				request.getSession().removeAttribute("horArtVO");
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null
					&& !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO",
					horarioArticulacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					horarioArticulacionDAO.getJornada(inst, sede, jor));
			List l = new ArrayList();
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			// componente
			l = new ArrayList();
			LComponenteVO comp = new LComponenteVO(1, "Acadnmico");
			l.add(comp);
			comp = new LComponenteVO(2, "Tncnico");
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

	private void initHorarioGrupo(HttpServletRequest request, Login usuVO) {
		try {
			FiltroHorarioVO filtro = (FiltroHorarioVO) request.getSession()
					.getAttribute("filtrohorArtVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lEspecialidadVO", horarioArticulacionDAO
						.getAjaxEspecialidad(filtro.getFilInstitucion()));
				request.setAttribute("lGrupoVO", horarioArticulacionDAO
						.getAjaxGrupo(filtro.getFilInstitucion(),
								filtro.getFilSede(), filtro.getFilJornada(),
								filtro.getFilAnhoVigencia(),
								filtro.getFilPerVigencia(),
								filtro.getFilComponente(),
								filtro.getFilEspecialidad(),
								filtro.getFilSemestre()));
			} else {
				request.getSession().removeAttribute("horArtVO");
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null
					&& !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO",
					horarioArticulacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					horarioArticulacionDAO.getJornada(inst, sede, jor));
			List l = new ArrayList();
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			// componente
			l = new ArrayList();
			LComponenteVO comp = new LComponenteVO(1, "Acadnmico");
			l.add(comp);
			comp = new LComponenteVO(2, "Tncnico");
			l.add(comp);
			request.setAttribute("lComponenteVO", l);
			// semestre
			l = new ArrayList();
			l.add("1");
			l.add("2");
			l.add("3");
			l.add("4");
			request.setAttribute("lSemestreVO", l);
			request.setAttribute("listaVigencia",
					horarioArticulacionDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initHorarioDocente(HttpServletRequest request, Login usuVO) {
		try {
			FiltroHorarioVO filtro = (FiltroHorarioVO) request.getSession()
					.getAttribute("filtrohorArtVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lDocenteVO", horarioArticulacionDAO
						.getAjaxDocenteSede(filtro.getFilInstitucion(),
								filtro.getFilSede(), filtro.getFilJornada()));
			} else {
				request.getSession().removeAttribute("horArtVO");
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null
					&& !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO",
					horarioArticulacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO",
					horarioArticulacionDAO.getJornada(inst, sede, jor));
			// componente
			List l = new ArrayList();
			LComponenteVO comp = new LComponenteVO(1, "Acadnmico");
			l.add(comp);
			comp = new LComponenteVO(2, "Tncnico");
			l.add(comp);
			request.setAttribute("lComponenteVO", l);
			// periodos de vigencia
			l = new ArrayList();
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			request.setAttribute("listaVigencia",
					horarioArticulacionDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
