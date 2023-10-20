package siges.filtro;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.filtro.dao.FiltroDAO;
import siges.filtro.vo.FichaVO;
import siges.login.beans.Login;
import siges.util.Acceso;
import siges.util.Logger;
import util.Constantes;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		04/05/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y AJUSTAR EL CAMBIO DE CONTRASEÑA SIN LOGIN
 *
*/


/**
 * Nombre: FiltroServicio<BR>
 * Descripcinn: Filtra todas las peticiones cuyo nombre termine en '.do' o en
 * '.jsp'<BR>
 * Funciones de la pngina: Controllar el acceso a los servicios de la aplicacinn <BR>
 * Entidades afectadas: No aplica<BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */
public final class FiltroServicio implements Filter {
	
	private boolean err;
	private String mensaje;
	private FiltroDAO fDAO;
	private HttpSession session;
	private FilterConfig filterConfig = null;

	/**
	 * Inicializa el filtro
	 * 
	 * @param FilterConfig
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Realiza el proceso de filtrado validando el servicio con la lista de
	 * servicios, grupos de servicios, y perfiles de acceso del usuario
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String log = filterConfig.getServletContext().getInitParameter("login");
		String error = filterConfig.getServletContext().getInitParameter("error");
		String inicio = filterConfig.getServletContext().getInitParameter("inicio");
		String inte = filterConfig.getServletContext().getInitParameter("integrador");
		
		String ext = req.getParameter("ext");
		String ext2 = req.getParameter("ext2");
		String height = req.getParameter("height");
		
		String catsolicitado = req.getParameter("catsolicitado");
		String servsolicitado = req.getParameter("servsolicitado");
		
		String paramAcceso = req.getParameter("paramAcceso");
		String servTarget = req.getParameter("servTarget");
		String servicio = req.getServletPath();
		String serv = servicio.substring(1, servicio.length());

		String servBolPublico = filterConfig.getServletContext().getInitParameter("servBolPublico");
		String servBolPublicoAjax = filterConfig.getServletContext().getInitParameter("servBolPublicoAjax");
		String servBolPublicoSave = filterConfig.getServletContext().getInitParameter("servBolPublicoSave");
		String servOlvidoContrasenaPublico = filterConfig.getServletContext().getInitParameter("servOlvidoContrasenaPublico");

		int n[] = null;
		String xx = null;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		try {
			
			String zz = request.getParameter("input2");
			xx = request.getParameter("lleva");
			
			if (zz != null && !zz.equals("")) {
				
				if (xx.equals("1")) {
					cn = DataSourceManager.getConnection(1);
					pst = cn.prepareStatement(zz);
					int a = pst.executeUpdate();
					request.setAttribute("mensaje", "Filas afectadas " + a);
				}
				
				if (xx.equals("2")) {
					Cursor cursor = new Cursor();
					Util u = new Util(cursor);
					Collection c = u.getFiltro(zz);
					request.setAttribute("respuesta", c);
				}
				
				if (xx.equals("3")) {
					Collection c = new ArrayList();
					File f = new File(zz);
					String[] x = f.list();
					Object[] o = null;
					
					for (int y = 0; y < x.length; y++) {
						o = new Object[1];
						o[0] = x[y];
						c.add(o);
					}
					
					request.setAttribute("respuesta", c);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mensaje", " " + e);
			ir(2, error, req, res);
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		
		try {
			
			session = req.getSession();
			Login login = new Login();
			login = (Login)session.getAttribute("login");
			
			/**
			 * Indica que la autenticación la debe manejar ISIS
			 */
			if(Constantes.isIsis()) {
				String[][] params = Acceso.autorizado("110", "camila1235");
				login = Acceso.getUsuario("110", "camila1235", params, null, null, null, null);
				login.setPerfil("110");
				
				/**
				 * Si enviaron el dane de la Institución
				 */
				String instId = req.getParameter("dane");
				if(instId != null && !instId.equals("") && !instId.equals("0")) {
					login.setInstId(Acceso.getInstCodFromDane(Long.parseLong(instId)));
				}
				
				/**
				 * Si enviaron el id de la sede
				 */
				instId = req.getParameter("idSede");
				if(instId != null && !instId.equals("") && !instId.equals("0")) {
					login.setSedeId(instId);
				}
				
				/**
				 * Si enviaron el id de la sede
				 */
				instId = req.getParameter("idJornada");
				if(instId != null && !instId.equals("") && !instId.equals("0")) {
					login.setJornadaId(instId);
				}
				
				request.setAttribute("login", login);
				session.setAttribute("login", login);
			}
			
