package siges.util;    

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import siges.util.Logger;
import siges.dao.*;
import siges.institucion.beans.Institucion;
import siges.institucion.beans.Sede;
import java.util.ResourceBundle;

public class ControllerReceptor extends HttpServlet{
  private ResourceBundle rb;
	//TIEMPOS DE TRIGGER
	private static final int BEFORE=1;
	private static final int AFTER=2;
	//CATEGORIAS DE TRIGGER
	private static final int INSTITUCION=1;
	private static final int SEDE=2;
	private static final int JORNADA=3;
	private static final int ESTUDIANTE=7;
	//FUNCIONES DE INSTITUCION
	private static final int INSERCIONINSTITUCION=1;
	private static final int ACTUALIZACIONDANE=2;
	private static final int ACTUALIZACIONNOMBRE=3;
	private static final int ACTUALIZACIONDEPTO=4;
	private static final int ACTUALIZACIONSECTOR=5;
	private static final int ELIMINACION=4;
	//FUNCIONES DE SEDE
	private static final int INSERCIONSEDE=1;
	private static final int ACTUALIZACIONDANESEDE=2;
	private static final int ACTUALIZACIONDANEINSTITUCION=3;
	private static final int ACTUALIZACIONNOMBRESEDE=4;
	private static final int ACTUALIZACIONDEPTOSEDE=5;
	private static final int ACTUALIZACIONCONSECUTIVOSEDE=6;
	private static final int ELIMINACIONSEDE=7;
	//func de jornada
	private static final int INSERCIONJORNADA=1;
	private static final int ACTUALIZACIONJORNADA=2;
	//FUNCIONES DE ESTUDIANTE
	private static final int ACTUALIZACIONNOMBREESTUDIANTE=4;	
	private static final int ACTUALIZACIONIDESTUDIANTE=5;	
	private static final int ACTUALIZACIONVIGENCIAESTUDIANTE=6;	
	private static final int INSERCIONESTUDIANTE=1;	
	private int cat;
	private int act;
	private int tmp;
	private Cursor cursor;
	private Integracion integracion;

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
	}

	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    rb=ResourceBundle.getBundle("integracion");
		cursor=new Cursor();
		cat=Integer.parseInt(request.getParameter("c")!=null?request.getParameter("c"):"0");
		act=Integer.parseInt(request.getParameter("a")!=null?request.getParameter("a"):"0");
		tmp=Integer.parseInt(request.getParameter("t")!=null?request.getParameter("t"):"0");
		if(cat==0 || act==0 || tmp==0){
			Logger.print("-1","Faltan parnmetros a la peticinn:",9,1,this.toString());
			return;
		}
