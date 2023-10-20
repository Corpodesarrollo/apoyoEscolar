package articulacion.repHorariosArt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import siges.dao.Ruta;
import siges.exceptions.InternalErrorException;
import articulacion.repHorariosArt.vo.*;
import siges.io.Zip;
import articulacion.repHorariosArt.vo.LEstudianteVO;
import siges.util.Recursos;
import java.util.Calendar;



/**
 * @author Latined
 *
 * Realiza todas las operaciones que interactuan con la base de datos
 * 
 */
public class repHorariosArtDAO extends Dao {
    public String sentencia;
    public String buscar;
    private ResourceBundle rb3;
  	private byte[] bytes;
  	private Zip zip;
	private int codigoArt;
	private String nombreArt;
  	private static final int codigo_tipo=3;


    public repHorariosArtDAO(Cursor cur){
        super(cur);
        bytes=null;
        rb3=ResourceBundle.getBundle("articulacion.repHorariosArt.bundle.repHorariosArt");
    }

		
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public void ponerReporte(String modulo,String us,String rec,String tipo,String nombre,String estado,String prepared){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;
    		
  		try{ 
  		  con=cursor.getConnection(); 
  		  
  		  pst=con.prepareStatement(rb3.getString(prepared));
  		  posicion=1;
  		  pst.clearParameters();
  		  pst.setString(posicion++,(us));
  		  pst.setString(posicion++,(rec));
  		  pst.setString(posicion++,(tipo));
  		  pst.setString(posicion++,(nombre));
  		  pst.setString(posicion++,(modulo));
  		  pst.setString(posicion++,(estado));		
  		  pst.executeUpdate();
  		  pst.close();
  		  con.commit();
   		  con.setAutoCommit(true);
  		}	
  		catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");
  		}catch(SQLException sqle){
  		    try{con.rollback();}catch(SQLException s){}
  		    setMensaje("Error intentando ponerReporte: ");
  		    switch (sqle.getErrorCode()) {
  		    default:
  		        setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
  		    }
  		}finally{
  		    try{
  		        OperacionesGenerales.closeStatement(pst);
  		        OperacionesGenerales.closeConnection(con);
  		        cursor.cerrar();
  		    } catch (InternalErrorException inte){}
  		}
  	}
		
			
  	/**
  	*	Funcinn: Actualiza el puesto del reporte en la tabla de reportes <BR>
  	*	@param	String puesto
  	*	@param	String nombreboletin
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public boolean updatePuestoReporte(String puesto,String nombrereportezip,String user,String prepared){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;
  			
  		try{ 
  		  con=cursor.getConnection(); 
  			pst=con.prepareStatement(rb3.getString(prepared));
  			posicion=1;
  			pst.clearParameters();   			
  			pst.setString(posicion++,(puesto));
  			pst.setString(posicion++,(nombrereportezip));
  			pst.setString(posicion++,(user));
  			pst.executeUpdate();
  			pst.close();
  			con.commit();
   		  con.setAutoCommit(true);
  	  }	
  		catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
  		}catch(SQLException sqle){
  		    try{con.rollback();}catch(SQLException s){}
  		    setMensaje("Error intentando updatePuestoReporte: ");
  		    switch (sqle.getErrorCode()) {
  		    default:
  		        setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
  		    }
  		    return false;
  		}finally{
  		    try{
  		        OperacionesGenerales.closeStatement(pst);
  		        OperacionesGenerales.closeConnection(con);
  		        cursor.cerrar();
  		    } catch (InternalErrorException inte){}
  		}
  		return true;
  	}
  	

  	
  	
  	public Long getDaneInstitucion(Long codigoInstitucion){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		Long daneInstitucion=new Long(0);
  			
  		try{ 
  			con=cursor.getConnection(); 
  			pst=con.prepareStatement(rb3.getString("getDaneInstitucion"));
  			posicion=1;
  			pst.clearParameters();   			
  			pst.setLong(posicion++,codigoInstitucion.longValue());
  			rs=pst.executeQuery();
  			if(rs.next())
  				daneInstitucion=new Long(rs.getLong(1));
  	  }	
  		catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");
  		}catch(SQLException sqle){
  		    try{con.rollback();}catch(SQLException s){}
  		    setMensaje("Error intentando getDaneInstitucion");
  		    switch (sqle.getErrorCode()) {
  		    default:
  		        setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
  		    }
  		}finally{
  		    try{
  		        OperacionesGenerales.closeStatement(pst);
  		        OperacionesGenerales.closeConnection(con);
  		        cursor.cerrar();
  		    } catch (InternalErrorException inte){}
  		}
  		return daneInstitucion;
  	}
  	
  	
  	
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/
  						
  	public boolean ponerReporteMensaje(String estado,String modulo,String us,String rec,String tipo,String nombre,String prepared,String mensaje){
  	    
  			Connection con=null;
  			PreparedStatement pst=null;
  			int posicion=1;
  	  		
  			try{ 
  			  con=cursor.getConnection(); 
  			  
  				pst=con.prepareStatement(rb3.getString(prepared));
  				posicion=1;
  				pst.clearParameters();
  				pst.setString(posicion++,(estado));
  				pst.setString(posicion++,(mensaje));
  				pst.setString(posicion++,(us));
  				pst.setString(posicion++,(rec));
  				pst.setString(posicion++,(tipo));
  				pst.setString(posicion++,(nombre));
  				pst.setString(posicion++,(modulo));	
  				pst.executeUpdate();
    			pst.close();
    			con.commit();
     		  con.setAutoCommit(true);
    	  }	
    		catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;
    		}catch(SQLException sqle){
    		    try{con.rollback();}catch(SQLException s){}
    		    setMensaje("Error intentando ponerReporteMensaje: ");
    		    switch (sqle.getErrorCode()) {
    		    default:
    		        setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
    		    }
    		    return false;
    		}finally{
    		    try{
    		        OperacionesGenerales.closeStatement(pst);
    		        OperacionesGenerales.closeConnection(con);
    		        cursor.cerrar();
    		    } catch (InternalErrorException inte){}
    		}
    		return true;
    	}
  	
  	
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public void updateReporte(String modulo,String nombrereporte,String user,String estado,String prepared){	
  		Connection con=null;
  		PreparedStatement pst=null;
  		int posicion=1;
  	 		
  		try{ 
  		  con=cursor.getConnection(); 
  		  
  			pst=con.prepareStatement(rb3.getString(prepared));
  			posicion=1;
  			pst.clearParameters();
  			pst.setString(posicion++,(modulo));
  			pst.setString(posicion++,(nombrereporte));
  			pst.setString(posicion++,(user));
  			pst.setLong(posicion++,Long.parseLong(estado));		
  			pst.executeUpdate();
  			pst.close();
  			con.commit();
  		  }	
  			catch(InternalErrorException e ){
  			    e.printStackTrace();
  				}
  			catch(Exception e ){
  			    e.printStackTrace();
  				}
  			finally{
  				try{
  				    OperacionesGenerales.closeStatement(pst);
  				    OperacionesGenerales.closeConnection(con);
  				    }catch(Exception e){}
  		  }
  	}

  	
  	
  	
  	

  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes <BR>
  	*	@param	String us
  	*	@param	String rec
  	*	@param	String tipo
  	*	@param	String nombre
  	*	@param	String prepared
  	**/

  	public void ponerReporteMensajeListo(String modulo,String us,String rec,String tipo,String nombre,String prepared){
  			Connection con=null;
  			PreparedStatement pst=null;
  			int posicion=1;

  			try{ 
  				  con=cursor.getConnection(); 
  				  
  					pst=con.prepareStatement(rb3.getString(prepared));
  					posicion=1;
  					pst.clearParameters();
  					pst.setString(posicion++,(us));
  					pst.setString(posicion++,(rec));
  					pst.setString(posicion++,(tipo));
  					pst.setString(posicion++,(nombre));
  					pst.setString(posicion++,(modulo));	
  					pst.executeUpdate();
  					pst.close();							
  					con.commit();
  			  }	
  				catch(InternalErrorException e ){
  				    e.printStackTrace();
  					}
  				catch(Exception e ){
  				    e.printStackTrace();
  					}
  				finally{
  					try{
  					OperacionesGenerales.closeStatement(pst);
  					OperacionesGenerales.closeConnection(con);
  					}
  					catch(Exception e){}
  			  }
  	}
  	
  
  	/**
  	*	Funcinn: Ejecuta los prepared statements correspondientes para actualzar el estado y ponerlo en la cola<BR>
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	**/
  	 
  	public String getVigenciaActual(){
  	  Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String vig=null;
  		
  		try{
  		  con=cursor.getConnection();
  		  pst=con.prepareStatement(rb3.getString("vigencia"));
  			posicion=1;
  		 	rs=pst.executeQuery();		 	
  		 	if(rs.next())vig=rs.getString(1); 
  		 	}
  		 catch(Exception e){		    
  			  e.printStackTrace();
  	  	}finally{
  			try{
  			    OperacionesGenerales.closeResultSet(rs);
  			    OperacionesGenerales.closeStatement(pst);    		    
  			    OperacionesGenerales.closeConnection(con);
  			    }catch(Exception e){}    
  			}
  			return vig;
  	}
  	
  	
  	

  	/**
  	*	Funcinn: Ejecuta los prepared statements correspondientes para actualzar el estado y ponerlo en la cola<BR>
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	**/
  	public byte[] getArrayBytes(Long idInstitucion,String idUsuario,String nombreReporteZip,File reportFile, Map parameterscopy,String modulo){
  	  byte[] bytes=null;
  	  Connection con=null;
  		try{
  		    con=cursor.getConnection();
  		    if((reportFile.getPath()!=null) && (parameterscopy!=null) &&(!parameterscopy.values().equals("0")) && (con!=null)){
  		        //System.out.println("***Se mandn ejecutar el jasper****");              	  	   
  		        bytes=JasperRunManager.runReportToPdf(reportFile.getPath(),parameterscopy,con);
  		    }
  		}
  		 catch(JRException e){
  		     e.printStackTrace();
  		     ponerReporteMensaje("2",modulo,idUsuario,rb3.getString("reportes.PathReportes")+nombreReporteZip+"","zip",""+nombreReporteZip,"ReporteActualizarBoletinGenerando","Ocurrio excepcion en el Jasper:_"+e);    		
  		     updateReporte(modulo,nombreReporteZip,idUsuario,"2","update_reporte");
  		     siges.util.Logger.print(idUsuario,"Excepcinn al generar el reporte:_Institucion:_"+idInstitucion+"_Usuario:_"+idUsuario+"_NombreReporte:_"+nombreReporteZip+"",3,1,this.toString());
  		 }
  		 catch(java.lang.OutOfMemoryError e){
  		     e.printStackTrace();
  		     //System.out.println(" WARNING: nMemoria Insuficiente para generar el reporte solicitado! : "+e.toString());
  		     ponerReporteMensaje("2",modulo,idUsuario,rb3.getString("reportes.PathReportes")+nombreReporteZip+"","zip",""+nombreReporteZip,"ReporteActualizarBoletinGenerando","Memoria insuficiente para generar el reporte:_"+e);
  		     updateReporte(modulo,nombreReporteZip,idUsuario,"2","update_reporte");
  		     siges.util.Logger.print(String.valueOf(idUsuario),"Excepcinn al generar el reporte:_Institucion:_"+idInstitucion+"_Usuario:_"+idUsuario+"_NombreReporte:_"+nombreReporteZip+"",3,1,this.toString());
  		 }
  		catch(Exception e){
  		    e.printStackTrace();
  		    ponerReporteMensaje("2",modulo,idUsuario,rb3.getString("reportes.PathReportes")+nombreReporteZip+"","zip",""+nombreReporteZip,"ReporteActualizarBoletinGenerando","Ocurrio Exception:_"+e);
  		    updateReporte(modulo,nombreReporteZip,idUsuario,"2","update_reporte");
  		    siges.util.Logger.print(idUsuario,"Excepcinn al generar el reporte:_Institucion:_"+idInstitucion+"_Usuario:_"+idUsuario+"_NombreReporte:_"+nombreReporteZip+"",3,1,this.toString());
  		}finally{
  		    try{
  		        OperacionesGenerales.closeConnection(con);
  		    }catch(Exception e){}    
  		}
  		return bytes;
  	}
  	
  	
    
  	
  	/**
  	*	Funcinn: Pone el reporte generado por el reporteador en la tabla de reportes, y luego poder ser visualizado en <BR> la lista de reportes generados <BR>
  	*	@param	byte bit
  	**/
  	
  	public void ponerArchivo(byte[] bit,String archivostatic,String context){
  	    	    
  		Connection con=null;
  		try{
  			con=cursor.getConnection();								
  			String archivosalida=Ruta.get(context,rb3.getString("reportes.PathReporte"));				
  			File f=new File(archivosalida);
  			if(!f.exists()) FileUtils.forceMkdir(f);			
  			FileOutputStream fileOut= new FileOutputStream(f+File.separator+archivostatic);
  			CopyUtils.copy(bit,fileOut);
  			fileOut.flush();
  			fileOut.close();
  		}catch(IOException e){
  		  e.printStackTrace();  
  		}
  		catch(Exception e ){
  			e.printStackTrace();
  		}
  		finally{
  				try{
  				  OperacionesGenerales.closeConnection(con);
  				}catch(InternalErrorException inte){}
  			}		
  	}
  	
  	
  	
  	public boolean isEmptyGrupo(FiltroBeanReporte filtroBeanReporte) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();					    
			rs2=null;
			pst=con.prepareStatement(rb3.getString("isEmptyGrupo"));
			posicion=1;		
			pst.setLong(posicion++,filtroBeanReporte.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilGrupo().longValue());
			//pst.setLong(posicion++,filtroBeanReporte.getInstitucion().longValue());
			//pst.setLong(posicion++,filtroBeanReporte.getSede().longValue());
			//pst.setLong(posicion++,filtroBeanReporte.getJornada().longValue());				
			rs2=pst.executeQuery();
			if(rs2.next()){
				pst=null;
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyGrupo2"));
				posicion=1;				
				pst.setLong(posicion++,filtroBeanReporte.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporte.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporte.getFilComponenteArt().longValue());
				pst.setLong(posicion++,filtroBeanReporte.getFilGrupo().longValue());	
				//pst.setLong(posicion++,filtroBeanReporte.getInstitucion().longValue());
				//pst.setLong(posicion++,filtroBeanReporte.getSede().longValue());
				//pst.setLong(posicion++,filtroBeanReporte.getJornada().longValue());																				
				rs2=pst.executeQuery();
				if(rs2.next())return false;
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	

	public boolean isEmptyGruposArt(FiltroBeanReporteGrupos filtroBeanReporteGrupos) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;		
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();					    
			rs2=null;
			pst=con.prepareStatement(rb3.getString("isEmptyGruposArt"));
			posicion=1;
			pst.setLong(posicion++,filtroBeanReporteGrupos.getInstitucion().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getJornada().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilSemestre().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilEspecialidad().longValue());
			pst.setLong(posicion++,filtroBeanReporteGrupos.getFilEspecialidad().longValue());							
			rs2=pst.executeQuery();
				if(rs2.next())return false;
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs2);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

	
  	public boolean isEmptyGrupos(FiltroBeanReporte filtroBeanReporte) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("getAjaxGrupo"));
			posicion=1;			
			
			pst.setLong(posicion++,filtroBeanReporte.getInstitucion().longValue());			
			pst.setLong(posicion++,filtroBeanReporte.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getJornada().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanReporte.getFilEspecialidad().longValue());
			rs=pst.executeQuery();
			while(rs.next()){
			    long cod_gru=rs.getLong(1);
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyGrupo"));
				posicion=1;
				
				pst.setLong(posicion++,filtroBeanReporte.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporte.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporte.getFilComponenteArt().longValue());
				pst.setString(posicion++,String.valueOf(cod_gru));
				//pst.setLong(posicion++,filtroBeanReporte.getInstitucion().longValue());
				//pst.setLong(posicion++,filtroBeanReporte.getSede().longValue());
				//pst.setLong(posicion++,filtroBeanReporte.getJornada().longValue());				
				rs2=pst.executeQuery();
				if(rs2.next()){
					pst=null;
					rs2=null;
					pst=con.prepareStatement(rb3.getString("isEmptyGrupo2"));
					posicion=1;					
					pst.setLong(posicion++,filtroBeanReporte.getFilAnoVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporte.getFilPerVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporte.getFilComponenteArt().longValue());
					pst.setString(posicion++,String.valueOf(cod_gru));
					//pst.setLong(posicion++,filtroBeanReporte.getInstitucion().longValue());
					//pst.setLong(posicion++,filtroBeanReporte.getSede().longValue());
					//pst.setLong(posicion++,filtroBeanReporte.getJornada().longValue());																				
					rs2=pst.executeQuery();
					if(rs2.next())return false;
				}
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}

  	
  	
	public boolean isEmptyDocente(FiltroBeanReporteDocente filtroBeanReporteDocente) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();					    
			rs2=null;
			pst=con.prepareStatement(rb3.getString("isEmptyDocente"));
			posicion=1;	
			pst.setString(posicion++,filtroBeanReporteDocente.getDocente().toString());
			pst.setLong(posicion++,filtroBeanReporteDocente.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getInstitucion().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getJornada().longValue());				
			rs2=pst.executeQuery();
			if(rs2.next()){
				pst=null;
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyDocente2"));
				posicion=1;
				pst.setLong(posicion++,filtroBeanReporteDocente.getDocente().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilComponenteArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getInstitucion().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getSede().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getJornada().longValue());																				
				rs2=pst.executeQuery();
				if(rs2.next())return false;
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	
	public boolean isEmptyDocentes(FiltroBeanReporteDocente filtroBeanReporteDocente) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("getAjaxDocente"));
			posicion=1;			
			pst.setLong(posicion++,filtroBeanReporteDocente.getInstitucion().longValue());			
			pst.setLong(posicion++,filtroBeanReporteDocente.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporteDocente.getJornada().longValue());			
			rs=pst.executeQuery();
			while(rs.next()){
			    long cod_doc=rs.getLong(1);
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyDocente"));
				posicion=1;	
				pst.setString(posicion++,String.valueOf(cod_doc));
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getFilComponenteArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getInstitucion().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getSede().longValue());
				pst.setLong(posicion++,filtroBeanReporteDocente.getJornada().longValue());				
				rs2=pst.executeQuery();
				if(rs2.next()){
					pst=null;
					rs2=null;
					pst=con.prepareStatement(rb3.getString("isEmptyDocente2"));
					posicion=1;
					pst.setLong(posicion++,cod_doc);
					pst.setLong(posicion++,filtroBeanReporteDocente.getFilAnoVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteDocente.getFilPerVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteDocente.getFilComponenteArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteDocente.getInstitucion().longValue());
					pst.setLong(posicion++,filtroBeanReporteDocente.getSede().longValue());
					pst.setLong(posicion++,filtroBeanReporteDocente.getJornada().longValue());																				
					rs2=pst.executeQuery();
					if(rs2.next())return false;
				}
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
  	

	
	public boolean isEmptyEspacio(FiltroBeanReporteEspacio filtroBeanReporteEspacio) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();					    
			rs2=null;
			pst=con.prepareStatement(rb3.getString("isEmptyEspacio"));
			posicion=1;			
			pst.setLong(posicion++,filtroBeanReporteEspacio.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getInstitucion().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getJornada().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getEspacio().longValue());
			rs2=pst.executeQuery();
			if(rs2.next()){
				pst=null;
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyEspacio2"));
				posicion=1;
				pst.setLong(posicion++,filtroBeanReporteEspacio.getEspacio().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilComponenteArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getInstitucion().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getSede().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getJornada().longValue());																				
				rs2=pst.executeQuery();
				if(rs2.next())return false;
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	
	//FUNCION PARA DETERMINAR Q HALLAN DATOS PAR GENERACION DE REPORTES DE VARIOS ESPACIOS FISICOS
	public boolean isEmptyEspacios(FiltroBeanReporteEspacio filtroBeanReporteEspacio) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("getAjaxEspacio"));
			posicion=1;			
			pst.setLong(posicion++,filtroBeanReporteEspacio.getInstitucion().longValue());			
			pst.setLong(posicion++,filtroBeanReporteEspacio.getSede().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getJornada().longValue());			
			pst.setLong(posicion++,filtroBeanReporteEspacio.getTipoEspacio().longValue());
			pst.setLong(posicion++,filtroBeanReporteEspacio.getTipoEspacio().longValue());			
			rs=pst.executeQuery();
			while(rs.next()){
			    long cod_esp=rs.getLong(1);
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyEspacio"));
				posicion=1;	
				pst.setString(posicion++,String.valueOf(cod_esp));
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getFilComponenteArt().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getInstitucion().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getSede().longValue());
				pst.setLong(posicion++,filtroBeanReporteEspacio.getJornada().longValue());				
				rs2=pst.executeQuery();
				if(rs2.next()){
					pst=null;
					rs2=null;
					pst=con.prepareStatement(rb3.getString("isEmptyEspacio2"));
					posicion=1;
					pst.setLong(posicion++,cod_esp);
					pst.setLong(posicion++,filtroBeanReporteEspacio.getFilAnoVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteEspacio.getFilPerVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteEspacio.getFilComponenteArt().longValue());
					pst.setLong(posicion++,filtroBeanReporteEspacio.getInstitucion().longValue());
					pst.setLong(posicion++,filtroBeanReporteEspacio.getSede().longValue());
					pst.setLong(posicion++,filtroBeanReporteEspacio.getJornada().longValue());																				
					rs2=pst.executeQuery();
					if(rs2.next())return false;
				}
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	
	public boolean isEmptyEstudiante(FiltroBeanRepEstudiante filtroBeanRepEstudiante) throws Exception{
		//FUNCION QUE VALIDA EXISTENCIA DE DATOS EN EL REPORTE, PARAMETROS DE HORARIO Y HORARIO DE UN ESTUDIANTE -WGOMEZJ
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("isEmptyEst"));
			posicion=1;
			pst.setLong(posicion++,filtroBeanRepEstudiante.getFilAnoVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getFilPerVigenciaArt().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getFilComponenteArt().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getFilEstudiante().longValue());						
			rs=pst.executeQuery();
			if(rs.next()){
				pst=null;
				rs=null;
				pst=con.prepareStatement(rb3.getString("isEmptyEst2"));
				posicion=1;
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilEstudiante().longValue());
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilComponenteArt().longValue());										
				rs=pst.executeQuery();
				if(rs.next())return false;
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	
	//FUNCION PARA VALIDAR SI EL REPORTE ES VACIO CUANDO SELECCIONA TODOS LOS ESTUDAINTES
	public boolean isEmptyEstudiantes(FiltroBeanRepEstudiante filtroBeanRepEstudiante) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("getAjaxEstudiante"));
			posicion=1;			
			pst.setLong(posicion++,filtroBeanRepEstudiante.getInstitucion().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getFilEspecialidad().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getSede().longValue());
			pst.setLong(posicion++,filtroBeanRepEstudiante.getJornada().longValue());			
			rs=pst.executeQuery();
			while(rs.next()){
			    long cod_est=rs.getLong(1);
				pst=null;
				rs2=null;
				pst=con.prepareStatement(rb3.getString("isEmptyEst"));
				posicion=1;				
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilAnoVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilPerVigenciaArt().longValue());
				pst.setLong(posicion++,filtroBeanRepEstudiante.getFilComponenteArt().longValue());
				pst.setLong(posicion++,cod_est);
				rs2=pst.executeQuery();
				if(rs2.next()){
					pst=null;
					rs2=null;
					pst=con.prepareStatement(rb3.getString("isEmptyEst2"));
					posicion=1;
					pst.setLong(posicion++,cod_est);
					pst.setLong(posicion++,filtroBeanRepEstudiante.getFilAnoVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanRepEstudiante.getFilPerVigenciaArt().longValue());
					pst.setLong(posicion++,filtroBeanRepEstudiante.getFilComponenteArt().longValue());										
					rs2=pst.executeQuery();
					if(rs2.next())return false;
				}
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return true;
	}
	
	
	public boolean isWorking(String idUsuario,String modulo) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int posicion=1;
		try{
  			con=cursor.getConnection();								
			pst=con.prepareStatement(rb3.getString("isWorking"));
			posicion=1;
			pst.setString(posicion++,idUsuario);
			pst.setString(posicion++,modulo);
			rs=pst.executeQuery();
			if(rs.next())return true;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();			
		}catch(Exception sqle){
			sqle.printStackTrace();
		}
		finally{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(con);
		}
		return false;
	}
	
	
	
  	/**
  	*	Funcinn: Ejecuta los prepared statements correspondientes para actualzar el estado y ponerlo en la cola<BR>
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	*	@param	String 
  	**/
  	 
  	public boolean ejecutarJasper(String usuarioId){
  	  Connection con=null;
  		PreparedStatement pst=null;
  		ResultSet rs=null;
  		int posicion=1;
  		String act=null;
  		
  		try{
  		  con=cursor.getConnection();
  			pst=con.prepareStatement(rb3.getString("queryEjecutarjasper"));
  			posicion=1;
  			pst.clearParameters();
  		  pst.setLong(posicion++,Long.parseLong(usuarioId));
  		  rs=pst.executeQuery();
  		  	
  		  if(!rs.next())
  		 	   return false;
  		  rs.close();pst.close();
  		  
  		 	}
  		 catch(Exception e){		    
  			  e.printStackTrace();
  	  	}finally{
  			try{
  			    OperacionesGenerales.closeResultSet(rs);
  			    OperacionesGenerales.closeStatement(pst);    		    
  			    OperacionesGenerales.closeConnection(con);
  			    }catch(Exception e){}    
  			}
  			return true;
  	}

  	
	public void updateReporteFecha(String preparedstatement,String modulo,String nombreboletin,String user,String estado){	
		Connection con=null;
		PreparedStatement pst=null;
		int posicion=1;
	 		
		try{ 
		  con=cursor.getConnection(); 
		  
			pst=con.prepareStatement(rb3.getString(preparedstatement));
			posicion=1;
			pst.clearParameters();
			pst.setString(posicion++,(modulo));
			pst.setString(posicion++,(nombreboletin));
			pst.setString(posicion++,(user));
			pst.setLong(posicion++,Long.parseLong(estado));		
			pst.executeUpdate();
			pst.close();
			con.commit();
	 		//System.out.println("nnSe actualizn la fecha en la tabla Reporte!!!!");
	 		
		  }	
			catch(InternalErrorException e){
				e.printStackTrace();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
			finally{
				try{
					OperacionesGenerales.closeStatement(pst);
				    OperacionesGenerales.closeConnection(con);
				    }catch(Exception e){}
		  }
	}
  	
			
  	public void updateReporteEstado(String modulo,String us,String rec,String tipo,String nombre,String prepared){
			Connection con=null;
			PreparedStatement pst=null;
			int posicion=1;

			try{ 
				  con=cursor.getConnection(); 
				  pst=con.prepareStatement(rb3.getString(prepared));
				  posicion=1;
				  pst.clearParameters();
				  pst.setString(posicion++,(us));
				  pst.setString(posicion++,(rec));
				  pst.setString(posicion++,(tipo));
				  pst.setString(posicion++,(nombre));
				  pst.setString(posicion++,(modulo));	
				  pst.executeUpdate();
				  con.commit();
				  pst.close();		
			  }	
			catch(InternalErrorException e ){
				e.printStackTrace();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
			finally{
				try{
					OperacionesGenerales.closeStatement(pst);
					OperacionesGenerales.closeConnection(con);
				}
				catch(Exception e){}
			}
	}
  	
  	/** **/
  	public int getVigenciaSis() throws InternalErrorException {  		
  		Calendar calendar = Calendar.getInstance();
  		int ano;
  		ano=calendar.get(Calendar.YEAR);
		return ano;
	}
  	public void ComponenteArt(int codigo, String nombre) {
  		
		this.codigoArt = codigo;
		this.nombreArt = nombre;
	}
  	
  	/*GETSEDE LIST WGOMEZJ*/
	public List getSede(long inst,int sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LSedeVO lp=null;
		int i=0;
		try{			
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getSede"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LSedeVO();
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
	//FUNCION PARA CARGAR JORNADAS
	public List getJornada(long inst,int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LJornadaVO lp=null;
		int i=0;
		try{
			//System.out.println("Calculo de jor"+inst+"::"+sede+"::"+jor);
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getJornada"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LJornadaVO();
				lp.setSede(rs.getInt(i++));
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
	//FUNCION PARA CARGAR LAS ESPECIALIDADES
	public List getAjaxEspecialidad(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LEspecialidadVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getEspecialidad"));
			i=1;
			st.setLong(i++,inst);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LEspecialidadVO();
				lp.setCodigo(rs.getLong(i++));
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
	
	
	//FUNCION PARA CARGAR TIPOS ESPACIO
	public List getTiposEspacio(){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LTipoEspVO lp=null;
		int i=0;
		try{
			//System.out.println("Calculo de jor"+inst+"::"+sede+"::"+jor);
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getTipoEspacio"));
			i=1;			
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LTipoEspVO();				
				lp.setCodigo(rs.getString(i++));
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


	public List getAjaxEstudiantes(long inst, int esp,int sed, int jorn) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LEstudianteVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getAjaxEstudiante"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,esp);	
			st.setInt(i++,sed);
			st.setInt(i++,jorn);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LEstudianteVO();
				lp.setCodigo(rs.getLong(i++));
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


	public Object getAjaxDocente(long inst, int sed, int jor) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LDocenteVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getAjaxDocente"));
			i=1;
			st.setLong(i++,inst);				
			st.setLong(i++,sed);
			st.setLong(i++,jor);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LDocenteVO();
				lp.setCodigo(rs.getLong(i++));
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


	public Object getAjaxGrupo(long inst, long sed, long jor, long vig, long per, long com, long esp) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getAjaxGrupo"));
			i=1;
			st.setLong(i++,inst);				
			st.setLong(i++,sed);
			st.setLong(i++,jor);
			st.setLong(i++,vig);
			st.setLong(i++,per);
			st.setLong(i++,com);
			st.setLong(i++,esp);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ItemVO();
				lp.setCodigo(rs.getLong(i++));
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


	public Object getAjaxEspacio(long inst, int sed,int jor, int tipo) {
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LDocenteVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb3.getString("getAjaxEspacio"));
			i=1;
			st.setLong(i++,inst);				
			st.setLong(i++,sed);
			st.setLong(i++,jor);
			st.setLong(i++,tipo);
			st.setLong(i++,tipo);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LDocenteVO();
				lp.setCodigo(rs.getLong(i++));
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


	
}
