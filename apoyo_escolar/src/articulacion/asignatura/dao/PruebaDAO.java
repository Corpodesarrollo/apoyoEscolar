package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.PruebaVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class PruebaDAO extends Dao {

	public PruebaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.asignatura.bundle.sentencias");
	}

	public synchronized boolean insertar(PruebaVO pruebaVo) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("Esto vale prueba principal: "+pruebaVo.getPruebaPrincipal());
			if (pruebaVo.getPruebaPrincipal() == 0)
				ps = cn.prepareStatement(rb.getString("inserta_prueba1"));
			else
				ps = cn.prepareStatement(rb.getString("inserta_prueba2"));
			ps.setLong(posicion++, pruebaVo.getArtPruCodAsig());
			if (pruebaVo.getPruebaPrincipal() != 0)
				ps.setLong(posicion++, pruebaVo.getPruebaPrincipal());

			ps.setString(posicion++, pruebaVo.getArtPruNombre());
			ps.setString(posicion++, pruebaVo.getArtPruAbreviatura());
			ps.setDouble(posicion++, pruebaVo.getArtPruPorcentaje());
			if (pruebaVo.getArtPruOrden() != 0)
				ps.setInt(posicion++, pruebaVo.getArtPruOrden());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (pruebaVo.getPruebaPrincipal() == 0) {
				ps.setLong(posicion++, pruebaVo.getPruebaPrincipal());
			} else {
				ps.setLong(posicion++, pruebaVo.getArtPruCodAsig());
				ps.setLong(posicion++, pruebaVo.getPruebaPrincipal());
			}
			ps.setInt(posicion++, pruebaVo.getArtPruBimestre());
			ps.execute();
			cn.commit();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			}
			try {
				cn.rollback();
			} catch (SQLException s) {
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

	public boolean actualizar(PruebaVO prueba1, PruebaVO prueba2) {
		boolean retorno = false;

		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualiza_prueba"));
			ps.setString(posicion++, prueba1.getArtPruNombre());
			ps.setString(posicion++, prueba1.getArtPruAbreviatura());
			ps.setDouble(posicion++, prueba1.getArtPruPorcentaje());
			if (prueba1.getArtPruOrden() != 0)
				ps.setInt(posicion++, prueba1.getArtPruOrden());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			ps.setInt(posicion++, prueba1.getArtPruBimestre());
			ps.setLong(posicion++, prueba2.getArtPruCodAsig());
			ps.setLong(posicion++, prueba2.getArtPruCodigo());
			ps.setLong(posicion++, prueba2.getPruebaPrincipal());

			ps.execute();
			cn.commit();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("Cndigo duplicado");
			case 2292:
				setMensaje("Registros Asociados");
			}

			try {
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
			} catch (SQLException s) {
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
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

	public PruebaVO asignar(String codAsig, String codPrueba, String codSub) {
		PruebaVO pruebaVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			// System.out.println("--Asignatura="+codAsig);
			// System.out.println("--Prueba="+codPrueba);
			// System.out.println("--SubPrueba="+codSub);
			long asig = Long.parseLong(codAsig);
			int pru = Integer.parseInt(codPrueba);
			long subp = Long.parseLong(codSub);
			ps = cn.prepareStatement(rb.getString("asigna_prueba"));
			ps.setLong(posicion++, asig);
			ps.setLong(posicion++, pru);
			ps.setLong(posicion++, subp);

			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				pruebaVO = new PruebaVO();
				pruebaVO.setFormaEstado("1");
				pruebaVO.setArtPruCodAsig(rs.getLong(i++));
				pruebaVO.setArtPruCodigo(rs.getLong(i++));
				pruebaVO.setArtPruNombre(rs.getString(i++));
				pruebaVO.setArtPruAbreviatura(rs.getString(i++));
				pruebaVO.setArtPruPorcentaje(rs.getDouble(i++));
				pruebaVO.setArtPruOrden(rs.getInt(i++));
				pruebaVO.setPruebaPrincipal(rs.getLong(i++));
				pruebaVO.setArtPruBimestre(rs.getInt(i++));
				// System.out.println("principal="+pruebaVO.getPruebaPrincipal());
			}
			rs.close();
			ps.close();
			if (pruebaVO != null) {
				if (pruebaVO.getPruebaPrincipal() == 0) {
					ps = cn.prepareStatement(rb
							.getString("asigna_porcentaje_prueba1"));
					posicion = 1;
					ps.setLong(posicion++, asig);
					ps.setLong(posicion++, pru);
					rs = ps.executeQuery();
					if (rs.next()) {
						pruebaVO.setArtPruTotal(rs.getDouble(1));
					}
				} else {
					ps = cn.prepareStatement(rb
							.getString("asigna_porcentaje_prueba2"));
					posicion = 1;
					ps.setLong(posicion++, asig);
					ps.setLong(posicion++, pru);
					ps.setLong(posicion++, subp);
					rs = ps.executeQuery();
					if (rs.next()) {
						pruebaVO.setArtPruTotal(rs.getDouble(1));
					}
				}
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
		return pruebaVO;
	}

	public boolean eliminar(String codAsig, String codPrueba, String codSub) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (Long.parseLong(codSub) == 0) {
				ps = cn.prepareStatement(rb.getString("validar_prueba"));
				ps.setLong(1, Long.parseLong(codAsig));
				ps.setLong(2, Long.parseLong(codPrueba));
				rs = ps.executeQuery();
				if (rs.next()) {
					setMensaje("La prueba tiene sub-pruebas");
					return false;
				}
				rs.close();
				ps.close();
			}
			ps = cn.prepareStatement(rb.getString("elimina_prueba"));
			ps.setLong(1, Long.parseLong(codAsig));
			ps.setLong(2, Long.parseLong(codPrueba));
			ps.setLong(3, Long.parseLong(codSub));
			ps.executeUpdate();
			retorno = true;
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			switch (sqle.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
			}
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public List getListaPrueba(long codAsignatura) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaPrueba = new ArrayList();
		PruebaVO pruebaVO = null;
		int i = 0;
		// System.out.println("CODIGO DE ASIGNATURA A LISTAR="+codAsignatura);
		try {
			cn = cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_prueba"));
			pt.setLong(1, codAsignatura);
			rs = pt.executeQuery();

			while (rs.next()) {
				i = 1;
				pruebaVO = new PruebaVO();
				// pruebaVO.setTipo(rs.getString(i++));
				pruebaVO.setArtPruCodAsig(rs.getLong(i++));
				pruebaVO.setArtPruCodigo(rs.getLong(i++));
				pruebaVO.setPruebaPrincipal(rs.getLong(i++));
				pruebaVO.setArtPruNombre(rs.getString(i++));
				pruebaVO.setArtPruPorcentaje(rs.getFloat(i++));

				listaPrueba.add(pruebaVO);
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
		return listaPrueba;
	}

	public List getListaPruebaPrinc(long codAsignatura) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaPrueba = new ArrayList();
		PruebaVO pruebaVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			// obtener default
			pruebaVO = new PruebaVO();
			pruebaVO.setArtPruCodigo(0);
			pruebaVO.setArtPruNombre("-Es Prueba Principal-");
			pruebaVO.setArtPruAbreviatura("Ppal");
			pruebaVO.setArtPruPorcentaje(getPorcentaje(cn, codAsignatura,
					pruebaVO.getArtPruCodigo()));
			pruebaVO.setPruebaPrincipal(0);
			listaPrueba.add(pruebaVO);
			// traer los de la base de datos
			pt = cn.prepareStatement(rb.getString("lista_pruebas_principales"));
			pt.setLong(1, codAsignatura);
			rs = pt.executeQuery();
			while (rs.next()) {
				i = 1;
				pruebaVO = new PruebaVO();
				pruebaVO.setArtPruCodigo(rs.getLong(i++));
				pruebaVO.setArtPruNombre(rs.getString(i++));
				pruebaVO.setArtPruAbreviatura(rs.getString(i++));
				pruebaVO.setArtPruPorcentaje(getPorcentaje(cn, codAsignatura,
						pruebaVO.getArtPruCodigo()));
				pruebaVO.setPruebaPrincipal(rs.getLong(i++));
				listaPrueba.add(pruebaVO);
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
		return listaPrueba;
	}

	public double getPorcentaje(Connection cn, long codAsignatura, long prueba) {
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			pt = cn.prepareStatement(rb.getString("getPorcentajeSubPruebas"));
			pt.setLong(1, codAsignatura);
			pt.setLong(2, prueba);
			rs = pt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public double getPorcentajeFaltante(Connection cn, long codAsignatura,
			long prueba, long subPrueba) {
		PreparedStatement pt = null;
		ResultSet rs = null;
		try {
			pt = cn.prepareStatement(rb.getString("getPorcentajeSubPruebas"));
			pt.setLong(1, codAsignatura);
			pt.setLong(2, prueba);
			rs = pt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
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
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List ajaxAsignatura(long insti, String componente, int especialidad,
			int area, int periodo, int complementaria, long anoVig, int perVig) {
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaAsignatura = new ArrayList();
		AsignaturaVO asignaturaVO = null;
		int posicion = 1, i = 0;
		try {
			/*
			 * System.out.println("Institucion: "+insti);
			 * System.out.println("componente: "+componente);
			 * System.out.println("especialidad: "+especialidad);
			 * System.out.println("area: "+area);
			 * System.out.println("periodo: "+periodo);
			 * System.out.println("complementaria: "+complementaria);
			 * System.out.println("anoVig: "+anoVig);
			 * System.out.println("perVig: "+perVig);
			 */

			if (componente != null) {
				cn = cursor.getConnection();
				pt = cn.prepareStatement(rb.getString("ajax_Asignatura"
						+ componente));
				pt.setLong(posicion++, insti);
				pt.setString(posicion++, componente);
				pt.setInt(posicion++, area);
				pt.setInt(posicion++, periodo);
				if (!componente.equals("1"))
					pt.setInt(posicion++, especialidad);
				pt.setLong(posicion++, anoVig);
				pt.setInt(posicion++, perVig);

				rs = pt.executeQuery();
				// System.out.println("Antes ");
				while (rs.next()) {
					i = 1;
					// System.out.println("Encuentra asignatura");
					asignaturaVO = new AsignaturaVO();
					asignaturaVO.setArtAsigCodigo(rs.getInt(i++));
					asignaturaVO.setArtAsigCodAsigIns(rs.getInt(i++));
					asignaturaVO.setArtAsigNombre(rs.getString(i++));

					listaAsignatura.add(asignaturaVO);
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
