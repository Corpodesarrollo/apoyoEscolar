package articulacion.retiroEstudiantes.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.retiroEstudiantes.vo.DatosPlanEvalVO;
import articulacion.retiroEstudiantes.vo.ParamsVO;
import articulacion.retiroEstudiantes.vo.PopUpMotivoVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.retiroEstudiantes.dao.EvaluacionDAO;

public class MotivoRetiro extends Service {

	private Cursor cursor;
	private String FICHA_NUEVO;
	private String FICHA_LISTA;
	private EvaluacionDAO evaluacionDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		cursor = new Cursor();

		// System.out.println("aca entra al motivo el servicio");
		FICHA_NUEVO = config.getInitParameter("FICHA_LISTA");
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		// System.out.println("aca entra al motivo el servicio");
		EvaluacionDAO evaluacionDAO = new EvaluacionDAO(cursor);
		String param = null;
		switch (CMD) {
		case ParamsVO.CMD_POP_MOSTRAR:
			// System.out.println("va a mostrar !!!!!!!!!!!!!!!!!!!!"+request.getParameter("param"));
			param = request.getParameter("param");
			if (param != null) {
				DatosPlanEvalVO datosVO = (DatosPlanEvalVO) session
						.getAttribute("FilEvaluacionRVO");
				String[] param2 = param.replace('|', ':').split(":");
				long asig = Long.parseLong(param2[0]);
				long grupo = Long.parseLong(param2[1]);
				long est = datosVO.getEstudiante();
				session.setAttribute("popUpMotivoVO",
						evaluacionDAO.getMotivo(est, asig, grupo));// ESTA ES LA
																	// CONSULTA
																	// QUE ME VA
																	// A HACER
																	// TODITO
				session.setAttribute("listaMotivoVO",
						evaluacionDAO.getAllMotivo());// ESTA ES LA CONSULTA QUE
														// TODITO
			}
			break;
		case ParamsVO.CMD_POP_CANCELAR:
			param = request.getParameter("param");
			PopUpMotivoVO motivoVO = new PopUpMotivoVO();
			motivoVO.setIndice(Integer.parseInt(param));
			session.setAttribute("popUpMotivoVO", motivoVO);// ESTA ES LA
															// CONSULTA QUE ME
															// TODITO
			session.setAttribute("listaMotivoVO", evaluacionDAO.getAllMotivo());// ESTA
																				// ES
																				// LA
																				// CONSULTA
																				// QUE
																				// ME
																				// VA
																				// A
																				// HACER
																				// TODITO
			// System.out.println("va a cancelar !!!!!!!!!!!!!!!!!!!!"+request.getParameter("param"));
			break;
		case Params.CMD_GUARDAR:
			// RequisitoVO
			// nuevos=(RequisitoVO)session.getAttribute("requisitoVO");

			/*
			 * if(preRequisitoDAO.insertar(nuevos))
			 * System.out.println("insertados"); else
			 * System.out.println("pailas no Se inserto el Requisito");
			 */
			break;
		case Params.CMD_NUEVO:

			// session.setAttribute("PreRequisitos",preRequisitoDAO.getPreRequisitos(componente,especialidad,area,periodo,asignaturaVO.getArtAsigCodigo()));
			session.setAttribute("listaMotivoVO", evaluacionDAO.getAllMotivo());// ESTA
																				// ES
																				// LA
																				// CONSULTA
																				// QUE
																				// ME
																				// VA
																				// A
																				// HACER
																				// TODITO

			break;
		}
		// dispatcher[0]=String.valueOf(Params.FORWARD);
		// dispatcher[1]=FICHA_NUEVO;
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_NUEVO;

		return dispatcher;
	}
}
