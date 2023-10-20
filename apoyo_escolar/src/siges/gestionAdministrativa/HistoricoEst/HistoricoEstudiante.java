package siges.gestionAdministrativa.HistoricoEst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.gestionAdministrativa.HistoricoEst.bean.FiltroHistorico;
import siges.login.beans.Login;
import siges.util.Acceso;

/**
 * Servlet implementation class HistoricoEstudiante
 */
public class HistoricoEstudiante extends HttpServlet {
	private String inte, instorigen;
	private Collection col;
	private String nomcomp = null;
	private Collection historico = new ArrayList();
	private FiltroHistorico beansjsp;
	private Login login;

	public String process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		inte = "/siges/gestionAdministrativa/HistoricoEst/HistoricoEstudiante.jsp";
		String totala, totalr, totals;
		Collection motivosa = new ArrayList();
		if (req.getSession().getAttribute("login") != null) {
			login = (Login) req.getSession().getAttribute("login");
		}
		int tipo;
		try {
			if (req.getParameter("tiposervlet") == null
					|| req.getParameter("tiposervlet").equals(""))
				tipo = 0;
			else {
				tipo = Integer.parseInt((String) req
						.getParameter("tiposervlet"));
			}

			if (tipo == 1) {
				instorigen = Acceso.instestudiante(
						(String) req.getParameter("tipodoc").trim(),
						(String) req.getParameter("numdoc").trim());
				if (instorigen != null) {
					if (instorigen.equals(login.getInstId())) {
						nomcomp = Acceso.nombrecompleto((String) req
								.getParameter("tipodoc").trim(), (String) req
								.getParameter("numdoc").trim());
						req.setAttribute("numdocbean", (String) req
								.getParameter("numdoc").trim());
						req.setAttribute("tipodocbean",
								(String) req.getParameter("tipodoc"));
						req.setAttribute("nomcompleto123", nomcomp);
						historico = Acceso.getgradocol((String) req
								.getParameter("tipodoc").trim(), (String) req
								.getParameter("numdoc").trim());
						req.setAttribute("filtroGradoColegiohis", historico);
					} else
						req.setAttribute("Validacioncolegio",
								"El Estudiante no se Encuentra Matriculado en el Colegio "
										+ login.getInst());
				} else
					req.setAttribute("Validacioncolegio",
							"El Estudiante no se Encuentra Matriculado en el Sistema");

			}
			if (tipo == 3) {
				motivosa = Acceso.getasistencia((String) req.getParameter("id")
						.trim(), (String) req.getParameter("vigencia").trim());
				totala = Acceso.getasistencias((String) req.getParameter("id")
						.trim(), (String) req.getParameter("vigencia").trim());
				totalr = Acceso.getretardos((String) req.getParameter("id")
						.trim(), (String) req.getParameter("vigencia").trim());
				totals = Acceso.getsalidastarde((String) req.getParameter("id")
						.trim(), (String) req.getParameter("vigencia").trim());
				req.setAttribute("NombreCompleto",
						(String) req.getParameter("nombrecompleto").trim());
				req.setAttribute("Col", (String) req.getParameter("col").trim());
				req.setAttribute("Sede", (String) req.getParameter("sede")
						.trim());
				req.setAttribute("Grado", (String) req.getParameter("grado")
						.trim());
				req.setAttribute("Grupo", (String) req.getParameter("grupo")
						.trim());
				req.setAttribute("Metodologia",
						(String) req.getParameter("metod").trim());
				req.setAttribute("DetalleInasistencia", motivosa);
				req.setAttribute("TotalInasistencia", totala);
				req.setAttribute("TotalRetardo", totalr);
				req.setAttribute("TotalSalidas", totals);
				inte = "/siges/gestionAdministrativa/HistoricoEst/HistoricoAsistencia.jsp";
			}
			col = Acceso.getTipoDoc();
			req.setAttribute("filtroTipoDocumiento", col);
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
