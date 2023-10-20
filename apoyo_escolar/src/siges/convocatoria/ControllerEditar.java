package siges.convocatoria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import siges.convocatoria.dao.*;
import siges.convocatoria.beans.*;
import siges.dao.Cursor;
import siges.dao.Util;
import siges.util.Correo;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{
    
    private String mensaje;
    private String buscar;
    private boolean err;
    private Util util;
    private Cursor cursor;
    private ResourceBundle rb;
    private Collection list;
    private Object[] o;
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
        rb=ResourceBundle.getBundle("convocatoria");
        ConvocatoriaDAO convDAO;
        Convocatoria conv;
        String pag="";
        String sig="/convocatoria/NuevoConvocatoria.jsp";
        String sig1="/convocatoria/FiltroConvocatoria.jsp";
        String p_inte=this.getServletContext().getInitParameter("integrador");
        String p_home=this.getServletContext().getInitParameter("home");
        String p_login=this.getServletContext().getInitParameter("login");
        String p_error=this.getServletContext().getInitParameter("er");
        String dato,docum;
        Collection col1=null;
        Correo correo=new Correo();
        int tipo;
        String consulta,fcategoria,fclase,catnombre,clanombre;
        cursor=new Cursor();
        util=new Util(cursor);
        convDAO=new ConvocatoriaDAO(cursor);
        try{
            if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
                tipo=0;
            }else{
                tipo=Integer.parseInt((String)request.getParameter("tipo"));
            }
            
            switch(tipo){
            //filtro
            case 0:
                pag=sig1;
                break;
            //form
            case 1:
            	Convocatoria con=null;
                docum=(String)request.getParameter("docum");
                if(!convDAO.validarExistencia(docum)){
                	con=convDAO.asignar(docum);
                	consulta=rb.getString("getInscritos");
    		        
    		        list = new ArrayList();
    				    o=new Object[2];
    						o[0]=enterolargo;
    						o[1]=docum;
    						list.add(o);
    						
    		        col1=util.getFiltro(consulta,list);
        		    request.setAttribute("inscritos",col1);
                }else{
                	con=new Convocatoria();
                	con.setFornumdoc(docum);
                	con.setForgrado0("-1");
                	
                }
                session.setAttribute("nuevoConvocatoria",con);
                list=util.getFiltro(rb.getString("cursos"));
                request.setAttribute("cursos",list);
                pag=sig;
                break;
            //guardar
            case 2:
                conv=(Convocatoria)request.getSession().getAttribute("nuevoConvocatoria");
                //if(convDAO.validarExistencia(conv.getFornumdoc())){
                //System.out.println(conv.getForcodcurso()+"<-----"+convDAO.isCupoLleno(Integer.parseInt(conv.getForcodcurso())));
                if(!convDAO.isCupoLleno(Integer.parseInt(conv.getForcodcurso()))){
                	if(convDAO.insertar(conv)){
                        conv=convDAO.asignar(conv.getFornumdoc());
                        session.setAttribute("nuevoConvocatoria",conv);
                        String mensaje2="La inscripcinn se realizn satisfactoriamente con el nnmero:"+conv.getForid();
                        list=util.getFiltro(rb.getString("cursos"));
                        request.setAttribute("cursos",list);
                        
                        consulta=rb.getString("getInscritos");
        		        
        		        list = new ArrayList();
        		        o=new Object[2];
        		        o[0]=enterolargo;
        		        o[1]=conv.getFornumdoc();
        		        list.add(o);
        						
        		        col1=util.getFiltro(consulta,list);
            		    request.setAttribute("inscritos",col1);
                        
                        //enviar correo docente
                        if(conv.getForcorreoins().equals(conv.getForcorreoper())){
                            if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a:"+conv.getForcorreoins();
                            }else{
                                mensaje2+="\n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins();
                            }
                        }else{
                            if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoins()+" y al correo: "+conv.getForcorreoper();
                            }else if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && !enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoins()+" . \n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoper();
                            }else if(!enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoper()+" . \n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins();
                            }else{
                                mensaje2+="\n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins()+" y al correo: "+conv.getForcorreoper();
                            }
                        }
                        
                        //enviar correo responsable
                        if(enviarCorreo2(correo,convDAO,conv,conv.getResponsable())){
                            mensaje2+="\n Un correo ha sido enviado al responsable del curso confirmando su inscripcinn.";
                        }else{
                            
                        }
                        request.setAttribute("mensaje",mensaje2);
                        pag=sig;
                    }else{
                        request.setAttribute("mensaje",convDAO.getMensaje());
                        session.removeAttribute("nuevoConvocatoria");
                        pag=sig1;
                    }
                }else{
                	request.setAttribute("mensaje","No es posible realiza la inscripcinn. El cupo del curso al que desea inscribirse esta lleno.");
                    session.removeAttribute("nuevoConvocatoria");
                    pag=sig1;
                }
                /*}else{
                    request.setAttribute("mensaje","Usted ya ha registrado la inscripcinn, no puede hacerla de nuevo.");
                    session.removeAttribute("nuevoConvocatoria");
                    pag=sig1;
                }*/
                
            break;
            //eliminar
            case 3:
            	String curso=(String)request.getParameter("id");
            	conv=(Convocatoria)request.getSession().getAttribute("nuevoConvocatoria");
            	list=util.getFiltro(rb.getString("cursos"));
                request.setAttribute("cursos",list);
            	if(convDAO.eliminarCurso(conv.getFornumdoc(),curso)){
            		request.setAttribute("mensaje","Curso eliminado satisfactoriamente");
            	}else{
            		request.setAttribute("mensaje",convDAO.getMensaje());
            	}
            	consulta=rb.getString("getInscritos");
		        
		        list = new ArrayList();
				    o=new Object[2];
						o[0]=enterolargo;
						o[1]=conv.getFornumdoc();
						list.add(o);
						
		        col1=util.getFiltro(consulta,list);
    		    request.setAttribute("inscritos",col1);
            	
            	pag=sig;
           	break;
            //actualizar datos
            case 4:
                conv=(Convocatoria)request.getSession().getAttribute("nuevoConvocatoria");
                //if(convDAO.validarExistencia(conv.getFornumdoc())){
                	if(convDAO.actualizar(conv)){
                        conv=convDAO.asignar(conv.getFornumdoc());
                        session.setAttribute("nuevoConvocatoria",conv);
                        String mensaje2="Los datos fueron modificados satisfactoriamente";
                        list=util.getFiltro(rb.getString("cursos"));
                        request.setAttribute("cursos",list);
                        
                        consulta=rb.getString("getInscritos");
        		        
        		        list = new ArrayList();
        		        o=new Object[2];
        		        o[0]=enterolargo;
        		        o[1]=conv.getFornumdoc();
        		        list.add(o);
        						
        		        col1=util.getFiltro(consulta,list);
            		    request.setAttribute("inscritos",col1);
                        
                        request.setAttribute("mensaje",mensaje2);
                        pag=sig;
                    }else{
                        request.setAttribute("mensaje",convDAO.getMensaje());
                        session.removeAttribute("nuevoConvocatoria");
                        pag=sig1;
                    }
                    
                /*}else{
                    request.setAttribute("mensaje","Usted ya ha registrado la inscripcinn, no puede hacerla de nuevo.");
                    session.removeAttribute("nuevoConvocatoria");
                    pag=sig1;
                }*/
                
            break;
            //Inscribir Curso
            case 5:
                conv=(Convocatoria)request.getSession().getAttribute("nuevoConvocatoria");
                //if(convDAO.validarExistencia(conv.getFornumdoc())){
                //System.out.println(conv.getForcodcurso()+"<-----"+convDAO.isCupoLleno(Integer.parseInt(conv.getForcodcurso())));
                if(!convDAO.isCupoLleno(Integer.parseInt(conv.getForcodcurso()))){
                	if(convDAO.insertarCurso(conv)){
                        conv=convDAO.asignar(conv.getFornumdoc());
                        session.setAttribute("nuevoConvocatoria",conv);
                        String mensaje2="La inscripcinn se realizn satisfactoriamente con el nnmero:"+conv.getForid();
                        list=util.getFiltro(rb.getString("cursos"));
                        request.setAttribute("cursos",list);
                        
                        consulta=rb.getString("getInscritos");
        		        
        		        list = new ArrayList();
        				    o=new Object[2];
        						o[0]=enterolargo;
        						o[1]=conv.getFornumdoc();
        						list.add(o);
        						
        		        col1=util.getFiltro(consulta,list);
            		    request.setAttribute("inscritos",col1);
                        
                        //enviar correo docente
                        if(conv.getForcorreoins().equals(conv.getForcorreoper())){
                            if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a:"+conv.getForcorreoins();
                            }else{
                                mensaje2+="\n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins();
                            }
                        }else{
                            if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoins()+" y al correo: "+conv.getForcorreoper();
                            }else if(enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && !enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoins()+" . \n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoper();
                            }else if(!enviarCorreo(correo,convDAO,conv,conv.getForcorreoins()) && enviarCorreo(correo,convDAO,conv,conv.getForcorreoper())){
                                mensaje2+="\n Un correo de confirmacinn de la inscripcinn ha sido enviado a: "+conv.getForcorreoper()+" . \n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins();
                            }else{
                                mensaje2+="\n Problemas al enviar confirmacinn de la inscripcinn al correo: "+conv.getForcorreoins()+" y al correo: "+conv.getForcorreoper();
                            }
                        }
                        
                        //enviar correo responsable
                        if(enviarCorreo2(correo,convDAO,conv,conv.getResponsable())){
                            mensaje2+="\n Un correo ha sido enviado al responsable del curso confirmando su inscripcinn.";
                        }else{
                            
                        }
                        request.setAttribute("mensaje",mensaje2);
                        pag=sig;
                    }else{
                        request.setAttribute("mensaje",convDAO.getMensaje());
                        session.removeAttribute("nuevoConvocatoria");
                        pag=sig1;
                    }
                }else{
                	request.setAttribute("mensaje","No es posible realiza la inscripcinn. El cupo del curso al que desea inscribirse esta lleno.");
                	session.removeAttribute("nuevoConvocatoria");
                	pag=sig1;
                }
                /*}else{
                    request.setAttribute("mensaje","Usted ya ha registrado la inscripcinn, no puede hacerla de nuevo.");
                    session.removeAttribute("nuevoConvocatoria");
                    pag=sig1;
                }*/
                
            break;
            }
            
            return pag;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error "+this+": "+e.toString());
            throw new	ServletException(e);
        }finally{
            if(cursor!=null)cursor.cerrar();
        }
    }
    
    /**
   	* Envia correo al solicitante
   	* @return boolean
   	*/
   	public boolean enviarCorreo(Correo correo, ConvocatoriaDAO cDAO, Convocatoria conv, String email){
   			String mensaje;
   			mensaje="Estimado docente. \n\n Usted ha quedado inscrito en el curso "+conv.getNombrecurso()+" en la jornada "+conv.getJorncurso()+". \n Para detalles, puede comunicarse con "+conv.getResponsable()+" al correo: "+conv.getCorreoresp()+" ";
   			correo.setTo(email);
   			correo.setFrom(cDAO.getParam("FROM"));
   			correo.setMailHost(cDAO.getParam("MAILHOST"));
   			correo.setSubject(cDAO.getParam("SUBJECTDOC"));
   			correo.setMensaje(mensaje);
   			return correo.enviarCorreo();
     }
   	
   	/**
   	* Envia correo al responsable
   	* @return boolean
   	*/
   	public boolean enviarCorreo2(Correo correo, ConvocatoriaDAO cDAO, Convocatoria conv, String email){
   			String mensaje;
   			mensaje="Estimado responsable. \n\n El docente "+conv.getFornombre1()+" "+conv.getForapellido1()+" se ha inscrito al curso "+conv.getNombrecurso()+" en la jornada "+conv.getJorncurso();
   			correo.setTo(email);
   			correo.setFrom(cDAO.getParam("FROM"));
   			correo.setMailHost(cDAO.getParam("MAILHOST"));
   			correo.setSubject(cDAO.getParam("SUBJECTRESP"));
   			correo.setMensaje(mensaje);
   			return correo.enviarCorreo();
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