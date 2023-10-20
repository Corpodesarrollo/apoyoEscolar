/**
 * 
 */
package siges.gestionAdministrativa.comparativos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.comparativos.dao.ComparativosDAO;
import siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO;
import siges.gestionAdministrativa.comparativos.vo.NivelEvalVO;
import siges.gestionAdministrativa.comparativos.vo.ParamsVO;
import siges.gestionAdministrativa.comparativos.vo.TipoEvalVO;
import siges.login.beans.Login;

/**
 * 28/04/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service {

	public String FICHA_FILTRO_REPORTES;
	public String REPORTES;
	private ResourceBundle rb;
	private String contextoTotal;
	private ComparativosDAO comparativosDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;

	/**
	 * 
	 /*
	 * 
	 * @function:
	 * @param config
	 * @throws ServletException
	 *             (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		comparativosDAO = new ComparativosDAO(new Cursor());
		FICHA_FILTRO_REPORTES = config
				.getInitParameter("FICHA_FILTRO_REPORTES");
		REPORTES = config.getInitParameter("REPORTES");
	}

	/*
	 * Procesa la peticinn
	 * 
	 * @param request peticinn
	 * 
	 * @param response respuesta
	 * 
	 * @return Ruta de redireccinn
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException (non-Javadoc)
	 * 
	 * @see
	 * siges.common.service.Service#process(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String dispatcher[] = new String[2];
		contextoTotal = context.getRealPath("/");
		String FICHA = null;

		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		FiltroReportesVO filtroVO = (FiltroReportesVO) session
				.getAttribute("filtroReportesVO");
		int CMD = getCmd(request, session, ParamsVO.CMD_NUEVO);
		int TIPO = getTipo(request, session, ParamsVO.FICHA_REPORTES);

		// System.out.println("REP COMPARATIVO TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO) {
		case ParamsVO.FICHA_REPORTES:

			FICHA = FICHA_FILTRO_REPORTES;

			switch (CMD) {

			case ParamsVO.CMD_NUEVO:
				filtroVO = null;
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request, session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				// System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD COMPARATIVOS");
				guardarSolicitud(request, session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;

			}

			break;
		}
		dispatcher[0] = String.valueOf(Params.INCLUDE);
		// System.out.println("APOYO:REP COMPARATIVOS FICHA " + FICHA);
		dispatcher[1] = FICHA;
		return dispatcher;
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroReportes(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroReportesVO filtroVO)
			throws ServletException {

		// System.out.println("COMPARATIVOS:cargarFiltroReportes ");

		try {

			int nivelUsuario = Integer.parseInt(usuVO.getNivel());

			// LISTADO DE REPORTES
			List reportes = new ArrayList();
			ItemVO itemReporte;
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_1);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_1_);
			reportes.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_2);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_2_);
			reportes.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_3);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_3_);
			reportes.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_4);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_4_);
			reportes.add(itemReporte);
			request.setAttribute("listaReportes", reportes);

			// LISTADO DE ORDEN REPORTE
			List orden_reportes = new ArrayList();
			ItemVO itemOrden;
			itemOrden = new ItemVO();
			itemOrden.setCodigo(ParamsVO.TIPO_REPORTE_1_ORDEN1);
			itemOrden.setNombre(ParamsVO.TIPO_REPORTE_1_ORDEN1_);
			orden_reportes.add(itemOrden);
			itemOrden = new ItemVO();
			itemOrden.setCodigo(ParamsVO.TIPO_REPORTE_1_ORDEN2);
			itemOrden.setNombre(ParamsVO.TIPO_REPORTE_1_ORDEN2_);
			orden_reportes.add(itemOrden);
			request.setAttribute("listaOrden", orden_reportes);

			if (filtroVO == null) {
				filtroVO = new FiltroReportesVO();
				if (nivelUsuario >= 2) {
					if (usuVO.getMunId() != null
							&& usuVO.getMunId().length() > 0) {
						filtroVO.setLoc(Long.parseLong(usuVO.getMunId()));
						// filtroVO.setProv(comparativosDAO.getProvMun(Long.parseLong(usuVO.getMunId())));
						filtroVO.setHab_loc(0);
						if (nivelUsuario >= 4) {
							if (usuVO.getInstId() != null
									&& usuVO.getInstId().length() > 0) {
								filtroVO.setInst(Long.parseLong(usuVO
										.getInstId()));
								filtroVO.setNivelEval((int) usuVO
										.getLogNivelEval());
								filtroVO.setNumPer(usuVO.getLogNumPer());
								filtroVO.setNomPerFinal(usuVO.getLogNomPerDef());
								filtroVO.setHab_inst(0);
							}
							if (nivelUsuario >= 6) {
								if (usuVO.getSedeId() != null
										&& usuVO.getSedeId().length() > 0)
									filtroVO.setSede(Long.parseLong(usuVO
											.getSedeId()));
								if (usuVO.getJornadaId() != null
										&& usuVO.getJornadaId().length() > 0)
									filtroVO.setJornd(Long.parseLong(usuVO
											.getJornadaId()));
							}
							filtroVO.setFechaProm(comparativosDAO
									.getFechaPromedios(ParamsVO.NIVEL_INST,
											filtroVO.getInst()));
						} else {
							filtroVO.setFechaProm(comparativosDAO
									.getFechaPromedios(ParamsVO.NIVEL_MUN,
											filtroVO.getLoc()));
						}
					}
				} else {
					filtroVO.setFechaProm(comparativosDAO.getFechaPromedios(
							ParamsVO.NIVEL_CENTRAL, 0));
				}
			}

			NivelEvalVO nivelVO = new NivelEvalVO();
			nivelVO.setCod_nivel_eval(1);
			TipoEvalVO tipoEval = null;

			// request.setAttribute("listaProvincia",
			// comparativosDAO.getProvincias());

			request.setAttribute("listaLocalidad",
					comparativosDAO.getLocalidades());
			if (filtroVO.getLoc() >= 0) {
				request.setAttribute("listaColegio",
						comparativosDAO.getColegios(filtroVO.getLoc()));
				if (filtroVO.getInst() >= 0) {

					nivelVO = comparativosDAO.getNivelEval(filtroVO.getInst());
					tipoEval = comparativosDAO.getTipoEval(filtroVO);
					filtroVO.setNumPer(nivelVO.getNumPer());
					filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
					filtroVO.setTipoEscala((int) tipoEval.getCod_tipo_eval());
					filtroVO.setValorIni(tipoEval.getEval_min());
					filtroVO.setValorFin(tipoEval.getEval_max());

					if (tipoEval.getCod_tipo_eval() == 2) {
						tipoEval.setEscala(comparativosDAO
								.getEscalaConceptual(filtroVO));
					} else if (tipoEval.getCod_tipo_eval() == 3) {
						tipoEval.setEscala(comparativosDAO.getEscalaMEN());
					}

					filtroVO.setFechaProm(comparativosDAO.getFechaPromedios(
							ParamsVO.NIVEL_INST, filtroVO.getInst()));
					request.setAttribute("listaSede",
							comparativosDAO.getSede(filtroVO.getInst()));
					request.setAttribute("listaMetodo",
							comparativosDAO.getMetodologia(filtroVO.getInst()));
					request.setAttribute(
							"listaPeriodo",
							getListaPeriodo(usuVO.getLogNumPer(),
									usuVO.getLogNomPerDef()));
					if (filtroVO.getSede() > 0) {
						nivelVO = comparativosDAO.getNivelEval(filtroVO
								.getInst());
						tipoEval = comparativosDAO.getTipoEval(filtroVO);
						filtroVO.setNumPer(nivelVO.getNumPer());
						filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
						filtroVO.setTipoEscala((int) tipoEval
								.getCod_tipo_eval());
						filtroVO.setValorIni(tipoEval.getEval_min());
						filtroVO.setValorFin(tipoEval.getEval_max());

						if (tipoEval.getCod_tipo_eval() == 2) {
							tipoEval.setEscala(comparativosDAO
									.getEscalaConceptual(filtroVO));
						} else if (tipoEval.getCod_tipo_eval() == 3) {
							tipoEval.setEscala(comparativosDAO.getEscalaMEN());
						}

						request.setAttribute("listaJord", comparativosDAO
								.getJornada(filtroVO.getInst(),
										filtroVO.getSede()));
						if (filtroVO.getJornd() > 0) {
							nivelVO = comparativosDAO.getNivelEval(filtroVO
									.getInst());
							tipoEval = comparativosDAO.getTipoEval(filtroVO);
							filtroVO.setNumPer(nivelVO.getNumPer());
							filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
							filtroVO.setTipoEscala((int) tipoEval
									.getCod_tipo_eval());
							filtroVO.setValorIni(tipoEval.getEval_min());
							filtroVO.setValorFin(tipoEval.getEval_max());

							if (tipoEval.getCod_tipo_eval() == 2) {
								tipoEval.setEscala(comparativosDAO
										.getEscalaConceptual(filtroVO));
							} else if (tipoEval.getCod_tipo_eval() == 3) {
								tipoEval.setEscala(comparativosDAO
										.getEscalaMEN());
							}

							request.setAttribute("listaGrado",
									comparativosDAO.getGrado(filtroVO));
						}
						if (filtroVO.getGrado() > 0) {
							nivelVO = comparativosDAO.getNivelEval(filtroVO
									.getInst());
							tipoEval = comparativosDAO.getTipoEval(filtroVO);
							filtroVO.setNumPer(nivelVO.getNumPer());
							filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
							filtroVO.setTipoEscala((int) tipoEval
									.getCod_tipo_eval());
							filtroVO.setValorIni(tipoEval.getEval_min());
							filtroVO.setValorFin(tipoEval.getEval_max());

							if (tipoEval.getCod_tipo_eval() == 2) {
								tipoEval.setEscala(comparativosDAO
										.getEscalaConceptual(filtroVO));
							} else if (tipoEval.getCod_tipo_eval() == 3) {
								tipoEval.setEscala(comparativosDAO
										.getEscalaMEN());
							}
							request.setAttribute("listaGrupo",
									comparativosDAO.getGrupo(filtroVO));
						}
					}

					nivelVO = comparativosDAO.getNivelEval(filtroVO.getInst());
					tipoEval = comparativosDAO.getTipoEval(filtroVO);
					filtroVO.setNumPer(nivelVO.getNumPer());
					filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
					filtroVO.setTipoEscala((int) tipoEval.getCod_tipo_eval());
					filtroVO.setValorIni(tipoEval.getEval_min());
					filtroVO.setValorFin(tipoEval.getEval_max());

					if (tipoEval.getCod_tipo_eval() == 2) {
						tipoEval.setEscala(comparativosDAO
								.getEscalaConceptual(filtroVO));
					} else if (tipoEval.getCod_tipo_eval() == 3) {
						tipoEval.setEscala(comparativosDAO.getEscalaMEN());
					}

					request.setAttribute("tipoEval", tipoEval);

				} else {
					filtroVO.setFechaProm(comparativosDAO.getFechaPromedios(
							ParamsVO.NIVEL_MUN, filtroVO.getLoc()));
					request.setAttribute("listaMetodo",
							comparativosDAO.getMetodologiaGlobal());
					request.setAttribute("listaJord",
							comparativosDAO.getJornadaGlobal());
					request.setAttribute("listaGrado",
							comparativosDAO.getGradoGlobal());
					request.setAttribute(
							"listaPeriodo",
							getListaPeriodo(comparativosDAO.getPerMax(),
									"FINAL"));

					tipoEval = new TipoEvalVO();
					tipoEval.setCod_tipo_eval(3);
					tipoEval.setEval_min(0);
					tipoEval.setEval_max(100);
					tipoEval.setTipo_eval_prees(-9);
					tipoEval.setModo_eval(3);
				}
			} else {
				filtroVO.setFechaProm(comparativosDAO.getFechaPromedios(
						ParamsVO.NIVEL_CENTRAL, 0));
				request.setAttribute("listaMetodo",
						comparativosDAO.getMetodologiaGlobal());
				request.setAttribute("listaJord",
						comparativosDAO.getJornadaGlobal());
				request.setAttribute("listaGrado",
						comparativosDAO.getGradoGlobal());
				request.setAttribute("listaPeriodo",
						getListaPeriodo(comparativosDAO.getPerMax(), "FINAL"));

				tipoEval = new TipoEvalVO();
				tipoEval.setCod_tipo_eval(3);
				tipoEval.setEval_min(0);
				tipoEval.setEval_max(100);
				tipoEval.setTipo_eval_prees(-9);
				tipoEval.setModo_eval(3);
			}

			// LISTADO VIGENCIAS
			long vigenciaActual = comparativosDAO.getVigenciaNumerico();
			List vigencia = new ArrayList();
			for (int vig = 2007; vig <= vigenciaActual; vig++) {
				ItemVO itemVig = new ItemVO();
				itemVig.setCodigo(vig);
				itemVig.setNombre(String.valueOf(vig));
				vigencia.add(itemVig);
			}
			filtroVO.setVig(vigenciaActual);
			request.setAttribute("listaVigencia", vigencia);

			// LISTADO DE ZONAS
			List zonas = new ArrayList();
			ItemVO itemZona;
			itemZona = new ItemVO();
			itemZona.setCodigo(ParamsVO.ZONA_TODAS);
			itemZona.setNombre(ParamsVO.ZONA_TODAS_);
			// zonas.add(itemZona);
			itemZona = new ItemVO();
			itemZona.setCodigo(ParamsVO.ZONA_URBANA);
			itemZona.setNombre(ParamsVO.ZONA_URBANA_);
			zonas.add(itemZona);
			itemZona = new ItemVO();
			itemZona.setCodigo(ParamsVO.ZONA_RURAL);
			itemZona.setNombre(ParamsVO.ZONA_RURAL_);
			zonas.add(itemZona);
			request.setAttribute("listaZonas", zonas);
			filtroVO.setNumAsig(comparativosDAO.getNumAsigParam());
			// System.out.println("NUEVO REP COMP: FECHA PROMEDIO: "+filtroVO.getFechaProm());

			if (filtroVO.getFechaProm() != null
					&& !filtroVO.getFechaProm().equals(""))
				filtroVO.setFechaPromValida(1);
			else
				filtroVO.setFechaPromValida(0);
			filtroVO.setValorIni(tipoEval.getEval_min());
			filtroVO.setValorFin(tipoEval.getEval_max());
			filtroVO.setTipoEscala(tipoEval.getModo_eval());

			request.setAttribute("listaEscala", comparativosDAO.getEscalaMEN());
			session.setAttribute("filtroReportesVO", filtroVO);

			if (tipoEval.getCod_tipo_eval() == 2) {
				tipoEval.setEscala(comparativosDAO
						.getEscalaConceptual(filtroVO));
			} else if (tipoEval.getCod_tipo_eval() == 3) {
				tipoEval.setEscala(comparativosDAO.getEscalaMEN());
			}

			request.setAttribute("tipoEval", tipoEval);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Errror interno: " + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @throws Exception
	 */
	private void guardarSolicitud(HttpServletRequest request,
			HttpSession session, Login usuVO, FiltroReportesVO filtroVO)
			throws ServletException {
		f2 = new java.sql.Timestamp(new java.util.Date().getTime());

		int tipoRep = filtroVO.getTipoReporte();
		String zip = "";
		String pdf = "";
		String xls = "";
		String nombreReporte = "";
		String loc = (filtroVO.getLoc() >= 0 ? "_Mun_" + filtroVO.getLoc() : "");
		String inst = (filtroVO.getInst() > 0 ? "_Inst_" + filtroVO.getInst()
				: "");
		String sede = (filtroVO.getSede() > 0 ? "_Sede_" + filtroVO.getSede()
				: "");
		String jornada = (filtroVO.getJornd() > 0 ? "_Jor_"
				+ filtroVO.getJornd() : "");
		String grado = (filtroVO.getGrado() >= 0 ? "_Gra_"
				+ filtroVO.getGrado() : "");
		String nomRep = loc + inst + sede + jornada + grado;
		if (tipoRep == ParamsVO.TIPO_REPORTE_1) {
			nombreReporte = ParamsVO.TIPO_REPORTE_1_A;
			nombreReporte = nombreReporte + nomRep;
		} else if (tipoRep == ParamsVO.TIPO_REPORTE_2) {
			nombreReporte = ParamsVO.TIPO_REPORTE_2_A;
			nombreReporte = nombreReporte + nomRep;
		} else if (tipoRep == ParamsVO.TIPO_REPORTE_3) {
			nombreReporte = ParamsVO.TIPO_REPORTE_3_A;
			nombreReporte = nombreReporte + nomRep;
		} else if (tipoRep == ParamsVO.TIPO_REPORTE_4) {
			nombreReporte = ParamsVO.TIPO_REPORTE_4_A;
			nombreReporte = nombreReporte + nomRep;
		} else {
			nombreReporte = ParamsVO.TIPO_REPORTE_1_A;
			nombreReporte = nombreReporte + nomRep;
		}
		filtroVO.setFecha(f2.toString().replace(' ', '_').replace(':', '-')
				.replace('.', '-'));
		zip = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".zip";
		pdf = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".pdf";
		xls = nombreReporte
				+ "_fecha_"
				+ f2.toString().replace(' ', '_').replace(':', '-')
						.replace('.', '-') + ".xls";
		filtroVO.setNombre_zip(zip);
		filtroVO.setNombre_pdf(pdf);
		filtroVO.setNombre_xls(xls);
		filtroVO.setUsuario(usuVO.getUsuarioId());
		// System.out.println("APOYO COMPARATIVOS: GUARDAR SOLICITUD ");
		try {
			if (comparativosDAO.insertarSolicitud(filtroVO)) {
				System.out.println("se inserto solicitud");
				comparativosDAO.insertarReporte(filtroVO);
				// System.out.println("se inserto Reporte");
				request.setAttribute("mensaje",
						"Solicitud ingresada correctamente");
			}
		} catch (Exception e) {
			request.setAttribute("mensaje",
					"No fue posible ingresar la Solicitud");
			e.printStackTrace();
		}
	}

	public List getListaPeriodo(long numPer, String nomPerDef) {
		List l = new ArrayList();
		ItemVO item = null;
		// System.out.println("APOYO ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for (int i = 1; i <= numPer; i++) {
			item = new ItemVO(i, "" + i);
			l.add(item);
		}
		item = new ItemVO(7, nomPerDef);
		l.add(item);
		return l;
	}
}
