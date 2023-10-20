package articulacion.horarioEstudiante.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.horarioArticulacion.vo.ParamsVO;
import articulacion.horarioEstudiante.vo.ComponenteVO;
import articulacion.horarioEstudiante.dao.HorarioEstudianteDAO;
import articulacion.horarioEstudiante.vo.HorarioVO;
import articulacion.horarioEstudiante.vo.FiltroInscripcionVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.login.beans.Login;

public class Lista extends Service {

	private Cursor c;
	private String FILTRO_INSCRIPCION;
	private HorarioEstudianteDAO horarioEstudianteDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		FILTRO_INSCRIPCION = config.getInitParameter("FILTRO_INSCRIPCION");
		c = new Cursor();
		// int CMD=getCmd(request,session,Params.CMD_NUEVO);
		// int TIPO=getTipo(request,session,ParamsVO.FICHA_DEFAULT);
		long colegio = 0;
		int sede = 0;
		int jornada = 0;
		int estudiante = 0;
		int esp = 0;
		HorarioVO horarioVO = new HorarioVO();

		Login usuVO = (Login) session.getAttribute("login");
		ComponenteVO componenteVO = (ComponenteVO) session
				.getAttribute("componenteVO");
		HorarioEstudianteDAO horarioEstudianteDAO = new HorarioEstudianteDAO(c);

		if (usuVO != null) {
			colegio = Long.parseLong(usuVO.getInstId());
			sede = Integer.parseInt(usuVO.getSedeId());
			session.setAttribute("listaJornadaVO", horarioEstudianteDAO
					.getListaJornada(usuVO.getInstId(), usuVO.getSedeId()));
			jornada = Integer.parseInt(usuVO.getJornadaId());
			estudiante = Integer.parseInt(usuVO.getCodigoEst());
			esp = Integer.parseInt(usuVO.getArtEspId());
		}

		FiltroInscripcionVO filtro = new FiltroInscripcionVO();
		filtro.setFilInstitucion(colegio);
		filtro.setFilSede(sede);
		filtro.setFilJornada(jornada);
		filtro.setFilEstudiante(estudiante);
		filtro.setFilEspecialidad(esp);

		if (componenteVO != null) {
			// System.out.println("ESTA ES LA JORNADA QUE ESCOJO EN EL COMBO "+componenteVO.getJornada());
			filtro.setFilJornada(componenteVO.getJornada());
			// System.out.println("ESTA ES LA JORNADA QUE ESCOJO EN EL COMBO "+componenteVO.getJornada());
			// System.out.println("componente= "+componenteVO.getComponente());
			horarioVO = horarioEstudianteDAO.getHorario(filtro, componenteVO);
			session.setAttribute("horArtVO", horarioVO);
		}

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FILTRO_INSCRIPCION;

		return dispatcher;
	}
}
