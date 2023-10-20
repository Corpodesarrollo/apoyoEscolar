package articulacion.importarArticulacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import articulacion.importarArticulacion.objetos.CodigoEstudianteVO;
import articulacion.importarArticulacion.objetos.DatosVO;
import articulacion.importarArticulacion.objetos.EliminadoVO;
import articulacion.importarArticulacion.objetos.EstudianteVO;
import articulacion.importarArticulacion.objetos.ResultadoVO;
import articulacion.importarArticulacion.objetos.VaciosVO;
import articulacion.importarArticulacion.objetos.EspecialidadesVO;
import articulacion.plantillaArticulacion.vo.JornadaVO;

public class ImportarArticulacionDAO extends Dao {

	public ImportarArticulacionDAO(Cursor c) {
		super(c);
		rb = ResourceBundle
				.getBundle("articulacion.importarArticulacion.bundle.sentencias");
	}

	public List getAntiguos(DatosVO filter, String metodologia) {
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lista = new ArrayList();
		JornadaVO jornadaVO = null;
		CodigoEstudianteVO codigoVO = null;
		int i = 0;
		try {

			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("getAntiguos"));
			i = 1;
			st.setLong(i++, Integer.parseInt(filter.getInstitucion()));
			st.setLong(i++, Integer.parseInt(filter.getInstitucion()));
			st.setInt(i++, Integer.parseInt(filter.getSede()));
			st.setInt(i++, Integer.parseInt(filter.getJornada()));
			st.setInt(i++, Integer.parseInt(metodologia));
			st.setInt(i++, Integer.parseInt(filter.getGrado()));
			st.setInt(i++, Integer.parseInt(filter.getGrupo()));

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				codigoVO = new CodigoEstudianteVO();
				codigoVO.setCodigo(rs.getInt(i++));
				codigoVO.setEspecialidad(rs.getInt(i++));
				codigoVO.setSemestre(rs.getInt(i++));
				codigoVO.setGrupo(rs.getInt(i++));
				codigoVO.setNivelado(rs.getString(i++));
				lista.add(codigoVO);
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
		return lista;
	}

