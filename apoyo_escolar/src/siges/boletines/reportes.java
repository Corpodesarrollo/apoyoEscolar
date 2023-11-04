package siges.boletines;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		02/07/2020	JORGE CAMACHO	Se corrige la ortografía de los mensajes y la identación para hacer el debug más claro

import java.util.List;
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
import siges.boletines.beans.FiltroBeanReports;
import siges.boletines.dao.ReporteLogrosDAO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.boletinPublico.vo.ParamsVO;

//import com.jms.weblogic.EnviarJMSReporte;

import siges.io.Zip;
import siges.login.beans.Login;


/**
 * Nombre: reportes<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class reportes extends HttpServlet {

	
	private Zip zip;
	private Thread t;
	private Login login;
	private boolean err;			// Variable que indica si hay o no errores en la validacion de los datos del formulario
	private Cursor cursor;			// Objeto que maneja las sentencias sql
	private String mensaje;
	private HttpSession session;
	
	private Integer nulo = new Integer(java.sql.Types.NULL);
	private Integer doble = new Integer(java.sql.Types.DOUBLE);
	private Integer caracter = new Integer(java.sql.Types.CHAR);
	private Integer entero = new Integer(java.sql.Types.INTEGER);
	private Integer cadena = new Integer(java.sql.Types.VARCHAR);
	private Integer fecha = new Integer(java.sql.Types.TIMESTAMP);
	private Integer gradocod = new Integer(java.sql.Types.INTEGER);
	private Integer enterolargo = new Integer(java.sql.Types.BIGINT);
	
	private Util util;
	private String er;
	private String sig;
	private String ant;
	private Object[] o;
	private String home;
	private String insertar;
	private Collection list;
	private String[] codigo;
	private Boletin_ boletin;
	private File reporte1_1;
	private File reporte1_2;
	private String buscarcodigo;
	private static int parametro;
	private ServletConfig config;
	private ResourceBundle r,rb, rb3;
	private java.sql.Timestamp f2;
	private FiltroBeanReports filtro;
	private String s = "/Reportes.do";
	private final String modulo = "3";
	private HttpServletRequest request;
	private String buscar, buscarjasper;
	private ReporteLogrosDAO reportesDAO;
	private HttpServletResponse response;
	private String archivo, archivozip, archivopre;
	private String s1 = "/boletines/ControllerBoletinFiltroEdit.do";
	
	ServletContext context;
	String contextoTotal;
	String path_jasper;
	String path_logo;
	String path_escudo;
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		rb = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		contextoTotal = context.getRealPath("/");
		path_jasper = Ruta2.get(context.getRealPath("/"), rb.getString("boletines_ruta_jaspers"));
		path_logo = Ruta.get(context.getRealPath("/"), rb.getString("boletines_logos"));
		path_escudo = Ruta.get(context.getRealPath("/"), rb.getString("boletines_imgs_inst"));
		
		super.init(config);
		cursor = new Cursor();
		if (!updateColaBolEstadoCero()) {
			return;
		}
		Boletin boletin = new Boletin(cursor, contextoTotal, path_jasper, path_logo, path_escudo);
		boletin.procesar_solicitudes();// se dispara el procesamiento de solicitudes
		return;
	}
	
	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/

	@SuppressWarnings({ "rawtypes", "unused" })
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		Collection list = new ArrayList();
		
		int count;
		int posicion = 1;
		int estudiantes = 0;
		
		String nom;
		String id = null;
		String met = null;
		String sede = null;
		String cont = null;
		String param = null;
		String grado = null;
		String grupo = null;
		String jornada = null;
		String existeboletin, boton;
		
		r = ResourceBundle.getBundle("path");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		rb3 = ResourceBundle.getBundle("siges.boletines.bundle.boletines");
		
		er = "/error.jsp";
		home = "/bienvenida.jsp";
		ant = "/boletines/GenerarBoletinesPorLogro.jsp";
		sig = "/boletines/GenerarBoletinesPorLogro.jsp";
		
		buscar = buscarjasper = insertar = existeboletin = null;
		
		cursor = new Cursor();
		session = request.getSession();
		param = rb3.getString("parametro");
		String alert = "El boletín se está generando en este momento.\nPulse en el hipervínculo 'Listado de Reportes' para ver si el reporte ya fue generado.\nO vaya a la opción de menu 'Reportes generados'";

		util = new Util(cursor);
		reportesDAO = new ReporteLogrosDAO(cursor);
		boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("");

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesión para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return null;
		}
		

		try {
			
			con = cursor.getConnection();
			
			Estudiante estudianteSeleccionado = reportesDAO.getDatosEstudiante(filtro.getFilTipoDoc(), filtro.getId());
			
			if(login.getPerfil().equalsIgnoreCase("510") && estudianteSeleccionado != null) {
				
				filtro.setSede(""+estudianteSeleccionado.getEstSede());
				filtro.setJornada(""+estudianteSeleccionado.getEstJornada());
				filtro.setMetodologia("-9");
				filtro.setGrado("-9");
				filtro.setGrupo("-9");
				filtro.setFilVigencia(estudianteSeleccionado.getEstVigencia());
			}
			
			
			// Validación para saber si el periodo esta cerrado
			if (!isPerCerrado(con, filtro, login)) {
				request.setAttribute(ParamsVO.SMS, "No se puede generar el boletín porque el período aún no está cerrado.");
				return "/boletines/ControllerBoletinFiltroEdit.do";
			}
			
			
			posicion = 1;
			pst = con.prepareStatement(rb3.getString("existe_mismo_boletin_en_cola"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(login.getInstId()));
			pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals("-9") ? filtro.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals("-9") ? filtro.getJornada() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia().equals("-9") ? filtro.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals("-9") ? filtro.getGrado() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals("-9") ? filtro.getGrupo() : "-9"));
			pst.setLong(posicion++, Long.parseLong(filtro.getPeriodo().trim()));
			pst.setLong(posicion++, filtro.getFilVigencia());
			rs = pst.executeQuery();
			
			if (!rs.next()) {

				rs.close();
				pst.close();

				sede = (!filtro.getSede().equals("-9") ? "_Sed_" + filtro.getSede().trim() : "");
				jornada = (!filtro.getJornada().equals("-9") ? "_Jor_" + filtro.getJornada().trim() : "");
				met = (!filtro.getMetodologia().equals("-9") ? "_Met_" + filtro.getMetodologia() : "");
				grado = (!filtro.getGrado().equals("-9") ? "_Gra_" + filtro.getGrado() : "");
				grupo = (!filtro.getGrupo().equals("-9") ? "_Gru_" + filtro.getGrupo() : "");
				id = (!filtro.getId().equals("") ? "_Doc_" + filtro.getId() : "");
				
				// CAMBIO NOMBRE PERIODO
				if (Long.parseLong(filtro.getPeriodo().trim()) == 7) {
					filtro.setPeriodonom("FINAL");
				}

				nom = sede
						+ jornada
						+ met
						+ grado
						+ grupo
						+ "_Per_"
						+ filtro.getPeriodonom().trim()
						+ id
						+ "_Fec_"
						+ f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-');
				
				archivo = "Bol_" + nom + ".pdf";
				archivopre = "Bol_Pre_" + nom + ".pdf";
				archivozip = "Bol_" + nom + ".zip";

				// System.out
				// .println("BOLETINES: Insertar el reporte en datos_boletin");
				// System.out.println("		conDescriptores: "
				// + filtro.getMostrarDescriptores());
				// System.out.println("		conLogros: " +
				// filtro.getMostrarLogros());
				// System.out.println("		conLogrosP: "
				// + filtro.getMostrarLogrosP());
				// System.out.println("		conAreas: " +
				// filtro.getMostrarAreas());
				// System.out.println("		conAsignaturas: "
				// + filtro.getMostrarAsignaturas());

				if (filtro.getId() != null && !filtro.getId().trim().equals("")	&& filtro.getId().trim().length() > 0) {
					// System.out.println("BOLETINES: INSERT BOLETIN POR DOCUMENTO.");
					if (filtro.getFilVigencia() == reportesDAO.getVigenciaNumerico())
						filtro = reportesDAO.getParamsEstudiante(filtro);
					else
						filtro = reportesDAO.getParamsEstudiante_anterior(filtro);
				}

				
				posicion = 1;
				pst = con.prepareStatement(rb3.getString("insert_datos_boletin"));
				pst.clearParameters();
				
				if(login.getPerfil().equalsIgnoreCase("510")) {
					pst.setLong(posicion++, estudianteSeleccionado.getEstInstitucion());
				} else {
					pst.setLong(posicion++, Long.parseLong(login.getInstId()));																		// DABOLINST
				}
				
				pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals("-9") ? filtro.getSede() : "-9"));									// DABOLSEDE
				pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals("-9") ? filtro.getJornada() : "-9"));							// DABOLJORNADA
				pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia().equals("-9") ? filtro.getMetodologia() : "-9"));					// DABOLMETODOLOGIA
				pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals("-9") ? filtro.getGrado() : "-9"));								// DABOLGRADO
				pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals("-9") ? filtro.getGrupo() : "-9"));								// DABOLGRUPO
				pst.setLong(posicion++,	Long.parseLong(filtro.getPeriodo().trim()));																// DABOLPERIODO
				pst.setString(posicion++, !filtro.getPeriodonom().trim().equals("") ? filtro.getPeriodonom().trim() : "");							// DABOLPERIODONOM
				pst.setString(posicion++, !filtro.getId().equals("") ? filtro.getId() : "");														// DABOLCEDULA
				pst.setString(posicion++, f2.toString().substring(0, f2.toString().indexOf(".")).replace(' ', '_').replace(':', '-').replace('.', '-'));// DABOLFECHA
				pst.setString(posicion++, archivozip);																								// DABOLNOMBREZIP
				pst.setString(posicion++, archivo);																									// DABOLNOMBREPDF
				pst.setString(posicion++, archivopre);																								// DABOLNOMBREPDFPRE
				pst.setLong(posicion++, Long.parseLong("-1"));																						// DABOLESTADO
				pst.setString(posicion++, login.getUsuarioId());																					// DABOLUSUARIO
				pst.setLong(posicion++,	Long.parseLong(!filtro.getMostrarDescriptores().equals("") ? filtro.getMostrarDescriptores() : "-9"));		// DABOLDESCRIPTORES
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarLogros().equals("") ? filtro.getMostrarLogros() : "-9"));					// DABOLLOGROS
				pst.setLong(posicion++,	Long.parseLong(filtro.isConAusenciasT() ? "1" : "-9"));														// DABOLINATOT
				pst.setLong(posicion++,	Long.parseLong(filtro.isConAusenciasD() ? "1" : "-9"));														// DABOLINADET
				// pst.setLong(posicion++,Long.parseLong(filtro.isConFirmaRector()?"1":"-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarFirmaRector().equals("") ? filtro.getMostrarFirmaRector() : "-9"));		// DABOLFIRREC
				// pst.setLong(posicion++,Long.parseLong(filtro.isConFirmaDirector()?"1":"-9"));
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarFirmaDirector().equals("") ? filtro.getMostrarFirmaDirector() : "-9"));	// DABOLFIRDIR
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarLogrosP().equals("") ? filtro.getMostrarLogrosP() : "-9"));				// DABOLTOTLOGROS
				pst.setLong(posicion++,	Long.parseLong(filtro.isFormatoDos() ? "1" : "-9"));														// DABOLFORDOS
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarAreas().equals("") ? filtro.getMostrarAreas() : "-9"));					// DABOLAREA
				pst.setLong(posicion++,	Long.parseLong(!filtro.getMostrarAsignaturas().equals("") ? filtro.getMostrarAsignaturas() : "-9"));		// DABOLASIG

				// long consecRepBol= reportesDAO.getConsecReporte();
				// pst.setLong(posicion++,consecRepBol);
				pst.setString(posicion++, login.getInst());																							// DABOLINSNOMBRE
				pst.setString(posicion++, login.getSede());																							// DABOLSEDNOMBRE
				pst.setString(posicion++, login.getJornada());																						// DABOLJORNOMBRE
				pst.setLong(posicion++, filtro.getFilVigencia());																					// DABOLVIGENCIA

				String resol = reportesDAO.getResolInst(Long.parseLong(login.getInstId()));
				pst.setString(posicion++, resol);																									// DABOLRESOLUCION
				pst.setLong(posicion++, login.getLogNivelEval());																					// DABOLNIVEVAL
				pst.setLong(posicion++, login.getLogNumPer());																						// DABOLNUMPER
				pst.setString(posicion++, login.getLogNomPerDef());																					// DABOLNOMPERDEF

				int tipoPrees = reportesDAO.getTipoEval(filtro, login);
				pst.setInt(posicion++, tipoPrees);																									// DABOLTIPOEVALPREES
				
				pst.setInt(posicion++, filtro.getFilTipoDoc());																						// DABOLTIPODOC
				pst.setLong(posicion++, Long.parseLong(!filtro.getMostrarLogrosP().equals("") ? filtro.getMostrarLogrosP() : "-9"));				// DABOLLOGROPEND

				Long dabolConsec = getDabolConsec(con);
				pst.setLong(posicion++, dabolConsec);																								// DABOLCONSEC
				pst.setString(posicion++, login.getLogSubTitBol());																					// DABOLTIPOREP

				
				pst.executeUpdate();

//				con.commit();
//				@MCuellar: MIG-REPORTES 
//				String mensaje="15-"+dabolConsec.toString();
//				System.out.println("Mensaje Cola: " + mensaje);
//				
//				EnviarJMSReporte.enviarMensaje(mensaje);
				
				ponerReporte(modulo, login.getUsuarioId(), rb3.getString("boletines.PathBoletines") + archivozip + "", "zip", "" + archivozip, "-1", "ReporteInsertarEstado");// Estado -1
				updateDatosBoletin("-1",archivozip , dabolConsec);// se forza el estado -1 para garantizar que la solicitud queda en cola para su generación
				
				// System.out.println("Se insertn el ZIP en Reporte con estado -1");
				siges.util.Logger.print(
						login.getUsuarioId(),
						"Petición de Boletin:_Institucion:_"
								+ login.getInstId() + "_Usuario:_"
								+ login.getUsuarioId() + "_NombreBoletin:_"
								+ archivozip + "", 3, 1, this.toString());
				setMensaje(alert);
				request.setAttribute("mensaje", getMensaje());

				cont = cola_boletines();

				if (cont != null && !cont.equals(""))
					updatePuestoBoletin(cont, archivozip, login.getUsuarioId(),	"update_puesto_boletin");
				
			} else {
				
				rs.close();
				pst.close();
				setMensaje("Ya existe una petición de boletín con los mismos parámetros!");
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("mensaje2", "Ya existe una petición de boletín con los mismos parámetros!");
				return s; // lo devuelve a la jsp
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, login.getUsuarioId(), rb3.getString("boletines.PathBoletines") + archivozip + "", "zip", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrio excepción en el Hilo");
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
		/**
		 * Se redirecciona a boletines para proceder con la genaración		 * 
		 */
		Boletin boletin = new Boletin(cursor, contextoTotal, path_jasper, path_logo, path_escudo);
		boletin.procesar_solicitudes();// se dispara el procesamiento de solicitudes
		/**
		 * Fin llamada Boletines
		 */
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

	public void updatePuestoBoletin(String puesto, String nombreboletinzip,
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
			// System.out.println("nnSe ejecutn update_puesto_boletin!!!!");
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

	public String cola_boletines() {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		String cant = null;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_boletin"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();
			// System.out.println("nnSe ejecutn puesto_del_boletin!!!!");

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
		// System.out.println("RETURN CANT: " + cant);
		return cant;
	}

	/**
	 * Inserta los valores por defecto para el bean de informaciÃ³n bnsica con
	 * respecto a la informacion de la institucinn educativa
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return boolean
	 */
	public boolean asignarBeans(HttpServletRequest request)
			throws ServletException, IOException {
		login = (Login) session.getAttribute("login");
		filtro = (FiltroBeanReports) session.getAttribute("filtrob");
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
			mensaje = "VERIFIQUE LA SIGUIENTE INFORMACIÓN:\n\n";
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

	/**
	 * @param cn
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean isPerCerrado(Connection cn, FiltroBeanReports filtro,
			Login login) throws Exception {
		// System.out.println("isPerCerrado ");

		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		ItemVO itemVO = null;
		List list = new ArrayList();

		long codInst = Long.parseLong(login.getInstId());
		long codSede = Long.parseLong(!filtro.getSede().equals("-9") ? filtro
				.getSede() : "-9");
		long codJord = Long
				.parseLong(!filtro.getJornada().equals("-9") ? filtro
						.getJornada() : "-9");
		long codPer = Long.parseLong(filtro.getPeriodo().trim());
		long vig = filtro.getFilVigencia();

		// System.out.println("vig " + vig);
		// System.out.println("codInst  " + codInst);
		// System.out.println("codSede  " + codSede);
		// System.out.println("codJord  " + codJord);
		// System.out.println("codPer  " + codPer);
		try {
			// cn=cursor.getConnection();
			st = cn.prepareStatement(rb3
					.getString("plantillaBoletin.isPerCerrado." + codPer));
			st.setLong(posicion++, vig);
			st.setLong(posicion++, codInst);
			st.setLong(posicion++, codSede);
			st.setLong(posicion++, codJord);
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				// System.out.println("resultado " + rs.getLong(posicion));
				return true;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public long getDabolConsec(Connection cn) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb3.getString("common.dabolConsec"));
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getLong(i++);
			}
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
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

	public boolean updateColaBolEstadoCero() {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("boletines.updateEnCola"));
			posicion = 1;
			pst.executeUpdate();
			pst.close();
			// System.out.println("REPORTES BOLETINES: actualizn en cola los reportes boletines generando");
			con.commit();
		} catch (Exception e) {
			System.out
					.println("REPORTES BOLETINES: nOcurrin Excepcion al actualizar el estado a -1!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}
	
	public void updateDatosBoletin(String nuevoestado, String nombreboletin,
			long dabolConsec) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb3.getString("update_datos_boletin_new"));
			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(nuevoestado));
			pst.setString(posicion++, (nombreboletin));
			pst.setLong(posicion++, dabolConsec);
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
}
