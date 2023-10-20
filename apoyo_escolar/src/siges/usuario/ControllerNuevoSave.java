package siges.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.usuario.beans.Usuario;
import siges.usuario.dao.UsuarioDAO;
import siges.util.Logger;

/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		27/04/2018		JORGE CAMACHO		Se editó la función insertarRegistroPer()
 *		2.0		30/04/2018		JORGE CAMACHO		Se editó la función insertarRegistroUsu()
 *		3.0		16/05/2018		JORGE CAMACHO		Se agregó el registro de auditoría en la función: actualizarRegistroPer()
 * 
*/

/**
 * Nombre: ControllerNuevoSave<br>
 * Descripcinn: Guardar, Editar o Eliminar informaciÃ³n del Usuario<br>
 * Funciones de la pngina: Controla la vista del formulario para editar, guardar 
 * o eliminar<br>
 * Entidades afectadas: Usuario<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerNuevoSave extends HttpServlet {
	
	private Usuario usuario, usuario2;

	private Cursor cursor;				// objeto que maneja las sentencias sql
	private Util util;
	private UsuarioDAO usuDAO;
	private String mensaje;				// mensaje en caso de error
	private boolean band;
	private boolean err;				// variable que inidica si hay o no errores en la validacion de los datos del formulario
	private int tipo;
	private HttpSession session;
	private String jerar, perf, docum, nivel;
	private Login login;
	private Collection col, list, coldep, colmun, colnuc, colinst, colsede,coljorn;
	private Object o[];
	private ResourceBundle rbUsuario;
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer fecha = new Integer(java.sql.Types.DATE);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession();
		rbUsuario = ResourceBundle.getBundle("usuarios");
		
		String boton;
		String buscar = null;
		String respuesta = "";
		String p_error = this.getServletContext().getInitParameter("er");
		String p_home = this.getServletContext().getInitParameter("home");
		String p_login = this.getServletContext().getInitParameter("login");
		String p_inte = this.getServletContext().getInitParameter("integrador");
		
		String sig = "/usuario/NuevoUsuario.jsp";
		String sig1 = "/usuario/FiltroUsuario.jsp";
		String sig2 = "/usuario/NuevoPersonal.jsp";
		String ant = "/usuario/ControllerUsuarioNuevoEdit.do";	// Página a la que se le dará el control si algo falla
		
		err = false;
		band = true;
		mensaje = null;
		respuesta = "La información fue ingresada satisfactoriamente ";
		cursor = new Cursor();
		
		try {
			util = new Util(cursor);
			usuDAO = new UsuarioDAO(cursor);
			boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("Cancelar");
			
			if (boton.equals("Cancelar")) {
				borrarBeans(request);
				return p_home;
			}
			
			if (boton.equals("GuardarPersonal")) {
				
				if (request.getParameter("tipo") == null || request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return p_error;
				}
				
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
				
				if (!asignarBeans(request)) {
					this.setMensaje("Error capturando datos de sesinn para el usuario");
					request.setAttribute("mensaje", mensaje);
					return p_error;
				}

				switch (tipo) {
				case 1:
					if (usuario.getEstadoper().equals("1")) {
						actualizarRegistroPer(request);
						request.setAttribute("mensaje", getMensaje());
						return sig1;
					} else if (!usuario.getEstadoper().equals("1")) {
						insertarRegistroPer(request);
						request.setAttribute("mensaje", getMensaje());
						return sig1;
					}
				}
			}
			
			if (boton.equals("GuardarUsuario")) {
				
				if (request.getParameter("tipo") == null || request.getParameter("tipo").equals("")) {
					setMensaje("Acceso denegado no hay una ficha definida");
					request.setAttribute("mensaje", getMensaje());
					return p_error;
				}
				
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
				
				if (!asignarBeans(request)) {
					this.setMensaje("Error capturando datos de sesión para el usuario");
					request.setAttribute("mensaje", mensaje);
					return p_error;
				}
				
				usuario.setUsuperfcodigo(usuario.getUsuperfcodigo().substring(usuario.getUsuperfcodigo().indexOf('|') + 1, usuario.getUsuperfcodigo().length()));
				
				switch (tipo) {
				case 1:
					if (usuario.getEstadousu().equals("1")) {
						actualizarRegistroUsu(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					} else if (!usuario.getEstadousu().equals("1")) {
						insertarRegistroUsu(request);
						request.setAttribute("mensaje", getMensaje());
						return (ant += "?tipo=" + tipo);
					}
				}
			}

			if (boton.equals("EditarPersonal")) {
				docum = (String) request.getParameter("idper");
				request.setAttribute("docum", docum);
				borrarBeans(request);
				request.setAttribute("tipo", "1");
				session.setAttribute("editar", "1");
				Usuario usuario = null;
				usuario = usuDAO.asignarPersonalUsuario(docum);
				
				if (usuario != null) {
					session.setAttribute("nuevoUsuario", usuario);
					session.setAttribute("nuevoUsuario2", usuario.clone());
				} else {
					setMensaje(usuDAO.getMensaje());
					request.setAttribute("mensaje", mensaje);
				}
				return sig2;
			}

			if (boton.equals("EditarUsuario")) {
				jerar = (String) request.getParameter("idusu").substring(0,	request.getParameter("idusu").indexOf('|'));
				docum = (String) request.getParameter("idusu").substring(request.getParameter("idusu").indexOf('|') + 1, request.getParameter("idusu").lastIndexOf("|"));
				perf = (String) request.getParameter("idusu").substring(request.getParameter("idusu").lastIndexOf("|") + 1,	request.getParameter("idusu").indexOf("*"));
				nivel = (String) request.getParameter("idusu").substring(request.getParameter("idusu").indexOf("*") + 1, request.getParameter("idusu").length());
				
				request.setAttribute("jerar", jerar);
				request.setAttribute("perfil", perf);
				request.setAttribute("docum", docum);
				borrarBeans(request);
				request.setAttribute("tipo", "1");
				session.setAttribute("editar", "1");
				
				Usuario usuario = null;
				usuario = usuDAO.asignarUsuario(jerar, perf, docum);
				
				cargarUbicacion(jerar, Integer.parseInt(nivel), perf, request);
				
				if (usuario != null) {
					session.setAttribute("nuevoUsuario", usuario);
					session.setAttribute("nuevoUsuario2", usuario.clone());
				} else {
					setMensaje(usuDAO.getMensaje());
					request.setAttribute("mensaje", mensaje);
				}
				return sig;
			}
			
			return p_error;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + this + ": " + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (util != null)
				util.cerrar();
		}
	}

	/**
	 * Cargar ubicacion del usuario a editar
	 * 
	 * @param jerar
	 * @param nivel
	 */
	public void cargarUbicacion(String jerar, int nivel, String perfil,	HttpServletRequest request) throws InternalErrorException {
		
		String sql = rbUsuario.getString("nivel");
		// System.out.println("USUARIO: ENTRO A CARGAR UBICACION  JERAR "+jerar+" NIVEL "+nivel);
		/*
		 * [250809][CAMBIA DE NIVEL 2 A 3 PORQUE ES EN NIVEL 3 DONDE ESTAN LAS
		 * LOCALIDADES]
		 */
		if (nivel == 2)
			nivel = 3;
		// System.out.println("nivel: "+nivel+" jerar: "+jerar+" perfil: "+perfil);
		// System.out.println("SQL: "+sql);

		list = new ArrayList();
		o = new Object[2];
		o[0] = cadena;
		o[1] = jerar;
		list.add(o);
		/*
		 * o=new Object[2]; o[0]=entero; o[1]=""+nivel; list.add(o);
		 */

		String[][] c = util.getFiltroMatriz(sql, list);
		request.setAttribute("_editar", c);

		// System.out.println("USUARIO: ENTRO A CARGAR UBICACION  JERAR "+jerar+" NIVEL "+nivel+
		// " DEPENDENCIA: "+c[0][0]);
		switch (nivel) {
		case 0:
			// NIVEL POA
			// System.out.println("NIVEL POA GET UBICACIONNIVAL ");

			break;

		case 1:
			// departamento

			break;
		case 2:
		case 3:
			// localidad
			list = new ArrayList();
			/*
			 * o=new Object[2]; o[0]=cadena; o[1]=c[0][0]; list.add(o);
			 */
			colmun = util.getFiltro(rbUsuario.getString("CargaLocalidades"));
			request.setAttribute("colmun", colmun);
			break;
		// case 3:
		// nucleo
		/*
		 * list = new ArrayList(); o=new Object[2]; o[0]=cadena; o[1]=c[0][0];
		 * list.add(o);
		 * colmun=util.getFiltro(rbUsuario.getString("CargaLocalidades"),list);
		 * request.setAttribute("colmun",colmun);
		 * 
		 * list = new ArrayList(); o=new Object[2]; o[0]=cadena; o[1]=c[0][1];
		 * list.add(o);
		 * colnuc=util.getFiltro(rbUsuario.getString("CargaNucleos"),list);
		 * request.setAttribute("colnuc",colnuc); break;
		 */
		case 4:
			// institucion
			// System.out.println("c[0][0] "+c[0][0]);
			list = new ArrayList();
			/*
			 * o=new Object[2]; o[0]=cadena; o[1]=c[0][0]; list.add(o);
			 */
			colmun = util.getFiltro(rbUsuario.getString("CargaLocalidades"));
			request.setAttribute("colmun", colmun);

			/*
			 * list = new ArrayList(); o=new Object[2]; o[0]=cadena;
			 * o[1]=c[0][1]; list.add(o);
			 * colnuc=util.getFiltro(rbUsuario.getString("CargaNucleos"),list);
			 * request.setAttribute("colnuc",colnuc);
			 */

			list = new ArrayList();
			o = new Object[2];
			o[0] = cadena;
			o[1] = c[0][1];
			list.add(o);
			/*
			 * o=new Object[2]; o[0]=cadena; o[1]=c[0][2]; list.add(o);
			 */
			colinst = util.getFiltro(rbUsuario.getString("CargaInstituciones"),	list);
			request.setAttribute("colinst", colinst);

			break;
		case 6:
			// sede-jorn
			list = new ArrayList();
			/*
			 * o=new Object[2]; o[0]=cadena; o[1]=c[0][0]; list.add(o);
			 */
			colmun = util.getFiltro(rbUsuario.getString("CargaLocalidades"));
			request.setAttribute("colmun", colmun);

			/*
			 * list = new ArrayList(); o=new Object[2]; o[0]=cadena;
			 * o[1]=c[0][1]; list.add(o);
			 * colnuc=util.getFiltro(rbUsuario.getString("CargaNucleos"),list);
			 * request.setAttribute("colnuc",colnuc);
			 */

			list = new ArrayList();
			o = new Object[2];
			o[0] = cadena;
			o[1] = c[0][1];
			list.add(o);
			/*
			 * o=new Object[2]; o[0]=cadena; o[1]=c[0][2]; list.add(o);
			 */
			colinst = util.getFiltro(rbUsuario.getString("CargaInstituciones"),
					list);
			request.setAttribute("colinst", colinst);

			list = new ArrayList();
			o = new Object[2];
			o[0] = cadena;
			o[1] = c[0][3];
			list.add(o);
			colsede = util.getFiltro(rbUsuario.getString("CargaSedes"), list);
			request.setAttribute("colsede", colsede);

			list = new ArrayList();
			o = new Object[2];
			o[0] = cadena;
			o[1] = c[0][3];
			list.add(o);
			o = new Object[2];
			o[0] = cadena;
			o[1] = c[0][4];
			list.add(o);
			coljorn = util
					.getFiltro(rbUsuario.getString("CargaJornadas"), list);
			request.setAttribute("coljorn", coljorn);

			break;
		}
	}

	/**
	 * Funcinn: Actualizar informaciÃ³n del Usuario<br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void actualizarRegistroPer(HttpServletRequest request) throws ServletException, IOException {
		
		boolean booValidaciones = true;
		
		switch (tipo) {
		case 1:
			// Se valida que el nuevo número de documento no se encuentre registrado en el sistema
			if (!usuario.getPernumdocum().equals(usuario2.getPernumdocum())) {
				if (validarExistenciaPer2(usuario.getPernumdocum())) {
					setMensaje("El Nnmero de identificacinn es de un personal que ya esta registrado, no se puede registrar");
					booValidaciones = false;
					return;
				}
			}
			
			// Se valida que el nuevo correo institucional no se encuentre registrado en el sistema
			Login login = (Login) session.getAttribute("login");
			String perfilMesa =  rbUsuario.getString("PerfilMesaAyuda");
			if(login.getPerfil().equals(perfilMesa)) {
				if(usuario2.getPercorreoinstitucional() != null){
					if (!usuario.getPercorreoinstitucional().toLowerCase().equals(usuario2.getPercorreoinstitucional().toLowerCase())) {
						if (usuDAO.existeCorreoInstitucional(usuario.getPercorreoinstitucional())) {
							setMensaje("El Correo Institucional ya se encuentra registrado, debe digitar un correo diferente.");
							booValidaciones = false;
							return;
						}
					}
				}
			}
						
			if (!compararFichasPer()) {
				booValidaciones = false;
			} else {
				setMensaje("La información fue actualizada satisfactoriamente -");
				Logger.print(login.getUsuarioId(), "Actualizacinn de Usuario: "	+ usuario.getUsulogin(), 7, 1, this.toString());
				return;
			}
			
			if (!usuDAO.actualizarPersonal(usuario)) {
				setMensaje(usuDAO.getMensaje());
				restaurarBeans(request);
				return;
			}
			
			setMensaje("La información fue actualizada satisfactoriamente.");
			
			// Se hace el registro de la información en la tabla de auditoria
			java.util.Date utilDate = new Date();
			java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
			
			String strObservaciones = "ANTES: PRIMER NOMBRE: " + usuario2.getPernombre1() + ", " +
										"SEGUNDO NOMBRE: " + usuario2.getPernombre2() + ", " +
										"PRIMER APELLIDO: " + usuario2.getPerapellido1() + ", " +
										"SEGUNDO APELLIDO: " + usuario2.getPerapellido2() + ", " +
										"TIPO DE DOCUMENTO: " + usuario2.getPertipdocum() + ", " +
										"NUMERO DE DOCUMENTO: " + usuario2.getPernumdocum() + ", " +
										"CORREO INSTITUCIONAL: " + usuario2.getPercorreoinstitucional() + " - " + 
									"DESPUÉS: PRIMER NOMBRE: " + usuario.getPernombre1() + ", " +
										"SEGUNDO NOMBRE: " + usuario.getPernombre2() + ", " +
										"PRIMER APELLIDO: " + usuario.getPerapellido1() + ", " +
										"SEGUNDO APELLIDO: " + usuario.getPerapellido2() + ", " +
										"TIPO DE DOCUMENTO: " + usuario.getPertipdocum() + ", " +
										"NUMERO DE DOCUMENTO: " + usuario.getPernumdocum() + ", " +
										"CORREO INSTITUCIONAL: " + usuario.getPercorreoinstitucional();
			
			usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, "Modificación de Usuario", strObservaciones);
			
			recargarBeans(request);
			
			break;
		}
	}

	/**
	 * Funcinn: Actualizar informaciÃ³n del Usuario<br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void actualizarRegistroUsu(HttpServletRequest request) throws ServletException, IOException {
		
		String strOperacion = "Modificación de Acceso";
		usuario.setUsucodjerar(verificarJerar(request) == null ? ""	: verificarJerar(request)[0]);
		
		switch (tipo) {
		case 1:
			if (!usuDAO.actualizarUsuario(usuario)) {
				setMensaje(usuDAO.getMensaje());
				restaurarBeans(request);
				return;
			}

			setMensaje("La información fue actualizada satisfactoriamente.");
			
			// Se hace el registro de la información en la tabla de auditoria
			java.util.Date utilDate = new Date();
			java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
			
			String strObservaciones = "LOGIN: " + usuario.getUsulogin() + " - ANTES: USUCODJERAR: " + usuario.getUsucodjerar2() + ", USUPERFCODIGO: " + usuario.getUsuperfcodigo2() +
										" - DESPUES: USUCODJERAR: " + usuario.getUsucodjerar() + ", USUPERFCODIGO: " + usuario.getUsuperfcodigo();
			
			usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, strOperacion, strObservaciones);

			try {
				usuDAO.enviarCorreoGrupoApoyoAccesos(login, dtFechaActual, usuario, strOperacion, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			recargarBeans(request);
			break;
		}
	}

	/**
	 * Funcinn: Comparar beans de Usuario <br>
	 * 
	 * @return boolean
	 **/
	public boolean compararFichasPer() {
		switch (tipo) {
		case 1:
			return usuDAO.compararBeansPer(usuario, usuario2);
		}
		return true;
	}

	/**
	 * Funcinn: Comparar beans de Usuario <br>
	 * 
	 * @return boolean
	 **/
	public boolean compararFichasUsu() {
		// System.out.println("ENTRO A FUNCION CMOARAR BEANS");
		switch (tipo) {
		case 1:
			return usuDAO.compararBeansUsu(usuario, usuario2);
		}
		return true;
	}

	/**
	 * Funcinn: Insertar registro de usuario <br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void insertarRegistroPer(HttpServletRequest request) throws ServletException, IOException {

		switch (tipo) {
		case 1:
			if (validarExistenciaPer2(usuario.getPernumdocum())) {
				setMensaje("El Número de Identificación es de un personal que ya está registrado, no se puede registrar.");
				return;
			}
			Login login = (Login) session.getAttribute("login");
			login.getPerfil();
			String perfilMesa =  rbUsuario.getString("PerfilMesaAyuda");
			if(login.getPerfil().equals(perfilMesa)) {
				if (usuDAO.existeCorreoInstitucional(usuario.getPercorreoinstitucional())) {
					setMensaje("El Correo Institucional ya se encuentra registrado, debe digitar un correo diferente.");
					return;
				}
			}
			
			if (!usuDAO.insertarPersonal(usuario)) {
				setMensaje(usuDAO.getMensaje());
				return;
			}
			
			session.setAttribute("nuevoUsuario2", (Usuario) usuario.clone());
			setMensaje("La información fue ingresada satisfactoriamente.");
			Logger.print(login.getUsuarioId(), "Insercinn de Nuevo Usuario: " + usuario.getUsulogin(), 6, 1, this.toString());
			
			// Se hace el registro de la información en la tabla de auditoria
			java.util.Date utilDate = new Date();
			java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
						
			String strObservaciones = "PRIMER NOMBRE: " + usuario.getPernombre1() + ", " +
										"SEGUNDO NOMBRE: " + usuario.getPernombre2() + ", " +
										"PRIMER APELLIDO: " + usuario.getPerapellido1() + ", " +
										"SEGUNDO APELLIDO: " + usuario.getPerapellido2() + ", " +
										"TIPO DE DOCUMENTO: " + usuario.getPertipdocum() + ", " +
										"NUMERO DE DOCUMENTO: " + usuario.getPernumdocum() + ", " +
										"CORREO INSTITUCIONAL: " + usuario.getPercorreoinstitucional();
			
			usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, "Creación de Usuario", strObservaciones);
			
			break;
		}
	}

	/**
	 * Funcinn: Insertar registro de usuario <br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public void insertarRegistroUsu(HttpServletRequest request)	throws ServletException, IOException {

		String strOperacion = "Creación de Acceso";
		usuario.setUsucodjerar(verificarJerar(request)[0]);

		switch (tipo) {
		case 1:
			if (validarExistenciaUsu(usuario.getUsupernumdocum(), usuario.getUsucodjerar(), usuario.getUsuperfcodigo())) {
				setMensaje("El Número de Identificación es de un Usuario que ya está registrado, no se puede registrar");
				return;
			}
			
			if (!usuDAO.insertarUsuario(usuario)) {
				setMensaje(usuDAO.getMensaje());
				return;
			}
			
			session.setAttribute("nuevoUsuario2", (Usuario) usuario.clone());
			setMensaje("La información fue ingresada satisfactoriamente");
			Logger.print(login.getUsuarioId(), "Insercinn de Nuevo Usuario: " + usuario.getUsulogin(), 6, 1, this.toString());
			
			// Se hace el registro de la información en la tabla de auditoria
			java.util.Date utilDate = new Date();
			java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
						
			String strObservaciones = "LOGIN: " + usuario.getUsupernumdocum() + ", USUCODJERAR: " + usuario.getUsucodjerar() + ", USUPERFCODIGO: " + usuario.getUsuperfcodigo();
			
			usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, strOperacion, strObservaciones);
			
			try {
				usuDAO.enviarCorreoGrupoApoyoAccesos(login, dtFechaActual, usuario, strOperacion, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		}
	
	}

	public String[] verificarJerar(HttpServletRequest request) throws ServletException, IOException {
		
		String consulta = "";
		String[] resul = null;
		String sede = request.getParameter("sede");
		String jor = request.getParameter("jornada");
		String mun = request.getParameter("municipio");
		String inst = request.getParameter("institucion");
		
		if (mun == null || mun.equals("-1")) {
			consulta = "select G_JerCodigo from g_jerarquia where G_JerDepto=(SELECT min(G_ConCodPadre) FROM G_Constante WHERE G_ConTipo=7) and G_JerTipo='1' and G_JERNIVEL='1'";
			resul = cursor.getFila(consulta);
		} else if (inst == null || inst.equals("-1")) {
			consulta = "select G_JerCodigo from g_jerarquia where G_JerDepto=(SELECT min(G_ConCodPadre) FROM G_Constante WHERE G_ConTipo=7) and G_JerMunic='"
					+ mun + "' and G_JerTipo='1' and G_JERNIVEL='3'";
			resul = cursor.getFila(consulta);
		} else if (sede == null || sede.equals("-1")) {
			consulta = "select G_JerCodigo from g_jerarquia where G_JerDepto=(SELECT min(G_ConCodPadre) FROM G_Constante WHERE G_ConTipo=7) and G_JerMunic='"
					+ mun
					+ "' and G_JerInst='"
					+ inst
					+ "' and G_JerTipo='1' and G_JERNIVEL='4'";
			resul = cursor.getFila(consulta);
		} else if (mun != null && inst != null && sede != null && jor != null) {
			consulta = "select G_JerCodigo from g_jerarquia where G_JerDepto=(SELECT min(G_ConCodPadre) FROM G_Constante WHERE G_ConTipo=7) and G_JerMunic='"
					+ mun
					+ "' and G_JerInst='"
					+ inst
					+ "' and G_JerSede='"
					+ sede
					+ "' and G_JerJorn='"
					+ jor
					+ "'and G_JerTipo='1' and G_JERNIVEL='6'";
			resul = cursor.getFila(consulta);
		}

		return resul;
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)	throws ServletException, IOException {
		
		request.getSession().removeAttribute("nuevoUsuario");
		request.getSession().removeAttribute("nuevoUsuario2");
	}

	/**
	 * Funcinn: Asigna a variables locales el contenido de los beans de session
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean true=asigno todos los beans; false=no existe alguno de
	 *         los beans en la session
	 */
	public boolean asignarBeans(HttpServletRequest request)	throws ServletException, IOException {
		
		usuario = (Usuario) request.getSession().getAttribute("nuevoUsuario");
		usuario2 = (Usuario) request.getSession().getAttribute("nuevoUsuario2");
		
		if (request.getSession().getAttribute("login") != null) {
			login = (Login) request.getSession().getAttribute("login");
			return true;
		}
		
		return true;
		
	}

	/**
	 * Funcinn: Busca en la base de datos un registro que coincida con el id
	 * ingresado por el usuario
	 * 
	 * @return boolean
	 */
	public boolean validarExistenciaUsu(String cod, String jerar, String perfil) {
		
		String sentencia = "Select UsuPerNumDocum from usuario where UsuPerNumDocum= '"
				+ cod
				+ "' and UsuCodJerar="
				+ jerar
				+ " and usuperfcodigo="
				+ perfil;
		
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		
		return false;
		
	}

	public boolean validarExistenciaPer(String cod, String tip) {
		
		String sentencia = "Select PerNumDocum from personal where PerNumDocum='" + cod + "' and pertipdocum='" + tip + "'";

		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		
		return false;
		
	}

	public boolean validarExistenciaPer2(String cod) {
		
		String sentencia = "Select PerNumDocum from personal where PerNumDocum='" + cod + "'";
		
		if (cursor.existe(sentencia)) {
			return true;// si existe
		}
		
		return false;
		
	}

	/**
	 * Funcinn: Referencia al bean del usuario con la informaciÃ³n proporcionada
	 * por el bean de respaldo
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void restaurarBeans(HttpServletRequest request) throws ServletException, IOException {
		
		switch (tipo) {
		case 1:
			session.setAttribute("nuevoUsuario", (Usuario) usuario2.clone());
			break;
		}
	}

	/**
	 * Funcinn: Referencia al bean de respaldo con la nueva informaciÃ³n
	 * proporcionada por el bean modificado por el usuario
	 * 
	 * @param int n
	 * @param HttpServletRequest
	 *            request
	 */
	public void recargarBeans(HttpServletRequest request) throws ServletException, IOException {
		
		switch (tipo) {
		case 1:
			session.setAttribute("nuevoUsuario2", (Usuario) usuario.clone());
			break;
		}
		
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String s = process(request, response);
		
		if (s != null && !s.equals(""))
			ir(1, s, request, response);
		
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
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes
	 * que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		
		band = false;
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		}
		mensaje += "  - " + s + "\n";
	}

	/**
	 * Funcinn: Retorna una variable que contiene los mensajes que se van a
	 * enviar a la vista
	 * 
	 * @return String
	 **/
	public String getMensaje() {
		return mensaje;
	}
	
}
