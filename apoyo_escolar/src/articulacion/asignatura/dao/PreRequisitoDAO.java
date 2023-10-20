package articulacion.asignatura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.asignatura.vo.AsignaturaVO;
import articulacion.asignatura.vo.DatosVO;
import articulacion.asignatura.vo.RequisitoVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

public class PreRequisitoDAO extends Dao {
	
	public PreRequisitoDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.asignatura.bundle.sentencias");
	}
	
	public List getPreRequisitos(DatosVO datosVO,AsignaturaVO asignaturaVO){
		//String componente, int especialidad, int area, int periodo, int codigo, long anoVig, int periodoVig
		//componente,especialidad,area,periodo,asignaturaVO.getArtAsigCodigo(),anoVig,periodoVig
		Connection cn=null;
		PreparedStatement pt=null;
		ResultSet rs=null;
		List lista=new ArrayList();
		RequisitoVO requisitoVO=null;
		int posicion=1,i=0;
		try{
			int componente=Integer.parseInt(datosVO.getComponente());
			cn=cursor.getConnection();
			pt = cn.prepareStatement(rb.getString("lista_Prerequisito."+componente));
			pt.setLong(posicion++,asignaturaVO.getArtAsigCodigo());
			pt.setLong(posicion++,asignaturaVO.getArtAsigCodDinst());
			pt.setInt(posicion++,asignaturaVO.getArtAsigCodMetod());
			pt.setInt(posicion++,componente);
			if(componente!=1) pt.setInt(posicion++,datosVO.getEspecialidad());
			pt.setInt(posicion++,datosVO.getPeriodo());
			pt.setLong(posicion++,datosVO.getAnoVigencia());
			pt.setInt(posicion++,datosVO.getPerVigencia());
			pt.setLong(posicion++,asignaturaVO.getArtAsigCodigo());
			rs=pt.executeQuery();
			while(rs.next()){
				i=1;
				requisitoVO=new RequisitoVO();
				requisitoVO.setAsigCodigo(rs.getInt(i++));
				requisitoVO.setAsigReCodigo(rs.getInt(i++));
				requisitoVO.setAsigNombre(rs.getString(i++));
				requisitoVO.setAsigChecked(rs.getInt(i++));
				if(requisitoVO.getAsigChecked()==1){requisitoVO.setAsigChecked_("checked");}
				lista.add(requisitoVO);
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
			OperacionesGenerales.closeStatement(pt);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return lista;
	}
	
	public List getListaPreReqIns(int asigCodigo){
		Connection conect=null;
        PreparedStatement prepST=null;
        ResultSet rs=null;
        List l=new ArrayList();
        RequisitoVO requisitoVO=null;
        int posicion=1;
        try {
			conect=cursor.getConnection();
			prepST=conect.prepareStatement(rb.getString("ListaPrereqIns"));
			prepST.setInt(posicion++,asigCodigo);
			rs=prepST.executeQuery();
			while(rs.next()){
				posicion=1;
				requisitoVO=new RequisitoVO();
				requisitoVO.setAsigCodigo(rs.getInt(posicion++));
				requisitoVO.setAsigReCodigo(rs.getInt(posicion++));
				requisitoVO.setAsigNombre(rs.getString(posicion++));
				l.add(requisitoVO);
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
	
	public boolean insertar(RequisitoVO requisito){
		Connection conect=null;
        PreparedStatement prepST=null;
        int posicion=1;
        boolean retorno=false;
        try {
        	long []pre=requisito.getNuevoRequisito();
        	conect=cursor.getConnection();
        	conect.setAutoCommit(false);
        	//eliminar
        	prepST=conect.prepareStatement(rb.getString("eliminar_PrerequisitoTotal"));
        	prepST.setLong(1,requisito.getAsigCodigo());
        	prepST.executeUpdate();
        	prepST.close();
        	//insertar
        	if(pre!=null){
	        	prepST=conect.prepareStatement(rb.getString("inserta_Prerequisito"));
	        	prepST.clearBatch();
	        	for(int i=0;i<pre.length;i++){
	        		posicion=1;
					if(pre[i]!=0){
						prepST.setLong(posicion++,requisito.getAsigCodigo());
						prepST.setLong(posicion++,pre[i]);
						prepST.addBatch();
					}
	        	}
	        	prepST.executeBatch();
        	}
			retorno=true;
			conect.commit();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno=false;
		} catch (SQLException e) {
			try{conect.rollback();}catch(SQLException s){}
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
	
	public boolean eliminar(int asigCodigo,String asigPreCodigo){
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminar_Prerequisito"));
			ps.setInt(posicion++,asigCodigo);
			ps.setInt(posicion++,Integer.parseInt(asigPreCodigo));
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			//setMensaje("Error eliminanado Perfil. Posible problema: "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){
			setMensaje("Error eliminando Perfil. Posible problema: "+sqle.toString());
			return false;
		}
		finally{
			try{
			OperacionesGenerales.closeStatement(ps);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	
}
