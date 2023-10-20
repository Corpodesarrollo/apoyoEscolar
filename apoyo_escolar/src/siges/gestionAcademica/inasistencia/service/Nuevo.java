package siges.gestionAcademica.inasistencia.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAcademica.inasistencia.dao.InasistenciaDAO;
import siges.gestionAcademica.inasistencia.vo.FiltroInasistencia;
import siges.gestionAcademica.inasistencia.vo.ParamVO;
import siges.login.beans.Login;

public class Nuevo extends Service {

	private String FICHA;
	private String FICHA_INASISTENCIA;
	private String FICHA_RETARDO;
	private String FICHA_SALIDA_TARDE;
	private String FICHA_INASISTENCIA_SIMPLE;
	private InasistenciaDAO inasistenciaDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		try {
			cursor = new Cursor();
			inasistenciaDAO = new InasistenciaDAO(cursor);
			FICHA_INASISTENCIA = config.getInitParameter("FICHA_INASISTENCIA");
			FICHA_RETARDO = config.getInitParameter("FICHA_RETARDO");
			FICHA_SALIDA_TARDE = config.getInitParameter("FICHA_SALIDA_TARDE");
			FICHA_INASISTENCIA_SIMPLE = config
					.getInitParameter("FICHA_INASISTENCIA_SIMPLE");
			Login usuVO = (Login) session.getAttribute("login");
			FiltroInasistencia filtroInasistencia = (FiltroInasistencia) session
					.getAttribute("filtroInasistencia");
			int CMD = getCmd(request, session, Params.CMD_NUEVO);
			int TIPO = getTipo(request, session, ParamVO.FICHA_DEFAULT);
			System.out.println("Valores inasistenciaNuevo: " + TIPO + "//"
					+ CMD);
			if (filtroInasistencia != null) {
				filtroInasistencia.setFilInstitucion(Long.parseLong(usuVO
						.getInstId()));
				filtroInasistencia.setFilSede(Integer.parseInt(usuVO
						.getSedeId()));
				filtroInasistencia.setFilJornada(Integer.parseInt(usuVO
						.getJornadaId()));
			}
			// System.out.println(":::: "+calendar.getMaximum(Calendar.DAY_OF_MONTH));
			switch (TIPO) {
			case ParamVO.FICHA_INASISTENCIA:
				FICHA = FICHA_INASISTENCIA;
				switch (CMD) {
				case Params.CMD_BUSCAR:
					buscarInasistencia(request, usuVO, filtroInasistencia);
					break;
				case Params.CMD_GUARDAR:
					guardarInasistencia(request, usuVO, filtroInasistencia);
					request.setAttribute(Params.SMS,
							"La información fue actualizada satisfactoriamente");
					break;
				}
				break;
			case ParamVO.FICHA_RETARDO:
				FICHA = FICHA_RETARDO;
				switch (CMD) {
				case Params.CMD_BUSCAR:
					buscarRetardo(request, usuVO, filtroInasistencia);
					break;
				case Params.CMD_GUARDAR:
					guardarRetardo(request, usuVO, filtroInasistencia);
					request.setAttribute(Params.SMS,
							"La información fue actualizada satisfactoriamente");
					break;
				}
				break;
			case ParamVO.FICHA_SALIDA_TARDE:
				FICHA = FICHA_SALIDA_TARDE;
				switch (CMD) {
				case Params.CMD_BUSCAR:
					buscarSalidaTarde(request, usuVO, filtroInasistencia);
					break;
				case Params.CMD_GUARDAR:
					guardarSalidaTarde(request, usuVO, filtroInasistencia);
					request.setAttribute(Params.SMS,
							"La información fue actualizada satisfactoriamente");
					break;
				}
				break;
			case ParamVO.FICHA_INASISTENCIA_SIMPLE:
				FICHA = FICHA_INASISTENCIA_SIMPLE;
				switch (CMD) {
				case Params.CMD_BUSCAR:
					buscarInasistenciaSimple(request, usuVO, filtroInasistencia);
					break;
				case Params.CMD_GUARDAR:
					guardarInasistenciaSimple(request, usuVO,
							filtroInasistencia);
					request.setAttribute(Params.SMS,
							"La información fue actualizada satisfactoriamente");
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	private void buscarInasistencia(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		System.out.println("buscarInasistencia");
		System.out.println("filtro " + filtro.getFilGrado());
		request.setAttribute("lEstudiante",
				inasistenciaDAO.getEstudianteInasistencia(filtro));
		request.setAttribute("lDias", inasistenciaDAO.getDias(filtro));
		System.out.println("filtro " + filtro.getFilGrado());

		filtro.setFalla(null);
		filtro.setFormaEstado("1");
	}

	private void buscarInasistenciaSimple(HttpServletRequest request,
			Login usuVO, FiltroInasistencia filtro) throws Exception {
		System.out.println("filtro-g  " + filtro.getFilGrado());
		request.setAttribute("lEstudiante",
				inasistenciaDAO.getEstudianteInasistenciaSimple(filtro));
		request.setAttribute("lDias", inasistenciaDAO.getDias(filtro));
		System.out.println("filtro g" + filtro.getFilGrado());
		// request.setParameter("cmd",ParamVO.CMD_BUSCAR);

		filtro.setFalla(null);
		filtro.setFormaEstado("1");
	}

	private void buscarRetardo(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		request.setAttribute("lEstudiante",
				inasistenciaDAO.getEstudianteRetardo(filtro));
		request.setAttribute("lDias", inasistenciaDAO.getDias(filtro));
		filtro.setFalla(null);
		filtro.setFormaEstado("1");
	}

	private void buscarSalidaTarde(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		request.setAttribute("lEstudiante",
				inasistenciaDAO.getEstudianteSalidaTarde(filtro));
		request.setAttribute("lDias", inasistenciaDAO.getDias(filtro));
		filtro.setFalla(null);
		filtro.setFormaEstado("1");
	}

	private void guardarInasistencia(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		inasistenciaDAO.guardarInasistencia(filtro);
		filtro.setFalla(null);
		filtro.setFormaEstado("0");
	}

	private void guardarInasistenciaSimple(HttpServletRequest request,
			Login usuVO, FiltroInasistencia filtro) throws Exception {
		inasistenciaDAO.guardarInasistenciaSimple(filtro);
		filtro.setFalla(null);
		filtro.setFormaEstado("0");
	}

	private void guardarRetardo(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		inasistenciaDAO.guardarRetardo(filtro);
		filtro.setFalla(null);
		filtro.setFormaEstado("0");
	}

	private void guardarSalidaTarde(HttpServletRequest request, Login usuVO,
			FiltroInasistencia filtro) throws Exception {
		inasistenciaDAO.guardarSalidaTarde(filtro);
		filtro.setFalla(null);
		filtro.setFormaEstado("0");
	}
}
