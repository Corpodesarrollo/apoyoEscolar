package siges.plantilla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.login.beans.Login;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.ParamsVO;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Logger;
import siges.util.Properties;

/**
 * siges.plantilla<br>
 * Funcinn: Genera los datos de las plantillas para las solicitudes de
 * plantillas masivas y llama a la clase que genera las plantillas fisicas <br>
 */
public class PlantillaUtil {
	private FiltroPlantilla filtroPlantilla;

	private String path;

	private PlantillaDAO plantillaDAO;

	private Collection list;

	private Collection[] col;

	private Object[] o;

	private int tipo;

	private String[] ubicacion;

	private String[] ubicacionPlantilla;

	private ResourceBundle rb;

	private java.sql.Timestamp f2;

	/**
	 * @param l
	 * @param c
	 * @param u
	 * @param f
	 * @param ub
	 * @param up
	 */
	public PlantillaUtil(PlantillaDAO u, FiltroPlantilla f, String p, int t) {
		plantillaDAO = u;
		filtroPlantilla = f;
		path = p;
		tipo = t;
		rb = ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		ubicacion = getUbicacionZip(filtroPlantilla.getUsuarioPlantilla(), filtroPlantilla.getNombrePlantilla(), tipo, path);
		ubicacionPlantilla = getUbicacionPlantilla(filtroPlantilla.getUsuarioPlantilla(), tipo, path);
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());
		col = new Collection[4];
		ponerNombres();
	}

	public void ponerNombres() {
		String a[] = plantillaDAO.getNombres(filtroPlantilla);
		if (a != null) {
			filtroPlantilla.setInstitucion_(a[0]);
			filtroPlantilla.setSede_(a[1]);
			filtroPlantilla.setJornada_(a[2]);
		}
	}

	/**
	 * @param id
	 * @return<br>
	 *         Return Type: String[]<br>
	 *         Version 1.1.<br>
	 */
	public String[] getUbicacionPlantilla(String id, int tipo, String p) {
		String path;
		String archivo = null;
		String[] ubicacionPlantilla;
		ubicacionPlantilla = new String[4];
		int i = 0;
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:
			path = rb.getString("asignatura.PathAsignatura") + "." + id;
			ubicacionPlantilla[i++] = Ruta2.get(p, rb.getString("asignatura.PathPlantilla"));// path
																								// de
																								// la
																								// plantilla
			ubicacionPlantilla[i++] = rb.getString("asignatura.Plantilla");// nombre
																			// de
																			// la
																			// plantilla
			ubicacionPlantilla[i++] = Ruta.get(p, path);// path del nuevo
														// archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		case Properties.PLANTILLAAREADESC:
			path = rb.getString("area.PathArea") + "." + id;
			ubicacionPlantilla[i++] = Ruta2.get(p, rb.getString("area.PathPlantilla"));// path
																						// de
																						// la
																						// plantilla
			ubicacionPlantilla[i++] = rb.getString("area.Plantilla");// nombre
																		// de la
																		// plantilla
			ubicacionPlantilla[i++] = Ruta.get(p, path);// path del nuevo
														// archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		case Properties.PLANTILLAPREE:
			path = rb.getString("preescolar.PathPreescolar") + "." + id;
			ubicacionPlantilla[i++] = Ruta2.get(p, rb.getString("preescolar.PathPlantilla"));// path
																								// de
																								// la
																								// plantilla
			ubicacionPlantilla[i++] = rb.getString("preescolar.Plantilla");// nombre
																			// de
																			// la
																			// plantilla
			ubicacionPlantilla[i++] = Ruta.get(p, path);// path del nuevo
														// archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		case Properties.PLANTILLARECUPERACION:
			path = rb.getString("recuperacion.PathRecuperacion") + "." + id;
			ubicacionPlantilla[i++] = Ruta2.get(p, rb.getString("recuperacion.PathPlantilla"));// path
																								// de
																								// la
																								// plantilla
			ubicacionPlantilla[i++] = rb.getString("recuperacion.Plantilla");// nombre
																				// de
																				// la
																				// plantilla
			ubicacionPlantilla[i++] = Ruta.get(p, path);// path del nuevo
														// archivo
			ubicacionPlantilla[i++] = archivo;// nombre del nuevo archivo
			break;
		}
		return ubicacionPlantilla;
	}

	/**
	 * @param id
	 * @param t
	 * @return<br>
	 *         Return Type: String[]<br>
	 *         Version 1.1.<br>
	 */
	public String[] getUbicacionZip(String id, String nombre, int tipo, String p) {
		String path;
		String archivo = nombre;
		String relativo = null;
		String ruta = null;
		String[] ubicacion = new String[3];
		switch (tipo) {
		case Properties.PLANTILLALOGROASIG:
			path = rb.getString("asignatura.PathAsignatura") + "." + id;
			relativo = Ruta.getRelativo(p, path);
			ruta = Ruta.get(p, path);// path del nuevo archivo
			break;
		case Properties.PLANTILLAAREADESC:
			path = rb.getString("area.PathArea") + "." + id;
			relativo = Ruta.getRelativo(p, path);
			ruta = Ruta.get(p, path);// path del nuevo archivo
			break;
		case Properties.PLANTILLAPREE:
			path = rb.getString("preescolar.PathPreescolar") + "." + id;
			relativo = Ruta.getRelativo(p, path);
			ruta = Ruta.get(p, path);// path del nuevo archivo
			break;
		case Properties.PLANTILLARECUPERACION:
			path = rb.getString("recuperacion.PathRecuperacion") + "." + id;
			relativo = Ruta.getRelativo(p, path);
			ruta = Ruta.get(p, path);// path del nuevo archivo
			break;
		}
		ubicacion[0] = archivo;
		ubicacion[1] = relativo;
		ubicacion[2] = ruta;
		return ubicacion;
	}

	/**
	 * @throws Exception
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaLogroAsigZip() throws Exception {
		System.out.println("ENTOR A getEscalaNivelEval zip ");
		boolean band, band2;
		//int n[] = { 0 };
		//String nivel = "1";
		//String[] est;
		//String[] jer = new String[1];
		String params[][] = null;
		String params_[][] = null;
		Collection files = new ArrayList();
		ItemVO item=null,item2=null;
		try {
			// NO SE ESCOGIO NADA
			// se escogio solo metodologia y grado
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				// consulta para traer grupos
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				// cnsulta para traer asignaturas
				List asignaturas=plantillaDAO.getListaAsignaturas(filtroPlantilla);
				
				
				plantillaDAO.getEscalaNivelEval(filtroPlantilla);
				System.out.println("ZIP " + filtroPlantilla.getRangosCompletos());
				
				if (filtroPlantilla.getRangosCompletos() != 1){
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, " No se puede generar la plantilla porque ann no se " +
							   "\n ha especificado n esta incompleta la escala valorativa" +
							   "\n para este grupo.");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, " No se puede generar la plantilla porque ann no se " +
							   "\n ha especificado n esta incompleta la escala valorativa" +
							   "\n para este grupo.");
				
					return;
				}
				
				
				if (grupos.size()==0 || asignaturas == null) {
					if (grupos.size()==0) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					} else if (asignaturas == null) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					}
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay Grupos o asignaturas definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[grupos.size()* asignaturas.size()][4];
					params_ = new String[grupos.size() * asignaturas.size()][4];
				}else{
					params = new String[grupos.size()* asignaturas.size()][5];
					params_ = new String[grupos.size() * asignaturas.size()][5];
				}
				int r = 0;
				for (int p = 0; p < grupos.size(); p++) {
					item=(ItemVO)grupos.get(p);
					for (int q = 0; q < asignaturas.size(); q++) {
						item2=(ItemVO)asignaturas.get(q);
						params[r][0] = filtroPlantilla.getMetodologia();
						params[r][1] = filtroPlantilla.getGrado();
						params[r][2] = String.valueOf(item.getCodigo());
						params[r][3] = String.valueOf(item2.getCodigo());
						params_[r][0] = filtroPlantilla.getMetodologia();
						params_[r][1] = filtroPlantilla.getGrado();
						params_[r][2] = item.getNombre();
						params_[r][3] = item2.getNombre();
						if(filtroPlantilla.getFilPlanEstudios()==1){
							params[r][4] = String.valueOf(item2.getPadre());
							params_[r][4] = item2.getPadre2();
						}
						r++;
					}
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y GRUPO
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && !filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				String grupo = filtroPlantilla.getGrupo().substring(filtroPlantilla.getGrupo().lastIndexOf("|") + 1, filtroPlantilla.getGrupo().length());
				// consulta para traer asignaturas
				List asignaturas=plantillaDAO.getListaAsignaturas(filtroPlantilla);
				if (asignaturas.size()==0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay Asignaturas definidas) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1,
						this.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[asignaturas.size()][4];
					params_ = new String[asignaturas.size()][4];
				}else{
					params = new String[asignaturas.size()][5];
					params_ = new String[asignaturas.size()][5];
				}
				int r = 0;
				for (int q = 0; q < asignaturas.size(); q++) {
					item=(ItemVO)asignaturas.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = grupo;
					params[r][3] = String.valueOf(item.getCodigo());
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = filtroPlantilla.getGrupo_();
					params_[r][3] = item.getNombre();
					if(filtroPlantilla.getFilPlanEstudios()==1){
						params[r][4] = String.valueOf(item.getPadre());
						params_[r][4] = item.getPadre2();
					}
					r++;
				}
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y ASIG
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && !filtroPlantilla.getAsignatura().equals("-3")) {
				String asig = filtroPlantilla.getAsignatura().substring(filtroPlantilla.getAsignatura().lastIndexOf('|') + 1, filtroPlantilla.getAsignatura().length());
				// consulta para traer grupos
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				if (grupos.size()==0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay grupos definidos");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay grupos definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Asig:" + asig + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
				params = new String[grupos.size()][4];
				params_ = new String[grupos.size()][4];
				int r = 0;
				for (int q = 0; q < grupos.size(); q++) {
					item=(ItemVO)grupos.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = String.valueOf(item.getCodigo());
					params[r][3] = asig;
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = item.getNombre();
					params_[r++][3] = filtroPlantilla.getAsignatura_();
				}
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Asig:" + asig + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			/* AREA GENERAL PARA TODAS LAS OPCIONES */
			int yyy = 0;
			col = new Collection[7];
			for (int p = 0; p < params.length; p++) {
				band = band2 = true;
				filtroPlantilla.setMetodologia(params[p][0]);
				filtroPlantilla.setMetodologia_(params_[p][0]);
				filtroPlantilla.setGrado(params[p][1]);
				filtroPlantilla.setGrado_(params_[p][1]);
				filtroPlantilla.setGrupo(params[p][2]);
				filtroPlantilla.setGrupo_(params_[p][2]);
				filtroPlantilla.setAsignatura(params[p][3]);
				filtroPlantilla.setAsignatura_(params_[p][3]);
				if(filtroPlantilla.getFilPlanEstudios()==1  && params[p].length==5){
					filtroPlantilla.setDocente(params[p][4]);
					filtroPlantilla.setDocente_(params_[p][4]);
				}
				// EVALUACION DE ASIGNATURA
				if (p == 0) {
					band = true;
					band2 = true;// NOTAS
					//col[1] = plantillaDAO.getEscala(2);
					col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla );
					col[5] = plantillaDAO.getEscala(1);
					
					//filtroPlantilla
				}
				if (p != 0 && params[p][0].equals(params[p - 1][0]) && params[p][1].equals(params[p - 1][1]) && params[p][2].equals(params[p - 1][2])) {
					band = false;
				}
				if (band) {
					col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
					// JERARQUIA DEL GRUPO AL QUE PERTENECE
					//filtroPlantilla.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
				}
				// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
				filtroPlantilla.setCerrar(plantillaDAO.getEstadoNotasAsig(filtroPlantilla));
				// NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE ASIGNATURAS
				Collection Coltemp = plantillaDAO.getNotasAsig(filtroPlantilla);
				
				//Si es conceptual tenemos que buscar LA equivalencia en
				// letras
				if(filtroPlantilla.getFilCodTipoEval() == ParamsVO.ESCALA_CONCEPTUAL ){
					colocarLiteral(col[1], Coltemp);
				}
				
				col[2] = Coltemp;
				 
				// HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
				// col[3]=Coltemp[1];
				/* para la cuestion de logros */
				col[4] = plantillaDAO.getLogro(filtroPlantilla);
				col[6] = plantillaDAO.getNotasLogro(filtroPlantilla);
				if (col[1].size() != 0 && col[0].size() != 0) {
					files.add(plantillaLogroAsigZip());
					yyy++;
				}
			}// for general de lista
			if (files.size() != 0) {
				ponerZipLogroAsig(files);
			} else {
				plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Logros");
				plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Logros");
			}
		} catch (Exception th) {
			th.printStackTrace();
			System.out.println("Plantilla de logro/asig zip con errores: " + th);
			System.out.println("Plantilla de logro/asig zip con errores: " + th.getMessage());
			plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Errores consultando Base de datos, Favor borre este reporte e intente generarlo de nuevo");
			throw new Exception(th);
		} finally {
			if (plantillaDAO != null)
				plantillaDAO.cerrar();
		}
	}

	public void asignarListaRecuperacionZip() throws Exception {
		//String s;
		boolean band, band2;
		//int n[] = { 0 };
		//String nivel = "1";
		//String[] est;
		//String[] jer = new String[1];
		String params[][] = null;
		String params_[][] = null;
		Collection files = new ArrayList();
		ItemVO item=null,item2=null;
		try {
			// NO SE ESCOGIO NADA
			/*
			if (filtroPlantilla.getMetodologia().equals("-3") && filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				// TRAER METODOLOGIAS,GRADOS,GRUPOS
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// sede
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getSede();
				list.add(o);
				o = new Object[2];// jorn
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getJornada();
				list.add(o);
				String grupos[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaMetodGradoGrupo"), list);
				// TRAER METODOLOGIAS,GRADOS,ASIG
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				String asignaturas[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaMetodologiaGradoAsig"), list);
				if (grupos == null || asignaturas == null) {
					if (grupos == null) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					} else if (asignaturas == null) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					}
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay grupos o asignaturas definidas) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
					return;
				}
				int r = 0;
				for (int p = 0; p < grupos.length; p++) {
					for (int q = 0; q < asignaturas.length; q++) {
						if (grupos[p][0].equals(asignaturas[q][0]) && grupos[p][2].equals(asignaturas[q][1]))
							r++;
					}
				}
				params = new String[r][4];
				params_ = new String[r][4];
				r = 0;
				for (int p = 0; p < grupos.length; p++) {
					for (int q = 0; q < asignaturas.length; q++) {
						if (grupos[p][0].equals(asignaturas[q][0]) && grupos[p][2].equals(asignaturas[q][1])) {
							params[r][0] = grupos[p][0];
							params_[r][0] = grupos[p][1];
							params[r][1] = grupos[p][2];
							params_[r][1] = grupos[p][3];
							params[r][2] = grupos[p][4];
							params_[r][2] = grupos[p][5];
							params[r][3] = asignaturas[q][2];
							params_[r++][3] = asignaturas[q][3];
						}
					}
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			*/
			// SOLO SE ESCOGIO METODOLOGIA
			/*
			if (!filtroPlantilla.getMetodologia().equals("-3") && filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				// TRAER GRADOS,GRUPO
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// sede
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getSede();
				list.add(o);
				o = new Object[2];// jorn
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getJornada();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getMetodologia();
				list.add(o);
				String grupos[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaGradoGrupo"), list);
				// TRAER GRADOS AREAS
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getMetodologia();
				list.add(o);
				String asignaturas[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaGradoAsig"), list);
				if (grupos == null || asignaturas == null) {
					if (grupos == null) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					} else if (asignaturas == null) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					}
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay grupos oi asignaturas definidas) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
					return;
				}
				int r = 0;
				for (int p = 0; p < grupos.length; p++) {
					for (int q = 0; q < asignaturas.length; q++) {
						if (grupos[p][0].equals(asignaturas[q][0]))
							r++;
					}
				}
				params = new String[r][4];
				params_ = new String[r][4];
				r = 0;
				for (int p = 0; p < grupos.length; p++) {
					for (int q = 0; q < asignaturas.length; q++) {
						if (grupos[p][0].equals(asignaturas[q][0])) {
							params[r][0] = filtroPlantilla.getMetodologia();
							params_[r][0] = filtroPlantilla.getMetodologia_();
							params[r][1] = grupos[p][0];
							params_[r][1] = grupos[p][1];
							params[r][2] = grupos[p][2];
							params_[r][2] = grupos[p][3];
							params[r][3] = asignaturas[q][1];
							params_[r++][3] = asignaturas[q][2];
						}
					}
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			*/
			// se escogio solo metodologia y grado
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				// consulta para traer grupos
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				List asignaturas=plantillaDAO.getListaAsignaturas(filtroPlantilla);
				if (grupos.size() == 0|| asignaturas.size() ==0) {
					if (grupos.size() ==0) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					} else if (asignaturas.size() == 0) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					}
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay Grupos o asignaturas definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[grupos.size() * asignaturas.size()][4];
					params_ = new String[grupos.size() * asignaturas.size()][4];
				}else{
					params = new String[grupos.size() * asignaturas.size()][5];
					params_ = new String[grupos.size() * asignaturas.size()][5];
				}
				int r = 0;
				for (int p = 0; p < grupos.size(); p++) {
					item=(ItemVO)grupos.get(p);
					for (int q = 0; q < asignaturas.size(); q++) {
						item2=(ItemVO)grupos.get(p);
						params[r][0] = filtroPlantilla.getMetodologia();
						params[r][1] = filtroPlantilla.getGrado();
						params[r][2] = String.valueOf(item.getCodigo());
						params[r][3] = String.valueOf(item2.getCodigo());
						params_[r][0] = filtroPlantilla.getMetodologia();
						params_[r][1] = filtroPlantilla.getGrado();
						params_[r][2] = item.getNombre();
						params_[r][3] = item2.getNombre();
						if(filtroPlantilla.getFilPlanEstudios()==1){
							params[r][4] = String.valueOf(item2.getPadre());
							params_[r][4] = item2.getPadre2();
						}
						r++;
					}
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y GRUPO
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && !filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getAsignatura().equals("-3")) {
				String grupo = filtroPlantilla.getGrupo().substring(filtroPlantilla.getGrupo().lastIndexOf("|") + 1, filtroPlantilla.getGrupo().length());
				// cnsulta para traer areas
				List asignaturas=plantillaDAO.getListaAsignaturas(filtroPlantilla);
				if (asignaturas.size() == 0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Asignaturas definidas");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Asignaturas definidas");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay Asignaturas definidas) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1,
						this.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[asignaturas.size()][4];
					params_ = new String[asignaturas.size()][4];
				}else{
					params = new String[asignaturas.size()][5];
					params_ = new String[asignaturas.size()][5];
				}
				int r = 0;
				for (int q = 0; q < asignaturas.size(); q++) {
					item=(ItemVO)asignaturas.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = grupo;
					params[r][3] = String.valueOf(item.getCodigo());
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = filtroPlantilla.getGrupo_();
					params_[r][3] = item.getNombre();
					if(filtroPlantilla.getFilPlanEstudios()==1){
						params[r][4] = String.valueOf(item.getPadre());
						params_[r][4] = item.getPadre2();
					}
					r++;
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y ASIG
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && !filtroPlantilla.getAsignatura().equals("-3")) {
				String asig = filtroPlantilla.getAsignatura().substring(filtroPlantilla.getAsignatura().lastIndexOf('|') + 1, filtroPlantilla.getAsignatura().length());
				// consulta para traer grupos
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				if (grupos.size() == 0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay grupos definidos");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura (No hay grupos definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Asig:" + asig + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
					params = new String[grupos.size()][4];
					params_ = new String[grupos.size()][4];
				int r = 0;
				for (int q = 0; q < grupos.size(); q++) {
					item=(ItemVO)grupos.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = String.valueOf(item.getCodigo());
					params[r][3] = asig;
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = item.getNombre();
					params_[r][3] = filtroPlantilla.getAsignatura_();
				}
				// System.out.println("Plantillas:
				// ("+filtroPlantilla.getUsuarioPlantilla()+")= "+r);
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Metod:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Asig:" + asig + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			/* AREA GENERAL PARA TODAS LAS OPCIONES */
			int yyy = 0;
			col = new Collection[7];
			for (int p = 0; p < params.length; p++) {
				band = band2 = true;
				filtroPlantilla.setMetodologia(params[p][0]);
				filtroPlantilla.setMetodologia_(params_[p][0]);
				filtroPlantilla.setGrado(params[p][1]);
				filtroPlantilla.setGrado_(params_[p][1]);
				filtroPlantilla.setGrupo(params[p][2]);
				filtroPlantilla.setGrupo_(params_[p][2]);
				filtroPlantilla.setAsignatura(params[p][3]);
				filtroPlantilla.setAsignatura_(params_[p][3]);
				if(filtroPlantilla.getFilPlanEstudios()==1 && params[p].length==5){
					filtroPlantilla.setDocente(params[p][4]);
					filtroPlantilla.setDocente_(params_[p][4]);
				}
				// 0=Estudiantes
				// 1=Logros
				// 2=Escala de logros
				// 3=Notas de logros
				// EVALUACION DE ASIGNATURA
				if (p == 0) {
					band = true;
					band2 = true;// NOTAS
					col[2] = plantillaDAO.getEscalaRecuperacion(1);
				}
				if (p != 0 && params[p][0].equals(params[p - 1][0]) && params[p][1].equals(params[p - 1][1]) && params[p][2].equals(params[p - 1][2])) {
					band = false;
				}
				if (band) {
					col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
					// JERARQUIA DEL GRUPO AL QUE PERTENECE
					//filtroPlantilla.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
				}
				// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
				filtroPlantilla.setCerrar(plantillaDAO.getEstadoNotasAsig(filtroPlantilla));
				col[1] = plantillaDAO.getLogro(filtroPlantilla);
				col[3] = plantillaDAO.getNotasRecuperacionLogro(filtroPlantilla);
				if (col[1].size() != 0 && col[0].size() != 0) {
					files.add(plantillaRecuperacionZip());
					yyy++;
				}
			}// for general de lista
			if (files.size() != 0) {
				ponerZipRecuperacion(files);
			} else {
				plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Logros");
				plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Logros");
			}
		} catch (Exception th) {
			System.out.println("Plantilla de Recuperacinn zip con errores: " + th);
			th.printStackTrace();
			plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Errores consultando Base de datos, Favor borre este reporte e intente generarlo de nuevo");
			throw new Exception(th);
		} finally {
			if (plantillaDAO != null)
				plantillaDAO.cerrar();
		}
	}

	/**
	 * @param list
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String ponerZipLogroAsig(Collection list) {
		Zip zip = new Zip();
		//String path;
		//String archivo;
		//String relativo;
		//String ruta;
		int zise;
		String id = filtroPlantilla.getUsuarioPlantilla();
		zise = 100000;
		if (zip.ponerZip(ubicacion[2], ubicacion[0], zise, list)) {
			Logger.print(id, "Plantilla (Zip)de Asignatura Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 1, "Listo");
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 1, "Listo");
			return ubicacion[1] + ubicacion[0];
		} else {
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
		}
		return "--";
	}

	public String ponerZipRecuperacion(Collection list) {
		Zip zip = new Zip();
		//String path;
		//String archivo;
		//String relativo;
		//String ruta;
		int zise;
		String id = filtroPlantilla.getUsuarioPlantilla();
		zise = 100000;
		if (zip.ponerZip(ubicacion[2], ubicacion[0], zise, list)) {
			Logger.print(id, "Plantilla (Zip)de Recuperacinn Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 1, "Listo");
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 1, "Listo");
			return ubicacion[1] + ubicacion[0];
		} else {
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
		}
		return "--";
	}

	/**
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String plantillaLogroAsigZip() {
		Excel excel = new Excel();
		//String relativo = "";
		//String path = "", archivo = "";
		//int i = 0;
		String[] config = new String[4];
		/* CONFIGURACION DE ARCHIVOS */
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLALOGROASIG);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACIONES DE ARCHIVOS */
		config = ubicacionPlantilla;
		
		config[3] = getNombreLogroAsig("EvaluacionAsignatura", filtroPlantilla.getSede(), filtroPlantilla.getJornada(), filtroPlantilla.getMetodologia(), filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(), filtroPlantilla.getAsignaturaAbre_(), filtroPlantilla.getPeriodo_(),filtroPlantilla.getDocente(),filtroPlantilla.getFilPlanEstudios());// nombre
																																																																						// del
																																																																						// nuevo
																																																																						// archivo
		if (!excel.plantillaLogroAsignatura(config, col, filtroPlantilla)) {
			// setMensaje(excel.getMensaje());
			return "--";
		}
		return config[2] + config[3];
	}

	public String plantillaRecuperacionZip() {
		Excel excel = new Excel();
		//String relativo = "";
		//String path = "", archivo = "";
		//int i = 0;
		String[] config = new String[4];
		/* CONFIGURACION DE ARCHIVOS */
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLARECUPERACION);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACIONES DE ARCHIVOS */
		config = ubicacionPlantilla;
		config[3] = getNombreLogroAsig("RecuperacionLogro", filtroPlantilla.getSede(), filtroPlantilla.getJornada(), filtroPlantilla.getMetodologia(), filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(), filtroPlantilla.getAsignaturaAbre_(), filtroPlantilla.getPeriodo_(),filtroPlantilla.getDocente(),filtroPlantilla.getFilPlanEstudios());// nombre
		if (!excel.plantillaRecuperacionLogro(config, col, filtroPlantilla)) {
			return "--";
		}
		return config[2] + config[3];
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param asig
	 * @param per
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String getNombreLogroAsig(String ini, String sed, String jor, String meto, String grad, String gru, String asig, String per,String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21) : meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String asi = formatear(asig);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
		if(estado==0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_" + pe + "_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_" + pe + "_Doc_"+doc+"_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
	}

	public String getNombreRecuperacion(String ini, String sed, String jor, String meto, String grad, String gru, String asig, String per,String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21) : meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String asi = formatear(asig);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
		if(estado==0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_" + pe + "_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Asi_" + asi + "_Per_" + pe + "_Doc_"+doc+"_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
	}

	/**
	 * @param a
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String formatear(String a) {
		return (a.replace(' ', '_').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I').replace('n', 'O').replace('n', 'U').replace('n', 'n').replace('n', 'N').replace('n', 'a').replace('n', 'e').replace('n', 'i').replace('n', 'o').replace('n', 'u').replace('n', 'A').replace('n', 'E').replace('n', 'I')
			.replace('n', 'O').replace('n', 'U').replace('n', 'c').replace(':', '_').replace('.', '_').replace('/', '_').replace('\\', '_'));
	}

	/**
	 * @throws Exception
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaDescAreaZip() throws Exception {
		System.out.println("asignarListaDescAreaZip ");
		//String s;
		boolean band, band2;
		//int n[] = { 0 };
		String params[][] = new String[1][1];
		String params_[][] = new String[1][1];
		//String[] jer = new String[1];
		Collection files = new ArrayList();
		ItemVO item=null, item2=null;
		try {
			// se escogio solo metodologia y grado
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getArea().equals("-3")) {
				// consulta para traer grupos
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				// cnsulta para traer areas
				List areas=plantillaDAO.getListaAreas(filtroPlantilla);
				if (grupos.size() == 0|| areas.size() == 0) {
					if (grupos.size() == 0) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					}
					if (areas.size() == 0) {
						plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Areas definidas");
						plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Areas definidas");
					}
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area (No hay Grupos o Areas definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[grupos.size() * areas.size()][4];
					params_ = new String[grupos.size() * areas.size()][4];
				}else{
					params = new String[grupos.size() * areas.size()][5];
					params_ = new String[grupos.size() * areas.size()][5];
				}
				int r = 0;
				for (int p = 0; p < grupos.size(); p++) {
					item=(ItemVO)grupos.get(p);
					for (int q = 0; q < areas.size(); q++) {
						item2=(ItemVO)areas.get(q);
						params[r][0] = filtroPlantilla.getMetodologia();
						params[r][1] = filtroPlantilla.getGrado();
						params[r][2] = String.valueOf(item.getCodigo());
						params[r][3] = String.valueOf(item2.getCodigo());
						params_[r][0] = filtroPlantilla.getMetodologia();
						params_[r][1] = filtroPlantilla.getGrado();
						params_[r][2] = item.getNombre();
						params_[r][3] = item2.getNombre();
						if(filtroPlantilla.getFilPlanEstudios()==1){
							params[r][4] = String.valueOf(item2.getPadre());
							params_[r][4] = item2.getPadre2();
						}
						r++;
					}
				}
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y GRUPO
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && !filtroPlantilla.getGrupo().equals("-3") && filtroPlantilla.getArea().equals("-3")) {
				String grupo = filtroPlantilla.getGrupo().substring(filtroPlantilla.getGrupo().lastIndexOf("|") + 1, filtroPlantilla.getGrupo().length());
				// cnsulta para traer areas
				List areas=plantillaDAO.getListaAreas(filtroPlantilla);
				if (areas.size() == 0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Areas definidas");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Areas definidas");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area (No hay Areas definidas) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
				if(filtroPlantilla.getFilPlanEstudios()==0){
					params = new String[areas.size()][4];
					params_ = new String[areas.size()][4];
				}else{
					params = new String[areas.size()][5];
					params_ = new String[areas.size()][5];
				}
				int r = 0;
				for (int q = 0; q < areas.size(); q++) {
					item=(ItemVO)areas.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = grupo;
					params[r][3] = String.valueOf(item.getCodigo());
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = filtroPlantilla.getGrupo_();
					params_[r][3] = item.getNombre();
					if(filtroPlantilla.getFilPlanEstudios()==1){
						params[r][4] = String.valueOf(item.getPadre());
						params_[r][4] = item.getPadre2();
					}
					r++;
				}
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Gru:" + grupo + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			// SOLO SE ESCOGIO METODOLOGIA, GRADO Y AREA
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3") && !filtroPlantilla.getArea().equals("-3")) {
				String area = filtroPlantilla.getArea().substring(filtroPlantilla.getArea().lastIndexOf('|') + 1, filtroPlantilla.getArea().length());
				// cnsulta para traer GRUPOS
				List grupos=plantillaDAO.getListaGrupos(filtroPlantilla);
				if (grupos.size() == 0) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area (No hay grupos definidos) Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Area:" + area + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this
						.toString());
					return;
				}
				params = new String[grupos.size()][4];
				params_ = new String[grupos.size()][4];
				int r = 0;
				for (int q = 0; q < grupos.size(); q++) {
					item=(ItemVO)grupos.get(q);
					params[r][0] = filtroPlantilla.getMetodologia();
					params[r][1] = filtroPlantilla.getGrado();
					params[r][2] = String.valueOf(item.getCodigo());
					params[r][3] = area;
					params_[r][0] = filtroPlantilla.getMetodologia();
					params_[r][1] = filtroPlantilla.getGrado();
					params_[r][2] = item.getNombre();
					params_[r++][3] = filtroPlantilla.getArea_();
				}
				Logger.print(filtroPlantilla.getUsuarioPlantilla(), "Plantilla Area Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Met:" + filtroPlantilla.getMetodologia() + " Gra:" + filtroPlantilla.getGrado() + " Area:" + area + " Per:" + filtroPlantilla.getPeriodo(), 2, 1, this.toString());
			}
			/* AREA GENERAL PARA TODAS LAS OPCIONES */
			int yyy = 0;
			col = new Collection[6];
			String[] est;
			for (int p = 0; p < params.length; p++) {
				band = band2 = true;
				filtroPlantilla.setMetodologia(params[p][0]);
				filtroPlantilla.setMetodologia_(params_[p][0]);
				filtroPlantilla.setGrado(params[p][1]);
				filtroPlantilla.setGrado_(params_[p][1]);
				filtroPlantilla.setGrupo(params[p][2]);
				filtroPlantilla.setGrupo_(params_[p][2]);
				filtroPlantilla.setArea(params[p][3]);
				filtroPlantilla.setArea_(params_[p][3]);
				if(filtroPlantilla.getFilPlanEstudios()==1 && params[p].length==5){
					filtroPlantilla.setDocente(params[p][4]);
					filtroPlantilla.setDocente_(params_[p][4]);
				}
				// EVALUACION DE AREA
				if (p == 0) {
					band = true;
					band2 = true;// NOTAS
					//col[1] = plantillaDAO.getEscala(2);
					
					/**  MODIFICADO Escala asignatura  20-03-10
					 * */
//					col[1] = plantillaDAO.getEscala(2);
					col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla);
					
					/** FIN
					 * **/
				}
