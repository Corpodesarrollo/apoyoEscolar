package siges.grupoPeriodo;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.grupoPeriodo.beans.AdminVO;
import siges.grupoPeriodo.dao.AdminDAO;
import siges.login.beans.Login;

/**
 * Nombre: ControllerAbrirEdit<BR>
 * Descripcinn: Controla la vista del formulario de apertura de grupos y cierre
 * de periodo<BR>
 * Funciones de la pngina: Pone los datos necesarios en la vista de formulario
 * de consulta y redirige el control a este <BR>
 * Entidades afectadas: No aplica <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerAdminEdit extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Cursor cursor;// objeto que maneja las sentencias sql

	private AdminDAO adminDAO;

	private final int TIPO_HORARIO = 1;

	private final int TIPO_GUARDAR_HORARIO = 2;

	private final int TIPO_BITACORA = 3;

	private final int TIPO_GUARDAR_BITACORA = 4;

	private final int TIPO_DEFAULT = TIPO_HORARIO;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Login login;
		AdminVO adminVO;
		String sig, sig2, sig3, sig4;
		String er;
		int tipo;
		sig = "/grupoPeriodo/adminParametros.jsp";
		sig2 = "/grupoPeriodo/adminParametros.jsp";
		sig3 = "/grupoPeriodo/adminBitacora.jsp";
		sig4 = "/grupoPeriodo/adminBitacora2.jsp";
		er = "/error.jsp";
		cursor = new Cursor();
		adminDAO = new AdminDAO(cursor);
		if (!asignarBeans(request)) {
			request.setAttribute(
					"mensaje",
					setMensaje("Error capturando datos de sesión para el usuario"));
			return er;
		}
		login = (Login) request.getSession().getAttribute("login");
		adminVO = (AdminVO) request.getSession().getAttribute("adminVO");
		if (adminVO == null) {
			adminVO = new AdminVO();
			adminVO.setAdminInst(login.getInstId());
		}
		tipo = getTipo(request);
		try {
			switch (tipo) {
			case TIPO_HORARIO:
				adminVO.setAdminInst(login.getInstId());
				cargarHorario(request, adminVO);
				break;
			case TIPO_GUARDAR_HORARIO:
				guardarHorario(request, adminVO);
				sig = sig2;
				break;
			case TIPO_BITACORA:
				adminVO.setAdminInst(login.getInstId());
				cargarBitacora(request, login);
				sig = sig3;
				break;
			case TIPO_GUARDAR_BITACORA:
				buscarBitacora(request, adminVO, login);
				sig = sig4;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
		}
		return sig;
	}

	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("adminVO");
	}

	public int getTipo(HttpServletRequest request) {
		int tipo_ = TIPO_DEFAULT;
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			request.getSession().removeAttribute("editar");
			tipo_ = 1;
		} else {
			tipo_ = Integer.parseInt((String) request.getParameter("tipo"));
		}
		return tipo_;
	}

	public void cargarHorario(HttpServletRequest request, AdminVO adminVO)
			throws InternalErrorException {
		request.getSession().setAttribute("adminVO",
				adminDAO.getAdminHorario(adminVO));
	}

	public void cargarBitacora(HttpServletRequest request, Login l)
			throws InternalErrorException {
		request.setAttribute("listaUsuarios", adminDAO.getUsuariosBitacora(l));
	}

	public void guardarHorario(HttpServletRequest request, AdminVO adminVO)
			throws InternalErrorException {
		if (adminDAO.setAdminHorario(adminVO)) {
			request.setAttribute("mensaje",
					setMensaje("información registrada satisfactoriamente"));
		} else {
			request.setAttribute(
					"mensaje",
					setMensaje("Error guardando información "
							+ adminDAO.getMensaje()));
		}
	}

	public void buscarBitacora(HttpServletRequest request, AdminVO adminVO,
			Login l) throws InternalErrorException {
		request.setAttribute("listaBitacora", adminDAO.getBitacora(adminVO, l));
	}

	/**
	 * Trae de session la credencial de aceso del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") != null)
			return true;
		return false;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);// redirecciona la peticion a doPost
	}

	/**
	 * Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
	}

	/**
	 * Redirige el control a otro servlet
	 * 
	 * @param int a: 1=redirigir como 'include', 2=redirigir como 'forward'
	 * @param String
	 *            s
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void ir(int a, String s, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (cursor != null)
			cursor.cerrar();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public String setMensaje(String s) {
		return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n" + "  - " + s + "\n";
	}
}