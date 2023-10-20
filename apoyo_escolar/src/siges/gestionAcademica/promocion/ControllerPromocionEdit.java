package siges.gestionAcademica.promocion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.gestionAcademica.promocion.dao.PromocionDAO;
import siges.login.beans.Login;
import siges.util.Properties;

/**
 * Nombre: ControllerEvaluacionEdit<BR>
 * Descripcinn: Controla la vista de los formularios de evaluacion en linea de
 * logros, descriptores, areas ,asignaturas y promocion <BR>
 * Funciones de la pngina: Proporciona los datos necesarios a la vista para
 * pintar los formularios de evaluacion en linea y redirige el control a la
 * vista respectiva <BR>
 * Entidades afectadas: tablas de evaluacion en modo de solo lectura <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public class ControllerPromocionEdit extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private PromocionDAO evaluacionDAO;//
	private ResourceBundle rb;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.promocion.bundle.promocion");
		Login login = null;
		String sig;
		String ant;
		String er;
		String home;
		int tipo;
		sig = getServletConfig().getInitParameter("sig");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		cursor = new Cursor();
		err = false;
		mensaje = null;
		evaluacionDAO = new PromocionDAO(cursor);
		if (request.getParameter("tipo") == null
				|| ((String) request.getParameter("tipo")).equals("")) {
			borrarBeans(request);
			request.getSession().removeAttribute("editar");
			tipo = 1;
		} else
			tipo = Integer.parseInt((String) request.getParameter("tipo"));
		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		login = (Login) request.getSession().getAttribute("login");
		request.setAttribute("Rollink", login.getNomPerfil());
		request.setAttribute("Vigenciaact",
				Long.toString(login.getVigencia_inst()));
		request.setAttribute("Validaviginst", login.getInstId());
		validarvigprom(request, login.getInstId());
		switch (tipo) {
		case 1:// promocion
			editarPromocion(request, login);
			break;

		}
		if (err) {
			request.setAttribute("mensaje", mensaje);
			return er;
		}
		// System.out.println("aqui esta"+sig);
		return sig;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request) {
		request.getSession().removeAttribute("filtroEvaluacion");
	}

	public void validarvigprom(HttpServletRequest req, String ins) {
		try {
			Collection colgg[] = evaluacionDAO.getEstadoPromo(ins);
			req.setAttribute("Validavig", colgg[0]);
		} catch (Exception e) {
			System.out.println("error validar vigencia promocion");
		}
	}

	/**
	 * Funcion: Pone en session los datos necesarios para la vista del
	 * formulario de filtro de Promocion de estudiantes<BR>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void editarPromocion(HttpServletRequest request, Login login)
			throws ServletException, IOException {
		String s;
		Collection list;
		Object[] o;
		try {
			request.getSession().removeAttribute("editar");
			Collection listas[] = new Collection[3];
			Collection parametros[] = new Collection[3];
			String consultas[] = { rb.getString("filtroGradoInstitucion2"),
					rb.getString("listaMetodologiaInstitucion"),
					rb.getString("filtroGrupoGradoInstitucion") };
			for (int zz = 0; zz < parametros.length; zz++) {
				parametros[zz] = new ArrayList();
			}
			int z = 0;
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			// grados por institucion
			parametros[0].add(o);
			// metodologia
			parametros[1].add(o);
			// grupos por grado,institucion,sede,jornada
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getInstId();
			parametros[2].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getSedeId();
			parametros[2].add(o);
			o = new Object[2];
			o[0] = Properties.ENTEROLARGO;
			o[1] = login.getJornadaId();
			parametros[2].add(o);
			listas = evaluacionDAO.getFiltro(consultas, parametros);
			request.setAttribute("filtroGrado2", listas[0]);
			request.setAttribute("filtroMetodologia", listas[1]);

			List listasTipoProm = new ArrayList();
			Object[] o2 = new Object[2];
			o2[0] = "1";
			o2[1] = "Semestre 1";
			listasTipoProm.add(o2);

			o2 = new Object[2];
			o2[0] = "2";
			o2[1] = "Semestre 2";
			listasTipoProm.add(o2);

			o2 = new Object[2];
			o2[0] = "3";
			o2[1] = "Anual";
			listasTipoProm.add(o2);

			request.setAttribute("filtroTipoProm", listasTipoProm);

			request.setAttribute("filtroGrupo2", listas[2]);
		} catch (Throwable th) {
			throw new ServletException(th);
		}
	}

	/**
	 * Trae de session la credencial de acceso del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (((Login) request.getSession().getAttribute("login")) == null)
			return false;
		return true;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
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
	 * Recibe la peticion por el metodo Post de HTTP
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
	 **/
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
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}
}