package articulacion.horarioEstudiante.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.horarioArticulacion.vo.FiltroHorarioVO;
import articulacion.horarioEstudiante.vo.FiltroInscripcionVO;
import articulacion.horarioEstudiante.vo.HorarioVO;
import articulacion.horarioEstudiante.vo.LAsignaturaVO;
import articulacion.horarioEstudiante.vo.LClaseVO;
import articulacion.horarioEstudiante.vo.LDiaVO;
import articulacion.horarioEstudiante.vo.LDocenteVO;
import articulacion.horarioEstudiante.vo.LEncabezadoVO;
import articulacion.horarioEstudiante.vo.LEspacioVO;
import articulacion.horarioEstudiante.vo.LEspecialidadVO;
import articulacion.horarioEstudiante.vo.LGrupoVO;
import articulacion.horarioEstudiante.vo.LJornadaVO;
import articulacion.horarioEstudiante.vo.LSedeVO;
import articulacion.horarioEstudiante.vo.ParametroVO;
import articulacion.horarioEstudiante.vo.ComponenteVO;
import articulacion.inscripcion.vo.JornadaVO;
import siges.login.beans.Login;

public class HorarioEstudianteDAO extends Dao{

	public HorarioEstudianteDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.horarioEstudiante.bundle.horarioEstudiante");
	}

	public ParametroVO getAllParam(long inst,int sed,int jor,int componente ){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ParametroVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			long periodo=getPeriodo(cn);
			st=cn.prepareStatement(rb.getString("getAllParamsHorario"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed); st.setInt(i++,sed);
			st.setInt(i++,jor); st.setInt(i++,jor);
			st.setLong(i++,getVigenciaNumerico()); 
			st.setLong(i++,periodo);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new ParametroVO();
				lp.setParInstitucion(rs.getLong(i++));
				lp.setParSede(rs.getInt(i++));
				lp.setParJornada(rs.getInt(i++));
				lp.setParAnhoVigencia(rs.getInt(i++));
				lp.setParPerVigencia(rs.getInt(i++));
				lp.setParComponente(rs.getInt(i++));
				lp.setParHoraInicio(rs.getString(i++));
				lp.setParHoraFinalizacion(rs.getString(i++));
				lp.setParNombreSede(rs.getString(i++));
				lp.setParNombreJornada(rs.getString(i++));
				
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
		return lp;
	}

	public ParametroVO getParametroVO(long inst,int sed,int jor,int componente,long ano){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ParametroVO parametroVO=null;
		int i=0;
		try{
			//System.out.println("entra a consulta param");
			cn=cursor.getConnection();
			long periodo=getPeriodo(cn);
			pst=cn.prepareStatement(rb.getString("getParametro"));			
			i=1;
			/*System.out.println("e "+inst);
			System.out.println("en "+sed);
			System.out.println("ent "+jor);
			System.out.println("ent "+ano);
			System.out.println("ent "+periodo);
			System.out.println("ent "+componente);*/
			
			pst.setLong(i++,inst);
			pst.setInt(i++,sed);
			pst.setLong(i++,jor);
			pst.setLong(i++,ano);
			pst.setLong(i++,periodo);
			pst.setLong(i++,componente);
			rs=pst.executeQuery();
			while(rs.next()){
				//System.out.println("entra a la respuesta");
				i=1;
				parametroVO=new ParametroVO();
				parametroVO.setParInstitucion(rs.getLong(i++));
				parametroVO.setParSede(rs.getInt(i++));
				parametroVO.setParJornada(rs.getInt(i++));
				parametroVO.setParAnhoVigencia(rs.getInt(i++));
				parametroVO.setParPerVigencia(rs.getInt(i++));
				parametroVO.setParComponente(rs.getInt(i++));
				parametroVO.setParSabado(rs.getInt(i++));
				parametroVO.setParDomingo(rs.getInt(i++));
				parametroVO.setParHoraIni(rs.getInt(i++));
				parametroVO.setParMinIni(rs.getInt(i++));
				parametroVO.setParHoraFin(rs.getInt(i++));
				parametroVO.setParMinFin(rs.getInt(i++));
				parametroVO.setParDuracion(rs.getInt(i++));
				parametroVO.setParHorasBloque(rs.getInt(i++));
				parametroVO.setParBloques(rs.getInt(i++));
				parametroVO.setFormaEstado("1");
				
			}
			//System.out.println("param "+parametroVO.getParSabado());
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
		return parametroVO;
	}

	

	public List getSede(long inst,int sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LSedeVO lp=null;
		int i=0;
		try{
			//System.out.println("Calculo de sedes"+inst+"::"+sede);
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getSede"));
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
			st=cn.prepareStatement(rb.getString("getJornada"));
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

	
	

	private String getHoraReal(int clase,int duracion,int hini,int mini){
		if(clase==1){
			return (hini<9?"0":"")+String.valueOf(hini)+":"+(mini<9?"0":"")+String.valueOf(mini);
		}
		int m=mini;
		m+=duracion*(clase-1);
		int h=Math.round(m/60);
		m=Math.round(m%60);
		hini+=h;
		mini=m;
		return (hini<9?"0":"")+String.valueOf(hini)+":"+(mini<9?"0":"")+String.valueOf(mini);
	}
	
	public HorarioVO getHorario(FiltroInscripcionVO  general,ComponenteVO filtro){
		//System.out.println("llega al dao");
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		//List lpA=new ArrayList();
		HorarioVO horario=null;
		ParametroVO param=null;
		LEncabezadoVO encabezadoVO=null;
		LClaseVO claseVO=null;
		LDiaVO diaVO=null;
		int i=0;
		try{
			//param=filtro.getParametroVO();
			int vig=(int)getVigenciaNumerico();	
			param= getParametroVO(general.getFilInstitucion(),general.getFilSede(),general.getFilJornada(),filtro.getComponente(),vig);
			//filtro.setFilDisabled(0);
			//encabezado
			List lEncabezado=new ArrayList();
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Lunes"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Martes"); lEncabezado.add(encabezadoVO); 
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Miercoles"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Jueves"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Viernes"); lEncabezado.add(encabezadoVO);		
			if (param!=null)
			{
			if(param.getParSabado()==1){ 
				encabezadoVO=new LEncabezadoVO(); 
				encabezadoVO.setNombre("Sabado"); 
				lEncabezado.add(encabezadoVO);	
				}
			if(param.getParDomingo()==1){ 
				encabezadoVO=new LEncabezadoVO(); 
				encabezadoVO.setNombre("Domingo"); lEncabezado.add(encabezadoVO);}
			
			cn=cursor.getConnection();
			int periodo=getPeriodo(cn);
			
			
			//clases
			List lClase=new ArrayList();
			List lDia=null;
			for(int j=1;j<=(param.getParHorasBloque()*param.getParBloques());j++){
				//System.out.println("J VALE" +j);
				claseVO=new LClaseVO();
				claseVO.setClase(j);
				claseVO.setHora(getHoraReal(j,param.getParDuracion(),param.getParHoraIni(),param.getParMinIni()));
				//crear los dias
				lDia=getDias(param.getParSabado(),param.getParDomingo());
				//buscar los dias
				
				st=cn.prepareStatement(rb.getString("getHorarioClase"+filtro.getComponente()));
				i=1;
				st.setLong(i++,general.getFilEstudiante());
				if(filtro.getComponente()==2) st.setLong(i++,general.getFilEspecialidad());
				st.setLong(i++,general.getFilInstitucion());
				st.setInt(i++,general.getFilSede());
				st.setInt(i++,filtro.getJornada());
				st.setInt(i++,vig);
				st.setInt(i++,periodo);
				st.setInt(i++,j);
				
				st.setLong(i++,general.getFilEstudiante());
				if(filtro.getComponente()==2) st.setLong(i++,general.getFilEspecialidad());
				st.setLong(i++,general.getFilInstitucion());
				st.setInt(i++,general.getFilSede());
				st.setInt(i++,filtro.getJornada());
				st.setInt(i++,vig);
				st.setInt(i++,periodo);
				st.setInt(i++,j);
				rs=st.executeQuery();
				int k=0;
				while(rs.next()){
					i=1;
					switch(rs.getInt(i++)){
						case 7:
							if(lDia.size()==6) k=5;
							if(lDia.size()==7) k=6;
						break;
						default:
							k=rs.getInt(1)-1;
						break;
					}
					//System.out.println("K VALE" +k);
				
					diaVO=(LDiaVO)lDia.get(k);
					diaVO.setAsignatura(rs.getString(i++));
					diaVO.setNombreAsignatura(rs.getString(i++));
					diaVO.setDocente(rs.getString(i++));
					diaVO.setEspacio(rs.getString(i++));
					diaVO.setEsp(rs.getLong(i++));
					diaVO.setGrupo(rs.getString(i++));
					diaVO.setIdAsignatura(rs.getLong(i++));
					diaVO.setIdGrupo(rs.getLong(i++));
					diaVO.setIdDocente(rs.getLong(i++));
					lDia.set(k,diaVO);
				}
				rs.close();
				st.close();
				claseVO.setDia(lDia);
				lClase.add(claseVO);
			}
			horario=new HorarioVO();
			horario.setHorEncabezado(lEncabezado);
			horario.setHorClase(lClase);
			horario.setHorEspacio(getAllEspacio(cn,general.getFilInstitucion(),general.getFilSede(),general.getFilJornada()));
			horario.setFormaEstado("1");
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
		return horario;
	}

	private LDiaVO resetDiaVO(LDiaVO diaVO){
		diaVO.setAsignatura(null);
		diaVO.setDocente(null);
		diaVO.setEspacio(null);
		diaVO.setEsp(0);
		diaVO.setGrupo(null);
		diaVO.setIdAsignatura(0);
		diaVO.setIdGrupo(0);
		diaVO.setIdDocente(0);
		diaVO.setChecked("");
		return diaVO;
	}
	
	private List getDias(int sab,int dom){
		//System.out.println("sabado "+sab);
		//System.out.println("dom "+dom);
		List l=new ArrayList();
		LDiaVO dia=null;
		for(int i=1;i<=5;i++){
			dia=new LDiaVO();
			dia.setDia(i);
			dia.setEsp(-99);
			l.add(dia);
		}
		if(sab==1){
			dia=new LDiaVO();
			dia.setEsp(-99);
			dia.setDia(6);
			l.add(dia);
		}
		if(dom==1){
			dia=new LDiaVO();
			dia.setEsp(-99);
			dia.setDia(7);
			l.add(dia);
		}
		//System.out.println("LO QUE NECESITA JOHN "+l.size());
		return l;
	}
	public boolean validarHorario(FiltroHorarioVO filtro){
		try{
			ParametroVO p=new ParametroVO();
			p.setParInstitucion(filtro.getFilInstitucion());
			p.setParSede(filtro.getFilSede());
			p.setParJornada(filtro.getFilJornada());
			//p=getParametroVO(p);
			if(p==null){				
				return false;
			}else{
				//filtro.setParametroVO(p);
			}
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		return true;
	}

	public List getAllEspacio(Connection cn,long inst,int sed,int jor){
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		LEspacioVO lp=null;
		int i=0;
		try{
			st=cn.prepareStatement(rb.getString("getEspacioFisico"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed);
			st.setInt(i++,jor);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new LEspacioVO();
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
			}catch(InternalErrorException inte){}
		}
		return lpA;
	}
	
	private int getPeriodo(Connection cn){
		int periodo=0;
		ResultSet rs=null;
		PreparedStatement ps = null;
		try{
			ps=cn.prepareStatement(rb.getString("getPeriodo"));
			rs=ps.executeQuery();
			if(rs.next()){
				periodo=rs.getInt(1);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(Exception sqle){
			sqle.printStackTrace();
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				}catch(InternalErrorException e){}
			}
		
		return periodo;
	}
	
	 public JornadaVO[] getListaJornada(String insti,String sede){
	    	
	    	//System.out.println("LA CUESTION ENTRO A LO DE JORNADA "+insti);
			JornadaVO[] jornada=null;
			int posicion = 1;
			Connection cn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List la=new ArrayList();
			try{
				cn = cursor.getConnection();
				ps = cn.prepareStatement(rb.getString("getJornada"));
				ps.setString(posicion++,insti);
				ps.setString(posicion++,sede);
				posicion = 1;
				rs = ps.executeQuery();
				JornadaVO a=null;
				while(rs.next()){
				//	System.out.println("ENCUENTRA LAS JORNADAS");
					a=new JornadaVO();
					posicion = 1;
					a.setCodigo(rs.getInt(posicion++));
					a.setNombre(rs.getString(posicion++));
					la.add(a);
				}			
				rs.close();
				ps.close();
				if(!la.isEmpty()){
					int i=0;
					jornada=new JornadaVO[la.size()];
					Iterator iterator =la.iterator();
					while (iterator.hasNext())
						jornada[i++]=(JornadaVO)(iterator.next());
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
			return jornada;
		}
	
}