	public ResultadoVO importarEstudiante(DatosVO filtro, List l,
			String metodologia) {
		// System.out.println("Llega al dao");
		ResultadoVO resultadoVO = new ResultadoVO();
		Connection cn = null;
		PreparedStatement pst = null;
		PreparedStatement psA = null;
		ResultSet rs = null;
		int posicion = 1;
		int insertados = 0;
		int eliminados = 0;
		int actualizados = 0;
		int mio = 0;
		int ni = 5;
		int otro = 1;
		long tutor = 0;
		List antiguos;
		EstudianteVO estudianteVO = null;
		CodigoEstudianteVO antiguoVO = null;
		VaciosVO vaciosVO = null;
		antiguos = getAntiguos(filtro, metodologia);
		// System.out.println("LONGITUD DE ANTIGUOS:  "+antiguos.size());
		boolean bandera = false;
		boolean bandera2 = false;
		try {
			cn = cursor.getConnection();
			cn.setAutoCommit(false);
			if (l != null && l.size() > 0) {
				// System.out.println("el valor sel size= " + l.size());
				for (int i = 0; i < l.size(); i++) { // long lGrupo=0;
					bandera = false;
					estudianteVO = (EstudianteVO) l.get(i);

					// lGrupo=traerCodigoGrupo(estudianteVO.getGrupo());
					// System.out.println("El grupo que se va para todas las
					// operaciones es "+lGrupo);
					// System.out.println("Nuevo: "+estudianteVO.getCodigo());
					/*
					 * System.out.println("el valor de 2:
					 * "+estudianteVO.getEspecialidad()); System.out.println("el
					 * valor de 2: "+estudianteVO.getSemestre());
					 * System.out.println("el valor de 3:
					 * "+estudianteVO.getGrupo()); System.out.println("el valor
					 * de 4: "+estudianteVO.getNivelado());
					 * System.out.println("El valor de bandera "+bandera);
					 */

					// System.out.println("size de antiguos " +
					// antiguos.size());
					if (l != null && antiguos.size() > 0) {
						for (int j = 0; j < antiguos.size(); j++) {
							antiguoVO = (CodigoEstudianteVO) antiguos.get(j);
							// System.out.println("Antiguo: " +
							// antiguoVO.getCodigo());
							// System.out.println("Grupo del archivo " +
							// filtro.getGrupo());

							if (estudianteVO.getCodigo() == antiguoVO
									.getCodigo()) {
								String banderita = "";
								if (estudianteVO.getNivelado().equals("X")
										|| estudianteVO.getNivelado().equals(
												"x"))
									banderita = "1";
								else
									banderita = "0";
								if (estudianteVO.getEspecialidad() != antiguoVO
										.getEspecialidad()
										|| estudianteVO.getSemestre() != antiguoVO
												.getSemestre()
										|| Long.parseLong(filtro.getGrupo()) != antiguoVO
												.getGrupo()
										|| banderita != antiguoVO.getNivelado()) {
									bandera = true;
									// System.out.println("Entro a actualizar");
									int posicionA = 1;
									try {
										psA = cn.prepareStatement(rb
												.getString("actualizar"));
										psA.setLong(posicionA++,
												estudianteVO.getCodigo());
										psA.setLong(posicionA++, Long
												.parseLong(filtro
														.getInstitucion()));
										psA.setLong(posicionA++, estudianteVO
												.getAbreviaturaCodigo());
										// pst.setString(posicion++,estudianteVO.getDocumento());
										if (estudianteVO.getDocumento().equals(
												""))
											psA.setNull(posicion++,
													Types.VARCHAR);
										else
											psA.setString(posicionA++,
													estudianteVO.getDocumento());
										psA.setLong(posicionA++,
												estudianteVO.getEspecialidad());
										psA.setLong(posicionA++,
												estudianteVO.getSemestre());
										psA.setLong(posicionA++, Long
												.parseLong(filtro.getGrupo()));

										if (estudianteVO.getNivelado().equals(
												"X")
												|| estudianteVO.getNivelado()
														.equals("x"))
											estudianteVO.setNivelado1(otro);
										else
											estudianteVO.setNivelado1(mio);
										psA.setLong(posicionA++,
												estudianteVO.getNivelado1());
										// psA.setLong(posicionA++, tutor);

										psA.setLong(posicionA++,
												estudianteVO.getCodigo());

										psA.executeUpdate();
										psA.close();
										// System.out.println(estudianteVO.getEspecialidad()
										// + "   " +
										// antiguoVO.getEspecialidad());
										// System.out.println(estudianteVO.getSemestre()
										// + "   " + antiguoVO.getSemestre());
										// System.out.println(Long.parseLong(filtro.getGrupo())
										// + "   " + antiguoVO.getGrupo());
										// System.out.println(estudianteVO.getNivelado()
										// + "   " + antiguoVO.getNivelado());
										actualizados++;
										// System.out.println("actualizo");
										// System.out.println("actualizados
										// "+actualizados);
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								bandera = true;
								break;
							}
						}

					}
					if (bandera == false) {
						// System.out.println("entro para hacer la insercion");
						pst = cn.prepareStatement(rb.getString("inserccion"));

						// System.out.println("El codigo a insertar es " +
						// estudianteVO.getCodigo());
						/*
						 * System.out.println(filtro.getInstitucion());
						 * System.out.println(ni);
						 * System.out.println(estudianteVO.getDocumento());
						 * System.out.println(estudianteVO.getEspecialidad());
						 * System.out.println(estudianteVO.getSemestre());
						 * System.out.println(estudianteVO.getGrupo());
						 * System.out.println(otro);
						 * System.out.println(estudianteVO.getNivelado());
						 * System.out.println(otro);
						 */

						pst.setLong(posicion++, estudianteVO.getCodigo());
						pst.setLong(posicion++,
								Long.parseLong(filtro.getInstitucion()));
						pst.setLong(posicion++,
								estudianteVO.getAbreviaturaCodigo());
						pst.setString(posicion++, estudianteVO.getDocumento());
						pst.setLong(posicion++, estudianteVO.getEspecialidad());
						pst.setLong(posicion++, estudianteVO.getSemestre());
						pst.setLong(posicion++,
								Long.parseLong(filtro.getGrupo()));

						if (estudianteVO.getNivelado().equals("X")
								|| estudianteVO.getNivelado().equals("x"))
							estudianteVO.setNivelado1(otro);
						else
							estudianteVO.setNivelado1(mio);
						pst.setLong(posicion++, estudianteVO.getNivelado1());
						// pst.setLong(posicion++, tutor);
						pst.executeUpdate();
						pst.close();
						insertados++;
						// System.out.println("Inserto correctamenteen la tabla art estudiante");
						posicion = 1;
					}
				}
			}

			// **********************DELETE***********************

			/*
			 * for(int i=0;i<vacios.size();i++) { EliminadoVO eliminadoVO=null;
			 * vaciosVO=(VaciosVO)vacios.get(i); System.out.println("pa borrar:
			 * "+vaciosVO.getCodigo()); System.out.println("entro para hacer la
			 * eliminacion"); cn=cursor.getConnection();
			 * 
			 * try { eliminadoVO=new EliminadoVO();
			 * pst=cn.prepareStatement(rb.getString("vacios"));
			 * System.out.println("El codigo a consultar es
			 * "+vaciosVO.getCodigo());
			 * pst.setLong(posicion++,vaciosVO.getCodigo());
			 * rs=pst.executeQuery(); while(rs.next()) {
			 * eliminadoVO.setCodigo(rs.getInt(posicion++)); } pst.close();
			 * 
			 * eliminados++; System.out.println("Elimino a este man");
			 * posicion=1; } catch(SQLException e) {e.printStackTrace();}
			 * 
			 * try {
			 * 
			 * pst=cn.prepareStatement(rb.getString("eliminacion"));
			 * System.out.println("El codigo a eliminar es
			 * "+vaciosVO.getCodigo());
			 * pst.setLong(posicion++,vaciosVO.getCodigo());
			 * pst.executeUpdate(); pst.close(); eliminados++;
			 * System.out.println("Elimino a este man"); posicion=1; }
			 * catch(SQLException e) {e.printStackTrace();} finally { try{
			 * OperacionesGenerales.closeStatement(pst);
			 * OperacionesGenerales.closeConnection(cn); }
			 * catch(InternalErrorException inte){} } }
			 */

			if (l != null && antiguos.size() > 0) {
				for (int i = 0; i < antiguos.size(); i++) {

					bandera2 = false;
					antiguoVO = (CodigoEstudianteVO) antiguos.get(i);
					// System.out.println("**********************************");

					if (l != null) {
						for (int j = 0; j < l.size(); j++) {
							estudianteVO = (EstudianteVO) l.get(j);
							// System.out.println("Nuevo: " +
							// estudianteVO.getCodigo() + "  Antiguo: " +
							// antiguoVO.getCodigo());
							if (estudianteVO.getCodigo() == antiguoVO
									.getCodigo()) {
								bandera2 = true;
								// System.out.println("Eliminar= " +
								// antiguoVO.getCodigo());
								bandera2 = true;
								break;
							}
						}
						if (!bandera2) {
							// System.out.println("entro para hacer la eliminacion");
							pst = cn.prepareStatement(rb
									.getString("eliminacion"));
							pst.setLong(posicion++, antiguoVO.getCodigo());
							pst.executeUpdate();
							pst.close();
							eliminados++;
							posicion = 1;
						}
					}
				}
			}
			cn.commit();
			resultadoVO.setEliminados(eliminados);
			resultadoVO.setIngresados(insertados);
			resultadoVO.setActualizados(actualizados);
		} catch (InternalErrorException in) {
			in.printStackTrace();
			setMensaje("NO se puede estabecer conexinn con la base de datos: ");
			return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: ");
			switch (sqle.getErrorCode()) {
			default:
				setMensaje(sqle.getMessage().replace('\'', '`')
						.replace('"', 'n'));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException s) {
			}
			setMensaje("Error intentando importar asignaturas a estudiantes. Posible problema: "
					+ e);
			return null;
		} finally {
			try {
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeStatement(psA);
				OperacionesGenerales.closeConnection(cn);
				cursor.cerrar();
			} catch (InternalErrorException inte) {
			}
		}
		return resultadoVO;
	}

	public List getEspecialidades(String inst) {
		// System.out.println("llego a get especialidades");
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List lEspecialidades = new ArrayList();
		EspecialidadesVO especialidadesVO = null;
		int i = 0;

		try {
			cn = cursor.getConnection();
			st = cn.prepareStatement(rb.getString("especialidades"));
			i = 1;
			st.setInt(i++, Integer.parseInt(inst));

			rs = st.executeQuery();
			while (rs.next()) {
				i = 1;
				// System.out.println("hizo la consulta de las especialidades");
				especialidadesVO = new EspecialidadesVO();
				especialidadesVO.setCodigo(rs.getInt(i++));
				especialidadesVO.setEspecialidad(rs.getString(i++));
				lEspecialidades.add(especialidadesVO);
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
		return lEspecialidades;
	}

	public List getAllgrupo(String inst, long especialidad) {
		// System.out.println("entro a vaeriguar los gruposGrupo ");
		List grupo = new ArrayList();
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getGrupos"));
			ps.setString(posicion++, inst);
			ps.setLong(posicion++, getVigenciaNumerico());
			ps.setLong(posicion++, getPeriodoNumerico());
			ps.setLong(posicion++, especialidad);
			int i = 1;
			rs = ps.executeQuery();
			Long a = null;
			while (rs.next()) {
				i = 1;
				Long n = new Long(rs.getLong(i++));
				grupo.add(n);
				// es eso
				// grupo.add(rs.getString(i++));
			}
			rs.close();
			ps.close();

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
		return grupo;
	}

	public long traerCodigoGrupo(long grupito) {

		long a = 0;
		int posicion = 1;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String grupoNuevo = "";
		String Semestre = "";
		String medio = "";
		String gru = "";
		int rango = 0;
		rango = grupoNuevo.length();
		grupoNuevo = String.valueOf(grupito);
		// System.out.println("El grupo que llego al DAo es " + grupoNuevo);
		/*
		 * System.out.println("el semestre a buscar es "+grupoNuevo.charAt(0));
		 * System.out.println("el semestre a buscar es "+grupoNuevo.charAt(1));
		 * System.out.println("el semestre a buscar es "+grupoNuevo.charAt(2));
		 */
		Semestre = String.valueOf(grupoNuevo.charAt(0));
		medio = String.valueOf(grupoNuevo.charAt(1));
		// System.out.println("el medio " + medio);
		if (medio.equals("0"))
			gru = String.valueOf(grupoNuevo.charAt(2));
		else
			gru = String.valueOf(grupoNuevo.charAt(1) + grupoNuevo.charAt(2));
		// System.out.println("el semestre a buscar es " + Semestre);
		// System.out.println("el semestre a buscar es " + gru);

		try {
			cn = cursor.getConnection();
			ps = cn.prepareStatement(rb.getString("getGru"));
			ps.setString(posicion++, Semestre);
			ps.setString(posicion++, gru);

			int i = 1;
			rs = ps.executeQuery();

			while (rs.next()) {
				i = 1;
				a = (rs.getLong(i++));

			}
			rs.close();
			ps.close();

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
		return a;
	}

}
