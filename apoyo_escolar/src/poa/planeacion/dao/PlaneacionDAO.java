package poa.planeacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import poa.common.vo.ParamPOA;
import poa.planeacion.vo.FiltroPlaneacionVO;
import poa.planeacion.vo.ParamsVO;
import poa.planeacion.vo.PlaneacionVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;

/**
 * Objeto de acceso a datos para el modulo de actividadese 14/01/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class PlaneacionDAO extends Dao {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            Objeto para la obtencinn de conexiones
	 */
	public PlaneacionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("poa.planeacion.bundle.Planeacion");
	}
	
	public List getListaFuenteFinanciacion() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		/*
		Recursos Propios
		Transferencias DEE 2012
		Transferencias DEE 2013
		Otras Transferencias SED
		Recursos Alcaldna Local
		Recursos Privados
		Otros Recursos
		Sin Recursos
		*/
		l.add(new String[]{"Recursos Propios","recursos propios"});
		l.add(new String[]{"Transferencias DEE 2012","transferencias DEE 2012"});
		l.add(new String[]{"Transferencias DEE 2013","transferencias DEE 2013"});
		l.add(new String[]{"Otras Transferencias SED","otras transferencias sed"});
		l.add(new String[]{"Recursos Alcaldna Local","recursos alcaldia local"});
		l.add(new String[]{"Recursos Privados","recursos privados"});
		l.add(new String[]{"Otros Recursos","otros recursos"});
		l.add(new String[]{"Sin Recursos","sin recursos"});
		/*int i = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}*/
		return l;
	}
	
