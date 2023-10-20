/**
 * 
 */
package poa.consulta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.consulta.vo.FiltroConsultaVO;

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
public class ConsultaDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto de obtencinn de conexiones
	 */
	public ConsultaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.consulta.bundle.Consulta");
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
			st = cn.prepareStatement(rb.getString("consulta.listaLocalidad"));
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
	public List getListaColegio(int loc) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("consulta.listaColegio"));
			st.setInt(i++, loc);
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
			pst = cn.prepareStatement(rb.getString("consulta.listaVigencia"));
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
			st = cn.prepareStatement(rb.getString("consulta.getEstado"));
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
				st = cn.prepareStatement(rb.getString("consulta.getEstadoSED"));
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
			st = cn.prepareStatement(rb.getString("consulta.getDane"));
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
}
