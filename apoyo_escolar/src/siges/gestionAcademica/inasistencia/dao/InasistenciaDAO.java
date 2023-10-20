/**
 * 
 */
package siges.gestionAcademica.inasistencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import siges.common.vo.ItemVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.gestionAcademica.inasistencia.vo.FallaEstudiante;
import siges.gestionAcademica.inasistencia.vo.FiltroInasistencia;
import siges.gestionAcademica.inasistencia.vo.InasistenciaEstudiante;
import siges.gestionAcademica.inasistencia.vo.ParamVO;

/**
 * 3/09/2007
 * 
 * @author Latined
 * @version 1.2
 */
public class InasistenciaDAO extends Dao {
	private SimpleDateFormat formaFecha = new SimpleDateFormat(
			"yyyy/MMM/dd HH:mm:ss.SSS");

	public InasistenciaDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("siges.gestionAcademica.inasistencia.bundle.inasistencia");
	}

	public List getAllGrado(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllGrado"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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

	public List getAllGrupo(long inst, int sede, int jor) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllGrupo"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, sede);
			st.setInt(i++, jor);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getLong(i++));
				itemVO.setPadre2(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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

	public List getAllMes() {
		List l = new ArrayList();
		ItemVO itemVO = null;
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.JANUARY);
		itemVO.setNombre("Enero");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.FEBRUARY);
		itemVO.setNombre("Febrero");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.MARCH);
		itemVO.setNombre("Marzo");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.APRIL);
		itemVO.setNombre("Abril");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.MAY);
		itemVO.setNombre("Mayo");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.JUNE);
		itemVO.setNombre("Junio");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.JULY);
		itemVO.setNombre("Julio");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.AUGUST);
		itemVO.setNombre("Agosto");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.SEPTEMBER);
		itemVO.setNombre("Septiembre");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.OCTOBER);
		itemVO.setNombre("Octubre");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.NOVEMBER);
		itemVO.setNombre("Noviembre");
		l.add(itemVO);
		itemVO = new ItemVO();
		itemVO.setCodigo(Calendar.DECEMBER);
		itemVO.setNombre("Diciembre");
		l.add(itemVO);
		return l;
	}

	public List getAllQuincena() {
		List l = new ArrayList();
		ItemVO itemVO = null;
		itemVO = new ItemVO();
		itemVO.setCodigo(ParamVO.QUINCENA_UNO);
		itemVO.setNombre("Primero");
		l.add(itemVO);
		ItemVO itemVO2 = new ItemVO();
		itemVO2.setCodigo(ParamVO.QUINCENA_DOS);
		itemVO2.setNombre("Segundo");
		l.add(itemVO2);

		return l;
	}

	public List getEstudianteInasistencia(FiltroInasistencia filtro)
			throws Exception {
		List l = new ArrayList();
		List lFalla = new ArrayList();
		FallaEstudiante falla = null;
		Connection cn = null;
		PreparedStatement st = null, st2 = null;
		ResultSet rs = null, rs2 = null;
		InasistenciaEstudiante estudiante = null;
		List mLunes = null;
		List mMartes = null;
		List mMiercoles = null;
		List mJueves = null;
		List mViernes = null;
		List mSabado = null;
		List mDomingo = null;
		String asigs = null;
		ItemVO itemVO = null;
		int i = 0, diaSemana = 0;
		boolean existenMaterias = false;
		int[] inasis = null;
		try {
			long vig = getVigenciaNumerico();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
			calendar.set(Calendar.MONTH, filtro.getFilMes());
			// System.out.println("Asignatura:::: " +
			// filtro.getFilAsignatura());
			// System.out.println("Dias del mes: " +
			// calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			filtro.setFilTotalDias(calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// saber si hay clase de sabado o de domingo
			/*
			 * st = cn.prepareStatement(rb.getString("getSabadoDomingo")); i =
			 * 1; st.setLong(i++, filtro.getFilInstitucion()); st.setInt(i++,
			 * filtro.getFilSede()); st.setInt(i++, filtro.getFilJornada()); rs
			 * = st.executeQuery(); if (rs.next()) {
			 * filtro.setFilSabado(rs.getInt(1));
			 * filtro.setFilDomingo(rs.getInt(2)); } rs.close(); st.close();
			 */

			filtro.setFilSabado(1);
			filtro.setFilDomingo(1);

			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			}
			rs.close();
			st.close();
			// System.out.println("1.))" + "getEstudianteInasistencia." +
			// filtro.getFilOrden() + "." + filtro.getFilPeriodo());
			// System.out.println("2." + filtro.getFilJerarquia());
			st = cn.prepareStatement(rb.getString("getEstudianteInasistencia."
					+ filtro.getFilOrden() + "." + filtro.getFilPeriodo()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			// st.setLong(i++,getVigenciaNumerico());
			rs = st.executeQuery();
			int index = 1;
			int mat = 0;
			String materias = null, materias0 = null;
			while (rs.next()) {
				i = 1;
				lFalla = new ArrayList();
				estudiante = new InasistenciaEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				// poner la lista de fallas
				if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {

					for (int dia = 1; dia <= 15; dia++) {
						materias = materias0 = "";
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						existenMaterias = false;
						switch (diaSemana) {
						case Calendar.MONDAY:
							if (mLunes == null) {
								mLunes = calcularMaterias(cn, filtro,
										Calendar.MONDAY);
							}
							for (mat = 0; mat < mLunes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mLunes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.TUESDAY:
							if (mMartes == null) {
								mMartes = calcularMaterias(cn, filtro,
										Calendar.TUESDAY);
							}
							for (mat = 0; mat < mMartes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mMartes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.WEDNESDAY:
							if (mMiercoles == null) {
								mMiercoles = calcularMaterias(cn, filtro,
										Calendar.WEDNESDAY);
							}
							for (mat = 0; mat < mMiercoles.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mMiercoles.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.THURSDAY:
							if (mJueves == null) {
								mJueves = calcularMaterias(cn, filtro,
										Calendar.THURSDAY);
							}
							for (mat = 0; mat < mJueves.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mJueves.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.FRIDAY:
							if (mViernes == null) {
								mViernes = calcularMaterias(cn, filtro,
										Calendar.FRIDAY);
							}
							for (mat = 0; mat < mViernes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mViernes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.SATURDAY:
							if (filtro.getFilSabado() == 1) {
								if (mSabado == null) {
									mSabado = calcularMaterias(cn, filtro,
											Calendar.SATURDAY);
								}
								for (mat = 0; mat < mSabado.size(); mat++) {
									existenMaterias = true;
									itemVO = (ItemVO) mSabado.get(mat);
									inasis = calcularInasistenciaEstudiante(cn,
											estudiante.getEvalCodigo(), filtro,
											itemVO);
									if (inasis[0] == 1) {
										falla.setFaChecked(1);
										materias += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									} else {
										materias += String.valueOf(itemVO
												.getCodigo()) + "@0|";
										materias0 += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									}
									falla.setFaMotivo(inasis[2]);
								}
								if (falla.getFaChecked() == 0) {
									materias = materias0;
								}
								if (!existenMaterias) {
									falla.setFaDisabled(1);
								}
							}
							break;
						case Calendar.SUNDAY:
							if (filtro.getFilDomingo() == 1) {
								if (mDomingo == null) {
									mDomingo = calcularMaterias(cn, filtro,
											Calendar.SUNDAY);
								}
								for (mat = 0; mat < mDomingo.size(); mat++) {
									existenMaterias = true;
									itemVO = (ItemVO) mDomingo.get(mat);
									inasis = calcularInasistenciaEstudiante(cn,
											estudiante.getEvalCodigo(), filtro,
											itemVO);
									if (inasis[0] == 1) {
										falla.setFaChecked(1);
										materias += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									} else {
										materias += String.valueOf(itemVO
												.getCodigo()) + "@0|";
										materias0 += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									}
									falla.setFaMotivo(inasis[2]);
								}
								if (falla.getFaChecked() == 0) {
									materias = materias0;
								}
								if (!existenMaterias) {
									falla.setFaDisabled(1);
								}
							}
							break;
						}
						if (materias.length() > 1)
							materias = materias.substring(0,
									materias.length() - 1);
						falla.setFaAsig(materias);
						lFalla.add(falla);
					}

				}//

				// ///////////

				if (filtro.getFilQuincena() == ParamVO.QUINCENA_DOS) {

					for (int dia = 16; dia <= filtro.getFilTotalDias(); dia++) {
						materias = materias0 = "";
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						existenMaterias = false;
						switch (diaSemana) {
						case Calendar.MONDAY:
							if (mLunes == null) {
								mLunes = calcularMaterias(cn, filtro,
										Calendar.MONDAY);
							}
							for (mat = 0; mat < mLunes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mLunes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.TUESDAY:
							if (mMartes == null) {
								mMartes = calcularMaterias(cn, filtro,
										Calendar.TUESDAY);
							}
							for (mat = 0; mat < mMartes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mMartes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.WEDNESDAY:
							if (mMiercoles == null) {
								mMiercoles = calcularMaterias(cn, filtro,
										Calendar.WEDNESDAY);
							}
							for (mat = 0; mat < mMiercoles.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mMiercoles.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.THURSDAY:
							if (mJueves == null) {
								mJueves = calcularMaterias(cn, filtro,
										Calendar.THURSDAY);
							}
							for (mat = 0; mat < mJueves.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mJueves.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.FRIDAY:
							if (mViernes == null) {
								mViernes = calcularMaterias(cn, filtro,
										Calendar.FRIDAY);
							}
							for (mat = 0; mat < mViernes.size(); mat++) {
								existenMaterias = true;
								itemVO = (ItemVO) mViernes.get(mat);
								inasis = calcularInasistenciaEstudiante(cn,
										estudiante.getEvalCodigo(), filtro,
										itemVO);
								if (inasis[0] == 1) {
									falla.setFaChecked(1);
									materias += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								} else {
									materias += String.valueOf(itemVO
											.getCodigo()) + "@0|";
									materias0 += String.valueOf(itemVO
											.getCodigo())
											+ "@"
											+ inasis[1]
											+ "|";
								}
								falla.setFaMotivo(inasis[2]);
							}
							if (falla.getFaChecked() == 0) {
								materias = materias0;
							}
							if (!existenMaterias) {
								falla.setFaDisabled(1);
							}
							break;
						case Calendar.SATURDAY:
							if (filtro.getFilSabado() == 1) {
								if (mSabado == null) {
									mSabado = calcularMaterias(cn, filtro,
											Calendar.SATURDAY);
								}
								for (mat = 0; mat < mSabado.size(); mat++) {
									existenMaterias = true;
									itemVO = (ItemVO) mSabado.get(mat);
									inasis = calcularInasistenciaEstudiante(cn,
											estudiante.getEvalCodigo(), filtro,
											itemVO);
									if (inasis[0] == 1) {
										falla.setFaChecked(1);
										materias += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									} else {
										materias += String.valueOf(itemVO
												.getCodigo()) + "@0|";
										materias0 += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									}
									falla.setFaMotivo(inasis[2]);
								}
								if (falla.getFaChecked() == 0) {
									materias = materias0;
								}
								if (!existenMaterias) {
									falla.setFaDisabled(1);
								}
							}
							break;
						case Calendar.SUNDAY:
							if (filtro.getFilDomingo() == 1) {
								if (mDomingo == null) {
									mDomingo = calcularMaterias(cn, filtro,
											Calendar.SUNDAY);
								}
								for (mat = 0; mat < mDomingo.size(); mat++) {
									existenMaterias = true;
									itemVO = (ItemVO) mDomingo.get(mat);
									inasis = calcularInasistenciaEstudiante(cn,
											estudiante.getEvalCodigo(), filtro,
											itemVO);
									if (inasis[0] == 1) {
										falla.setFaChecked(1);
										materias += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									} else {
										materias += String.valueOf(itemVO
												.getCodigo()) + "@0|";
										materias0 += String.valueOf(itemVO
												.getCodigo())
												+ "@"
												+ inasis[1]
												+ "|";
									}
									falla.setFaMotivo(inasis[2]);
								}
								if (falla.getFaChecked() == 0) {
									materias = materias0;
								}
								if (!existenMaterias) {
									falla.setFaDisabled(1);
								}
							}
							break;
						}
						if (materias.length() > 1)
							materias = materias.substring(0,
									materias.length() - 1);
						falla.setFaAsig(materias);
						lFalla.add(falla);
					}

				}//

				// System.out.println("estudiante.setEvalFallas(lFalla);  3");
				estudiante.setEvalFallas(lFalla);
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeResultSet(rs2);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	/**
	 * @function:
	 * @param inst
	 * @param met
	 * @param gra
	 * @param grupo
	 * @param dia
	 * @param asig
	 * @return
	 */
	public List getMaterias(long inst, int met, int gra, long grupo, int dia,
			long asig) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllMateria." + dia));
			i = 1;
			st.setLong(i++, grupo);
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, gra);
			st.setLong(i++, asig);
			st.setLong(i++, asig);
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				itemVO.setPadre(rs.getInt(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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

	//
	/**
	 * @function:
	 * @param cn
	 * @param estudiante
	 * @param filtro
	 * @param asig
	 * @return
	 * @throws Exception
	 */
	public int[] calcularInasistenciaEstudiante(Connection cn, long estudiante,
			FiltroInasistencia filtro, ItemVO asig) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));
			String dia_ = (filtro.getFilDia() < 10 ? "0" + filtro.getFilDia()
					: String.valueOf(filtro.getFilDia()));
			// System.out.println("FECHA DADA=" + dia_ + "/" + mes_ + "/" +
			// getVigenciaNumerico());
			st = cn.prepareStatement(rb.getString("getInasistencia"));
			i = 1;
			st.setLong(i++, asig.getCodigo());
			st.setLong(i++, estudiante);
			st.setString(i++, dia_ + "/" + mes_ + "/" + getVigenciaNumerico());
			st.setLong(i++, filtro.getFilPeriodo());
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next()) {
				return (new int[] { 1, rs.getInt(1), rs.getInt(2) });
			} else {
				return (new int[] { 0, (int) asig.getPadre(), -99 });
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param cn
	 * @param filtro
	 * @param dia
	 * @return
	 * @throws Exception
	 */
	public List calcularMaterias(Connection cn, FiltroInasistencia filtro,
			int dia) throws Exception {
		System.out
				.println(formaFecha.format(new Date()) + " calcularMaterias ");
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {

			System.out.println("dia " + dia);
			System.out.println("CONSU7LTA "
					+ rb.getString("getAllMaterias2." + dia));
			st = cn.prepareStatement(rb.getString("getAllMaterias2." + dia));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			System.out.println("filtro.getFilJerarquia() "
					+ filtro.getFilJerarquia());
			st.setLong(i++, filtro.getFilInstitucion());
			System.out.println("filtro.getFilInstitucion() "
					+ filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilMetodologia());
			System.out.println("filtro.getFilMetodologia() "
					+ filtro.getFilMetodologia());
			st.setLong(i++, filtro.getFilGrado());
			System.out.println("filtro.getFilGrado() " + filtro.getFilGrado());
			st.setLong(i++, filtro.getFilAsignatura());
			System.out.println("filtro.getFilAsignatura() "
					+ filtro.getFilAsignatura());
			st.setLong(i++, filtro.getFilAsignatura());
			st.setLong(i++, getVigenciaNumerico());
			System.out
					.println("getVigenciaNumerico() " + getVigenciaNumerico());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				System.out
						.println("itemVO.setCodigo(--- " + itemVO.getCodigo());
				itemVO.setPadre(rs.getLong(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return l;
	}

	public List getDias(FiltroInasistencia filtro) throws Exception {
		List l = new ArrayList();
		String[] dias = { "", "D", "L", "M", "M", "J", "V", "S" };
		ItemVO itemVO = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
		calendar.set(Calendar.MONTH, filtro.getFilMes());

		if (filtro.getFilQuincena() == 1) {
			for (int dia = 1; dia <= 15; dia++) {
				calendar.set(Calendar.DAY_OF_MONTH, dia);
				switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				/*
				 * case Calendar.SATURDAY: if (filtro.getFilSabado() == 1) {
				 * itemVO = new ItemVO(); itemVO.setCodigo(dia);
				 * itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
				 * l.add(itemVO); } break; case Calendar.SUNDAY: if
				 * (filtro.getFilDomingo() == 1) { itemVO = new ItemVO();
				 * itemVO.setCodigo(dia);
				 * itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
				 * l.add(itemVO); } break;
				 */
				default:
					itemVO = new ItemVO();
					itemVO.setCodigo(dia);
					itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
					l.add(itemVO);
					break;
				}
			}
		}

		if (filtro.getFilQuincena() == 2) {
			for (int dia = 16; dia <= filtro.getFilTotalDias(); dia++) {
				calendar.set(Calendar.DAY_OF_MONTH, dia);
				switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				/*
				 * case Calendar.SATURDAY: if (filtro.getFilSabado() == 1) {
				 * itemVO = new ItemVO(); itemVO.setCodigo(dia);
				 * itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
				 * l.add(itemVO); } break; case Calendar.SUNDAY: if
				 * (filtro.getFilDomingo() == 1) { itemVO = new ItemVO();
				 * itemVO.setCodigo(dia);
				 * itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
				 * l.add(itemVO); } break;
				 */
				default:
					itemVO = new ItemVO();
					itemVO.setCodigo(dia);
					itemVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
					l.add(itemVO);
					break;
				}
			}
		}

		return l;
	}

	public List getListaMotivo() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllMotivo"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @param filtro
	 * @throws Exception
	 */
	public void guardarInasistencia(FiltroInasistencia filtro) throws Exception {
		System.out.println(formaFecha.format(new Date())
				+ " - guardarInasistencia");

		System.out.println("filtro.getFilGrado() " + filtro.getFilGrado());
		Connection cn = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		int i = 0;
		long est = 0;
		int dia = 0;
		String fecha = null;
		String fechaIni = null;
		String fechaFin = null;
		int mot = 0;
		String asig[] = null;
		String asig2[] = null;
		long asignatura = 0;
		long jerAsig = -99;
		int ih = 0;
		try {
			long vig = getVigenciaNumerico();
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));

			if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
				fechaIni = "01/" + mes_ + "/" + vig;
				fechaFin = 15 + "/" + mes_ + "/" + vig;

			} else {
				fechaIni = "16/" + mes_ + "/" + vig;
				fechaFin = filtro.getFilTotalDias() + "/" + mes_ + "/" + vig;
			}

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// calcular la jerarquia de la asignatura
			if (filtro.getFilAsignatura() != -99) {
				st = cn.prepareStatement(rb.getString("getJerarAsignatura"));
				i = 1;
				st.setLong(i++, filtro.getFilInstitucion());
				st.setInt(i++, filtro.getFilMetodologia());
				st.setInt(i++, filtro.getFilGrado());
				st.setLong(i++, filtro.getFilAsignatura());
				st.setLong(i++, vig);
				rs = st.executeQuery();
				if (rs.next()) {
					jerAsig = rs.getLong(1);
				}
				rs.close();
				st.close();
			}
			// ELIMINAR
			st = cn.prepareStatement(rb.getString("eliminarInasistencia"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setString(i++, fechaIni);
			st.setString(i++, fechaFin);
			st.setInt(i++, filtro.getFilPeriodo());
			st.setLong(i++, vig);
			st.setLong(i++, jerAsig);
			st.setLong(i++, jerAsig);
			st.executeUpdate();
			st.close();

			// INGRESAR
			if (filtro.getFalla() != null) {
				String[] falla = filtro.getFalla();
				st = cn.prepareStatement(rb.getString("ingresarInasistencia"));
				st2 = cn.prepareStatement(rb.getString("ingresarInasistencia2"));
				st.clearBatch();
				st2.clearBatch();
				for (int j = 0; j < falla.length; j++) {
					// System.out.println("-FALLA: " + falla[j]);
					asig = falla[j].replace('|', ':').split(":");
					if (asig != null && asig.length >= 4
							&& asig[3].length() > 0) {
						est = Long.parseLong(asig[0]);
						dia = Integer.parseInt(asig[1]);
						mot = Integer.parseInt(asig[2]);
						fecha = (dia < 10 ? "0" + dia : String.valueOf(dia))
								+ "/" + mes_ + "/" + vig;
						// System.out.println("-"+asig[3]+"-FECHA: " + fecha);
						for (int k = 3; k < asig.length; k++) {
							i = 1;
							asig2 = asig[k].split("@");
							asignatura = Long.parseLong(asig2[0]);
							ih = Integer.parseInt(asig2[1]);
							if (asig2 != null && ih > 0) {
								if (mot != -99) {
									st.setLong(i++, asignatura);
									st.setLong(i++, est);
									st.setString(i++, fecha);
									st.setInt(i++, filtro.getFilPeriodo());
									st.setInt(i++, ih);
									st.setInt(i++, mot);
									st.setLong(i++, vig);
									st.addBatch();
								} else {
									st2.setLong(i++, asignatura);
									st2.setLong(i++, est);
									st2.setString(i++, fecha);
									st2.setInt(i++, filtro.getFilPeriodo());
									st2.setInt(i++, ih);
									st2.setLong(i++, vig);
									st2.addBatch();
								}
							}
						}
					}
				}
				st.executeBatch();
				st2.executeBatch();
			}
			System.out.println("filtro.getFilGrado() " + filtro.getFilGrado());

			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public List getEstudianteRetardo(FiltroInasistencia filtro)
			throws Exception {
		List l = new ArrayList();
		List lFalla = new ArrayList();
		FallaEstudiante falla = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InasistenciaEstudiante estudiante = null;
		int i = 0, diaSemana = 0;
		;
		int index = 1;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
			calendar.set(Calendar.MONTH, filtro.getFilMes());
			filtro.setFilTotalDias(calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			cn = cursor.getConnection();
			// saber si hay clase de sabado o de domingo
			st = cn.prepareStatement(rb.getString("getSabadoDomingo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilSabado(rs.getInt(1));
				filtro.setFilDomingo(rs.getInt(2));
			}
			rs.close();
			st.close();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("getEstudianteRetardo."
					+ filtro.getFilOrden()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			// st.setLong(i++,getVigenciaNumerico());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lFalla = new ArrayList();
				estudiante = new InasistenciaEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				// poner la lista de fallas
				if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
					for (int dia = 1; dia <= 15; dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */

						falla = new FallaEstudiante();
						// System.out.println("dia " + dia);
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						if (calcularRetardoEstudiante(cn,
								estudiante.getEvalCodigo(), filtro) == 1) {
							falla.setFaChecked(1);
						}
						lFalla.add(falla);
					}

				}

				if (filtro.getFilQuincena() == ParamVO.QUINCENA_DOS) {
					for (int dia = 16; dia <= filtro.getFilTotalDias(); dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						if (calcularRetardoEstudiante(cn,
								estudiante.getEvalCodigo(), filtro) == 1) {
							falla.setFaChecked(1);
						}
						lFalla.add(falla);
					}

				}

				// System.out.println("estudiante.setEvalFallas(lFalla); 4");
				estudiante.setEvalFallas(lFalla);
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
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
	 * @function:
	 * @param cn
	 * @param estudiante
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public int calcularRetardoEstudiante(Connection cn, long estudiante,
			FiltroInasistencia filtro) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));
			String dia_ = (filtro.getFilDia() < 10 ? "0" + filtro.getFilDia()
					: String.valueOf(filtro.getFilDia()));
			// System.out.println("FECHA DADA=" + dia_ + "/" + mes_ + "/" +
			// getVigenciaNumerico());
			st = cn.prepareStatement(rb.getString("getRetardo"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setLong(i++, estudiante);
			st.setString(i++, dia_ + "/" + mes_ + "/" + getVigenciaNumerico());
			st.setLong(i++, filtro.getFilPeriodo());
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next()) {
				return (1);
			} else {
				return (0);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param cn
	 * @param estudiante
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public int calcularSalidaTardeEstudiante(Connection cn, long estudiante,
			FiltroInasistencia filtro) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));
			String dia_ = (filtro.getFilDia() < 10 ? "0" + filtro.getFilDia()
					: String.valueOf(filtro.getFilDia()));
			// System.out.println("FECHA DADA=" + dia_ + "/" + mes_ + "/" +
			// getVigenciaNumerico());
			st = cn.prepareStatement(rb.getString("getSalidaTarde"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setLong(i++, estudiante);
			st.setString(i++, dia_ + "/" + mes_ + "/" + getVigenciaNumerico());
			st.setLong(i++, filtro.getFilPeriodo());
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next()) {
				return (1);
			} else {
				return (0);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public List getEstudianteSalidaTarde(FiltroInasistencia filtro)
			throws Exception {
		List l = new ArrayList();
		List lFalla = new ArrayList();
		FallaEstudiante falla = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InasistenciaEstudiante estudiante = null;
		int i = 0, diaSemana = 0;
		;
		int index = 1;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
			calendar.set(Calendar.MONTH, filtro.getFilMes());
			filtro.setFilTotalDias(calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			cn = cursor.getConnection();
			// calcular el codigo de jerarquia
			// saber si hay clase de sabado o de domingo
			st = cn.prepareStatement(rb.getString("getSabadoDomingo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilSabado(rs.getInt(1));
				filtro.setFilDomingo(rs.getInt(2));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("getEstudianteSalidaTarde."
					+ filtro.getFilOrden()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			// st.setLong(i++,getVigenciaNumerico());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lFalla = new ArrayList();
				estudiante = new InasistenciaEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				// poner la lista de fallas
				if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {

					for (int dia = 1; dia <= 15; dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						if (calcularSalidaTardeEstudiante(cn,
								estudiante.getEvalCodigo(), filtro) == 1) {
							falla.setFaChecked(1);
						}
						lFalla.add(falla);
					}
				}

				if (filtro.getFilQuincena() == ParamVO.QUINCENA_DOS) {

					for (int dia = 16; dia <= filtro.getFilTotalDias(); dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) {// cae // un // sabado
						 * // pero // no // hay // clase // los // sabados
						 * continue; } if (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) {// cae // un // domingo
						 * // pero // no // hay // clase // ese // dia continue;
						 * }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						if (calcularSalidaTardeEstudiante(cn,
								estudiante.getEvalCodigo(), filtro) == 1) {
							falla.setFaChecked(1);
						}
						lFalla.add(falla);
					}
				}
				// System.out.println("estudiante.setEvalFallas(lFalla); 11");
				estudiante.setEvalFallas(lFalla);
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
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
	 * @function:
	 * @param filtro
	 * @throws Exception
	 */
	public void guardarRetardo(FiltroInasistencia filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		long est = 0;
		int dia = 0;
		String fecha = null;
		String fechaIni = null;
		String fechaFin = null;
		String asig[] = null;
		try {
			long vig = getVigenciaNumerico();
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));

			if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
				fechaIni = "01/" + mes_ + "/" + vig;
				fechaFin = 15 + "/" + mes_ + "/" + vig;

			} else {
				fechaIni = "16/" + mes_ + "/" + vig;
				fechaFin = filtro.getFilTotalDias() + "/" + mes_ + "/" + vig;
			}
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// ELIMINAR
			st = cn.prepareStatement(rb.getString("eliminarRetardo"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setString(i++, fechaIni);
			st.setString(i++, fechaFin);
			st.setInt(i++, filtro.getFilPeriodo());
			st.setLong(i++, vig);
			st.executeUpdate();
			st.close();
			// INGRESAR
			if (filtro.getFalla() != null) {
				String[] falla = filtro.getFalla();
				st = cn.prepareStatement(rb.getString("ingresarRetardo"));
				st.clearBatch();
				for (int j = 0; j < falla.length; j++) {
					// System.out.println("-FALLA: " + falla[j]);
					asig = falla[j].replace('|', ':').split(":");
					if (asig != null && asig.length >= 2) {
						i = 1;
						est = Long.parseLong(asig[0]);
						dia = Integer.parseInt(asig[1]);
						fecha = (dia < 10 ? "0" + dia : String.valueOf(dia))
								+ "/" + mes_ + "/" + vig;
						// System.out.println("-"+asig[3]+"-FECHA: " + fecha);
						st.setLong(i++, filtro.getFilJerarquia());
						st.setLong(i++, est);
						st.setString(i++, fecha);
						st.setInt(i++, filtro.getFilPeriodo());
						st.setLong(i++, vig);
						st.addBatch();
					}
				}
				st.executeBatch();
			}
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public Collection getlismeses(long ins, long vig) {
		Connection cn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Collection list = null;
		Collection list2 = new ArrayList();
		try {
			cn = cursor.getConnection();
			pst = cn.prepareStatement("select TO_NUMBER(to_char(prf_ini_per1,'MM')),TO_NUMBER(to_char(prf_FIN_per1,'MM')),TO_NUMBER(to_char(prf_ini_per2,'MM')),TO_NUMBER(to_char(prf_FIN_per2,'MM')),TO_NUMBER(to_char(prf_ini_per3,'MM')),TO_NUMBER(to_char(prf_FIN_per3,'MM')),TO_NUMBER(to_char(prf_ini_per4,'MM')),TO_NUMBER(to_char(prf_FIN_per4,'MM')),TO_NUMBER(to_char(prf_ini_per5,'MM')),TO_NUMBER(to_char(prf_FIN_per5,'MM')),TO_NUMBER(to_char(prf_ini_per6,'MM')),TO_NUMBER(to_char(prf_FIN_per6,'MM')),TO_NUMBER(to_char(prf_ini_per7,'MM')),TO_NUMBER(to_char(prf_FIN_per7,'MM')) from periodo_prgfechas where prfcodinst=? and prfvigencia=?");
			pst.setLong(1, ins);
			pst.setLong(2, vig);
			rs = pst.executeQuery();
			int m = rs.getMetaData().getColumnCount();
			list = new ArrayList();
			Object[] o;
			int j = 0, f = 0;
			while (rs.next()) {
				f++;
				o = new Object[m];
				for (j = 1; j <= m; j++)
					o[j - 1] = rs.getString(j);
				list.add(o);
			}
			if (list.size() > 0) {
				list2.addAll(list);
			}
			rs.close();
			pst.close();
			// System.out.println("accesos:"+(list!=null?list.size():0));
			return list2;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return null;
	}

	public void guardarSalidaTarde(FiltroInasistencia filtro) throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		int i = 0;
		long est = 0;
		int dia = 0;
		String fecha = null;
		String fechaIni = null;
		String fechaFin = null;
		String asig[] = null;
		try {
			long vig = getVigenciaNumerico();
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));

			if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
				fechaIni = "01/" + mes_ + "/" + vig;
				fechaFin = 15 + "/" + mes_ + "/" + vig;

			} else {
				fechaIni = "16/" + mes_ + "/" + vig;
				fechaFin = filtro.getFilTotalDias() + "/" + mes_ + "/" + vig;
			}

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// ELIMINAR
			st = cn.prepareStatement(rb.getString("eliminarSalidaTarde"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setString(i++, fechaIni);
			st.setString(i++, fechaFin);
			st.setInt(i++, filtro.getFilPeriodo());
			st.setLong(i++, vig);
			st.executeUpdate();
			st.close();
			// INGRESAR
			if (filtro.getFalla() != null) {
				String[] falla = filtro.getFalla();
				st = cn.prepareStatement(rb.getString("ingresarSalidaTarde"));
				st.clearBatch();
				for (int j = 0; j < falla.length; j++) {
					i = 1;
					// System.out.println("-FALLA: " + falla[j]);
					asig = falla[j].replace('|', ':').split(":");
					if (asig != null && asig.length >= 2) {
						est = Long.parseLong(asig[0]);
						dia = Integer.parseInt(asig[1]);
						fecha = (dia < 10 ? "0" + dia : String.valueOf(dia))
								+ "/" + mes_ + "/" + vig;
						// System.out.println("-"+asig[3]+"-FECHA: " + fecha);
						st.setLong(i++, filtro.getFilJerarquia());
						st.setLong(i++, est);
						st.setString(i++, fecha);
						st.setInt(i++, filtro.getFilPeriodo());
						st.setLong(i++, vig);
						st.addBatch();
					}
				}
				st.executeBatch();
			}
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	/**
	 * @function:
	 * @param inst
	 * @param met
	 * @param gra
	 * @return
	 */
	public List getAjaxAsignatura(long inst, int met, int gra) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			long vigencia = getVigenciaNumerico();
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxAsignatura"));
			i = 1;
			st.setLong(i++, inst);
			st.setInt(i++, met);
			st.setInt(i++, gra);
			st.setLong(i++, vigencia);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
	 * @function:
	 * @param filtro
	 * @return
	 * @throws Exception
	 */
	public List getEstudianteInasistenciaSimple(FiltroInasistencia filtro)
			throws Exception {
		List l = new ArrayList();
		List lFalla = new ArrayList();
		FallaEstudiante falla = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		InasistenciaEstudiante estudiante = null;
		int i = 0, diaSemana = 0;
		int[] inasis = null;
		try {
			int vig = (int) getVigenciaNumerico();
			Calendar calendar = Calendar.getInstance();
			calendar.setLenient(false);
			calendar.set(Calendar.YEAR, vig);
			calendar.set(Calendar.MONTH, filtro.getFilMes());
			// System.out.println("Dias del mes: " +
			// calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			if (filtro.getFilMes() == Calendar.FEBRUARY) {
				if ((vig % 4 == 0) && ((vig % 100 != 0) || (vig % 400 == 0))) {
					// System.out.println("El ano es bisiesto");
					filtro.setFilTotalDias(29);
				} else {
					// System.out.println("El ano no es bisiesto");
					filtro.setFilTotalDias(28);
				}
			} else {
				filtro.setFilTotalDias(calendar
						.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			// System.out.println("ACTUAL MAXIMO DE DIA:"+filtro.getFilTotalDias()+"//"+getVigenciaNumerico()+"//"+filtro.getFilMes());
			// System.out.println("datos:"+filtro.getFilInstitucion()+"//"+filtro.getFilSede()+"//"+filtro.getFilJornada()+"//"+filtro.getFilMetodologia()+"//"+filtro.getFilGrado()+"//"+filtro.getFilGrupo()+"//"+filtro.getFilPeriodo());
			cn = cursor.getConnection();
			// saber si hay clase de sabado o de domingo
			st = cn.prepareStatement(rb.getString("getSabadoDomingo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilSabado(rs.getInt(1));
				filtro.setFilDomingo(rs.getInt(2));
			}
			rs.close();
			st.close();
			// calcular el codigo de jerarquia
			st = cn.prepareStatement(rb.getString("getJerarquiaGrupo"));
			i = 1;
			st.setLong(i++, filtro.getFilInstitucion());
			st.setInt(i++, filtro.getFilSede());
			st.setInt(i++, filtro.getFilJornada());
			st.setInt(i++, filtro.getFilMetodologia());
			st.setInt(i++, filtro.getFilGrado());
			st.setInt(i++, filtro.getFilGrupo());
			rs = st.executeQuery();
			if (rs.next()) {
				filtro.setFilJerarquia(rs.getLong(1));
			}
			rs.close();
			st.close();
			// System.out.println("1.))" + "getEstudianteInasistencia." +
			// filtro.getFilOrden() + "." + filtro.getFilPeriodo());
			// System.out.println("2." + filtro.getFilJerarquia());
			st = cn.prepareStatement(rb
					.getString("getEstudianteInasistenciaSimple."
							+ filtro.getFilOrden()));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			// st.setLong(i++,getVigenciaNumerico());
			rs = st.executeQuery();
			int index = 1;
			// String materias = null,materias0 = null;
			while (rs.next()) {
				i = 1;
				lFalla = new ArrayList();
				estudiante = new InasistenciaEstudiante();
				estudiante.setEvalCodigo(rs.getLong(i++));
				estudiante.setEvalApellido(rs.getString(i++));
				estudiante.setEvalNombre(rs.getString(i++));
				estudiante.setEvalConsecutivo(index++);
				// poner la lista de fallas
				if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
					for (int dia = 1; dia <= 15; dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) { continue; } if
						 * (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) { continue; }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						inasis = calcularInasistenciaSimpleEstudiante(cn,
								estudiante.getEvalCodigo(), filtro);
						falla.setFaChecked(inasis[0]);
						falla.setFaMotivo(inasis[1]);
						lFalla.add(falla);
					}
				}

				// /////////

				if (filtro.getFilQuincena() == ParamVO.QUINCENA_DOS) {
					for (int dia = 16; dia <= filtro.getFilTotalDias(); dia++) {
						filtro.setFilDia(dia);
						calendar.set(Calendar.DAY_OF_MONTH, dia);
						diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
						/*
						 * if (diaSemana == Calendar.SATURDAY &&
						 * filtro.getFilSabado() == 0) { continue; } if
						 * (diaSemana == Calendar.SUNDAY &&
						 * filtro.getFilDomingo() == 0) { continue; }
						 */
						falla = new FallaEstudiante();
						falla.setFaDia(dia);
						falla.setFaDiaSemana(diaSemana);
						inasis = calcularInasistenciaSimpleEstudiante(cn,
								estudiante.getEvalCodigo(), filtro);
						falla.setFaChecked(inasis[0]);
						falla.setFaMotivo(inasis[1]);
						lFalla.add(falla);
					}
				}
				// System.out.println("estudiante.setEvalFallas(lFalla); 6" );
				estudiante.setEvalFallas(lFalla);
				l.add(estudiante);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
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

	public int[] calcularInasistenciaSimpleEstudiante(Connection cn,
			long estudiante, FiltroInasistencia filtro) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));
			String dia_ = (filtro.getFilDia() < 10 ? "0" + filtro.getFilDia()
					: String.valueOf(filtro.getFilDia()));
			st = cn.prepareStatement(rb.getString("getInasistenciaSimple"));
			i = 1;
			st.setLong(i++, estudiante);
			st.setString(i++, dia_ + "/" + mes_ + "/" + getVigenciaNumerico());
			st.setLong(i++, filtro.getFilPeriodo());
			st.setLong(i++, getVigenciaNumerico());
			rs = st.executeQuery();
			if (rs.next()) {
				return (new int[] { 1, rs.getInt(1) });
			} else {
				return (new int[] { 0, -99 });
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public void guardarInasistenciaSimple(FiltroInasistencia filtro)
			throws Exception {
		Connection cn = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		int i = 0;
		long est = 0;
		int dia = 0;
		String fecha = null;
		String fechaIni = null;
		String fechaFin = null;
		int mot = 0;
		String asig[] = null;
		long codigo = 0;
		try {
			long vig = getVigenciaNumerico();
			String mes_ = ((filtro.getFilMes() + 1) < 10 ? "0"
					+ (filtro.getFilMes() + 1) : String.valueOf(filtro
					.getFilMes() + 1));

			if (filtro.getFilQuincena() == ParamVO.QUINCENA_UNO) {
				fechaIni = "01/" + mes_ + "/" + vig;
				fechaFin = 15 + "/" + mes_ + "/" + vig;

			} else {
				fechaIni = "16/" + mes_ + "/" + vig;
				fechaFin = filtro.getFilTotalDias() + "/" + mes_ + "/" + vig;
			}

			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			// ELIMINAR
			st = cn.prepareStatement(rb.getString("eliminarInasistenciaSimple"));
			i = 1;
			st.setLong(i++, filtro.getFilJerarquia());
			st.setString(i++, fechaIni);
			st.setString(i++, fechaFin);
			st.setInt(i++, filtro.getFilPeriodo());
			st.setLong(i++, vig);
			st.executeUpdate();
			st.close();
			// INGRESAR
			if (filtro.getFalla() != null) {
				String[] falla = filtro.getFalla();
				for (int j = 0; j < falla.length; j++) {
					i = 1;
					// System.out.println("-FALLA: " + falla[j]);
					asig = falla[j].replace('|', ':').split(":");
					if (asig != null && asig.length >= 3) {
						est = Long.parseLong(asig[0]);
						dia = Integer.parseInt(asig[1]);
						mot = Integer.parseInt(asig[2]);
						fecha = (dia < 10 ? "0" + dia : String.valueOf(dia))
								+ "/" + mes_ + "/" + vig;
						codigo = getCodigoAsistencia(cn, est);
						// System.out.println("-"+asig[3]+"-FECHA: " + fecha);

						//

						if (mot != -99) {
							st = cn.prepareStatement(rb
									.getString("ingresarInasistenciaSimple"));
							st.setLong(i++, est);
							st.setLong(i++, codigo);
							st.setString(i++, fecha);
							st.setInt(i++, mot);
							st.setInt(i++, filtro.getFilPeriodo());
							st.setLong(i++, vig);
							st.executeUpdate();
							st.close();
						} else {
							st2 = cn.prepareStatement(rb
									.getString("ingresarInasistenciaSimple2"));
							st2.setLong(i++, est);
							st2.setLong(i++, codigo);
							st2.setString(i++, fecha);
							st2.setInt(i++, filtro.getFilPeriodo());
							st2.setLong(i++, vig);
							st2.executeUpdate();
							st2.close();
						}
					}
				}
			}
			cn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
			throw new Exception(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
			throw new Exception(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st2);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public long getCodigoAsistencia(Connection cn, long est)
			throws SQLException {
		System.out.println(formaFecha.format(new Date())
				+ " getCodigoAsistencia");
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {

			if (cn == null) {
				System.out.println("cn es null");
			}
			st = cn.prepareStatement(rb.getString("getCodigoAsistencia"));
			i = 1;
			st.setLong(i++, est);
			System.out.println("est " + est);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			} else {
				throw new SQLException("No encontrado");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new SQLException(sqle.getMessage());
		} catch (Exception sqle) {
			sqle.printStackTrace();
			throw new SQLException(sqle.getMessage());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
	}

	public String getnomulp(long ins, long vig) {
		System.out.println(formaFecha.format(new Date())
				+ " getCodigoAsistencia");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement("select insparnomperdef from institucion_parametro where insparcodinst=? and insparvigencia=?");
			i = 1;
			st.setLong(i++, ins);
			st.setLong(i++, vig);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);

			} else {
				throw new SQLException("No encontrado");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
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
		return null;
	}

	public boolean tieneHorario(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("tieneHorarioGlobal"));
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0)
					return false;
			}
			rs.close();
			st.close();
			st = cn.prepareStatement(rb.getString("tieneHorarioInstitucion"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0)
					return false;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return true;
	}

	public List getAllMetodologia(long inst) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		ItemVO itemVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAllMetodologia"));
			i = 1;
			st.setLong(i++, inst);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				itemVO = new ItemVO();
				itemVO.setCodigo(rs.getLong(i++));
				itemVO.setNombre(rs.getString(i++));
				l.add(itemVO);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error obteniendo datos. Posible problema: "
					+ sqle.toString());
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
}
