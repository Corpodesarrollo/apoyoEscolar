package siges.reporte;

/**
 *	VERSION		FECHA			AUTOR				DESCRIPCION
 *		1.0		19/03/2021		JORGE CAMACHO		Código espagueti
 *
*/

import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;

import siges.dao.Util;
import siges.dao.Cursor;
import siges.login.beans.Login;
import siges.util.beans.ReporteVO;
import siges.dao.OperacionesGenerales;
import siges.reporte.beans.FiltroBeanCarne;
import siges.exceptions.InternalErrorException;


/**
 * Nombre: ControllerCarneFiltroSave<BR>
 * Descripcinn: Controla la negociacion de busqueda para la vista de resultados <BR>
 * Funciones de la pngina: Busca los datos y redirige el control a la vista de
 * resultados <BR>
 * Entidades afectadas: Boletin <BR>
 * Fecha de modificacinn: 04/10/2005 <BR>
 * 
 * @author Latined <BR>
 * @version v 1.0 <BR>
 */

public class ControllerCarneFiltroSave extends HttpServlet {

	private boolean err;			// Variable que indica si hay o no errores en la validacion de los datos del formulario
	private Login login;
	private Cursor cursor;			// Objeto que maneja las sentencias sql
	private String mensaje;
	private HttpSession session;
	private ResourceBundle r, rb3;
	private Collection list;
	private Object[] o;
	private java.sql.Timestamp f2;
	private FiltroBeanCarne filtro;
	private Carne carne;
	private Util util;
	private final String modulo = "20";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletConfig config;
	private String s;
	private String s1;
	private String archivo, archivozip, archivopre;
	private String insertar;
	private String ant;
	private String sig;
	private String ant2;
	private String sig2;
	private String er;

	private String home;
	private String path;

	/**
	 * Procesa la peticion HTTP
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 **/

	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		
		int count;
		int posicion = 1;
		String cont = null;
		
		r = ResourceBundle.getBundle("path");
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		rb3 = ResourceBundle.getBundle("siges.reporte.bundle.carnes");
		
		String nom;
		String existeboletin, boton;
		
		s = "/Reportes.do";
		s1 = "/reporte/ControllerCarneFiltroEdit.do";
		
		ant = getServletConfig().getInitParameter("ant");		// reporte/GenerarCarne.jsp
		sig = getServletConfig().getInitParameter("sig");		// reporte/GenerarCarne.jsp
		ant2 = getServletConfig().getInitParameter("ant2");		// reporte/GenerarCarne.jsp
		sig2 = getServletConfig().getInitParameter("sig2");
		er = getServletContext().getInitParameter("error");
		home = getServletContext().getInitParameter("home");
		
		cursor = new Cursor();
		session = request.getSession();
		
		String alert = "El reporte se está generando en este momento.\nPulse en el hipervínculo 'Listado de Reportes' para ver si el reporte ya fue generado.\nO vaya a la opción de menu 'Reportes generados'";
		String alert2 = "Usted ya mandó a generar un reporte de carnés... Por favor espere a que termine, para solicitar uno nuevo";
		String alert3 = "Ya existe una petición de carné con los mismos parámetros";
		
		util = new Util(cursor);
		boton = (request.getParameter("cmd") != null) ? request.getParameter("cmd") : new String("");

