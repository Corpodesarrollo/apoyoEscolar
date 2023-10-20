package siges.boletines;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		10/11/2020	JOHN HEREDIA	Se modificó el método process() para realizar un ajuste en los Certificados


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.boletines.beans.Estudiante;
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;

/**
 * Nombre: certificados<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class certificados extends HttpServlet {

	private Cursor cursor;// objeto que maneja las sentencias sql
	private Zip zip;
	private Thread t;
	private Login login;
	private HttpSession session;
	private String mensaje;
	private boolean err;// variable que inidica si hay o no errores en la
						// validacion de los datos del formulario
	private Integer gradocod = new Integer(java.sql.Types.INTEGER);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private Boletin_ boletin;
	private Util util;
	private String[] codigo;
	private String buscarcodigo;
	private final String modulo = "4";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s = "/Reportes.do";
	private String s1 = "/boletines/ControllerBoletinFiltroEdit.do";
	private String archivo, archivozip, archivopre;
	private String buscar, buscarjasper;
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private byte[] bytes;
	private Map parameters;
	private String path;
	private static int parametro;
	private ReporteLogrosDAO reportesDAO;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/

	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int count;
		String cont = null;
		String param = null;
		String sede = null;
		String jornada = null;
		String met = null;
		String grado = null;
		String grupo = null;
		String id = null;
		int estudiantes = 0;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		String existeboletin, boton;
		String nom;
		ant = "/boletines/GenerarBoletinesPorLogro.jsp";
		sig = "/boletines/GenerarBoletinesPorLogro.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		buscar = buscarjasper = insertar = existeboletin = null;
		cursor = new Cursor();
		session = request.getSession();
		param = rb3.getString("parametro");
		String alert = "El Certificado se está generando en este momento. \nPulse en el hipervínculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";

		util = new Util(cursor);
		reportesDAO = new ReporteLogrosDAO(cursor);
		boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return null;
		}
		// System.out.println("Limite de ninos: "+parametro);

		try {
			con = cursor.getConnection();
			
			Estudiante estudianteSeleccionado = estudianteSeleccionado = reportesDAO.getDatosEstudiante(filtro.getFilTipoDoc(), filtro.getId());
			if(login.getPerfil().equalsIgnoreCase("510") && estudianteSeleccionado != null){
				
				login.setInstId(""+estudianteSeleccionado.getEstInstitucion());
				filtro.setSede(""+estudianteSeleccionado.getEstSede());
				filtro.setJornada(""+estudianteSeleccionado.getEstJornada());
				filtro.setGrado(""+estudianteSeleccionado.getEstGrado());
				filtro.setGrupo(""+estudianteSeleccionado.getEstGrupo());
				filtro.setMetodologia(""+estudianteSeleccionado.getEstMetodologia());
				filtro.setFilVigencia(estudianteSeleccionado.getEstVigencia());
				
			}

			pst = con.prepareStatement(rb3
					.getString("existe_certificado_en_cola"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			if (filtro.getId().equals("")) {
				pst.setLong(posicion++, Long.parseLong(!filtro.getSede()
						.equals("-9") ? filtro.getSede() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getJornada()
						.equals("-9") ? filtro.getJornada() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia()
						.equals("-9") ? filtro.getMetodologia() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getGrado()
						.equals("-9") ? filtro.getGrado() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo()
						.equals("-9") ? filtro.getGrupo() : "-9"));
			} else {
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
			}
			// pst.setLong(posicion++,-9);
			pst.setLong(posicion++, filtro.getFilVigencia());
			rs = pst.executeQuery();
			if (!rs.next()) {
//				System.out.println("CERTIFICADOS: NO HAY REGISTROS EN ESTADO CERO O -1");
				rs.close();
				pst.close();

				if (filtro.getId().equals("")) {
					sede = (!filtro.getSede().equals("-9") ? "_Sede_"
							+ filtro.getSede().trim() : "");
					jornada = (!filtro.getJornada().equals("-9") ? "_Jornada_"
							+ filtro.getJornada().trim() : "");
					met = (!filtro.getMetodologia().equals("-9") ? "_Metodologia_"
							+ filtro.getMetodologia()
							: "");
					grado = (!filtro.getGrado().equals("-9") ? "_Grado_"
							+ filtro.getGrado() : "");
					grupo = (!filtro.getGrupo().equals("-9") ? "_Grupo_"
							+ filtro.getGrupo() : "");
					id = "";
				} else {
					id = (!filtro.getId().equals("") ? "_Doc_" + filtro.getId()
							: "");
					grado = "";
					grupo = "";
					sede = "";
					jornada = "";
					met = "";
				}

				nom = sede
						+ jornada
						+ met
						+ grado
						+ grupo
						+ id
						+ "_Fecha_"
						+ f2.toString().replace(' ', '_').replace(':', '-')
								.replace('.', '-');
				archivo = "Certificado_" + nom + ".pdf";
				archivopre = "Certificado_Preescolar_" + nom + ".pdf";
				archivozip = "Certificado_" + nom + ".zip";

//				System.out .println("CERTIFICADOS: Insertar el reporte en datos_boletin");
//				System.out.println("CERTIFICADOS: METODOLOGIA FILTRO:" + filtro.getMetodologia());

				if (filtro.getId() != null && !filtro.getId().trim().equals("")
						&& filtro.getId().trim().length() > 0) {
//					System.out.println("CERTIFICADOS: INSERT BOLETIN POR DOCUMENTO.METOD:"+ filtro.getMetodologia());
					// JOHN HEREDIA: Se cambió del método getParamsEstudiante() al getParamsEstudiante3()
					filtro = reportesDAO.getParamsEstudiante3(filtro);
					
				}

				pst = con.prepareStatement(rb3
						.getString("insert_datos_certificado"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getInstId()));
				pst.setLong(posicion++, Long.parseLong(!filtro.getSede()
						.equals("-9") ? filtro.getSede() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getJornada()
						.equals("-9") ? filtro.getJornada() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia()
						.equals("-9") ? filtro.getMetodologia() : "-9"));
				if (!filtro.getId().equals("")) {
					pst.setLong(posicion++, -9);
					pst.setLong(posicion++, -9);
				} else {
					pst.setLong(posicion++, Long.parseLong(!filtro.getGrado()
							.equals("-9") ? filtro.getGrado() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo()
							.equals("-9") ? filtro.getGrupo() : "-9"));
				}

				pst.setLong(posicion++, 7);
				pst.setString(posicion++, "");
				pst.setString(posicion++,
						!filtro.getId().equals("") ? filtro.getId() : "");

				pst.setString(posicion++, f2.toString().replace(' ', '_')
						.replace(':', '-').replace('.', '-'));
				pst.setString(posicion++, archivozip);
				pst.setString(posicion++, archivo);
				pst.setString(posicion++, archivopre);
				pst.setLong(posicion++, Long.parseLong("-1"));
				pst.setString(posicion++, login.getUsuarioId());
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, 1);
				pst.setLong(posicion++, 1);
				// CAMBIAR POR SECUENCIA
				// long consecRepBol= reportesDAO.getConsecReporte();
				// pst.setLong(posicion++,consecRepBol);
				pst.setString(posicion++, login.getInst());
				pst.setString(posicion++, login.getSede());
				pst.setString(posicion++, login.getJornada());
				pst.setLong(posicion++, filtro.getFilVigencia());

				String resol = reportesDAO.getResolInst(Long.parseLong(login
						.getInstId()));
				pst.setString(posicion++, resol);
				filtro.setFilInst(Long.parseLong(login.getInstId()));
				filtro = reportesDAO.paramsInst(filtro);
				pst.setLong(posicion++, filtro.getNivelEval());
				pst.setLong(posicion++, filtro.getNumPer());
				pst.setString(posicion++, filtro.getNomPerFinal());

				int tipoPrees = reportesDAO.getTipoEval(filtro, login);
//				System.out.println("CERTIFICADOS: INSERT TIPOEVALPREES: " + tipoPrees + "   NIVEL EVAL: " + login.getLogNivelEval());
				pst.setInt(posicion++, tipoPrees);
				// pst.setInt(posicion++,1);
				pst.setInt(posicion++, filtro.getFilTipoDoc());
				pst.setInt(posicion++, filtro.getTipoProm());
				pst.executeUpdate();
//				System.out.println("CERTIFICADOS: Se insertn en datos_boletin!!!!");

				con.commit();
				ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("boletines.PathCertificados")
								+ archivozip + "", "zip", "" + archivozip,
						"-1", "ReporteInsertarEstado");// Estado -1
				System.out
						.println("Se insertn el ZIP en Reporte con estado -1");
				siges.util.Logger.print(
						login.getUsuarioId(),
						"Peticinn de Boletin:_Institucion:_"
								+ login.getInstId() + "_Usuario:_"
								+ login.getUsuarioId() + "_NombreBoletin:_"
								+ archivozip + "", 3, 1, this.toString());
				setMensaje(alert);
				request.setAttribute("mensaje", getMensaje());

				cont = cola_certificados();

				if (cont != null && !cont.equals(""))
					updatePuestoCertificado(cont, archivozip,
							login.getUsuarioId(), "update_puesto_boletin");
			} else {
				rs.close();
				pst.close();
				setMensaje("nYa existe una peticinn de certificado con los mismos parnmetros!");
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("mensaje2",
						"nYa existe una peticinn de certificado con los mismos parnmetros!");
				return s;// lo devuelve a la jsp
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("boletines.PathCertificados") + archivozip
							+ "", "zip", "" + archivozip,
					"ReporteActualizarBoletin", "Ocurrin excepcinn en el Hilo");
		} finally {
			try {
				if (cursor != null)
					cursor.cerrar();
				if (util != null)
					util.cerrar();
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}

		ReporteVO reporteVO = new ReporteVO();
		reporteVO.setRepTipo(ReporteVO._REP_CERTIFICADOS);
		reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
		request.getSession().setAttribute("reporteVO", reporteVO);
		return s;// se va a reportes.do
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void ponerReporte(String modulo, String us, String rec, String tipo,
			String nombre, String estado, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.setString(posicion++, (estado));
			pst.executeUpdate();
			pst.close();
//			System.out.println("nnnInsertn en la tabla Reporte!!!");

		} catch (InternalErrorException e) {
			System.out.println("Error Interno: " + e.toString());
		} catch (Exception e) {
			System.out.println("Error Exception: " + e.toString());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void updatePuestoCertificado(String puesto, String nombreboletinzip,
			String user, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (puesto));
			pst.setString(posicion++, (nombreboletinzip));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			con.commit();
//			System.out.println("nnSe ejecutn update_puesto_certificado!!!!");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
	 * 
	 * @param String
	 *            puesto
	 * @param String
	 *            nombreboletin
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public String cola_certificados() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_certificado"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
//			System.out.println("nnSe ejecutn puesto_del_certificado!!!!");

			if (rs.next())
				cant = rs.getString(1);
			rs.close();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
//		System.out.println("RETURN CANT: " + cant);
		return cant;
	}

	/**
	 * Inserta los valores por defecto para el bean de información básica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		filtro = (FiltroBeanReports) session.getAttribute("filtroc");

		String sentencia = "";
		return true;
	}

	/**
	 * Funcinn: Pone el reporte generado por el reporteador en la tabla de
	 * reportes <BR>
	 * 
	 * @param String
	 *            us
	 * @param String
	 *            rec
	 * @param String
	 *            tipo
	 * @param String
	 *            nombre
	 * @param String
	 *            prepared
	 **/

	public void ponerReporteMensaje(String estado, String modulo, String us,
			String rec, String tipo, String nombre, String prepared,
			String mensaje) {

		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (estado));
			pst.setString(posicion++, (mensaje));
			pst.setString(posicion++, (us));
			pst.setString(posicion++, (rec));
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, (nombre));
			pst.setString(posicion++, (modulo));
			pst.executeUpdate();
			pst.close();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	public String getVigencia() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("vigencia"));
			posicion = 1;
			rs = pst.executeQuery();
			if (rs.next())
				vig = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return vig;
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
		doPost(request, response);
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
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 **/
	public void setMensaje(String s) {
		if (!err) {
			err = true;
			mensaje = "VERIFIQUE LA SIGUIENTE información: \n\n";
		}
		mensaje = "  - " + s + "\n";
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
	public void ir(int a, String s, HttpServletRequest rq,
			HttpServletResponse rs) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(s);
		if (a == 1)
			rd.include(rq, rs);
		else
			rd.forward(rq, rs);
	}

	/*
	 * Cierra cursor
	 */
	public void destroy() {
		try {
			t.stop();
//			System.out.println("HILO_BOLETIN TERMINANDO");
		} catch (Exception ex) {
		}

		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
