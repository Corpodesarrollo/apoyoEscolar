package siges.datoMaestro;


import javax.servlet.RequestDispatcher; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import org.apache.commons.validator.*;
import java.io.IOException;
import siges.datoMaestro.beans.*;
import siges.dao.*;
import siges.login.beans.*;
import siges.util.Recursos;

/**
*	Nombre: DatoMaestroNuevoSave.java	<BR>
*	Descripcinn:	Proviene de DatoMaestroNuevo.jsp y procesa la insercion de un nuevo registro de dato maestro<BR>
*	Funciones de la pngina:	Usa el objeto DatoMaestroBean de la sesion para procesar los datos de un nuevo registro	<BR> y enviar el control dependiendo de si fue ono posible la transaccion <BR>
*	Entidades afectadas:	La correspondiente al dato maestro que se especifica en el objeto DatoMaestroBean	<BR>
*	Fecha de modificacinn:	20/07/2005	<BR>
*	@author Latined	<BR>
*	@version v 1.0	<BR>
*/


public class DatoMaestroNuevoSave extends HttpServlet
{
	private Cursor cursor;//objeto que maneja las sentencias sql
	private GenericValidator validator;//validador del bean
	private Util util;
	private Dao dao;
	
	

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
		String texto[];//vector que contiene el texto digitado en los input de tipo 'TEXT'
		String lista[];//vector que contiene los items seleccionados de los 'select'
		String listadep[];//vector que contiene los items seleccionados de los 'select'
		String check[];//vector que contiene los items seleccionados de los 'select'
		String checksentencia[];//vector que contiene los items seleccionados de los 'select'
		String institucion;	
		String nivel;	
		String intensidad[];
		String [][] matrix;
		String [] codigo;
		String [] codigoj;
		String [] codigol;
		String [] codigod;
		String [] codigou;
		String [] codigou1;
		String [] cont;
		String u;	
		String num;	
		String metodologiaid;//variable que almacena el id de la metodologia de la institucion
		String metodologia;//variable que almacena el nombre de la metodologia de la institucion	
		Login login;

		int id;//valor del ID para los datos maestros cuyo ID es generado automaticamente
		int dato;//variable que captura numero de dato maestro del que vamos a mostrar datos
		int validar;//variable que indica el tipo de validacion que se efectuara a los datos
		String er;//nombre de la pagina a la que ira si hay errores
		String ant;//pagina a la que ira en caso de que no se pueda procesar esta pagina
		String sig;//nombre de la pagina a la que ira despues de ejecutar los comandos de esta
		String home;//nombre de la pagina a la que ira si hay errores
		String boton;//captura el valor del boton pulsado en el formulario
		String buscar;//consulta sql para validar la existencia de un registro igual
		String buscarlogro;//consulta sql para validar la existencia de un logro igual
		String buscarcontenido;//consulta sql para validar la existencia de un logro igual
		String buscartema;//consulta sql para validar la existencia de un logro igual
		String searchcode;//consulta sql para encontrar el cndigo de la jerarquia
		String searchunique;//consulta sql para encontrar el cndigo de la jerarquia
		String searchunique1;//consulta sql para encontrar el cndigo de la jerarquia
		String buscarrepetido;
		String buscarrepetido1;
		String consul;//consulta sql para encontrar el cndigo de la jerarquia
		String insertar;//consulta sql para la insercion del nuevo registro
		String insertar_transaccion[];//consulta sql para la insercion del nuevo registro
		String insertarlogro;//consulta sql para la insercion del nuevo registro
		String insertarjerarquia;
		String insertarjerarquia1[];
		String actualizar;//consulta sql para la actualizacion del id en la tabla 'consecutivoid'
		String consecutivo;//consulta sql para buscar el valor del ID de las tablas maestras en la tabla 'consecutivoid'
		String r[];//vector que contiene todos los campos del registro a actualizar
		String x1,y1;
		DatoMaestroBean datoMaestro;//objeto que tendra los datos referentes al dato maestro escogido	
		validator=new GenericValidator();
		String vigencia;

