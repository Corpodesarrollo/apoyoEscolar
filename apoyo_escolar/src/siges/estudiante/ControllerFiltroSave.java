package siges.estudiante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.estudiante.beans.Basica;
import siges.estudiante.beans.Familiar;
import siges.estudiante.beans.FiltroBean;
import siges.estudiante.beans.Salud;
import siges.estudiante.dao.EstudianteDAO;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;
import util.BitacoraCOM;
import util.LogEstudianteDto;

/**
 * Nombre: ControllerFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Estudiante <BR>
 * Fecha de modificacinn: 20/07/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerFiltroSave extends HttpServlet {
	private String mensaje;// mensaje en caso de error
	private String buscar;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Cursor cursor;// objeto que maneja las sentencias sql
	private EstudianteDAO estudianteDAO;//
	private Basica basica;
	private Login login;
	private FiltroBean filtro;
	private ResourceBundle rb;
	// VARIABLE PARA GENERAR REPORTE
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String contextoTotal;
	private String context;
	private String er;
	private String archivo, archivozip;
	private File escudo;
	private byte[] bytes;
	private Map parameters;
	private File reportFile;
	private String path;
	private String path1;
	private String path2;
	private final String modulo = "57";
	private java.sql.Timestamp f2;

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
		rb = ResourceBundle.getBundle("siges.estudiante.bundle.estudiante");
		String sig, sig2, sig3;
		String ant;
		String er;
		String home;
		String sentencia;
		String boton;
		String id;
		sig = "/estudiante/FiltroResultado.jsp";
		sig2 = "/estudiante/ControllerNuevoEdit.do";
		sig3 = "/estudiante/ControllerFiltroSave.do";
		ant = "/index.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		cursor = new Cursor();
		err = false;
		mensaje = null;
		ServletContext context = (ServletContext) request.getSession()
				.getServletContext();
		contextoTotal = context.getRealPath("/");
		path = Ruta2
				.get(context.getRealPath("/"), rb.getString("ruta_jaspers"));
		path1 = Ruta.get(context.getRealPath("/"),
				rb.getString("ruta_img_escudo"));
		path2 = Ruta.get(context.getRealPath("/"),
				rb.getString("ruta_img_escudo"));
		reportFile = new File(path
				+ rb.getString("jasper_estudiantes_x_grupos"));
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		try {
			estudianteDAO = new EstudianteDAO(cursor);
			boton = (request.getParameter("cmd") != null) ? request
					.getParameter("cmd") : new String("");
			if (boton.equals("Nuevo")) {
				borrarBeans(request);
				sig = sig2;
			}
			if (boton.equals("Editar")) {
				sig = editarBasica(request, sig2);
				if (request.getParameter("modcon") != null
						&& !request.getParameter("modcon").equals(""))
					request.getSession().setAttribute("ModoConsulta", "1");
				else
					request.getSession().removeAttribute("ModoConsulta");
			}
			if (boton.equals("Eliminar")) {
				id = (request.getParameter("id") != null) ? request
						.getParameter("id") : new String("");
				if (!estudianteDAO.eliminarEstudiante("1", id)) {
					setMensaje(estudianteDAO.getMensaje());
					request.setAttribute("mensaje", mensaje);
					return er;
				}
				try
				{
					Basica basica = estudianteDAO.asignarBasica(id, login);
					LogEstudianteDto log = new LogEstudianteDto();
					log.setNumeroIdentificacion(basica.getEstnumdoc());
					log.setEstado("Activo");
					log.setNombreInstitucion(login.getInst());
					log.setSede(basica.getEstsede());
					log.setJornada(basica.getEstjor());
					log.setGrado(basica.getEstgra());
					log.setGrupo(basica.getEstgrupo());
					BitacoraCOM.insertarBitacora(
							Long.parseLong(login.getInstId()), 
							Integer.parseInt(login.getJornadaId()),
							2 ,
							login.getPerfil(), 
							Integer.parseInt(login.getSede()), 
							0, 
							3, 
							login.getUsuarioId(), 
							new Gson().toJson(log)
							);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Error " + this + ":" + e.toString());
				}
				boton = "";
			}
			// OPCION DESCARGAR REPORTE WGOMEZ (24/04/2008)
			if (boton.equals("Descargar")) {
				// System.out.println("DESGARGAR REPORTE EST X GRUPO");
				// sig="/Reportes.do";
				sig = estudiantesXGrupo(request, contextoTotal);
			}

			if (boton.equals("")) {
				if (!asignarBeans(request)) {
					setMensaje("Error capturando datos de sesinn para el usuario");
					request.setAttribute("mensaje", mensaje);
					return er;
				}
				asignarListaNuevo(request);
				request.getSession().setAttribute("filtroEst", filtro);
//				System.out.println("tipo modcon " + request.getParameter("modcon"));
				if (request.getParameter("modcon") != null
						&& !request.getParameter("modcon").equals(""))
					request.getSession().setAttribute("ModoConsulta", "1");
				else
					request.getSession().removeAttribute("ModoConsulta");
			}
			return sig;
		} catch (Exception e) {
			System.out.println("Error " + this + ":" + e.toString());
			throw new ServletException(e);
		} finally {
			if (cursor != null)
				cursor.cerrar();
			if (estudianteDAO != null)
				estudianteDAO.cerrar();
		}
	}

	public String editarBasica(HttpServletRequest request, String sig2)
			throws ServletException, IOException {
		borrarBeans(request);
		request.setAttribute("tipo", "1");
		request.getSession().setAttribute("editar", "1");
		String id = (request.getParameter("id") != null) ? request
				.getParameter("id") : new String("");
		Basica basica;
		int tipo;
		Familiar familiar = null;
		Salud salud = null;
		basica = estudianteDAO.asignarBasica(id, login);
		if (basica != null) {
			// edicion de un nino que no es de esa institucion
			/*
			 * if(basica.getEstado().equals("2") &&
			 * basica.getEstsede().equals("")){ setMensaje(
			 * "El estudiante no pertenece a este colegio y no es posible editar su información"
			 * ); request.setAttribute("mensaje",mensaje); }
			 */
			if (!basica.getEstcodfamilia().equals(""))
				familiar = estudianteDAO.asignarFamiliar(basica
						.getEstcodfamilia());
			if (familiar == null) {
				familiar = new Familiar();
				// familiar.setEstado("1");
			}
			salud = estudianteDAO.asignarSalud("1", id);
			if (salud == null) {
				salud = new Salud();
			}
			if (familiar != null) {
				request.getSession().setAttribute("nuevoFamiliar", familiar);
				request.getSession().setAttribute("nuevoFamiliar2",
						familiar.clone());
			}
			if (salud != null) {
				request.getSession().setAttribute("nuevoSalud", salud);
				request.getSession().setAttribute("nuevoSalud2", salud.clone());
			}
			request.getSession().setAttribute("nuevoBasica", basica);
			request.getSession().setAttribute("nuevoBasica2", basica.clone());
			request.getSession().setAttribute("subframe",
					"/estudiante/FiltroResultado.jsp");
