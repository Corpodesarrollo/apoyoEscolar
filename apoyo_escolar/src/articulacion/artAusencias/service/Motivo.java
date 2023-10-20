package articulacion.artAusencias.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.vo.Params;
import articulacion.artAusencias.dao.AusenciasDAO;
import articulacion.artAusencias.vo.DatosVO;
import articulacion.artAusencias.vo.EstDiasVO;
import articulacion.artAusencias.vo.ListaMotivosVO;
import articulacion.artAusencias.vo.ParamsVO;
import articulacion.artAusencias.vo.TempMotivoVO;
import siges.common.service.Service;
import siges.login.beans.Login;
import siges.dao.Cursor;

public class Motivo extends Service {

	private String MOTIVO;
	private Cursor cursor;

	// private AsignaturaDAO asignaturaDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		MOTIVO = config.getInitParameter("MOTIVO");
		DatosVO datosVO = (DatosVO) session.getAttribute("FilAusenciasVO");
		ListaMotivosVO listaMotivosVO = (ListaMotivosVO) session
				.getAttribute("listaMotivosVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_LISTA_MOTIVO);
		// int TIPO=getTipo(request,session,0);
		switch (CMD) {
		case ParamsVO.CMD_LISTA_MOTIVO:
			String codEst = (String) request.getParameter("cod");
			String dia = (String) request.getParameter("dia");
			String numDia = (String) request.getParameter("numDia");
			AusenciasDAO ausenciasDAO = new AusenciasDAO(cursor);
			String fecha = dia + "/" + datosVO.getMes() + "/"
					+ datosVO.getAnVigencia();
			request.setAttribute("listaMotivosVO",
					ausenciasDAO.getListaMotivos());
			request.setAttribute("listaAsignaEstVO", ausenciasDAO
					.getListaAsignaturas(codEst, datosVO.getGrupo(), numDia));
			request.setAttribute("ausencia", ausenciasDAO.ausencia(null,
					datosVO, Long.parseLong(codEst), fecha,
					Integer.parseInt(dia)));
			break;
		case ParamsVO.CMD_GUARDA_MOTIVO:
			int remov = -1;
			Iterator itMotivos;
			TempMotivoVO motivoVO = (TempMotivoVO) session
					.getAttribute("tempMotivoVO");

			List lista;
			if (listaMotivosVO == null) {
				listaMotivosVO = new ListaMotivosVO();
				lista = new ArrayList();
				// System.out.println("esta cosa es nula");
			} else {
				// System.out.println("el listado tiene ="+listaMotivosVO.getTempMotivoVO().size());
				TempMotivoVO tempMotivoVO;
				int cont = 0;
				lista = listaMotivosVO.getTempMotivoVO();
				itMotivos = lista.iterator();
				while (itMotivos.hasNext()) {
					tempMotivoVO = (TempMotivoVO) itMotivos.next();
					if (tempMotivoVO.getEstudiante() == motivoVO
							.getEstudiante()
							&& tempMotivoVO.getDia().equals(motivoVO.getDia())) {
						remov = cont;
						// System.out.println("encontro");
					}
					cont++;
				}
			}
			if (lista == null)
				lista = new ArrayList();
			if (remov != -1) {
				lista.remove(remov);
				// System.out.println("removin");
			}
			lista.add(motivoVO);
			listaMotivosVO.setTempMotivoVO(lista);
			session.setAttribute("listaMotivosVO", listaMotivosVO);
			// System.out.println("Total de motivos guardados=="+lista.size());
			break;
		}

		// System.out.println("estamos en ausencias");

		dispatcher[0] = String.valueOf(Params.INCLUDE);
		dispatcher[1] = MOTIVO;

		return dispatcher;
	}
}