		ant="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";//pagina a la que se dara el control si algo falla
		sig="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";//pagina a la que se dara el control despues de procesar este servlet
		er="/error.jsp";
		home="/bienvenida.jsp";
		x1=y1="";
		matrix=null;
		buscar=buscarlogro=buscarcontenido=buscartema=searchcode=searchunique=consul=insertar=insertarlogro=insertarjerarquia=actualizar=null;
		buscarrepetido=buscarrepetido1=null;
		codigo=codigoj=codigol=codigod=codigou=codigou1=cont=null;
		u=num=null;
		vigencia=null;
		insertarjerarquia1=null;
		insertar_transaccion=null;
		datoMaestro=(DatoMaestroBean)request.getSession().getAttribute("datoMaestro");
		int posicion=0;
		
		try{
		cursor=new Cursor();
		util=new Util(cursor);
		dao= new Dao(cursor);
		login=(Login)request.getSession().getAttribute("login");

		if(datoMaestro==null){
			ir(2,home,request,response);
			return;
		}	
		boton=(request.getParameter("cmd")!=null)?request.getParameter("cmd"):new String("Aceptar");
		if (boton.equals("Aceptar")){
			validar=0;

			lista=new String[8];
			texto=new String[10];
			lista[1]=(request.getParameter("lista1")!=null)?request.getParameter("lista1"):new String("null");//captura el valor de el select cuyo nombre es 'lista1'
			lista[2]=(request.getParameter("lista2")!=null)?request.getParameter("lista2"):new String("null");//captura el valor de el select cuyo nombre es 'lista2'
			lista[3]=(request.getParameter("lista3")!=null)?request.getParameter("lista3"):new String("null");//captura el valor de el select cuyo nombre es 'lista3'
			lista[4]=(request.getParameter("lista4")!=null)?request.getParameter("lista4"):new String("null");//captura el valor de el select cuyo nombre es 'lista4'
			lista[5]=(request.getParameter("lista5")!=null)?request.getParameter("lista5"):new String("null");//captura el valor de el select cuyo nombre es 'lista5'
			lista[6]=(request.getParameter("lista6")!=null)?request.getParameter("lista6"):new String("null");//captura el valor de el select cuyo nombre es 'lista6'

			texto[1]=(request.getParameter("texto1")!=null)?request.getParameter("texto1"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto1'
			texto[2]=(request.getParameter("texto2")!=null)?request.getParameter("texto2"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto2'
			texto[3]=(request.getParameter("texto3")!=null)?request.getParameter("texto3"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto3'
			texto[4]=(request.getParameter("texto4")!=null)?request.getParameter("texto4"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto4'
			texto[5]=(request.getParameter("texto5")!=null)?request.getParameter("texto5"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto5'
			texto[6]=(request.getParameter("texto6")!=null)?request.getParameter("texto6"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto6'
			texto[7]=(request.getParameter("texto7")!=null)?request.getParameter("texto7"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto7'
			texto[8]=(request.getParameter("texto8")!=null)?request.getParameter("texto8"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto8'
			texto[9]=(request.getParameter("texto9")!=null)?request.getParameter("texto9"):new String("");//captura el valor de el input de tipo TEXT cuyo nombre es 'texto9'

			check=request.getParameterValues("chk");
			intensidad=request.getParameterValues("ih");
			insertar_transaccion=new String[20];	
			institucion=login.getInstId();
			nivel=login.getNivel();
			metodologia=login.getMetodologia();
			metodologiaid=login.getMetodologiaId();
			vigencia=dao.getVigencia();	
			
			if(vigencia==null){
			  System.out.println("VIGENCIA NULA");  
				ir(2,home,request,response);				
				return;
			}
			
			if(check!=null)
				checksentencia=new String[check.length];
			
			dato=Integer.parseInt(datoMaestro.getDato());//numero del dato maestro al que hacemos referencia

System.out.println("dato +++++++++++++++++++++++++++++++++++ " + dato);
			switch (dato){

			case 1:		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 5 and (lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(5)+"))) = lower(ltrim(rtrim('"+texto[3]+"'))))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (5,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.JORNADA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;				

			case 2:	//G_Constante			
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar=" select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+=" where G_ConTipo = 10 and (lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"'))) ";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(5)+"))) = lower(ltrim(rtrim('"+texto[3]+"'))))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (10,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPODOCUMENTO);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 3:	//G_Constante	Departamento
				System.out.println("crear nevo Departamento");
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select * from G_Constante ";//consulta para extraer los datos
				buscar+=" where G_ConTipo = 6 and (lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(5)+"))) = lower(ltrim(rtrim('"+texto[3]+"'))))";

				//construccion de la consulta de insercion del registro
				insertar_transaccion[1]="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (6,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				//insercion del registro en la tabla de jerarquias
				insertar_transaccion[2]="insert into g_jerarquia (g_jertipo,g_jernivel,g_jercodigo,g_jerdepto) values (1,1,  CODIGO_JERARQUIA.nextVal ,ltrim(rtrim('"+texto[1]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
				}else{
						if(cursor.registrar_transaccion(insertar_transaccion)){
						   request.setAttribute("ok","ok");
							 request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							 Recursos.setRecurso(Recursos.DEPARTAMENTO);				        
							 ir(2,sig, request, response);
							 return;
	  				}
	          else{
	            
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
				}
				
				//break;

				case 4://nivel
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 1 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (1,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.NIVEL);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;


			case 5:	//G_Constante		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 2 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (2,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 6://G_TipoEspacio 
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" values('"+texto[1]+"',ltrim(rtrim('"+texto[2]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOESPACIO);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
				//break;

			case 8://G_TipoReconocimiento 
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
						buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
						buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
						//construccion de la consulta de insercion del registro
						insertar="insert into "+datoMaestro.getTabla()+" values('"+texto[1]+"',ltrim(rtrim('"+texto[2]+"')))";
						
						if(cursor.existe(buscar)){
								
								request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
								ir(2,ant, request, response);
								return;
							}else{
								if(cursor.registrar(insertar)){
									request.setAttribute("ok","ok");
									request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
									Recursos.setRecurso(Recursos.TIPORECONOCIMIENTO);
									ir(2,sig, request, response);
								return;
								}
								else{
									
									request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
									ir(2,ant, request, response);
									return;
								}
							}			
					//break;

			case 9:	//G_Constante
				checksentencia=null;
				//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 3 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (3,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.METODOLOGIA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}							
				//break;


				case 10://G_Constante Localidad para Jerarquia
					System.out.println("Entro a crear localidad");
					checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from G_Constante where G_ConTipo = 8  and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar_transaccion[1]="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConCodPadre,G_ConNombre) values (8,ltrim(rtrim('"+texto[1]+"')),"+lista[1]+",ltrim(rtrim('"+texto[2]+"')))";			
					
					//buscar padre
					String buscar2="select G_JerDepto from g_jerarquia where G_JerTipo=1 and G_JerNivel=2 and G_JerMunic= "+lista[1]+"";
					String padre=cursor.nombre(buscar2)[0];

					//insercion del registro en la tabla de jerarquias
					if(!padre.equals(null))
					  insertar_transaccion[2]="insert into g_jerarquia (G_JerTipo,G_JerNivel,G_Jercodigo,G_Jerdepto,G_JerMunic,G_JerLocal) values (1,3,  CODIGO_JERARQUIA.nextVal ,"+padre+","+lista[1]+",ltrim(rtrim('"+texto[1]+"')))";

					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
				//break;


			case 11:	//G_Constante				
				checksentencia=null;
				//construccion de la consulta para verificar registros iguales
				buscar="select * from G_Constante where G_ConTipo = 9  and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				 //construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (9,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOOCUPANTE);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 12:	//G_Constante			
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 4 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (4,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.ESPECIALIDAD);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 13://Condicion 
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" values('"+texto[1]+"',ltrim(rtrim('"+texto[2]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.CONDICION);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
				//break;


			case 14:	//G_Constante		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 11 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (11,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.CONVIVENCIA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 15:	//G_Constante	
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 12 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (12,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.DISCAPACIDAD);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;

			case 16:	//G_Constante			
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 13 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (13,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.CAPACIDAD);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;

			case 17:	//G_Constante		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 14 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (14,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.ETNIA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 18:	//G_Constante		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 15 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (15,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.AUSENCIA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 19:	//G_Constante		
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar=" select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+=" where G_ConTipo = 16 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (16,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.PROGRAMA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

				case 21://G_Constante Municipio para Jerarquia
					System.out.println("crear Municipio/localida ");
					checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from G_Constante where G_ConTipo = 7  and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar_transaccion[1]="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConCodPadre,G_ConNombre) values (7,ltrim(rtrim('"+texto[1]+"')),"+lista[1]+",ltrim(rtrim('"+texto[2]+"')))";

					//insercion del registro en la tabla de jerarquias
					insertar_transaccion[2]="insert into g_jerarquia (G_JerTipo,G_JerNivel,G_Jercodigo,G_Jerdepto,G_JerMunic) values (1,2, codigo_jerarquia.nextVal ,"+lista[1]+",ltrim(rtrim('"+texto[1]+"')))";

					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
					}else{
						if(cursor.registrar_transaccion(insertar_transaccion)){
						  request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.LOCALIDAD);
							ir(2,sig, request, response);
							return;
	  					 }
             else{ 						 
              
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}
				//break;

				case 22:
					checksentencia=null;
					//construccion de la consulta de insercion del registro
					buscar="select * from G_Area where lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";

					insertar="insert into G_Area values (ltrim(rtrim('"+texto[1]+"')),ltrim(rtrim('"+texto[2]+"')),"+lista[1]+")";
					
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
				//break;


				case 23://g_grado
					checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(3)+"))) = lower(ltrim(rtrim('"+texto[3]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[4]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" values("+lista[1]+", ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')),ltrim(rtrim('"+texto[5]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.GRADO);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
				//break;


			case 24://G_Constante			
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 17 and (lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(5)+"))) = lower(ltrim(rtrim('"+texto[3]+"'))))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (17,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.CALENDARIO);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			

				//break;

			case 25://G_Constante			
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 18 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (18,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.PROPIEDAD);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 26://G_Constante		
				checksentencia=null;
				//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";//consulta para extraer los datos
				buscar+="where G_ConTipo = 19 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (19,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.DISCAPACIDADINSTITUCION);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 27://G_Nucleo 
				 checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(3)+"))) = lower(ltrim(rtrim('"+texto[3]+"')))";

					//construccion de la consulta de insercion del registro
					insertar_transaccion[1]="insert into "+datoMaestro.getTabla()+" values("+lista[1]+", ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')),ltrim(rtrim('"+texto[5]+"')),ltrim(rtrim('"+texto[6]+"')))";

					//buscar padre
					buscar2="select G_JerDepto from g_jerarquia where G_JerTipo=1 and G_JerNivel=2 and G_JerMunic= "+lista[1]+"";
					padre=cursor.nombre(buscar2)[0];

					//insercion del registro en la tabla de jerarquias
					if(!padre.equals(null))
					   insertar_transaccion[2]="insert into g_jerarquia (G_JerTipo,G_JerNivel,G_Jercodigo,G_Jerdepto,G_JerMunic,G_JerLocal) values (1,3,nvl((select max(g_jerarquia.g_jercodigo)+1 from g_jerarquia),1),"+padre+","+lista[1]+",ltrim(rtrim('"+texto[2]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
					}else{
						if(cursor.registrar_transaccion(insertar_transaccion)){
						  request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.NUCLEO);
							ir(2,sig, request, response);
							return;
	  					 }
	          else{ 						 
	            
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}
					
				//break;

			case 29://G_Asignatura
				checksentencia=null;
				//construccion de la consulta para verificar registros iguales
				buscar="select * from G_Asignatura where lower(ltrim(rtrim("+datoMaestro.getCampo(3)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				buscar+=" and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Asignatura (g_asicodarea,g_asicodigo,g_asinombre) values ("+lista[1]+",ltrim(rtrim('"+texto[1]+"')),ltrim(rtrim('"+texto[2]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
			//break;
			
				
			case 28://área de la institucinn
			    insertar_transaccion=new String[(check.length*2)+1];
			    posicion=0;
					//construccion de la consulta para verificar registros iguales
					buscar="select distinct arecodigo from "+ datoMaestro.getTabla() +" where AreCodInst="+institucion+" and "+datoMaestro.getCampo(2)+" = lower(ltrim(rtrim('"+metodologiaid+"')))";
					buscar+=" and "+datoMaestro.getCampo(3)+" = lower(ltrim(rtrim('"+lista[1]+"')))";
					buscar+=" and "+datoMaestro.getCampo(7)+" = "+vigencia;
					
					System.out.println("BUSCAR AREA: "+buscar);
					//construccion de la consulta de insercion del registro
/*1*/			insertar_transaccion[posicion++]="insert into "+datoMaestro.getTabla()+" values (ltrim(rtrim('"+institucion+"')),"+metodologiaid+","+lista[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')),"+vigencia+")";					
					
				if(check!=null){
					for(int z=0;z<check.length;z++){
						if(check[z]!=null){
/*2*/			     insertar_transaccion[posicion++]="insert into Grado_Area (GraAreCodInst,GraAreCodGrado,GraAreCodMetod,GraAreCodArea,GraAreVigencia) values (ltrim(rtrim('"+institucion+"')),"+check[z]+","+metodologiaid+","+lista[1]+","+vigencia+")";
						}
					 }
					}
				else{
            
						request.setAttribute("mensaje",setMensaje("nNo hay grados para la metodologia seleccionada! \nPor lo tanto no se puede insertar el registro"));
						ir(2,ant, request, response);
						return;
					}
														
					//consulta para jerarquias
					String buscar5="SELECT '2' tipo,'4' nivel,InsCodDepto,InsCodMun,InsCodLocal,InsCodigo,'"+lista[1]+"' area,'"+metodologiaid+"' metodologia FROM institucion ";
					buscar5+=" where InsCodigo = "+institucion+"";
						
					matrix=cursor.getDatoMaestro(buscar5);

					//insercion del registro en la tabla de jerarquias
					if(check!=null){
						for(int y=0;y<matrix.length;y++){
							if(matrix[y][0]!=null){
									for(int z=0;z<check.length;z++){
										if(check[z]!=null){
										    buscarrepetido=" select distinct codigo_jerarquia from g_jerarquia_tipo2_nivel4 where cod_institucion = "+institucion+" and cod_metod = "+matrix[y][7]+" and cod_area = "+matrix[y][6]+" and cod_grado="+check[z]+" and vigencia="+vigencia+"";
										    if(!cursor.existe(buscarrepetido))
/*3*/								        insertar_transaccion[posicion++]="insert into g_jerarquia (G_JerTipo,G_JerNivel,G_JerCodigo,G_JerDepto,G_JerMunic,G_JerLocal,G_JerInst,G_JerAreaAsig,G_JerMetod,G_JerGrado,G_jerVigencia) values ("+matrix[y][0]+","+matrix[y][1]+",nvl((select max(g_jerarquia.g_jercodigo)+1 from g_jerarquia),1),"+matrix[y][2]+","+matrix[y][3]+","+matrix[y][4]+","+matrix[y][5]+","+matrix[y][6]+","+matrix[y][7]+","+check[z]+","+vigencia+")";
										 }
									}
								}
							}
					 }				
					
					//sentencias para la insercinn del registro 
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
					}else{
						if(cursor.registrar_transaccion(insertar_transaccion)){
						  request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							ir(2,sig, request, response);
							return;
	  					 }
	          else{ 						 
	            
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}
				//break;


				case 30://Asignaturas

		     insertar_transaccion=new String[(check.length*2)+1];
		     posicion=0;
		     checksentencia=new String[check.length];
				 ant+="?x="+lista[1];
				 ant+="?y="+lista[2];
				 ant="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";//pagina a la que se dara el control si algo falla
					
					//construccion de la consulta para verificar registros iguales
					buscar="select distinct asicodigo from "+ datoMaestro.getTabla() +" where AsiCodInst="+institucion+" and "+datoMaestro.getCampo(2)+" = lower(ltrim(rtrim('"+metodologiaid+"')))";
					buscar+=" and "+datoMaestro.getCampo(3)+" = lower(ltrim(rtrim('"+lista[1]+"')))";
					buscar+=" and "+datoMaestro.getCampo(4)+" = lower(ltrim(rtrim('"+lista[2]+"')))";
					buscar+=" and "+datoMaestro.getCampo(8)+" = "+vigencia;
					
					//construccion de la consulta de insercion del registro
					insertar_transaccion[posicion++]="insert into "+datoMaestro.getTabla()+" values (ltrim(rtrim('"+institucion+"')),"+metodologiaid+","+lista[1]+","+lista[2]+",ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')),ltrim(rtrim('"+texto[5]+"')),"+vigencia+",null)";

					if(check!=null){
						for(int z=0;z<check.length;z++){
							if(check[z]!=null){
							   insertar_transaccion[posicion++]="insert into Grado_Asignatura (GraAsiCodInst,GraAsiCodGrado,GraAsiCodMetod,GraAsiCodAsign,GraAsiIh,GraAsiVigencia) values (ltrim(rtrim('"+institucion+"')),"+check[z]+","+metodologiaid+","+lista[2]+","+intensidad[z]+","+vigencia+")";
							}
						 }
						}
					else{
	            
							request.setAttribute("mensaje",setMensaje("nNo hay grados para la metodologia seleccionada! \nPor lo tanto no se puede insertar el registro"));
							ir(2,ant, request, response);
							return;
						}
					
					//consulta para jerarquias
					String buscar4="SELECT '3' tipo,'4' nivel,InsCodDepto,InsCodMun,InsCodLocal,InsCodigo,'"+lista[2]+"' asignatura,'"+metodologiaid+"' metodologia FROM institucion";
					buscar4+=" where InsCodigo = "+institucion+" ";

					matrix=cursor.getDatoMaestro(buscar4);				

				//insercion del registro en la tabla de jerarquias
					if(check!=null){
						for(int y=0;y<matrix.length;y++){
							if(matrix[y][0]!=null){
									for(int z=0;z<check.length;z++){
										if(check[z]!=null){
										    buscarrepetido1=" select distinct codigo_jerarquia from g_jerarquia_tipo3_nivel4 where cod_institucion = "+institucion+" AND cod_metod = "+matrix[y][7]+" AND cod_asignatura = "+matrix[y][6]+" and cod_grado="+check[z]+" and vigencia="+vigencia+"";
										    if(!cursor.existe(buscarrepetido1))
										      insertar_transaccion[posicion++]="insert into g_jerarquia (G_JerTipo,G_JerNivel,G_JerCodigo,G_JerDepto,G_JerMunic,G_JerLocal,G_JerInst,G_JerAreaAsig,G_JerMetod,G_JerGrado,G_jerVigencia) values ("+matrix[y][0]+","+matrix[y][1]+",nvl((select max(g_jerarquia.g_jercodigo)+1 from g_jerarquia),1),"+matrix[y][2]+","+matrix[y][3]+","+matrix[y][4]+","+matrix[y][5]+","+matrix[y][6]+","+matrix[y][7]+","+check[z]+","+vigencia+")";
										}
									}
								}
							}
					 }
					System.out.println("ANT: "+ant);
					//sentencias para la insercinn del registro 
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							System.out.println("ANT: "+ant);
							ir(2,ant, request, response);
							return;
					}else{
						if(cursor.registrar_transaccion(insertar_transaccion)){
						  request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							ir(2,sig, request, response);
							return;
	  					 }
	          else{
	            
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}
				//break;
						
				case 31://Plan de Estudios por metodologna de Institucinn
					checksentencia=null;
					buscar="select distinct placodinst from "+ datoMaestro.getTabla() +" where PlaCodInst= "+institucion+" and PlaCodMetod='"+metodologiaid+"' and plavigencia="+vigencia;
					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" (PlaCodInst,PlaCodMetod,PlaCriterio,PlaProcedimiento,PlaPlanesEspeciales,PlaVigencia) values (ltrim(rtrim('"+institucion+"')),'"+metodologiaid+"',ltrim(rtrim('"+texto[1]+"')),ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),"+vigencia+")";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			

				//break;	
				
				case 37://Logro
										
					ant+="?x="+lista[1];
					ant+="?y="+lista[2];
					ant="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";//pagina a la que se dara el control si algo falla
					checksentencia=null;
													
				//consulta para encontrar el cndigo de la jerarquia
					searchcode=" select distinct codigo_jerarquia from g_jerarquia_tipo3_nivel4 ";
					searchcode+=" where cod_institucion="+institucion+" and cod_metod='"+metodologiaid+"' and cod_grado='"+lista[1]+"' and cod_asignatura='"+lista[2]+"' and vigencia="+vigencia;
										
					codigol=cursor.nombre(searchcode);

					if(codigol==null){
						
						request.setAttribute("mensaje",setMensaje("Error: No se encontrn el cndigo de la jerarquia"));
						ir(2,ant, request, response);
						return;
					}				
					//construccion de la consulta para verificar registros iguales
					buscarlogro="select distinct LogNombre from "+ datoMaestro.getTabla() +" where logcodjerar='"+codigol[0]+"' and logvigencia='"+vigencia+"' and (lower(ltrim(rtrim("+datoMaestro.getCampo(6)+"))) = lower(ltrim(rtrim('"+texto[5]+"')))";
					buscarlogro+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(7)+"))) = lower(ltrim(rtrim('"+texto[6]+"'))))";
					
				//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" (LogCodJerar,LogCodigo,LogCodPerInicial,LogCodPerFinal,LogNombre,LogAbrev,LogDescripcion,LogVigencia) values ('"+codigol[0]+"',nvl((select max(logro.logcodigo)+1 from logro),1),'"+lista[3]+"','"+lista[4]+"',ltrim(rtrim('"+texto[5]+"')),ltrim(rtrim('"+texto[6]+"')),ltrim(rtrim('"+texto[7]+"')),"+vigencia+")";
						
						if(cursor.existe(buscarlogro)){
								
								request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
								ir(2,ant, request, response);
								return;
							}else{
								if(cursor.registrar(insertar)){
									request.setAttribute("ok","ok");
									request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
									ir(2,sig, request, response);
								return;
								}
								else{
									
									request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
									ir(2,ant, request, response);
									return;
								}
							}			
				//break;

							

				case 39://Descriptores Valorativos

					ant+="?x="+lista[1];
					ant+="?y="+lista[2];
					ant="/datoMaestro/DatoMaestroNuevoEdit.do?ext=1";//pagina a la que se dara el control si algo falla
					
					checksentencia=null;
					//consulta para encontrar el cndigo de la jerarquia
					searchcode=" select distinct codigo_jerarquia from g_jerarquia_tipo2_nivel4 ";
					searchcode+=" where cod_institucion="+institucion+" and cod_metod='"+metodologiaid+"' and cod_grado='"+lista[1]+"' and cod_area='"+lista[2]+"' and vigencia="+vigencia;
										
					codigod=cursor.nombre(searchcode);

					if(codigod==null){
						
						request.setAttribute("mensaje",setMensaje("Error: No se encontrn el cndigo de la jerarquia"));
						ir(2,ant, request, response);
						return;
					}				

					//construccion de la consulta para verificar registros iguales
					String buscardesc="select distinct DesNombre from "+ datoMaestro.getTabla() +" where descodjerar='"+codigod[0]+"' and desvigencia='"+vigencia+"' and (lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[6]+"')))";
					buscardesc+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(5)+"))) = lower(ltrim(rtrim('"+texto[7]+"'))))";
					
					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" (DesCodJerar,DesCodigo,DesCodTipDes,DesNombre,DesAbrev,DesDescripcion,DesCodPerInicial,DesCodPerFinal,DesVigencia) values ('"+codigod[0]+"',nvl((select max(descriptor_valorativo.descodigo)+1 from descriptor_valorativo),1),'"+lista[3]+"',ltrim(rtrim('"+texto[6]+"')),ltrim(rtrim('"+texto[7]+"')),ltrim(rtrim('"+texto[8]+"')),'"+lista[4]+"','"+lista[5]+"','"+vigencia+"')";

					if(cursor.existe(buscardesc)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
					}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							  return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
					}
				//break;


			case 40://tipo de descriptor 
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" values('"+texto[1]+"',ltrim(rtrim('"+texto[2]+"')))";

					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
					
				//break;

			case 42://Escala valorativa General
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
					buscar="select * from "+ datoMaestro.getTabla() +" where "+datoMaestro.getCampo(1)+" ="+texto[1]+" ";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into "+datoMaestro.getTabla()+" (EscCodigo,EscNombre,EscAbreviatura,EscTipo) values (ltrim(rtrim('"+texto[1]+"')),ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),'"+lista[1]+"')";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
			//break;

			case 43:	//G_Constante	tipo escala valorativa
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
				buscar+="where G_ConTipo = 23 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (23,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;

			case 44:	//G_Constante	RangoTarifa
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
				buscar+="where G_ConTipo = 20 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (20,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.TARIFA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				
				//break;

			case 45:	//G_Constante	Asociaciones privadas
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
				buscar+="where G_ConTipo = 21 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (21,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.ASOCIACION);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 46:	//G_Constante	Idioma
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
				buscar+="where G_ConTipo = 22 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (22,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";

				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.IDIOMA);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 47:	//G_Constante	Cargo GE
				checksentencia=null;
					//construccion de la consulta para verificar registros iguales
				buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
				buscar+="where G_ConTipo = 24 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
				buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

				//construccion de la consulta de insercion del registro
				insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (24,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
				
				if(cursor.existe(buscar)){
						
						request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
						ir(2,ant, request, response);
						return;
					}else{
						if(cursor.registrar(insertar)){
							request.setAttribute("ok","ok");
							request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
							Recursos.setRecurso(Recursos.TIPOCARGO);
							ir(2,sig, request, response);
						return;
						}
						else{
							
							request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
							ir(2,ant, request, response);
							return;
						}
					}			
				//break;

			case 49:	//G_Constante	tipo atencinn especial
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
					buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
					buscar+="where G_ConTipo = 31 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (31,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOATENCIONESPECIAL);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
					//break;
				
				
			case 50://ROT_TIPO_RECESO  
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
						buscar="select * from "+ datoMaestro.getTabla() +" where lower(ltrim(rtrim("+datoMaestro.getCampo(1)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
						buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";
						//construccion de la consulta de insercion del registro
						insertar="insert into "+datoMaestro.getTabla()+" values('"+texto[1]+"',ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')))";
						
						if(cursor.existe(buscar)){
								
								request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
								ir(2,ant, request, response);
								return;
							}else{
								if(cursor.registrar(insertar)){
									request.setAttribute("ok","ok");
									request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
									Recursos.setRecurso(Recursos.TIPORECESO);
									ir(2,sig, request, response);
								return;
								}
								else{
									
									request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
									ir(2,ant, request, response);
									return;
								}
							}			
					//break;
				
			case 51:	//G_Constante	tipo especialidad
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
					buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
					buscar+="where G_ConTipo = 34 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (34,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOESPECIALIDAD);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
					//break;
						
			case 52:	//G_Constante	tipo modalidad
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
					buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
					buscar+="where G_ConTipo = 33 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (33,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOMODALIDAD);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
					//break;

			case 53:	//G_Constante	tipo nnfasis
					checksentencia=null;
						//construccion de la consulta para verificar registros iguales
					buscar="select G_ConCodigo,G_ConNombre from G_Constante ";
					buscar+="where G_ConTipo = 32 and lower(ltrim(rtrim("+datoMaestro.getCampo(2)+"))) = lower(ltrim(rtrim('"+texto[1]+"')))";
					buscar+=" or lower(ltrim(rtrim("+datoMaestro.getCampo(4)+"))) = lower(ltrim(rtrim('"+texto[2]+"')))";

					//construccion de la consulta de insercion del registro
					insertar="insert into G_Constante (G_ConTipo,G_ConCodigo,G_ConNombre,G_ConAbrev,G_ConOrden) values (32,"+texto[1]+",ltrim(rtrim('"+texto[2]+"')),ltrim(rtrim('"+texto[3]+"')),ltrim(rtrim('"+texto[4]+"')))";
					
					if(cursor.existe(buscar)){
							
							request.setAttribute("mensaje",setMensaje("Existe un registro con estos datos y no es posible insertarlo"));
							ir(2,ant, request, response);
							return;
						}else{
							if(cursor.registrar(insertar)){
								request.setAttribute("ok","ok");
								request.setAttribute("mensaje","Los datos fueron registrados satisfactoriamente");
								Recursos.setRecurso(Recursos.TIPOENFASIS);
								ir(2,sig, request, response);
							return;
							}
							else{
								
								request.setAttribute("mensaje",setMensaje(cursor.getMensaje()));
								ir(2,ant, request, response);
								return;
							}
						}			
					//break;
					
			}				
		}
		}catch(Exception e){
		  e.printStackTrace();
		  System.out.println("Error "+this+": "+e.toString());
		 throw new	ServletException(e);
		}finally{
			  if(cursor!=null)cursor.cerrar();if(util!=null)util.cerrar();
		}		
	}


 /**
*	Concatena el parametro en una variable que contiene los mensajes que se van a enviar a la vista
*	@param	String s
**/
	public String setMensaje(String s){
		String mensaje="VERIFIQUE LA SIGUIENTE información: \n\n";
		mensaje="  - "+s+"\n";
		return mensaje;
	}		

/**
*	Redirige el control a otro servlet
*	@param	int a: 1=redirigir como 'include', 2=redirigir como 'forward'
*	@param	String s
*	@param	HttpServletRequest request
*	@param	HttpServletResponse response
**/
	public void ir(int a,String s,HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException{
    if(cursor!=null)cursor.cerrar();if(dao!=null)dao.cerrar();if(util!=null)util.cerrar();	    
		RequestDispatcher rd=getServletContext().getRequestDispatcher(s);
		if(a==1) rd.include(rq,rs);
		else rd.forward(rq,rs);
	}

/*
*Cierra cursor
*
*/
public void destroy(){
	if(cursor!=null)cursor.cerrar();

	cursor=null;
	util=null;
}

}