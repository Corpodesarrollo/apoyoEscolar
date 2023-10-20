package articulacion.plantillaArticulacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;


import siges.dao.Util;
import siges.plantilla.beans.FiltroPlantilla;
import siges.plantilla.beans.Logros;
import siges.util.Recursos;
import siges.util.beans.*;

/** siges.plantilla<br>
 * Funcinn: Clase que genera las plantillas fisicamente en disco a partir de las plantillas y de los datos proporcionados por la clase que lo instancia   
 * <br>
 */
public class Excel{
	private String pathBajar;
	private String pathTemporal;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet,sheet2;
	private	HSSFRow row,row2;
	private	HSSFCell cell,cell2;
	private HSSFWorkbook workbookNuevo;
	private HSSFSheet sheetNuevo;
	private	HSSFRow rowNuevo;
	private	HSSFCell cellNuevo;
	private String mensaje;
	private Iterator iterator,iterator2,iterator3,iterator4;
	private Object[] o=null;
	private Object[] o2=null;
	private boolean error;
	private Util util;
	private String jerarquia;
	
	/**
	 * 
	 */
	public Excel(){
		mensaje="";
		error=true;
		o=new Object[2];
	}
	
	/**
	 * @param u
	 */
	public Excel(Util u){
		this.util=u;
		mensaje="";
  	error=true;
		o=new Object[2];
  }
	
