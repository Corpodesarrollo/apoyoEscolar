/**
 * 
 */
package siges.importarFotografia.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import siges.dao.Ruta;
import siges.importarFotografia.dao.ImportarFotografiaDAO;
import siges.importarFotografia.thread.ProcesarFotos;


/**
 * 24/09/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class ImportarFotografiaIO {
	private ImportarFotografiaDAO importarFotografiaDAO;

	ResourceBundle rb = ResourceBundle.getBundle("siges.importarFotografia.bundle.importarFotografia");

	//private final int BUFFER_SIZE = 8192;

	/**
	 * Constructor
	 * 
	 * @param importarFotografiaDAO
	 */
	public ImportarFotografiaIO(ImportarFotografiaDAO importarFotografiaDAO) {
		this.importarFotografiaDAO = importarFotografiaDAO;
	}

	public List subirFileEstudiante(FileItem item, String path, long usuario) throws Exception {
		String pathSubir = Ruta.get(path, rb.getString("fotografia.pathSubir") + "." + usuario);
		String pathTemporal = Ruta.get(path, rb.getString("fotografia.temporal"));
		String fileName = null;
		List resultado = null;
		List hilos=null;
		try {
			System.out.println("IMPORTARFOTOGRAFIA"+": va a guardar en "+pathSubir);
			System.out.println("IMPORTARFOTOGRAFIA"+": archivo a guardar "+item.getName());
			// poner el archivo en disco
			fileName = item.getName();
			java.io.File f = new java.io.File(pathSubir);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
			if(fileName.equals(""))fileName = (Math.random()*100)+".zip";
			f = new java.io.File(pathSubir + fileName);
			System.out.println("IMPORTARFOTOGRAFIA"+": escribiendo zip"+f.getAbsolutePath());
			item.write(f);
			System.out.println("IMPORTARFOTOGRAFIA"+": escritura zip exitosa");
			// descomprimir y verificar extensiones
			String[] archivo = { pathSubir, fileName };
			System.out.println("IMPORTARFOTOGRAFIA"+": inicia proceso en hilo");
			long consec=importarFotografiaDAO.getConsecutivoHilo(String.valueOf(usuario));
			importarFotografiaDAO.insertHilo(String.valueOf(usuario), consec, 0);
			ProcesarFotos procesarFotos =  new ProcesarFotos(importarFotografiaDAO, archivo, path, consec, usuario);
			procesarFotos.start();
			hilos= importarFotografiaDAO.getHilosUsuario(String.valueOf(usuario));
			/*List files=descomprimirFile(archivo);
			System.out.println("IMPORTARFOTOGRAFIA"+": finaliza descompresion");
			if(files!=null && files.size()>0){
				System.out.println("IMPORTARFOTOGRAFIA"+": encontro archivos validos");
				//pasarle al dao para que los importe y devuelva un List de
				System.out.println("IMPORTARFOTOGRAFIA"+": inicia importacion");
				resultado=importarFotografiaDAO.importarEstudiante(files, path);
				System.out.println("IMPORTARFOTOGRAFIA"+": finaliza importacion");
				System.out.println("IMPORTARFOTOGRAFIA"+": inicia borrado de archivos");
				borrarArchivos(files);
				System.out.println("IMPORTARFOTOGRAFIA"+": finaliza borrado de archivos");
			}else{
				System.out.println("IMPORTARFOTOGRAFIA"+": no encontro archivos validos"); 
			}*/
		} catch (Exception e) {
			System.out.println("IMPORTARFOTOGRAFIA"+": error en subida de arhivos");
			e.printStackTrace();
			throw new Exception(e.getMessage()+"-"+item.getName()+"-");
		}
		// return new String[]{pathSubir,fileName};
		return hilos;
	}

	public List descomprimirFile(String[] archivo) throws Exception {
		File f = null;
		String o[];
		List lFiles = new ArrayList();
		long tamanoMaximo=Long.parseLong(rb.getString("fotografia.maximoTamano"))*1024;
		int nn = 0;
		int exedidos=0;
		try {
			f = new File(archivo[0] + archivo[1]);
			java.io.File files[]=f.listFiles();
			if(files!=null){
				for(int i=0;i<files.length;i++){
					String entryName = files[i].getName();
					if(files[i].length()>(tamanoMaximo)){
						exedidos++;
						continue;
					}
					nn++;
					if(files[i].isDirectory()){
						System.out.println("IMPORTARFOTOGRAFIA"+": El archivo contiene carpetas y no es posible importarlo.");
						throw new Exception("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
					}
					if(!entryName.toLowerCase().endsWith(".jpg")){
						System.out.println("IMPORTARFOTOGRAFIA"+": El archivo contiene archivos que no son imagenes jpg");
						throw new Exception("El archivo contiene archivos que no son imagenes jpg, eliminelas e importe de nuevo");
					}
					o = new String[2];
					o[0] = archivo[0];
					o[1] = entryName;
					lFiles.add(o);
				}
				if (nn == 0 && exedidos==0) {
					System.out.println("IMPORTARFOTOGRAFIA"+": El archivo zip esta vacio o esta danado");
					throw new Exception("El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip'");
				}
				if (nn == 0 && exedidos>0) {
					System.out.println("IMPORTARFOTOGRAFIA"+": El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("+rb.getString("fotografia.maximoTamano")+" Kb)");
					throw new Exception("El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("+rb.getString("fotografia.maximoTamano")+" Kb). \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip' y que el tamano de las fotos no se exceda");
				}
				//File f2=new File(archivo[0]);
				//f.archiveCopyAllTo(f2);
				//File.umount(f,true);
			}	
			System.out.println("IMPORTARFOTOGRAFIA"+": descompresion completada");
		} catch (Exception e) {
			System.out.println("IMPORTARFOTOGRAFIA"+": error descomprimiendo");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				FileUtils.forceDelete(new java.io.File(archivo[0] + archivo[1]));
			} catch (Exception e) {System.out.println("IMPORTARFOTOGRAFIA"+": error borrando ZIP");}
		}
		return lFiles;
	}

