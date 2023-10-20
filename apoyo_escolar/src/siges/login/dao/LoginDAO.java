/**
 * 
 */
package siges.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.login.beans.MensajesVO;

/**
 * 17/05/2010
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class LoginDAO extends Dao {
	private ResourceBundle rb;
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public LoginDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.login.bundle.login");
	}

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaMensajes(String perfil, String local, String inst,
			String sede, String jord) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List list = new ArrayList();
		ItemVO itemVO = null;
		int posicion = 1;
		String sql = " SELECT   MSJCODIGO, "
				+ "          MSJASUNTO, "
				+ "          MSJFECHA, "
				+ "         to_char(MSJFECHAINI, 'DD/MM/YYYY' ), "
				+ "         to_char(MSJFECHAFIN, 'DD/MM/YYYY' ), "
				+ "         MSJCONTENIDO, "
				+ "         MSJENVIADOPOR, "
				+ "         MSJENVIADOAPERFIL, "
				+ "         MSJENVIADOALOCAL, "
				+ "         MSJENVIADOACOLEG, "
				+ "         MSJENVIADOASEDE, "
				+ "         MSJENVIADOAJORN, "
				+ "         MSJUSUARIO "
				+ "  FROM   MENSAJE "
				+ "  WHERE   (LENGTH(COALESCE(MSJENVIADOAPERFIL,' ')) = 1 OR INSTR( MSJENVIADOAPERFIL,',"
				+ perfil
				+ ",') > 0)"
				+ "  AND     (LENGTH(COALESCE(MSJENVIADOALOCAL,' ')) = 1 OR INSTR( MSJENVIADOALOCAL,',"
				+ local
				+ ",') > 0)"
				+ "  AND     (LENGTH(COALESCE(MSJENVIADOACOLEG,' ')) = 1 OR INSTR( MSJENVIADOACOLEG,',"
				+ inst
				+ ",') > 0)"
				+ "  AND     (LENGTH(COALESCE(MSJENVIADOASEDE,' ')) = 1 OR INSTR( MSJENVIADOASEDE, ',"
				+ inst
				+ "|"
				+ sede
				+ ",') > 0)"
				+ "  AND     (LENGTH(COALESCE(MSJENVIADOAJORN,' ')) = 1 OR INSTR( MSJENVIADOAJORN, ',"
				+ jord
				+ ",') > 0)"
				+ "  AND  to_date(to_char(sysdate,'dd/mm/yyyy'),'dd/mm/yyyy') between MSJFECHAINI and MSJFECHAfin"
				+ "  ORDER BY MSJFECHA desc";
		try {
			cn = cursor.getConnection();
			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				posicion = 1;

				// SELECT , FROM MENSAJE
				MensajesVO mVO = new MensajesVO();
				mVO.setMsjcodigo(rs.getInt(posicion++)); // MSJCODIGO,
				mVO.setMsjasunto(rs.getString(posicion++)); // MSJASUNTO,
				mVO.setMsjfecha(rs.getString(posicion++)); // MSJFECHA,

				mVO.setMsjfechaini(rs.getString(posicion++));// MSJFECHAINI,
				mVO.setMsjfechafin(rs.getString(posicion++)); // MSJFECHAFIN,
				mVO.setMsjcontenido(rs.getString(posicion++)); // MSJCONTENIDO,

				mVO.setMsjenviadopor(rs.getInt(posicion++)); // MSJENVIADOPOR,
				mVO.setMsjenviadoaperfil(rs.getString(posicion++)); // MSJENVIADOAPERFIL,
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); // MSJENVIADOALOCAL,

				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); // MSJENVIADOACOLEG,
				mVO.setMsjenviadoasede(rs.getString(posicion++)); // MSJENVIADOASEDE,
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); // MSJENVIADOAJORN

				mVO.setMsjusuario(rs.getString(posicion++)); // MSJUSUARIO
				list.add(mVO);
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
		return list;
	}

	public MensajesVO getMensaje(int id) throws Exception {

		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		MensajesVO mVO = new MensajesVO();
		int posicion = 1;
		String sql = " SELECT   MSJCODIGO, " + "          MSJASUNTO, "
				+ "          MSJFECHA, "
				+ "         to_char(MSJFECHAINI, 'DD/MM/YYYY' ), "
				+ "         to_char(MSJFECHAFIN, 'DD/MM/YYYY' ), "
				+ "         MSJCONTENIDO, " + "         MSJENVIADOPOR, "
				+ "         MSJENVIADOAPERFIL, "
				+ "         MSJENVIADOALOCAL, " + "         MSJENVIADOACOLEG, "
				+ "         MSJENVIADOASEDE, " + "         MSJENVIADOAJORN, "
				+ "         MSJUSUARIO " + "  FROM   MENSAJE "
				+ "  WHERE  MSJCODIGO =  " + id;
		try {
			cn = cursor.getConnection();
			// System.out.println("sql " + sql);
			st = cn.prepareStatement(sql);
			posicion = 1;
			rs = st.executeQuery();
			if (rs.next()) {
				posicion = 1;
				// SELECT , FROM MENSAJE

				mVO.setMsjcodigo(rs.getInt(posicion++)); // MSJCODIGO,
				mVO.setMsjasunto(rs.getString(posicion++)); // MSJASUNTO,
				mVO.setMsjfecha(rs.getString(posicion++)); // MSJFECHA,

				mVO.setMsjfechaini(rs.getString(posicion++));// MSJFECHAINI,
				mVO.setMsjfechafin(rs.getString(posicion++)); // MSJFECHAFIN,
				mVO.setMsjcontenido(rs.getString(posicion++)); // MSJCONTENIDO,

				mVO.setMsjenviadopor(rs.getInt(posicion++)); // MSJENVIADOPOR,
				mVO.setMsjenviadoaperfil(rs.getString(posicion++)); // MSJENVIADOAPERFIL,
				mVO.setMsjenviadoalocal(rs.getString(posicion++)); // MSJENVIADOALOCAL,

				mVO.setMsjenviadoacoleg(rs.getString(posicion++)); // MSJENVIADOACOLEG,
				mVO.setMsjenviadoasede(rs.getString(posicion++)); // MSJENVIADOASEDE,
				mVO.setMsjenviadoajorn(rs.getString(posicion++)); // MSJENVIADOAJORN

				mVO.setMsjusuario(rs.getString(posicion++)); // MSJUSUARIO

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
		return mVO;
	}
	
	/**
	 * Metodo que permite cargas las alertas por docente de bajo rendimiento o inasistencia de estudiantes
	 * 
	 * @param l
	 * @return
	 * @throws Exception
	 */
	public Collection listadeAlertasxdocente(Login l,String asignatura, String area) throws Exception{
		
		Collection col=new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("listarmasenestadocero"));
		    st.setLong(1,Long.parseLong(l.getUsuarioId()));
		    st.setString(2, asignatura);
		    st.setString(3, area);
			
			rs = st.executeQuery();
			while (rs.next()) {
				/*String[] datos=new String[8];
				int posicion=0;
	
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion);
				datos[posicion] = rs.getString(++posicion); */
				Collection datos=new ArrayList();
				int posicion=0;
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				datos.add(rs.getString(++posicion));
				
				col.add(datos);
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
		return col;
	
	}
	/**
	 * Metodo que agrupa las asignaturas y areas de las alarmas.
	 * @param l
	 * @return
	 * @throws Exception
	 */
	public Collection listaAreasyAsigxDocente(Login l) throws Exception{
				
				Collection col=new ArrayList();
				Connection cn = null;
				PreparedStatement st = null;
				ResultSet rs = null;
				int i = 1;
				try {
					cn = cursor.getConnection();
					st = cn.prepareStatement(rb.getString("listaasigyarea"));
				    st.setLong(1,Long.parseLong(l.getUsuarioId()));
					
					rs = st.executeQuery();
					while (rs.next()) {
						String[] datos=new String[2];
						int posicion=0;
						datos[posicion] = rs.getString(++posicion);
						datos[posicion] = rs.getString(++posicion);
						col.add(datos);
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
				return col;
			
			}
	/**
	 * Metodo encargado de eliminar alarmas que ya han sido cargadas
	 * @param l
	 * @return
	 * @throws Exception
	 */
	public int limpiarregistrodealarmas(Login l) throws Exception{
		Collection col=new ArrayList();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("eliminaralarmas"));
		    st.setLong(1,Long.parseLong(l.getUsuarioId()));
			
			return st.executeUpdate();
			
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
		
		
	}

}
