package poa.aprobacionActividades.dao;

//	VERSION		FECHA		AUTOR			DESCRIPCION
//		1.0		27/11/2019	JORGE CAMACHO	Se modificó el método getListaActividades para asignar los campos SEGFECHACUMPLIMT y SEGFECHAREALCUMPLIM

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.aprobacionActividades.vo.ColegioPoaVO;
import poa.aprobacionActividades.vo.FiltroPlaneacionVO;
import poa.aprobacionActividades.vo.ParamsVO;
import poa.aprobacionActividades.vo.PlaneacionVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el mndulo de aprobacinn del POA. 
 * 14/01/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class PlaneacionDAO extends Dao{

	/**
	 * Constructor
	 *  
	 * @param c Objeto de obtencinn de conexiones a la base de datos
	 */
	public PlaneacionDAO(Cursor c){
		super(c);
		rb=ResourceBundle.getBundle("poa.aprobacionActividades.bundle.Aprobacion");
	}

public List getListaFuenteFinanciacion() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		
		l.add(new String[]{"Recursos Propios","recursos propios"});
		l.add(new String[]{"Transferencias DEE 2012","transferencias DEE 2012"});
		l.add(new String[]{"Transferencias DEE 2013","transferencias DEE 2013"});
		l.add(new String[]{"Otras Transferencias SED","otras transferencias sed"});
		l.add(new String[]{"Recursos Alcaldna Local","recursos alcaldia local"});
		l.add(new String[]{"Recursos Privados","recursos privados"});
		l.add(new String[]{"Otros Recursos","otros recursos"});
		l.add(new String[]{"Sin Recursos","sin recursos"});
	
		return l;
	}
	