//		if(cursor.abrir(1)){
//			Logger.print("-1","Conexion rechazada: "+cursor.getMensaje(),9,1,this.toString());
//			return;
//		}    
		  integracion=new Integracion(cursor);
			switch(cat){
				case INSTITUCION:
				  institucion(request);
				break;
				case SEDE:
					sede(request);
				break;
				case JORNADA:
				  jornada(request);
				break;
				case ESTUDIANTE:
				  estudiante(request);
				break;
			}
			cursor.cerrar();
	}

	public void estudiante(HttpServletRequest request) throws ServletException, IOException{
		try{
			switch(act){			
			case ACTUALIZACIONVIGENCIAESTUDIANTE:
				switch(tmp){
					case AFTER:						
						String params[]=getParametros(request);
						System.out.println("Actualizar vigencia de estudiante:"+params.length);
						if(params[0]==null || params[1]==null){
							Logger.print("-1","Faltan parnmetros al actualizar Vigencia de Estudiante:",9,1,this.toString());
							return;								
						}
						if(!integracion.actualizarVigenciaAlumno(params)){
						  Logger.print("-1","Error Actualizando Vigencia Est :"+params[5]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");												
					break;
				}
			break;			
				case ACTUALIZACIONNOMBREESTUDIANTE:
					switch(tmp){
						case AFTER:						
							String params[]=getParametros(request);
							System.out.println("Actualizar nombre de estudiante:"+params.length);
							//if(params!=null)System.out.println("fecha: "+params[9]);
							if(params[5]==null || params[6]==null){
								Logger.print("-1","Faltan parnmetros al actualizar Nombre de Estudiante:",9,1,this.toString());
								return;								
							}
							if(!integracion.actualizarNombreAlumno(params)){
							  Logger.print("-1","Error Actualizando Nombre a Est :"+params[5]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");												
						break;
					}
				break;			
				case ACTUALIZACIONIDESTUDIANTE:
					switch(tmp){
						case AFTER:
							System.out.println("Actualizar Id del Alumno");
							String params[]=getParametros(request);
							if(params[3]==null || params[4]==null || params[2]==null || params[5]==null){
								Logger.print("-1","Faltan parnmetros al actualizar Id de Estudiante:",9,1,this.toString());
								return;								
							}
							if(!integracion.actualizarIdAlumno(params)){
							  Logger.print("-1","Error Actualizando Id de Estudiante :"+params[3]+" a "+params[4]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");						
						break;
					}
				break;			
				case INSERCIONESTUDIANTE:
					switch(tmp){
						case AFTER:
							System.out.println("Insercion Estudiante");
							String parametros[]=getParametros(request);
							if(parametros[0]==null || parametros[1]==null || parametros[2]==null || parametros[3]==null || parametros[4]==null || parametros[8]==null || parametros[9]==null || parametros[10]==null || parametros[12]==null){
								Logger.print("-1","Faltan parnmetros al Insertar Estudiante:",9,1,this.toString());
								return;								
							}							
							if(!integracion.insertarAlumno(parametros)){
							  Logger.print("-1","Error Insertando Id de Estudiante :"+parametros[9]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");						
						break;
					}
				break;
			}
		}catch(NullPointerException e){
			Logger.print("-1","Faltan parnmetros a la peticinn:",9,1,this.toString());
			return;
		}
	}

	public void jornada(HttpServletRequest request) throws ServletException, IOException{
		try{
		switch(act){		
			case ACTUALIZACIONJORNADA:
				switch(tmp){
					case AFTER:
						System.out.println("ACTUALIZAR ESTUDIANTE");
						String parametros[]=getParametros(request);
						if(parametros[8]==null || parametros[9]==null){
							Logger.print("-1","Faltan parnmetros al Actualizar ubicacinn de Estudiante:",9,1,this.toString());
							return;
						}
						int nn=integracion.existeEstudiate(parametros[8],parametros[9]);
						if(nn==1){
							if(!integracion.actualizarAlumno(parametros)){
							  Logger.print("-1","Error Actualizando Est :"+parametros[9]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}
						}
						if(nn==0){
							if(!integracion.insertarAlumno(parametros)){
							  Logger.print("-1","Error Insertando Id de Estudiante :"+parametros[9]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}							
						}
						if(nn==-1){
							Logger.print("-1","Error Actualizando Id de Estudiante :"+parametros[9]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");					
					break;
				}
			break;
		}
		}catch(NullPointerException e){
			Logger.print("-1","Faltan parnmetros a la peticinn:",9,1,this.toString());
			return;
		}
	}

	public void sede(HttpServletRequest request) throws ServletException, IOException{
		//try{
		switch(act){		
			case ELIMINACIONSEDE:
				switch(tmp){
					case AFTER:
						System.out.println("eliminar sede ");				
						String parametros[]=getParametros(request);
						if(parametros[0]==null || parametros[1]==null || parametros[2]==null){
							Logger.print("-1","Faltan parnmetros al Eliminar sede:",9,1,this.toString());
							return;							
						}
						if(!integracion.eliminarSede(parametros)){
						  Logger.print("-1","Error eliminando sede: "+parametros[2]+" para Inst: "+parametros[0]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");						
					break;
				}
			break;
			case ACTUALIZACIONDANEINSTITUCION:
				switch(tmp){
					case AFTER:
						System.out.println("actualizar institucion a sede ");
						String parametros[]=getParametros(request);
						if(parametros[0]==null || parametros[1]==null || parametros[2]==null || parametros[3]==null){
							Logger.print("-1","Faltan parnmetros al Actualizar institucion a sede:",9,1,this.toString());
							return;						
						}
						System.out.println(""+parametros.length);
						if(!integracion.actualizarDaneInstitucion(parametros)){
						  Logger.print("-1","Error actualizando Institucion a sede : "+parametros[2]+" para Inst: "+parametros[0]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");						
					break;
				}
			break;
			case ACTUALIZACIONDANESEDE:
				switch(tmp){
					case AFTER:
						System.out.println("actualizar dane sede ");
						String params[]=getParametros(request);
						if(params[0]==null || params[1]==null || params[2]==null || params[3]==null){
							Logger.print("-1","Faltan parnmetros al Actualizar Dane de sede:",9,1,this.toString());
							return;						
						}
						if(!integracion.actualizarDaneSede(params)){
						  Logger.print("-1","Error actualizando DANE de sede : "+params[0]+" por DANE: "+params[1]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");						
					break;
				}
			break;
			case ACTUALIZACIONNOMBRESEDE:
				switch(tmp){
				case AFTER:
					System.out.println("actualizar nombre sede ");
					String params[]=getParametros(request);
					if(params[0]==null || params[1]==null || params[2]==null || params[3]==null){
						Logger.print("-1","Faltan parnmetros al Actualizar Nombre de sede:",9,1,this.toString());
						return;						
					}					
					if(!integracion.actualizarNombreSede(params)){
					  Logger.print("-1","Error actualizando nombre de sede a colegio: "+params[0]+" de sede : "+params[1]+"."+integracion.getMensaje(),9,1,this.toString());
						return;
					}
					System.out.println("-SIGES- accinn remota satisfactoria");						
				break;
			}
			break;
			case ACTUALIZACIONCONSECUTIVOSEDE:
				switch(tmp){
					case AFTER:
						System.out.println("actualizar CONSECUTIVO DE sede ");
						String params[]=getParametros(request);
						if(params[0]==null || params[1]==null || params[2]==null || params[3]==null){
							Logger.print("-1","Faltan parnmetros al Actualizar Codigo de sede:",9,1,this.toString());
							return;						
						}
						if(!integracion.actualizarConsecutivoSede(params)){
						  Logger.print("-1","Error actualizando consecutivo de sede a colegio: "+params[0]+" de sede : "+params[1]+"."+integracion.getMensaje(),9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");						
					break;
				}
				break;	
				case ACTUALIZACIONDEPTOSEDE:
					switch(tmp){
						case AFTER:
							System.out.println("actualizar Depto sede ");  
							String params[]=getParametros(request);
							if(params[0]==null || params[1]==null || params[2]==null || params[3]==null){
								Logger.print("-1","Faltan parnmetros al Actualizar Depto de sede:",9,1,this.toString());
								return;						
							}					
							if(!integracion.actualizarDeptoSede(params)){
							  Logger.print("-1","Error actualizando Depto de sede a colegio: "+params[0]+" de sede : "+params[1]+"."+integracion.getMensaje(),9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");						
						break;
					}
				break;
				case INSERCIONSEDE:
					System.out.println("Insertar Sede");
						switch(tmp){
							case AFTER:
								String params[]=getParametros(request);
								Sede i=new Sede();
								i.setSedcodins(params[0]);
								i.setSedcoddaneanterior(params[1]);
								i.setSedcodigo(params[2]);
								i.setSedresguardo(params[3]);
								i.setSeddireccion(params[4]);
								i.setSednombre(params[5]);
								//i.setSedEstado(params[6]);
								if(!integracion.insertarSede(i)){
								  Logger.print("-1","Error insertando Sede: "+i.getSedcoddaneanterior()+". Para Inst="+i.getSedcodins()+"("+integracion.getMensaje()+")",9,1,this.toString());
									return;
								}
								System.out.println("-SIGES- accinn remota satisfactoria");
							break;
						}			    
				break;				
			}
//		}catch(NullPointerException e){
//			Logger.print("-1","Faltan parnmetros a la peticinn:",9,1,this.toString());
//			return;
//		}
	}
	
	public void institucion(HttpServletRequest request) throws ServletException, IOException{
		try{	
			switch(act){
			case ACTUALIZACIONSECTOR:
				System.out.println("ACTUALIZAR SECTOR DE INSTITUCION");
				switch(tmp){
				case AFTER:
					String params[]=getParametros(request);
					if(params[0]==null || params[1]==null){
						Logger.print("-1","Faltan parnmetros al Actualizar Sector de institucion:",9,1,this.toString());
						return;					
					}
					if(!integracion.actualizarSectorInsitucion(params)){
					  Logger.print("-1","Error actualizando sector a colegio: "+params[0]+".("+integracion.getMensaje()+")",9,1,this.toString());
						return;
					}
					System.out.println("-SIGES- accinn remota satisfactoria");
				break;
			}			    
			break;				
			case INSERCIONINSTITUCION:
				System.out.println("Insertar Institucion");
					switch(tmp){
					case AFTER:
						String params[]=getParametros(request);
						Institucion i=new Institucion();
						i.setInscoddane(params[0]);
						i.setInscoddepto(params[1]);
						i.setInscodmun(params[2]);
						i.setInscodlocal(params[3]);
						i.setInsnombre(params[4]);
						i.setInsestado(params[5]);
						i.setInssector(params[6]);
						if(!integracion.insertarInsitucion(i)){
						  Logger.print("-1","Error insertando Colegio: "+i.getInscoddane()+".("+integracion.getMensaje()+")",9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");
					break;
				}			    
			break;
			case ACTUALIZACIONDANE:
				switch(tmp){
					case AFTER:
						System.out.println("Actualizar Dane Colegio");
						String params[]=getParametros(request);
						if(params[0]==null || params[1]==null){
							Logger.print("-1","Faltan parnmetros al Actualizar Dane de institucion:",9,1,this.toString());
							return;
						}
						if(!integracion.actualizarDaneInsitucion(params)){
						  Logger.print("-1","Error actualizando DANE: "+params[0]+" por DANE: "+params[1]+". ()",9,1,this.toString());
							return;
						}
						System.out.println("-SIGES- accinn remota satisfactoria");
					break;
				}
			break;					
			case ACTUALIZACIONNOMBRE:
					System.out.println("Actualizar Nombre Institucion");
					switch(tmp){
						case AFTER:
							String params[]=getParametros(request);
							if(params[0]==null || params[1]==null || params[2]==null){
								Logger.print("-1","Faltan parnmetros al Actualizar Nombre de institucion:",9,1,this.toString());
								return;
							}
							if(!integracion.actualizarNombreInsitucion(params)){
								Logger.print("-1","Error Actualizando Nombre de colegio: para DANE: "+params[0]+". ()",9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");
						break;
					}
				break;
			case ACTUALIZACIONDEPTO:
				switch(tmp){
					case AFTER:
						System.out.println("Actualizar Depto Institucion");
						String valores[]=getParametros(request);
						if(valores[0]!=null && valores[1]!=null){
							if(!integracion.actualizarDeptoInstitucion(valores)){
							  Logger.print("-1","Error Actualizando Depto de colegio: para DANE: "+valores[0]+" Con nuevo Depto: "+valores[1]+". ()",9,1,this.toString());
								return;
							}
							System.out.println("-SIGES- accinn remota satisfactoria");
						}
					break;
				}
			break;				
		}
		}catch(NullPointerException e){
			Logger.print("-1","Faltan parnmetros a la peticinn: "+e,9,1,this.toString());
			return;
		}			
	}
	
	public String[] getParametros(HttpServletRequest request) throws ServletException, IOException{
		boolean band=true;
		int n=0;
		while(band){
			if(request.getParameter("p"+(n+1))!=null) n++;
			else band=false;
		}
		if(n==0) return null;
		String as[]=new String[n];
		band=true;
		n=0;
		for(int i=0;i<as.length;i++){
			as[i]=(request.getParameter("p"+(i+1)).equals("null")?null:request.getParameter("p"+(i+1)));			
		}
		return as;
	}
}