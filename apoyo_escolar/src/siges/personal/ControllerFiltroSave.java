package siges.personal;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Cursor;
import siges.dao.Util;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Salud;
import siges.login.beans.Login;
import siges.personal.beans.FiltroPersonal;
import siges.personal.beans.Personal;
import siges.personal.dao.PersonalDAO;

/**
 * Nombre: ControllerFiltroEdit Descripcion: Controla el formulario de nuevo
 * registro por parte de la orientadora Parametro de entrada: HttpServletRequest
 * request, HttpServletResponse response Parametro de salida: HttpServletRequest
 * request, HttpServletResponse response Funciones de la pagina: Procesar la
 * peticion y enviar el control al formulario de nuevo registro Entidades
 * afectadas: Tablas maestras en modo de solo lectura Fecha de modificacinn:
 * 01/12/04
 * 
 * @author Pasantes UD
 * @version $v 1.2 $
 */
public class ControllerFiltroSave extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private PersonalDAO personalDAO;//
	private Util util;
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
		Personal personal = null;
		FiltroPersonal filtro = null;
		Login login = (Login) request.getSession().getAttribute("login");
		String sig, sig2, sig3;
		String ant;
		String er;
		String home;
		String boton;
		String id;
		String id2;
		sig = getServletConfig().getInitParameter("sig");
		sig2 = getServletConfig().getInitParameter("sig2");
		sig3 = getServletConfig().getInitParameter("sig3");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		rb = ResourceBundle.getBundle("siges.personal.bundle.personal");
		cursor = new Cursor();
		err = false;
		mensaje = null;
		try {
			personalDAO = new PersonalDAO(cursor);
			util = new Util(cursor);
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("");
			if (boton.equals("Nuevo")) {
				borrarBeans(request);
				sig = sig2;
			}
			if (boton.equals("Editar")) {
				borrarBeans(request);
				request.setAttribute("tipo", "1");
				request.getSession().setAttribute("editar", "1");
				id = (request.getParameter("id") != null) ? request
						.getParameter("id") : new String("");
				id2 = (request.getParameter("id2") != null) ? request
						.getParameter("id2") : new String("");
				Convivencia convivencia;
				personal = personalDAO.asignarPersonal(id, id2);
				if (personal != null) {
					// convivencia
					convivencia = personalDAO.asignarConvivencia(id);
					if (convivencia != null) {
						convivencia.setEstado("1");
						request.getSession().setAttribute("nuevoConvivencia",
								convivencia);
						request.getSession().setAttribute("nuevoConvivencia2",
								convivencia.clone());
					}
					// salud
					Salud salud = personalDAO.asignarSalud("2", id);
					if (salud == null) {
						salud = new Salud();
					}
					// sede y jornada de la persona buscada
					if (!personal.getPercodjerar().equals("")) {
						personal.setSedejornada(personalDAO
								.getSedeJornadaPerfil(Long.parseLong(login
										.getInstId()), Long.parseLong(personal
										.getPernumdocum())));
					}
					request.getSession()
							.setAttribute("nuevoPersonal", personal);
					request.getSession().setAttribute("nuevoPersonal2",
							personal.clone());
					request.getSession().setAttribute("nuevoSalud", salud);
					request.getSession().setAttribute("nuevoSalud2",
							salud.clone());
					sig = sig2 + "?tipo=1";

					// if(
					// (login.getPerfil().trim().equals(ParamsVO.PERFIL_RECTOR)
					// ||
					// login.getPerfil().trim().equals(ParamsVO.PERFIL_ADMIN_ACADEMICO))){
					request.getSession().setAttribute("subframePer",
							"/personal/FiltroResultado.jsp");
					// }else{
					// request.getSession().removeAttribute("subframePer");
					// }

				} else {
					setMensaje(personalDAO.getMensaje());
					request.setAttribute("mensaje", mensaje);
				}
			}
			if (boton.equals("Eliminar")) {
				id = (request.getParameter("id") != null) ? request
						.getParameter("id") : new String("");
				id2 = (request.getParameter("id2") != null) ? request
						.getParameter("id2") : new String("");
				if (!personalDAO.eliminarUsuario(id, id2, login.getInstId())) {
					setMensaje(personalDAO.getMensaje());
					request.setAttribute("mensaje", mensaje);
					return er;
				}
				request.setAttribute(
						"mensaje",
						"VERIFIQUE LA SIGUIENTE INFORMACION: \n\n"
								+ "- Accesos de usuario eliminados satisfactoriamente");
				boton = "";
			}
			if (boton.equals("")) {
				filtro = (FiltroPersonal) request.getSession().getAttribute(
						"filtroPersonal");
				if (login == null || filtro == null) {
					setMensaje("- Error capturando datos de sesinn para el usuario");
					request.setAttribute("mensaje", mensaje);
					return er;
				}
				asignarListaNuevo(request, filtro, login);
			}
		} catch (Exception e) {
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (personalDAO != null)
				personalDAO.cerrar();
			if (util != null)
				util.cerrar();
		}
		// System.out.println("Controller Filtro save " + sig);
		return sig;
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("nuevoPersonal");
		request.getSession().removeAttribute("nuevoPersonal2");
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoConvivencia2");
		request.getSession().removeAttribute("subframePer");
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("filtroPersonal") == null)
			return false;
		return true;
	}

	public String getValorSQL(String a) {
		a = a.trim().replace('*', ' ').replace('\'', ' ').replace(',', ' ');
		return a;
	}

	/**
	 * Inserta los valores por defecto para el bean de información bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	/*
	 * public void asignarLista(HttpServletRequest request,FiltroPersonal
	 * filtro,Login login)throws ServletException, IOException{ String s;
	 * Collection lista=new ArrayList(); Object[] o=new Object[2]; try{
	 * s=rb.getString("personal.buscar"); if(filtro.getSede().equals("-99")){
	 * o=new Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId();
	 * lista.add(o); s+=" "+rb.getString("personal.where1"); }else{
	 * if(!filtro.getSede().equals("-99") || !filtro.getJornada().equals("-99")
	 * && filtro.getGrado().equals("-99")){ if(!filtro.getSede().equals("-99")
	 * && filtro.getJornada().equals("-99")){ o=new Object[2];
	 * o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId(); lista.add(o); o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getSede();
	 * lista.add(o); s+=" "+rb.getString("personal.where2"); } //solo jornada
	 * if(!filtro.getSede().equals("-99") &&
	 * !filtro.getJornada().equals("-99")){ o=new Object[2];
	 * o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId(); lista.add(o); o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getSede();
	 * lista.add(o); o=new Object[2]; o[0]=Properties.ENTEROLARGO;
	 * o[1]=filtro.getJornada(); lista.add(o);
	 * s+=" "+rb.getString("personal.where3"); } } }
	 * if(!filtro.getTipoPersona().equals("0")){ o=new Object[2];
	 * o[0]=Properties.ENTERO;
	 * if(filtro.getTipoPersona().equals("1"))o[1]="422";
	 * if(filtro.getTipoPersona().equals("2"))o[1]="421";
	 * if(filtro.getTipoPersona().equals("3"))o[1]="423"; lista.add(o);
	 * s+=" "+rb.getString("personal.where4"); }
	 * if(!filtro.getId().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=getValorSQL(filtro.getId()); lista.add(o);
	 * s+=" "+rb.getString("personal.where5"); }
	 * if(!filtro.getNombre1().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=getValorSQL(filtro.getNombre1());
	 * lista.add(o); s+=" "+rb.getString("personal.where6"); }
	 * if(!filtro.getNombre2().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=getValorSQL(filtro.getNombre2());
	 * lista.add(o); s+=" "+rb.getString("personal.where7"); }
	 * if(!filtro.getApellido1().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=getValorSQL(filtro.getApellido1());
	 * lista.add(o); s+=" "+rb.getString("personal.where8"); }
	 * if(!filtro.getApellido2().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=getValorSQL(filtro.getApellido2());
	 * lista.add(o); s+=" "+rb.getString("personal.where9"); }
	 * switch(Integer.parseInt(filtro.getOrden())){ case 0:
	 * s+=" "+rb.getString("personal.orden1"); break; case 1:
	 * s+=" "+rb.getString("personal.orden2"); break; case 2:
	 * s+=" "+rb.getString("personal.orden3"); break; } //System.out.println(s);
	 * request
	 * .getSession().setAttribute("filtroListaPersonal",util.getFiltro(s,lista
	 * )); }catch(Exception th){ throw new ServletException(th); } }
	 */

	public void asignarListaNuevo(HttpServletRequest request,
			FiltroPersonal filtro, Login login) throws ServletException,
			IOException {
		try {
			filtro.setInsttitucion(login.getInstId().trim());
			request.getSession().setAttribute("filtroListaPersonal",
					util.getBuscarPersonal(filtro));
		} catch (Exception th) {
			throw new ServletException(th);
		}
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