public List getListaAccionMejoramiento() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 

		l.add(new String[]{"Uso Eficiente del Agua","uso eficiente del agua"});
		l.add(new String[]{"Uso Eficiente de la Energia","uso eficiente de la energia"});
		l.add(new String[]{"Gestion Integral de Residuos","gestion integral de residuos"});
		l.add(new String[]{"Mejoramiento de las Condiciones Ambientales Internas","mejoramiento de las condiciones ambientales internas"});
		l.add(new String[]{"Criterios Ambientales Para las Compras y Gestion Contractual","criterios ambientales para las compras y gestion contractual"});
		l.add(new String[]{"Extension de Buenas Practicas Ambientales","extension de buenas practicas ambientales"});
		l.add(new String[]{"Otras","otras"});
		
		return l;
	}
	
	public List getListaRubroGasto(String tipoRubro) throws Exception {
	
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		
		if(tipoRubro == null){
			return l;
		}
		else if (tipoRubro.equalsIgnoreCase("Inversion")){
			l.add(new String[]{"Vitrina Pedagogica","vitrina pedagogica"});
			l.add(new String[]{"Escuela Ciudad Escuela","escuela ciudad escuela"});
			l.add(new String[]{"Medio Ambiente y Prevencion de Desastres","medio ambiente y prevencion de desastres"});
			l.add(new String[]{"Aprovechamiento del Tiempo Libre","aprovechamiento del tiempo libre"});
			l.add(new String[]{"Educacion Sexual","educacion sexual"});
			l.add(new String[]{"Compra Equipos Beneficio de los Estudiantes","compra equipos beneficio de los estudiantes"});
			l.add(new String[]{"Formacion de Valores","formacion de valores"});
			l.add(new String[]{"Formacion Tecnica y Para el Trabajo","formacion tecnica y para el trabajo"});
			l.add(new String[]{"Fomento de la Cultura","fomento de la cultura"});
			l.add(new String[]{"Otros Proyectos","otros proyectos"});
			l.add(new String[]{"Investigacinn y Estudios","Investigacinn y Estudios"});
			
		}else if(tipoRubro.equalsIgnoreCase("Funcionamiento")){
			l.add(new String[]{"Servicios Personales","servicios personales"});
			l.add(new String[]{"Gastos Generales","gastos generales"});
			l.add(new String[]{"Pasivos Exigibles Inversinn","Pasivos Exigibles Inversinn"});
		}
		
		
		return l;
	}

	/**
	 * Calcula la lista de Localidades  
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaLocalidad() throws Exception{ //Esta es la que llaman
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaLocalidad"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	/**
	 * Calcula la lista de Ã¡reas de gestinn
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaAreaGestion"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Calcula la lista de fuentes de financiacinn
	 * @return Lista de fuentes de financiacinn
	 * @throws Exception
	 */
	public List getListaFuenteFinanciera() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaFuenteFinanciera"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Calcula la lista de fuentes de financiacinn
	 * @return Lista de fuentes de financiacinn
	 * @throws Exception
	 */
	public List getListaFuenteFinancieraSin() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaFuenteFinancieraSin"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	/**
	 * Calcula la lista de lineas de accinn  
	 * @param areaGestion codigo del area de gestinn
	 * @return Lista de lineas de accinn
	 * @throws Exception
	 */
	public List getListaLineaAccion(int areaGestion) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaLineaAccion"));
			st.setInt(i++,areaGestion);
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Calcula la lista de tipo de meta
	 * @return	Lista de tipos de meta
	 * @throws Exception
	 */
	public List getListaTipoMeta() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaTipoMeta"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
	
	/**
	 * Calcula la lista de unidades de medida  
	 * @return	Lista de unidades de medida
	 * @throws Exception
	 */
	public List getListaUnidadMedida() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaUnidadMedida"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Calcula la lista de actividades con recursos
	 * @param filtro Filtro de bnsqueda  
	 * @return Lista de actividades 
	 * @throws Exception
	 */
	public List getListaActividades(FiltroPlaneacionVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		PlaneacionVO item=null;
		int i=0;
		int cons=1;
		try{
			cn=cursor.getConnection();
			//calcular el nombre del colegio
			st=cn.prepareStatement(rb.getString("poa.getNombreColegio"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilNombreColegio(rs.getString(1));
			}
			rs.close();
			st.close();
			/*
			//saber si se puede aprobar
			st=cn.prepareStatement(rb.getString("poa.getEstado"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilEstatoPOA(rs.getInt(1));
				if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_COLEGIO || filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_SED){
					if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_COLEGIO){
						filtro.setFilHabilitado(1);
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					}else{
						filtro.setFilHabilitado(0);
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					}
				}else{
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
					filtro.setFilHabilitado(0);
				}
			}else{
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
				filtro.setFilHabilitado(0);
			}
			rs.close();
			st.close();
			*/
			//calculo de la lista
			st=cn.prepareStatement(rb.getString("planeacion.lista2"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,filtro.getFilVigencia());
			st.setInt(i++,filtro.getFilAreaGestion());
			st.setInt(i++,filtro.getFilAreaGestion());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new PlaneacionVO();
				item.setPlaConsecutivo(cons++);
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestionNombre(rs.getString(i++));
				item.setPlaLineaAccionNombre(rs.getString(i++));
				item.setPlaPonderador(rs.getFloat(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				item.setSEGFECHAREALCUMPLIM(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Obtiene la actividad con recursos  
	 * @param vigencia anho de vigencia
	 * @param institucion codigo de la institucinn
	 * @param codigo codigo de la actividad
	 * @param estadoPOA codigo del estado del POA del colegios
	 * @return Actividad que se solicitn
	 * @throws Exception
	 */
	public PlaneacionVO getActividad(int vigencia, long institucion,long codigo,int estadoPOA) throws Exception{
		//si lo obtiene pero esta borrando la busqueda del filtro y ademas los checkes estan descuadrados
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		PlaneacionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.obtener2"));
			i=1;
			st.setInt(i++,vigencia);
			st.setLong(i++,institucion);
			st.setLong(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new PlaneacionVO();
				item.setFormaEstado("1");
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaOrden(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestion(rs.getInt(i++));
				item.setPlaPonderador(rs.getFloat(i++));
				item.setPlaLineaAccion(rs.getInt(i++));
				item.setPlaTipoMeta(rs.getInt(i++));
				item.setPlaMetaAnualCantidad(rs.getLong(i++));
				item.setPlaMetaAnualUnidad(rs.getInt(i++));
				item.setPlaMetaAnualCual(rs.getString(i++));
				item.setPlaPresupuesto(rs.getLong(i++));
				item.setPlaResponsable(rs.getString(i++));
				item.setPlaFecha(rs.getString(i++));
				item.setPlaCronograma1(rs.getString(i++));
				item.setPlaCronograma2(rs.getString(i++));
				item.setPlaCronograma3(rs.getString(i++));
				item.setPlaCronograma4(rs.getString(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				item.setTipoActividad(rs.getString(i++));
				/* PIMA - PIGA */
				item.setAccionMejoramiento(rs.getString(i++));
				item.setAccionMejoramientoOtras(rs.getString(i++));
				item.setTipoGasto(rs.getString(i++));
				item.setRubroGasto(rs.getString(i++));
				
				if(item.getTipoGasto() != null){
					if(item.getTipoGasto().equalsIgnoreCase("Inversion")){
						item.setProyectoInversion(rs.getString(i++));
					}else if(item.getTipoGasto().equalsIgnoreCase("Funcionamiento")){
						item.setSubnivelGasto(rs.getString(i++));
					}
				}
				else{
					i++;
				}
				item.setFuenteFinanciacion(rs.getString(i++));
				item.setFuenteFinanciacionOtros(rs.getString(i++));
				item.setMontoAnual(new Long(rs.getLong(i++)));
				item.setPresupuestoParticipativo(new Long(rs.getLong(i++)));
				item.setTipoActividad(rs.getString(i++));
				item.setSEGFECHACUMPLIMT(rs.getString(i++));
				
				item.setPlaDisabled("disabled");
				item.setPlaDesHabilitado(true);
			}
			rs.close();
			st.close();
			//obtener las fuentes de financiacion
			if(item!=null){
				List l=new ArrayList();
				st=cn.prepareStatement(rb.getString("planeacion.obtenerFuente"));
				i=1;
				st.setInt(i++,vigencia);
				st.setLong(i++,institucion);
				st.setLong(i++,codigo);
				rs=st.executeQuery();
				while (rs.next()){
					l.add(rs.getString(1));
				}
				int n[]=new int[l.size()];
				for(int j=0;j<l.size();j++){
					n[j]=Integer.parseInt((String)l.get(j));
				}
				item.setPlaFuenteFinanciera(n);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	/**
	 * Calcula la lista de vigencias desde la anterior a la posterior a la actual  
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual()throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ItemVO itemVO=null;
		List listaVigencia=new ArrayList();
		try{
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("planeacion.listaVigencia"));
			rs=pst.executeQuery();
			if(rs.next()){
				itemVO=new ItemVO(rs.getInt(1),String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(2),String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO=new ItemVO(rs.getInt(3),String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return listaVigencia;
	}


	/**
	 * Calcula los comentarios de las tablas relacionadas con actividades con recursos  
	 * @param filtro Filtro de bnsqueda de actividades 
	 * @return Filtro de bnsqueda de actividades
	 * @throws Exception
	 */
	public FiltroPlaneacionVO getLabels(FiltroPlaneacionVO filtro)throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		if(filtro==null) filtro=new FiltroPlaneacionVO();
		try{
			filtro.setFilVigencia((int)getVigenciaNumericoPOA());
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("planeacion.label"));
			rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)){ filtro.setLblVigencia(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)){ filtro.setLblObjetivo(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)){ filtro.setLblActividad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)){ filtro.setLblAreaGestion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPONDERADO)){ filtro.setLblPonderador(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)){ filtro.setLblLineaAccion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODTIMETA)){ filtro.setLblTipoMeta(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCANTIDAD)){ filtro.setLblMetaAnualCantidad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODUNMEDIDA)){ filtro.setLblMetaAnualUnidad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACOTROCUAL)){ filtro.setLblMetaAnualCual(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPRESUPUESTO)){ filtro.setLblPresupuesto(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRERESPON)){ filtro.setLblResponsable(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACFECHATERMIN)){ filtro.setLblFecha(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO1)){ filtro.setLblCronograma1(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO2)){ filtro.setLblCronograma2(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO3)){ filtro.setLblCronograma3(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO4)){ filtro.setLblCronograma4(rs.getString(2));continue;}
			}
			rs.close();
			pst.close();
			pst=cn.prepareStatement(rb.getString("planeacion.labelFuente"));
			rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLFTCODFUFINANC)){ filtro.setLblFuenteFinanciera(rs.getString(2));continue;}
			}
			rs.close();
			pst.close();
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
			OperacionesGenerales.closeResultSet(rs);
			OperacionesGenerales.closeStatement(pst);
			OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return filtro;
	}

	/**
	 * Aprueba el POA a nivel de secretaria  
	 * @param filtro Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public void aprobarSed(FiltroPlaneacionVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		int codigo=0;
		try{
			cn=cursor.getConnection();
			//validacion de Estado
			st=cn.prepareStatement(rb.getString("poa.getEstado"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				int estado=rs.getInt(1);
				if(estado==ParamPOA.ESTADO_POA_APROBADO_SED || estado==ParamPOA.ESTADO_POA_RECHAZADO_SED){
					throw new Exception("Ya fue aprobado o rechazado a nivel de secretarna de educacinn");
				}	
			}else{
				throw new Exception("No ha sido aprobado por el colegio");
			}
			rs.close();
			st.close();
			//calculo del codigo
			st=cn.prepareStatement(rb.getString("poa.codigo"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				codigo=(rs.getInt(1));
			}
			rs.close();
			st.close();
			//ingresar
			st=cn.prepareStatement(rb.getString("poa.ingresar"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,codigo);
			st.setInt(i++,ParamPOA.ESTADO_POA_APROBADO_SED);
			st.setInt(i++,ParamPOA.ETAPA_POA_PLANEACION);
			st.setNull(i++,Types.VARCHAR);
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	/**
	 * Calcula la lista de actividades sin recursos  
	 * @param filtro Filtro de busqueda de actividades
	 * @return Lista de actividades sin recursos
	 * @throws Exception
	 */
	public List getListaActividadesSin(FiltroPlaneacionVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		PlaneacionVO item=null;
		int i=0;
		int cons=1;
		try{
			cn=cursor.getConnection();
			//calcular el nombre del colegio
			st=cn.prepareStatement(rb.getString("poa.getNombreColegio"));
			i=1;
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilNombreColegio(rs.getString(1));
			}
			rs.close();
			st.close();
			/*
			//saber si se puede aprobar
			st=cn.prepareStatement(rb.getString("poa.getEstado"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				filtro.setFilEstatoPOA(rs.getInt(1));
				if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_COLEGIO || filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_SED){
					if(filtro.getFilEstatoPOA()==ParamPOA.ESTADO_POA_APROBADO_COLEGIO){
						filtro.setFilHabilitado(1);
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					}else{
						filtro.setFilHabilitado(0);
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					}
				}else{
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
					filtro.setFilHabilitado(0);
				}
			}else{
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
				filtro.setFilHabilitado(0);
			}
			rs.close();
			st.close();
			*/
			//calculo de la lista
				st=cn.prepareStatement(rb.getString("planeacionSin.lista2"));
				i=1;
				st.setLong(i++,filtro.getFilInstitucion());
				st.setInt(i++,filtro.getFilVigencia());
				st.setInt(i++,filtro.getFilAreaGestion());
				st.setInt(i++,filtro.getFilAreaGestion());
				rs=st.executeQuery();
				while(rs.next()){
					i=1;
					item=new PlaneacionVO();
					item.setPlaConsecutivo(cons++);
					item.setPlaVigencia(rs.getInt(i++));
					item.setPlaInstitucion(rs.getLong(i++));
					item.setPlaCodigo(rs.getLong(i++));
					item.setPlaObjetivo(rs.getString(i++));
					item.setPlaActividad(rs.getString(i++));
					item.setPlaAreaGestionNombre(rs.getString(i++));
					item.setPlaLineaAccionNombre(rs.getString(i++));
					item.setPlaccodobjetivo(rs.getInt(i++));
					item.setPlaccodobjetivoText(rs.getString(i++));
					
					l.add(item);
				}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 * Obtiene la actividad sin recursos
	 * @param vigencia Anho de vigencia
	 * @param institucion codigo de la institucinn
	 * @param codigo codigo de la actividad
	 * @param estadoPOA codigo del estado del POA 
	 * @return Actividad sin recursos solicitada
	 * @throws Exception
	 */
	public PlaneacionVO getActividadSin(int vigencia, long institucion,long codigo,int estadoPOA) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		PlaneacionVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacionSin.obtener2"));
			i=1;
			st.setInt(i++,vigencia);
			st.setLong(i++,institucion);
			st.setLong(i++,codigo);
			rs=st.executeQuery();
			if(rs.next()){
				i=1;
				item=new PlaneacionVO();
				item.setFormaEstado("1");
				item.setPlaVigencia(rs.getInt(i++));
				item.setPlaInstitucion(rs.getLong(i++));
				item.setPlaCodigo(rs.getLong(i++));
				item.setPlaOrden(rs.getLong(i++));
				item.setPlaObjetivo(rs.getString(i++));
				item.setPlaActividad(rs.getString(i++));
				item.setPlaAreaGestion(rs.getInt(i++));
				item.setPlaLineaAccion(rs.getInt(i++));
				item.setPlaMetaAnualCantidad(rs.getLong(i++));
				item.setPlaMetaAnualUnidad(rs.getInt(i++));
				item.setPlaMetaAnualCual(rs.getString(i++));
				item.setPlaPresupuesto(rs.getLong(i++));
				item.setPlaccodobjetivo(rs.getInt(i++));
				item.setPlaccodobjetivoText(rs.getString(i++));
				item.setPlaDisabled("disabled");
				item.setPlaDesHabilitado(true);
			}
			rs.close();
			st.close();
			//obtener las fuentes de financiacion
			if(item!=null){
				List l=new ArrayList();
				st=cn.prepareStatement(rb.getString("planeacionSin.obtenerFuente"));
				i=1;
				st.setInt(i++,vigencia);
				st.setLong(i++,institucion);
				st.setLong(i++,codigo);
				rs=st.executeQuery();
				while (rs.next()){
					l.add(rs.getString(1));
				}
				int n[]=new int[l.size()];
				for(int j=0;j<l.size();j++){
					n[j]=Integer.parseInt((String)l.get(j));
				}
				item.setPlaFuenteFinanciera(n);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return item;
	}

	/**
	 * Calcula los comentarios de las tablas relacionadas con actividades sin recursos
	 * @param filtro Filtro de bnsqueda de actividades
	 * @return Filtro de bnsqueda de actividades
	 * @throws Exception
	 */
	public FiltroPlaneacionVO getLabelsSin(FiltroPlaneacionVO filtro)throws Exception{
		Connection cn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		if(filtro==null)filtro=new FiltroPlaneacionVO();
		try{
			filtro.setFilVigencia((int)getVigenciaNumericoPOA());
			cn=cursor.getConnection();
			pst=cn.prepareStatement(rb.getString("planeacionSin.label"));
			rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)){ filtro.setLblVigencia(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)){ filtro.setLblObjetivo(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)){ filtro.setLblActividad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)){ filtro.setLblAreaGestion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)){ filtro.setLblLineaAccion(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCANTIDAD)){ filtro.setLblMetaAnualCantidad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACCODUNMEDIDA)){ filtro.setLblMetaAnualUnidad(rs.getString(2));continue;}
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLACOTROCUAL)){ filtro.setLblMetaAnualCual(rs.getString(2));continue;}
			}
			rs.close();
			pst.close();
			pst=cn.prepareStatement(rb.getString("planeacionSin.labelFuente"));
			rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).equals(ParamsVO.CAMPO_PLFTCODFUFINANC)){ filtro.setLblFuenteFinanciera(rs.getString(2));continue;}
			}
			rs.close();
			pst.close();
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return filtro;
	}

	/**
	 * Calcula la lista de colegios de la localidad
	 * @param loc Codigo de la localidad
	 * @return Lista de colegios de la localidad indicada
	 * @throws Exception
	 */
	public List getListaColegio(FiltroPlaneacionVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ColegioPoaVO item=null;
		int i=1;
		int cons=1;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.listaColegio"));
			st.setInt(i++,filtro.getFilLocalidad());
			st.setInt(i++,filtro.getFilVigencia());
			st.setInt(i++,filtro.getFilEstatoPOA());
			st.setInt(i++,filtro.getFilEstatoPOA());
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ColegioPoaVO();
				item.setColConsecutivo(cons++);
				item.setColVigencia(filtro.getFilVigencia());
				item.setColColegio(rs.getLong(i++));
				item.setColNombre(rs.getString(i++));
				item.setColCodigo(rs.getInt(i++));
				item.setColEstado(rs.getInt(i++));
				item.setColNombreEstado(getNombreEstado(item.getColEstado()));
				item.setColFechaEstado(rs.getString(i++));
				item.setColObservacion(rs.getString(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}

	/**
	 *  Rechaza el POA por parte de la secretaria  
	 * @param filtro Filtro de bnsqueda de actividades
	 * @throws Exception
	 */
	public void rechazarSed(FiltroPlaneacionVO filtro) throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int i=0;
		int codigo=0;
		try{
			cn=cursor.getConnection();
			//validacion de Estado
			st=cn.prepareStatement(rb.getString("poa.getEstado"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				int estado=rs.getInt(1);
				if(estado==ParamPOA.ESTADO_POA_APROBADO_SED || estado==ParamPOA.ESTADO_POA_RECHAZADO_SED){
					throw new Exception("Ya fue aprobado o rechazado a nivel de secretarna de educacinn");
				}	
			}else{
				throw new Exception("No ha sido aprobado por el colegio");
			}
			rs.close();
			st.close();
			//calculo del codigo
			st=cn.prepareStatement(rb.getString("poa.codigo"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			rs=st.executeQuery();
			if(rs.next()){
				codigo=(rs.getInt(1));
			}
			rs.close();
			st.close();
			//ingresar
			st=cn.prepareStatement(rb.getString("poa.ingresar"));
			i=1;
			st.setInt(i++,filtro.getFilVigencia());
			st.setLong(i++,filtro.getFilInstitucion());
			st.setInt(i++,codigo);
			st.setInt(i++,ParamPOA.ESTADO_POA_RECHAZADO_SED);
			st.setInt(i++,ParamPOA.ETAPA_POA_PLANEACION);
			st.setString(i++,filtro.getFilMotivo());
			st.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
	}

	/**
	 * Calcula la lista de estados de poa
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public List getListaEstado() throws Exception{
		List l=new ArrayList();
		l.add(new ItemVO(ParamPOA.ESTADO_POA_APROBADO_COLEGIO,ParamPOA.ESTADO_POA_APROBADO_COLEGIO_));
		l.add(new ItemVO(ParamPOA.ESTADO_POA_APROBADO_SED,ParamPOA.ESTADO_POA_APROBADO_SED_));
		l.add(new ItemVO(ParamPOA.ESTADO_POA_RECHAZADO_SED,ParamPOA.ESTADO_POA_RECHAZADO_SED_));
		return l;
	}

	/**
	 * Calcula la lista de estados de poa
	 * @return Lista de localidades
	 * @throws Exception
	 */
	public String getNombreEstado(int estado) throws Exception{
		switch(estado){
			case ParamPOA.ESTADO_POA_APROBADO_COLEGIO: return ParamPOA.ESTADO_POA_APROBADO_COLEGIO_; 
			case ParamPOA.ESTADO_POA_APROBADO_SED: return ParamPOA.ESTADO_POA_APROBADO_SED_;
			case ParamPOA.ESTADO_POA_RECHAZADO_SED: return ParamPOA.ESTADO_POA_RECHAZADO_SED_; 
		}
		return null;
	}
	

	
	public List getlistaObjetivos() throws Exception{
		Connection cn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List l=new ArrayList();
		ItemVO item=null;
		int i=0;
		try{
			cn=cursor.getConnection();
			st=cn.prepareStatement(rb.getString("planeacion.getlistaObjetivos"));
			rs=st.executeQuery();
			while(rs.next()){
				i=1;
				item=new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				//item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());
		}catch(Exception sqle){
			sqle.printStackTrace();
			throw new Exception("Error interno: "+sqle.getMessage());
		}finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}
		}
		return l;
	}
}