//				col[1] = plantillaDAO.getEscala(2);
				
				/**  MODIFICADO Escala asignatura  20-03-10
				 * */
//				col[1] = plantillaDAO.getEscala(2);
				col[1] = plantillaDAO.getEscalaNivelEval(filtroPlantilla);
				
				/** FIN
				 * **/
				
				if (p != 0 && params[p][0].equals(params[p - 1][0]) && params[p][1].equals(params[p - 1][1]) && params[p][2].equals(params[p - 1][2])) {
					band = false;
				}
				if (band) {
					col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
					// JERARQUIA DEL GRUPO AL QUE PERTENECE
					filtroPlantilla.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
				}
				// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
				filtroPlantilla.setCerrar(plantillaDAO.getEstadoNotasArea(filtroPlantilla));
				// NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
				Collection Coltemp = plantillaDAO.getNotasArea(filtroPlantilla);
				col[2] = Coltemp;
				// HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
				// col[3]=Coltemp[1];
				/* Para la cuestion de descriptor */
				col[4] = plantillaDAO.getDescriptor(filtroPlantilla);// LISTA
																		// DE
																		// DESCRIPTORES
				col[5] = plantillaDAO.getNotasDesc(filtroPlantilla);// NOTAS DE
																	// DESCRIPTORES
				if (col[1].size() != 0 && col[0].size() != 0) {
					files.add(plantillaDescAreaZip());
				}
			}// for genral de lista
			if (files.size() != 0) {
				ponerZipAreaDesc(files);
			} else {
				plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Descriptores");
				plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "Grados sin grupos, Grados sin Asignaturas o Grados sin Descriptores");
			}
			filtroPlantilla = null;
			if (plantillaDAO != null)
				plantillaDAO.cerrar();
		} catch (Exception th) {
			System.out.println("Plantilla de Descriptor/Area con errores: " + th);
			plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Errores consultando Base de datos, Favor borre este reporte e intente generarlo de nuevo");
			th.printStackTrace();
			throw new Exception(th);
		} finally {
			if (plantillaDAO != null)
				plantillaDAO.cerrar();
		}
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param area
	 * @param per
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String getNombreDescAre(String ini, String sed, String jor, String meto, String grad, String gru, String area, String per,String doc, int estado) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21) : meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String are = formatear(area);
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
		if(estado==0)
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Are_" + are + "_Per_" + pe + "_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
		else
			return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Are_" + are + "_Per_" + pe + "_Doc_"+doc+"_(" + fe + ")[" + (int) (Math.random() * 1000) + "].xls");
	}

	/**
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String plantillaDescAreaZip() {
		System.out.println("plantillaDescAreaZip");
		Excel excel = new Excel();
		//String relativo = null;
		//int i = 0;
		String[] config = new String[4];
		// CONFIGURACION DE ARCHIVOS
		//String path;
		//String archivo;
		try{
			filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLAAREADESC);
			filtroPlantilla.setNivelLogro("1");
			// UBICACION ES DE ARCHIVOS
			config = ubicacionPlantilla;
			config[3] = getNombreDescAre("EvaluacionArea", filtroPlantilla.getSede(), filtroPlantilla.getJornada(), filtroPlantilla.getMetodologia(), filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(), filtroPlantilla.getArea_(), filtroPlantilla.getPeriodo_(),filtroPlantilla.getDocente(),filtroPlantilla.getFilPlanEstudios());
			if (!excel.plantillaAreaDescriptor(config, col, filtroPlantilla)) {
				// setMensaje(excel.getMensaje());
				return "--";
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("PLANTILLA AREA " + e.getMessage() );
			e.printStackTrace();
		}
		return config[2] + config[3];
	}
	
	/**
	 * @param list
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String ponerZipAreaDesc(Collection list) {
		Zip zip = new Zip();
		//String path;
		//String archivo;
		//String relativo;
		//String ruta;
		int zise;
		String id = filtroPlantilla.getUsuarioPlantilla();
		zise = 100000;
		if (zip.ponerZip(ubicacion[2], ubicacion[0], zise, list)) {
			Logger.print(id, "Plantilla (Zip)de Area Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 1, "Listo");
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 1, "Listo");
			return ubicacion[1] + ubicacion[0];
		} else {
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
		}
		return "--";
	}

	/**
	 * @throws Exception
	 *             <br>
	 *             Return Type: void<br>
	 *             Version 1.1.<br>
	 */
	public void asignarListaPreescolarZip() throws Exception {
		String s;
		boolean band;
		//int n[] = { 0 };
		//String nivel = "1";
		//String[] est;
		String params[][] = new String[1][1];
		String params_[][] = new String[1][1];
		Collection files = new ArrayList();
		try {
			// NO SE ESCOGIO NADA
			if (filtroPlantilla.getMetodologia().equals("-3") && filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3")) {
				// TRAER METODOLOGIAS,GRADOS,GRUPOS
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// sede
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getSede();
				list.add(o);
				o = new Object[2];// jorn
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getJornada();
				list.add(o);
				String grupos[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaMetodGradoGrupo2"), list);
				if (grupos == null) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					return;
				}
				params = new String[grupos.length][3];
				params_ = new String[grupos.length][3];
				for (int p = 0; p < grupos.length; p++) {
					params[p][0] = grupos[p][0];
					params_[p][0] = grupos[p][1];
					params[p][1] = grupos[p][2];
					params_[p][1] = grupos[p][3];
					params[p][2] = grupos[p][4];
					params_[p][2] = grupos[p][5];
				}
			}
			// SOLO SE ESCOGIO METODOLOGIA
			if (!filtroPlantilla.getMetodologia().equals("-3") && filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3")) {
				// TRAER GRADOS,GRUPO
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// sede
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getSede();
				list.add(o);
				o = new Object[2];// jorn
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getJornada();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getMetodologia();
				list.add(o);
				String grupos[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaGradoGrupo2"), list);
				if (grupos == null) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					return;
				}
				params = new String[grupos.length][3];
				params_ = new String[grupos.length][3];
				for (int p = 0; p < grupos.length; p++) {
					params[p][0] = filtroPlantilla.getMetodologia();
					params_[p][0] = filtroPlantilla.getMetodologia_();
					params[p][1] = grupos[p][0];
					params_[p][1] = grupos[p][1];
					params[p][2] = grupos[p][2];
					params_[p][2] = grupos[p][3];
				}
			}
			// se escogio solo metodologia y grado
			if (!filtroPlantilla.getMetodologia().equals("-3") && !filtroPlantilla.getGrado().equals("-3") && filtroPlantilla.getGrupo().equals("-3")) {
				// consulta para traer grupos
				list = new ArrayList();
				o = new Object[2];// inst
				o[0] = Properties.ENTEROLARGO;
				o[1] = filtroPlantilla.getInstitucion();
				list.add(o);
				o = new Object[2];// sede
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getSede();
				list.add(o);
				o = new Object[2];// jorn
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getJornada();
				list.add(o);
				o = new Object[2];// metodologia
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getMetodologia();
				list.add(o);
				o = new Object[2];// grado
				o[0] = Properties.ENTERO;
				o[1] = filtroPlantilla.getGrado();
				list.add(o);
				String grupos[][] = plantillaDAO.getFiltroMatriz(rb.getString("plantilla.listaGrupo2"), list);
				if (grupos == null) {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "No hay Grupos definidos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "No hay Grupos definidos");
					return;
				}
				params = new String[grupos.length][3];
				params_ = new String[grupos.length][3];
				for (int p = 0; p < grupos.length; p++) {
					params[p][0] = filtroPlantilla.getMetodologia();
					params[p][1] = filtroPlantilla.getGrado();
					params[p][2] = grupos[p][0];
					params_[p][0] = filtroPlantilla.getMetodologia();
					params_[p][1] = filtroPlantilla.getGrado();
					params_[p][2] = grupos[p][1];
				}
			}
			/* AREA GENERAL PARA TODAS LAS OPCIONES */
			for (int p = 0; p < params.length; p++) {
				band = true;
				filtroPlantilla.setMetodologia(params[p][0]);
				filtroPlantilla.setMetodologia_(params_[p][0]);
				filtroPlantilla.setGrado(params[p][1]);
				filtroPlantilla.setGrado_(params_[p][1]);
				filtroPlantilla.setGrupo(params[p][2]);
				filtroPlantilla.setGrupo_(params_[p][2]);
				if (p == 0) {
					band = true;
				}
				if (p != 0 && params[p][0].equals(params[p - 1][0]) && params[p][1].equals(params[p - 1][1]) && params[p][2].equals(params[p - 1][2])) {
					band = false;
				}
				// ESTUDIANTES
				if (band) {
					col[0] = plantillaDAO.getEstudiantes(filtroPlantilla);
					// JERARQUIA DEL GRUPO AL QUE PERTENECE
					filtroPlantilla.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
				}
				// REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
				filtroPlantilla.setCerrar("0");
				// NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
				col[2] = plantillaDAO.getNotasPree(filtroPlantilla);
				if (col[0].size() != 0) {
					files.add(plantillaPreescolarZip());
				} else {
					plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Grados sin grupos");
					plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "Grados sin grupos");
				}
			}// for general de lista
			ponerZipPree(files);
		} catch (Exception th) {
			plantillaDAO.setReporteActualizar(filtroPlantilla.getUsuarioPlantilla(), ubicacion[1] + ubicacion[0], 2, "Errores consultando Base de datos, Favor borre este reporte e intente generarlo de nuevo");
			plantillaDAO.actualizarPlantillas(filtroPlantilla.getUsuarioPlantilla(), ubicacion[0], 2, "Errores consultando Base de datos, Favor borre este reporte e intente generarlo de nuevo");
			System.out.println("Plantilla de Preescolar con errores: " + th);
			th.printStackTrace();
			throw new Exception(th);
		} finally {
			if (plantillaDAO != null)
				plantillaDAO.cerrar();
		}
	}

	/**
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String plantillaPreescolarZip() {
		Excel excel = new Excel();
		//String relativo = null;
		//int i = 0;
		String[] config = new String[4];
		/* ENCABEZADO DE LA PLANTILLA */
		filtroPlantilla.setTipoPlantilla("" + Properties.PLANTILLAPREE);
		filtroPlantilla.setNivelLogro("1");
		/* UBICACION ES DE ARCHIVOS */
		config = ubicacionPlantilla;
		config[3] = getNombrePreescolar("EvaluacionPreescolar", filtroPlantilla.getSede(), filtroPlantilla.getJornada(), filtroPlantilla.getMetodologia(), filtroPlantilla.getGrado_(), filtroPlantilla.getGrupo_(), filtroPlantilla.getPeriodo_());
		/*if (!excel.plantillaPreescolar(config, col, filtroPlantilla)) {
			// setMensaje(excel.getMensaje());
			return "--";
		}*/
		return config[2] + config[3];
	}

	/**
	 * @param list
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String ponerZipPree(Collection list) {
		Zip zip = new Zip();
		//String path;
		//String archivo;
		//String relativo;
		//String ruta;
		int zise;
		String id = filtroPlantilla.getUsuarioPlantilla();
		zise = 100000;
		if (zip.ponerZip(ubicacion[2], ubicacion[0], zise, list)) {
			Logger.print(id, "Plantilla (Zip)de Preescolar Inst:" + filtroPlantilla.getInstitucion() + " Sede:" + filtroPlantilla.getSede() + " Jorn:" + filtroPlantilla.getJornada() + " Per:" + filtroPlantilla.getPeriodo(), 3, 1, this.toString());
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 1, "Listo");
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 1, "Listo");
			return ubicacion[1] + ubicacion[0];
		} else {
			plantillaDAO.setReporteActualizar(id, ubicacion[1] + ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
			plantillaDAO.actualizarPlantillas(id, ubicacion[0], 2, "Error Generando archivo Zip" + zip.getMensaje());
		}
		return "--";
	}

	/**
	 * @param ini
	 * @param sed
	 * @param jor
	 * @param meto
	 * @param grad
	 * @param gru
	 * @param per
	 * @return<br>
	 *         Return Type: String<br>
	 *         Version 1.1.<br>
	 */
	public String getNombrePreescolar(String ini, String sed, String jor, String meto, String grad, String gru, String per) {
		String jo = formatear((jor.length() > 10 ? meto.substring(0, 10) : jor));
		String met = formatear((meto.length() > 21 ? meto.substring(0, 21) : meto));
		String gra = formatear((grad.length() > 8 ? grad.substring(0, 8) : grad));
		String gr = formatear((gru.length() > 8 ? gru.substring(0, 8) : gru));
		String pe = formatear((per.length() > 10 ? per.substring(0, 10) : per));
		String fe = f2.toString().replace(':', '-').replace('.', '-').substring(0, 10) + "_" + f2.toString().replace(':', '-').replace('.', '-').substring(11, 19);
		return (ini + "_Sed_" + sed + "_Jor_" + jo + "_Met_" + met + "_Gra_" + gra + "_Gru_" + gr + "_Per_" + pe + "_(" + fe + ").xls");
	}
	
	
	
	/**
	 * @function:  
	 * @param col
	 * @param colTemp
	 * @throws Exception
	 */
	public void colocarLiteral(Collection col, Collection colTemp ) throws Exception {
		try{ 
		java.util.Iterator   iterator = colTemp.iterator();
		while (iterator.hasNext()) { 
			Object[] o  = (Object[]) iterator.next();
			o[1] = getNotaReal(col,(String)o[1]);
			o[3] = getNotaReal(col,(String)o[3]);
		}
		
		}catch (Exception e) {
			throw new Exception(e.getMessage() );
		}
	}
	
	
	/**
	 * @function:  
	 * @param escala
	 * @param n
	 * @return
	 */
	public String getNotaReal(Collection escala, String n) {
		
		if (n == null)
			return null;
		java.util.Iterator   iterator = escala.iterator();
		while (iterator.hasNext()) { 
			Object[] o  = (Object[]) iterator.next(); 
			String valor = (String)o[0];
			if ( valor.trim().equals( n.trim())){
				return (String)o[1];
			}
		}
		return "";
	}
}
