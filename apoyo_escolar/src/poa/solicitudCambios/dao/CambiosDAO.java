package poa.solicitudCambios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.solicitudCambios.vo.FiltroCambiosVO;
import poa.solicitudCambios.vo.ParamsVO;
import poa.solicitudCambios.vo.CambioVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el modulo de actividadese 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class CambiosDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public CambiosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.solicitudCambios.bundle.cambios");
	}

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public List getListaCambios(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		CambioVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// saber si se puede aprobar
			st = cn.prepareStatement(rb.getString("poa.getEstado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
				filtro.setFilObservacion(rs.getString(2));
				filtro.setFilObservacionLinea(rs.getString(3));
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					filtro.setFilHabilitado(1);
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
				} else {
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_SED)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					filtro.setFilHabilitado(0);
				}
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilHabilitado(1);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
			}
			rs.close();
			st.close();
			// calculo de la lista
			st = cn.prepareStatement(rb.getString("cambio.lista"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilNivel());
			st.setLong(i++, filtro.getFilEntidad());

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new CambioVO();
				item.setVigencia(rs.getInt(i++));
				item.setNivel(rs.getInt(i++));
				item.setEntidad(rs.getLong(i++));
				item.setFechaSol(rs.getString(i++));
				item.setFechaSol_(rs.getString(i++));
				item.setFechaSol_(item.getFechaSol_().replace(':', '|'));

				item.setEstado(rs.getInt(i++));
				if (item.getEstado() == ParamsVO.ESTADO_ENVIADO)
					item.setNombreEstado(ParamsVO.ESTADO_ENVIADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_APROBADO)
					item.setNombreEstado(ParamsVO.ESTADO_APROBADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_RECHAZADO)
					item.setNombreEstado(ParamsVO.ESTADO_RECHAZADO_);

				item.setFechaEstado(rs.getString(i++));
				item.setAsunto(rs.getString(i++));
				item.setSolicitud(rs.getString(i++));
				item.setUsuario(rs.getString(i++));
				item.setUsuarioEstado(rs.getString(i++));
				item.setObservacion(rs.getString(i++));
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
	 * Obtiene una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @param estadoPOA
	 *            estado de POA
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO getCambio(int vigencia, long nivel, long entidad,
			String fechaSol, int estadoPOA, boolean habil) throws Exception {
		// si lo obtiene pero esta borrando la busqueda del filtro y ademas los
		// checkes estan descuadrados
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		CambioVO item = null;
		int i = 0;
		String fs = fechaSol.replace('-', ' ');
		// System.out.println("CAMBIO POA: GET CAMBIO: FS: "+fs);
		fs = fs.replace('|', ':');
		// System.out.println("CAMBIO POA: GET CAMBIO: FS: "+fs);
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("cambio.obtener"));
			i = 1;

			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new CambioVO();
				item.setFormaEstado("1");
				item.setVigencia(rs.getInt(i++));
				item.setNivel(rs.getInt(i++));
				item.setEntidad(rs.getLong(i++));
				item.setFechaSol(rs.getString(i++));
				item.setEstado(rs.getInt(i++));

				if (item.getEstado() == ParamsVO.ESTADO_ENVIADO)
					item.setNombreEstado(ParamsVO.ESTADO_ENVIADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_APROBADO)
					item.setNombreEstado(ParamsVO.ESTADO_APROBADO_);
				else if (item.getEstado() == ParamsVO.ESTADO_RECHAZADO)
					item.setNombreEstado(ParamsVO.ESTADO_RECHAZADO_);

				item.setFechaEstado(rs.getString(i++));
				item.setAsunto(rs.getString(i++));
				item.setSolicitud(rs.getString(i++));
				item.setUsuario(rs.getString(i++));
				item.setUsuarioEstado(rs.getString(i++));
				item.setObservacion(rs.getString(i++));
				if (estadoPOA != ParamPOA.ESTADO_POA_APROBADO_SED) {
					item.setDisabled("disabled");
					item.setDesHabilitado(true);
				}
			}
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
		return item;
	}

	/**
	 * Ingresa una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO ingresarCambio(CambioVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("cambio.validarEstado"));
			i = 1;
			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) != ParamPOA.ESTADO_POA_APROBADO_SED) {
					throw new Exception(
							"El POA no ha sido aprobado y no se puede registrar cambios");
				}
			}
			rs.close();
			st.close();

			//
			st = cn.prepareStatement(rb.getString("cambio.ingresar"));
			i = 1;

			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getNivel());
			st.setLong(i++, item.getEntidad());
			st.setLong(i++, ParamsVO.ESTADO_ENVIADO);
			st.setString(i++, item.getAsunto());
			st.setString(i++, item.getSolicitud());
			st.setString(i++, item.getUsuario());

			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
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

	/**
	 * Actualiza una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public CambioVO actualizarCambio(CambioVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			st = cn.prepareStatement(rb.getString("cambio.actualizar"));
			i = 1;

			st.setLong(i++, item.getEstado());
			st.setString(i++, item.getAsunto());
			st.setString(i++, item.getSolicitud());
			st.setString(i++, item.getUsuario());
			// w
			st.setInt(i++, item.getVigencia());
			st.setLong(i++, item.getNivel());
			st.setLong(i++, item.getEntidad());
			st.setString(i++, item.getFechaSol());
			st.executeUpdate();

			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
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

	/**
	 * Elimina una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @throws Exception
	 */
	public void eliminarCambio(int vigencia, long nivel, long entidad,
			String fecha) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		String fs = fecha.replace('-', ' ');
		// System.out.println("CAMBIO1 POA: GET CAMBIO: FS: "+fs);
		fs = fs.replace('|', ':');
		// System.out.println("CAMBIO2 POA: GET CAMBIO: FS: "+fs);
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("cambio.obtenerEstado"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamsVO.ESTADO_APROBADO) {
					throw new Exception(
							"El cambio ya ha sido aprobado y no se puede eliminar");
				}
			}
			rs.close();
			st.close();

			// eliminar cambio
			st = cn.prepareStatement(rb.getString("cambio.eliminar"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, nivel);
			st.setLong(i++, entidad);
			st.setString(i++, fs);
			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
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

	/**
	 * Calcula la lista de anos de vigencia
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
			pst = cn.prepareStatement(rb.getString("cambio.listaVigencia"));
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
	 * Obtiene los comentarios de los campos de las tablas de actividad con
	 * recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public FiltroCambiosVO getLabels(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (filtro == null)
			filtro = new FiltroCambiosVO();
		try {
			filtro.setFilVigencia((int) getVigenciaNumericoPOA());
			cn = cursor.getConnection();
			// calcular si es fecha habil para poder guardar actividades
			pst = cn.prepareStatement(rb.getString("cambio.rangoFecha"));
			pst.setInt(1, filtro.getFilVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				filtro.setFilRangoFechas(rs.getString(1) + " - "
						+ rs.getString(2));
				filtro.setFilFechaHabil(rs.getInt(3) == 0 ? false : true);
			} else {
				filtro.setFilRangoFechas("No establecido");
				filtro.setFilFechaHabil(false);
			}
			rs.close();
			pst.close();
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
		return filtro;
	}

	/**
	 * Aprueba el POA por parte del colegio
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public void aprobarColegio(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		int codigo = 0;
		try {
			cn = cursor.getConnection();
			// validacion de %
			st = cn.prepareStatement(rb.getString("poa.validarPonderado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) != 100)
					throw new Exception(
							"El porcentaje de ponderado debe sumar 100%");
			} else {
				throw new Exception("No hay actividades");
			}
			rs.close();
			st.close();
			// calculo del codigo
			st = cn.prepareStatement(rb.getString("poa.codigo"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				codigo = (rs.getInt(1));
			}
			rs.close();
			st.close();
			// ingresar
			st = cn.prepareStatement(rb.getString("poa.ingresar"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilEntidad());
			st.setInt(i++, codigo);
			st.setInt(i++, ParamPOA.ESTADO_POA_APROBADO_COLEGIO);
			st.setInt(i++, ParamPOA.ETAPA_POA_PLANEACION);
			st.setNull(i++, Types.VARCHAR);
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
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

	/**
	 * Calcula la lista de areas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion(int vigencia, long institucion, long codigo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("cambio.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				item.setPadre2(rs.getString(3));
				item.setPadre((rs.getLong(3))
						- getPonderadoArea(cn, vigencia, institucion, codigo,
								item.getCodigo()));
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

	public int getPonderadoArea(Connection cn, int vig, long inst, long codigo,
			long area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb.getString("cambio.getPonderadoArea"));
			i = 1;
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setLong(i++, codigo);
			st.setLong(i++, area);
			rs = st.executeQuery();
			if (rs.next())
				return rs.getInt(1);
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

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public float getTotalPonderado(FiltroCambiosVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// calculo del total ponderado
			st = cn.prepareStatement(rb.getString("cambio.totalPonderado"));
			st.setInt(1, filtro.getFilVigencia());
			st.setLong(2, filtro.getFilEntidad());
			rs = st.executeQuery();
			if (rs.next()) {
				return (rs.getFloat(1));
			}
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
		return 0;
	}

}
