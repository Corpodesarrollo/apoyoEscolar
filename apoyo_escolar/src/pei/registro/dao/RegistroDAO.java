package pei.registro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



import pei.common.vo.ParametroHijoVO;
import pei.registro.vo.AjusteVO;
import pei.registro.vo.CapacitacionVO;
import pei.registro.vo.CriterioEvaluacionVO;
import pei.registro.vo.CriterioVO;
import pei.registro.vo.CurriculoVO;
import pei.registro.vo.CiudadaniaVO;
import pei.registro.vo.PoblacionesVO;
import pei.registro.vo.PrimeraInfanciaVO;
import pei.registro.vo.Proyecto40HorasVO;
import pei.registro.vo.DesarrolloAdministrativoVO;
import pei.registro.vo.DesarrolloCurricularVO;
import pei.registro.vo.DocumentoVO;
import pei.registro.vo.FiltroRegistroVO;
import pei.registro.vo.HorizonteVO;
import pei.registro.vo.IdentificacionVO;
import pei.registro.vo.ParamsVO;
import pei.registro.vo.ProgramaVO;
import pei.registro.vo.ProyectoVO;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.login.beans.Login;
import siges.util.Logger;

/**
 * @author john
 * 
 */
public class RegistroDAO extends Dao {

	/**
	 * @param c
	 */
	public RegistroDAO(Cursor c) {
		super(c);
		rb = ResourceBundle.getBundle("pei.registro.bundle.Registro");
	}

