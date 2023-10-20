package articulacion.especialidad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.especialidad.vo.EspecialidadBaseVO;
import articulacion.especialidad.vo.EspecialidadVO;
import articulacion.especialidad.vo.ListaProgramaVO;
import articulacion.especialidad.vo.TipoPeriodoVO;
import articulacion.especialidad.vo.UniversidadVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;



public class EspecialidadDAO extends Dao{
	public EspecialidadDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.especialidad.bundle.sentencias");
	}
	
	public List getListaPeriodos(){
		Connection conect=null;
        PreparedStatement prepST=null;
        ResultSet rs=null;
        List l=new ArrayList();
        TipoPeriodoVO periodoVO=null;
        int posicion=1;
        try {
			periodoVO=new TipoPeriodoVO();
			periodoVO.setCodigo(-99);
			periodoVO.setNombre("-Seleccione uno-");
			l.add(periodoVO);
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("getListaPeriodos"));
			rs=prepST.executeQuery();
			while(rs.next()){
				posicion=1;
				periodoVO=new TipoPeriodoVO();
				periodoVO.setCodigo(rs.getInt(posicion++));
				periodoVO.setNombre(rs.getString(posicion++));
				l.add(periodoVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally{
			try{
	        	OperacionesGenerales.closeResultSet(rs);
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	public List getListaUniversidad(){
		Connection conect=null;
        PreparedStatement prepST=null;
        ResultSet rs=null;
        List universidad=new ArrayList();
        UniversidadVO universidadVO=null;
        int posicion=1;
        try {
			universidadVO=new UniversidadVO();
			universidadVO.setCodigo(-99);
			universidadVO.setNombre("-Seleccione una-");
			universidad.add(universidadVO);
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("getListaUniversidad"));
			rs=prepST.executeQuery();
			while(rs.next()){
				posicion=1;
				universidadVO=new UniversidadVO();
				universidadVO.setCodigo(rs.getInt(posicion++));
				universidadVO.setNombre(rs.getString(posicion++));
				universidad.add(universidadVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally{
			try{
	        	OperacionesGenerales.closeResultSet(rs);
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
			}catch(InternalErrorException inte){}
		}
		return universidad;
	}
	
	public List getListaCarBase(String buscar,String inst){
		
		Connection conect=null;
        PreparedStatement prepST=null;
        ResultSet rs=null;
        List carbase=new ArrayList();
        EspecialidadBaseVO carbaseVO=null;
        int posicion=1;
        try {
        	
        	
      //	System.out.println("buscar = "+buscar);
        	carbaseVO=new EspecialidadBaseVO();
        	carbaseVO.setCodigo(-99);
        	carbaseVO.setNombre("-Seleccione una-");
			carbase.add(carbaseVO);
			conect=cursor.getConnection();
			
		  //  System.out.println("Aca que hay en institucion "+inst);
			prepST=conect.prepareStatement(rb.getString(buscar));
			if(buscar.compareToIgnoreCase("getListaCarreraBase2")==0)
			prepST.setString(posicion++,inst);
			
			rs=prepST.executeQuery();
			while(rs.next()){
				posicion=1;
				carbaseVO=new EspecialidadBaseVO();
				carbaseVO.setCodigo(rs.getInt(posicion++));
				carbaseVO.setNombre(rs.getString(posicion++));
				carbase.add(carbaseVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally{
			try{
	        	OperacionesGenerales.closeResultSet(rs);
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
			}catch(InternalErrorException inte){}
		}
		return carbase;
	}
	
	public boolean insertar(EspecialidadVO carrera){
		Connection conect=null;
        PreparedStatement prepST=null;
        int posicion=1;
        boolean retorno=false;
       // System.out.println("llego al metodo insertar del Dao ");
        try {
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("insercion"));
			
			//System.out.println("codigo institucion "+carrera.getCarTipoPrograma());
			prepST.setInt(posicion++,carrera.getCarCodinst());
			prepST.setInt(posicion++,carrera.getCarCodigo());
			prepST.setInt(posicion++,carrera.getCarCouniv());
			prepST.setString(posicion++,carrera.getCarNombre());
			prepST.setString(posicion++,carrera.getCarRegicfes());
			prepST.setString(posicion++,carrera.getCarNumaprob());
			prepST.setString(posicion++,carrera.getCarFecaprob());
			prepST.setInt(posicion++,carrera.getCarNumperiodo());
			prepST.setString(posicion++,carrera.getCarTitulo());
			prepST.setString(posicion++,carrera.getCarDescripcion());
			if(carrera.getCarTipoPrograma()==0 || carrera.getCarTipoPrograma()==-99)
				prepST.setNull(posicion++,Types.VARCHAR);
			
			else
			prepST.setInt(posicion++,carrera.getCarTipoPrograma());
			
			
			prepST.execute();
			//System.out.println("Lo hixo bien ff");
			retorno=true;
			
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno=false;
		} catch (SQLException e) {
			e.printStackTrace();
			retorno=false;
		}finally{
			try{
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
				}catch(InternalErrorException inte){}
		}
		return retorno;
	}
	public boolean eliminar(String codCarbasrera, String parametro){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			//validar art_estudiante
			ps = cn.prepareStatement(rb.getString("validarEliminarEspecialidad"));
			ps.setInt(posicion++,Integer.parseInt(codCarbasrera));
			ps.setInt(posicion++,Integer.parseInt(parametro));
			rs=ps.executeQuery();
			if(rs.next()){
				return false;
			}
			//eliminar
			ps = cn.prepareStatement(rb.getString("eliminacion"));
			ps.setInt(posicion++,Integer.parseInt(codCarbasrera));
			ps.setInt(posicion++,Integer.parseInt(parametro));
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			//setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			switch(sqle.getErrorCode()){
				//Violacinn de llave primaria
				case 1:case 2291: 
					setMensaje("Ya existe un registro con el mismo cndigo. No se puede Actualizar");
		  		break;
		  		//Violacinn de llave secundaria
				case 2292:
					setMensaje(String.valueOf(2292));
		  		break;
		  		//Longitud de campo
				case 1401:
					setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
					break;
				default:
					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
			}
			sqle.printStackTrace();
			return false;
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error eliminando Perfil. Posible problema: "+sqle.toString());
			return false;
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	public EspecialidadVO asignar(String codCarbasrera, String parametro){
		EspecialidadVO carreraVo=null;
		Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int posicion=1,i=1;
        try{
        	
            cn=cursor.getConnection();
            ps=cn.prepareStatement(rb.getString("asignacion"));
            ps.clearParameters();
            posicion=1;
  			ps.setInt(posicion++,Integer.parseInt(codCarbasrera));
  			ps.setInt(posicion++,Integer.parseInt(parametro));
            rs=ps.executeQuery();
            if(rs.next()){
            	//System.out.println("lego a asignar");
            	i=1;
            	carreraVo=new EspecialidadVO();
            	carreraVo.setFormaEstado("1");
            	//este orden es igual al orden de la base de datos
            	
            	
            	carreraVo.setCarCodinst(rs.getInt(i++));
            	carreraVo.setCarCodigo(rs.getInt(i++));
            	carreraVo.setCarCouniv(rs.getInt(i++));
            	carreraVo.setCarNombre(rs.getString(i++));
            	carreraVo.setCarRegicfes(rs.getString(i++));
            	carreraVo.setCarNumaprob(rs.getString(i++));
            	carreraVo.setCarFecaprob(rs.getString(i++));
            	//carreraVo.setCarTipperiodo(rs.getInt(i++));
            	carreraVo.setCarNumperiodo(rs.getInt(i++));
            	carreraVo.setCarTitulo(rs.getString(i++));
            	carreraVo.setCarDescripcion(rs.getString(i++));
            	carreraVo.setCarTipoPrograma(rs.getInt(i++));
            }
        }catch(SQLException sqle){
        	sqle.printStackTrace();
        	try{
        		cn.rollback();
        		}
        	catch(SQLException s){}
        	setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
        }catch(Exception sqle){
        	sqle.printStackTrace();
        	setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
        }
        finally{
			try{
        	OperacionesGenerales.closeResultSet(rs);
        	OperacionesGenerales.closeStatement(ps);
        	OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
        }
        return carreraVo;
	}
	
	public boolean actualizar(EspecialidadVO carrera1,EspecialidadVO carrera2){
		boolean retorno=false;
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion=1;
			try {
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				
				ps = cn.prepareStatement(rb.getString("actualizar"));
				
				ps.setInt(posicion++,carrera1.getCarCouniv());
				ps.setInt(posicion++,carrera1.getCarCodigo());
				
				if(carrera1.getCarNombre().equals(""))ps.setNull(posicion++,Types.VARCHAR);
				else ps.setString(posicion++,carrera1.getCarNombre());
				
				if(carrera1.getCarRegicfes().equals(""))ps.setNull(posicion++,Types.VARCHAR);
				else ps.setString(posicion++,carrera1.getCarRegicfes());
			
				if(carrera1.getCarNumaprob().equals(""))ps.setNull(posicion++,Types.VARCHAR);
				else ps.setString(posicion++,carrera1.getCarNumaprob());
			
				ps.setString(posicion++,carrera1.getCarFecaprob());
				//ps.setInt(posicion++,carrera1.getCarTipperiodo());
				ps.setInt(posicion++,carrera1.getCarNumperiodo());
				
				if(carrera1.getCarTitulo().equals(""))ps.setNull(posicion++,Types.VARCHAR);
				else ps.setString(posicion++,carrera1.getCarTitulo());
				
				if(carrera1.getCarDescripcion().equals(""))ps.setNull(posicion++,Types.VARCHAR);
				else ps.setString(posicion++,carrera1.getCarDescripcion());
				
				if(carrera1.getCarTipoPrograma()==0 || carrera1.getCarTipoPrograma()==-99)
					ps.setNull(posicion++,0);
				
				else
				ps.setInt(posicion++,carrera1.getCarTipoPrograma());
				
				ps.setInt(posicion++,carrera1.getCarCodigo());
				
				
				
				
				ps.execute();
				cn.commit();
				//System.out.println("llego");
				retorno=true;
			}catch(SQLException e){
				e.printStackTrace();
				try{
					cn.rollback();//en caso que falle la actualizacion, sedebe retroceder la accinn
				}catch(SQLException s){}
				setMensaje("Error actualizando Carrera. Posible problema: "+getErrorCode(e));
				return false;
			}catch(Exception e){
				e.printStackTrace();
				setMensaje("Error actualizando Carrera. Posible problema: "+e.toString());
				return false;
			}
			finally{
				try{
					OperacionesGenerales.closeStatement(ps);
					OperacionesGenerales.closeConnection(cn);
				}catch(InternalErrorException inte){}
			}
			
		return retorno;
	}
	
	public List getAllListaEspecialidad(String codigo){
		Connection cn=null;
		ResultSet rs=null;
		PreparedStatement ps = null;
		List listaCarrera=new ArrayList();
		EspecialidadVO carreraVO=null;
		//System.out.println("llega hasta el metodo de listar las carreas");
		//System.out.println("El codigo que llego es: "+codigo);
		int i=0;
		int posicion=1;
		try{
			cn=cursor.getConnection();
			ps=cn.prepareStatement(rb.getString("lista"));
			ps.setInt(posicion++,Integer.parseInt(codigo));
			rs=ps.executeQuery();
			while(rs.next()){
				i=1;
				carreraVO=new EspecialidadVO();
				carreraVO.setCarCodigo(rs.getInt(i++));
				carreraVO.setCarCodinst(rs.getInt(i++));
				carreraVO.setCarNombre(rs.getString(i++));
				carreraVO.setCarUniversidad(rs.getString(i++));
			
				listaCarrera.add(carreraVO);
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
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return listaCarrera;
	
	}
	
	
	public List getListaTipoPrograma(){
		Connection conect=null;
        PreparedStatement prepST=null;
        ResultSet rs=null;
        List l=new ArrayList();
        ListaProgramaVO listaProgramaVO=null;
        int posicion=1;
        try {
        	
        	listaProgramaVO=new ListaProgramaVO();
        	listaProgramaVO.setCodigo(-99);
        	listaProgramaVO.setNombre("-Seleccione una-");
        	l.add(listaProgramaVO);
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("getTipoPrograma"));
			rs=prepST.executeQuery();
			
			
			
			while(rs.next()){
				posicion=1;
				listaProgramaVO=new ListaProgramaVO();
				listaProgramaVO.setCodigo(rs.getInt(posicion++));
				listaProgramaVO.setNombre(rs.getString(posicion++));
				l.add(listaProgramaVO);
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();			
		} catch (SQLException e) {
			e.printStackTrace();			
		}finally{
			try{
	        	OperacionesGenerales.closeResultSet(rs);
	        	OperacionesGenerales.closeStatement(prepST);
	        	OperacionesGenerales.closeConnection(conect);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
}
