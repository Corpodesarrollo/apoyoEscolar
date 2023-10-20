package articulacion.retiroEstudiantes.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.perfil.vo.Url;
import articulacion.retiroEstudiantes.dao.EvaluacionDAO;
import articulacion.retiroEstudiantes.vo.DatosPlanEvalVO;
import articulacion.retiroEstudiantes.vo.EvaluacionVO;
import articulacion.retiroEstudiantes.vo.ParamsVO;
import articulacion.retiroEstudiantes.vo.PopUpMotivoVO;
import siges.login.beans.Login;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;

public class Lista extends Service {
	private String FICHA_LISTA;

	private EvaluacionDAO evaluacionDAO = new EvaluacionDAO(new Cursor());

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		FICHA_LISTA = config.getInitParameter("FICHA_LISTA");
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_EVALUACION);
		DatosPlanEvalVO datosVO = (DatosPlanEvalVO) session
				.getAttribute("FilEvaluacionRVO");
		Login usuVO = (Login) session.getAttribute("login");
		// System.out.println("VALOR DE CMD: "+CMD);
		switch (CMD) {
		case ParamsVO.CMD_GUARDAR:
			guardar(request);
			break;
		case ParamsVO.CMD_FILTRAR:
			if (datosVO != null) {
				request.setAttribute("listaAsignaturaVO",
						evaluacionDAO.getAllListaAsignatura(datosVO));
			}
			break;

		}

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = FICHA_LISTA;

		return dispatcher;
	}

	private void guardar(HttpServletRequest request) {
		String[] band = request.getParameterValues("band");
		String[] x = request.getParameterValues("x");
		String[] mot = request.getParameterValues("mot");
		String[] desc = request.getParameterValues("desc");
		List l = new ArrayList();
		PopUpMotivoVO motivoVO = null;
		if (band != null) {
			DatosPlanEvalVO datosVO = (DatosPlanEvalVO) request.getSession()
					.getAttribute("FilEvaluacionRVO");
			for (int i = 0; i < band.length; i++) {
				if (band[i].equals("1")) {
					motivoVO = new PopUpMotivoVO();
					String param = x[i];
					String[] params2 = param.replace('|', ':').split(":");
					long asig = Long.parseLong(params2[0]);
					long gru = Long.parseLong(params2[1]);
					long motivo = Long.parseLong(mot[i]);
					motivoVO.setCodigoEstudiante(datosVO.getEstudiante());
					motivoVO.setCodigoAsignatura(asig);
					motivoVO.setCodigoGrupo(gru);
					motivoVO.setCodigoMotivo(motivo);
					motivoVO.setDescripcion(desc[i]);
					l.add(motivoVO);
					// System.out.println("valor de bandera: "+band[i]);
				}
			}// INSERTAR
			try {
				if (evaluacionDAO.insertar(l, datosVO)) {
					request.setAttribute(Params.SMS,
							"La información fue ingresada satisfactoriamente");
				} else {
					request.setAttribute(Params.SMS,
							"No se puede registrar la inscripcinn: "
									+ evaluacionDAO.getMensaje());
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute(Params.SMS,
						"la información no fue ingresada: Error interno");
			}
		}

	}

}
