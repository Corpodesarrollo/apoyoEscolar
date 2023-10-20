package siges.rotacion;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.rotacion.beans.DocXGrupoVO;
import siges.rotacion.beans.EspXGradoVO;
import siges.rotacion.beans.FiltroRotacion;
import siges.rotacion.beans.Horario;
import siges.rotacion.beans.ParamsVO;
import siges.rotacion.beans.Rotacion;
import siges.rotacion.dao.RotacionDAO;

/**
 * @author Administrador
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class ControllerEditar extends HttpServlet {
	public static final long serialVersionUID = 1;
	private Cursor cursor = new Cursor();
	private Util util = new Util(cursor);
	private RotacionDAO rotacionDAO = new RotacionDAO(cursor);
	private ResourceBundle rbRot = ResourceBundle
			.getBundle("siges.rotacion.bundle.rotacion");;
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);

	/**
	 * Funcinn: Procesa la peticion HTTP.
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
			String sig, sig11, sig21, sig31, sig41, sig51, sig61, sig71, sig81, sig91, sig100, sig102, sig101, sig110, sig112, sig111, sig121;
			String dato, dato2, dato3, dato4, dato5;
			String fichaDocenteGrupo;
			String fichaEspacioGrado;
			int tipo;
			String docenteescogido = "";
			sig = "/rotacion/ControllerGuardar.do";
			sig11 = "/rotacion/NuevoEstructura.jsp";
			sig21 = "/rotacion/NuevoFijarEspacios.jsp";
			sig31 = "/rotacion/NuevoFijarDocentes.jsp";
			sig41 = "/rotacion/NuevoInhabilitarEspacios.jsp";
			sig51 = "/rotacion/NuevoInhabilitarDocentes.jsp";
			sig61 = "/rotacion/NuevoFijarAsignatura.jsp";
			sig71 = "/rotacion/NuevoFijarEspacioDocente.jsp";
			sig81 = "/rotacion/NuevoPriorizar.jsp";
			sig91 = "/rotacion/NuevoGenerarHorario.jsp";
			sig100 = "/rotacion/Horario.jsp";
			sig101 = "/rotacion/Horario.jsp";
			sig102 = "/rotacion/Horario2.jsp";
			sig110 = "/rotacion/BorrarHorario.jsp";
			sig111 = "/rotacion/BorrarHorario.jsp";
			sig112 = "/rotacion/BorrarHorario2.jsp";
			sig121 = "/rotacion/NuevoInhabilitarHora.jsp";
			fichaDocenteGrupo = "/rotacion/NuevoDocenteGrupo.jsp";
			fichaEspacioGrado = "/rotacion/NuevoEspacioGrado.jsp";
			String p_error = this.getServletContext().getInitParameter("er");
			String[] params = null;

			String[] aa = null;
			if (request.getParameter("tipo") == null
					|| ((String) request.getParameter("tipo")).equals("")) {
				borrarBeans(request);
				tipo = 10;
			} else {
				tipo = Integer.parseInt((String) request.getParameter("tipo"));
			}

			docenteescogido = (String) request.getParameter("docenteescogido");
			try {
				if (!docenteescogido.equals("") && docenteescogido != "") {

					session.setAttribute("docenteescogido", docenteescogido);
				}
			} catch (NullPointerException exp) {

			}

			int CMD = getCmd(request, session, Params.CMD_NUEVO);
			if (!asignarBeans(request)) {
				request.setAttribute(
						"mensaje",
						this.getMensaje("Error capturando datos de sesinn para el usuario"));
				return p_error;
			}
			Login login = (Login) session.getAttribute("login");
			Rotacion r = (siges.rotacion.beans.Rotacion) session
					.getAttribute("rotacion");
			// Rotacion r2 = (siges.rotacion.beans.Rotacion)
			// session.getAttribute("rotacionEditar");
			DocXGrupoVO docXGrupoVO = (DocXGrupoVO) session
					.getAttribute("docXGrupoVO");
			EspXGradoVO espXGradoVO = (EspXGradoVO) session
					.getAttribute("espXGradoVO");
			Horario horario = (Horario) session.getAttribute("horario");
			if (horario != null) {
				horario.setInst(login.getInstId());
			} else {
				horario = new Horario();
				horario.setInst(login.getInstId());
			}
			session.setAttribute("isLockedRotacion",
					rotacionDAO.isLocked(login));
			// System.out.println("EDITAR rotacion=" + tipo + " cmd " + CMD);
			if (tipo == 100 && CMD == 0)
				request.setAttribute("horarioEntraref", "1");
			switch (tipo) {
			case 11:
			case 21:
			case 31:
			case 51:
			case 61:
			case 71:
			case 81:
			case 121:
			case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:
			case ParamsVO.FICHA_ESPACIO_GRADO:
			case 10:
			case 12:
			case 13:
			case 16:
			case 20:
			case 22:
			case 23:
			case 26:
			case 29:
			case 30:
			case 32:
			case 33:
			case 36:
			case 39:
			case 40:
			case 41:
			case 47:
			case 42:
			case 43:
			case 46:
			case 49:
			case 50:
			case 52:
			case 53:
			case 56:
			case 59:
			case 60:
			case 62:
			case 63:
			case 66:
			case 69:
			case 70:
			case 72:
			case 73:
			case 76:
			case 79:
			case 80:
			case 90:
			case 100:
			case 101:
			case 102:
			case 110:
			case 111:
			case 112:
			case 120:
			case 122:
			case 123:
			case 126:
			case 129:
				break;
			default:
				tipo = 10;
			}
			// System.out.println("EDITAR rotacion=" + tipo);
			// REVISION DE FILTRO
			int vig = (int) rotacionDAO.getVigenciaInst(Long.parseLong(login
					.getInstId()));
			FiltroRotacion filtroRotacion = (FiltroRotacion) request
					.getSession().getAttribute("filtroRotacion");
			if (filtroRotacion == null) {
				filtroRotacion = new FiltroRotacion();
			}
			if (filtroRotacion.getFilAnhoVigencia() == 0) {
				filtroRotacion.setFilAnhoVigencia(vig);
			}
			if (filtroRotacion.getFilMetodologia() == 0) {
				filtroRotacion.setFilMetodologia(rotacionDAO
						.getMetodologia(Long.parseLong(login.getInstId())));
			}
			request.getSession().setAttribute("filtroRotacion", filtroRotacion);
			// FIN DE REVISION DE FILTRO
			switch (tipo) {
			case ParamsVO.FICHA_DOCENTE_GRUPO_NUEVO:
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
				case ParamsVO.CMD_NUEVO:
					if (docXGrupoVO != null) {
						docXGrupoVO.setFormaEstado("0");
					}
					session.removeAttribute("docXGrupoVO");
					break;
				case ParamsVO.CMD_EDITAR:
					params = request.getParameterValues("param");
					if (params == null || params.length < 4
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null) {
						request.setAttribute("mensaje",
								getMensaje("No se pudo obtener"));
					} else {
						docXGrupoVO = rotacionDAO.getDocenteGrupo(
								Long.parseLong(params[0]),
								Long.parseLong(params[1]),
								Long.parseLong(params[2]),
								Long.parseLong(params[3]));
						if (docXGrupoVO != null) {
							session.setAttribute("docXGrupoVO", docXGrupoVO);
							request.setAttribute("guia", params[0]);
							request.setAttribute("guia2", params[1]);
							request.setAttribute("guia3", params[2]);
						} else {
							request.setAttribute("mensaje",
									getMensaje("No se pudo obtener: "
											+ rotacionDAO.getMensaje()));
						}
					}
					break;
				case ParamsVO.CMD_ELIMINAR:
					params = request.getParameterValues("param");
					if (params == null || params.length < 3
							|| params[0] == null || params[1] == null
							|| params[2] == null) {
						request.setAttribute("mensaje",
								getMensaje("No se pudo eliminar"));
					} else {
						if (!rotacionDAO.eliminarDocenteGrupo(
								Long.parseLong(params[0]),
								Long.parseLong(params[1]),
								Long.parseLong(params[2]))) {
							request.setAttribute("mensaje",
									getMensaje("No se pudo eliminar: "
											+ rotacionDAO.getMensaje()));
						} else {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							if (docXGrupoVO != null) {
								docXGrupoVO.setFormaEstado("0");
								session.removeAttribute("docXGrupoVO");
							}
						}
					}
					break;
				case ParamsVO.CMD_GUARDAR:
					if (docXGrupoVO.getFormaEstado().equals("1")) {// actualizar
						if (rotacionDAO.actualizarDocenteGrupo(docXGrupoVO)) {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							docXGrupoVO.setFormaEstado("0");
							session.removeAttribute("docXGrupoVO");
						} else {
							request.setAttribute(
									"mensaje",
									getMensaje("No se pudo actualizar la asignacinn: "
											+ rotacionDAO.getMensaje()));
						}
					} else {// ingresar
						if (rotacionDAO.ingresarDocenteGrupo(docXGrupoVO)) {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							docXGrupoVO.setFormaEstado("0");
							session.removeAttribute("docXGrupoVO");
						} else {
							request.setAttribute(
									"mensaje",
									getMensaje("No se pudo ingresar la asignacinn: "
											+ rotacionDAO.getMensaje()));
						}
					}
					break;
				}
				initNuevoDocenteGrupo(request, login, docXGrupoVO);
				sig = fichaDocenteGrupo;
				break;
			case ParamsVO.FICHA_ESPACIO_GRADO:
				switch (CMD) {
				case ParamsVO.CMD_BUSCAR:
				case ParamsVO.CMD_NUEVO:
					if (espXGradoVO != null) {
						espXGradoVO.setFormaEstado("0");
					}
					session.removeAttribute("espXGradoVO");
					break;
				case ParamsVO.CMD_EDITAR:
					params = request.getParameterValues("param");
					if (params == null || params.length < 6
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null) {
						request.setAttribute("mensaje",
								getMensaje("No se pudo obtener"));
					} else {
						espXGradoVO = rotacionDAO.getEspacioGrado(
								Long.parseLong(params[0]),
								Integer.parseInt(params[1]),
								Integer.parseInt(params[2]),
								Integer.parseInt(params[3]),
								Integer.parseInt(params[4]),
								Long.parseLong(params[5]));
						if (espXGradoVO != null) {
							session.setAttribute("espXGradoVO", espXGradoVO);
							request.setAttribute("guia", params[0]);
							request.setAttribute("guia2", params[1]);
							request.setAttribute("guia3", params[2]);
							request.setAttribute("guia4", params[3]);
							request.setAttribute("guia5", params[4]);
						} else {
							request.setAttribute("mensaje",
									getMensaje("No se pudo obtener: "
											+ rotacionDAO.getMensaje()));
						}
					}
					break;
				case ParamsVO.CMD_ELIMINAR:
					params = request.getParameterValues("param");
					if (params == null || params.length < 6
							|| params[0] == null || params[1] == null
							|| params[2] == null || params[3] == null
							|| params[4] == null || params[5] == null) {
						request.setAttribute("mensaje",
								getMensaje("No se pudo eliminar"));
					} else {
						if (!rotacionDAO.eliminarEspacioGrado(
								Long.parseLong(params[0]),
								Integer.parseInt(params[1]),
								Integer.parseInt(params[2]),
								Integer.parseInt(params[3]),
								Integer.parseInt(params[4]),
								Long.parseLong(params[5]))) {
							request.setAttribute("mensaje",
									getMensaje("No se pudo eliminar: "
											+ rotacionDAO.getMensaje()));
						} else {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							if (espXGradoVO != null) {
								espXGradoVO.setFormaEstado("0");
								session.removeAttribute("espXGradoVO");
							}
						}
					}
					break;
				case ParamsVO.CMD_GUARDAR:
					if (espXGradoVO.getFormaEstado().equals("1")) {// actualizar
						if (rotacionDAO.actualizarEspacioGrado(espXGradoVO)) {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							espXGradoVO.setFormaEstado("0");
							session.removeAttribute("espXGradoVO");
						} else {
							request.setAttribute(
									"mensaje",
									getMensaje("No se pudo actualizar la asignacinn: "
											+ rotacionDAO.getMensaje()));
						}
					} else {// ingresar
						if (rotacionDAO.ingresarEspacioGrado(espXGradoVO)) {
							request.setAttribute("mensaje",
									getMensaje("Operacinn satisfecha"));
							espXGradoVO.setFormaEstado("0");
							session.removeAttribute("espXGradoVO");
						} else {
							request.setAttribute(
									"mensaje",
									getMensaje("No se pudo ingresar la asignacinn: "
											+ rotacionDAO.getMensaje()));
						}
					}
					break;
				}
				initNuevoEspacioGrado(request, login, espXGradoVO);
				sig = fichaEspacioGrado;
				break;
			case 10:
				// Defecto Nuevo estructura
				cargarEstructura(request, login, filtroRotacion);
				borrarBeans(request);
				sig = sig11;
				break;
			case 12:
				// Editar estructura
				dato = request.getParameter("id");
				r = rotacionDAO.asignarEstructura(dato);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				cargarEstructura(request, login, filtroRotacion);

				sig = sig11;
				break;
			case 13:
				// eliminar estructura
				dato = request.getParameter("id");
				if (rotacionDAO.eliminarEstructura(dato)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "", "Elimininacinn en Tabla 'rot_estructura'", 7, 1,
						this.toString());
				sig = sig11;
				break;
			case 16:
				// nuevo estructura

				cargarEstructura(request, login, filtroRotacion);

				borrarBeans(request);
				sig = sig11;
				break;
			/*
			 * case 19: //Refrescar estructura cargarEstructura(request, login);
			 * 
			 * borrarBeans(request); request.setAttribute("refrescar","0");
			 * request.setAttribute("mensaje",request.getParameter("mensaje"));
			 * sig=sig11; break;
			 */
			case 20:
				// Defecto espacio
				cargarFijarEspacio(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig21;
				break;
			case 21:
				// Guardar espacio
				cargarFijarEspacio(request, filtroRotacion, login);
				break;
			case 22:
				// Editar espacio
				aa = request.getParameter("id").replace('|', ':').split(":");
				dato = aa[0];
				dato2 = aa[1];
				dato3 = aa[2];
				dato4 = aa[3];
				dato5 = aa[4];
				r = rotacionDAO.asignarFijarEspacio(dato, dato2, dato3, dato4,
						dato5, login.getInstId());
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					// System.out.println("ES NULO EL OBJETO FIJAR ESPACIO");
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}
				cargarFijarEspacio(request, filtroRotacion, login);
				sig = sig21;
				break;
			case 23:
				// eliminar espacio
				aa = request.getParameter("id").replace('|', ':').split(":");
				dato = aa[0];
				dato2 = aa[1];
				dato3 = aa[2];
				dato4 = aa[3];
				if (rotacionDAO.eliminarFijarEspacio(dato, dato2, dato3, dato4,
						filtroRotacion.getFilMetodologia())) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "",
						"Elimininacinn en Tabla 'rot_espacio_asig_grado'", 7,
						1, this.toString());

				sig = sig21;
				break;
			case 26:
				// nuevo espacio

				borrarBeans(request);
				cargarFijarEspacio(request, filtroRotacion, login);
				sig = sig21;
				break;
			case 29:
				// Refrescar espacio

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarFijarEspacio(request, filtroRotacion, login);
				sig = sig21;
				break;
			case 30:
				// Defecto docente

				cargarDocente(request, login);
				borrarBeans(request);
				sig = sig31;
				break;
			case 32:
				// Editar docente

				dato = request.getParameter("id").substring(0,
						request.getParameter("id").indexOf("|"));
				dato2 = request.getParameter("id").substring(
						request.getParameter("id").indexOf("|") + 1,
						request.getParameter("id").lastIndexOf("|"));
				dato3 = request.getParameter("id").substring(
						request.getParameter("id").lastIndexOf("|") + 1,
						request.getParameter("id").length());
				r = rotacionDAO.asignarFijarDocente(dato, dato2, dato3,
						login.getInstId());
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				cargarDocente(request, login);
				sig = sig31;
				break;
			case 33:
				// eliminar docente

				dato = request.getParameter("id").substring(0,
						request.getParameter("id").indexOf("|"));
				dato2 = request.getParameter("id").substring(
						request.getParameter("id").indexOf("|") + 1,
						request.getParameter("id").lastIndexOf("|"));
				dato3 = request.getParameter("id").substring(
						request.getParameter("id").lastIndexOf("|") + 1,
						request.getParameter("id").length());
				if (rotacionDAO.eliminarFijarDocente(dato, dato2, dato3)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "",
						"Elimininacinn en Tabla 'rot_espacio_asig_grado'", 7,
						1, this.toString());

				sig = sig31;
				break;
			case 36:
				// nuevo docente

				borrarBeans(request);
				cargarDocente(request, login);
				sig = sig31;
				break;
			case 39:
				// Refrescar docente

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarDocente(request, login);
				sig = sig31;
				break;
			case 41:// guardar inhesp
				cargarInhEspacios(request, filtroRotacion, login);
				break;
			case 40:
			case 47:
				// defecto esp fis x jorn
				cargarInhEspacios(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig41;
				break;
			case 42:
				// editar esp fis x jorn
				aa = request.getParameter("id").replace('|', ':').split(":");
				// System.out.println("------ " + aa[0] + " - " + aa[1] + " - "
				// + aa[2] + " - " + aa[3] + " - " + aa[4]);
				r = rotacionDAO.asignarInhEspacios(aa);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					// System.out.println("NO LLEGO NADA");
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}
				cargarInhEspacios(request, filtroRotacion, login);
				sig = sig41;
				break;
			case 43:
				// eliminar esp fis x jorn
				aa = request.getParameter("id").replace('|', ':').split(":");
				if (rotacionDAO.eliminarInhEspacios(aa)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "", "Elimininacinn en Tabla 'rot_espfis_jor'", 7, 1,
						this.toString());

				sig = sig41;
				break;
			case 46:
				// nuevo esp fis x jorn
				borrarBeans(request);
				cargarInhEspacios(request, filtroRotacion, login);
				sig = sig41;
				break;
			case 49:
				// Refrescar esp fis x jorn

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarInhEspacios(request, filtroRotacion, login);
				sig = sig41;
				break;
			case 50:
				// defecto inhabilitar docentes
				cargarDocInh(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig51;
				break;
			case 51:
				// guardar inhabilitar docentes
				cargarDocInh(request, filtroRotacion, login);
				break;
			case 52:
				// editar inhabilitar docentes
				aa = request.getParameter("id").replace('|', ':').split(":");
				r = rotacionDAO.asignarInhDocente(aa);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				cargarDocInh(request, filtroRotacion, login);
				sig = sig51;
				break;
			case 53:
				// eliminar inhabilitar docentes
				aa = request.getParameter("id").replace('|', ':').split(":");
				if (rotacionDAO.eliminarInhDocente(aa)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");

				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "",
						"Elimininacinn en Tabla 'rot_inhabilitar_docente'", 7,
						1, this.toString());

				sig = sig51;
				break;
			case 56:
				// nuevo inhabilitar docentes

				borrarBeans(request);
				cargarDocInh(request, filtroRotacion, login);
				sig = sig51;
				break;
			case 59:
				// Refrescar inhabilitar docentes

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarDocInh(request, filtroRotacion, login);
				sig = sig51;
				break;
			case 60:
				// defecto fijar asignaturas

				cargarFijarAsignatura(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig61;
				break;
			case 61:
				// guardar fijar asignaturas
				cargarFijarAsignatura(request, filtroRotacion, login);
				break;
			case 62:
				// editar fijar asignaturas

				dato = request.getParameter("id").substring(0,
						request.getParameter("id").indexOf("|"));
				dato2 = request.getParameter("id").substring(
						request.getParameter("id").indexOf("|") + 1,
						request.getParameter("id").length());
				r = rotacionDAO.asignarFijarAsignatura(dato, dato2);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				cargarFijarAsignatura(request, filtroRotacion, login);
				sig = sig61;
				break;
			case 63:
				// eliminar fijar asignaturas

				dato = request.getParameter("id").substring(0,
						request.getParameter("id").indexOf("|"));
				dato2 = request.getParameter("id").substring(
						request.getParameter("id").indexOf("|") + 1,
						request.getParameter("id").length());
				if (rotacionDAO.eliminarFijarAsignatura(dato, dato2)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "", "Elimininacinn en Tabla 'rot_fijar_asignatura'",
						7, 1, this.toString());

				sig = sig61;
				break;
			case 66:
				// nuevo fijar asignaturas

				borrarBeans(request);
				cargarFijarAsignatura(request, filtroRotacion, login);
				sig = sig61;
				break;
			case 69:
				// Refrescar fijar asignaturas

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarFijarAsignatura(request, filtroRotacion, login);
				sig = sig61;
				break;
			case 70:
				// defecto fijar espacio por docente
				cargarFijarEspacioDocente(request, login);
				borrarBeans(request);
				sig = sig71;
				break;
			case 72:
				// editar fijar espacio por docente
				aa = request.getParameter("id").replace('|', ':').split(":");
				dato = aa[0];
				dato2 = aa[1];
				dato3 = aa[2];
				dato4 = aa[3];
				r = rotacionDAO.asignarFijarEspacioDocente(dato, dato2, dato3,
						dato4);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				cargarFijarEspacioDocente(request, login);
				sig = sig71;
				break;
			case 73:
				// eliminar fijar espacio por docente
				aa = request.getParameter("id").replace('|', ':').split(":");
				dato = aa[0];
				dato2 = aa[1];
				dato3 = aa[2];
				dato4 = aa[3];
				if (rotacionDAO.eliminarFijarEspacioDocente(dato, dato2, dato3,
						dato4)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "", "Elimininacinn en Tabla 'rot_espacio_docente'",
						7, 1, this.toString());

				sig = sig71;
				break;
			case 76:
				// nuevo fijar espacio por docente

				borrarBeans(request);
				cargarFijarEspacioDocente(request, login);
				sig = sig71;
				break;
			case 79:
				// Refrescar fijar espacio por docente

				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarFijarEspacioDocente(request, login);
				sig = sig71;
				break;
			case 80:
				// priorizar

				borrarBeans(request);
				cargarPriorizar(request, filtroRotacion, login);
				if (r == null)
					r = new Rotacion();
				r.setInstjerar_(rotacionDAO.obtenerJerar_1_4(login));
				r = rotacionDAO.asignarPrioridad(r.getInstjerar_(), login);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}

				sig = sig81;
				break;
			/*
			 * case 89: // Refrescar fijar espacio por docente
			 * borrarBeans(request); request.setAttribute("refrescar", "0");
			 * request.setAttribute("mensaje", request.getParameter("mensaje"));
			 * cargarPriorizar(request, filtroRotacion,login);
			 * r.setInstjerar_(rotacionDAO.obtenerJerar_1_4(login)); r =
			 * rotacionDAO.asignarPrioridad(r.getInstjerar_(), login); if (r !=
			 * null) { session.setAttribute("rotacion", r);
			 * session.setAttribute("rotacionEditar", r.clone()); } else {
			 * request.setAttribute("mensaje",
			 * getMensaje(rotacionDAO.getMensaje())); return p_error; } sig =
			 * sig81; break;
			 */
			case 90:
				// generar horario
				borrarBeans(request);
				cargarDocentesFaltantes(session, login);
				cargarCapacidadEspFis(session, login, rotacionDAO);
				sig = sig91;
				break;
			case 100:// PINTAR FORMA HORARIO
				editarHorario(request, login, horario);
				sig = sig100;
				break;
			case 101:// GUARDAR HORARIO
				String docenteescogido2 = "";
				docenteescogido2 = (String) request
						.getParameter("docenteescogido2");
				try {
					if (!docenteescogido2.equals("") && docenteescogido2 != ""
							&& !docenteescogido2.equals("ninguno")
							&& docenteescogido2 != "ninguno") {
						session.setAttribute("docenteescogido", docenteescogido);
					}
				} catch (NullPointerException exp) {
					exp.printStackTrace();
				}
				// valida si se ha habilitado opcion d eun segundo docente para
				// misma area
				if (docenteescogido2.equals("ninguno")) {
					// guarda
					guardarHorario(request, horario);
					editarHorario(request, login, horario);
					sig = sig101;
				} else {
					// guarda
					guardarHorario(request, horario);
					editarHorario(request, login, horario);
					// coge mismo horario y setea cedula nuevo docente
					horario.setDocente(docenteescogido2);
					ponerHorario(request, horario);
					sig = sig102;
				}
				break;
			case 102:// PINTAR MALLA DE HORARIO
				ponerHorario(request, horario);
				sig = sig102;
				break;
			case 110:// PINTAR FORMA BORRARHORARIO
				FormaBorrarHorario(request, login, horario);
				sig = sig110;
				break;
			case 111:// BORRAR HORARIO
				borrarHorario(request, login, horario);
				FormaBorrarHorario(request, login, horario);
				sig = sig110;
				break;
			case 112:// PINTAR MALLA DE BORRARHORARIO
				if (!horario.getGrupo().equals("-1")) {
					ponerBorrarHorario(request, horario);
					sig = sig112;
				} else {
					// ponerHorario(request,horario);
					borrarHorario(request, login, horario);
					FormaBorrarHorario(request, login, horario);
					sig = sig111;
				}
				break;
			case 120:
				// Forma Nuevo inhabilitar HOras
				cargarInhHoras(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig121;
				break;
			case 121:
				// Forma Guardar inhabilitar HOras
				cargarInhHoras(request, filtroRotacion, login);
				break;
			case 122:
				// editar inhabilitar HOras
				aa = request.getParameter("id").replace('|', ':').split(":");
				r = rotacionDAO.asignarInhHora(aa);
				if (r != null) {
					session.setAttribute("rotacion", r);
					session.setAttribute("rotacionEditar", r.clone());
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
					return p_error;
				}
				cargarInhHoras(request, filtroRotacion, login);
				sig = sig121;
				break;
			case 123:
				// eliminar inhabilitar Hora
				aa = request.getParameter("id").replace('|', ':').split(":");
				if (rotacionDAO.eliminarInhHora(aa)) {
					request.setAttribute("mensaje",
							getMensaje("Operacion satisfecha"));
				} else {
					request.setAttribute("mensaje",
							getMensaje(rotacionDAO.getMensaje()));
				}
				request.setAttribute("refrescar", "1");
				request.removeAttribute("guia");
				borrarBeans(request);
				siges.util.Logger.print(login != null ? login.getUsuarioId()
						: "", "Elimininacinn en Tabla 'rot_inhabilitar_Hora'",
						7, 1, this.toString());

				sig = sig121;
				break;
			case 126:
				// nuevo inhabilitar docentes
				cargarInhHoras(request, filtroRotacion, login);
				borrarBeans(request);
				sig = sig121;
				break;
			case 129:
				// Refrescar inhabilitar hORAS
				borrarBeans(request);
				request.setAttribute("refrescar", "0");
				request.setAttribute("mensaje", request.getParameter("mensaje"));
				cargarInhHoras(request, filtroRotacion, login);
				sig = sig121;
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

	public void editarHorario(HttpServletRequest request, Login login,
			Horario horario) throws ServletException, IOException {
		try {
			Collection list = new ArrayList();
			Object[] o = new Object[2];
			o[0] = enterolargo;
			o[1] = login.getInstId();
			list.add(o);
			request.setAttribute(
					"filtroSedeF",
					rotacionDAO.getFiltro(
							rbRot.getString("filtroSedeInstitucion"), list));
			request.setAttribute("filtroJornadaF", rotacionDAO.getFiltro(
					rbRot.getString("filtroSedeJornadaInstitucion"), list));
			request.setAttribute("filtroMetodologiaF", rotacionDAO.getFiltro(
					rbRot.getString("listaMetodologiaInstitucion"), list));
			request.setAttribute("filtroGradoF2", rotacionDAO.getFiltro(
					rbRot.getString("rot2.filtroSedeJornadaGradoInstitucion2"),
					list));
			request.setAttribute("filtroGrupoF", rotacionDAO.getFiltro(rbRot
					.getString("rot2.filtroSedeJornadaGradoGrupoInstitucion"),
					list));
			/*
			 * if (horario.getSede().equals("")) {
			 * horario.setSede(request.getParameter("sede"));
			 * horario.setJornada(request.getParameter("jornada"));
			 * horario.setMetodologia(request.getParameter("metodologia"));
			 * horario.setGrado(request.getParameter("grado"));
			 * horario.setGrupo(request.getParameter("grupo"));
			 * horario.setAsignatura(request.getParameter("asignatura"));
			 * horario.setDocente(request.getParameter("docente"));
			 * if(request.getParameter("vigencia")!=null){
			 * horario.setVigencia(Integer
			 * .parseInt(request.getParameter("vigencia"))); }else{
			 * horario.setVigencia((int)horarioDAO.getVigenciaNumerico()); } }
			 */
			request.setAttribute("filtroDocenteF",
					rotacionDAO.getDocentes(horario));
			request.setAttribute("filtroDocenteF2",
					rotacionDAO.getDocentes(horario));

			request.setAttribute("filtroAsignaturaF",
					rotacionDAO.getAsignaturas(horario));
			request.setAttribute("listaVigencia", rotacionDAO
					.getAllVigencia(Long.parseLong(login.getInstId())));
		} catch (InternalErrorException e) {
			e.printStackTrace();
			System.out.println("excepcion " + e);
		}
	}

	public void borrarHorario(HttpServletRequest request, Login login,
			Horario horario) throws ServletException, IOException {
		if (rotacionDAO.borrarHorarioRotacion(horario)) {
			request.setAttribute("mensaje",
					"Horario borrado satisfactoriamente");
		} else {
			request.setAttribute("mensaje", "Error borrando horario: "
					+ rotacionDAO.getMensaje());
		}
	}

	public void FormaBorrarHorario(HttpServletRequest request, Login login,
			Horario horario) throws ServletException, IOException {
		try {
			Collection list = new ArrayList();
			Object[] o = new Object[2];
			o[0] = enterolargo;
			o[1] = login.getInstId();
			list.add(o);
			request.setAttribute(
					"filtroSedeF",
					rotacionDAO.getFiltro(
							rbRot.getString("filtroSedeInstitucion"), list));
			request.setAttribute("filtroJornadaF", rotacionDAO.getFiltro(
					rbRot.getString("filtroSedeJornadaInstitucion"), list));
			request.setAttribute("filtroMetodologiaF", rotacionDAO.getFiltro(
					rbRot.getString("listaMetodologiaInstitucion"), list));
			request.setAttribute("filtroGradoF2", rotacionDAO.getFiltro(
					rbRot.getString("rot2.filtroSedeJornadaGradoInstitucion2"),
					list));
			request.setAttribute("filtroGrupoF", rotacionDAO.getFiltro(rbRot
					.getString("rot2.filtroSedeJornadaGradoGrupoInstitucion"),
					list));
			/*
			 * o = new Object[2]; o[0] = enterolargo; o[1] =
			 * horarioDAO.getVigencia(); list.add(o);
			 * request.setAttribute("filtroAsignaturaF",
			 * horarioDAO.getFiltro(rbRot
			 * .getString("filtroAsignaturaInstitucion"), list)); if
			 * (horario.getSede().equals("")) {
			 * horario.setSede(request.getParameter("sede"));
			 * horario.setJornada(request.getParameter("jornada"));
			 * horario.setMetodologia(request.getParameter("metodologia"));
			 * horario.setGrado(request.getParameter("grado"));
			 * horario.setGrupo(request.getParameter("grupo"));
			 * horario.setAsignatura(request.getParameter("asignatura"));
			 * horario.setDocente(request.getParameter("docente")); }
			 */
			request.setAttribute("filtroAsignaturaF",
					rotacionDAO.getAsignaturas(horario));
			request.setAttribute("listaVigencia", rotacionDAO
					.getAllVigencia(Long.parseLong(login.getInstId())));
		} catch (InternalErrorException e) {
			System.out.println("excepcion " + e);
		}
	}

	public void guardarHorario(HttpServletRequest request, Horario horario)
			throws ServletException, IOException {
		// HorarioDAO hor = new HorarioDAO(cursor);
		if (rotacionDAO.guardarHorarioRotacion(horario)) {
			request.setAttribute("mensaje",
					"Horario guardado satisfactoriamente");
		} else {
			request.setAttribute("mensaje", "Error guardando horario: "
					+ rotacionDAO.getMensaje());
		}
	}

	public void ponerHorario(HttpServletRequest request, Horario horario)
			throws ServletException, IOException {
		String[] r = rotacionDAO.getHorarioEstructura(horario);
		if (r != null) {
			request.setAttribute("hora", r);
		} else {
			request.setAttribute("mensaje",
					"No se han configurado los parnmetros en Estructura");
			return;
		}

		String nombreDocenteEscogido = rotacionDAO.getDocentesxDoc(horario
				.getDocente());

		// nombre docente escogido activo
		request.setAttribute("nombreDocenteEscogido", nombreDocenteEscogido);
		// nombre docente escogido activo
		request.setAttribute("vigenciaseleccionada",
				String.valueOf(horario.getVigencia()));
		// malla de horarios como tal
		request.setAttribute("hora2", rotacionDAO.getHorario(horario));
		// listadocentes por nombre y documenro
		request.setAttribute("nombrexdocentes",
				rotacionDAO.getNombrexDocentes(horario));
		// lista de espacios
		request.setAttribute("listaEsp", rotacionDAO.getEspacios(horario));
		// inhabilidades de espacio
		request.setAttribute("listaInhabilidadesEsp",
				rotacionDAO.getInhabilidadesEspacio(horario));
		// llista de asignaturas
		request.setAttribute("filtroAsignaturaH",
				rotacionDAO.getAsignaturas(horario));
		// restricciones de horario por grupo-docente-asignatura simultaneo
		request.setAttribute("horDoc",
				rotacionDAO.getRestriccionesHorario(horario));
		// restricciones de docente por inhabilidades
		request.setAttribute("listaInhabilidadesDocente",
				rotacionDAO.getInhabilidadesDocente(horario));
		// restricciones de hora
		request.setAttribute("listaInhabilidadesHora",
				rotacionDAO.getInhabilidadesHora(horario));
	}

	public void ponerBorrarHorario(HttpServletRequest request, Horario horario)
			throws ServletException, IOException {
		String[] r = rotacionDAO.getHorarioEstructura(horario);
		if (r != null) {
			request.setAttribute("hora", r);
		} else {
			request.setAttribute("mensaje",
					"No se han configurado los parametros ");
			return;
		}
		request.setAttribute("hora2", rotacionDAO.getHorarioGrupo(horario));
		horario.setJerGrupo(rotacionDAO.getJeraquia());
		request.setAttribute("listaEsp", rotacionDAO.getEspacios(horario));
		request.setAttribute("filtroDocenteF",
				rotacionDAO.getDocentesGrado(horario));
		request.setAttribute("filtroAsignaturaF",
				rotacionDAO.getAsignaturasGrado(horario));
	}

	public void cargarCapacidadEspFis(HttpSession session, Login login,
			RotacionDAO rot) throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getMetodologiaId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = "" + rot.getVigenciaInst(Long.parseLong(login.getInstId()));
		list.add(o);
		String[][] c1 = util.getFiltroMatriz(
				rbRot.getString("Lista.CapacidadEspAsig"), list);
		// session.setAttribute("capacidadEspFis",util.getFiltro(rbRot.getString("Lista.CapacidadEspAsig"),list));
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getMetodologiaId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = (rot.obtenerEspAsigGrado(login)[0] == null ? "-1" : rot
				.obtenerEspAsigGrado(login)[0]);
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = (rot.obtenerEspAsigGrado(login)[1] == null ? "-1" : rot
				.obtenerEspAsigGrado(login)[1]);
		list.add(o);
		String[][] c2 = util.getFiltroMatriz(
				rbRot.getString("Lista.CapacidadGrados"), list);
		// session.setAttribute("capacidadEspFis",util.getFiltro(rbRot.getString("Lista.CapacidadGrados"),list));
		list = new ArrayList();
		if (c1 != null && c1 != null) {
			for (int i = 0; i < c1.length; i++) {
				for (int j = 0; j < c2.length; j++) {
					if (Integer.parseInt(c1[i][4]) > Integer.parseInt(c2[i][1])) {
						/*
						 * o=new Object[2]; o[0]=enterolargo;
						 * o[1]=login.getInstId(); list.add(o);
						 */
					}
				}
			}
		} else {
		}
		session.setAttribute("capacidadEspFis", list);
	}

	public void cargarDocentesFaltantes(HttpSession session, Login login)
			throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = ""
				+ rotacionDAO
						.getVigenciaInst(Long.parseLong(login.getInstId()));
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getMetodologiaId();
		list.add(o);
		session.setAttribute("docentesFaltantes", util.getFiltro(
				rbRot.getString("Lista.DocentesFaltantes"), list));
	}

	public void cargarDocInh(HttpServletRequest request, FiltroRotacion filtro,
			Login login) throws Exception {
		Collection list = new ArrayList();
		Object o[] = null;
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		request.setAttribute("filtroDocente",
				util.getFiltro(rbRot.getString("Lista.listaDocentes"), list));
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("listaEstructura", rotacionDAO.getAllEstructura(
				Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void cargarInhHoras(HttpServletRequest request,
			FiltroRotacion filtro, Login login) throws Exception {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("listaEstructura", rotacionDAO.getAllEstructura(
				Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void cargarInhEspacios(HttpServletRequest request,
			FiltroRotacion filtro, Login login) throws Exception {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		request.setAttribute("listaEstructura", rotacionDAO.getAllEstructura(
				Long.parseLong(login.getInstId()), sede, jornada, filtro));
	}

	public void cargarPriorizar(HttpServletRequest request,
			FiltroRotacion filtro, Login login) throws Exception {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		List l = rotacionDAO.getAllGrado(Long.parseLong(login.getInstId()),
				filtro.getFilMetodologia());
		request.setAttribute("filtroGradoF2", l);
		// estructuras y estructuras-grado
		l = rotacionDAO.getAllEstructura(Long.parseLong(login.getInstId()),
				sede, jornada, filtro);
		request.setAttribute("listaEstructura", l);
		request.setAttribute("totalEst", String.valueOf(l.size()));
		request.setAttribute("listaEstructuraGrado", rotacionDAO
				.getAllEstructuraGrado(Long.parseLong(login.getInstId()), sede,
						jornada, filtro));
		l = rotacionDAO.getAllAsignatura(Long.parseLong(login.getInstId()),
				filtro.getFilMetodologia(), filtro.getFilAnhoVigencia());
		request.setAttribute("filtroAsignatura", l);
		request.setAttribute("totalAsig", String.valueOf(l.size()));
		request.setAttribute("listaMetodologia", rotacionDAO
				.getAllMetodologia(Long.parseLong(login.getInstId())));
		initFiltro(request, login);
	}

	public void cargarFijarAsignatura(HttpServletRequest request,
			FiltroRotacion filtro, Login login) throws Exception {
		int jornada = -99;
		int sede = -99;
		if (!login.getSedeId().equals("")) {
			sede = Integer.parseInt(login.getSedeId());
		}
		if (!login.getJornadaId().equals("")) {
			jornada = Integer.parseInt(login.getJornadaId());
		}
		Collection list = new ArrayList();
		Object o[] = null;
		// String consulta = "", consulta2 = "";
		// consulta += rbRot.getString("Lista.Estructura");
		// consulta2 += rbRot.getString("Lista.EstructuraGrado");
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilMetodologia());
		list.add(o);
		request.setAttribute("filtroGradoF2", util.getFiltro(
				rbRot.getString("Lista.filtroSedeJornadaGradoInstitucion2"),
				list));
		request.setAttribute("listaEstructura", rotacionDAO.getAllEstructura(
				Long.parseLong(login.getInstId()), sede, jornada, filtro));
		request.setAttribute("listaEstructuraGrado", rotacionDAO
				.getAllEstructuraGrado(Long.parseLong(login.getInstId()), sede,
						jornada, filtro));

		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilMetodologia());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilAnhoVigencia());
		list.add(o);
		request.setAttribute("filtroAsignatura",
				util.getFiltro(rbRot.getString("Lista.Asignatura"), list));
	}

	private void cargarEstructura(HttpServletRequest request, Login login,
			FiltroRotacion filtro) throws Exception {
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
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilMetodologia());
		list.add(o);
		// System.out.println("la metodologia es:"+filtro.getFilMetodologia()+"/"+util.getFiltro(rbRot.getString("Lista.filtroSedeJornadaGradoInstitucion2"),
		// list).size());
		request.setAttribute("filtroGradoF2", util.getFiltro(
				rbRot.getString("Lista.filtroSedeJornadaGradoInstitucion2"),
				list));
	}

	public void cargarFijarEspacioDocente(HttpServletRequest request,
			Login login) throws Exception {
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
		request.setAttribute("filtroDocente",
				util.getFiltro(rbRot.getString("Lista.listaDocentes"), list));
		request.setAttribute("filtroEspFis",
				util.getFiltro(rbRot.getString("Lista.EspFis"), list));
	}

	private void cargarFijarEspacio(HttpServletRequest request,
			FiltroRotacion filtro, Login login) throws Exception {
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
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilMetodologia());
		list.add(o);
		request.setAttribute("filtroGradoF2", util.getFiltro(
				rbRot.getString("Lista.filtroSedeJornadaGradoInstitucion2"),
				list));
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilMetodologia());
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = String.valueOf(filtro.getFilAnhoVigencia());
		list.add(o);
		request.setAttribute("filtroAsignatura",
				util.getFiltro(rbRot.getString("Lista.Asignatura"), list));
		request.setAttribute("filtroGradoAsignatura", util.getFiltro(
				rbRot.getString("Lista.filtroGradoAsignatura2"), list));

		String consulta = rbRot.getString("Lista.EspFis");
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		if (login.getSedeId() != "") {
			o = new Object[2];
			o[0] = enterolargo;
			o[1] = login.getSedeId();
			list.add(o);
			o = new Object[2];
			o[0] = enterolargo;
			o[1] = login.getJornadaId();
			list.add(o);
			consulta += " " + rbRot.getString("Lista.EspFis.1");
		}
		consulta += " " + rbRot.getString("Lista.EspFis.2");
		request.setAttribute("filtroEspFis", util.getFiltro(consulta, list));

	}

	public void cargarDocente(HttpServletRequest request, Login login)
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
		request.setAttribute("filtroDocente",
				util.getFiltro(rbRot.getString("Lista.listaDocentes"), list));
		list = new ArrayList();
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getInstId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = login.getMetodologiaId();
		list.add(o);
		o = new Object[2];
		o[0] = enterolargo;
		o[1] = ""
				+ rotacionDAO
						.getVigenciaInst(Long.parseLong(login.getInstId()));
		list.add(o);
		request.setAttribute("filtroAsignatura",
				util.getFiltro(rbRot.getString("Lista.Asignatura"), list));
		request.setAttribute("filtroGradoAsignatura", util.getFiltro(
				rbRot.getString("Lista.filtroGradoAsignatura2"), list));
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
		session.removeAttribute("rotacion");
		session.removeAttribute("rotacionEditar");
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

	public String getMensaje(String s) {
		return "VERIFIQUE LA SIGUIENTE INFORMACION: \n\n" + "  - " + s + "\n";
	}

	private void initNuevoDocenteGrupo(HttpServletRequest request, Login login,
			DocXGrupoVO doc) {
		long inst = Long.parseLong(login.getInstId());
		request.setAttribute("lSedeVO", rotacionDAO.getAllSede(inst));
		request.setAttribute("lJornadaVO", rotacionDAO.getAllJornada(inst));
		if (doc != null && doc.getFormaEstado().equals("1")) {
			request.setAttribute(
					"ajaxDocente",
					rotacionDAO.getAjaxDocente(doc.getDocGruInstitucion(),
							doc.getDocGruSede(), doc.getDocGruJornada()));
			request.setAttribute("ajaxAsignatura", rotacionDAO
					.getAjaxAsignatura(doc.getDocGruInstitucion(),
							doc.getDocGruSede(), doc.getDocGruJornada(),
							doc.getDocGruMetodologia(), doc.getDocGruDocente(),
							doc.getDocGruVigencia()));
			request.setAttribute("ajaxGrado", rotacionDAO.getAjaxGrado(
					doc.getDocGruInstitucion(), doc.getDocGruSede(),
					doc.getDocGruJornada(), doc.getDocGruMetodologia(),
					doc.getDocGruDocente(), doc.getDocGruAsignatura()));
			request.setAttribute("ajaxGrupo", rotacionDAO.getAjaxGrupo(
					doc.getDocGruInstitucion(), doc.getDocGruSede(),
					doc.getDocGruJornada(), doc.getDocGruMetodologia(),
					doc.getDocGruDocente(), doc.getDocGruAsignatura(),
					doc.getDocGruGrado()));
		}
	}

	public int getCmd(HttpServletRequest request, HttpSession session,
			int defecto) {
		int cmd = defecto;
		if (request.getParameter("cmd") != null
				&& !((String) request.getParameter("cmd")).equals("")) {
			if (GenericValidator.isInt(request.getParameter("cmd"))) {
				return Integer.parseInt((String) request.getParameter("cmd"));
			} else {
				return 0;
			}
		}
		return cmd;
	}

	private void initNuevoEspacioGrado(HttpServletRequest request, Login login,
			EspXGradoVO esp) {
		long inst = Long.parseLong(login.getInstId());
		request.setAttribute("lSedeVO", rotacionDAO.getAllSede(inst));
		request.setAttribute("lJornadaVO", rotacionDAO.getAllJornada(inst));
		if (esp != null && esp.getFormaEstado().equals("1")) {
			request.setAttribute(
					"ajaxEspacio",
					rotacionDAO.getAjaxEspacio(esp.getEspGraInstitucion(),
							esp.getEspGraSede(), esp.getEspGraJornada()));
			request.setAttribute("ajaxGrado", rotacionDAO.getAjaxGrado2(
					esp.getEspGraInstitucion(), esp.getEspGraMetodologia(),
					esp.getEspGraSede(), esp.getEspGraJornada()));
		}
	}

	private void initFiltro(HttpServletRequest request, Login login)
			throws NumberFormatException, Exception {
		int vig = (int) rotacionDAO.getVigenciaInst(Long.parseLong(login
				.getInstId()));
		List l = new ArrayList();
		l.add(String.valueOf(vig - 1));
		l.add(String.valueOf(vig));
		l.add(String.valueOf(vig + 1));
		request.setAttribute("listaVigencia", l);
	}
}