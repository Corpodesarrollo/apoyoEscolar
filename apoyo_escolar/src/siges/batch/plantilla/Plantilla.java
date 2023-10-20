package siges.batch.plantilla;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import siges.batch.plantilla.DAO.PlantillaBatchDAO;
import siges.dao.Cursor;
import siges.dao.OperacionesGenerales;
import siges.dao.Ruta;
import siges.dao.Ruta2;
import siges.exceptions.InternalErrorException;
import siges.io.Zip;
import siges.plantilla.Excel;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.dao.PlantillaDAO;
import siges.util.Properties;

/**
 *	Nombre:	<BR>
 *	Descripcinn:		<BR>
 *	Funciones de la pngina:		<BR>
 *	Entidades afectadas:		<BR>
 *	Fecha de modificacinn:	20/07/2005	<BR>
 *	@author Latined	<BR>
 *	@version v 1.0	<BR>
 */

public class Plantilla{
	private Cursor c;
	private PlantillaBatchDAO dao;
	private FiltroPlantilla d;
	private PlantillaDAO plantillaDAO;
	private ResourceBundle rb;
	private ResourceBundle rb2;
	private String ctx;
	private Collection[] col;
	private Collection list;
	private Object[] o;
	private String []ubicacion;
	private String []ubicacionPlantilla;
	private String [][]periodo;
	private Date f2;
	private long param;
	private String name;
	
	public Plantilla(Cursor cursor,FiltroPlantilla dato,String context,long params){
		c=cursor;
		d=dato;
		ctx=context;
		param=params;
		col=new Collection[4];
		ubicacion=new String[3];
		ubicacionPlantilla=new String[4];
		f2=new java.sql.Date(System.currentTimeMillis());
		rb=ResourceBundle.getBundle("batch"); 
		rb2=ResourceBundle.getBundle("siges.plantilla.bundle.plantilla");
		list = new ArrayList();
		plantillaDAO=new PlantillaDAO(c);
		dao =new PlantillaBatchDAO(c);
	}
	
	public void run(){
		//System.out.println(this.getName()+": Entra"); 
		procesar();
		//System.out.println(this.getName()+": Sale"); 
	}
	
