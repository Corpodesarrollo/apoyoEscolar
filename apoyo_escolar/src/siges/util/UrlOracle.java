package siges.util;

import java.sql.*;
import java.net.*;
import java.util.ResourceBundle;

public class UrlOracle{
	private static ResourceBundle rb;	
	private static String dominio,logger;
	
	/**
	 * Al Insertar un nuevo estudiante 
	 */
	public static String insertAlumno(long a,String b,long c,String d,long e,long f,String g,String h,long i,String j,String k,String l,String m,String n,long o)throws  Exception{
	  if(e<=11){
		  init(); 
			String []consulta={
			"c="+URLEncoder.encode(rb.getString("estudiante"),"UTF-8") ,
			"a="+URLEncoder.encode(rb.getString("estudiante.insertar"),"UTF-8"),
			"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
			"p1="+URLEncoder.encode(""+a,"UTF-8"),
			"p2="+URLEncoder.encode(""+b,"UTF-8"),
			"p3="+URLEncoder.encode(""+c,"UTF-8"),
			"p4="+URLEncoder.encode(""+d,"UTF-8"),
			"p5="+URLEncoder.encode(""+e,"UTF-8"),
			"p6="+URLEncoder.encode(""+f,"UTF-8"),
			"p7="+URLEncoder.encode(""+g,"UTF-8"),
			"p8="+URLEncoder.encode(""+h,"UTF-8"),
			"p9="+URLEncoder.encode(""+i,"UTF-8"),
			"p10="+URLEncoder.encode(""+j,"UTF-8"),
			"p11="+URLEncoder.encode(""+k,"UTF-8"),
			"p12="+URLEncoder.encode(""+l,"UTF-8"),
			"p13="+URLEncoder.encode(""+m,"UTF-8"),
			"p14="+URLEncoder.encode(""+n,"UTF-8"),
			"p15="+URLEncoder.encode(""+o,"UTF-8"),
			};
			String error="Error Insertando Est:"+j+".";
			String []params={dominio,error,logger};
			ir(consulta,params);
	  }else log("Error Insertando Est:"+j+". Pertenece a un grado Superior a 11 y no se insertn");
		return "";
	}
	
