/**
 * 
 */
package siges.rotacion2.thread.dao;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.rotacion2.thread.vo.ParamsVO;
import siges.rotacion2.thread.vo.RotacionVO;

/**
 * 31/08/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Rotacion2DAO extends Dao{
	
	public Rotacion2DAO(Cursor cur) {
		super(cur);
		rb = ResourceBundle.getBundle("siges.rotacion2.thread.bundle.rotacion2");
	}

	/**
	 * @function: Proceso rotacion mejorado 230310  
	 * @param id
	 * @throws Exception
	 */
	public void procesarRotacion(long id) throws Exception{
		Connection cn = null;
		CallableStatement cst = null;
		try {
			cn = cursor.getConnection();
			//CORRER LA PRIMERA PARTE DE ROTACION 2_1
			cst = cn.prepareCall(rb.getString("rotacion.generarRotacion1"));
			cst.setLong(1,id);
			cst.executeUpdate();
			cst.close();
			//CORRER LA SEGUNDA PARTE DE ROTACION 2_2
			cst = cn.prepareCall(rb.getString("rotacion.generarRotacion2"));
			cst.setLong(1,id);
			cst.executeUpdate();
			cst.close();
		}catch (Exception sqle) {
			System.out.println("Error procesoRotacion:" + sqle);
			sqle.printStackTrace();
			throw new Exception("Error procesoRotacion: "+sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		} finally {
			try {
				OperacionesGenerales.closeStatement(cst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
	
	public boolean isActivo() throws Exception{
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("rotacion.isActivo"));
			rs = pst.executeQuery();
			return rs.next();
		}catch (Exception sqle) {
			System.out.println("Error isActivo:" + sqle);
			sqle.printStackTrace();
			throw new Exception("Error isActivo: "+sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public RotacionVO getSolicitud() throws Exception{
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement pst = null;
		RotacionVO rot=null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("rotacion.getSolicitud"));
			rs = pst.executeQuery();
			if(rs.next()){
				int i=1;
				//DAROTINST,
				//DAROTSEDE,
				//DAROTJOR,
				//DAROTMET,
				//DAROTGRADO,
				//DAROTGRUPO,
				//DAROTUSUARIO,
				//DAROTID,
				//DAROTVIGENCIA
				rot=new RotacionVO();
				rot.setRotInstitucion(rs.getLong(i++));
				rot.setRotSede(rs.getInt(i++));
				rot.setRotJornada(rs.getInt(i++));
				rot.setRotMetodologia(rs.getInt(i++));
				rot.setRotGrado(rs.getInt(i++));
				rot.setRotGrupo(rs.getInt(i++));
				rot.setRotUsuario(rs.getLong(i++));
				rot.setRotId(rs.getLong(i++));
				rot.setRotVigencia(rs.getInt(i++));
				rot.setRotGradoSolicitud(rot.getRotGrado());
			}
		}catch (Exception sqle) {
			System.out.println("Error getSolicitud:" + sqle);
			sqle.printStackTrace();
			throw new Exception("Error getSolicitud: "+sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return rot;
	}

	public RotacionVO actualizarSolicitud(RotacionVO rot) throws Exception{
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			if(rot.getRotEstado()==ParamsVO.SOLICITUD_PROCESANDO)
				pst = cn.prepareStatement(rb.getString("rotacion.actualizarSolicitud1"));
			else 
				pst = cn.prepareStatement(rb.getString("rotacion.actualizarSolicitud2"));
			pst.setInt(1,rot.getRotEstado());
			pst.setString(2,rot.getRotMensaje());
			pst.setLong(3,rot.getRotId());
			pst.setLong(4,rot.getRotUsuario());
			pst.executeUpdate();
			System.out.println(rot.getRotId()+"//"+rot.getRotUsuario()+"//cambio estado a:"+rot.getRotEstado()+"="+pst.executeUpdate());
		}catch (Exception sqle) {
			System.out.println("Error actualizarSolicitud:" + sqle);
			sqle.printStackTrace();
			throw new Exception("Error actualizarSolicitud: "+sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return rot;
	}

	public void restaurarSolicitudes() throws Exception{
		Connection cn = null;
		PreparedStatement pst = null;
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("rotacion.restaurarSolicitud"));
			pst.setInt(1,ParamsVO.SOLICITUD_NUEVO);
			pst.setInt(2,ParamsVO.SOLICITUD_PROCESANDO);
			pst.executeUpdate();
		}catch (Exception sqle) {
			System.out.println("Error restaurarSolicitud:" + sqle);
			sqle.printStackTrace();
			throw new Exception("Error restaurarSolicitud: "+sqle.getMessage().replace('\'', '`').replace('"', 'n'));
		} finally {
			try {
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

}
