package siges.integracion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Collection;
import java.util.ArrayList;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.exceptions.InternalErrorException;
import siges.dao.OperacionesGenerales;
import siges.integracion.beans.Colegio;
import siges.integracion.beans.Datos;
import siges.integracion.beans.Estudiante;
import siges.integracion.beans.Sede;

/**
 * Nombre: Util Descripcion: Realiza operaciones en la BD Funciones de la
 * pagina: Realiza la logica de negocio Entidades afectadas: todas las tablas de
 * la BD Fecha de modificacinn: Feb-05
 * 
 * @author Latined
 * @version $v 1.3 $
 */
public class IntegracionDAO extends Dao {

	/**
	 * Constructor de la clase
	 */
	public IntegracionDAO(Cursor cur) {
		super(cur);
		rb = ResourceBundle.getBundle("siges.integracion.bundle.integracion");
	}

	public boolean insertarAlumno(Estudiante est) {
		int posicion = 1;
		Connection cn = null;
		CallableStatement cst = null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			// llamar al procedimiento que inserta el nino
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			cst = cn.prepareCall(rb.getString("estudiante.insert"));
			cst.setLong(posicion++, est.getCodigo());// 1
			cst.setLong(posicion++, est.getVigencia());// 2
			cst.setLong(posicion++, est.getDaneInst());// 3
			cst.setLong(posicion++, est.getSede());// 4
			cst.setLong(posicion++, est.getJornada());// 5
			cst.setLong(posicion++, est.getMetodologia());// 6
			cst.setLong(posicion++, est.getGrado());// 7
			cst.setLong(posicion++, est.getGrupo());// 8
			cst.setLong(posicion++, est.getTipoId());// 9
			cst.setString(posicion++, est.getNumId());// 10
			cst.setString(posicion++, est.getApellido1());// 11
			cst.setString(posicion++, est.getApellido2());// 12
			cst.setString(posicion++, est.getNombre1());// 13
			cst.setString(posicion++, est.getNombre2());// 14
			cst.setLong(posicion++, est.getDdNaci());// 15
			cst.setLong(posicion++, est.getDmNaci());// 16
			cst.setInt(posicion++, est.getGenero());// 17
			cst.setString(posicion++, est.getFechaNaci());// 18
			cst.setLong(posicion++, est.getDdExp());// 19
			cst.setLong(posicion++, est.getDmExp());// 20
			cst.setInt(posicion++, est.getEstado());// 21
			cst.setString(posicion++, est.getNomGrupo());// 22
			cst.setLong(posicion++, est.getId());// 23
			cst.executeUpdate();
			cst.close();
			//averiguar si tiene error en la tabla para devolver false 
			pst = cn.prepareStatement(rb.getString("integra.error"));
			pst.setLong(1,est.getId());
			rs=pst.executeQuery();
			if(rs.next()){
				setMensaje(rs.getString(1));
				return false;
			}
			cn.commit();
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeCallableStatement(cst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarIdAlumno(Estudiante est) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			cst = cn.prepareCall(rb.getString("estudiante.updateId"));
			cst.setLong(posicion++, est.getId());// 1
			cst.setLong(posicion++, est.getCodigo());// 2
			cst.setInt(posicion++, est.getVigencia());// 3
			cst.setInt(posicion++, est.getTipoId());// 4
			cst.setString(posicion++, est.getNumId());// 5
			cst.setString(posicion++, est.getApellido1());// 6
			cst.setString(posicion++, est.getApellido2());// 7
			cst.setString(posicion++, est.getNombre1());// 8
			cst.setString(posicion++, est.getNombre2());// 9
			cst.setInt(posicion++, est.getEstado());// 10
			cst.setLong(posicion++, est.getDdExp());// 11
			cst.setLong(posicion++, est.getDmExp());// 12
			cst.setInt(posicion++, est.getGenero());// 13
			cst.executeUpdate();
			cst.close();
			//averiguar si tiene error en la tabla para devolver false 
			pst = cn.prepareStatement(rb.getString("integra.error"));
			pst.setLong(1,est.getId());
			rs=pst.executeQuery();
			if(rs.next()){
				setMensaje(rs.getString(1));
				return false;
			}
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(cst);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public int existeEstudiate(Datos d,long codigo) {
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			long tipoId = Long.parseLong(d.getP8());
			String id = (d.getP9());
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("estudiante.getCodigo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, (tipoId));
			pst.setString(posicion++, (id));
			rs = pst.executeQuery();
			if (!rs.next()) {
				return 0;
			} else {
				//System.out.println("codigos: "+codigo+"//"+rs.getLong(1));
				if(codigo!=rs.getLong(1)) return 2;
				return 1;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return -1;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. Error en INTEGRACION existe:" + sqle);
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return -1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public int existeEstudiateNew(Estudiante e) {
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("estudiante.getCodigo"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, e.getTipoId());
			pst.setString(posicion++, e.getNumId());
			rs = pst.executeQuery();
			if (!rs.next()) {
				return 0;
			} else {
				if(e.getCodigo()!=rs.getLong(1)) return 2;
				return 1;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return -1;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. Error en INTEGRACION existe:" + sqle);
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return -1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
	
	public int existeEstudiateOld(Estudiante e) {
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		int posicion = 1;
		int tipo=0;
		String num=null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("estudiante.getCodigo2"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, e.getCodigo());
			rs = pst.executeQuery();
			if (!rs.next()) {
				return 0;
			} else {
				tipo=rs.getInt(1);
				num=rs.getString(2);
				//if(e.getCodigo()!=rs.getLong(1)) return 2;
				//return 1;
			}
			rs.close();pst.close();
			//System.out.println("VALORES ACTUALES: "+tipo+"//"+num+" VALORES NUEVOS: "+e.getTipoId()+"//"+e.getNumId());
			if(tipo!=e.getTipoId() || !num.equals(e.getNumId())){
				pst = cn.prepareStatement(rb.getString("estudiante.getCodigo"));
				pst.clearParameters();
				posicion = 1;
				pst.setLong(posicion++, e.getTipoId());
				pst.setString(posicion++, e.getNumId());
				rs = pst.executeQuery();
				if (rs.next()) {
					return 2;
				}
			}
			return 1;
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return -1;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. Error en INTEGRACION existe:" + sqle);
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return -1;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
	
	public boolean isActivo() {
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("integra.isActivo"));
			pst.clearParameters();
			rs = pst.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. Error en INTEGRACION isActivo:" + sqle);
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public boolean actualizarAlumno(Estudiante est) {
		int posicion = 1;
		Connection cn = null;
		CallableStatement cst = null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		try {
			// llamar al procedimiento que actualiza el nino
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			cst = cn.prepareCall(rb.getString("estudiante.update"));
			cst.setLong(posicion++, est.getCodigo());// 1
			cst.setInt(posicion++, est.getVigencia());// 2
			cst.setLong(posicion++, est.getDaneInst());// 3
			cst.setLong(posicion++, est.getSede());// 4
			cst.setLong(posicion++, est.getJornada());// 5
			cst.setLong(posicion++, est.getMetodologia());// 6
			cst.setLong(posicion++, est.getGrado());// 7
			cst.setLong(posicion++, est.getGrupo());// 8
			cst.setLong(posicion++, est.getOldDaneInst());// 9
			cst.setLong(posicion++, est.getOldSede());// 10
			cst.setLong(posicion++, est.getOldJornada());// 11
			cst.setLong(posicion++, est.getOldMetodologia());// 12
			cst.setLong(posicion++, est.getOldGrado());// 13
			cst.setLong(posicion++, est.getOldGrupo());// 14
			cst.setLong(posicion++, est.getTipoId());// 15
			cst.setString(posicion++, est.getNumId());// 16
			cst.setInt(posicion++, est.getEstado());// 17
			cst.setString(posicion++, est.getNomGrupo());// 18
			cst.setLong(posicion++, est.getId());// 19
			cst.executeUpdate();
			cst.close();
			//averiguar si tiene error en la tabla para devolver false 
			pst = cn.prepareStatement(rb.getString("integra.error"));
			pst.setLong(1,est.getId());
			rs=pst.executeQuery();
			if(rs.next()){
				setMensaje(rs.getString(1));
				return false;
			}
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(cst);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean eliminarSede(Sede s) {
		int posicion = 1;
		long inst = 0;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean band = false;
		try {// inst viejo,inst nuevo, dane sede, cons sede
			//String daneinst = d.getP1().trim();
			//String danesede = d.getP2().trim();
			//String cons = d.getP3().trim();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.getCodigo"));
			pst.setLong(posicion++, s.getDaneColegioOld());
			rs = pst.executeQuery();
			if (rs.next())
				inst = rs.getLong(1);
			rs.close();
			pst.close();
			// validar si tiene jornadas
			pst = cn.prepareStatement(rb.getString("sede.deleteSede0"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, s.getCodigoOld());
			rs = pst.executeQuery();
			if (rs.next()) {
				band = true;
			}
			rs.close();
			pst.close();
			if (band) {
				// poner estado en 'I'
				pst = cn.prepareStatement(rb.getString("sede.estadoSede"));
				pst.clearParameters();
				posicion = 1;
				pst.setString(posicion++, "I");
				pst.setLong(posicion++, inst);
				pst.setLong(posicion++, s.getCodigoOld());
				int n = pst.executeUpdate();
				// System.out.println("cambio estado a sede ="+n);
				cleanMensaje();
				setMensaje("La sede tiene jornadas, se cambia estado a Inactiva");
			} else {
				// elimina en sede y jerarquia
				for (int j = 1; j <= 2; j++) {
					pst = cn.prepareStatement(rb.getString("sede.deleteSede" + j));
					pst.clearParameters();
					posicion = 1;
					pst.setLong(posicion++, inst);
					pst.setLong(posicion++, s.getCodigoOld());
					int n = pst.executeUpdate();
					pst.close();
					// System.out.println("elimino sede para "+j+" ="+n);
				}
			}
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
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

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 */
	public boolean insertarSede(Sede s) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		long inst = 0, loc = 0;
		long depto = 0;
		long mun = 0;
		ResultSet rs = null;
		try {
			//long dane = Long.parseLong(d.getP1().trim());
			//long daneSede = Long.parseLong(d.getP2().trim());
			//long cons = Long.parseLong(d.getP3().trim());
			//String nombre = d.getP4().trim();
			//String dir = d.getP5().trim();
			//String tel = d.getP6().trim();
			//String est = d.getP7().trim();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.getUbicacion"));
			pst.setLong(posicion++, s.getDaneColegio());
			rs = pst.executeQuery();
			if (rs.next()) {
				depto = rs.getLong(1);
				mun = rs.getLong(2);
				loc = rs.getLong(3);
				inst = rs.getLong(4);
			}
			rs.close();
			pst.close();
			
			pst = cn.prepareStatement(rb.getString("sede.insertar"));
			pst.clearParameters();
			posicion = 1;
			// sedcodins,sedcodigo,sedcoddaneanterior,sednombre
			pst.setLong(posicion++, inst);
			pst.setLong(posicion++, s.getCodigo());
			pst.setLong(posicion++, s.getDaneSede());
			pst.setString(posicion++, s.getNombre());
			pst.setString(posicion++, s.getDireccion());
			pst.setString(posicion++, s.getTelefono());
			pst.setString(posicion++, s.getEstado());
			int n = pst.executeUpdate();
			// System.out.println("insertando la sede "+n);
			posicion = 1;
			pst.close();
			pst = cn.prepareStatement(rb.getString("sede.insertar2"));
			pst.clearParameters();
			// G_JerDepto, G_JerMunic, G_JerLocal, G_JerInst, G_JerSede
			pst.setLong(posicion++, (depto));
			pst.setLong(posicion++, (mun));
			pst.setLong(posicion++, (loc));
			pst.setLong(posicion++, (inst));
			pst.setLong(posicion++, s.getCodigo());
			n = pst.executeUpdate();
			// System.out.println("insertando jerarquia en sede "+n);
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException ss) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
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

	/**
	 * Funcinn: Insertar Institucinn<br>
	 * 
	 * @param Institucion
	 *            i
	 * @return boolean
	 */
	public synchronized boolean insertarInstitucion(Colegio c) {
		int posicion = 1;
		Statement st = null;
		long cod = 0;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			//validar si ya esta
			pst = cn.prepareStatement(rb.getString("institucion.getInstitucion"));
			pst.clearParameters();
			posicion=1;
			pst.setLong(posicion++, (c.getDane11()));// dane
			rs=pst.executeQuery();
			if(rs.next()){
				cleanMensaje();
				setMensaje("La institucinn ya existe");
				return false;
			}
			rs.close();
			pst.close();
			//insertar
			pst = cn.prepareStatement(rb.getString("institucion.insertar"));
			pst.clearParameters();
			pst.setLong(posicion++, c.getDane11());// dane
			pst.setLong(posicion++, c.getDepartamento());// deptp
			pst.setLong(posicion++, c.getLocalidad());// mun
			pst.setLong(posicion++, c.getLocalidad());// local
			pst.setString(posicion++, c.getNombre());// nombre
			pst.setString(posicion++, c.getEstado());// estado
			pst.setLong(posicion++, c.getZona());// sector
			pst.setString(posicion++, c.getEstado());// estado
			pst.executeUpdate();
			// System.out.println("inserto institucion:");
			pst.close();
			// traer el codigo de institucion.
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("institucion.getCodigo"));
			pst.clearParameters();
			pst.setLong(posicion++, c.getDane11());// dane
			rs = pst.executeQuery();
			if (rs.next()) {
				cod = rs.getLong(1);
			} else {
				cleanMensaje();
				setMensaje("No se encuentra cndigo del colegio insertado para insertar la Jerarquia");
				cn.rollback();
				return false;
			}
			rs.close();
			pst.close();
			// insertar en jerarquia
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("institucion.insertar2"));
			pst.clearParameters();
			pst.setInt(posicion++, 1);// TIPO 1
			pst.setInt(posicion++, 4);// NIVEL=4 INSTITUCIONES
			pst.setLong(posicion++, c.getDepartamento());
			pst.setLong(posicion++, c.getLocalidad());
			pst.setLong(posicion++, c.getLocalidad());
			pst.setLong(posicion++, cod);
			pst.executeUpdate();
			// System.out.println("inserto institucion2:");
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. "+sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} 
		finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public boolean actualizarDaneSede(Sede s) {
		//long inst = 0;
		Connection cn = null;
		PreparedStatement pst = null;
		CallableStatement cst = null;
		Statement st = null;
		ResultSet rs = null;
		int posicion = 1;
		try {// dane inst, consecutivo sede, dane sede viejo, dane sede nuevo
			//long daneOld = Long.parseLong(d.getP1().trim());
			//long daneNew = Long.parseLong(d.getP2().trim());
			//long daneSedeOld = Long.parseLong(d.getP3().trim());
			//long daneSedeNew = Long.parseLong(d.getP4().trim());
			//long consOld = Long.parseLong(d.getP5().trim());
			//long consNew = Long.parseLong(d.getP6().trim());
			//String nom = (d.getP7().trim());
			//String dir = (d.getP8().trim());
			//String tel = (d.getP9().trim());
			//String esta = (d.getP10().trim());
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			cst = cn.prepareCall(rb.getString("sede.update"));
			posicion = 1;
			cst.clearParameters();
			cst.setLong(posicion++,s.getDaneColegioOld());
			cst.setLong(posicion++,s.getDaneColegio());
			cst.setLong(posicion++,s.getDaneSedeOld());
			cst.setLong(posicion++,s.getDaneSede());
			cst.setLong(posicion++,s.getCodigoOld());
			cst.setLong(posicion++,s.getCodigo());
			cst.setString(posicion++,s.getNombre());
			cst.setString(posicion++,s.getDireccion());
			cst.setString(posicion++,s.getTelefono());
			cst.setString(posicion++,s.getEstado());
			cst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException se) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	/*
	 * Actualizar Institucion en archivo1
	 */
	public boolean actualizarDaneInstitucion(Colegio c) {
		long inst = 0;
		String nomOld = null;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int posicion = 1;
		try {
			//long daneOld = Long.parseLong(d.getP1().trim());
			//long daneNew = Long.parseLong(d.getP2().trim());
			//long loc = Long.parseLong(d.getP3().trim());
			//String nom = d.getP4().trim();
			//String est = d.getP5().trim();
			//long sector = Long.parseLong(d.getP6().trim());
			//String dir= d.getP7().trim();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.getCodigoNombre"));
			pst.clearParameters();
			pst.setLong(posicion++, c.getDane11Old());
			rs = pst.executeQuery();
			if (rs.next()) {
				inst = rs.getLong(1);
				nomOld = rs.getString(2);
			}
			rs.close();
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("institucion.update"));
			pst.clearParameters();
			pst.setLong(posicion++, c.getDane11());
			pst.setLong(posicion++, c.getLocalidad());
			pst.setLong(posicion++, c.getLocalidad());
			pst.setString(posicion++, c.getNombre());
			pst.setString(posicion++, c.getEstado());
			pst.setLong(posicion++, c.getZona());
			pst.setString(posicion++, c.getDireccion());
			pst.setLong(posicion++, c.getDane11Old());
			int n = pst.executeUpdate();
			// System.out.println("Actualizo Dane: "+n);
			pst.close();
			posicion = 1;
			pst = cn.prepareStatement(rb.getString("institucion.update2"));
			pst.clearParameters();
			pst.setLong(posicion++, c.getLocalidad());
			pst.setLong(posicion++, c.getLocalidad());
			pst.setLong(posicion++, (inst));
			n = pst.executeUpdate();
			pst.close();
			//
			pst = cn.prepareStatement(rb.getString("historicoInstitucion"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, 2);// actualizacion de dane
			pst.setLong(posicion++, inst);
			pst.setNull(posicion++, java.sql.Types.VARCHAR);
			pst.setLong(posicion++, c.getDane11Old());
			pst.setLong(posicion++, c.getDane11());
			pst.setString(posicion++, nomOld);
			pst.setString(posicion++, c.getNombre());
			n = pst.executeUpdate();
			// System.out.println("Inserto historico: "+n);
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
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

	public boolean eliminarInstitucion(Colegio c) {
		int posicion = 1;
		long inst = 0;
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {// inst viejo,loc viejo
			//String daneinst = d.getP1().trim();
			//String locInst = d.getP2().trim();
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			pst = cn.prepareStatement(rb.getString("institucion.getCodigo"));
			pst.setLong(posicion++, c.getDane11Old());
			rs = pst.executeQuery();
			if (rs.next()) {
				inst = rs.getLong(1);
			} else {
				setMensaje("No existe la Institucinn");
				return false;
			}
			rs.close();
			pst.close();
			// poner estado en 'I'
			pst = cn.prepareStatement(rb.getString("institucion.updateEstado"));
			pst.clearParameters();
			posicion = 1;
			pst.setLong(posicion++, inst);
			pst.executeUpdate();
			cn.commit();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			System.out.println("INTEGRACION." + sqle.getMessage());
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
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

	public boolean registrarNovedad(Datos d) {
		int posicion = 1;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			//insertar
			//ID, CATEGORIA, FUNCION,FECHA, ESTADO, MENSAJE,P1, P2, P3, P4, P5, P6, P7, P8, P9,P10, P11, P12,P13, P14, P15,P16, P17, P18,P19, P20, P21,P22, P23
			pst = cn.prepareStatement(rb.getString("integra.registroNovedad"));
			pst.clearParameters();
			pst.setLong(posicion++, d.getId());
			pst.setInt(posicion++, d.getCategoria());
			pst.setInt(posicion++, d.getFuncion());
			pst.setInt(posicion++, d.getEstado());
			pst.setString(posicion++, d.getMensaje());
			pst.setString(posicion++, d.getP1());
			pst.setString(posicion++, d.getP2());
			pst.setString(posicion++, d.getP3());
			pst.setString(posicion++, d.getP4());
			pst.setString(posicion++, d.getP5());
			pst.setString(posicion++, d.getP6());
			pst.setString(posicion++, d.getP7());
			pst.setString(posicion++, d.getP8());
			pst.setString(posicion++, d.getP9());
			pst.setString(posicion++, d.getP10());
			pst.setString(posicion++, d.getP11());
			pst.setString(posicion++, d.getP12());
			pst.setString(posicion++, d.getP13());
			pst.setString(posicion++, d.getP14());
			pst.setString(posicion++, d.getP15());
			pst.setString(posicion++, d.getP16());
			pst.setString(posicion++, d.getP17());
			pst.setString(posicion++, d.getP18());
			pst.setString(posicion++, d.getP19());
			pst.setString(posicion++, d.getP20());
			pst.setString(posicion++, d.getP21());
			pst.setString(posicion++, d.getP22());
			pst.setString(posicion++, d.getP23());
			pst.executeUpdate();
			pst.close();
		} catch (InternalErrorException in) {
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return false;
		} catch (Exception sqle) {
			sqle.printStackTrace();
			System.out.println("INTEGRACION. "+sqle);
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			cleanMensaje();
			setMensaje("Posible problema: ");
			setMensaje(sqle.getMessage().replace('\'', '`').replace('"', 'n'));
			return false;
		} 
		finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}
	
}