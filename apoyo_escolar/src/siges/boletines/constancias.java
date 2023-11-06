package siges.boletines;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

import siges.boletines.beans.Estudiante;
import siges.boletines.beans.FiltroBeanConstancias;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;

/**
 * Nombre: constancias<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class constancias extends HttpServlet {

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
	private ResourceBundle r, rb3, rbBol;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanConstancias filtro;
	private Boletin_ boletin;
	private Util util;
	private String[] codigo;
	private String buscarcodigo;
	private final String modulo = "7";
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
	private File reportFile;
	private File reportFile1;
	private String path;
	private String vigencia;
	private File escudo;
	private String codigodane;
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
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		r = ResourceBundle.getBundle("path");
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.constancias");
		rbBol = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		String nomC = null,nomP = null;
		ant = "/boletines/GenerarConstancias.jsp";
		sig = "/boletines/GenerarConstancias.jsp";
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		buscar = buscarjasper = insertar = null;
		cursor = new Cursor();
		session = request.getSession();
		String alert = "La Constancia se esta generando en este momento. \nPulse en el hipervinculo 'Listado de Reportes' para ver si el reporte ya fue generado. \nO vaya a la opción de menu 'Reportes generados'";
		util = new Util(cursor);
		vigencia = getVigencia();
		if (vigencia == null) {
//			System.out.println("*LA VIGENCIA ES NULA");
			return null;
		}
//		System.out.println("VIGENCIA DE LA CONSTANCIA: " + vigencia);

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesinn para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return null;
		}

		ServletContext context = request.getSession()
				.getServletContext();
		String contextoTotal = context.getRealPath("/");
		String path = Ruta2.get(context.getRealPath("/"), "boletines.reports");
		String path_logo = Ruta.get(context.getRealPath("/"),
				"private.img.logos");
		String path_escudo = Ruta.get(context.getRealPath("/"),"private.escudo");
		reportFile = new File(path + rb3.getString("rep"));
		reportFile1 = new File(path + rb3.getString("reppre"));
		parameters = new HashMap();
		
		int n = 1;

		try {
			con = cursor.getConnection();
			reportesDAO = new ReporteLogrosDAO(cursor);
			Estudiante estudianteSeleccionado = reportesDAO.getDatosEstudiante2(filtro.getId());
			if(login.getPerfil().equalsIgnoreCase("510") && estudianteSeleccionado != null){
				
				login.setInstId(""+estudianteSeleccionado.getEstInstitucion());
				filtro.setSede(""+estudianteSeleccionado.getEstSede());
				filtro.setJornada(""+estudianteSeleccionado.getEstJornada());
				filtro.setGrado(""+estudianteSeleccionado.getEstGrado());
				filtro.setGrupo(""+estudianteSeleccionado.getEstGrupo());
				filtro.setMetodologia(""+estudianteSeleccionado.getEstMetodologia());
			}
			pst = con.prepareStatement(rb3.getString("dane"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			rs = pst.executeQuery();
			if (rs.next())
				codigodane = rs.getString(1);
			// System.out.println("DANE: "+codigodane);
			rs.close();
			pst.close();
//			System.out.println("vigencia: " + filtro.getAno() + " vigencia actual " + filtro.getAno_actual() + " id " + filtro.getId());

			if (filtro.getAno().equals(filtro.getAno_actual())) {
				pst = con
						.prepareStatement("select * from Estudiante where ESTNUMDOC=?");
				posicion = 1;
				pst.clearParameters();
				pst.setString(
						posicion++,
						!filtro.getId().equals("") ? filtro.getId() : filtro
								.getEstudiante());
				rs = pst.executeQuery();
				if (!rs.next()) {
					rs.close();
					pst.close();
					setMensaje("nEl Estudiante no se encuentra matriculado en la vigencia actual!");
					request.setAttribute("mensaje", mensaje);
					return s1;
				}
			} else {
				pst = con
						.prepareStatement("select * from promocion,estudiante where ESTNUMDOC=? and procodestud=estcodigo and provigencia=?");
				posicion = 1;
				pst.clearParameters();
				pst.setString(
						posicion++,
						!filtro.getId().equals("") ? filtro.getId() : filtro
								.getEstudiante());
				pst.setLong(posicion++, Long.parseLong(filtro.getAno()));
				rs = pst.executeQuery();
				if (!rs.next()) {
					rs.close();
					pst.close();
					setMensaje("nEl Estudiante no se encuentra matriculado en la vigencia seleccionada!");
					request.setAttribute("mensaje", mensaje);
					return s;
				}
			}
			rs.close();
			pst.close();
			pst = con.prepareStatement(rb3.getString("existeconstancias"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
			rs = pst.executeQuery();

			if (!rs.next()) {
				rs.close();
				pst.close();
//				System.out.println("******nNO HAY NINGUN CONTANCIA GENERANDOSE!...*********");

				String inst = filtro.getInstitucion().trim().replace(':', '-').replace('�', 'N')
						.replace('.', '-').replace('n', 'a').replace('n', 'A')
						.replace('n', 'e').replace('n', 'E').replace('n', 'i')
						.replace('n', 'I').replace('n', 'o').replace('n', 'O')
						.replace('n', 'u').replace('n', 'U').replace('n', 'n')
						.replace('n', 'N').replace(' ', '_');
				String id = (!filtro.getId().equals("") ? "_Doc_Est_"
						+ filtro.getId().trim()
						: "");
				String est = (!filtro.getEstudiante().equals("-9") ? "_Doc_"
						+ filtro.getEstudiante().trim()
						: "");
				nomC = //inst
						est
						//id
						+ "_Fec_"
						+ f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-');
				nomP = inst
						+ est
						+ id
						+ "_Fec_"
						+ f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-');

				if (filtro.gettiporep().trim().equals("1")) {
					archivo = "Cons_" + nomC + ".pdf";
					archivopre = "Cons_Pre_" + nomC + ".pdf";
					archivozip = "Cons_" + nomC + ".zip";
				} else {
					archivo = "Paz_" + nomP + ".pdf";
					archivopre = "Paz_Pre_" + nomP + ".pdf";
					archivozip = "Paz_" + nomP + ".zip";
				}	
				String archivoEscudo = reportesDAO.getPathEscudo(Long.parseLong(login.getInstId()));
				escudo = new File(archivoEscudo);
				
				if (escudo.exists())
					parameters.put("LogoInstitucion", archivoEscudo);				
				else
					parameters.put("LogoInstitucion", path_escudo + rb3.getString("imagen"));

				parameters.put("usuario", (login.getUsuarioId()));
				parameters.put("LogoCundinamarca", path_escudo + rb3.getString("imagen"));
				parameters.put("efecto", (filtro.getMotivo().trim()));
				// System.out.println("tiporep: " + filtro.gettiporep().trim());
				parameters.put("tipo", (filtro.gettiporep().trim()));
				
				long consecutivoConsultaExterna = this.getConsecutivoConsultasExternas();
				this.insertarConsultasExternas(consecutivoConsultaExterna,"", "", "", "CON");
				String pinConsultaExterna = "CON"+consecutivoConsultaExterna;
				parameters.put("PINCONSULTAEXTERNA", pinConsultaExterna);
				
				parameters.put("INSTITUCION", login.getInst());
				parameters.put("INSTITUCIONCOD", login.getInstId());
				
				parameters.put("SEDECOD", login.getSedeId());
				parameters.put("JORNADACOD", login.getJornadaId());

				ponerReporte(modulo, login.getUsuarioId(),
						rb3.getString("constancias.PathConstancias")
								+ archivozip + "", "zip7", "" + archivozip,
						"-1", "ReporteInsertarEstado");
				
				Constancia constancia = new Constancia(cursor, filtro, contextoTotal,
						archivo, archivopre, archivozip, f2, parameters, path,
						path_logo, reportFile, reportFile1, n++);
				if (!constancia.procesamientoConstancia(f2, parameters,Long.valueOf(login.getSedeId()), Long.valueOf(login.getJornadaId()),Long.valueOf(login.getInstId()), login.getInst())) {// se dispara el procesamiento de solicitudes				
					ponerReporteMensaje("2", modulo, filtro.getUsuarioid(), rb3.getString("constancias.PathConstancias")
									+ archivozip + "", "zip7", "" + archivozip,	"ReporteActualizarBoletinPaila", "Problema en process");
					constancia.updateReporte(archivozip, filtro.getUsuarioid(), "2");
				}

				setMensaje(alert);
				request.setAttribute("mensaje", getMensaje());
			} else {
				rs.close();
				pst.close();
				setMensaje("nUsted ya mandn generar una constancia... Por favor espere que termine, para solicitar una nueva!");
				request.setAttribute("mensaje", mensaje);
				return s1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("constancias.PathConstancias") + archivozip
							+ "", "zip7", "" + archivozip,
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
		reporteVO.setRepTipo(ReporteVO._REP_CONSTANCIAS);
		reporteVO.setRepOrden(ReporteVO.ORDEN_DESC);
		request.getSession().setAttribute("reporteVO", reporteVO);
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
		filtro = (FiltroBeanConstancias) session.getAttribute("filtrobcc");
		filtro.setInstitucionid(login.getInstId());
		filtro.setInstitucion(login.getInst());
		filtro.setUsuarioid(login.getUsuarioId());
		vigencia = "" + getVigenciaInst(Long.parseLong(login.getInstId()));
		filtro.setAno_actual(vigencia);
		String sentencia = "";
		return true;
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
//			System.out.println("HILO_CONSTANCIA TERMINANDO");
		} catch (Exception ex) {
		}

		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

	public String getVigenciaInst(long codInst) {
//		System.out.println("getVigenciaInst  codInst " + codInst);
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String vig = null;

		try {
			con = cursor.getConnection();
			pst = con
					.prepareStatement("SELECT INSVIGENCIA FROM INSTITUCION WHERE INSCODIGO = ?");
			posicion = 1;
			pst.setLong(posicion, codInst);
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
	 * Obtiene el proximo consecutivo de la tabla CONSULTAS_EXTERNAS
	 * 
	 **/
	public long getConsecutivoConsultasExternas() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		ResultSet rs = null;
		long consecutivoConsultaExterna = 0;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rbBol.getString("consecutivo_consulta_externa"));
			pst.clearParameters();
			rs = pst.executeQuery();
			if (rs.next())
				consecutivoConsultaExterna = rs.getLong(1);
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
		return consecutivoConsultaExterna;
	}
	
	/**
	 * Ingresa un registro en la tabla de CONSULTAS_EXTERNAS
	 * 
	 **/
	public void insertarConsultasExternas(long consecutivoConsultaExterna ,String rutaArchivo, String nombreArchivo, String extensionArchivo, String tipo) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			
			pst = con.prepareStatement(rbBol.getString("insertar_consulta_externa"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, tipo);
			pst.setLong(posicion++, consecutivoConsultaExterna);
			pst.setString(posicion++, rutaArchivo);
			pst.setString(posicion++, nombreArchivo);
			pst.setString(posicion++, extensionArchivo);
			pst.executeUpdate();
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
}