	/**
	 * Devuelve una conexinn activa
	 * 
	 * @return Conexinn
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		return cursor.getConnection();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEtapaDesarrollo() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_ETAPA);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEnfoque() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_ENFOQUE);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEnfasis() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_ENFASIS);
	}

	/**
	 * @param padre
	 * @return
	 * @throws Exception
	 */
	private List getListaParametroHijo(int padre) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ParametroHijoVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("pei.listaParametroHijo"));
			st.setInt(1, padre);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				item = new ParametroHijoVO();
				item.setCodigo(rs.getLong(i++));
				item.setNombre(rs.getString(i++));
				item.setAbreviatura(rs.getString(i++));
				item.setDescripcion(rs.getString(i++));
				item.setEditable(rs.getInt(i++) == 1 ? true : false);
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

	public IdentificacionVO getIdentificacion(long institucion)
			throws Exception {
		
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		IdentificacionVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("identificacion.getIdentificacion"));
			st.setLong(1, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new IdentificacionVO();
				item.setEdicion(true);
				// PEICODINST, PEIESTUDMAT, PEINUMJORNAD, PEIEXISTE, PEINOMBRE,
				// PEICODETAPADES, PEICODENFASIS, PEIENFASISOTRO,
				// PEICODENFPEDAG, PEIENFPEDAGOTRO
				// , PEIESTADO
				item.setIdenInstitucion(rs.getLong(i++));
				item.setIdenEstudiantes(rs.getLong(i++));
				item.setIdenJornadas(rs.getInt(i++));
				item.setIdenExiste(rs.getInt(i++));
				item.setIdenNombre(rs.getString(i++));
				item.setIdenEtapa(rs.getInt(i++));
				item.setIdenEnfasis(rs.getInt(i++));
				item.setIdenEnfasisOtro(rs.getString(i++));
				item.setIdenEnfoque(rs.getInt(i++));
				item.setIdenEnfoqueOtro(rs.getString(i++));
				item.setIdenEstado(rs.getInt(i++));
				
				/* Ajustes Junio 2013*/
				
				item.setIdenCaracter(rs.getInt(i++));
				if(item.getIdenCaracter() == 0){
					item.setIdenCaracter(1);
				}
				item.setIdenEducacionFormalAdulos(rs.getInt(i++));
				item.setIdenCantidadEducacionFormalAdultos(rs.getInt(i++));
				item.setIdenAceleracionAprendizaje(rs.getInt(i++));
				item.setIdenCantidadAceleracionAprendizaje(rs.getInt(i++));
				/********************/
				
				item.setIdenEstadoNombre(getEstadoPEI(item.getIdenEstado()));
				if (item.getIdenEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
					item.setIdenDisabled(true);
					item.setIdenDisabled_(ParamsVO.DISABLED);
				}
				item = getParametrosColegio(cn, item);
			} else {// se debe construir con los valores por defecto
				item = new IdentificacionVO();
				item.setIdenInstitucion(institucion);
				item.setIdenEstado(ParamsVO.ESTADO_PEI_EN_CONSTRUCCION);
				item.setIdenEstadoNombre(ParamsVO.ESTADO_PEI_SIN_INICIAR_);
				item.setIdenExiste(ParamsVO.PEI_EXISTE_NO);
				item = getParametrosColegio(cn, item);
			}
			// System.out.println("valores identidad: "+item.isIdenDisabled());
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
		//Logger.print(usuVO.getUsuarioId(), "Consulta de PEI, módulo de identificación. ",
			//	7, 1, this.toString());
		return item;
	}

	private IdentificacionVO getParametrosColegio(Connection cn,
			IdentificacionVO item) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			st = cn.prepareStatement(rb
					.getString("identificacion.getInformacionColegio"));
			st.setLong(1, item.getIdenInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				// INSCODIGO, INSRECTORNOMBRE, INSRECTORTEL, INSRECTORCORREO
				i = 2;
				item.setIdenRector(rs.getString(i++));
				item.setIdenTelefono(rs.getString(i++));
				item.setIdenCorreo(rs.getString(i++));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb
					.getString("identificacion.getInformacionSede"));
			st.setLong(1, item.getIdenInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setIdenSedes(rs.getInt(1));
			}
			rs.close();
			st.close();
			if (!item.isEdicion()) {
				st = cn.prepareStatement(rb
						.getString("identificacion.getInformacionJornada"));
				st.setLong(1, item.getIdenInstitucion());
				rs = st.executeQuery();
				if (rs.next()) {
					item.setIdenJornadas(rs.getInt(1));
				}
				rs.close();
				st.close();
				st = cn.prepareStatement(rb
						.getString("identificacion.getInformacionEstudiante"));
				st.setLong(1, item.getIdenInstitucion());
				rs = st.executeQuery();
				if (rs.next()) {
					item.setIdenEstudiantes(rs.getLong(1));
				}
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
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	public IdentificacionVO ingresarIdentificacion(IdentificacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("identificacion.insertar"));
			// PEICODINST, PEIESTUDMAT, PEINUMJORNAD, PEIEXISTE, PEINOMBRE,
			// PEICODETAPADES, PEICODENFASIS, PEIENFASISOTRO, PEICODENFPEDAG,
			// PEIENFPEDAGOTRO, PEIESTADO
			st.setLong(i++, item.getIdenInstitucion());
			st.setLong(i++, item.getIdenEstudiantes());
			st.setInt(i++, item.getIdenJornadas());
			st.setInt(i++, item.getIdenExiste());
			st.setString(i++, item.getIdenNombre());
			st.setInt(i++, item.getIdenEtapa());
			st.setInt(i++, item.getIdenEnfasis());
			st.setString(i++, item.getIdenEnfasisOtro());
			st.setInt(i++, item.getIdenEnfoque());
			st.setString(i++, item.getIdenEnfoqueOtro());
			st.setInt(i++, item.getIdenEstado());
			
			/*Ajustes Junio 2013*/
			st.setInt(i++, item.getIdenCaracter());
			st.setInt(i++, item.getIdenEducacionFormalAdulos());
			st.setInt(i++, item.getIdenCantidadEducacionFormalAdultos());
			st.setInt(i++, item.getIdenAceleracionAprendizaje());
			st.setInt(i++, item.getIdenCantidadAceleracionAprendizaje());
			/********************/
			
			st.executeUpdate();
			// actualizar cosas
			item.setEdicion(true);
			item.setIdenEstadoNombre(getEstadoPEI(item.getIdenEstado()));
			if (item.getIdenEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
				item.setIdenDisabled(true);
				item.setIdenDisabled_(ParamsVO.DISABLED);
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

	public IdentificacionVO actualizarIdentificacion(IdentificacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("identificacion.actualizar"));
			// PEIESTUDMAT=?,PEINUMJORNAD=?,PEIEXISTE=?,PEINOMBRE=?,
			// PEICODETAPADES=?,PEICODENFASIS=?,PEIENFASISOTRO=?,PEICODENFPEDAG=?,
			// PEIENFPEDAGOTRO=?,PEIESTADO=?
			st.setLong(i++, item.getIdenEstudiantes());
			st.setInt(i++, item.getIdenJornadas());
			st.setInt(i++, item.getIdenExiste());
			st.setString(i++, item.getIdenNombre());
			st.setInt(i++, item.getIdenEtapa());
			st.setInt(i++, item.getIdenEnfasis());
			st.setString(i++, item.getIdenEnfasisOtro());
			st.setInt(i++, item.getIdenEnfoque());
			st.setString(i++, item.getIdenEnfoqueOtro());
			st.setInt(i++, item.getIdenEstado());
			
			/*Ajustes Junio 2013*/
			st.setInt(i++, item.getIdenCaracter());
			st.setInt(i++, item.getIdenEducacionFormalAdulos());
			st.setInt(i++, item.getIdenCantidadEducacionFormalAdultos());
			st.setInt(i++, item.getIdenAceleracionAprendizaje());
			st.setInt(i++, item.getIdenCantidadAceleracionAprendizaje());
			/********************/
				
			// w
			st.setLong(i++, item.getIdenInstitucion());
			st.executeUpdate();
			// actualizar ciertas cosas
			item.setIdenEstadoNombre(getEstadoPEI(item.getIdenEstado()));
			if (item.getIdenEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
				item.setIdenDisabled(true);
				item.setIdenDisabled_(ParamsVO.DISABLED);
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

	private String getEstadoPEI(int estado) {
		switch (estado) {
		case ParamsVO.ESTADO_PEI_EN_CONSTRUCCION:
			return ParamsVO.ESTADO_PEI_EN_CONSTRUCCION_;
		case ParamsVO.ESTADO_PEI_COMPLETO:
			return ParamsVO.ESTADO_PEI_COMPLETO_;
		case ParamsVO.ESTADO_PEI_A_ACTUALIZAR:
			return ParamsVO.ESTADO_PEI_A_ACTUALIZAR_;
		default:
			return ParamsVO.ESTADO_PEI_SIN_INICIAR_;
		}
	}

	public HorizonteVO getHorizonte(long institucion, boolean disabled)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		HorizonteVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("horizonte.getHorizonte"));
			st.setLong(1, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new HorizonteVO();
				item.setEdicion(true);
				// PEICODINST, PEIESTADO, PEIPROCESO,
				// PEIETAPAMISION, PEIETAPAVISION, PEIETAPAPERFILES,
				// PEIETAPAOBJINSTIT, PEIETAPADIFICULTAD
				item.setHorInstitucion(rs.getLong(i++));
				item.setHorEstado(rs.getInt(i++));
				item.setHorEstadoNombre(getEstadoPEI(item.getHorEstado()));
				item.setHorProceso(rs.getInt(i++));
				item.setHorMision(rs.getInt(i++));
				item.setHorVision(rs.getInt(i++));
				item.setHorPerfil(rs.getInt(i++));
				item.setHorObjetivo(rs.getInt(i++));
				item.setHorDificultad(rs.getString(i++));
				/*Ajustes Junio 2013*/
				item.sethorDiagnostico(rs.getInt(i++));
				item.setHorEtapaPrincipios(rs.getInt(i++));
				item.setHorImprontas(rs.getInt(i++));
				
				if (disabled) {
					item.setHorDisabled(true);
					item.setHorDisabled_(ParamsVO.DISABLED);
				}
				/*
				 * PENDIENTE POR SABER SI EL ESTADO INFLUYE EN EL BLOQUEO DEL
				 * FORM
				 */
				if (item.getHorEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
					item.setHorDisabled(true);
					item.setHorDisabled_(ParamsVO.DISABLED);
				}
			} else {// se debe construir con los valores por defecto
				item = new HorizonteVO();
				item.setHorInstitucion(institucion);
				item.setHorEstado(ParamsVO.ESTADO_PEI_SIN_INICIAR);
				item.setHorEstadoNombre(ParamsVO.ESTADO_PEI_SIN_INICIAR_);
				item.setHorDisabled(true);
				item.setHorDisabled_(ParamsVO.DISABLED);
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

	public HorizonteVO actualizarHorizonte(HorizonteVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("horizonte.actualizar"));
			// PEIPROCESO=?, PEIETAPAMISION=?, PEIETAPAVISION=?,
			// PEIETAPAPERFILES=?, PEIETAPAOBJINSTIT=?, PEIETAPADIFICULTAD=?
			// WHERE PEICODINST=?
			st.setInt(i++, item.getHorProceso());
			st.setInt(i++, item.getHorMision());
			st.setInt(i++, item.getHorVision());
			st.setInt(i++, item.getHorPerfil());
			st.setInt(i++, item.getHorObjetivo());
			st.setString(i++, item.getHorDificultad());
			/*Ajustes Junio 2013*/
			st.setInt(i++, item.getHorDiagnostico());
			st.setInt(i++, item.getHorEtapaPrincipios());
			st.setInt(i++, item.getHorImprontas());
			
			// w
			st.setLong(i++, item.getHorInstitucion());
			st.executeUpdate();
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
	 * @return
	 * @throws Exception
	 */
	
	public List getListaDiagnostico() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_PROCESO_DIAGNOSTICO);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		
		return lista;
	}
	
	public List getListaProceso() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.PROCESO_PEI_CONSULTA,ParamsVO.PROCESO_PEI_CONSULTA_));
		lista.add(new ItemVO(ParamsVO.PROCESO_PEI_SOCIALIZACION,ParamsVO.PROCESO_PEI_SOCIALIZACION_));
		lista.add(new ItemVO(ParamsVO.PROCESO_PEI_ARTICULACION,ParamsVO.PROCESO_PEI_ARTICULACION_));
		return lista;
	}

	public List getListaEtapa() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_HORIZONTE_INSTITUCIONAL);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		/* Version Anterior
		lista.add(new ItemVO(ParamsVO.ETAPA_PEI_DEFINIDO,ParamsVO.ETAPA_PEI_DEFINIDO_));
		lista.add(new ItemVO(ParamsVO.ETAPA_PEI_SOCIALIZADO,ParamsVO.ETAPA_PEI_SOCIALIZADO_));
		lista.add(new ItemVO(ParamsVO.ETAPA_PEI_COMPARTIDO,ParamsVO.ETAPA_PEI_COMPARTIDO_));
		*/
		return lista;
	}
	
	public List getListaEstructuraCurricular() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_ESTRUCTURA_CURRICULAR);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		
		return lista;
	}
	
	public List getListaPlanEstudios() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_PLAN_ESTUDIOS);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	public List getListaSistemaEvaluacion() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_SISTEMA_EVALUACION);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	public List getListaProyectoCiudadania() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_PROYECTO_CIUDADANIA);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaPreparacion() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_PREPARACION);
	}

	public List getListaElemento() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.ELEMENTO_PEI_CONSULTA,
				ParamsVO.ELEMENTO_PEI_CONSULTA_));
		lista.add(new ItemVO(ParamsVO.ELEMENTO_PEI_SOCIALIZACION,
				ParamsVO.ELEMENTO_PEI_SOCIALIZACION_));
		lista.add(new ItemVO(ParamsVO.ELEMENTO_PEI_ARTICULACION,
				ParamsVO.ELEMENTO_PEI_ARTICULACION_));
		lista.add(new ItemVO(ParamsVO.ELEMENTO_PEI_IMPLEMENTACION,
				ParamsVO.ELEMENTO_PEI_IMPLEMENTACION_));
		return lista;
	}

	public List getListaSiNo() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.RESPUESTA_SI, ParamsVO.RESPUESTA_SI_));
		lista.add(new ItemVO(ParamsVO.RESPUESTA_NO, ParamsVO.RESPUESTA_NO_));
		return lista;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaFaseCurricular() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_FASE_CURRICULAR);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaFormulacion() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_FORMULACION);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaEjecucion() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_EJECUCION);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaSeguimiento() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_SEGUIMIENTO);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaUniversidad() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_UNIVERSIDADES);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaProgramas() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_PROGRAMAS);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List getListaProyectos() throws Exception {
		return getListaParametroHijo(ParamsVO.PARAM_PADRE_PROYECTOS_LEY);
	}

	public List getListaEstrategia() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.ESTRATEGIA_PEI_MEDIA,
				ParamsVO.ESTRATEGIA_PEI_MEDIA_));
		lista.add(new ItemVO(ParamsVO.ESTRATEGIA_PEI_UNIVERSIDAD,
				ParamsVO.ESTRATEGIA_PEI_UNIVERSIDAD_));
		lista.add(new ItemVO(ParamsVO.ESTRATEGIA_PEI_SUPERIOR_SENA,
				ParamsVO.ESTRATEGIA_PEI_SUPERIOR_SENA_));
		lista.add(new ItemVO(ParamsVO.ESTRATEGIA_PEI_SENA,
				ParamsVO.ESTRATEGIA_PEI_SENA_));
		return lista;
	}

	/**
	 * @param institucion
	 * @return
	 * @throws Exception
	 */
	public DesarrolloCurricularVO getDesarrolloCurricular(long institucion,
			boolean disabled) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DesarrolloCurricularVO item = null;
		int conteoAlumnosPrimeraInfancia=0;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.get"));
			st.setLong(1, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new DesarrolloCurricularVO();
				item.setEdicion(true);
				// PEICODINST, PEIESTADO, PEICODFASECURRIC,
				// PEIHERRAMIENTAVIDA, PEIBASECONOCIMIENTO, PEIALCANCEC1,
				// PEIALCANCEC2, PEIALCANCEC3, PEIALCANCEC4, PEIALCANCEC5,
				// PEIDIFICULTADC1, PEIDIFICULTADC2,PEIDIFICULTADC3,
				// PEIDIFICULTADC4, PEIDIFICULTADC5, PEIARTICULADO,
				// PEIESTMEDIA, PEIESTARTICULADA,PEIESTARTSENA,
				// PEIESTINTSENA, PEISEGEGRESADO,PEIPRACTICALAB
				item.setDesInstitucion(rs.getLong(i++));
				item.setDesEstado(rs.getInt(i++));
				item.setDesEstadoNombre(getEstadoPEI(item.getDesEstado()));
				item.setDesFaseCurriculo(rs.getInt(i++));
				item.setDesHerramientaVida(rs.getInt(i++));
				item.setDesBaseConocimiento(rs.getInt(i++));
				item.setDesAlcance1(rs.getString(i++));
				item.setDesAlcance2(rs.getString(i++));
				item.setDesAlcance3(rs.getString(i++));
				item.setDesAlcance4(rs.getString(i++));
				item.setDesAlcance5(rs.getString(i++));
				item.setDesDificultad1(rs.getString(i++));
				item.setDesDificultad2(rs.getString(i++));
				item.setDesDificultad3(rs.getString(i++));
				item.setDesDificultad4(rs.getString(i++));
				item.setDesDificultad5(rs.getString(i++));
				item.setDesArticulado(rs.getInt(i++));
				item.setDesMedia(rs.getInt(i++));
				item.setDesArticUniversidad(rs.getInt(i++));
				item.setDesArticSena(rs.getInt(i++));
				item.setDesIntegradoSena(rs.getInt(i++));
				item.setDesEgresado(rs.getInt(i++));
				item.setDesPasantia(rs.getInt(i++));
				/* Ajustes Junio 2013 */
				item.setDesEstructuraCurricular(rs.getInt(i++));
				item.setDesMallaCurricular(rs.getInt(i++));
				item.setDesPlanEstudios(rs.getInt(i++));
				item.setDesCiclos(rs.getInt(i++));
				item.setDesOtroDiseno(rs.getString(i++));
				item.setDesCiclo1(rs.getInt(i++));
				item.setDesCiclo2(rs.getInt(i++));
				item.setDesCiclo3(rs.getInt(i++));
				item.setDesCiclo4(rs.getInt(i++));
				item.setDesCiclo5(rs.getInt(i++));
				item.setDesSistemaEvaluacion(rs.getInt(i++));
				item.setDesCriterioEvaluacion(rs.getInt(i++));
				item.setDesCiudadania(rs.getInt(i++));
				item.setDesAvanceCiudadania(rs.getInt(i++));
				item.setDesFaseCiudadania(rs.getInt(i++));
				item.setDesProyecto40Horas(rs.getInt(i++));
				item.setDesNecesidades(rs.getInt(i++));
				item.setDesCantidadNecesidades(rs.getInt(i++));
				
				if(item.getDesEstado() == 1){
					PreparedStatement st2 = null;
					ResultSet rs2 = null;
					st2 = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.conteoPrimeraInfancia"));
					st2.setLong(1, institucion);
					rs2 = st2.executeQuery();
					if (rs2.next()) {
						conteoAlumnosPrimeraInfancia=rs2.getInt(1);
						if(conteoAlumnosPrimeraInfancia > 0){
							item.setDesPrimeraInfancia(1);
							/*try {
								PreparedStatement st3 = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.insertarPrimeraInfancia"));
								st3.setLong(1, institucion);
								st3.executeUpdate();
							} catch (Exception e) {
								e.printStackTrace();
								throw new Exception("Error interno: " + e.getMessage());
							}*/
						}else{
							item.setDesPrimeraInfancia(0);
						}
					}else{
						item.setDesPrimeraInfancia(rs.getInt(i++));
					}
				}else{
					item.setDesPrimeraInfancia(rs.getInt(i++));
				}
				/* ****************** */
				item.setDesListaCriterioPreparacion(getListaCriterio(cn, item,
						ParamsVO.PARAM_PADRE_PREPARACION));
				item.setDesListaCriterioFormulacion(getListaCriterio(cn, item,
						ParamsVO.PARAM_PADRE_FORMULACION));
				item.setDesListaCriterioEjecucion(getListaCriterio(cn, item,
						ParamsVO.PARAM_PADRE_EJECUCION));
				item.setDesListaCriterioSeguimiento(getListaCriterio(cn, item,
						ParamsVO.PARAM_PADRE_SEGUIMIENTO));
				// item.setDesListaArticulacionSENA(getListaArticulacionSENA(cn,item));
				// item.setDesListaArticulacionUniversidad(getListaArticulacionUniversidad(cn,item));
				item.setDesListaProyecto(getListaProyecto(cn, item));
				// item.setDesListaOtroProyecto(getListaOtroProyecto(cn,item));
				// item.setDesListaCapacitacion(getListaCapacitacion(cn,item));
				if (disabled) {
					item.setDesDisabled(true);
					item.setDesDisabled_(ParamsVO.DISABLED);
				}
				/*
				 * PENDIENTE POR SABER SI EL ESTADO INFLUYE EN EL BLOQUEO DEL
				 * FORM
				 */
				if (item.getDesEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
					item.setDesDisabled(true);
					item.setDesDisabled_(ParamsVO.DISABLED);
				}
			} else {// se debe construir con los valores por defecto
				item = new DesarrolloCurricularVO();
				item.setDesInstitucion(institucion);
				item.setDesEstado(ParamsVO.ESTADO_PEI_SIN_INICIAR);
				item.setDesEstadoNombre(ParamsVO.ESTADO_PEI_SIN_INICIAR_);
				item.setDesDisabled(true);
				item.setDesDisabled_(ParamsVO.DISABLED);
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

	public DesarrolloCurricularVO actualizarDesarrolloCurricular(
			DesarrolloCurricularVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			st = cn.prepareStatement(rb.getString("desarrolloc.actualizar"));
			// PEICODFASECURRIC=?, PEIHERRAMIENTAVIDA=?, PEIBASECONOCIMIENTO=?,
			// PEIALCANCEC1=?,PEIALCANCEC2=?, PEIALCANCEC3=?,
			// PEIALCANCEC4=?, PEIALCANCEC5=?, PEIDIFICULTADC1=?,
			// PEIDIFICULTADC2=?,PEIDIFICULTADC3=?, PEIDIFICULTADC4=?,
			// PEIDIFICULTADC5=?, PEIARTICULADO=?, PEIESTMEDIA=?,
			// PEIESTARTICULADA=?,PEIESTARTSENA=?, PEIESTINTSENA=?,
			// PEISEGEGRESADO=?,PEIPRACTICALAB=?
			st.setInt(i++, item.getDesFaseCurriculo());
			st.setInt(i++, item.getDesHerramientaVida());
			st.setInt(i++, item.getDesBaseConocimiento());
			if (item.getDesAlcance1() == null
					|| item.getDesAlcance1().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesAlcance1());
			if (item.getDesAlcance2() == null
					|| item.getDesAlcance2().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesAlcance2());
			if (item.getDesAlcance3() == null
					|| item.getDesAlcance3().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesAlcance3());
			if (item.getDesAlcance4() == null
					|| item.getDesAlcance4().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesAlcance4());
			if (item.getDesAlcance5() == null
					|| item.getDesAlcance5().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesAlcance5());
			if (item.getDesDificultad1() == null
					|| item.getDesDificultad1().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultad1());
			if (item.getDesDificultad2() == null
					|| item.getDesDificultad2().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultad2());
			if (item.getDesDificultad3() == null
					|| item.getDesDificultad3().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultad3());
			if (item.getDesDificultad4() == null
					|| item.getDesDificultad4().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultad4());
			if (item.getDesDificultad5() == null
					|| item.getDesDificultad5().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultad5());
			st.setInt(i++, item.getDesArticulado());
			st.setInt(i++, item.getDesMedia());
			st.setInt(i++, item.getDesArticUniversidad());
			st.setInt(i++, item.getDesArticSena());
			st.setInt(i++, item.getDesIntegradoSena());
			st.setInt(i++, item.getDesEgresado());
			st.setInt(i++, item.getDesPasantia());
			/* Ajustes Junio 2013 */
			st.setInt(i++, item.getDesEstructuraCurricular());
			st.setInt(i++, item.getDesMallaCurricular());
			st.setInt(i++, item.getDesPlanEstudios());
			st.setInt(i++, item.getDesCiclos());
			st.setString(i++, item.getDesOtroDiseno());
			st.setInt(i++, item.getDesCiclo1());
			st.setInt(i++, item.getDesCiclo2());
			st.setInt(i++, item.getDesCiclo3());
			st.setInt(i++, item.getDesCiclo4());
			st.setInt(i++, item.getDesCiclo5());
			st.setInt(i++, item.getDesSistemaEvaluacion());
			st.setInt(i++, item.getDesCriterioEvaluacion());
			st.setInt(i++, item.getDesCiudadania());
			st.setInt(i++, item.getDesAvanceCiudadania());
			st.setInt(i++, item.getDesFaseCiudadania());
			st.setInt(i++, item.getDesProyecto40Horas());
			st.setInt(i++, item.getDesNecesidades());
			st.setInt(i++, item.getDesCantidadNecesidades());
			/* ****************** */
			
			// w
			st.setLong(i++, item.getDesInstitucion());
			st.executeUpdate();
			// actualizar los demas paneles
			actualizarCriterio(cn, item, ParamsVO.PARAM_PADRE_PREPARACION,
					item.getDesCriterioPreparacion());
			actualizarCriterio(cn, item, ParamsVO.PARAM_PADRE_FORMULACION,
					item.getDesCriterioFormulacion());
			actualizarCriterio(cn, item, ParamsVO.PARAM_PADRE_EJECUCION,
					item.getDesCriterioEjecucion());
			actualizarCriterio(cn, item, ParamsVO.PARAM_PADRE_SEGUIMIENTO,
					item.getDesCriterioSeguimiento());
			actualizarProyecto(cn, item);
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

	private List getListaCriterio(Connection cn, DesarrolloCurricularVO item,
			int tipo) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		CriterioVO criterio;
		try {
			st = cn.prepareStatement(rb
					.getString("desarrolloc.criterio.getLista"));
			st.setLong(1, item.getDesInstitucion());
			st.setLong(2, item.getDesInstitucion());
			st.setInt(3, tipo);
			rs = st.executeQuery();
			while (rs.next()) {
				// CRALCODINST, CRALCODCRITERIOPADRE, CRALCODCRITERIOHIJO,
				// CRALVALORINST,PARHNOMBRE,PARHABREV
				criterio = new CriterioVO();
				i = 1;
				criterio.setCritInstitucion(rs.getLong(i++));
				criterio.setCritTipo(rs.getInt(i++));
				criterio.setCritCodigo(rs.getInt(i++));
				criterio.setCritValor(rs.getInt(i++));
				criterio.setCritNombre(rs.getString(i++));
				criterio.setCritAbreviatura(rs.getString(i++));
				criterio.setCritValorMaximo(rs.getInt(i++));
				criterio.setCritDescripcion(rs.getString(i++));
				lista.add(criterio);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	private List getListaProyecto(Connection cn, DesarrolloCurricularVO item)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		ProyectoVO proyecto;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto.getLista"));
			st.setLong(1, item.getDesInstitucion());
			st.setLong(2, item.getDesInstitucion());
			st.setInt(3, ParamsVO.PARAM_PADRE_PROYECTOS_LEY);
			rs = st.executeQuery();
			while (rs.next()) {
				// PROYCODINST, PARHCODIGO, PROYETAPA,PARHNOMBRE
				proyecto = new ProyectoVO();
				i = 1;
				proyecto.setProyInstitucion(rs.getLong(i++));
				proyecto.setProyCodigo(rs.getInt(i++));
				proyecto.setProyEtapa(rs.getInt(i++));
				proyecto.setProyNombre(rs.getString(i++));
				proyecto.setProyAlcance(rs.getString(i++));
				proyecto.setProyDificultad(rs.getString(i++));
				proyecto.setProyDescripcion(rs.getString(i++));
				lista.add(proyecto);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	public List getListaProgramaSENA(DesarrolloCurricularVO item)
			throws Exception {
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			return getListaArticulacionSENA(cn, item);
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}
	
	public List getListaProgramaUniversidad(DesarrolloCurricularVO item)
			throws Exception {
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			return getListaArticulacionUniversidad(cn, item);
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getListaOtroProyecto(DesarrolloCurricularVO item)
			throws Exception {
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			return getListaOtroProyecto(cn, item);
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public List getListaCapacitacion(DesarrolloCurricularVO item)
			throws Exception {
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			return getListaCapacitacion(cn, item);
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	private List getListaArticulacionSENA(Connection cn,
		DesarrolloCurricularVO item) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		ProgramaVO programa;
		try {
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaSENA.getLista"));
			st.setInt(1, ParamsVO.PARAM_PADRE_PROGRAMAS);
			st.setLong(2, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				programa = new ProgramaVO();
				i = 1;
				programa.setProInstitucion(rs.getLong(i++));
				programa.setProCodigo(rs.getLong(i++));
				programa.setProEstudiantes(rs.getInt(i++));
				programa.setProNombre(rs.getString(i++));
				programa.setProDescripcion(rs.getString(i++));
				lista.add(programa);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	private List getListaArticulacionUniversidad(Connection cn,
			DesarrolloCurricularVO item) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		ProgramaVO programa;
		try {
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaUniversidad.getLista"));
			st.setLong(1, item.getDesInstitucion());
			st.setInt(2, ParamsVO.PARAM_PADRE_UNIVERSIDADES);
			st.setInt(3, ParamsVO.PARAM_PADRE_PROGRAMAS);
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGUCODINST, PRGUCODUNIVERSIDAD, PRGUCODPROGRAMA,
				// PRGUNUMESTUD,a.PARHNOMBRE,b.PARHNOMBRE
				// FROM PEI_INST_PRGUNIVERSIDAD,PEI_PARAM_HIJO a,PEI_PARAM_HIJO
				// b
				// where PRGUCODINST=? and a.PARHCODPADRE=?
				// and a.PARHCODIGO=PRGUCODUNIVERSIDAD and a.PARHESTADO=1
				// and b.PARHCODPADRE=? and b.PARHCODIGO=PRGUCODPROGRAMA
				// and b.PARHESTADO=1
				programa = new ProgramaVO();
				i = 1;
				programa.setProInstitucion(rs.getLong(i++));
				programa.setProUniversidad(rs.getLong(i++));
				programa.setProCodigo(rs.getLong(i++));
				programa.setProEstudiantes(rs.getInt(i++));
				programa.setProNombreUniversidad(rs.getString(i++));
				programa.setProNombre(rs.getString(i++));
				programa.setProDescripcion(rs.getString(i++));
				programa.setProDescripcionUniversidad(rs.getString(i++));
				lista.add(programa);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	private List getListaOtroProyecto(Connection cn, DesarrolloCurricularVO item)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		ProyectoVO proyecto;
		try {
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE, OPROALCANCE,
				// OPRODIFICULTAD, OPROENTIDAD
				// FROM PEI_INST_OTROPROYECTO
				// where OPROCODINST=? order by OPROCODPROYECTO
				proyecto = new ProyectoVO();
				i = 1;
				proyecto.setProyInstitucion(rs.getLong(i++));
				proyecto.setProyCodigo(rs.getInt(i++));
				proyecto.setProyNombre(rs.getString(i++));
				proyecto.setProyAlcance(rs.getString(i++));
				proyecto.setProyDificultad(rs.getString(i++));
				proyecto.setProyEntidad(rs.getString(i++));
				lista.add(proyecto);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	private List getListaCapacitacion(Connection cn, DesarrolloCurricularVO item)
			throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		CapacitacionVO capacitacion;
		try {
			st = cn.prepareStatement(rb
					.getString("desarrolloc.capacitacion.getLista"));
			st.setLong(1, item.getDesInstitucion());
			st.setInt(2, ParamsVO.PARAM_PADRE_UNIVERSIDADES);
			rs = st.executeQuery();
			while (rs.next()) {
				// SELECT CADOCODINST, CADOCODFORMAC, CADONOMBREFORMAC,
				// CADONUMDOCENTES, CADOCODUNIVERSIDAD,PARHNOMBRE
				// FROM PEI_INST_CAPACITADOCENTES,PEI_PARAM_HIJO
				// where CADOCODINST=? and PARHCODPADRE=?
				// and CADOCODUNIVERSIDAD=PARHCODIGO and PARHESTADO=1
				// order by CADOCODFORMAC
				capacitacion = new CapacitacionVO();
				i = 1;
				capacitacion.setCapInstitucion(rs.getLong(i++));
				capacitacion.setCapCodigo(rs.getInt(i++));
				capacitacion.setCapNombreFormacion(rs.getString(i++));
				capacitacion.setCapDocentes(rs.getInt(i++));
				capacitacion.setCapUniversidad(rs.getInt(i++));
				capacitacion.setCapNombreUniversidad(rs.getString(i++));
				lista.add(capacitacion);
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
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	public DesarrolloCurricularVO actualizarCriterio(Connection cn,
			DesarrolloCurricularVO item, int tipo, String[] criterio)
			throws Exception {
		PreparedStatement st = null;
		int i = 1;
		String[] row;
		try {
			if (criterio == null)
				return item;
			for (int j = 0; j < criterio.length; j++) {
				i = 1;
				row = criterio[j].split(":");
				if (row == null || row.length < 2)
					continue;
				st = cn.prepareStatement(rb
						.getString("desarrolloc.criterio.actualizar"));
				// CRALVALORINST=?
				// where CRALCODINST=?
				// and CRALCODCRITERIOPADRE=?
				// and CRALCODCRITERIOHIJO=?
				st.setInt(i++, Integer.parseInt(row[1]));
				// w
				st.setLong(i++, item.getDesInstitucion());
				st.setInt(i++, tipo);
				st.setInt(i++, Integer.parseInt(row[0]));
				i = st.executeUpdate();
				st.close();
				if (i == 0) {
					i = 1;
					// CRALCODINST, CRALCODCRITERIOPADRE,
					// CRALCODCRITERIOHIJO,CRALVALORINST
					st = cn.prepareStatement(rb
							.getString("desarrolloc.criterio.insertar"));
					st.setLong(i++, item.getDesInstitucion());
					st.setInt(i++, tipo);
					st.setInt(i++, Integer.parseInt(row[0]));
					st.setInt(i++, Integer.parseInt(row[1]));
					st.executeUpdate();
					st.close();
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	public DesarrolloCurricularVO actualizarProyecto(Connection cn,
			DesarrolloCurricularVO item) throws Exception {
		PreparedStatement st = null;
		int i = 1;
		String[] row;
		String[] proyecto = item.getDesProyecto();
		try {
			if (proyecto == null)
				return item;
			for (int j = 0; j < proyecto.length; j++) {
				i = 1;
				row = proyecto[j].replace('|', '#').split("#");
				if (row == null || row.length < 4)
					continue;
				st = cn.prepareStatement(rb.getString("desarrolloc.proyecto.actualizar"));
				// PROYETAPA=? WHERE PROYCODINST=? AND PROYCODPROYECTO=?
				st.setInt(i++, Integer.parseInt(row[1]));
				st.setString(i++, row[2]);
				st.setString(i++, row[3]);
				// w
				st.setLong(i++, item.getDesInstitucion());
				st.setInt(i++, Integer.parseInt(row[0]));
				i = st.executeUpdate();
				st.close();
				if (i == 0) {
					i = 1;
					// PROYCODINST, PROYCODPROYECTO, PROYETAPA,PROYDIFICULTAD
					st = cn.prepareStatement(rb
							.getString("desarrolloc.proyecto.insertar"));
					st.setLong(i++, item.getDesInstitucion());
					st.setInt(i++, Integer.parseInt(row[0]));
					st.setInt(i++, Integer.parseInt(row[1]));
					st.setString(i++, row[2]);
					st.executeUpdate();
					st.close();
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("Error de datos: " + sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception("Error interno: " + sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return item;
	}

	public ProgramaVO ingresarProgramaSENA(ProgramaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validacion
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaSENA.validarInsertar"));
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Programa duplicado");
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaSENA.insertar"));
			// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProCodigo());
			st.setLong(i++, item.getProEstudiantes());
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
		return item;
	}

	public ProgramaVO eliminarProgramaSENA(ProgramaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaSENA.eliminar"));
			// PRGSCODINST, PRGSCODPROG
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProCodigo());
			st.executeUpdate();
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

	public ProgramaVO ingresarProgramaUniversidad(ProgramaVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validacion
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaUniversidad.validarInsertar"));
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProUniversidad());
			st.setLong(i++, item.getProCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				throw new Exception("Programa duplicado");
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaUniversidad.insertar"));
			// PRGUCODINST, PRGUCODUNIVERSIDAD, PRGUCODPROGRAMA, PRGUNUMESTUD
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProUniversidad());
			st.setLong(i++, item.getProCodigo());
			st.setLong(i++, item.getProEstudiantes());
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
		return item;
	}

	public ProgramaVO eliminarProgramaUniversidad(ProgramaVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.programaUniversidad.eliminar"));
			// PRGSCODINST, PRGSCODPROG
			st.setLong(i++, item.getProInstitucion());
			st.setLong(i++, item.getProUniversidad());
			st.setLong(i++, item.getProCodigo());
			st.executeUpdate();
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

	public ProyectoVO ingresarOtroProyecto(ProyectoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.id"));
			st.setLong(i++, item.getProyInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setProyCodigo(rs.getLong(1));
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.insertar"));
			// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
			// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
			st.setLong(i++, item.getProyInstitucion());
			st.setLong(i++, item.getProyCodigo());
			st.setString(i++, item.getProyNombre());
			st.setString(i++, item.getProyAlcance());
			st.setString(i++, item.getProyDificultad());
			st.setString(i++, item.getProyEntidad());
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
		return item;
	}

	public ProyectoVO actualizarOtroProyecto(ProyectoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getProyNombre());
			st.setString(i++, item.getProyAlcance());
			st.setString(i++, item.getProyDificultad());
			st.setString(i++, item.getProyEntidad());
			// w
			st.setLong(i++, item.getProyInstitucion());
			st.setLong(i++, item.getProyCodigo());
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
		return item;
	}

	public ProyectoVO eliminarOtroProyecto(ProyectoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.eliminar"));
			st.setLong(i++, item.getProyInstitucion());
			st.setLong(i++, item.getProyCodigo());
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
		return item;
	}

	public ProyectoVO getOtroProyecto(ProyectoVO proyectoVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ProyectoVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.otroProyecto.get"));
			st.setLong(1, proyectoVO.getProyInstitucion());
			st.setLong(2, proyectoVO.getProyCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new ProyectoVO();
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setProyInstitucion(rs.getLong(i++));
				item.setProyCodigo(rs.getLong(i++));
				item.setProyNombre(rs.getString(i++));
				item.setProyAlcance(rs.getString(i++));
				item.setProyDificultad(rs.getString(i++));
				item.setProyEntidad(rs.getString(i++));
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

	public CapacitacionVO ingresarCapacitacion(CapacitacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// validacion
			st = cn.prepareStatement(rb
					.getString("desarrolloc.capacitacion.id"));
			st.setLong(i++, item.getCapInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setCapCodigo(rs.getInt(1));
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb
					.getString("desarrolloc.capacitacion.insertar"));
			// CADOCODINST, CADOCODFORMAC, CADONOMBREFORMAC, CADONUMDOCENTES,
			// CADOCODUNIVERSIDAD
			st.setLong(i++, item.getCapInstitucion());
			st.setLong(i++, item.getCapCodigo());
			st.setString(i++, item.getCapNombreFormacion());
			st.setLong(i++, item.getCapDocentes());
			st.setInt(i++, item.getCapUniversidad());
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
		return item;
	}

	public CapacitacionVO eliminarCapacitacion(CapacitacionVO item)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb
					.getString("desarrolloc.capacitacion.eliminar"));
			// CADOCODINST=? and CADOCODFORMAC=?
			st.setLong(i++, item.getCapInstitucion());
			st.setLong(i++, item.getCapCodigo());
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
		return item;
	}

	public List getListaEtapaProyecto() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.ETAPAPROY_PEI_FORMULACION,
				ParamsVO.ETAPAPROY_PEI_FORMULACION_));
		lista.add(new ItemVO(ParamsVO.ETAPAPROY_PEI_SOCIALIZACION,
				ParamsVO.ETAPAPROY_PEI_SOCIALIZACION_));
		lista.add(new ItemVO(ParamsVO.ETAPAPROY_PEI_IMPLEMENTACION,
				ParamsVO.ETAPAPROY_PEI_IMPLEMENTACION_));
		lista.add(new ItemVO(ParamsVO.ETAPAPROY_PEI_CONSOLIDACION,
				ParamsVO.ETAPAPROY_PEI_CONSOLIDACION_));
		return lista;
	}

	public DesarrolloAdministrativoVO getDesarrolloAdministrativo(
			long institucion, boolean disabled) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DesarrolloAdministrativoVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloa.get"));
			st.setLong(1, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new DesarrolloAdministrativoVO();
				item.setEdicion(true);
				// PEICODINST,PEIESTADO,PEIPOACONSTRUCCION,PEIPOACOMUNIDADESC,
				// PEIPOATOMADECISION,PEIEICONSTRUCCION,PEIEITOMADECISION,
				// PEIEFRECUENCIA,PEIHRRORIENTSED,PEIHRROTROSDOCS,
				// PEIHRRCUALES,PEIPRODADMIN,PEIPRODADMINCDIFICULTAD,
				// PEIMANUALFUNC,PEIMANUALFUNCDIFICULTAD,PEIPROCINVENT,
				// PEIPROCINVENTDIFICULTAD,PEISISTINF,PEISISTINFDIFICULTAD,
				// PEIESTCOMUNIC,PEIESTCOMUNICDIFICULTAD
				item.setDesInstitucion(rs.getLong(i++));
				item.setDesEstado(rs.getInt(i++));
				item.setDesEstadoNombre(getEstadoPEI(item.getDesEstado()));

				item.setDesPoaConstruccion(rs.getInt(i++));
				item.setDesPoaComunidad(rs.getInt(i++));
				item.setDesPoaDecisiones(rs.getInt(i++));
				item.setDesEiConstruccion(rs.getInt(i++));
				item.setDesEiDecisiones(rs.getInt(i++));
				item.setDesEiFrecuencia(rs.getInt(i++));
				item.setDesEiOrientacion(rs.getInt(i++));
				item.setDesEiDocumento(rs.getInt(i++));
				item.setDesEiDocumentoCual(rs.getString(i++));

				item.setDesEtapaAdmin(rs.getInt(i++));
				item.setDesDificultadAdmin(rs.getString(i++));
				item.setDesEtapaManual(rs.getInt(i++));
				item.setDesDificultadManual(rs.getString(i++));
				item.setDesEtapaInventario(rs.getInt(i++));
				item.setDesDificultadInventario(rs.getString(i++));
				item.setDesEtapaEscolar(rs.getInt(i++));
				item.setDesDificultadEscolar(rs.getString(i++));
				item.setDesEtapaComunicacion(rs.getInt(i++));
				item.setDesDificultadComunicacion(rs.getString(i++));
				
				/* Ajustes Junio 2013*/
				item.setDesManualConvivencia(rs.getInt(i++));
				item.setDesReglamentoDocentes(rs.getInt(i++));
				item.setDesGobiernoEscolar(rs.getInt(i++));
				item.setDesDocumentoMatricula(rs.getInt(i++));
				item.setDesRelacionOrganizaciones(rs.getInt(i++));
				item.setDesOtrasOrganizaciones(rs.getString(i++));
				item.setDesPorcentajeMecanismos(rs.getInt(i++));
				item.setDesPorcentajeExpresiones(rs.getInt(i++));
				item.setDesExpresiones(rs.getString(i++));
				item.setDesPorcentajeCriteriosOrgAdmin(rs.getInt(i++));
				item.setDesfechaUltimaActualizacion(rs.getString(i++));
				/* ***************** */
				
				if (disabled) {
					item.setDesDisabled(true);
					item.setDesDisabled_(ParamsVO.DISABLED);
				}
				if (item.getDesEstado() == ParamsVO.ESTADO_PEI_COMPLETO) {
					item.setDesDisabled(true);
					item.setDesDisabled_(ParamsVO.DISABLED);
				}
			} else {// se debe construir con los valores por defecto
				item = new DesarrolloAdministrativoVO();
				item.setDesInstitucion(institucion);
				item.setDesEstado(ParamsVO.ESTADO_PEI_SIN_INICIAR);
				item.setDesEstadoNombre(ParamsVO.ESTADO_PEI_SIN_INICIAR_);
				item.setDesDisabled(true);
				item.setDesDisabled_(ParamsVO.DISABLED);
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

	public DesarrolloAdministrativoVO actualizarDesarrolloAdministrativo(
			DesarrolloAdministrativoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloa.actualizar"));
			// PEIPOACONSTRUCCION=?,PEIPOACOMUNIDADESC=?,PEIPOATOMADECISION=?,
			// PEIEICONSTRUCCION=?,PEIEITOMADECISION=?,PEIEFRECUENCIA=?,
			// PEIHRRORIENTSED=?,PEIHRROTROSDOCS=?,PEIHRRCUALES=?,
			// PEIPRODADMIN=?,PEIPRODADMINCDIFICULTAD=?,PEIMANUALFUNC=?,
			// PEIMANUALFUNCDIFICULTAD=?,PEIPROCINVENT=?,PEIPROCINVENTDIFICULTAD=?,
			// PEISISTINF=?,PEISISTINFDIFICULTAD=?,PEIESTCOMUNIC=?,
			// PEIESTCOMUNICDIFICULTAD=?
			// WHERE PEICODINST=?
			st.setInt(i++, item.getDesPoaConstruccion());
			st.setInt(i++, item.getDesPoaComunidad());
			st.setInt(i++, item.getDesPoaDecisiones());
			st.setInt(i++, item.getDesEiConstruccion());
			st.setInt(i++, item.getDesEiDecisiones());
			st.setInt(i++, item.getDesEiFrecuencia());
			st.setInt(i++, item.getDesEiOrientacion());
			st.setInt(i++, item.getDesEiDocumento());
			if (item.getDesEiDocumentoCual() == null
					|| item.getDesEiDocumentoCual().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesEiDocumentoCual());

			st.setInt(i++, item.getDesEtapaAdmin());
			if (item.getDesDificultadAdmin() == null
					|| item.getDesDificultadAdmin().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultadAdmin());
			st.setInt(i++, item.getDesEtapaManual());
			if (item.getDesDificultadManual() == null
					|| item.getDesDificultadManual().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultadManual());
			st.setInt(i++, item.getDesEtapaInventario());
			if (item.getDesDificultadInventario() == null
					|| item.getDesDificultadInventario().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultadInventario());
			st.setInt(i++, item.getDesEtapaEscolar());
			if (item.getDesDificultadEscolar() == null
					|| item.getDesDificultadEscolar().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultadEscolar());
			st.setInt(i++, item.getDesEtapaComunicacion());
			if (item.getDesDificultadComunicacion() == null
					|| item.getDesDificultadComunicacion().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesDificultadComunicacion());
			
			/* Ajustes Junio 2013 */
			
			st.setInt(i++, item.getDesManualConvivencia());
			st.setInt(i++, item.getDesReglamentoDocentes());
			st.setInt(i++, item.getDesGobiernoEscolar());
			st.setInt(i++, item.getDesDocumentoMatricula());
			st.setInt(i++, item.getDesRelacionOrganizaciones());
			
			if (item.getDesOtrasOrganizaciones() == null || item.getDesOtrasOrganizaciones().trim().equals(""))
				st.setNull(i++, Types.VARCHAR);
			else
				st.setString(i++, item.getDesOtrasOrganizaciones());
			
			st.setInt(i++, item.getDesPorcentajeMecanismos());
			st.setInt(i++, item.getDesPorcentajeExpresiones());
			st.setString(i++, item.getDesExpresiones());
			st.setInt(i++, item.getDesPorcentajeCriteriosOrgAdmin());
			
			/* ****************** */
			
			// w
			st.setLong(i++, item.getDesInstitucion());
			st.executeUpdate();
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

	public List getListaFrecuencia() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_POCO,
				ParamsVO.FRECUENCIA_POCO_));
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_ALGUNO,
				ParamsVO.FRECUENCIA_ALGUNO_));
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_FRECUENTE,
				ParamsVO.FRECUENCIA_FRECUENTE_));
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_SIEMPRE,
				ParamsVO.FRECUENCIA_SIEMPRE_));
		return lista;
	}

	public List getListaManualConvivencia() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_MANUAL_CONVIVENCIA);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	public List getListaReglamentoDocentes() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_REGLAMENTO_DOCENTES);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	public List getListaGobiernoEscolar() throws Exception {
		List lista = new ArrayList();
		/* Ajustes Junio 2013 */
		List listaParametrosHijo = getListaParametroHijo(ParamsVO.PARAM_PADRE_GOBIERNO_ESCOLAR);
		if(listaParametrosHijo != null && listaParametrosHijo.size() > 0){
			
			for(int i = 0 ; i < listaParametrosHijo.size() ; i++){
				ParametroHijoVO parametroHijoActual = (ParametroHijoVO) listaParametrosHijo.get(i);
				lista.add(new ItemVO(parametroHijoActual.getCodigo(),parametroHijoActual.getNombre()));
			}
		}
		
		return lista;
	}
	
	public List getListaFrecuenciaEvaluacion() throws Exception {
		List lista = new ArrayList();
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_EVALUACION_SEMESTRAL,
				ParamsVO.FRECUENCIA_EVALUACION_SEMESTRAL_));
		lista.add(new ItemVO(ParamsVO.FRECUENCIA_EVALUACION_ANUAL,
				ParamsVO.FRECUENCIA_EVALUACION_ANUAL_));
		return lista;
	}

	/**
	 * @param padre
	 * @return
	 * @throws Exception
	 */
	public List getListaInstitucion(int localidad) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("pei.listaInstitucion"));
			st.setInt(1, localidad);
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

	public List getListaLocalidad() throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("pei.listaLocalidad"));
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

	public String getNombreInstitucion(long codigo) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("pei.getNombreInstitucion"));
			st.setLong(1, codigo);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
		return null;
	}

	public FiltroRegistroVO getLabels(FiltroRegistroVO f) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("pei.listaParametroPadre"));
			rs = st.executeQuery();
			while (rs.next()) {
				switch (rs.getInt(1)) {
				case 1:
					f.setLblEtapa(rs.getString(3));
					break;
				case 2:
					f.setLblEnfasis(rs.getString(3));
					break;
				case 3:
					f.setLblEnfoque(rs.getString(3));
					break;
				case 4:
					f.setLblFase(rs.getString(3));
					break;
				case 5:
					f.setLblPreparacion(rs.getString(3));
					break;
				case 6:
					f.setLblFormulacion(rs.getString(3));
					break;
				case 7:
					f.setLblEjecucion(rs.getString(3));
					break;
				case 8:
					f.setLblSeguimiento(rs.getString(3));
					break;
				case 9:
					f.setLblPrograma(rs.getString(3));
					break;
				case 10:
					f.setLblUniversidad(rs.getString(3));
					break;
				case 11:
					f.setLblProyecto(rs.getString(3));
					break;
				}
			}
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return f;
	}

	public List getListaDocumento(IdentificacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		DocumentoVO doc = null;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("documento.getLista"));
			st.setLong(1, item.getIdenInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// DOCSCODINST, DOCSCODIGO, DOCSRUTA,DOCSNOMBRE,
				// DOCSDESCRIPCION, DOCSUSARIO, to_char(DOCSFECHA,'dd/mm/yyyy')
				// FROM PEI_INST_DOCS where DOCSCODINST=? order by DOCSFECHA
				doc = new DocumentoVO();
				i = 1;
				doc.setDocInstitucion(rs.getLong(i++));
				doc.setDocCodigo(rs.getInt(i++));
				doc.setDocRuta(rs.getString(i++));
				doc.setDocNombre(rs.getString(i++));
				doc.setDocDescripcion(rs.getString(i++));
				doc.setDocUsuario(rs.getLong(i++));
				doc.setDocFecha(rs.getString(i++));
				lista.add(doc);
			}
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return lista;
	}

	public DocumentoVO getDocumento(DocumentoVO documentoVO) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		DocumentoVO item = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("documento.get"));
			st.setLong(1, documentoVO.getDocInstitucion());
			st.setLong(2, documentoVO.getDocCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item = new DocumentoVO();
				item.setEdicion(true);
				// DOCSCODINST, DOCSCODIGO, DOCSRUTA,DOCSNOMBRE,
				// DOCSDESCRIPCION, DOCSUSARIO,to_char(DOCSFECHA,'dd/mm/yyyy')
				item.setDocInstitucion(rs.getLong(i++));
				item.setDocCodigo(rs.getInt(i++));
				item.setDocRuta(rs.getString(i++));
				item.setDocNombre(rs.getString(i++));
				item.setDocDescripcion(rs.getString(i++));
				item.setDocUsuario(rs.getLong(i++));
				item.setDocFecha(rs.getString(i++));
				item.setDocUsuarioNombre(rs.getString(i++));
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

	public DocumentoVO eliminarDocumento(DocumentoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular la ruta del documento
			st = cn.prepareStatement(rb.getString("documento.getRuta"));
			st.setLong(i++, item.getDocInstitucion());
			st.setLong(i++, item.getDocCodigo());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setDocRuta(rs.getString(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("documento.eliminar"));
			i = 1;
			st.setLong(i++, item.getDocInstitucion());
			st.setLong(i++, item.getDocCodigo());
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
		return item;
	}

	public DocumentoVO ingresarDocumento(DocumentoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("documento.id"));
			st.setLong(i++, item.getDocInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setDocCodigo(rs.getInt(1));
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("documento.insertar"));
			// DOCSCODINST, DOCSCODIGO, DOCSRUTA,
			// DOCSNOMBRE, DOCSDESCRIPCION, DOCSUSARIO,DOCSFECHA
			st.setLong(i++, item.getDocInstitucion());
			st.setInt(i++, item.getDocCodigo());
			st.setString(i++, item.getDocRuta());
			st.setString(i++, item.getDocNombre());
			st.setString(i++, item.getDocDescripcion());
			st.setLong(i++, item.getDocUsuario());
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
		return item;
	}

	public DocumentoVO actualizarDocumento(DocumentoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("documento.actualizar"));
			// DOCSNOMBRE=?, DOCSDESCRIPCION=?, DOCSUSARIO=?,
			// DOCSFECHA=to_date(?,'dd/mm/yyyy')
			st.setString(i++, item.getDocNombre());
			st.setString(i++, item.getDocDescripcion());
			st.setLong(i++, item.getDocUsuario());
			// w
			st.setLong(i++, item.getDocInstitucion());
			st.setLong(i++, item.getDocCodigo());
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
		return item;
	}

	public int getDocumentoId(long institucion) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("documento.id"));
			st.setLong(i++, institucion);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
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
		return 0;
	}
	
	/* Ajustes Junio 2013*/
	public List getListaCurriculo(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		CurriculoVO curriculo;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.curriculo.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				curriculo = new CurriculoVO();
				i = 1;
				curriculo.setCurCodigoInstitucion(rs.getLong(i++));
				curriculo.setCurConsecutivo(rs.getInt(i++));
				curriculo.setCurAlcance(rs.getString(i++));
				curriculo.setCurDificultad(rs.getString(i++));
				lista.add(curriculo);
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
		return lista;
	}
	
	public CurriculoVO ingresarCurriculo(CurriculoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.curriculo.id"));
			st.setLong(i++, item.getCurCodigoInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setCurConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.curriculo.insertar"));
			st.setLong(i++, item.getCurCodigoInstitucion());
			st.setLong(i++, item.getCurConsecutivo());
			st.setString(i++, item.getCurAlcance());
			st.setString(i++, item.getCurDificultad());
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
		return item;
	}

	public CurriculoVO actualizarOtroProyecto(CurriculoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.otroProyecto.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getCurAlcance());
			st.setString(i++, item.getCurDificultad());
			// w
			st.setLong(i++, item.getCurCodigoInstitucion());
			st.setInt(i++, item.getCurConsecutivo());
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
		return item;
	}

	public CurriculoVO eliminarCurriculo(CurriculoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.curriculo.eliminar"));
			st.setLong(i++, item.getCurCodigoInstitucion());
			st.setInt(i++, item.getCurConsecutivo());
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
		return item;
	}

	public CurriculoVO getOtroProyecto(CurriculoVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.curriculo..get"));
			st.setLong(1, item.getCurCodigoInstitucion());
			st.setLong(2, item.getCurConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setCurCodigoInstitucion(rs.getLong(i++));
				item.setCurConsecutivo(rs.getInt(i++));
				item.setCurAlcance(rs.getString(i++));
				item.setCurDificultad(rs.getString(i++));
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
	
	/* Ciudadania*/
	
public List getListaCiudadania(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		CiudadaniaVO ciudadania;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				ciudadania = new CiudadaniaVO();
				i = 1;
				ciudadania.setCiuCodigoInstitucion(new Long(rs.getLong(i++)));
				ciudadania.setCiuConsecutivo(rs.getInt(i++));
				ciudadania.setCiuAlcance(rs.getString(i++));
				ciudadania.setCiuDificultad(rs.getString(i++));
				lista.add(ciudadania);
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
		return lista;
	}
	
	public CiudadaniaVO ingresarCiudadania(CiudadaniaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania.id"));
			st.setLong(i++, item.getCiuCodigoInstitucion().longValue());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setCiuConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania.insertar"));
			st.setLong(i++, item.getCiuCodigoInstitucion().longValue());
			st.setLong(i++, item.getCiuConsecutivo());
			st.setString(i++, item.getCiuAlcance());
			st.setString(i++, item.getCiuDificultad());
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
		return item;
	}

	public CiudadaniaVO actualizarCiudadania(CiudadaniaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getCiuAlcance());
			st.setString(i++, item.getCiuDificultad());
			// w
			st.setLong(i++, item.getCiuCodigoInstitucion().longValue());
			st.setInt(i++, item.getCiuConsecutivo());
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
		return item;
	}

	public CiudadaniaVO eliminarCiudadania(CiudadaniaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania.eliminar"));
			st.setLong(i++, item.getCiuCodigoInstitucion().longValue());
			st.setInt(i++, item.getCiuConsecutivo());
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
		return item;
	}

	public CiudadaniaVO getCiudadania(CiudadaniaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.ciudadania..get"));
			st.setLong(1, item.getCiuCodigoInstitucion().longValue());
			st.setLong(2, item.getCiuConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setCiuCodigoInstitucion(new Long(rs.getLong(i++)));
				item.setCiuConsecutivo(rs.getInt(i++));
				item.setCiuAlcance(rs.getString(i++));
				item.setCiuDificultad(rs.getString(i++));
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
	
	
	/* Poblaciones */
	
public List getListaPoblaciones(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		PoblacionesVO poblaciones;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				poblaciones = new PoblacionesVO();
				i = 1;
				poblaciones.setPobCodigoInstitucion(rs.getLong(i++));
				poblaciones.setPobConsecutivo(rs.getInt(i++));
				poblaciones.setPobLineaPolitica(rs.getString(i++));
				poblaciones.setPobEstudiantes(rs.getInt(i++));
				lista.add(poblaciones);
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
		return lista;
	}
	
	public PoblacionesVO ingresarPoblaciones(PoblacionesVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.id"));
			st.setLong(i++, item.getPobCodigoInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setPobConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.insertar"));
			st.setLong(i++, item.getPobCodigoInstitucion());
			st.setLong(i++, item.getPobConsecutivo());
			st.setString(i++, item.getPobLineaPolitica());
			st.setInt(i++, item.getPobEstudiantes());
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
		return item;
	}

	public PoblacionesVO actualizarPoblaciones(PoblacionesVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getPobLineaPolitica());
			st.setInt(i++, item.getPobEstudiantes());
			// w
			st.setLong(i++, item.getPobCodigoInstitucion());
			st.setInt(i++, item.getPobConsecutivo());
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
		return item;
	}

	public PoblacionesVO eliminarPoblaciones(PoblacionesVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.eliminar"));
			st.setLong(i++, item.getPobCodigoInstitucion());
			st.setInt(i++, item.getPobConsecutivo());
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
		return item;
	}

	public PoblacionesVO getPoblaciones(PoblacionesVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.poblaciones.get"));
			st.setLong(1, item.getPobCodigoInstitucion());
			st.setLong(2, item.getPobConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setPobCodigoInstitucion(rs.getLong(i++));
				item.setPobConsecutivo(rs.getInt(i++));
				item.setPobLineaPolitica(rs.getString(i++));
				item.setPobEstudiantes(rs.getInt(i++));
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
	
	
	/* Primera Infancia */
	
	
public List getListaPrimeraInfancia(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		List lista = new ArrayList();
		List listaGuardada = new ArrayList();
		
		PrimeraInfanciaVO primeraInfancia;
		try {
			if(item.getDesEstado() == 1){
				st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.vaciarDatosInstitucion"));
				st.setLong(1, item.getDesInstitucion());
				st.executeUpdate();
				
				st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.llenarPrimeraInfancia"));
				st.setLong(1, item.getDesInstitucion());
				st.setLong(2, item.getDesInstitucion());
				rs = st.executeQuery();
				String nombreGradoAnterior="";
				primeraInfancia = null;
				while (rs.next()) {
					i=1;
					if(!rs.getString(i).equalsIgnoreCase(nombreGradoAnterior)){
						if(primeraInfancia != null){
							primeraInfancia.setPriCodigoInstitucion(item.getDesInstitucion());
							lista.add(primeraInfancia);
						}
						
						primeraInfancia = new PrimeraInfanciaVO();
						primeraInfancia.setPriGrado(rs.getString(i++));
						
						nombreGradoAnterior = primeraInfancia.getPriGrado();
						
						primeraInfancia.setPriCursos(1);
						primeraInfancia.setPriEstudiantes(new Long(rs.getLong(i++)));
					}else{
						primeraInfancia.setPriCursos(primeraInfancia.getPriCursos()+1);
						long estudiantes = rs.getLong(2)+primeraInfancia.getPriEstudiantes().longValue();
						primeraInfancia.setPriEstudiantes(new Long(estudiantes));
					}
					
				}
				if(!rs.next()){
					primeraInfancia.setPriCodigoInstitucion(item.getDesInstitucion());
					lista.add(primeraInfancia);
				}
				if(lista != null && lista.size()>0){
					for(int j = 0; j<lista.size();j++){
						listaGuardada.add(ingresarPrimeraInfancia((PrimeraInfanciaVO)lista.get(j)));
					}
				}
			}else{
				st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.getLista"));
				st.setLong(1, item.getDesInstitucion());
				rs = st.executeQuery();
				while (rs.next()) {
					// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
					primeraInfancia = new PrimeraInfanciaVO();
					i = 1;
					primeraInfancia.setPriCodigoInstitucion(rs.getLong(i++));
					primeraInfancia.setPriConsecutivo(rs.getInt(i++));
					primeraInfancia.setPriGrado(rs.getString(i++));
					primeraInfancia.setPriCursos(rs.getInt(i++));
					primeraInfancia.setPriEstudiantes(new Long(rs.getLong(i++)));
					lista.add(primeraInfancia);
				}
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
		if(item.getDesEstado()==1){
			return listaGuardada;
		}else{
			return lista;
		}
			
	}
	
	public PrimeraInfanciaVO ingresarPrimeraInfancia(PrimeraInfanciaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.id"));
			st.setLong(i++, item.getPriCodigoInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setPriConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.insertar"));
			st.setLong(i++, item.getPriCodigoInstitucion());
			st.setLong(i++, item.getPriConsecutivo());
			st.setString(i++, item.getPriGrado());
			st.setInt(i++, item.getPriCursos());
			st.setLong(i++, item.getPriEstudiantes().longValue());
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
		return item;
	}

	public PrimeraInfanciaVO actualizarPrimeraInfancia(PrimeraInfanciaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getPriGrado());
			st.setInt(i++, item.getPriCursos());
			st.setLong(i++, item.getPriEstudiantes().longValue());
			// w
			st.setLong(i++, item.getPriCodigoInstitucion());
			st.setLong(i++, item.getPriConsecutivo());
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
		return item;
	}

	public PrimeraInfanciaVO eliminarPrimeraInfancia(PrimeraInfanciaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.eliminar"));
			st.setLong(i++, item.getPriCodigoInstitucion());
			st.setLong(i++, item.getPriConsecutivo());
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
		return item;
	}

	public PrimeraInfanciaVO getPrimeraInfancia(PrimeraInfanciaVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.primeraInfancia.get"));
			st.setLong(i++, item.getPriCodigoInstitucion());
			st.setLong(i++, item.getPriConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setPriCodigoInstitucion(rs.getLong(i++));
				item.setPriConsecutivo(rs.getInt(i++));
				item.setPriGrado(rs.getString(i++));
				item.setPriCursos(rs.getInt(i++));
				item.setPriEstudiantes(new Long(rs.getLong(i++)));
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

	
	

	/* Proyecto 40 Horas */
	

public List getListaProyecto40Horas(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		Proyecto40HorasVO proyecto40Horas;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				proyecto40Horas = new Proyecto40HorasVO();
				i = 1;
				proyecto40Horas.setHorCodigoInstitucion(rs.getLong(i++));
				proyecto40Horas.setHorConsecutivo(rs.getInt(i++));
				proyecto40Horas.setHorCentro(rs.getString(i++));
				proyecto40Horas.setHorEstudiantes(rs.getInt(i++));
				lista.add(proyecto40Horas);
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
		return lista;
	}
	
	public Proyecto40HorasVO ingresarProyecto40Horas(Proyecto40HorasVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.id"));
			st.setLong(i++, item.getHorCodigoInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setHorConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.insertar"));
			st.setLong(i++, item.getHorCodigoInstitucion());
			st.setLong(i++, item.getHorConsecutivo());
			st.setString(i++, item.getHorCentro());
			st.setInt(i++, item.getHorEstudiantes());
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
		return item;
	}

	public Proyecto40HorasVO actualizarProyecto40Horas(Proyecto40HorasVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getHorCentro());
			st.setInt(i++, item.getHorEstudiantes());
			// w
			st.setLong(i++, item.getHorCodigoInstitucion());
			st.setInt(i++, item.getHorConsecutivo());
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
		return item;
	}

	public Proyecto40HorasVO eliminarProyecto40Horas(Proyecto40HorasVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.eliminar"));
			st.setLong(i++, item.getHorCodigoInstitucion());
			st.setInt(i++, item.getHorConsecutivo());
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
		return item;
	}

	public Proyecto40HorasVO getProyecto40Horas(Proyecto40HorasVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.proyecto40Horas.get"));
			st.setLong(1, item.getHorCodigoInstitucion());
			st.setLong(2, item.getHorConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setHorCodigoInstitucion(rs.getLong(i++));
				item.setHorConsecutivo(rs.getInt(i++));
				item.setHorCentro(rs.getString(i++));
				item.setHorEstudiantes(rs.getInt(i++));
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
	
	
	
	
	/* Criterio Evaluacion */
	
public List getListaCriterioEvaluacion(DesarrolloCurricularVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		CriterioEvaluacionVO criterioEvaluacion;
		try {
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.getLista"));
			st.setLong(1, item.getDesInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				criterioEvaluacion = new CriterioEvaluacionVO();
				i = 1;
				criterioEvaluacion.setCriCodigoInstitucion(rs.getLong(i++));
				criterioEvaluacion.setCriConsecutivo(rs.getInt(i++));
				criterioEvaluacion.setCriAlcance(rs.getString(i++));
				criterioEvaluacion.setCriDificultad(rs.getString(i++));
				lista.add(criterioEvaluacion);
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
		return lista;
	}
	
	public CriterioEvaluacionVO ingresarCriterioEvaluacion(CriterioEvaluacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.id"));
			st.setLong(i++, item.getCriCodigoInstitucion());
			rs = st.executeQuery();
			if (rs.next()) {
				item.setCriConsecutivo(rs.getInt(1)+1);
			}
			rs.close();
			st.close();
			i = 1;
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.insertar"));
			st.setLong(i++, item.getCriCodigoInstitucion());
			st.setLong(i++, item.getCriConsecutivo());
			st.setString(i++, item.getCriAlcance());
			st.setString(i++, item.getCriDificultad());
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
		return item;
	}

	public CriterioEvaluacionVO actualizarCriterioEvaluacion(CriterioEvaluacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.actualizar"));
			// OPRONOMBRE=?, OPROALCANCE=?, OPRODIFICULTAD=?,
			// OPROENTIDAD=? where OPROCODINST=? and OPROCODPROYECTO=?
			st.setString(i++, item.getCriAlcance());
			st.setString(i++, item.getCriDificultad());
			// w
			st.setLong(i++, item.getCriCodigoInstitucion());
			st.setInt(i++, item.getCriConsecutivo());
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
		return item;
	}

	public CriterioEvaluacionVO eliminarCriterioEvaluacion(CriterioEvaluacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.eliminar"));
			st.setLong(i++, item.getCriCodigoInstitucion());
			st.setInt(i++, item.getCriConsecutivo());
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
		return item;
	}

	public CriterioEvaluacionVO getCriterioEvaluacion(CriterioEvaluacionVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("desarrolloc.criterioEvaluacion.get"));
			st.setLong(1, item.getCriCodigoInstitucion());
			st.setLong(2, item.getCriConsecutivo());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// OPROCODINST, OPROCODPROYECTO, OPRONOMBRE,
				// OPROALCANCE, OPRODIFICULTAD, OPROENTIDAD
				item.setCriCodigoInstitucion(rs.getLong(i++));
				item.setCriConsecutivo(rs.getInt(i++));
				item.setCriAlcance(rs.getString(i++));
				item.setCriDificultad(rs.getString(i++));
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
	

	
	
	
	/* Ajuste */
	
public List getListaAjuste(IdentificacionVO item)throws Exception {
		
		Connection cn = cursor.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		List lista = new ArrayList();
		AjusteVO ajuste;
		try {
			st = cn.prepareStatement(rb.getString("identificacion.ajuste.getLista"));
			st.setLong(1, item.getIdenInstitucion());
			rs = st.executeQuery();
			while (rs.next()) {
				// PRGSCODINST, PRGSCODPROG, PRGSNUMESTUD,PARHNOMBRE
				ajuste = new AjusteVO();
				i = 1;
				ajuste.setAjuCodigoInstitucion(new Long(rs.getLong(i++)));
				ajuste.setAjuResolucion(new Long(rs.getLong(i++)));
				ajuste.setAjuVigencia(rs.getInt(i++));
				ajuste.setAjuAjuste(rs.getString(i++));
				ajuste.setAjuFecha(rs.getString(i++));
				ajuste.setAjuUsuario(rs.getString(i++));
				lista.add(ajuste);
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
		return lista;
	}
	
	public AjusteVO ingresarAjuste(AjusteVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			// calcular el codigo
			i = 1;
			st = cn.prepareStatement(rb.getString("identificacion.ajuste.insertar"));
			st.setLong(i++, item.getAjuCodigoInstitucion().longValue());
			st.setLong(i++, item.getAjuResolucion().longValue());
			st.setInt(i++, item.getAjuVigencia());
			st.setString(i++, item.getAjuAjuste());
			st.setString(i++, item.getAjuUsuario());
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
		return item;
	}

	public AjusteVO actualizarAjuste(AjusteVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("identificacion.ajuste.actualizar"));
			//PEIVIGENCIA=?, PEIAJUSTE=?, PEIFECHA=sysdate, PEIUSUARIO=?
			st.setInt(i++, item.getAjuVigencia());
			st.setString(i++, item.getAjuAjuste());
			st.setString(i++, item.getAjuUsuario());
			// w
			st.setLong(i++, item.getAjuCodigoInstitucion().longValue());
			st.setLong(i++, item.getAjuResolucion().longValue());
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
		return item;
	}

	public AjusteVO eliminarAjuste(AjusteVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("identificacion.ajuste.eliminar"));
			st.setLong(i++, item.getAjuCodigoInstitucion().longValue());
			st.setLong(i++, item.getAjuResolucion().longValue());
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
		return item;
	}

	public AjusteVO getAjuste(AjusteVO item) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("identificacion.ajuste..get"));
			st.setLong(1, item.getAjuCodigoInstitucion().longValue());
			st.setLong(2, item.getAjuResolucion().longValue());
			rs = st.executeQuery();
			if (rs.next()) {
				i = 1;
				item.setEdicion(true);
				// PEICODINST, PEIRESOLUCION, PEIVIGENCIA, PEIAJUSTE, to_char(PEIFECHA,'dd/mm/yyyy'), PEIUSUARIO
				item.setAjuCodigoInstitucion(new Long(rs.getLong(i++)));
				item.setAjuResolucion(new Long(rs.getInt(i++)));
				item.setAjuVigencia(rs.getInt(i++));
				item.setAjuAjuste(rs.getString(i++));
				item.setAjuFecha(rs.getString(i++));
				item.setAjuFecha(rs.getString(i++));
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
	
	
	
}