	/*
	 * Al Actualizar la llave principal enla tabla alumno
	 */
	public static String updateIdAlumno(String depto,String mun,long tipo,String id0,String id1,long tipo0,long vig)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("estudiante"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("estudiante.actualizarId"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+depto,"UTF-8"),
		"p2="+URLEncoder.encode(""+mun,"UTF-8"),
		"p3="+URLEncoder.encode(""+tipo,"UTF-8"),
		"p4="+URLEncoder.encode(""+id0,"UTF-8"),
		"p5="+URLEncoder.encode(""+id1,"UTF-8"),
		"p6="+URLEncoder.encode(""+tipo0,"UTF-8"),
		"p7="+URLEncoder.encode(""+vig,"UTF-8")
		};
		String error="Error Actualizando Id de Est:"+id0+" a Nuevo Id:"+id1+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}
	
	/*
	 * Al Actualizar nombres de alumno
	 */
	public static String updateVigenciaAlumno(long tipo,String est,Timestamp d)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("estudiante"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("estudiante.actualizarVigencia"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+tipo,"UTF-8"),
		"p2="+URLEncoder.encode(""+est,"UTF-8"),
		"p3="+URLEncoder.encode(""+d,"UTF-8")
		};
		String error="Error Actualizando Vigencia de Est:"+est+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

	/*
	 * Al Actualizar nombres de alumno
	 */
	public static String updateNombreAlumno(String ape1,String ape2,String nom1,String nom2,long estado,String est,long tipo,long grado,String gene,Date d)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("estudiante"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("estudiante.actualizarNombre"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+ape1,"UTF-8"),
		"p2="+URLEncoder.encode(""+ape2,"UTF-8"),
		"p3="+URLEncoder.encode(""+nom1,"UTF-8"),
		"p4="+URLEncoder.encode(""+nom2,"UTF-8"),
		"p5="+URLEncoder.encode(""+estado,"UTF-8"),
		"p6="+URLEncoder.encode(""+est,"UTF-8"),
		"p7="+URLEncoder.encode(""+tipo,"UTF-8"),
		"p8="+URLEncoder.encode(""+grado,"UTF-8"),
		"p9="+URLEncoder.encode(""+gene,"UTF-8"),
		"p10="+URLEncoder.encode(""+d,"UTF-8")
		};
		String error="Error Actualizando Nombres de Est:"+est+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

	/*
	 * Al Actualizar la ubicacinn del alumno (desde inst a grupo)
	 */
	public static String updateAlumno(long a,String b,long c,String d,long e,long f,String g,String h,long i,String j,String k,String l,String m,String n,long o)throws  Exception{
	  //try{
		init();
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("jornada"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("jornada.actualizar"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+a,"UTF-8"),
		"p2="+URLEncoder.encode(""+b,"UTF-8"),
		"p3="+URLEncoder.encode(""+c,"UTF-8"),
		"p4="+URLEncoder.encode(""+d,"UTF-8"),
		"p5="+URLEncoder.encode(""+e,"UTF-8"),
		"p6="+URLEncoder.encode(""+f,"UTF-8"),
		"p7="+URLEncoder.encode(""+g,"UTF-8"),
		"p8="+URLEncoder.encode(""+h,"UTF-8"),
		"p9="+URLEncoder.encode(""+i,"UTF-8"),
		"p10="+URLEncoder.encode(""+j,"UTF-8"),
		"p11="+URLEncoder.encode(""+k,"UTF-8"),
		"p12="+URLEncoder.encode(""+l,"UTF-8"),
		"p13="+URLEncoder.encode(""+m,"UTF-8"),
		"p14="+URLEncoder.encode(""+n,"UTF-8"),
		"p15="+URLEncoder.encode(""+o,"UTF-8")};
		String error="Error Actualizando Est:"+j+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
//	  }catch(Exception es){
//	  	log("Error actualizando est:"+a+"/"+b+"/"+c+"/"+d+"/"+e+"/"+f+"/"+g+"/"+h+"/"+i+"/"+j+"/"+k+"/"+l+"/"+m+"/"+o+"/"+n);
//	  }
		return "";
	}

	/*
	 * Al actualizar institucion o sede de la tabla archivo3
	 */
	public static String updateSede(long oldinst,long newinst,String oldsede,String newsede,String oldnomsede)throws  Exception{
	  init();
	  if(oldinst==newinst){
			String []consulta={
			"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
			"a="+URLEncoder.encode(rb.getString("sede.actualizarConsecutivo"),"UTF-8"),
			"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
			"p1="+URLEncoder.encode(""+oldinst,"UTF-8"),
			"p2="+URLEncoder.encode(""+newinst,"UTF-8"),
			"p3="+URLEncoder.encode(""+oldsede,"UTF-8"),
			"p4="+URLEncoder.encode(""+newsede,"UTF-8"),
			"p5="+URLEncoder.encode(""+oldnomsede,"UTF-8")			
			};
			String error="Error actualizando Consecutivo de Sede: "+oldsede+" para Institucinn: "+oldinst+".";
			String []params={dominio,error,logger};
			ir(consulta,params);
	  }else{
			String []consulta={
			"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
			"a="+URLEncoder.encode(rb.getString("sede.actualizarDaneInstitucion"),"UTF-8"),
			"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
			"p1="+URLEncoder.encode(""+oldinst,"UTF-8"),
			"p2="+URLEncoder.encode(""+newinst,"UTF-8"),
			"p3="+URLEncoder.encode(""+oldsede,"UTF-8"),
			"p4="+URLEncoder.encode(""+newsede,"UTF-8"),
			"p5="+URLEncoder.encode(""+oldnomsede,"UTF-8")			
			};
			String error="Error actualizando Institucion a Sede: "+oldsede+" para Institucinn: "+oldinst+".";
			String []params={dominio,error,logger};
			ir(consulta,params);
	  }
		return "";
	}

	/*
	 * AL actualizar el codigo dane en la tabla archivo1
	 */
	public static String updateDane(long codigo0,long codigo1)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("institucion"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("institucion.actualizarDane"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo1,"UTF-8")
		};
		String error="Error actualizando DANE: "+codigo0+" por DANE: "+codigo1+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

	/*
	 * al actualizar el dane de la sede enla tabla archivo3
	 */
	public static String updateDaneSede(long codigo0,String codigo1,long codigo2,long codigo3)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("sede.actualizarDaneSede"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo1,"UTF-8"),
		"p3="+URLEncoder.encode(""+codigo2,"UTF-8"),
		"p4="+URLEncoder.encode(""+codigo3,"UTF-8")
		};
		String error="Error actualizando DANE de la Sede: "+codigo1+" por DANE: "+codigo2+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}
	
	/*
	 * Al actualizar la ubicacion de la institucion en archivo1
	 */
	public static String updateDeptoInstitucion(long codigo0,String depto,String mun,String loc)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("institucion"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("institucion.actualizarDepto"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+depto,"UTF-8"),
		"p3="+URLEncoder.encode(""+mun,"UTF-8"),
		"p4="+URLEncoder.encode(""+loc,"UTF-8")
		};
		String error="Error Actualizando Depto de institucinn: para DANE: "+codigo0+" Con nuevo Depto: "+depto+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}
	
	/*
	 * Al actualizar la ubicacinn de la sede
	 */
	public static String updateDeptoSede(long codigo0,long codigo1,String codigo2,String depto,String mun)throws  Exception{
		init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("sede.actualizarDepto"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo1,"UTF-8"),
		"p3="+URLEncoder.encode(""+codigo2,"UTF-8"),
		"p4="+URLEncoder.encode(""+depto,"UTF-8"),
		"p5="+URLEncoder.encode(""+mun,"UTF-8"),
		};
		String error="Error Actualizando Depto de sede: para DANE: "+codigo0+" Con nuevo Depto: "+depto+".";
		String []params={dominio,error,logger};				
		ir(consulta,params);
		return "";
	}

	/*
	 * al actualizar el nombre de la institucinn 
	 */
	public static String updateNombreInstitucion(long codigo0,String nombre,String estado,String sector,long anno)throws  Exception{
		init(); 
	  if(sector.equals("1") && anno==2005){
			String []consulta={
			"c="+URLEncoder.encode(rb.getString("institucion"),"UTF-8"),
			"a="+URLEncoder.encode(rb.getString("institucion.actualizarNombre"),"UTF-8"),
			"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
			"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
			"p2="+URLEncoder.encode(""+nombre,"UTF-8"),
			"p3="+URLEncoder.encode(""+estado,"UTF-8")
			};
			String error="Error Actualizando Nombre de institucinn: para DANE: "+codigo0+". ";
			String []params={dominio,error,logger};
			ir(consulta,params);
	  }else{
	  	log("Error Actualizando Nombre de institucinn: para DANE: "+codigo0+". Institucinn Inactiva, Sector Privado o vigencia menor");	
	  }
		return "";
	}

