/**
 * 
 */
package siges.indicadores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.indicadores.vo.DescriptorVO;
import siges.indicadores.vo.FiltroDescriptorVO;
import siges.indicadores.vo.FiltroLogroVO;
import siges.indicadores.vo.LogroVO;

/**
 * 22/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class IndicadoresDAO extends Dao {
	public ResourceBundle rb;

	public IndicadoresDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.indicadores.bundle.Indicadores");
	}

	public List getListaPeriodo() throws Exception {
		List l = new ArrayList();
		ItemVO item = null;
		item = new ItemVO(Params.PERIODO_PRIMERO, Params.PERIODO_PRIMERO_);
		l.add(item);
		item = new ItemVO(Params.PERIODO_SEGUNDO, Params.PERIODO_SEGUNDO_);
		l.add(item);
		item = new ItemVO(Params.PERIODO_TERCERO, Params.PERIODO_TERCERO_);
		l.add(item);
		item = new ItemVO(Params.PERIODO_CUARTO, Params.PERIODO_CUARTO_);
		l.add(item);
		item = new ItemVO(Params.PERIODO_QUINTO, Params.PERIODO_QUINTO_);
		l.add(item);
		return l;
	}

	public List getListaPeriodo(long numPer) throws Exception {
		List l = new ArrayList();
		ItemVO item = null;
		// System.out.println("ENTRO A CARGAR PERIODOS ** "+numPer);
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		return l;
	}

	public List getListaTipoDescriptor() throws Exception {
		List l = new ArrayList();
		ItemVO item = null;
		item = new ItemVO(Params.TIPO_DESCRIPTOR_FORTALEZA,
				Params.TIPO_DESCRIPTOR_FORTALEZA_);
		l.add(item);
		item = new ItemVO(Params.TIPO_DESCRIPTOR_DIFICULTAD,
				Params.TIPO_DESCRIPTOR_DIFICULTAD_);
		l.add(item);
		item = new ItemVO(Params.TIPO_DESCRIPTOR_RECOMENDACION,
				Params.TIPO_DESCRIPTOR_RECOMENDACION_);
		l.add(item);
		item = new ItemVO(Params.TIPO_DESCRIPTOR_ESTRATEGIA,
				Params.TIPO_DESCRIPTOR_ESTRATEGIA_);
		l.add(item);
		return l;
	}

	public List getListaMetodologia(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.listaMetodologia"));
			st.setLong(i++, inst);
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

	public List getListaGrado(long inst, int metod) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.listaGrado"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
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

	public List getListaAsignatura(long inst, int metod, int vig, int grado)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.listaAsignatura"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, vig);
			st.setInt(i++, grado);
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

	public List getListaArea(long inst, int metod, int vig, int grado)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("descriptor.listaArea"));
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, vig);
			st.setInt(i++, grado);
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

	public List getListaDocenteAsignatura(long inst, int metod, int vig,
			int grado, long asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("logro.listaDocenteAsignatura"));
			st.setInt(i++, vig);
			st.setLong(i++, asig);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
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

	public List getListaDocenteArea(long inst, int metod, int vig, int grado,
			long area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("descriptor.listaDocenteArea"));
			st.setInt(i++, vig);
			st.setLong(i++, area);
			st.setLong(i++, inst);
			st.setInt(i++, metod);
			st.setInt(i++, grado);
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

	public List getListaLogro(FiltroLogroVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		LogroVO item = null;
		int i = 1;
		long codigoJerarquia = 0;
		int cons = 1;
		try {
			if (filtro == null)
				return null;
			cn = cursor.getConnection();
			filtro.setFilInHabilitado(0);
			if (filtro.getFilPlanEstudios() == 1
					&& filtro.getFilNivelPerfil() > 4) {
				if (filtro.getFilUsuario() != filtro.getFilDocente()) {
					filtro.setFilInHabilitado(1);
				}
			}
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("logro.codigoJerarquia"));
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setLong(i++, filtro.getFilAsignatura());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoJerarquia = rs.getLong(1);
			}
			rs.close();
			st.close();
			String sql = rb.getString("logro.lista.0");
			if (filtro.getFilPlanEstudios() == 1) {
				sql += " " + rb.getString("logro.lista.1");
			}
			sql += " " + rb.getString("logro.lista.2");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, codigoJerarquia);
			st.setInt(i++, filtro.getFilVigencia());
			if (filtro.getFilPlanEstudios() == 1) {
				st.setLong(i++, filtro.getFilDocente());
			}
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new LogroVO();
				item.setLogConsecutivo(cons++);
				item.setLogCodigoJerarquia(rs.getLong(i++));
				item.setLogCodigo(rs.getLong(i++));
				item.setLogVigencia(rs.getInt(i++));
				item.setLogNombre(rs.getString(i++));
				item.setLogAbreviatura(rs.getString(i++));
				item.setLogOrden(rs.getInt(i++));
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

	public List getListaDescriptor(FiltroDescriptorVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		DescriptorVO item = null;
		int i = 1;
		int cons = 1;
		long codigoJerarquia = 0;
		try {
			if (filtro == null)
				return null;
			cn = cursor.getConnection();
			filtro.setFilInHabilitado(0);
			if (filtro.getFilPlanEstudios() == 1
					&& filtro.getFilNivelPerfil() > 4) {
				if (filtro.getFilUsuario() != filtro.getFilDocente()) {
					filtro.setFilInHabilitado(1);
				}
			}
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("descriptor.codigoJerarquia"));
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setLong(i++, filtro.getFilArea());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				codigoJerarquia = rs.getLong(1);
			}
			rs.close();
			st.close();
			String sql = rb.getString("descriptor.lista.0");
			if (filtro.getFilPlanEstudios() == 1) {
				sql += " " + rb.getString("descriptor.lista.1");
			}
			sql += " " + rb.getString("descriptor.lista.2");
			st = cn.prepareStatement(sql);
			i = 1;
			st.setLong(i++, codigoJerarquia);
			st.setInt(i++, filtro.getFilTipo());
			st.setInt(i++, filtro.getFilVigencia());
			if (filtro.getFilPlanEstudios() == 1) {
				st.setLong(i++, filtro.getFilDocente());
			}
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new DescriptorVO();
				item.setDesConsecutivo(cons++);
				item.setDesCodigoJerarquia(rs.getLong(i++));
				item.setDesCodigo(rs.getLong(i++));
				item.setDesVigencia(rs.getInt(i++));
				item.setDesTipo(rs.getInt(i++));
				item.setDesNombre(rs.getString(i++));
				item.setDesAbreviatura(rs.getString(i++));
				item.setDesOrden(rs.getInt(i++));
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

	public LogroVO ingresarLogro(LogroVO logro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("logro.codigoJerarquia"));
			st.setLong(i++, logro.getLogInstitucion());
			st.setInt(i++, logro.getLogMetodologia());
			st.setInt(i++, logro.getLogGrado());
			st.setLong(i++, logro.getLogAsignatura());
			st.setInt(i++, logro.getLogVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				logro.setLogCodigoJerarquia(rs.getLong(1));
			}
			rs.close();
			st.close();
			// validacion abrev
			st = cn.prepareStatement(rb.getString("logro.validarIngresarAbrev"));
			i = 1;
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setInt(i++, logro.getLogVigencia());
			st.setString(i++, logro.getLogAbreviatura());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Ya existe un logro con esta abreviatura: "
						+ logro.getLogAbreviatura());
			}
			rs.close();
			st.close();
			//
			st = cn.prepareStatement(rb.getString("logro.codigo"));
			i = 1;
			st.setInt(i++, logro.getLogVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				logro.setLogCodigo(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("logro.ingresar"));
			i = 1;
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setLong(i++, logro.getLogCodigo());
			st.setInt(i++, logro.getLogPeriodoIni());
			st.setInt(i++, logro.getLogPeriodoFin());
			st.setString(i++, logro.getLogNombre());
			st.setString(i++, logro.getLogAbreviatura());
			st.setString(i++, logro.getLogDescripcion());
			st.setInt(i++, logro.getLogVigencia());
			if (logro.getLogPlanEstudios() == 1) {
				st.setLong(i++, logro.getLogDocente());
			} else {
				st.setNull(i++, Types.VARCHAR);
			}
			st.setInt(i++, logro.getLogOrden());
			st.executeUpdate();
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
		return logro;
	}

	public DescriptorVO ingresarDescriptor(DescriptorVO desc) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("descriptor.codigoJerarquia"));
			st.setLong(i++, desc.getDesInstitucion());
			st.setInt(i++, desc.getDesMetodologia());
			st.setInt(i++, desc.getDesGrado());
			st.setLong(i++, desc.getDesArea());
			st.setInt(i++, desc.getDesVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				desc.setDesCodigoJerarquia(rs.getLong(1));
			}
			// System.out.println(desc.getDesGrado() + "//"
			// + desc.getDesInstitucion() + "//" + desc.getDesArea()
			// + "valor de jerar: " + desc.getDesCodigoJerarquia());
			rs.close();
			st.close();

			// validacion abrev
			st = cn.prepareStatement(rb
					.getString("descriptor.validarIngresarAbrev"));
			i = 1;
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setInt(i++, desc.getDesVigencia());
			st.setString(i++, desc.getDesAbreviatura());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception(
						"Ya existe un descriptor con esta abreviatura: "
								+ desc.getDesAbreviatura());
			}
			rs.close();
			st.close();
			//

			st = cn.prepareStatement(rb.getString("descriptor.codigo"));
			i = 1;
			st.setInt(i++, desc.getDesVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				desc.setDesCodigo(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("descriptor.ingresar"));
			i = 1;
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setLong(i++, desc.getDesCodigo());
			st.setInt(i++, desc.getDesTipo());
			st.setString(i++, desc.getDesNombre());
			st.setString(i++, desc.getDesAbreviatura());
			st.setString(i++, desc.getDesDescripcion());
			st.setInt(i++, desc.getDesPeriodoIni());
			st.setInt(i++, desc.getDesPeriodoFin());
			st.setInt(i++, desc.getDesVigencia());
			if (desc.getDesPlanEstudios() == 1) {
				st.setLong(i++, desc.getDesDocente());
			} else {
				st.setNull(i++, Types.VARCHAR);
			}
			st.setInt(i++, desc.getDesOrden());
			st.executeUpdate();
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
		return desc;
	}

	public LogroVO actualizarLogro(LogroVO logro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();

			// validacion abrev
			st = cn.prepareStatement(rb.getString("logro.validarActAbrev"));
			i = 1;
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setInt(i++, logro.getLogVigencia());
			st.setString(i++, logro.getLogAbreviatura());
			st.setLong(i++, logro.getLogCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Ya existe un logro con esta abreviatura: "
						+ logro.getLogAbreviatura());
			}
			rs.close();
			st.close();
			//

			st = cn.prepareStatement(rb.getString("logro.actualizar"));
			i = 1;
			st.setInt(i++, logro.getLogPeriodoIni());
			st.setInt(i++, logro.getLogPeriodoFin());
			st.setString(i++, logro.getLogNombre());
			st.setString(i++, logro.getLogAbreviatura());
			st.setString(i++, logro.getLogDescripcion());
			st.setInt(i++, logro.getLogOrden());
			// w
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setLong(i++, logro.getLogCodigo());
			st.setInt(i++, logro.getLogVigencia());
			st.executeUpdate();
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
		return logro;
	}

	public DescriptorVO actualizarDescriptor(DescriptorVO desc)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validacion abrev
			st = cn.prepareStatement(rb.getString("descriptor.validarActAbrev"));
			i = 1;
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setInt(i++, desc.getDesVigencia());
			st.setString(i++, desc.getDesAbreviatura());
			st.setLong(i++, desc.getDesCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception(
						"Ya existe un descriptor con esta abreviatura: "
								+ desc.getDesAbreviatura());
			}
			rs.close();
			st.close();
			//

			st = cn.prepareStatement(rb.getString("descriptor.actualizar"));
			i = 1;
			st.setInt(i++, desc.getDesTipo());
			st.setString(i++, desc.getDesNombre());
			st.setString(i++, desc.getDesAbreviatura());
			st.setString(i++, desc.getDesDescripcion());
			st.setInt(i++, desc.getDesPeriodoIni());
			st.setInt(i++, desc.getDesPeriodoFin());
			st.setInt(i++, desc.getDesOrden());
			// w
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setLong(i++, desc.getDesCodigo());
			st.setInt(i++, desc.getDesVigencia());
			st.executeUpdate();
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
		return desc;
	}

	public void eliminarLogro(LogroVO logro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validacion
			st = cn.prepareStatement(rb.getString("logro.validarEliminar"));
			i = 1;
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setLong(i++, logro.getLogCodigo());
			st.setInt(i++, logro.getLogVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("El logro ya ha sido evaluado");
			}
			rs.close();
			st.close();
			// eliminacion
			st = cn.prepareStatement(rb.getString("logro.eliminar"));
			i = 1;
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setLong(i++, logro.getLogCodigo());
			st.setInt(i++, logro.getLogVigencia());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void eliminarDescriptor(DescriptorVO desc) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validar
			st = cn.prepareStatement(rb.getString("descriptor.validarEliminar"));
			i = 1;
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setLong(i++, desc.getDesCodigo());
			st.setInt(i++, desc.getDesVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("El descriptor ya ha sido evaluado");
			}
			rs.close();
			st.close();
			// eliminar
			st = cn.prepareStatement(rb.getString("descriptor.eliminar"));
			i = 1;
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setLong(i++, desc.getDesCodigo());
			st.setInt(i++, desc.getDesVigencia());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public LogroVO getLogro(LogroVO logro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		LogroVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.obtener"));
			st.setLong(i++, logro.getLogCodigoJerarquia());
			st.setLong(i++, logro.getLogCodigo());
			st.setInt(i++, logro.getLogVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new LogroVO();
				item.setFormaEstado("1");
				item.setLogDisabled("disabled");
				item.setLogCodigoJerarquia(rs.getLong(i++));
				item.setLogCodigo(rs.getLong(i++));
				item.setLogPeriodoIni(rs.getInt(i++));
				item.setLogPeriodoFin(rs.getInt(i++));
				item.setLogNombre(rs.getString(i++));
				item.setLogAbreviatura(rs.getString(i++));
				item.setLogDescripcion(rs.getString(i++));
				item.setLogVigencia(rs.getInt(i++));
				item.setLogDocente(rs.getLong(i++));
				item.setLogOrden(rs.getInt(i++));
			}
			rs.close();
			st.close();
			if (item != null) {
				st = cn.prepareStatement(rb
						.getString("logro.obtenerGradoAsignatura"));
				i = 1;
				st.setLong(i++, logro.getLogCodigoJerarquia());
				rs = st.executeQuery();
				if (rs.next()) {
					i = 1;
					item.setLogInstitucion(rs.getLong(i++));
					item.setLogMetodologia(rs.getInt(i++));
					item.setLogGrado(rs.getInt(i++));
					item.setLogAsignatura(rs.getLong(i++));
				}
				item.setLogPlanEstudios(getEstadoPlan(cn,
						item.getLogInstitucion()));
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
		return item;
	}

	public DescriptorVO getDescriptor(DescriptorVO desc) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DescriptorVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("descriptor.obtener"));
			st.setLong(i++, desc.getDesCodigoJerarquia());
			st.setLong(i++, desc.getDesCodigo());
			st.setInt(i++, desc.getDesVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new DescriptorVO();
				item.setFormaEstado("1");
				item.setDesDisabled("disabled");
				item.setDesCodigoJerarquia(rs.getLong(i++));
				item.setDesCodigo(rs.getLong(i++));
				item.setDesTipo(rs.getInt(i++));
				item.setDesNombre(rs.getString(i++));
				item.setDesAbreviatura(rs.getString(i++));
				item.setDesDescripcion(rs.getString(i++));
				item.setDesPeriodoIni(rs.getInt(i++));
				item.setDesPeriodoFin(rs.getInt(i++));
				item.setDesVigencia(rs.getInt(i++));
				item.setDesDocente(rs.getLong(i++));
				item.setDesOrden(rs.getInt(i++));
			}
			rs.close();
			st.close();
			if (item != null) {
				st = cn.prepareStatement(rb
						.getString("descriptor.obtenerGradoArea"));
				i = 1;
				st.setLong(i++, desc.getDesCodigoJerarquia());
				rs = st.executeQuery();
				if (rs.next()) {
					i = 1;
					item.setDesInstitucion(rs.getLong(i++));
					item.setDesMetodologia(rs.getInt(i++));
					item.setDesGrado(rs.getInt(i++));
					item.setDesArea(rs.getLong(i++));
				}
				item.setDesPlanEstudios(getEstadoPlan(cn,
						item.getDesInstitucion()));
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
		return item;
	}

	public int getEstadoPlan(long inst) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.getEstadoPlan"));
			st.setLong(i++, inst);
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

	public int getNivelPerfil(int perfil) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("logro.getNivelPerfil"));
			st.setInt(i++, perfil);
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

	public int getNivelPerfil(Connection cn, int perfil) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("logro.getNivelPerfil"));
			st.setInt(i++, perfil);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public int getEstadoPlan(Connection cn, long inst) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("logro.getEstadoPlan"));
			st.setLong(i++, inst);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}
}