	/**
	 * @param config
	 * @param c
	 * @param filtro
	 * @return<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean plantillaPreescolar(String[] config,Collection[] c,FiltroPlantilla filtro){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		FileInputStream input;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			String tipo[]={"","Corporal","Comunicativa","Cognitiva","ntica","Estntica","Comportamiento"};
			if(filtro.getGrado().equals("30") || filtro.getGrado().equals("31")){
				tipo[1]="Comportamiento Social";
				tipo[2]="Comunicativa";
				tipo[3]="Cientnfica";
				tipo[4]="Convivencia";
				tipo[5]="Lndica";
				tipo[6]="Comportamiento";
			}
			for(int y=1;y<tipo.length;y++){
				sheet = workbook.getSheetAt(y-1);//OBTENER HOJA DE PLANTILLA
				filtro.setArea(""+y);
				filtro.setArea_(tipo[y]);
				setEncabezadoPreescolar(filtro);
				setEstudiantesPreescolar(c[0],0);
				setNotasPreescolar(c[0],c[2],""+(y));
			}
			sheet = workbook.getSheetAt(6);//OBTENER HOJA OCULATA
			setEncabezadoPreescolarOculto(filtro);
			setEstudiantesPreescolar(c[0],1);
			//poner archivo
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			FileOutputStream fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();//CERRAR
			fileOut.close();
			input.close();
    }catch(Exception e){
    	setMensaje(e.toString());
    	System.out.println("Error plantilla Pree:"+e.toString());
    	return false;
    }
		return true;
	}

	/**
	 * @param config
	 * @param c
	 * @param filtro
	 * @return<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean plantillaLogroAsignatura(String[] config,Collection[] c,FiltroPlantilla filtro){
		//0=Estudiantes
		//1=Escala asignatura
		//2=nOTAS DE asignatura
		//3=ausencias
		//4=Logros
		//5=Escala de logros
		//6=Notas de logros
		String jus="",nojus="",mot="",por="";
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=2;
		int numCelda=-2;
		HSSFSheet sheet2;
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			//hoja de escalas
			sheet = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
			setEscalaLogroAsignatura(c);
			filtro.setJerarquiaLogro(jerarquia);
			//hoja de plantilla
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE PLANTILLA
			setEncabezadoLogroAsignatura(filtro);
			setEstudiantesLogroAsignatura(c[0],0);
			setLogros(c[4],1);
			setNotasAsignatura(c[0],c[3],c[1],c[2]);
			setNotasLogro(c[0],c[4],c[5],c[6]);
			//hoja oculta 
			sheet = workbook.getSheetAt(2);//OBTENER HOJA OCULTA
			setEncabezadoLogroAsignaturaOculto(filtro);			
			setEstudiantesLogroAsignatura(c[0],1);
			setLogros(c[4],1);
			setLogrosOcultos(c[4],0);
			//poner el archivo
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();//CERRAR
			fileOut.close();
			input.close();
    }catch(IOException e){
    	setMensaje(e.toString());
    	System.out.println("Error Asignatura: "+e.toString());
    	return false;
    }finally{
    	try{
    		if(input!=null)input.close();
    		if(fileOut!=null)fileOut.close();
    	}catch(IOException e){}
    }
		return true;
	}

	public boolean plantillaRecuperacionLogro(String[] config,Collection[] c,FiltroPlantilla filtro){
		//0=Estudiantes
		//1=Logros
		//2=Escala de logros
		//3=Notas de logros
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=2;
		int numCelda=-2;
		HSSFSheet sheet2;
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			//hoja de escalas
			sheet = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
			setEscalaRecuperacion(c);
			filtro.setJerarquiaLogro(jerarquia);
			//hoja de plantilla
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE PLANTILLA
			setEncabezadoRecuperacion(filtro);
			setEstudiantesRecuperacion(c[0],0);
			setLogrosRecuperacion(c[1],1);
			setNotasRecuperacion(c[0],c[1],c[2],c[3]);
			//hoja oculta 
			sheet = workbook.getSheetAt(2);//OBTENER HOJA OCULTA
			setEncabezadoRecuperacionOculto(filtro);			
			setEstudiantesRecuperacion(c[0],1);
			setLogrosRecuperacion(c[1],1);
			setLogrosRecuperacionOcultos(c[1],0);
			setNotasRecuperacion(c[0],c[1],c[2],c[3]);
			//poner el archivo
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();//CERRAR
			fileOut.close();
			input.close();
    }catch(IOException e){
    	setMensaje(e.toString());
    	System.out.println("Error Recuperacion: "+e.toString());
    	return false;
    }finally{
    	try{
    		if(input!=null)input.close();
    		if(fileOut!=null)fileOut.close();
    	}catch(IOException e){}
    }
		return true;
	}
	
	/**
	 * @param config
	 * @param c
	 * @param filtro
	 * @return<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean plantillaAreaDescriptor(String[] config,Collection[] c,FiltroPlantilla filtro){
		/*
		 * 0=Estidiantes
		 * 1=Esala Area
		 * 2=NOtas Area
		 * 3=ausencia Area
		 * 4=Lista Descriptores
		 * 5=Notas Descriptor
		 */
		String jus="",nojus="",mot="",por="";
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=2;
		int numCelda=-2;
		HSSFSheet sheet2;
		FileInputStream input=null;
		FileOutputStream fileOut=null;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			//hoja de escalas
			sheet = workbook.getSheetAt(0);//OBTENER HOJA DE ESCALAS
			setEscalaAreaDescriptor(c[1]);
			//filtro.setJerarquiaLogro(jerarquia);
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE FORTALEZAS
			setDescriptor(c[4],1);
			filtro.setJerarquiaLogro(jerarquia);
			sheet = workbook.getSheetAt(2);//OBTENER HOJA DE DIFICULTADES
			setDescriptor(c[4],2);
			sheet = workbook.getSheetAt(3);//OBTENER HOJA DE RECOMENDACIONES
			setDescriptor(c[4],3);
			sheet = workbook.getSheetAt(4);//OBTENER HOJA DE ESTRATEGIAS
			setDescriptor(c[4],4);
			//hoja de plantilla
			sheet = workbook.getSheetAt(5);//OBTENER HOJA DE PLANTILLA
			setEncabezadoAreaDescriptor(filtro);
			setEstudiantesAreaDescriptor(c[0],0);
			setNotasArea(c[0],c[3],c[1],c[2]);
			setNotasDescriptor(c[0],c[5]);
			//hoja oculta 
			sheet = workbook.getSheetAt(6);//OBTENER HOJA OCULTA
			setEncabezadoAreaDescriptorOculto(filtro);			
			setEstudiantesAreaDescriptor(c[0],1);
			//poner el archivo
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
			fileOut.flush();//CERRAR
			fileOut.close();
			input.close();
    }catch(IOException e){
    	setMensaje(e.toString());
    	System.out.println("Error Asignatura: "+e.toString());
    	return false;
    }finally{
    	try{
    		if(input!=null)input.close();
    		if(fileOut!=null)fileOut.close();
    	}catch(IOException e){}
    }
		return true;
	}
	
	/**
	 * @param config
	 * @param c
	 * @param filtro
	 * @return<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean plantillaBateriaLogro(String[] config,Collection[] c,Logros filtro){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=2;
		int numCelda=-2;
		HSSFSheet sheet2;
	  FileInputStream input;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			sheet = workbook.getSheetAt(0);//OBTENER HOJA DE LOS escala
			setEscalaBateriaLogro(c[0]);			
			sheet = workbook.getSheetAt(1);//OBTENER HOJA DE LOS plantilla
			setEncabezadoBateriaLogro(filtro);
			sheet = workbook.getSheetAt(2);//OBTENER HOJA oculta
			setEncabezadoBateriaLogroOculto(filtro);
			/*crear el archivo de plantilla fisicamente*/
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			FileOutputStream fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			input.close();
    }catch(IOException e){
    	setMensaje(e.toString());
    	System.out.println("Error logro 2: "+e.toString());
    	return false;
  	}
		return true;
	}
	
	/**
	 * @param config
	 * @param c
	 * @param filtro
	 * @return<br>
	 * Return Type: boolean<br>
	 * Version 1.1.<br>
	 */
	public boolean plantillaBateriaDescriptor(String[] config,Collection[] c,Logros filtro){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=2;
		int numCelda=-2;
		HSSFSheet sheet2;
	  FileInputStream input;
		try{
			input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
			workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
			if(workbook==null){setMensaje("LIBRO NO EXISTE"); return false; }
			sheet = workbook.getSheetAt(0);
			setEscalaBateriaDescriptor(c[0]);
			for(int y=1;y<5;y++){
			sheet = workbook.getSheetAt(y);
			setEncabezadoBateriaDescriptor(filtro);
			}
			sheet = workbook.getSheetAt(5);
			setEncabezadoBateriaDescriptorOculto(filtro);
			/*crear el archivo de plantilla fisicamente*/
			File f=new File(config[2]);
			if(!f.exists()) FileUtils.forceMkdir(f);
			FileOutputStream fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			input.close();
    }catch(Exception e){
    	setMensaje(e.toString());
    	System.out.println("Error batria descriptor: "+e.toString());
    	return false;
  	}
		return true;
	}

	/**
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setNotasLogro(Collection c1,Collection c2,Collection c3,Collection c4){
		int numFila;
		int numCelda;
		if(!c1.isEmpty()){
			numFila=Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				String id=(String)((Object[])iterator.next())[0];
				if(!c2.isEmpty()){
					numCelda=Asignatura.Asig[Asignatura.EvaluacionLogro][Asignatura.COL]-1;
					iterator2=c2.iterator();
					while(iterator2.hasNext()){
						numCelda++;
						o=((Object[])iterator2.next());
						String log=(String)o[0];
						String jer=(String)o[3];
						if(!c4.isEmpty()){
							iterator4 =c4.iterator();
							while(iterator4.hasNext()){
								o2=((Object[])iterator4.next());
								String vlr=(String)o2[0];//est
								String vlr2=(String)o2[1];//logro
								String vlr3=(String)o2[2];//jerarquia
								String vlr4=(String)o2[3];//cod evaluacion
								if(!c3.isEmpty()){
									iterator3 =c3.iterator();
									while(iterator3.hasNext()){
										o2=((Object[])iterator3.next());
										String not=(String)o2[0];//cod nota
										String not2=(String)o2[1];//nombre nota				
										if(vlr.equals(id) && vlr2.equals(log) && vlr3.equals(jer) && vlr4.equals(not)){
											cell=row.createCell((short)numCelda);
											cell.setCellValue((String)not2);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}	
	}
	
	public void setNotasRecuperacion(Collection c1,Collection c2,Collection c3,Collection c4){
		int numFila;
		int numCelda;
		if(!c1.isEmpty()){
 			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont fuente=workbook.createFont();
  		fuente.setFontName(HSSFFont.FONT_ARIAL);
  		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  		fuente.setColor(HSSFFont.COLOR_RED);
  		style.setFont(fuente); 			
			numFila=Recuperacion.Rec[Recuperacion.EvaluacionLogro][Recuperacion.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				String id=(String)((Object[])iterator.next())[0];
				if(!c2.isEmpty()){
					numCelda=Recuperacion.Rec[Recuperacion.EvaluacionLogro][Recuperacion.COL]-1;
					iterator2=c2.iterator();
					while(iterator2.hasNext()){
						numCelda++;
						o=((Object[])iterator2.next());
						String log=(String)o[0];
						String jer=(String)o[3];
						if(!c4.isEmpty()){
							iterator4 =c4.iterator();
							while(iterator4.hasNext()){
								o2=((Object[])iterator4.next());
								String vlr=(String)o2[0];//est
								String vlr2=(String)o2[1];//logro
								String vlr3=(String)o2[2];//jerarquia
								String vlr4=(String)o2[3];//cod evaluacion
								if(!c3.isEmpty()){
									iterator3 =c3.iterator();
									while(iterator3.hasNext()){
										o2=((Object[])iterator3.next());
										String not=(String)o2[0];//cod nota
										String not2=(String)o2[1];//nombre nota				
										if(vlr.equals(id) && vlr2.equals(log) && vlr3.equals(jer) && vlr4.equals(not)){
											cell=row.createCell((short)numCelda);
											cell.setCellValue((String)not2);
											cell.setCellStyle(style);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}	
	}
	
	/*
	 * Pone los descriptores que tienen los estudiantes en el archivo separados por comas
	 * @param: Collection c1 Lista de estudiantes
	 * @param: Collection c2 Lista de notas que ya estan {codest,tipodesc,coddesc}
	 */
	/**
	 * @param c1
	 * @param c2<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setNotasDescriptor(Collection c1,Collection c2){
		int numFila;
		int numCelda;
		String id;String z;
		int celdas[]={
				0,
				Area.Area[Area.EvalFortaleza][Area.COL],
				Area.Area[Area.EvalDificultad][Area.COL],
				Area.Area[Area.EvalRecomendacion][Area.COL],
				Area.Area[Area.EvalEstrategia][Area.COL]
				};
		if(!c1.isEmpty()){
			numFila=Area.Area[Area.EvalFortaleza][Area.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				id=(String)((Object[])iterator.next())[0];
				if(!c2.isEmpty()){
					iterator2=c2.iterator();
					while(iterator2.hasNext()){
						o=((Object[])iterator2.next());
						String []id2={(String)o[0],(String)o[1],(String)o[2]};
						if(id2[0].equals(id)){
							numCelda=celdas[Integer.parseInt(id2[1])];							
							cell=row.getCell((short)numCelda);
							if(cell==null){
								cell=row.createCell((short)numCelda);
								cell.setCellValue((String)id2[2]);
							}else{
								z=getValorSql(cell);
								cell.setCellValue((z!=null?z:"")+","+(String)id2[2]);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param c<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEscalaBateriaLogro(Collection c){
		if(!c.isEmpty()){
			int numFila=BateriaLogro.BatLog[BateriaLogro.ListaPeriodo1][BateriaLogro.FIL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				int[] pos={
						BateriaLogro.BatLog[BateriaLogro.ListaPeriodo1][BateriaLogro.COL],
						BateriaLogro.BatLog[BateriaLogro.ListaPeriodo2][BateriaLogro.COL]
						};
				for(int j=0;j<o.length;j++){
		 			cell=row.createCell((short)pos[j]);
					cell.setCellValue((String)(o[j]!=null?o[j]:""));	
				}
			}
		}
	}

	/**
	 * @param c<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEscalaBateriaDescriptor(Collection c){
		if(!c.isEmpty()){
			int numFila=BateriaDescriptor.BatDes[BateriaDescriptor.ListaPeriodo1][BateriaDescriptor.FIL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				int[] pos={
						BateriaDescriptor.BatDes[BateriaDescriptor.ListaPeriodo1][BateriaDescriptor.COL],
						BateriaDescriptor.BatDes[BateriaDescriptor.ListaPeriodo2][BateriaDescriptor.COL]
						};
				for(int j=0;j<o.length;j++){
		 			cell=row.createCell((short)pos[j]);
					cell.setCellValue((String)(o[j]!=null?o[j]:""));	
				}
			}
		}
	}
	
	/**
	 * @param c<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEscalaLogroAsignatura(Collection[] c){
		if(c!=null){
			//ESCALA DE ASIGNATURA
			if(!c[1].isEmpty()){
				int numFila=Asignatura.Asig[Asignatura.EscalaAsignatura1][Asignatura.FIL];
				iterator =c[1].iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Asignatura.Asig[Asignatura.EscalaAsignatura1][Asignatura.COL],Asignatura.Asig[Asignatura.EscalaAsignatura2][Asignatura.COL]};
					for(int j=1;j<o.length;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
			}
			//ESCALA DE LOGROS
			if(!c[5].isEmpty()){
				int numFila=Asignatura.Asig[Asignatura.EscalaLogro1][Asignatura.FIL];
				iterator =c[5].iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Asignatura.Asig[Asignatura.EscalaLogro1][Asignatura.COL],Asignatura.Asig[Asignatura.EscalaLogro2][Asignatura.COL]};
					for(int j=1;j<o.length;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
			}
			//MOTIVOS DE AUSENCIA
			if(!Recursos.getRecurso(Recursos.AUSENCIA).isEmpty()){
				int numFila=Asignatura.Asig[Asignatura.Ausencia1][Asignatura.FIL];
				iterator =Recursos.getRecurso(Recursos.AUSENCIA).iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={Asignatura.Asig[Asignatura.Ausencia1][Asignatura.COL],Asignatura.Asig[Asignatura.Ausencia2][Asignatura.COL]};
					for(int j=0;j<o.length-1;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));
					}
				}
			}
			//LOGROS
			if(!c[4].isEmpty()){
				int numFila=Asignatura.Asig[Asignatura.ListaLogro1][Asignatura.FIL];
				iterator =c[4].iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Asignatura.Asig[Asignatura.ListaLogro1][Asignatura.COL],Asignatura.Asig[Asignatura.ListaLogro2][Asignatura.COL]};
					//int[] pos_={0,0,1};
					for(int j=1;j<o.length-1;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
				jerarquia=(String)(o[3]!=null?o[3]:"");
			}	
		}
	}

	public void setEscalaRecuperacion(Collection[] c){
		//0=Estudiantes
		//1=Logros
		//2=Escala de logros
		//3=Notas de logros
		if(c!=null){
			//ESCALA DE LOGROS
			if(!c[2].isEmpty()){
				int numFila=Recuperacion.Rec[Recuperacion.EscalaLogro1][Recuperacion.FIL];
				iterator =c[2].iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Recuperacion.Rec[Recuperacion.EscalaLogro1][Recuperacion.COL],
							Recuperacion.Rec[Recuperacion.EscalaLogro2][Recuperacion.COL]};
					for(int j=1;j<o.length;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
			}
			//LOGROS
			if(!c[1].isEmpty()){
				int numFila=Recuperacion.Rec[Recuperacion.ListaLogro1][Recuperacion.FIL];
				iterator =c[1].iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Recuperacion.Rec[Recuperacion.ListaLogro1][Recuperacion.COL],
							Recuperacion.Rec[Recuperacion.ListaLogro2][Recuperacion.COL]};
					for(int j=1;j<o.length-1;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
				jerarquia=(String)(o[3]!=null?o[3]:"");
			}	
		}
	}
	
	/**
	 * @param c<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEscalaAreaDescriptor(Collection c){
		if(c!=null){
			//ESCALA DE AREA
			if(!c.isEmpty()){
				int numFila=Area.Area[Area.EscalaArea1][Area.FIL];
				iterator =c.iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={0,Area.Area[Area.EscalaArea1][Area.COL],Area.Area[Area.EscalaArea2][Area.COL]};
					for(int j=1;j<o.length;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));	
					}
				}
			}
			//MOTIVOS DE AUSENCIA
			if(!Recursos.getRecurso(Recursos.AUSENCIA).isEmpty()){
				int numFila=Area.Area[Area.Ausencia1][Area.FIL];
				iterator =Recursos.getRecurso(Recursos.AUSENCIA).iterator();
				while(iterator.hasNext()){
					row=sheet.createRow(numFila++);
					o=(Object[])iterator.next();
					int[] pos={Area.Area[Area.Ausencia1][Area.COL],Area.Area[Area.Ausencia2][Area.COL]};
					for(int j=0;j<o.length-1;j++){
			 			cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));
					}
				}
			}
		}
	}

	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setDescriptor(Collection c,int n){		
		int numFila=0;
		int[] pos=new int[3];
		pos[0]=0;
		if(!c.isEmpty()){
			switch(n){
				case 1:
					numFila=Area.Area[Area.Fortaleza1][Area.FIL];
					pos[1]=Area.Area[Area.Fortaleza1][Area.COL];
					pos[2]=Area.Area[Area.Fortaleza2][Area.COL];
				break;
				case 2:
					numFila=Area.Area[Area.Dificultad1][Area.FIL];
					pos[1]=Area.Area[Area.Dificultad1][Area.COL];
					pos[2]=Area.Area[Area.Dificultad2][Area.COL];
				break;
				case 3:
					numFila=Area.Area[Area.Recomendacion1][Area.FIL];
					pos[1]=Area.Area[Area.Recomendacion1][Area.COL];
					pos[2]=Area.Area[Area.Recomendacion2][Area.COL];
				break;
				case 4:
					numFila=Area.Area[Area.Estrategia1][Area.FIL];
					pos[1]=Area.Area[Area.Estrategia1][Area.COL];
					pos[2]=Area.Area[Area.Estrategia2][Area.COL];
				break;
			}
			iterator =c.iterator();
			while(iterator.hasNext()){
				o=(Object[])iterator.next();
	 			if(((String)o[0]).equals(""+n)){
					row=sheet.createRow(numFila++);
					for(int j=1;j<(o.length-1);j++){
						cell=row.createCell((short)pos[j]);
						cell.setCellValue((String)(o[j]!=null?o[j]:""));
		 			}
				}
				jerarquia=(String)(o[3]!=null?o[3]:"");
			}
		}	
	}
	
	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoBateriaLogro(Logros f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion_());
			//FECHA
			numFila=BateriaLogro.BatLog[BateriaLogro.Fecha2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Fecha2][BateriaLogro.COL]);
			cell.setCellValue(d.toString());
			//grado
			numFila=BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaGrado_());
			//ASIGNATURA
			numFila=BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura_());
			//metodologia
			numFila=BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia_());
		}
	}

	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoBateriaDescriptor(Logros f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//INSTITUCION
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion_());
			//FECHA
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Fecha2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Fecha2][BateriaDescriptor.COL]);
			cell.setCellValue(d.toString());
			//GRADO
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaGrado_());
			//ASIGNATURA
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura_());
			//metodologia
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia_());
		}
	}

	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoBateriaLogroOculto(Logros f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Institucion2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion_());

			numFila=BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Institucion1][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion());
			//FECHA
			numFila=BateriaLogro.BatLog[BateriaLogro.Fecha2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Fecha2][BateriaLogro.COL]);
			cell.setCellValue(d.toString());
			//grado
			numFila=BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Grado2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaGrado_());

			numFila=BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Grado1][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaGrado());
			//ASIGNATURA
			numFila=BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Asignatura2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura_());

			numFila=BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Asignatura1][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura());
			//metodologia
			numFila=BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Metodologia2][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia_());

			numFila=BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaLogro.BatLog[BateriaLogro.Metodologia1][BateriaLogro.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia());
			
			//tipo
			numFila=BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)BateriaLogro.BatLog[BateriaLogro.Tipo][BateriaLogro.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//nivel de logro
			numFila=BateriaLogro.BatLog[BateriaLogro.Nivel][BateriaLogro.COL];
			cell=row.createCell((short)BateriaLogro.BatLog[BateriaLogro.Nivel][BateriaLogro.COL]);
			cell.setCellValue(f.getNivelLogro());			
		}
	}

	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoBateriaDescriptorOculto(Logros f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Institucion2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion_());

			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Institucion1][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaInstitucion());
			//FECHA
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Fecha2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Fecha2][BateriaDescriptor.COL]);
			cell.setCellValue(d.toString());
			//grado
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Grado2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaGrado_());

			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Grado1][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaGrado());
			//ASIGNATURA
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Area2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura_());

			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Area1][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaAsignatura());
			//metodologia
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia2][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia_());

			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Metodologia1][BateriaDescriptor.COL]);
			cell.setCellValue((String)f.getPlantillaMetodologia());
			
			//tipo
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Tipo][BateriaDescriptor.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//nivel de logro
			numFila=BateriaDescriptor.BatDes[BateriaDescriptor.Nivel][BateriaDescriptor.COL];
			cell=row.createCell((short)BateriaDescriptor.BatDes[BateriaDescriptor.Nivel][BateriaDescriptor.COL]);
			cell.setCellValue(f.getNivelLogro());			
		}
	}

	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoLogroAsignatura(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Asignatura.Asig[Asignatura.Institucion2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Institucion2][Asignatura.COL]);
			cell.setCellValue((String)f.getInstitucion_());
			//sede
			numFila=Asignatura.Asig[Asignatura.Sede2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Sede2][Asignatura.COL]);
			cell.setCellValue((String)f.getSede_());
			//jornada
			numFila=Asignatura.Asig[Asignatura.Jornada2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Jornada2][Asignatura.COL]);
			cell.setCellValue((String)f.getJornada_());
			//grado
			numFila=Asignatura.Asig[Asignatura.Grado2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grado2][Asignatura.COL]);
			cell.setCellValue((String)f.getGrado_());
			//GRUPO
			numFila=Asignatura.Asig[Asignatura.Grupo2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grupo2][Asignatura.COL]);
			cell.setCellValue((String)f.getGrupo_());
			//ASIGNATURA
			numFila=Asignatura.Asig[Asignatura.Asignatura2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Asignatura2][Asignatura.COL]);
			cell.setCellValue((String)f.getAsignatura_());
			//PERIODO
			numFila=Asignatura.Asig[Asignatura.Periodo2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Periodo2][Asignatura.COL]);
			cell.setCellValue((String)f.getPeriodo_());
			//DOCENTE
			numFila=Asignatura.Asig[Asignatura.Docente2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Docente2][Asignatura.COL]);
			cell.setCellValue((String)f.getDocente_());
			//FECHA
			numFila=Asignatura.Asig[Asignatura.Fecha2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Fecha2][Asignatura.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Asignatura.Asig[Asignatura.Metodologia2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Metodologia2][Asignatura.COL]);
			cell.setCellValue((String)f.getMetodologia_());
			//cerrado o no
			if(f.getCerrar().equals("1")){
				numFila=Asignatura.Asig[Asignatura.TxtCerrado][Asignatura.FIL];
				row=sheet.getRow(numFila);
				if(row==null)row=sheet.createRow(numFila);
				cell=row.getCell((short)Asignatura.Asig[Asignatura.TxtCerrado][Asignatura.COL]);
				if(cell==null)cell=row.createCell((short)2);
				cell.setCellValue("LA EVALUACION ESTA CERRADA");
			}
		}		
	}

	public void setEncabezadoRecuperacion(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Recuperacion.Rec[Recuperacion.Institucion2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Institucion2][Recuperacion.COL]);
			cell.setCellValue((String)f.getInstitucion_());
			//sede
			numFila=Recuperacion.Rec[Recuperacion.Sede2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Sede2][Recuperacion.COL]);
			cell.setCellValue((String)f.getSede_());
			//jornada
			numFila=Recuperacion.Rec[Recuperacion.Jornada2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Jornada2][Recuperacion.COL]);
			cell.setCellValue((String)f.getJornada_());
			//grado
			numFila=Recuperacion.Rec[Recuperacion.Grado2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grado2][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrado_());
			//GRUPO
			numFila=Recuperacion.Rec[Recuperacion.Grupo2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grupo2][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrupo_());
			//ASIGNATURA
			numFila=Recuperacion.Rec[Recuperacion.Asignatura2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Asignatura2][Recuperacion.COL]);
			cell.setCellValue((String)f.getAsignatura_());
			//PERIODO
			numFila=Recuperacion.Rec[Recuperacion.Periodo2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Periodo2][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo_());
			//PERIODO actual 
			numFila=Recuperacion.Rec[Recuperacion.PeriodoActual2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.PeriodoActual2][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo2_());
			//DOCENTE
			numFila=Recuperacion.Rec[Recuperacion.Docente2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Docente2][Recuperacion.COL]);
			cell.setCellValue((String)f.getDocente_());
			//FECHA
			numFila=Recuperacion.Rec[Recuperacion.Fecha2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Fecha2][Recuperacion.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Recuperacion.Rec[Recuperacion.Metodologia2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Metodologia2][Recuperacion.COL]);
			cell.setCellValue((String)f.getMetodologia_());
			//cerrado o no
			if(f.getCerrar().equals("1")){
				numFila=Recuperacion.Rec[Recuperacion.TxtCerrado][Recuperacion.FIL];
				row=sheet.getRow(numFila);
				if(row==null)row=sheet.createRow(numFila);
				cell=row.getCell((short)Recuperacion.Rec[Recuperacion.TxtCerrado][Recuperacion.COL]);
				if(cell==null)cell=row.createCell((short)2);
				cell.setCellValue("LA RECUPERACION ESTA CERRADA");
			}
		}		
	}
	
	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoAreaDescriptor(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Area.Area[Area.Institucion2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Institucion2][Area.COL]);
			cell.setCellValue((String)f.getInstitucion_());
			//sede
			numFila=Area.Area[Area.Sede2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Sede2][Area.COL]);
			cell.setCellValue((String)f.getSede_());
			//jornada
			numFila=Area.Area[Area.Jornada2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Jornada2][Area.COL]);
			cell.setCellValue((String)f.getJornada_());
			//grado
			numFila=Area.Area[Area.Grado2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grado2][Area.COL]);
			cell.setCellValue((String)f.getGrado_());
			//GRUPO
			numFila=Area.Area[Area.Grupo2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grupo2][Area.COL]);
			cell.setCellValue((String)f.getGrupo_());
			//AREA
			numFila=Area.Area[Area.Area2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Area2][Area.COL]);
			cell.setCellValue((String)f.getArea_());
			//PERIODO
			numFila=Area.Area[Area.Periodo2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Periodo2][Area.COL]);
			cell.setCellValue((String)f.getPeriodo_());
			//DOCENTE
			numFila=Area.Area[Area.Docente2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Docente2][Area.COL]);
			cell.setCellValue((String)f.getDocente_());
			//FECHA
			numFila=Area.Area[Area.Fecha2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Fecha2][Area.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Area.Area[Area.Metodologia2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Metodologia2][Area.COL]);
			cell.setCellValue((String)f.getMetodologia_());
			//cerrado o no
			if(f.getCerrar().equals("1")){
				numFila=Area.Area[Area.TxtCerrado][Area.FIL];
				row=sheet.getRow(numFila);
				if(row==null)row=sheet.createRow(numFila);
				cell=row.getCell((short)Area.Area[Area.TxtCerrado][Area.COL]);
				if(cell==null)cell=row.createCell((short)2);
				cell.setCellValue("LA EVALUACION ESTA CERRADA");
			}
		}		
	}

	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoPreescolar(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Preescolar.Pree[Preescolar.Institucion2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Institucion2][Preescolar.COL]);
			cell.setCellValue((String)f.getInstitucion_());
			//sede
			numFila=Preescolar.Pree[Preescolar.Sede2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Sede2][Preescolar.COL]);
			cell.setCellValue((String)f.getSede_());
			//jornada
			numFila=Preescolar.Pree[Preescolar.Jornada2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Jornada2][Preescolar.COL]);
			cell.setCellValue((String)f.getJornada_());
			//grado
			numFila=Preescolar.Pree[Preescolar.Grado2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grado2][Preescolar.COL]);
			cell.setCellValue((String)f.getGrado_());
			//GRUPO
			numFila=Preescolar.Pree[Preescolar.Grupo2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grupo2][Preescolar.COL]);
			cell.setCellValue((String)f.getGrupo_());
			//Preescolar
			numFila=Preescolar.Pree[Preescolar.Dimension2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Dimension2][Preescolar.COL]);
			cell.setCellValue((String)f.getArea_());
			//PERIODO
			numFila=Preescolar.Pree[Preescolar.Periodo2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Periodo2][Preescolar.COL]);
			cell.setCellValue((String)f.getPeriodo_());
			//DOCENTE
			numFila=Preescolar.Pree[Preescolar.Docente2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Docente2][Preescolar.COL]);
			cell.setCellValue((String)f.getDocente_());
			//FECHA
			numFila=Preescolar.Pree[Preescolar.Fecha2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Fecha2][Preescolar.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Preescolar.Pree[Preescolar.Metodologia2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Metodologia2][Preescolar.COL]);
			cell.setCellValue((String)f.getMetodologia_());
			//cerrado o no
			if(f.getCerrar().equals("1")){
				numFila=Preescolar.Pree[Preescolar.TxtCerrado][Preescolar.FIL];
				row=sheet.getRow(numFila);
				if(row==null)row=sheet.createRow(numFila);
				cell=row.getCell((short)Preescolar.Pree[Preescolar.TxtCerrado][Preescolar.COL]);
				if(cell==null)cell=row.createCell((short)2);
				cell.setCellValue("LA EVALUACION ESTA CERRADA");
			}
		}		
	}
	
	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoLogroAsignaturaOculto(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Asignatura.Asig[Asignatura.Institucion2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Institucion2][Asignatura.COL]);
			cell.setCellValue((String)f.getInstitucion_());

			numFila=Asignatura.Asig[Asignatura.Institucion1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Institucion1][Asignatura.COL]);
			cell.setCellValue((String)f.getInstitucion());
			//sede
			numFila=Asignatura.Asig[Asignatura.Sede2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Sede2][Asignatura.COL]);
			cell.setCellValue((String)f.getSede_());

			numFila=Asignatura.Asig[Asignatura.Sede1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Sede1][Asignatura.COL]);
			cell.setCellValue((String)f.getSede());
			//jornada
			numFila=Asignatura.Asig[Asignatura.Jornada2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Jornada2][Asignatura.COL]);
			cell.setCellValue((String)f.getJornada_());

			numFila=Asignatura.Asig[Asignatura.Jornada1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Jornada1][Asignatura.COL]);
			cell.setCellValue((String)f.getJornada());
			//grado
			numFila=Asignatura.Asig[Asignatura.Grado2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grado2][Asignatura.COL]);
			cell.setCellValue((String)f.getGrado_());

			numFila=Asignatura.Asig[Asignatura.Grado1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grado1][Asignatura.COL]);
			cell.setCellValue((String)f.getGrado());			
			//GRUPO
			numFila=Asignatura.Asig[Asignatura.Grupo2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grupo2][Asignatura.COL]);
			cell.setCellValue((String)f.getGrupo_());

			numFila=Asignatura.Asig[Asignatura.Grupo1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Grupo1][Asignatura.COL]);
			cell.setCellValue((String)f.getGrupo());
			//ASIGNATURA
			numFila=Asignatura.Asig[Asignatura.Asignatura2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Asignatura2][Asignatura.COL]);
			cell.setCellValue((String)f.getAsignatura_());

			numFila=Asignatura.Asig[Asignatura.Asignatura1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Asignatura1][Asignatura.COL]);
			cell.setCellValue((String)f.getAsignatura());
			//PERIODO
			numFila=Asignatura.Asig[Asignatura.Periodo2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Periodo2][Asignatura.COL]);
			cell.setCellValue((String)f.getPeriodo_());

			numFila=Asignatura.Asig[Asignatura.Periodo1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Periodo1][Asignatura.COL]);
			cell.setCellValue((String)f.getPeriodo());
			//DOCENTE
			numFila=Asignatura.Asig[Asignatura.Docente2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Docente2][Asignatura.COL]);
			cell.setCellValue((String)f.getDocente_());

			numFila=Asignatura.Asig[Asignatura.Docente1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Docente1][Asignatura.COL]);
			cell.setCellValue((String)f.getDocente());
			//FECHA
			numFila=Asignatura.Asig[Asignatura.Fecha2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Fecha2][Asignatura.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Asignatura.Asig[Asignatura.Metodologia2][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Metodologia2][Asignatura.COL]);
			cell.setCellValue((String)f.getMetodologia_());

			numFila=Asignatura.Asig[Asignatura.Metodologia1][Asignatura.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Asignatura.Asig[Asignatura.Metodologia1][Asignatura.COL]);
			cell.setCellValue((String)f.getMetodologia());
			//tipo
			numFila=Asignatura.Asig[Asignatura.Tipo][Asignatura.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Asignatura.Asig[Asignatura.Tipo][Asignatura.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//cerrado
			numFila=Asignatura.Asig[Asignatura.Cerrado][Asignatura.FIL];
			cell=row.createCell((short)Asignatura.Asig[Asignatura.Cerrado][Asignatura.COL]);
			cell.setCellValue(f.getCerrar());
			//nivel de los logros
			numFila=Asignatura.Asig[Asignatura.NivelLogro][Asignatura.FIL];
			cell=row.createCell((short)Asignatura.Asig[Asignatura.NivelLogro][Asignatura.COL]);
			cell.setCellValue(f.getNivelLogro());
			//jerarquia de grupo
			numFila=Asignatura.Asig[Asignatura.JerarquiaGrupo][Asignatura.FIL];
			cell=row.createCell((short)Asignatura.Asig[Asignatura.JerarquiaGrupo][Asignatura.COL]);
			cell.setCellValue(f.getJerarquiagrupo());
			//jerarquia de logro
			numFila=Asignatura.Asig[Asignatura.JerarquiaLogro][Asignatura.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Asignatura.Asig[Asignatura.JerarquiaLogro][Asignatura.COL]);
			cell.setCellValue(f.getJerarquiaLogro());		
		}		
	}

	public void setEncabezadoRecuperacionOculto(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Recuperacion.Rec[Recuperacion.Institucion2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Institucion2][Recuperacion.COL]);
			cell.setCellValue((String)f.getInstitucion_());

			numFila=Recuperacion.Rec[Recuperacion.Institucion1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Institucion1][Recuperacion.COL]);
			cell.setCellValue((String)f.getInstitucion());
			//sede
			numFila=Recuperacion.Rec[Recuperacion.Sede2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Sede2][Recuperacion.COL]);
			cell.setCellValue((String)f.getSede_());

			numFila=Recuperacion.Rec[Recuperacion.Sede1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Sede1][Recuperacion.COL]);
			cell.setCellValue((String)f.getSede());
			//jornada
			numFila=Recuperacion.Rec[Recuperacion.Jornada2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Jornada2][Recuperacion.COL]);
			cell.setCellValue((String)f.getJornada_());

			numFila=Recuperacion.Rec[Recuperacion.Jornada1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Jornada1][Recuperacion.COL]);
			cell.setCellValue((String)f.getJornada());
			//grado
			numFila=Recuperacion.Rec[Recuperacion.Grado2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grado2][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrado_());

			numFila=Recuperacion.Rec[Recuperacion.Grado1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grado1][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrado());			
			//GRUPO
			numFila=Recuperacion.Rec[Recuperacion.Grupo2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grupo2][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrupo_());

			numFila=Recuperacion.Rec[Recuperacion.Grupo1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Grupo1][Recuperacion.COL]);
			cell.setCellValue((String)f.getGrupo());
			//ASIGNATURA
			numFila=Recuperacion.Rec[Recuperacion.Asignatura2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Asignatura2][Recuperacion.COL]);
			cell.setCellValue((String)f.getAsignatura_());

			numFila=Recuperacion.Rec[Recuperacion.Asignatura1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Asignatura1][Recuperacion.COL]);
			cell.setCellValue((String)f.getAsignatura());
			//PERIODO
			numFila=Recuperacion.Rec[Recuperacion.Periodo2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Periodo2][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo_());

			numFila=Recuperacion.Rec[Recuperacion.Periodo1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Periodo1][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo());
			//PERIODO ACTUAL
			numFila=Recuperacion.Rec[Recuperacion.PeriodoActual2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.PeriodoActual2][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo2_());

			numFila=Recuperacion.Rec[Recuperacion.PeriodoActual1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.PeriodoActual1][Recuperacion.COL]);
			cell.setCellValue((String)f.getPeriodo2());
			//DOCENTE
			numFila=Recuperacion.Rec[Recuperacion.Docente2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Docente2][Recuperacion.COL]);
			cell.setCellValue((String)f.getDocente_());

			numFila=Recuperacion.Rec[Recuperacion.Docente1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Docente1][Recuperacion.COL]);
			cell.setCellValue((String)f.getDocente());
			//FECHA
			numFila=Recuperacion.Rec[Recuperacion.Fecha2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Fecha2][Recuperacion.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Recuperacion.Rec[Recuperacion.Metodologia2][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Metodologia2][Recuperacion.COL]);
			cell.setCellValue((String)f.getMetodologia_());

			numFila=Recuperacion.Rec[Recuperacion.Metodologia1][Recuperacion.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Recuperacion.Rec[Recuperacion.Metodologia1][Recuperacion.COL]);
			cell.setCellValue((String)f.getMetodologia());
			//tipo
			numFila=Recuperacion.Rec[Recuperacion.Tipo][Recuperacion.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Tipo][Recuperacion.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//cerrado
			numFila=Recuperacion.Rec[Recuperacion.Cerrado][Recuperacion.FIL];
			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Cerrado][Recuperacion.COL]);
			cell.setCellValue(f.getCerrar());
			//nivel de los logros
			numFila=Recuperacion.Rec[Recuperacion.NivelLogro][Recuperacion.FIL];
			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.NivelLogro][Recuperacion.COL]);
			cell.setCellValue(f.getNivelLogro());
			//jerarquia de grupo
			numFila=Recuperacion.Rec[Recuperacion.JerarquiaGrupo][Recuperacion.FIL];
			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.JerarquiaGrupo][Recuperacion.COL]);
			cell.setCellValue(f.getJerarquiagrupo());
			//jerarquia de logro
			numFila=Recuperacion.Rec[Recuperacion.JerarquiaLogro][Recuperacion.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.JerarquiaLogro][Recuperacion.COL]);
			cell.setCellValue(f.getJerarquiaLogro());		
		}		
	}
	
	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoAreaDescriptorOculto(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Area.Area[Area.Institucion2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Institucion2][Area.COL]);
			cell.setCellValue((String)f.getInstitucion_());

			numFila=Area.Area[Area.Institucion1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Institucion1][Area.COL]);
			cell.setCellValue((String)f.getInstitucion());
			//sede
			numFila=Area.Area[Area.Sede2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Sede2][Area.COL]);
			cell.setCellValue((String)f.getSede_());

			numFila=Area.Area[Area.Sede1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Sede1][Area.COL]);
			cell.setCellValue((String)f.getSede());
			//jornada
			numFila=Area.Area[Area.Jornada2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Jornada2][Area.COL]);
			cell.setCellValue((String)f.getJornada_());

			numFila=Area.Area[Area.Jornada1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Jornada1][Area.COL]);
			cell.setCellValue((String)f.getJornada());
			//grado
			numFila=Area.Area[Area.Grado2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grado2][Area.COL]);
			cell.setCellValue((String)f.getGrado_());

			numFila=Area.Area[Area.Grado1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grado1][Area.COL]);
			cell.setCellValue((String)f.getGrado());			
			//GRUPO
			numFila=Area.Area[Area.Grupo2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grupo2][Area.COL]);
			cell.setCellValue((String)f.getGrupo_());

			numFila=Area.Area[Area.Grupo1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Grupo1][Area.COL]);
			cell.setCellValue((String)f.getGrupo());
			//AREA
			numFila=Area.Area[Area.Area2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Area2][Area.COL]);
			cell.setCellValue((String)f.getArea_());

			numFila=Area.Area[Area.Area1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Area1][Area.COL]);
			cell.setCellValue((String)f.getArea());
			//PERIODO
			numFila=Area.Area[Area.Periodo2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Periodo2][Area.COL]);
			cell.setCellValue((String)f.getPeriodo_());

			numFila=Area.Area[Area.Periodo1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Periodo1][Area.COL]);
			cell.setCellValue((String)f.getPeriodo());
			//DOCENTE
			numFila=Area.Area[Area.Docente2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Docente2][Area.COL]);
			cell.setCellValue((String)f.getDocente_());

			numFila=Area.Area[Area.Docente1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Docente1][Area.COL]);
			cell.setCellValue((String)f.getDocente());
			//FECHA
			numFila=Area.Area[Area.Fecha2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Fecha2][Area.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Area.Area[Area.Metodologia2][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Metodologia2][Area.COL]);
			cell.setCellValue((String)f.getMetodologia_());

			numFila=Area.Area[Area.Metodologia1][Area.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Area.Area[Area.Metodologia1][Area.COL]);
			cell.setCellValue((String)f.getMetodologia());
			//tipo
			numFila=Area.Area[Area.Tipo][Area.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Area.Area[Area.Tipo][Area.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//cerrado
			numFila=Area.Area[Area.Cerrado][Area.FIL];
			cell=row.createCell((short)Area.Area[Area.Cerrado][Area.COL]);
			cell.setCellValue(f.getCerrar());
			//nivel de los logros
			numFila=Area.Area[Area.NivelLogro][Area.FIL];
			cell=row.createCell((short)Area.Area[Area.NivelLogro][Area.COL]);
			cell.setCellValue(f.getNivelLogro());
			//jerarquia de grupo
			numFila=Area.Area[Area.JerarquiaGrupo][Area.FIL];
			cell=row.createCell((short)Area.Area[Area.JerarquiaGrupo][Area.COL]);
			cell.setCellValue(f.getJerarquiagrupo());
			//jerarquia de logro
			numFila=Area.Area[Area.JerarquiaArea][Area.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Area.Area[Area.JerarquiaArea][Area.COL]);
			cell.setCellValue(f.getJerarquiaLogro());		
		}		
	}

	
	/**
	 * @param f<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEncabezadoPreescolarOculto(FiltroPlantilla f){
		java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
		int numFila=1;
		if(f!=null){
			//institucion
			numFila=Preescolar.Pree[Preescolar.Institucion2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Institucion2][Preescolar.COL]);
			cell.setCellValue((String)f.getInstitucion_());

			numFila=Preescolar.Pree[Preescolar.Institucion1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Institucion1][Preescolar.COL]);
			cell.setCellValue((String)f.getInstitucion());
			//sede
			numFila=Preescolar.Pree[Preescolar.Sede2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Sede2][Preescolar.COL]);
			cell.setCellValue((String)f.getSede_());

			numFila=Preescolar.Pree[Preescolar.Sede1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Sede1][Preescolar.COL]);
			cell.setCellValue((String)f.getSede());
			//jornada
			numFila=Preescolar.Pree[Preescolar.Jornada2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Jornada2][Preescolar.COL]);
			cell.setCellValue((String)f.getJornada_());

			numFila=Preescolar.Pree[Preescolar.Jornada1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Jornada1][Preescolar.COL]);
			cell.setCellValue((String)f.getJornada());
			//grado
			numFila=Preescolar.Pree[Preescolar.Grado2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grado2][Preescolar.COL]);
			cell.setCellValue((String)f.getGrado_());

			numFila=Preescolar.Pree[Preescolar.Grado1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grado1][Preescolar.COL]);
			cell.setCellValue((String)f.getGrado());			
			//GRUPO
			numFila=Preescolar.Pree[Preescolar.Grupo2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grupo2][Preescolar.COL]);
			cell.setCellValue((String)f.getGrupo_());

			numFila=Preescolar.Pree[Preescolar.Grupo1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Grupo1][Preescolar.COL]);
			cell.setCellValue((String)f.getGrupo());
			//Preescolar
			numFila=Preescolar.Pree[Preescolar.Dimension2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Dimension2][Preescolar.COL]);
			cell.setCellValue((String)f.getArea_());

			numFila=Preescolar.Pree[Preescolar.Dimension1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Dimension1][Preescolar.COL]);
			cell.setCellValue((String)f.getArea());
			//PERIODO
			numFila=Preescolar.Pree[Preescolar.Periodo2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Periodo2][Preescolar.COL]);
			cell.setCellValue((String)f.getPeriodo_());

			numFila=Preescolar.Pree[Preescolar.Periodo1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Periodo1][Preescolar.COL]);
			cell.setCellValue((String)f.getPeriodo());
			//DOCENTE
			numFila=Preescolar.Pree[Preescolar.Docente2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Docente2][Preescolar.COL]);
			cell.setCellValue((String)f.getDocente_());

			numFila=Preescolar.Pree[Preescolar.Docente1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Docente1][Preescolar.COL]);
			cell.setCellValue((String)f.getDocente());
			//FECHA
			numFila=Preescolar.Pree[Preescolar.Fecha2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Fecha2][Preescolar.COL]);
			cell.setCellValue(d.toString());
			//METODOLOGIA
			numFila=Preescolar.Pree[Preescolar.Metodologia2][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Metodologia2][Preescolar.COL]);
			cell.setCellValue((String)f.getMetodologia_());

			numFila=Preescolar.Pree[Preescolar.Metodologia1][Preescolar.FIL];
			row=sheet.getRow(numFila);
			cell=row.getCell((short)Preescolar.Pree[Preescolar.Metodologia1][Preescolar.COL]);
			cell.setCellValue((String)f.getMetodologia());
			//tipo
			numFila=Preescolar.Pree[Preescolar.Tipo][Preescolar.FIL];
			row=sheet.createRow(numFila);
			cell=row.createCell((short)Preescolar.Pree[Preescolar.Tipo][Preescolar.COL]);
			cell.setCellValue(f.getTipoPlantilla());
			//cerrado
			numFila=Preescolar.Pree[Preescolar.Cerrado][Preescolar.FIL];
			cell=row.createCell((short)Preescolar.Pree[Preescolar.Cerrado][Preescolar.COL]);
			cell.setCellValue(f.getCerrar());
			//nivel de los logros
			numFila=Preescolar.Pree[Preescolar.NivelLogro][Preescolar.FIL];
			cell=row.createCell((short)Preescolar.Pree[Preescolar.NivelLogro][Preescolar.COL]);
			cell.setCellValue(f.getNivelLogro());
			//jerarquia de grupo
			numFila=Preescolar.Pree[Preescolar.JerarquiaGrupo][Preescolar.FIL];
			cell=row.createCell((short)Preescolar.Pree[Preescolar.JerarquiaGrupo][Preescolar.COL]);
			cell.setCellValue(f.getJerarquiagrupo());
		}		
	}

	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEstudiantesLogroAsignatura(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Asignatura.Asig[Asignatura.Codigo][Asignatura.FIL];
			int contador=1;
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				for(int j=0;j<o.length-1;j++){
					switch(j){
						case 0://codigo o consecutivo
							if(n==0){
						 		cell=row.createCell((short)Asignatura.Asig[Asignatura.Codigo][Asignatura.COL]);
								cell.setCellValue(""+(contador++));								
							}else{
					 			cell=row.createCell((short)Asignatura.Asig[Asignatura.Codigo][Asignatura.COL]);
								cell.setCellValue((String)(o[j]!=null?o[j]:""));								
							}
						break;
						case 2://Apellidos
				 			cell=row.createCell((short)Asignatura.Asig[Asignatura.Apellido][Asignatura.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
						case 4://nombres
				 			cell=row.createCell((short)Asignatura.Asig[Asignatura.Nombre][Asignatura.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
					}
				}
			}
		}
	}

	public void setEstudiantesRecuperacion(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Recuperacion.Rec[Recuperacion.Codigo][Recuperacion.FIL];
			int contador=1;
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				for(int j=0;j<o.length-1;j++){
					switch(j){
						case 0://codigo o consecutivo
							if(n==0){
						 		cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Codigo][Recuperacion.COL]);
								cell.setCellValue(""+(contador++));								
							}else{
					 			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Codigo][Recuperacion.COL]);
								cell.setCellValue((String)(o[j]!=null?o[j]:""));								
							}
						break;
						case 2://Apellidos
				 			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Apellido][Recuperacion.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
						case 4://nombres
				 			cell=row.createCell((short)Recuperacion.Rec[Recuperacion.Nombre][Recuperacion.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
					}
				}
			}
		}
	}
	
	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEstudiantesAreaDescriptor(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Area.Area[Area.Codigo][Area.FIL];
			int contador=1;
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				for(int j=0;j<o.length-1;j++){
					switch(j){
						case 0://codigo o consecutivo
							if(n==0){
						 		cell=row.createCell((short)Area.Area[Area.Codigo][Area.COL]);
								cell.setCellValue(""+(contador++));								
							}else{
					 			cell=row.createCell((short)Area.Area[Area.Codigo][Area.COL]);
								cell.setCellValue((String)(o[j]!=null?o[j]:""));								
							}
						break;
						case 2://Apellidos
				 			cell=row.createCell((short)Area.Area[Area.Apellido][Area.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
						case 4://nombres
				 			cell=row.createCell((short)Area.Area[Area.Nombre][Area.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
					}
				}
			}
		}
	}

	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setEstudiantesPreescolar(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Preescolar.Pree[Preescolar.Codigo][Preescolar.FIL];
			int contador=1;
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				o=(Object[])iterator.next();
				for(int j=0;j<o.length-1;j++){
					switch(j){
						case 0://codigo o consecutivo
							if(n==0){
						 		cell=row.createCell((short)Preescolar.Pree[Preescolar.Codigo][Preescolar.COL]);
								cell.setCellValue(""+(contador++));								
							}else{
					 			cell=row.createCell((short)Preescolar.Pree[Preescolar.Codigo][Preescolar.COL]);
								cell.setCellValue((String)(o[j]!=null?o[j]:""));								
							}
						break;
						case 2://Apellidos
				 			cell=row.createCell((short)Preescolar.Pree[Preescolar.Apellido][Preescolar.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
						case 4://nombres
				 			cell=row.createCell((short)Preescolar.Pree[Preescolar.Nombre][Preescolar.COL]);
							cell.setCellValue((String)(o[j]!=null?o[j]:"")+" "+(o[j+1]!=null?o[j+1]:""));							
						break;
					}
				}
			}
		}
	}

	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setLogros(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Asignatura.Asig[Asignatura.Logros2][Asignatura.FIL];
			int numCelda=Asignatura.Asig[Asignatura.Logros2][Asignatura.COL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.getRow(numFila);
				if(row==null) row=sheet.createRow(numFila);
				o=(Object[])iterator.next();
		 		cell=row.getCell((short)numCelda++);
		 		if(cell==null)cell=row.createCell((short)(numCelda-1));
				cell.setCellValue((String)(o[n]!=null?o[n]:""));
			}
		}
	}
	
	public void setLogrosRecuperacion(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Recuperacion.Rec[Recuperacion.Logros2][Recuperacion.FIL];
			int numCelda=Recuperacion.Rec[Recuperacion.Logros2][Recuperacion.COL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.getRow(numFila);
				if(row==null) row=sheet.createRow(numFila);
				o=(Object[])iterator.next();
		 		cell=row.getCell((short)numCelda++);
		 		if(cell==null)cell=row.createCell((short)(numCelda-1));
				cell.setCellValue((String)(o[n]!=null?o[n]:""));
			}
		}
	}
	
	/**
	 * @param c
	 * @param n<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setLogrosOcultos(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Asignatura.Asig[Asignatura.Logros1][Asignatura.FIL];
			int numCelda=Asignatura.Asig[Asignatura.Logros1][Asignatura.COL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila);
				o=(Object[])iterator.next();
		 		cell=row.createCell((short)numCelda++);
				cell.setCellValue((String)(o[n]!=null?o[n]:""));
			}
		}
	}
	
	public void setLogrosRecuperacionOcultos(Collection c,int n){
		if(!c.isEmpty()){
			int numFila=Recuperacion.Rec[Recuperacion.Logros1][Recuperacion.FIL];
			int numCelda=Recuperacion.Rec[Recuperacion.Logros1][Recuperacion.COL];
			iterator =c.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila);
				o=(Object[])iterator.next();
		 		cell=row.createCell((short)numCelda++);
				cell.setCellValue((String)(o[n]!=null?o[n]:""));
			}
		}
	}
	
	/**
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setNotasAsignatura(Collection c1,Collection c2,Collection c3,Collection c4){
		String jus="",nojus="",mot="",por="";
		String not;
		String not2;
		String vlr;								
		String vlr2;
		int[] pos={
				Asignatura.Asig[Asignatura.Evaluacion][Asignatura.COL],
				Asignatura.Asig[Asignatura.AusenciaJus][Asignatura.COL],
				Asignatura.Asig[Asignatura.AusenciaNoJus][Asignatura.COL],
				Asignatura.Asig[Asignatura.Motivo][Asignatura.COL],
				Asignatura.Asig[Asignatura.Porcentaje][Asignatura.COL]};
		int j=0;
		if(!c1.isEmpty()){
			int numFila=Asignatura.Asig[Asignatura.Evaluacion][Asignatura.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				String id=(String)((Object[])iterator.next())[0];
				if(!c2.isEmpty()){
					jus=nojus=mot=por="";
					iterator2=c2.iterator();
					while(iterator2.hasNext()){
						o=(Object[])iterator2.next();							
						String id2=(String)o[0];
						if(id.equals(id2)){
							jus=(String)o[1];
							nojus=(String)o[2];
							mot=(String)o[3];
							por=(String)o[4];
							break;
						}
					}
				}
				if(!c4.isEmpty()){
					iterator2 =c4.iterator();
					while(iterator2.hasNext()){
						o=((Object[])iterator2.next());
						not=(String)o[0];//id Est 
						not2=(String)o[1];//nota
						if(!c3.isEmpty()){
							iterator3 =c3.iterator();
							while(iterator3.hasNext()){
								o=((Object[])iterator3.next());
								vlr=(String)o[0];//codigo nota
								vlr2=(String)o[1];//nombre nota
								if(not2!=null){
									if(not.equals(id) && not2.equals(vlr)){
										j=0;																		
										cell=row.createCell((short)pos[j++]);//nota
										cell.setCellValue((String)vlr2);									
										cell=row.createCell((short)pos[j++]);//jus
										cell.setCellValue((String)jus);									
										cell=row.createCell((short)pos[j++]);//nojus
										cell.setCellValue((String)nojus);									
										cell=row.createCell((short)pos[j++]);//mot
										cell.setCellValue((String)mot);									
										cell=row.createCell((short)pos[j++]);//por
										cell.setCellValue((String)por);
										break;									
									}
								}	
							}
						}
					}
				}
			}
		}	
	}
	
	/**
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setNotasArea(Collection c1,Collection c2,Collection c3,Collection c4){
		String jus="",nojus="",mot="",por="";
		String not;
		String not2;
		String vlr;								
		String vlr2;
		int[] pos={
				Area.Area[Area.Evaluacion][Area.COL],
				Area.Area[Area.AusenciaJus][Area.COL],
				Area.Area[Area.AusenciaNoJus][Area.COL],
				Area.Area[Area.Motivo][Area.COL],
				Area.Area[Area.Porcentaje][Area.COL]};
		int j=0;
		if(!c1.isEmpty()){
			int numFila=Area.Area[Area.Evaluacion][Area.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				String id=(String)((Object[])iterator.next())[0];
				if(!c2.isEmpty()){
					jus=nojus=mot=por="";
					iterator2=c2.iterator();
					while(iterator2.hasNext()){
						o=(Object[])iterator2.next();							
						String id2=(String)o[0];
						if(id.equals(id2)){
							jus=(String)o[1];
							nojus=(String)o[2];
							mot=(String)o[3];
							por=(String)o[4];
							break;
						}
					}
				}
				if(!c4.isEmpty()){
					iterator2 =c4.iterator();
					while(iterator2.hasNext()){
						o=((Object[])iterator2.next());
						not=(String)o[0];//id Est 
						not2=(String)o[1];//nota
						if(!c3.isEmpty()){
							iterator3 =c3.iterator();
							while(iterator3.hasNext()){
								o=((Object[])iterator3.next());
								vlr=(String)o[0];//codigo nota
								vlr2=(String)o[1];//nombre nota
								if(not2!=null){
									if(not.equals(id) && not2.equals(vlr)){
										j=0;																		
										cell=row.createCell((short)pos[j++]);//nota
										cell.setCellValue((String)vlr2);									
										cell=row.createCell((short)pos[j++]);//jus
										cell.setCellValue((String)jus);									
										cell=row.createCell((short)pos[j++]);//nojus
										cell.setCellValue((String)nojus);									
										cell=row.createCell((short)pos[j++]);//mot
										cell.setCellValue((String)mot);									
										cell=row.createCell((short)pos[j++]);//por
										cell.setCellValue((String)por);
										break;									
									}
								}	
							}
						}
					}
				}
			}
		}	
	}
	
	/**
	 * @param c1
	 * @param c3
	 * @param tipo<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setNotasPreescolar(Collection c1,Collection c3,String tipo){
		if(!c1.isEmpty()){
			int numFila=Preescolar.Pree[Preescolar.Evaluacion][Preescolar.FIL];
			iterator =c1.iterator();
			while(iterator.hasNext()){
				row=sheet.createRow(numFila++);
				String id=(String)((Object[])iterator.next())[0];
				if(!c3.isEmpty()){
					iterator2 =c3.iterator();
					while(iterator2.hasNext()){
						o=((Object[])iterator2.next());
						String est=(String)o[0];
						String dim=(String)o[1];
						String ev=(String)o[2];
						if(est.equals(id) && dim.equals(tipo)){
							int j=0;																		
							cell=row.createCell((short)Preescolar.Pree[Preescolar.Evaluacion][Preescolar.COL]);
							cell.setCellValue((String)ev);									
						}
					}
				}
			}
		}
	}

	/**
	 * @param s<br>
	 * Return Type: void<br>
	 * Version 1.1.<br>
	 */
	public void setMensaje(String s){
		mensaje+=s;
	}

	/**
	 * @return<br>
	 * Return Type: String<br>
	 * Version 1.1.<br>
	 */
	public String getMensaje(){
		return mensaje;
	}

	/**
	 * @param celda
	 * @return<br>
	 * Return Type: String<br>
	 * Version 1.1.<br>
	 */
	public String getValorSql(HSSFCell celda){
		String valor=null;
    if(celda==null)	return null;
    switch (celda.getCellType()){
    	case 0: //numerico    		
    		valor=String.valueOf((double)celda.getNumericCellValue());
    		if(valor.endsWith(".0")) valor=valor.substring(0,valor.length()-2);
    	break;
      case 1://string
	     	if(celda.getStringCellValue().trim().equals("")) valor=null;
		    else valor=celda.getStringCellValue().replace('\'',' ').trim(); 
	    break;
	    case 2://formula
	     	if(celda.getStringCellValue().trim().equals("")) valor=null;
		      else valor=celda.getStringCellValue().trim();
	    break;
	    case 3:	valor=null; break;//blank
	    case 4://boolean
	    	if(celda.getStringCellValue().trim().equals("")) valor=null;
		      else valor=celda.getStringCellValue().trim();
	    break;
	    case 5:	//error
				if(celda.getStringCellValue().trim().equals("")) valor=null;
		      else valor=celda.getStringCellValue().trim();
	    break;
	  }
		return valor;
	}

	public String formatear(String a){
	  	return (a.replace(' ','_').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','n').replace('n','N').replace('n','a').replace('n','e').replace('n','i').replace('n','o').replace('n','u').replace('n','A').replace('n','E').replace('n','I').replace('n','O').replace('n','U').replace('n','c').replace(':','_').replace('.','_').replace('/','_').replace('\\','_'));
	  }
}