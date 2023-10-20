package siges.convocatoria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import siges.convocatoria.beans.*;
import siges.conflicto.beans.InfluenciaConflictos;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * @author Administrador
 * 
 * Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ConvocatoriaDAO extends Dao {

	public String sentencia;

	public String mensaje;

	public String buscar;

	private java.text.SimpleDateFormat sdf;

	private ResourceBundle rb;

	public ConvocatoriaDAO(Cursor cur) {
		super(cur);
		sentencia = null;
		mensaje = "";
		sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		rb = ResourceBundle.getBundle("convocatoria");
	}

	public String getParam(String param) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("correoparam"));
			pst.clearParameters();
			posicion = 1;
			if (param.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, param.trim());
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando obtener Parnmetros de correo. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
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
		return null;
	}

	public Convocatoria asignar(String nd) {
		Convocatoria c = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1, i = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("Asignar"));
			pst.clearParameters();
			posicion = 1;
			// if(td.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
			// else pst.setInt(posicion++,Integer.parseInt(td.trim()));
			if (nd.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(nd.trim()));
			rs = pst.executeQuery();
			if (rs.next()) {
				i = 1;
				c = new Convocatoria();
				c.setEstado("1");
				c.setFortipodoc(rs.getString(i++));
				c.setFornumdoc(rs.getString(i++));
				c.setFornombre1(rs.getString(i++));
				c.setFornombre2(rs.getString(i++));
				c.setForapellido1(rs.getString(i++));
				c.setForapellido2(rs.getString(i++));
				c.setForedad(rs.getString(i++));
				c.setFordireccion(rs.getString(i++));
				c.setFortelefono(rs.getString(i++));
				c.setForcorreoper(rs.getString(i++));
				c.setForcorreoins(rs.getString(i++));
				c.setForcodlocal(rs.getString(i++));
				c.setForcoddane(rs.getString(i++));
				c.setFornomins(rs.getString(i++));
				c.setForcodsede(rs.getString(i++));
				c.setForcodjorn(rs.getString(i++));
				c.setForservicio(rs.getString(i++));
				c.setForescalafon(rs.getString(i++));
				System.out.println("g0: " + c.getForgrado0());
				c.setForgrado0(rs.getString(i++));
				System.out.println("g0_: " + c.getForgrado0());
				c.setForgrado1(rs.getString(i++));
				c.setForgrado2(rs.getString(i++));
				c.setForgrado3(rs.getString(i++));
				c.setForgrado4(rs.getString(i++));
				c.setForgrado5(rs.getString(i++));
				c.setForgrado6(rs.getString(i++));
				c.setForgrado7(rs.getString(i++));
				c.setForgrado8(rs.getString(i++));
				c.setForgrado9(rs.getString(i++));
				c.setForgrado10(rs.getString(i++));
				c.setForgrado11(rs.getString(i++));
				c.setForcodcurso(rs.getString(i++));
				c.setForcodjorcurso(rs.getString(i++));
				c.setFornomsede(rs.getString(i++));
				c.setFornomjorn(rs.getString(i++));
				c.setForjerjornada(rs.getString(i++));
				c.setForid(rs.getString(i++));
				c.setResponsable(rs.getString(i++));
				c.setCorreoresp(rs.getString(i++));
				c.setNombrecurso(rs.getString(i++));
				c.setJorncurso(rs.getString(i++));
			}
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Inscripcion. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return c;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return c;
	}

	public boolean eliminarCurso(String nd, String cur) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("eliminarCurso"));
			pst.clearParameters();
			posicion = 1;

			if (nd.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(nd.trim()));
			if (cur.equals(""))
				pst.setNull(posicion++, java.sql.Types.NULL);
			else
				pst.setLong(posicion++, Long.parseLong(cur.trim()));

			pst.executeUpdate();
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
			} catch (SQLException s) {
			}
			setMensaje("Error SQL intentando ELIMINAR Curso inscrito. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public synchronized boolean insertarCurso(Convocatoria c) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("InsertarHijo"));
			pst.clearParameters();
			posicion = 1;

			if (c.getFortipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFortipodoc().trim()));
			if (c.getFornumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFornumdoc().trim()));
			if (c.getForcodcurso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodcurso().trim()));
			if (c.getForcodjorcurso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodjorcurso().trim()));
			pst.executeUpdate();

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
			setMensaje("Error SQL intentando ingresar información de la Inscripcinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public synchronized boolean insertar(Convocatoria c) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("Insertar"));
			pst.clearParameters();
			posicion = 1;
			// System.out.println("c.getFortipodoc(): "+c.getFortipodoc());
			if (c.getFortipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFortipodoc().trim()));
			// System.out.println("c.getFornumdoc(): "+c.getFornumdoc());
			if (c.getFornumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFornumdoc().trim()));
			// System.out.println("c.getFornombre1(): "+c.getFornombre1());
			if (c.getFornombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornombre1().trim());
			// System.out.println("c.getFornombre2(): "+c.getFornombre2());
			if (c.getFornombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornombre2().trim());
			// System.out.println("c.getForapellido1(): "+c.getForapellido1());
			if (c.getForapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForapellido1().trim());
			// System.out.println("c.getForapellido2(): "+c.getForapellido2());
			if (c.getForapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForapellido2().trim());
			// System.out.println("c.getForedad(): "+c.getForedad());
			if (c.getForedad().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForedad().trim()));
			// System.out.println("c.getFordireccion(): "+c.getFordireccion());
			if (c.getFordireccion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFordireccion().trim());
			// System.out.println("c.getFortelefono(): "+c.getFortelefono());
			if (c.getFortelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFortelefono().trim());
			// System.out.println("c.getForcorreoper(): "+c.getForcorreoper());
			if (c.getForcorreoper().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForcorreoper().trim());
			// System.out.println("c.getForcorreoins(): "+c.getForcorreoins());
			if (c.getForcorreoins().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForcorreoins().trim());
			// System.out.println("c.getForcodlocal(): "+c.getForcodlocal());
			if (c.getForcodlocal().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodlocal().trim()));
			// System.out.println("c.getForcoddane(): "+c.getForcoddane());
			if (c.getForcoddane().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcoddane().trim()));
			// System.out.println("c.getFornomins(): "+c.getFornomins());
			if (c.getFornomins().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornomins().trim());
			// System.out.println("c.getForcodsede(): "+c.getForcodsede());
			if (c.getForcodsede().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodsede().trim()));
			// System.out.println("c.getForcodjorn(): "+c.getForcodjorn());
			if (c.getForcodjorn().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodjorn().trim()));
			// System.out.println("c.getForservicio(): "+c.getForservicio());
			if (c.getForservicio().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForservicio().trim()));
			// System.out.println("c.getForescalafon(): "+c.getForescalafon());
			if (c.getForescalafon().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForescalafon().trim()));
			// System.out.println("c.getForgrado0(): "+c.getForgrado0());
			if (c.getForgrado0().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado0().trim()));
			// System.out.println("c.getForgrado1(): "+c.getForgrado1());
			if (c.getForgrado1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado1().trim()));
			// System.out.println("c.getForgrado2(): "+c.getForgrado2());
			if (c.getForgrado2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado2().trim()));
			// System.out.println("c.getForgrado3(): "+c.getForgrado3());
			if (c.getForgrado3().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado3().trim()));
			// System.out.println("c.getForgrado4(): "+c.getForgrado4());
			if (c.getForgrado4().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado4().trim()));
			// System.out.println("c.getForgrado5(): "+c.getForgrado5());
			if (c.getForgrado5().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado5().trim()));
			// System.out.println("c.getForgrado6(): "+c.getForgrado6());
			if (c.getForgrado6().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado6().trim()));
			// System.out.println("c.getForgrado7(): "+c.getForgrado7());
			if (c.getForgrado7().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado7().trim()));
			// System.out.println("c.getForgrado8(): "+c.getForgrado8());
			if (c.getForgrado8().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado8().trim()));
			// System.out.println("c.getForgrado9(): "+c.getForgrado9());
			if (c.getForgrado9().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado9().trim()));
			// System.out.println("c.getForgrado10(): "+c.getForgrado10());
			if (c.getForgrado10().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado10().trim()));
			// System.out.println("c.getForgrado11(): "+c.getForgrado11());
			if (c.getForgrado11().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado11().trim()));
			// System.out.println("fecha: "+new
			// Timestamp(System.currentTimeMillis()));
			pst.setTimestamp(posicion++, new Timestamp(System.currentTimeMillis()));
			// System.out.println("c.getForcodcurso(): "+c.getForcodcurso());
			// if(c.getForcodcurso().equals(""))
			// pst.setNull(posicion++,java.sql.Types.VARCHAR);
			// else
			// pst.setLong(posicion++,Long.parseLong(c.getForcodcurso().trim()));
			// System.out.println("c.getForcodjorcurso():
			// "+c.getForcodjorcurso());
			// if(c.getForcodjorcurso().equals(""))
			// pst.setNull(posicion++,java.sql.Types.VARCHAR);
			// else
			// pst.setLong(posicion++,Long.parseLong(c.getForcodjorcurso().trim()));
			// System.out.println("c.getFornomsede(): "+c.getFornomsede());
			if (c.getFornomsede().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornomsede().trim());
			// System.out.println("c.getFornomjorn(): "+c.getFornomjorn());
			if (c.getFornomjorn().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornomjorn().trim());
			/*
			 * if(c.getForjerjornada().equals(""))
			 * pst.setNull(posicion++,java.sql.Types.VARCHAR); else
			 * pst.setLong(posicion++,Long.parseLong(c.getForjerjornada().trim()));
			 */

			pst.executeUpdate();
			pst.close();

			pst = cn.prepareStatement(rb.getString("InsertarHijo"));
			pst.clearParameters();
			posicion = 1;

			if (c.getFortipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFortipodoc().trim()));
			if (c.getFornumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFornumdoc().trim()));
			if (c.getForcodcurso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodcurso().trim()));
			if (c.getForcodjorcurso().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForcodjorcurso().trim()));
			pst.executeUpdate();

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
			setMensaje("Error SQL intentando ingresar información de la Inscripcinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public synchronized boolean actualizar(Convocatoria c) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("actualizar"));
			pst.clearParameters();
			posicion = 1;
			// System.out.println("c.getFortipodoc(): "+c.getFortipodoc());
			// System.out.println("c.getFornombre1(): "+c.getFornombre1());
			if (c.getFornombre1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornombre1().trim());
			// System.out.println("c.getFornombre2(): "+c.getFornombre2());
			if (c.getFornombre2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFornombre2().trim());
			// System.out.println("c.getForapellido1(): "+c.getForapellido1());
			if (c.getForapellido1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForapellido1().trim());
			// System.out.println("c.getForapellido2(): "+c.getForapellido2());
			if (c.getForapellido2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForapellido2().trim());
			// System.out.println("c.getForedad(): "+c.getForedad());
			if (c.getForedad().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForedad().trim()));
			// System.out.println("c.getFordireccion(): "+c.getFordireccion());
			if (c.getFordireccion().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFordireccion().trim());
			// System.out.println("c.getFortelefono(): "+c.getFortelefono());
			if (c.getFortelefono().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getFortelefono().trim());
			// System.out.println("c.getForcorreoper(): "+c.getForcorreoper());
			if (c.getForcorreoper().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForcorreoper().trim());
			// System.out.println("c.getForcorreoins(): "+c.getForcorreoins());
			if (c.getForcorreoins().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setString(posicion++, c.getForcorreoins().trim());
			// System.out.println("c.getForgrado0(): "+c.getForgrado0());
			if (c.getForgrado0().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado0().trim()));
			// System.out.println("c.getForgrado1(): "+c.getForgrado1());
			if (c.getForgrado1().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado1().trim()));
			// System.out.println("c.getForgrado2(): "+c.getForgrado2());
			if (c.getForgrado2().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado2().trim()));
			// System.out.println("c.getForgrado3(): "+c.getForgrado3());
			if (c.getForgrado3().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado3().trim()));
			// System.out.println("c.getForgrado4(): "+c.getForgrado4());
			if (c.getForgrado4().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado4().trim()));
			// System.out.println("c.getForgrado5(): "+c.getForgrado5());
			if (c.getForgrado5().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado5().trim()));
			// System.out.println("c.getForgrado6(): "+c.getForgrado6());
			if (c.getForgrado6().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado6().trim()));
			// System.out.println("c.getForgrado7(): "+c.getForgrado7());
			if (c.getForgrado7().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado7().trim()));
			// System.out.println("c.getForgrado8(): "+c.getForgrado8());
			if (c.getForgrado8().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado8().trim()));
			// System.out.println("c.getForgrado9(): "+c.getForgrado9());
			if (c.getForgrado9().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado9().trim()));
			// System.out.println("c.getForgrado10(): "+c.getForgrado10());
			if (c.getForgrado10().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado10().trim()));
			// System.out.println("c.getForgrado11(): "+c.getForgrado11());
			if (c.getForgrado11().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getForgrado11().trim()));

			if (c.getFortipodoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFortipodoc().trim()));
			// System.out.println("c.getFornumdoc(): "+c.getFornumdoc());
			if (c.getFornumdoc().equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(c.getFornumdoc().trim()));
			pst.executeUpdate();

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
			setMensaje("Error SQL intentando ingresar información de la Inscripcinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean validarExistencia(String id) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("ValidarExistencia"));
			pst.clearParameters();
			posicion = 1;
			if (id.equals(""))
				pst.setNull(posicion++, java.sql.Types.VARCHAR);
			else
				pst.setLong(posicion++, Long.parseLong(id.trim()));
			rs = pst.executeQuery();
			if (rs.next())
				return false;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Inscripcinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
	public boolean isCupoLleno(int id) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1,band=0;
		try {
			cn = cursor.getConnection();
			
			pst = cn.prepareStatement(rb.getString("numeroCupos"));
			pst.clearParameters();
			posicion = 1;
			pst.setInt(posicion++, id);
			rs = pst.executeQuery();
			if (rs.next())	band=rs.getInt(1);
			
			if(band==1)	return true;
			else	return false;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("No se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error intentando asignar información de la Inscripcinn. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			}
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return false;
	}
	
}