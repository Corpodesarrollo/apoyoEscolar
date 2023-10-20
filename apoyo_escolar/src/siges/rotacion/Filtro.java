package siges.rotacion;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import siges.dao.*;
import siges.rotacion.beans.Horario;
import siges.rotacion.dao.RotacionDAO;

public class Filtro extends HttpServlet {
	public static final long serialVersionUID = 1;
	public Cursor cursor= new Cursor();
	public RotacionDAO dao = new RotacionDAO(cursor);
	
	/**
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Collection list = new ArrayList();
		int combo;
		String sig = "/rotacion/filtro.jsp";
		try {
			combo = request.getParameter("combo") != null ? Integer.parseInt((String) request.getParameter("combo")) : -1;
			Horario h = null;
			String docente="";
			switch (combo) {
			case 1:
				// Carga docentes
				h = new Horario();
				h.setInst(request.getParameter("inst"));
				h.setSede(request.getParameter("sede"));
				h.setJornada(request.getParameter("jor"));
				h.setMetodologia(request.getParameter("met"));
				h.setGrado(request.getParameter("gra"));
				h.setAsignatura(request.getParameter("asi"));
				h.setVigencia(Integer.parseInt(request.getParameter("vig")));
				list = dao.getDocentes(h);
				request.setAttribute("var", "1");
				request.setAttribute("col1", list);
				
				break;
			case 2:
				// Carga ASIG
				h = new Horario();
				h.setInst(request.getParameter("inst"));
				h.setSede(request.getParameter("sede"));
				h.setJornada(request.getParameter("jor"));
				h.setMetodologia(request.getParameter("met"));
				h.setGrado(request.getParameter("gra"));
				h.setVigencia(Integer.parseInt(request.getParameter("vig")));
				list = dao.getAsignaturas(h);
				request.setAttribute("var", "2");
				request.setAttribute("col1", list);
				break;
			case 3:
				// Carga ASIG
				h = new Horario();
				h.setInst(request.getParameter("inst"));
				h.setSede(request.getParameter("sede"));
				h.setJornada(request.getParameter("jor"));
				h.setMetodologia(request.getParameter("met"));
				h.setGrado(request.getParameter("gra"));
				h.setVigencia(Integer.parseInt(request.getParameter("vig")));
				list = dao.getAsignaturas(h);
				request.setAttribute("var", "3");
				request.setAttribute("col1", list);
				break;
			case 4:
				docente=request.getParameter("docente");
				String vigencia=request.getParameter("vig");
				Integer horasdif=dao.getHorasLibresxDocente(docente,vigencia);
				request.setAttribute("var", "4");
				request.setAttribute("col1", horasdif);
				break;
			case 5:
				docente=request.getParameter("docente");
				String vigencia1=request.getParameter("vig");
				Integer horasdif1=dao.getHorasLibresxDocente(docente,vigencia1);
				request.setAttribute("var", "5");
				request.setAttribute("col1", horasdif1);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sig;
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(2, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}
}
