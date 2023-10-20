package siges.grupo.dao;
import siges.dao.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.grupo.beans.GrupoBean;
import siges.estudiante.beans.Convivencia;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;


public class GrupoDAO extends Dao{
  	public String sentencia;
  	public String mensaje;
  	public String buscar;
  	private ResourceBundle rb;
    
  	/**
  	*	Constructor de la clase
  	**/
  	public GrupoDAO(Cursor cur){
 	    super(cur);
  		sentencia=null;
  		mensaje="";
  		rb=ResourceBundle.getBundle("grupo");
  	}


  	/**
  	 *	Inserta los datos del bean en la tabla de Grupo	
  	 *	@param Cursor cursor
  	 *	@param 
  	 *	@param int tipo
  	 *	@return boolean 
  	 */
  		public boolean insertarGrupo(GrupoBean g){
  			int posicion=1;
  			Connection cn=null;
  			PreparedStatement pst=null,pst2=null,pst3=null,pst4=null;
  			String codigogrupo;
  			ResultSet rs=null;
  			String dpto=null,mun=null,loc=null;
  			
  			try{
  				cn=cursor.getConnection(); cn.setAutoCommit(false);
  				pst=cn.prepareStatement(rb.getString("InsertarGrupo"));
  				pst2=cn.prepareStatement(rb.getString("buscarcodigo_grupo"));
  				pst3=cn.prepareStatement(rb.getString("SeleccionarGrupoJerarquia"));
  				pst4=cn.prepareStatement(rb.getString("InsertarGrupoJerarquia"));
  				posicion=1;
  				pst.clearParameters();
  				pst2.clearParameters();
  				pst3.clearParameters();
  				pst4.clearParameters();
  			  pst2.setLong(posicion++,Long.parseLong(g.getInsitucionnew()));
  			  pst2.setLong(posicion++,Long.parseLong(g.getSedenew()));
  			  pst2.setLong(posicion++,Long.parseLong(g.getJornadanew()));
  			  pst2.setLong(posicion++,Long.parseLong(g.getGradonew()));
  			  pst2.setLong(posicion++,Long.parseLong(g.getMetodologianew()));
  			  
  			  rs=pst2.executeQuery();
  			  	
     	  	if(rs.next()){
      	  	 codigogrupo=rs.getString(1);
    				 rs.close();
   					 pst2.close();     	  		      	  		 
      	  	}else{
   					 setMensaje("Error: No se encontrn el cndigo de la jerarquia");
     				 rs.close();
     				 pst2.close();
   					 return false;
      	  	}  				  				
  				
     	  	if(codigogrupo!=null){
     	  	  posicion=1;
     	  	  pst.setLong(posicion++,Long.parseLong(codigogrupo));
	  				if(g.getCodigonew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setLong(posicion++,Long.parseLong(g.getCodigonew().trim()));
	  				if(g.getNombrenew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setString(posicion++,g.getNombrenew().trim());
	  				if(g.getEspacionew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setLong(posicion++,Long.parseLong(g.getEspacionew().trim()));
	  				if(g.getCoordinadornew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setLong(posicion++,Long.parseLong(g.getCoordinadornew().trim()));
	  				if(g.getCuponew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setLong(posicion++,Long.parseLong(g.getCuponew().trim()));
	  				if(g.getOrdennew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
	  				else pst.setLong(posicion++,Long.parseLong(g.getOrdennew().trim()));
	  				
	  				posicion=1;
	  				rs=null;
	  				pst3.setLong(posicion++,Long.parseLong(g.getInsitucionnew().trim()));
	  				rs=pst3.executeQuery();
	  				if(rs.next()){
	  				   dpto=rs.getString(1);
	  				   mun=rs.getString(2);
	  				   loc=rs.getString(3);
	     				 rs.close();
	   					 pst3.close();
	  				}
	  				pst.executeUpdate();
	  				
	  				posicion=1;
	  				//G_JERDEPTO,G_JERMUNIC,G_JERLOCAL,G_JERINST,G_JERSEDE,G_JERJORN,G_JERGRADO,G_JERGRUPO,G_JERMETOD
	  				pst4.setLong(posicion++,Long.parseLong(dpto));
	  				pst4.setLong(posicion++,Long.parseLong(mun));
	  				pst4.setLong(posicion++,Long.parseLong(loc));
	  				pst4.setLong(posicion++,Long.parseLong(g.getInsitucionnew().trim()));
	  				pst4.setLong(posicion++,Long.parseLong(g.getSedenew().trim()));
	  				pst4.setLong(posicion++,Long.parseLong(g.getJornadanew().trim()));
	  				pst4.setLong(posicion++,Long.parseLong(g.getGradonew().trim()));
	  				pst4.setLong(posicion++,Long.parseLong(g.getCodigonew().trim()));
	  				pst4.setLong(posicion++,Long.parseLong(g.getMetodologianew().trim()));
	  				
	  				pst4.executeUpdate();
     	  	}
  				cn.commit();
  				cn.setAutoCommit(true);
  			}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
  			catch(SQLException sqle){
  				try{cn.rollback();}catch(SQLException s){}
  		 		//setMensaje("Error intentando insertar grupo.("+sqle.getErrorCode()+") Posible problema: ");  		 		
					switch(sqle.getErrorCode()){
						//Violacinn de llave primaria
						case 00001: case 2291: //case 2291:
							setMensaje("Ya existe un registro con el mismo cndigo");
						break;
						//Violacinn de llave secundaria
						case 2292:
							setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
						break;
						//Longitud de campo
						case 1401:
							setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
						break;
	
						default:
							setMensaje(sqle.toString().replace('\'','`').replace('"','n'));
				}  		 		
  		 		return false;
  			}
  			finally{
  				try{
  				OperacionesGenerales.closeStatement(pst);
  				OperacionesGenerales.closeStatement(pst2);
  				OperacionesGenerales.closeStatement(pst3);
  				OperacionesGenerales.closeStatement(pst4);
  				OperacionesGenerales.closeResultSet(rs);
  				OperacionesGenerales.closeConnection(cn);
  				cursor.cerrar();
  				}catch(InternalErrorException inte){}
  			}
  			return true;
  		}


  		
    	/**
    	*	Funcinn:  Asignar Grupo<br>
    	*	@param	String id
    	*	@return	boolean
    	**/
    	public GrupoBean asignarGrupo(String id,String id2,Login login){
    	  GrupoBean b=null;
    		Connection cn=null;
    		PreparedStatement pst=null,pst2=null,pst3=null;
    		ResultSet r=null;
    		int posicion=1; //posicion inicial de llenado del preparedstatement
  			int i=1;
  			
    		try{
    			cn=cursor.getConnection();
    			pst=cn.prepareStatement(rb.getString("GrupoAsignar1"));
    			pst.clearParameters();
    			pst.setLong(posicion++,Long.parseLong(id2.trim()));
    			r = pst.executeQuery();
    			if(r.next()){
    				b=new GrupoBean();
  				  b.setInstitucionnew(r.getString(i++));
  				  b.setSedenew(r.getString(i++));
	  				b.setJornadanew(r.getString(i++));
	  				b.setMetodologianew(r.getString(i++));
	  				b.setGradonew(r.getString(i++));
    			}
	  			r.close();
	  			pst.close();

    	  	pst=cn.prepareStatement(rb.getString("GrupoAsignar2"));  			
    	  	pst.clearParameters();posicion=1;
    	  	pst.setLong(posicion++,Long.parseLong(id2.trim()));
    	  	pst.setLong(posicion++,Long.parseLong(id.trim()));
    	  	r = pst.executeQuery();
    	  	i=1;
    	  	if(r.next()){
    	  	   b.setCodigonew(r.getString(i++));
    	  		 b.setNombrenew(r.getString(i++));
    		  	 b.setCuponew(r.getString(i++));
    		  	 b.setOrdennew(r.getString(i++));
    		  	 b.setJerarquiagrupo(r.getString(i++));
    	  		}
   	  	  r.close();
  	  	  pst.close();
    			    			    			
  	  		pst=cn.prepareStatement(rb.getString("GrupoAsignar3"));  			
  	  		pst.clearParameters();posicion=1;
  	  		pst.setLong(posicion++,Long.parseLong(id2.trim()));
  	  		pst.setLong(posicion++,Long.parseLong(id.trim()));
  	  		r = pst.executeQuery();
  	  		i=1;
  	  		if(r.next())
  	  			  b.setCoordinadornew(r.getString(i++));  	  			
   	  	  r.close();
  	  	  pst.close();
    			
  	  		pst=cn.prepareStatement(rb.getString("GrupoAsignar4"));  			
  	  		pst.clearParameters();posicion=1;
  	  		pst.setLong(posicion++,Long.parseLong(id2.trim()));
  	  		pst.setLong(posicion++,Long.parseLong(id.trim()));
  	  		r = pst.executeQuery();
  	  		i=1;
  	  		if(r.next()){
  	  		   b.setEspacionew(r.getString(i++));
  	  		   b.setTipoespacionew(r.getString(i++));
  	  		}   
   	  	  r.close();
  	  	  pst.close();
    			
/*  	  	  System.out.println("GrupoBean: \n");
  	  	  System.out.println("Inst: "+b.getInsitucionnew());
  	  	  System.out.println("Sede: "+b.getSedenew());
  	  	  System.out.println("Jornada: "+b.getJornadanew());
  	  	  System.out.println("Metodologia: "+b.getMetodologianew());
  	  	  System.out.println("Grado: "+b.getGradonew());
  	  	  System.out.println("TipoEspacio: "+b.getTipoespacionew());
  	  	  System.out.println("Espacio: "+b.getEspacionew());
  	  	  System.out.println("Coordinador: "+b.getCoordinadornew());
  	  	  System.out.println("Codigo: "+b.getCodigonew());
  	  	  System.out.println("Nombre: "+b.getNombrenew());
  	  	  System.out.println("Cupo: "+b.getCuponew());
  	  	  System.out.println("Orden: "+b.getOrdennew());
  	  	  System.out.println("Jerarquia: "+b.getJerarquiagrupo());
*/  	  	  
    		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return null;}
    		catch(SQLException sqle){
    	 		setMensaje("Error intentando asignar información basica. Posible problema: ");
							switch(sqle.getErrorCode()){
							//Violacinn de llave primaria
							case 00001: case 2291: //case 2291:
								setMensaje("Ya existe un registro con el mismo cndigo");
							break;
							//Violacinn de llave secundaria
							case 2292:
								setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
							break;
							//Longitud de campo
							case 1401:
								setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
							break;
		
							default:
								setMensaje(sqle.toString().replace('\'','`').replace('"','n'));
					}  		 		
    			return b;
    		}finally{
    			try{
    			OperacionesGenerales.closeResultSet(r);
    			OperacionesGenerales.closeStatement(pst);
    			OperacionesGenerales.closeConnection(cn);
    			}catch(InternalErrorException inte){}
    		}
    		return b;
    	}
  		
    	public boolean compararBeans(GrupoBean b, GrupoBean b2){
/*    	    
  	  	  System.out.println("GrupoBean: \n");
  	  	  System.out.println("Inst: "+b.getInsitucionnew());
  	  	  System.out.println("Inst2: "+b2.getInsitucionnew());
  	  	  System.out.println("Sede: "+b.getSedenew());
  	  	  System.out.println("Sede2: "+b2.getSedenew());
  	  	  System.out.println("Jornada: "+b.getJornadanew());
  	  	  System.out.println("Jornada2: "+b2.getJornadanew());
  	  	  System.out.println("Metodologia: "+b.getMetodologianew());
  	  	  System.out.println("Metodologia2: "+b2.getMetodologianew());
  	  	  System.out.println("Grado: "+b.getGradonew());
  	  	  System.out.println("Grado2: "+b2.getGradonew());
  	  	  System.out.println("TipoEspacio: "+b.getTipoespacionew());
  	  	  System.out.println("TipoEspacio2: "+b2.getTipoespacionew());
  	  	  System.out.println("Espacio: "+b.getEspacionew());
  	  	  System.out.println("Espacio2: "+b2.getEspacionew());
  	  	  System.out.println("Coordinador: "+b.getCoordinadornew());
  	  	  System.out.println("Coordinador2: "+b2.getCoordinadornew());
  	  	  System.out.println("Codigo: "+b.getCodigonew());
  	  	  System.out.println("Codigo2: "+b2.getCodigonew());
  	  	  System.out.println("Nombre: "+b.getNombrenew());
  	  	  System.out.println("Nombre2: "+b2.getNombrenew());
  	  	  System.out.println("Cupo: "+b.getCuponew());
  	  	  System.out.println("Cupo2: "+b2.getCuponew());
  	  	  System.out.println("Orden: "+b.getOrdennew());
  	  	  System.out.println("Orden2: "+b2.getOrdennew());
  	  	  System.out.println("Jerarquia: "+b.getJerarquiagrupo());
  	  	  System.out.println("Jerarquia2: "+b2.getJerarquiagrupo());*/
    	    
    			if(!b.getInsitucionnew().equals(b2.getInsitucionnew())) return false;    			
    			if(!b.getSedenew().equals(b2.getSedenew())) return false;    			
    			if(!b.getJornadanew().equals(b2.getJornadanew())) return false;    			
    			if(!b.getMetodologianew().equals(b2.getMetodologianew())) return false;    			
    			if(!b.getGradonew().equals(b2.getGradonew())) return false;    			
    			if(!b.getTipoespacionew().equals("-9")){
    			    if(!b.getTipoespacionew().equals(b2.getTipoespacionew())) return false;
    			}else{
    			    if(b2.getTipoespacionew().equals("")){
    			        if(!b.getTipoespacionew().equals("")){System.out.println("FALSSSSSSSSEEE0");}
    			        else{
        			        System.out.println("FALSSSSSSSSEEE1");
        			        return false;
    		    			 }    			        
    			    }else{
    			        System.out.println("FALSSSSSSSSEEE3A");
        			    if(b.getTipoespacionew().equals("-9"))
        			        if(!b.getTipoespacionew().equals(b2.getTipoespacionew()))System.out.println("FALSSSSSSSSEEEX"); return false;    			        
		    			 }    			        

    			  }        			
    			if(!b.getEspacionew().equals("-9")){
    			    if(!b.getEspacionew().equals(b2.getEspacionew())) return false;
    			}else{
    				if(b2.getEspacionew().equals("")){
    			    if(!b.getEspacionew().equals("")){System.out.println("FALSSSSSSSSEEE0");}
			        else{
    			        System.out.println("FALSSSSSSSSEEE2A");
    			        return false;
		    			 }    			        
    				}else{
      			    if(b.getEspacionew().equals("-9"))
      			        if(!b.getEspacionew().equals(b2.getEspacionew()))System.out.println("FALSSSSSSSSEEEX2"); return false;    			        
	    			 }    			        
    			}
    			
     			if(!b.getCoordinadornew().equals("-9")){ 
    			    if(!b.getCoordinadornew().equals(b2.getCoordinadornew())) return false;
    			}else{
    			    if(b2.getCoordinadornew().equals("")){
    			        if(!b.getCoordinadornew().equals("")){System.out.println("FALSSSSSSSSEEE0");}
    			        else{
        			        System.out.println("FALSSSSSSSSEEE3B");
        			        return false;
    		    			 }    			        
    			    }else{
        			    if(b.getCoordinadornew().equals("-9"))
        			        if(!b.getCoordinadornew().equals(b2.getCoordinadornew()))System.out.println("FALSSSSSSSSEEEX3"); return false;    			        
		    			 }    			        

    			  }        			
    			if(!b.getCodigonew().equals(b2.getCodigonew())) return false;
    			if(!b.getNombrenew().equals(b2.getNombrenew())) return false;
    			if(!b.getCuponew().equals(b2.getCuponew())) return false;
    			if(!b.getOrdennew().equals(b2.getOrdennew())) return false;
    			if(!b.getJerarquiagrupo().equals(b2.getJerarquiagrupo())) return false;
    			return true;
    		}
  		
    	
    	/**
    	 *	Inserta los datos del bean en la tabla de informacion basica del egresado	
    	 *	@param Cursor cursor
    	 *	@param EliminarGrupo
    	 *	@param int tipo
    	 *	@return boolean 
    	 */
    		public boolean eliminarGrupo(String inst,String codigo,String codigoj,String cod_grado,String cod_metod){
    			int posicion=1;
    			Connection cn=null;
    			PreparedStatement pst=null,pst1=null;
    			ResultSet rs=null;
    			String codigojerarquia=null;
    			
    			try{
    	  			cn=cursor.getConnection(); cn.setAutoCommit(false);
    	  			pst=cn.prepareStatement(rb.getString("EliminarGrupo"));
    	  			pst.clearParameters();
    	  			if(codigoj.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(codigoj.trim()));
    	  			if(codigo.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(codigo.trim()));
    	  			pst.executeUpdate();
    	  			pst.close();
    	  			
    	  			pst=cn.prepareStatement(rb.getString("CodigoJerarquia"));
    	  			posicion=1;
    	  			pst.clearParameters();
    	  			if(inst.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(inst.trim()));
    	  			if(cod_metod.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(cod_metod.trim()));
    	  			if(cod_grado.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(cod_grado.trim()));
    	  			if(codigo.equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
    	  			else pst.setLong(posicion++,Long.parseLong(codigo.trim()));
    	  			rs=pst.executeQuery();
    	  			if(rs.next()){
    	  			    codigojerarquia=rs.getString(1);
    	  			   
    	  			    pst1=cn.prepareStatement(rb.getString("EliminarGrupoJerarquia"));
	      	  			posicion=1;
	      	  			pst1.clearParameters();	      	  			
	      	  			pst1.setLong(posicion++,Long.parseLong(codigojerarquia.trim()));
	      	  			pst1.executeUpdate();
	      	  			pst1.close();
    	  			}else{
    	  			    return false;
    	  			  }
    	  			rs.close();
    	  			pst.close();
    	  			cn.commit();
    	  			cn.setAutoCommit(true);    	  			
    			}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
    			catch(SQLException sqle){
    				try{cn.rollback();}catch(SQLException s){}
    		 		//setMensaje("Error intentando insertar grupo.("+sqle.getErrorCode()+") Posible problema: ");
    		 		
  					switch(sqle.getErrorCode()){
  						//Violacinn de llave primaria
  						case 00001: case 2291: //case 2291:
  							setMensaje("Ya existe un registro con el mismo cndigo");
  						break;
  						//Violacinn de llave secundaria
  						case 2292:
  							setMensaje("No se puede realizar la operacinn ya que tiene registros asociados");
  						break;
  						//Longitud de campo
  						case 1401:
  							setMensaje("Alguno(s) campo(s) exceden la longitud permitida");
  						break;
  	
  						default:
  							setMensaje(sqle.toString().replace('\'','`').replace('"','n'));
  				}  		 		
    		 		return false;  				
    			}
    			finally{
    				try{
 				    OperacionesGenerales.closeResultSet(rs);    
    				OperacionesGenerales.closeStatement(pst);
    				OperacionesGenerales.closeStatement(pst1);    				
    				OperacionesGenerales.closeConnection(cn);
    				cursor.cerrar();
    				}catch(InternalErrorException inte){}
    			}
    			return true;
    		}
  		
      	/**
      	*	Funcinn:  Actualizar Grupo<br>
      	*	@param	GrupoBean b
      	*	@return	boolean
      	**/
      	public boolean actualizar(GrupoBean b){
      		int posicion=1; 
      		Connection cn=null;
      		PreparedStatement pst=null;
      		try{
      		  System.out.println("Metodoactualizar"); 
      			cn=cursor.getConnection(); cn.setAutoCommit(false);
      			pst=cn.prepareStatement(rb.getString("GrupoActualizar"));
      			pst.clearParameters();
      			
      			if(b.getEspacionew().equals("-9")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      			else pst.setInt(posicion++,Integer.parseInt(b.getEspacionew().trim()));
      			
      			if(b.getCoordinadornew().equals("-9")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      			else pst.setLong(posicion++,Long.parseLong(b.getCoordinadornew().trim()));
      			
      			if(b.getNombrenew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      			else pst.setString(posicion++,b.getNombrenew().trim());
      			
      			if(b.getCuponew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      			else pst.setInt(posicion++,Integer.parseInt(b.getCuponew().trim()));

      			if(b.getOrdennew().equals("")) pst.setNull(posicion++,java.sql.Types.VARCHAR);
      			else pst.setInt(posicion++,Integer.parseInt(b.getOrdennew().trim()));

      			pst.setLong(posicion++,Long.parseLong(b.getJerarquiagrupo().trim()));
      			pst.setLong(posicion++,Long.parseLong(b.getCodigonew().trim()));
      			
      			System.out.println("ACTUALIZAR GRUPO: \n ");
      			System.out.println("espacio: "+b.getEspacionew());
      			System.out.println("coordinador: "+b.getCoordinadornew());
      			System.out.println("nombre: "+b.getNombrenew());
      			System.out.println("cupo: "+b.getCuponew());
      			System.out.println("orden: "+b.getOrdennew());
      			System.out.println("grucodjerar: "+b.getJerarquiagrupo());
      			System.out.println("grucodigo: "+b.getCodigonew()); 
      			pst.executeUpdate();			
      			cn.commit();
      			cn.setAutoCommit(true);
      		}catch(InternalErrorException in){ setMensaje("NO se puede estabecer conexinn con la base de datos: ");	return false;}
      		catch(SQLException sqle){
      			try{cn.rollback();}catch(SQLException s){}
      	 		setMensaje("Error intentando actualizar el Grupo.("+sqle.getErrorCode()+") Posible problema: ");
      			switch(sqle.getErrorCode()){
      				default:
      					setMensaje(sqle.getMessage().replace('\'','`').replace('"','n'));
      			}
      			return false;
      		}
      		catch(Exception e){
      		    e.printStackTrace();
        			try{cn.rollback();}catch(SQLException s){}
        	 		setMensaje("Error intentando actualizar el Grupo.("+e.getMessage()+")");
        	 		return false;
      		}
      		finally{
      			try{
      			OperacionesGenerales.closeStatement(pst);
      			OperacionesGenerales.closeConnection(cn);
      			cursor.cerrar();
      			}catch(InternalErrorException inte){}
      		}
      		return true;
      	}
  		
  		
  		
  		/**
  	*	Funcinn:  Cerrar conecciones<br>
  	**/
  	public void cerrar(){
  		try{		
  		if(cursor!=null)cursor.cerrar();
  		}catch(Exception e){}	
  	}

  	/**
  	*	Retorna una variable que contiene los mensajes que se van a enviar a la vista
  	*	@return String	
  	**/
  		public String getMensaje(){
  			return mensaje;
  		}		

  		/**
  		*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
  		*	@param	String s
  		**/
  			public void setMensaje(String s){
  				mensaje+=s;	
  			}
}
