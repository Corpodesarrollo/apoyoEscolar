package siges.rotacion2;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.rotacion.beans.Horario;
import siges.rotacion.dao.RotacionDAO;
import siges.rotacion2.beans.Rotacion2;
import siges.rotacion2.beans.Validacion;
import siges.rotacion2.dao.Rotacion2DAO;
import siges.rotacion2.util.RotacionExcel;
import siges.util.Properties;

/**
 * @author Administrador
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class ControllerEditar extends HttpServlet {
	public static final long serialVersionUID = 1;
	private String mensaje;
	private Timestamp f2;
	private boolean err;
	private Cursor cursor = new Cursor();
	private Util util = new Util(cursor);//
	private RotacionDAO rotacionDAO = new RotacionDAO(cursor);
	private Rotacion2DAO rotacion2DAO = new Rotacion2DAO(cursor);
	private ResourceBundle rbRot = ResourceBundle
			.getBundle("siges.rotacion2.bundle.rotacion2");
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Funcinn: Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			f2 = new Timestamp(new java.util.Date().getTime());
			String sig, sig10, sig11, sig12, sig13, sig14, sig17, sig21, sig22, sig23, sig31, sig41, sig42, sig43;
			int tipo;
			sig = "/rotacion2/ControllerGuardar.do";
			sig10 = "/rotacion2/NuevoGenerarHorario.jsp";
			sig11 = "/rotacion2/NuevoGenerarHorario2.jsp";
			sig12 = "/rotacion2/Reportes.jsp";
			sig13 = "/rotacion2/HorarioGrupo.jsp";
			sig14 = "/rotacion2/HorarioGrupo2.jsp";
			sig17 = "/rotacion2/Inconsistencia.jsp";
			// sig21 = "/rotacion2/Inconsistencia2.jsp";
			sig22 = "/rotacion2/Confirmar.jsp";
			sig23 = "/rotacion2/Reportes.jsp";
			sig31 = "/rotacion2/EstadoGrupo.jsp";
			sig41 = "/rotacion2/HorarioAsignatura.jsp";
			sig42 = "/rotacion2/HorarioAsignatura2.jsp";
			sig43 = "/rotacion2/HorarioFallo.jsp";

			String p_error = this.getServletContext().getInitParameter("er");
			err = false;
			mensaje = null;
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				tipo = 0;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}
			switch (tipo) {
			case 0:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 22:
			case 23:
			case 24:
			case 31:
			case 41:
			case 42:
			case 43:
				break;
			default:
				tipo = 0;
			}
			if (!asignarBeans(request)) {
				this.setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje", mensaje);
				return p_error;
			}
			Login login = (Login) session.getAttribute("login");
			Rotacion2 r = (Rotacion2) session.getAttribute("rotacion2");
			Validacion v = (Validacion) session.getAttribute("validacion");
			long codInst = Long.parseLong(login.getInstId());
			int vig = (int) rotacion2DAO.getVigenciaInst(codInst);
			if (r != null) {
				r.setUsuario(Long.parseLong(login.getUsuarioId()));
				if (r.getVigencia() == 0) {
					r.setVigencia(vig);
				}
			} else {
				r = new Rotacion2();
				if (r.getVigencia() == 0) {
					r.setVigencia(vig);
				}
				session.setAttribute("rotacion2", r);
			}
			Horario horario = (Horario) session.getAttribute("horario");
			if (horario != null) {
				horario.setInst(login.getInstId());
			} else {
				horario = new Horario();
				horario.setInst(login.getInstId());
			}
			// System.out.println(" EDITAR! " + tipo);
			String dir;
			String cmd = null;
			switch (tipo) {
			case 0:
				// generar Formulario de solicitud
				cargarCombos(request, util, login);
				sig = sig10;
				break;
			case 11:
				// generar Resultados de solicitud
				borrarBeans(request);
				v.setHoja1(cargarDocentesFaltantes(session, r));
				v.setHoja2(null);
				// v.setHoja2(cargarCapacidadEspFis(session, r));
				v.setHoja3(cargarDocentesSinCarga(session, r));
				cargarValidacionEspacios(request, session, login, r);
				v.setUsuario(login.getUsuarioId());
				request.setAttribute("plantilla", plantillaValidacion(v));
				request.setAttribute("tipoArchivo", "xls");
				// System.out.println("---------**--" + r.getGrupo_());
				sig = sig11;
				break;
			case 12:// guardar solicitus
				if (rotacion2DAO.isLocked(r)) {
					setMensaje("No se puede registrar la solicitud porque ya fue solicitada antes y esta en cola o en proceso. \n Pulse en el botnn 'Ver Solicitudes' para revisar la lista de peticiones");
					request.setAttribute("mensaje", mensaje);
					// System.out.println("repetido");
					sig = sig11;
				} else {
					rotacion2DAO.setReporte(r);
					request.setAttribute("lista",
							rotacion2DAO.getReportes(login));
					setMensaje("La solicitud fue registrada satisfactoriamente y se esta procesado. \nEste proceso puede tardar algun tiempo dependiendo de la cantidad de grados y asignaturas");
					request.setAttribute("mensaje", mensaje);
					sig = sig12;
				}
				break;
			case 13:// ver formulario de horario
				request.setAttribute("refrescar", "1");
				dir = request.getParameter("dir");
				sig = sig13;
				editarHorario(request, login, horario, dir);
				break;
			case 14:// ver Maya de horario
				sig = sig14;
				ponerHorarioGrupo(request, horario);
				break;
			case 15:// ver Reporte de solicitudes
				sig = sig12;
				String id = request.getParameter("dir");
				if (id != null && !id.equals("")) {
					borrarSolicitud(id);
				}
				request.setAttribute("lista", rotacion2DAO.getReportes(login));
				break;
			case 17:// ver FORMA DE INCONSISTENCIAS GENERALES
				sig = sig17;
				setInconsistenciasGenerales(request, horario);
				break;
			case 19:// refresca
				request.setAttribute("refrescar", "0");
				sig = sig13;
				break;
			case 22:// ver forma de confirmacion
				sig = sig22;
				String aut = request.getParameter("aut");
				dir = request.getParameter("dir");
				confirmar(request, horario, dir, aut);
				break;
			case 23:// hacer lo que confirmacion de accion masiva diga
				cmd = request.getParameter("cmd");
				ejecutar(request, horario, cmd, login);
				sig = sig23;
				break;
			case 24:// Aceptar o rechazar Grupo
				cmd = request.getParameter("cmd");
				ejecutarGrupo(request, horario, cmd, login);
				sig = sig13;
				break;
			case 31:// VER ESTADO DE LOS GRUPOS
				sig = sig31;
				setEstadoGrupos(request, horario);
				break;
			case 41:// VER FORMA DE HORARIO ASIGNATURA
				dir = request.getParameter("dir");
				editarHorario(request, login, horario, dir);
				sig = sig41;
				break;
			case 42:// VER MAYA DE HORARIO ASIGNATURA
				ponerHorarioAsignatura(request, horario);
				sig = sig42;
				break;
			case 43:// VER MAYA DE FALLOS
				ponerHorarioFalla(request, horario);
				sig = sig43;
				break;
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

	public String getNombre(String ini, Validacion v) {
		String fe = f2.toString().replace(':', '-').replace('.', '-')
				.substring(0, 10)
				+ "_"
				+ f2.toString().replace(':', '-').replace('.', '-')
						.substring(11, 19);
		return (ini + "_Sede_" + formatear(v.getSede_()) + "_Jor"
				+ formatear(v.getJornada_()) + "_Grado"
				+ formatear(v.getNombreGrado()) + "_(" + fe + ").xls");
	}

	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'n')
				.replace('n', 'N').replace('n', 'a').replace('n', 'e')
				.replace('n', 'i').replace('n', 'o').replace('n', 'u')
				.replace('n', 'A').replace('n', 'E').replace('n', 'I')
				.replace('n', 'O').replace('n', 'U').replace('n', 'c')
				.replace(':', '_').replace('.', '_').replace('/', '_').replace(
				'\\', '_'));
	}

	public String plantillaValidacion(Validacion v) {
		RotacionExcel excel = new RotacionExcel();
		String relativo = "";
		String path = "", archivo = "";
		int i = 0;
		String[] config = new String[4];
		/* CONFIGURACION DE ARCHIVOS */
		path = rbRot.getString("plantillaValidacion.PathPlantilla") + "."
				+ v.getUsuario();
		/* calcular el nombre del archivo */
		archivo = getNombre("PlantillaValidacion", v);
		relativo = Ruta.getRelativo(getServletContext(), path);
		i = 0;
		config[i++] = Ruta2.get(getServletContext(),
				rbRot.getString("plantillaValidacion.Path"));// path
																// de
																// la
																// plantilla
		config[i++] = rbRot.getString("plantillaValidacion.Plantilla");// nombre
																		// de la
																		// plantilla
		config[i++] = Ruta.get(getServletContext(), path);// path del nuevo
															// archivo
		config[i++] = archivo;// nombre del nuevo archivo
		if (!excel.plantillaValidacion(config, v)) {
			setMensaje(excel.getMensaje());
			return "--";
		}
		// Logger.print(v.getUsuario(),"Bateria Logro
		// Inst:"+logros.getPlantillaInstitucion()+"
		// Metod:"+logros.getPlantillaMetodologia()+"
		// Grado:"+logros.getPlantillaGrado()+"
		// Asig:"+asig,3,1,this.toString());
		rotacion2DAO.ponerReporte(v.getUsuario(), relativo + config[3], "xls",
				config[3], 5);
		return relativo + config[3];
	}

	public void borrarSolicitud(String id) {
		rotacion2DAO.borrarSolicitud(id);
	}

	public void ejecutar(HttpServletRequest request, Horario horario,
			String cmd, Login l) throws ServletException, IOException {
		boolean band = false;
		if (cmd != null) {
			String a = horario.getAccion();
			if (a.equals("1")) {// Aceptar
				if (cmd.equals("1")) {// SI
					band = rotacion2DAO.setHorarioPropuesto(horario, l);
				}
				if (band) {
					request.setAttribute("mensaje",
							"Horario propuesto ha sido oficializado satisfactoriamente");
				} else {
					request.setAttribute("mensaje",
							"No se pudo oficializar el horario propuesto: "
									+ rotacion2DAO.getMensaje());
				}
			}
			if (a.equals("2")) {// Rechazar
				if (cmd.equals("1")) {// SI
					band = rotacion2DAO.setHorarioOficial(horario, l);
				}
				if (band) {
					request.setAttribute("mensaje",
							"Horario propuesto ha sido rechazado satisfactoriamente");
				} else {
					request.setAttribute("mensaje",
							"No se pudo rechazar el horario propuesto: "
									+ rotacion2DAO.getMensaje());
				}
			}
		}
		request.setAttribute("lista", rotacion2DAO.getReportes(l));
	}

	public void ejecutarGrupo(HttpServletRequest request, Horario horario,
			String cmd, Login l) throws ServletException, IOException {
		boolean band = false, band2 = false;
		String alert = "";
		if (cmd != null) {
			int cmd_ = Integer.parseInt(cmd);
			String a = horario.getAccion();
			if (cmd_ == 1) {// SI ACEPTO
				band = rotacion2DAO.aceptarHorarioGrupo(horario, l);
			}
			if (cmd_ == 2) {// SI RECHAZO
				band = rotacion2DAO.rechazarHorarioGrupo(horario, l);
			}
			if (band) {
				band2 = rotacion2DAO.validarUltimoGrupo(horario, l);
				if (band2) {
					alert = "\n y se Oficializn toda la solicitud de rotacinn porque era el nltimo grupo en validar";
				}
				if (cmd_ == 1) {
					request.setAttribute("mensaje",
							"Horario de grupo ha sido oficializado satisfactoriamente"
									+ alert);
				}
				if (cmd_ == 2) {
					request.setAttribute("mensaje",
							"Horario de grupo ha sido rechazado satisfactoriamente"
									+ alert);
				}
			} else {
				if (cmd_ == 1) {
					request.setAttribute("mensaje",
							"No se pudo oficializar el horario del grupo: "
									+ rotacion2DAO.getMensaje());
				}
				if (cmd_ == 2) {
					request.setAttribute("mensaje",
							"No se pudo rechazar el horario del grupo: "
									+ rotacion2DAO.getMensaje());
				}
			}
		}
		editarHorario(request, l, horario, null);
	}

	public void confirmar(HttpServletRequest request, Horario horario,
			String dir, String aut) throws ServletException, IOException {
		if (dir != null) {
			String[] a = dir.replace('|', ':').split(":");
			horario.setSede(a[0]);
			horario.setJornada(a[1]);
			horario.setMetodologia(a[2]);
			horario.setGrado(a[3]);
			horario.setGrupo(a[4]);
			horario.setGradoRotacion(a[3]);
			horario.setGrupoRotacion(a[4]);
			horario.setIdSolicitud(a[5]);
			horario.setVigencia(Integer.parseInt(a[6]));
			horario.setAccion(aut);
		}
	}

	public void ponerHorarioGrupo(HttpServletRequest request, Horario horario) {
		String[] r = rotacionDAO.getHorarioEstructura(horario);
		if (r != null) {
			request.setAttribute("hora", r);
		} else {
			request.setAttribute("mensaje",
					"No se han configurado los parametros ");
			return;
		}

		horario.setEditable(rotacionDAO.getEditable(horario));
		request.setAttribute("hora2",
				rotacionDAO.getHorarioPropuestoGrupo(horario));
		horario.setJerGrupo(rotacionDAO.getJeraquia());
		request.setAttribute("listaEsp", rotacionDAO.getEspacios(horario));
		request.setAttribute("filtroDocenteF",
				rotacionDAO.getDocentesGrado(horario));
		request.setAttribute("filtroAsignaturaF",
				rotacionDAO.getAsignaturasGrado(horario));
	}

	public void setInconsistenciasGenerales(HttpServletRequest request,
			Horario horario) throws ServletException, IOException {
		request.setAttribute("listaIncGral",
				rotacionDAO.getInconsistenciasGenerales(horario));
		/*
		 * request.setAttribute("listaEsp", rotacionDAO.getEspacios(horario));
		 * request.setAttribute("filtroDocenteF",
		 * rotacionDAO.getDocentesGrado(horario));
		 * request.setAttribute("filtroAsignaturaF",
		 * rotacionDAO.getAsignaturasGrado(horario));
		 */
	}

	public void setEstadoGrupos(HttpServletRequest request, Horario horario)
			throws ServletException, IOException {
		request.setAttribute("listaEstadoGrupos",
				rotacionDAO.getEstadoGrupos(horario));
	}

	public void editarHorario(HttpServletRequest request, Login login,
			Horario horario, String dir) throws ServletException, IOException {
		// String s;
		if (dir != null) {
			// System.out.println("Valor de DIR: "+dir);
			/*
			 * DAROTSEDE//0 DAROTJOR//1 DAROTMET//2 DAROTGRADO//3 DAROTGRUPO//4
			 * DAROTID//5//DAROTVIGENCIA//6
			 */
			String[] a = dir.replace('|', ':').split(":");
			horario.setSede(a[0]);
			horario.setJornada(a[1]);
			horario.setMetodologia(a[2]);
			horario.setGrado(a[3]);
			horario.setGrupo(a[4]);
			horario.setGradoRotacion(a[3]);
			horario.setGrupoRotacion(a[4]);
			horario.setIdSolicitud(a[5]);
			horario.setVigencia(Integer.parseInt(a[6]));
		}
		Collection[] list = rotacionDAO.getFiltroRot2(login, horario);
		request.setAttribute("filtroGradoR2", list[0]);
		request.setAttribute("filtroGrupoR2", list[1]);
	}

	public void cargarCombos(HttpServletRequest request, Util util, Login login)
			throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		request.setAttribute("filtroSedeF", util.getFiltro(
				rbRot.getString("Lista.filtroSedeInstitucion"), list));
		request.setAttribute("filtroJornadaF", util.getFiltro(
				rbRot.getString("Lista.filtroSedeJornadaInstitucion"), list));
		request.setAttribute("filtroMetodologiaF", util.getFiltro(
				rbRot.getString("Lista.MetodologiaInstitucion"), list));
		request.setAttribute("filtroGradoF2", util.getFiltro(
				rbRot.getString("Lista.filtroSedeJornadaGradoInstitucion2"),
				list));
		request.setAttribute(
				"filtroGrupoF",
				rotacionDAO.getFiltro(
						rbRot.getString("Lista.filtroSedeJornadaGradoGrupoInstitucion"),
						list));
		long codInst = Long.parseLong(login.getInstId());
		int vig = (int) rotacion2DAO.getVigenciaInst(codInst);
		List l = new ArrayList();
		l.add(String.valueOf(vig - 1));
		l.add(String.valueOf(vig));
		l.add(String.valueOf(vig + 1));
		request.setAttribute("listaVigencia", l);
	}

	public Collection cargarCapacidadEspFis(HttpSession session, Rotacion2 r)
			throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(r.getInstitucion());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(r.getSede());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(r.getJornada());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		long codInst = Long.parseLong(r.getInstitucion() + "");
		int vig = (int) rotacion2DAO.getVigenciaInst(codInst);

		o[1] = "" + vig;

		list.add(o);
		String[][] c1 = util.getFiltroMatriz(
				rbRot.getString("Lista.CapacidadEspAsig"), list);
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(r.getInstitucion());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(r.getMetodologia());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		String[] z = rotacion2DAO.obtenerEspAsigGrado(r);
		o[1] = (z[0] == null ? "-1" : z[0]);
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = (z[1] == null ? "-1" : z[1]);
		list.add(o);
		String[][] c2 = util.getFiltroMatriz(
				rbRot.getString("Lista.CapacidadGrados"), list);
		// System.out.println("jorn: " + z[0]);
		// System.out.println("grado: " + z[1]);
		list = new ArrayList();
		if (c1 != null && c1 != null) {
			for (int i = 0; i < c1.length; i++) {
				for (int j = 0; j < c2.length; j++) {
					if (Integer.parseInt(c1[i][4]) < Integer.parseInt(c2[j][1])) {
						o = new Object[6];
						o[0] = c1[i][1];
						o[1] = c1[i][6];
						o[2] = c1[i][3];
						o[3] = c1[i][4];
						o[4] = c2[j][0];
						o[5] = c2[j][1];
						list.add(o);
					}
				}
			}
		}
		session.setAttribute("capacidadEspFis", list);
		return list;
	}

	public Collection cargarDocentesFaltantes(HttpSession session, Rotacion2 r)
			throws Exception {
		Collection c = rotacion2DAO.getDocentesFaltantes(r);
		session.setAttribute("docentesFaltantes", c);
		return c;
	}

	public Collection cargarDocentesSinCarga(HttpSession session, Rotacion2 r)
			throws Exception {
		Collection c = rotacion2DAO.getDocentesSinCarga(r);
		session.setAttribute("docentesSinCarga", c);
		return c;
	}

	public void cargarValidacionEspacios(HttpServletRequest request,
			HttpSession session, Login login, Rotacion2 r) throws Exception {
		Collection c = rotacion2DAO.getValidacionEspacios(r,
				Long.parseLong(login.getUsuarioId()));
		if (c == null || c.size() == 0) {
			request.setAttribute("invalido", "0");
		} else {
			request.setAttribute("invalido", "1");
		}
		session.setAttribute("validacionEspacios", c);
	}

	/**
	 * Funcinn: Inserta los valores por defecto para el bean de información
	 * bnsica con respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null) {
			return false;
		}
		return true;
	}

	/**
	 * Funcinn: Elimina del contexto de la sesion los beans de informacion del
	 * usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("rotacion2");
		session.removeAttribute("rotacion2b");
	}

	/**
	 * Funcinn: Recibe la peticion por el metodo get de HTTP
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
	 * Funcinn: Recibe la peticion por el metodo Post de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		if (s != null && !s.equals("")) {
			String targ = (String) request.getSession().getAttribute(
					"servTarget");
			if (targ != null && targ.equals("" + Properties._BLANK)) {
				ir(2, s, request, response);
			} else {
				ir(1, s, request, response);
			}
		}
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
	 * Funcinn: Concatena el parametro en una variable que contiene los mensajes
	 * que se van a enviar a la vista
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

	public void ponerHorarioAsignatura(HttpServletRequest request,
			Horario horario) {
		String[] r = rotacionDAO.getHorarioEstructura(horario);
		if (r != null) {
			request.setAttribute("hora", r);
		} else {
			request.setAttribute("mensaje",
					"No se han configurado los parnmetros del horario");
			return;
		}
		request.setAttribute("horarioAsignatura",
				rotacionDAO.getHorarioPropuestoAsignatura(horario));
	}

	public void ponerHorarioFalla(HttpServletRequest request, Horario horario) {
		String[] r = rotacionDAO.getHorarioEstructura(horario);
		if (r != null) {
			request.setAttribute("hora", r);
		} else {
			request.setAttribute("mensaje",
					"No se han configurado los parnmetros del horario");
			return;
		}
		request.setAttribute("horarioFallo",
				rotacionDAO.getHorarioFalla(horario));
	}
}