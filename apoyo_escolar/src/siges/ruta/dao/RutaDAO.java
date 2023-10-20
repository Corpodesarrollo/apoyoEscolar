package siges.ruta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.institucion.organizacion.beans.AsociacionVO;
import siges.login.beans.Login;
import siges.ruta.vo.FiltroVO;
import siges.ruta.vo.NutricionVO;
import siges.ruta.vo.GestacionVO;

public class RutaDAO extends Dao{
    public String mensaje;
    private java.text.SimpleDateFormat sdf;
    private ResourceBundle rb;

	public RutaDAO(Cursor c){
		super(c);
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("siges.ruta.ruta");
	}
	
	public Collection[] getFiltrosBuscarNutricion(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[5];
		try{
			long inst=Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("Filtro.Sede"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Jornada2"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Metodologia"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Grado"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[3] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Grupo"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[4] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener filtros de busqueda. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection[] getFiltrosBuscarGestacion(Login login){
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection []list = new Collection[5];
		try{
			long inst=Long.parseLong(login.getInstId());
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("Filtro.Sede"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[0] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Jornada"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[1] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Metodologia"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[2] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Grado2"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[3] = getCollection(rs);
			rs.close();ps.close();
			ps = cn.prepareStatement(rb.getString("Filtro.Grupo2"));
			posicion = 1;
			ps.setLong(posicion++, inst);
			rs = ps.executeQuery();
			list[4] = getCollection(rs);
			rs.close();ps.close();
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			try{cn.rollback();}catch(SQLException s){}
			setMensaje("Error intentando obtener filtros de busqueda. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return list;
	}
	
	public Collection getResultadoNutricion(Login login,FiltroVO filtro){
		String s=null;
		Collection c=null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			int inst_=0;
			int sede_=0;
			int jor_=0;
			int met_=0;
			int gra_=0;
			int gru_=0;
			int id_=0;
			int nom1_=0;
			int nom2_=0;
			int ape1_=0;
			int ape2_=0;
				s=" "+rb.getString("estudiante.consulta1");
				if((filtro.getSede()!=null && !filtro.getSede().equals("-9")) || (filtro.getJornada()!=null && !filtro.getJornada().equals("-9")) || (filtro.getGrado()!=null && !filtro.getGrado().equals("-9")) || (filtro.getGrupo()!=null && !filtro.getGrupo().equals("-9")) || (filtro.getMetodologia()!=null && !filtro.getMetodologia().equals("-9"))){
				   inst_=1;
				   //o[1]=login.getInstId();
				   s+=" "+rb.getString("estudiante.consulta111");					
				   s+=" "+rb.getString("estudiante.consulta2");					
				}
				if(filtro.getSede()!=null && !filtro.getSede().equals("-9")){
				   //o[1]=filtro.getSede();
				   sede_=1;
				   s+=" "+rb.getString("estudiante.consulta3");
				}
				if(filtro.getJornada()!=null && !filtro.getJornada().equals("-9")){
					   //o[1]=filtro.getJornada();
					jor_=1;
					   s+=" "+rb.getString("estudiante.consulta4");
				}
				if(filtro.getGrado()!=null && !filtro.getGrado().equals("-9")){
					   //o[1]=filtro.getGrado();
					gra_=1;
				   s+=" "+rb.getString("estudiante.consulta5");
				}
				if(filtro.getGrupo()!=null && !filtro.getGrupo().equals("-9")){
					   //o[1]=filtro.getGrupo();
					gru_=1;
					   s+=" "+rb.getString("estudiante.consulta6");
				}
				if(filtro.getMetodologia()!=null && !filtro.getMetodologia().equals("-9")){
					   //o[1]=filtro.getMetodologia();
					met_=1;
					   s+=" "+rb.getString("estudiante.consulta61");
				}
				if(!filtro.getId().trim().equals("")){
					   //o[1]=filtro.getId();
					id_=1;
					   s+=" "+rb.getString("estudiante.consulta8");
				}
				if(!filtro.getNombre1().trim().equals("")){
					   //o[1]=filtro.getNombre1();
					nom1_=1;
					   s+=" "+rb.getString("estudiante.consulta9");
				}
				if(!filtro.getNombre2().trim().equals("")){
					   //o[1]=filtro.getNombre2();
					nom2_=1;
					   s+=" "+rb.getString("estudiante.consulta10");
				}
				if(!filtro.getApellido1().trim().equals("")){
					   //o[1]=filtro.getApellido1();
					ape1_=1;
					   s+=" "+rb.getString("estudiante.consulta11");
				}
				if(!filtro.getApellido2().trim().equals("")){
					   //o[1]=filtro.getApellido2();
					ape2_=1;
					   s+=" "+rb.getString("estudiante.consulta12");
				}
				
				switch(Integer.parseInt(filtro.getOrden())){
					case -1:
						s+=" "+rb.getString("estudiante.order1");
					break;
					case 0:
						s+=" "+rb.getString("estudiante.order2");
					break;
					case 1:
						s+=" "+rb.getString("estudiante.order3");
					break;
					case 2:
						s+=" "+rb.getString("estudiante.order4");
					break;
				}		   
			//System.out.println(s);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(s);
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			if(inst_==1){
				//System.out.println("Inst="+login.getInstId());
				ps.setLong(posicion++, Long.parseLong(login.getInstId()));
			}
			if(sede_==1){
				//System.out.println("sede="+filtro.getSede());
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
			}
			if(jor_==1){
				//System.out.println("jor="+filtro.getJornada());
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			}
			if(gra_==1){
				//System.out.println("gra="+filtro.getGrado());
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			}
			if(gru_==1){
				//System.out.println("gru="+filtro.getGrupo());
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			}
			if(met_==1){
				//System.out.println("met="+filtro.getMetodologia());
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			}
			if(id_==1){
				//System.out.println("id="+filtro.getId());
				ps.setString(posicion++, (filtro.getId()));
			}
			if(nom1_==1){
				//System.out.println("nom1="+filtro.getNombre1());
				ps.setString(posicion++, (filtro.getNombre1()));
			}
			if(nom2_==1){
				//System.out.println("nom2="+filtro.getNombre2());
				ps.setString(posicion++, (filtro.getNombre2()));
			}
			if(ape1_==1){
				//System.out.println("ape1="+filtro.getApellido1());
				ps.setString(posicion++, (filtro.getApellido1()));
			}
			if(ape2_==1){
				//System.out.println("ape2="+filtro.getApellido2());
				ps.setString(posicion++, (filtro.getApellido2()));
			}
			rs=ps.executeQuery();
			c=getCollection(rs);
			//System.out.println("Longitud="+c.size());
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException ss){}
			setMensaje("Error intentando obtener resultado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return c;
	}

	public Collection getResultadoGestacion(Login login,FiltroVO filtro){
		String s=null;
		Collection c=null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			int inst_=0;
			int sede_=0;
			int jor_=0;
			int met_=0;
			int gra_=0;
			int gru_=0;
			int id_=0;
			int nom1_=0;
			int nom2_=0;
			int ape1_=0;
			int ape2_=0;
				s=" "+rb.getString("gestacion.consulta1");
				if((filtro.getSede()!=null && !filtro.getSede().equals("-9")) || (filtro.getJornada()!=null && !filtro.getJornada().equals("-9")) || (filtro.getGrado()!=null && !filtro.getGrado().equals("-9")) || (filtro.getGrupo()!=null && !filtro.getGrupo().equals("-9")) || (filtro.getMetodologia()!=null && !filtro.getMetodologia().equals("-9"))){
				   inst_=1;
				   //o[1]=login.getInstId();
				   s+=" "+rb.getString("gestacion.consulta111");					
				   s+=" "+rb.getString("gestacion.consulta2");					
				}
				if(filtro.getSede()!=null && !filtro.getSede().equals("-9")){
				   //o[1]=filtro.getSede();
				   sede_=1;
				   s+=" "+rb.getString("gestacion.consulta3");
				}
				if(filtro.getJornada()!=null && !filtro.getJornada().equals("-9")){
					   //o[1]=filtro.getJornada();
					jor_=1;
					   s+=" "+rb.getString("gestacion.consulta4");
				}
				if(filtro.getGrado()!=null && !filtro.getGrado().equals("-9")){
					   //o[1]=filtro.getGrado();
					gra_=1;
				   s+=" "+rb.getString("gestacion.consulta5");
				}
				if(filtro.getGrupo()!=null && !filtro.getGrupo().equals("-9")){
					   //o[1]=filtro.getGrupo();
					gru_=1;
					   s+=" "+rb.getString("gestacion.consulta6");
				}
				if(filtro.getMetodologia()!=null && !filtro.getMetodologia().equals("-9")){
					   //o[1]=filtro.getMetodologia();
					met_=1;
					   s+=" "+rb.getString("gestacion.consulta61");
				}
				if(!filtro.getId().trim().equals("")){
					   //o[1]=filtro.getId();
					id_=1;
					   s+=" "+rb.getString("gestacion.consulta8");
				}
				if(!filtro.getNombre1().trim().equals("")){
					   //o[1]=filtro.getNombre1();
					nom1_=1;
					   s+=" "+rb.getString("gestacion.consulta9");
				}
				if(!filtro.getNombre2().trim().equals("")){
					   //o[1]=filtro.getNombre2();
					nom2_=1;
					   s+=" "+rb.getString("gestacion.consulta10");
				}
				if(!filtro.getApellido1().trim().equals("")){
					   //o[1]=filtro.getApellido1();
					ape1_=1;
					   s+=" "+rb.getString("gestacion.consulta11");
				}
				if(!filtro.getApellido2().trim().equals("")){
					   //o[1]=filtro.getApellido2();
					ape2_=1;
					   s+=" "+rb.getString("gestacion.consulta12");
				}
				
				switch(Integer.parseInt(filtro.getOrden())){
					case -1:
						s+=" "+rb.getString("gestacion.order1");
					break;
					case 0:
						s+=" "+rb.getString("gestacion.order2");
					break;
					case 1:
						s+=" "+rb.getString("gestacion.order3");
					break;
					case 2:
						s+=" "+rb.getString("gestacion.order4");
					break;
				}		   
			//System.out.println(s);
			cn = cursor.getConnection();
			ps = cn.prepareStatement(s);
			posicion = 1;
			ps.setLong(posicion++, Long.parseLong(filtro.getPeriodo()));
			if(inst_==1){
				//System.out.println("Inst="+login.getInstId());
				ps.setLong(posicion++, Long.parseLong(login.getInstId()));
			}
			if(sede_==1){
				//System.out.println("sede="+filtro.getSede());
				ps.setLong(posicion++, Long.parseLong(filtro.getSede()));
			}
			if(jor_==1){
				//System.out.println("jor="+filtro.getJornada());
				ps.setLong(posicion++, Long.parseLong(filtro.getJornada()));
			}
			if(gra_==1){
				//System.out.println("gra="+filtro.getGrado());
				ps.setLong(posicion++, Long.parseLong(filtro.getGrado()));
			}
			if(gru_==1){
				//System.out.println("gru="+filtro.getGrupo());
				ps.setLong(posicion++, Long.parseLong(filtro.getGrupo()));
			}
			if(met_==1){
				//System.out.println("met="+filtro.getMetodologia());
				ps.setLong(posicion++, Long.parseLong(filtro.getMetodologia()));
			}
			if(id_==1){
				//System.out.println("id="+filtro.getId());
				ps.setString(posicion++, (filtro.getId()));
			}
			if(nom1_==1){
				//System.out.println("nom1="+filtro.getNombre1());
				ps.setString(posicion++, (filtro.getNombre1()));
			}
			if(nom2_==1){
				//System.out.println("nom2="+filtro.getNombre2());
				ps.setString(posicion++, (filtro.getNombre2()));
			}
			if(ape1_==1){
				//System.out.println("ape1="+filtro.getApellido1());
				ps.setString(posicion++, (filtro.getApellido1()));
			}
			if(ape2_==1){
				//System.out.println("ape2="+filtro.getApellido2());
				ps.setString(posicion++, (filtro.getApellido2()));
			}
			rs=ps.executeQuery();
			c=getCollection(rs);
			//System.out.println("Longitud="+c.size());
		}catch(InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return null;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			try{cn.rollback();}catch(SQLException ss){}
			setMensaje("Error intentando obtener resultado. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
			}
			return null;
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte){}
		}
		return c;
	}
	
    public synchronized boolean insertar(NutricionVO vo,Login l,FiltroVO f){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try{
        	long inst=Long.parseLong(l.getInstId());
        	long sede=Long.parseLong(f.getSede());
        	long jor=Long.parseLong(f.getJornada());
        	long met=Long.parseLong(f.getMetodologia());
        	long gra=Long.parseLong(f.getGrado());
        	long gru=Long.parseLong(f.getGrupo());        	
        	long per=Long.parseLong(f.getPeriodo());        	
        	long jerGrupo=-1;
            boolean exists=false;
            boolean delete=false;
            boolean haveData=false;
        	String []estud=vo.getNutEstudiante();
        	String []estatura=vo.getNutEstatura();
        	String []peso=vo.getNutPeso();
        	String []jerarquia=vo.getNutJerarquia();
        	String estudiante_=null;
        	String estatura_=null;
        	String peso_=null;
        	String estatura0_=null;
        	String peso0_=null;
        	if(estud==null){ System.out.println("No se pudo aaaaa");return true; }
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            if(jerarquia!=null && jerarquia[0]!=null){
            	jerGrupo=Long.parseLong(jerarquia[0]);
            }else{
            	setMensaje("No se encontro el grupo seleccionado");
            	return false;
            }
            //obtener lo que esta en la base de datos
            pst = cn.prepareStatement(rb.getString("getNutriciones"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++,jerGrupo);
            pst.setLong(posicion++,per);
            rs=pst.executeQuery();
            String[][] actual=getFiltroMatriz(getCollection(rs));
            rs.close();
            pst.close();
            for(int i=0;i<estud.length;i++){
            	exists=false;
            	delete=false;
            	haveData=false;
            	estudiante_=estud[i];
            	if(estatura!=null) estatura_=estatura[i];
            	else estatura_=null;
            	if(peso!=null) peso_=peso[i];
            	else peso_=null;
				if((estatura_!=null && !estatura_.equals(""))||(peso_!=null && !peso_.equals(""))){
					haveData=true;
				}
            	if(estudiante_!=null && !estudiante_.equals("")){
            		if(actual!=null){
            			for(int j=0;j<actual.length;j++){
            				if(actual[j][0].equals(estudiante_)){
            					exists=true;
            					if((estatura_==null || estatura_.equals(""))&&(peso_==null || peso_.equals(""))){
            						delete=true;
            					}
            				}
            			}
            		}
            		if(exists && delete){
                        pst = cn.prepareStatement(rb.getString("Nutricion.delete"));
                        pst.clearParameters();
                        posicion = 1;
                        pst.setLong(posicion++,Long.parseLong(estudiante_));
                        pst.setLong(posicion++,per);
            			pst.executeUpdate();
            		}else{
            			if(haveData){
	                		if(exists) pst = cn.prepareStatement(rb.getString("Nutricion.update"));
	                		if(!exists) pst = cn.prepareStatement(rb.getString("Nutricion.insert"));
	                        pst.clearParameters();
	                        posicion = 1;
	                        if(peso_==null || peso_.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
	                        else pst.setDouble(posicion++,Double.parseDouble("0"+peso_));
	                        if(estatura_==null || estatura_.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR); 
	                        else pst.setDouble(posicion++,Double.parseDouble("0"+estatura_));
	                        pst.setLong(posicion++,Long.parseLong(estudiante_));
	                        pst.setLong(posicion++,per);
	            			pst.executeUpdate();
            			}	
            		}
            	}
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Nutricinn. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Nutricinn. Posible problema: "+sqle);
            return false;
        }        finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
    public synchronized boolean insertar(GestacionVO vo,Login l,FiltroVO f){
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;
        try{
        	long per=Long.parseLong(f.getPeriodo());        	
        	long jerGrupo=-1;
            boolean exists=false;
            boolean delete=false;
            boolean haveData=false;
        	String []estud=vo.getGesEstudiante();
        	String []gestante=vo.getGesGestante();
        	String []lactante=vo.getGesLactante();
        	String []padre=vo.getGesPadre();
        	String []jerarquia=vo.getGesJerarquia();
        	if(gestante!=null){
        		for(int i=0;i<gestante.length;i++){
        			System.out.println("Ges="+gestante[i]+"//Lac="+lactante[i]+"//Padre="+padre[i]);
        		}
        	}
        	String estudiante_=null;
        	String gestante_=null;
        	String lactante_=null;
        	String padre_=null;
        	if(estud==null){ System.out.println("No se pudo bbb");return true; }
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            if(jerarquia!=null && jerarquia[0]!=null){
            	jerGrupo=Long.parseLong(jerarquia[0]);
            }else{
            	setMensaje("No se encontro el grupo seleccionado");
            	return false;
            }
            //obtener lo que esta en la base de datos
            pst = cn.prepareStatement(rb.getString("Gestacion.getGestaciones"));
            pst.clearParameters();
            posicion = 1;
            pst.setLong(posicion++,jerGrupo);
            pst.setLong(posicion++,per);
            rs=pst.executeQuery();
            String[][] actual=getFiltroMatriz(getCollection(rs));
            rs.close();
            pst.close();
            for(int i=0;i<estud.length;i++){
            	exists=false;
            	delete=false;
            	haveData=false;
            	estudiante_=estud[i];
            	gestante_=gestante[i];
            	lactante_=lactante[i];
            	padre_=padre[i];
				if((!gestante_.equals("-1"))||(!lactante_.equals("-1"))||(!padre_.equals("-1"))){
					haveData=true;
				}
            	if(estudiante_!=null && !estudiante_.equals("")){
            		if(actual!=null){
            			for(int j=0;j<actual.length;j++){
            				if(actual[j][0].equals(estudiante_)){
            					exists=true;
            					if((gestante_.equals("-1"))&&(lactante_.equals("-1"))&&(padre_.equals("-1"))){
            						delete=true;
            					}
            				}
            			}
            		}
            		if(exists && delete){
                        pst = cn.prepareStatement(rb.getString("Gestacion.delete"));
                        pst.clearParameters();
                        posicion = 1;
                        pst.setLong(posicion++,Long.parseLong(estudiante_));
                        pst.setLong(posicion++,per);
            			pst.executeUpdate();
            		}else{
            			if(haveData){
	                		if(exists) pst = cn.prepareStatement(rb.getString("Gestacion.update"));
	                		if(!exists) pst = cn.prepareStatement(rb.getString("Gestacion.insert"));
	                        pst.clearParameters();
	                        posicion = 1;
	                        if(gestante_.equals("-1")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
	                        else pst.setInt(posicion++,Integer.parseInt(gestante_));
	                        if(lactante_.equals("-1")) pst.setNull(posicion++, java.sql.Types.VARCHAR); 
	                        else pst.setInt(posicion++,Integer.parseInt(lactante_));
	                        if(padre_.equals("-1")) pst.setNull(posicion++, java.sql.Types.VARCHAR); 
	                        else pst.setInt(posicion++,Integer.parseInt(padre_));
	                        pst.setLong(posicion++,Long.parseLong(estudiante_));
	                        pst.setLong(posicion++,per);
	            			pst.executeUpdate();
            			}	
            		}
            	}
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch(SQLException sqle){
        	sqle.printStackTrace();
            try{cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Gestacinn. Posible problema: ");
            switch (sqle.getErrorCode()){
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } catch(Exception sqle){
        	sqle.printStackTrace();
            try{if(cn!=null)cn.rollback();}catch(SQLException s){}
            setMensaje("Error intentando ingresar Gestacinn. Posible problema: "+sqle);
            return false;
        }        finally{
            try{
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            }catch(InternalErrorException inte){}
        }
        return true;
    }
}
