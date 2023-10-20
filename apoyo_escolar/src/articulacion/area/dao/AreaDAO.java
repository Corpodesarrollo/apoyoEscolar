package articulacion.area.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.area.vo.AreaVO;
import articulacion.area.vo.FiltroVO;
import articulacion.area.vo.GAreaVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class AreaDAO extends Dao{

	public AreaDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.area.bundle.area");
	}
	
	public List getAllArea(FiltroVO filtro){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		AreaVO areaVO=null;
		int i=0;
		try{
			long institucion=filtro.getFilInstitucion();
			long vigencia=filtro.getFilAnho();
			long periodo=filtro.getFilPer();
//			System.out.println(institucion+":"+vigencia+":"+periodo);
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getAllArea"));
			i=1;
			pst.setLong(i++,institucion);
			pst.setLong(i++,vigencia);
			pst.setLong(i++,periodo);
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				areaVO=new AreaVO();
				areaVO.setAreInstitucion(rs.getLong(i++));
				areaVO.setAreMetodologia(rs.getInt(i++));
				areaVO.setAreCodigo(rs.getLong(i++));
				areaVO.setAreIdentificacion(rs.getLong(i++));
				areaVO.setAreNombre(rs.getString(i++));
				areaVO.setAreAbreviatura(rs.getString(i++));
				areaVO.setAreAnhoVigencia((int)vigencia);
				areaVO.setArePerVigencia((int)periodo);
				lista.add(areaVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}

	public List getAllGArea(){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		GAreaVO areaVO=null;
		int i=0;
		try{
			areaVO=new GAreaVO();
			areaVO.setCodigo(-99);
			areaVO.setNombre("--seleccione uno--");
			lista.add(areaVO);
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getAllGArea"));
			i=1;
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				areaVO=new GAreaVO();
				areaVO.setCodigo(rs.getLong(i++));
				areaVO.setNombre(rs.getString(i++));
				lista.add(areaVO);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}


	public AreaVO getAjaxArea(String id){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		AreaVO areaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getAjaxArea"));			
			i=1;
			pst.setInt(i++,Integer.parseInt(id));
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				areaVO=new AreaVO();
				areaVO.setAreIdentificacion(rs.getLong(i++));
				areaVO.setAreNombre(rs.getString(i++));
				areaVO.setAreAbreviatura(rs.getString(i++));
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return areaVO;
	}

	public AreaVO getArea(AreaVO a){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		AreaVO areaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getArea"));			
			i=1;
			pst.setLong(i++,a.getAreInstitucion());
			pst.setInt(i++,a.getAreMetodologia());
			pst.setLong(i++,a.getAreCodigo());
			pst.setInt(i++,a.getAreAnhoVigencia());
			pst.setInt(i++,a.getArePerVigencia());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				areaVO=new AreaVO();
				areaVO.setAreInstitucion(rs.getLong(i++));
				areaVO.setAreMetodologia(rs.getInt(i++));
				areaVO.setAreCodigo(rs.getLong(i++));
				areaVO.setAreIdentificacion(rs.getLong(i++));
				areaVO.setAreNombre(rs.getString(i++));
				areaVO.setAreOrden(rs.getInt(i++));
				areaVO.setAreAbreviatura(rs.getString(i++));
				areaVO.setAreAnhoVigencia(rs.getInt(i++));
				areaVO.setArePerVigencia(rs.getInt(i++));
				areaVO.setFormaEstado("1");
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return areaVO;
	}
	
	public boolean insertar(AreaVO p){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			//validar si ya esta
//			System.out.println("Validacinn="+p.getAreInstitucion()+"//"+p.getAreMetodologia()+"//"+p.getAreCodigo()+"//"+p.getAreAnhoVigencia()+"//"+p.getArePerVigencia());
			ps = cn.prepareStatement(rb.getString("validarIDArea"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreCodigo());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());
			rs=ps.executeQuery();
			if(rs.next()){
				setMensaje("El área ya existe");
				return false;
			}
			rs.close();ps.close();
			//validar si la identificacion ya esta
			ps = cn.prepareStatement(rb.getString("validarIDArea2"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreIdentificacion());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());
			rs=ps.executeQuery();
			if(rs.next()){
				setMensaje("El cndigo ya existe");
				return false;
			}
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("insertarArea"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreCodigo());
			ps.setLong(posicion++,p.getAreIdentificacion());
			ps.setString(posicion++,p.getAreNombre());
			if(p.getAreOrden()==0) ps.setNull(posicion++,java.sql.Types.VARCHAR);
			else ps.setLong(posicion++,p.getAreOrden());
			ps.setString(posicion++,p.getAreAbreviatura());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error insertantdo Perfil. Posible problema: "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error insertando Perfil. Posible problema: "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public boolean actualizar(AreaVO p,AreaVO p2){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			//validar que la nueva identificacion no se repita
			ps = cn.prepareStatement(rb.getString("validarIdArea3"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreIdentificacion());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());
			ps.setLong(posicion++,p.getAreCodigo());
			rs=ps.executeQuery();
			if(rs.next()){
				setMensaje("La identificacinn coincide con otra");
				return false;
			}
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("actualizarArea"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreIdentificacion());
			ps.setString(posicion++,p.getAreNombre());
			if(p.getAreOrden()==0) ps.setNull(posicion++,java.sql.Types.VARCHAR);
			else ps.setLong(posicion++,p.getAreOrden());
			ps.setString(posicion++,p.getAreAbreviatura());
			//where
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreCodigo());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());

			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error insertantdo Perfil. Posible problema: "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error insertando Perfil. Posible problema: "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public boolean eliminar(AreaVO p){
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
//			System.out.println(p.getAreInstitucion()+"//"+p.getAreMetodologia()+"//"+p.getAreCodigo());
			ps = cn.prepareStatement(rb.getString("eliminarArea"));
			posicion = 1;
			ps.setLong(posicion++,p.getAreInstitucion());
			ps.setLong(posicion++,p.getAreMetodologia());
			ps.setLong(posicion++,p.getAreCodigo());
			ps.setLong(posicion++,p.getAreAnhoVigencia());
			ps.setLong(posicion++,p.getArePerVigencia());
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			switch(sqle.getErrorCode()){
				//Violacinn de llave primaria
				case 1:case 2291: 
					setMensaje("Ya existe un registro con el mismo cndigo. No se puede Actualizar");
		  		break;
		  		//Violacinn de llave secundaria
				case 2292:
					setMensaje("Tiene asignaturas asociadas");
		  		break;
		  		//Longitud de campo
				case 1401:
					setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
					break;
				default:
					setMensaje("Error insertantdo Perfil. Posible problema: "+sqle.getMessage().replace('\'','`').replace('"','n'));
			}
			return false;
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error insertando Perfil. Posible problema: "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
}
