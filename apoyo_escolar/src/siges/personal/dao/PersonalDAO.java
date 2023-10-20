package siges.personal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.estudiante.beans.AsistenciaVO;
import siges.estudiante.beans.Convivencia;
import siges.estudiante.beans.Salud;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.personal.beans.Carga;
import siges.personal.beans.CargaHorariaTotal;
import siges.personal.beans.FormacionVO;
import siges.personal.beans.GrupoVO;
import siges.personal.beans.LaboralVO;
import siges.personal.beans.Personal;

public class PersonalDAO extends Dao {
	private Cursor cursor;

	public String sentencia;

	public String mensaje;

	public String buscar;

	private ResourceBundle rb;

	private java.text.SimpleDateFormat sdf;
	
	private Connection cn = null;

	/**
	 * Constructor de la clase
	 */
	public PersonalDAO(Cursor cur) {
		super(cur);
		this.cursor = cur;
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("siges.personal.bundle.personal");
	}

	public Carga asignarFijarDocente(String id, String id2, String id3,
			String inst) {
		Carga r = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		Collection list = new ArrayList();
		Object[] o;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Asignar.FijarDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			pst.setLong(posicion++, Long.parseLong(id3.trim()));
			System.out.println("VALE: " + id + " - " + id2 + " - " + id3);
			rs = pst.executeQuery();
			if (rs.next()) {
				r = new Carga();
				int i = 1;
				r.setEstado("1");
				r.setRotdagjerjornada(rs.getString(i++));
				r.setRotdagdocente(rs.getString(i++));
				r.setRotdagasignatura(rs.getString(i++));
				r.setRotdagsede(rs.getString(i++));
				r.setRotdagjornada(rs.getString(i++));
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb.getString("Asignar.FijarDocenteGrado"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, Long.parseLong(inst.trim()));
			pst.setLong(posicion++, Long.parseLong(r.getRotdagsede().trim()));
			pst.setLong(posicion++, Long.parseLong(r.getRotdagjornada().trim()));
			rs = pst.executeQuery();
			while (rs.next()) {
				int j = 1;
				o = new Object[1];
				o[0] = rs.getString(j++);
				list.add(o);
				r.setRotdaghoras(rs.getString(j++));
			}
			r.setRotdaggrados(getFiltroArray(list));
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return r;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return r;
	}

	public boolean compararBeans(Salud s, Salud s2) {
		if (!s.getSaltipoperso().equals(s2.getSaltipoperso()))
			return false;
		if (!s.getSalcodperso().equals(s2.getSalcodperso()))
			return false;
		if (!s.getSaltiposangre().equals(s2.getSaltiposangre()))
			return false;
		if (!s.getSaleps().equals(s2.getSaleps()))
			return false;
		if (!s.getSalars().equals(s2.getSalars()))
			return false;
		if (!s.getSalalergias().equals(s2.getSalalergias()))
			return false;
		if (!s.getSalenfermedades().equals(s2.getSalenfermedades()))
			return false;
		if (!s.getSalmedicamentos().equals(s2.getSalmedicamentos()))
			return false;
		if (!s.getSaltelemerg().equals(s2.getSaltelemerg()))
			return false;
		if (!s.getSalperemerg().equals(s2.getSalperemerg()))
			return false;
		if (!s.getSalestado().equals(s2.getSalestado()))
			return false;
		return true;
	}

	public boolean compararBeans(AsistenciaVO a, AsistenciaVO a2) {
		if (!a.getAsiTipoPer().equals(a2.getAsiTipoPer()))
			return false;
		if (!a.getAsiCodPer().equals(a2.getAsiCodPer()))
			return false;
		if (!a.getAsiCodigo().equals(a2.getAsiCodigo()))
			return false;
		if (!a.getAsiFecha().equals(a2.getAsiFecha()))
			return false;
		if (!a.getAsiMotivo().equals(a2.getAsiMotivo()))
			return false;
		if (!a.getAsiObservacion().equals(a2.getAsiObservacion()))
			return false;
		if (!a.getAsiJustificada().equals(a2.getAsiJustificada()))
			return false;
		return true;
	}

	public boolean compararBeans(LaboralVO l, LaboralVO l2) {
		if (!l.getLabCodPerso().equals(l2.getLabCodPerso()))
			return false;
		if (!l.getLabId().equals(l2.getLabId()))
			return false;
		if (!l.getLabNomEntid().equals(l2.getLabNomEntid()))
			return false;
		if (!l.getLabFechaInicio().equals(l2.getLabFechaInicio()))
			return false;
		if (!l.getLabFechaFinal().equals(l2.getLabFechaFinal()))
			return false;
		if (!l.getLabCargo().equals(l2.getLabCargo()))
			return false;
		if (!l.getLabDescripcion().equals(l2.getLabDescripcion()))
			return false;
		if (!l.getLabContacto().equals(l2.getLabContacto()))
			return false;
		if (!l.getLabTelefono().equals(l2.getLabTelefono()))
			return false;
		return true;
	}

	public boolean compararBeans(Carga r, Carga r2) {
		int cont;
		if (!r.getRotdagjornada().equals(r2.getRotdagjornada()))
			return false;
		if (!r.getRotdagdocente().equals(r2.getRotdagdocente()))
			return false;
		if (!r.getRotdagasignatura().equals(r2.getRotdagasignatura()))
			return false;
		if (!r.getRotdaghoras().equals(r2.getRotdaghoras()))
			return false;
		if (r.getRotdaggrados() == null && r2.getRotdaggrados() != null)
			return false;
		if (r.getRotdaggrados() != null && r2.getRotdaggrados() == null)
			return false;
		if (r.getRotdaggrados() != null && r2.getRotdaggrados() != null) {
			cont = 0;
			if (r.getRotdaggrados().length != r2.getRotdaggrados().length)
				return false;
			else {
				for (int a = 0; a < r.getRotdaggrados().length; a++) {
					if (!r.getRotdaggrados()[a].equals(r2.getRotdaggrados()[a])) {
						cont++;
						;
					}
				}
				if (!(r.getRotdaggrados().length == cont)) {
					return false;
				}
			}
		}
		return true;
	}

	public Collection getLaborales(Personal p) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaLaborales"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información laboral. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public Collection getAsistencias(String tipo, Personal p) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaAsistencias"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(tipo));
			ps.setString(posicion++, (p.getPernumdocum()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información acadnmica. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public boolean existePersona(String id, String id2) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("personal.existe"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.setLong(posicion++, Long.parseLong(id2.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				return true;
			}
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			setMensaje("Error intentando buscar de personal. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}

	public boolean eliminarLaboral(Personal p, String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("LaboralEliminar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR laboral. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarAsistencia(Personal p, String tipo, String id) {
		System.out.println("eliminarAsistencia ");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaEliminar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(tipo));
			System.out.println("Long.parseLong(tipo) " + Long.parseLong(tipo));

			pst.setString(posicion++, (p.getPernumdocum()));
			System.out.println("p.getPernumdocum() " + p.getPernumdocum());

			pst.setLong(posicion++, Long.parseLong(id));
			System.out.println("Long.parseLong(id) " + Long.parseLong(id));

			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			System.out.println("" + in.getMensaje());
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("" + sqle.getMessage());
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR asistencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Eliminar Convivencia<br>
	 * 
	 * @param String
	 *            id
	 * @return boolean
	 */
	public boolean eliminarConvivencia(String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaEliminar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Asignar Personal<br>
	 * 
	 * @param String
	 *            id
	 * @param String
	 *            id2
	 * @return boolean
	 */
	public Personal asignarPersonal(String id, String id2) {
		Personal p = null;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("PersonalAsignar2"));
			pst.clearParameters();
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, (id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				p = new Personal();
				int i = 1;
				p.setPercodjerar(r.getString(i++));
				p.setPertipo(r.getString(i++));
				p.setPerexpdoccoddep(r.getString(i++));
				p.setPerexpdoccodmun(r.getString(i++));
				p.setPertipdocum(r.getString(i++));
				p.setPernumdocum(r.getString(i++));
				p.setPernombre1(r.getString(i++));
				p.setPernombre2(r.getString(i++));
				p.setPerapellido1(r.getString(i++));
				p.setPerapellido2(r.getString(i++));
				p.setPeremail(r.getString(i++));
				p.setPerfechanac(r.getString(i++));
				p.setPerlugnaccoddep(r.getString(i++));
				p.setPerlugnaccodmun(r.getString(i++));
				p.setPeredad(r.getString(i++));
				p.setPergenero(r.getString(i++));
				p.setPeretnia(r.getString(i++));
				p.setPerdireccion(r.getString(i++));
				p.setPertelefono(r.getString(i++));
				p.setPerzona(r.getString(i++));
				p.setPerlocvereda(r.getString(i++));
				p.setPerbarcorreg(r.getString(i++));
				p.setEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar información de personal. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return p;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return p;
	}

	public LaboralVO asignarLaboral(Personal p, String id) {
		LaboralVO l = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("LaboralAsignar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				l = new LaboralVO();
				int i = 1;
				l.setLabCodPerso(r.getString(i++));
				l.setLabId(r.getString(i++));
				l.setLabNomEntid(r.getString(i++));
				l.setLabFechaInicio(r.getString(i++));
				l.setLabFechaFinal(r.getString(i++));
				l.setLabCargo(r.getString(i++));
				l.setLabDescripcion(r.getString(i++));
				l.setLabContacto(r.getString(i++));
				l.setLabTelefono(r.getString(i++));
				l.setLabEstado("1");
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar laboral. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return l;
		} catch (Exception sqle) {
			setMensaje("Error SQL intentando asignar laboral. Posible problema: "
					+ sqle);
			sqle.printStackTrace();
			return l;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public AsistenciaVO asignarAsistencia(Personal p, String tipo, String id) {
		System.out.println("asignarAsistencia ");
		AsistenciaVO a = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("AsistenciaAsignar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(tipo));
//			System.out.println("tipo " + tipo);
			pst.setString(posicion++, (p.getPernumdocum()));
			System.out.println("p.getPernumdocum() " + p.getPernumdocum());
			pst.setLong(posicion++, Long.parseLong(id));
			System.out.println("id " + id);
			r = pst.executeQuery();
			if (r.next()) {
				a = new AsistenciaVO();
				int i = 1;
				a.setAsiTipoPer(r.getString(i++));
				a.setAsiCodPer(r.getString(i++));
				a.setAsiCodigo(r.getString(i++));
				a.setAsiFecha(r.getString(i++));
				a.setAsiMotivo(r.getString(i++));
				a.setAsiObservacion(r.getString(i++));
				a.setAsiJustificada(r.getString(i++));
				a.setAsiEstado("1");
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			System.out.println("in " + in.getMensaje());
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			System.out.println("sqle " + sqle.getMessage());
			setMensaje("Error SQL intentando asignar asistencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return a;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return a;
	}

	/**
	 * Funcinn: Asignar Convivencia<br>
	 * 
	 * @param String
	 *            id
	 * @return boolean
	 */
	public Convivencia asignarConvivencia(String id) {
		Convivencia c = null;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("ConvivenciaAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				c = new Convivencia();
				int i = 1;
				c.setConcodigo(r.getString(i++));
				c.setConcodinst(r.getString(i++));
				c.setContipoperso(r.getString(i++));
				c.setConcodperso(r.getString(i++));
				c.setContipo(r.getString(i++));
				c.setCondescripcion(r.getString(i++));
				c.setConfecha(r.getString(i++));
				c.setConacuerdos(r.getString(i++));
				c.setConseguimiento(r.getString(i++));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar convivencia. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return c;
		} catch (Exception sqle) {
			setMensaje("Error SQL intentando asignar convivencia. Posible problema: "
					+ sqle);
			sqle.printStackTrace();
			return c;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return c;
	}

	/**
	 * Funcinn: Eliminar Estudiante<br>
	 * 
	 * @param String
	 *            id
	 * @param String
	 *            id2
	 * @return boolean
	 */
	public boolean eliminarUsuario(String id2, String id, String inst) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			long tipo = Long.parseLong(id);
			String numero = id2;
			long institucion = Long.parseLong(inst);
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// borrarlo de docente-sede-jornada
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar1"));
			pst.clearParameters();
			pst.setLong(posicion++, institucion);
			pst.setString(posicion++, numero);
			pst.executeUpdate();
			pst.close();
			// borrarlo de usuario
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar2"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, numero);
			pst.setLong(posicion++, institucion);
			pst.executeUpdate();
			pst.close();
			// borrarlo de usuario
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar3"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, numero);
			pst.setLong(posicion++, institucion);
			pst.executeUpdate();
			pst.close();
			// borrarlo de usuario
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar4"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, numero);
			pst.setLong(posicion++, institucion);
			pst.executeUpdate();
			pst.close();
			// borrarlo de usuario
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar5"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, numero);
			pst.setLong(posicion++, institucion);
			pst.executeUpdate();
			pst.close();
			// borrarlo de usuario
			pst = cn.prepareStatement(rb.getString("UsuarioEliminar6"));
			pst.clearParameters();
			posicion = 1;
			pst.setString(posicion++, numero);
			pst.setLong(posicion++, institucion);
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia1sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia2sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia3sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia4sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia5sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia6sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

			pst = cn.prepareStatement(rb
					.getString("Ususariohorarioupdatedia7sinvigencia"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, Integer.parseInt(numero));
			pst.executeUpdate();
			pst.close();
			cn.commit();

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando eliminar estudiante. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Comparar beans de Convivencia<br>
	 * 
	 * @param Convivencia
	 *            c
	 * @param Convivencia
	 *            c2
	 * @return boolean
	 */
	public boolean compararBeans(Convivencia c, Convivencia c2) {
		if (!c.getConcodinst().equals(c2.getConcodinst()))
			return false;
		if (!c.getContipoperso().equals(c2.getContipoperso()))
			return false;
		if (!c.getConcodperso().equals(c2.getConcodperso()))
			return false;
		if (!c.getContipo().equals(c2.getContipo()))
			return false;
		if (!c.getCondescripcion().equals(c2.getCondescripcion()))
			return false;
		if (!c.getConfecha().equals(c2.getConfecha()))
			return false;
		if (!c.getConacuerdos().equals(c2.getConacuerdos()))
			return false;
		if (!c.getConseguimiento().equals(c2.getConseguimiento()))
			return false;
		return true;
	}

	public boolean actualizar(Salud s, String[] valores) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("SaludActualizar"));
			pst.clearParameters();
			if (s.getSaltipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(s.getSaltipoperso().trim()));
			if (s.getSalcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSalcodperso().trim()));
			if (s.getSaltiposangre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltiposangre().trim());
			if (s.getSaleps().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaleps().trim());
			if (s.getSalars().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalars().trim());
			if (s.getSalalergias().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalalergias().trim());
			if (s.getSalenfermedades().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalenfermedades().trim());
			if (s.getSalmedicamentos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalmedicamentos().trim());
			if (s.getSaltelemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltelemerg().trim());
			if (s.getSalperemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalperemerg().trim());

			if (valores[0].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(valores[0].trim()));
			if (valores[1].equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(valores[1].trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información familiar. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizar(LaboralVO l) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("LaboralActualizar"));
			pst.clearParameters();
			if (l.getLabNomEntid().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabNomEntid());
			if (l.getLabFechaInicio().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(l.getLabFechaInicio())
								.getTime()));
			if (l.getLabFechaFinal().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(l.getLabFechaFinal())
								.getTime()));
			if (l.getLabCargo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabCargo());
			if (l.getLabDescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabDescripcion());
			if (l.getLabContacto().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabContacto());
			if (l.getLabTelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabTelefono());
			// where
			pst.setLong(posicion++, Long.parseLong(l.getLabCodPerso()));
			pst.setLong(posicion++, Long.parseLong(l.getLabId()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizar(AsistenciaVO a) {
		System.out.println("actualizar ");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaActualizar"));
			pst.clearParameters();
			if (a.getAsiFecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(a.getAsiFecha()).getTime()));
			if (a.getAsiMotivo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAsiMotivo());
			if (a.getAsiObservacion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, a.getAsiObservacion());
			if (a.getAsiJustificada().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(a.getAsiJustificada()));
			// where
			pst.setString(posicion++, a.getAsiTipoPer());
			pst.setString(posicion++, a.getAsiCodPer());
			pst.setLong(posicion++, Long.parseLong(a.getAsiCodigo()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			System.out.println("in " + in.getMensaje());
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de asistencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Actualizar Convivencia<br>
	 * 
	 * @param Convivencia
	 *            c
	 * @return boolean
	 */
	public boolean actualizar(Convivencia c) {
		// System.out.println("!! actualizar convivencia ");
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaActualizar"));
			pst.clearParameters();
			if (c.getConcodinst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(c.getConcodinst().trim()));
			if (c.getContipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(c.getContipoperso().trim()));
			if (c.getConcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConcodperso().trim());
			if (c.getContipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getContipo().trim()));
			if (c.getCondescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getCondescripcion().trim());
			if (c.getConfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(c.getConfecha()).getTime()));
			if (c.getConacuerdos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConacuerdos().trim());
			if (c.getConseguimiento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConseguimiento().trim());

			if (c.getConcodigo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getConcodigo().trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public synchronized boolean insertar(LaboralVO l) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long per = Long.parseLong(l.getLabCodPerso());
			long id = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("LaboralId"));
			pst.clearParameters();
			pst.setLong(posicion++, per);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("LaboralInsertar"));
			pst.clearParameters();
			pst.setLong(posicion++, per);
			pst.setLong(posicion++, id);
			if (l.getLabNomEntid().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabNomEntid());
			if (l.getLabFechaInicio().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(l.getLabFechaInicio())
								.getTime()));
			if (l.getLabFechaFinal().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(l.getLabFechaFinal())
								.getTime()));
			if (l.getLabCargo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabCargo());
			if (l.getLabDescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabDescripcion());
			if (l.getLabContacto().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabContacto());
			if (l.getLabTelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, l.getLabTelefono());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean insertar(Salud s) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("SaludInsertar"));
			pst.clearParameters();
			if (s.getSaltipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(s.getSaltipoperso().trim()));
			if (s.getSalcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(s.getSalcodperso().trim()));
			if (s.getSaltiposangre().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltiposangre().trim());
			if (s.getSaleps().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaleps().trim());
			if (s.getSalars().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalars().trim());
			if (s.getSalalergias().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalalergias().trim());
			if (s.getSalenfermedades().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalenfermedades().trim());
			if (s.getSalmedicamentos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalmedicamentos().trim());
			if (s.getSaltelemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSaltelemerg().trim());
			if (s.getSalperemerg().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, s.getSalperemerg().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public/* synchronized */boolean insertar(AsistenciaVO a) {
		System.out.println("insertar ");
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String est = (a.getAsiCodPer());
			long id = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("AsistenciaId"));
			pst.clearParameters();
			pst.setString(posicion++, a.getAsiTipoPer());
			pst.setString(posicion++, est);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("AsistenciaInsertar"));
			pst.clearParameters();
			pst.setString(posicion++, a.getAsiTipoPer());
			System.out.println("a.getAsiTipoPer() " + a.getAsiTipoPer());
			pst.setString(posicion++, est);
			System.out.println(" est " + est);
			pst.setLong(posicion++, id);
			System.out.println("id " + id);

			System.out.println("a.getAsiFecha() " + a.getAsiFecha());
			System.out.println("a.getAsiMotivo() " + a.getAsiMotivo());
			System.out
					.println("a.getAsiObservacion() " + a.getAsiObservacion());

			System.out.println("Long.parseLong(a.getAsiJustificada() -"
					+ a.getAsiJustificada() + "-");

			// ASITIPPERSO, ASICODPERSO, ASICODIGO,ASIFECHA, ASIMOTIVO,
			// ASIOBSERVACION,ASIJUSTIFICADA
			if (a.getAsiFecha().equals("")) {
				pst.setNull(posicion++, java.sql.Types.DATE);
			} else {
				pst.setString(posicion++, a.getAsiFecha());
			}

			if (a.getAsiMotivo().equals("")) {
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			} else {
				pst.setString(posicion++, a.getAsiMotivo());
			}

			if (a.getAsiObservacion().equals("")) {
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			} else {
				pst.setString(posicion++, a.getAsiObservacion());
			}

			if (a.getAsiJustificada().equals("")
					&& GenericValidator.isLong(a.getAsiJustificada().trim())) {
				pst.setNull(posicion++, java.sql.Types.NUMERIC);
			} else {
				pst.setLong(posicion++,
						Long.parseLong(a.getAsiJustificada().trim()));
			}

			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);

		} catch (InternalErrorException in) {
			System.out.println("in " + in.getMensaje());
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			System.out.println("sqle " + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de asistencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("" + e.getMessage());

		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public boolean insertar(Convivencia c) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("ConvivenciaInsertar"));
			pst.clearParameters();
			if (c.getConcodinst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++,
						Long.parseLong(c.getConcodinst().trim()));
			if (c.getContipoperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++,
						Integer.parseInt(c.getContipoperso().trim()));
			if (c.getConcodperso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConcodperso().trim());
			if (c.getContipo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setInt(posicion++, Integer.parseInt(c.getContipo().trim()));
			if (c.getCondescripcion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getCondescripcion().trim());
			if (c.getConfecha().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setDate(posicion++,
						new java.sql.Date(sdf.parse(c.getConfecha()).getTime()));
			if (c.getConacuerdos().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConacuerdos().trim());
			if (c.getConseguimiento().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getConseguimiento().trim());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de convivencia.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} catch (java.text.ParseException e) {
			setMensaje("Error interpretando fecha:" + e.toString());
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: insertar sede-jornada en la base de datos<br>
	 * 
	 * @param PersonalVO
	 *            p
	 * @param String
	 *            inst
	 * @param String
	 *            sede
	 * @param String
	 *            [] jornada
	 * @return boolean
	 */
	public boolean insertarSedeJornada(Personal p, long inst, String[] jornada) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet r = null;
		ResultSet r3 = null;
		String[] m = null;
		String param[] = new String[3];
		int x = 0;
		long ih = 0;
		String pass = null;
		try {
			long docente = Long.parseLong(p.getPernumdocum());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// traer IH
			pst = cn.prepareStatement(rb.getString("getIHDocente"));
			posicion = 1;
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, inst);
			r = pst.executeQuery();
			if (r.next()) {
				ih = r.getLong(1);
			}
			r.close();
			pst.close();
			// traer password
			pst = cn.prepareStatement(rb.getString("getPassword"));
			posicion = 1;
			pst.setLong(posicion++, docente);
			r = pst.executeQuery();
			if (r.next()) {
				pass = r.getString(1);
			}
			r.close();
			pst.close();

			/* borrar DSJ */
			pst = cn.prepareStatement(rb.getString("BorrarDocenteSedeJornada"));
			posicion = 1;
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, inst);
			pst.executeUpdate();
			pst.close();
			/* borrar Usuario */
			pst = cn.prepareStatement(rb.getString("BorrarUsuario"));
			posicion = 1;
			pst.setLong(posicion++, docente);
			pst.setLong(posicion++, inst);
			int n = pst.executeUpdate();
			System.out.println("Borro=" + n);
			pst.close();
			/* Crear sede jornada y usuario */
			pst = cn.prepareStatement(rb
					.getString("IngresarDocenteSedeJornada"));
			pst2 = cn.prepareStatement(rb.getString("IngresarUsuario"));

			pst.clearBatch();
			pst2.clearBatch();
			// pst3.clearBatch();
			String[] ready = new String[jornada.length];
			int index = 0;
			boolean band = false;
			for (int i = 0; i < jornada.length; i++) {
				posicion = 1;
				band = false;
				param = jornada[i].replace('|', ':').split(":");
				if (param != null) {
					for (int j = 0; j < index; j++) {
						if ((param[0] + "|" + param[1]).equals(ready[j])) {
							band = true;
							break;
						}
					}
					ready[index++] = param[0] + "|" + param[1];
					if (!band) {
						System.out.println("Inserta="
								+ Long.parseLong(param[0]) + "//"
								+ Long.parseLong(param[1]));

						pst.setLong(posicion++, docente);
						pst.setLong(posicion++, inst);
						pst.setLong(posicion++, Long.parseLong(param[0]));// sede
						pst.setLong(posicion++, Long.parseLong(param[1]));// jornada
						pst.setLong(posicion++, ih);// ih
						pst.addBatch();
					}

					/*
					 * pst3 =
					 * cn.prepareStatement(rb.getString("isExisteUsuario"));
					 * posicion = 1; pst3.setLong(posicion++,docente);
					 * pst3.setLong(posicion++,inst);
					 * pst3.setLong(posicion++,Long.parseLong(param[0]));//sede
					 * pst3
					 * .setLong(posicion++,Long.parseLong(param[1]));//jornada
					 * r3 = pst.executeQuery();
					 */

					System.out.println("InsertaUSER=" + inst + "//" + docente
							+ "//" + pass + "//" + Long.parseLong(param[0])
							+ "//" + Long.parseLong(param[1]) + "//"
							+ Long.parseLong(param[2]));
					posicion = 1;

					pst2.setLong(posicion++, docente);
					pst2.setString(posicion++, pass);
					pst2.setLong(posicion++, Long.parseLong(param[2]));// perfil
					pst2.setLong(posicion++, docente);
					pst2.setLong(posicion++, inst);
					pst2.setLong(posicion++, Long.parseLong(param[0]));// sede
					pst2.setLong(posicion++, Long.parseLong(param[1]));// jornada
					pst2.addBatch();
				}
				// r3.close();
				// pst3.close();

			}
			pst.executeBatch();
			pst2.executeBatch();
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ingresar información de sede y jornada. Posible problema: ");
			System.out.println("excepcion " + sqle);
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeStatement(pst2);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public FormacionVO asignarFormacion(Personal p, String id) {
		FormacionVO f = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet r = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("FormacionAsignar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				f = new FormacionVO();
				int i = 1;
				f.setForCodPerso(r.getString(i++));
				f.setForId(r.getString(i++));
				f.setForNomInst(r.getString(i++));
				f.setForPrograma(r.getString(i++));
				f.setForSemestres(r.getString(i++));
				f.setForTermino(r.getString(i++));
				f.setForAnho(r.getString(i++));
				f.setForTitulo(r.getString(i++));
				f.setForCarrera(r.getString(i++));
				f.setForEstado("1");
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error SQL intentando asignar formacion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return f;
		} catch (Exception sqle) {
			setMensaje("Error SQL intentando asignar formacion. Posible problema: "
					+ sqle);
			sqle.printStackTrace();
			return f;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public Collection getFormaciones(Personal p) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection list = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("listaFormaciones"));
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			rs = ps.executeQuery();
			list = getCollection(rs);
			rs.close();
			ps.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener información formacinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return list;
	}

	public boolean actualizar(FormacionVO f) {
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("FormacionActualizar"));
			pst.clearParameters();
			if (f.getForPrograma().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForPrograma()));
			if (f.getForNomInst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForNomInst());
			if (f.getForSemestres().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForSemestres()));
			if (f.getForTermino().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForTermino()));
			if (f.getForAnho().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForAnho()));
			if (f.getForTitulo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForTitulo());
			if (f.getForCarrera().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForCarrera());
			// where
			pst.setLong(posicion++, Long.parseLong(f.getForCodPerso()));
			pst.setLong(posicion++, Long.parseLong(f.getForId()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando actualizar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean compararBeans(FormacionVO f, FormacionVO f2) {
		if (!f.getForCodPerso().equals(f2.getForCodPerso()))
			return false;
		if (!f.getForId().equals(f2.getForId()))
			return false;
		if (!f.getForNomInst().equals(f2.getForNomInst()))
			return false;
		if (!f.getForPrograma().equals(f2.getForPrograma()))
			return false;
		if (!f.getForSemestres().equals(f2.getForSemestres()))
			return false;
		if (!f.getForTermino().equals(f2.getForTermino()))
			return false;
		if (!f.getForAnho().equals(f2.getForAnho()))
			return false;
		if (!f.getForTitulo().equals(f2.getForTitulo()))
			return false;
		if (!f.getForCarrera().equals(f2.getForCarrera()))
			return false;
		return true;
	}

	public boolean eliminarFormacion(Personal p, String id) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("FormacionEliminar"));
			pst.clearParameters();
			pst.setLong(posicion++, Long.parseLong(p.getPernumdocum()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR formacion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Inserta los datos del bean en la tabla de informacion basica del egresado
	 * 
	 * @param Cursor
	 *            cursor
	 * @param ActualizarNuevoBasica
	 *            basica
	 * @param int tipo
	 * @return boolean
	 */
	public synchronized boolean insertar(FormacionVO f) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			long per = Long.parseLong(f.getForCodPerso());
			long id = 1;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			/*
			 * pst = cn.prepareStatement(rb.getString("FormacionId"));
			 * pst.clearParameters(); System.out.println("per " + per);
			 * pst.setLong(posicion++, per); rs = pst.executeQuery(); if
			 * (rs.next()) { id = rs.getLong(1); }
			 * 
			 * System.out.println("id " + id); rs.close(); pst.close();
			 */

			posicion = 1;
			pst = cn.prepareStatement(rb.getString("FormacionInsertar"));
			pst.clearParameters();
			System.out.println("CONSULTA ---+6 "
					+ rb.getString("FormacionInsertar"));
			/*
			 * FORTIPOPROG, FORNOMINST,FORNUMSEMES, FORTERMINO,
			 * FORANNO,FORTITULO, FORESTUDIO
			 */
			pst.setLong(posicion++, per);
			pst.setLong(posicion++, per);
			if (f.getForPrograma().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForPrograma()));
			if (f.getForNomInst().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForNomInst());
			if (f.getForSemestres().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForSemestres()));
			if (f.getForTermino().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForTermino()));
			if (f.getForAnho().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(f.getForAnho()));
			if (f.getForTitulo().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForTitulo());
			if (f.getForCarrera().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, f.getForCarrera());
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);

		} catch (InternalErrorException in) {
			System.out.println(in.getMessage());
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información de formacinn.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/**
	 * Funcinn: Asignar Salud<br>
	 * 
	 * @param String
	 *            id
	 * @param String
	 *            id2
	 * @return boolean
	 */
	public Salud asignarSalud(String id, String id2) {
		Salud s = null;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("SaludAsignar"));
			pst.clearParameters();
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			if (id2.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id2.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				s = new Salud();
				int i = 1;
				s.setSaltipoperso(r.getString(i++));
				s.setSalcodperso(r.getString(i++));
				s.setSaltiposangre(r.getString(i++));
				s.setSaleps(r.getString(i++));
				s.setSalars(r.getString(i++));
				s.setSalalergias(r.getString(i++));
				s.setSalenfermedades(r.getString(i++));
				s.setSalmedicamentos(r.getString(i++));
				s.setSaltelemerg(r.getString(i++));
				s.setSalperemerg(r.getString(i++));
				s.setSalestado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return s;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return s;
	}

	public List getPerfiles() throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("getAllPerfil"));
			rs = pst.executeQuery();
			while (rs.next()) {
				itemVO = new ItemVO();
				int i = 1;
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			throw new Exception(sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public String[] getSedeJornadaPerfil(long inst, long id) throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List l = new ArrayList();
		String[] lista = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("personal.sedeJornada"));
			pst.setLong(1, id);
			pst.setLong(2, inst);
			rs = pst.executeQuery();
			while (rs.next()) {
				l.add(rs.getString(1));
			}
			if (l.size() > 0) {
				lista = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					lista[i] = (String) l.get(i);
				}
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			throw new Exception(sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}
	
	public List getGruposInstSedeJornadaGrado(long institucion, int sede, int jornada, int grado) throws Exception {
		GrupoVO grupo = null;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.gruposInstitucionSedeJornada"));
			pst.setLong(1, institucion);
			pst.setInt(2, sede);
			pst.setInt(3, jornada);
			pst.setInt(4, grado);
			rs = pst.executeQuery();
			while (rs.next()) {
				int i=1;
				grupo = new GrupoVO();
				grupo.setGruNombre(rs.getString(i++));
				grupo.setGruCodigo(rs.getInt(i++));
				grupo.setGruCodigoJerarquia(new Long(rs.getLong(i++)));
				grupo.setGruGrado(rs.getInt(i++));
				grupo.setGruInstitucion(new Long(rs.getLong(i++)));
				grupo.setGruSede(rs.getInt(i++));
				grupo.setGruJornada(rs.getInt(i++));
				grupo.setGruCodigoJerarquiaGrupo(new Long(rs.getLong(i+1)));
				lista.add(grupo);
			}
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			throw new Exception(sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}
	
	public List getGruposInstSedeJornadaGradoMetodologia(long institucion, int sede, int jornada, int grado, int metodologia) throws Exception {
		GrupoVO grupo = null;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		try {
			cn = cursor.getConnection();
			
			pst = cn.prepareStatement(rb.getString("Personal.gruposInstitucionSedeJornadaMetodologia"));
			pst.setLong(1, institucion);
			pst.setInt(2, sede);
			pst.setInt(3, jornada);
			pst.setInt(4, grado);
			pst.setInt(5, metodologia);
			rs = pst.executeQuery();
			while (rs.next()) {
				int i=1;
				grupo = new GrupoVO();
				grupo.setGruNombre(rs.getString(i++));
				grupo.setGruCodigo(rs.getInt(i++));
				grupo.setGruCodigoJerarquia(new Long(rs.getLong(i++)));
				grupo.setGruGrado(rs.getInt(i++));
				grupo.setGruInstitucion(new Long(rs.getLong(i++)));
				grupo.setGruSede(rs.getInt(i++));
				grupo.setGruJornada(rs.getInt(i++));
				grupo.setGruCodigoJerarquiaGrupo(new Long(rs.getLong(i+1)));
				lista.add(grupo);
			}
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			throw new Exception(sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}
	
	public boolean insertarFijarDocente(Carga r, Login l) {
		
		PreparedStatement pst = null;
		int x = 0;
		try {
			cn = cursor.getConnection();
			int posicion = 1;
			long docente = Long.parseLong(r.getRotdagdocente().trim());
			long asignatura = Long.parseLong(r.getRotdagasignatura().trim());
			long institucion = Long.parseLong(l.getInstId());
			long sede = Long.parseLong(r.getRotdagsede().trim());
			long jornada = Long.parseLong(r.getRotdagjornada().trim());
			long metodologia = Long.parseLong(r.getRotdagmetodologia().trim());
			long ih = Long.parseLong(r.getRotdaghoras().equals("") ? "0" : r .getRotdaghoras());
			long vigencia = r.getRotdagVigencia();
			//cn.setAutoCommit(false);
			if (ih != 0) {
				pst = cn.prepareStatement(rb.getString("Actualizar.ih"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, (ih));
				pst.setLong(posicion++, (institucion));
				pst.setLong(posicion++, (docente));
				pst.executeUpdate();
				pst.close();
			}
			cn.commit();
			cn.setAutoCommit(true);

			pst = cn.prepareStatement(rb.getString("Eliminar.FijarDocente"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, (docente));
			pst.setLong(posicion++, (asignatura));
			pst.setLong(posicion++, (institucion));
			pst.setLong(posicion++, (sede));
			pst.setLong(posicion++, (jornada));
			pst.setLong(posicion++, (metodologia));
			pst.setLong(posicion++, (vigencia));
			int n = pst.executeUpdate();
			// System.out.println("valor de eliminados:"+n);
			pst.close();
			// limpiar horarios

			this.consultargruposxgrado(cn, this.consultargradosxdocente(docente, vigencia), docente,vigencia, asignatura);

			String[] grados = r.getRotdaggrados();
			String[] gradosIH = r.getRotdagIHgrados_();
			if (gradosIH != null) {
				for (x = 0; x < gradosIH.length; x++) {
					if (gradosIH[x] != null)
						break;
				}
			}
			// System.out.println("valor de ingresados:"+grados.length);
			if (grados != null) {
				pst = cn.prepareStatement(rb.getString("Insertar.FijarDocente"));
				pst.clearBatch();
				for (int i = 0; i < grados.length; i++) {
					// System.out.println("mm:"+grados[i]+"//"+gradosIH[x]);
					 System.out.println("Fijar Docente : "+docente+" - "+asignatura+" - "+institucion+" - "+sede+" - "+jornada+" - "+metodologia+" - "+vigencia);
					posicion = 1;
					pst.setLong(posicion++, docente);
					pst.setLong(posicion++, asignatura);
					pst.setLong(posicion++, institucion);
					pst.setLong(posicion++, sede);
					pst.setLong(posicion++, jornada);
					pst.setLong(posicion++, metodologia);
					pst.setLong(posicion++, Long.parseLong(grados[i].trim()));
					pst.setLong(posicion++,	Long.parseLong(gradosIH[x++].trim()));
					pst.setLong(posicion++, (vigencia));
					pst.addBatch();
				}
				pst.executeBatch();
			}
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de Docente-Grado-Asignatura. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return true;
	}
	
	
	public boolean insertarGruposDocente(long asignatura ,long idDocente, long grupo, int vigencia) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.insertarGruposDocente"));
			pst.clearParameters();
			pst.setLong(posicion++, grupo);
			pst.setLong(posicion++, idDocente);
			pst.setLong(posicion++, asignatura);
			pst.setInt(posicion++, vigencia);
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
				return false;
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				return false;
			}
		}
		return true;
	}
	
	public List getGruposDocente(long idDocente, long asignatura, int vigencia) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List listaGruposDocente = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.getGruposDocente"));
			pst.clearParameters();
			pst.setLong(posicion++, idDocente);
			pst.setLong(posicion++, asignatura);
			pst.setInt(posicion++, vigencia);
			rs=pst.executeQuery();
			int i=1;
			while (rs.next()){
				listaGruposDocente.add(""+rs.getLong(i));
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
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
			} 
		}
		return listaGruposDocente;
	}
	
	public boolean eliminarGruposGrado(long institucion, int sede,int jornada, int grado,long asignatura, long idDocente, int vigencia) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.selectGruposGradoDeseleccionado"));
			pst.clearParameters();
			pst.setLong(posicion++, institucion);
			pst.setInt(posicion++, sede);
			pst.setInt(posicion++, jornada);
			pst.setInt(posicion++, grado);
			pst.setLong(posicion++, asignatura);
			pst.setLong(posicion++, idDocente);
			pst.setInt(posicion++, vigencia);
			
			rs=pst.executeQuery();
			if (!rs.next()){
				return false;
			}
			
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("Personal.eliminarGruposGradoDeseleccionado"));
			pst.clearParameters();
			pst.setLong(posicion++, institucion);
			pst.setInt(posicion++, sede);
			pst.setInt(posicion++, jornada);
			pst.setInt(posicion++, grado);
			pst.setLong(posicion++, asignatura);
			pst.setLong(posicion++, idDocente);
			pst.setInt(posicion++, vigencia);
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
				return false;
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			} 
		}
		return true;
	}
	
	
	public boolean eliminarGruposDocente(long idDocente, long asignatura, int vigencia, long grupo) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.selectGruposDocente"));
			pst.clearParameters();
			pst.setLong(posicion++, grupo);
			pst.setLong(posicion++, idDocente);
			pst.setLong(posicion++, asignatura);
			pst.setInt(posicion++, vigencia);
			
			rs=pst.executeQuery();
			if (!rs.next()){
				return false;
			}
			
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("Personal.eliminarGruposDocente"));
			pst.clearParameters();
			pst.setLong(posicion++, idDocente);
			pst.setLong(posicion++, asignatura);
			pst.setInt(posicion++, vigencia);
			pst.setLong(posicion++, grupo);
			
			pst.executeUpdate();
			cn.commit();
			cn.setAutoCommit(true);
			
			
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
				return false;
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			} 
		}
		return true;
	}
	
	/**
	 * Retorna una lista de tipo {@link siges.personal.beans.CargaHorariaTotal CargaHorariaTotal} 
	 * con la carga horaria del docente
	 * 
	 * @param  idDocente No de Identificacion del docente del cual se va a obtener la carga horaria
	 * @param  institucion codigo de la institucion de la cual se desea la carga horaria
	 * @param  vigencia vigencia de la carga horaria
	 * @return lista de objeto 
	 * 
	 */
	public List getCargaHorariaTotal(long idDocente, long institucion, int vigencia) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List listaCargaHorariaTotal = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Lista.FijarDocente3"));
			pst.clearParameters();
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, idDocente);
			pst.setInt(posicion++, vigencia);
			rs=pst.executeQuery();
			int i=1;
			while (rs.next()){
				i=1;
				CargaHorariaTotal cargaHorariaTotal = new CargaHorariaTotal();
				cargaHorariaTotal.setCargaHTSede(rs.getInt(i++));
				cargaHorariaTotal.setCargaHTJornada(rs.getInt(i++));
				cargaHorariaTotal.setCargaHTMetodologia(rs.getInt(i++));
				cargaHorariaTotal.setCargaHTAsignatura(new Long(rs.getLong(i++)));
				cargaHorariaTotal.setCargaHTGrado(rs.getInt(i++));
				cargaHorariaTotal.setCargaHTIntensidadHoraria(rs.getInt(i++));
				listaCargaHorariaTotal.add(cargaHorariaTotal);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando ingresar información laboral.("
					+ sqle.getErrorCode() + ") Posible problema: ");
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
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			} 
		}
		return listaCargaHorariaTotal;
	}
	
	public List getMaximoIntensidadHorariaAsignatura(long institucion, int vigencia, int metodologia) {
		int posicion = 1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List listaMaximoAsignaturas = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Personal.maximoIntensidadHorariaAsignatura"));
			pst.clearParameters();
			pst.setLong(posicion++, institucion);
			pst.setLong(posicion++, vigencia);
			pst.setInt(posicion++, metodologia);
			rs=pst.executeQuery();
			int i=1;
			
			while (rs.next()){
				i=1;
				String [] maximoAsignatura = new String[3];
				maximoAsignatura [0] = ""+rs.getInt(i++);
				maximoAsignatura [1] = ""+rs.getLong(i++);
				maximoAsignatura [2] = ""+rs.getInt(i++);
				listaMaximoAsignaturas.add(maximoAsignatura);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			
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
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			} 
		}
		return listaMaximoAsignaturas;
	}
	
	
	public int[] consultargradosxdocente(long docente, long vigencia){
		int posicion;
		int[] grados = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		try {
		
		
		
		pst = cn.prepareStatement(rb.getString("usuario.grupoxcedula"));
		posicion = 1;
		pst.setLong(posicion++, docente);
		pst.setLong(posicion++, vigencia);
		rs = pst.executeQuery();

		String stringgrado = "";
		while (rs.next()) {
			stringgrado = stringgrado.concat(",").concat(
					String.valueOf(rs.getInt(1)));
		}
		String[] arr = stringgrado.split(",");
		grados = new int[arr.length];
		for (int p = 0; p < arr.length; p++) {
			if (arr[p] != "" && !arr[p].equals("")) {
				grados[p] = Integer.parseInt(arr[p]);
			}
		}

		rs.close();
		pst.close();
		cursor.cerrar();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return grados;
	}

	public String[] consultargruposxgrado(Connection cn,int[] grados, long docente,long vigencia, long asignatura)  {
		int posicion;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
		
		Collection list = null;
		if (grados != null) {
				
			//cn = cursor.getConnection();
				
			for (int i = 0; i < grados.length; i++) {

				pst = cn.prepareStatement(rb.getString("usuario.grupoxgrado"));
				posicion = 1;
				pst.setLong(posicion++,Long.parseLong(String.valueOf(grados[i])));
				rs = pst.executeQuery();

				String stringgrupo = "";
				while (rs.next()) {
					stringgrupo = stringgrupo.concat(",").concat(
							String.valueOf(rs.getInt(1)));
				}
				String[] arr = stringgrupo.split(",");
				int[] grupos = new int[arr.length];
				for (int p = 0; p < arr.length; p++) {
					if (arr[p] != "" && !arr[p].equals("")) {
						grupos[p] = Integer.parseInt(arr[p]);
					}
				}
				rs.close();
				pst.clearParameters();
				pst.close();
				// Se empeiza a limpiar horarios de esa asignacion con vigencia
				// docente y grupo

				for (int k = 0; k < grupos.length; k++) {
					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia1convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h1 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia2convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h2 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia3convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h3 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia4convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h4 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia5convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h5 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia6convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++, Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h6 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

					pst = cn.prepareStatement(rb.getString("Ususariohorarioupdatedia7convigencia"));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, (docente));
					pst.setLong(posicion++, (vigencia));
					pst.setLong(posicion++,	Long.parseLong(String.valueOf(grupos[k]).trim()));
					pst.setLong(posicion++, (asignatura));
					int h7 = pst.executeUpdate();
					// System.out.println("valor de eliminados:"+n);
					pst.clearParameters();
					pst.close();

				}

				cn.commit();
				cn.setAutoCommit(true);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Personal asignarPersonal(String id) {
		Personal p = null;
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		ResultSet r = null;
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("PersonalAsignar222"));
			pst.clearParameters();
			// if (id2.equals(""))
			// pst.setNull(posicion++, java.sql.Types.VARCHAR);
			// else
			// pst.setLong(posicion++, Long.parseLong(id2.trim()));
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, (id.trim()));
			r = pst.executeQuery();
			if (r.next()) {
				p = new Personal();
				int i = 1;
				p.setPercodjerar(r.getString(i++));
				p.setPertipo(r.getString(i++));
				p.setPerexpdoccoddep(r.getString(i++));
				p.setPerexpdoccodmun(r.getString(i++));
				p.setPertipdocum(r.getString(i++));
				p.setPernumdocum(r.getString(i++));
				p.setPernombre1(r.getString(i++));
				p.setPernombre2(r.getString(i++));
				p.setPerapellido1(r.getString(i++));
				p.setPerapellido2(r.getString(i++));
				p.setPerfechanac(r.getString(i++));
				p.setPerlugnaccoddep(r.getString(i++));
				p.setPerlugnaccodmun(r.getString(i++));
				p.setPeredad(r.getString(i++));
				p.setPergenero(r.getString(i++));
				p.setPeretnia(r.getString(i++));
				p.setPerdireccion(r.getString(i++));
				p.setPertelefono(r.getString(i++));
				p.setPerzona(r.getString(i++));
				p.setPerlocvereda(r.getString(i++));
				p.setPerbarcorreg(r.getString(i++));
				p.setEstado("1");
			}
		} catch (InternalErrorException in) {
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			setMensaje("Error intentando asignar información de personal. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return p;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(r);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return p;
	}

	public boolean updatePersonal(String pernumdoc, String perEmail) {
		System.out.println("updatePersonal ");
		int posicion = 1; // posicion inicial de llenado del preparedstatement
		Connection cn = null;
		PreparedStatement pst = null, pst2 = null, pst3 = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("updatePersonal"));

			pst.setString(posicion++, perEmail);
			System.out.println("perEmail " + perEmail);
			// where
			pst.setString(posicion++, pernumdoc);
			System.out.println("pernumdoc " + pernumdoc);

			pst.executeUpdate();

		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			setMensaje("Error SQL intentando ingresar información de salud. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	public boolean insetrarMensajeRegistroBorrado(String llaveCompuesta, String funcionQueLlama) {
//		String llaveCompuesta = idGrupo + "-" + idDocente + "-" + idAsignatura + "-" + idVigencia;
		int posicion = 1;
		
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("prueba.insertarMensajeBorrado"));
			pst.clearParameters();
			pst.setString(posicion++, llaveCompuesta);
			pst.setString(posicion++, funcionQueLlama);
			pst.executeUpdate();
			cn.commit();
//			cn.setAutoCommit(true);
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			try {
				cn.rollback();
			} catch (SQLException s) {
				return false;
			}
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			} 
		}
		return true;
	}
}
