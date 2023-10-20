package siges.login;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.util.Acceso;
import siges.util.Logger;
import siges.login.beans.Login;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		04/05/2018		JORGE CAMACHO		Se editó la función autenticacion() para revisar el tema de la contraseña temporal
 *
*/


/**
 * Nombre: ControllerLogin<br>
 * Descripcinn: Controla el proceso de logeado en el Sistema<br>
 * Funciones de la pngina: Validar existencia, Seguridad y Seguimiento<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String inte;
	private String home;
	private String log2;
	private String log_;
	private String contrasena;
	private String contrasena2;

	public String autenticacion(HttpServletRequest req) throws Exception {
		
		String view = null;
		Login login = null;
		String mensaje = null;
		
		HttpSession session = req.getSession();
		removerObjetos(req);
		
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		session.setAttribute("numeroDocumento", log);
		
		// VALIDACION NORMAL DE ACCESO
		String[][] params = Acceso.autorizado(log, password);

		try {
			
			if (params == null) {
				
				params = Acceso.autorizadoEstudiante(log, password);
				
				if (params != null) {

					login = Acceso.getUsuarioEstudiante(params);
					
					if (login == null) {
						Logger.print(log, "Login. No fue posible obtener los datos del estudiante: "+ log, 0, 1, this.toString());
						mensaje = ("No fue posible obtener los datos del estudiante");
						req.setAttribute("mensaje", mensaje);
						view = log2;
						return view;
					}
					
					session = req.getSession(true);
					session.setAttribute("login", login);
					
					removerObjetos(req);
					
					Logger.print(log, "Login. Acceso de estudiante satisfactorio: "	+ login.getUsuarioId(), 0, 1, this.toString());
					
					// TODO: Revisar si es necesario en este punto el cambio de la contraseña temporal
					view = inte;
					
					return view;
					
				} else {
					Logger.print(log, "Login. Los datos no corresponden a un usuario registrado: " + log, 0, 1, this.toString());
					mensaje = ("Los datos no corresponden a un usuario registrado");
					req.setAttribute("mensaje", mensaje);
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
					req.setAttribute("multiple", Acceso.getPerfiles(params));
					view = log_;
					
					Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());
					
					return view;
					
				}
			}
			
			// TIENE MAS DE UN ACCESO A LA BD
			if (params.length > 1 && params[0][4].equals("1")) {
				
				// SI LA CONTRASEÑA ES TEMPORAL DEBE CAMBIARLA OBLIGATORIAMENTE				
				if (params[0][5].equals("1")) {
					
					return contrasena;
					
				} else {
					login = Acceso.getUsuario(log, password, params, null, null, null, null);
					session.setAttribute("login", login);
					req.setAttribute("log", log);
					
					try {
						req.setAttribute("pass", password);
					} catch (Exception e) {
						e.printStackTrace();
						Logger.print(log, "Error al agregar password: "+ e.getMessage(), 0, 1, this.toString());
					}
					
					ponerPerfilesTodos(req, params);
					view = log_;
					Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());
					
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
					return view;
				}
				
				// USUARIO NORMAL
				login = Acceso.getUsuario(log, password, params, null, null, null, null);
				if (login == null) {
					// ERROR OBTENIENDO LOS DATOS DEL USUARIO
					Logger.print(log, "Login. No fue posible obtener los datos del usuario: " + log, 0, 1, this.toString());
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
				Logger.print(log, "Login. Autenticacinn exitosa." + log, 0, 1, this.toString());
				return view;
			}
			
		} catch(Exception e) {
			Logger.print(log, "Login. Error Autenticacion." + e.getMessage(), 0, 1, this.toString());
			return null;
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
	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		
		String s = process(req, res);
		
		if (s != null && !s.equals(""))
			ir(2, s, req, res);
		
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

	public String getUser(HttpServletRequest request){
		String user = null;
		if(request.getParameter("user")!=null && !((String)request.getParameter("user")).equals("")){
		    return (String)request.getParameter("tipo");
		}    
		return user;
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
	public void ir(int a, String s, HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
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
		Collection col[] = Acceso.ponerPerfiles(nivel, Long.parseLong(jer));
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
		Collection col[] = Acceso.ponerPerfilesTodos(params);
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

		String view = "";
		String key = req.getParameter("key");
		String hfin = req.getParameter("hfin");
		String hora = req.getParameter("hora");
		String band = req.getParameter("bandera");
		String hini = req.getParameter("hinicio");
		
		try {
			
			String horaValida = validacionHora(req, band, hini, hfin, hora);
			
			if (horaValida != null)
				return horaValida;
			
			Login login = (Login) req.getSession().getAttribute("login");
			
			if (key == null) {
				
				if (login != null) {
					Logger.print("0", "Login. Cierre de sesion de usuario "	+ login.getUsuarioId(), 0, 1, this.toString());
				}
				
				removerObjetos(req);
				req.getSession().invalidate();
				
				return log2;
				   
				/**
				 * MODIFICADO POR: Mauricio Coral 
				 * Fecha de modificacinn: 25/02/2013
				 * Token: 48982jfsidjflwkdfok3094u
				 * Al iniciar la aplicacinn nos redirecciona directamente a la pngina principal sin pasar por login.jsp
				 * Si se requiere restablecer la pngina de login cambiar "autenticacion(req)" por "log2" 
				 */
				//return autenticacion(req); //Autenticacinn inicial    
			}
			
			int llave = Integer.parseInt(key);
			
			switch (llave) {
			case -99:
				return usuarioConflicto(req);
			case -999:
				return usuarioPadreFamilia(req);
			case 0:
				return autenticacion(req);
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
			}
			
			return view;
			
		} catch (IllegalStateException th) {
			String log = (String) req.getParameter("login");
			Logger.print("0", "Login. Acceso fuera del rango de usuarios permitidos: " + log, 0, 1, this.toString());
			
			String mensaje = ("No es posible ingresar al sistema en este momento porque se ha alcanzado el límite de usuarios permitidos\nPor favor intente ingresar más tarde");
			
			req.setAttribute("mensaje", mensaje);
			
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
			pst = cn.prepareStatement("select distinct decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo),nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)<=3),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=4),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=5),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=6),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=7),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=8),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=9),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=10),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=11),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=12),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=13),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=14),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=15),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=16),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=17),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=18),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=19),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=1 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)>=20),0) a from grado where gracodigo not in(62,50,30) order by decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo)");
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
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
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
			pst = cn.prepareStatement("select distinct decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo),nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)<=3),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=4),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=5),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=6),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=7),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=8),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=9),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=10),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=11),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=12),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=13),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=14),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=15),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=16),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=17),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=18),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)=19),0) a,nvl((select count(ESTFECHANAC) from estudiante,g_jerarquia where ESTESTADO BETWEEN 100 AND 199 AND estgenero=2 and g_jergrado=gracodigo and g_jercodigo = estgrupo and g_jerinst=? and floor(months_between(sysdate,ESTFECHANAC)/12)>=20),0) a from grado where gracodigo not in(62,50,30) order by decode(gracodigo,41,-1,40,-2,60,12,61,13,gracodigo)");
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
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
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
			siges.util.Logger.print("0", "Error intentando iniciar conexion: "
					+ e, 7, 1, "siges.util.Acceso");
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
		restringido = "SELECT USULOGIN FROM USUARIOS_RESTRINGIDOS where USULOGIN like '"
				+ log + "' AND USUESTADO=-2";
		return existe(restringido);
	}

	public String seleccionAcceso(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		String seleccion[] = req.getParameter("perfilPedido").replace('|', ':')
				.split(":");
		String jer = seleccion[0];
		String perf = seleccion[1];
		String[][] params = Acceso.autorizado(log, password, jer, perf);
		String mensaje = null;
		if (params == null) {
			Logger.print(
					"0",
					"Los datos no corresponden a un usuario registrado: " + log,
					0, 1, this.toString());
			mensaje = ("-Los datos no corresponden a un usuario registrado-");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		Login login = Acceso.getUsuario(log, password, params, jer, null, null,
				null);
		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: "
					+ log, 0, 1, this.toString());
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
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1,
				this.toString());
		return view;
	}

	public String seleccionNivel2(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String view = null;
		String log = ((String) req.getParameter("login")).trim();
		String password = ((String) req.getParameter("password")).trim();
		String inst = (String) req.getParameter("inst");
		String sede = (String) req.getParameter("sede");
		String jornada = (String) req.getParameter("jornada");
		String inst_ = (String) req.getParameter("inst_");
		String sede_ = (String) req.getParameter("sede_");
		String jornada_ = (String) req.getParameter("jornada_");
		String[][] params = Acceso.autorizado(log, password);
		String mensaje = null;
		if (params == null) {
			Logger.print(
					"0",
					"Los datos no corresponden a un usuario registrado: " + log,
					0, 1, this.toString());
			mensaje = ("Los datos no corresponden a un usuario registrado");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		Login login = Acceso.getUsuario(log, password, params, null, inst,
				sede, jornada);
		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: "
					+ log, 0, 1, this.toString());
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
		String[] met = Acceso.getMetodologia(inst);
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
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1,
				this.toString());
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
		String[][] params = Acceso.autorizado(log, password);
		String mensaje = null;

		if (params == null) {

			Logger.print(
					"0",
					"Los datos no corresponden a un usuario registrado: " + log,
					0, 1, this.toString());
			mensaje = ("Los datos no corresponden a un usuario registrado");
			req.setAttribute("mensaje", mensaje);
			view = log2;
			return view;
		}
		Login login = Acceso.getUsuario(log, password, params, null, null,
				sede, jornada);

		if (login == null) {
			Logger.print("0", "No fue posible obtener los datos del usuario: "
					+ log, 0, 1, this.toString());
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
		Logger.print("0", "Login de usuario satisfactorio: " + log, 0, 1,
				this.toString());
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
				mensaje = ("Sistema inhabilitado temporalmente. Horario de Servicio entre las "
						+ hfin + " horas y las " + hini + " horas.");
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
				consulta = "update USUARIOS_RESTRINGIDOS set USUESTADO=-2,usufecha=sysdate where USULOGIN like '"
						+ log + "'";
				registrar(consulta);
				String sms = "Favor enviar el correo a adminsiges@gmail.com para que su informacion sea recuperada";
				mensaje = (sms);
				req.setAttribute("mensaje", mensaje);
			}
			if (option.equals("0")) {// habilitar
				consulta = "update USUARIOS_RESTRINGIDOS set USUESTADO=0,usufecha=sysdate where USULOGIN like '"
						+ log + "'";
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
//		req.setAttribute("serv", "/OlvidoContrasena.do");
		
		return inte;
		
	}
	
}