	public void procesar(){
		//System.out.println(this.getName()+": Procesa");
		String[] arbol=null;
		String per;
		ResultSet rs = null;
		Connection cn=null;
		PreparedStatement pst=null;
		int posicion;
		try{
			cn=c.getConnection();
			//traer sede,jornada
			pst = cn.prepareStatement(rb.getString("dato.jornadas"));
			pst.clearParameters();
			posicion=1;
			pst.setLong(posicion++,Long.parseLong(d.getInstitucion()));
			rs = pst.executeQuery();
			String[][] lista=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
			//for(int tt=0;tt<lista.length;tt++){ System.out.println("sede-jornada="+lista[tt][0]+"//"+lista[tt][2]); }
			rs.close();
			pst.close();
			//traer metodologias
			pst = cn.prepareStatement(rb.getString("dato.metodologias"));
			pst.clearParameters();
			posicion=1;
			pst.setLong(posicion++,Long.parseLong(d.getInstitucion()));
			rs = pst.executeQuery();
			String[][] metod=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
			//for(int tt=0;tt<metod.length;tt++){ System.out.println("metod="+metod[tt][0]+"//"+metod[tt][1]); }
			rs.close();
			pst.close();
			cn.close();
			//parte donde se parte en logro asig y area des /
			//System.out.println("Desc/Area");
			//CUIDADO, EL PnRMER METODO DE LOS QUE VIENEN BORRA EL CONTENIDO DE LA CARPETA
			asignarDescArea(lista,metod);
			if(!d.getPeriodo().equals("5")){
				//System.out.println("Logro/Asig");
				asignarLogroAsig(lista,metod);
				//System.out.println("Preescolar");
				asignarPreescolar(lista,metod);
			}
		}catch(Exception e){
		    dao.finalizarInst(d.getInstitucion(),d.getPeriodo(),2,0,e.toString());
		    //System.out.println("Error process Plantilla:");
		    e.printStackTrace(); //throw new NullPointerException();
		}
		finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}		   
		}
	}
	
	public String ponerZip(Collection list,String grado,int tipo){
		Zip zip=new Zip();
		String path;
		String archivo;
		String relativo;
		String ruta;
		int zise;		
		switch(tipo){
		case Properties.PLANTILLALOGROASIG:		
			zise=100000;
			if(zip.ponerZip(ubicacion[2],ubicacion[0]+"_Met_"+d.getMetodologia()+"_Gra_"+grado+"_Per_"+d.getPeriodo()+".zip",zise,list)){
				return ubicacion[1]+ubicacion[0];
			}
			break;
		case Properties.PLANTILLAAREADESC:
			zise=100000;
			if(zip.ponerZip(ubicacion[2],ubicacion[0]+"_Met_"+d.getMetodologia()+"_Gra_"+grado+"_Per_"+d.getPeriodo()+".zip",zise,list)){
				return ubicacion[1]+ubicacion[0];
			}
			break;
		case Properties.PLANTILLAPREE:		
			zise=100000;
			if(zip.ponerZip(ubicacion[2],ubicacion[0]+"_Met_"+d.getMetodologia()+"_Gra_"+grado+"_Per_"+d.getPeriodo()+".zip",zise,list)){
				return ubicacion[1]+ubicacion[0];
			}
			break;
		}			
		return "--";
	}
	
	
	public void getUbicacionZip(Date t,int tipo){
		String path;
		String archivo=null;
		String relativo=null;
		String ruta=null;
		switch(tipo){
		case Properties.PLANTILLALOGROASIG:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			archivo="Asignatura["+t.toString().replace(':','-').replace('.','-').replace(' ','_')+"]";
			relativo=Ruta.getRelativo(ctx,path);
			ruta=Ruta.get(ctx,path);
			ubicacion[0]=archivo;
			ubicacion[1]=relativo;
			ubicacion[2]=ruta;
			break;
		case Properties.PLANTILLAAREADESC:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			archivo="Area["+t.toString().replace(':','-').replace('.','-').replace(' ','_')+"]";
			relativo=Ruta.getRelativo(ctx,path);
			ruta=Ruta.get(ctx,path);
			ubicacion[0]=archivo;
			ubicacion[1]=relativo;
			ubicacion[2]=ruta;
			break;
		case Properties.PLANTILLAPREE:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			archivo="Preescolar["+t.toString().replace(':','-').replace('.','-').replace(' ','_')+"]";
			relativo=Ruta.getRelativo(ctx,path);
			ruta=Ruta.get(ctx,path);
			ubicacion[0]=archivo;
			ubicacion[1]=relativo;
			ubicacion[2]=ruta;
			break;
		}
	}
	
	public void getUbicacionPlantilla(int tipo){
		String path;
		String archivo=null;
		String relativo=null;
		int i=0;
		switch(tipo){
		case Properties.PLANTILLALOGROASIG:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			relativo=Ruta.getRelativo(ctx,path);
			ubicacionPlantilla[i++]=Ruta2.get(ctx,rb2.getString("asignatura.PathPlantilla"));//path de la plantilla		
			ubicacionPlantilla[i++]=rb2.getString("asignatura.Plantilla");//nombre de la plantilla		
			ubicacionPlantilla[i++]=Ruta.get(ctx,path);//path del nuevo archivo		
			ubicacionPlantilla[i++]=archivo;//nombre del nuevo archivo
			break;
		case Properties.PLANTILLAAREADESC:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			relativo=Ruta.getRelativo(ctx,path);
			ubicacionPlantilla[i++]=Ruta2.get(ctx,rb2.getString("area.PathPlantilla"));//path de la plantilla		
			ubicacionPlantilla[i++]=rb2.getString("area.Plantilla");//nombre de la plantilla		
			ubicacionPlantilla[i++]=Ruta.get(ctx,path);//path del nuevo archivo		
			ubicacionPlantilla[i++]=archivo;//nombre del nuevo archivo
			break;
		case Properties.PLANTILLAPREE:		
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			relativo=Ruta.getRelativo(ctx,path);
			ubicacionPlantilla[i++]=Ruta2.get(ctx,rb2.getString("preescolar.PathPlantilla"));//path de la plantilla		
			ubicacionPlantilla[i++]=rb2.getString("preescolar.Plantilla");//nombre de la plantilla		
			ubicacionPlantilla[i++]=Ruta.get(ctx,path);//path del nuevo archivo		
			ubicacionPlantilla[i++]=archivo;//nombre del nuevo archivo
			break;
		}
	}
	
	public void borrarPath(){
		String path;
		String archivo=null;
		String relativo=null;
		int i=0;
			path=rb.getString("path.raiz")+"."+d.getInstitucion()+"."+d.getSede()+"."+d.getJornada();
			relativo=Ruta.get(ctx,path);
    try{
		File f=new File(relativo);
    if(f.exists()){ 
    	FileUtils.cleanDirectory(f);
    }
    }catch(Exception e){System.out.println("Error borrando archivos;"+relativo);}
	}
	
	public String getNombreLogroAsig(String ini,String sed,String jor,String meto,String grad,String gru,String asig,String per){
		String jo=formatear((jor.length()>10?meto.substring(0,10):jor));
		String met=formatear((meto.length()>21?meto.substring(0,21):meto));
		String gra=formatear((grad.length()>8?grad.substring(0,8):grad));
		String gr=formatear((gru.length()>8?gru.substring(0,8):gru));
		String asi=formatear(asig);
		String pe=formatear((per.length()>10?per.substring(0,10):per));
		String fe=f2.toString().replace(':','-').replace('.','-');
		return (ini+"_Sed_"+sed+"_Jor_"+jo+"_Met_"+met+"_Gra_"+gra+"_Gru_"+gr+"_Asi_"+asi+"_Per_"+pe+"_("+fe+")["+(int)(Math.random()*1000)+"].xls");	
	}
	
	public String getNombrePreescolar(String ini,String sed,String jor,String meto,String grad,String gru,String per){
		String jo=formatear((jor.length()>10?meto.substring(0,10):jor));
		String met=formatear((meto.length()>21?meto.substring(0,21):meto));
		String gra=formatear((grad.length()>8?grad.substring(0,8):grad));
		String gr=formatear((gru.length()>8?gru.substring(0,8):gru));
		String pe=formatear((per.length()>10?per.substring(0,10):per));
		String fe=f2.toString().replace(':','-').replace('.','-');
		return (ini+"_Sed_"+sed+"_Jor_"+jo+"_Met_"+met+"_Gra_"+gra+"_Gru_"+gr+"_Per_"+pe+"_("+fe+").xls");	
	}
	
	public String getNombreDescAre(String ini,String sed,String jor,String meto,String grad,String gru,String area,String per){
		String jo=formatear((jor.length()>10?meto.substring(0,10):jor));
		String met=formatear((meto.length()>21?meto.substring(0,21):meto));
		String gra=formatear((grad.length()>8?grad.substring(0,8):grad));
		String gr=formatear((gru.length()>8?gru.substring(0,8):gru));
		String are=formatear(area);
		String pe=formatear((per.length()>10?per.substring(0,10):per));
		String fe=f2.toString().replace(':','-').replace('.','-');
		return (ini+"_Sed_"+sed+"_Jor_"+jo+"_Met_"+met+"_Gra_"+gra+"_Gru_"+gr+"_Are_"+are+"_Per_"+pe+"_("+fe+")["+(int)(Math.random()*1000)+"].xls");	
	}
	
	public String formatear(String a){
		return (a.replace(' ','_').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','n').replace('n','N').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','c').replace(':','_').replace('.','_').replace('/','_').replace('\\','_'));
	}
	
	public void asignarLogroAsig(String[][] lista,String[][]metod){
		ResultSet rs = null;
		Connection cn=null;
		PreparedStatement pst=null;
		Collection files=new ArrayList();
		long institucion=Long.parseLong(d.getInstitucion());
		long jer=-1;
		int posicion,tipo;
		int m,i,f;
		try{
			long vigencia=Long.parseLong(plantillaDAO.getVigencia());
				//iterar por sede jornada
				for(int it=0;it<lista.length;it++){
					d.setSede(lista[it][0]);
					d.setSede_(lista[it][1]);
					d.setJornada(lista[it][2]);
					d.setJornada_(lista[it][3]);
					//iterar por metodologias
					for(int im=0;im<metod.length;im++){
						files=new ArrayList();
						d.setMetodologia(metod[im][0]);   
						d.setMetodologia_(metod[im][1]);
						cn=c.getConnection();
						//System.out.println(this.getName()+" sede:"+lista[it][0]+" jor:"+lista[it][2]+" met:"+d.getMetodologia());
						//traer grupos
						pst = cn.prepareStatement(rb.getString("dato.GradoGrupo"));
						pst.clearParameters();
						posicion=1;
						pst.setLong(posicion++,institucion);
						pst.setLong(posicion++,Long.parseLong(d.getSede()));
						pst.setLong(posicion++,Long.parseLong(d.getJornada()));
						pst.setLong(posicion++,Long.parseLong(d.getMetodologia()));
						rs = pst.executeQuery();
						String[][] grupos=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
						rs.close();
						pst.close();
						//traer asignaturas
						pst = cn.prepareStatement(rb.getString("dato.GradoAsig"));
						pst.clearParameters();
						posicion=1;
						pst.setLong(posicion++,institucion);
						pst.setLong(posicion++,Long.parseLong(d.getMetodologia()));
						pst.setLong(posicion++,vigencia);
						rs = pst.executeQuery();
						String asignaturas[][]=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
						rs.close();
						pst.close();
						cn.close();
						//construir lista de parametros
						if(grupos!=null && asignaturas!=null){			  
							int r=0;
							for(int p=0;p<grupos.length;p++){
								for(int q=0;q<asignaturas.length;q++){
									if(grupos[p][0].equals(asignaturas[q][0])) r++;
								}
							}
							String [][]params=new String[r][4];
							String [][]params_=new String[r][4];
							r=0;
							for(int p=0;p<grupos.length;p++){
								for(int q=0;q<asignaturas.length;q++){
									if(grupos[p][0].equals(asignaturas[q][0])){
										params[r][0]=d.getMetodologia();
										params_[r][0]=d.getMetodologia_();
										params[r][1]=grupos[p][0];
										params_[r][1]=grupos[p][1];
										params[r][2]=grupos[p][2];
										params_[r][2]=grupos[p][3];
										params[r][3]=asignaturas[q][1];
										params_[r++][3]=asignaturas[q][2];
									}
								}
							}
							boolean band,band2;
							//ASIGNATURAS/
							tipo=Properties.PLANTILLALOGROASIG;
							getUbicacionZip(f2,tipo);
							getUbicacionPlantilla(tipo);
							//FOR GENERAL PARA TODOS
						  int yyy=0;
							col=new Collection[7];
							d.setOrden("0");
							for(int p=0;p<params.length;p++){
								band=band2=true;		    
								d.setMetodologia(params[p][0]);
								d.setMetodologia_(params_[p][0]);
								d.setGrado(params[p][1]);
								d.setGrado_(params_[p][1]);
								d.setGrupo(params[p][2]);
								d.setGrupo_(params_[p][2]);
								d.setAsignatura(params[p][3]);
								d.setAsignatura_(params_[p][3]);
								//System.out.println("GR_"+d.getGrado()+" GP_"+d.getGrupo()+" AS_"+d.getAsignatura()+"_"+d.getAsignatura_());
						    if(p==0){
						    	band=true; band2=true;//NOTAS
						    	//col[1]=plantillaDAO.getEscala(2);

								
								/**  MODIFICADO Escala asignatura  20-03-10
								 * */
//								col[1] = plantillaDAO.getEscala(2);
								col[1] = plantillaDAO.getEscalaNivelEval(d);
								
								/** FIN
								 * **/
						    	col[5]=plantillaDAO.getEscala(1);
						    }
								if(p!=0 && !params[p][1].equals(params[p-1][1])){
									//System.out.println("Cambio de grado="+params[p][1]+"_"+params[p-1][1]);
									if(files.size()!=0){
										ponerZip(files,params[p-1][1],tipo);
										files=new ArrayList();
									}
								}
					      if(p!=0 && params[p][0].equals(params[p-1][0]) && params[p][1].equals(params[p-1][1]) && params[p][2].equals(params[p-1][2])){
					        band=false;
					      } 
						    if(band){
						    	col[0]=plantillaDAO.getEstudiantes(d);
						    	//JERARQUIA DEL GRUPO AL QUE PERTENECE
						    	d.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
						    }
								//REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
						    d.setCerrar(plantillaDAO.getEstadoNotasAsig(d));
								//NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE ASIGNATURAS
								Collection Coltemp=plantillaDAO.getNotasAsig(d);
								if(Coltemp!=null){
									col[2]=Coltemp;
									//HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
									//col[3]=Coltemp[1];
									/*para la cuestion de logros*/
									col[4]=plantillaDAO.getLogro(d);
									col[6]=plantillaDAO.getNotasLogro(d);
									if(col[1].size()!=0 && col[0].size()!=0){
										files.add(plantillaLogroAsigZip());
										yyy++;
									}
								}		
							}
							if(files.size()!=0){
								ponerZip(files,d.getGrado(),tipo);
							}
						}
						else{
							//System.out.println("NO hay grupos o asignaturas");  
						}
					}//for de metodologias
				}//for de iteracion de sede jornada
		}catch(Exception e){
			System.out.println("Error Plantilla Batch Logro/Asig:"+e);
			e.printStackTrace(); 
			//throw new NullPointerException();
		}
		finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}		   
		}
	}

	public String plantillaLogroAsigZip(){
		Excel excel=new Excel();
		String relativo="";
		String path="",archivo="";
		int i=0;
		String[] config=new String[4];
		/*CONFIGURACION DE ARCHIVOS*/
				d.setTipoPlantilla(""+Properties.PLANTILLALOGROASIG);
				d.setNivelLogro("1");
				/*UBICACIONES DE ARCHIVOS*/
				config=ubicacionPlantilla;				
				config[3]=getNombreLogroAsig("EvaluacionAsignatura",d.getSede(),d.getJornada(),d.getMetodologia(),d.getGrado_(),d.getGrupo_(),d.getAsignatura_(),d.getPeriodo_());//nombre del nuevo archivo
				if(!excel.plantillaLogroAsignatura(config,col,d)){
					//setMensaje(excel.getMensaje());
					return "--";
				}
		return config[2]+config[3];
	}
	
	public void asignarDescArea(String[][] lista,String[][]metod){
		ResultSet rs = null;
		Connection cn=null;
		PreparedStatement pst=null;
		Collection files=new ArrayList();
		long jer=-1;
		int posicion,tipo;
		int m,i,f;
		try{
			long vigencia=Long.parseLong(plantillaDAO.getVigencia());
				//iterar por sede jornada
				for(int it=0;it<lista.length;it++){					
					d.setSede(lista[it][0]);
					d.setSede_(lista[it][1]);
					d.setJornada(lista[it][2]);
					d.setJornada_(lista[it][3]);
					//borrar todo lo que este en la carpeta de la inst-sede-jor a generar
					borrarPath();
					//iterar por metodologias					
					for(int im=0;im<metod.length;im++){
						files=new ArrayList();
						d.setMetodologia(metod[im][0]);   
						d.setMetodologia_(metod[im][1]);
						cn=c.getConnection();
						//System.out.println(this.getName()+" sede:"+lista[it][0]+" jor:"+lista[it][2]+" met:"+d.getMetodologia());
						//traer grupos
						pst = cn.prepareStatement(rb.getString("dato.GradoGrupo"));
						pst.clearParameters();
						posicion=1;
						pst.setLong(posicion++,Long.parseLong(d.getInstitucion()));
						pst.setLong(posicion++,Long.parseLong(d.getSede()));
						pst.setLong(posicion++,Long.parseLong(d.getJornada()));
						pst.setLong(posicion++,Long.parseLong(d.getMetodologia()));
						rs = pst.executeQuery();
						String[][] grupos=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
						rs.close();
						pst.close();
						//traer asignaturas
						pst = cn.prepareStatement(rb.getString("dato.GradoArea"));
						pst.clearParameters();
						posicion=1;
						pst.setLong(posicion++,Long.parseLong(d.getInstitucion()));
						pst.setLong(posicion++,Long.parseLong(d.getMetodologia()));
						pst.setLong(posicion++,vigencia);
						rs = pst.executeQuery();
						String areas[][]=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
						rs.close();
						pst.close();
						cn.close();
						//construir lista de parametros
						if(grupos!=null && areas!=null){
							int r=0;
							for(int p=0;p<grupos.length;p++){
								for(int q=0;q<areas.length;q++){
									if(grupos[p][0].equals(areas[q][0])) r++;
								}
							}
							String [][]params=new String[r][4];
							String [][]params_=new String[r][4];
							r=0;
							for(int p=0;p<grupos.length;p++){							    
								for(int q=0;q<areas.length;q++){
									if(grupos[p][0].equals(areas[q][0])){
										params[r][0]=d.getMetodologia();
										params_[r][0]=d.getMetodologia_();
										params[r][1]=grupos[p][0];
										params_[r][1]=grupos[p][1];
										params[r][2]=grupos[p][2];
										params_[r][2]=grupos[p][3];
										params[r][3]=areas[q][1];
										params_[r++][3]=areas[q][2];
									}
								}
							}
							boolean band,band2;
							//AREAS/				  
							tipo=Properties.PLANTILLAAREADESC;
							getUbicacionZip(f2,tipo);
							getUbicacionPlantilla(tipo);
							//FOR GENERAL PARA TODOS
							d.setOrden("0");
						  int yyy=0;
						  col=new Collection[6];
							for(int p=0;p<params.length;p++){
								band=band2=true;		    
								d.setMetodologia(params[p][0]);
								d.setMetodologia_(params_[p][0]);
								d.setGrado(params[p][1]);
								d.setGrado_(params_[p][1]);
								d.setGrupo(params[p][2]);
								d.setGrupo_(params_[p][2]);
								d.setArea(params[p][3]);
								d.setArea_(params_[p][3]);
								//System.out.println("GR_"+d.getGrado()+" GP_"+d.getGrupo()+" AR_"+d.getArea()+"_"+d.getArea_());
								if(p==0){ 
									band=true; band2=true;
					  			//col[1]=plantillaDAO.getEscala(2);
					  			
									/**  MODIFICADO Escala asignatura  17-04-10
									 * */
//									col[1] = plantillaDAO.getEscala(2);
									col[1] = plantillaDAO.getEscalaNivelEval(d);
									
									/** FIN
									 * **/
								}
								if(p!=0 && !params[p][1].equals(params[p-1][1])){
									if(files.size()!=0){
										ponerZip(files,params[p-1][1],tipo);
										files=new ArrayList();
									}
								}
								if(p!=0 && params[p][0].equals(params[p-1][0]) && params[p][1].equals(params[p-1][1]) && params[p][2].equals(params[p-1][2])){
									band=false;
								}
						    if(band){
						    	col[0]=plantillaDAO.getEstudiantes(d);
					  			//JERARQUIA DEL GRUPO AL QUE PERTENECE
						    	d.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));
						    }
				  			//REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
						    d.setCerrar(plantillaDAO.getEstadoNotasArea(d));
				  			//NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
								Collection Coltemp=plantillaDAO.getNotasArea(d);
								if(Coltemp!=null){
									col[2]=Coltemp;			
									//HORAS DE AUSENCIA JUSTIFICADAY NO JUSTIFICADA
									//col[3]=Coltemp[1];
									/*Para la cuestion de descriptor*/
									col[4]=plantillaDAO.getDescriptor(d);//LISTA DE DESCRIPTORES
									col[5]=plantillaDAO.getNotasDesc(d);//NOTAS DE DESCRIPTORES
									if(col[1].size()!=0 && col[0].size()!=0){
										  files.add(plantillaDescAreaZip());
									}
								}	
							}//for general de lista
							if(files.size()!=0){
								ponerZip(files,d.getGrado(),tipo);
							}					
						}//si hay grupos asig
						else{
							//System.out.println("NO hay grupos o areas");  
						}
					}//for de metodologias
				}//for de iteracion de sede jornada
		}catch(Exception e){
			System.out.println("Error Plantilla Batch Desc/Area:"+e);
			e.printStackTrace();
			//throw new NullPointerException();
		}
		finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}		   
		}
	}
	
	public String plantillaDescAreaZip(){ 
		Excel excel=new Excel();
		String relativo=null;
		int i=0;
		String[] config=new String[4];
		//CONFIGURACION DE ARCHIVOS
		String path;
		String archivo;
				d.setTipoPlantilla(""+Properties.PLANTILLAAREADESC);
				d.setNivelLogro("1");
				//UBICACION ES DE ARCHIVOS
				config=ubicacionPlantilla;
				config[3]=getNombreDescAre("EvaluacionArea",d.getSede(),d.getJornada(),d.getMetodologia(),d.getGrado_(),d.getGrupo_(),d.getArea_(),d.getPeriodo_());
	try{
				if(!excel.plantillaAreaDescriptor(config,col,d)){
					//setMensaje(excel.getMensaje());
					return "--";
				}
	}catch (Exception e) {
		e.printStackTrace();
		//System.out.println("PLANTILLA EVAL" + e.getMessage() );
		e.printStackTrace();
	}
		return config[2]+config[3];
	}
	
	public void asignarPreescolar(String[][] lista,String[][]metod){
		ResultSet rs = null;
		Connection cn=null;
		PreparedStatement pst=null;
		Collection files=new ArrayList();
		long jer=-1;
		int posicion,tipo;
		int m,i,f;
		try{
				//iterar por sede jornada
				for(int it=0;it<lista.length;it++){
					d.setSede(lista[it][0]);
					d.setSede_(lista[it][1]);
					d.setJornada(lista[it][2]);
					d.setJornada_(lista[it][3]);
					//iterar por metodologias
					for(int im=0;im<metod.length;im++){
						files=new ArrayList();
						d.setMetodologia(metod[im][0]);   
						d.setMetodologia_(metod[im][1]);
						cn=c.getConnection();
						//System.out.println(this.getName()+" sede:"+lista[it][0]+" jor:"+lista[it][2]+" met:"+d.getMetodologia());
						//traer grupos
						pst = cn.prepareStatement(rb.getString("dato.GradoGrupoPre"));
						pst.clearParameters();
						posicion=1;
						pst.setLong(posicion++,Long.parseLong(d.getInstitucion()));
						pst.setLong(posicion++,Long.parseLong(d.getSede()));
						pst.setLong(posicion++,Long.parseLong(d.getJornada()));
						pst.setLong(posicion++,Long.parseLong(d.getMetodologia()));
						rs = pst.executeQuery();
						String[][] grupos=plantillaDAO.getFiltroMatriz(plantillaDAO.getCollection(rs));
						rs.close();
						pst.close();
						cn.close();
						//construir lista de parametros
						if(grupos!=null){			  
							int r=0;
							String [][]params=new String[grupos.length][3];
							String [][]params_=new String[grupos.length][3];
							for(int p=0;p<grupos.length;p++){
								params[p][0]=d.getMetodologia();
								params_[p][0]=d.getMetodologia_();
								params[p][1]=grupos[p][0];
								params_[p][1]=grupos[p][1];
								params[p][2]=grupos[p][2];
								params_[p][2]=grupos[p][3];
							}
							boolean band,band2;
							//PRE/
							tipo=Properties.PLANTILLAPREE;
							getUbicacionZip(f2,tipo);
							getUbicacionPlantilla(tipo);
							//sin ESCALA 
							//FOR GENERAL PARA TODOS
							d.setOrden("0");
							for(int p=0;p<params.length;p++){
								band=band2=true;		    
								d.setMetodologia(params[p][0]);
								d.setMetodologia_(params_[p][0]);
								d.setGrado(params[p][1]);
								d.setGrado_(params_[p][1]);
								d.setGrupo(params[p][2]);
								d.setGrupo_(params_[p][2]);
								if(p==0){ band=true;}
								if(p!=0 && !params[p][1].equals(params[p-1][1])){
									if(files.size()!=0){
										ponerZip(files,params[p-1][1],tipo);
										files=new ArrayList();
									}
								}
								if(p!=0 && params[p][0].equals(params[p-1][0]) && params[p][1].equals(params[p-1][1]) && params[p][2].equals(params[p-1][2])){
									band=false;
								} 
						    //ESTUDIANTES
						    if(band){
									col[0]=plantillaDAO.getEstudiantes(d);
							  	//JERARQUIA DEL GRUPO AL QUE PERTENECE
									d.setJerarquiagrupo(plantillaDAO.getJerarquiaGrupo(col[0]));					
						    }
					  			//REVISAR SI ESTA O NO ABIERTA PARA EDICION DEL GRUPO
					  			d.setCerrar("0");
					  			//NOTAS QUE YA ESTAN EN LA TABLA DE EVALUACION DE AREA
					  			col[2]=plantillaDAO.getNotasPree(d);
					  			if(col[0].size()!=0){
									  files.add(plantillaPreescolarZip());
									}
							}//for general de lista
							if(files.size()!=0){
								ponerZip(files,d.getGrado(),tipo);
							}
						}//si hay grupos asig
						else{
							//System.out.println("NO hay grupos o asignaturas");  
						}
					}//for de metodologias
				}//for de iteracion de sede jornada
		}catch(Exception e){
			System.out.println("Error Plantilla Batch Pree:"+e);
			e.printStackTrace(); 
			//throw new NullPointerException();
		}
		finally{
			try{
				OperacionesGenerales.closeResultSet(rs);
				OperacionesGenerales.closeStatement(pst);
				OperacionesGenerales.closeConnection(cn);
			}catch(InternalErrorException inte){}		   
		}
	}
	
	public String plantillaPreescolarZip(){ 
		Excel excel=new Excel();
		String relativo=null;
		int i=0;
		String[] config=new String[4];
		/*ENCABEZADO DE LA PLANTILLA*/
		d.setTipoPlantilla(""+Properties.PLANTILLAPREE);
		d.setNivelLogro("1");
		/*UBICACION ES DE ARCHIVOS*/		
		config=ubicacionPlantilla;
		config[3]=getNombrePreescolar("EvaluacionPreescolar",d.getSede(),d.getJornada(),d.getMetodologia(),d.getGrado_(),d.getGrupo_(),d.getPeriodo_());
		/*if(!excel.plantillaPreescolar(config,col,d)){
			//setMensaje(excel.getMensaje());
			return "--";
		}*/
		return config[2]+config[3];
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name!=null?name:"";
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
