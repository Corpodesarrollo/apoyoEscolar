package siges.plantilla.comportamiento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.common.vo.Url;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.login.beans.Login;
import siges.plantilla.Excel2;
import siges.plantilla.beans.FiltroComportamiento;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.comportamiento.dao.ComportamientoDAO;

public class Nuevo extends Service {

	private String FICHA_COMPORTAMIENTO;
	private ComportamientoDAO comportamientoDAO;
	private Cursor cursor;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		comportamientoDAO = new ComportamientoDAO(cursor);
		FICHA_COMPORTAMIENTO = config.getInitParameter("FICHA_COMPORTAMIENTO");
		Login usuVO = (Login) session.getAttribute("login");
		FiltroComportamiento filtroPlantillaComportamiento = (FiltroComportamiento) session
				.getAttribute("filtroPlantillaComportamiento");
		if (filtroPlantillaComportamiento != null) {
			filtroPlantillaComportamiento.setFilInstitucion(Long
					.parseLong(usuVO.getInstId()));
			filtroPlantillaComportamiento.setFilSede(Integer.parseInt(usuVO
					.getSedeId()));
			filtroPlantillaComportamiento.setFilJornada(Integer.parseInt(usuVO
					.getJornadaId()));
		}
		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_DEFAULT);
		// System.out.println("process CMD " + CMD + " TIPO " + TIPO);
		switch (TIPO) {
		case ParamsVO.FICHA_COMPORTAMIENTO:
			initComportamiento(request, usuVO);
			switch (CMD) {
			case Params.CMD_NUEVO:
				session.removeAttribute("filtroPlantillaComportamiento");
				break;
			case Params.CMD_BUSCAR:
				comportamientoBuscar(request, filtroPlantillaComportamiento,
						usuVO);
				break;
			}
			break;
		}
		dispatcher[0] = String.valueOf(Params.FORWARD);
		dispatcher[1] = FICHA_COMPORTAMIENTO;
		return dispatcher;
	}

	private void initComportamiento(HttpServletRequest request, Login login) {
		long inst = Long.parseLong(login.getInstId());
		int sed = Integer.parseInt(login.getSedeId());
		int jor = Integer.parseInt(login.getJornadaId());
		request.setAttribute("lMetodologia",
				comportamientoDAO.getAllMetodologia(inst));
		request.setAttribute("lGrado",
				comportamientoDAO.getAllGrado(inst, sed, jor));
		request.setAttribute("lGrupo",
				comportamientoDAO.getAllGrupo(inst, sed, jor));
		// request.setAttribute("lPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);
		request.setAttribute("lPeriodo",
				getListaPeriodo(login.getLogNumPer(), login.getLogNomPerDef()));
	}

	/**
	 * @function:
	 * @param request
	 * @param filtro
	 * @param login
	 * @throws ServletException
	 */
	private void comportamientoBuscar(HttpServletRequest request,
			FiltroComportamiento filtro, Login login) throws ServletException {
		try {
			// estudiante con nota
			filtro.setFilInstitucion(Long.parseLong(login.getInstId()));
			List listaEstudiantes = comportamientoDAO
					.getEstudianteComportamientoPlantilla(filtro);
			// System.out.println("comportamiento LISTA EST ");
			if (listaEstudiantes == null || listaEstudiantes.size() == 0) {
				request.setAttribute("mensaje",
						"No hay estudiantes en el grupo indicado");
				return;
			}
			// System.out.println("comportamiento LISTA EST2 ");
			// escala valorativa
			// List listaEscala = comportamientoDAO.getEscalaComportamiento();

			// poner atributos de encabezado
			filtro.setFilNombreInstitucion(login.getInst());
			filtro.setFilNombreSede(login.getSede());
			filtro.setFilNombreJornada(login.getJornada());
			filtro.setFilNombreMetodologia(login.getMetodologia());
			filtro.setFilTipo(ParamsVO.FICHA_COMPORTAMIENTO);
			List periodos = getListaPeriodo(login.getLogNumPer(),
					login.getLogNomPerDef());
			// System.out.println("comportamiento LISTA EST 3");
			comportamientoDAO.getParametrosComportamiento(filtro, periodos);
			List listaEscala = comportamientoDAO.getEscalaNivelEval(filtro);

			if (filtro.getRangosCompletos() != 1) {
				String msg = "\n No se puede generar la plantilla porque ann no se "
						+ "\n ha especificado n esta incompleta la escala valorativa"
						+ "\n para este grupo.";

				request.setAttribute("mensaje", msg);
				return;
			}

			if (listaEscala == null || listaEscala.size() == 0) {
				request.setAttribute("mensaje", "No hay escala valorativa");
				return;
			}

			Url url = new Url();
			ResourceBundle rb = ResourceBundle
					.getBundle("siges.plantilla.bundle.plantilla");
			url.setPathPlantilla(Ruta2.get(context,
					rb.getString("comportamiento.PathPlantilla")));
			url.setNombrePlantilla(rb.getString("comportamiento.Plantilla"));
			url.setPathDescarga(Ruta.get(
					context,
					rb.getString("comportamiento.PathDownload") + "."
							+ login.getUsuarioId()));
			url.setNombreDescarga(getNombreComportamiento(filtro));
			url.setPathRelativo(Ruta.getRelativo(
					context,
					rb.getString("comportamiento.PathDownload") + "."
							+ login.getUsuarioId()));
			Excel2 excel2 = new Excel2();
			excel2.plantillaComportamiento(filtro, listaEstudiantes,
					listaEscala, url);
			request.setAttribute("plantilla",
					url.getPathRelativo() + url.getNombreDescarga());
			request.setAttribute("tipoArchivo", "xls");
			filtro.setFormaEstado("1");
		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}
	}

	private String getNombreComportamiento(FiltroComportamiento filtro) {
		java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		String sed = formatear(filtro.getFilNombreSede().length() > 20 ? filtro
				.getFilNombreSede().substring(0, 20) : filtro
				.getFilNombreSede());
		String jo = formatear(filtro.getFilNombreJornada());
		String met = formatear(filtro.getFilNombreMetodologia());
		String gra = formatear(filtro.getFilNombreGrado());
		String gru = formatear(filtro.getFilNombreGrupo());
		String per = formatear(filtro.getFilNombrePeriodo());
		String fec = formatear(d.toString());
		return ("Evaluacion_Comportamiento" + "_Sed_" + sed + "_Jor_" + jo
				+ "_Met_" + met + "_Gra_" + gra + "_Gru_" + gru + "_Per_" + per
				+ "_(" + fec + ").xls");
	}

	private static String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'n')
				.replace('n', 'N').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'c')
				.replace(':', '_').replace('.', '_').replace('/', '_').replace(
				'\\', '_'));
	}

	/*
	 * MODIFICACION PERIODOS EVVALUACION EN LINEA 12-03-10 WG
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
