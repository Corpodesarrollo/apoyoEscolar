/**
 * 
 */
package siges.recuperacion.dao;

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
import siges.recuperacion.vo.FiltroRecuperacionVO;
import siges.recuperacion.vo.ParamsVO;
import siges.recuperacion.vo.RecuperacionVO;

/**
 * 12/02/2009 
 * @author Latined
 * @version 1.2
 */
public class RecuperacionDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c
	 */
	public RecuperacionDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("siges.recuperacion.bundle.recuperacion");
	}

    public List getListaMetodologia(long inst) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	try{
    		cn=cursor.getConnection();		
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaMetodologia"));
      		pst.setLong(1,inst);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaGrado(long inst,int met) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	int i=1;
    	try{
    		cn=cursor.getConnection();		
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaGrado"));
      		pst.setLong(i++,inst);
      		pst.setInt(i++,met);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaGrupo(long inst,int sede, int jornada, int met, int grado) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	int i=1;long cod=0;
    	try{
    		cn=cursor.getConnection();
    		//calculo de codigo
      		pst=cn.prepareStatement(rb.getString("recuperacion.jerarquiaGrado"));
      		pst.setLong(i++,inst);
      		pst.setInt(i++,sede);
      		pst.setInt(i++,jornada);
      		pst.setInt(i++,met);
      		pst.setInt(i++,grado);
      		rs=pst.executeQuery();
      		if(rs.next()){
      			cod=rs.getLong(1);
      		}
      		rs.close();
      		pst.close();
    		//calculo de grupos
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaGrupo"));
      		i=1;
      		pst.setLong(i++,cod);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaOrden() throws Exception{
    	List l=new ArrayList();
    	ItemVO item=null;
   		item=new ItemVO(ParamsVO.ORDEN_APELLIDO ,ParamsVO.ORDEN_APELLIDO_); l.add(item);
   		item=new ItemVO(ParamsVO.ORDEN_NOMBRE ,ParamsVO.ORDEN_NOMBRE_); l.add(item);
   		item=new ItemVO(ParamsVO.ORDEN_IDENTIFICACION ,ParamsVO.ORDEN_IDENTIFICACION_); l.add(item);
    	return l;
    }

    public List getListaArea(long inst,int met, int grado,int vig) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	int i=1;
    	try{
    		cn=cursor.getConnection();		
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaArea"));
      		pst.setLong(i++,inst);
      		pst.setInt(i++,met);
      		pst.setInt(i++,vig);
      		pst.setInt(i++,grado);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaAsignatura(long inst,int met, int grado,int vig) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	int i=1;
    	try{
    		cn=cursor.getConnection();		
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaAsignatura"));
      		pst.setLong(i++,inst);
      		pst.setInt(i++,met);
      		pst.setInt(i++,vig);
      		pst.setInt(i++,grado);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaVigencias() throws Exception{
    	List l=new ArrayList();
    	try{
	   		int vigActual=(int)getVigenciaNumerico();
	   		l.add(new ItemVO(vigActual-1,String.valueOf(vigActual-1))); 
	   		l.add(new ItemVO(vigActual,String.valueOf(vigActual))); 
       	}catch(Exception e){
            e.printStackTrace();
            //throw new Exception("Error Interno: "+e.getMessage());
        }      
     	return l;
    }

    public List getListaRecuperacionArea(FiltroRecuperacionVO filtro) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	RecuperacionVO recuperacion=null;
    	int i=1;int j=1;
    	try{
    		cn=cursor.getConnection();
    		//calcula la jjerarquia de grupo
    		pst=cn.prepareStatement(rb.getString("recuperacion.jerarquiaGrupo"));
    		pst.setLong(i++,filtro.getFilInstitucion());
    		pst.setInt(i++,filtro.getFilSede());
    		pst.setInt(i++,filtro.getFilJornada());
    		pst.setInt(i++,filtro.getFilMetodologia());
    		pst.setInt(i++,filtro.getFilGrado());
    		pst.setInt(i++,filtro.getFilGrupo());
    		rs=pst.executeQuery();
    		if(rs.next()){
    			filtro.setFilJerarquiaGrupo(rs.getLong(1));
    		}    		
    		rs.close();pst.close();
    		i=1;
    		pst=cn.prepareStatement(rb.getString("recuperacion.jerarquiaArea"));
    		pst.setLong(i++,filtro.getFilInstitucion());
    		pst.setInt(i++,filtro.getFilMetodologia());
    		pst.setInt(i++,filtro.getFilGrado());
    		pst.setLong(i++,filtro.getFilArea());
    		pst.setInt(i++,filtro.getFilVigencia());
    		rs=pst.executeQuery();
    		if(rs.next()){
    			filtro.setFilJerarquiaArea(rs.getLong(1));
    		}    		
    		rs.close();pst.close();
    		i=1;
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaRecuperacionArea")+" "+rb.getString("recuperacion.listaRecuperacionArea.orden"+filtro.getFilOrden()));
      		pst.setLong(i++,filtro.getFilJerarquiaArea());
      		pst.setInt(i++,filtro.getFilVigencia());
      		pst.setLong(i++,filtro.getFilJerarquiaGrupo());
      		pst.setInt(i++,5);
      		pst.setInt(i++,4);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			i=1;      			
      			recuperacion=new RecuperacionVO();
      			recuperacion.setRecConsecutivo(j++);
      			recuperacion.setRecCodigo(rs.getLong(i++));
      			recuperacion.setRecApellido(rs.getString(i++));
      			recuperacion.setRecNombre(rs.getString(i++));
      			recuperacion.setRecNotaFinal(rs.getString(i++));
      			recuperacion.setRecNotaRecuperacion(rs.getInt(i++));
      			recuperacion.setRecFecha(rs.getString(i++));
      			l.add(recuperacion);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public List getListaNota() throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	ItemVO item=null;
    	try{
    		cn=cursor.getConnection();		
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaNota"));
      		rs=pst.executeQuery();
      		while(rs.next()){
      			item=new ItemVO(rs.getLong(1),rs.getString(2));
      			l.add(item);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public void guardarRecuperacionArea(FiltroRecuperacionVO filtro,RecuperacionVO recuperacion) throws Exception{
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	int i=1;int j=1;
    	try{
			String []fecha=recuperacion.getRecFechas();
			long []codigo=recuperacion.getRecCodigos();
			int []nota=recuperacion.getRecNotas();
    		cn=cursor.getConnection();
    		if(codigo!=null){
    			for(j=0;j<codigo.length;j++){
    	    		i=1;
    	      		if(nota[j]==0){ 
    	      			pst=cn.prepareStatement(rb.getString("recuperacion.actualizarRecuperacionArea2"));
    	      		}else{ 
    	      			pst=cn.prepareStatement(rb.getString("recuperacion.actualizarRecuperacionArea1"));
    	      			pst.setInt(i++,nota[j]);
    	      			pst.setString(i++,fecha[j]);
    	      		}
    	      		pst.setLong(i++,filtro.getFilJerarquiaArea());
    	      		pst.setLong(i++,codigo[j]);
    	      		pst.setInt(i++,filtro.getFilVigencia());
    	      		pst.executeUpdate();
    				pst.close();
    			}
    		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    }

    public List getListaRecuperacionAsignatura(FiltroRecuperacionVO filtro) throws Exception{
    	List l=new ArrayList();
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	RecuperacionVO recuperacion=null;
    	int i=1;int j=1;
    	try{
    		cn=cursor.getConnection();
    		//calcula la jjerarquia de grupo
    		pst=cn.prepareStatement(rb.getString("recuperacion.jerarquiaGrupo"));
    		pst.setLong(i++,filtro.getFilInstitucion());
    		pst.setInt(i++,filtro.getFilSede());
    		pst.setInt(i++,filtro.getFilJornada());
    		pst.setInt(i++,filtro.getFilMetodologia());
    		pst.setInt(i++,filtro.getFilGrado());
    		pst.setInt(i++,filtro.getFilGrupo());
    		rs=pst.executeQuery();
    		if(rs.next()){
    			filtro.setFilJerarquiaGrupo(rs.getLong(1));
    		}    		
    		rs.close();pst.close();
    		i=1;
    		pst=cn.prepareStatement(rb.getString("recuperacion.jerarquiaAsignatura"));
    		pst.setLong(i++,filtro.getFilInstitucion());
    		pst.setInt(i++,filtro.getFilMetodologia());
    		pst.setInt(i++,filtro.getFilGrado());
    		pst.setLong(i++,filtro.getFilAsignatura());
    		pst.setInt(i++,filtro.getFilVigencia());
    		rs=pst.executeQuery();
    		if(rs.next()){
    			filtro.setFilJerarquiaAsignatura(rs.getLong(1));
    		}    		
    		rs.close();pst.close();
    		i=1;
      		pst=cn.prepareStatement(rb.getString("recuperacion.listaRecuperacionAsig")+" "+rb.getString("recuperacion.listaRecuperacionAsig.orden"+filtro.getFilOrden()));
      		pst.setLong(i++,filtro.getFilJerarquiaAsignatura());
      		pst.setInt(i++,filtro.getFilVigencia());
      		pst.setLong(i++,filtro.getFilJerarquiaGrupo());
      		pst.setInt(i++,5);
      		pst.setInt(i++,4);
      		rs=pst.executeQuery();
      		while(rs.next()){
      			i=1;      			
      			recuperacion=new RecuperacionVO();
      			recuperacion.setRecConsecutivo(j++);
      			recuperacion.setRecCodigo(rs.getLong(i++));
      			recuperacion.setRecApellido(rs.getString(i++));
      			recuperacion.setRecNombre(rs.getString(i++));
      			recuperacion.setRecNotaFinal(rs.getString(i++));
      			recuperacion.setRecNotaRecuperacion(rs.getInt(i++));
      			recuperacion.setRecFecha(rs.getString(i++));
      			l.add(recuperacion);
      		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    	return l;
    }

    public void guardarRecuperacionAsignatura(FiltroRecuperacionVO filtro,RecuperacionVO recuperacion) throws Exception{
    	Connection cn=null;
    	PreparedStatement pst=null;
    	ResultSet rs = null;
    	int i=1;int j=1;
    	try{
			String []fecha=recuperacion.getRecFechas();
			long []codigo=recuperacion.getRecCodigos();
			int []nota=recuperacion.getRecNotas();
    		cn=cursor.getConnection();
    		if(codigo!=null){
    			for(j=0;j<codigo.length;j++){
    	    		i=1;
    	      		if(nota[j]==0){ 
    	      			pst=cn.prepareStatement(rb.getString("recuperacion.actualizarRecuperacionAsig2"));
    	      		}else{ 
    	      			pst=cn.prepareStatement(rb.getString("recuperacion.actualizarRecuperacionAsig1"));
    	      			pst.setInt(i++,nota[j]);
    	      			pst.setString(i++,fecha[j]);
    	      		}
    	      		pst.setLong(i++,filtro.getFilJerarquiaAsignatura());
    	      		pst.setLong(i++,codigo[j]);
    	      		pst.setInt(i++,filtro.getFilVigencia());
    	      		pst.executeUpdate();
    				pst.close();
    			}
    		}
       	}catch(SQLException e){
    	   e.printStackTrace();
       	   throw new Exception("Error SQL: "+e.getMessage());
       }catch(Exception e){
           e.printStackTrace();
           throw new Exception("Error Interno: "+e.getMessage());
       }finally{
   		   try{
   			   OperacionesGenerales.closeResultSet(rs);
   			   OperacionesGenerales.closeStatement(pst);
   			   OperacionesGenerales.closeConnection(cn);
   		   }catch(Exception e){}
   		}      
    }
}
