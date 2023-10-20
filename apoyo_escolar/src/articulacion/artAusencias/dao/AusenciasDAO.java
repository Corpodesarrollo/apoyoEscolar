package articulacion.artAusencias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import articulacion.artAusencias.vo.AsignaturaClaseVO;
import articulacion.artAusencias.vo.AsignaturaVO;
import articulacion.artAusencias.vo.DatosVO;
import articulacion.artAusencias.vo.DiaVO;
import articulacion.artAusencias.vo.EspecialidadVO;
import articulacion.artAusencias.vo.AusenciasVO;
import articulacion.artAusencias.vo.EstDiasVO;
import articulacion.artAusencias.vo.EstudianteVO;
import articulacion.artAusencias.vo.GrupoVO;
import articulacion.artAusencias.vo.ListaMotivosVO;
import articulacion.artAusencias.vo.MotivoVO;
import articulacion.artAusencias.vo.TempMotivoVO;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import articulacion.evaluacionArt.vo.JornadaVO;
import siges.exceptions.InternalErrorException;

public class AusenciasDAO extends Dao {

	private ResourceBundle rb;

	public AusenciasDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.artAusencias.bundle.sentencias");
	}

	public List getEstudiantes(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		EstudianteVO estudianteVO = null;
		int i = 1;
		String inicio = "1/" + datosVO.getMes() + "/" + datosVO.getAnVigencia();
		String fin = getDiasmes(datosVO.getMes()) + "/" + datosVO.getMes()
				+ "/" + datosVO.getAnVigencia();
		int diasMes = getDiasmes(datosVO.getMes());
		try {
			cn = cursor.getConnection();
			if (datosVO.getComponente() == 1) {
				st = cn.prepareStatement(rb.getString("getEstudiantes"
						+ datosVO.getOrdenado()));
			}
			if (datosVO.getComponente() == 2) {
				st = cn.prepareStatement(rb.getString("getEstudiantes"
						+ (datosVO.getOrdenado() + 2)));
			}
			st.setLong(i++, datosVO.getGrupo());
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				estudianteVO = new EstudianteVO();
				estudianteVO.setCodigo(rs.getInt(i++));
				estudianteVO.setDocumento(rs.getString(i++));
				estudianteVO.setApellido(rs.getString(i++));
				estudianteVO.setNombre(rs.getString(i++));
				estudianteVO.setDias(diasAusencia(datosVO, diasMes, inicio,
						fin, estudianteVO.getCodigo(), cn));
				l.add(estudianteVO);
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

	public List diasAusencia(DatosVO datosVO, int diasMes, String inicio,
			String fin, long codigo, Connection cn) throws Exception {

		List l = new ArrayList();
		String fecha = "";
		int i = 1;

		String[] habilitados = new String[7];
		DiaVO diaVO = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
		calendar.set(Calendar.MONTH, (datosVO.getMes() - 1));

		// System.out.println("el numero de dnas del mes "+filtro.getMes()+"="+getDiasmes(filtro.getMes()));
		for (int dia = 1; dia <= getDiasmes(datosVO.getMes()); dia++) {
			calendar.set(Calendar.DAY_OF_MONTH, dia);
			fecha = "" + dia + "/" + datosVO.getMes() + "/"
					+ datosVO.getAnVigencia();
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				if (datosVO.getDomingo() == 1) {
					diaVO = new DiaVO();
					diaVO.setCodigo(dia);
					diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
					diaVO.setCheck(checked(cn, fecha, codigo));
					if (habilitados[0] == null) {
						diaVO.setHabilitar(habilitado(codigo,
								datosVO.getGrupo(),
								calendar.get(Calendar.DAY_OF_WEEK) - 1,
								datosVO.getAnVigencia(),
								datosVO.getPerVigencia(), cn));
						habilitados[0] = "" + diaVO.isHabilitar();
						// System.out.println("Domingo Habilitar="+diaVO.isHabilitar());
					} else {
						if (habilitados[0].equals("true"))
							diaVO.setHabilitar(true);
						// System.out.println("Domingo habilitar="+diaVO.isHabilitar());
					}
					l.add(diaVO);
				}
				break;
			case Calendar.MONDAY:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setCheck(checked(cn, fecha, codigo));
				if (habilitados[1] == null) {
					diaVO.setHabilitar(habilitado(codigo, datosVO.getGrupo(),
							calendar.get(Calendar.DAY_OF_WEEK) - 1,
							datosVO.getAnVigencia(), datosVO.getPerVigencia(),
							cn));
					habilitados[1] = "" + diaVO.isHabilitar();
					// System.out.println("Lunes Habilitar="+diaVO.isHabilitar());
				} else {
					if (habilitados[1].equals("true"))
						diaVO.setHabilitar(true);
					// System.out.println("Lunes habilitar="+diaVO.isHabilitar());
				}
				l.add(diaVO);
				break;
			case Calendar.TUESDAY:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setCheck(checked(cn, fecha, codigo));
				if (habilitados[2] == null) {
					diaVO.setHabilitar(habilitado(codigo, datosVO.getGrupo(),
							calendar.get(Calendar.DAY_OF_WEEK) - 1,
							datosVO.getAnVigencia(), datosVO.getPerVigencia(),
							cn));
					habilitados[2] = "" + diaVO.isHabilitar();
					// System.out.println("Martes Habilitar="+diaVO.isHabilitar());
				} else {
					if (habilitados[2].equals("true"))
						diaVO.setHabilitar(true);
					// System.out.println("Martes habilitar="+diaVO.isHabilitar());
				}
				l.add(diaVO);
				break;
			case Calendar.WEDNESDAY:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setCheck(checked(cn, fecha, codigo));
				if (habilitados[3] == null) {
					diaVO.setHabilitar(habilitado(codigo, datosVO.getGrupo(),
							calendar.get(Calendar.DAY_OF_WEEK) - 1,
							datosVO.getAnVigencia(), datosVO.getPerVigencia(),
							cn));
					habilitados[3] = "" + diaVO.isHabilitar();
					// System.out.println("Miercoles Habilitar="+diaVO.isHabilitar());
				} else {
					if (habilitados[3].equals("true"))
						diaVO.setHabilitar(true);
					// System.out.println("Miercoles habilitar="+diaVO.isHabilitar());
				}
				l.add(diaVO);
				break;
			case Calendar.THURSDAY:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setCheck(checked(cn, fecha, codigo));
				if (habilitados[4] == null) {
					diaVO.setHabilitar(habilitado(codigo, datosVO.getGrupo(),
							calendar.get(Calendar.DAY_OF_WEEK) - 1,
							datosVO.getAnVigencia(), datosVO.getPerVigencia(),
							cn));
					habilitados[4] = "" + diaVO.isHabilitar();
					// System.out.println("Jueves Habilitar="+diaVO.isHabilitar());
				} else {
					if (habilitados[4].equals("true"))
						diaVO.setHabilitar(true);
					// System.out.println("Jueves habilitar="+diaVO.isHabilitar());
				}
				l.add(diaVO);
				break;
			case Calendar.FRIDAY:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setCheck(checked(cn, fecha, codigo));
				if (habilitados[5] == null) {
					diaVO.setHabilitar(habilitado(codigo, datosVO.getGrupo(),
							calendar.get(Calendar.DAY_OF_WEEK) - 1,
							datosVO.getAnVigencia(), datosVO.getPerVigencia(),
							cn));
					habilitados[5] = "" + diaVO.isHabilitar();
					// System.out.println("Viernes Habilitar="+diaVO.isHabilitar());
				} else {
					if (habilitados[5].equals("true"))
						diaVO.setHabilitar(true);
					// System.out.println("Viernes habilitar="+diaVO.isHabilitar());
				}
				l.add(diaVO);
				break;
			case Calendar.SATURDAY:
				if (datosVO.getSabado() == 1) {
					diaVO = new DiaVO();
					diaVO.setCodigo(dia);
					diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
					diaVO.setCheck(checked(cn, fecha, codigo));
					if (habilitados[6] == null) {
						diaVO.setHabilitar(habilitado(codigo,
								datosVO.getGrupo(),
								calendar.get(Calendar.DAY_OF_WEEK) - 1,
								datosVO.getAnVigencia(),
								datosVO.getPerVigencia(), cn));
						habilitados[6] = "" + diaVO.isHabilitar();
						// System.out.println("Sabado Habilitar="+diaVO.isHabilitar());
					} else {
						if (habilitados[6].equals("true"))
							diaVO.setHabilitar(true);
						// System.out.println("Sabado habilitar="+diaVO.isHabilitar());
					}
					l.add(diaVO);
				}
				break;
			}
		}
		// System.out.println("cantidad de datos = "+l.size());
		return l;
	}

	public boolean checked(Connection cn, String fecha, long codigo)
			throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean estado = false;
		int i = 1;
		try {
			st = cn.prepareStatement(rb.getString("getAusenciaFecha"));
			// System.out.println("codigo---->="+codigo);
			// System.out.println("fecha---->="+fecha);
			st.setLong(i++, codigo);
			st.setString(i++, fecha);
			rs = st.executeQuery();
			// System.out.println("busca dia check");
			while (rs.next()) {
				estado = true;
				// System.out.println("encuentra ausencia en fecha= "+fecha);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return estado;
	}

	public boolean habilitado(long codEst, long grupo, int dia, int anho,
			int periodo, Connection cn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean habilitado = false;
		int pos = 1;
		try {
			// System.out.println("--------------Habilitado--------------");
			/*
			 * System.out.println(rb.getString("getListaAsignaturas"));
			 * System.out.println("el estudi="+codEst);
			 * System.out.println("el grupo="+grupo);
			 * System.out.println("el dna="+dia);
			 */
			st = cn.prepareStatement(rb.getString("getListaAsignaturas"));
			st.setLong(pos++, grupo);
			st.setInt(pos++, dia);
			st.setLong(pos++, codEst);
			st.setInt(pos++, anho);
			st.setInt(pos++, periodo);
			rs = st.executeQuery();
			if (rs.next()) {
				habilitado = true;
				// System.out.println("Este dna tiene asignaturas "+dia);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
			} catch (InternalErrorException inte) {
			}
		}
		return habilitado;
	}

	public EspecialidadVO[] getListaEspecialidad(String insti) {
		EspecialidadVO[] especialidad = null;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List la = new ArrayList();
		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getEspecialidad"));
			ps.setString(posicion++, insti);
			posicion = 1;
			rs = ps.executeQuery();
			EspecialidadVO a = null;
			while (rs.next()) {
				a = new EspecialidadVO();
				posicion = 1;
				a.setCodigo(rs.getInt(posicion++));
				a.setNombre(rs.getString(posicion++));
				la.add(a);
			}
			rs.close();
			ps.close();
			if (!la.isEmpty()) {
				int i = 0;
				especialidad = new EspecialidadVO[la.size()];
				Iterator iterator = la.iterator();
				while (iterator.hasNext())
					especialidad[i++] = (EspecialidadVO) (iterator.next());
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error Posible problema: " + sqle);
		} catch (Exception sqle) {
			sqle.printStackTrace();
			setMensaje("Error Posible problema: " + sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(ps);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
				inte.printStackTrace();
			}
		}
		return especialidad;
	}

	public List getAjaxGrupo(long inst, int metod, int sede, int jornada,
			int anVigencia, int perVigencia, int componente, long especialidad) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		GrupoVO lp = null;
		int i = 1;
		try {
			if (inst == 0 || sede == 0 || jornada == 0 || anVigencia == 0
					|| perVigencia == 0 || componente == -99) {
				return null;
			}
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAjaxGrupo" + componente));

			st.setLong(i++, inst);
			st.setLong(i++, sede);
			st.setLong(i++, jornada);
			st.setLong(i++, anVigencia);
			st.setLong(i++, perVigencia);
			st.setLong(i++, componente);
			if (componente == 2)
				st.setLong(i++, especialidad);

			// System.out.println("Metodo grupo");
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				// System.out.println("Encuentra datos en grupo");
				lp = new GrupoVO();
				lp.setCodigo(rs.getLong(i++));
				lp.setConsecutivo(rs.getString(i++));
				lpA.add(lp);
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
		return lpA;
	}

	public List getListaMotivos() {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		MotivoVO lm = null;
		int i = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getListaMotivos"));
			i = 1;
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				lm = new MotivoVO();
				lm.setCodigo(rs.getInt(i++));
				lm.setNombre(rs.getString(i++));
				lpA.add(lm);
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
		return lpA;
	}

	public List getListaAsignaturas(String codEst, long grupo, String dia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lpA = new ArrayList();
		MotivoVO lm = null;
		int pos = 1, i;
		try {
			i = 1;
			cn = cursor.getConnection();
			/*
			 * System.out.println(rb.getString("getListaAsignaturas"));
			 * System.out.println("el estudi="+codEst);
			 * System.out.println("el grupo="+grupo);
			 * System.out.println("el dna="+dia);
			 */
			st = cn.prepareStatement(rb.getString("getListaAsignaturas"));
			st.setString(pos++, codEst);
			st.setLong(pos++, grupo);
			st.setString(pos++, dia);
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				// System.out.println("encuetra asign inscritas ");
				lm = new MotivoVO();
				lm.setCodigo(rs.getInt(i++));
				lm.setNombre(rs.getString(i++));
				lm.setClase(rs.getString(i++));
				lpA.add(lm);
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
		return lpA;
	}

	public boolean insertaAusencias(Connection cn, AusenciasVO ausenciasVO)
			throws SQLException {
		// System.out.println("llega al metodo de insercion");
		boolean retorno = false;
		PreparedStatement ps = null;
		int posicion = 1;
		try {
			if (ausenciasVO.getArtAusCodAsig() != null) {
				// System.out.println("Ingresa ausencia: "+ausenciasVO.getArtAusCodAsig().length);
				ps = cn.prepareStatement(rb.getString("insertaAusencias"));
				for (int a = 0; a < ausenciasVO.getArtAusCodAsig().length; a++) {
					posicion = 1;

					ps.setLong(posicion++, ausenciasVO.getArtAusCodEstud());
					ps.setLong(posicion++, ausenciasVO.getArtAusCodAsig()[a]);
					ps.setLong(posicion++, ausenciasVO.getArtAusClaseAsig()[a]);
					ps.setString(posicion++, ausenciasVO.getArtAusFecha());
					ps.setLong(posicion++, ausenciasVO.getArtAusGrupoArt());
					ps.setLong(posicion++, ausenciasVO.getArtAusTipMotivo());
					if (ausenciasVO.getArtAusDescripcion() != "")
						ps.setString(posicion++,
								ausenciasVO.getArtAusDescripcion());
					else
						ps.setNull(posicion++, Types.VARCHAR);
					ps.addBatch();
				}
				ps.executeBatch();
			}
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("entra al problema de SQLException");
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(ps);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public List getJornada(String inst, String sede) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List l = new ArrayList();
		JornadaVO jornadaVO = null;
		int i = 0;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getJornada"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));
			st.setInt(i++, Integer.parseInt(sede));
			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				jornadaVO = new JornadaVO();
				jornadaVO.setSede(rs.getInt(i++));
				jornadaVO.setCodigo(rs.getInt(i++));
				jornadaVO.setNombre(rs.getString(i++));
				l.add(jornadaVO);
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

	public boolean adminInsercinn(DatosVO filtro, EstDiasVO estDiasVO,
			List motivos) {
		boolean retorno = true, defecto = true;
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			AusenciasVO ausenciasVO = null;
			Iterator itMotivos;
			long estudiante = 0;
			String dia = "";
			TempMotivoVO tempMotivoVO = null;
			Calendar calendario = Calendar.getInstance();
			String estDia[] = estDiasVO.getEstDia();
			if (estDia != null) {
				for (int a = 0; a < estDia.length; a++) {
					defecto = true;
					// System.out.println("DIA: "+estDia[a]);
					ausenciasVO = new AusenciasVO();
					estudiante = getCodEst(estDia[a]);
					dia = getDiaCodEst(estDia[a]);
					ausenciasVO.setArtAusCodEstud(estudiante);
					ausenciasVO.setArtAusGrupoArt(filtro.getGrupo());
					ausenciasVO.setArtAusFecha(dia + "/" + filtro.getMes()
							+ "/" + filtro.getAnVigencia());
					itMotivos = motivos.iterator();
					while (itMotivos.hasNext()) {

						tempMotivoVO = (TempMotivoVO) itMotivos.next();
						// System.out.println("datos a comparar---------------<>");
						// System.out.println(estudiante+"<->"+tempMotivoVO.getEstudiante()+"  "+dia+"<->"+tempMotivoVO.getDia());
						if (estudiante == tempMotivoVO.getEstudiante()
								&& dia.equals(tempMotivoVO.getDia())) {
							// System.out.println("entra if "+tempMotivoVO.getDescripcion());
							ausenciasVO.setArtAusClaseAsig(tempMotivoVO
									.getClaseAsig());
							ausenciasVO.setArtAusCodAsig(tempMotivoVO
									.getAsignaturas());
							ausenciasVO.setArtAusTipMotivo(tempMotivoVO
									.getMotivo());
							ausenciasVO.setArtAusDescripcion(tempMotivoVO
									.getDescripcion());
							defecto = false;
						}
						// System.out.println("entra al while: "+tempMotivoVO.getClaseAsig());
					}
					if (defecto) {
						calendario.set(Calendar.YEAR,
								(int) filtro.getAnVigencia());
						calendario.set(Calendar.MONTH,
								(int) (filtro.getMes() - 1));
						calendario.set(Calendar.DAY_OF_MONTH,
								Integer.parseInt(dia));
						// System.out.println(calendario.get(Calendar.DAY_OF_WEEK));
						AsignaturaClaseVO asignaturaClaseVO = getAsigEstudiante(
								cn, estudiante, filtro.getGrupo(),
								calendario.get(Calendar.DAY_OF_WEEK) - 1,
								filtro.getAnVigencia(), filtro.getPerVigencia());
						ausenciasVO.setArtAusClaseAsig(asignaturaClaseVO
								.getClase());
						ausenciasVO.setArtAusCodAsig(asignaturaClaseVO
								.getAsignatura());
						ausenciasVO.setArtAusTipMotivo(4);
						ausenciasVO.setArtAusDescripcion("");
						// System.out.println("entra al defecto: ");
						// System.out.println("entra al defecto: "+asignaturaClaseVO.getClase().length);
					}
					if (ausenciasVO != null)
						if (!insertaAusencias(cn, ausenciasVO))
							break;
				}
			}
			cn.commit();
			// System.out.println("ejecuta el commit");
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public boolean borrarAusencias(DatosVO filtro) {
		Connection cn = null;
		PreparedStatement st = null;
		boolean retorno = true;
		int posicion = 1;
		String inicio = "1/" + filtro.getMes() + "/" + filtro.getAnVigencia();
		String fin = getDiasmes(filtro.getMes()) + "/" + filtro.getMes() + "/"
				+ filtro.getAnVigencia();

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("borra_ausencias"));
			st.setLong(posicion++, filtro.getGrupo());
			st.setString(posicion++, inicio);
			st.setString(posicion++, fin);
			st.executeUpdate();
		} catch (SQLException e) {
			retorno = false;
//			System.out.println("error eliminando las ausencias del mes " + filtro.getMes());
			e.printStackTrace();
		} catch (InternalErrorException e) {
			e.printStackTrace();
			retorno = false;
		} finally {
			try {
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return retorno;
	}

	public List getDias(DatosVO filtro) throws Exception {
		List l = new ArrayList();
		String[] dias = { "", "D", "L", "M", "M", "J", "V", "S" };
		DiaVO diaVO = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, (int) getVigenciaNumerico());
		calendar.set(Calendar.MONTH, (filtro.getMes() - 1));
		// System.out.println("el numero de dnas del mes "+filtro.getMes()+"="+getDiasmes(filtro.getMes()));
		for (int dia = 1; dia <= getDiasmes(filtro.getMes()); dia++) {
			calendar.set(Calendar.DAY_OF_MONTH, dia);
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SATURDAY:
				if (filtro.getSabado() == 1) {
					diaVO = new DiaVO();
					diaVO.setCodigo(dia);
					diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
					diaVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
					l.add(diaVO);
				}
				break;
			case Calendar.SUNDAY:
				if (filtro.getDomingo() == 1) {
					diaVO = new DiaVO();
					diaVO.setCodigo(dia);
					diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
					diaVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
					l.add(diaVO);
				}
				break;
			default:
				// System.out.println("dia="+dia+ "-"
				// +calendar.get(Calendar.DAY_OF_WEEK));
				diaVO = new DiaVO();
				diaVO.setCodigo(dia);
				diaVO.setNumDia(calendar.get(Calendar.DAY_OF_WEEK));
				diaVO.setNombre(dias[calendar.get(Calendar.DAY_OF_WEEK)]);
				l.add(diaVO);
				break;
			}
		}
		// System.out.println("cantidad de datos = "+l.size());
		return l;
	}

	public int getDiasmes(int mes) {
		int dias = 0;
		if (mes >= 1 && mes <= 7) {
			if (mes == 2) {
				dias = 28;
			} else if (mes % 2 != 0) {
				dias = 31;
			} else {
				dias = 30;
			}
		} else if (mes >= 8 && mes <= 12) {
			if (mes % 2 != 0) {
				dias = 30;
			} else {
				dias = 31;
			}
		}
		return dias;
	}

	public long getCodEst(String estDia) {
		return Long.parseLong(estDia.substring(0, estDia.lastIndexOf("-")));
	}

	private String getDiaCodEst(String estDia) {
		return estDia.substring(estDia.lastIndexOf("-") + 1, estDia.length());
	}

	public AsignaturaClaseVO getAsigEstudiante(Connection cn, long codEst,
			long grupo, int dia, int anho, int per) throws SQLException {
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		AsignaturaClaseVO asignaturaClaseVO = new AsignaturaClaseVO();
		long[] asignaturas = null;
		int[] clase = null;
		int pos = 1, cant = 0;
		try {
			// System.out.println("valor de dia="+dia);
			st1 = cn.prepareStatement(rb.getString("numeroHoras"));
			st1.setLong(pos++, grupo);
			st1.setInt(pos++, dia);
			st1.setLong(pos++, codEst);
			st1.setInt(pos++, anho);
			st1.setInt(pos++, per);
			rs = st1.executeQuery();
			while (rs.next()) {
				cant = rs.getInt(1);
			}
			if (cant != 0) {
				// System.out.println("cant="+cant);
				asignaturas = new long[cant];
				clase = new int[cant];
			}
			// System.out.println("valores="+codEst+"//"+grupo+"//"+dia+"//"+cant);
			pos = 1;
			st2 = cn.prepareStatement(rb.getString("getListaAsignaturas"));
			st2.setLong(pos++, grupo);
			st2.setLong(pos++, dia);
			st2.setLong(pos++, codEst);
			st2.setInt(pos++, anho);
			st2.setInt(pos++, per);
			rs = st2.executeQuery();
			pos = 0;
			while (rs.next()) {
				// System.out.println("encuentra hora: "+rs.getInt(3));
				asignaturas[pos] = rs.getLong(1);
				clase[pos] = rs.getInt(3);
				pos++;
			}
			// System.out.println("termina");
			asignaturaClaseVO.setAsignatura(asignaturas);
			asignaturaClaseVO.setClase(clase);
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
				OperacionesGenerales.closeStatement(st1);
				OperacionesGenerales.closeStatement(st2);
			} catch (InternalErrorException inte) {
			}
		}
		return asignaturaClaseVO;
	}

	public AusenciasVO getAusencia(String codEst, DatosVO datosVO, String dia) {
		AusenciasVO ausenciasVO = new AusenciasVO();
		Connection cn = null;
		PreparedStatement st = null;
		PreparedStatement st1 = null;
		int pos = 1;
		ResultSet rs = null;
		String fecha = dia + "/" + datosVO.getMes() + "/"
				+ datosVO.getAnVigencia();
		int total = 0;
		try {
			cn = cursor.getConnection();

			st1 = cn.prepareStatement(rb.getString("getNumAusenciaFecha"));
			st1.setString(pos++, codEst);
			st1.setString(pos++, fecha);
			rs = st1.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
				// System.out.println("Total ausencias="+total);
			}
			if (total < 0) {
				pos = 1;
				int i = 1, cont = 0;
				long[] asig = new long[total];
				st = cn.prepareStatement(rb.getString("getAusenciaFecha"));
				st.setString(pos++, codEst);
				st.setString(pos++, fecha);
				rs = st.executeQuery();
				while (rs.next()) {
					i = 1;
					asig[cont] = rs.getLong(i++);
					ausenciasVO.setArtAusTipMotivo(rs.getLong(i++));
					ausenciasVO.setArtAusDescripcion(rs.getString(i++));
					// System.out.println("encuentra");
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			// setMensaje("Error obteniendo datos. Posible problema: "+getErrorCode(sqle));
		} catch (Exception sqle) {
			sqle.printStackTrace();
			// setMensaje("Error obteniendo datos. Posible problema: "+sqle.toString());
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeStatement(st1);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return ausenciasVO;
	}

	public boolean adminInsercinn(DatosVO filtro, EstDiasVO estDiasVO) {
		boolean retorno = true;
		Connection cn = null;
		if (estDiasVO.getEstDia() != null) {
			try {
				cn = cursor.getConnection();
				cn.setAutoCommit(false);
				AusenciasVO ausenciasVO = null;
				long estudiante = 0;
				String dia = "";
				Calendar calendario = Calendar.getInstance();
				for (int a = 0; a < estDiasVO.getEstDia().length; a++) {
					// System.out.println("----------------------------------------estudiante "+estudiante);
					ausenciasVO = new AusenciasVO();
					estudiante = getCodEst(estDiasVO.getEstDia()[a]);
					dia = getDiaCodEst(estDiasVO.getEstDia()[a]);

					ausenciasVO.setArtAusCodEstud(estudiante);
					ausenciasVO.setArtAusGrupoArt(filtro.getGrupo());
					ausenciasVO.setArtAusFecha(dia + "/" + filtro.getMes()
							+ "/" + filtro.getAnVigencia());

					// System.out.println("entra al defecto");
					calendario.set(Calendar.YEAR, (int) filtro.getAnVigencia());
					calendario.set(Calendar.MONTH, (int) (filtro.getMes() - 1));
					calendario
							.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
					// System.out.println(calendario.get(Calendar.DAY_OF_WEEK));
					AsignaturaClaseVO asignaturaClaseVO = getAsigEstudiante(cn,
							estudiante, filtro.getGrupo(),
							calendario.get(Calendar.DAY_OF_WEEK),
							filtro.getAnVigencia(), filtro.getPerVigencia());
					ausenciasVO
							.setArtAusClaseAsig(asignaturaClaseVO.getClase());
					ausenciasVO.setArtAusCodAsig(asignaturaClaseVO
							.getAsignatura());
					ausenciasVO.setArtAusTipMotivo(4);
					ausenciasVO.setArtAusDescripcion("");

					if (ausenciasVO != null)
						if (!insertaAusencias(cn, ausenciasVO))
							break;
				}
				cn.commit();
				// System.out.println("ejecuta el commit");
			} catch (InternalErrorException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					OperacionesGenerales.closeConnection(cn);
				} catch (InternalErrorException inte) {
				}
			}
		}
		return retorno;
	}

	public DatosVO paramsHorario(DatosVO datosVO) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int i = 1;
		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("params_horario"));
			st.setLong(i++, datosVO.getInstitucion());
			st.setLong(i++, datosVO.getSede());
			st.setLong(i++, datosVO.getJornada());
			st.setLong(i++, datosVO.getAnVigencia());
			st.setLong(i++, datosVO.getPerVigencia());
			st.setLong(i++, datosVO.getComponente());
			rs = st.executeQuery();
			while (rs.next()) {
				datosVO.setSabado(rs.getInt(1));
				datosVO.setDomingo(rs.getInt(2));
				// System.out.println("Domingo="+datosVO.getSabado());
				// System.out.println("Sabado="+datosVO.getDomingo());
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		return datosVO;
	}

	public ListaMotivosVO inicio(List listado, DatosVO datosVO) {
		ListaMotivosVO listaMotivosVO = new ListaMotivosVO();
		List listadoInicio = new ArrayList();
		Iterator it1 = listado.iterator();
		Iterator it2;
		EstudianteVO estudianteVO = null;
		DiaVO diaVO = null;
		String fecha = "";
		Connection cn = null;
		try {
			cn = cursor.getConnection();
			while (it1.hasNext()) {
				estudianteVO = (EstudianteVO) it1.next();
				it2 = estudianteVO.getDias().iterator();
				while (it2.hasNext()) {
					diaVO = (DiaVO) it2.next();
					if (diaVO.isCheck()) {
						// System.out.println("dia");
						fecha = diaVO.getCodigo() + "/" + datosVO.getMes()
								+ "/" + datosVO.getAnVigencia();
						listadoInicio.add(ausencia(cn, datosVO,
								estudianteVO.getCodigo(), fecha,
								diaVO.getCodigo()));
					}
				}
			}
		} catch (InternalErrorException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}
		// System.out.println("tamano de list devuelto="+listadoInicio.size());
		listaMotivosVO.setTempMotivoVO(listadoInicio);
		return listaMotivosVO;
	}

	public TempMotivoVO ausencia(Connection cn, DatosVO datosVO, long estud,
			String fecha, int dia) {
		TempMotivoVO tempMotivoVO = new TempMotivoVO();
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean cursorNulo = false;
		if (cn == null) {
			try {
				cn = cursor.getConnection();
				cursorNulo = true;
			} catch (InternalErrorException e1) {
				e1.printStackTrace();
			}
		}
		long[] asignatura = null;
		int[] clase = null;
		int i = 1, cant = 0, cont = 0;
		try {
			st = cn.prepareStatement(rb.getString("contarAusencia"));
			st.setLong(i++, datosVO.getGrupo());
			st.setString(i++, fecha);
			st.setLong(i++, estud);
			rs = st.executeQuery();
			while (rs.next()) {
				cant = rs.getInt(1);
				// System.out.println("encontrados="+cant);
			}

			i = 1;
			if (cant > 0) {
				asignatura = new long[cant];
				clase = new int[cant];
				st = cn.prepareStatement(rb.getString("getAusencia"));
				st.setLong(i++, datosVO.getGrupo());
				st.setString(i++, fecha);
				st.setLong(i++, estud);
				rs = st.executeQuery();
				tempMotivoVO.setDia("" + dia);
				while (rs.next()) {
					i = 1;
					tempMotivoVO.setEstudiante(rs.getLong(i++));
					tempMotivoVO.setMotivo(rs.getInt(i++));
					tempMotivoVO.setDescripcion(rs.getString(i++));
					asignatura[cont] = rs.getLong(i++);
					clase[cont] = rs.getInt(i++);
					cont++;
				}
				tempMotivoVO.setAsignaturas(asignatura);
				tempMotivoVO.setClaseAsig(clase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(st);
				if (cursorNulo)
					OperacionesGenerales.closeConnection(cn);
			} catch (InternalErrorException inte) {
			}
		}

		return tempMotivoVO;
	}
}
