/**
 * 
 */
package siges.institucion.correoLider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.institucion.correoLider.beans.CorreoLiderVO;
import siges.institucion.correoLider.beans.MailingListVO;
import siges.institucion.correoLider.beans.ParamsMail;
import siges.institucion.correoLider.beans.ParamsVO;
import siges.institucion.organizacion.beans.Params;
import siges.login.beans.Login;

/**
 * 24/08/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class CorreoDAO extends Dao {

	public CorreoDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("siges.institucion.correoLider.bundle.correoLider");
	}

	public List getAjaxInstitucion(int localidad) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxInstitucion"));
			i = 1;
			st.setInt(i++, localidad);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
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

	public List getAjaxCargo(int tipoCargo) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;
		int i = 0;
		try {
			if (tipoCargo == ParamsVO.TIPO_CARGO_LIDER) {
				lp = new ItemVO();
				lp.setCodigo(Params.PARAM_PERSONERO);
				lp.setNombre(Params.PARAM_PERSONERO_);
				lpA.add(lp);
				lp = new ItemVO();
				lp.setCodigo(Params.PARAM_CONTRALOR);
				lp.setNombre(Params.PARAM_CONTRALOR_);
				lpA.add(lp);
				lp = new ItemVO();
				lp.setCodigo(Params.PARAM_PRESIDENTE);
				lp.setNombre(Params.PARAM_PRESIDENTE_);
				lpA.add(lp);
				lp = new ItemVO();
				lp.setCodigo(Params.PARAM_CONSEJO);
				lp.setNombre(Params.PARAM_CONSEJO_);
				lpA.add(lp);
				return lpA;
			}
			if (tipoCargo == ParamsVO.TIPO_CARGO_TODOS) {
				return lpA;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxCargo"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
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

	public List getAllLocalidad() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		ItemVO lp = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllLocalidad"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lp = new ItemVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
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

	public List getCorreos(CorreoLiderVO correo) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		MailingListVO listVO = null;
		int i = 0;
		int nivel=0;
		try {
			if(correo.getCorrTipoCargo()==ParamsVO.TIPO_CARGO_TODOS){
				correo.setCorrTipoCargo(0);
			}
			/*
			if(correo.getCorrLocalidad()==-99){
				nivel=0;//nada
			}
			if(correo.getCorrLocalidad()!=-99 && correo.getCorrInstitucion()==-99){
				nivel=1;//loca
			}
			if(correo.getCorrLocalidad()!=-99 && correo.getCorrInstitucion()!=-99){
				nivel=2;//cole
			}
			*/
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getCorreos"+String.valueOf(correo.getCorrTipoCargo())));
			i = 1;
			if(correo.getCorrTipoCargo()==ParamsVO.TIPO_CARGO_ASOCIACION || correo.getCorrTipoCargo()==ParamsVO.TIPO_CARGO_LIDER){
				//lider
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
			}	
			if(correo.getCorrTipoCargo()==ParamsVO.TIPO_CARGO_GOBIERNO){
				//acade
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
				//
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
			}	
			if(correo.getCorrTipoCargo()==0){
				//lider
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
				//acade
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
				//
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
				//aso
				st.setInt(i++, correo.getCorrLocalidad());
				st.setInt(i++, correo.getCorrLocalidad());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrInstitucion());
				st.setLong(i++, correo.getCorrCargo());
				st.setLong(i++, correo.getCorrCargo());
			}
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				listVO = new MailingListVO();
				listVO.setUser(rs.getString(i++));
				listVO.setMail(rs.getString(i++));
				lpA.add(listVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
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

	public List getCorreosPrueba(CorreoLiderVO correo) {
		List lpA = new ArrayList();
		MailingListVO listVO = null;
		listVO = new MailingListVO();
		listVO.setUser("Lira Jazmin Pineda Moreno");
		listVO.setMail("lirap@localhost.com");
//		listVO = new MailingListVO();
//		listVO.setUser("Jaime Andres Gomez Lopez");
//		listVO.setMail("jago@localhost.com");
		lpA.add(listVO);
		return lpA;
	}

	//
	public ParamsMail getParamsMail() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ParamsMail mail = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getParamsCorreo"));
			rs = st.executeQuery();
			while (rs.next()) {
				if (mail == null)
					mail = new ParamsMail();
				if (rs.getString(1).equals("FROM")) {
					mail.setFrom(rs.getString(2));
				}
				if (rs.getString(1).equals("MAILHOST")) {
					mail.setHost(rs.getString(2));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return mail;
	}


	public void insertCorreo(CorreoLiderVO correo,Login login){
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("insertCorreo"));
			i = 1;
			st.setLong(i++, Long.parseLong(login.getUsuarioId()));
			st.setInt(i++, correo.getCorrLocalidad());
			st.setLong(i++, correo.getCorrInstitucion());
			st.setInt(i++, correo.getCorrTipoCargo());
			st.setLong(i++, correo.getCorrCargo());
			st.setString(i++, correo.getCorrAsunto());
			st.setString(i++, correo.getCorrMensaje());
			st.setString(i++, correo.getCorrNombreAdjunto());
			st.setInt(i++, correo.getCorrEstado());
			st.setInt(i++, correo.getCorrTotal());
			st.setInt(i++, correo.getCorrEnviados());
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte){}
		}
	}

}