/*
 * Al actualizar el sector de la institucion
 */
	public static String updateSectorInstitucion(long codigo0,String estado)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("institucion"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("institucion.actualizarSector"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+estado,"UTF-8")
		};
		String error="Error Actualizando Sector de institucinn: para DANE: "+codigo0+". ";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

/*
 * Al actualizar al nombre de la sede
 */
	public static String updateNombreSede(long codigo0,long codigo1,String codigo2,String nombre0,String nombre)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("sede.actualizarNombre"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo0,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo1,"UTF-8"),
		"p3="+URLEncoder.encode(""+codigo2,"UTF-8"),
		"p4="+URLEncoder.encode(""+nombre0,"UTF-8"),
		"p5="+URLEncoder.encode(""+nombre,"UTF-8")
		};
		String error="Error Actualizando Nombre de sede: para DANE: "+codigo0+". sede :"+codigo1+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "La sentencia fue insertada ";
	}

	/*
	 * Al insertasr una sede
	 */
	public static String insertSede(long codigo,long codigo2,String cons,String depto,String mun,String nombre)throws  Exception{
		init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("sede.insertar"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo2,"UTF-8"),
		"p3="+URLEncoder.encode(""+cons,"UTF-8"),
		"p4="+URLEncoder.encode(""+depto,"UTF-8"),
		"p5="+URLEncoder.encode(""+mun,"UTF-8"),
		"p6="+URLEncoder.encode(""+nombre,"UTF-8"),
		};				
		String error="Error insertando sede: con DANE: "+codigo+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

	/*
	 * Al eliminar una sede 
	 */
	public static String deleteSede(long codigo,long codigo2,String cons)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("sede"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("sede.eliminar"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo,"UTF-8"),
		"p2="+URLEncoder.encode(""+codigo2,"UTF-8"),
		"p3="+URLEncoder.encode(""+cons,"UTF-8")
		};				
		String error="Error eliminando sede: con DANE: "+codigo+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}

	/*
	 * Al insertar una institucinn
	 */
	public static String insertInstitucion(long codigo,String depto,String mun,String local,String nombre,String estado,String sector)throws  Exception{
	  init(); 
		String []consulta={
		"c="+URLEncoder.encode(rb.getString("institucion"),"UTF-8"),
		"a="+URLEncoder.encode(rb.getString("institucion.insertar"),"UTF-8"),
		"t="+URLEncoder.encode(rb.getString("trigger.after"),"UTF-8"),
		"p1="+URLEncoder.encode(""+codigo,"UTF-8"),
		"p2="+URLEncoder.encode(""+depto,"UTF-8"),
		"p3="+URLEncoder.encode(""+mun,"UTF-8"),
		"p4="+URLEncoder.encode(""+local,"UTF-8"),
		"p5="+URLEncoder.encode(""+nombre,"UTF-8"),
		"p6="+URLEncoder.encode(""+estado,"UTF-8"),
		"p7="+URLEncoder.encode(""+sector,"UTF-8")
		};
		String error="Error insertando institucinn: con DANE: "+codigo+".";
		String []params={dominio,error,logger};
		ir(consulta,params);
		return "";
	}
	
	/*
	 * Envia la peticinn al hilo
	 */
	public static void ir(String[] a,String[] b){
		UrlHilo hilo=new UrlHilo(a,b);
		hilo.start();
	  return; 
	}

	/*
	 * Inserta un registro en el log de una excepcion ocurrida al internat conectarse
	 */
	public static void log(String mensaje){
		try{
			Connection conn=DriverManager.getConnection("jdbc:default:connection:");
			PreparedStatement stmt = conn.prepareStatement(logger);
			stmt.setString(1,"-1");
			stmt.setString(2,mensaje);
			stmt.setString(3,"9");
			stmt.setString(4,"1");
			stmt.setString(5,"siges.util.UrlOracle");
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException es){return;}
	}	
	
	/*
	 * Log de transacciones estatico
	 */
	public static void log(String mensaje,String lo){
		try{
			Connection conn=DriverManager.getConnection("jdbc:default:connection:");
			PreparedStatement stmt = conn.prepareStatement(lo);
			stmt.setString(1,"-1");
			stmt.setString(2,mensaje);
			stmt.setString(3,"9");
			stmt.setString(4,"1");
			stmt.setString(5,"siges.util.UrlOracle");
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException es){return;}
	}	

	/*
	 * Inicializa los parametros de envio de los datos
	 */
	public static void init(){
		if(dominio==null){
			try{
				rb=ResourceBundle.getBundle("remote");
				dominio=rb.getString("dominio");
				logger=rb.getString("logger");				
			}catch(Exception e){
				log("Archivo de propiedades no encontrado ("+e+")","INSERT INTO log_application (log_usuario, log_mensaje, log_nivel, log_tipo, log_clase) VALUES (substr(?,1,15),substr(?,1,150),?,?,substr(?,1,150))");
			}
		}
	}
}
