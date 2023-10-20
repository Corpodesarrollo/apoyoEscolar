/**
 * 
 */
package siges.gestionAdministrativa.repResultadosAca.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import siges.boletines.ControllerAuditoriaReporte;
import siges.boletines.vo.DatosBoletinVO;
import siges.boletines.vo.DatosComVigAntVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.DataSourceManager;
import siges.dao.OperacionesGenerales;
import siges.evaluacion.beans.NivelEvalVO;
import siges.evaluacion.beans.TipoEvalVO;
import siges.exceptions.InternalErrorException;
import siges.gestionAdministrativa.repResultadosAca.vo.FiltroRendimientoVigenciaAnteriorVO;
import siges.gestionAdministrativa.repResultadosAca.vo.FiltroRepResultadosVO;
import siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO;
import siges.gestionAdministrativa.repResultadosAca.vo.ReporteVO;
import siges.login.beans.Login;

/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class RepResultadosAcaDAO extends Dao {
	private ResourceBundle rb;

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public RepResultadosAcaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAdministrativa.repResultadosAca.bundle.repResultadosAca");
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
	 * @return
	 * @throws Exception
	 */
	public List getLocalidades() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.getLocalidades"));
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre("(" + itemVO.getCodigo() + ") "
						+ rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getColegios(long codLoc, long tipoCol) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.getColegios"));
			st.setLong(posicion++, codLoc);
			st.setLong(posicion++, tipoCol);
			st.setLong(posicion++, tipoCol);
			rs = st.executeQuery();

			while (rs.next()) {
				posicion = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getInt(posicion++));
				itemVO.setNombre(rs.getString(posicion++));
				list.add(itemVO);
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
		return list;
	}

	public FiltroRepResultadosVO paramsInst(FiltroRepResultadosVO filtro) {
		// System.out.println("ENTRO A CARGAR PARAMETROS DE LA INSTITUCION RESULTADOS*** INST: "+filtro.getInst());
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getParametrosInstitucion"));
			long vigenciaActual = getVigenciaInst(filtro.getInst());
			pst.setLong(1, vigenciaActual);
			pst.setLong(2, filtro.getInst());
			rs = pst.executeQuery();
			int f = 0;
			if (rs.next()) {
				f = 1;
				filtro.setNumPer(rs.getLong(f++));
				filtro.setTipoPer(rs.getInt(f++));
				filtro.setNomPerFinal(rs.getString(f++));
				filtro.setNivelEval(rs.getInt(f++));
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			System.out.println("error:: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return filtro;
	}

	public FiltroRendimientoVigenciaAnteriorVO paramsInstconRendVigenciaAnterior(
			FiltroRendimientoVigenciaAnteriorVO filtro) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = DataSourceManager.getConnection(1);
			pst = cn.prepareStatement(rb.getString("getParametrosInstitucion"));
			long vigenciaActual = getVigenciaInst(filtro.getInst());
			pst.setLong(1, vigenciaActual);
			pst.setLong(2, filtro.getInst());
			rs = pst.executeQuery();
			int  f = 0;
			if (rs.next()) {
				f = 1;
				filtro.setNumPer(rs.getLong(f++));
				filtro.setTipoPer(rs.getInt(f++));
				filtro.setNomPerFinal(rs.getString(f++));
				filtro.setNivelEval(rs.getInt(f++));
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			System.out.println("error:: " + e);
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (Exception e) {
			}
		}
		return filtro;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean insertarSolicitud(FiltroRepResultadosVO filtro, Login login)
			throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		java.sql.Timestamp f2;
		
		
		try {
			cn = cursor.getConnection();
			f2 = new java.sql.Timestamp(new java.util.Date().getTime());
			pst = cn.prepareStatement(rb.getString("rep.insertar"));

			posicion = 1;
			pst.clearParameters();
			pst.setLong(posicion++, filtro.getInst());
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, filtro.getPeriodo());
			pst.setString(posicion++, "");
			pst.setString(posicion++, "");
			pst.setString(
					posicion++,
					f2.toString().replace(' ', '_').replace(':', '-')
							.replace('.', '-'));
			pst.setString(posicion++, filtro.getNombre_zip());
			pst.setString(posicion++, filtro.getNombre_pdf());
			pst.setString(posicion++, "");
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

			pst.setString(posicion++, filtro.getInst_nombre());
			pst.setString(posicion++, "");
			pst.setString(posicion++, "");
			pst.setLong(posicion++, getVigenciaInst(filtro.getInst()));

			String resol = getResolInst(filtro.getInst());
			pst.setString(posicion++, resol);

			pst.setLong(posicion++, getNivelEval(filtro.getInst()));
			pst.setLong(posicion++, login.getLogNumPer());
			pst.setString(posicion++, login.getLogNomPerDef());
			pst.setInt(posicion++, -9);
			pst.setInt(posicion++, -9);
			int tipoReporte = 7;
			if (filtro.getTipoReporte() == 1)
				tipoReporte = 7;
			else if (filtro.getTipoReporte() == 2)
				tipoReporte = 8;
			else if (filtro.getTipoReporte() == 3)
				tipoReporte = 9;

			pst.setInt(posicion++, tipoReporte);
			
			if(filtro.isTodas())
				pst.setInt(posicion++, 1); //Generar reporte para todas las localidades
			else
				pst.setInt(posicion++, 0); //Generar reporte solo para el colegio
			
			pst.setLong(posicion++, filtro.getOrden());
			pst.executeUpdate();
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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getResolInst(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String resol = "";
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.GetResolInst"));
			st.setLong(1, inst);
			rs = st.executeQuery();
			if (rs.next())
				resol = rs.getString(1);
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
		return resol;
	}

	public long getNivelEval(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		long codigoNivelEval = -9;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getNivelEval"));
			st.setLong(i++, getVigenciaNumerico());
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				codigoNivelEval = rs.getInt(1);
			}
			rs.close();
			st.close();
			i = 1;

			rs.close();
			st.close();

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
		return codigoNivelEval;
	}

	public void insertarReporte(FiltroRepResultadosVO filtro) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("rep.insertarReporte"));
			String ruta = rb.getString("rep.PathReporte");
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
	 * @return
	 * @throws Exception
	 */
	public DatosBoletinVO getSolicitudes() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_generar"));
			st.clearParameters();
			rs = st.executeQuery();
			int pos = 1;
			if(rs.next()){
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
				db.setDABOLVIGENCIA(rs.getInt(pos++));
				db.setDABOLRESOLUCION(rs.getString(pos++));
				db.setDABOLNIVEVAL(rs.getInt(pos++));
				db.setDABOLSUBTITULO(rs.getString(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDABOLNUMPER(rs.getInt(pos++));
				db.setDABOLNOMPERDEF(rs.getString(pos++));
				db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
				db.setAlllocalidad(rs.getInt(pos++));
				db.setDANE12(rs.getString(pos++));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			db = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			db = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return db;
	}
	
	public String getNotasFromColegio(long conse, long inst){
		String notas = "";
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select distinct A.ARENOMBRE, A.ARECODIGO from BOL_AREA A where A.ARECONSECBOL = "+conse+" and A.ARECODINS = "+inst+" order by ARECODIGO");
			rs = st.executeQuery();
			while (rs.next()) {
				if (notas.length() > 1)
					notas  = notas + " ;" + rs.getString(1);
				else
					notas  = rs.getString(1);
				
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			
		} catch (Exception sqle) {
			sqle.printStackTrace();
			
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return notas;
	}

	public List getSolicitudesResCompViganeterior() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosComVigAntVO db = null;
		List listdb = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("rep.rep_generarDaResCompVig"));
			st.clearParameters();
			rs = st.executeQuery();
			while (rs.next()) {
				int pos = 1;
				db = new DatosComVigAntVO();
				db.setDACOMPANTINST(rs.getLong(pos++));
				db.setDACOMPANTSEDE(rs.getLong(pos++));
				db.setDACOMPANTJORNADA(rs.getLong(pos++));
				db.setDACOMPANTMETODOLOGIA(rs.getLong(pos++));
				db.setDACOMPANTGRADO(rs.getLong(pos++));
				db.setDACOMPANTGRUPO(rs.getLong(pos++));
				db.setDACOMPANTAREA(rs.getLong(pos++));
				db.setDACOMPANTPERIODO(rs.getLong(pos++));
				db.setDACOMPANTRIPTOR(rs.getString(pos++));
				db.setDACOMPANTVIGENCIA(rs.getInt(pos++));
				db.setDACOMPANTFECHA(rs.getString(pos++));
				db.setDACOMPANTFECHAGEN(rs.getString(pos++));
				db.setDACOMPANTFECHAFIN(rs.getString(pos++));
				db.setDACOMPANTNOMBREZIP(rs.getString(pos++));
				db.setDACOMPANTNOMBREPDF(rs.getString(pos++));
				// se utiliza para presentar el estado
				db.setDACOMPANTESTADO(rs.getLong(pos++));
				db.setDACOMPANTUSUARIO(rs.getString(pos++));
				db.setDACOMPANTPUESTO(rs.getLong(pos++));
				db.setDANE(rs.getString(pos++));
				db.setDACOMPANTCONSECUTIVO(rs.getInt(pos++));
				listdb.add(db);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			listdb = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			listdb = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listdb;
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
	public boolean llenarTablas(DatosBoletinVO rep2, ReporteVO rep) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (rep2.getDABOLCONSEC() > 0) {
				con = cursor.getConnection();
				cstmt = con
						.prepareCall("{call PK_RESULTADOS.PK_RES_INICIAL(?)}");
				cstmt.setLong(posicion++, rep2.getDABOLCONSEC());
				cstmt.executeUpdate();
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println("REP RESULTADOS:LLENAR TABLAS: excepcinnSQL: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				rep2.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep2.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep2.getDABOLFECHAGEN(),
						rep2.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(rep2.getDABOLCONSEC());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			System.out.println("REP RESULTADOS:LLENAR TABLAS: excepcinn: "
					+ e.getMessage());
			e.printStackTrace();
			try {
				rep2.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep2.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep2.getDABOLFECHAGEN(),
						rep2.getDABOLFECHAFIN());
				rep.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				rep.setMensaje("ERROR SQL: LLENAR_TABLAS, MOTIVO: "
						+ e.getMessage());
				updateReporte(rep);
				limpiarTablas(rep2.getDABOLCONSEC());
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
	 * @return
	 * @throws Exception
	 */
	public boolean updateSolicitud(long consec, int estado, String fGen,
			String fFin) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		ControllerAuditoriaReporte auditoriaReporte = new ControllerAuditoriaReporte();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.updateDaBol"));
			st.setInt(posicion++, estado);
			st.setString(posicion++, fGen);
			st.setString(posicion++, fFin);
			st.setLong(posicion++, consec);
			st.executeUpdate();
			auditoriaReporte.insertarAuditoria("RESULTADOS ACADEMICOS", 7, "Reporte estado: " + estado,consec);
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
	
	public boolean updateSolicitudNombre(long consec, String Nombre) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.updateDaBolnOMBRE"));
			st.setString(posicion++, Nombre);
			st.setLong(posicion++, consec);
			st.executeUpdate();
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
	 * Se actualiza estado de solicitud de reporte de Comparativo de vigencia
	 * anterior
	 * 
	 * @param estado
	 * @param fGen
	 * @param fFin
	 * @param inst
	 * @param vig
	 * @param periodo
	 * @return
	 * @throws Exception
	 */
	public boolean updateSolicitudCompVigenciaAnt(int estado, String fGen,
			String fFin, long inst, long vig, long periodo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.updateDaResCompVig"));
			st.setInt(posicion++, estado);
			st.setString(posicion++, fGen);
			st.setString(posicion++, fFin);
			st.setLong(posicion++, inst);
			st.setLong(posicion++, vig);
			st.setLong(posicion++, periodo);
			st.executeUpdate();
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
	 * @return
	 * @throws Exception
	 */
	public boolean updateReporte(ReporteVO reporte) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			java.sql.Date fecActual = new java.sql.Date(new Date().getTime());
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.updateReporte"));
			st.setInt(posicion++, reporte.getEstado());
			st.setString(posicion++, reporte.getMensaje());
			st.setDate(posicion++, fecActual);
			st.setString(posicion++, reporte.getUsuario());
			st.setString(posicion++, reporte.getRecurso());
			st.setString(posicion++, reporte.getTipo());
			st.setString(posicion++, reporte.getNombre());
			st.setInt(posicion++, reporte.getModulo());
			st.executeUpdate();
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
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int estado = 0;
		try {
			if (consec > 0) {
				con = cursor.getConnection();
				pst = con
						.prepareStatement(rb.getString("validarEstadoReporte"));
				pst.setLong(1, consec);
				rs = pst.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
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

	public int validarEstadoReporteCompVigAnt(long inst, long vig, long per) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int estado = 0;
		try {
			if (vig > 0) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb
						.getString("validarEstadoReporteCompVigAnt"));
				pst.setLong(1, inst);
				pst.setLong(2, vig);
				pst.setLong(3, per);
				rs = pst.executeQuery();
				if (rs.next())
					estado = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return estado;
		} catch (Exception e) {
			e.printStackTrace();

			System.out
					.println("Hubo error en validacion rep Comp vigencia anteriror");
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
	public boolean validarDatosReporte(DatosBoletinVO rep) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		boolean cont = true;
		try {
			if (rep.getDABOLCONSEC() > 0) {

				String sql = rb.getString("rep.validarDatos");
				con = cursor.getConnection();
				pst = con.prepareStatement(sql);
				posicion = 1;
				pst.setLong(posicion++, rep.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next())
					cont = true;
				else
					cont = false;
			} else
				cont = false;
		} catch (SQLException e) {
			e.printStackTrace();
			limpiarTablas(rep.getDABOLCONSEC());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			limpiarTablas(rep.getDABOLCONSEC());
			System.out
					.println("nSe limpiaron las tablas despuns de generada la excepcinn!");
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
			}
		}
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

	public void limpiarTablas(long consec) {
		Connection con = null;
		CallableStatement cstmt = null;
		int posicion = 1;
		try {
			con = cursor.getConnection();
			cstmt = con
					.prepareCall("{call PK_BOLETIN.PK_BOL_BORRAR_TABLAS(?)}");
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

	public DatosBoletinVO datosConv(DatosBoletinVO rep, ReporteVO reporte) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			if (rep != null) {
				con = cursor.getConnection();
				pst = con.prepareStatement(rb
						.getString("rep.getDatosConvenciones"));
				posicion = 1;
				pst.setLong(1, rep.getDABOLCONSEC());
				rs = pst.executeQuery();
				if (rs.next()) {
					rep.setDABOLCONVINST(rs.getString(posicion++));
					rep.setDABOLCONVMEN(rs.getString(posicion++));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				rep.setDABOLFECHAFIN(new java.sql.Timestamp(System
						.currentTimeMillis()).toString());
				updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR SQL: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
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
				updateSolicitud(rep.getDABOLCONSEC(),
						ParamsVO.ESTADO_REPORTE_NOGEN, rep.getDABOLFECHAGEN(),
						rep.getDABOLFECHAFIN());
				reporte.setEstado(ParamsVO.ESTADO_REPORTE_NOGEN);
				reporte.setMensaje("ERROR EXCP: DATOS CONV, MOTIVO: "
						+ e.getMessage());
				updateReporte(reporte);
				limpiarTablas(rep.getDABOLCONSEC());
				limpiarTablas(rep.getDABOLCONSEC());
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

	public boolean updateEscalaMEN(long consec) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.updateMEN1"));
			st.setLong(posicion++, consec);
			st.executeUpdate();
			st.close();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("rep.updateMEN2"));
			st.setLong(posicion++, consec);
			st.executeUpdate();
			st.close();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("rep.updateMEN3"));
			st.setLong(posicion++, consec);
			st.executeUpdate();
			st.close();

			posicion = 1;
			st = cn.prepareStatement(rb.getString("rep.updateMEN4"));
			st.setLong(posicion++, consec);
			st.executeUpdate();
			st.close();

			cn.commit();
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
	 * Se insertan valores de repororte de comparativo anterior
	 * 
	 * @param filtro
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean insertarSolicitudCompVigenciaAnterior(
			FiltroRendimientoVigenciaAnteriorVO filtro, Login login)
			throws Exception {

		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		java.sql.Timestamp f2;
		try {
			cn = cursor.getConnection();
			f2 = new java.sql.Timestamp(new java.util.Date().getTime());
			pst = cn.prepareStatement(rb.getString("rep.insertarviganterior"));

			posicion = 1;
			pst.clearParameters();

			pst.setLong(posicion++, filtro.getInst());
			pst.setLong(posicion++, Long.parseLong(login.getSedeId()));
			pst.setLong(posicion++, Long.parseLong(login.getJornadaId()));
			pst.setLong(posicion++, Long.parseLong(login.getMetodologiaId()));
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, filtro.getPeriodo());
			pst.setString(posicion++, "");
			pst.setLong(posicion++, filtro.getFilVigencia());
			pst.setString(posicion++, "");
			pst.setString(posicion++, "");
			pst.setString(
					posicion++,
					f2.toString().replace(' ', '_').replace(':', '-')
							.replace('.', '-'));
			pst.setString(posicion++, filtro.getNombre_zip());
			pst.setString(posicion++, filtro.getNombre_pdf());
			pst.setString(posicion++, "-1");
			pst.setString(posicion++, login.getUsuarioId());
			pst.setLong(posicion++, -9);
			pst.setLong(posicion++, filtro.getConsec());

			pst.executeUpdate();

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
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Inserta registro de reporte de comparacinn vigencia anterior.
	 * 
	 * @param filtro
	 */
	public void insertarReporteCompVigenciaAnterior(
			FiltroRendimientoVigenciaAnteriorVO filtro) {
		Connection con = null;
		PreparedStatement pst = null;
		int posicion = 1;

		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb.getString("rep.insertarReporte"));
			String ruta = rb.getString("rep.PathReporte");
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, filtro.getUsuario());
			pst.setString(posicion++, ruta + filtro.getNombre_zip());
			pst.setString(posicion++, "zip");
			pst.setString(posicion++, filtro.getNombre_zip());
			pst.setString(posicion++, ParamsVO.REP_MODULO_COMPVIGENCIAANT);
			pst.setString(posicion++, "" + ParamsVO.ESTADO_REPORTE_COLA);
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
	 * Realiza el proceso de carga de información del reporte de comparacinn de
	 * vigencia anterior.
	 * 
	 * @param request
	 * @param session
	 * @param usuVO
	 * @param filtroVO
	 */
	public void cargarDatosReporte(HttpServletRequest request,
			HttpSession session, Login usuVO,
			FiltroRendimientoVigenciaAnteriorVO filtroVO) {
		ArrayList valores = new ArrayList();
		ArrayList valoresfinal = new ArrayList();

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;

		try {

			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("rep.calculopromediogradoxper"
							+ filtroVO.getPeriodo()));
			posicion = 1;
			pst.setLong(posicion++, filtroVO.getFilVigencia());
			pst.setLong(posicion++, Long.parseLong(usuVO.getInstId()));

			rs = pst.executeQuery();

			while (rs.next()) {
				String datos[] = new String[3];
				datos[0] = rs.getString(1);
				datos[1] = rs.getString(2);
				datos[2] = rs.getString(3);

				valores.add(datos);
			}
			int gradoincial = 0;
			double total = 0;
			double totalins = 0;
			double alto = 0;
			double basico = 0;
			double superior = 0;
			double bajo = 0;
			double tas = 0;
			double altoins = 0;
			double basicoins = 0;
			double superiorins = 0;
			double bajoins = 0;
			double tasins = 0;

			for (int i = 0; i < valores.size(); i++) {
				String[] datos = (String[]) valores.get(i);
				if (String.valueOf(gradoincial).equals(datos[1])) {
					total = total + Integer.parseInt(datos[2]);
					if (datos[0].trim().equals("ALTO")) {
						alto = Integer.parseInt(datos[2]);
						altoins = altoins + Integer.parseInt(datos[2]);
						totalins = totalins + Integer.parseInt(datos[2]);
					}
					if (datos[0].trim().equals("SUPERIOR")) {
						superior = Integer.parseInt(datos[2]);
						superiorins = superiorins + Integer.parseInt(datos[2]);
						totalins = totalins + Integer.parseInt(datos[2]);
					}
					tas = alto + superior;
					if (datos[0].trim().equals("BAJO")) {
						bajo = Integer.parseInt(datos[2]);
						bajoins = bajoins + Integer.parseInt(datos[2]);
						totalins = totalins + Integer.parseInt(datos[2]);
					}
					if (datos[0].trim().equals("BASICO")) {
						basico = Integer.parseInt(datos[2]);
						basicoins = basicoins + Integer.parseInt(datos[2]);
						totalins = totalins + Integer.parseInt(datos[2]);
					}

					if (i == (valores.size() - 1)) {
						String[] datosfinal = new String[8];

						datosfinal[0] = String.valueOf(filtroVO
								.getFilVigencia());
						datosfinal[1] = String.valueOf(filtroVO.getPeriodo());
						datosfinal[2] = usuVO.getInstId();
						datosfinal[3] = String.valueOf(gradoincial);
						if (total == 0.0)
							total = 1;
						datosfinal[4] = String.valueOf(((tas) * 100) / total);
						if (datosfinal[4] == "NaN")
							datosfinal[4] = "0";

						// medio
						datosfinal[5] = String.valueOf((basico * 100) / total);
						if (datosfinal[5] == "NaN")
							datosfinal[5] = "0";
						// bajo
						datosfinal[6] = String.valueOf(((bajo) * 100) / total);
						if (datosfinal[6] == "NaN")
							datosfinal[6] = "0";
						datosfinal[7] = String.valueOf(filtroVO.getConsec());

						valoresfinal.add(datosfinal);

					}

				} else {

					String[] datosfinal = new String[8];

					datosfinal[0] = String.valueOf(filtroVO.getFilVigencia());
					datosfinal[1] = String.valueOf(filtroVO.getPeriodo());
					datosfinal[2] = usuVO.getInstId();
					datosfinal[3] = String.valueOf(gradoincial);
					// super alto
					// superior
					if (total == 0.0)
						total = 1;
					datosfinal[4] = String.valueOf((tas * 100) / total);
					if (datosfinal[4] == "NaN")
						datosfinal[4] = "0";
					// medio
					datosfinal[5] = String.valueOf((basico * 100) / total);
					if (datosfinal[5] == "NaN")
						datosfinal[5] = "0";
					// bajo
					datosfinal[6] = String.valueOf(((bajo) * 100) / total);
					if (datosfinal[6] == "NaN")
						datosfinal[6] = "0";
					datosfinal[7] = String.valueOf(filtroVO.getConsec());

					valoresfinal.add(datosfinal);

					gradoincial = Integer.parseInt(datos[1]);
					i = i - 1;
					total = 0;

					alto = 0;
					basico = 0;

					superior = 0;
				}

			}

			rs.close();
			pst.close();

			for (int i = 0; i < valoresfinal.size(); i++) {
				String[] datos = (String[]) valoresfinal.get(i);
				System.out.println("vig " + datos[0] + " " + datos[1] + " "
						+ datos[2] + "grado " + datos[3] + " " + datos[4] + " "
						+ datos[5] + " " + datos[6] + " " + datos[7]);
				pst = con.prepareStatement(rb
						.getString("rep.insertarviganteior"));
				posicion = 1;
				pst.clearParameters();
				pst.setString(posicion++, datos[0]);
				pst.setString(posicion++, datos[1]);
				pst.setString(posicion++, datos[2]);
				pst.setString(posicion++, datos[3]);
				pst.setString(posicion++, datos[4]);
				pst.setString(posicion++, datos[5]);
				pst.setString(posicion++, datos[6]);
				pst.setString(posicion++, datos[7]);
				pst.executeUpdate();
				con.commit();
			}
			if (totalins == 0.0)
				totalins = 1;
			tasins = altoins + superiorins;
			pst = con.prepareStatement(rb.getString("rep.insertarviganteior"));
			posicion = 1;
			posicion = 1;
			pst.clearParameters();
			pst.setString(posicion++, String.valueOf(filtroVO.getFilVigencia()));
			pst.setString(posicion++, String.valueOf(filtroVO.getPeriodo()));
			pst.setString(posicion++, usuVO.getInstId());
			pst.setString(posicion++, "9999");
			pst.setString(posicion++, String.valueOf((tasins * 100) / totalins));
			pst.setString(posicion++,
					String.valueOf((basicoins * 100) / totalins));
			pst.setString(posicion++,
					String.valueOf(((bajoins) * 100) / totalins));
			pst.setString(posicion++, String.valueOf(filtroVO.getConsec()));
			pst.executeUpdate();
			con.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			System.out.println("error " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error " + e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(con);
			} catch (Exception e) {
				System.out.println("error " + e.getMessage());
			}
		}

	}

	public String getNombreColegio(long idinst) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getnombredecolegio"));
			st.setLong(1, idinst);

			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
		return "No registrado";
	}

	public void eliminarregistrosreporteGenerado(int consecutivo) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = cursor.getConnection();
			pst = con.prepareStatement(rb
					.getString("rep.eliminarregsitrosviganterior"));

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

	public int obtenersecuenciaParaReporteCompVigAnteriror() throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select SEC_COMPVIGANT.NEXTVAL from dual");

			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;

	}

	/**
	 * Conteo registros para reporte
	 * 
	 * @return
	 * @throws Exception
	 */
	public int contarRegistrosReporte(long vigencia, long idinst, int periodo,
			int consecutivo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		int posicion = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("compvig.obtenertotalregistros"));
			st.setLong(posicion++, vigencia);
			st.setInt(posicion++, periodo);
			st.setLong(posicion++, idinst);
			st.setInt(posicion++, consecutivo);

			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}

	public List getNombeMaterias(long dabolconsec) {

			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			List list = new ArrayList();
			ItemVO itemVO = null;
			int posicion = 1;
			try {
				cn = cursor.getConnection();
				st = cn.prepareStatement("select distinct A.ARECODIGO, A.ARENOMBRE from BOL_AREA A where A.ARECONSECBOL = "+dabolconsec+" order by ARECODIGO");
				rs = st.executeQuery();
				while (rs.next()) {
					posicion = 1;
					itemVO = new ItemVO();
					itemVO.setCodigo(rs.getInt(posicion++));
					itemVO.setNombre(rs.getString(posicion++));
					list.add(itemVO);
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception sqle) {
				sqle.printStackTrace();
			} finally {
				try {
					OperacionesGenerales.closeResultSet(rs);
					OperacionesGenerales.closeStatement(st);
					OperacionesGenerales.closeConnection(cn);
				} catch (InternalErrorException inte) {
				}
			}
			return list;

	}
	
	public ResultSet getNotasEstudiante(long consec, long codest) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select A.ARECODEST, A.ARECODIGO, A.ARENOMBRE,A.EVAAREEVAL7,A.EVAAREMEN From BOL_AREA A where A.ARECONSECBOL = "+consec +" and A.ARECODEST= "+codest+" order by ARECODIGO;");
			rs = st.executeQuery();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return rs;
	}
	
	public ResultSet getEstudiantes(long consec) {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select distinct EST.ESTCODIGO,SEDDANE,SEDNOMBRE, JORNOMBRE, GRACODIGO, EST.NOMTIPODOC||'. '||EST.ESTNUMDOC as DOC, ESTNOMBRE1, EST.ESTNOMBRE2, EST.ESTAPELLIDO1, EST.ESTAPELLIDO2, EST.GRANOMBRE, EST.NOMDIRGRUPO,GRUCODIGO from BOL_ESTUDIANTE EST where EST.ESTCONSECBOL = "+consec+" order by SEDNOMBRE, JORNOMBRE,GRACODIGO, GRUCODIGO,EST.ESTAPELLIDO1, EST.ESTAPELLIDO2, ESTNOMBRE1, EST.ESTNOMBRE2,ESTCODIGO");
			rs = st.executeQuery();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return rs;

}

	public String getArchivoListos() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String listaReportesListos = "";
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_temp_listo"));
			rs = st.executeQuery();
			long totalOrden = 0;
			long countOrden = 0;
			String nombreReporte = "";
			while (rs.next()) {
				if(totalOrden == 0){
					nombreReporte = rs.getString(12);
					totalOrden = rs.getLong(33);
					countOrden++;
					listaReportesListos += rs.getLong(20) + ";";
				} else {
					if(nombreReporte.equals(rs.getString(12))){
						countOrden++;
						listaReportesListos += rs.getLong(20) + ";";
					}
				}
			}
			if(countOrden == totalOrden && countOrden > 0){//Si estan listos todos los reportes de esa solicitud
				return listaReportesListos;
			} else {
				return null;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return null;
	}
	
	public String getArchivoListoCol() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String listaReportesListos = "";
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_temp_listo_col"));
			rs = st.executeQuery();
			if (rs.next()) {
				listaReportesListos = String.valueOf(rs.getLong(20));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaReportesListos;
	}
	
	public List getArchivoListoByConsec(long consec) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		List listdb = new ArrayList();
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_temp_listo2"));
			st.setLong(1, consec);
			rs = st.executeQuery();
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
					db.setDABOLVIGENCIA(rs.getInt(pos++));
					db.setDABOLRESOLUCION(rs.getString(pos++));
					db.setDABOLNIVEVAL(rs.getInt(pos++));
					db.setDABOLSUBTITULO(rs.getString(pos++));
					db.setDANE(rs.getString(pos++));
					db.setDABOLNUMPER(rs.getInt(pos++));
					db.setDABOLNOMPERDEF(rs.getString(pos++));
					db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
					db.setAlllocalidad(rs.getInt(pos++));
					db.setDABOLORDEN(rs.getString(pos++));
					db.setDABOLESTACT(rs.getInt(pos++));
					listdb.add(db);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			listdb = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			listdb = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listdb;
	}
	
		Connection cn = null;
		public DatosBoletinVO getArchivoListoByConsecCol(long consec) {
		PreparedStatement st = null;
		ResultSet rs = null;
		DatosBoletinVO db = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_temp_listo2"));
			st.setLong(1, consec);
			rs = st.executeQuery();
			if (rs.next()) {
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
					db.setDABOLVIGENCIA(rs.getInt(pos++));
					db.setDABOLRESOLUCION(rs.getString(pos++));
					db.setDABOLNIVEVAL(rs.getInt(pos++));
					db.setDABOLSUBTITULO(rs.getString(pos++));
					db.setDANE(rs.getString(pos++));
					db.setDABOLNUMPER(rs.getInt(pos++));
					db.setDABOLNOMPERDEF(rs.getString(pos++));
					db.setDABOLTIPOEVALPREES(rs.getInt(pos++));
					db.setAlllocalidad(rs.getInt(pos++));
					db.setDABOLORDEN(rs.getString(pos++));
					db.setDABOLESTACT(rs.getInt(pos++));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			db = null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			db = null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return db;
	}

	/**
	 * Retorna el listado de colegios de una localidad dada un solo colegio que pertenesca a esa localidad
	 * @param inst
	 * @return
	 */
	public String getColegiosFromLocalidad(long localidad, long vigencia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String r = ""; 
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.colegionlocalidad"));
			st.setLong(1, localidad);
			st.setLong(2, vigencia);
			st.setLong(3, vigencia);
			rs = st.executeQuery();
			while (rs.next()) {
				r += rs.getLong(1) + ";";
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	/**
	 * Elimina un registro de datos boletin dado el consecutivo
	 * @param parseLong
	 */
	public void deleteReporte(long parseLong) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.deleteRep"));
			st.setLong(1, parseLong);
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
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
	 * Consulta si existen solicitudes en estado 0 (Procesando por ORACLe)
	 * Si existen mas de $parameter en procesando, no realice mns solicitudes 
	 * @return
	 */
	public boolean solicitudesPendientes() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("rep.rep_generando"));
			rs = st.executeQuery();
			int counter = 0;
			while (rs.next()) {
				counter++;
				if(counter >= 5)
					return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
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
	



}
