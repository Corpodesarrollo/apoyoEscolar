package siges.integracion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.integracion.beans.Datos;

/**
 * 18/03/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class MatriculasDAO extends Dao{
	private String mensaje;

	/**
	 * Constructor de la clase
	 */
	public MatriculasDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("siges.integracion.bundle.integracion");
	}

	public Datos[] getPila(int tipo) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Datos[] datos = new Datos[5];
		try {
			cn=cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("integra.pila."+tipo));
			rs = pst.executeQuery();
			int n = 0;
			int posicion = 1;
			while (rs.next()) {
				posicion = 1;
				datos[n] = new Datos();
				datos[n].setId(rs.getLong(posicion++));
				datos[n].setCategoria(rs.getInt(posicion++));
				datos[n].setFuncion(rs.getInt(posicion++));
				datos[n].setEstado(rs.getInt(posicion++));
				datos[n].setFecha(rs.getString(posicion++));
				datos[n].setP1(rs.getString(posicion++));
				datos[n].setP2(rs.getString(posicion++));
				datos[n].setP3(rs.getString(posicion++));
				datos[n].setP4(rs.getString(posicion++));
				datos[n].setP5(rs.getString(posicion++));
				datos[n].setP6(rs.getString(posicion++));
				datos[n].setP7(rs.getString(posicion++));
				datos[n].setP8(rs.getString(posicion++));
				datos[n].setP9(rs.getString(posicion++));
				datos[n].setP10(rs.getString(posicion++));
				datos[n].setP11(rs.getString(posicion++));
				datos[n].setP12(rs.getString(posicion++));
				datos[n].setP13(rs.getString(posicion++));
				datos[n].setP14(rs.getString(posicion++));
				datos[n].setP15(rs.getString(posicion++));
				datos[n].setP16(rs.getString(posicion++));
				datos[n].setP17(rs.getString(posicion++));
				datos[n].setP18(rs.getString(posicion++));
				datos[n].setP19(rs.getString(posicion++));
				datos[n].setP20(rs.getString(posicion++));
				datos[n].setP21(rs.getString(posicion++));
				datos[n].setP22(rs.getString(posicion++));
				datos[n++].setP23(rs.getString(posicion++));
			}
			return datos;
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String[] getSede(long inst, long jor, long met, long gra, long gru, long vig) {
		String[] sede = new String[2];
		Connection cn = null;
		PreparedStatement pst = null;
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			// get sede
			cn=cursor.getConnection();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("integra.getSede"));
			pst.clearParameters();
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, jor);
			pst.setLong(posicion++, gra);
			pst.setLong(posicion++, gru);
			pst.setLong(posicion++, vig);
			rs = pst.executeQuery();
			if (rs.next()) {
				sede[0] = rs.getString(1);
				sede[1] = rs.getString(2);
				return sede;
			} else {
				return null;
			}
			/*
			 * ESTO SE HACIA PARA PONERLE UNA SEDE POR DEFECTO AL NInO
			 * rs.close();pst.close(); posicion=1;
			 * pst=cn.prepareStatement(rb.getString("integra.getSedeMin"));
			 * pst.clearParameters(); pst.setLong(posicion++,inst);
			 * rs=pst.executeQuery(); if(rs.next()){ sede[0]=rs.getString(1);
			 * sede[1]=""+gru; return sede; }
			 */
		} catch (InternalErrorException inte) {
			System.out.println("Error actualizando pila:");
			return null;
		} catch (Exception inte) {
			inte.printStackTrace();
			System.out.println("Error actualizando pila:");
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// return sede;
	}

	public void ActualizarPila(Datos d) {
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			//System.out.println("Actualiza pila: "+d.getEstado());
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			posicion = 1;
			if(!d.isEliminar()){
				pst = cn.prepareStatement(rb.getString("integra.actualizarEstado"));
				pst.setInt(posicion++, d.getEstado());
				if (d.getMensaje()==null || d.getMensaje().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
				else pst.setString(posicion++, d.getMensaje().length()>189?d.getMensaje().substring(0,190):d.getMensaje());
				pst.setLong(posicion++, d.getId());
				pst.setInt(posicion++, d.getCategoria());
				pst.setInt(posicion++, d.getFuncion());
				pst.executeUpdate();
			}else{
				pst = cn.prepareStatement(rb.getString("integra.eliminar"));
				pst.setLong(posicion++, d.getId());
				pst.setInt(posicion++, d.getCategoria());
				pst.setInt(posicion++, d.getFuncion());
				pst.executeUpdate();
			}
			cn.commit();
		} catch (InternalErrorException inte) {
			System.out.println("Error actualizando pila:");
		} catch (Exception inte) {
			inte.printStackTrace();
			System.out.println("Error actualizando pila:");
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Retorna una variable que contiene los mensajes que se van a enviar a la
	 * vista
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Concatena el parametro en una variable que contiene los mensajes que se
	 * van a enviar a la vista
	 * 
	 * @param String
	 *            s
	 */
	public void setMensaje(String s) {
		mensaje += s;
	}


	public void init() {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn=cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("integra.actualizarEstadoTotal"));
			pst.executeUpdate();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void actualizarActivo(Datos d) {
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn=cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("integra.actualizarEstadoActivo"));
			pst.setLong(1, d.getId());
			pst.executeUpdate();
		} catch (Exception inte) {
			System.out.println("Error actualizando a activo:");
			inte.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
}