		if (!asignarBeans(request)) {
			setMensaje("Error capturando datos de sesión para el usuario");
			request.setAttribute("mensaje", mensaje);
			ir(2, er, request, response);
			return null;
		}

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("existe_mismo_carne_en_cola"));

			posicion = 1;
			pst.clearParameters();

			if (filtro.getSede() == null) {
				filtro = (FiltroBeanCarne) session.getAttribute("filtro");
			}

			String id2 = (!filtro.getId().equals("") || !filtro.getId().equals(" ") ? filtro.getId() : "-9");
			
			pst.setLong(posicion++, Long.parseLong(filtro.getInstitucion()));
			pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals("-9") ? filtro.getSede() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals("-9") ? filtro.getJornada() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia().equals("-9") ? filtro.getMetodologia() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals("-9") ? filtro.getGrado() : "-9"));
			pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals("-9") ? filtro.getGrupo() : "-9"));

			if (id2 != null && !id2.equals("") && !id2.equals(" ")) {
				pst.setString(posicion++, id2);
			} else {
				pst.setString(posicion++, "-9");
			}
			pst.setString(posicion++, login.getUsuarioId());

			rs = pst.executeQuery();

			if (!rs.next()) {
				
				System.out.println("******NO HAY NINGUNO CON LOS MISMOS PARÁMETROS!...EN ESTADO CERO O -1!*********");
				
				rs.close();
				pst.close();
				
				pst = con.prepareStatement(rb3.getString("existecarne"));
				posicion = 1;
				pst.clearParameters();
				pst.setLong(posicion++, Long.parseLong(login.getUsuarioId()));
				rs = pst.executeQuery();

				if (!rs.next()) {
					
					rs.close();
					pst.close();
					
					System.out.println("******NO HAY NINGUNO GENERÁNDOSE PARA ESTE USUARIO!... EN ESTADO CERO!*********");

					String localidad = (!filtro.getLocalidad().equals("-9") ? "_Loc_" + filtro.getLocalidad() : "");
					String institucion = (!filtro.getInstitucion().equals("-9") ? "_Inst_" + filtro.getInstitucion() : "");
					String sede = (!filtro.getSede().equals("-9") ? "_Sed_"	+ filtro.getSede().trim() : "");
					String jornada = (!filtro.getJornada().equals("-9") ? "_Jrd_" + filtro.getJornada().trim() : "");
					String met = (!filtro.getMetodologia().equals("-9") ? "_Metd_" + filtro.getMetodologia() : "");
					String grado = (!filtro.getGrado().equals("-9") ? "_Gra_" + filtro.getGrado() : "");
					String grupo = (!filtro.getGrupo().equals("-9") ? "_Gru_" + filtro.getGrupo() : "");
					String id = (!filtro.getId().equals("") || !filtro.getId().equals(" ") ? "_Doc_" + filtro.getId() : "");

					nom = localidad + institucion + sede + jornada + met + grado + grupo + id + "_Fecha_" + f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-');
					System.out.println("******Nombre archivo carnet: " + nom);
					
					if (GenericValidator.isInt(filtro.getFormatoCarne().trim())	&& Long.parseLong(filtro.getFormatoCarne().trim()) > 10) {
						archivo = "Carne_Admin_Docen" + nom + ".pdf";
						archivozip = "Carne_Admin_Docen" + nom + ".zip";
					} else {
						archivo = "Carne_Estd" + nom + ".pdf";
						archivozip = "Carne_Estd" + nom + ".zip";
					}

					System.out.println("Se manda a insertar el reporte en datos_carne!!!!");
					
					pst = con.prepareStatement(rb3.getString("insert_datos_carne"));
					posicion = 1;
					
					pst.clearParameters();
					pst.setLong(posicion++, Long.parseLong(!filtro.getInstitucion().equals("-9") ? filtro.getInstitucion() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getSede().equals("-9") ? filtro.getSede() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getJornada().equals("-9") ? filtro.getJornada() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getMetodologia().equals("-9") ? filtro.getMetodologia() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getGrado().equals("-9") ? filtro.getGrado() : "-9"));
					pst.setLong(posicion++, Long.parseLong(!filtro.getGrupo().equals("-9") ? filtro.getGrupo() : "-9"));
					pst.setString(posicion++, !filtro.getId().equals("") ? filtro.getId() : "");
					pst.setLong(posicion++, Long.parseLong(!filtro.getOrden().equals("-9") ? filtro.getOrden() : "-9"));
					pst.setString(posicion++, f2.toString().replace(' ', '_').replace(':', '-').replace('.', '-'));
					pst.setString(posicion++, archivozip);
					pst.setString(posicion++, archivo);
					pst.setLong(posicion++, Long.parseLong("-1"));
					pst.setString(posicion++, login.getUsuarioId());
					
					if (GenericValidator.isInt(filtro.getFormatoCarne().trim())) {
						pst.setLong(posicion++,	Integer.valueOf(filtro.getFormatoCarne().trim()).longValue());
					} else {
						pst.setLong(posicion++, 1);
					}
					pst.executeUpdate();
					System.out.println("Se insertó en datos_carne!!!!");
					con.commit();
					
					ponerReporte(modulo, login.getUsuarioId(), rb3.getString("carnes.PathCarnes") + archivozip + "", "zip20", "" + archivozip, "-1", "ReporteInsertarEstado");
					System.out.println("Se insertó el ZIP en Reporte con estado -1");
					
					siges.util.Logger.print(login.getUsuarioId(), "Peticinn de Boletin:_Institucion:_" + login.getInstId() + "_Usuario:_"
									+ login.getUsuarioId() + "_NombreBoletin:_" + archivozip + "", 6, 1, this.toString());
					
					setMensaje(alert);
					request.setAttribute("mensaje", getMensaje());
					request.getSession().removeAttribute("filtro");
					request.getSession().removeAttribute("filtroCarne");
					cont = cola_carnes();

					if (cont != null && !cont.equals(""))
						updatePuestoBoletin(cont, archivozip, login.getUsuarioId(), "update_puesto_carne");
					
				} else {
					
					rs.close();
					pst.close();
					setMensaje(alert2);
					request.setAttribute("mensaje", getMensaje());
					
					return s1;
					
				}
				
			} else {
				
				System.out.println("***Ya hay un registro de carné repetido***");
				rs.close();
				pst.close();
				setMensaje(alert3);
				request.setAttribute("mensaje", getMensaje());
				
				return s1;// lo devuelve a la jsp
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ponerReporteMensaje("2", modulo, login.getUsuarioId(),
					rb3.getString("carnes.PathCarnes") + archivozip + "",
					"zip20", "" + archivozip, "ReporteActualizarBoletin",
					"Ocurrin excepcinn en FiltroSave");
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
		reporteVO.setRepTipo(ReporteVO._REP_CARNES);
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

	public void ponerReporte(String modulo, String us, String rec, String tipo,	String nombre, String estado, String prepared) {
		
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
	public void updatePuestoBoletin(String puesto, String nombreboletinzip, String user, String prepared) {
		
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
	public String cola_carnes() {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		int posicion = 1;
		String cant = null;

		try {
			
			con = cursor.getConnection();
			pst = con.prepareStatement(rb3.getString("puesto_del_carne"));
			posicion = 1;
			pst.clearParameters();
			rs = pst.executeQuery();

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
		filtro = (FiltroBeanCarne) session.getAttribute("filtroCarne");
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
			mensaje = "VERIFIQUE LA SIGUIENTE informaciÃ³n: \n\n";
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
	public void ir(int a, String s, HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		
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
		if (cursor != null)
			cursor.cerrar();
		cursor = null;
	}

}
