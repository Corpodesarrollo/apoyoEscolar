package articulacion.grupoArt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.grupoArt.vo.DatosVO;
import articulacion.grupoArt.vo.GruAsignaturaVO;
import articulacion.grupoArt.vo.GrupoArtVO;

public class GrupoArtDAO extends Dao {

	public GrupoArtDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.grupoArt.bundle.sentencias");
	}

	public synchronized boolean insertar(GrupoArtVO grupoVo) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			// cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("insertar_Grupo"));
			ps.setInt(posicion++, grupoVo.getArtGruCodInst());
			ps.setInt(posicion++, grupoVo.getArtGruCodSede());
			ps.setInt(posicion++, grupoVo.getArtGruCodJornada());
			ps.setInt(posicion++, grupoVo.getArtGruAnoVigencia());
			ps.setInt(posicion++, grupoVo.getArtGruPerVigencia());
			ps.setInt(posicion++, grupoVo.getArtGruPerEsp());
			ps.setInt(posicion++, grupoVo.getArtGruComponente());
			if (grupoVo.getArtGruCodEsp() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruCodEsp());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			// ps.setInt(posicion++,grupoVo.getArtGruCodigo());
			ps.setInt(posicion++, grupoVo.getArtGruConsecutivo());
			ps.setBoolean(posicion++, grupoVo.getArtGruRepite());
			if (grupoVo.getArtGruOrden() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruOrden());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (grupoVo.getArtGruCupoNivel() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruCupoNivel());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (grupoVo.getArtGruCupoNoNivel() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruCupoNoNivel());
			else
				ps.setNull(posicion++, Types.VARCHAR);

			if (grupoVo.getArtGruCupoGeneral() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruCupoGeneral());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			
			/**********Asignatura************/
			if (grupoVo.getArtGruCodAsig() != 0)
				ps.setInt(posicion++, grupoVo.getArtGruCodAsig());
			else
				ps.setNull(posicion++, Types.INTEGER);
			
			ps.execute();
			
			
			/*long cod = getCodigo(grupoVo, cn);
			insertaAsignatura(cod, grupoVo.getAsignaturas(), cn);*/

			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public boolean actualizar(GrupoArtVO grupo1, GrupoArtVO grupo2) {
		boolean retorno = false;

		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizar_Grupo"));
			ps.setInt(posicion++, grupo1.getArtGruCodInst());
			ps.setInt(posicion++, grupo1.getArtGruCodSede());
			ps.setInt(posicion++, grupo1.getArtGruCodJornada());
			ps.setInt(posicion++, grupo1.getArtGruAnoVigencia());
			ps.setInt(posicion++, grupo1.getArtGruPerVigencia());
			ps.setInt(posicion++, grupo1.getArtGruPerEsp());
			ps.setInt(posicion++, grupo1.getArtGruComponente());
			if (grupo1.getArtGruCodEsp() != 0)
				ps.setInt(posicion++, grupo1.getArtGruCodEsp());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			// ps.setInt(posicion++,grupo1.getArtGruCodigo());
			ps.setInt(posicion++, grupo1.getArtGruConsecutivo());
			ps.setBoolean(posicion++, grupo1.getArtGruRepite());
			ps.setInt(posicion++, grupo1.getArtGruOrden());
			if (grupo1.getArtGruCupoNivel() != 0)
				ps.setInt(posicion++, grupo1.getArtGruCupoNivel());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (grupo1.getArtGruCupoNoNivel() != 0)
				ps.setInt(posicion++, grupo1.getArtGruCupoNoNivel());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (grupo1.getArtGruCupoGeneral() != 0)
				ps.setInt(posicion++, grupo1.getArtGruCupoGeneral());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			
			/*********Asignatura*********/
			if (grupo1.getArtGruCodAsig() != 0)
				ps.setInt(posicion++, grupo1.getArtGruCodAsig());
			else
				ps.setNull(posicion++, Types.INTEGER);
			
			ps.setLong(posicion++, grupo2.getArtGruCodigo());
			
			
			ps.execute();
			/*eliminaAsignatura(grupo2.getArtGruCodigo(), cn);
			if (grupo1.getAsignaturas() != null) {
				insertaAsignatura(grupo2.getArtGruCodigo(),grupo1.getAsignaturas(), cn);
			}*/

			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}
			// setMensaje("Error actualizando Perfil. Posible problema: "+getErrorCode(e));
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			// setMensaje("Error actualizando Perfil. Posible problema: "+e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public GrupoArtVO asignar(String codGrupo) {
		GrupoArtVO grupoVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignar_Grupo"));
			ps.setInt(posicion++, Integer.parseInt(codGrupo));

			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				grupoVO = new GrupoArtVO();
				grupoVO.setFormaEstado("1");

				grupoVO.setArtGruCodInst(rs.getInt(i++));
				grupoVO.setArtGruCodSede(rs.getInt(i++));
				grupoVO.setArtGruCodJornada(rs.getInt(i++));
				grupoVO.setArtGruAnoVigencia(rs.getInt(i++));
				grupoVO.setArtGruPerVigencia(rs.getInt(i++));
				grupoVO.setArtGruPerEsp(rs.getInt(i++));
				grupoVO.setArtGruComponente(rs.getInt(i++));
				grupoVO.setArtGruCodEsp(rs.getInt(i++));
				grupoVO.setArtGruCodigo(rs.getInt(i++));
				grupoVO.setArtGruConsecutivo(rs.getInt(i++));
				grupoVO.setArtGruRepite(rs.getBoolean(i++));
				grupoVO.setArtGruOrden(rs.getInt(i++));
				grupoVO.setArtGruCupoNivel(rs.getInt(i++));
				grupoVO.setArtGruCupoNoNivel(rs.getInt(i++));
				grupoVO.setArtGruCupoGeneral(rs.getInt(i++));
				grupoVO.setArtGruCodAsig(rs.getInt(i++));
				if (grupoVO.getArtGruCupoGeneral() != 0)
					grupoVO.setCheck(true);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return grupoVO;
	}

	public boolean eliminar(String codGrupo) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			long codigo = Long.parseLong(codGrupo);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			eliminaAsignatura(codigo, cn);
			ps = cn.prepareStatement(rb.getString("eliminar_Grupo"));
			ps.setLong(1, codigo);
			ps.executeUpdate();
			cn.commit();
			retorno = true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			// setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			switch (sqle.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
		} catch (Exception sqle) {
			setMensaje("Error eliminando Grupo. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public List getListaGrupo(int institucion, int sede, int jornada,
			int aVigencia, int perVigencia, int periodo, int componente,
			int especialidad) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaGrupo = new ArrayList();
		GrupoArtVO grupoVO = null;
		int posicion = 1, i = 0;

		try {
			if (componente != 0) {
				cn = cursor.getConnection();
				pt = cn.prepareStatement(rb.getString("lista_Grupo"
						+ componente));
				pt.setInt(posicion++, institucion);
				pt.setInt(posicion++, sede);
				pt.setInt(posicion++, jornada);
				pt.setInt(posicion++, aVigencia);
				pt.setInt(posicion++, perVigencia);
				pt.setInt(posicion++, periodo);
				pt.setInt(posicion++, componente);
				if (componente == 2) {
					if (especialidad != 0)
						pt.setInt(posicion++, especialidad);
					else {
						pt.setNull(posicion++, Types.VARCHAR);
						// System.out.println("asigna nulo");
					}
				}
				rs = pt.executeQuery();
				// System.out.println("termina");
				while (rs.next()) {
					// System.out.println("encuentra");
					i = 1;
					grupoVO = new GrupoArtVO();
					grupoVO.setArtGruCodigo(rs.getInt(i++));
					grupoVO.setArtGruConsecutivo(rs.getInt(i++));
					grupoVO.setArtGruCupoNivel(rs.getInt(i++));
					grupoVO.setArtGruCupoNoNivel(rs.getInt(i++));
					grupoVO.setArtGruCupoGeneral(rs.getInt(i++));
					grupoVO.setArtGruPerEsp(periodo);
					grupoVO.setArtGruCodAsig(rs.getInt(i++));
					grupoVO.setArtGruCodAsigNombre(rs.getString(i++));
					listaGrupo.add(grupoVO);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaGrupo;
	}

	public List getListaAsignatura(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaAsignatura = new ArrayList();
		GruAsignaturaVO asignaturaVO = null;
		int posicion = 1, i = 0;
		/*
		 * System.out.println("institucion  "+datosVO.getInstitucion());
		 * System.out.println("sede "+datosVO.getSede());
		 * System.out.println("jornada "+datosVO.getJornada());
		 * System.out.println("aVigencia  "+datosVO.getAnVigencia());
		 * System.out.println("perVigencia "+datosVO.getPerVigencia());
		 * System.out.println("periodo "+datosVO.getPeriodo());
		 * System.out.println("componente "+datosVO.getComponente());
		 * System.out.println("especialidad "+datosVO.getEspecialidad());
		 */
		try {
			if (datosVO != null) {
				// System.out.println("Datos VO no es NULO");
				if (datosVO.getComponente() != 0) {
					cn = cursor.getConnection();
					pt = cn.prepareStatement(rb.getString("lista_Asignatura"
							+ datosVO.getComponente()));
					pt.setInt(posicion++, datosVO.getInstitucion());
					pt.setInt(posicion++, datosVO.getComponente());
					pt.setInt(posicion++, datosVO.getPeriodo());
					pt.setInt(posicion++, datosVO.getAnVigencia());
					pt.setInt(posicion++, datosVO.getPerVigencia());
					if (datosVO.getComponente() == 2) {
						if (datosVO.getEspecialidad() != 0)
							pt.setInt(posicion++, datosVO.getEspecialidad());
						else {
							pt.setNull(posicion++, Types.VARCHAR);
						}
					}
					rs = pt.executeQuery();
					// System.out.println("termina");
					while (rs.next()) {
						// System.out.println("encuentra");
						i = 1;
						asignaturaVO = new GruAsignaturaVO();
						asignaturaVO.setCodigo(rs.getInt(i++));
						asignaturaVO.setCodigo2(rs.getInt(i++));
						asignaturaVO.setNombre(rs.getString(i++));

						listaAsignatura.add(asignaturaVO);
					}
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaAsignatura;
	}

	public long getCodigo(GrupoArtVO grupoVO, Connection cn)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1;
		long cod = 0;
		try {
			ps = cn.prepareStatement(rb.getString("cosultaCodigo"
					+ grupoVO.getArtGruComponente()));
			ps.setInt(posicion++, grupoVO.getArtGruCodInst());
			ps.setInt(posicion++, grupoVO.getArtGruCodSede());
			ps.setInt(posicion++, grupoVO.getArtGruCodJornada());
			ps.setInt(posicion++, grupoVO.getArtGruAnoVigencia());
			ps.setInt(posicion++, grupoVO.getArtGruPerVigencia());
			ps.setInt(posicion++, grupoVO.getArtGruPerEsp());
			ps.setInt(posicion++, grupoVO.getArtGruComponente());
			ps.setInt(posicion++, grupoVO.getArtGruConsecutivo());
			ps.setBoolean(posicion++, grupoVO.getArtGruRepite());
			if (grupoVO.getArtGruComponente() != 1)
				ps.setInt(posicion++, grupoVO.getArtGruCodEsp());
			rs = ps.executeQuery();
			// System.out.println("antes de ");
			while (rs.next()) {
				// System.out.println("encuentra cndigo");
				cod = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
		return cod;
	}

	public void insertaAsignatura(long grupo, long[] asig, Connection cn)
			throws SQLException {
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("insertar_Asignatura"));
			for (int a = 0; a < asig.length; a++) {
				posicion = 1;
				ps.setLong(posicion++, grupo);
				ps.setLong(posicion++, asig[a]);
				ps.addBatch();
				// System.out.println("INSERTA:"+grupo+"//"+asig[a]);
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			}
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void eliminaAsignatura(long codigo, Connection cn)
			throws SQLException {
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			ps = cn.prepareStatement(rb.getString("elimina_Asignaturas"));
			ps.setLong(posicion++, codigo);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
			}
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getAsigIncritas(String grupo) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaAsignatura = new ArrayList();
		GruAsignaturaVO gruAsignaturaVO = null;
		int posicion = 1, i = 0;

		try {
			if (grupo != null) {
				// System.out.println("grupo----->"+grupo);
				cn = cursor.getConnection();
				pt = cn.prepareStatement(rb.getString("lista_AsigInscrita"));
				pt.setLong(posicion++, Long.parseLong(grupo));
				rs = pt.executeQuery();
				while (rs.next()) {
					// System.out.println("encuentra inscritas");
					i = 1;
					gruAsignaturaVO = new GruAsignaturaVO();
					gruAsignaturaVO.setCodigo(rs.getInt(i++));
					listaAsignatura.add(gruAsignaturaVO);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaAsignatura;
	}
}
