//Version Fecha		    Autor		    Descripcion
//1.0	  25/11/2020	John Heredia	se agrego la opcion de validacion campo Orden para el Area base indicada, sobre el metodo  actualizarArea  



/**
 * 
 */
package siges.gestionAcademica.planDeEstudios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.planDeEstudios.vo.AreaVO;
import siges.gestionAcademica.planDeEstudios.vo.AsignaturaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroAreaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroAsignaturaVO;
import siges.gestionAcademica.planDeEstudios.vo.FiltroPlanDeEstudiosVO;
import siges.gestionAcademica.planDeEstudios.vo.PlanDeEstudiosVO;

/**
 * 27/10/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class PlanDeEstudiosDAO extends Dao {
	private ResourceBundle rb;

	public PlanDeEstudiosDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.planDeEstudios.bundle.planDeEstudios");
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
			st = cn.prepareStatement(rb.getString("common.listaMetodologia"));
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

	public List getListaAreaBase() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaAreaBase"));
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

	public ItemVO getAreaBase(long area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.obtenerAreaBase"));
			st.setLong(i++, area);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public List getListaGradoTotal() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGradoTotal"));
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

	public List getListaGradoAreaBase(long inst, int met, int vig, long area)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGradoAreaBase"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, vig);
			st.setLong(i++, area);
			st.setLong(i++, inst);
			st.setInt(i++, met);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre2(rs.getString(i++));
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

	public List getListaGradoBase(long inst, int met) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGradoBase"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
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

	public List getListaArea(long inst, int met, int vig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaArea"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
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

	public List getListaAsignaturaBase(long area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaAsignaturaBase"));
			st.setLong(i++, area);
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

	public ItemVO getAsignaturaBase(long asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("common.obtenerAsignaturaBase"));
			st.setLong(i++, asig);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
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

	public List getListaGradoArea(long inst, int met, int vig, long area)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("common.listaGradoArea"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, vig);
			st.setLong(i++, area);
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

	public long getCodigoJerarquia(Connection cn) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("common.codigoJerarquia"));
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getLong(i++);
			}
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List getListaPlanEstudios(FiltroPlanDeEstudiosVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		PlanDeEstudiosVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planDeEstudios.lista"));
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new PlanDeEstudiosVO();
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaMetodologia(rs.getInt(i++));
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaMetodologiaNombre(rs.getString(i++));
				item.setPlaCriterio(rs.getString(i++));
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

	public PlanDeEstudiosVO getPlanEstudios(PlanDeEstudiosVO plan)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlanDeEstudiosVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planDeEstudios.obtener"));
			st.setLong(i++, plan.getPlaInstitucion());
			st.setInt(i++, plan.getPlaMetodologia());
			st.setInt(i++, plan.getPlaVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new PlanDeEstudiosVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaMetodologia(rs.getInt(i++));
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaCriterio(rs.getString(i++));
				item.setPlaProcedimiento(rs.getString(i++));
				item.setPlaPlanEspecial(rs.getString(i++));
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

	public PlanDeEstudiosVO ingresarPlanDeEstudios(PlanDeEstudiosVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validar ingresar
			st = cn.prepareStatement(rb
					.getString("planDeEstudios.validarIngreso"));
			i = 1;
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaMetodologia());
			st.setInt(i++, item.getPlaVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception(
						"Ya existe un plan de estudios para la metodologna indicada");
			}
			rs.close();
			st.close();
			// ingresar
			st = cn.prepareStatement(rb.getString("planDeEstudios.insertar"));
			i = 1;
			// PLACODINST, PLACODMETOD, PLAVIGENCIA,
			// PLACRITERIO,PLAPROCEDIMIENTO, PLAPLANESESPECIALES
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaMetodologia());
			st.setInt(i++, item.getPlaVigencia());
			st.setString(i++, item.getPlaCriterio());
			st.setString(i++, item.getPlaProcedimiento());
			st.setString(i++, item.getPlaPlanEspecial());
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
		return item;
	}

	public PlanDeEstudiosVO actualizarPlanDeEstudios(PlanDeEstudiosVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// actualizar
			st = cn.prepareStatement(rb.getString("planDeEstudios.actualizar"));
			i = 1;
			st.setString(i++, item.getPlaCriterio());
			st.setString(i++, item.getPlaProcedimiento());
			st.setString(i++, item.getPlaPlanEspecial());
			// w
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaMetodologia());
			st.setInt(i++, item.getPlaVigencia());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception("Erro interno: " + sqle.getMessage());
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

	public void eliminarPlanDeEstudios(PlanDeEstudiosVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// eliminar
			st = cn.prepareStatement(rb.getString("planDeEstudios.eliminar"));
			i = 1;
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaMetodologia());
			st.setInt(i++, item.getPlaVigencia());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception("Erro interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getListaArea(FiltroAreaVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		AreaVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("area.lista"));
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilVigencia());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new AreaVO();
				item.setAreInstitucion(rs.getLong(i++));
				item.setAreMetodologia(rs.getInt(i++));
				item.setAreVigencia(rs.getInt(i++));
				item.setAreCodigo(rs.getLong(i++));
				item.setAreNombre(rs.getString(i++));
				item.setAreAbreviatura(rs.getString(i++));
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

	public AreaVO getArea(AreaVO area) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		AreaVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("area.obtener"));
			st.setLong(i++, area.getAreInstitucion());
			st.setInt(i++, area.getAreMetodologia());
			st.setInt(i++, area.getAreVigencia());
			st.setLong(i++, area.getAreCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new AreaVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				item.setAreInstitucion(rs.getLong(i++));
				item.setAreMetodologia(rs.getInt(i++));
				item.setAreVigencia(rs.getInt(i++));
				item.setAreCodigo(rs.getLong(i++));
				item.setAreNombre(rs.getString(i++));
				item.setAreAbreviatura(rs.getString(i++));
				item.setAreOrden(rs.getLong(i++));
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

	public AreaVO ingresarArea(AreaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar ingresar
			st = cn.prepareStatement(rb.getString("area.validarIngreso"));
			i = 1;
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception(
						"Ya existe un área para el área base indicada");
			}
			rs.close();
			st.close();
			// ingresar
			st = cn.prepareStatement(rb.getString("area.insertar"));
			i = 1;
			// ARECODINST, ARECODMETOD, AREVIGENCIA,ARECODIGO,ARENOMBRE,
			// AREABREV,AREORDEN
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.setString(i++, item.getAreNombre());
			st.setString(i++, item.getAreAbreviatura());
			st.setLong(i++, item.getAreOrden());
			st.executeUpdate();
			ingresarGradoArea(cn, item);
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

	public AreaVO actualizarArea(AreaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			// validar ingresar
			st = cn.prepareStatement(rb.getString("area.validarActualizarOrden"));
			i = 1;
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreOrden());
			rs = st.executeQuery();
			if (rs.next()) {
			    throw new Exception(
			   "Ya existe el campo Orden para el �rea base indicada");
				}
				rs.close();
				st.close();

			
			
			// ingresar
			st = cn.prepareStatement(rb.getString("area.actualizar"));
			i = 1;
			// ARENOMBRE=?,AREABREV=?,AREORDEN=? WHERE ARECODINST=? AND
			// ARECODMETOD=? AND AREVIGENCIA=? AND ARECODIGO=?
			st.setString(i++, item.getAreNombre());
			st.setString(i++, item.getAreAbreviatura());
			st.setLong(i++, item.getAreOrden());
			// w
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.executeUpdate();
			ingresarGradoArea(cn, item);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
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

	public void eliminarArea(AreaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// /validar
			st = cn.prepareStatement(rb.getString("area.validarEliminar.1"));
			i = 1;
			// ASICODINST=? AND ASICODMETOD=? AND ASIVIGENCIA=? AND ASICODAREA=?
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene asignaturas asociadas");
			}
			rs.close();
			st.close();

			// validar
			st = cn.prepareStatement(rb.getString("area.validarEliminar.2"));
			i = 1;
			st.setInt(i++, 2);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.setInt(i++, item.getAreVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene descriptores evaluativos asociados");
			}
			rs.close();
			st.close();

			// validar
			st = cn.prepareStatement(rb.getString("area.validarEliminar.3"));
			i = 1;
			st.setInt(i++, 2);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.setInt(i++, item.getAreVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene evaluacinn de área asociada");
			}
			rs.close();
			st.close();

			// validar
			st = cn.prepareStatement(rb.getString("area.validarEliminar.4"));
			i = 1;
			st.setInt(i++, 2);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.setInt(i++, item.getAreVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene evaluación de descriptores asociado");
			}
			rs.close();
			st.close();

			// ELIMINAR
			st = cn.prepareStatement(rb.getString("area.eliminarTodoGradoArea"));
			i = 1;
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.executeUpdate();
			st.close();
			// eliminar todo en jerarquia
			st = cn.prepareStatement(rb
					.getString("area.eliminarTodoGradoAreaJerarquia"));
			i = 1;
			st.setInt(i++, 2);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
			st.executeUpdate();
			st.close();
			// eliminar
			st = cn.prepareStatement(rb.getString("area.eliminar"));
			i = 1;
			// ARECODINST=? AND ARECODMETOD=? AND AREVIGENCIA=? AND ARECODIGO=?
			st.setLong(i++, item.getAreInstitucion());
			st.setInt(i++, item.getAreMetodologia());
			st.setInt(i++, item.getAreVigencia());
			st.setLong(i++, item.getAreCodigo());
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

	public void ingresarGradoArea(Connection cn, AreaVO item) throws Exception {
		PreparedStatement st = null, st2 = null, st3 = null;
		ResultSet rs = null;
		PreparedStatement st_jer = null;
		ResultSet rs_jer = null;
		int i = 1, posicion = 1;
		String aux = "", consulta;
		long dep = 0, mun = 0, loc = 0, cod = 0;
		try {
			// traer los datos de institucion
			st = cn.prepareStatement(rb.getString("common.datosInstitucion"));
			i = 1;
			st.setLong(i++, item.getAreInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				dep = rs.getLong(1);
				mun = rs.getLong(2);
				loc = rs.getLong(3);
			}
			rs.close();
			st.close();
			long[] grado = item.getAreGrado();
			for (i = 0; i < grado.length; i++) {
				if (grado[i] != -1) {
					aux += grado[i] + ",";
				}
			}
			if (aux.equals("") || aux.lastIndexOf(",") == -1) {
				// validar la eliminacion de todo
				st = cn.prepareStatement(rb.getString("area.validarEliminar.2"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen descriptores evaluativos asociados");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb.getString("area.validarEliminar.3"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de nrea asociada");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb.getString("area.validarEliminar.4"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de descriptores asociado");
				}
				rs.close();
				st.close();
				// ELIMINAR 
				st = cn.prepareStatement(rb
						.getString("area.eliminarTodoGradoArea"));
				posicion = 1;
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setInt(posicion++, item.getAreVigencia());
				st.setLong(posicion++, item.getAreCodigo());
				st.executeUpdate();
				st.close();
				// eliminar todo en jerarquia
				st = cn.prepareStatement(rb
						.getString("area.eliminarTodoGradoAreaJerarquia"));
				posicion = 1;
				st.setInt(posicion++, 2);
				st.setInt(posicion++, 4);
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setInt(posicion++, item.getAreVigencia());
				st.setLong(posicion++, item.getAreCodigo());
				st.executeUpdate();
				st.close();
			} else {
				aux = aux.substring(0, aux.lastIndexOf(","));
				// validar la eliminacinn de los que fueron quitados
				st = cn.prepareStatement(rb
						.getString("area.validarEliminarGradoArea.1.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("area.validarEliminarGradoArea.1.2"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen descriptores evaluativos asociados");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("area.validarEliminarGradoArea.2.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("area.validarEliminarGradoArea.2.2"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de área asociada");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("area.validarEliminarGradoArea.3.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("area.validarEliminarGradoArea.3.2"));
				i = 1;
				st.setInt(i++, 2);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAreInstitucion());
				st.setInt(i++, item.getAreMetodologia());
				st.setInt(i++, item.getAreVigencia());
				st.setLong(i++, item.getAreCodigo());
				st.setInt(i++, item.getAreVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de descriptores asociado");
				}
				rs.close();
				st.close();

				// eliminar los que fueron quitados
				consulta = rb.getString("area.eliminarGradoArea") + " (" + aux
						+ ")";
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setInt(posicion++, item.getAreVigencia());
				st.setLong(posicion++, item.getAreCodigo());
				st.executeUpdate();
				st.close();
				// eliminar los que fueron quitados en jerarquia
				consulta = rb.getString("area.eliminarGradoAreaJerarquia")
						+ " (" + aux + ")";
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setInt(posicion++, 2);
				st.setInt(posicion++, 4);
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setInt(posicion++, item.getAreVigencia());
				st.setLong(posicion++, item.getAreCodigo());
				st.executeUpdate();
				st.close();

				// traer el codigo de jerarquia inicial
				/*
				 * st=cn.prepareStatement(rb.getString("common.codigoJerarquia"))
				 * ; rs=st.executeQuery(); if(rs.next()){ cod=rs.getLong(1); }
				 * rs.close(); st.close();
				 */
				// traer la lista de items a ingresar
				consulta = rb.getString("area.listaGradoArea.ini") + aux
						+ rb.getString("area.listaGradoArea.fin");
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setLong(posicion++, item.getAreInstitucion());
				st.setInt(posicion++, item.getAreMetodologia());
				st.setInt(posicion++, item.getAreVigencia());
				st.setLong(posicion++, item.getAreCodigo());
				rs = st.executeQuery();
				st2 = cn.prepareStatement(rb
						.getString("area.insertarGradoArea"));
				st2.clearBatch();
				st3 = cn.prepareStatement(rb
						.getString("area.insertarGradoAreaJerarquia"));
				st3.clearBatch();

				while (rs.next()) {
					// grado_area
					posicion = 1;
					st2.setLong(posicion++, item.getAreInstitucion());
					st2.setInt(posicion++, item.getAreMetodologia());
					st2.setInt(posicion++, item.getAreVigencia());
					st2.setLong(posicion++, item.getAreCodigo());
					st2.setLong(posicion++, rs.getLong(1));
					st2.addBatch();
					// jerarquia
					posicion = 1;
					st3.setInt(posicion++, 2);
					st3.setInt(posicion++, 4);
					//
					//
					// traer el codigo de jerarquia inicial
					st_jer = cn.prepareStatement(rb
							.getString("common.codigoJerarquia"));
					rs_jer = st_jer.executeQuery();
					if (rs_jer.next()) {
						cod = rs_jer.getLong(1);
					}
					rs_jer.close();
					st_jer.close();

					//
					System.out.println("Nuevo cndigo jerarquia cod " + cod);

					st3.setLong(posicion++, cod);
					st3.setLong(posicion++, dep);
					st3.setLong(posicion++, mun);
					st3.setLong(posicion++, loc);
					st3.setLong(posicion++, item.getAreInstitucion());
					st3.setInt(posicion++, item.getAreMetodologia());
					st3.setInt(posicion++, item.getAreVigencia());
					st3.setLong(posicion++, rs.getLong(1));
					st3.setLong(posicion++, item.getAreCodigo());
					st3.addBatch();
				}
				st2.executeBatch();
				st3.executeBatch();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeStatement(st3);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getListaGradoAsignaturaBase(long inst, int met, int vig,
			long area, long asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("common.listaGradoAsignaturaBase"));
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, vig);
			st.setLong(i++, area);
			st.setLong(i++, asig);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre2(rs.getString(i++));// checked
				item.setPadre(rs.getLong(i++));// ih
				item.setCodigo2(rs.getString(i++));// checked Estado
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

	public List getListaAsignatura(FiltroAsignaturaVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		AsignaturaVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("asignatura.lista"));
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilArea());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new AsignaturaVO();
				item.setAsiInstitucion(rs.getLong(i++));
				item.setAsiMetodologia(rs.getInt(i++));
				item.setAsiVigencia(rs.getInt(i++));
				item.setAsiArea(rs.getLong(i++));
				item.setAsiCodigo(rs.getLong(i++));
				item.setAsiNombre(rs.getString(i++));
				item.setAsiAbreviatura(rs.getString(i++));
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

	public AsignaturaVO getAsignatura(AsignaturaVO asig) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		AsignaturaVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("asignatura.obtener"));
			st.setLong(i++, asig.getAsiInstitucion());
			st.setInt(i++, asig.getAsiMetodologia());
			st.setInt(i++, asig.getAsiVigencia());
			st.setLong(i++, asig.getAsiCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new AsignaturaVO();
				item.setFormaEstado("1");
				item.setFormaDisabled("disabled");
				item.setAsiInstitucion(rs.getLong(i++));
				item.setAsiMetodologia(rs.getInt(i++));
				item.setAsiVigencia(rs.getInt(i++));
				item.setAsiArea(rs.getLong(i++));
				item.setAsiCodigo(rs.getLong(i++));
				item.setAsiNombre(rs.getString(i++));
				item.setAsiAbreviatura(rs.getString(i++));
				item.setAsiOrden(rs.getLong(i++));
				item.setAsiEstado(rs.getString(i++));
				item.setPlan1(rs.getDouble(i++));
				item.setPlan2(rs.getDouble(i++));
				item.setPlan3(rs.getDouble(i++));
				item.setPlanciclo1(rs.getDouble(i++));
				item.setPlanciclo2(rs.getDouble(i++));
				item.setPlanciclo3(rs.getDouble(i++));
				item.setTipoponderacion(rs.getInt(i++));
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

	public AsignaturaVO ingresarAsignatura(AsignaturaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar ingresar
			st = cn.prepareStatement(rb.getString("asignatura.validarIngreso"));
			i = 1;
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception(
						"Ya existe una asignatura para la asignatura base indicada");
			}
			rs.close();
			st.close();
			// ingresar
			st = cn.prepareStatement(rb.getString("asignatura.insertar"));
			i = 1;
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiArea());
			st.setLong(i++, item.getAsiCodigo());
			st.setString(i++, item.getAsiNombre());
			st.setString(i++, item.getAsiAbreviatura());
			st.setLong(i++, item.getAsiOrden());
			st.setDouble(i++, item.getPlan1());
			st.setDouble(i++, item.getPlan2());
			st.setDouble(i++, item.getPlan3());
			st.setDouble(i++, item.getPlanciclo1());
			st.setDouble(i++, item.getPlanciclo2());
			st.setDouble(i++, item.getPlanciclo3());
			st.setInt(i++, item.getTipoponderacion());
			st.executeUpdate();
			ingresarGradoAsignatura(cn, item);
			cn.commit();
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
		return item;
	}

	/**
	 * @param cn
	 * @param item
	 * @throws Exception
	 */
	public void ingresarGradoAsignatura(Connection cn, AsignaturaVO item)
			throws Exception {
		PreparedStatement st = null, st2 = null, st3 = null;
		PreparedStatement st_jer = null;
		ResultSet rs = null;
		ResultSet rs_jer = null;
		int i = 1, posicion = 1;
		int eliminar = 1;
		String aux = "", consulta, grd;
		String[] ih = null;
		long dep = 0, mun = 0, loc = 0, cod = 0;
		try {
			// traer los datos de institucion
			st = cn.prepareStatement(rb.getString("common.datosInstitucion"));
			i = 1;
			st.setLong(i++, item.getAsiInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				dep = rs.getLong(1);
				mun = rs.getLong(2);
				loc = rs.getLong(3);
			}
			rs.close();
			st.close();
			String[] grado = item.getAsiGrado();
			for (i = 0; i < grado.length; i++) {
				grd = grado[i].split(":")[0];
				if (!grd.equals("-1")) {
					aux += grd + ",";
				}
			}
			if (aux.equals("") || aux.lastIndexOf(",") == -1) {
				// validar antes de eliminar todo
				// validar
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminar.1"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tiene logros asociados");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminar.2"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tiene evaluacinn de asignatura asociada");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminar.3"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tiene evaluacinn de logros asociado");
				}
				rs.close();
				st.close();

				// eliminar todo
				st = cn.prepareStatement(rb
						.getString("asignatura.eliminarTodoGradoAsignatura"));
				posicion = 1;
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiCodigo());
				st.executeUpdate();
				st.close();
				// eliminar todo en jerarquia
				st = cn.prepareStatement(rb
						.getString("asignatura.eliminarTodoGradoAsignaturaJerarquia"));
				posicion = 1;
				st.setInt(posicion++, 3);
				st.setInt(posicion++, 4);
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiCodigo());
				st.executeUpdate();
				st.close();

			} else {
				aux = aux.substring(0, aux.lastIndexOf(","));
				// validar la eliminacinn de los que fueron quitados
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminarGradoAsignatura.1.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("asignatura.validarEliminarGradoAsignatura.1.2"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen logros asociados");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminarGradoAsignatura.2.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("asignatura.validarEliminarGradoAsignatura.2.2"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de asignatura asociada");
				}
				rs.close();
				st.close();

				// validar
				st = cn.prepareStatement(rb
						.getString("asignatura.validarEliminarGradoAsignatura.3.1")
						+ " ("
						+ aux
						+ ")"
						+ rb.getString("asignatura.validarEliminarGradoAsignatura.3.2"));
				i = 1;
				st.setInt(i++, 3);
				st.setInt(i++, 4);
				st.setLong(i++, item.getAsiInstitucion());
				st.setInt(i++, item.getAsiMetodologia());
				st.setInt(i++, item.getAsiVigencia());
				st.setLong(i++, item.getAsiCodigo());
				st.setInt(i++, item.getAsiVigencia());
				rs = st.executeQuery();
				if (rs.next()) {
					throw new Exception(
							"Hay grados que no se pueden eliminar porque tienen evaluacinn de logro asociado");
				}
				rs.close();
				st.close();

				// eliminar los que quitaron
				consulta = rb.getString("asignatura.eliminarGradoAsignatura")
						+ " (" + aux + ")";
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiCodigo());
				st.executeUpdate();
				st.close();
				// eliminarlos de jerarquia
				consulta = rb
						.getString("asignatura.eliminarGradoAsignaturaJerarquia")
						+ " (" + aux + ")";
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setInt(posicion++, 3);
				st.setInt(posicion++, 4);
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiCodigo());
				st.executeUpdate();
				st.close();

				// traer el codigo de jerarquia inicial
				/*
				 * st=cn.prepareStatement(rb.getString("common.codigoJerarquia"))
				 * ; rs=st.executeQuery(); if(rs.next()){ cod=rs.getLong(1);
				 * }rs.close();st.close();
				 */
				// traer los que si
				consulta = rb.getString("asignatura.listaGradoAsignatura.ini")
						+ aux
						+ rb.getString("asignatura.listaGradoAsignatura.fin");
				st = cn.prepareStatement(consulta);
				posicion = 1;
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiArea());
				st.setLong(posicion++, item.getAsiInstitucion());
				st.setInt(posicion++, item.getAsiMetodologia());
				st.setInt(posicion++, item.getAsiVigencia());
				st.setLong(posicion++, item.getAsiCodigo());
				rs = st.executeQuery();
				st2 = cn.prepareStatement(rb
						.getString("asignatura.insertarGradoAsignatura"));
				st2.clearBatch();
				st3 = cn.prepareStatement(rb
						.getString("asignatura.insertarGradoAsignaturaJerarquia"));
				st3.clearBatch();

				while (rs.next()) {
					// GRAASICODINST, GRAASICODMETOD,
					// GRAASIVIGENCIA,GRAASICODGRADO, GRAASICODASIGN
					posicion = 1;
					st2.setLong(posicion++, item.getAsiInstitucion());
					st2.setInt(posicion++, item.getAsiMetodologia());
					st2.setInt(posicion++, item.getAsiVigencia());
					st2.setLong(posicion++, rs.getLong(1));
					st2.setLong(posicion++, item.getAsiCodigo());
					st2.addBatch();

					//
					// traer el codigo de jerarquia inicial
					st_jer = cn.prepareStatement(rb
							.getString("common.codigoJerarquia"));
					rs_jer = st_jer.executeQuery();
					if (rs_jer.next()) {
						cod = rs_jer.getLong(1);
					}
					rs_jer.close();
					st_jer.close();
					//

					System.out.println("cod jerar para asignatura  cod:" + cod);
					// jerarquia
					posicion = 1;
					st3.setInt(posicion++, 3);
					st3.setInt(posicion++, 4);
					st3.setLong(posicion++, cod);
					st3.setLong(posicion++, dep);
					st3.setLong(posicion++, mun);
					st3.setLong(posicion++, loc);
					st3.setLong(posicion++, item.getAsiInstitucion());
					st3.setInt(posicion++, item.getAsiMetodologia());
					st3.setInt(posicion++, item.getAsiVigencia());
					st3.setLong(posicion++, rs.getLong(1));
					st3.setLong(posicion++, item.getAsiCodigo());
					st3.addBatch();
					System.out.println("mmmm: " + cod + "//" + dep + "//" + mun
							+ "//" + loc + "//" + item.getAsiInstitucion()
							+ "//" + item.getAsiMetodologia() + "//"
							+ item.getAsiVigencia() + "//"
							+ item.getAsiCodigo() + "//" + rs.getLong(1));
				}
				st2.executeBatch();
				st3.executeBatch();
				st2.close();
				st3.close();
				// falta actualizar todo por si cambiaron la IH
				st2 = cn.prepareStatement(rb
						.getString("asignatura.actualizarGradoAsignatura"));
				for (i = 0; i < grado.length; i++) {
					System.out.println("**********************GRADO ASIG: "
							+ grado[i]);
					ih = grado[i].split(":");
					// System.out.println("**********************GRADO ASIG IH : "+ih[0]);
					if (ih != null && ih.length > 1 && !ih[0].equals("-1")) {
						posicion = 1;
						st2.setInt(posicion++, Integer.parseInt(ih[1]));
						st2.setString(posicion++, ih[2]);
						st2.setLong(posicion++, item.getAsiInstitucion());
						st2.setInt(posicion++, item.getAsiMetodologia());
						st2.setInt(posicion++, item.getAsiVigencia());
						st2.setLong(posicion++, Long.parseLong(ih[0]));
						st2.setLong(posicion++, item.getAsiCodigo());
						st2.addBatch();
					}
				}
				st2.executeBatch();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeStatement(st3);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public AsignaturaVO actualizarAsignatura(AsignaturaVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// ingresar
			st = cn.prepareStatement(rb.getString("asignatura.actualizar"));
			i = 1;
			// ASINOMBRE=?,ASIORDEN=?,ASIABREV=? WHERE ASICODINST=? AND
			// ASICODMETOD=? AND ASIVIGENCIA=? AND ASICODIGO=?
			st.setString(i++, item.getAsiNombre());
			st.setString(i++, item.getAsiAbreviatura());
			st.setLong(i++, item.getAsiOrden());
			st.setDouble(i++, item.getPlan1());
			st.setDouble(i++, item.getPlan2());
			st.setDouble(i++, item.getPlan3());
			st.setDouble(i++, item.getPlanciclo1());
			st.setDouble(i++, item.getPlanciclo2());
			st.setDouble(i++, item.getPlanciclo3());
			st.setInt(i++, item.getTipoponderacion());
			// w
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.executeUpdate();
			ingresarGradoAsignatura(cn, item);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
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

	public void eliminarAsignatura(AsignaturaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validar
			st = cn.prepareStatement(rb
					.getString("asignatura.validarEliminar.1"));
			i = 1;
			st.setInt(i++, 3);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.setInt(i++, item.getAsiVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene logros asociados");
			}
			rs.close();
			st.close();

			// validar
			st = cn.prepareStatement(rb
					.getString("asignatura.validarEliminar.2"));
			i = 1;
			st.setInt(i++, 3);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.setInt(i++, item.getAsiVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene evaluacinn de asignatura asociada");
			}
			rs.close();
			st.close();

			// validar
			st = cn.prepareStatement(rb
					.getString("asignatura.validarEliminar.3"));
			i = 1;
			st.setInt(i++, 3);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.setInt(i++, item.getAsiVigencia());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Tiene evaluacinn de logros asociado");
			}
			rs.close();
			st.close();

			// eliminar gradoAsig
			st = cn.prepareStatement(rb
					.getString("asignatura.eliminarTodoGradoAsignatura"));
			i = 1;
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.executeUpdate();
			st.close();
			// eliminar todo en jerarquia
			st = cn.prepareStatement(rb
					.getString("asignatura.eliminarTodoGradoAsignaturaJerarquia"));
			i = 1;
			st.setInt(i++, 3);
			st.setInt(i++, 4);
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
			st.executeUpdate();
			st.close();

			// eliminar
			st = cn.prepareStatement(rb.getString("asignatura.eliminar"));
			i = 1;
			st.setLong(i++, item.getAsiInstitucion());
			st.setInt(i++, item.getAsiMetodologia());
			st.setInt(i++, item.getAsiVigencia());
			st.setLong(i++, item.getAsiCodigo());
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

	public int actualizarMasivoPonderacionesAsignatura(long idInst,
			int vigencia, int tipoPonderacion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// ingresar
			st = cn.prepareStatement(rb
					.getString("asignatura.actualizarMasivoPonderaciones"));
			i = 1;
			// set
			st.setInt(i++, tipoPonderacion);
			st.setInt(i++, 0);
			st.setInt(i++, 0);
			st.setInt(i++, 0);
			st.setInt(i++, 0);
			st.setInt(i++, 0);
			st.setInt(i++, 0);
			// where
			st.setLong(i++, idInst);
			st.setInt(i++, vigencia);
			st.executeUpdate();

			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 1;
	}

	public Collection valorvalplans(String a, String b, String c, String d,
			String el, String fl) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("select * from (select graasicodgrado,count(plan1),count(plan2),count(plan3),count(planciclo1),count(planciclo2),count(planciclo3),count(*),100-sum(nvl(plan1,0)),100-sum(nvl(plan2,0)),100-sum(nvl(plan3,0)),100-sum(nvl(planciclo1,0)),100-sum(nvl(planciclo2,0)),100-sum(nvl(planciclo3,0)) from grado_asignatura,asignatura where graasicodinst = ? AND graasicodgrado between ? and ? AND graasicodmetod = ? AND graasivigencia = ? and asicodarea=? and asicodinst=graasicodinst and asicodmetod=graasicodmetod and asivigencia=graasivigencia AND graasicodasign = asicodigo group by graasicodgrado) where rownum=1");
			pst.setString(1, a);
			pst.setString(2, b);
			pst.setString(3, c);
			pst.setString(4, d);
			pst.setString(5, el);
			pst.setString(6, fl);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0, f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (j = 1; j <= m; j++) {
					o[j - 1] = rs.getString(j);
				}
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list2;
	}

	public Collection valorvalplansins(String a, String b, String c, String d)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("select count(p),count(*),100-nvl(sum(p),0) from (select distinct asicodigo,plan1 p from grado_asignatura,asignatura where graasicodinst = ? AND graasicodmetod = ? AND graasivigencia = ? and asicodarea=? and asicodinst=graasicodinst and asicodmetod=graasicodmetod and asivigencia=graasivigencia AND graasicodasign = asicodigo)");
			pst.setString(1, a);
			pst.setString(2, b);
			pst.setString(3, c);
			pst.setString(4, d);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0, f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (j = 1; j <= m; j++) {
					o[j - 1] = rs.getString(j);
				}
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return list2;
	}

}