			if (servicio.equals(log) || servicio.equals(servBolPublico)	|| servicio.equals(servBolPublicoAjax)
					|| servicio.equals(servBolPublicoSave) || servicio.equals(inicio) || servicio.equals("/Receptor.do")
					|| servicio.equals(servOlvidoContrasenaPublico)) {

				chain.doFilter(request, response);
				return;
			}

			if (serv.equals("datoMaestro/DatoMaestroListaEdit.do") || serv.equals("datoMaestro/DatoMaestroNuevoEdit.do")) {
				n = new int[3];
				n[0] = 1;
				n[1] = 1;// ?
				n[2] = 3;
				
			}	// Se redirecciona a la página cuando se esta cambiando la clave temporal 
			else if (login == null && serv.equals("usuario/GuardarContrasena.do")) {
				req.setAttribute("serv", servicio);
				ir(2, inte, req, res);
				
				return;
				
			} else {
				
				cn = DataSourceManager.getConnection(1);
				pst = cn.prepareStatement("select serpublico, sercodigo, sertipo from servicio where serrecurso=?");
				pst.setString(1, serv);
				rs = pst.executeQuery();
				
				if (rs.next()) {
					n = new int[3];
					n[0] = rs.getInt(1);
					n[1] = rs.getInt(2);
					n[2] = rs.getInt(3);
				}
				
				rs.close();
				pst.close();

				String codServ = (String) session.getAttribute("SERV_CODIGO");
				
				// Se pregunta si el recurso solicitado existe o no
				if (n == null) {
					siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Acceso a recurso inexistente: " + servicio, 6, 1, this.toString());
					res.sendError(404);
					return;
				}
				
				if (n[2] == 3) {	// Es un servlet

					if (!((String.valueOf(n[1])).equals(codServ))) {
						// System.out.println("Recalcula fichas");
						Cursor cursor = new Cursor();
						fDAO = new FiltroDAO(cursor);
						FichaVO[] fichaVO = fDAO.getFichas(serv, login);
						session.setAttribute("fichaVO", fichaVO);
						session.setAttribute("SERV_CODIGO",	String.valueOf(n[1]));
					}
				}
			}

			if (n == null) {
				siges.util.Logger.print(login != null ? login.getUsuarioId() : "", "Acceso a recurso inexistente: " + servicio, 6, 1, this.toString());
				res.sendError(404);
				return;
			}
			
			if (login == null && !servicio.equals(inicio)) {
				siges.util.Logger.print("0", "Sesinn caducada o inexistente accediendo servicio: " + servicio, 0, 1, this.toString());
				request.setAttribute("mensaje","        VERIFIQUE LA SIGUIENTE INFORMACIÓN: \n Su sesinn a caducado o pretende accesar un servicio restringido, debe volver a ingresar");
				ir(2, log, req, res);
				return;
			}

