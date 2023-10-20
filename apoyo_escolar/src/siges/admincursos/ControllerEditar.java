package siges.admincursos;

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

import siges.admincursos.dao.AdminCursosDAO;
import siges.admincursos.beans.AdminCursos;
import siges.admincursos.io.Excel;
import siges.dao.Cursor;
import siges.dao.Ruta;
import siges.dao.Util;

/**
 * @author Administrador
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public class ControllerEditar extends HttpServlet{
    
    private Timestamp f2;
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
        f2=new Timestamp(new java.util.Date().getTime());
        HttpSession session = request.getSession();
        rb=ResourceBundle.getBundle("siges.admincursos.admincursos");
        AdminCursosDAO acDAO;
        String sig="/admincursos/NuevoCursos.jsp";
        String sig1="/admincursos/ReporteCursos.jsp";
        String p_inte=this.getServletContext().getInitParameter("integrador");
        String p_home=this.getServletContext().getInitParameter("home");
        String p_login=this.getServletContext().getInitParameter("login");
        String p_error=this.getServletContext().getInitParameter("er");
        String dato;
        String consulta,fcategoria,fclase,catnombre,clanombre;
        cursor=new Cursor();
        util=new Util(cursor);
        acDAO=new AdminCursosDAO(cursor);
        int tipo;
        try{
            AdminCursos ac=(AdminCursos)session.getAttribute("admincursos");
            AdminCursos ac2=(AdminCursos)session.getAttribute("admincursos2");
            
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
            case 0:
                
                borrarBeans(request);
                break;
            //guardar o actualizar
            case 1:
                
                if(ac.getEstado().equals("1")){
                    if(acDAO.compararBeansCursos(ac,ac2)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");
                    }else if(acDAO.actualizarCursos(ac)){
                        request.setAttribute("mensaje","La información fue actualizada satisfactoriamente");                        
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;
                    }
                }else if(!ac.getEstado().equals("1")){
                    if(acDAO.insertarCursos(ac)){
                        request.setAttribute("mensaje","La información fue ingresada satisfactoriamente");
                    }else{
                        request.setAttribute("mensaje",acDAO.getMensaje());
                        return p_error;    
                    }
                }
                
                borrarBeans(request);
                break;
            //editar
            case 2:
                
                dato=request.getParameter("id");
                ac=acDAO.asignarCursos(dato);
                if(ac!=null){
                    session.setAttribute("admincursos",ac);
                    session.setAttribute("admincursos2",ac.clone());
                }else{
                    request.setAttribute("mensaje",acDAO.getMensaje());
                    return p_error;    
                }
                break;
            //eliminar
            case 3:
                
                dato=request.getParameter("id");
                if(!acDAO.validarExistenciaCursos(dato)){
                    request.setAttribute("mensaje","No se puede eliminar, tiene registros asociados");
                }else if(acDAO.eliminarCursos(dato)){
                    request.setAttribute("mensaje","Operacion satisfecha");
                }else{
      		            request.setAttribute("mensaje",acDAO.getMensaje());
                }
                borrarBeans(request);
                break;
            //interfaz de reporte
            case 4:
                
                sig=sig1;
                break;
            //generar reporte
            case 5:
                
                list=util.getFiltro(rb.getString("rep.insertar"));
                request.setAttribute("reporte",reporte(ac,list));
                sig=sig1;
                break;
            }
            
            return sig;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error "+this+": "+e.toString());
            throw new	ServletException(e);
        }finally{
            if(cursor!=null)cursor.cerrar();
        }
    }
    
    public String reporte(AdminCursos ac,Collection col){
        Excel excel=new Excel();
   	    String relativo=null;
   	    String []config=new String[4];
   	    String path;
   	    String archivo;
   	    int i;
   	    path=rb.getString("rep.path");
   	    archivo=getNombreRep();
   	    relativo=Ruta.getRelativo(getServletContext(),path);
   	    i=0;
   	    config[i++]=Ruta.get(getServletContext(),rb.getString("rep.PathPlantilla"));//path de la plantilla
   	    config[i++]=rb.getString("rep.Plantilla");//nombre de la plantilla		
   	  		config[i++]=Ruta.get(getServletContext(),path);//path del nuevo archivo		
   	  		config[i++]=archivo;//nombre del nuevo archivo
   	  		if(!excel.plantilla1(config,col,ac)){
   	  		    //setMensaje(excel.getMensaje());
   	  		    return "--";
   	  		}
   	  		//Logger.print(login.getUsuarioId(),"Plantilla Asignatura Inst:"+filtroPlantilla.getInstitucion()+" Sede:"+filtroPlantilla.getSede()+" Jorn:"+filtroPlantilla.getJornada()+" Gra:"+filtroPlantilla.getGrado()+" Grupo:"+grupo+" Asig:"+asig+" Per:"+filtroPlantilla.getPeriodo(),3,1,this.toString());
   	  		ponerReporte("-1",relativo+config[3],"xls",config[3],1);
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
   	
   	public String getNombreRep(){
   	    String fe=f2.toString().replace(':','-').replace('.','-').substring(0,10)+"_"+f2.toString().replace(':','-').replace('.','-').substring(11,19);
   	    
   	    return ("ReporteCursos_("+fe+").xls");
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
        session.removeAttribute("admincursos");
        session.removeAttribute("admincursos2");
        request.removeAttribute("guia");
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
