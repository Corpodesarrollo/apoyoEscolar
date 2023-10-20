package poa.reportes.dao;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		04/12/2019	JORGE CAMACHO	Se agregaron los métodos getListaPeriodos() y convertirFecha() para la creaci�n din�mica de los periodos de los reportes

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.consulta.vo.FiltroConsultaVO;
import poa.reportes.vo.FechasSegColegioVO;
import poa.reportes.vo.FiltroReportesVO;
import poa.reportes.vo.ParamsVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el proceso de consulta 15/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class ReportesDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto de obtencinn de conexiones
	 */
	public ReportesDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.reportes.bundle.reportes");
	}

	/**
	 * Calcula la lista de Localidades
	 * 
	 * @return lista de localidades
	 * @throws Exception
	 */
	public List getListaLocalidad() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("reportes.listaLocalidad"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Calcula la lista de colegios de la localidad
	 * 
	 * @param loc
	 *            codigo de la localidad
	 * @return Lista de colegios de la localidad indicada
	 * @throws Exception
	 */
	public List getListaColegio(int loc, int vig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("reportes.listaColegio"));
			st.setInt(i++, loc);
			st.setInt(i++, vig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Calcula la lista de vigencias desde la anterior a la posterior a la
	 * actual
	 * 
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual() throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		List listaVigencia = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("reportes.listaVigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				itemVO = new ItemVO(rs.getInt(1), String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(2), String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(3), String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * Calcula la lista de areas de gestion
	 * 
	 * @return lista de localidades
	 * @throws Exception
	 */
	public List getListaAreasGestion() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("consulta.listaAreas"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Calcula la lista de Lineas de accion segun area de gestion
	 * 
	 * @return lista de localidades
	 * @throws Exception
	 */
	public List getListaLineasAccion(int are) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("consulta.listaLineasAccion"));
			i = 1;
			st.setInt(i++, are);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Calcula la lista de fuentes de financiacion
	 * 
	 * @return lista de localidades
	 * @throws Exception
	 */
	public List getListaFuentesFin() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("consulta.listaFuentes"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Lista de actividades del POA
	 * @throws Exception
	 */
	public List getListaPOA(FiltroConsultaVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		int i = 0;
		try {
			cn = cursor.getConnection();
			// saber si se puede aprobar
			st = cn.prepareStatement(rb.getString("reportes.getEstado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_SED) {
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO) {
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					} else {
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					}
				} else {
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
				}
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
			}
			rs.close();
			st.close();
			// calculo de la lista
			if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
					|| filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_SED) {
				l.add(new ItemVO(0, "Archivo.xls"));
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
		return l;
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
	 * Valida que los parametros del filtro cumpla las validaciones
	 * 
	 * @param filtro
	 *            FIltro de busqueda de actividades
	 * @return false=no cumple; true=si cumple
	 * @throws Exception
	 */
	public boolean validarDatos(FiltroConsultaVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			// saber si es solo por colegio
			if (filtro.getFilNivel() < 4) {// es admnistrador
				// validar si hay al menos un colegio con estado aprobado col o
				// aprobado sed
				st = cn.prepareStatement(rb.getString("reportes.getEstadoSED"));
				i = 1;
				st.setInt(i++, filtro.getFilVigencia());
				st.setLong(i++, filtro.getFilInstitucion());
				st.setLong(i++, filtro.getFilInstitucion());
				st.setInt(i++, filtro.getFilLocalidad());
				st.setInt(i++, filtro.getFilLocalidad());
				st.setInt(i++, ParamPOA.ESTADO_POA_APROBADO_COLEGIO);
				st.setInt(i++, ParamPOA.ESTADO_POA_APROBADO_SED);
				rs = st.executeQuery();
				if (!rs.next()) {
					return false;
				}
			} else {// es colegio
					// validar que tenga actividades
				st = cn.prepareStatement(rb
						.getString("consulta.getActividadColegio"));
				i = 1;
				st.setInt(i++, filtro.getFilVigencia());
				st.setLong(i++, filtro.getFilInstitucion());
				st.setInt(i++, filtro.getFilVigencia());
				st.setLong(i++, filtro.getFilInstitucion());
				rs = st.executeQuery();
				if (!rs.next()) {
					return false;
				}
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
		return true;
	}

	/**
	 * Calcula el codigo DANE del colegio indicado
	 * 
	 * @param inst
	 *            codigo de la institucion
	 * @return codigo DANE del colegio
	 * @throws Exception
	 */
	public String getDane(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("reportes.getDane"));
			st.setLong(i++, inst);
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
		return null;
	}
	
	public String getFechaAprobacionPoa(int vigencia, long filInstitucion) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("reportes.getFechaAprobacionPoa"));
			st.setInt(i++,vigencia);
			st.setLong(i++,filInstitucion); 
			
			rs=st.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return null;
	}
	
	public Date getFechaHastaPlaneacion(int vigencia) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("reportes.getFechaHastaPlaneacion"));
			st.setInt(i++,vigencia);			
			rs=st.executeQuery();
			if(rs.next()){
				return rs.getDate(1);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return null;
	}
	
	public FechasSegColegioVO getFechaFinSeguimiento(int vigencia) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		FechasSegColegioVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("reportes.getFechasSeguimiento"));
			i=1;
			st.setInt(i++,vigencia);
			
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new FechasSegColegioVO();
				item.setProgFechaFin1(rs.getDate(i++)); 
				item.setProgFechaFin2(rs.getDate(i++)); 
				item.setProgFechaFin3(rs.getDate(i++)); 
				item.setProgFechaFin4(rs.getDate(i)); 				

			}
			rs.close();
			st.close();
			
			if(item.getProgFechaFin1() == null && item.getProgFechaFin2() == null 
					&& item.getProgFechaFin3() == null && item.getProgFechaFin4() == null){
				item = null;				
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}
	
	public FiltroReportesVO getUltimaFechaModificacion(int vigencia, long filInstitucion) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		FiltroReportesVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("reportes.getUltimaFechaModificacion"));
			i=1;
			st.setInt(i++,vigencia);		
			st.setLong(i++,filInstitucion);			
			
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new FiltroReportesVO();
				item.setFechaModificacion1(rs.getString(i++));
				item.setFechaModificacion2(rs.getString(i++));
				item.setFechaModificacion3(rs.getString(i++));
				item.setFechaModificacion4(rs.getString(i));	

			}
			rs.close();
			st.close();
			
			if(item.getFechaModificacion1() == null && item.getFechaModificacion2() == null 
					&& item.getFechaModificacion3() == null && item.getFechaModificacion4() == null){
				item = null;				
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	/**
	 * Calcula el codigo DANE del colegio indicado
	 * 
	 * @param inst
	 *            codigo de la institucion
	 * @return codigo DANE del colegio
	 * @throws Exception
	 */
	public String getNombreLocalidad(int loc) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			if (loc >= 0) {
				cn = cursor.getConnection();
				st = cn.prepareStatement(rb.getString("reportes.getNombreLoc"));
				st.setInt(i++, loc);
				rs = st.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} else {
				return "Todas";
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
		return null;
	}

	/**
	 * Calcula el codigo DANE del colegio indicado
	 * 
	 * @param inst
	 *            codigo de la institucion
	 * @return codigo DANE del colegio
	 * @throws Exception
	 */
	public String getNombreInsititucion(int loc, long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			if (loc >= 0) {
				cn = cursor.getConnection();
				st = cn.prepareStatement(rb
						.getString("reportes.getNombreInstitucion"));
				st.setInt(i++, loc);
				st.setLong(i++, inst);
				rs = st.executeQuery();
				if (rs.next()) {
					return rs.getString(1);
				}
			} else {
				return null;
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
		return null;
	}

	/**
	 * Calcula la lista de Lineas de accion segun area de gestion
	 * 
	 * @return lista de localidades
	 * @throws Exception
	 */
	public void llenaTablaTmpReporte10(int vig, int loc, long inst, String usu)
			throws Exception {
		// int vig2=-99;
		int loc2 = -99;
		long inst2 = -99;
		if (loc > 0) {
			loc2 = loc;
		}
		if (inst > 0) {
			inst2 = inst;
		}
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		// List l=new ArrayList();
		int i = 0;
		try {
			// DELETE TABLA TEMPORAL
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("reportes.deleteTmpRep"));
			st.executeUpdate();
			st.close();
			// FIN DELETE TABLA TEMPORAL

			// INSERT TABLA TEMPORAL REPORTE ESTADOS META
			String sql = rb.getString("reportes.insert1");
			sql = sql + " " + rb.getString("reportes.insert2");
			sql = sql + " " + rb.getString("reportes.insert3");
			sql = sql + " " + rb.getString("reportes.insert4");
			sql = sql + " " + rb.getString("reportes.insert5");
			sql = sql + " " + rb.getString("reportes.insert6");
			sql = sql + " " + rb.getString("reportes.insert7");
			sql = sql + " " + rb.getString("reportes.insert8");
			sql = sql + " " + rb.getString("reportes.insert9");
			sql = sql + " " + rb.getString("reportes.insert10");
			sql = sql + " " + rb.getString("reportes.insert11");
			sql = sql + " " + rb.getString("reportes.insert12");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setString(i++, usu);
			st.setInt(i++, vig);
			st.setLong(i++, inst2);
			st.setLong(i++, inst2);
			st.setInt(i++, loc2);
			st.setInt(i++, loc2);
			st.executeUpdate();
			st.close();
			// System.out.println("REPORTE INSERT DAO ******");
			// RELLENO DE CEROS PARA LOS DEMAS ESTADOS
			String sql2 = rb.getString("reportes.insert2_1");
			sql2 = sql2 + " " + rb.getString("reportes.insert2_2");
			sql2 = sql2 + " " + rb.getString("reportes.insert2_3");
			i = 1;
			st = cn.prepareStatement(sql2);
			st.setString(i++, usu);
			st.executeUpdate();
			st.close();

			/*
			 * rs=st.executeQuery(); while(rs.next()){ i=1; item=new ItemVO();
			 * item.setCodigo(rs.getLong(i++));
			 * item.setNombre(rs.getString(i++)); l.add(item); }
			 */
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
		// return l;
	}

	public List getlistaObjetivos() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.getlistaObjetivos"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				// item.setPadre(rs.getLong(i++));
				l.add(item);
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
		return l;
	}

	/**
	 * Valida que los parametros del filtro cumpla las validaciones
	 * 
	 * @param filtro
	 *            FIltro de busqueda de actividades
	 * @return false=no cumple; true=si cumple
	 * @throws Exception
	 */
	public boolean validarDatosPOA(FiltroReportesVO filtro) throws Exception {
		// System.out.println("APOY_ POA: ENTRO VALDIAR DATOS POA PARA REPORTES ***************************************FEB INST:"+filtro.getFilInstitucion()+"    VIGENCIA:"+filtro.getFilVigencia());
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();

			// validar si hay al menos un colegio con estado aprobado col o
			// aprobado sed
			st = cn.prepareStatement(rb.getString("reportes.validarPOA"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			// System.out.println("APOYO POA: ENCONTRO REGISTRO EN POA PARA REPORTES ************************************RS: "+rs.next());
			if (rs.next()) {
				// System.out.println("APOYO POA: ENCONTRO REGISTRO EN POA PARA REPORTES ************************************RS: "+rs.getString(1));
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
		// System.out.println("APOYO POA:NOOOOOOOOOOOOOOOOOOOOO ENCONTRO REGISTRO EN POA PARA REPORTES ************************************");
		return false;
	}
	
	
	/**
	 * Calcula la lista de los periodos a los que se les ha hecho seguimiento  
	 * @return Lista de periodos con seguimiento
	 * @throws Exception
	 */
	public List getListaPeriodos(int vigencia) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		
		List listaPeriodos = new ArrayList();
		
		Date fecha = new Date();
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		int anho = Integer.parseInt(yearFormat.format(fecha));
		
		try {
			
			if (vigencia > 0) {
				
				if (vigencia == anho) {
					
					cn = cursor.getConnection();
					pst = cn.prepareStatement(rb.getString("reportes.fechasSeguimiento"));
					
					pst.setInt(1, vigencia);
					
					rs = pst.executeQuery();
					
					if(rs.next()) {
						
						Date dateI1 = new SimpleDateFormat("dd/MM/yyyy").parse(convertirFecha(rs.getString(1)));
						Date dateI2 = new SimpleDateFormat("dd/MM/yyyy").parse(convertirFecha(rs.getString(2)));
						Date dateI3 = new SimpleDateFormat("dd/MM/yyyy").parse(convertirFecha(rs.getString(3)));
						Date dateI4 = new SimpleDateFormat("dd/MM/yyyy").parse(convertirFecha(rs.getString(4)));
						
						if (fecha.after(dateI1)) {
							ItemVO item2VO = new ItemVO(ParamsVO.PERIODO_PRIMERO, ParamsVO.PERIODO_PRIMERO_);
							listaPeriodos.add(item2VO);
						}
						
						if (fecha.after(dateI2)) {
							ItemVO item2VO = new ItemVO(ParamsVO.PERIODO_SEGUNDO, ParamsVO.PERIODO_SEGUNDO_);
							listaPeriodos.add(item2VO);
						}

						if (fecha.after(dateI3)) {
							ItemVO item2VO = new ItemVO(ParamsVO.PERIODO_TERCERO, ParamsVO.PERIODO_TERCERO_);
							listaPeriodos.add(item2VO);
						}
						
						if (fecha.after(dateI4)) {
							ItemVO item2VO = new ItemVO(ParamsVO.PERIODO_CUARTO, ParamsVO.PERIODO_CUARTO_);
							listaPeriodos.add(item2VO);
						}
						
					}
					
				} else {
				
					ItemVO item2VO = new ItemVO(ParamsVO.PERIODO_PRIMERO, ParamsVO.PERIODO_PRIMERO_);
					listaPeriodos.add(item2VO);
					
					item2VO = new ItemVO(ParamsVO.PERIODO_SEGUNDO, ParamsVO.PERIODO_SEGUNDO_);
					listaPeriodos.add(item2VO);
					
					item2VO = new ItemVO(ParamsVO.PERIODO_TERCERO, ParamsVO.PERIODO_TERCERO_);
					listaPeriodos.add(item2VO);
					
					item2VO = new ItemVO(ParamsVO.PERIODO_CUARTO, ParamsVO.PERIODO_CUARTO_);
					listaPeriodos.add(item2VO);
					
				}
				
			}
			
		} catch(Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch(InternalErrorException inte){}
		}
		
		return listaPeriodos;
		
	}
	
	
	public String convertirFecha(String strFecha) {
		
		String fecha = strFecha.substring(8,10) + "/" + strFecha.substring(5, 7) + "/" + strFecha.substring(0, 4);
		
		return fecha;
		
	}
	
}
