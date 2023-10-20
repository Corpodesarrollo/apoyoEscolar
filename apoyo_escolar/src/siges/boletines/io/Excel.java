package siges.boletines.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import siges.dao.Util;
import siges.util.beans.ResumenAsignatura;
import siges.util.beans.ResumenArea;
import siges.boletines.beans.ResAsignatura;
import siges.boletines.beans.ResArea;
import siges.io.Zip;

public class Excel {
  	private String pathBajar;
  	private String pathTemporal;
  	private HSSFWorkbook workbook;
  	private	HSSFRow row;
  	private	HSSFCell cell;
  	private String mensaje;
  	private Iterator iterator,iterator2,iterator3,iterator4;
  	private Object[] o=null;
  	private Object[] o2=null;
  	private boolean error;
  	private Util util;
  	private String jerarquia;
  	private Zip zip;
  	private HSSFFont fuente;
  	
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
    
    
  	public boolean plantillaResumenAsignatura(String[] config,Collection[] c,ResAsignatura resAsignatura,String archivosalida, int size,Collection files,String nombreZip){
  			/*
  			 * [0]NInos [0] codigo [1] nombre [2] apellidos
  			 * [1]Asignaturas [0] nombre
  			 * [2]Matriz de notas de x por 101  [0] codigo del est [1...10] notas
  			 * [3]Escalas valorativas
  			 * [4]Escalas valorativas excluidas
  			 */
  	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
  			int numFila=2;
  			int numCelda=-2;
  			HSSFSheet sheet;
  			FileInputStream input=null;
  			FileOutputStream fileOut=null;
  			Collection list = new ArrayList();
  			Zip zip= new Zip();
  			try{
  				input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
  				workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
  				if(workbook==null){System.out.println("hoja no hay");setMensaje("LIBRO NO EXISTE"); return false; }
  				//hoja de escalas
  				sheet = workbook.getSheetAt(0);//OBTENER HOJA DE PRINCIPAL
					setEncabezadoAsignatura(resAsignatura,sheet);
					setEstudiantesAsignatura(c[0],sheet);
					setNombresAsignatura(c[1],sheet);
					long[][] datos=setNotasAsignatura(c[0],c[2],c[3],sheet);
					
  				sheet = workbook.getSheetAt(1);//
					setEncabezadoAsignatura(resAsignatura,sheet);
					setEstudiantesAsignatura(c[0],sheet);
					setEscalaAsignatura(c[0],datos,c[3],sheet);

  				sheet = workbook.getSheetAt(2);//OBTENER HOJA DE PRINCIPAL
					setEncabezadoAsignatura(resAsignatura,sheet);
					setEstudiantesAsignatura(c[0],sheet);
					setNombresAsignatura(c[1],sheet);
					setNotasExcluidasAsignatura(c[0],c[2],c[4],sheet);
					
					//poner el archivo
  				File f=new File(config[2]);
  				if(!f.exists()) FileUtils.forceMkdir(f);
  				fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
  				workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
  				fileOut.flush();//CERRAR
  				fileOut.close();
  				input.close();
  				if(!zip.ponerZip(archivosalida,nombreZip,size,files)){
  				   setMensaje("Problemas al generar Zip"); 
   				   System.out.println("Problemas al generar Zip***");
   				   return false;
   				}
  	    }catch(Exception e){
  	      e.printStackTrace();
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

  	
  	public void setNombresAsignatura(Collection c,HSSFSheet sheet){
  		if(!c.isEmpty()){
  			int numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Asignatura][ResumenAsignatura.FIL];
  			int numCelda=ResumenAsignatura.ResAsig[ResumenAsignatura.Asignatura][ResumenAsignatura.COL];
  			iterator =c.iterator();
  			while(iterator.hasNext()){
  				row=sheet.getRow(numFila);
  				if(row==null) row=sheet.createRow(numFila);
  				o=(Object[])iterator.next();
  		 		cell=row.getCell((short)numCelda);
  		 		if(cell==null)cell=row.createCell((short)(numCelda));
  					cell.setCellValue((String)(o[0]!=null?o[0]:""));
  					numCelda+=5;
  				}
  		}
  	}
  	
  	public void setNombresArea(Collection c,HSSFSheet sheet){
    		if(!c.isEmpty()){
    			int numFila=ResumenArea.ResArea[ResumenArea.Area][ResumenArea.FIL];
    			int numCelda=ResumenArea.ResArea[ResumenArea.Area][ResumenArea.COL];
    			iterator =c.iterator();
    			while(iterator.hasNext()){
    				row=sheet.getRow(numFila);
    				if(row==null) row=sheet.createRow(numFila);
    				o=(Object[])iterator.next();
    		 		cell=row.getCell((short)numCelda);
    		 		if(cell==null)cell=row.createCell((short)(numCelda));
    					cell.setCellValue((String)(o[0]!=null?o[0]:""));
    					numCelda+=5;
    				}
    		}
    	}
  	
  	public void setEncabezadoAsignatura(ResAsignatura f,HSSFSheet sheet){
  			java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
  			int numFila=1;
  			if(f!=null){
  				//institucion
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Institucion][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Institucion][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getInstitucion());
  				//sede
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Sede][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Sede][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getSede());
  				//jornada
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Jornada][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Jornada][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getJornada());
  				//grado
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Grado][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Grado][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getGrado());
  				//GRUPO
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Grupo][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Grupo][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getGrupo());
  				//PERIODO
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Periodo][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Periodo][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getPeriodo());
  				//FECHA
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Fecha][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Fecha][ResumenAsignatura.COL]);
  				cell.setCellValue(d.toString());
  				//METODOLOGIA
  				numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Metodologia][ResumenAsignatura.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Metodologia][ResumenAsignatura.COL]);
  				cell.setCellValue((String)f.getMetodologia());
  			}		
  	}
  	
  	public void setEncabezadoArea(ResArea f,HSSFSheet sheet){
  			java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
  			int numFila=1;
  			if(f!=null){
  				//institucion
  				numFila=ResumenArea.ResArea[ResumenArea.Institucion][ResumenArea.FIL];
  				System.out.println("numFila: "+numFila);
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Institucion][ResumenArea.COL]);
  				System.out.println("numColumna: "+ResumenArea.ResArea[ResumenArea.Institucion][ResumenArea.COL]);
  				cell.setCellValue((String)f.getInstitucion());
  				//sede
  				numFila=ResumenArea.ResArea[ResumenArea.Sede][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Sede][ResumenArea.COL]);
  				cell.setCellValue((String)f.getSede());
  				//jornada
  				numFila=ResumenArea.ResArea[ResumenArea.Jornada][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Jornada][ResumenArea.COL]);
  				cell.setCellValue((String)f.getJornada());
  				//grado
  				numFila=ResumenArea.ResArea[ResumenArea.Grado][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Grado][ResumenArea.COL]);
  				cell.setCellValue((String)f.getGrado());
  				//GRUPO
  				numFila=ResumenArea.ResArea[ResumenArea.Grupo][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Grupo][ResumenArea.COL]);
  				cell.setCellValue((String)f.getGrupo());
  				//PERIODO
  				numFila=ResumenArea.ResArea[ResumenArea.Periodo][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Periodo][ResumenArea.COL]);
  				cell.setCellValue((String)f.getPeriodo());
  				//FECHA
  				numFila=ResumenArea.ResArea[ResumenArea.Fecha][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Fecha][ResumenArea.COL]);
  				cell.setCellValue(d.toString());
  				//METODOLOGIA
  				numFila=ResumenArea.ResArea[ResumenArea.Metodologia][ResumenArea.FIL];
  				row=sheet.getRow(numFila);
  				cell=row.getCell((short)ResumenArea.ResArea[ResumenArea.Metodologia][ResumenArea.COL]);
  				cell.setCellValue((String)f.getMetodologia());
  			}		
  	}
  	
  	public void setEstudiantesAsignatura(Collection c,HSSFSheet sheet){
  		if(!c.isEmpty()){
  			int numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Codigo][ResumenAsignatura.FIL];
  			int contador=1;
  			iterator =c.iterator();
  			while(iterator.hasNext()){
  				row=sheet.createRow(numFila++);
  				o=(Object[])iterator.next();
  				//consecutivo
 					cell=row.createCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Codigo][ResumenAsignatura.COL]);
 					cell.setCellValue(""+(contador++));								
  				//Apellidos
 					cell=row.createCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Apellido][ResumenAsignatura.COL]);
  				cell.setCellValue((String)(o[1]!=null?o[1]:""));							
  				//nombres
  				cell=row.createCell((short)ResumenAsignatura.ResAsig[ResumenAsignatura.Nombre][ResumenAsignatura.COL]);
  				cell.setCellValue((String)(o[2]!=null?o[2]:""));							
  			}
  		}
  	}
  	
  	public void setEscalaAsignatura(Collection c,long[][]datos,Collection c1,HSSFSheet sheet){
  	    int numFila=0;
  			int numCelda=0;
  			int contador=1;
  	    if(!c1.isEmpty()){
  	        numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Escala][ResumenAsignatura.FIL];
  	        numCelda=ResumenAsignatura.ResAsig[ResumenAsignatura.Escala][ResumenAsignatura.COL];
      			iterator =c1.iterator();
      			row=sheet.createRow(numFila);
      			while(iterator.hasNext()){
      			    o=(Object[])iterator.next();
      					cell=row.createCell((short)numCelda++);
       					cell.setCellValue(""+o[1]);
      			}
  	    }
