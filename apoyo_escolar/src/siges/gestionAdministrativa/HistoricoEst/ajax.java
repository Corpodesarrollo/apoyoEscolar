package siges.gestionAdministrativa.HistoricoEst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.util.Acceso;

/**
 * Servlet implementation class HistoricoEstudiante
 */
public class ajax extends HttpServlet {
	private String inte;
	private Collection col;
	private String nomcomp = null;
	private Collection historico = new ArrayList();

	public String process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		int tipo;
		inte = "/siges/gestionAdministrativa/HistoricoEst/HistoricoEstudiante.jsp";
		try {

			if (req.getParameter("tiposervlet") == null
					|| req.getParameter("tiposervlet").equals(""))
				tipo = 0;
			else {
//				System.out.println("tipo jsp " + (String) req.getParameter("tiposervlet"));
//				System.out.println("tipodoc jsp " + (String) req.getParameter("tipodoc").trim());
//				System.out.println("numdoc jsp " + (String) req.getParameter("numdoc").trim());
				tipo = Integer.parseInt((String) req
						.getParameter("tiposervlet"));
			}

			if (tipo == 1) {
				nomcomp = Acceso.nombrecompleto(
						(String) req.getParameter("tipodoc").trim(),
						(String) req.getParameter("numdoc").trim());
				req.setAttribute("nomcompleto1", nomcomp);
				System.out.println("nombre completo " + nomcomp);
				historico = Acceso.getgradocol(
						(String) req.getParameter("tipodoc").trim(),
						(String) req.getParameter("numdoc").trim());
				req.setAttribute("filtroGradoColegiohis", historico);
			}
			return inte;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		}

	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String s = process(req, res);
		if (s != null && !s.equals(""))
			ir(2, s, req, res);
	}

	/**
	 * recibe el url de destino, el request y el response y manda el control a
	 * la pagina indicada
	 * 
	 * @param: int a
	 * @param: String s
	 * @param: HttpServletRequest request
	 * @param: HttpServletResponse response
	 */
	public void ir(int a, String s, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(req, res);
		else
			rd.forward(req, res);
	}

}
