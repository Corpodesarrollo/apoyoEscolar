/**
 * 
 */
package siges.gestionAdministrativa.RepPuestoEstudiante.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;

import com.lowagie.text.pdf.PRAcroForm;



import siges.common.service.Service;
import siges.common.vo.ItemVO;
import siges.common.vo.Params;
import siges.dao.Cursor;
import siges.gestionAdministrativa.RepPuestoEstudiante.dao.ReporteDAO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.FiltroReportesVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.NivelEvalVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.ParamsVO;
import siges.gestionAdministrativa.RepPuestoEstudiante.vo.TipoEvalVO;

import siges.login.beans.Login; 
/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class Nuevo extends Service{
	
	public String FICHA_FILTRO_REPORTES;
	public String FICHA_FILTRO_REPORTES2;
	public String FICHA_FILTRO_REPORTES3;
	public String FICHA_FILTRO_REPORTES4;
	public String FICHA_FILTRO_REPORTES5;
	public String FICHA_FILTRO_REPORTES6;
	public String FICHA_FILTRO_REPORTES7;
	public String REPORTES;
	private ResourceBundle rb;
	private String contextoTotal;
	private ReporteDAO comparativosDAO;
	private Login usuVO;
	private java.sql.Timestamp f2;
	
	/**
	 *  
	 
	 /*
	  * @function: 
	  * @param config
	  * @throws ServletException
	  * (non-Javadoc)
	  * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	  */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		comparativosDAO = new ReporteDAO(new Cursor());
		FICHA_FILTRO_REPORTES= config.getInitParameter("FICHA_FILTRO_REPORTES");
		FICHA_FILTRO_REPORTES2= config.getInitParameter("FICHA_FILTRO_REPORTES2");
		FICHA_FILTRO_REPORTES3= config.getInitParameter("FICHA_FILTRO_REPORTES3");
		FICHA_FILTRO_REPORTES4= config.getInitParameter("FICHA_FILTRO_REPORTES4");
		FICHA_FILTRO_REPORTES5= config.getInitParameter("FICHA_FILTRO_REPORTES5");
		FICHA_FILTRO_REPORTES6= config.getInitParameter("FICHA_FILTRO_REPORTES6");
		FICHA_FILTRO_REPORTES7= config.getInitParameter("FICHA_FILTRO_REPORTES7");
		REPORTES= config.getInitParameter("REPORTES");
	}
	
	/*
	 * Procesa la petición
	 * @param request petición
	 * @param response respuesta
	 * @return Ruta de redirección
	 * @throws ServletException
	 * @throws IOException
	 * (non-Javadoc)
	 * @see siges.common.service.Service#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String[] process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String dispatcher[]=new String[2];
		contextoTotal=context.getRealPath("/");
		String FICHA=null;
		
		HttpSession session = request.getSession();
		usuVO = (Login) session.getAttribute("login");
		FiltroReportesVO filtroVO = (FiltroReportesVO) session.getAttribute("filtroReportesVO");
		int CMD=getCmd(request,session,ParamsVO.CMD_NUEVO);
		int TIPO=getTipo(request,session,ParamsVO.FICHA_REPORTES); 
		
		System.out.println("REP COMPARATIVO TIPO " + TIPO +"   CMD "+ CMD);
		switch (TIPO){
		case ParamsVO.FICHA_REPORTES:
			
			FICHA = FICHA_FILTRO_REPORTES;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
			
		case ParamsVO.FICHA_REPORTES2:
			
			FICHA = FICHA_FILTRO_REPORTES2;				
			System.out.println("FICHA_FILTRO_REPORTES2 00000000000000000000000000000000000000000000000000");
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("****************************************************************SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
			
		case ParamsVO.FICHA_REPORTES3:
			
			FICHA = FICHA_FILTRO_REPORTES3;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
			
		case ParamsVO.FICHA_REPORTES4:
			
			FICHA = FICHA_FILTRO_REPORTES4;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
		case ParamsVO.FICHA_REPORTES5:
			
			FICHA = FICHA_FILTRO_REPORTES5;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
			
		case ParamsVO.FICHA_REPORTES6:
			
			FICHA = FICHA_FILTRO_REPORTES6;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
		case ParamsVO.FICHA_REPORTES7:
			
			FICHA = FICHA_FILTRO_REPORTES7;				
			
			switch (CMD){
			
			case ParamsVO.CMD_NUEVO:
				filtroVO=null;				
				session.removeAttribute("filtroReportesVO");
				cargarFiltroReportes(request,session, usuVO, filtroVO);
				break;
			case ParamsVO.CMD_GUARDAR:
				System.out.println("SIGES: ENTRO A GUARDAR SOLICITUD REPORTES");
				guardarSolicitud(request,session, usuVO, filtroVO);
				FICHA = REPORTES;
				break;	
				
			}
			
			break;
			
		}
		dispatcher[0]=String.valueOf(Params.INCLUDE);
		System.out.println("APOYO:REP REPORTES FICHA " + FICHA);
		dispatcher[1]=FICHA;
		return dispatcher;
	}
	
	
	/**
	 * @param request
	 * @throws Exception
	 */
	private void cargarFiltroReportes(HttpServletRequest request, HttpSession session,Login usuVO, FiltroReportesVO filtroVO ) throws ServletException {
	
		System.out.println("REPORTES:cargarFiltroReportes ");
		
		
		try {
			
			
			
			int nivelUsuario=Integer.parseInt(usuVO.getNivel());
			
			//LISTADO DE REPORTES
			List reportes =  new ArrayList();
			ItemVO itemReporte;
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_7);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_7_);
			reportes.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_8);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_8_);
			reportes.add(itemReporte);
			itemReporte = new ItemVO();
			itemReporte.setCodigo(ParamsVO.TIPO_REPORTE_9);
			itemReporte.setNombre(ParamsVO.TIPO_REPORTE_9_);
			reportes.add(itemReporte);		
			request.setAttribute("listaReportes",reportes);
			
			
			if(filtroVO==null){
				filtroVO =  new FiltroReportesVO();
				filtroVO.setVig(comparativosDAO.getVigenciaNumerico());
				if(nivelUsuario>=2){
					if(usuVO.getMunId()!=null && usuVO.getMunId().length()>0){
						filtroVO.setLoc(Long.parseLong(usuVO.getMunId()));
						//filtroVO.setProv(comparativosDAO.getProvMun(Long.parseLong(usuVO.getMunId())));
					filtroVO.setHab_loc(0);
						if(nivelUsuario>=4){						
							if(usuVO.getInstId()!=null && usuVO.getInstId().length()>0){
								filtroVO.setInst(Long.parseLong(usuVO.getInstId()));
								filtroVO.setNivelEval((int)usuVO.getLogNivelEval());
								filtroVO.setNumPer(usuVO.getLogNumPer());
								filtroVO.setNomPerFinal(usuVO.getLogNomPerDef());
								filtroVO.setHab_inst(0);
							}
							if(nivelUsuario>=6){
								if(usuVO.getSedeId()!=null && usuVO.getSedeId().length()>0)
								filtroVO.setSede(Long.parseLong(usuVO.getSedeId()));
								if(usuVO.getJornadaId()!=null && usuVO.getJornadaId().length()>0)
								filtroVO.setJornd(Long.parseLong(usuVO.getJornadaId()));
							}
							
						}else{
							
						}
					}
				}else{
					//filtroVO.setFechaProm(comparativosDAO.getFechaPromedios(ParamsVO.NIVEL_CENTRAL, 0));
				}
			}
			
			NivelEvalVO nivelVO=new NivelEvalVO();
			nivelVO.setCod_nivel_eval(1);
			TipoEvalVO tipoEval=null;
			
			request.setAttribute("listaLocalidad", comparativosDAO.getLocalidades(filtroVO.getProv()));
			if(filtroVO.getLoc()>=0){
				request.setAttribute("listaColegio", comparativosDAO.getColegios(filtroVO.getLoc()));
				if(filtroVO.getInst()>=0){
					
					request.setAttribute("listaSede", comparativosDAO.getSede(filtroVO.getInst()));				
					request.setAttribute("listaMetodo", comparativosDAO.getMetodologia(filtroVO.getInst()));
					request.setAttribute("listaPeriodo",getListaPeriodo(usuVO.getLogNumPer(), usuVO.getLogNomPerDef()));
					request.setAttribute("listaAsignatura",comparativosDAO.getAsignaturas(filtroVO.getInst(), filtroVO.getMetodo(), filtroVO.getGrado(), null, filtroVO.getVig()));
					if(filtroVO.getSede()>0){
						request.setAttribute("listaJord", comparativosDAO.getJornada(filtroVO.getInst(), filtroVO.getSede()));
						if(filtroVO.getJornd()>0){
							request.setAttribute("listaGrado", comparativosDAO.getGrado(filtroVO));
							request.setAttribute("listaDocente", comparativosDAO.getDocentes(filtroVO));
						}
						if(filtroVO.getGrado()>0){
							request.setAttribute("listaGrupo", comparativosDAO.getGrupo(filtroVO));
						}
					}
					
					nivelVO=comparativosDAO.getNivelEval(filtroVO.getInst());
					tipoEval=comparativosDAO.getTipoEval(filtroVO);
					filtroVO.setNumPer(nivelVO.getNumPer());
					filtroVO.setNomPerFinal(nivelVO.getNomPerFinal());
					filtroVO.setTipoEscala((int)tipoEval.getCod_tipo_eval());
					filtroVO.setValorIni(tipoEval.getEval_min());
					filtroVO.setValorFin(tipoEval.getEval_max());
					
					if(tipoEval.getCod_tipo_eval()==2){
						tipoEval.setEscala(comparativosDAO.getEscalaConceptual(filtroVO));
						tipoEval.setEval_min(0);
						tipoEval.setEval_max(100);
					}else if(tipoEval.getCod_tipo_eval()==3){
						tipoEval.setEscala(comparativosDAO.getEscalaMEN());
					}
					request.setAttribute("tipoEval", tipoEval);
					
				}else{
					
					request.setAttribute("listaMetodo", comparativosDAO.getMetodologiaGlobal());
					request.setAttribute("listaJord", comparativosDAO.getJornadaGlobal());
					request.setAttribute("listaGrado", comparativosDAO.getGradoGlobal());
					request.setAttribute("listaPeriodo",getListaPeriodo(comparativosDAO.getPerMax(), "FINAL"));
					
					tipoEval=new TipoEvalVO();
					tipoEval.setCod_tipo_eval(3);
					tipoEval.setEval_min(0);
					tipoEval.setEval_max(100);				
					tipoEval.setTipo_eval_prees(-9);
					tipoEval.setModo_eval(3);
				} 
			}else{
				
				//request.setAttribute("listaMetodo", comparativosDAO.getMetodologiaGlobal());
				//request.setAttribute("listaJord", comparativosDAO.getJornadaGlobal());
				//request.setAttribute("listaGrado", comparativosDAO.getGradoGlobal());
				//request.setAttribute("listaPeriodo",getListaPeriodo(comparativosDAO.getPerMax(), "FINAL"));
				
				tipoEval=new TipoEvalVO();
				tipoEval.setCod_tipo_eval(3);
				tipoEval.setEval_min(0);
				tipoEval.setEval_max(100);				
				tipoEval.setTipo_eval_prees(-9);
				tipoEval.setModo_eval(3);
			}
			
		 			 	
			
		 	//LISTADO VIGENCIAS
		 	long vigenciaActual=comparativosDAO.getVigenciaNumerico();
			List vigencia =  new ArrayList();
			for(int vig=2007; vig<=vigenciaActual;vig++){
				ItemVO itemVig= new ItemVO();
				itemVig.setCodigo(vig);
				itemVig.setNombre(String.valueOf(vig));
				vigencia.add(itemVig);				
			}
			filtroVO.setVig(vigenciaActual);
			request.setAttribute("listaVigencia",vigencia);	
						
			//filtroVO.setNumAsig(comparativosDAO.getNumAsigParam());	
			System.out.println("NUEVO REP COMP: FECHA PROMEDIO: "+filtroVO.getFechaProm());
			
			if(filtroVO.getFechaProm()!=null && !filtroVO.getFechaProm().equals(""))
				filtroVO.setFechaPromValida(1);
			else
				filtroVO.setFechaPromValida(0);
			filtroVO.setValorIni(tipoEval.getEval_min());
			filtroVO.setValorFin(tipoEval.getEval_max());
			//filtroVO.setTipoEscala(tipoEval.getModo_eval());
			filtroVO.setTipoEscala((int)tipoEval.getCod_tipo_eval());
			
			request.setAttribute("listaEscala",comparativosDAO.getEscalaMEN());
			session.setAttribute("filtroReportesVO",filtroVO);
			
			if(tipoEval.getCod_tipo_eval()==2){
				tipoEval.setEscala(comparativosDAO.getEscalaConceptual(filtroVO));
			}else if(tipoEval.getCod_tipo_eval()==3){
				tipoEval.setEscala(comparativosDAO.getEscalaMEN());
			}
			
				
			request.setAttribute("tipoEval", tipoEval);	
	 		
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServletException("Errror interno: " + e.getMessage());
		}
	}
	
	/**
	 * @param request
	 * @throws Exception
	 */
	private void guardarSolicitud(HttpServletRequest request, HttpSession session,Login usuVO, FiltroReportesVO filtroVO ) throws ServletException {
		f2=new java.sql.Timestamp(new java.util.Date().getTime());
		
		int tipoRep=filtroVO.getTipoReporte();
		String zip="";
		String pdf="";
		String xls="";
		String nombreReporte="";
		String loc=(filtroVO.getLoc()>=0?"_Loc_"+filtroVO.getLoc():"");
		String inst=(filtroVO.getInst()>0?"_Inst_"+filtroVO.getInst():"");
		String sede=(filtroVO.getSede()>0?"_Sede_"+filtroVO.getSede():"");
		String jornada=(filtroVO.getJornd()>0?"_Jor_"+filtroVO.getJornd():"");		
		String grado=(filtroVO.getGrado()>=0?"_Gra_"+filtroVO.getGrado():"");
		String nomRep=loc+inst+sede+jornada+grado;
		if(tipoRep==ParamsVO.TIPO_REPORTE_1){
			nombreReporte=ParamsVO.TIPO_REPORTE_1_A;
			nombreReporte=nombreReporte+nomRep;		
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_2){
			nombreReporte=ParamsVO.TIPO_REPORTE_2_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_3){
			nombreReporte=ParamsVO.TIPO_REPORTE_3_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_4){
			nombreReporte=ParamsVO.TIPO_REPORTE_4_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_5){
			nombreReporte=ParamsVO.TIPO_REPORTE_5_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_6){
			nombreReporte=ParamsVO.TIPO_REPORTE_6_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_7){
			nombreReporte=ParamsVO.TIPO_REPORTE_7_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_8){
			nombreReporte=ParamsVO.TIPO_REPORTE_8_A;
			nombreReporte=nombreReporte+nomRep;	
		}else if(tipoRep==ParamsVO.TIPO_REPORTE_9){
			nombreReporte=ParamsVO.TIPO_REPORTE_9_A;
			nombreReporte=nombreReporte+nomRep;	
		}else{
			nombreReporte=ParamsVO.TIPO_REPORTE_1_A;
			nombreReporte=nombreReporte+nomRep;	
		} 
		filtroVO.setFecha(f2.toString().replace(' ','_').replace(':','-').replace('.','-'));
		zip=nombreReporte+"_fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-')+".zip";
		pdf=nombreReporte+"_fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-')+".pdf";
		xls=nombreReporte+"_fecha_"+f2.toString().replace(' ','_').replace(':','-').replace('.','-')+".xls";
		filtroVO.setNombre_zip(zip);
		filtroVO.setNombre_pdf(pdf);
		filtroVO.setNombre_xls(xls);
		filtroVO.setUsuario(usuVO.getUsuarioId());
		System.out.println("REPORTES: GUARDAR SOLICITUD ");
		try {
			if(comparativosDAO.insertarSolicitud(filtroVO)){
				System.out.println("se inserto solicitud");
				comparativosDAO.insertarReporte(filtroVO);
				System.out.println("se inserto Reporte");
				request.setAttribute("mensaje","Solicitud ingresada correctamente");
			}
		} catch (Exception e) {
			request.setAttribute("mensaje","No fue posible ingresar la Solicitud");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List getListaPeriodo(long numPer, String nomPerDef){
		List l=new ArrayList();
		ItemVO item=null;
		System.out.println("ENTRO A CARGAR PERIODOS ** FILTRO EVALUACION EDIT"+numPer);
		for(int i=1;i<=numPer;i++){
			item=new ItemVO(i,""+i); l.add(item);
		}		
		item=new ItemVO(7,nomPerDef); l.add(item);
		return l;
	}
}

