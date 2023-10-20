package siges.rotacion2.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.rotacion.beans.Horario;
import siges.rotacion2.beans.Rotacion2;

/**
 * @author Administrador
 * 
 *         Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de
 *         cndigo
 */
public class Rotacion2DAO extends siges.dao.Dao {

	private Cursor cursor;

	public String sentencia;

	public String mensaje;

	public String buscar;

	private ResourceBundle rbRot;

	public int[] resultado;

	public String[] dias = { "", "Lunes", "Martes", "Miercoles", "Jueves",
			"Viernes" };

	/**
	 * Constructor de la clase
	 */
	public Rotacion2DAO(Cursor cur) {
		super(cur);
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		rbRot = ResourceBundle.getBundle("siges.rotacion2.bundle.rotacion2");
	}

	public void ponerReporte(String us, String rec, String tipo, String nombre,
			int modulo) {
		PreparedStatement pst = null;
		Connection cn = null;
		try {
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot
					.getString("ReportePlantillaInsertar"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(us));
			pst.setString(posicion++, rec);
			pst.setString(posicion++, (tipo));
			pst.setString(posicion++, nombre);
			pst.setInt(posicion++, (modulo));
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando Guardar reporte. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
	}

	public String[] obtenerEspAsigGrado(Rotacion2 r) {
		String[] str = new String[2];
		PreparedStatement pst = null;
		Connection cn = null;
		ResultSet rs = null;
		try {
			long inst = r.getInstitucion();
			long sede = r.getSede();
			long jor = r.getJornada();
			int posicion = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rbRot.getString("Asignar.EspAsigGrado"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			rs = pst.executeQuery();
			if (rs.next()) {
				str[0] = rs.getString(1);
				str[1] = rs.getString(2);
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando asignar informacion de jerarquia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * Funcinn: Ejecuta un consulta en la base de datos y devuelte el resultado<br>
	 * 
	 * @param Collection
	 *            lista
	 * @return String[]
	 */
	public String[] getFiltroArray(Collection a) throws InternalErrorException {
		if (a != null && !a.isEmpty()) {
			String[] m = new String[a.size()];
			String z;
			int i = 0;
			Iterator iterator = a.iterator();
			Object[] o;
			while (iterator.hasNext()) {
				z = "";
				o = (Object[]) iterator.next();
				for (int j = 0; j < o.length; j++)
					z += (o[j] != null ? o[j] : "") + "|";
				m[i++] = z.substring(0, z.lastIndexOf("|"));
			}
			return m;
		}
		return null;
	}

	public Collection getDocentesFaltantes(Rotacion2 r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = r.getInstitucion();
			long sede = r.getSede();
			long jor = r.getJornada();
			long gra = r.getGrado();
			long met = r.getMetodologia();
			long vig = r.getVigencia();
			cn = cursor.getConnection();
			if (gra == -9) {
				pst = cn.prepareStatement(rbRot
						.getString("Lista.DocentesFaltantes"));
				pst.clearParameters();
				int pos = 1;
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
				pst.setLong(pos++, vig);
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
			} else {
				pst = cn.prepareStatement(rbRot
						.getString("Lista.DocentesFaltantesGrado"));
				pst.clearParameters();
				int pos = 1;
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
				pst.setLong(pos++, gra);
				pst.setLong(pos++, vig);
				pst.setLong(pos++, inst);
				pst.setLong(pos++, sede);
				pst.setLong(pos++, jor);
				pst.setLong(pos++, met);
				pst.setLong(pos++, gra);
			}
			rs = pst.executeQuery();
			list = getCollection(rs);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Collection getDocentesSinCarga(Rotacion2 r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = r.getInstitucion();
			long sede = r.getSede();
			long jor = r.getJornada();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Lista.DocentesSinCarga"));
			pst.clearParameters();
			int pos = 1;
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setLong(pos++, jor);
			rs = pst.executeQuery();
			list = getCollection(rs);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Collection getReportes(Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			String institucion = l.getInstId();
			String sede = l.getSedeId();
			String jor = l.getJornadaId();
			cn = cursor.getConnection();
			if (sede.equals("")) {
				pst = cn.prepareStatement(rbRot.getString("listaReporte1"));
				pst.clearParameters();
				int posicion = 1;
				pst.setLong(posicion++, Long.parseLong(institucion));
			} else {
				pst = cn.prepareStatement(rbRot.getString("listaReporte2"));
				pst.clearParameters();
				int posicion = 1;
				pst.setLong(posicion++, Long.parseLong(institucion));
				pst.setLong(posicion++, Long.parseLong(sede));
				pst.setLong(posicion++, Long.parseLong(jor));
			}
			rs = pst.executeQuery();
			list = getCollection(rs);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public long[][] getLong(String[][] a) {
		long[][] b = new long[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				b[i][j] = Long.parseLong(a[i][j]);
			}
		}
		return b;
	}

	public String[][] getAsignado(Rotacion2 r) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String asignacion[][] = null;
		try {
			long gru = r.getJergrupo();
			long asig = r.getAsignatura();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("rotacion2.isAsignado"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, gru);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			rs = pst.executeQuery();
			asignacion = getFiltroMatriz(getCollection(rs));
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception("Error Interno GetAsignado:" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error SQL getAsignado:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Excepcion getAsignado:" + e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return asignacion;
	}

	public void actualizarPlantillas(String us, String rec, int estado,
			String mensaje) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rbRot
					.getString("plantillaHilo.actualizar"));
			int posicion = 1;
			ps.setInt(posicion++, estado);
			ps.setString(posicion++, mensaje);
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar plantillas. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return;
	}

	public void actualizarTodos() {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rbRot.getString("rotacion2.todos"));
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar Todos los reportes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return;
	}

	public void borrarSolicitud(String id) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			long cod = Long.parseLong(id);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarSolicitud"));
			ps.clearParameters();
			ps.setLong(1, cod);
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando borrar solicitus: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return;
	}

	public boolean isCompleto(Rotacion2 r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long gru = r.getJergrupo();
			long asig = r.getAsignatura();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("rotacion2.isCompleto"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, gru);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			pst.setLong(posicion++, asig);
			rs = pst.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean isLocked(Rotacion2 r) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			System.out
					.println("valores de la solicitud: " + r.getMetodologia());
			long institucion = r.getInstitucion();
			int sede = r.getSede();
			int jor = r.getJornada();
			int met = r.getMetodologia();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("Reportelocked"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setInt(posicion++, sede);
			pst.setInt(posicion++, jor);
			pst.setInt(posicion++, met);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setEstadoGrupos(Rotacion2 r, int estado) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long institucion = r.getInstitucion();
			long sede = r.getSede();
			long jor = r.getJornada();
			long met = r.getMetodologia();
			long gra = r.getGrado_();
			long gru = r.getGrupo_();
			long usu = r.getUsuario();
			int posicion = 1;
			int n = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (gra == -9 && gru == -9) {// toda la sede-jornada
				// System.out.println("Entra a cuadrar Grupos 1");
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarGrupo1"));
				pst.clearParameters();
				posicion = 1;
				pst.setInt(posicion++, estado);
				pst.setLong(posicion++, usu);
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
			}
			if (gra != -9 && gru == -9) {// todo el grado
				// System.out.println("Entra a cuadrar Grupos 2");
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarGrupo2"));
				pst.clearParameters();
				posicion = 1;
				pst.setInt(posicion++, estado);
				pst.setLong(posicion++, usu);
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
			}
			if (gra != -9 && gru != -9) {// todo el grupo
				// System.out.println("Entra a cuadrar Grupos 3");
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarGrupo3"));
				pst.clearParameters();
				posicion = 1;
				pst.setInt(posicion++, estado);
				pst.setLong(posicion++, usu);
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
			}
			n = pst.executeUpdate();
			// System.out.println("Actualiza Grupos "+n);
			pst.close();
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			throw new Exception("Error Interno Set Estado:" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error SQL Set Estado:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Excepcion Set Estado:" + e.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void setReporte(Rotacion2 r) {
		Connection cn = null;
		PreparedStatement pst = null;
		// Collection list = null;
		try {
			long institucion = r.getInstitucion();
			long usuario = r.getUsuario();
			int sede = r.getSede();
			int jor = r.getJornada();
			int gra = r.getGrado();
			int gru = r.getGrupo_();
			int met = r.getMetodologia();
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rbRot.getString("ReporteInsertar"));
			pst.clearParameters();
			int posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setInt(posicion++, sede);
			pst.setInt(posicion++, jor);
			pst.setInt(posicion++, met);
			pst.setInt(posicion++, gra);
			pst.setInt(posicion++, gru);
			pst.setLong(posicion++, usuario);
			pst.setLong(posicion++, r.getVigencia());
			pst.executeUpdate();
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizarRotacion(Rotacion2 r, int estado, String mensaje,
			int estado2) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			if (estado == 0)
				ps = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarId0"));
			else
				ps = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarId1"));
			int posicion = 1;
			ps.setInt(posicion++, estado);
			ps.setString(posicion++, mensaje);
			ps.setLong(posicion++, r.getIdRotacion());
			ps.setLong(posicion++, r.getUsuario());
			ps.setInt(posicion++, estado2);
			ps.executeUpdate();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar rotacion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return;
	}

	public boolean validarUltimoGrupo(Horario h, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long id = Long.parseLong(h.getIdSolicitud());
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long resp = Long.parseLong(l.getUsuarioId());
			long graRotacion = Long.parseLong(h.getGradoRotacion());
			long gruRotacion = Long.parseLong(h.getGrupoRotacion());
			long vig = h.getVigencia();
			int posicion = 0, n = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// VERIFICAR SI QUEDAN GRUPOS SIN OFICIALIZAR O RECHAZAR PARA VER SI
			if (graRotacion == -9 && gruRotacion == -9) {// todos
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.listoGrupo1"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
			}
			if (graRotacion != -9 && gruRotacion == -9) {// grado
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.listoGrupo2"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, graRotacion);
			}
			if (graRotacion != -9 && gruRotacion != -9) {// grupo
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.listoGrupo3"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, graRotacion);
				pst.setLong(posicion++, gruRotacion);
			}
			rs = pst.executeQuery();
			int cant = -1;
			if (rs.next()) {
				cant = rs.getInt(1);
			}
			rs.close();
			pst.close();
			if (cant == 0) {// SIGNIFICA QUE NO HAY MAS GRUPOS Y TOCA ACTUALIZAR
				// TODA LA SOLICITUD
				// BORRA LAS INCONSISTENCIAS PARA ESTE ID-USUARIO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarInconsistencias"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, id);
				n = pst.executeUpdate();
				// System.out.println("Borra inconsistecias "+n);
				pst.close();
				// PONE EN 0 LA IH PROPUESTA PARA TODOS LOS DOCENTES DE ESA
				// INST-SEDE-JORN
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.limpiarIhPropuesta"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, vig);
				n = pst.executeUpdate();
				// System.out.println("Borra IHREAL="+n);
				pst.close();
				// CAMBIA DE ESTADO A LA SOLICITUD
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.actualizarId2"));
				pst.clearParameters();
				posicion = 1;
				pst.setInt(posicion++, 3);
				pst.setString(posicion++, "");
				pst.setLong(posicion++, resp);
				pst.setString(posicion++, h.getIdSolicitud());
				pst.setInt(posicion++, 1);
				pst.setLong(posicion++, vig);
				n = pst.executeUpdate();
				// System.out.println(h.getIdSolicitud()+"CAMBIA ESTADO DE
				// SOLICITUD="+n);
				cn.commit();
				return true;
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean aceptarHorarioGrupo(Horario h, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long vig = h.getVigencia();
			long resp = Long.parseLong(l.getUsuarioId());
			int posicion = 0, n = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("Oficializar horario Grupo");
			// BORRA EL HORARIO OFICIAL
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarOficialGrupo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println("Eliminados "+n);
			pst.close();
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.insertarOficialGrupo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, vig);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			n = pst.executeUpdate();
			// System.out.println("Insertados "+n);
			pst.close();
			// BORRA EL HORARIO PROPUESTO QUE YA SE OFICIALIZO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarPropuestoGrupo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println("Limpia Propuesto"+n);
			pst.close();
			// CAMBIA DE ESTADO AL GRUPO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.actualizarGrupo3"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 3);
			pst.setLong(posicion++, resp);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			n = pst.executeUpdate();
			// System.out.println(h.getIdSolicitud()+"CAMBIA ESTADO DE
			// GRUPO="+n);
			pst.close();
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean rechazarHorarioGrupo(Horario h, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			// long vig = h.getVigencia();
			long resp = Long.parseLong(l.getUsuarioId());
			int posicion = 0, n = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("Rechazar horario Grupo");
			// BORRA EL HORARIO PROPUESTO QUE YA SE OFICIALIZO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarPropuestoGrupo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			n = pst.executeUpdate();
			// System.out.println("Limpia Propuesto"+n);
			pst.close();
			// CAMBIA DE ESTADO AL GRUPO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.actualizarGrupo3"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 4);
			pst.setLong(posicion++, resp);
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, met);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			n = pst.executeUpdate();
			// System.out.println(h.getIdSolicitud()+"CAMBIA ESTADO DE
			// GRUPO="+n);
			pst.close();
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * Returns the number of open cursors on an Oracle Database.
	 * 
	 * @return Number of currently open cursors - note that this method will
	 *         produce one of these but it cleanly closes it so will not
	 *         accumulate them.
	 * @param enhanceConnection
	 *            Connection to the Oracle Database
	 */
	public static int getCurrentOpenCursors(Connection conn) {
		PreparedStatement psQuery = null;
		ResultSet rs = null;
		int cursors = -1;
		try {
			String sqlQuery = "select count(*) AS COUNT from v$open_cursor where user_name like 'REDP0608'";
			psQuery = conn.prepareStatement(sqlQuery);
			rs = psQuery.executeQuery();
			if (rs.next()) {
				cursors = rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			System.out
					.println("SQLException in getCurrentOpenCursors(Connection conn): "
							+ e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psQuery != null) {
					psQuery.close();
				}
			} catch (SQLException ex) {
				System.out
						.println("A SQLException error has occured in getCurrentOpenCursors(Connection conn): "
								+ ex.getMessage());
				ex.printStackTrace();
			}
		}
		return cursors;
	}

	public Collection getValidacionEspacios(Rotacion2 r, long id) {
		Connection cn = null;
		PreparedStatement pst = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			long inst = r.getInstitucion();
			long sede = r.getSede();
			long jor = r.getJornada();
			int met = r.getMetodologia();
			int gra = r.getGrado();
			long vig = r.getVigencia();
			cn = cursor.getConnection();
			cst = cn.prepareCall(rbRot.getString("Lista.ValidarEspacios"));
			cst.clearParameters();
			int pos = 1;
			cst.setLong(pos++, id);
			cst.setLong(pos++, inst);
			cst.setLong(pos++, sede);
			cst.setLong(pos++, jor);
			cst.setInt(pos++, met);
			cst.setInt(pos++, gra);
			cst.setLong(pos++, vig);
			cst.executeUpdate();
			cst.close();
			pst = cn.prepareStatement(rbRot.getString("Lista.ValidarEspacios2"));
			pos = 1;
			pst.setLong(pos++, id);
			pst.setLong(pos++, inst);
			pst.setLong(pos++, sede);
			pst.setInt(pos++, met);
			pst.setLong(pos++, vig);
			rs = pst.executeQuery();
			list = getCollection(rs);
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeStatement(cst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean setHorarioPropuesto(Horario h, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long id = Long.parseLong(h.getIdSolicitud());
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long vig = h.getVigencia();
			long resp = Long.parseLong(l.getUsuarioId());
			int posicion = 0, n = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (h.getGrado().equals("-9") && h.getGrupo().equals("-9")) {// todo
				// System.out.println("Oficializar horario total");
				// BORRA EL HORARIO OFICIAL
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarOficial"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, vig);
				n = pst.executeUpdate();
				// System.out.println("Eliminados "+n);
				pst.close();
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.insertarOficial"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				n = pst.executeUpdate();
				// System.out.println("Insertados "+n);
				pst.close();
				// BORRA EL HORARIO PROPUESTO QUE YA SE OFICIALIZO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuesto"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				n = pst.executeUpdate();
				// System.out.println("Limpia Propuesto"+n);
				pst.close();
			}
			if (!h.getGrado().equals("-9") && h.getGrupo().equals("-9")) {// grado
				// System.out.println("Oficializar horario Grado");
				// BORRA EL HORARIO OFICIAL
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarOficialGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, vig);
				n = pst.executeUpdate();
				// System.out.println("Eliminados "+n);
				pst.close();
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.insertarOficialGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				n = pst.executeUpdate();
				// System.out.println("Insertados "+n);
				pst.close();
				// BORRA EL HORARIO PROPUESTO QUE YA SE OFICIALIZO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuestoGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				n = pst.executeUpdate();
				// System.out.println("Limpia Propuesto"+n);
				pst.close();
			}
			if (!h.getGrado().equals("-9") && !h.getGrupo().equals("-9")) {// grupo
				// System.out.println("Oficializar horario Grupo");
				// BORRA EL HORARIO OFICIAL
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarOficialGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
				pst.setLong(posicion++, vig);
				n = pst.executeUpdate();
				// System.out.println("Eliminados "+n);
				pst.close();
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.insertarOficialGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
				n = pst.executeUpdate();
				// System.out.println("Insertados "+n);
				pst.close();
				// BORRA EL HORARIO PROPUESTO QUE YA SE OFICIALIZO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuestoGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
				n = pst.executeUpdate();
				// System.out.println("Limpia Propuesto"+n);
				pst.close();
			}
			// BORRA LAS INCONSISTENCIAS PARA ESTE ID-USUARIO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarInconsistencias"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			n = pst.executeUpdate();
			// System.out.println("Borra inconsistecias "+n);
			pst.close();
			// PONE EN 0 LA IH PROPUESTA PARA TODOS LOS DOCENTES DE ESA
			// INST-SEDE-JORN
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.limpiarIhPropuesta"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println("Borra IHREAL="+n);
			pst.close();
			// CAMBIA DE ESTADO A LA SOLICITUD
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.actualizarId2"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 3);
			pst.setString(posicion++, "");
			pst.setLong(posicion++, resp);
			pst.setString(posicion++, h.getIdSolicitud());
			pst.setInt(posicion++, 1);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println(h.getIdSolicitud()+"CAMBIA ESTADO DE
			// SOLICITUD="+n);
			pst.close();
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean setHorarioOficial(Horario h, Login l) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long id = Long.parseLong(h.getIdSolicitud());
			long institucion = Long.parseLong(h.getInst());
			long sede = Long.parseLong(h.getSede());
			long jor = Long.parseLong(h.getJornada());
			long met = Long.parseLong(h.getMetodologia());
			long gra = Long.parseLong(h.getGrado());
			long gru = Long.parseLong(h.getGrupo());
			long resp = Long.parseLong(l.getUsuarioId());
			long vig = h.getVigencia();
			int posicion = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// System.out.println("VALORES:::"+institucion+"//"+sede+"//"+jor+"//"+met+"//"+gra+"//"+gru+"//"+resp+"//"+vig);
			if (gra == -9 && gru == -9) {// todo
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuesto"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
			}
			if (gra != -9 && gru == -9) {// grado
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuestoGrado"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
			}
			if (gra != -9 && gru != -9) {// grupo
				// BORRA EL HORARIO PROPUESTO
				pst = cn.prepareStatement(rbRot
						.getString("rotacion2.borrarPropuestoGrupo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, institucion);
				pst.setLong(posicion++, sede);
				pst.setLong(posicion++, jor);
				pst.setLong(posicion++, met);
				pst.setLong(posicion++, gra);
				pst.setLong(posicion++, gru);
			}
			int n = pst.executeUpdate();
			// System.out.println("Eliminados "+n);
			pst.close();
			// BORRA LAS INCONSISTENCIAS PARA ESTE ID-USUARIO
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.borrarInconsistencias"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, id);
			n = pst.executeUpdate();
			// System.out.println("Borra inconsistecias "+n);
			pst.close();
			// PONE EN 0 LA IH PROPUESTA PARA TODOS LOS DOCENTES DE ESA
			// INST-SEDE-JORN
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.limpiarIhPropuesta"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, sede);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println("Borra IHREAL="+n);
			pst.close();
			// CAMBIA DE ESTADO A LA SOLICITUD
			pst = cn.prepareStatement(rbRot
					.getString("rotacion2.actualizarId2"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, 4);
			pst.setString(posicion++, "");
			pst.setLong(posicion++, resp);
			pst.setString(posicion++, h.getIdSolicitud());
			pst.setInt(posicion++, 1);
			pst.setLong(posicion++, vig);
			n = pst.executeUpdate();
			// System.out.println(h.getIdSolicitud()+"CAMBIA ESTADO DE
			// SOLICITUD="+n);
			pst.close();
			//
			cn.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}