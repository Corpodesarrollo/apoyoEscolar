package siges.conflicto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import siges.dao.*;
import siges.exceptions.InternalErrorException;

import siges.conflicto.dao.*;
import siges.conflicto.beans.ConflictoEscolar;
import siges.conflicto.beans.TipoConflicto;
import siges.conflicto.beans.ResolucionConflictos;
import siges.conflicto.beans.MedidasInst;
import siges.conflicto.beans.Proyectos;
import siges.conflicto.beans.InfluenciaConflictos;
import siges.login.beans.*;


/**
 * @author Administrador
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;//mensaje en caso de error
   	private String buscar;
   	private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
   	private Cursor cursor;//objeto que maneja las sentencias sql
   	private ConflictoEscolarDAO conflictoDAO;//
   	private ResourceBundle rb;
   	private Collection list;
   	private Object[] o;
   	private ConflictoEscolar ce;
   	private TipoConflicto tp;
   	private ResolucionConflictos rc;
   	private MedidasInst mi;
   	private Proyectos pr;
   	private InfluenciaConflictos ic;
   	private Integer cadena=new Integer(java.sql.Types.VARCHAR);
   	private Integer entero=new Integer(java.sql.Types.INTEGER);
   	private Integer fecha=new Integer(java.sql.Types.DATE);
   	private Integer nulo=new Integer(java.sql.Types.NULL);
   	private Integer doble=new Integer(java.sql.Types.DOUBLE);
   	private Integer caracter=new Integer(java.sql.Types.CHAR);
   	private Integer enterolargo=new Integer(java.sql.Types.BIGINT);
   	
   	/**
   	*	Procesa la peticion HTTP
   	*	@param	HttpServletRequest request
   	*	@param	HttpServletResponse response
   	**/
   		public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
   		    HttpSession session = request.getSession();
   		    rb=ResourceBundle.getBundle("conflicto");
   		    String sig="/conflicto/NuevoTipoConflicto.jsp";
   		    //String sig="/conflicto/FiltroConflicto.jsp";
   		    String sig1="/conflicto/NuevoTipoConflicto.jsp";
   		    String sig2="/conflicto/NuevoFormasRes.jsp";
   		    String sig3="/conflicto/NuevoMedidas.jsp";
   		    String sig4="/conflicto/NuevoProyectos.jsp";
   		    String sig5="/conflicto/NuevoInfluenciaConf.jsp";
   		    String sig6="/conflicto/NuevoConsolidadoGrupo.jsp";
   		    String p_inte=this.getServletContext().getInitParameter("integrador");
   		    String p_home=this.getServletContext().getInitParameter("home");
   		    String p_login=this.getServletContext().getInitParameter("login");
   		    String p_error=this.getServletContext().getInitParameter("er");
   		    cursor=new Cursor();
   		    int tipo, categoria;
   		    try{
   		        Login login=(Login)session.getAttribute("login");
   		        session.getAttribute("login");
   		        conflictoDAO=new ConflictoEscolarDAO(cursor);
   		        if(!asignarBeans(request)){
   		            setMensaje("Error capturando datos de sesinn para el usuario");
   		            request.setAttribute("mensaje",mensaje);
   		            return(p_error);
   		        }
   		        
   		        if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
   		            borrarBeans(request);
   		            tipo=10;
   		        }
   		        else{
   		            tipo=Integer.parseInt((String)request.getParameter("tipo"));
   		        }
	    		    
   		        if(request.getParameter("categoria")==null || ((String)request.getParameter("categoria")).equals("")){
   		            categoria=0;
   		        }
   		        else{
   		            categoria=Integer.parseInt((String)request.getParameter("categoria"));
   		        }
	    		    
   		        if(ce==null){
   		            ce=new ConflictoEscolar();
   		            if(login.getSedeId().equals("") && login.getJornadaId().equals("")){
   		                ce.setCesede("-1");
   		                ce.setCejornada("-1");
   		            }else{
   		                ce.setCesede(login.getSedeId());
   		                ce.setCejornada(login.getJornadaId());
   		            }
   		            ce.setCeperiodo("-1");
   		            session.setAttribute("nuevoConflicto",ce);
   		        }
   		        List listaPeriodos = conflictoDAO.obtenerPeridos(login.getVigencia_actual());
   		        if(listaPeriodos != null && listaPeriodos.size()>0){
   		        	request.setAttribute("listaPeriodos", listaPeriodos);
   		        }
   		        switch(tipo){
   		        //tipos conflicto
   		        case 11:

   		            ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            agregarTipoConflicto(request, ce, categoria, login);
   		            request.setAttribute("mensaje",mensaje);
   		        case 10:
   		        	System.out.println("***entro a buscar - case 10***");
   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		            	ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            	if(!conflictoDAO.isPeriodoCerrado(ce,login)){
   		                    tp=conflictoDAO.asignarTipoConflicto(ce.getCejerjorn(),ce.getCeperiodo(),categoria);
   		                    if(tp!=null){
   		                        session.setAttribute("nuevoTipo",tp);
   		                        request.setAttribute("sololectura","0");
   		                    }else{
   		                        setMensaje(conflictoDAO.getMensaje());
   		                        request.setAttribute("mensaje",mensaje);
   		                        return p_error;
   		                    }
   		                }else{
   		                    request.setAttribute("sololectura","1");
   		                }
   		            	cargarConflicto(request,categoria,1,"");
   		            }
   		            
   		            sig=sig1;
   		            break;
   		        case 21:

   		            ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            agregarFormaResolucion(request, ce, categoria, "resol");
   		            request.setAttribute("mensaje",mensaje);
   		        case 20:
   		        	System.out.println("***case 20 - Resolucinn de conflictos ***");	
   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		            	ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		                if(!conflictoDAO.isPeriodoCerrado(ce,login)){
   		                    rc=conflictoDAO.asignarResolucionConflicto(ce.getCejerjorn(),ce.getCeperiodo(),categoria);
   		                    if(rc!=null){
   		                        session.setAttribute("nuevoResolucion",rc);
   		                        request.setAttribute("sololectura","0");
   		                    }else{
   		                        setMensaje(conflictoDAO.getMensaje());
   		                        request.setAttribute("mensaje",mensaje);
   		                        return p_error;
   		                    }
   		                }else{
   		                    request.setAttribute("sololectura","1");
   		                }
   		            cargarConflicto(request,categoria,2,ce.getCejerjorn());
   		            }
   		            
   		            sig=sig2;
   		            break;
   		        case 31:

   		            ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            agregarMedidasInst(request, ce, categoria);
   		            request.setAttribute("mensaje",mensaje);
   		        case 30:

   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		            	ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		                if(!conflictoDAO.isPeriodoCerrado(ce,login)){
   		                    mi=conflictoDAO.asignarMedidasInst(ce.getCejerjorn(),ce.getCeperiodo(),categoria);
   		                    if(mi!=null){
   		                        session.setAttribute("nuevoMedidas",mi);
   		                        request.setAttribute("sololectura","0");
   		                    }else{
   		                        setMensaje(conflictoDAO.getMensaje());
   		                        request.setAttribute("mensaje",mensaje);
   		                        return p_error;    
   		                    }
   		                }else{
   		                    request.setAttribute("sololectura","1");
   		                }
   		                cargarConflicto(request,categoria,0,"");
   		            }

   		            sig=sig3;
   		            break;
   		        case 41:

   		            ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            agregarFormaResolucion(request, ce, categoria, "proy");
   		            request.setAttribute("mensaje",mensaje);
   		        case 40:

   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		            	ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		                if(!conflictoDAO.isPeriodoCerrado(ce,login)){
   		                    pr=conflictoDAO.asignarProyectos(ce.getCejerjorn(),ce.getCeperiodo(),categoria);
   		                    if(pr!=null){
   		                        session.setAttribute("nuevoProyectos",pr);
   		                        request.setAttribute("sololectura","0");
   		                    }else{
   		                        setMensaje(conflictoDAO.getMensaje());
   		                        request.setAttribute("mensaje",mensaje);
   		                        return p_error;    
   		                    }
   		                }else{
   		                    request.setAttribute("sololectura","1");
   		                }
   		                cargarConflicto(request,categoria,2,ce.getCejerjorn());
   		            }

   		            sig=sig4;
   		            break;
   		        case 51:

   		            ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            agregarInfluencia(request, ce, categoria);
   		            request.setAttribute("mensaje",mensaje);
   		        case 50:

   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		            	ce.setCejerjorn(conflictoDAO.obtenerJerar_1_6(ce.getCesede(),ce.getCejornada(),login));
   		            	if(!conflictoDAO.isPeriodoCerrado(ce,login)){
   		                    ic=conflictoDAO.asignarInfluencia(ce.getCejerjorn(),ce.getCeperiodo(),categoria);
   		                    if(ic!=null){
   		                        session.setAttribute("nuevoInfluencia",ic);
   		                        request.setAttribute("sololectura","0");
   		                    }else{
   		                        setMensaje(conflictoDAO.getMensaje());
   		                        request.setAttribute("mensaje",mensaje);
   		                        return p_error;    
   		                    }
   		                }else{
   		                    request.setAttribute("sololectura","1");
   		                }
   		            	cargarConflicto(request,categoria,1,"");
   		            }

   		            sig=sig5;
   		            break;
   		        case 60:
   		            if(!ce.getCesede().equals("-1") && !ce.getCejornada().equals("-1") && !ce.getCeperiodo().equals("-1")){
   		                cargarGrados(request, login, ce);
   		                cargarConsolidado(request, login, ce,conflictoDAO);
   		            }

   		            cargarConflicto(request,categoria,1,"");
   		            sig=sig6;
   		            break;
   		        }
   		        //cargarConflicto(request,categoria);
   		        cargarSedeJornada(request,login);
   		        //asignarSedeJornada(login,ce,session);

   		        return sig;
   		    }
   		    catch(Exception e){
   		        e.printStackTrace();
   		        System.out.println("Error "+this+": "+e.toString());
   		        throw new	ServletException(e);
   		    }finally{
   		        if(cursor!=null)cursor.cerrar();
   		    }
   		}
   		
   		public void cargarGrados(HttpServletRequest request,Login l, ConflictoEscolar ce) throws ServletException, IOException{
   		    try{
   		  		    list = new ArrayList();
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=l.getInstId();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCemetodologia();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCesede();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCejornada();
   		  		    list.add(o);
   		  		    request.setAttribute("filtroGradoF2",conflictoDAO.getFiltro(rb.getString("filtroSedeJornadaGradoInstitucion2"),list));
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		     			throw new	ServletException(th);
   		  		}
   		}
   		
   		public void cargarConsolidado(HttpServletRequest request,Login l, ConflictoEscolar ce, ConflictoEscolarDAO cDAO) throws ServletException, IOException{
   		    try{
   		  		    list = new ArrayList();
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCeperiodo();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=l.getInstId();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCesede();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCejornada();
   		  		    list.add(o);
   		  		    o=new Object[2];
   		  		    o[0]=new Integer(java.sql.Types.BIGINT);
   		  		    o[1]=ce.getCemetodologia();
   		  		    list.add(o);
   		  		    o=new Object[2];
		  		    o[0]=new Integer(java.sql.Types.BIGINT);
		  		    o[1]=cDAO.getVigencia();
		  		    list.add(o);
   		  		    
   		  		    request.setAttribute("lista",conflictoDAO.getFiltro(rb.getString("Asignar.Consolidado"),list));
   		  		    
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		     			throw new	ServletException(th);
   		  		}
   		}
   		
   		public void cargarSedeJornada(HttpServletRequest request,Login login) throws ServletException, IOException{
     	  	ResourceBundle rb=ResourceBundle.getBundle("conflicto");
   	  		try{
   	  		    Collection list = new ArrayList();
   	  		    Object[] o=new Object[2];
   	  		    o[0]=enterolargo;
   	  		    o[1]=login.getInstId();
   	  		    list.add(o);
   	  		    request.setAttribute("filtroSedeF",conflictoDAO.getFiltro(rb.getString("filtroSedeInstitucion"),list));
   	  		    request.setAttribute("filtroJornadaF",conflictoDAO.getFiltro(rb.getString("filtroSedeJornadaInstitucion"),list));
   	  		    request.setAttribute("filtroMetodologia",conflictoDAO.getFiltro(rb.getString("filtroMetodologia"),list));
   	  		}catch(InternalErrorException e){System.out.println("excepcion "+e);}
   		}
   		
   		public void asignarSedeJornada(Login login,ConflictoEscolar ce,HttpSession session){
   		    if(ce==null){
   		        ce=new ConflictoEscolar();
   		        if(login.getSedeId().equals("") && login.getJornadaId().equals("")){
   		            System.out.println(" sede y jorn no existen ");
   		            ce.setCesede("-1");
   		            ce.setCejornada("-1");
   		            System.out.println(ce.getCesede()+" - "+ce.getCejornada());
   		        }else{
   		            System.out.println(" SI existen ");
   		            ce.setCesede(login.getSedeId());
   		            ce.setCejornada(login.getJornadaId());
   		        }
   		        ce.setCeperiodo("-1");
   		        session.setAttribute("nuevoConflicto",ce);
   		    }
   		}
   		
   		public void agregarInfluencia(HttpServletRequest request, ConflictoEscolar c, int cat)throws ServletException, IOException{
   		    try{
   		        String texto, aux;
   		        int z=0;
   		        list = new ArrayList();
   		        o=new Object[2];
   		        o[z++]=new Integer(java.sql.Types.INTEGER);
   		        o[z++]=""+cat;
   		        list.add(o);
   		        String[][] cat5=conflictoDAO.getFiltroMatriz(rb.getString("filtroCat"),list);
   		        if(conflictoDAO.eliminarTipoConflicto(c.getCejerjorn(),c.getCeperiodo(),cat)){
   		            for(int i=0;i<cat5.length;i++){
			   		            aux=(String)cat5[i][0];
			   		            texto=request.getParameter("d"+aux)!=null?request.getParameter("d"+aux):"-1";
			   		            if(!texto.equals("")){
				                    if(!texto.equals("-1")){
				                        if(conflictoDAO.insertarInfluencias(c.getCejerjorn(),c.getCeperiodo(),aux,texto,cat)){
				                        	setMensaje2("Operacion satisfecha");
				                        }else{
				                            setMensaje2(conflictoDAO.getMensaje());
				                        }
				                    }
			   		            }
			   		        }
   		        }else{
   		            setMensaje2(conflictoDAO.getMensaje());   		            
   		        }
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		        throw new	ServletException(th);
   		    }
   		}
   		
   		public void agregarMedidasInst(HttpServletRequest request, ConflictoEscolar c, int cat)throws ServletException, IOException{
   		    try{
   		        String aux1,aux2,texto;
   		        int z=0;
   		        list = new ArrayList();
   		        o=new Object[2];
   		        o[z++]=new Integer(java.sql.Types.INTEGER);
   		    				o[z++]=""+cat;
   		    				list.add(o);
   		    				String[][] cat3=conflictoDAO.getFiltroMatriz(rb.getString("filtroCat"),list);
   		    				String[][] cl=conflictoDAO.getFiltroMatriz(rb.getString("filtroClaseConflicto"));
   		    				if(conflictoDAO.eliminarTipoConflicto(c.getCejerjorn(),c.getCeperiodo(),cat)){
   		    				    for(int i=0;i<cl.length;i++){
   		    				        aux1=(String)cl[i][0];
   		    				        for(int j=0;j<cat3.length;j++){
   		    				            aux2=(String)cat3[j][0];
   		    				            texto=request.getParameter("t"+aux1+"|"+aux2)!=null?request.getParameter("t"+aux1+"|"+aux2):"-1";
   		    				            if(!texto.equals("")){
   		    				                if(!texto.equals("-1")){
   		    				                    if(conflictoDAO.insertarMedidasInst(c.getCejerjorn(),c.getCeperiodo(),aux1,aux2,texto,cat)){
   		    				                        setMensaje2("Operacion satisfecha");
   		    				                    }else{
   		    				                        setMensaje2(conflictoDAO.getMensaje());
   		    				                    }
   		    				                }
   		    				            }
   		    				        }
   		    				    }
   		    				}else{
   		            setMensaje2(conflictoDAO.getMensaje());   		            
   		        }
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		        throw new	ServletException(th);
   		    }
   		}
   		
   		public void agregarFormaResolucion(HttpServletRequest request, ConflictoEscolar c, int cat, String param)throws ServletException, IOException{
   			try{
   				String[] vec;
   				String[] vecOtro;
   				List filtroCat;
   		        String tipo,item,otro=null;
   		        if(conflictoDAO.eliminarTipoConflicto(c.getCejerjorn(),c.getCeperiodo(),cat)){
   		        	if(request.getParameterValues(param)!=null){
   		        		vec=request.getParameterValues(param);
   		        		vecOtro=request.getParameterValues("otro_");
   		        		filtroCat = (List) request.getSession().getAttribute("filtroCat");
   		        		
   		        		int contadorOtro=0;
   		        		for(int i=0;i<vec.length;i++){
   		        			Object[] categoria = (Object[]) filtroCat.get(i);
   		        			if(!vec[i].equals("-1")){
   		        				
   		        				item=vec[i].replace('|',':').split(":")[0];
   		        				tipo=""+0;//vec[i].replace('|',':').split(":")[1];
   		        				
   		        				
   		        				if(vecOtro != null && categoria.length > 4){
	   		        				if(categoria[4]!= null && categoria[4].toString().equals("1")){
	   		        					otro = vecOtro[contadorOtro];
	   		        					contadorOtro++;
	   		        					//otro=(String)(request.getParameter("otro")==null?"":request.getParameter("otro"));
	   		        				}else{
	   		        					otro="";
	   		        				}
	   		        			}else{
	   		        				otro="";
	   		        			}
   		        				
   		        				if(conflictoDAO.insertarFormaResolucion(c.getCejerjorn(),c.getCeperiodo(),tipo,item,cat,otro)){
   		        					setMensaje2("Operacion satisfecha");
   		        				}else{
   		        					setMensaje2(conflictoDAO.getMensaje());
   		        				}
   		        			}else{
   		        				if(categoria[4]!= null && categoria[4].toString().equals("1")){
   	   		        				contadorOtro++;
   	   		        			}
   		        				otro="";
   		        			}
   		        			
   		        		}
   		        	request.getSession().removeAttribute("filtroCat");
   		        	}
   		        	/*otro=(String)request.getParameter("otro");
   		        	 if(otro!=null)	conflictoDAO.actualizarOtro(otro);*/
   		        }else{
   		        	setMensaje2(conflictoDAO.getMensaje());   		            
   		        }
   			}catch(Throwable th){
   				th.printStackTrace();
   				throw new	ServletException(th);
   			}
   		}
   		
   		public void agregarTipoConflicto(HttpServletRequest request, ConflictoEscolar c, int cat, Login login)throws ServletException, IOException{
   		    try{
   		        String[][] cl=conflictoDAO.getFiltroMatriz(rb.getString("filtroClaseConflicto"));
   		        String[][] tp=conflictoDAO.getFiltroMatriz(rb.getString("filtroTipoConflicto"));
   		        String auxcl,auxtp,auxtp2,aux,aux0,aux2;String aux3;
   		        if(conflictoDAO.eliminarTipoConflicto(c.getCejerjorn(),c.getCeperiodo(),cat)){
   		            for(int i=0;i<cl.length;i++){
   		                auxcl=(String)cl[i][0];
   		                for(int j=0;j<tp.length;j++){
   		                    auxtp=(String)tp[j][3];
   		                    auxtp2=(String)tp[j][0];
   		                    if(auxcl.equals(auxtp)){
   		                        aux=request.getParameter("k"+auxtp2)!=null?request.getParameter("k"+auxtp2):"-1";
   		                        aux2=request.getParameter("c"+auxtp2)!=null?request.getParameter("c"+auxtp2):"-1";
   		                        aux3=request.getParameter("c_ent"+auxtp2)!=null?request.getParameter("c_ent"+auxtp2):"-9";
   		                        if(aux.equals("2") && !aux2.equals("-1")){
   		                            if(conflictoDAO.insertarTipoConflicto(c.getCejerjorn(),c.getCeperiodo(),auxtp2,aux2,aux3,cat)){
   		                                setMensaje2("Operacion satisfecha");
   		                            }
   		                            else{
   		                                setMensaje2(conflictoDAO.getMensaje());
   		                            }
   		                        }
   		                    }
   		                }
   		            }
   		        }else{
   		            setMensaje2(conflictoDAO.getMensaje());   		            
   		        }
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		        throw new	ServletException(th);
   		    }
   		}
   		
   		public void cargarConflicto(HttpServletRequest request, int cat, int forma, String jerar) throws ServletException, IOException{
   		    try{
   		        int z=0;
   		        if(forma==1){
   		        	System.out.println(" - cargarConflicto - forma 1 ");
   		            request.setAttribute("filtroTipo",conflictoDAO.getFiltro(rb.getString("filtroTipoConflicto")));	
   		        }else if(forma==2){
   		        	System.out.println(" - cargarConflicto - forma 2 ");
   		            z=0;
   		            list = new ArrayList();
   		            o=new Object[2];
   		            o[z++]=new Integer(java.sql.Types.INTEGER);
   		            o[z++]=jerar;
   		            list.add(o);
   		            z=0;
   		            o=new Object[2];
   		            o[z++]=new Integer(java.sql.Types.INTEGER);
   		            o[z++]=conflictoDAO.getVigencia();
   		            list.add(o);
   		            request.setAttribute("filtroTipo",conflictoDAO.getFiltro(rb.getString("filtroTipoConflicto2"),list));
   		        }
   				
   		        request.setAttribute("filtroClase",conflictoDAO.getFiltro(rb.getString("filtroClaseConflicto")));
   				
   		        z=0;
   		        list = new ArrayList();
   		        o=new Object[2];
   		        o[z++]=new Integer(java.sql.Types.INTEGER);
   		        o[z++]=""+cat;
   		        list.add(o);
   		        request.getSession().setAttribute("filtroCat",conflictoDAO.getFiltro(rb.getString("filtroCat"),list));
   		        request.setAttribute("filtroCat",conflictoDAO.getFiltro(rb.getString("filtroCat"),list));
   		    }catch(Throwable th){
   		        th.printStackTrace();
   		        throw new	ServletException(th);
   		    }
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
   			  	ir(1,s,request,response);
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
   			    ce=(ConflictoEscolar)session.getAttribute("nuevoConflicto");
   			    tp=(TipoConflicto)session.getAttribute("nuevoTipo");
   			    rc=(ResolucionConflictos)session.getAttribute("nuevoResolucion");
   			    mi=(MedidasInst)session.getAttribute("nuevoMedidas");
   			    pr=(Proyectos)session.getAttribute("nuevoProyectos");
   			    ic=(InfluenciaConflictos)session.getAttribute("nuevoInfluencia");
   			    if(session.getAttribute("login")==null)
   				    return false;
   			    return true;
   			}		
   		
   		/**
    	* Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
    	*	@param HttpServletRequest request
    	*/
      public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
          //HttpSession session=request.getSession();
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
}