//			System.out.println("tipo mod con " + request.getParameter("modcons").equals("1"));
			if (request.getParameter("modcons") == null
					|| request.getParameter("modcons").equals(""))
				tipo = 0;
			else
				tipo = Integer.parseInt((String) request
						.getParameter("modcons"));
			if (tipo == 1) {
//				System.out.println("tipo mod con " + request.getParameter("modcons"));
				return sig2 + "?tipo=1";
			}
			System.out.println("termino ");
			return sig2 + "?tipo=1";
		} else {
			setMensaje(estudianteDAO.getMensaje());
			request.setAttribute("mensaje", mensaje);
			return sig2;
		}
	}

	/**
	 * Elimina del contexto de la sesion los beans de informacion del usuario
	 * 
	 * @param HttpServletRequest
	 *            request
	 */
	public void borrarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		request.getSession().removeAttribute("nuevoBasica");
		request.getSession().removeAttribute("nuevoFamiliar");
		request.getSession().removeAttribute("nuevoSalud");
		request.getSession().removeAttribute("nuevoConvivencia");
		request.getSession().removeAttribute("nuevoBasica2");
		request.getSession().removeAttribute("nuevoFamiliar2");
		request.getSession().removeAttribute("nuevoSalud2");
		request.getSession().removeAttribute("nuevoConvivencia2");
		request.getSession().removeAttribute("encabezado");
		request.getSession().removeAttribute("subframe");
	}

	/**
	 * Trae de sesion los datos de la credencial del usuario y el formulario de
	 * filtro
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) request.getSession().getAttribute("login");
		filtro = (FiltroBean) request.getSession().getAttribute("filtroEst");
		return true;
	}

	/**
	 * Hace la busqueda de los datos del usuario en la base de datos y sube a
	 * sesion el resultado
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	/*
	 * public void asignarLista(HttpServletRequest request)throws
	 * ServletException, IOException{ String s=null; try{
	 * 
	 * if(filtro.getOrden() != null &&
	 * GenericValidator.isInt(filtro.getOrden())){
	 * 
	 * System.out.println("filtro.getSede() ++++++++++ " + filtro.getSede());
	 * s=" "+rb.getString("estudiante.consulta1"); Collection lista=new
	 * ArrayList(); Object[] o=new Object[2]; if((filtro.getSede()!=null &&
	 * !filtro.getSede().equals("-9")) || (filtro.getJornada()!=null &&
	 * !filtro.getJornada().equals("-9")) || (filtro.getGrado()!=null &&
	 * !filtro.getGrado().equals("-9")) || (filtro.getGrupo()!=null &&
	 * !filtro.getGrupo().equals("-9")) || (filtro.getMetodologia()!=null &&
	 * !filtro.getMetodologia().equals("-9"))){ o[0]=Properties.ENTEROLARGO;
	 * o[1]=login.getInstId(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta111");
	 * s+=" "+rb.getString("estudiante.consulta2"); }else{
	 * o[0]=Properties.ENTEROLARGO; o[1]=login.getInstId(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta111.1");
	 * s+=" "+rb.getString("estudiante.consulta111.2"); }
	 * 
	 * if( filtro.getInstitucion()==null ){ filtro.setInstitucion(Long.valueOf(
	 * login.getInstId())); }
	 * 
	 * 
	 * System.out.println("filtro.getSede() " + filtro.getSede());
	 * if(filtro.getSede()!=null && !filtro.getSede().equals("-9")){ o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getSede();
	 * lista.add(o); s+=" "+rb.getString("estudiante.consulta3"); }
	 * System.out.println("filtro.getJornada() " + filtro.getJornada());
	 * 
	 * if(filtro.getJornada()!=null && !filtro.getJornada().equals("-9")){ o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getJornada();
	 * lista.add(o); s+=" "+rb.getString("estudiante.consulta4"); }
	 * 
	 * System.out.println("filtro.getGrado() " + filtro.getGrado());
	 * if(filtro.getGrado()!=null && !filtro.getGrado().equals("-9")){ o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getGrado();
	 * lista.add(o); s+=" "+rb.getString("estudiante.consulta5"); }
	 * 
	 * System.out.println("filtro.getGrupo() " + filtro.getGrupo());
	 * if(filtro.getGrupo()!=null && !filtro.getGrupo().equals("-9")){ o=new
	 * Object[2]; o[0]=Properties.ENTEROLARGO; o[1]=filtro.getGrupo();
	 * lista.add(o); s+=" "+rb.getString("estudiante.consulta6"); }
	 * 
	 * System.out.println("filtro.getMetodologia() " + filtro.getMetodologia());
	 * if(filtro.getMetodologia()!=null &&
	 * !filtro.getMetodologia().equals("-9")){ o=new Object[2];
	 * o[0]=Properties.ENTEROLARGO; o[1]=filtro.getMetodologia(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta61"); }
	 * s+=rb.getString("estudiante.consulta7");
	 * System.out.println("pedazo consulta ---------- " +
	 * rb.getString("estudiante.consulta7") +"----"); }
	 * if(!filtro.getId().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=filtro.getId(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta8"); }
	 * if(!filtro.getNombre1().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=filtro.getNombre1(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta9"); }
	 * if(!filtro.getNombre2().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=filtro.getNombre2(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta10"); }
	 * if(!filtro.getApellido1().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=filtro.getApellido1(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta11"); }
	 * if(!filtro.getApellido2().trim().equals("")){ o=new Object[2];
	 * o[0]=Properties.CADENA; o[1]=filtro.getApellido2(); lista.add(o);
	 * s+=" "+rb.getString("estudiante.consulta12"); }
	 * switch(Integer.parseInt(filtro.getOrden())){ case -1:
	 * s+=" "+rb.getString("estudiante.order1"); break; case 0:
	 * s+=" "+rb.getString("estudiante.order2"); break; case 1:
	 * s+=" "+rb.getString("estudiante.order3"); break; case 2:
	 * s+=" "+rb.getString("estudiante.order4"); break; default :
	 * s+=" "+rb.getString("estudiante.order1"); break; } System.out.println(s);
	 * request.getSession().setAttribute("filtroEstudiantes",estudianteDAO.
	 * getFiltroEstudiante(s,lista)); } }catch(Throwable th){
	 * System.out.println("error "+th); th.printStackTrace(); throw new
	 * ServletException(th); } }
	 */

	public void asignarListaNuevo(HttpServletRequest request)
			throws ServletException, IOException {

		try {
			Long inst = new Long(Long.parseLong(login.getInstId()));
			filtro.setInstitucion(inst);
			request.getSession().setAttribute("filtroEstudiantes",estudianteDAO.getBuscarEstudiante(filtro));
		} catch (Throwable th) {
			System.out.println("error " + th);
			th.printStackTrace();
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

	// FUNCION PARA DESCARGAR REPORTE ETUDIANTES POR GRUPO
	public String estudiantesXGrupo(HttpServletRequest request,
			String contextoTotal) throws ServletException, IOException {
		filtro = (FiltroBean) request.getSession().getAttribute("filtro");
		String sede = null, jornada = null, nombreReporte = null, cod_grupo = null, grado = null;
		parameters = new HashMap();
		String archivoSalida = null;
		String archivosalida = null;
		int zise;
		Collection list = new ArrayList();
		Zip zip = new Zip();
		ReporteVO reporteVO = new ReporteVO();
		try {

			reporteVO.setRepTipo(ReporteVO._REP_ENCUESTA_EST_X_GRUP);
			reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);

			// System.out.println("*********////*********nENTRn A GENERAR  estudiantesXGrupo REPORTE!**********//**********");
			if (!asignarBeans(request)) {
				setMensaje("Error capturando bean del reporte estudiantes por grupo");
				request.setAttribute("mensaje", mensaje);
				ir(1, er, request, response);
				return "/Reportes.do";
			}
			// System.out.println("*********////*********nENTRn A GENERAR  estudiantesXGrupo REPORTE!**********//**********");

			filtro.setInstitucionDane(estudianteDAO
					.getDaneInstitucion(new Long(login.getInstId())));
			filtro.setInstitucion(new Long(login.getInstId()));
			/* PARAMETROS PARA el JASPER DE LISTADO DE EST POR GRUPO */
			// System.out.println("new Long(login.getInstId()) "
			// + new Long(login.getInstId()));
			parameters.put("INSTITUCION", new Long(login.getInstId()));
			parameters.put("SEDE", new Long(filtro.getSede()));
			parameters.put("JORNADA", new Long(filtro.getJornada()));
			parameters.put("GRADO", new Long(filtro.getGrado()));
			if (!filtro.getGrupo().equals("") && filtro.getGrupo().length() > 0)
				parameters.put("GRUPO", new Long(filtro.getGrupo()));
			else
				parameters.put("GRUPO", new Long(-99));

			// primer nombre
			if (filtro.getNombre1() != null && filtro.getNombre1().length() > 0)
				parameters.put("EST_NOMBRE1", new String(filtro.getNombre1()));
			else
				parameters.put("EST_NOMBRE1", new String("-99"));

			// segundo nombre
			if (filtro.getNombre1() != null && filtro.getNombre1().length() > 0)
				parameters.put("EST_NOMBRE2", new String(filtro.getNombre1()));
			else
				parameters.put("EST_NOMBRE2", new String("-99"));

			// segundo apellido1
			if (filtro.getNombre1() != null && filtro.getNombre1().length() > 0)
				parameters
						.put("EST_APELLIDO1", new String(filtro.getNombre1()));
			else
				parameters.put("EST_APELLIDO1", new String("-99"));

			// segundo apellido2
			if (filtro.getNombre1() != null && filtro.getNombre1().length() > 0)
				parameters
						.put("EST_APELLIDO2", new String(filtro.getNombre1()));
			else
				parameters.put("EST_APELLIDO2", new String("-99"));

			parameters.put("USUARIO", login.getUsuario());
			escudo = new File(path2 + "e" + filtro.getInstitucionDane()
					+ ".gif");

			if (escudo.exists())
				parameters.put("PATH_ICONO_INSTITUCION",
						path2 + "e" + filtro.getInstitucionDane() + ".gif");
			else
				parameters.put("PATH_ICONO_INSTITUCION", null);

			parameters.put("PATH_ICONO_SECRETARIA",
					path1 + rb.getString("imagen"));
			parameters.put("SUBREPORT_DIR", path);

			escudo = new File(path2 + "e" + filtro.getInstitucionDane()
					+ ".gif");
			// if (escudo.exists())
			// System.out.println("PATH_ICONO_INSTITUCION: " + path2 + "e"
			// + filtro.getInstitucionDane() + ".gif");
			// else
			// System.out.println("PATH_ICONO_INSTITUCION: " + null);

			Login usuVO = (Login) request.getSession().getAttribute("login");
			java.sql.Timestamp f2;
			f2 = new java.sql.Timestamp(new java.util.Date().getTime());

			filtro.setUsuario(usuVO.getUsuarioId());
			filtro.setNomInst(usuVO.getInst());
			filtro.setFecha(f2.toString());

			parameters.put("FECHA", new String(filtro.getFecha()));
			parameters.put("USUARIO_ID", new String(filtro.getUsuario()));
			parameters.put("ORDEN", new Long(filtro.getOrden()));

			/*
			 * System.out.println("fecha REPORTE ESTUDIANTE " +
			 * parameters.get("FECHA") ); System.out.println(" REPORTE USUARIO "
			 * + parameters.get("USUARIO_ID") ); System.out.println("+++ ORDEN "
			 * + parameters.get("ORDEN") );
			 * 
			 * /* // System.out.println("PATH_ICONO_SECRETARIA: //
			 * "+path1+rb3.getString("imagen")); //
			 * System.out.println("SUBREPORT_DIR: "+path);
			 * 
			 * System.out.println("********
			 */// /*********nENTRn A GENERAR estudiantesXGrupo
				// REPORTE!**********//**********");
			if (!estudianteDAO.isWorking(login.getUsuarioId(), modulo)) {
				sede = "Sed_"
						+ (!filtro.getSede().equals("-99") ? filtro.getSede()
								: "");
				// System.out.println("*SEDE*"+sede);
				jornada = "_Jorn_"
						+ (!filtro.getJornada().equals("-9") ? filtro
								.getJornada() : "");
				grado = "_grad_"
						+ (!filtro.getGrado().equals("-9") ? filtro.getGrado()
								: "");
				if (!filtro.getGrupo().equals("")
						&& filtro.getGrupo().length() > 0)
					cod_grupo = (!filtro.getGrupo().equals("-99") ? "_Grup_"
							: ""
									+ (!filtro.getGrupo().equals("-9") ? filtro
											.getGrupo() : ""));
				nombreReporte = sede
						+ jornada
						+ grado
						+ cod_grupo
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Estudiantes_x_Grupo_" + nombreReporte + ".xls";
				archivozip = "Estudiantes_x_Grupo_" + nombreReporte + ".zip";
				estudianteDAO.ponerReporte(modulo, login.getUsuarioId(),rb.getString("reporte.PathReporte") + archivozip + "",	"zip", "" + archivozip, "0", "ReporteInsertarEstado");// Estado
																				// -1
				if (!estudianteDAO.getBuscarEstudiante(filtro).isEmpty()) {

					/**
					 * Llamado a procedimiento
					 * */
					// System.out.println("antes de llamar proce " + new
					// java.sql.Timestamp(new java.util.Date().getTime()));
					estudianteDAO.callHojaVida(filtro);
					// System.out.println("despues de llamar proce " + new
					// java.sql.Timestamp(new java.util.Date().getTime()));

					//
					// System.out.println("Hay datos para ejecutar el jasper");
					archivoSalida = estudianteDAO.getArchivo(
							login.getUsuarioId(), archivozip, reportFile,
							parameters, contextoTotal, archivo);
					estudianteDAO.updateReporteEstado(modulo,
							login.getUsuarioId(),
							rb.getString("reporte.PathReporte") + archivozip
									+ "", "zip", "" + archivozip,
							"ReporteActualizarListo");
					// estudianteDAO.ponerArchivo(bytes, archivo,
					// contextoTotal);
					archivosalida = Ruta.get(contextoTotal,
							rb.getString("reportes.PathReporte"));
					// System.out.println("archivosalida: "+archivosalida);
					list.add(archivoSalida);// pdf
					zise = 100000;
					if (zip.ponerZip(archivosalida, archivozip, zise, list)) {
						estudianteDAO.updateReporteFecha(
								"update_reporte_general", modulo, archivozip,
								login.getUsuarioId(), "1");
						// borrarHojaVida
						estudianteDAO.borrarHojaVida(filtro);

						request.getSession().setAttribute("reporteVO",
								reporteVO);
						return "/Reportes.do";
					} else {
						estudianteDAO.ponerReporteMensaje("4", modulo,
								login.getUsuarioId(),
								rb.getString("reporte.PathReporte")
										+ archivozip + "", "zip", ""
										+ archivozip,
								"ReporteActualizarHorarioFallo",
								"Ocurrin problema al hacer zip");
						estudianteDAO.updateReporteFecha(
								"update_reporte_general", modulo, archivozip,
								login.getUsuarioId(), "2");
						setMensaje("Error al crear zip");
						request.setAttribute("mensaje", mensaje);
						estudianteDAO.borrarHojaVida(filtro);
						request.getSession().setAttribute("reporteVO",
								reporteVO);
						return "/Reportes.do";
					}
				} else {
					// System.out.println("El Reporte esta vacio");
					// Insertarlo en la tabla REPORTE -- ESTADO 2
					estudianteDAO
							.ponerReporteMensaje("2", modulo,
									login.getUsuarioId(),
									rb.getString("reporte.PathReporte")
											+ archivozip + "", "zip", ""
											+ archivozip,
									"ReporteActualizarHorarioFallo",
									"Ocurrin problema en Listado de estudiantes por Grupo.");
					estudianteDAO.updateReporteFecha("update_reporte_general",
							modulo, archivozip, login.getUsuarioId(), "2");
					setMensaje("No hay datos suficientes para generar el reporte Listado de estudiantes por Grupo.");
					request.setAttribute("mensaje", mensaje);
					request.getSession().setAttribute("reporteVO", reporteVO);
					return "/Reportes.do";
				}

			} else {
				// System.out.println("YA MANDO GENERAR");
				setMensaje("Usted ya mandn generar un reporte de Listado de estudiantes por Grupo.\nPor favor espere que termine, para solicitar uno nuevo");
				request.setAttribute("mensaje", mensaje);
				request.getSession().setAttribute("reporteVO", reporteVO);
				return "/Reportes.do";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e.printStackTrace() " + e.getMessage());
			estudianteDAO.ponerReporteMensaje("2", modulo,
					login.getUsuarioId(), rb.getString("reporte.PathReporte")
							+ archivozip + "", "zip", "" + archivozip,
					"ReporteActualizarBoletin",
					"Ocurrin excepcinn en Listado de estudiantes por Grupo."
							+ e.getMessage());
			// estudianteDAO.updateReporteEstado(modulo, login.getUsuarioId(),
			// rb.getString("reporte.PathReporte")+ archivozip + "", "zip", "" +
			// archivozip,"ReporteActualizarListo");
			try {
				estudianteDAO.borrarHojaVida(filtro);
			} catch (Exception ee) {
				e.printStackTrace();
				estudianteDAO.ponerReporteMensaje("8", modulo,
						login.getUsuarioId(),
						rb.getString("reporte.PathReporte") + archivozip + "",
						"zip", "" + archivozip, "ReporteActualizarBoletin",
						"Ocurrin excepcinn en Listado de estudiantes por Grupo."
								+ e.getMessage());
				System.out.println(ee.getMessage());
			}
			request.getSession().setAttribute("reporteVO", reporteVO);
			return "/Reportes.do";
		}
	}

}