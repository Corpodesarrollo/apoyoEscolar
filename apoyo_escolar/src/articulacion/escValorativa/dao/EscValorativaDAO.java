package articulacion.escValorativa.dao;

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
import articulacion.escValorativa.vo.AbreviaturaVO;
import articulacion.escValorativa.vo.DatosVO;
import articulacion.escValorativa.vo.EscValorativaVO;

public class EscValorativaDAO extends Dao {

	public EscValorativaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.escValorativa.bundle.sentencias");
	}

	public synchronized boolean insertar(EscValorativaVO escValorativaVO) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();

			ps = cn.prepareStatement(rb.getString("insertar_Escala"));

			ps.setLong(posicion++, escValorativaVO.getArtEscValCodInst());
			ps.setInt(posicion++, escValorativaVO.getArtEscValCodMetod());
			ps.setInt(posicion++, escValorativaVO.getArtEscValAnoVigencia());
			ps.setInt(posicion++, escValorativaVO.getArtEscValPerVigencia());
			ps.setString(posicion++, escValorativaVO.getArtEscValConceptual());
			ps.setString(posicion++, escValorativaVO.getArtEscValNombre());
			ps.setFloat(posicion++, escValorativaVO.getArtEscValRangoIni());
			ps.setFloat(posicion++, escValorativaVO.getArtEscValRangoFin());

			ps.executeUpdate();
			retorno = true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1:
			case 2291:
				setMensaje("La abreviatura de esta escala ya estn registrada");
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

	public boolean actualizar(EscValorativaVO escValorativaVO1,
			EscValorativaVO escValorativaVO2) {
		boolean retorno = false;

		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			ps = cn.prepareStatement(rb.getString("actualizar_Escala"));

			ps.setString(posicion++, escValorativaVO1.getArtEscValConceptual());
			ps.setString(posicion++, escValorativaVO1.getArtEscValNombre());
			ps.setFloat(posicion++, escValorativaVO1.getArtEscValRangoIni());
			ps.setFloat(posicion++, escValorativaVO1.getArtEscValRangoFin());

			ps.setLong(posicion++, escValorativaVO2.getArtEscValCodInst());
			ps.setInt(posicion++, escValorativaVO2.getArtEscValCodMetod());
			ps.setInt(posicion++, escValorativaVO2.getArtEscValAnoVigencia());
			ps.setInt(posicion++, escValorativaVO2.getArtEscValPerVigencia());
			ps.setString(posicion++, escValorativaVO2.getArtEscValConceptual());
			ps.execute();
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
				return false;
			}
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

	public EscValorativaVO asignar(long inst, int metod, int anVigencia,
			int perVigencia, String conceptual) {
		EscValorativaVO escValorativaVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignar_Escala"));
			ps.setLong(posicion++, inst);
			ps.setInt(posicion++, metod);
			ps.setInt(posicion++, anVigencia);
			ps.setInt(posicion++, perVigencia);
			ps.setString(posicion++, conceptual);

			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				escValorativaVO = new EscValorativaVO();
				escValorativaVO.setFormaEstado("1");

				escValorativaVO.setArtEscValCodInst(rs.getLong(i++));
				escValorativaVO.setArtEscValCodMetod(rs.getInt(i++));
				escValorativaVO.setArtEscValAnoVigencia(rs.getInt(i++));
				escValorativaVO.setArtEscValPerVigencia(rs.getInt(i++));
				escValorativaVO.setArtEscValConceptual(rs.getString(i++));
				escValorativaVO.setArtEscValNombre(rs.getString(i++));
				escValorativaVO.setArtEscValRangoIni(rs.getFloat(i++));
				escValorativaVO.setArtEscValRangoFin(rs.getFloat(i++));

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
		return escValorativaVO;
	}

	public boolean eliminar(long inst, int metod, int anVigencia,
			int perVigencia, String conceptual) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminar_Escala"));
			ps.setLong(posicion++, inst);
			ps.setInt(posicion++, metod);
			ps.setInt(posicion++, anVigencia);
			ps.setInt(posicion++, perVigencia);
			ps.setString(posicion++, conceptual);
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

	public List getListaEscala(long institucion, int metodologia,
			int aVigencia, int perVigencia) {
		// System.out.println("Entra");
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaEscala = new ArrayList();
		EscValorativaVO escValorativaVO = null;
		int posicion = 1, i = 0;
		/*
		 * System.out.println("institucion  "+institucion);
		 * System.out.println("metodologia "+metodologia);
		 * System.out.println("aVigencia  "+aVigencia);
		 * System.out.println("perVigencia "+perVigencia);
		 */

		try {

			cn = cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_Escala"));
			pt.setLong(posicion++, institucion);
			pt.setLong(posicion++, metodologia);
			pt.setInt(posicion++, aVigencia);
			pt.setInt(posicion++, perVigencia);

			rs = pt.executeQuery();
			// System.out.println("termina");
			while (rs.next()) {
				i = 1;
				escValorativaVO = new EscValorativaVO();

				escValorativaVO.setArtEscValConceptual(rs.getString(i++));
				escValorativaVO.setArtEscValNombre(rs.getString(i++));
				escValorativaVO.setArtEscValRangoIni(rs.getFloat(i++));
				escValorativaVO.setArtEscValRangoFin(rs.getFloat(i++));

				listaEscala.add(escValorativaVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaEscala;
	}

	public List getListaAbreviatura() {
		// System.out.println("Entra");
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		List listaAbreViatura = new ArrayList();
		AbreviaturaVO abreviaturaVO = null;
		int i = 0;

		try {

			cn = cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_Abreviatura"));
			rs = pt.executeQuery();
			// System.out.println("termina");
			while (rs.next()) {
				// System.out.println("encuentra");
				i = 1;
				abreviaturaVO = new AbreviaturaVO();
				abreviaturaVO.setCodigo(rs.getInt(i++));
				abreviaturaVO.setNombre(rs.getString(i++));
				abreviaturaVO.setAbreviatura(rs.getString(i++));
				// abreviaturaVO.setRaIni(rs.getFloat(i++));
				// abreviaturaVO.setRaFin(rs.getFloat(i++));

				listaAbreViatura.add(abreviaturaVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaAbreViatura;
	}

	public boolean validarRangos(DatosVO datosVO,
			EscValorativaVO escValorativaVO, int tipo) {
		System.out.println(escValorativaVO.getArtEscValRangoIni() + " "
				+ escValorativaVO.getArtEscValRangoFin() + " "
				+ escValorativaVO.getArtEscValConceptual());
		Connection cn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		int i = 1;

		try {
			cn = cursor.getConnection();
			// System.out.println("Tipo= "+tipo);
			if (tipo == 1)
				pt = cn.prepareStatement(rb.getString("validar_nuevo"));
			else
				pt = cn.prepareStatement(rb.getString("validar_actualiza"));

			pt.setFloat(i++, escValorativaVO.getArtEscValRangoIni());
			pt.setFloat(i++, escValorativaVO.getArtEscValRangoFin());
			pt.setFloat(i++, escValorativaVO.getArtEscValRangoIni());
			pt.setFloat(i++, escValorativaVO.getArtEscValRangoFin());
			pt.setFloat(i++, escValorativaVO.getArtEscValRangoIni());
			pt.setFloat(i++, escValorativaVO.getArtEscValRangoFin());
			pt.setInt(i++, datosVO.getInstitucion());
			pt.setInt(i++, datosVO.getMetodologia());
			pt.setInt(i++, datosVO.getAnVigencia());
			pt.setInt(i++, datosVO.getPerVigencia());

			if (tipo != 1)
				pt.setString(i++, escValorativaVO.getArtEscValConceptual());
			rs = pt.executeQuery();

			// System.out.println("Antes del While");
			while (rs.next()) {
				// System.out.println("encuentra rangos solapados");
				this.setMensaje("El valor inicial o final coincide con un rango de valores ya establecido para otra escala");
				return false;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pt);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
