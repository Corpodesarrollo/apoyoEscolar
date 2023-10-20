/**
 * 
 */
package articulacion.artRotacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.artRotacion.vo.EstructuraVO;
import articulacion.artRotacion.vo.FiltroHorarioVO;
import articulacion.artRotacion.vo.HorarioVO;
import articulacion.artRotacion.vo.LClaseVO;
import articulacion.artRotacion.vo.LDiaVO;
import articulacion.artRotacion.vo.LEncabezadoVO;
import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * 6/10/2007 
 * @author Latined
 * @version 1.2
 */
public class ArtRotacionDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c
	 */
	public ArtRotacionDAO(Cursor c) {
		super(c);
		rb=ResourceBundle.getBundle("articulacion.artRotacion.bundle.artRotacion");		
	}

	public List getAjaxAsignatura(long inst,int anho,int per,int comp,long esp, int sem,long grupo){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,anho);
			st.setInt(i++,per);
			st.setInt(i++,comp);
			st.setLong(i++,esp);
			st.setLong(i++,esp);
			st.setInt(i++,sem);
			//st.setInt(i++,1);//asignaturas que no son complementarias
			st.setLong(i++,grupo);
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

	public List getAjaxDocente(long inst,int sed,int jor,long semestre,long asignatura){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxDocente"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed);
			st.setInt(i++,jor);
			st.setLong(i++,asignatura);
			st.setLong(i++,semestre);
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

	public List getAjaxDocenteSede(long inst,int sed,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxDocente0"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed);
			st.setInt(i++,jor);
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

	public List getAjaxEspecialidad(long inst){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxEspecialidad"));
			i=1;
			st.setLong(i++,inst);
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

	public List getAjaxGrupo(long inst,int sede,int jor,int anho,int per,int comp,long esp, int sem){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxGrupo"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sede);
			st.setInt(i++,jor);
			st.setInt(i++,anho);
			st.setInt(i++,per);
			st.setInt(i++,comp);
			st.setLong(i++,esp);
			st.setLong(i++,esp);
			st.setInt(i++,sem);
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

	public List getJornada(long inst,int sede,int jor){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
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
				lp=new ItemVO();
				lp.setPadre(rs.getInt(i++));
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

	public List getSede(long inst,int sede){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
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
				lp=new ItemVO();
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

	public boolean actualizar(EstructuraVO p,EstructuraVO p2){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int posicion=1;
		try{
			//calculo de hfin y mfin
			int min=p.getParDuracion()*p.getParHorasBloque()*p.getParBloques();
			int h=Math.round(min/60);
			int m=Math.round(min%60);
			int x=p.getParMinIni()+m;
			int th=Math.round(x/60);
			int tm=Math.round(x%60);
			int hfin=p.getParHoraIni()+h+th;
			if(hfin>23){
				setMensaje("La hora final esta mas alla de las 23:59 y no se puede registrar");
				return false;
			}
			p.setParMinFin(tm);
			p.setParHoraFin(hfin);
			//
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("actualizarParametro"));
			posicion = 1;
			ps.setString(posicion++,p.getParNombre());
			ps.setInt(posicion++,p.getParSabado());
			ps.setInt(posicion++,p.getParDomingo());
			ps.setInt(posicion++,p.getParHoraIni());
			ps.setInt(posicion++,p.getParMinIni());
			ps.setInt(posicion++,p.getParHoraFin());
			ps.setInt(posicion++,p.getParMinFin());
			ps.setInt(posicion++,p.getParDuracion());
			ps.setInt(posicion++,p.getParHorasBloque());
			ps.setInt(posicion++,p.getParBloques());
			//w
			ps.setLong(posicion++,p.getParInstitucion());
			ps.setInt(posicion++,p.getParSede());
			ps.setInt(posicion++,p.getParJornada());
			ps.setLong(posicion++,p.getParAnhoVigencia());
			ps.setLong(posicion++,p.getParPerVigencia());
			ps.setInt(posicion++,p.getParComponente());
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

	public boolean eliminar(EstructuraVO p){
		Connection cn = null;
		PreparedStatement ps = null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(rb.getString("eliminarParametro"));
			posicion = 1;
			ps.setLong(posicion++,p.getParInstitucion());
			ps.setInt(posicion++,p.getParSede());
			ps.setInt(posicion++,p.getParJornada());
			ps.setLong(posicion++,p.getParAnhoVigencia());
			ps.setLong(posicion++,p.getParPerVigencia());
			ps.setLong(posicion++,p.getParComponente());
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
					setMensaje(String.valueOf(2292));
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

	public EstructuraVO getParametroVO(EstructuraVO a){
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		EstructuraVO estructuraVO=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("getParametro"));			
			i=1;
			pst.setLong(i++,a.getParInstitucion());
			pst.setInt(i++,a.getParSede());
			pst.setLong(i++,a.getParJornada());
			pst.setInt(i++,a.getParAnhoVigencia());
			pst.setInt(i++,a.getParPerVigencia());
			pst.setInt(i++,a.getParComponente());
			rs=pst.executeQuery();
			while(rs.next()){
				i=1;
				estructuraVO=new EstructuraVO();
				estructuraVO.setParNombre(rs.getString(i++));
				estructuraVO.setParInstitucion(rs.getLong(i++));
				estructuraVO.setParSede(rs.getInt(i++));
				estructuraVO.setParJornada(rs.getInt(i++));
				estructuraVO.setParAnhoVigencia(rs.getInt(i++));
				estructuraVO.setParPerVigencia(rs.getInt(i++));
				estructuraVO.setParComponente(rs.getInt(i++));
				estructuraVO.setParSabado(rs.getInt(i++));
				estructuraVO.setParDomingo(rs.getInt(i++));
				estructuraVO.setParHoraIni(rs.getInt(i++));
				estructuraVO.setParMinIni(rs.getInt(i++));
				estructuraVO.setParHoraFin(rs.getInt(i++));
				estructuraVO.setParMinFin(rs.getInt(i++));
				estructuraVO.setParDuracion(rs.getInt(i++));
				estructuraVO.setParHorasBloque(rs.getInt(i++));
				estructuraVO.setParBloques(rs.getInt(i++));
				estructuraVO.setFormaEstado("1");
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
		return estructuraVO;
	}

	public boolean guardarHorario(FiltroHorarioVO filtro,String []check,String [] checkEsp){
		Connection cn=null;
		PreparedStatement st=null;
		PreparedStatement st2=null;
		EstructuraVO param=null;
		int i=0;
		try{
			param=filtro.getEstructuraVO();
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//eliminar el horario como tal
			st=cn.prepareStatement(rb.getString("eliminarHorario"));
			i=1;
			st.setLong(i++,filtro.getFilGrupo());
			st.setLong(i++,filtro.getFilAsignatura());
			st.executeUpdate();
			st.close();
			//ingresar los dias escogidos
			st=cn.prepareStatement(rb.getString("ingresarHorario"));
			st.clearBatch();
			st2=cn.prepareStatement(rb.getString("ingresarHorario2"));
			st2.clearBatch();
			String [] valores=null;
			String [] valores2=null;
			if(check!=null){
				for(int j=0;j<check.length;j++){
					if(check[j]!=null && !check[j].equals("-99")){
						valores=check[j].replace('|',':').split(":");
						valores2=checkEsp[j].replace('|',':').split(":");
						i=1;
						if(valores2[2]==null || valores2[2].equals("-99")){//sepone null explicito
							st2.setLong(i++,filtro.getFilGrupo());
							st2.setInt(i++,Integer.parseInt(valores[1]));
							st2.setInt(i++,Integer.parseInt(valores[0]));
							st2.setLong(i++,filtro.getFilAsignatura());
							if(filtro.getFilDocente()==-99) st2.setNull(i++,Types.VARCHAR);
							else st2.setLong(i++,filtro.getFilDocente());
							st2.setLong(i++,filtro.getFilInstitucion());
							st2.setInt(i++,filtro.getFilSede());
							st2.setInt(i++,filtro.getFilJornada());
							st2.addBatch();
						}else{//se pone el valro porpiamente
							st.setLong(i++,filtro.getFilGrupo());
							st.setInt(i++,Integer.parseInt(valores[1]));
							st.setInt(i++,Integer.parseInt(valores[0]));
							st.setLong(i++,filtro.getFilAsignatura());
							if(filtro.getFilDocente()==-99) st.setNull(i++,Types.VARCHAR);
							else st.setLong(i++,filtro.getFilDocente());
							st.setLong(i++,Long.parseLong(valores2[2]));
							st.setLong(i++,filtro.getFilInstitucion());
							st.setInt(i++,filtro.getFilSede());
							st.setInt(i++,filtro.getFilJornada());
							st.addBatch();
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
			}	
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}

	public synchronized boolean insertar(EstructuraVO p){
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int posicion=1;
		try{
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			//validar si ya esta
			ps = cn.prepareStatement(rb.getString("validarIDParametro"));
			posicion = 1;
			ps.setLong(posicion++,p.getParInstitucion());
			ps.setLong(posicion++,p.getParSede());
			ps.setLong(posicion++,p.getParJornada());
			ps.setLong(posicion++,p.getParAnhoVigencia());
			ps.setLong(posicion++,p.getParPerVigencia());
			ps.setInt(posicion++,p.getParComponente());
			rs=ps.executeQuery();
			if(rs.next()){
				setMensaje("Los parnmetros de la sede jornada y componente ya existen");
				return false;
			}
			rs.close();ps.close();
			//calculo de hfin y mfin
			int min=p.getParDuracion()*p.getParHorasBloque()*p.getParBloques();
			int h=Math.round(min/60);
			int m=Math.round(min%60);
			int x=p.getParMinIni()+m;
			int th=Math.round(x/60);
			int tm=Math.round(x%60);
			int hfin=p.getParHoraIni()+h+th;
			if(hfin>23){
				setMensaje("La hora final esta mas alla de las 23:59 y no se puede registrar");
				return false;
			}
			p.setParMinFin(tm);
			p.setParHoraFin(hfin);
			//calcular el codigo unico
			ps = cn.prepareStatement(rb.getString("getCodigoEstructura"));
			rs=ps.executeQuery();
			if(rs.next()){
				p.setParCodigo(rs.getLong(1));
			}
			rs.close();
			ps.close();
			
			//ingresar
			ps = cn.prepareStatement(rb.getString("insertarParametro"));
			posicion = 1;
			ps.setLong(posicion++,p.getParCodigo());
			ps.setString(posicion++,p.getParNombre());
			ps.setLong(posicion++,p.getParInstitucion());
			ps.setInt(posicion++,p.getParSede());
			ps.setInt(posicion++,p.getParJornada());
			ps.setLong(posicion++,p.getParAnhoVigencia());
			ps.setLong(posicion++,p.getParPerVigencia());
			ps.setInt(posicion++,p.getParComponente());
			ps.setInt(posicion++,p.getParSabado());
			ps.setInt(posicion++,p.getParDomingo());
			ps.setInt(posicion++,p.getParHoraIni());
			ps.setInt(posicion++,p.getParMinIni());
			ps.setInt(posicion++,p.getParHoraFin());
			ps.setInt(posicion++,p.getParMinFin());
			ps.setInt(posicion++,p.getParDuracion());
			ps.setInt(posicion++,p.getParHorasBloque());
			ps.setInt(posicion++,p.getParBloques());
			ps.executeUpdate();
			cn.commit();
		}catch(SQLException sqle){sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error insertantdo Parnmetro. Posible problema: "+getErrorCode(sqle));
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

	public boolean validarHorario(FiltroHorarioVO filtro){
		try{
			//porque no envio anoy periodo del filtro?
			EstructuraVO p=new EstructuraVO();
			p.setParInstitucion(filtro.getFilInstitucion());
			p.setParSede(filtro.getFilSede());
			p.setParJornada(filtro.getFilJornada());
			p.setParComponente(filtro.getFilComponente());
			p.setParAnhoVigencia(filtro.getFilAnhoVigencia());
			p.setParPerVigencia(filtro.getFilPerVigencia());
			p=getParametroVO(p);
			if(p==null){				
				return false;
			}else{
				filtro.setEstructuraVO(p);
			}
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		}
		return true;
	}

	private List getDias(int sab,int dom){
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
		return l;
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

	public HorarioVO getHorario(FiltroHorarioVO filtro){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		HorarioVO horario=null;
		EstructuraVO param=null;
		LEncabezadoVO encabezadoVO=null;
		LClaseVO claseVO=null;
		LDiaVO diaVO=null;
		int i=0;
		try{
			param=filtro.getEstructuraVO();
			filtro.setFilDisabled(0);
			//encabezado
			List lEncabezado=new ArrayList();
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Lunes"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Martes"); lEncabezado.add(encabezadoVO); 
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Miercoles"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Jueves"); lEncabezado.add(encabezadoVO);
			encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Viernes"); lEncabezado.add(encabezadoVO);		
			if(param.getParSabado()==1){ encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Sabado"); lEncabezado.add(encabezadoVO);	}
			if(param.getParDomingo()==1){ encabezadoVO=new LEncabezadoVO(); encabezadoVO.setNombre("Domingo"); lEncabezado.add(encabezadoVO);}
			cn=cursor.getConnection();
			//intensidad horaria
			st=cn.prepareStatement(rb.getString("getIntensidadHoraria"));
			i=1;
			st.setLong(i++,filtro.getFilAsignatura());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilIH(rs.getInt(1));
			}
			rs.close();
			st.close();
			//clases
			List lClase=new ArrayList();
			List lDia=null;
			for(int j=1;j<=(param.getParHorasBloque()*param.getParBloques());j++){
				claseVO=new LClaseVO();
				claseVO.setClase(j);
				claseVO.setHora(getHoraReal(j,param.getParDuracion(),param.getParHoraIni(),param.getParMinIni()));
				//crear los dias
				lDia=getDias(param.getParSabado(),param.getParDomingo());
				//buscar los dias
				st=cn.prepareStatement(rb.getString("getHorarioClase"));
				i=1;
				st.setLong(i++,filtro.getFilGrupo());
				st.setLong(i++,filtro.getFilAnhoVigencia());
				st.setLong(i++,filtro.getFilPerVigencia());
				st.setInt(i++,j);
				st.setLong(i++,filtro.getFilDocente());
				st.setLong(i++,filtro.getFilAnhoVigencia());
				st.setLong(i++,filtro.getFilPerVigencia());
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
					diaVO=(LDiaVO)lDia.get(k);
					diaVO.setAsignatura(rs.getString(i++));
					diaVO.setDocente(rs.getString(i++));
					diaVO.setEspacio(rs.getString(i++));
					diaVO.setEsp(rs.getLong(i++));
					diaVO.setGrupo(rs.getString(i++));
					diaVO.setIdAsignatura(rs.getLong(i++));
					diaVO.setIdGrupo(rs.getLong(i++));
					diaVO.setIdDocente(rs.getLong(i++));
					diaVO.setChecked("checked");
					//validar RESTRICCIONES
					//DOC= ASIG= GRUPO!=   PERO DIA_DOC=-99 
					if(filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilAsignatura()==diaVO.getIdAsignatura() && filtro.getFilDocente()==diaVO.getIdDocente() && diaVO.getIdDocente()==-99){
						diaVO=resetDiaVO(diaVO);
					}
					//DOC= ASIG= GRUPO!=   PERO DIA_DOC!=-99 
					if(filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilAsignatura()==diaVO.getIdAsignatura() && filtro.getFilDocente()==diaVO.getIdDocente() && diaVO.getIdDocente()!=-99){
						diaVO.setDisabled("disabled");
						diaVO.setStyle("c5");
					}
					//DOC= ASIG!= GRUPO= 
					if(filtro.getFilAsignatura()!=diaVO.getIdAsignatura() && filtro.getFilDocente()==diaVO.getIdDocente() && filtro.getFilGrupo()==diaVO.getIdGrupo()){
						diaVO.setDisabled("disabled");
						diaVO.setStyle("c6");
					}
					//doc= ASIG!= GRUPO!= pero dia_doc!=-99
					if(filtro.getFilAsignatura()!=diaVO.getIdAsignatura() && filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilDocente()==diaVO.getIdDocente() && filtro.getFilDocente()!=-99){
						diaVO.setDisabled("disabled");
						diaVO.setStyle("c5");
					}
					//doc= ASIG!= GRUPO!= pero dia_doc==-99
					if(filtro.getFilAsignatura()!=diaVO.getIdAsignatura() && filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilDocente()==diaVO.getIdDocente() && filtro.getFilDocente()==-99){
						diaVO=resetDiaVO(diaVO);
					}
					//doc!= asig= grupo=
					//caso especial en que no se debe dejar guardar el horario
					if(filtro.getFilAsignatura()==diaVO.getIdAsignatura() && filtro.getFilGrupo()==diaVO.getIdGrupo() && filtro.getFilDocente()!=diaVO.getIdDocente()){
						diaVO.setDisabled("disabled");
						diaVO.setStyle("c4");
						filtro.setFilDisabled(1);
					}
					//doc!= asig!= grupo=
					if(filtro.getFilAsignatura()!=diaVO.getIdAsignatura() && filtro.getFilDocente()!=diaVO.getIdDocente() && filtro.getFilGrupo()==diaVO.getIdGrupo()){
						diaVO.setDisabled("disabled");
						diaVO.setStyle("c4");
					}
					
					//doc!= asig= grupo!= 
					if(filtro.getFilAsignatura()==diaVO.getIdAsignatura() && filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilDocente()!=diaVO.getIdDocente()){
						diaVO=resetDiaVO(diaVO);
					}
					//doc!= asig!= grupo!= 
					if(filtro.getFilAsignatura()!=diaVO.getIdAsignatura() && filtro.getFilGrupo()!=diaVO.getIdGrupo() && filtro.getFilDocente()!=diaVO.getIdDocente()){
						diaVO=resetDiaVO(diaVO);
					}
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
			horario.setHorEspacio(getAllEspacio(cn,filtro.getFilInstitucion(),filtro.getFilSede(),filtro.getFilJornada()));
			horario.setFormaEstado("1");
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

	private List getAllEspacio(Connection cn,long inst,int sed,int jor){
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
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
			}catch(InternalErrorException inte){}
		}
		return lpA;
	}

	public List getAllParam(long inst,int sed,int jor,int vigencia,int periodo){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		EstructuraVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAllParamsHorario"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed); st.setInt(i++,sed);
			st.setInt(i++,jor); st.setInt(i++,jor);
			st.setInt(i++,vigencia); 
			st.setInt(i++,periodo);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				lp=new EstructuraVO();
				lp.setParNombre(rs.getString(i++));
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
				lp.setParNombreComponente(rs.getString(i++));
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

	
	public boolean eliminarHorario(FiltroHorarioVO filtro)throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			cn.setAutoCommit(false);
			//cuando NO tiene semestre
			if(filtro.getFilSemestre()==-99){
//				System.out.println("Elimina horario de componente");
				st=cn.prepareStatement(rb.getString("eliminarHorarioComponente."+filtro.getFilComponente()));
				i=1;
				st.setLong(i++,filtro.getFilInstitucion());
				st.setLong(i++,filtro.getFilSede());
				st.setLong(i++,filtro.getFilJornada());
				st.setInt(i++,filtro.getFilAnhoVigencia());
				st.setInt(i++,filtro.getFilPerVigencia());
				st.setInt(i++,filtro.getFilComponente());
				if(filtro.getFilComponente()==2){
					st.setInt(i++,filtro.getFilEspecialidad());
				}
				st.executeUpdate();
				st.close();
				/*
				st=cn.prepareStatement(rb.getString("eliminarIHHorarioSemestre"));
				i=1;
				st.setLong(i++,filtro.getFilGrupo());
				st.executeUpdate();
				st.close();
				*/
			}
			//cuando NO tiene grupo
			if(filtro.getFilSemestre()!=-99 && filtro.getFilGrupo()==-99){
//				System.out.println("Elimina horario de semestre");
				st=cn.prepareStatement(rb.getString("eliminarHorarioSemestre."+filtro.getFilComponente()));
				i=1;
				st.setLong(i++,filtro.getFilInstitucion());
				st.setLong(i++,filtro.getFilSede());
				st.setLong(i++,filtro.getFilJornada());
				st.setInt(i++,filtro.getFilAnhoVigencia());
				st.setInt(i++,filtro.getFilPerVigencia());
				st.setInt(i++,filtro.getFilComponente());
				if(filtro.getFilComponente()==2){
					st.setInt(i++,filtro.getFilEspecialidad());
				}
				st.setInt(i++,filtro.getFilSemestre());
				st.executeUpdate();
				st.close();
			}
			
			//cuando NO tiene asig
			if(filtro.getFilSemestre()!=-99 && filtro.getFilGrupo()!=-99 && filtro.getFilAsignatura()==-99){
//				System.out.println("Elimina horario de grupo");
				st=cn.prepareStatement(rb.getString("eliminarHorarioGrupo"));
				i=1;
				st.setLong(i++,filtro.getFilGrupo());
				st.executeUpdate();
				st.close();
			}
			//cuando Tiene todo
			if(filtro.getFilSemestre()!=-99 && filtro.getFilGrupo()!=-99 && filtro.getFilAsignatura()!=-99){
//				System.out.println("Elimina horario de Asignatura");
				st=cn.prepareStatement(rb.getString("eliminarHorarioAsignatura"));
				i=1;
				st.setLong(i++,filtro.getFilGrupo());
				st.setLong(i++,filtro.getFilAsignatura());
				st.executeUpdate();
				st.close();
			}
			cn.commit();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
			return false;
		}catch(Exception sqle){
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
			return false;
		}finally{
			try{
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return true;
	}
	
	public int getPeriodoActual(){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getPeriodoActual"));
			rs=st.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
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
		return 1;
	}
	
	public List getAjaxEstructura(long inst,int sed,int jor,int anho,int periodo){
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List lpA=new ArrayList();
		ItemVO lp=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("getAjaxEstructura"));
			i=1;
			st.setLong(i++,inst);
			st.setInt(i++,sed);
			st.setInt(i++,jor);
			st.setInt(i++,anho);
			st.setInt(i++,periodo);
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
}
