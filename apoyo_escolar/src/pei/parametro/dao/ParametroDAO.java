/**
 * 
 */
package pei.parametro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import pei.parametro.vo.FiltroParametroVO;
import pei.parametro.vo.ParametroVO;
import pei.parametro.vo.ParamsVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.inasistencia.vo.ParamVO;

/**
 * @author john
 *
 */
public class ParametroDAO extends Dao {

	/**
	 * @param c
	 */
	public ParametroDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("pei.parametro.bundle.Parametro");
	}

	public List getListaEstado() throws Exception{
		List lista=new ArrayList();
		lista.add(new ItemVO(ParamsVO.ESTADO_PARAMETRO_ACTIVO, ParamsVO.ESTADO_PARAMETRO_ACTIVO_));
		lista.add(new ItemVO(ParamsVO.ESTADO_PARAMETRO_INACTIVO, ParamsVO.ESTADO_PARAMETRO_INACTIVO_));
		return lista;
	}
	
	public List getListaTipo() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("pei.listaParametroPadre"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	public List getListaParametro(FiltroParametroVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ParametroVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("parametro.lista"));
			st.setInt(1, filtro.getFilTipo());
			st.setInt(2, filtro.getFilEstado());
			st.setInt(3, filtro.getFilEstado());
			rs=st.executeQuery();
			while(rs.next()){
				//PARHCODPADRE, PARHCODIGO, PARHNOMBRE, PARHABREV, 
				//PARHDESCRIPCION, PARHESTADO, PARHORDEN, PARHEDICION, 
				//PARHVALMAX
				i=1;
				item=new ParametroVO();
				item.setParTipo(rs.getInt(i++));
				item.setParCodigo(rs.getInt(i++));
				item.setParNombre(rs.getString(i++));
				item.setParAbreviatura(rs.getString(i++));
				item.setParDescripcion(rs.getString(i++));
				item.setParEstado(rs.getInt(i++));
				item.setParOrden(rs.getInt(i++));
				item.setParEdicion(rs.getInt(i++));
				item.setParValor(rs.getInt(i++));
				item.setParNombreEstado(getEstado(item.getParEstado()));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	public ParametroVO getParametro(ParametroVO parametro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ParametroVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("parametro.get"));
			st.setInt(1, parametro.getParTipo());
			st.setInt(2, parametro.getParCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				//PARHCODPADRE, PARHCODIGO, PARHNOMBRE, PARHABREV, 
				//PARHDESCRIPCION, PARHESTADO, PARHORDEN, PARHEDICION, 
				//PARHVALMAX
				i=1;
				item=new ParametroVO();
				item.setEdicion(true);
				item.setParDisabled(true);
				item.setParDisabled_(ParamVO.DISABLED);
				item.setParTipo(rs.getInt(i++));
				item.setParCodigo(rs.getInt(i++));
				item.setParNombre(rs.getString(i++));
				item.setParNombreOld(item.getParNombre());
				item.setParAbreviatura(rs.getString(i++));
				item.setParDescripcion(rs.getString(i++));
				item.setParEstado(rs.getInt(i++));
				item.setParOrden(rs.getInt(i++));
				item.setParEdicion(rs.getInt(i++));
				item.setParValor(rs.getInt(i++));
				item.setParNombreEstado(getEstado(item.getParEstado()));
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public ParametroVO ingresarParametro(ParametroVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar ingreso
			st=cn.prepareStatement(rb.getString("parametro.validar.insertar"));
			i=1;
			st.setInt(i++, item.getParTipo());
			st.setString(i++, item.getParNombre());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("El nombre del parnmetro ya estn registrado");
			}
			rs.close();
			st.close();
			//calcular codigo
			st=cn.prepareStatement(rb.getString("parametro.id"));
			i=1;
			st.setInt(i++, item.getParTipo());
			rs=st.executeQuery();
			if(rs.next()){
				item.setParCodigo(rs.getInt(1));
			}
			rs.close();
			st.close();
			//ingresar
			st=cn.prepareStatement(rb.getString("parametro.insertar"));
			i=1;
			st.setInt(i++, item.getParTipo());
			st.setInt(i++, item.getParCodigo());
			st.setString(i++, item.getParNombre());
			st.setString(i++, item.getParAbreviatura());
			st.setString(i++, item.getParDescripcion());
			st.setInt(i++, item.getParEstado());
			st.setInt(i++, item.getParOrden());
			st.setInt(i++, item.getParEdicion());
			st.setInt(i++, item.getParValor());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public ParametroVO actualizarParametro(ParametroVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar ingreso
			if(!item.getParNombre().equalsIgnoreCase(item.getParNombreOld())){
				st=cn.prepareStatement(rb.getString("parametro.validar.actualizar"));
				i=1;
				st.setInt(i++, item.getParTipo());
				st.setString(i++, item.getParNombre());
				st.setString(i++, item.getParNombreOld());
				rs=st.executeQuery();
				if(rs.next()){
					throw new Exception("El nombre del parnmetro ya estn registrado");
				}
				rs.close();
				st.close();
			}
			//actualizar
			st=cn.prepareStatement(rb.getString("parametro.actualizar"));
			i=1;
			st.setString(i++, item.getParNombre());
			st.setString(i++, item.getParAbreviatura());
			st.setString(i++, item.getParDescripcion());
			st.setInt(i++, item.getParEstado());
			st.setInt(i++, item.getParOrden());
			st.setInt(i++, item.getParEdicion());
			st.setInt(i++, item.getParValor());
			//
			st.setInt(i++, item.getParTipo());
			st.setInt(i++, item.getParCodigo());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	public ParametroVO eliminarParametro(ParametroVO item) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			//validar eliminacion
			st=cn.prepareStatement(rb.getString("parametro.validar.eliminar."+item.getParTipo()));
			i=1;
			st.setInt(i++, item.getParCodigo());
			if(item.getParTipo()==9 || item.getParTipo()==10)st.setInt(i++, item.getParCodigo());
			rs=st.executeQuery();
			if(rs.next()){
				throw new Exception("No se puede eliminar el parnmetro porque tiene información asociada");
			}
			rs.close();
			st.close();
			//eliminar
			st=cn.prepareStatement(rb.getString("parametro.eliminar"));
			i=1;
			st.setInt(i++, item.getParTipo());
			st.setInt(i++, item.getParCodigo());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	private String getEstado(int estado){
		switch(estado){
			case ParamsVO.ESTADO_PARAMETRO_ACTIVO:
				return ParamsVO.ESTADO_PARAMETRO_ACTIVO_;
			case ParamsVO.ESTADO_PARAMETRO_INACTIVO:
				return ParamsVO.ESTADO_PARAMETRO_INACTIVO_;
			default: 
			return ParamsVO.ESTADO_PARAMETRO_INACTIVO_;
		}
	}
}
