package siges.conflictoReportes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.conflictoReportes.beans.*;
import siges.conflictoReportes.dao.*;

/**
 * @author Administrador
 * 
 * TODO Para cambiar la plantilla de este comentario generado, vaya a Ventana -
 * Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerFiltro extends HttpServlet {

	private String mensaje;

	// private String buscar;
	private boolean err;

	private Cursor cursor;

	private Util util;

	private ResourceBundle rb;

	private Collection list, col1;

	private Object[] o;

	private ConflictoReportesDAO crDAO;

	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		rb = ResourceBundle.getBundle("conflictoReportes");
		String sig1 = "/conflictoReportes/FiltroRep1.jsp";
		String sig2 = "/conflictoReportes/FiltroRep2.jsp";
		String sig3 = "/conflictoReportes/FiltroRep3.jsp";
		String sig4 = "/conflictoReportes/FiltroRep4.jsp";
		String sig = "/conflictoReportes/filtro.jsp";
		String p_inte = this.getServletContext().getInitParameter("integrador");
		String p_home = this.getServletContext().getInitParameter("home");
		String p_login = this.getServletContext().getInitParameter("login");
		String p_error = this.getServletContext().getInitParameter("er");
		String consulta, fcategoria, fclase, catnombre, clanombre;
		String depar;
		String munic;
		String nucleo;
		String inst;
		String sede;
		String jornada;
		String forma;
		//int combo;
		cursor = new Cursor();
		util = new Util(cursor);
		int tipo;
		try {
			Login login = (Login) session.getAttribute("login");
			ConflictoFiltro cf = (ConflictoFiltro) session.getAttribute("filtroreportes");
			crDAO = new ConflictoReportesDAO(cursor);

			if (!asignarBeans(request)) {
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return (p_error);
			}

			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				tipo = 0;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}

			forma = (String) request.getParameter("forma");
			request.setAttribute("forma", forma);
			List listaPeriodos = new ArrayList();
			if(tipo == 0){
				tipo =2;
			}
			
			switch (tipo) {
			case 0:

				break;
			case 1:
				int z = 0;
				list = new ArrayList();
				o = new Object[2];
				o[z++] = new Integer(java.sql.Types.BIGINT);
				o[z++] = login.getInstId();
				list.add(o);
				request.setAttribute("filtroSedeF", crDAO.getFiltro(rb.getString("filtroSedeInstitucion"), list));
				request.setAttribute("filtroJornadaF", crDAO.getFiltro(rb.getString("filtroSedeJornadaInstitucion"), list));
				request.setAttribute("filtroMetodologia", crDAO.getFiltro(rb.getString("filtroMetodologia"), list));
				sig1 = sig2;
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
				break;
			case 2:
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
   		        z = 0;
				list = new ArrayList();
				o = new Object[2];
				o[z++] = new Integer(java.sql.Types.BIGINT);
				o[z++] = login.getInstId();
				list.add(o);
				request.setAttribute("filtroSedeF", crDAO.getFiltro(rb.getString("filtroSedeInstitucion"), list));
				request.setAttribute("filtroJornadaF", crDAO.getFiltro(rb.getString("filtroSedeJornadaInstitucion"), list));
				request.setAttribute("filtroMetodologia", crDAO.getFiltro(rb.getString("filtroMetodologia"), list));
   		        if(login.getNivel().trim().equalsIgnoreCase("4") || login.getNivel().trim().equalsIgnoreCase("6")){
	   		    	Collection list;
			    	Object[] o;
			    	String s;
			    	list = new ArrayList();
			    	o = new Object[2];
	   		    	
	   		    	list = new ArrayList();
	   		    	o = new Object[2];
	   		    	o[0] = new Integer(java.sql.Types.INTEGER);
	   		    	o[1] = login.getInstId();
	   		    	list.add(o);
	   		    	if (request.getSession().getAttribute("filtroJornadaF") == null)
	   		    		request.getSession().setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
	   		    	
	   		    	list = new ArrayList();
					o = new Object[2];
					o[0] = new Integer(java.sql.Types.BIGINT);
					o[1] = login.getInstId();
					list.add(o);
					col1 = util.getFiltro(rb.getString("CargaSedes"), list);
					request.setAttribute("var", "4");
					request.setAttribute("CargaSedes", col1);
	   		    	
				} 
				sig1 = sig3;
				break;
			case 3:
				// carga instituciones
				munic = (String) request.getParameter("loc");
				// nucleo=(String)request.getParameter("nucleo");
				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = munic;
				list.add(o);
				col1 = util.getFiltro(rb.getString("CargaInstituciones"), list);

				request.setAttribute("var", "3");
				request.setAttribute("col1", col1);
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
				return sig;
			case 4:
				// Carga sedes
				inst = (String) request.getParameter("col");
				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = inst;
				list.add(o);
				col1 = util.getFiltro(rb.getString("CargaSedes"), list);
				request.setAttribute("var", "4");
				request.setAttribute("col1", col1);
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
				return sig;
			case 5:
				// Carga jornada
				inst = (String) request.getParameter("col");
				sede = (String) request.getParameter("sed");
				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = inst;
				list.add(o);
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = sede;
				list.add(o);
				col1 = util.getFiltro(rb.getString("CargaJornadas"), list);
				request.setAttribute("var", "5");
				request.setAttribute("col1", col1);
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
				return sig;
			case 6:
				int zz = 0;
				list = new ArrayList();
				o = new Object[2];
				o[zz++] = new Integer(java.sql.Types.BIGINT);
				o[zz++] = login.getInstId();
				list.add(o);
				request.setAttribute("filtroSedeF", crDAO.getFiltro(rb.getString("filtroSedeInstitucion"), list));
				request.setAttribute("filtroJornadaF", crDAO.getFiltro(rb.getString("filtroSedeJornadaInstitucion"), list));
				listaPeriodos = crDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.getSession().setAttribute("listaPeriodos", listaPeriodos);
   		        }
				sig1 = sig4;
				break;

			}

			return sig1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
		}
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(2, s, request, response);
	}

	/**
	 * Funcinn: Redirige el control a otro servlet
	 * 
	 * @param int
	 *            a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null)
			return false;
		return true;
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje2(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje = "  - " + s + "\n";
	}

}