public List getListaAccionMejoramiento() throws Exception {
		
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		/*
		Recursos Propios
		Transferencias DEE 2012
		Transferencias DEE 2013
		Otras Transferencias SED
		Recursos Alcaldna Local
		Recursos Privados
		Otros Recursos
		Sin Recursos
		*/
/*		USO EFICIENTE DEL AGUA 
		USO EFICIENTE DE LA ENERGIA
		GESTION INTEGRAL DE RESIDUOS
		MEJORAMIENTO DE LAS CONDICIONES AMBIENTALES INTERNAS
		CRnTERIOS AMBIENTALES PARA LAS COMPRAS Y GESTInN CONTRACTUAL 
		EXTENSInN DE BUENAS PRnCTICAS AMBIENTALES 
		OTRAS 
*/
		l.add(new String[]{"Uso Eficiente del Agua","uso eficiente del agua"});
		l.add(new String[]{"Uso Eficiente de la Energia","uso eficiente de la energia"});
		l.add(new String[]{"Gestion Integral de Residuos","gestion integral de residuos"});
		l.add(new String[]{"Mejoramiento de las Condiciones Ambientales Internas","mejoramiento de las condiciones ambientales internas"});
		l.add(new String[]{"Criterios Ambientales Para las Compras y Gestion Contractual","criterios ambientales para las compras y gestion contractual"});
		l.add(new String[]{"Extension de Buenas Practicas Ambientales","extension de buenas practicas ambientales"});
		l.add(new String[]{"Otras","otras"});
		/*int i = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}*/
		return l;
	}
	
	public List getListaRubroGasto(String tipoRubro) throws Exception {
	
		List l = new ArrayList();
		String itemLista[] = new String[2]; 
		/*
		<c:if test="${sessionScope.planeacionVO.tipoGasto eq 'Inversion'}">
			<option value="" vitrina pedagogica'}">selected</c:if>></option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq'escuela ciudad escuela'}">selected</c:if>></option>
			</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'aprovechamiento del tiempo libre'}">selected</c:if>></option>
			
			
			
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'formacion de valores'}">selected</c:if>>Formacion de Valores</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'formacion tecnica y para el trabajo'}">selected</c:if>>Formacion Tecnica y Para el Trabajo</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'fomento de la cultura'}">selected</c:if>>Fomento de la Cultura</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'investigacion y estudios'}">selected</c:if>>Investigacion y Estudios</option>
			<option value="otros proyectos" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'investigacion y estudios'}">selected</c:if>>Otros Proyectos</option>
			<option value="Inversinn y Estudios" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'Inversinn y Estudios'}">selected</c:if>>Inversinn y Estudios</option>
		</c:if>
		<c:if test="${sessionScope.planeacionVO.tipoGasto eq 'Funcionamiento'}">
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'servicios personales'}">selected</c:if>>Servicios Personales</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'gastos generales'}">selected</c:if>>Gastos Generales</option>
			<option value="" <c:if test="${sessionScope.planeacionVO.rubroGasto eq 'pasivos exigibles'}">selected</c:if>></option>
		</c:if>
		 */
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
		
		/*int i = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}*/
		return l;
	}

	/**
	 * Calcula la lista de areas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de fuentes de financiacinn
	 * 
	 * @return Lista de fuentes de financiacinn
	 * @throws Exception
	 */
	public List getListaFuenteFinanciera() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaFuenteFinanciera"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de fuentes de financiacinn
	 * 
	 * @return Lista de fuentes de financiacinn
	 * @throws Exception
	 */
	public List getListaFuenteFinancieraSin() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaFuenteFinancieraSin"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de lineas de accinn de un area de gestinn
	 * 
	 * @param areaGestion
	 *            codigo del area de gestinn
	 * @return Lista de lineas de accinn
	 * @throws Exception
	 */
	public List getListaLineaAccion(int areaGestion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaLineaAccion"));
			st.setInt(i++, areaGestion);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de tipos de meta
	 * 
	 * @return Lista de tipos de meta
	 * @throws Exception
	 */
	public List getListaTipoMeta() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.listaTipoMeta"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Calcula la lista de unidades de medida
	 * 
	 * @return lista de unidades de medida
	 * @throws Exception
	 */
	public List getListaUnidadMedida() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaUnidadMedida"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public List getListaActividades(FiltroPlaneacionVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		PlaneacionVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// saber si se puede aprobar
			st = cn.prepareStatement(rb.getString("poa.getEstado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
				filtro.setFilObservacion(rs.getString(2));
				filtro.setFilObservacionLinea(rs.getString(3));
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					filtro.setFilHabilitado(1);
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
				} else {
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_SED)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					filtro.setFilHabilitado(0);
				}
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilHabilitado(1);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
			}
			rs.close();
			st.close();
			// calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacion.lista2"));
			// System.out.println("FILTRO LISTA PLANEACION AREA GESTION: "+filtro.getFilAreaGestion());
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			st.setInt(i++, filtro.getFilAreaGestion());
			st.setInt(i++, filtro.getFilAreaGestion());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
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
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Obtiene una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @param estadoPOA
	 *            estado de POA
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO getActividad(int vigencia, long institucion,
			long codigo, int estadoPOA, boolean habil) throws Exception {
		// si lo obtiene pero esta borrando la busqueda del filtro y ademas los
		// checkes estan descuadrados
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlaneacionVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacion.obtener2"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
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
				
				//PIMA
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
				
				
				
				//**************************
				
				if (estadoPOA == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| estadoPOA == ParamPOA.ESTADO_POA_APROBADO_SED
						|| !habil) {
					item.setPlaDisabled("disabled");
					item.setPlaDesHabilitado(true);
				}
			}
			rs.close();
			st.close();
			// obtener las fuentes de financiacion
			if (item != null) {
				List l = new ArrayList();
				st = cn.prepareStatement(rb
						.getString("planeacion.obtenerFuente"));
				i = 1;
				st.setInt(i++, vigencia);
				st.setLong(i++, institucion);
				st.setLong(i++, codigo);
				rs = st.executeQuery();
				while (rs.next()) {
					l.add(rs.getString(1));
				}
				int n[] = new int[l.size()];
				for (int j = 0; j < l.size(); j++) {
					n[j] = Integer.parseInt((String) l.get(j));
				}
				item.setPlaFuenteFinanciera(n);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Ingresa una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO ingresarActividad(PlaneacionVO item) throws Exception {
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;
		
		int i = 0;
		
		try {
			
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			
			// Validar el estado de POA
			st = cn.prepareStatement(rb.getString("planeacion.validarEstado"));
			
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				if (rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_COLEGIO || rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_SED) {
					
					throw new Exception("El POA ya ha sido aprobado y no se puede registrar más actividades");
					
				}
				
			}
			
			rs.close();
			st.close();
			
			// Validar que el ponderado no se pase de 100%
			st = cn.prepareStatement(rb.getString("poa.validarPonderado"));
			
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				if (rs.getInt(1) + item.getPlaPonderador() > 100) {
					
					throw new Exception("El total ponderado supera el 100% permitido por colegio y vigencia");
				}
				
			}
			
			rs.close();
			st.close();
			
			// Se consulta el máxino ID
			st = cn.prepareStatement(rb.getString("planeacion.codigo"));
			
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				item.setPlaCodigo(rs.getLong(1));
				
			}
			
			rs.close();
			st.close();
			
			// Se ingresa la información
			st = cn.prepareStatement(rb.getString("planeacion.ingresar"));
			
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setFloat(i++, item.getPlaPonderador());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setInt(i++, item.getPlaTipoMeta());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			st.setString(i++, item.getPlaMetaAnualCual());
			st.setLong(i++, item.getPlaPresupuesto());
			st.setString(i++, item.getPlaResponsable());
			st.setString(i++, item.getPlaFecha());
			st.setString(i++, item.getPlaCronograma1());
			st.setString(i++, item.getPlaCronograma2());
			st.setString(i++, item.getPlaCronograma3());
			st.setString(i++, item.getPlaCronograma4());
			st.setInt(i++, item.getPlaccodobjetivo());
			
			// PIMA 
			st.setString(i++, item.getAccionMejoramiento());
			
			if(item.getAccionMejoramiento() == null) {
				st.setString(i++, "");
			} else {
				st.setString(i++, item.getAccionMejoramientoOtras());
			}
			
			st.setString(i++, item.getTipoGasto());
			st.setString(i++, item.getRubroGasto());
			
			if(item.getTipoGasto() != null) {
				if(item.getTipoGasto().equalsIgnoreCase("Inversion")) {
					st.setString(i++, item.getProyectoInversion());
				} else if(item.getTipoGasto().equalsIgnoreCase("Funcionamiento")) {
					st.setString(i++, item.getSubnivelGasto());
				} else {
					st.setString(i++, "");
				}
			} else {
				st.setString(i++, "");
			}
						
			st.setString(i++, item.getFuenteFinanciacion());
			
			if(item.getFuenteFinanciacion() != null && item.getFuenteFinanciacion().equalsIgnoreCase("otros recursos")) {
				st.setString(i++, item.getFuenteFinanciacionOtros());
			} else {
				st.setNull(i++,Types.VARCHAR);
			}
			
			if(item.getTipoActividad().equalsIgnoreCase("PIMA - PIGA")) {
				st.setLong(i++, item.getMontoAnual().longValue());
				if(item.getPresupuestoParticipativo() != null && !item.getPresupuestoParticipativo().equals("")) {
					st.setLong(i++, item.getPresupuestoParticipativo().longValue());
				} else {
					st.setLong(i++,0);
				}
			} else {
				st.setLong(i++,0);
				st.setLong(i++,0);
			}
			
			st.setString(i++, item.getTipoActividad());
			st.setString(i++, item.getSEGFECHACUMPLIMT());
			st.setString(i++,item.getPlaIdUsuario());
			
			//****
			st.executeUpdate();
			
			// ingresar fuentes de financiacion
			ingresarFuentes(cn, item);
			
			cn.commit();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		
		return item;
		
	}
	

	/**
	 * Ingresa las fuentes de financiacinn de una actividad con recursos
	 * 
	 * @param cn
	 *            Conexinn
	 * @param item
	 *            Actividad
	 * @throws Exception
	 */
	public void ingresarFuentes(Connection cn, PlaneacionVO item)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			int[] fuentes = item.getPlaFuenteFinanciera();
			if (fuentes == null)
				return;
			st = cn.prepareStatement(rb.getString("planeacion.eliminarFuente"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.executeUpdate();
			st.close();
			//
			st = cn.prepareStatement(rb.getString("planeacion.ingresarFuente"));
			st.clearBatch();
			for (int j = 0; j < fuentes.length; j++) {
				i = 1;
				st.setInt(i++, item.getPlaVigencia());
				st.setLong(i++, item.getPlaInstitucion());
				st.setLong(i++, item.getPlaCodigo());
				st.setInt(i++, fuentes[j]);
				st.addBatch();
			}
			st.executeBatch();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Actualiza una actividad con recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO actualizarActividad(PlaneacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar que el ponderado no se pase de 100%
			st = cn.prepareStatement(rb
					.getString("planeacion.validarPonderadoActualizacion"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) + item.getPlaPonderador() > 100) {
					throw new Exception(
							"El total ponderado supera el 100% permitido por colegio y vigencia");
				}
			}
			rs.close();
			st.close();
			// actualizar
			st = cn.prepareStatement(rb.getString("planeacion.actualizar"));
			i = 1;
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setFloat(i++, item.getPlaPonderador());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setInt(i++, item.getPlaTipoMeta());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			st.setString(i++, item.getPlaMetaAnualCual());
			st.setLong(i++, item.getPlaPresupuesto());
			st.setString(i++, item.getPlaResponsable());
			st.setString(i++, item.getPlaFecha());
			st.setString(i++, item.getPlaCronograma1());
			st.setString(i++, item.getPlaCronograma2());
			st.setString(i++, item.getPlaCronograma3());
			st.setString(i++, item.getPlaCronograma4());
			st.setInt(i++, item.getPlaccodobjetivo());//18
			
			// PIMA - PIGA
			if(item.getTipoActividad().equalsIgnoreCase("PIMA - PIGA")){

				st.setString(i++, item.getAccionMejoramiento());

				if(item.getAccionMejoramiento() != null){
					if(item.getAccionMejoramiento().equalsIgnoreCase("otras")){
						st.setString(i++, item.getAccionMejoramientoOtras());
					}else{
						st.setString(i++, "");
					}
				}else{
					st.setString(i++, "");
				}

				st.setString(i++, item.getTipoGasto());
				st.setString(i++, item.getRubroGasto());

				if(item.getTipoGasto() != null){
					if(item.getTipoGasto().equalsIgnoreCase("Inversion")){
						st.setString(i++, item.getProyectoInversion());
					}else if(item.getTipoGasto().equalsIgnoreCase("Funcionamiento")){
						st.setString(i++, item.getSubnivelGasto());
					}
				}else{
					st.setString(i++, "");
				}

				st.setString(i++, item.getFuenteFinanciacion());

				if(item.getFuenteFinanciacion() != null && item.getFuenteFinanciacion().equalsIgnoreCase("otros recursos")){
					st.setString(i++,item.getFuenteFinanciacionOtros());
				}else{
					st.setNull(i++,Types.VARCHAR);
				}

				st.setLong(i++, item.getMontoAnual().longValue());
				st.setLong(i++, item.getPresupuestoParticipativo().longValue());
			}else{
				for(int j = i; j <= 25 ; j++){
					st.setString(j, "");
					i++;
				}
				st.setLong(i++, 0);
				st.setLong(i++, 0);
			}
			st.setString(i++, item.getTipoActividad());
			st.setString(i++, item.getSEGFECHACUMPLIMT());//31
			st.setString(i++,item.getPlaIdUsuario());
			
			// where
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			//****
			st.executeUpdate();
			// ingresar fuentes de financiacion
			ingresarFuentes(cn, item);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Elimina una actividad con recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @throws Exception
	 */
	public void eliminarActividad(int vigencia, long institucion, long codigo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("planeacion.validarEstado"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_SED) {
					throw new Exception(
							"El POA ya ha sido aprobado y no se puede eliminar actividades");
				}
			}
			rs.close();
			st.close();
			// eliminar fuentes de financiacion
			st = cn.prepareStatement(rb.getString("planeacion.eliminarFuente"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			st.executeUpdate();
			st.close();
			// eliminar actividad
			st = cn.prepareStatement(rb.getString("planeacion.eliminar"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
	
	/**
	 * Indica si las activiades pueden ser eliminadas
	 * True - Se pueden eliminar
	 * False - No se pueden eliminar
	 * @param vigencia
	 * @param institucion
	 * @param codigo
	 * @throws Exception
	 */
	public boolean eliminarActividadEnable(int vigencia, long institucion)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			int i = 0;
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb.getString("planeacion.validarEstado"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_SED) {
					return false;
				}
			}
			return true;
		}catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}	
	}

	/**
	 * Calcula la lista de anos de vigencia
	 * 
	 * @return Lista de anhos de vigencia
	 * @throws Exception
	 */
	public List getListaVigenciaActual() throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ItemVO itemVO = null;
		List listaVigencia = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement(rb.getString("planeacion.listaVigencia"));
			rs = pst.executeQuery();
			if (rs.next()) {
				itemVO = new ItemVO(rs.getInt(1), String.valueOf(rs.getInt(1)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(2), String.valueOf(rs.getInt(2)));
				listaVigencia.add(itemVO);
				itemVO = new ItemVO(rs.getInt(3), String.valueOf(rs.getInt(3)));
				listaVigencia.add(itemVO);
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return listaVigencia;
	}

	/**
	 * Obtiene los comentarios de los campos de las tablas de actividad con
	 * recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public FiltroPlaneacionVO getLabels(FiltroPlaneacionVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (filtro == null)
			filtro = new FiltroPlaneacionVO();
		try {
			filtro.setFilVigencia((int) getVigenciaNumericoPOA());
			cn = cursor.getConnection();
			// calcular si es fecha habil para poder guardar actividades
			pst = cn.prepareStatement(rb.getString("planeacion.rangoFecha"));
			pst.setInt(1, filtro.getFilVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				filtro.setFilRangoFechas(rs.getString(1) + " - "
						+ rs.getString(2));
				filtro.setFilFechaHabil(rs.getInt(3) == 0 ? false : true);
			} else {
				filtro.setFilRangoFechas("No establecido");
				filtro.setFilFechaHabil(false);
			}
			rs.close();
			pst.close();

			pst = cn.prepareStatement(rb.getString("planeacion.label"));
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)) {
					filtro.setLblVigencia(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)) {
					filtro.setLblObjetivo(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)) {
					filtro.setLblActividad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)) {
					filtro.setLblAreaGestion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPONDERADO)) {
					filtro.setLblPonderador(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)) {
					filtro.setLblLineaAccion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODTIMETA)) {
					filtro.setLblTipoMeta(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCANTIDAD)) {
					filtro.setLblMetaAnualCantidad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODUNMEDIDA)) {
					filtro.setLblMetaAnualUnidad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACOTROCUAL)) {
					filtro.setLblMetaAnualCual(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPRESUPUESTO)) {
					filtro.setLblPresupuesto(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRERESPON)) {
					filtro.setLblResponsable(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACFECHATERMIN)) {
					filtro.setLblFecha(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO1)) {
					filtro.setLblCronograma1(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO2)) {
					filtro.setLblCronograma2(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO3)) {
					filtro.setLblCronograma3(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACPERIODO4)) {
					filtro.setLblCronograma4(rs.getString(2));
					continue;
				}
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb.getString("planeacion.labelFuente"));
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLFTCODFUFINANC)) {
					filtro.setLblFuenteFinanciera(rs.getString(2));
					continue;
				}
			}
			rs.close();
			pst.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	/**
	 * Aprueba el POA por parte del colegio
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public void aprobarColegio(FiltroPlaneacionVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		int codigo = 0;
		try {
			cn = cursor.getConnection();
			// validacion de %
			st = cn.prepareStatement(rb.getString("poa.validarPonderado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) != 100)
					throw new Exception(
							"El porcentaje de ponderado debe sumar 100%");
			} else {
				throw new Exception("No hay actividades");
			}
			rs.close();
			st.close();
			// calculo del codigo
			st = cn.prepareStatement(rb.getString("poa.codigo"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				codigo = (rs.getInt(1));
			}
			rs.close();
			st.close();
			// ingresar
			st = cn.prepareStatement(rb.getString("poa.ingresar"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, codigo);
			st.setInt(i++, ParamPOA.ESTADO_POA_APROBADO_COLEGIO);
			st.setInt(i++, ParamPOA.ETAPA_POA_PLANEACION);
			st.setNull(i++, Types.VARCHAR);
			st.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Calcula la lista de actividades sin recursos
	 * 
	 * @param filtro
	 *            Filtro de bnsqueda de actividades
	 * @return Lista de actividades sin recursos
	 * @throws Exception
	 */
	public List getListaActividadesSin(FiltroPlaneacionVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		PlaneacionVO item = null;
		int i = 0;
		int cons = 1;
		try {
			cn = cursor.getConnection();
			// saber si se puede aprobar
			st = cn.prepareStatement(rb.getString("poa.getEstado"));
			i = 1;
			st.setInt(i++, filtro.getFilVigencia());
			st.setLong(i++, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilEstatoPOA(rs.getInt(1));
				filtro.setFilObservacion(rs.getString(2));
				filtro.setFilObservacionLinea(rs.getString(3));
				if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_RECHAZADO_SED) {
					filtro.setFilHabilitado(1);
					filtro.setFilEstado(ParamPOA.ESTADO_POA_RECHAZADO_SED_);
				} else {
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_COLEGIO)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_COLEGIO_);
					if (filtro.getFilEstatoPOA() == ParamPOA.ESTADO_POA_APROBADO_SED)
						filtro.setFilEstado(ParamPOA.ESTADO_POA_APROBADO_SED_);
					filtro.setFilHabilitado(0);
				}
			} else {
				filtro.setFilEstatoPOA(ParamPOA.ESTADO_POA_NINGUNO);
				filtro.setFilEstado(ParamPOA.ESTADO_POA_NINGUNO_);
				filtro.setFilHabilitado(1);
			}
			rs.close();
			st.close();
			// calculo de la lista
			st = cn.prepareStatement(rb.getString("planeacionSin.lista2"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilVigencia());
			st.setInt(i++, filtro.getFilAreaGestion());
			st.setInt(i++, filtro.getFilAreaGestion());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
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
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * Obtiene una actividad sin recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @param estadoPOA
	 *            estado de POA
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO getActividadSin(int vigencia, long institucion,
			long codigo, int estadoPOA, boolean habil) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PlaneacionVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("planeacionSin.obtener2"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new PlaneacionVO();
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
				if (estadoPOA == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| estadoPOA == ParamPOA.ESTADO_POA_APROBADO_SED
						|| !habil) {
					item.setPlaDisabled("disabled");
					item.setPlaDesHabilitado(true);
				}
			}
			rs.close();
			st.close();
			// obtener las fuentes de financiacion
			if (item != null) {
				List l = new ArrayList();
				st = cn.prepareStatement(rb
						.getString("planeacionSin.obtenerFuente"));
				i = 1;
				st.setInt(i++, vigencia);
				st.setLong(i++, institucion);
				st.setLong(i++, codigo);
				rs = st.executeQuery();
				while (rs.next()) {
					l.add(rs.getString(1));
				}
				int n[] = new int[l.size()];
				for (int j = 0; j < l.size(); j++) {
					n[j] = Integer.parseInt((String) l.get(j));
				}
				item.setPlaFuenteFinanciera(n);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Elimina una actividad sin recursos
	 * 
	 * @param vigencia
	 *            anho de vigencia
	 * @param institucion
	 *            colegio
	 * @param codigo
	 *            actividad
	 * @throws Exception
	 */
	public void eliminarActividadSin(int vigencia, long institucion, long codigo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb
					.getString("planeacionSin.validarEstado"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_SED) {
					throw new Exception(
							"El POA ya ha sido aprobado y no se puede eliminar actividades");
				}
			}
			rs.close();
			st.close();
			// eliminar fuentes de financiacion
			st = cn.prepareStatement(rb
					.getString("planeacionSin.eliminarFuente"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			st.executeUpdate();
			st.close();
			// eliminar actividad
			st = cn.prepareStatement(rb.getString("planeacionSin.eliminar"));
			i = 1;
			st.setInt(i++, vigencia);
			st.setLong(i++, institucion);
			st.setLong(i++, codigo);
			st.executeUpdate();
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Ingresa una actividad sin recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO ingresarActividadSin(PlaneacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// validar el estado de pOA
			st = cn.prepareStatement(rb
					.getString("planeacionSin.validarEstado"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_COLEGIO
						|| rs.getInt(1) == ParamPOA.ESTADO_POA_APROBADO_SED) {
					throw new Exception(
							"El POA ya ha sido aprobado y no se puede registrar mas actividades");
				}
			}
			rs.close();
			st.close();
			// insertar
			st = cn.prepareStatement(rb.getString("planeacionSin.codigo"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setPlaCodigo(rs.getLong(1));
			}
			rs.close();
			st.close();
			//
			st = cn.prepareStatement(rb.getString("planeacionSin.ingresar"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			st.setString(i++, item.getPlaMetaAnualCual());
			if (item.getPlaPresupuesto() != 0) {
				st.setLong(i++, item.getPlaPresupuesto());
			} else {
				st.setNull(i++, Types.VARCHAR);
			}
			st.setInt(i++, item.getPlaccodobjetivo());

			st.executeUpdate();
			ingresarFuentesSin(cn, item);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Actualiza una actividad sin recursos
	 * 
	 * @param item
	 *            Actividad
	 * @return Actividad
	 * @throws Exception
	 */
	public PlaneacionVO actualizarActividadSin(PlaneacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.prepareStatement(rb.getString("planeacionSin.actualizar"));
			i = 1;
			st.setLong(i++, item.getPlaOrden());
			st.setString(i++, item.getPlaObjetivo());
			st.setString(i++, item.getPlaActividad());
			st.setInt(i++, item.getPlaAreaGestion());
			st.setInt(i++, item.getPlaLineaAccion());
			st.setLong(i++, item.getPlaMetaAnualCantidad());
			st.setInt(i++, item.getPlaMetaAnualUnidad());
			st.setString(i++, item.getPlaMetaAnualCual());
			if (item.getPlaPresupuesto() != 0)
				st.setLong(i++, item.getPlaPresupuesto());
			else
				st.setNull(i++, Types.VARCHAR);
			st.setInt(i++, item.getPlaccodobjetivo());
			// w
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.executeUpdate();
			ingresarFuentesSin(cn, item);
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	/**
	 * Obtiene los comentarios de los campos de las tablas de actividades sin
	 * recursos
	 * 
	 * @param filtro
	 *            Filtro de bnsqueda de actividades
	 * @return Filtro de busqueda de actividades
	 * @throws Exception
	 */
	public FiltroPlaneacionVO getLabelsSin(FiltroPlaneacionVO filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (filtro == null)
			filtro = new FiltroPlaneacionVO();
		try {
			filtro.setFilVigencia((int) getVigenciaNumericoPOA());
			cn = cursor.getConnection();
			// calcular si es fecha habil para poder guardar actividades
			pst = cn.prepareStatement(rb.getString("planeacion.rangoFecha"));
			pst.setInt(1, filtro.getFilVigencia());
			rs = pst.executeQuery();
			if (rs.next()) {
				filtro.setFilRangoFechas(rs.getString(1) + " - "
						+ rs.getString(2));
				filtro.setFilFechaHabil(rs.getInt(3) == 0 ? false : true);
			} else {
				filtro.setFilRangoFechas("No establecido");
				filtro.setFilFechaHabil(false);
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb.getString("planeacionSin.label"));
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACVIGENCIA)) {
					filtro.setLblVigencia(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACOBJETIVO)) {
					filtro.setLblObjetivo(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACNOMBRE)) {
					filtro.setLblActividad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODARGESTION)) {
					filtro.setLblAreaGestion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODLIACCION)) {
					filtro.setLblLineaAccion(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCANTIDAD)) {
					filtro.setLblMetaAnualCantidad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACCODUNMEDIDA)) {
					filtro.setLblMetaAnualUnidad(rs.getString(2));
					continue;
				}
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLACOTROCUAL)) {
					filtro.setLblMetaAnualCual(rs.getString(2));
					continue;
				}
			}
			rs.close();
			pst.close();
			pst = cn.prepareStatement(rb.getString("planeacionSin.labelFuente"));
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(ParamsVO.CAMPO_PLFTCODFUFINANC)) {
					filtro.setLblFuenteFinanciera(rs.getString(2));
					continue;
				}
			}
			rs.close();
			pst.close();
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return filtro;
	}

	/**
	 * Ingresa las fuentes de financiacion de una actividad sin recursos
	 * 
	 * @param cn
	 *            Conexinn
	 * @param item
	 *            Actividad
	 * @throws Exception
	 */
	public void ingresarFuentesSin(Connection cn, PlaneacionVO item)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			int[] fuentes = item.getPlaFuenteFinanciera();
			if (fuentes == null)
				return;
			st = cn.prepareStatement(rb
					.getString("planeacionSin.eliminarFuente"));
			i = 1;
			st.setInt(i++, item.getPlaVigencia());
			st.setLong(i++, item.getPlaInstitucion());
			st.setLong(i++, item.getPlaCodigo());
			st.executeUpdate();
			st.close();
			//
			st = cn.prepareStatement(rb
					.getString("planeacionSin.ingresarFuente"));
			st.clearBatch();
			for (int j = 0; j < fuentes.length; j++) {
				i = 1;
				st.setInt(i++, item.getPlaVigencia());
				st.setLong(i++, item.getPlaInstitucion());
				st.setLong(i++, item.getPlaCodigo());
				st.setInt(i++, fuentes[j]);
				st.addBatch();
			}
			st.executeBatch();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * Calcula la lista de areas de gestinn
	 * 
	 * @return Lista de areas de gestinn
	 * @throws Exception
	 */
	public List getListaAreaGestion(int vigencia, long institucion, long codigo)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.listaAreaGestion"));
			rs = st.executeQuery();
			while (rs.next()) {
				item = new ItemVO();
				item.setCodigo(rs.getLong(1));
				item.setNombre(rs.getString(2));
				item.setPadre2(rs.getString(3));
				item.setPadre((rs.getLong(3))
						- getPonderadoArea(cn, vigencia, institucion, codigo,
								item.getCodigo()));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public int getPonderadoArea(Connection cn, int vig, long inst, long codigo,
			long area) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb
					.getString("planeacion.getPonderadoArea"));
			i = 1;
			st.setInt(i++, vig);
			st.setLong(i++, inst);
			st.setLong(i++, codigo);
			st.setLong(i++, area);
			rs = st.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	/**
	 * calcula la lista de actividades con recursos
	 * 
	 * @param filtro
	 *            Filtro de busqueda de actividades
	 * @return lista de actividades con recursos
	 * @throws Exception
	 */
	public float getTotalPonderado(FiltroPlaneacionVO filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			// calculo del total ponderado
			st = cn.prepareStatement(rb.getString("planeacion.totalPonderado"));
			st.setInt(1, filtro.getFilVigencia());
			st.setLong(2, filtro.getFilInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				return (rs.getFloat(1));
			}
			rs.close();
			st.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return 0;
	}

	public List getlistaObjetivos() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("planeacion.getlistaObjetivos"));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ItemVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				// item.setPadre(rs.getLong(i++));
				l.add(item);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}
	
	/**
	 * Cuando el Tipo de Meta pasa de DEMANDA a SUMATORIA o CONSTANTE, se deben eliminar los datos para los campos demanda1, demanda2, demanda3 y demanda4
	 * 
	 */
	public void actualizarTablasTipoMeta(PlaneacionVO item) throws Exception {

		int i = 0;
		
		ResultSet rs = null;
		Connection cn = null;
		PreparedStatement st = null;

		try {
			
			// 1 - SUMATORIA
			// 2 - DEMANDA
			// 3 - CONSTANTE
			if (item.getPlaTipoMeta() != 2) {
				
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				
				st = cn.prepareStatement(rb.getString("planeacion.actualizarTablasTipoMeta"));

				i = 1;
				st.setInt(i++, item.getPlaVigencia());		
				st.setLong(i++, item.getPlaInstitucion());
				st.setLong(i++, item.getPlaCodigo());
				rs = st.executeQuery();

				rs.close();
				st.close();

				cn.commit();
				
			}

		} catch(SQLException sqle) {

			sqle.printStackTrace();
			throw new Exception("Error de datos: "+sqle.getMessage());

		} catch(Exception sqle) {

			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());

		} finally {

			try {

				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);

			} catch(InternalErrorException inte) {}

		}

	}
	
}
