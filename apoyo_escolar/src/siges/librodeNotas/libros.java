package siges.librodeNotas;

import java.io.File;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.librodeNotas.beans.FiltroBeanLibro;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;

;

/**
 * Nombre: libros<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: libros <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class libros extends HttpServlet {

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
	private FiltroBeanLibro filtro;
	private Libro_ boletin;
	private Util util;
	private String[] codigo;
	private String buscarcodigo;
	private final String modulo = "8";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s = "/Reportes.do";
	private String s1 = "/librodeNotas/ControllerLibroFiltroEdit.do";
	private String archivo, archivozip, archivopre;
	private String buscar, buscarjasper;
	private String insertar;
	private String ant;
	private String er;
	private String sig;
	private String home;
	private byte[] bytes;
	private Map parameters;
	private File reportFile;
	private File reportFile1;
	private String path;
	private String vigencia;
	private ReporteLogrosDAO reportesDAO;

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
		session = request.getSession();
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int count;
		String cont = null;
		String met = null;
		String grado = null;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("siges.librodeNotas.bundle.libros");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		cursor = new Cursor();
		reportesDAO = new ReporteLogrosDAO(cursor);
		String existeboletin, boton;
		String nom;
		sig = getServletConfig().getInitParameter("sig");
		ant = getServletConfig().getInitParameter("ant");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		buscar = buscarjasper = insertar = existeboletin = null;
		cursor = new Cursor();
		String alert = "El Libro de Notas se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";

		util = new Util(cursor);
		boton = (request.getParameter("cmd") != null) ? request
				.getParameter("cmd") : new String("");

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(1, er, request, response);
			return null;
		}

		ServletContext context = request.getSession()
				.getServletContext();
		String contextoTotal = context.getRealPath("/");
		// String path=context.getRealPath(rb3.getString("jaspers"));
		// String path1=context.getRealPath(rb3.getString("imgs"));
		// reportFile=new
		// File(path+File.separator+rb3.getString("rep"));//encabezado
		// reportFile1=new File(path+File.separator+rb3.getString("repre"));

		try {
			/*
			 * vigencia=getVigencia();
			 * 
			 * if(vigencia==null){
			 * System.out.println("*W* LA VIGENCIA ES NULA EN LIBROS*W* ");
			 * return null; }
			 * System.out.println("VIGENCIA DEL LIBRO: "+vigencia);
			 */

			System.out
					.println("LIBRONOTAS:VALIDACION PERIODOS CERRADOS ********************************");
			System.out.println("LIBRONOTAS:institucinn : " + login.getInstId());
			System.out.println("LIBRONOTAS:sede : " + filtro.getSede());
			System.out.println("LIBRONOTAS:jornada : " + filtro.getJornada());
			System.out.println("LIBRONOTAS:vigenciaSel : " + filtro.getAno());

			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("periodos_cerrados"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(filtro.getAno().trim()));
			pst.setLong(posicion++, Long.parseLong(filtro.getSede().trim()));
			pst.setLong(posicion++, Long.parseLong(filtro.getJornada().trim()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				// System.out.println("LIBRONOTAS:ENTRA POR EL IF !rs.next(): ES DECIR LA CONSULTA NO ARROJO REGISTROS PUEDE CONTINUAR********************************");

				met = (!filtro.getMetodologia().equals("-9") ? "Met_"
						+ filtro.getMetodologia() : "");
				grado = (!filtro.getGrado().equals("-9") ? "_Gra_"
						+ filtro.getGrado() : "");

				nom = met
						+ grado
						+ "_Fec_"
						+ f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-');
				archivo = "Lib_de_Not_" + nom + ".pdf";
				archivopre = "Lib_de_Not_Pre_" + nom + ".pdf";
				archivozip = "Lib_de_Not_" + nom + ".zip";

				System.out
						.println("nnSe mando insertar el reporte en datos_libro!!!!");
				if (pst != null)
					pst.close();
				pst = con.prepareStatement(rb3.getString("insert_datos_libro"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getInstId()));
				pst.setLong(posicion++, Long.parseLong(!filtro.getSede()
						.equals("-9") ? filtro.getSede() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getJornada()
						.equals("-9") ? filtro.getJornada() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia()
						.equals("-9") ? filtro.getMetodologia() : "-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getGrado()
						.equals("-9") ? filtro.getGrado() : "-9"));
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, 7);
				pst.setString(posicion++, "");
				pst.setString(posicion++, "");
				pst.setString(posicion++, f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-'));
				pst.setString(posicion++, archivozip);
				pst.setString(posicion++, archivo);
				pst.setString(posicion++, archivopre);
				pst.setLong(posicion++, Long.parseLong("-1"));
				pst.setString(posicion++, login.getUsuarioId());
				pst.setLong(posicion++, -9);
				pst.setLong(posicion++, -9);
				// pst.setLong(posicion++,-9);
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
				pst.setLong(posicion++, Long.parseLong(filtro.getAno()));

				String resol = reportesDAO.getResolInst(Long.parseLong(login
						.getInstId()));
				pst.setString(posicion++, resol);

				FiltroBeanReports filtrob = new FiltroBeanReports();
				filtrob.setMetodologia(filtro.getMetodologia());
				filtrob.setGrado(filtro.getGrado());
				filtrob.setFilInst(Long.parseLong(login.getInstId()));
				filtrob.setFilVigencia(Long.parseLong(filtro.getAno()));
				filtrob = reportesDAO.paramsInst(filtrob);

				pst.setLong(posicion++, filtrob.getNivelEval());
				pst.setLong(posicion++, filtrob.getNumPer());
				pst.setString(posicion++, filtrob.getNomPerFinal());

				int tipoPrees = reportesDAO.getTipoEval(filtrob, login);

				System.out.println("LIBROS: INSERT TIPOEVALPREES: " + tipoPrees
						+ "   NIVEL EVAL: " + login.getLogNivelEval());
				pst.setInt(posicion++, tipoPrees);
				// pst.setInt(posicion++,1);
				pst.setInt(posicion++, -9);
				pst.setInt(posicion++, filtro.getTipoProm());
				pst.setLong(
						posicion++,
						Long.parseLong(!filtro.getMostrarFirmaRector().equals(
								"") ? filtro.getMostrarFirmaRector() : "-9"));
				pst.executeUpdate();
				System.out.println("LIBROS: Se insertn en datos_boletin!!!!");

				con.commit();
				pst.close();
				ponerReporte(
						modulo,
						login.getUsuarioId(),
						rb3.getString("boletines.PathLibros") + archivozip + "",
						"zip", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado
																				// -1
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

				cont = cola_libros();

				if (cont != null && !cont.equals(""))
					updatePuestoLibro(cont, archivozip, login.getUsuarioId(),
							"update_puesto_boletin");

			} else {
				rs.close();
				pst.close();
				// System.out
				// .println("LIBRONOTAS:ENTRA POR EL ELSE !rs.next(): ES DECIR LA CONSULTA ARROJO REGISTROS Y NO PUEDE CONTINUAR********************************");
				setMensaje("Para poder generar el Libro de Notas, los periodos deben estar cerrados \nPor favor dirnjase al menn de Gestinn Administrativa en el vnnculo Abrir Grupo/Cerrar Periodo");
				request.setAttribute("mensaje", mensaje);
				return s1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en el Hilo: " + e.toString());
			ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("boletines.PathLibros") + archivozip + "",
					"zip1", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en el Hilo");
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
		reporteVO.setRepTipo(ReporteVO._REP_LIBRO_NOTAS);
		reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
		request.getSession().setAttribute("reporteVO", reporteVO);

		/**
		 * Se redirecciona a boletines para proceder con la genaraci�n		 * 
		 */
		Libro l = new Libro(cursor,contextoTotal,path);	  
		l.procesar_solicitudes();// se dispara el procesamiento de solicitudes
		
		return s;

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
			System.out.println("nnnInsertn en la tabla Reporte!!!");

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
			System.out.println("Se obtuvo vigencia");
		} catch (Exception e) {
			System.out.println("nOcurrin Excepcion al consultar la vigencia!");
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
			System.out
					.println("nActualizn la tabla Reporte con el mensaje recibido!");
			con.commit();
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

	public void updatePuestoLibro(String puesto, String nombreboletinzip,
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
			System.out.println("nnSe ejecutn update_puesto_boletin!!!!");
		} catch (InternalErrorException e) {
			System.out.println("Error Interno: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Exception 2: " + e.toString());
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

	public String cola_libros() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_libro"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			System.out.println("nnSe ejecutn puesto_del_libro!!!!");

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
		System.out.println("RETURN CANT: " + cant);
		return cant;
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
		login = (Login) session.getAttribute("login");
		filtro = (FiltroBeanLibro) session.getAttribute("filtrolibro");
		String sentencia = "";
		return true;
	}

	/**
	 * Recibe la peticion por el metodo get de HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/
	@Override
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = process(request, response);
		System.out.println("se fue para: " + s);
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
	@Override
	public void destroy() {
		try {
			t.stop();
			System.out.println("HILO_LIROS TERMINANDO");
		} catch (Exception ex) {
		}

		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
