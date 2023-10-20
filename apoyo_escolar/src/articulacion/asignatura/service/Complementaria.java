package articulacion.asignatura.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import articulacion.asignatura.dao.ComplementariaDAO;
import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.ParamsVO;
import articulacion.asignatura.vo.ComplementariaVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class Complementaria extends Service {

	private Cursor cursor;
	private String FICHA_NUEVO;
	private String FICHA_LISTA;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		HttpSession session = request.getSession();
		cursor = new Cursor();
		FICHA_NUEVO = config.getInitParameter("FICHA_NUEVO");
		FICHA_LISTA = config.getInitParameter("FICHA_LISTA");
		String FICHA = FICHA_NUEVO;
		int CMD = getCmd(request, session, Params.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		ComplementariaDAO complementariaDAO = new ComplementariaDAO(cursor);
		AsignaturaVO asignaturaVO = (AsignaturaVO) session
				.getAttribute("asignaturaVO");
		DatosVO datosVO = (DatosVO) session.getAttribute("datosVO");
		// System.out.println("Inicia Complementaria");
		switch (CMD) {
		case Params.CMD_GUARDAR:
			FICHA = FICHA_LISTA;
			ComplementariaVO nuevos = (ComplementariaVO) session
					.getAttribute("complementariaVO");
			if (complementariaDAO.insertar(nuevos)) {
				// System.out.println("insertados");
				request.setAttribute(Params.SMS,
						"Complementarias actualizadas satisfactoriamente");
				request.setAttribute("Complementarias", complementariaDAO
						.getListaComplementariaIns(asignaturaVO
								.getArtAsigCodigo()));
			} else {
				// System.out.println("pailas no Se pudieron insertar los Requisitos");
				request.setAttribute(Params.SMS,
						"Error interno actualizando complementarias");
			}
			break;
		case Params.CMD_NUEVO:
			if (datosVO != null) {
				request.setAttribute("ComplementariaVO", complementariaDAO
						.getComplementarias(datosVO, asignaturaVO));
			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA;
		return dispatcher;
	}
}