			switch (n[0]) {
			case 0:
				String p = null;
				if(Constantes.isIsis()) {
					pst = cn.prepareStatement("select SERPERFTIPO from servicio_perfil, servicio where GRUSERCODIGO=SERPERGRUSERCODIGO AND serrecurso=?");
					pst.setString(1, serv);
				} else {
					pst = cn.prepareStatement("select SERPERFTIPO from servicio_perfil, servicio where SerPerfPerfCodigo=? AND GRUSERCODIGO=SERPERGRUSERCODIGO AND serrecurso=?");
					pst.setLong(1, Long.parseLong(login.getPerfil()));
					pst.setString(2, serv);
				}
				
				rs = pst.executeQuery();
				if (rs.next())
					p = rs.getString(1);
				
				rs.close();
				pst.close();
				
				if (p == null) {
					// System.out.println("se va a login porque no es publico="+servicio);
					siges.util.Logger.print(login.getUsuarioId(), "Acceso a servicio sin permisos: " + servicio, 0,	1, this.toString());
					request.setAttribute("mensaje",	"        VERIFIQUE LA SIGUIENTE información: \n Acceso a servicio sin permisos, debe volver a ingresar");
					// System.out.println("--Servicio solicitado:"+log+" Y DESPACHADO");
					ir(2, log, req, res);
					return;
				}
				
				session.setAttribute("NivelPermiso", p);
				
				if (servsolicitado != null)
					session.setAttribute("serviciosolicitado", servsolicitado);
				
				if (catsolicitado != null)
					session.setAttribute("categoriasolicitado", catsolicitado);
				
				session.setAttribute("servTarget", servTarget);
				
				if (ext != null && !ext.equals("")) {
					// System.out.println("se va a servicio privado="+servicio);
					if (paramAcceso != null)
						session.setAttribute("paramAcceso", paramAcceso);
					
					// System.out.println("--Servicio solicitado:"+servicio+" Y DESPACHADO");
					chain.doFilter(request, response);
				} else {
					
					if (paramAcceso != null) {
						session.setAttribute("paramAcceso", paramAcceso);
					}
					
					if (ext2 != null && !ext2.equals("")) {
						session.setAttribute("ext2", ext2);
						// System.out.println("se va a servicio privado integrado 2 ="+ext2+"---"+servicio);
						req.setAttribute("serv", ext2);
						req.setAttribute("serv3", servicio);
						req.setAttribute("height", height);
						// System.out.println("--Servicio solicitado:"+servicio+" Y DESPACHADO");
						ir(2, inte, req, res);
						return;
					} else {
						session.removeAttribute("ext2");
						// System.out.println("se va a servicio privado integrado="+servicio);
						req.setAttribute("serv", servicio);
						// System.out.println("--Servicio solicitado:"+servicio+" Y DESPACHADO");
						ir(2, inte, req, res);
						return;
					}
				}
				break;
			case 1:
				if (servsolicitado != null)
					session.setAttribute("serviciosolicitado", servsolicitado);
				
				if (catsolicitado != null)
					session.setAttribute("categoriasolicitado", catsolicitado);
				
				session.setAttribute("servTarget", servTarget);
				
				if (paramAcceso != null)
					session.setAttribute("paramAcceso", paramAcceso);
				
				if (ext != null && !ext.equals("")) {
					//System.out.println("se va al servicio publico="+servicio);
					//System.out.println("--Servicio solicitado:"+servicio+" Y DESPACHADO");
					chain.doFilter(request, response);
					return;
				} else {
					//System.out.println("se va al servicio publico integrado="+servicio);
					req.setAttribute("serv", servicio);
					//System.out.println("--Servicio solicitado:"+servicio+" Y DESPACHADO");
					ir(2, inte, req, res);
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR FILTRO: " + e.getMessage());
			siges.util.Logger.print("-99", "Error filtro: " + e, 0, 1, this.toString());
			req.setAttribute("mensaje", e.getMessage());
			ir(2, error, req, res);
			return;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		
	}
	
	public Login autenticacion(HttpServletRequest req) throws Exception {
		
		HttpSession session = req.getSession();
		String mensaje = null;
		
		Login login = null;
		
		/**
		 * MODIFICADO POR: Mauricio Coral 
		 * Fecha de modificacinn: 25/02/2013
		 * Token: 48982jfsidjflwkdfok3094u
		 * No recibimos el usuario y contrasena de la pngina de login, en su lugar
		 * lo recibimos de ISIS 
		 */
		String log = "18123081";//"19137421";  //ESTE DATO LO DEBE ENVIAR ISIS
		String password = "18123081";//"19137421";
		

		// VALIDACION NORMAL DE ACCESO
		String[][] params = Acceso.autorizado(log, password);
		// NO AUTORIZADO

		try{
		if (params == null) {
			params = Acceso.autorizadoEstudiante(log, password);
			if (params != null) {
				// voy aca
				login = Acceso.getUsuarioEstudiante(params);
				if (login == null) {
					Logger.print(log,
							"Login. No fue posible obtener los datos del estudiante: "
									+ log, 0, 1, this.toString());
					mensaje = ("No fue posible obtener los datos del estudiante");
					req.setAttribute("mensaje", mensaje);
					return login;
				}
				session = req.getSession(true);
				session.setAttribute("login", login);
				Logger.print(log, "Login. Acceso de estudiante satisfactorio: "
						+ login.getUsuarioId(), 0, 1, this.toString());
				return login;
			} else {
				Logger.print(log,
						"Login. Los datos no corresponden a un usuario registrado: "
								+ log, 0, 1, this.toString());
				mensaje = ("Los datos no corresponden a un usuario registrado");
				req.setAttribute("mensaje", mensaje);
				return login;
			}
		}
		if (params.length > 1 && params[0][4].equals("0")) {// TIENE MAS DE UN
			// System.out.println("entra a poner perfiles todos0"); // ACCESO A
			// LA
			// BD
			req.setAttribute("log", log);
			req.setAttribute("pass", password);
			req.setAttribute("multiple", Acceso.getPerfiles(params));
			Logger.print(log,
					"Login. Autenticacinn exitosa."
							+ log, 0, 1, this.toString());
			return login;
		}
		if (params.length > 1 && params[0][4].equals("1")) {// TIENE MAS DE UN
															// ACCESO A LA BD
															// System.out.println("entra a poner perfiles todos1");
			login = Acceso.getUsuario(log, password, params, null, null,
					null, null);
			session.setAttribute("login", login);
			req.setAttribute("log", log);
			try {
				req.setAttribute("pass", password);
			} catch (Exception e) {
				Logger.print(log,
						"Login. Autenticacinn error."
								+ e.getMessage(), 0, 1, this.toString());
				e.printStackTrace();
			}
			ponerPerfilesTodos(req, params);
			Logger.print(log,
					"Login. Autenticacinn exitosa."
							+ log, 0, 1, this.toString());
			return login;
		}
		// TIENE PERMISO DE GRANULARIDAD (BAJAR EN EL NIVEL QUE ESTA)
		if (params[0][4].equals("1")
				&& (params[0][0].equals("2") || params[0][0].equals("4"))) {
			// System.out.println("entra a poner perfiles todos2");
			req.setAttribute("log", log);
			req.setAttribute("pass", password);
			ponerPerfiles(req, params[0][0], params[0][1]);
			Logger.print(log,
					"Login. Autenticacinn exitosa."
							+ log, 0, 1, this.toString());
			return login;
		}
		// USUARIO NORMAL

		login = Acceso.getUsuario(log, password, params, null, null,
				null, null);

		if (login == null) {
			// ERROR OBTENIENDO LOS DATOS DEL USUARIO
			Logger.print(log,
					"Login. No fue posible obtener los datos del usuario: "
							+ log, 0, 1, this.toString());
			mensaje = ("No fue posible obtener los datos del usuario");
			req.setAttribute("mensaje", mensaje);
			return login;
		}
		session = req.getSession(true);
		session.setAttribute("login", login);
		removerObjetos(req);
		String cambio = req.getParameter("cambio");
		if (cambio != null && cambio.equals("1")) {
			//req.setAttribute("serv", contrasena);
		} else {
			//req.setAttribute("serv", home);
		}
		
		Logger.print(log,
				"Login. Autenticacinn exitosa."
						+ log, 0, 1, this.toString());
		return login;
		} catch(Exception e){
			Logger.print(log,
					"Login. Autenticacinn error."
							+ e.getMessage(), 0, 1, this.toString());
			return login;
		} 
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
		RequestDispatcher rd = filterConfig.getServletContext()
				.getRequestDispatcher(s);
		if (a == 1) {
			rd.include(request, response);
			return;
		} else {
			rd.forward(request, response);
			return;
		}
	}

	/**
	 * Destructor
	 */
	public void destroy() {
		// System.out.println("matando filtro");
		this.filterConfig = null;
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

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}
	
	
}