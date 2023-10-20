package articulacion.plantillaEvaluacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.plantillaArticulacion.vo.DatosVO;
import articulacion.plantillaEvaluacion.vo.AsignaturaVO;
import articulacion.plantillaEvaluacion.vo.DatosPlanEvalVO;
import articulacion.plantillaEvaluacion.vo.EspecialidadVO;
import articulacion.plantillaEvaluacion.vo.EstEvaluacionVO;
import articulacion.plantillaEvaluacion.vo.EstudianteVO;
import articulacion.plantillaEvaluacion.vo.GrupoVO;
import articulacion.plantillaEvaluacion.vo.RangosVO;
import articulacion.plantillaEvaluacion.vo.datosPruebasVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import articulacion.evaluacionArt.vo.JornadaVO;
import siges.exceptions.InternalErrorException;


public class EvaluacionDAO extends Dao {
	
    private ResourceBundle rb;
    
    public EvaluacionDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("articulacion.plantillaEvaluacion.bundle.sentencias");
	}
       
   public List getEstudiantes(DatosPlanEvalVO datosVO){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		EstudianteVO estudianteVO=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			if(datosVO.getComponente()==1)
				st=cn.prepareStatement(rb.getString("getEstudiantes"+datosVO.getOrdenado()));
			if(datosVO.getComponente()==2)
				st=cn.prepareStatement(rb.getString("getEstudiantes"+(datosVO.getOrdenado()+2)));
			st.setLong(i++,datosVO.getInstitucion());
			st.setLong(i++,datosVO.getSede());
			st.setLong(i++,datosVO.getMetodologia());
			st.setLong(i++,datosVO.getGrupo());
			if(datosVO.getComponente()==2) st.setLong(i++,datosVO.getEspecialidad());
			st.setLong(i++,datosVO.getAsignatura());
			st.setLong(i++,datosVO.getPrueba());
			st.setLong(i++,datosVO.getBimestre());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				estudianteVO=new EstudianteVO();
				estudianteVO.setCodigo(rs.getInt(i++));
				estudianteVO.setDocumento(rs.getString(i++));
				estudianteVO.setApellido(rs.getString(i++));
				estudianteVO.setApellido1(rs.getString(i++));
				estudianteVO.setNombre(rs.getString(i++));
				estudianteVO.setNombre1(rs.getString(i++));
				estudianteVO.setNotaNum1(rs.getFloat(i++));
				estudianteVO.setNotaNum2(rs.getFloat(i++));
				estudianteVO.setNotaNum3(rs.getFloat(i++));
				estudianteVO.setNotaNum4(rs.getFloat(i++));
				estudianteVO.setNotaNum5(rs.getFloat(i++));
//				estudianteVO.setEstado(rs.getBoolean(i++));
				l.add(estudianteVO);
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
		return l;
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
   
   public List getListaRangos(DatosPlanEvalVO datosVO){
	
		Connection cn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List l=new ArrayList();
		RangosVO rangosVO=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			if(datosVO!=null){
								
				ps = cn.prepareStatement(rb.getString("getRangos"));
				ps.setLong(i++,datosVO.getInstitucion());
				ps.setLong(i++,datosVO.getMetodologia());
				ps.setInt(i++,datosVO.getAnVigencia());
				ps.setInt(i++,datosVO.getPerVigencia());
				
				rs=ps.executeQuery();
				while(rs.next()){
					i=1;
					rangosVO=new RangosVO();
					rangosVO.setConceptual(rs.getString(i++));
					rangosVO.setInicio(rs.getFloat(i++));
					rangosVO.setFin(rs.getFloat(i++));
					l.add(rangosVO);
				}
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
		return l;
		
	}
   
   public List getJornada(String inst, String sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		JornadaVO jornadaVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getJornada"));
			i=1;
			st.setInt(i++,Integer.parseInt(inst));
			st.setInt(i++,Integer.parseInt(sede));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				jornadaVO=new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
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
		return l;
	} 
   
   public List getAjaxAsignatura(long grupo){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
			if(grupo==0){return null;}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,grupo);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
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
   public List getAjaxPrueba(long asignatura){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		AsignaturaVO lp=null;
		int i=0;
		try{
			if(asignatura==0){return null;}
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxPrueba"));
			i=1;
			st.setLong(i++,asignatura);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
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

	public boolean insertaEvaluacion(DatosPlanEvalVO datosVO, long est, double notNum, String notCon, boolean estado, List compa) {
		boolean retorno=true;
		Connection cn=null;
		PreparedStatement ps=null;
		int posicion=1;
		try {
			cn=cursor.getConnection();
			
			Iterator iter=compa.iterator();
			EstEvaluacionVO ev;
			boolean actualiza=false,inserta=true;
			while(iter.hasNext()){
				ev=(EstEvaluacionVO)iter.next();
				if(est==ev.getCodigo()){
					if(!notCon.equals(ev.getNotaCon())||notNum!=ev.getNotaNum()||estado!=ev.isEstado()){
						actualiza=true;
					}
					inserta=false;
				}
			}

			if(inserta){
				ps=cn.prepareStatement(rb.getString("insertar_Evaluacion"));
				ps.setLong(posicion++,datosVO.getInstitucion());
				ps.setLong(posicion++,datosVO.getMetodologia());
				ps.setInt(posicion++,datosVO.getAnVigencia());
				ps.setInt(posicion++,datosVO.getPerVigencia());
				ps.setLong(posicion++,datosVO.getAsignatura());
				ps.setLong(posicion++,datosVO.getPrueba());
				ps.setLong(posicion++,est);
				ps.setDouble(posicion++,notNum);
				if(!notCon.equals(""))
					ps.setString(posicion++,notCon);
				else
					ps.setNull(posicion++,Types.VARCHAR);
				ps.setBoolean(posicion++,estado);
				
				ps.executeUpdate();
			}
			if(actualiza){
//				***************Actualizar ********************************
				try {
					posicion=1;
					ps=cn.prepareStatement(rb.getString("actualizar_Evaluacion"));
					ps.setDouble(posicion++,notNum);
					if(!notCon.equals(""))
						ps.setString(posicion++,notCon);
					else
						ps.setNull(posicion++,Types.VARCHAR);
					ps.setBoolean(posicion++,estado);
					ps.setLong(posicion++,est);
					ps.executeUpdate();
				} catch (SQLException e1) {
					retorno=false;
					e1.printStackTrace();
				}
				//************************************************************
			}
			
		} catch (InternalErrorException e) {
			retorno=false;
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 1: case 2291:
				//setMensaje("Cndigo duplicado");
				
				break;
			}
		}finally{
			try{
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return retorno;
	}
	
	public List getEstEvaluacion(DatosPlanEvalVO datosVO){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		EstEvaluacionVO estEvaluacionVO=null;
		int i,pos=1;
		try{
			cn=cursor.getConnection();
			
			st=cn.prepareStatement(rb.getString("getEstEvaluacion"));
			st.setLong(pos++,datosVO.getInstitucion());
			st.setLong(pos++,datosVO.getMetodologia());
			st.setLong(pos++,datosVO.getAnVigencia());
			st.setLong(pos++,datosVO.getPerVigencia());
			st.setLong(pos++,datosVO.getAsignatura());
			st.setLong(pos++,datosVO.getPrueba());
						
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				estEvaluacionVO=new EstEvaluacionVO();
				estEvaluacionVO.setCodigo(rs.getInt(i++));
				estEvaluacionVO.setNotaNum(rs.getFloat(i++));
				estEvaluacionVO.setNotaCon(rs.getString(i++));
				estEvaluacionVO.setEstado(rs.getBoolean(i++));
				
				l.add(estEvaluacionVO);
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
		return l;
	}
	
	public void setReporte(String us,String rec,String tipo,String nombre,int modulo){
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("ReporteInsertar"));			
			int posicion=1;
			ps.setString(posicion++, us);
			ps.setString(posicion++, rec);
			ps.setString(posicion++, tipo);
			ps.setString(posicion++, nombre);
			ps.setInt(posicion++, modulo);			
			ps.executeUpdate();
			ps.close();
		}catch(InternalErrorException in){setMensaje("NO se puede estabecer conexinn con la base de datos: "); return;
		}catch (SQLException sqle) {sqle.printStackTrace();
			try {cn.rollback();} catch (SQLException s) {}
			setMensaje("Error intentando ingresar reporte. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return;
	}
	
	
	 public DatosPlanEvalVO getSedeJornada(DatosPlanEvalVO datosEVO){
	    	Connection cn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int i=0;
			try{
				cn=cursor.getConnection();
				st=cn.prepareStatement(rb.getString("sedeJornada"));
				i=1;
				st.setLong(i++,datosEVO.getInstitucion());
				st.setLong(i++,datosEVO.getSede());
				st.setLong(i++,datosEVO.getJornada());
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					datosEVO.setInstitucion_(rs.getString(i++));
					datosEVO.setSede_(rs.getString(i++));
					datosEVO.setJornada_(rs.getString(i++));
					datosEVO.setFecha_(rs.getString(i++));
				}
				rs.close();
				st.close();
				st=cn.prepareStatement(rb.getString("getEncabezado"));
				i=1;
				st.setLong(i++,datosEVO.getSede());
				st.setLong(i++,datosEVO.getJornada());
				st.setLong(i++,datosEVO.getMetodologia());
				st.setLong(i++,datosEVO.getAsignatura());
				st.setLong(i++,datosEVO.getGrupo());
				st.setLong(i++,datosEVO.getPrueba());
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					datosEVO.setAsignatura_(rs.getString(i++));
					datosEVO.setGrupo_(rs.getString(i++));
					datosEVO.setPrueba_(rs.getString(i++));
					datosEVO.setPorcentaje0(rs.getLong(i++));
					datosEVO.setNombre(rs.getString(i++));
					datosEVO.setAbreviatura(rs.getString(i++));
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
			return datosEVO;
			
		}
	   
	 public String getDocente(DatosPlanEvalVO datosVO){
		String docente="";
		
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		DatosPlanEvalVO datosEVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getDocente"));
			i=1;
			st.setLong(i++,datosVO.getGrupo());
			st.setLong(i++,datosVO.getInstitucion());
			st.setLong(i++,datosVO.getSede());
			st.setLong(i++,datosVO.getJornada());
			st.setLong(i++,datosVO.getAsignatura());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
			    docente=rs.getString(i++);
				
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
		
		 return docente;
	 }
	
	 public List getPruebas(DatosPlanEvalVO datosVO){
			Connection cn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			List l=new ArrayList();
			datosPruebasVO datosPruebas=null;
			int i,pos=1;
			try{
				cn=cursor.getConnection();
				st=cn.prepareStatement(rb.getString("getPruebas"));
				st.setLong(pos++,datosVO.getAsignatura());
				st.setLong(pos++,datosVO.getPrueba());
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					datosPruebas=new datosPruebasVO();
					datosPruebas.setCodigoPrueba(rs.getLong(i++));
					datosPruebas.setCodigoSubPrueba(rs.getLong(i++));
					datosPruebas.setNombrePrueba(rs.getString(i++));
					datosPruebas.setAbreviatura(rs.getString(i++));
					datosPruebas.setPorcentaje(rs.getLong(i++));
					l.add(datosPruebas);
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
			return l;
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
	            rs= st.executeQuery();
	            while(rs.next()){
	                i=1;
	                lp=new GrupoVO();
	                lp.setCodigo (rs.getLong(i++));
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
}
