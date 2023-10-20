/**
 * 
 */
package siges.gestionAcademica.repEstadisticos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.FiltroCommonVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.repEstadisticos.vo.DatosBoletinVO;
import siges.gestionAcademica.repEstadisticos.vo.EstudianteVO;
import siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO;
import siges.gestionAcademica.repEstadisticos.vo.ParamsVO;
import siges.gestionAcademica.repEstadisticos.vo.ReporteEstadisticoVO;
import siges.login.beans.Login;

/**
 * 17/05/2010 
 * @author Athenea TA
 * @version 1.1
 */
/**
 * @author desarrollo
 * 
 */
public class RepEstadisticosDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public RepEstadisticosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.repEstadisticos.bundle.repEstadisticos");
	}

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	/**
	 * @funtion
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean insertarSolicitud(FiltroRepEstadisticoVO filtro)
			throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;

		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("reporte.insertar"));
			st.setLong(posicion++, filtro.getTipoReporte());
			st.setLong(posicion++, filtro.getVig());
			st.setInt(posicion++, filtro.getNivelEval());
			st.setLong(posicion++, filtro.getFillocal());
			st.setString(posicion++, filtro.getFillocalNomb());
			st.setLong(posicion++, filtro.getFilinst());
			st.setString(posicion++, filtro.getFilinstNomb());
			st.setLong(posicion++, filtro.getZona());
			st.setString(posicion++, filtro.getFilzonaNomb());
			st.setLong(posicion++, filtro.getFilsede());
			st.setString(posicion++, filtro.getFilsedeNomb());
			st.setLong(posicion++, filtro.getFiljornd());
			st.setString(posicion++, filtro.getFiljorndNomb());
			st.setLong(posicion++, filtro.getFilmetod());
			st.setString(posicion++, filtro.getFilmetodNomb());
			st.setLong(posicion++, filtro.getFilgrado());
			st.setString(posicion++, filtro.getFilgradoNomb());
			st.setLong(posicion++, filtro.getFilgrupo());
			st.setString(posicion++, filtro.getFilgrupoNomb());
			st.setString(posicion++, filtro.getAsigCodigos());
			st.setString(posicion++, filtro.getAsigNombres());
			st.setLong(posicion++, filtro.getPeriodo());
			st.setDouble(posicion++, filtro.getValorIni());
			st.setDouble(posicion++, filtro.getValorFin());
			st.setLong(posicion++, filtro.getEscala());
			st.setString(posicion++, filtro.getEscalaNombre());
			st.setLong(posicion++, filtro.getOrdenReporte());
			st.setLong(posicion++, filtro.getNumPer());
			st.setString(posicion++, filtro.getNomPerFinal());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getFecha());
			st.setString(posicion++, filtro.getNombre_zip());
			st.setString(posicion++, filtro.getNombre_pdf());
			st.setString(posicion++, filtro.getNombre_xls());
			st.setInt(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			st.setString(posicion++, filtro.getUsuario());
			st.setString(posicion++, "");
			st.setString(posicion++, "");
			st.executeUpdate();
			String a;
			return true;

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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @param filtro
	 */
	public void insertarReporte(FiltroRepEstadisticoVO filtro) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("reporte.insertarReporte"));
			String ruta = rb.getString("reporte.PathEstadistico");
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, filtro.getUsuario());
			pst.setString(posicion++, ruta + filtro.getNombre_zip());
			pst.setString(posicion++, "zip");
			pst.setString(posicion++, filtro.getNombre_zip());
			pst.setString(posicion++, ParamsVO.REP_MODULO);
			pst.setString(posicion++, "" + ParamsVO.ESTADO_REPORTE_COLA);
			pst.executeUpdate();
			pst.close();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());

		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListarden() throws Exception {

		List list = new ArrayList();
		try {

			list.add(new ItemVO(ParamsVO.ORDEN_APELLIDO,
					ParamsVO.ORDEN_APELLIDO_));
			list.add(new ItemVO(ParamsVO.ORDEN_IDENTIFICACION,
					ParamsVO.ORDEN_IDENTIFICACION_));
			list.add(new ItemVO(ParamsVO.FICHA_NOMBRE, ParamsVO.FICHA_NOMBRE_));
			list.add(new ItemVO(ParamsVO.FICHA_COIDGO, ParamsVO.FICHA_COIDGO_));

		} catch (Exception e) {
		} finally {

		}
		return list;
	}

	/**
	 * @param modulo
	 * @param us
	 * @param rec
	 * @param tipo
	 * @param nombre
	 * @param estado
	 */
	public void ponerReporte(ReporteEstadisticoVO reporteVO) throws Exception {
		// System.out.println("Reportes estadisticos: ponerReporte");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb.getString("ReporteInsertarEstado"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, reporteVO.getUsuario());
			pst.setString(posicion++, reporteVO.getRecurso());
			pst.setString(posicion++, reporteVO.getTipo());
			pst.setString(posicion++, reporteVO.getNombre());
			pst.setInt(posicion++, reporteVO.getModulo());
			pst.setInt(posicion++, reporteVO.getEstado());
			pst.executeUpdate();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param estado
	 * @param modulo
	 * @param us
	 * @param rec
	 * @param tipo
	 * @param nombre
	 * @param prepared
	 * @param mensaje
	 */
	/*
	 * public void ponerReporteMensaje(ReporteVO reporteVO)throws Exception{
	 * System.out.println("ponerReporteMensaje"); Connection con=null;
	 * PreparedStatement pst=null; int posicion=1;
	 * 
	 * try{ con=cursor.getConnection();
	 * 
	 * pst=con.prepareStatement(rb.getString("ponerReporteMensaje"));
	 * posicion=1; pst.clearParameters();
	 * pst.setInt(posicion++,reporteVO.getEstado() );
	 * pst.setString(posicion++,reporteVO.g );
	 * 
	 * //where pst.setString(posicion++,reporteVO.getUsuario() );
	 * pst.setString(posicion++,reporteVO.getRecurso());
	 * pst.setString(posicion++,reporteVO.getTipo());
	 * pst.setString(posicion++,reporteVO.getNombre());
	 * pst.setInt(posicion++,reporteVO.getModulo());
	 * //pst.setInt(posicion++,ParamsVO.ESTADO_REPORTE_COLA);
	 * 
	 * 
	 * int i = pst.executeUpdate(); pst.close();
	 * System.out.println("nActualizn la tabla Reporte con el mensaje recibido!"
	 * ); con.commit();
	 * 
	 * if(i == 0){ throw new Exception("Error actualizando registro reporte"); }
	 * } catch(InternalErrorException e ){ e.printStackTrace(); }
	 * catch(Exception e ){ e.printStackTrace(); } finally{ try{
	 * OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(con); } catch(Exception e){} } }
	 */

	/**
	 * @param estado
	 * @param modulo
	 * @param us
	 * @param rec
	 * @param tipo
	 * @param nombre
	 * @param prepared
	 * @param mensaje
	 */
	public void ponerReporteMensaje(ReporteEstadisticoVO reporteVO)
			throws Exception {
		// System.out.println("Estadisticos : ponerReporteMensaje");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb
					.getString("estadisticos.ponerReporteMensaje"));
			posicion = 1;
			pst.clearParameters();
			pst.setInt(posicion++, reporteVO.getEstado());
			pst.setString(posicion++, reporteVO.getMensaje());

			// where
			pst.setString(posicion++, reporteVO.getUsuario());
			pst.setString(posicion++, reporteVO.getTipo());
			pst.setString(posicion++, reporteVO.getNombre());
			pst.setInt(posicion++, reporteVO.getModulo());

			int i = pst.executeUpdate();
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
	 * @param reporteVO
	 */
	public void limpiarTablasAsistencia(ReporteEstadisticoVO reporteVO) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 0;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("delete_reporte_asistencia"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, reporteVO.getUsuario());
			pst.setString(posicion++, reporteVO.getFecha());
			int i = pst.executeUpdate();
			if (i == 0) {
				throw new Exception(
						"Error al eliminar registro tabla temporal reporte asistencia.");
			}
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
	 * @param filtroVO
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean proceso_asistencia(FiltroRepEstadisticoVO filtroVO,
			Login login) throws Exception {
		// System.out.println("Reportes estadisticos: proceso_asistencia");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;
		int vigencia = 0;

		try {

			vigencia = getVigenciaInst(filtroVO.getFilinst());

			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("insert_encabezado_asistencia"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, login.getUsuarioId());
			pst.setLong(posicion++, filtroVO.getFilinst());
			pst.setLong(posicion++, filtroVO.getFilsede());
			pst.setLong(posicion++, filtroVO.getFiljornd());
			pst.setLong(posicion++, filtroVO.getFilmetod());
			pst.setLong(posicion++, filtroVO.getFilgrado());
			pst.setLong(posicion++, filtroVO.getFilgrupo());
			pst.executeUpdate();
			pst.close();

		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(new Exception());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(new Exception());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * @param filtroVO
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean isListaAsistencia(FiltroRepEstadisticoVO filtroVO,
			ReporteEstadisticoVO reporteVO, Login login) throws Exception {
		// System.out.println("isListaAsistencia");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		int vigencia = 0;

		try {

			vigencia = getVigenciaInst(filtroVO.getFilinst());

			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("getListaAsistencia"));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, login.getUsuarioId());
			pst.setString(posicion++, reporteVO.getFecha());
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception(new Exception());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(new Exception());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * @function: Llama el procedimiento que llena la tabla REP_HOJA_VIDA
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean callHojaVida(FiltroRepEstadisticoVO filtro,
			ReporteEstadisticoVO reporteVO) throws Exception {
		// System.out.println("callHojaVida " );
		Connection con = null;
		CallableStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean band = true;
		try {
			con = cursor.getConnection();
			rs = null;
			pst = con.prepareCall(rb.getString("reporte.callHojaVida"));
			posicion = 1;
			pst.setLong(posicion++, filtro.getFilinst());
			pst.setString(posicion++, filtro.getFilinstNomb());
			pst.setLong(posicion++, filtro.getFilsede());
			pst.setLong(posicion++, filtro.getFiljornd());
			pst.setLong(posicion++, filtro.getFilgrado());
			pst.setLong(posicion++, filtro.getFilmetod());
			pst.setLong(posicion++, filtro.getFilgrupo());
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, "-99");
			pst.setString(posicion++, filtro.getUsuario());
			// System.out.println("filtro.getUsuario() " + filtro.getUsuario());
			// System.out.println("reporteVO.getFecha()  " +
			// reporteVO.getFecha());
			pst.setString(posicion++, reporteVO.getFecha());
			pst.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle);
		} finally {
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeCallableStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		// System.out.println("true");
		return band;
	}

	/**
	 * @param filtro
	 * @param reporteVO
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public FiltroRepEstadisticoVO insertarDatosBoletin(
			FiltroRepEstadisticoVO filtro, ReporteEstadisticoVO reporteVO)
			throws Exception {
		// System.out.println(" RepEstadisticos: inserrDatosBoletin");
		// System.out.println("--------------------------------------------------------reporteVO.setTipoReporte "
		// + reporteVO.getTipoReporte());
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {

			cn = cursor.getConnection();

			pst = cn.prepareStatement(rb
					.getString("estadisticos.insert_datos_boletin"));
			pst.setLong(posicion++, filtro.getFilinst()); // DABOLINST
			pst.setLong(posicion++, filtro.getFilsede());// DABOLSEDE
			pst.setLong(posicion++, filtro.getFiljornd());// DABOLJORNADA
			pst.setLong(posicion++, filtro.getFilmetod());// DABOLMETODOLOGIA
			pst.setLong(posicion++, filtro.getFilgrado());// DABOLGRADO
			pst.setLong(posicion++, filtro.getFilgrupo()/* filtro.getFilgrupo() */);// DABOLGRUPO
			pst.setLong(posicion++, filtro.getFilperd());// DABOLPERIODO
			pst.setString(posicion++, "" + filtro.getFilperdNomb());// DABOLPERIODONOM
			pst.setString(posicion++, filtro.getFilnumdoc());// DABOLCEDULA

			pst.setString(posicion++, filtro.getFecha());// DABOLFECHA
			pst.setString(posicion++, reporteVO.getNombre_zip());// DABOLNOMBREZIP

			pst.setString(posicion++, reporteVO.getNombre_pdf());// DABOLNOMBREPDF
			pst.setString(posicion++, "-99");// DABOLNOMBREPDFPRE
			pst.setLong(posicion++, reporteVO.getEstado());// DABOLESTADO
			pst.setString(posicion++, filtro.getUsuario());// login.getUsuarioId()
															// //DABOLUSUARIO
			pst.setLong(posicion++, 1);// MostrarDescriptores()
										// //DABOLDESCRIPTORES
			pst.setLong(posicion++, 1);// MostrarLogros() //DABOLLOGROS
			pst.setLong(posicion++, -9);// ConAusenciasT //DABOLINATOT
			pst.setLong(posicion++, -9);// isConAusenciasD //DABOLINADET
			pst.setLong(posicion++, -9);// ConFirmaRector() //DABOLFIRREC
			pst.setLong(posicion++, -9);// ConFirmaRector() //DABOLFIRDIR
			pst.setLong(posicion++, -9);// MostrarLogrosP //DABOLTOTLOGROS
			pst.setLong(posicion++, -9);// FormatoDos //DABOLFORDOS
			if (reporteVO.getTipoReporte() == ParamsVO.TIPOREP_RepEstdcDesct) {
				pst.setLong(posicion++, filtro.getFilarea());
			} else {
				pst.setLong(posicion++, 1);// MostrarAreas //DABOLAREA
			}
			pst.setLong(posicion++, 1);// MostrarAsignaturas() //DABOLASIG
			// long consecRepBol= getConsecReporte(cn);
			// pst.setLong(posicion++,consecRepBol); //DABOLCONSEC
			pst.setString(posicion++, filtro.getFilinstNomb());// DABOLINSNOMBRE
			pst.setString(posicion++, filtro.getFilsedeNomb()); // DABOLSEDNOMBRE
			pst.setString(posicion++, filtro.getFiljorndNomb()); // DABOLJORNOMBRE
			pst.setLong(posicion++, filtro.getFilvigencia()); // DABOLVIGENCIA

			String resol = getResolInst(cn, filtro.getFilinst());//
			pst.setString(posicion++, resol);// DABOLRESOLUCION

			int codigoNivelEval = getTipoEval_codigoNivelEval(cn, filtro);
			pst.setLong(posicion++, codigoNivelEval); // DABOLNIVEVAL
			pst.setLong(posicion++, filtro.getFilperd_num());// DABOLNUMPER
			pst.setString(posicion++, filtro.getFilperdNomb_final());// DABOLNOMPERDEF
			long tipoPrees = getTipoEval(cn, filtro).getCod_tipo_eval_pree();
			pst.setLong(posicion++, tipoPrees); // DABOLTIPODOC
			pst.setLong(posicion++, filtro.getFiltipodoc()); // DABOLTIPOREP
			pst.setLong(posicion++, reporteVO.getTipoReporte()); // DABOLTIPOREP

			pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			System.out.println("ERROR inserrDatosBoletin" + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {

				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return filtro;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FUNCIONES PARA EL HILO
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return
	 * @throws Exception
	 */
	/*
	 * public List getSolicitudes() throws Exception{
	 * System.out.println("RepEstadisticos: getSolicitudes"); Connection
	 * cn=null; PreparedStatement st=null; ResultSet rs=null; List list = new
	 * ArrayList(); ItemVO itemVO = null; int posicion = 1;
	 * FiltroRepEstadisticoVO rep=null; try{ cn=cursor.getConnection();
	 * System.out.println("consutla " + rb.getString("estadisticos.rep_generar")
	 * ); st=cn.prepareStatement(rb.getString("estadisticos.rep_generar"));
	 * posicion=1; st.setLong(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
	 * 
	 * st.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcAisg);
	 * st.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcAisg);
	 * st.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcAisg);
	 * 
	 * rs=st.executeQuery();
	 * 
	 * while (rs.next()){ posicion=1; rep=new FiltroRepEstadisticoVO();
	 * //,,,,,,,,,,,, ,,,,,DABOLASIG, DABOLCONSEC, DABOLINSNOMBRE,
	 * DABOLSEDNOMBRE, DABOLJORNOMBRE, DABOLVIGENCIA, DABOLRESOLUCION,
	 * DABOLNIVEVAL, DABOLSUBTITULO, (select INSCODDANE from INSTITUCION where
	 * INSCODIGO = DABOLINST) as DANE, DABOLNUMPER, DABOLNOMPERDEF,
	 * DABOLTIPOEVALPREES from DATOS_BOLETIN WHERE DABOLESTADO=? AND
	 * DABOLTIPOREP IN (?,?,?) rep.setFilinst(rs.getInt(posicion++));//DABOLINST
	 * rep.setFilsede(rs.getInt(posicion++));//DABOLSEDE,
	 * rep.setFiljornd(rs.getInt(posicion++));//DABOLJORNADA
	 * rep.setFilmetod(rs.getInt(posicion++));//DABOLMETODOLOGIA
	 * rep.setFilgrado(rs.getLong(posicion++));//DABOLGRADO
	 * rep.setFilgrupo(rs.getLong(posicion++));//DABOLGRUPO
	 * rep.setFilperd(rs.getLong(posicion++));//DABOLPERIODO
	 * rep.setFilperd_num(rs.getString(posicion++));//DABOLPERIODONOM
	 * rep.setFilnumdoc(rs.getString(posicion++));//DABOLCEDULA
	 * rep.setFilfecha(rs.getString(posicion++));//DABOLFECHA
	 * rep.setUsuario(rs.getString(posicion++));//DABOLUSUARIO
	 * rep.setFilNOMBREZIP(rs.getString(posicion++));//DABOLNOMBREZIP
	 * rep.setFilNOMBREPDF(rs.getString(posicion++));//DABOLNOMBREPDF
	 * rep.setFilNOMBREPDFPRE(rs.getString(posicion++));//DABOLNOMBREPDFPRE
	 * rep.setFilDESCRIPTORES(rs.getInt(posicion++));//DABOLDESCRIPTORES
	 * rep.setFilLOGROS(rs.getInt(posicion++));//DABOLLOGROS
	 * rep.setFilTOTLOGROS(rs.getInt(posicion++));//DABOLTOTLOGROS
	 * rep.setFilAREA(rs.getInt(posicion++));//DABOLAREA
	 * rep.setFilASIG(rs.getInt(posicion++));
	 * rep.setFilCONSEC(rs.getLong(posicion++));
	 * rep.setFilINSNOMBRE(rs.getString(posicion++));
	 * rep.setFilSEDNOMBRE(rs.getString(posicion++));
	 * rep.setFilJORNOMBRE(rs.getString(posicion++));
	 * //db.setFilJORNOMBRE(rs.getString(posicion++));
	 * rep.setFilVIGENCIA(rs.getInt(posicion++));
	 * rep.setFilRESOLUCION(rs.getString(posicion++));
	 * rep.setFilNIVEVAL(rs.getInt(posicion++));
	 * rep.setFilSUBTITULO(rs.getString(posicion++));
	 * rep.setDANE(rs.getString(posicion++));
	 * rep.setFilNUMPER(rs.getInt(posicion++));
	 * rep.setFilNOMPERDEF(rs.getString(posicion++));
	 * rep.setFilTIPOEVALPREES(rs.getInt(posicion++)); list.add(rep);
	 * 
	 * }
	 * 
	 * if(!list.isEmpty()){
	 * System.out.println("REP COMPARATIVOS:nnnnSI HAY REPORTES POR GENERAR!!!!"
	 * ); } }catch(SQLException sqle){ sqle.printStackTrace(); throw new
	 * Exception("Error de datos: "+sqle.getMessage()); }catch(Exception sqle){
	 * sqle.printStackTrace(); throw new
	 * Exception("Error interno: "+sqle.getMessage()); }finally{ try{
	 * OperacionesGenerales.closeResultSet(rs);
	 * OperacionesGenerales.closeStatement(st);
	 * OperacionesGenerales.closeConnection(cn); }catch(InternalErrorException
	 * inte){} } return list; }
	 */

	public List getSolicitudes() throws Exception {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		DatosBoletinVO db = null;
		List lista = new ArrayList();
		try {
			con = cursor.getConnection();

			pst = con.prepareStatement(rb
					.getString("estadisticos.boletin_a_generar"));
			posicion = 1;
			// pst.setLong(posicion++,consecutivo);

			pst.setLong(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			pst.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			pst.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			pst.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			pst.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			pst.setLong(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);
			rs = pst.executeQuery();

			while (rs.next()) {
				int pos = 1;
				db = new DatosBoletinVO();
				db.setDABOLINST(rs.getLong(pos++));
				db.setDABOLSEDE(rs.getLong(pos++));
				db.setDABOLJORNADA(rs.getLong(pos++));
				db.setDABOLMETODOLOGIA(rs.getLong(pos++));
				db.setDABOLGRADO(rs.getLong(pos++));
				db.setDABOLGRUPO(rs.getLong(pos++));
				db.setDABOLPERIODO(rs.getLong(pos++));
				db.setDABOLPERIODONOM(rs.getString(pos++));
				db.setDABOLCEDULA(rs.getString(pos++));
				db.setDABOLFECHA(rs.getString(pos++));
				db.setDABOLUSUARIO(rs.getString(pos++));
				db.setDABOLNOMBREZIP(rs.getString(pos++));
				// System.out.println("db.setDABOLNOMBREZIP " +
				// db.getDABOLNOMBREZIP());
				db.setDABOLNOMBREPDF(rs.getString(pos++));
				db.setDABOLNOMBREPDFPRE(rs.getString(pos++));
				db.setDABOLDESCRIPTORES(rs.getInt(pos++));
				db.setDABOLLOGROS(rs.getInt(pos++));
				db.setDABOLTOTLOGROS(rs.getInt(pos++));
				db.setDABOLAREA(rs.getInt(pos++));
				db.setDABOLASIG(rs.getInt(pos++));
				db.setDABOLCONSEC(rs.getLong(pos++));
				db.setDABOLINSNOMBRE(rs.getString(pos++));
				db.setDABOLSEDNOMBRE(rs.getString(pos++));
				db.setDABOLJORNOMBRE(rs.getString(pos++));
				// db.setDABOLJORNOMBRE(rs.getString(pos++));
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				db.setDABOLTIPOREP(rs.getInt(pos++));
				lista.add(db);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return lista;
	}

	/**
	 * @param consec
	 */
	public void limpiarTablas(long consec) {
		// System.out.println("RepEstadisticos : limpiarTablas");
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN.pk_bol_borrar_tablas(?)}");
			cstmt.setLong(posicion++, consec);
			cstmt.executeUpdate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean updateSolicitud(DatosBoletinVO db) throws Exception {
		// System.out.println( "Estadistica : updateSolicitud");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("estadisticos.updateDaBol"));

			// , ParamsVO.ESTADO_REPORTE_EJE, rep.getDABOLFECHAGEN(),
			// rep.getDABOLFECHAFIN()
			st.setInt(posicion++, db.getDABOLESTADO());
			// System.out.println("db.getDABOLESTADO() -> "+
			// db.getDABOLESTADO());
			if (db.getDABOLFECHAGEN() == null) {
				st.setNull(posicion++, ParamsVO.CADENA);
			} else {
				st.setString(posicion++, db.getDABOLFECHAGEN());
			}
			if (db.getDABOLFECHAFIN() == null) {
				st.setNull(posicion++, ParamsVO.CADENA);
			} else {
				st.setString(posicion++, db.getDABOLFECHAFIN());
			}
			// System.out.println("db.getDABOLFECHAGEN() " +
			// db.getDABOLFECHAGEN());
			// System.out.println("db.getDABOLFECHAFIN() " +
			// db.getDABOLFECHAFIN());
			st.setLong(posicion++, db.getDABOLCONSEC());
			// System.out.println("db.getDABOLCONSEC() " + db.getDABOLCONSEC());
			st.executeUpdate();
			return true;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// throw new Exception("Error de datos: "+sqle.getMessage());
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			// throw new Exception("Error interno: "+sqle.getMessage());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	public boolean llenarTablas(DatosBoletinVO reporte, ReporteEstadisticoVO rep) {
		// System.out.println(this.getClass()+" : llenarTablas");
		Connection con = null;
		CallableStatement cstmt = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (reporte.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				/* callable statement */
				cstmt = con.prepareCall("{call PK_BOLETIN.PK_BOL_INICIAL(?)}");
				cstmt.setLong(posicion++, reporte.getDABOLCONSEC());
				// System.out.println("REP ESTADISTICOS:consec: "+reporte.getDABOLCONSEC()+" Inicia procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.executeUpdate();
				// System.out.println("REP ESTADISTICOS:consec: "+reporte.getDABOLCONSEC()+" Fin procedimiento Hora: "+new
				// java.sql.Timestamp(System.currentTimeMillis()).toString());
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println("REP ESTADISTICOS:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte);
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				ponerReporteMensaje(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out.println("REP COMPARATIVOS:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				reporte.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(reporte);
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR EXCP: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				ponerReporteMensaje(rep);
				limpiarTablas(reporte.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeCallableStatement(cstmt);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	public int validarEstadoReporte(long consec) {
		// System.out.println("Estadisticos : validarEstadoReporte");
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (consec > 0) {
				con = cursor.getConnection();
				pst = con
						.prepareStatement(rb.getString("validarEstadoReporte"));
				posicion = 1;
				pst.setLong(1, consec);
				rs = pst.executeQuery();
				if (rs.next()) {
					estado = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(consec);
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(consec);
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
			return estado;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return estado;
	}

	public boolean validarDatosReporte(DatosBoletinVO daBolVO) {
		System.out.println("Estadisticos : validarDatosReporteAsig");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean cont = true;
		String sql = "";
		try {
			// System.out.println("daBolVO.getDABOLTIPOREP() " +
			// daBolVO.getDABOLTIPOREP());
			if (daBolVO.getDABOLTIPOREP() == ParamsVO.TIPOREP_RepEstdcAsig) {
				sql = rb.getString("estadisticos.validarDatosReporteAsig");
			} else if (daBolVO.getDABOLTIPOREP() == ParamsVO.TIPOREP_RepEstdcArea) {
				sql = rb.getString("estadisticos.validarDatosReporteArea");
			} else if (daBolVO.getDABOLTIPOREP() == ParamsVO.TIPOREP_RepEstdcLogro) {
				sql = rb.getString("estadisticos.validarDatosReporteLogro");
			} else if (daBolVO.getDABOLTIPOREP() == ParamsVO.TIPOREP_RepEstdcDesct) {
				sql = rb.getString("estadisticos.validarDatosReporteDesct");
			} else if (daBolVO.getDABOLTIPOREP() == ParamsVO.TIPOREP_RepEstdcLogroGrad) {
				sql = rb.getString("estadisticos.validarDatosReporteLogroGrad");
			}

			// System.out.println("rep.getDABOLCONSEC() " +
			// daBolVO.getDABOLCONSEC());
			if (daBolVO.getDABOLCONSEC() > 0) {

				con = cursor.getConnection();
				// System.out.println("sql " + sql);
				pst = con.prepareStatement(sql);
				posicion = 1;
				pst.setLong(posicion++, daBolVO.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					// System.out.println("Si hay datos");
					cont = true;
				} else {
					// System.out.println("no hay datos");
					cont = false;
				}
			} else
				cont = false;
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(daBolVO.getDABOLCONSEC());
			limpiarTablasDim(daBolVO.getDABOLCONSEC());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(daBolVO.getDABOLCONSEC());
			limpiarTablasDim(daBolVO.getDABOLCONSEC());
			System.out
					.println("BOLETINES: consec:"
							+ daBolVO.getDABOLCONSEC()
							+ "nSe limpiaron las tablas despuns de generada la excepcinn!");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		System.out.println("respuesta cont " + cont);
		return cont;
	}

	/**
	 * 
	 * limpia las tablas de los datos generados por la consulta de generacinn
	 * del reporte
	 * 
	 * @param HttpServletRequest
	 *            request
	 */

	public void limpiarTablasDim(long consec) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN_DIMENSIONES.PK_BOL_DIM_BORRAR_TABLAS(?)}");
			cstmt.setLong(posicion++, consec);
			cstmt.executeUpdate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeCallableStatement(cstmt);
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

	public boolean updateColaCompEstadoCero() throws Exception {
		// System.out.println("RepEstadistico : updateColaCompEstadoCero");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("estadisticos.updateEnCola"));
			pst.setInt(posicion++, ParamsVO.ESTADO_REPORTE_COLA);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);
			pst.setInt(posicion++, ParamsVO.ESTADO_REPORTE_EJE);
			posicion = 1;
			pst.executeUpdate();

		} catch (Exception e) {
			System.out
					.println("REPORTES ESTADISTICOS: nOcurrin Excepcion al actualizar el estado a -1!");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);

			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return true;
	}

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para almacenar
	 * los datos de la consulta requerida por el usuario<BR>
	 * 
	 * @param String
	 *            institucioncod
	 * @param String
	 *            gradocod
	 * @param String
	 *            metodologiacod
	 * @param String
	 *            periodo
	 **/
	public DatosBoletinVO datosConv(DatosBoletinVO rep,
			ReporteEstadisticoVO reporte) {
		// System.out.println("Estadisticos : datosConv " );
		Connection con = null;
		PreparedStatement pst = null;
		Collection list = new ArrayList();
		ResultSet rs = null;
		int posicion = 1;
		int estado = 0;
		try {
			if (rep != null) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb
						.getString("estadisticos.getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, rep.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					rep.setDABOLCONVINST(rs.getString(posicion++));
					rep.setDABOLCONVMEN(rs.getString(posicion++));
				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR SQL: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				ponerReporteMensaje(reporte);
				limpiarTablas(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return rep;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep);
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR EXCP: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				ponerReporteMensaje(reporte);
				limpiarTablas(rep.getDABOLCONSEC());
				limpiarTablasDim(rep.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return rep;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		return rep;
	}// fin de preparedstatements

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

	public boolean updatePuestoReporte(String puesto, String nombrereportezip,
			String user, String prepared) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString(prepared));
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, (puesto));
			pst.setString(posicion++, (nombrereportezip));
			pst.setString(posicion++, (user));
			pst.executeUpdate();
			pst.close();
			con.commit();
			con.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando updatePuestoReporte: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
				// cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
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

	public boolean updatePuestoBoletin(ReporteEstadisticoVO reporteVO) {
		// System.out.println("Estadisticos : updatePuestoBoletin");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("estadistico.update_puesto_boletin"));
			posicion = 1;
			// System.out.println("Nuevo puesto  " + reporteVO.getPuesto() +
			// " para consecutivo " + reporteVO.getConsec());
			if (reporteVO.getPuesto() == -999) {
				// System.out.println("COLOCAR EN BLANCO");
				pst.setNull(posicion++, Types.NUMERIC);
			} else {
				pst.setInt(posicion++, reporteVO.getPuesto());
			}
			pst.setLong(posicion++, reporteVO.getConsec());
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);

			int i = pst.executeUpdate();
			con.commit();
			// if(i == 0){
			// System.out.println("No se actualizo ningun registro");
			// }

			return true;
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
		return false;
	}

	/**
	 * Metodo que actualiza el puesto de los reporte que estan en cola
	 * 
	 * @return
	 */
	public boolean update_cola_boletines() throws Exception {
		// System.out.println(this.getClass() +" : update_cola_boletines ");
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("estadisticos.update_puesto"));
			posicion = 1;
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);
			int i = pst.executeUpdate();
			con.commit();
			// System.out.println("Se actualizaron los puesto de " + i
			// +"reportes ");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
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
	/*
	 * public boolean updatePuestoReporte( ){
	 * System.out.println("updatePuestoReporte"); Connection con=null;
	 * PreparedStatement pst=null; int posicion=1;
	 * 
	 * try{ con=cursor.getConnection();
	 * 
	 * System.out.println("CONSULTA " +
	 * rb.getString("estadisticos.update_puesto_boletin_1"));
	 * pst=con.prepareStatement
	 * (rb.getString("estadisticos.update_puesto_boletin_1")); posicion=1; int i
	 * = pst.executeUpdate(); pst.close(); con.commit();
	 * con.setAutoCommit(true); if(i == 0){
	 * System.out.println("Error no actualizo reporte en cola");
	 * 
	 * } } catch(InternalErrorException in)
	 * {setMensaje("NO se puede estabecer conexinn con la base de datos: "
	 * );return false; }catch(SQLException sqle){
	 * try{con.rollback();}catch(SQLException s){}
	 * setMensaje("Error intentando updatePuestoReporte: "); switch
	 * (sqle.getErrorCode()) { default:
	 * setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n')); }
	 * return false; }finally{ try{ OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(con); // cursor.cerrar(); } catch
	 * (InternalErrorException inte){} } return true; }
	 */

	/**
	 * Funcinn: Ejecuta los prepared statements correspondientes para actualzar
	 * el estado y ponerlo en la cola<BR>
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 **/

	/*
	 * public boolean updateColaReportesEstadoCero(){
	 * System.out.println("updateColaReportesEstadoCero"); Connection con=null;
	 * PreparedStatement pst=null; int posicion=1;
	 * 
	 * try{ con=cursor.getConnection(); pst=con.prepareStatement(rb.getString(
	 * "estadisticos.actualizar_reportes_en_cola")); posicion=1;
	 * pst.executeUpdate(); pst.close(); con.commit(); } catch(Exception e ){
	 * e.printStackTrace(); return false; }finally{ try{
	 * OperacionesGenerales.closeStatement(pst);
	 * OperacionesGenerales.closeConnection(con); }catch(Exception e){} } return
	 * true; }
	 */

	/**
	 * @return
	 */
	public int cola_boletines() throws Exception {
		// System.out.println("Estadisticos : cola_boletines");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int cant = 0;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("estadisticos.puesto_del_boletin"));
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			pst.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);
			rs = pst.executeQuery();

			if (rs.next()) {
				cant = rs.getInt(1);
			}
			rs.close();
			pst.close();
			// System.out.println("PUESTO   " + cant);
		} catch (InternalErrorException e) {
			e.printStackTrace();
			new Exception(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			new Exception(e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
		// System.out.println("RETURN CANT: "+cant);
		return cant;
	}

	/**
	 * @param filVO
	 * @return
	 * @throws Exception
	 */
	public List getListaEstudiante(FiltroCommonVO filVO) throws Exception {
		// System.out.println("RepEstadisticos: getListaEstudiante ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		List lista = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("estadisticos.getListaEstudiante"));
			st.setLong(posicion++, filVO.getFilinst());
			st.setLong(posicion++, filVO.getFilsede());
			st.setLong(posicion++, filVO.getFiljornd());
			st.setLong(posicion++, filVO.getFilmetod());
			st.setLong(posicion++, filVO.getFilgrado());
			st.setLong(posicion++, filVO.getFilgrupo());
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				ItemVO itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setCodigo(rs.getLong(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				lista.add(itemVO);
			}
			// System.out.println("lista " + lista.size());

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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public EstudianteVO getNombreEstudiante(long codEst) throws Exception {
		// System.out.println("BoletinPublico: getNombreEstudiante ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EstudianteVO estudianteVO = new EstudianteVO();
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("estadisticos.getNombreEstudiante"));
			st.setLong(posicion++, codEst);
			// System.out.println("codEst " + codEst);
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				estudianteVO.setEstcodigo(rs.getLong(posicion++));
				estudianteVO.setEstgrupo(rs.getLong(posicion++));
				estudianteVO.setEsttipodoc(rs.getInt(posicion++));
				estudianteVO.setEstnumdoc(rs.getString(posicion++));
				estudianteVO.setEstnombre(rs.getString(posicion++));
			}

			// System.out.println("estudianteVO.setEstnombre( " +
			// estudianteVO.getEstnombre());
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return estudianteVO;
	}

	/**
	 * @function:
	 * @param codInst
	 * @param tipoDoc
	 * @param numDOc
	 * @return
	 * @throws Exception
	 */
	public EstudianteVO getNombreEstudiante(long tipoDoc, String numDOc)
			throws Exception {
		// System.out.println("BoletinPublico: getNombreEstudiante ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		EstudianteVO estudianteVO = new EstudianteVO();
		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNombreEstudiante"));
			st.setString(posicion++, numDOc.trim());
			st.setLong(posicion++, tipoDoc);
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				estudianteVO.setEstcodigo(rs.getLong(posicion++));
				estudianteVO.setEstgrupo(rs.getLong(posicion++));
				estudianteVO.setEstnombre(rs.getString(posicion++));
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return estudianteVO;
	}

	/**
	 * @function: Obtiene los datos de jeraquia deacuerdo con el codigo de grupo
	 *            que esta en estudiante
	 * @param estudianteVO
	 * @return
	 * @throws Exception
	 */
	public FiltroRepEstadisticoVO getDatosJerarquia(
			FiltroRepEstadisticoVO filtroVO, EstudianteVO estudianteVO)
			throws Exception {
		// System.out.println("BoletinPublico: getDatosJerarquia ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getDatosJerarquia"));

			st.setLong(posicion++, estudianteVO.getEstgrupo());
			// System.out.println("estudianteVO.getEstgrupo()  " +
			// estudianteVO.getEstgrupo());

			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				filtroVO.setFilinst(rs.getInt(posicion++));
				filtroVO.setFilsede(rs.getInt(posicion++));
				filtroVO.setFiljornd(rs.getInt(posicion++));
				filtroVO.setFilmetod(rs.getInt(posicion++));
				filtroVO.setFilgrado(rs.getInt(posicion++));
				filtroVO.setFilgrupo(rs.getInt(posicion++));
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtroVO;
	}

	/**
	 * @funtion Metodo que valida si el perido esta cerrado
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public boolean isPerCerrado(FiltroRepEstadisticoVO filtro) throws Exception {
		// System.out.println("Estadisticos: isPerCerrado " );
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		ItemVO itemVO = null;

		List list = new ArrayList();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("estadisticos.isPerCerrado."
					+ filtro.getFilperd()));
			st.setLong(posicion++, filtro.getFilvigencia());
			st.setLong(posicion++, filtro.getFilinst());
			st.setLong(posicion++, filtro.getFilsede());
			st.setLong(posicion++, filtro.getFiljornd());
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
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public ReporteEstadisticoVO getReporteConsecutivo(
			ReporteEstadisticoVO reporteVO) throws Exception {
		// System.out.println("Estadisticos : getReporteConsecutivo ");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {

			cn = cursor.getConnection();

			st = cn.prepareStatement(rb
					.getString("estadisticos.getConsecutivo"));
			st.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcAsig);
			st.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcArea);
			st.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogro);
			st.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcDesct);
			st.setInt(posicion++, ParamsVO.TIPOREP_RepEstdcLogroGrad);
			st.setString(posicion++, reporteVO.getNombre_zip());
			// System.out.println("reporteVO.getNombre_zip() " +
			// reporteVO.getNombre_zip());
			rs = st.executeQuery();

			if (rs.next()) {
				posicion = 1;
				reporteVO.setConsec((rs.getLong(posicion++)));
				// System.out.println("CONSECUTIVO DEL BOLETIN TIPO 8 " +
				// reporteVO.getConsec());
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
			throw new Exception("Error de datos: " + sql.getMessage());

		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());

		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return reporteVO;
	}

}
