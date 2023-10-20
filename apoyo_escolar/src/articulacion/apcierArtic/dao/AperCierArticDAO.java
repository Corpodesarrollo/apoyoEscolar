package articulacion.apcierArtic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.apcierArtic.vo.*;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;


public class AperCierArticDAO extends Dao {
	
    private ResourceBundle rb;
    
    public AperCierArticDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.apcierArtic.bundle.sentencias");
	}
       
   
   public EspecialidadVO[] getListaEspecialidad(String insti){
		EspecialidadVO[] especialidad=null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la=new ArrayList();
		try{
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(posicion++,insti);
			posicion = 1;
			rs = ps.executeQuery();
			EspecialidadVO a=null;
			while(rs.next()){
				a=new EspecialidadVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				la.add(a);
			}			
			rs.close();
			ps.close();
			if(!la.isEmpty()){
				int i=0;
				especialidad=new EspecialidadVO[la.size()];
				Iterator iterator =la.iterator();
				while (iterator.hasNext())
					especialidad[i++]=(EspecialidadVO)(iterator.next());
			}
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error Posible problema: "+sqle);
		}catch(Exception sqle){sqle.printStackTrace();
			setMensaje("Error Posible problema: "+sqle.toString());
		}
		finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){inte.printStackTrace();}
		}
		return especialidad;
	}
   
   
   public List getAjaxGrupo(long inst,int metod,int sede, int jornada,int anVigencia, int perVigencia, int componente, long especialidad){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		GrupoVO lp=null;
		int i=0;
		try{
			if(inst==0 || sede==0 || jornada==0 || anVigencia==0 || perVigencia==0 || componente==-99){return null;}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxGrupo"+componente));
			i=1;
	
			st.setLong(i++,inst);
			st.setLong(i++,sede);
			st.setLong(i++,jornada);
			st.setLong(i++,anVigencia);
			st.setLong(i++,perVigencia);
			st.setLong(i++,componente);
			if(componente==2)
				st.setLong(i++,especialidad);
			
	//		System.out.println("Metodo grupo");
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
	//			System.out.println("Encuentra datos en grupo");
				lp=new GrupoVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setConsecutivo(rs.getString(i++));
				lpA.add(lp);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lpA;
	}
   public List getAjaxAsignatura(long grupo){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
	//		System.out.println("Grupo= "+grupo);
			if(grupo==0){return null;}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,grupo);
			
			rs=st.executeQuery();
	//		System.out.println("------------>Metodo asignatura");
			while(rs.next()){
				i=1;
	//			System.out.println("Encuentra datos en ajax Asignatura");
				lp=new AsignaturaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lpA.add(lp);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lpA;
	}
   public List getListaPrueba(DatosVO datosVO){
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ListaPruebaVO lp=null;
		int i=0,posicion=1;
		try{
			cn=cursor.getConnection();
			ps=cn.prepareStatement(rb.getString("getListaPruebas"));
			ps.setLong(posicion++,datosVO.getAsignatura());
			ps.setLong(posicion++,datosVO.getBimestre());
			rs=ps.executeQuery();
		//	System.out.println("antes de buscar pruebas "+datosVO.getBimestre());
			while(rs.next()){
		//		System.out.println("encuantra pruebas");
				i=1;
				lp=new ListaPruebaVO();
				lp.setCodigo(rs.getInt(i++));
				lp.setNombre(rs.getString(i++));
				lp.setEstado(rs.getBoolean(i++));
				lp.setFecha(rs.getString(i++));
				lpA.add(lp);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lpA;
	}

	public boolean actualizaEstado(DatosVO datosVO, PruebaVO pruebaVO) {
		boolean retorno=false;
		Connection cn=null;
		PreparedStatement ps=null;
		int posicion=1;
		try {
			cn=cursor.getConnection();
						
			ps=cn.prepareStatement(rb.getString("actualizar_Estado"));
			
			if(pruebaVO.isEstado())
				ps.setBoolean(posicion++,false);
			else
				ps.setBoolean(posicion++,true);			
			
			ps.setLong(posicion++,datosVO.getAsignatura());
			ps.setLong(posicion++,pruebaVO.getCodigo());
			ps.setLong(posicion++,datosVO.getBimestre());
			
			
			ps.executeUpdate();
			retorno=true;
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1: case 2291:
				setMensaje("Cndigo duplicado");
				break;
			case 2292:
				setMensaje("Registros Asociados");
				break;
				
			}
			e.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return retorno;
	}
}
