package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.*;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class AsignaturaDAO extends Dao {

	public AsignaturaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.asignatura.bundle.sentencias");
	}

	public synchronized boolean insertar(AsignaturaVO asignaturaVo) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("insertar_Asignatura"));
			ps.setInt(posicion++, asignaturaVo.getArtAsigCodDinst());
			ps.setInt(posicion++, asignaturaVo.getArtAsigCodMetod());
			ps.setLong(posicion++, asignaturaVo.getArtAsigAnoVigencia());
			ps.setLong(posicion++, asignaturaVo.getArtAsigPerVigencia());
			// NO SE INSERTA CODIGO PORQUE ES AUTONUMERICO
			ps.setInt(posicion++, asignaturaVo.getArtAsigComponente());
			// if(asignaturaVo.getArtAsigCom()!=0)
			ps.setInt(posicion++, asignaturaVo.getArtAsigCom());
			// else
			// ps.setNull(posicion++,Types.VARCHAR);
			if (asignaturaVo.getArtAsigCodEsp() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigCodEsp());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			ps.setInt(posicion++, asignaturaVo.getArtAsigCodArea());
			// ps.setInt(posicion++,asignaturaVo.getArtAsigTipPeriodo());
			ps.setInt(posicion++, asignaturaVo.getArtAsigNumPeriodo());
			
			
			if (asignaturaVo.getArtAsigCodAsig() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigCodAsig());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			
			
			ps.setString(posicion++, asignaturaVo.getArtAsigNombre());
			ps.setInt(posicion++, asignaturaVo.getArtAsigCodAsigIns());
			ps.setString(posicion++, asignaturaVo.getArtAsigAbreviatura());
			ps.setInt(posicion++, asignaturaVo.getArtAsigIntHoraria());
			ps.setInt(posicion++, asignaturaVo.getArtAsigCredito());
			ps.setInt(posicion++, asignaturaVo.getArtAsigSesion());
			if (asignaturaVo.getArtAsigHabilitable() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigHabilitable());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			ps.setString(posicion++, asignaturaVo.getArtAsigDescripcion());
			ps.setInt(posicion++, asignaturaVo.getArtAsigOrden());
			ps.setBoolean(posicion++, asignaturaVo.isArtAsigHomologar());
			ps.setDouble(posicion++, asignaturaVo.getArtAsigNotMinHom());
			if (!asignaturaVo.getArtAsigTip().equals("0"))
				ps.setString(posicion++, asignaturaVo.getArtAsigTip());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (asignaturaVo.getArtAsigNat() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigNat());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (asignaturaVo.getArtAsigMod() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigMod());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			if (asignaturaVo.getArtAsigClasConsec() != 0)
				ps.setInt(posicion++, asignaturaVo.getArtAsigClasConsec());
			else
				ps.setNull(posicion++, Types.VARCHAR);
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
				cn.rollback();// en caso que falle la actualizacion, sedebe
								// retroceder la accinn
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

	public boolean actualizar(AsignaturaVO asignatura1, AsignaturaVO asignatura2) {
		boolean retorno = false;

		Connection cn = null;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);

			// System.out.println("Cod="+asignatura1.getArtAsigCodAsig());

			ps = cn.prepareStatement(rb.getString("actualizar_Asignatura"));
			ps.setInt(posicion++, asignatura1.getArtAsigComponente());
			if (asignatura1.getArtAsigCodEsp() != 0)
				ps.setInt(posicion++, asignatura1.getArtAsigCodEsp());
			else
				ps.setNull(posicion++, Types.VARCHAR);

			ps.setInt(posicion++, asignatura1.getArtAsigCodArea());
			ps.setInt(posicion++, asignatura1.getArtAsigNumPeriodo());

			if (asignatura1.getArtAsigCodAsig() != 0)
				ps.setInt(posicion++, asignatura1.getArtAsigCodAsig());
			else
				ps.setNull(posicion++, Types.VARCHAR);

			ps.setString(posicion++, asignatura1.getArtAsigNombre());
			ps.setInt(posicion++, asignatura1.getArtAsigCodAsigIns());
			ps.setString(posicion++, asignatura1.getArtAsigAbreviatura());
			ps.setDouble(posicion++, asignatura1.getArtAsigIntHoraria());
			ps.setInt(posicion++, asignatura1.getArtAsigCredito());
			ps.setInt(posicion++, asignatura1.getArtAsigSesion());
			if (asignatura1.getArtAsigHabilitable() != 0)
				ps.setInt(posicion++, asignatura1.getArtAsigHabilitable());
			else
				ps.setNull(posicion++, Types.VARCHAR);
			ps.setString(posicion++, asignatura1.getArtAsigDescripcion());
			ps.setInt(posicion++, asignatura1.getArtAsigOrden());
			ps.setBoolean(posicion++, asignatura1.isArtAsigHomologar());
			ps.setDouble(posicion++, asignatura1.getArtAsigNotMinHom());
			ps.setString(posicion++, asignatura1.getArtAsigTip());
			ps.setInt(posicion++, asignatura1.getArtAsigNat());
			ps.setInt(posicion++, asignatura1.getArtAsigMod());
			ps.setInt(posicion++, asignatura1.getArtAsigClasConsec());

			ps.setInt(posicion++, asignatura2.getArtAsigCodigo());

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
			setMensaje("Error actualizando Asignatura");
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

	public AsignaturaVO asignar(String codAsignatura) {
		AsignaturaVO asignaturaVO = null;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("asignar_Asignatura"));
			ps.setInt(posicion++, Integer.parseInt(codAsignatura));

			rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
				asignaturaVO = new AsignaturaVO();
				asignaturaVO.setFormaEstado("1");

				asignaturaVO.setArtAsigCodDinst(rs.getInt(i++));
				asignaturaVO.setArtAsigCodMetod(rs.getInt(i++));
				asignaturaVO.setArtAsigAnoVigencia(rs.getInt(i++));
				asignaturaVO.setArtAsigPerVigencia(rs.getInt(i++));
				asignaturaVO.setArtAsigCodigo(rs.getInt(i++));
				asignaturaVO.setArtAsigComponente(rs.getInt(i++));
				asignaturaVO.setArtAsigCom(rs.getInt(i++));
				asignaturaVO.setArtAsigCodEsp(rs.getInt(i++));
				asignaturaVO.setArtAsigCodArea(rs.getInt(i++));
				asignaturaVO.setArtAsigTipPeriodo(rs.getInt(i++));
				asignaturaVO.setArtAsigNumPeriodo(rs.getInt(i++));
				asignaturaVO.setArtAsigCodAsig(rs.getInt(i++));
				asignaturaVO.setArtAsigNombre(rs.getString(i++));
				asignaturaVO.setArtAsigCodAsigIns(rs.getInt(i++));
				asignaturaVO.setArtAsigAbreviatura(rs.getString(i++));
				asignaturaVO.setArtAsigIntHoraria(rs.getInt(i++));
				asignaturaVO.setArtAsigCredito(rs.getInt(i++));
				asignaturaVO.setArtAsigSesion(rs.getInt(i++));
				asignaturaVO.setArtAsigHabilitable(rs.getInt(i++));
				asignaturaVO.setArtAsigDescripcion(rs.getString(i++));
				asignaturaVO.setArtAsigOrden(rs.getInt(i++));
				asignaturaVO.setArtAsigHomologar(rs.getBoolean(i++));
				asignaturaVO.setArtAsigNotMinHom(rs.getInt(i++));
				asignaturaVO.setArtAsigTip(rs.getString(i++));
				asignaturaVO.setArtAsigNat(rs.getInt(i++));
				asignaturaVO.setArtAsigMod(rs.getInt(i++));
				asignaturaVO.setArtAsigClasConsec(rs.getInt(i++));

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
		return asignaturaVO;
	}

	public boolean eliminar(String codAsignatura) {
		boolean retorno = false;
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("elim_Cascade_Corequisito"));
			ps.setInt(1, Integer.parseInt(codAsignatura));
			ps.executeUpdate();

			ps = cn.prepareStatement(rb.getString("elim_Cascade_Prerequisito"));
			ps.setInt(1, Integer.parseInt(codAsignatura));
			ps.executeUpdate();

			ps = cn.prepareStatement(rb.getString("eliminar_Asignatura"));
			ps.setInt(1, Integer.parseInt(codAsignatura));
			ps.executeUpdate();
			cn.commit();
			retorno = true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			switch (sqle.getErrorCode()) {
			case 2292:
				setMensaje("Registros Asociados");
			}
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
		} catch (Exception sqle) {
			setMensaje("Error eliminando Asignatura. Posible problema: "
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

	public List getListaAsignatura(long insti, String componente,
			int especialidad, int area, int periodo, int complementaria,
			long anoVig, int perVig) {
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
				pt = cn.prepareStatement(rb.getString("lista_Asignatura"
						+ componente));
				pt.setLong(posicion++, insti);
				pt.setString(posicion++, componente);
				pt.setInt(posicion++, area);
				pt.setInt(posicion++, periodo);
				if (!componente.equals("1"))
					pt.setInt(posicion++, especialidad);
				pt.setLong(posicion++, anoVig);
				pt.setInt(posicion++, perVig);
				pt.setInt(posicion++, complementaria);
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

	public List getListaTipoPrograma() {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ListaProgramaVO listaProgramaVO = null;
		int posicion = 1;
		try {
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("getTipoPrograma"));
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				listaProgramaVO = new ListaProgramaVO();
				listaProgramaVO.setCodigo(rs.getInt(posicion++));
				listaProgramaVO.setNombre(rs.getString(posicion++));
				l.add(listaProgramaVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getListaNombresAsignaturas(int area) {
		Connection conect = null;
		PreparedStatement prepST = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ListaNombresVO listaNombresVO = null;
		int posicion = 1;
		try {
			conect = cursor.getConnection();
			prepST = conect.prepareStatement(rb.getString("getNombres"));
			prepST.setInt(posicion++, area);
			rs = prepST.executeQuery();
			while (rs.next()) {
				posicion = 1;
				listaNombresVO = new ListaNombresVO();
				listaNombresVO.setCodigo(rs.getInt(posicion++));
				listaNombresVO.setNombre(rs.getString(posicion++));
				l.add(listaNombresVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(prepST);
				OperacionesGenerales.closeConnection(conect);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getAjaxArea(long inst, int anho, int per, int comp) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ListaAreaVO lp = null;
		int i = 0;
		try {
			if (inst == 0 || anho == 0 || per == 0) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxArea"));
			i = 1;
			st.setLong(i++, inst);
			st.setLong(i++, anho);
			st.setLong(i++, per);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ListaAreaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
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
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lpA;
	}

	public boolean checkCodigo(DatosVO datosVO, AsignaturaVO asignaturaVO) {
		boolean esta = false;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getCodigo"));
			st.setLong(i++, datosVO.getInst());
			st.setLong(i++, asignaturaVO.getArtAsigCodAsigIns());
			// System.out.println("antes");
			// System.out.println("i "+datosVO.getInst());
			// System.out.println("e "+asignaturaVO.getArtAsigCodAsigIns());
			rs = st.executeQuery();
			while (rs.next()) {
				// System.out.println("encuentra");
				esta = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return esta;
	}

}