//		    for(int t=0;t<datos.length;t++){
//	           for(int u=0;u<datos[t].length;u++){
//	               System.out.print(datos[t][u]+",");   
//	           }		        
//	           System.out.println();
//		    }
  	    if(!c.isEmpty()){
    			numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Total][ResumenAsignatura.FIL];
    			iterator =c.iterator();
    			String id=null;
    			long id2;
    			boolean band=false;
    			while(iterator.hasNext()){
    				row=sheet.createRow(numFila++);
    				o=(Object[])iterator.next();
    				id2=Long.parseLong(""+o[0]);
    				//System.out.println("ID2="+id2);
    				if(datos!=null){
    				    band=false;
    				    for(int t=0;t<datos.length;t++){
    				        //System.out.println("ID="+datos[t][0]);
    				        if(datos[t][0]==id2){
    				           band=true;
    				           //System.out.print("Si son ="+id2); 
    				           numCelda=ResumenAsignatura.ResAsig[ResumenAsignatura.Total][ResumenAsignatura.COL];
    				           for(int u=1;u<datos[t].length;u++){
    				      					cell=row.createCell((short)numCelda++);
    				       					cell.setCellValue(datos[t][u]);								
    				           }
    				           break;
    				        }
    				    }
    				    //if(band){System.out.println("Si es =");}
    				    //if(!band){System.out.println("No es =");}
    				}
    			}
    		}
    	}
  	
  	public void setEscalaArea(Collection c,long[][]datos,Collection c1,HSSFSheet sheet){
  	    int numFila=0;
  			int numCelda=0;
  			int contador=1;
  	    if(!c1.isEmpty()){
  	        numFila=ResumenArea.ResArea[ResumenArea.Escala][ResumenArea.FIL];
  	        numCelda=ResumenArea.ResArea[ResumenArea.Escala][ResumenArea.COL];
      			iterator =c1.iterator();
      			row=sheet.createRow(numFila);
      			while(iterator.hasNext()){
      			    o=(Object[])iterator.next();
      					cell=row.createCell((short)numCelda++);
       					cell.setCellValue(""+o[1]);
      			}
  	    }
  	    if(!c.isEmpty()){
    			numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Total][ResumenAsignatura.FIL];
    			iterator =c.iterator();
    			String id=null;
    			long id2;
    			boolean band=false;
    			while(iterator.hasNext()){
    				row=sheet.createRow(numFila++);
    				o=(Object[])iterator.next();
    				id2=Long.parseLong(""+o[0]);
    				if(datos!=null){
    				    band=false;
    				    for(int t=0;t<datos.length;t++){
    				        if(datos[t][0]==id2){
    				           band=true;
    				           numCelda=ResumenArea.ResArea[ResumenArea.Total][ResumenArea.COL];
    				           for(int u=1;u<datos[t].length;u++){
    				      					cell=row.createCell((short)numCelda++);
    				       					cell.setCellValue(datos[t][u]);								
    				           }
    				           break;
    				        }
    				    }
    				}
    			}
    		}
    	}
  	
  	public void setEstudiantesArea(Collection c,HSSFSheet sheet){
    		if(!c.isEmpty()){
    			int numFila=ResumenArea.ResArea[ResumenArea.Codigo][ResumenArea.FIL];
    			int contador=1;
    			iterator =c.iterator();
    			while(iterator.hasNext()){
    				row=sheet.createRow(numFila++);
    				o=(Object[])iterator.next();
    				//consecutivo
   					cell=row.createCell((short)ResumenArea.ResArea[ResumenArea.Codigo][ResumenArea.COL]);
   					cell.setCellValue(""+(contador++));								
    				//Apellidos
   					cell=row.createCell((short)ResumenArea.ResArea[ResumenArea.Apellido][ResumenArea.COL]);
    				cell.setCellValue((String)(o[1]!=null?o[1]:""));							
    				//nombres
    				cell=row.createCell((short)ResumenArea.ResArea[ResumenArea.Nombre][ResumenArea.COL]);
    				cell.setCellValue((String)(o[2]!=null?o[2]:""));							
    			}
    		}
    	}
  	
  	public long[][] setNotasAsignatura(Collection c,Collection c1,Collection c2,HSSFSheet sheet){
  			String not;
  			String abrev=null;
  			String[] escalas=null;
  			long [][] datos=null;
  			int j=0,l=0;
  			int pos=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.COL];
  			HSSFFont fuente=workbook.createFont();
  			HSSFCellStyle style = workbook.createCellStyle();
	  		fuente.setFontName("Arial");
	  		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  		fuente.setColor(HSSFFont.COLOR_RED);
	  		style.setFont(fuente);
  			
  			if(!c2.isEmpty()){
  			    escalas=new String[c2.size()];
  			    iterator =c2.iterator();
  			    int t=0;
  			    while(iterator.hasNext()){
  			        abrev=(String)((Object[])iterator.next())[1];
  			        escalas[t++]=abrev;
  			    }
  			}
  			if(!c.isEmpty()){
  			  datos=new long[c.size()][c2.size()+1];  
  				int numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.FIL];
  				iterator =c.iterator();
  				while(iterator.hasNext()){
  					row=sheet.createRow(numFila++);
  					String id=(String)((Object[])iterator.next())[0];
  					datos[j][0]=Long.parseLong(id);
  					if(!c1.isEmpty()){
  						iterator2 =c1.iterator();
  						while(iterator2.hasNext()){
  							o=((Object[])iterator2.next());
  							not=(String)o[0];//id Est 
								if(not.equals(id)){								    
  								pos=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.COL];
  								for(int k=1;k<o.length;k++){
  								  for(int m=0;m<escalas.length;m++){
  								      if(escalas[m].equals((String)o[k])){
  								          datos[j][m+1]=datos[j][m+1]+1;
  								      }
  								  }
  	  							cell=row.createCell((short)pos++);//nota
  	  							cell.setCellValue((String)o[k]);	
  	  							if(cell.getStringCellValue().equalsIgnoreCase("I") || cell.getStringCellValue().equalsIgnoreCase("D"))
  	  							    cell.setCellStyle(style);
  								}
  								break;
  							}
  						}
  					}
  					j++;
  				}
  			}
  			return datos;
  		}
  	
  	public void setNotasExcluidasAsignatura(Collection c,Collection c1,Collection c2,HSSFSheet sheet){
  			String not;
  			String abrev=null;
  			String[] escalas=null;
  			int j=0,l=0;
  			int pos=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.COL];
  			HSSFFont fuente=workbook.createFont();
  			HSSFCellStyle style = workbook.createCellStyle();
	  		fuente.setFontName("Arial");
	  		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  		fuente.setColor(HSSFFont.COLOR_RED);
	  		style.setFont(fuente);
  			if(!c2.isEmpty()){
  			    escalas=new String[c2.size()];
  			    iterator =c2.iterator();
  			    int t=0;
  			    while(iterator.hasNext()){
  			        abrev=(String)((Object[])iterator.next())[1];
  			        escalas[t++]=abrev;
  			    }
  			}
  			if(!c.isEmpty()){
  				int numFila=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.FIL];
  				iterator =c.iterator();
  				while(iterator.hasNext()){
  					row=sheet.createRow(numFila++);
  					String id=(String)((Object[])iterator.next())[0];
  					if(!c1.isEmpty()){
  						iterator2 =c1.iterator();
  						while(iterator2.hasNext()){
  							o=((Object[])iterator2.next());
  							not=(String)o[0];//id Est 
								if(not.equals(id)){								    
  								pos=ResumenAsignatura.ResAsig[ResumenAsignatura.Nota][ResumenAsignatura.COL];
  								for(int k=1;k<o.length;k++){
  								  for(int m=0;m<escalas.length;m++){
  								      if(escalas[m].equals((String)o[k])){
  			  	  							cell=row.createCell((short)pos);
  			  	  							cell.setCellValue((String)o[k]);	
  			  	  							if(cell.getStringCellValue().equalsIgnoreCase("I") || cell.getStringCellValue().equalsIgnoreCase("D"))
  			  	  							    cell.setCellStyle(style);
  								      }
  								  }
  								  pos++;
  								}
  								break;
  							}
  						}
  					}
  				}
  			}
  		}
  	
  	public long[][] setNotasArea(Collection c,Collection c1,Collection c2,HSSFSheet sheet){
  			String not;
  			String abrev=null;
  			String[] escalas=null;
  			int j=0,l=0;
  			long[][]datos=null;
  			int pos=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.COL];
  			HSSFFont fuente=workbook.createFont();
  			HSSFCellStyle style = workbook.createCellStyle();
	  		fuente.setFontName("Arial");
	  		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  		fuente.setColor(HSSFFont.COLOR_RED);
	  		style.setFont(fuente);
  			
  			if(!c2.isEmpty()){
  			    escalas=new String[c2.size()];
  			    iterator =c2.iterator();
  			    int t=0;
  			    while(iterator.hasNext()){
  			        abrev=(String)((Object[])iterator.next())[1];
  			        escalas[t++]=abrev;
  			    }
  			}
  			if(!c.isEmpty()){
   			  datos=new long[c.size()][c2.size()+1];  
  				int numFila=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.FIL];
  				iterator =c.iterator();
  				while(iterator.hasNext()){
  					row=sheet.createRow(numFila++);
  					String id=(String)((Object[])iterator.next())[0];
  					datos[j][0]=Long.parseLong(id);
  					if(!c1.isEmpty()){
  						iterator2 =c1.iterator();
  						while(iterator2.hasNext()){
  							o=((Object[])iterator2.next());
  							not=(String)o[0];//id Est 
								if(not.equals(id)){
  								pos=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.COL];
  								for(int k=1;k<o.length;k++){
    								  for(int m=0;m<escalas.length;m++){
    								      if(escalas[m].equals((String)o[k])){
    								          datos[j][m+1]=datos[j][m+1]+1;
    								      }
    								  }
  	  							cell=row.createCell((short)pos++);//nota
  	  							cell.setCellValue((String)o[k]);
  	  							if(cell.getStringCellValue().equalsIgnoreCase("I") || cell.getStringCellValue().equalsIgnoreCase("D"))
  	  							    cell.setCellStyle(style);
  								}
  								break;
  							}
  						}
    					j++;
  					}
  				}
  			}
  			return datos;
  		}
  	
  	public void setNotasExcluidasArea(Collection c,Collection c1,Collection c2,HSSFSheet sheet){
  			String not;
  			String abrev=null;
  			String[] escalas=null;
  			int j=0,l=0;
  			int pos=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.COL];
  			HSSFFont fuente=workbook.createFont();
  			HSSFCellStyle style = workbook.createCellStyle();
	  		fuente.setFontName("Arial");
	  		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  		fuente.setColor(HSSFFont.COLOR_RED);
	  		style.setFont(fuente);
  			if(!c2.isEmpty()){
  			    escalas=new String[c2.size()];
  			    iterator =c2.iterator();
  			    int t=0;
  			    while(iterator.hasNext()){
  			        abrev=(String)((Object[])iterator.next())[1];
  			        escalas[t++]=abrev;
  			    }
  			}
  			if(!c.isEmpty()){
  				int numFila=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.FIL];
  				iterator =c.iterator();
  				while(iterator.hasNext()){
  					row=sheet.createRow(numFila++);
  					String id=(String)((Object[])iterator.next())[0];
  					if(!c1.isEmpty()){
  						iterator2 =c1.iterator();
  						while(iterator2.hasNext()){
  							o=((Object[])iterator2.next());
  							not=(String)o[0];//id Est 
								if(not.equals(id)){
  								pos=ResumenArea.ResArea[ResumenArea.Nota][ResumenArea.COL];
  								for(int k=1;k<o.length;k++){
    								  for(int m=0;m<escalas.length;m++){
    								      if(escalas[m].equals((String)o[k])){
    			  	  							cell=row.createCell((short)pos);
    			  	  							cell.setCellValue((String)o[k]);
    			  	  							if(cell.getStringCellValue().equalsIgnoreCase("I") || cell.getStringCellValue().equalsIgnoreCase("D"))
    			  	  							    cell.setCellStyle(style);
    								      }
    								  }
  								    pos++;
  								}
  								break;
  							}
  						}
  					}
  				}
  			}
  		}
  	
  	public boolean plantillaResumenArea(String[] config,Collection[] c,ResArea resArea,String archivosalida, int size,Collection files,String nombreZip){
  			/*
  			 * [0]NInos [0] codigo [1] nombre [2] apellidos
  			 * [1]Asignaturas [0] nombre
  			 * [2]Matriz de notas de x por 101  [0] codigo del est [1...10] notas
  			 * [3]Escalas valorativas
  			 * [4]Escalas valorativas excluidas
  			 */
  	    java.sql.Date d=new java.sql.Date(new java.util.Date().getTime());
  			int numFila=2;
  			int numCelda=-2;
  			HSSFSheet sheet;
  			FileInputStream input=null;
  			FileOutputStream fileOut=null;
  			Collection list = new ArrayList();
  			Zip zip= new Zip();
  			
  			try{
  				input=new FileInputStream(config[0]+config[1]);//TRAER EL ARCHIVO
  				workbook= new HSSFWorkbook(input);//TRAER UN ARCHIVO DE PLANTILLA
  				if(workbook==null){System.out.println("hoja no hay");setMensaje("LIBRO NO EXISTE"); return false; }
  				//hoja de escalas
  				sheet = workbook.getSheetAt(0);//OBTENER HOJA DE PRINCIPAL
					setEncabezadoArea(resArea,sheet);
					setEstudiantesArea(c[0],sheet);
					setNombresArea(c[1],sheet);
					long[][] datos=setNotasArea(c[0],c[2],c[3],sheet);
  				sheet = workbook.getSheetAt(1);//OBTENER HOJA DE PRINCIPAL
					setEncabezadoArea(resArea,sheet);
					setEstudiantesArea(c[0],sheet);
					setEscalaArea(c[0],datos,c[3],sheet);
  				sheet = workbook.getSheetAt(2);//OBTENER HOJA DE PRINCIPAL
					setEncabezadoArea(resArea,sheet);
					setEstudiantesArea(c[0],sheet);
					setNombresArea(c[1],sheet);
					setNotasExcluidasArea(c[0],c[2],c[4],sheet);
					//poner el archivo
  				File f=new File(config[2]);
  				if(!f.exists()) FileUtils.forceMkdir(f);
  				fileOut= new FileOutputStream(config[2]+config[3]);//CREAR UN ARCHIVO
  				workbook.write(fileOut);//ESCRIBIR EL ARCHIVO A DISCO
  				fileOut.flush();//CERRAR
  				fileOut.close();
  				input.close();
  				if(!zip.ponerZip(archivosalida,nombreZip,size,files)){
   				   System.out.println("***Problemas al generar Zip***");
   				   return false;
   				}
  	    }catch(Exception e){
  	      e.printStackTrace();
  	    	setMensaje(e.toString());
  	    	System.out.println("Error Area: "+e.toString());
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
}