/*
	public List descomprimirFile(String[] archivo) throws Exception {
		java.io.File f = null;
		java.io.FileOutputStream fos = null;
		java.io.FileInputStream fis = null;
		ZipInputStream zis = null, zis2 = null;
		String o[];
		List files = new ArrayList();
		try {
			int count;
			byte data[] = new byte[BUFFER_SIZE];
			ZipEntry entry;
			// Create a ZipInputStream to read the zip file
			BufferedOutputStream dest = null;
			f = new java.io.File(archivo[0] + archivo[1]);
			// iterar sobre el zip para ver si tiene carpetas o archivos de
			// extenxion diferente a excels
			fis = new java.io.FileInputStream(f);
			zis2 = new ZipInputStream(new BufferedInputStream(fis));
			int nn = 0;
			int exedidos=0;
			long tamanoMaximo=Long.parseLong(rb.getString("fotografia.maximoTamano"))*1024;
			while ((entry = zis2.getNextEntry()) != null) {
				if(entry.getSize()>tamanoMaximo){
					exedidos++;
					continue;
				}
				nn++;
				if (entry.isDirectory()) {
					System.out.println("IMPORTARFOTOGRAFIA"+": El archivo contiene carpetas y no es posible importarlo.");
					throw new Exception("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				String entryName = entry.getName();
				// System.out.println(""+entryName);
				if (entryName.indexOf("/") != -1 || entryName.indexOf("\\") != -1) {
					System.out.println("IMPORTARFOTOGRAFIA"+": El archivo contiene carpetas y no es posible importarlo.");
					throw new Exception("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				if (!entryName.toLowerCase().endsWith(".jpg")) {
					System.out.println("IMPORTARFOTOGRAFIA"+": El archivo contiene archivos que no son imagenes jpg");
					throw new Exception("El archivo contiene archivos que no son imagenes jpg, eliminelas e importe de nuevo");
				}
			}
			if (nn == 0 && exedidos==0) {
				System.out.println("IMPORTARFOTOGRAFIA"+": El archivo zip esta vacio o esta danado");
				throw new Exception("El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip'");
			}
			if (nn == 0 && exedidos>0) {
				System.out.println("IMPORTARFOTOGRAFIA"+": El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("+rb.getString("fotografia.maximoTamano")+" Kb)");
				throw new Exception("El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("+rb.getString("fotografia.maximoTamano")+" Kb). \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip' y que el tamano de las fotos no se exceda");
			}
			zis2.close();
			fis.close();
			fis = new java.io.FileInputStream(f);
			zis = new ZipInputStream(new BufferedInputStream(fis));
			System.out.println("IMPORTARFOTOGRAFIA"+": descomprimiendo en disco");
			while ((entry = zis.getNextEntry()) != null) {
				if(entry.getSize()>tamanoMaximo){
					continue;
				}
				String entryName = entry.getName();
				if (!entry.isDirectory()) {
					String destFN = archivo[0] + java.io.File.separator + entryName;
					fos = new java.io.FileOutputStream(destFN);
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);
					
					while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
						dest.write(data, 0, count);
					}
					o = new String[2];
					o[0] = archivo[0];
					o[1] = entryName;
					//System.out.println(archivo[0]+" _ "+entryName);
					files.add(o);
					dest.flush();
					dest.close();
				}
			}
			System.out.println("IMPORTARFOTOGRAFIA"+": descompresion completada");
			fis.close();
			zis2.close();
			zis.close();
		} catch (Exception e) {
			System.out.println("IMPORTARFOTOGRAFIA"+": error descomprimiendo");
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
				if (zis != null)
					zis.close();
				if (zis2 != null)
					zis2.close();
				FileUtils.forceDelete(f);
			} catch (Exception e) {
			}
		}
		return files;
	}
 */

	public List subirFilePersonal(FileItem item, String path, long usuario) throws Exception {
		String pathSubir = Ruta.get(path, rb.getString("fotografia.pathSubir") + "." + usuario);
		String pathTemporal = Ruta.get(path, rb.getString("fotografia.temporal"));
		String fileName = null;
		List resultado = null;
		try {
			// poner el archivo en disco
			fileName = item.getName();
			java.io.File f = new java.io.File(pathSubir);
			if (!f.exists()) {
				FileUtils.forceMkdir(f);
			}
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1, fileName.length());
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
			f = new java.io.File(pathSubir + fileName);
			item.write(f);
			// descomprimir y verificar extensiones
			String[] archivo = { pathSubir, fileName };
			List files=descomprimirFile(archivo);
			if(files!=null){
				//pasarle al dao para que los importe y devuelva un List de
				resultado=importarFotografiaDAO.importarPersonal(files);
				borrarArchivos(files);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		// return new String[]{pathSubir,fileName};
		return resultado;
	}
	
	private void borrarArchivos(List files)throws Exception {
		java.io.File f;
		String o[];
		try {
			if(files!=null && files.size()>0){
				for(int i=0;i<files.size();i++){
					o=(String[])files.get(i);
					f=new java.io.File(o[0]+o[1]);
					if(f.exists()){
						//FileUtils.forceDelete(f);
						f.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
