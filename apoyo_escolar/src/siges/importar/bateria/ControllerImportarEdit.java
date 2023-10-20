package siges.importar.bateria;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.login.beans.Login;
import siges.util.Acceso;

/**
 * siges.importar.bateria<br>
 * Funcinn: Servicio que recibe la solicitud de importacion y devuleve el
 * formulario de filtro de recurso a importar <br>
 */
public class ControllerImportarEdit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: String<br>
	 *             Version 1.1.<br>
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sig = null, sig4, sig5;
		String er;
		int tipo;
		sig4 = getServletConfig().getInitParameter("sig4");
		sig5 = getServletConfig().getInitParameter("sig5");
		er = getServletContext().getInitParameter("error");
		Login usuVO = (Login) session.getAttribute("login");
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			session.removeAttribute("editar");
			tipo = 4;
		} else {
			String valorCampo = (String) request.getParameter("tipo");
			try {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			} catch (NumberFormatException e) {
				tipo = Integer.parseInt(valorCampo.substring(
						valorCampo.length() - 1, valorCampo.length()));
			}
		}
		if (!asignarBeans(request)) {

			request.setAttribute(
					"mensaje",
					setMensaje("Error capturando datos de sesinn para el usuario"));
			return er;
		}
		request.getSession().setAttribute(
				"logroanddesc",
				Acceso.getlogrosdesc(usuVO.getInstId(),
						Long.toString(usuVO.getVigencia_inst())));
		switch (tipo) {
		case 4:
			sig = sig4;
			break;
		case 5:
			sig = sig5;
			break;
		}
		return sig;
	}

	/**
	 * @param request
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	/**
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: boolean<br>
	 *             Version 1.1.<br>
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * @param a
	 * @param s
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * @param s
	 * <br>
	 *            Return Type: void<br>
	 *            Version 1.1.<br>
	 */
	public String setMensaje(String s) {
		String mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		return mensaje += "  - " + s + "\n";
	}
}