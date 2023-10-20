package siges.parametro;

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
import siges.dao.Cursor;
import siges.dao.Util;
import siges.login.beans.Login;
import siges.parametro.beans.Parametros;
import siges.parametro.dao.ParametroDAO;

/**
 * @author Administrador
 *
 */
	public class ControllerFiltro extends HttpServlet{

	    private String mensaje;//mensaje en caso de error
	    private String buscar;
	    private boolean err;//variable que inidica si hay o no errores en la validacion de los datos del formulario
	    private Cursor cursor;//objeto que maneja las sentencias sql
	    private Util util;//
	    private Parametros p;
	    private Login login;
	    private ParametroDAO paramDAO;
	    private HttpSession session;
	    private Collection col1, list;
	    private Object o[];
	    private ResourceBundle rbParam;
	    private Integer cadena=new Integer(java.sql.Types.VARCHAR);
	    private Integer entero=new Integer(java.sql.Types.INTEGER);
	    private Integer fecha=new Integer(java.sql.Types.DATE);
	    private Integer nulo=new Integer(java.sql.Types.NULL);
	    private Integer doble=new Integer(java.sql.Types.DOUBLE);
	    private Integer caracter=new Integer(java.sql.Types.CHAR);
	    private Integer enterolargo=new Integer(java.sql.Types.BIGINT);

	    /**
	     *	Funcinn: Procesa la peticion HTTP
	     *	@param	HttpServletRequest request
	     *	@param	HttpServletResponse response
	     **/
	    public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	        session = request.getSession();
	        rbParam=ResourceBundle.getBundle("siges.parametro.bundle.parametro");
	        list = new ArrayList();
	        //String sig,sig1,sig2,sig3,sig4,sig5,sig6,sig7,sig8,sig9,sig10;
	        String consulta;
	        int tipo;
	        String sig="/parametro/ControllerParametroSave.do";
	        String sig1="/parametro/Generales.jsp";
	        //sig1="/parametro/Vigencia.jsp";
	        //String sig2="/parametro/Horario.jsp";
	        String sig3="/parametro/Recolector.jsp";
	        String sig4="/parametro/Pbatch.jsp";
	        String sig5="/parametro/Ibatch.jsp";
	        //String sig6="/parametro/Boletin.jsp";
	        //String sig7="/parametro/Lnotas.jsp";
	        //String sig8="/parametro/Integracion.jsp";
	        //String sig9="/parametro/RepEstadistico.jsp";
	        //String sig10="/parametro/Certificado.jsp";
	        String p_inte=this.getServletContext().getInitParameter("integrador");
	        String p_home=this.getServletContext().getInitParameter("home");
	        String p_login=this.getServletContext().getInitParameter("login");
	        String p_error=this.getServletContext().getInitParameter("er");
	        err=false;
	        mensaje=null;
	        cursor=new Cursor();

	        try{
	            util=new Util(cursor);
	            paramDAO=new ParametroDAO(cursor);
	            if(request.getParameter("tipo")==null || ((String)request.getParameter("tipo")).equals("")){
	                borrarBeans(request);
	                tipo=1;
	                p=paramDAO.obtenerParams();
	                session.setAttribute("parametros",p);
	            }else{
	                tipo=Integer.parseInt((String)request.getParameter("tipo"));
	            }
	            if(!asignarBeans(request)){
	                this.setMensaje("Error capturando datos de sesinn para el usuario");
	                request.setAttribute("mensaje",mensaje);
	                return p_error;
	            }

	            switch(tipo){
	            case 1:

	                sig=sig1;
	                break;
	            /*case 2:

	                sig=sig2;
	                break;*/
	            case 3:

	                sig=sig3;
	                break;
	            case 4:

	                sig=sig4;
	                break;
	            case 5:

	                sig=sig5;
	                break;
	            /*case 6:

	                sig=sig6;
	                break;*/
	            /*case 7:

	                sig=sig7;
	                break;*/
	            /*case 8:

	                sig=sig8;
	                break;*/
	            /*case 9:

	                sig=sig9;
	                break;*/
	            /*case 10:

	                sig=sig10;
	                break;*/
	            }
	            return sig;
	        }catch(Exception e){
	            System.out.println("Error "+this+": "+e.toString());
	            throw new	ServletException(e);
	        }finally{
	            if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
	        }
	    }

	    /**
	     *	Funcinn: Inserta los valores por defecto para el bean de información bnsica con respecto a la informacion de la institucinn educativa
	     *	@param HttpServletRequest request
	     *	@return boolean 
	     */
	    public boolean asignarBeans(HttpServletRequest request)throws ServletException, IOException{ 
	        p=(Parametros)session.getAttribute("parametros");
	        if(session.getAttribute("login")!=null){
	            login=(Login)session.getAttribute("login");
	            return true;
	        }
	        return true;
	    }

	    /**
	     * Funcinn: Elimina del contexto de la sesion los beans de informacion del usuario
	     *	@param HttpServletRequest request
	     */
	    public void borrarBeans(HttpServletRequest request) throws ServletException, IOException{
	        session.removeAttribute("parametros");
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
	     *	Funcinn: Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
	     *	@param	String s
	     **/
	    public void setMensaje(String s){
	        if(!err){
	            err=true;
	            mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
	        }
	        mensaje+="  - "+s+"\n";
	    }
	}