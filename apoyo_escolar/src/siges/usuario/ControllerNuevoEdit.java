package siges.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import siges.dao.Util;
import siges.dao.Cursor;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.usuario.beans.Usuario;
import siges.usuario.dao.UsuarioDAO;
import siges.util.Acceso;
import siges.util.Logger;


/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		17/05/2018		JORGE CAMACHO		CODIGO ESPAGUETI Y SE EDITO LA FUNCION process() PARA AGREGAR LA AUDITORIA DE USUARIOS CUANDO SE ELIMINA UN ACCESO
 * 
*/


/**
 * Nombre: ControllerNuevoEdit<br>
 * Descripcinn: Nuevo o Editar Usuario<br>
 * Funciones de la pngina: Controla la vista del formulario nuevo y edicinn de
 * usuario<br>
 * Entidades afectadas: Usario<br>
 * Fecha de modificacinn: 20/07/2005 <br>
 * 
 * @author Latined <br>
 * @version v 1.0 <br>
 */
public class ControllerNuevoEdit extends HttpServlet {

	private String mensaje;
	private String buscar;
	private boolean error;
	private Cursor cursor;
	private Usuario usuario;
	private Util util;
	private UsuarioDAO usuDAO;
	private Login login;
	private HttpSession session;
	private String sig;
	private Collection col, col2, list, list2;
	private String consulta, departamento, municipio, nucleo, institucion, sede, jornada, usupernumdoc, perf, nivel;
	private String[] params;
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
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int tipo;
		list = new ArrayList();
		session = request.getSession();
		rbUsuario = ResourceBundle.getBundle("usuarios");
		
		String p_error = this.getServletContext().getInitParameter("er");
		String p_home = this.getServletContext().getInitParameter("home");
		String p_login = this.getServletContext().getInitParameter("login");
		String p_inte = this.getServletContext().getInitParameter("integrador");
		
		String sig = "/usuario/FiltroUsuario.jsp";
		String sig1 = "/usuario/NuevoUsuario.jsp";
		String sig2 = "/usuario/FiltroResultado.jsp";
		String sig3 = "bienvenida.jsp";
		String sig4 = "/usuario/NuevoGuardar.jsp";
		String sig5 = "/usuario/NuevoPersonal.jsp";
		String sig6 = "/usuario/FiltroResultado2.jsp";
		String jerar, perfil, docum;
		
		error = false;
		mensaje = null;
		cursor = new Cursor();
		
