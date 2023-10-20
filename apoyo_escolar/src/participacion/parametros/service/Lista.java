package participacion.parametros.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import participacion.parametros.dao.ParametrosDAO;
import participacion.parametros.vo.ParamsVO;
import siges.common.service.Service;
import siges.common.vo.Params;
import siges.dao.Cursor;
import articulacion.universidad.dao.UniversidadDAO;

public class Lista extends Service {

	private String FICHA_LISTA_DISCAPACIDAD;
	private String FICHA_LISTA_ETNIA;
	private String FICHA_LISTA_LGTB;
	private String FICHA_LISTA_OCUPACION;
	private String FICHA_LISTA_ROL;
	private String FICHA;

	private Cursor cursor;
	private ParametrosDAO parametrosDAO;

	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String dispatcher[] = new String[2];
		cursor = new Cursor();
		parametrosDAO = new ParametrosDAO(cursor);
		FICHA_LISTA_DISCAPACIDAD = config
				.getInitParameter("FICHA_LISTA_DISCAPACIDAD");
		FICHA_LISTA_ETNIA = config.getInitParameter("FICHA_LISTA_ETNIA");
		FICHA_LISTA_LGTB = config.getInitParameter("FICHA_LISTA_LGTB");
		FICHA_LISTA_OCUPACION = config
				.getInitParameter("FICHA_LISTA_OCUPACION");
		FICHA_LISTA_ROL = config.getInitParameter("FICHA_LISTA_ROL");

		int CMD = getCmd(request, session);
		int TIPO = getTipo(request, session, 0);
		// System.out.println("PARTICIPACION PARAMETRO LISTA  TIPO "+ TIPO +
		// " CND " + CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_DISCAPACIDAD:
			request.setAttribute("listaDiscapacidadVO",
					parametrosDAO.getAllListaDiscapacidades());
			request.setAttribute("guia", getRequest2(request, "guia", "-1"));
			FICHA = FICHA_LISTA_DISCAPACIDAD;
			break;
		case ParamsVO.FICHA_ETNIA:
			request.setAttribute("listaEtniasVO",
					parametrosDAO.getAllListaEtnias());
			request.setAttribute("guia", getRequest2(request, "guia", "-1"));
			FICHA = FICHA_LISTA_ETNIA;
			break;
		case ParamsVO.FICHA_LGTB:
			request.setAttribute("listaLgtbVO", parametrosDAO.getAllListaLgtb());
			request.setAttribute("guia", getRequest2(request, "guia", "-1"));
			FICHA = FICHA_LISTA_LGTB;
			break;
		case ParamsVO.FICHA_OCUPACION:
			request.setAttribute("listaOcupacionVO",
					parametrosDAO.getAllListaOcupacion());
			request.setAttribute("guia", getRequest2(request, "guia", "-1"));
			FICHA = FICHA_LISTA_OCUPACION;
			break;
		case ParamsVO.FICHA_ROL:
			request.setAttribute("listaRolVO", parametrosDAO.getAllListaRol());
			request.setAttribute("guia", getRequest2(request, "guia", "-1"));
			FICHA = FICHA_LISTA_ROL;
			break;
		default:
			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		// System.out.println("FICHA " + FICHA);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

}
