package siges.institucion.organizacion;


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
import siges.institucion.organizacion.beans.*;
import siges.institucion.organizacion.dao.OrganizacionDAO;
import siges.login.beans.Login;
import siges.util.Properties;

/**
*	Nombre:	
*	Descripcion:	
*	Parametro de entrada:	HttpServletRequest request, HttpServletResponse response
*	Parametro de salida:	HttpServletRequest request, HttpServletResponse response
*	Funciones de la pagina:	
*	Entidades afectadas:	
*	Fecha de modificacinn:	
*	@author 
*	@version 
*/
public class ControllerOrganizacionEdit extends HttpServlet{
	private String mensaje;//mensaje en caso de error
	private Cursor cursor;//objeto que maneja las sentencias sql
	private OrganizacionDAO organizacionDAO;
	private AsociacionVO asociacionVO;
	private ResourceBundle rb;
	private static String FICHA_GOB_;//lista/eliminar consejos directivos
	private static String FICHA_GOB2_;//nuevo consejo directivo
	private static String FICHA_GOB3_;//lista/eliminar consejos academicos
	private static String FICHA_GOB4_;//nuevo consejo academico
	private static String FICHA_ASO_;
	private static String FICHA_ASO2_;
	private static String FICHA_LID_;
	private static String FILTRO_LID_;
	private static String FICHA_LID2_;
	private static String FICHA_ANT_;
	private static String FICHA_HOME_;
	private static String FICHA_ERR_;

/**
*	Procesa la peticion HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	  HttpSession session = request.getSession();
	  rb=ResourceBundle.getBundle("siges.institucion.organizacion.organizacion");
	  String sig=null;
		int tipo;
		FICHA_GOB_="/institucion/organizacion/Gobierno.jsp";
		FICHA_GOB2_="/institucion/organizacion/FormaGobierno.jsp";
		FICHA_GOB3_="/institucion/organizacion/Gobierno.jsp";
		FICHA_GOB4_="/institucion/organizacion/FormaGobierno2.jsp";
		FICHA_ASO_="/institucion/organizacion/Asociaciones.jsp";
		FICHA_ASO2_="/institucion/organizacion/FormaAsociaciones.jsp";
		FICHA_LID_="/institucion/organizacion/Lideres.jsp";
		FILTRO_LID_="/institucion/organizacion/Filtro.jsp";
		FICHA_LID2_="/institucion/organizacion/FormaLideres.jsp";
		FICHA_ANT_="/index.jsp";
		FICHA_ERR_="/error.jsp";
		FICHA_HOME_="/bienvenida.jsp";
		cursor=new Cursor();
		mensaje=null;
		try{
			organizacionDAO=new OrganizacionDAO(cursor);
			if(!asignarBeans(request)){
				setMensaje("Error capturando datos de sesinn para el usuario");
				request.setAttribute("mensaje",mensaje);
				return (FICHA_ERR_);
			}
			Login login=(Login)session.getAttribute("login");
			asociacionVO=(AsociacionVO)session.getAttribute("asociacionVO");
			tipo=getTipo(request,session);
			switch(tipo){
				case Params.FICHA_GOB:
					editarGobierno(request,session,login);
					sig=FICHA_GOB_;
				break;	
				case Params.FICHA_GOB2:
					editarFormaGobierno(request,session,login);
					sig=FICHA_GOB2_;
				break;	
				case Params.FICHA_GOB3:
					editarGobierno2(request,session,login);
					sig=FICHA_GOB3_;
				break;	
				case Params.FICHA_GOB4:
					editarFormaGobierno2(request,session,login);
					sig=FICHA_GOB4_;
				break;	
				case Params.FICHA_ASO:
					editarAsociacion(request,session,login,asociacionVO);
					sig=FICHA_ASO_;
				break;	
				case Params.FICHA_ASO2:
					editarFormaAsociacion(request,session,login,asociacionVO);
					sig=FICHA_ASO2_;
				break;	
				case Params.FICHA_LID:
					editarLider(request,session,login);
					sig=FICHA_LID_;
				break;	
				case Params.FICHA_LID2:
					editarFormaLider(request,session,login);
					sig=FICHA_LID2_;
				break;	
				case Params.FILTRO_LID:
					filtroLider(request,session,login);
					sig=FILTRO_LID_;
				break;	
			}
			return sig;
		}catch(Exception e){
		    e.printStackTrace();
			throw new ServletException(e);
		}finally{
		  if(cursor!=null)cursor.cerrar();
		}
	}
	
	private int getTipo(HttpServletRequest request,HttpSession session){
			if(request.getParameter("tipo2")==null || ((String)request.getParameter("tipo2")).equals("")){
					borrarBeans(request,session);
					session.removeAttribute("editar2");
					session.removeAttribute("subframe2");
					return Params.FICHA_DEFAULT;
				}else
				    return Integer.parseInt((String)request.getParameter("tipo2"));
	}
/**
 * Elimina del contexto de la sesion los beans de informacion del usuario
 *	@param HttpServletRequest request
 */
	public void borrarBeans(HttpServletRequest request,HttpSession session){
		session.removeAttribute("ConsejoDirectivoVO");
		session.removeAttribute("ConsejoDirectivoVO2");
		session.removeAttribute("ConsejoAcademicoVO");
		session.removeAttribute("ConsejoAcademicoVO2");
		session.removeAttribute("LiderVO");
		session.removeAttribute("LiderVO2");
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
 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
 *	información que se desee mostrar de la tabla de información basica
 *	@param HttpServletRequest request
 */
	public void editarGobierno(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
		if(boton.equals("Eliminar") && login!=null){
			if(organizacionDAO.eliminarConsejoDirectivo(login,r,s)){
				session.removeAttribute("ConsejoDirectivoVO");
				session.removeAttribute("ConsejoDirectivoVO2");
			}else
				setMensaje(organizacionDAO.getMensaje());
		}
		Collection []c=new Collection[2];
		c=organizacionDAO.getConsejos(login);
	 	request.setAttribute("filtroConsejos",c[0]);
	 	request.setAttribute("filtroConsejos2",c[1]);
	}

	/**
	 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
	 *	información que se desee mostrar de la tabla de información basica
	 *	@param HttpServletRequest request
	 */
		public void editarGobierno2(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
			String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
			String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
			String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
			String t=(request.getParameter("t")!=null)?request.getParameter("t"):new String("");
			String u=(request.getParameter("u")!=null)?request.getParameter("u"):new String("");
			if(boton.equals("Eliminar") && login!=null){
				if(organizacionDAO.eliminarConsejoAcademico(login,r,s,t,u)){
					session.removeAttribute("ConsejoAcademicoVO");
					session.removeAttribute("ConsejoAcademicoVO2");
				}else
					setMensaje(organizacionDAO.getMensaje());
			}
			Collection []c=new Collection[2];
			c=organizacionDAO.getConsejos(login);
		 	request.setAttribute("filtroConsejos",c[0]);
		 	request.setAttribute("filtroConsejos2",c[1]);
		}
		
	public void editarFormaGobierno(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
		if(boton.equals("Editar") && login!=null){
			ConsejoDirectivoVO consejoDirectivoVO=organizacionDAO.getConsejoDirectivoVO(login,r,s);
			if(consejoDirectivoVO!=null){
				session.setAttribute("consejoDirectivoVO",consejoDirectivoVO);
				session.setAttribute("consejoDirectivoVO2",consejoDirectivoVO.clone());
			}else{
				setMensaje(organizacionDAO.getMensaje());
			}	
		}
		if(boton.equals("Nuevo")){
			session.removeAttribute("consejoDirectivoVO");
			session.removeAttribute("consejoDirectivoVO");
		}
		//cargar valores de forma
		Collection []c=organizacionDAO.getSedes(login);
		request.setAttribute("filtroSedeF",c[0]);
		request.setAttribute("filtroJornadaF",c[1]);
	}
	
	public void editarFormaGobierno2(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
		String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
		String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
		String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
		String t=(request.getParameter("t")!=null)?request.getParameter("t"):new String("");
		String u=(request.getParameter("u")!=null)?request.getParameter("u"):new String("");
		System.out.println("//"+r+"//"+s+"//"+t+"//"+u);
		if(boton.equals("Editar") && login!=null){
			ConsejoAcademicoVO consejoAcademicoVO=organizacionDAO.getConsejoAcademicoVO(login,r,s,t,u);
			if(consejoAcademicoVO!=null){
				session.setAttribute("consejoAcademicoVO",consejoAcademicoVO);
				session.setAttribute("consejoAcademicoVO2",consejoAcademicoVO.clone());
			}else{
				setMensaje(organizacionDAO.getMensaje());
			}	
		}
		if(boton.equals("Nuevo")){
			session.removeAttribute("consejoAcademicoVO");
			session.removeAttribute("consejoAcademicoVO2");
		}
		Collection []c=organizacionDAO.getSedesDocentes(login);
		request.setAttribute("filtroSedeF",c[0]);
		request.setAttribute("filtroJornadaF",c[1]);
		request.setAttribute("filtroDocente",c[2]);
	}
	
	/**
	 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
	 *	información que se desee mostrar de la tabla de información basica
	 *	@param HttpServletRequest request
	 */
		public void editarAsociacion(HttpServletRequest request,HttpSession session,Login login,AsociacionVO asociacionVO) throws ServletException, IOException{
			if(asociacionVO==null || asociacionVO.getAsoInst().equals("")){
				asociacionVO=organizacionDAO.getAsociacion(login);
				if(asociacionVO!=null){
					session.setAttribute("asociacionVO",asociacionVO);
					session.setAttribute("asociacionVO2",asociacionVO.clone());
				}else{
					setMensaje(organizacionDAO.getMensaje());
				}	
			}
			String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
			String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
			String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
			if(boton.equals("Eliminar") && login!=null){
				if(organizacionDAO.eliminarAsociacionIntegrantesVO(r,s,asociacionVO)){
					session.removeAttribute("asociacionIntegrantesVO");
					session.removeAttribute("asociacionIntegrantesVO");
				}else
					setMensaje(organizacionDAO.getMensaje());
			}
			if(asociacionVO!=null && !asociacionVO.getAsoCodigo().equals("")){
				request.setAttribute("filtroAsociaciones",organizacionDAO.getAsociacionesIntegrantes(asociacionVO));
			}	
		}
		
		public void editarLider(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
			String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
			String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
			String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
			String t=(request.getParameter("t")!=null)?request.getParameter("t"):new String("");
			if(boton.equals("Eliminar") && login!=null){
				if(organizacionDAO.eliminarLider(r,s,t)){
					session.removeAttribute("liderVO");
					session.removeAttribute("liderVO");
				}else
					setMensaje(organizacionDAO.getMensaje());
			}
			request.setAttribute("filtroLideres",organizacionDAO.getLideres(login));
		}
		
		public void filtroLider(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
			String cmd=(request.getParameter("combo")!=null)?request.getParameter("combo"):new String("");
			int combo=-1;
			if(cmd!=null){
				combo=Integer.parseInt(cmd);
			}
			String forma=request.getParameter("forma");
			String [] params={request.getParameter("sede"),
					request.getParameter("jornada"),
					request.getParameter("metod"),
					request.getParameter("grado"),
					request.getParameter("grupo")};
			request.setAttribute("forma",forma);
			switch(combo){
				case 1:
						request.setAttribute("var",cmd);
						request.setAttribute("col1",organizacionDAO.getEstudiantesGrado(login,params));
				break;
				case 2:
					request.setAttribute("var",cmd);
					request.setAttribute("col1",organizacionDAO.getEstudiantesGrupo(login,params));
				break;
			}
		}
		
		/**
		 *	trae de la base de datos los registros de las tablas maestras dependiendo del tipo de 
		 *	información que se desee mostrar de la tabla de información basica
		 *	@param HttpServletRequest request
		 */
			public void editarFormaAsociacion(HttpServletRequest request,HttpSession session,Login login,AsociacionVO asociacionVO) throws ServletException, IOException{
				String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
				String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
				String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
				if(boton.equals("Editar") && login!=null){
					AsociacionIntegrantesVO asociacionIntegrantesVO=organizacionDAO.getAsociacionIntegrantesVO(r,s,asociacionVO);
					if(asociacionIntegrantesVO!=null){
						session.setAttribute("asociacionIntegrantesVO",asociacionIntegrantesVO);
						session.setAttribute("asociacionIntegrantesVO2",asociacionIntegrantesVO.clone());
					}else
						setMensaje(organizacionDAO.getMensaje());
				}
				if(boton.equals("Nuevo")){
					session.removeAttribute("asociacionIntegrantesVO");
					session.removeAttribute("asociacionIntegrantesVO2");
				}
			}
			
			public void editarFormaLider(HttpServletRequest request,HttpSession session,Login login) throws ServletException, IOException{
				String boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("");
				String r=(request.getParameter("r")!=null)?request.getParameter("r"):new String("");
				String s=(request.getParameter("s")!=null)?request.getParameter("s"):new String("");
				String t=(request.getParameter("t")!=null)?request.getParameter("t"):new String("");
				String tipoLider=(request.getParameter("tipoLider")!=null)?request.getParameter("tipoLider"):new String("");
				request.setAttribute("tipoLider",tipoLider);
				if(boton.equals("Editar") && login!=null){
					LiderVO liderVO=organizacionDAO.getLiderVO(r,s,t);
					if(liderVO!=null){
						session.setAttribute("liderVO",liderVO);
						session.setAttribute("liderVO2",liderVO.clone());
					}else
						setMensaje(organizacionDAO.getMensaje());
				}
				if(boton.equals("Nuevo")){
					session.removeAttribute("liderVO");
					session.removeAttribute("liderVO2");
				}
				Collection []c=organizacionDAO.getFiltrosLider(login);
				if(c!=null){
					request.setAttribute("filtroSedeF",c[0]);
					request.setAttribute("filtroJornadaF",c[1]);
					request.setAttribute("filtroMetodologiaF",c[2]);		
					request.setAttribute("filtroGradoF2",c[3]);
					request.setAttribute("filtroGrupoF",c[4]);
				}	
			}
			
/**
*	Recibe la peticion por el metodo get de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);//redirecciona la peticion a doPost
	}

/**
*	Recibe la peticion por el metodo Post de HTTP
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String s=process(request, response);
		if(s!=null && !s.equals(""))
			ir(2,s,request,response);
	}

/**
*	Redirige el control a otro servlet
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
		mensaje="VERIFIQUE LA SIGUIENTE INFORMACION: \n\n";
		mensaje+="  - "+s+"\n";
	}
}