		try {
			util = new Util(cursor);
			usuDAO = new UsuarioDAO(cursor);
			if (request.getParameter("tipo") == null || ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				session.removeAttribute("editar");
				tipo = 0;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}

			if (!asignarBeans(request)) {
				this.setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return p_error;
			}
			
			list = new ArrayList();
			o = new Object[2];
			o[0] = enterolargo;
			o[1] = login.getPerfil();
			list.add(o);
			if(!login.getPerfil().equals(rbUsuario.getString("PerfilMesaAyuda"))) {
				col = util.getFiltro(rbUsuario.getString("FiltroPerfil"), list);
				request.setAttribute("filtroPerfil", col);
			}else {
				// Se adiciona esta consulta para que la mesa de ayuda no pueda agregar cualquier perfil.
				col = util.getFiltroMesaAyuda(rbUsuario.getString("FiltroPerfilMesaAyuda"), list);
				request.setAttribute("filtroPerfil", col);
			}

			request.setAttribute("filtroDependencias", usuDAO.getDependencias());

			switch (tipo) {
			case 0:
				// filtro (inicio)
				/*
				 * consulta=
				 * "select PerfCodigo, PerfNombre, PerfDescripcion, PerfAbreviatura, perfnivel from perfil where PerfCodigo>="
				 * +login.getPerfil()+" order by to_number(PerfCodigo)";
				 * col=util.getFiltro(consulta);
				 * request.setAttribute("filtroPerfil",col);
				 */
				return sig;
			case 1:
				// System.out.println("crear nuevo acceso"+request.getParameter("idaux")+"//"+request.getParameter("idusu"));
				// nuevo Usuario
				String numdoc;
				if (request.getParameter("idaux") != null && !request.getParameter("idaux").equals("")) {
					numdoc = (String) request.getParameter("idaux");
					usuario.setUsupernumdocum(numdoc);
				} else if (request.getParameter("idusu") != null) {
					numdoc = docum = (String) request
							.getParameter("idusu")
							.substring(
									request.getParameter("idusu").indexOf('|') + 1,
									request.getParameter("idusu").lastIndexOf(
											"|"));
					usuario.setUsupernumdocum(numdoc);
					// System.out.println(usuario.getUsupernumdocum()+"nuevoUsu: "+request.getParameter("idusu"));
				} else {
					usuario.setUsupernumdocum("");
				}
				
				usuario.setEstadousu("0");
				return sig1;
			case 2:
				// buscar Usuario
				docum = (String) request.getParameter("idper");
				list = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = docum;
				list.add(o);
				col = util.getFiltro(rbUsuario.getString("BuscaUsuarios"), list);
				request.setAttribute("buscaUsuario13", col);
				request.setAttribute("idusu", request.getParameter("idper"));
				String[][] access = util.getFiltroMatriz(col);
				if (access != null) {
					col2 = Acceso.getPerfiles(access);
					request.setAttribute("buscaUsuario", col2);
				}
				return sig6;
			case 3:
				// eliminar Usuario
				String strOperacion = "Eliminación de Acceso";
				
				jerar = (String) request.getParameter("idusu").substring(0,	request.getParameter("idusu").indexOf('|'));
				docum = (String) request.getParameter("idusu").substring(request.getParameter("idusu").indexOf('|') + 1, request.getParameter("idusu").lastIndexOf("|"));
				perfil = (String) request.getParameter("idusu").substring(request.getParameter("idusu").lastIndexOf("|") + 1, request.getParameter("idusu").indexOf("*"));
				nivel = (String) request.getParameter("idusu").substring(request.getParameter("idusu").indexOf("*") + 1, request.getParameter("idusu").length());
				
				// System.out.println("ELIMINAR="+jerar+"-"+perfil+"-"+docum);
				// verifica que sea de nivel 6
				String consulta = "select USUPERNUMDOCUM from usuario where USUPERNUMDOCUM in ( "
						+ "select DOCSEDJORNUMDOCUM from DOCENTE_SEDE_JORNADA, g_jerarquia "
						+ "where G_JERCODIGO='"
						+ jerar
						+ "' "
						+ "and g_jerinst=DOCSEDJORCODINST "
						+ "and g_jersede=DOCSEDJORCODSEDE "
						+ "and g_jerjorn=DOCSEDJORCODJOR )";

				String consulta2 = "select DOCSEDJORNUMDOCUM, DOCSEDJORCODINST, DOCSEDJORCODSEDE, DOCSEDJORCODJOR "
						+ "from DOCENTE_SEDE_JORNADA, g_jerarquia "
						+ "where G_JERCODIGO='"
						+ jerar
						+ "' "
						+ "and g_jerinst=DOCSEDJORCODINST "
						+ "and g_jersede=DOCSEDJORCODSEDE "
						+ "and g_jerjorn=DOCSEDJORCODJOR and DOCSEDJORNUMDOCUM='"
						+ docum + "'";

				if (cursor.existe(consulta)) {
					if (Integer.parseInt(login.getPerfil()) < Integer.parseInt(perfil)) {
						params = cursor.nombre(consulta2);
						
						if (params != null)
							usuDAO.eliminarDocenteSedeJornada(jerar, perfil, docum, params);
						
						if (params == null)
							usuDAO.eliminarUsuario(jerar, perfil, docum);

						setMensaje("La información fue actualizada satisfactoriamente.");
						
						// Se hace el registro de la información en la tabla de auditoria
						java.util.Date utilDate = new Date();
						java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
						
						String strObservaciones = "LOGIN: " + docum + ", USUCODJERAR: " + jerar + ", USUPERFCODIGO: " + perfil;
						
						usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, strOperacion, strObservaciones);
						
						try {
							// En este punto el usuario es un objeto vacío por lo que toca asignarle la información
							usuario = usuDAO.asignarPersonalUsuario(docum);
							// El usuario consultado no tiene los datos de jerarquía ni perfil
							usuario.setUsucodjerar(jerar);
							usuario.setUsuperfcodigo(perfil);
							usuDAO.enviarCorreoGrupoApoyoAccesos(login, dtFechaActual, usuario, strOperacion, request);
						} catch (Exception e) {
							e.printStackTrace();
						}						
						
					} else {
						setMensaje("No tiene permisos para eliminar este usuario");
					}
				} else {
					if (Integer.parseInt(login.getPerfil()) < Integer.parseInt(perfil)) {
						usuDAO.eliminarUsuario(jerar, perfil, docum);
						setMensaje("La información fue actualizada satisfactoriamente.");
						
						// Se hace el registro de la información en la tabla de auditoria
						java.util.Date utilDate = new Date();
						java.sql.Date dtFechaActual = new java.sql.Date(utilDate.getTime());
						
						String strObservaciones = "LOGIN: " + docum + ", USUCODJERAR: " + jerar + ", USUPERFCODIGO: " + perfil;
						
						usuDAO.registroAuditoria(login.getUsuarioId(), dtFechaActual, strOperacion, strObservaciones);
						
						try {
							// En este punto el usuario es un objeto vacío por lo que toca asignarle la información
							usuario = usuDAO.asignarPersonalUsuario(docum);
							// El usuario consultado no tiene los datos de jerarquía ni perfil
							usuario.setUsucodjerar(jerar);
							usuario.setUsuperfcodigo(perfil);
							usuDAO.enviarCorreoGrupoApoyoAccesos(login, dtFechaActual, usuario, strOperacion, request);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} else {
						setMensaje("No tiene permisos para eliminar este usuario");
					}
				}

				request.setAttribute("mensaje", getMensaje());
				return sig;
			case 4:
				// System.out.println("entra a editar un usuario");
				// editar Usuario
				// perfil=(String)request.getParameter("idusu").substring(request.getParameter("idusu").indexOf('|')+1,request.getParameter("idusu").lastIndexOf("|"));
				perfil = (String) request.getParameter("idusu").substring(request.getParameter("idusu").lastIndexOf('|') + 1, request.getParameter("idusu").lastIndexOf("*"));
				
				if (Integer.parseInt(login.getPerfil()) < Integer.parseInt(perfil)) {
					sig = sig4;
				} else {
					setMensaje("No tiene permisos para editar este usuario");
					request.setAttribute("mensaje", getMensaje());
					sig = sig2;
				}
				return sig;
			case 5:
				// nuevo Personal
				usuario.setEstadoper("0");
				return sig5;
			case 6:
				// buscar Personal
				return asignarLista(request);
			case 7:
				// editar personal
				sig = sig4;
				return sig;
			}
			
			if (error) {
				request.setAttribute("mensaje", mensaje);
				return p_error;
			}
			
			return sig;
			
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
	 * Funcinn: Asignar la lista de resultados del filtro<br>
	 * 
	 * @param HttpServletRequest
	 *            request
	 **/
	public String asignarLista(HttpServletRequest request)
			throws ServletException, IOException {
		perf = (String) request.getParameter("usuperfcodigo").substring(
				request.getParameter("usuperfcodigo").indexOf('|') + 1,
				request.getParameter("usuperfcodigo").length());
		String nivel = "";
		String codPerfil = (String) request.getParameter("usuperfcodigo");
		// System.out.println("COD PERFIL: "+codPerfil);
		if (codPerfil != null && !codPerfil.equals("-1"))
			nivel = (String) request.getParameter("usuperfcodigo").substring(0,
					request.getParameter("usuperfcodigo").indexOf('|'));
		// System.out.println("ASIGNAR LISTA NIVEL: "+nivel);
		// departamento="25";
		municipio = request.getParameter("municipio");
		institucion = request.getParameter("institucion");
		sede = request.getParameter("sede");
		jornada = request.getParameter("jornada");
		usupernumdoc = request.getParameter("usupernumdocum");

		// System.out.println(login.getDepId()+"**"+perf+"**"+municipio+"**"+nucleo+"**"+institucion+"**"+sede+"**"+jornada+"***"+usupernumdoc+"***//***"+(perf.equals("-1")
		// && !usupernumdoc.equals("")));

		try {
			if (perf.equals("-1") && !usupernumdoc.equals("")) {
				list = new ArrayList();
				o = new Object[2];
				o[0] = cadena;
				o[1] = usupernumdoc;
				list.add(o);
				col = util.getFiltro(rbUsuario.getString("FiltroDocumento"), list);
				request.setAttribute("filtroDocumento", col);
				sig = "/usuario/FiltroResultado.jsp";
			} else if (!perf.equals("-1")) {
				list2 = new ArrayList();
				o = new Object[2];
				o[0] = enterolargo;
				o[1] = perf;
				list2.add(o);
				consulta = rbUsuario.getString("AsignarLista0");
				if (!nivel.equals("0")) {
					if (municipio == null && institucion == null
							&& sede == null && jornada == null) {
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = login.getDepId();
						list2.add(o);
						consulta += " " + rbUsuario.getString("AsignarLista1");
						// System.out.println("1,1: "+consulta);
					} else if (institucion == null && sede == null
							&& jornada == null) {
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = login.getDepId();
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = municipio;
						list2.add(o);
						consulta += " " + rbUsuario.getString("AsignarLista2");
						// System.out.println("1,2: "+consulta);
					} else if (sede == null && jornada == null) {
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = login.getDepId();
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = municipio;
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = institucion;
						list2.add(o);
						consulta += " " + rbUsuario.getString("AsignarLista3");
						// System.out.println("1,4: "+consulta);
					} else if (sede != null && jornada != null) {
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = login.getDepId();
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = municipio;
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = institucion;
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = sede;
						list2.add(o);
						o = new Object[2];
						o[0] = enterolargo;
						o[1] = jornada;
						list2.add(o);
						consulta += " " + rbUsuario.getString("AsignarLista4");
						/*
						 * System.out.println("1,6: "+consulta);
						 * System.out.println("perf: "+perf);
						 * System.out.println("dep: "+login.getDepId());
						 * System.out.println("mun: "+municipio);
						 * System.out.println("ins: "+institucion);
						 * System.out.println("sede: "+sede);
						 * System.out.println("jorn: "+jornada);
						 */
					} else {
						System.out.println("error en consulta");
					}
				}

				if (!usupernumdoc.equals("")) {
					o = new Object[2];
					o[0] = enterolargo;
					o[1] = usupernumdoc;
					list2.add(o);
					consulta += " and usupernumdocum=?";
					// System.out.println("mas docum: "+consulta);
				}

				consulta += " order by usupernumdocum";

				col = util.getFiltro(consulta, list2);
				// System.out.println("TAMAnO DE CASE: " + col.size());
				if (col.size() != 0) {
					// System.out
					// .println("ENTRO CON USURIO ENCONTRADO BUSCA USUARIO 13");
					request.setAttribute("buscaUsuario13", col);
					String[][] access = util.getFiltroMatriz(col);
					/*
					 * if(access!=null){ for(int
					 * indice=0;indice<access.length;indice++){
					 * System.out.println
					 * ("Me lleva :"+access[indice][0]+"//"+access
					 * [indice][1]+"//"
					 * +access[indice][2]+"//"+access[indice][3]); } }else{
					 * System.out.println("Access es null"); }
					 */
					col = Acceso.getPerfiles(access);
					// System.out.println("GET PERFLES POA 13 :" + col.size());
					request.setAttribute("buscaUsuario2", col);
				}
				sig = "/usuario/FiltroResultado2.jsp";
			}

			Logger.print(login.getUsuarioId(), "Consulta par gerneracinn de resultados", 2, 1, this.toString());
			
		} catch (InternalErrorException e) {
			e.printStackTrace();
		}
		
		return sig;
		
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)	throws ServletException, IOException {
		session.removeAttribute("nuevoUsuario");
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de informaciÃ³n
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request) throws ServletException, IOException {
		
		usuario = (Usuario) request.getSession().getAttribute("nuevoUsuario");
		
		if (request.getSession().getAttribute("login") != null) {
			login = (Login) request.getSession().getAttribute("login");
			return true;
		}
		
		return true;
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
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
	 * Funcinn: Concatena el parametro en una variable que contiennne los
	 * mensajes que se van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!error) {
			error = true;
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

