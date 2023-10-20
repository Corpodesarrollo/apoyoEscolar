package siges.conflictoReportes;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.conflictoReportes.beans.ConflictoFiltro;
import siges.conflictoReportes.dao.ConflictoReportesDAO;
import siges.conflictoReportes.io.*;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.util.Logger;
import siges.io.Zip;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String mensaje;
    private boolean err;
    private Cursor cursor;
    private Util util;
    private Timestamp f2;
    private ResourceBundle rb;
   	private ConflictoReportesDAO crDAO;
   	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
   	private Integer entero=new Integer(java.sql.Types.INTEGER);
   	public static final int GRUPOCERRADO=1;
    public static final int GRUPOABIERTO=0;
    public static final int PERIODOCERRADO=1;
    public static final int PERIODOABIERTO=0;
	private String mensaje3; 
	private final String MODULOREPORTE="12";
   	
   	/**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   	    HttpSession session = request.getSession();
   	    f2=new Timestamp(new java.util.Date().getTime());
   	    rb=ResourceBundle.getBundle("conflictoReportes");
   	    String sig1="/conflictoReportes/FiltroRep1.jsp";
   	    String sig2="/conflictoReportes/FiltroRep2.jsp";
   	    String sig3="/conflictoReportes/FiltroRep3.jsp";
   	    String sig4="/conflictoReportes/FiltroRep4.jsp";
   	    String sig="/conflictoReportes/filtro.jsp";
   	    String p_inte=this.getServletContext().getInitParameter("integrador");
   	    String p_home=this.getServletContext().getInitParameter("home");
   	    String p_login=this.getServletContext().getInitParameter("login");
   	    String p_error=this.getServletContext().getInitParameter("er");
   	    String consulta;
   	    cursor=new Cursor();
   	    util=new Util(cursor);
   	    int tipo,z;
   	    Collection list,rep;
   	    Object[] o;
   	    try{
   	        Login login=(Login)session.getAttribute("login");
   	        ConflictoFiltro cf=(ConflictoFiltro)session.getAttribute("filtroreportes");
   	        crDAO = new ConflictoReportesDAO(cursor);

   	        if(!asignarBeans(request)){
   	            setMensaje("Error capturando datos de sesinn para el usuario");
   	            request.setAttribute("mensaje",mensaje);
   	            return(p_error);
   	        }

   	        if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
   	            borrarBeans(request);
   	            tipo=0;
   	        }else{
   	            tipo=Integer.parseInt((String)request.getParameter("tipo"));
   	        }
   	        switch(tipo){
   	        case 3:
   	            if(!cf.getLocal1().equals("-1")){
   	                z=0;
   	                list = new ArrayList();
   	                o=new Object[2];
   	                o[z++]=new Integer(java.sql.Types.BIGINT);
   	                o[z++]=""+PERIODOABIERTO;
   	                list.add(o);
   	                z=0;
   	                o=new Object[2];
   	                o[z++]=new Integer(java.sql.Types.BIGINT);
   	                o[z++]=cf.getLocal1();
   	                list.add(o);
   	                z=0;
	                o=new Object[2];
	                o[z++]=new Integer(java.sql.Types.BIGINT);
	                o[z++]=crDAO.getVigencia();
	                list.add(o);
	                if(cf.getPeriodo1().equalsIgnoreCase("10")){
	                	consulta=rb.getString("reporte1.ini1");
	                }else if(cf.getPeriodo1().equalsIgnoreCase("20")){
	                	consulta=rb.getString("reporte1.ini2");
	                }else{
	                	consulta=rb.getString("reporte1.ini"+cf.getPeriodo1());
	                }
   	                consulta+=" "+rb.getString("reporte1.mid")+" ";
   	                consulta+=rb.getString("reporte1.fin");
   	                rep=util.getFiltro(consulta,list);
   	            }else{
   	            	z=0;
   	            	list = new ArrayList();
   	            	o=new Object[2];
   	            	o[z++]=new Integer(java.sql.Types.BIGINT);
   	            	o[z++]=""+PERIODOABIERTO;
   	            	list.add(o);
   	            	z=0;
   	                o=new Object[2];
   	                o[z++]=new Integer(java.sql.Types.BIGINT);
   	                o[z++]=crDAO.getVigencia();
   	                list.add(o);
   	                if(cf.getPeriodo1().equalsIgnoreCase("10")){
	                	consulta=rb.getString("reporte1.ini1");
	                }else if(cf.getPeriodo1().equalsIgnoreCase("20")){
	                	consulta=rb.getString("reporte1.ini2");
	                }else{
	                	consulta=rb.getString("reporte1.ini"+cf.getPeriodo1());
	                }
   	                consulta+=" "+rb.getString("reporte1.fin");
   	                rep=util.getFiltro(consulta,list);
   	            }
   	            
   	            request.setAttribute("reporte1",reporte1(login,cf,rep));
   	            break;
   	        //reporte 2
   	        case 4:
   	            z=0;
   	            list = new ArrayList();
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=login.getInstId();
   	            list.add(o);
   	            z=0;
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=cf.getSede2();
   	            list.add(o);
   	            z=0;
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=cf.getJorn2();
   	            list.add(o);
   	            z=0;
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=cf.getMetodologia2();
   	            list.add(o);
   	            z=0;
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=crDAO.getVigencia();
   	            list.add(o);
   	            z=0;
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=String.valueOf(GRUPOABIERTO);
   	            list.add(o);
      		  		    
   	            rep=util.getFiltro(rb.getString("reporte2."+cf.getPeriodo2()),list);
   	            
   	            z=0;
   	            list = new ArrayList();
   	            o=new Object[2];
   	            o[z++]=new Integer(java.sql.Types.BIGINT);
   	            o[z++]=login.getInstId();
   	            list.add(o);
   	            request.setAttribute("filtroSedeF",crDAO.getFiltro(rb.getString("filtroSedeInstitucion"),list));
   	            request.setAttribute("filtroJornadaF",crDAO.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
   	            request.setAttribute("filtroMetodologia",crDAO.getFiltro(rb.getString("filtroMetodologia"),list));
   	            request.setAttribute("reporte2",reporte2(login,cf,rep));
   	            sig1=sig2;
   	            break;
   	        //reporte 3
   	        case 5:
   	        	//Reporte general apoyo escolar
   	        	cf.setJerjornada3(crDAO.obtenerJerar(cf,6));
   	        	cf.setCodigojerar3(crDAO.obtenerJerar(cf,6));
   	        	request.setAttribute("reporte3",reporte3(request,login,cf));
   	        	
   	        	if(login.getNivel().trim().equalsIgnoreCase("4") || login.getNivel().trim().equalsIgnoreCase("6")){
	   		    	Collection col1;
			    	
			    	String s;
			    	list = new ArrayList();
			    	o = new Object[2];
	   		    	
	   		    	list = new ArrayList();
	   		    	o = new Object[2];
	   		    	o[0] = new Integer(java.sql.Types.INTEGER);
	   		    	o[1] = login.getInstId();
	   		    	list.add(o);
	   		    	if (request.getSession().getAttribute("filtroJornadaF") == null)
	   		    		request.getSession().setAttribute("filtroJornadaF",util.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
	   		    	
	   		    	list = new ArrayList();
					o = new Object[2];
					o[0] = new Integer(java.sql.Types.BIGINT);
					o[1] = login.getInstId();
					list.add(o);
					col1 = util.getFiltro(rb.getString("CargaSedes"), list);
					request.setAttribute("var", "4");
					request.setAttribute("CargaSedes", col1);
	   		    	
				}
   	        	
   	            sig1=sig3;
   	            break;
   	            
   	        case 6: // '\006'
   	            //System.out.println("***entro por case 6***");
   	            if(!cf.getLocal1().equals("-1")){
   	                z = 0;
   	                list = new ArrayList();
   	                o = new Object[2];
   	                o[z++] = new Integer(-5);
   	                o[z++] = "1";
   	                list.add(((Object) (o)));
   	                z = 0;
   	                o = new Object[2];
   	                o[z++] = new Integer(-5);
   	                o[z++] = cf.getLocal1();
   	                list.add(((Object) (o)));
   	                z = 0;
   	                o = new Object[2];
   	                o[z++] = new Integer(-5);
   	                o[z++] = crDAO.getVigencia();
   	                list.add(((Object) (o)));
   	                
   	                if(cf.getPeriodo1().equalsIgnoreCase("10")){
	                	consulta=rb.getString("reporte4.ini1");
	                }else if(cf.getPeriodo1().equalsIgnoreCase("20")){
	                	consulta=rb.getString("reporte4.ini2");
	                }else{
	                	consulta = rb.getString("reporte4.ini" + cf.getPeriodo1());
	                }
   	                consulta = consulta + " " + rb.getString("reporte4.mid") + " ";
   	                consulta = consulta + rb.getString("reporte4.fin");
   	                rep = util.getFiltro(consulta, list);
   	            } else{
   	                z = 0;
   	                list = new ArrayList();
   	                o = new Object[2];
   	                o[z++] = new Integer(-5);
   	                o[z++] = "1";
   	                list.add(((Object) (o)));
   	                z = 0;
   	                o = new Object[2];
   	                o[z++] = new Integer(-5);
   	                o[z++] = crDAO.getVigencia();
   	                list.add(((Object) (o)));
   	                if(cf.getPeriodo1().equalsIgnoreCase("10")){
	                	consulta=rb.getString("reporte4.ini1");
	                }else if(cf.getPeriodo1().equalsIgnoreCase("20")){
	                	consulta=rb.getString("reporte4.ini2");
	                }else{
	                	consulta = rb.getString("reporte4.ini" + cf.getPeriodo1());
	                }
   	                consulta = consulta + " " + rb.getString("reporte4.fin");
   	                rep = util.getFiltro(consulta, list);
   	            }
   	            request.setAttribute("reporte1", reporte4(login, cf, rep));
   	            sig1 = sig4;
   	            break;
   	            
   	        }
   	        return sig1;
   	    }catch(Exception e){
   	        e.printStackTrace();
   	        System.out.println("Error "+this+": "+e.toString());
   	        throw new	ServletException(e);
   	    }finally{
   	        if(cursor!=null)cursor.cerrar();
   	    }
   	}
   	
   	public String getNombreRep3(ConflictoFiltro fr){
   		String local=formatear(fr.getNomlocal3().length()>10?fr.getNomlocal3().substring(0,10):fr.getNomlocal3());
   		String colegio=formatear(fr.getNomcolegio3().length()>10?fr.getNomcolegio3().substring(0,10):fr.getNomcolegio3());
   		String sede=formatear(fr.getSede3().length()>10?fr.getSede3().substring(0,10):fr.getSede3());
   		String jorn=formatear(fr.getNomjorn3().length()>10?fr.getNomjorn3().substring(0,10):fr.getNomjorn3());
   		String periodo=formatear(fr.getNomperiodo3().length()>10?fr.getNomperiodo3().substring(0,10):fr.getNomperiodo3());
   		String fe=f2.toString().replace(':','-').replace('.','-').substring(0,10)+"_"+f2.toString().replace(':','-').replace('.','-').substring(11,19);
   		
   		return ("RepGralConfEscolar_L_"+local+"_C_"+colegio+"_S_"+sede+"_J_"+jorn+"_P_"+periodo+"_("+fe+")");
   	}
   	
   	public boolean ponerArchivo(String modulo,String path,byte[] bit,String archivo){
		try{
			File f=new File(path);
			if(!f.exists()) FileUtils.forceMkdir(f);			
			FileOutputStream fileOut= new FileOutputStream(f+File.separator+archivo);
			CopyUtils.copy(bit,fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(IOException e){
			setMensaje3("Error: "+e.getMessage());
		    e.printStackTrace();
			return false;
		}
		return true;
	}

   	public String reporte3(HttpServletRequest request,Login login,ConflictoFiltro fr){
   		byte[] bytes;
   		Map parameters;
   		File jasperIni;
   		Collection list = null;
   		String pathrep, archivo, pathjasper, filepdf, filezip, fileXls;
   		String pathrepRelativo;
   		parameters=new HashMap();
   		Zip zip= new Zip();
   		bytes=null;
   		pathrepRelativo=Ruta.getRelativo(getServletContext(),rb.getString("reporte3.path")+"."+login.getUsuarioId());//carpeta donde se almacenan los reportes
   		pathrep=Ruta.get(getServletContext(),rb.getString("reporte3.path")+"."+login.getUsuarioId());//carpeta donde se almacenan los reportes
   		pathjasper=Ruta2.get(getServletContext(),rb.getString("reporte3.rutajaspers"));//carpeta donde se encuentran los jaspers
   		archivo=getNombreRep3(fr);//nombre del archivo
   		filepdf=archivo+".pdf";//nombre del archivo pdf
		filezip=archivo+".zip";//nombre del archivo zip
		fileXls=archivo+".xls";
   		jasperIni=new File(pathjasper+rb.getString("reporte3.jasperIni"));//archivo jasper principal
   		
   		//PARAMETROS PARA EL JASPER
    	parameters.put("codigo_jerarquia",fr.getCodigojerar3());
    	parameters.put("codigo_periodo",fr.getPeriodo3());
    	parameters.put("institucion",fr.getColegio3());
    	parameters.put("jornada",fr.getJerjornada3());
    	parameters.put("jornada_consolidado_grupo",fr.getJorn3());
    	parameters.put("nombre_periodo",fr.getNomperiodo3());
    	parameters.put("sede",fr.getSede3());
    	parameters.put("tipo_conflicto",pathjasper+rb.getString("reporte3.jasper_tipo_conf"));//ruta jasper
    	parameters.put("proyectos",pathjasper+rb.getString("reporte3.jasper_proyectos"));//ruta jasper
    	parameters.put("resolucion_conflicto",pathjasper+rb.getString("reporte3.jasper_res_conf"));//ruta jasper
    	parameters.put("consolidado_grupo",pathjasper+rb.getString("reporte3.jasper_con_grupo"));//ruta jasper
    	parameters.put("influencia_conflictos",pathjasper+rb.getString("reporte3.jasper_influ_conf"));//ruta jasper
    	parameters.put("medidas_institucionales",pathjasper+rb.getString("reporte3.jasper_medid_inst"));//ruta jasper
    	
    	if(!crDAO.existeGenracionReporte(login)){//verifica si el mismo usuario esta generando el mismo reporte
    		if(crDAO.existenDatosReporte()){//verifica si el reporte contiene datos
    			crDAO.ponerReporte(MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filezip,"zip",filezip,"0","ReporteInsertarEstado");//coloca el reporte en estado '0'
    			if((jasperIni.getPath()!=null) && (parameters!=null) &&(!parameters.values().equals("0"))){//verifica si se lleno el parameter y el .jasper existe
    				bytes=crDAO.correrjaper(jasperIni.getPath(),parameters);
    				list = new ArrayList();
    				try {
    					fileXls = crDAO.getArchivoXLS(jasperIni,parameters, pathrepRelativo, fileXls);
    					list.add(fileXls);
					} catch (Exception e) {
						e.printStackTrace();
					}
    				if(bytes!=null){
    					if(ponerArchivo(MODULOREPORTE,pathrep,bytes,filepdf)){
    						list.add(pathrep+filepdf);//pdf
    						int size=100000;
    						zip.ponerZip(pathrep,filezip,size,list);
    							if(!crDAO.updateReporteEstado(MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filezip,"zip",""+filezip,"ReporteActualizarListo")){
    								crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filezip,"zip",""+filezip,"ReporteActualizarBoletinPaila","Ocurrio problema al actualizar fecha final del reporte");
    								request.setAttribute("error","Ocurrio problema al actualizar fecha final del reporte");
    							}else{
    								return Ruta.getRelativo(getServletContext(),rb.getString("reporte3.path")+"."+login.getUsuarioId())+filezip;
    							}
    					}else{
    						crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filepdf,"pdf",""+filepdf,"ReporteActualizarBoletinPaila","Error al crear el zip del reporte");
        					request.setAttribute("error","Error al crear el zip del reporte");
        					request.setAttribute("errormsj",getMensaje3());
    					}
    				}else{
    					crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filepdf,"pdf",""+filepdf,"ReporteActualizarBoletinPaila","Error al generar el jasper del reporte");
    					request.setAttribute("error","Error al generar el jasper del reporte");
    					request.setAttribute("errormsj",crDAO.getMensaje());
    				}
    			}else{
    				crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filepdf,"pdf",""+filepdf,"ReporteActualizarBoletinPaila","Ruta del jasper no encontrada o el map de parametros es null");
    				request.setAttribute("error","Ruta del jasper no encontrada o el map de parametros es null");
    			}
    		}else{
    			//reporte no tiene datos
    			crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filepdf,"pdf",""+filepdf,"ReporteActualizarBoletinPaila","Reporte no tiene datos");
    			request.setAttribute("error","Reporte no tiene datos");
    		}
    	}else{
    		//existe un reporte generandose
    		crDAO.ponerReporteMensaje("2",MODULOREPORTE,login.getUsuarioId(),pathrepRelativo+filepdf,"pdf",""+filepdf,"ReporteActualizarBoletinPaila","Existe un reporte generandose para ese usuario");
    		request.setAttribute("error","Existe un reporte generandose para ese usuario");
    	}
   		return "--";
   	}
   	
   	
   	
   	public String reporte1(Login login,ConflictoFiltro fr,Collection col){
   	    Excel excel=new Excel();
   	    String relativo=null;
   	    String []config=new String[4];
   	    String path;
   	    String archivo;
   	    int i;
   	    path=rb.getString("rep1.path")+"."+login.getUsuarioId();
   	    archivo=getNombreRep1(fr);
   	    relativo=Ruta.getRelativo(getServletContext(),path);
   	    i=0;
   	    config[i++]=Ruta2.get(getServletContext(),rb.getString("rep1.PathPlantilla"));//path de la plantilla
   	    config[i++]=rb.getString("rep1.Plantilla");//nombre de la plantilla		
   	    config[i++]=Ruta.get(getServletContext(),path);//path del nuevo archivo		
   	    config[i++]=archivo;//nombre del nuevo archivo
   	    if(!excel.plantilla1(config,col,fr,login)){
   	    	//setMensaje(excel.getMensaje());
   	    	return "--";
   	    }
   	    //Logger.print(login.getUsuarioId(),"Plantilla Asignatura Inst:"+filtroPlantilla.getInstitucion()+" Sede:"+filtroPlantilla.getSede()+" Jorn:"+filtroPlantilla.getJornada()+" Gra:"+filtroPlantilla.getGrado()+" Grupo:"+grupo+" Asig:"+asig+" Per:"+filtroPlantilla.getPeriodo(),3,1,this.toString());
   	    ponerReporte(login.getUsuarioId(),relativo+config[3],"xls",config[3],1);
   	    return relativo+config[3];
   	}
   	
   	public String reporte2(Login login,ConflictoFiltro fr,Collection col){
   	    Excel excel=new Excel();
   	    String relativo=null;
   	    String []config=new String[4];
   	    String path;
   	    String archivo;
   	    int i;
   	    path=rb.getString("rep2.path")+"."+login.getUsuarioId();
   	    archivo=getNombreRep2(fr);
   	    relativo=Ruta.getRelativo(getServletContext(),path);
   	    i=0;
   	    config[i++]=Ruta2.get(getServletContext(),rb.getString("rep2.PathPlantilla"));//path de la plantilla
   	    config[i++]=rb.getString("rep2.Plantilla");//nombre de la plantilla		
   	    config[i++]=Ruta.get(getServletContext(),path);//path del nuevo archivo		
   	    config[i++]=archivo;//nombre del nuevo archivo
   	    if(!excel.plantilla2(config,col,fr,login)){
   	    	//setMensaje(excel.getMensaje());
   	    	return "--";
   	    }
   	    //Logger.print(login.getUsuarioId(),"Plantilla Asignatura Inst:"+filtroPlantilla.getInstitucion()+" Sede:"+filtroPlantilla.getSede()+" Jorn:"+filtroPlantilla.getJornada()+" Gra:"+filtroPlantilla.getGrado()+" Grupo:"+grupo+" Asig:"+asig+" Per:"+filtroPlantilla.getPeriodo(),3,1,this.toString());
   	    ponerReporte(login.getUsuarioId(),relativo+config[3],"xls",config[3],1);
   	    return relativo+config[3];
   	}
   	
   	public String reporte4(Login login,ConflictoFiltro fr,Collection col){
   	    Excel excel=new Excel();
   	    String relativo=null;
   	    String []config=new String[4];
   	    String path;
   	    String archivo;
   	    int i;
   	    path=rb.getString("rep1.path")+"."+login.getUsuarioId();
   	    archivo=getNombreRep1(fr);
   	    relativo=Ruta.getRelativo(getServletContext(),path);
   	    i=0;
   	    config[i++]=Ruta2.get(getServletContext(),rb.getString("rep1.PathPlantilla"));//path de la plantilla
   	    config[i++]=rb.getString("rep4.Plantilla");//nombre de la plantilla		
   	    config[i++]=Ruta.get(getServletContext(),path);//path del nuevo archivo		
   	    config[i++]=archivo;//nombre del nuevo archivo
   	    if(!excel.plantilla1(config,col,fr,login)){
   	    	//setMensaje(excel.getMensaje());
   	    	return "--";
   	    }
   	    //Logger.print(login.getUsuarioId(),"Plantilla Asignatura Inst:"+filtroPlantilla.getInstitucion()+" Sede:"+filtroPlantilla.getSede()+" Jorn:"+filtroPlantilla.getJornada()+" Gra:"+filtroPlantilla.getGrado()+" Grupo:"+grupo+" Asig:"+asig+" Per:"+filtroPlantilla.getPeriodo(),3,1,this.toString());
   	    ponerReporte(login.getUsuarioId(),relativo+config[3],"xls",config[3],1);
   	    return relativo+config[3];
   	}

   	
   	/**
   	*	Funcinn: Pone el registro del reporte generado <BR>
   	*	@param String us	
   	*	@param String rec	
   	*	@param String tipo	
   	*	@param String nombre	
   	**/
   	public void ponerReporte(String us,String rec,String tipo,String nombre,int modulo){
   		Collection list;
   		Object[] o;
   		list = new ArrayList();
   		o=new Object[2];
   		o[0]=cadena; o[1]=us;//usuario
   		list.add(o);
   		o=new Object[2];
   		o[0]=cadena; o[1]=rec;//rec
   		list.add(o);
   		o=new Object[2];
   		o[0]=cadena; o[1]=tipo;//tipo
   		list.add(o);
   		o=new Object[2];
   		o[0]=cadena; o[1]=nombre;//nombre
   		list.add(o);
   		o=new Object[2];
   		o[0]=entero; o[1]=""+modulo;//nombre
   		list.add(o);
   		cursor.registrar(rb.getString("ReporteInsertar"),list);
   	}
   	
   	public String getNombreRep1(ConflictoFiltro fr){
   	    String local=formatear((fr.getNomlocal1().length()>10?fr.getNomlocal1().substring(0,10):fr.getNomlocal1()));
   	    String periodo=formatear((fr.getNomperiodo1().length()>10?fr.getNomperiodo1().substring(0,10):fr.getNomperiodo1()));
   	    String fe=f2.toString().replace(':','-').replace('.','-').substring(0,10)+"_"+f2.toString().replace(':','-').replace('.','-').substring(11,19);
   	    
   	    return ("reporte1CE_L_"+local+"_P_"+periodo+"_("+fe+").xls");
   	}
   	
   	public String getNombreRep2(ConflictoFiltro fr){
   	    String sede=formatear((fr.getNomsede2().length()>10?fr.getNomsede2().substring(0,10):fr.getNomsede2()));
   	    String jorn=formatear((fr.getNomjorn2().length()>10?fr.getNomjorn2().substring(0,10):fr.getNomjorn2()));
   	    String periodo=formatear((fr.getNomperiodo2().length()>10?fr.getNomperiodo2().substring(0,10):fr.getNomperiodo2()));
   	    String fe=f2.toString().replace(':','-').replace('.','-').substring(0,10)+"_"+f2.toString().replace(':','-').replace('.','-').substring(11,19);
   	    
   	    return ("reporte2CE_S_"+sede+"_J_"+jorn+"_P_"+periodo+"_("+fe+").xls");
   	}
   	
   	public String formatear(String a){
   	    return (a.replace(' ','_').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','n').replace('n','N').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','c').replace(':','_').replace('.','_').replace('/','_').replace('\\','_'));
   	}

   	
   	/**
   	*	Funcinn: Recibe la peticion por el metodo get de HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
   	    doPost(request,response);//redirecciona la peticion a doPost
   	}
   	
   	/**
   	*	Funcinn: Recibe la peticion por el metodo Post de HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
   	    String s=process(request, response);
   	    if(s!=null && !s.equals(""))
   	        ir(2,s,request,response);
   	}
   	
   	/**
   	*	Funcinn: Redirige el control a otro servlet
   	*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
   	*	@param	String s
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   	public void ir(int a,String s,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
   	    if(cursor!=null)cursor.cerrar();
   	    RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
   	    if(a==1) rd.include(request, response);
   	    else rd.forward(request, response);
   	}

   	/**
   	*	Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
   	*	@param HttpServletRequest request
   	*	@return boolean 
   	*/
   	public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
   	    HttpSession session = request.getSession();
   	    if(session.getAttribute("login")==null)
   	        return false;
   	    return true;
   	}
   	
   	/**
     * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
     *	@param HttpServletRequest request
     */
    	public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
    	    HttpSession session=request.getSession();
    	}
    	
    	/**
      *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
      *	@param	String s
      **/
     	public void setMensaje(String s){
     	    if (!err){
     	        err=true;
     	        mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
     	    }
     	    mensaje+="  - "+s+"\n";
     	}

     	/**
      *	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
      *	@param	String s
      **/
     	public void setMensaje2(String s){
     	    if (!err){
     	        err=true;
     	        mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
     	    }
     	    mensaje="  - "+s+"\n";
     	}

		public String getMensaje3() {
			return (mensaje3 != null) ? mensaje3 : "";
		}

		public void setMensaje3(String mensaje3) {
			this.mensaje3 = mensaje3;
		}

}
