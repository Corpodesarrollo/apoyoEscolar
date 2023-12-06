package siges.autenticaPaginas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.util.Acceso;
import siges.util.AccesoPaginas;
import siges.util.Logger;
import util.BitacoraCOM;

/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		04/09/2023		JAMES TELLEZ		Se genera servicio para autenticar paginas
 *
*/

/**
 * Nombre: ControllerAutenticaPag<br>
 * Descripcinn: Controla el proceso de logeado en el sistema para una pagina<br>
 * Funciones de la pangina: Validar existencia, Seguridad y Seguimiento<br>
 * 
 * @author LINKTIC <br>
 * @version v1.0 <br>
 */
public class ControllerAutenticaPag extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String inte;
	private String home;
	private String log2;
	private String log_;
	private String contrasena;
	private String contrasena2;
	private String URLPag;
	private BitacoraCOM bitacoraCOM;

	public String autenticacion(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String view = null;
		Login login = null;
		String mensaje = null;

		HttpSession session = req.getSession();
		removerObjetos(req);

		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		session.setAttribute("numeroDocumento", log);
		session.setAttribute("loginBitacora", log+'-'+password);
		
		bitacoraCOM = new BitacoraCOM();
		String loginBitacora = (String)session.getAttribute("loginBitacora");

		// VALIDACION NORMAL DE ACCESO
		String[][] params = AccesoPaginas.autorizado(log, password);

		try {
			if (params == null) {

				params = AccesoPaginas.autorizadoEstudiante(log, password);

				if (params != null) {

					login = AccesoPaginas.getUsuarioEstudiante(params);

					if (login == null) {
						Logger.print(log, "Login. No fue posible obtener los datos del estudiante: " + log, 0, 1,
								this.toString());
						mensaje = ("No fue posible obtener los datos del estudiante");
						req.setAttribute("mensaje", mensaje);
						entregarRespuesta(res, "ERROR", log,
								String.format("Login. No fue posible obtener los datos del estudiante: %s", log));
						view = log2;
						return view;
					}

					session = req.getSession(true);
					session.setAttribute("login", login);
					removerObjetos(req);

					Logger.print(log, "Login. Acceso de estudiante satisfactorio: " + session.toString(), 0, 1,
							this.toString());
					// entregarRespuesta(res, "NORMAL", login,
					// String.format("Login. Acceso de estudiante satisfactorio:
					// %s", login.getUsuarioId()));

					// TODO: Revisar si es necesario en este punto el cambio de
					// la contraseña temporal
					view = inte;

					return view;

				} else {
					Logger.print(log, "Login. Los datos no corresponden a un usuario registrado: " + log, 0, 1,
							this.toString());
					mensaje = ("Los datos no corresponden a un usuario registrado");
					req.setAttribute("mensaje", mensaje);
					entregarRespuesta(res, "ERROR", log,
							String.format("Login. Los datos no corresponden a un usuario registrado: %s", log));
					view = log2;

					return view;
				}
			}

			// TIENE MAS DE UN ACCESO A LA BD
			if (params.length > 1 && params[0][4].equals("0")) {

				// SI LA CONTRASEÑA ES TEMPORAL DEBE CAMBIARLA OBLIGATORIAMENTE
				if (params[0][5] != null && params[0][5].equals("1")) {

					return contrasena;

				} else {

					req.setAttribute("log", log);
					req.setAttribute("pass", password);
					req.setAttribute("multiple", AccesoPaginas.getPerfiles(params));
					view = log_;

					Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());
					// entregarRespuesta(res, "NORMAL",
					// AccesoPaginas.getPerfiles(params), "Login. Autenticacion
					// exitosa.");
					session = req.getSession(true);
					session.setAttribute("login", login);
					return view;

				}
			}

			// TIENE MAS DE UN ACCESO A LA BD
			if (params.length > 1 && params[0][4].equals("1")) {

				// SI LA CONTRASEÑA ES TEMPORAL DEBE CAMBIARLA OBLIGATORIAMENTE
				if (params[0][5].equals("1")) {

					return contrasena;

				} else {
					login = AccesoPaginas.getUsuario(log, password, params, null, null, null, null);
					session.setAttribute("login", login);
					req.setAttribute("log", log);

					try {
						req.setAttribute("pass", password);
					} catch (Exception e) {
						e.printStackTrace();
						Logger.print(log, "Error al agregar password: " + e.getMessage(), 0, 1, this.toString());
						entregarRespuesta(res, "ERROR", log,
								String.format("Error al agregar password: %s", e.getMessage()));
					}

					ponerPerfilesTodos(req, params);
					view = log_;
					Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());

					Map<String, Object> resp = new HashMap<String, Object>();
					resp.put("LoginInst", req.getAttribute("LoginInst"));
					resp.put("LoginJor", req.getAttribute("LoginJor"));
					resp.put("multiple4", req.getAttribute("multiple4"));

					// entregarRespuesta(res, "NORMAL", login, "168: Login.
					// Autenticacion exitosa.");
					return view;
				}

			}

			// SI LA CONTRASEÑA ES TEMPORAL DEBE CAMBIARLA OBLIGATORIAMENTE
			if (params[0][5].equals("1")) {

				return contrasena;

			} else {

				// TIENE PERMISO DE GRANULARIDAD (BAJAR EN EL NIVEL QUE ESTA)
				if (params[0][4].equals("1") && (params[0][0].equals("2") || params[0][0].equals("4"))) {
					// System.out.println("entra a poner perfiles todos2");
					req.setAttribute("log", log);
					req.setAttribute("pass", password);
					ponerPerfiles(req, params[0][0], params[0][1]);
					view = log_;
					Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());

					Map<String, Object> resp = new HashMap<String, Object>();
					resp.put("LoginInst", req.getAttribute("LoginInst"));
					resp.put("LoginJor", req.getAttribute("LoginJor"));
					resp.put("multiple4", req.getAttribute("multiple4"));
					login = AccesoPaginas.getUsuario(log, password, params, null, null, null, null);
					// entregarRespuesta(res, "NORMAL", resp, "195: Login.
					// Autenticacion exitosa.");
					session = req.getSession(true);
					session.setAttribute("login", login);
					return view;
				}

				// USUARIO NORMAL
				login = AccesoPaginas.getUsuario(log, password, params, null, null, null, null);
				if (login == null) {
					// ERROR OBTENIENDO LOS DATOS DEL USUARIO
					Logger.print(log, "Login. No fue posible obtener los datos del usuario: " + log, 0, 1,
							this.toString());
					mensaje = ("No fue posible obtener los datos del usuario");
					entregarRespuesta(res, "ERROR", null, "Login. No fue posible obtener los datos del usuario: ");

					req.setAttribute("mensaje", mensaje);
					view = log2;
					return view;
				}

				session = req.getSession(true);
				session.setAttribute("login", login);
				// entregarRespuesta(res, "NORMAL", login, "215: Login.
				// Autenticacion exitosa.");

				removerObjetos(req);

				String cambio = req.getParameter("cambio");
				if (cambio != null && cambio.equals("1")) {
					req.setAttribute("serv", contrasena);
				} else {
					req.setAttribute("serv", home);
				}

				view = inte;
				Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());

				return view;
			}

		} catch (Exception e) {
			Logger.print(log, "Login. Error Autenticacion." + e.getMessage(), 0, 1, this.toString());
			entregarRespuesta(res, "ERROR", log,
					String.format("232: Login. Error Autenticacion. error: %s", e.getMessage()));
			return null;
		}
	}

	public String abrirSesion(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		String mensaje = null;
		Login login = null;

		String[][] params = AccesoPaginas.autorizado(log, password);

		if (params == null) {
			params = AccesoPaginas.autorizadoEstudiante(log, password);

			if (params != null) {
				login = AccesoPaginas.getUsuarioEstudiante(params);
			}
		} else {
			login = AccesoPaginas.getUsuario(log, password, params, null, null, null, null);
		}

		if (params == null) {
			Logger.print("0", "Los datos no corresponden a un usuario registrado: " + log, 0, 1, this.toString());
			mensaje = ("-Los datos no corresponden a un usuario registrado-");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}

		ponerPerfilesTodos(req, params);
		req.setAttribute("multiple", AccesoPaginas.getPerfiles(params));

		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: " + log, 0, 1, this.toString());
			mensaje = ("No fue posible obtener los datos del usuario");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		session = req.getSession(true);
		session.setAttribute("login", login);
		req.setAttribute("log", log);
		req.setAttribute("pass", password);
		removerObjetos(req);

		/*
		 * String cambio = req.getParameter("cambio"); if (cambio != null &&
		 * cambio.equals("1")) { req.setAttribute("serv", contrasena); } else {
		 * req.setAttribute("serv", home); }
		 */
		view = inte;
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1, this.toString());
		return view;
	}

	public String cierreSesion(HttpServletRequest req, HttpServletResponse res) throws Exception {
		removerObjetos(req);
		req.getSession().invalidate();
		// entregarRespuesta(res, "NORMAL", null, String.format("238: Login. Se
		// ha cerrado la sesion correctamente"));
		return null;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String s = process(req, res);
		crearIframe(URLPag, res);

		/*
		 * if (s != null && !s.equals("")) ir(2, s, req, res);
		 */

	}

	public boolean existe(String buscar) {
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (!r.next()) {
				return false;
			} else {
				return true;
			}
		} catch (InternalErrorException in) {
			return false;
		} catch (Exception sqle) {
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String existeRegistro(String buscar) {
		Connection cn = null;
		ResultSet r = null;
		Statement st = null;
		try {
			cn = getConnection();
			st = cn.createStatement();
			r = st.executeQuery(buscar);
			if (r == null || !r.next()) {
				return null;
			} else {
				return r.getString(1);
			}
		} catch (InternalErrorException in) {
			return null;
		} catch (Exception sqle) {
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public Connection getConnection() throws InternalErrorException {
		try {
			Connection con = DataSourceManager.getConnection(1);
			return con;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}

	public String getUser(HttpServletRequest request) {
		String user = null;
		if (request.getParameter("user") != null && !((String) request.getParameter("user")).equals("")) {
			return (String) request.getParameter("tipo");
		}
		return user;
	}

	/**
	 * recibe el url de destino, el request y el response y manda el control a
	 * la pagina indicada
	 * 
	 * @param: int
	 *             a
	 * @param: String
	 *             s
	 * @param: HttpServletRequest
	 *             request
	 * @param: HttpServletResponse
	 *             response
	 */
	public void ir(int a, String s, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(request, response);
		else
			rd.forward(request, response);
	}

	/**
	 * Funcinn: Colocar perfiles de usuario a seleccionar en la el formulario de
	 * login<br>
	 * 
	 * @param HttpServletRequest
	 *            req
	 * @param String
	 *            mun
	 */
	public void ponerPerfiles(HttpServletRequest req, String niv, String jer) {
		int nivel = Integer.parseInt(niv.trim());
		Collection col[] = AccesoPaginas.ponerPerfiles(nivel, Long.parseLong(jer));
		switch (nivel) {
		case 2:
			req.setAttribute("LoginInst", col[0]);
			req.setAttribute("LoginSede", col[1]);
			req.setAttribute("LoginJor", col[2]);
			req.setAttribute("multiple2", "1");
			break;
		case 4:
			req.setAttribute("LoginSede", col[0]);
			req.setAttribute("LoginJor", col[1]);
			req.setAttribute("multiple3", "1");
			break;
		}
	}

	public void ponerPerfilesTodos(HttpServletRequest req, String[][] params) {
		Collection col[] = AccesoPaginas.ponerPerfilesTodos(params);
		req.setAttribute("LoginInst", col[0]);
		req.setAttribute("LoginSede", col[1]);
		req.setAttribute("LoginJor", col[2]);
		req.setAttribute("multiple4", "1");
	}

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		inte = getServletContext().getInitParameter("main");
		home = getServletContext().getInitParameter("home");
		log2 = getServletContext().getInitParameter("login");
		log_ = getServletContext().getInitParameter("login2");
		contrasena = getServletContext().getInitParameter("contrasena");
		contrasena2 = getServletContext().getInitParameter("contrasena2");
		URLPag = ((String) req.getParameter("URLPag")).trim();

		String view = "";
		String key = req.getParameter("key");
		String hfin = req.getParameter("hfin");
		String hora = req.getParameter("hora");
		String band = req.getParameter("bandera");
		String hini = req.getParameter("hinicio");

		try {
			Login login = (Login) req.getSession().getAttribute("login");

			if (key == null) {
				if (login != null) {
					Logger.print("0", "Login. Cierre de sesion de usuario " + login.getUsuarioId(), 0, 1,
							this.toString());
					entregarRespuesta(res, "ERROR", login.getUsuarioId(),
							String.format("Login. Cierre de sesion de usuario, %s ", login.getUsuarioId()));
				}

				Logger.print("0", "Login. Llave principal vacia, valor variable key = " + key, 0, 1, this.toString());
				entregarRespuesta(res, "ERROR", key,
						String.format("Login. Llave principal vacia, valor variable key =  %s ", key));
				removerObjetos(req);
				req.getSession().invalidate();

				return log2;

				/**
				 * MODIFICADO POR: Mauricio Coral Fecha de modificacinn:
				 * 25/02/2013 Token: 48982jfsidjflwkdfok3094u Al iniciar la
				 * aplicacinn nos redirecciona directamente a la pngina
				 * principal sin pasar por login.jsp Si se requiere restablecer
				 * la pngina de login cambiar "autenticacion(req)" por "log2"
				 */
				// return autenticacion(req); //Autenticacinn inicial

			} else if (key.equalsIgnoreCase("-1")) {
				return cierreSesion(req, res);
			}

			String horaValida = validacionHora(req, band, hini, hfin, hora);

			if (horaValida != null) {
				entregarRespuesta(res, "NORMAL", horaValida, "Se valido la hora");
				return horaValida;
			}

			int llave = Integer.parseInt(key);

			switch (llave) {
			case -99:
				return usuarioConflicto(req);
			case -999:
				return usuarioPadreFamilia(req);
			case -1:
				return cierreSesion(req, res);
			case 0:
				return autenticacion(req, res);
			case 1:
				return seleccionAcceso(req);
			case 2:
				return seleccionNivel2(req);
			case 3:
				return seleccionNivel4(req);
			case 4:
				return validacionRestringidos(req);
			case 5:
				return "/siges/gestionAdministrativa/cierreVigencia/Reporte.jsp";
			case 6:
				return "/siges/gestionAdministrativa/cierreVigencia/Reportexls.jsp";
			case 7:
				return "/siges/gestionAcademica/promocion/Reportepro.jsp";
			case 8:
				return "/siges/gestionAcademica/regNotasVigencia/ReportePDF.jsp";
			case 9:
				req.setAttribute("EncaPgEg", reporteenc(login.getInstId()));
				req.setAttribute("HombresPgEg", reporte1(login.getInstId()));
				req.setAttribute("MujeresPgEg", reporte2(login.getInstId()));
				return "/institucion/Reportec600.jsp";
			case 10:
				req.setAttribute("inst", login.getInstId());
				return "/institucion/EstadisticosPDF.jsp";
			case 11:
				req.setAttribute("inst", login.getInstId());
				return "/institucion/EstadisticosXLS.jsp";
			case 12:
				return olvidoContrasena(req);
			case 13:
				return abrirSesion(req, res);
			}

			return view;

		} catch (IllegalStateException th) {
			String log = (String) req.getParameter("login");
			Logger.print("0", "Login. Acceso fuera del rango de usuarios permitidos: " + log, 0, 1, this.toString());
			String mensaje = ("No es posible ingresar al sistema en este momento porque se ha alcanzado el límite de usuarios permitidos\nPor favor intente ingresar más tarde");
			req.setAttribute("mensaje", mensaje);
			entregarRespuesta(res, "ERROR", log, String.format(mensaje + ", %s", log));
			view = log2;

			return view;

		} catch (Exception th) {
			th.printStackTrace();
			throw new ServletException(th);
		}

	}

	public boolean registrar(String insertar) {
		Connection cn = null;
		Statement st = null;
		try {
			cn = getConnection();
			cn.setAutoCommit(false);
			st = cn.createStatement();
			cn.commit();
			cn.setAutoCommit(true);
			return true;
		} catch (InternalErrorException e) {
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void removerObjetos(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("paramAcceso");
		session.removeAttribute("imagenEscudo");
		session.removeAttribute("menu");
		session.removeAttribute("menuParam");
		session.removeAttribute("categorias");
		session.removeAttribute("serviciosolicitado");
		session.removeAttribute("mnucat");
		session.removeAttribute("mnuPublico");
		session.removeAttribute("mnuPrivado");
		session.removeAttribute("menuParam");
		req.removeAttribute("mensaje");
	}

	public Collection reporte1(String ins) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			int i = 1;
			cn = getConnection();
			pst = cn.prepareStatement(
					"select distinct decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo),nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)<=3),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=4),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=5),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=6),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=7),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=8),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=9),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=10),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=11),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=12),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=13),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=14),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=15),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=16),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=17),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=18),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=19),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)>=20),0) a from grado where gracodigo not in(62,50,30) order by decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo)");
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++) {
					o[j - 1] = rs.getString(j);
				}
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: " + e, 7, 1, "siges.util.AccesoPaginas");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Collection reporte2(String ins) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			int i = 1;
			cn = getConnection();
			pst = cn.prepareStatement(
					"select distinct decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo),nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)<=3),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=4),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=5),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=6),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=7),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=8),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=9),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=10),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=11),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=12),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=13),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=14),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=15),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=16),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=17),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=18),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=19),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)>=20),0) a from grado where gracodigo not in(62,50,30) order by decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo)");
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			pst.setInt(i++, Integer.parseInt(ins));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: " + e, 7, 1, "siges.util.AccesoPaginas");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Collection reporteenc(String ins) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = getConnection();
			pst = cn.prepareStatement("select inscodigo,inscoddane,insnombre from institucion where inscodigo=?");
			pst.setInt(1, Integer.parseInt(ins));
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0;
			while (rs.next()) {
				o = new Object[m];
				for (j = 1; j <= m; j++) {
					o[j - 1] = rs.getString(j);
				}
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close(); // System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			siges.util.Logger.print("0", "Error intentando iniciar conexion: " + e, 7, 1, "siges.util.AccesoPaginas");
			System.out.println("error: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public boolean restringidoPendiente(String log) {
		String restringido = null;
		restringido = "SELECT USULOGIN FROM USUARIOS_RESTRINGIDOS where USULOGIN like '" + log + "' AND USUESTADO=-2";
		return existe(restringido);
	}

	public String seleccionAcceso(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		session.setAttribute("loginBitacora", log+'-'+password);
		String seleccion[] = req.getParameter("perfilPedido").replace('|', ':').split(":");
		String jer = seleccion[0];
		String perf = seleccion[1];
		String[][] params = AccesoPaginas.autorizado(log, password, jer, perf);
		String mensaje = null;

		if (params == null) {
			Logger.print("0", "Los datos no corresponden a un usuario registrado: " + log, 0, 1, this.toString());
			mensaje = ("-Los datos no corresponden a un usuario registrado-");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}

		Login login = AccesoPaginas.getUsuario(log, password, params, jer, null, null, null);

		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: " + log, 0, 1, this.toString());
			mensaje = ("No fue posible obtener los datos del usuario");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		session = req.getSession(true);
		session.setAttribute("login", login);
		removerObjetos(req);
		String cambio = req.getParameter("cambio");
		if (cambio != null && cambio.equals("1")) {
			req.setAttribute("serv", contrasena);
		} else {
			req.setAttribute("serv", home);
		}
		view = inte;
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1, this.toString());
		return view;
	}

	public String seleccionNivel2(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		session.setAttribute("loginBitacora", log+'-'+password);
		String inst = (String) req.getParameter("inst");
		String sede = (String) req.getParameter("sede");
		String jornada = (String) req.getParameter("jornada");
		String inst_ = (String) req.getParameter("inst_");
		String sede_ = (String) req.getParameter("sede_");
		String jornada_ = (String) req.getParameter("jornada_");
		String[][] params = AccesoPaginas.autorizado(log, password);
		String mensaje = null;
		if (params == null) {
			Logger.print("0", "Los datos no corresponden a un usuario registrado: " + log, 0, 1, this.toString());
			mensaje = ("Los datos no corresponden a un usuario registrado");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		Login login = AccesoPaginas.getUsuario(log, password, params, null, inst, sede, jornada);
		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: " + log, 0, 1, this.toString());
			mensaje = ("No fue posible obtener los datos del usuario");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		login.setInstId(inst);
		login.setInst(inst_);
		login.setSedeId(sede);
		login.setSede(sede_);
		login.setJornadaId(jornada);
		login.setJornada(jornada_);
		String[] met = AccesoPaginas.getMetodologia(inst);
		login.setMetodologiaId(met[0]);
		login.setMetodologia(met[1]);
		login.setNivel("6");
		session = req.getSession(true);
		session.setAttribute("login", login);
		removerObjetos(req);
		String cambio = req.getParameter("cambio");
		if (cambio != null && cambio.equals("1")) {
			req.setAttribute("serv", contrasena);
		} else {
			req.setAttribute("serv", home);
		}
		view = inte;
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1, this.toString());
		return view;
	}

	public String seleccionNivel4(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		String sede = (String) req.getParameter("sede");
		String jornada = (String) req.getParameter("jornada");
		String sede_ = (String) req.getParameter("sede_");
		String jornada_ = (String) req.getParameter("jornada_");
		String[][] params = AccesoPaginas.autorizado(log, password);
		String mensaje = null;

		if (params == null) {

			Logger.print("0", "Los datos no corresponden a un usuario registrado: " + log, 0, 1, this.toString());
			mensaje = ("Los datos no corresponden a un usuario registrado");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		Login login = AccesoPaginas.getUsuario(log, password, params, null, null, sede, jornada);

		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: " + log, 0, 1, this.toString());
			mensaje = ("No fue posible obtener los datos del usuario");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		login.setSedeId(sede);
		login.setSede(sede_);
		login.setJornadaId(jornada);
		login.setJornada(jornada_);
		login.setNivel("6");
		session = req.getSession(true);
		session.setAttribute("login", login);
		removerObjetos(req);
		String cambio = req.getParameter("cambio");
		if (cambio != null && cambio.equals("1")) {
			req.setAttribute("serv", contrasena);
		} else {
			req.setAttribute("serv", home);
		}
		view = inte;
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1, this.toString());
		return view;
	}

	public String usuarioConflicto(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Login l = new Login();
		l.setUsuarioId("666");
		l.setPerfil("666");
		session = req.getSession(true);
		session.setAttribute("login", l);
		req.setAttribute("serv", "/convocatoria/ControllerEditar.do");

		return inte;
	}

	public String usuarioPadreFamilia(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Login l = new Login();
		l.setUsuarioId("666");
		l.setPerfil("666");
		session = req.getSession(true);
		session.setAttribute("login", l);
		req.setAttribute("serv", "/siges/gestionAdministrativa/padreFliaPublico/Nuevo.do");
		return inte;
	}

	public String validacionHora(HttpServletRequest req, String band, String hini, String hfin, String hora) {
		long nhini = 0;
		long nhfin = 0;
		long nhora = 0;
		String mensaje = null;
		// SISTEMA FUERA DE SERVICIO
		if (band != null && band.equals("1")) {
			removerObjetos(req);
			req.getSession().invalidate();
			mensaje = ("Sistema fuera de servicio temporalmente");
			req.setAttribute("mensaje", mensaje);
			return log2;
		}
		// SISTEMA EN RANGO DE HORAS
		if (band != null && band.equals("2")) {
			if (hini != null && !hini.equals("")) {
				nhini = Long.parseLong(hini);
			}
			if (hfin != null && !hfin.equals("")) {
				nhfin = Long.parseLong(hfin);
			}
			if (hora != null && !hora.equals("")) {
				nhora = Long.parseLong(hora);
			}
			if (nhora >= nhini && nhora < nhfin) {
				removerObjetos(req);
				req.getSession().invalidate();
				mensaje = ("Sistema inhabilitado temporalmente. Horario de Servicio entre las " + hfin + " horas y las "
						+ hini + " horas.");
				req.setAttribute("mensaje", mensaje);
				return log2;
			}
		}
		return null;
	}

	public String validacionRestringidos(HttpServletRequest req) {
		String pag = "/login.jsp";
		/* mirar que viene en la solicitud */
		String option = req.getParameter("option");
		String log = (String) req.getParameter("login");
		String consulta = null;
		String mensaje = null;
		if (option != null) {
			if (option.equals("-1")) {// no habilitar
				consulta = "update USUARIOS_RESTRINGIDOS set USUESTADO=-2,usufecha=sysdate where USULOGIN like '" + log
						+ "'";
				registrar(consulta);
				String sms = "Favor enviar el correo a adminsiges@gmail.com para que su informacion sea recuperada";
				mensaje = (sms);
				req.setAttribute("mensaje", mensaje);
			}
			if (option.equals("0")) {// habilitar
				consulta = "update USUARIOS_RESTRINGIDOS set USUESTADO=0,usufecha=sysdate where USULOGIN like '" + log
						+ "'";
				registrar(consulta);
				String sms = "Ahora puede ingresar de nuevo al sistema ya que se le permitio el acceso";
				mensaje = (sms);
				req.setAttribute("mensaje", mensaje);
			}
		}
		return pag;
	}

	private String olvidoContrasena(HttpServletRequest req) {

		Login l = new Login();
		HttpSession session = req.getSession();

		l.setPerfil("666");
		l.setUsuarioId("666");

		session = req.getSession(true);
		session.setAttribute("login", l);

		req.setAttribute("serv", "/usuario/OlvidoContrasena.do");
		// req.setAttribute("serv", "/OlvidoContrasena.do");

		return inte;

	}

	private String transformObjectToJson(Object objeto) throws JsonProcessingException {

		String objetoRespuesta = null;
		ObjectMapper mapperTransform = new ObjectMapper();

		if (objeto != null) {
			objetoRespuesta = mapperTransform.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);
		}

		return objetoRespuesta;
	}

	private void entregarRespuesta(HttpServletResponse res, String tipoMensaje, Object data, String mensaje) {
		Map<String, Object> respuestaServicio = new HashMap<String, Object>();

		try {
			switch (tipoMensaje.toUpperCase()) {
			case "NORMAL":
				respuestaServicio.put("statusCode", "200");
				respuestaServicio.put("message", mensaje);
				respuestaServicio.put("responseData", data);

				res.setContentType("application/json");
				res.getWriter().write(transformObjectToJson(respuestaServicio));
				break;
			case "ERROR":
				respuestaServicio.put("statusCode", "404");
				respuestaServicio.put("message", mensaje);
				respuestaServicio.put("responseData", null);

				res.setContentType("application/json");
				res.getWriter().write(transformObjectToJson(respuestaServicio));
				break;

			default:
				respuestaServicio.put("statusCode", "404");
				respuestaServicio.put("message", mensaje);
				respuestaServicio.put("responseData", null);

				res.setContentType("application/json");
				res.getWriter().write(transformObjectToJson(respuestaServicio));
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			respuestaServicio.put("statusCode", "404");
			respuestaServicio.put("message",
					String.format("Se presento un error al entregar respuesta, ERROR [%s] ", e.getMessage()));
			respuestaServicio.put("responseData", null);

			try {
				res.setContentType("application/json");
				res.getWriter().write(transformObjectToJson(respuestaServicio));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void crearIframe(String URLPag, HttpServletResponse res) {

		PrintWriter out;
		try {
			res.addHeader("Set-Cookie", "SameSite=None; Secure");
			res.setContentType("text/html; charset=UTF-8");
			out = res.getWriter();
			out.println(" <html> ");
			out.println(" <head> ");
			out.println(" <style> ");
			out.println(
					" .loader-section{ width: 100vw; height: 100vh; max-width: 100%; position: fixed; top: 0; display: flex; justify-content: center; align-items: center; background-color: #ffffff; z-index:999; transition: all 1s 1s ease-out; opacity:1; } ");
			out.println(" .loaded{ opacity:0; z-index:-1; } ");
			out.println(
					" .loader { width: 120px; height: 120px; border: 13px solid #005cbb; border-bottom-color: transparent; border-radius: 50%; display: inline-block; box-sizing: border-box; animation: rotation 1s linear infinite; } ");
			out.println(
					" @keyframes rotation { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } } ");
			out.println(" </style> ");
			out.println(
					" <script> setTimeout(function(){ window.location.href = '" + URLPag + "'; }, 3000); </script> ");
			out.println(" </head> ");
			out.println(" <body> ");
			out.println(" <div class='loader-section' > ");
			out.println(" <span class='loader'></span> ");
			out.println(" </div> ");
			out.println(" </body> ");
			out.println(" </html> ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}