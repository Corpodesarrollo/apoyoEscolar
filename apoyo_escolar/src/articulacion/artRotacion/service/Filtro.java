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

import articulacion.artRotacion.dao.ArtRotacionDAO;
import articulacion.artRotacion.vo.EstructuraVO;
import articulacion.artRotacion.vo.FiltroEstructuraVO;
import articulacion.artRotacion.vo.FiltroHorarioVO;
import articulacion.artRotacion.vo.FiltroRecesoVO;
import articulacion.artRotacion.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;

/**
 * 6/10/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class Filtro extends Service {
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

	public String[] process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		Login usuVO = (Login) session.getAttribute("login");
		cursor = new Cursor();
		artRotacionDAO = new ArtRotacionDAO(cursor);
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
		int CMD = getCmd(request, session, ParamsVO.CMD_BUSCAR);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
//		System.out.println("ARTFILTROROTACION=Tipo=" + TIPO + " CMD=" + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_ESTRUCTURA:
			FICHA = FICHA_ESTRUCTURA;
			FiltroEstructuraVO artRotFiltroEstructuraVO = (FiltroEstructuraVO) session.getAttribute("artRotFiltroEstructuraVO");
			initParams(request, usuVO, artRotFiltroEstructuraVO);
			break;
		case ParamsVO.FICHA_HORARIO:
			switch (CMD) {
			case ParamsVO.CMD_BUSCAR:
			case ParamsVO.CMD_GUARDAR:
				FICHA = FICHA_HORARIO;
				initHorario(request, usuVO);
				break;
			}
			break;
		case ParamsVO.FICHA_BORRAR_HORARIO:
				FICHA = FICHA_BORRAR_HORARIO;
				initBorrarHorario(request, usuVO);
			break;
		case ParamsVO.FICHA_RECESO:
			FICHA = FICHA_RECESO;
			initReceso(request, usuVO);
			break;
		}
		dispatcher[0] = String.valueOf(ParamsVO.INCLUDE);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void initReceso(HttpServletRequest request, Login usuVO) throws ServletException {
		try {
			FiltroRecesoVO filtro = (FiltroRecesoVO) request.getSession().getAttribute("artRotFiltroRecesoVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lEstructuraVO", artRotacionDAO.getAjaxEstructura(filtro.getFilInstitucion(), filtro.getFilSede(), filtro.getFilJornada(), filtro.getFilAnhoVigencia(), filtro.getFilPerVigencia()));
			}
			List l = new ArrayList();
			// periodos de vigencia
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			request.setAttribute("listaVigencia",artRotacionDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno");
		}
	}
	
	private void initHorario(HttpServletRequest request, Login usuVO) throws ServletException {
		try {
			FiltroHorarioVO filtro = (FiltroHorarioVO) request.getSession().getAttribute("artRotFiltroHorarioVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lDocenteVO", artRotacionDAO.getAjaxDocente(filtro.getFilInstitucion(), filtro.getFilSede(), filtro.getFilJornada(), filtro.getFilSemestre(), filtro.getFilAsignatura()));
				request.setAttribute("lEspecialidadVO", artRotacionDAO.getAjaxEspecialidad(filtro.getFilInstitucion()));
				request.setAttribute("lAsignaturaVO", artRotacionDAO.getAjaxAsignatura(filtro.getFilInstitucion(), filtro.getFilAnhoVigencia(), filtro.getFilPerVigencia(), filtro.getFilComponente(), filtro.getFilEspecialidad(), filtro.getFilSemestre(), filtro.getFilGrupo()));
				request.setAttribute("lGrupoVO", artRotacionDAO.getAjaxGrupo(filtro.getFilInstitucion(), filtro.getFilSede(), filtro.getFilJornada(), filtro.getFilAnhoVigencia(), filtro.getFilPerVigencia(), filtro.getFilComponente(), filtro.getFilEspecialidad(), filtro.getFilSemestre()));
			} else {
				request.getSession().removeAttribute("artRotHorarioVO");
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO", artRotacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO", artRotacionDAO.getJornada(inst, sede, jor));
			// ano vigencia
			List l = new ArrayList();
			// periodos de vigencia
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			// componente
			l = new ArrayList();
			ItemVO itemVO = new ItemVO();
			itemVO.setCodigo(1);
			itemVO.setNombre("Acadnmico");
			l.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(2);
			itemVO.setNombre("Tncnico");
			l.add(itemVO);
			request.setAttribute("lComponenteVO", l);
			// semestre
			l = new ArrayList();
			l.add("1");
			l.add("2");
			l.add("3");
			l.add("4");
			request.setAttribute("lSemestreVO", l);
			request.setAttribute("listaVigencia",artRotacionDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno");
		}
	}

	private void initBorrarHorario(HttpServletRequest request, Login usuVO) throws ServletException {
		try {
			FiltroHorarioVO filtro = (FiltroHorarioVO) request.getSession().getAttribute("artRotFiltroHorarioVO");
			if (filtro != null && filtro.getFormaEstado().equals("1")) {
				request.setAttribute("lEspecialidadVO", artRotacionDAO.getAjaxEspecialidad(filtro.getFilInstitucion()));
				request.setAttribute("lAsignaturaVO", artRotacionDAO.getAjaxAsignatura(filtro.getFilInstitucion(), filtro.getFilAnhoVigencia(), filtro.getFilPerVigencia(), filtro.getFilComponente(), filtro.getFilEspecialidad(), filtro.getFilSemestre(), filtro.getFilGrupo()));
				request.setAttribute("lGrupoVO", artRotacionDAO.getAjaxGrupo(filtro.getFilInstitucion(), filtro.getFilSede(), filtro.getFilJornada(), filtro.getFilAnhoVigencia(), filtro.getFilPerVigencia(), filtro.getFilComponente(), filtro.getFilEspecialidad(), filtro.getFilSemestre()));
			} else {
				request.getSession().removeAttribute("artRotHorarioVO");
			}
			long inst = -99;
			int sede = -99;
			int jor = -99;
			inst = Long.parseLong(usuVO.getInstId());
			if (usuVO.getSedeId() != null && !usuVO.getSedeId().equals(""))
				sede = Integer.parseInt(usuVO.getSedeId());
			if (usuVO.getJornadaId() != null && !usuVO.getJornadaId().equals(""))
				jor = Integer.parseInt(usuVO.getJornadaId());
			// SEDE
			request.setAttribute("lSedeVO", artRotacionDAO.getSede(inst, sede));
			// JORNADA
			request.setAttribute("lJornadaVO", artRotacionDAO.getJornada(inst, sede, jor));
			List l = new ArrayList();
			// periodos de vigencia
			l.add("1");
			l.add("2");
			request.setAttribute("lPeriodoVO", l);
			// componente
			l = new ArrayList();
			ItemVO itemVO = new ItemVO();
			itemVO.setCodigo(1);
			itemVO.setNombre("Acadnmico");
			l.add(itemVO);
			itemVO = new ItemVO();
			itemVO.setCodigo(2);
			itemVO.setNombre("Tncnico");
			l.add(itemVO);
			request.setAttribute("lComponenteVO", l);
			// semestre
			l = new ArrayList();
			l.add("1");
			l.add("2");
			l.add("3");
			l.add("4");
			request.setAttribute("lSemestreVO", l);
			request.setAttribute("listaVigencia",artRotacionDAO.getListaVigencia());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error interno");
		}
	}
	
	private void initParams(HttpServletRequest request, Login usuVO, FiltroEstructuraVO filtro) throws ServletException {
		long inst = -99;
		int sede = -99;
		int jor = -99;
		inst = Long.parseLong(usuVO.getInstId());
		try {
			int per=1;
			int vig = (int) artRotacionDAO.getVigenciaNumerico();
			// ano vigencia
			List l = new ArrayList();
			// periodos de vigencia
			l = new ArrayList();l.add("1");l.add("2");
			request.setAttribute("listaPeriodo", l);
			//parametros
			if(filtro!=null){
				if(filtro.getAnhoVigencia()!=0){
					vig = filtro.getAnhoVigencia();
				}
				if(filtro.getPerVigencia()!=0){
					per = filtro.getPerVigencia();
				}
			}else{
				filtro=new FiltroEstructuraVO();
				per=artRotacionDAO.getPeriodoActual();
			}
			filtro.setAnhoVigencia(vig);
			filtro.setPerVigencia(per);
			request.getSession().setAttribute("artRotFiltroEstructuraVO",filtro);
			request.setAttribute("listaHorarioParamVO", artRotacionDAO.getAllParam(inst, sede, jor, vig,per));
			request.setAttribute("listaVigencia",artRotacionDAO.getListaVigencia());
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new ServletException("Error interno");
		}
	}
}
