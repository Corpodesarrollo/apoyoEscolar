/**
 * 
 */
package siges.importarFotografia.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import siges.importarFotografia.dao.ImportarFotografiaDAO;
import siges.integracion.beans.Datos;

/**
 * 1/07/2009
 * 
 * @author Athenea TA
 * @version 1.1
 */
public class ProcesarFotos extends Thread {
	public static final long serialVersionUID = 1;
	private static boolean running = true;
	private final int BUFFER_SIZE = 8192;
	ResourceBundle rb = ResourceBundle
			.getBundle("siges.importarFotografia.bundle.importarFotografia");

	private ImportarFotografiaDAO importarFotografiaDAO;
	private int tipo;
	private String[] archivo;
	private String path;
	private long consec;
	private long usuario;

	public ProcesarFotos(ImportarFotografiaDAO dao, String[] archivoZip,
			String path1, long consecutivo, long usuario) {
		// super("INTEGRADOR"+tipo);
		this.importarFotografiaDAO = dao;
		this.archivo = archivoZip;
		this.path = path1;
		this.consec = consecutivo;
		this.usuario = usuario;
	}

	public void run() {
		try {
			// CAMBIAR ESTADO A PROCESANDO
			importarFotografiaDAO.updateHilo(String.valueOf(usuario), consec,
					1, 0, 0, "Procesando archivos");
			// while (true) {
			if (!running) {
				System.out.println(this.getName() + "FINALIZANDO");
				return;
			}
			// System.out.println("IMPORTARFOTOGRAFIA: INICIANDO HILO");
			Thread.sleep(500);
			procesarFotos(usuario, consec);
			// System.out.println("IMPORTARFOTOGRAFIA: FINALIZO PROCESO HILO");
			// }
		} catch (Exception ex) {
			try {
				importarFotografiaDAO.updateHilo(String.valueOf(usuario),
						consec, 3, 0, 0, "Error hilo Procesando archivos.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
			System.out.println(this.getName()
					+ ". Error Hilo Importar Fotografia: " + ex);
		}
	}

	public void procesarFotos(long usuario, long consecutivo) {
		Datos[] datos = null;
		List resultado = null;
		int i = 0;
		try {
			// System.out.println("IMPORTARFOTOGRAFIA NORMAL"
			// + ": inicia descompresion" + archivo[0] + archivo[1]);
			List files = descomprimirFile(archivo);
			System.out.println("IMPORTARFOTOGRAFIA"
					+ ": finaliza descompresion");
			if (files != null && files.size() > 0) {
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": encontro archivos validos");
				// pasarle al dao para que los importe y devuelva un List de
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": inicia importacion");
				resultado = importarFotografiaDAO.importarEstudiante(files,
						path, usuario, consecutivo);
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": finaliza importacion");
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": inicia borrado de archivos");
				borrarArchivos(files);
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": finaliza borrado de archivos");
			} else {
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": no encontro archivos validos");
				importarFotografiaDAO.updateHilo(String.valueOf(usuario),
						consecutivo, 2, 0, 0,
						"Proceso no encontrn archivos vnlidos.");
			}

		} catch (Exception e) {
			try {
				importarFotografiaDAO.updateHilo(String.valueOf(usuario),
						consecutivo, 2, 0, 0, e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		// System.out.println("Termina Integracinn");
	}

	/**
	 * @param running
	 *            The running to set.
	 */
	public static void setRunning(boolean running) {
		ProcesarFotos.running = running;
	}

	public List descomprimirFile(String[] archivo) throws Exception {
		File f = null, f2 = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		ZipInputStream zis = null, zis2 = null;
		Iterator iterator;
		String o[];
		List files = new ArrayList();
		try {
			int count;
			byte data[] = new byte[BUFFER_SIZE];
			ZipEntry entry;
			// Create a ZipInputStream to read the zip file
			BufferedOutputStream dest = null;
			f = new File(archivo[0] + archivo[1]);
			// iterar sobre el zip para ver si tiene carpetas o archivos de
			// extenxion diferente a excels
			fis = new FileInputStream(f);
			zis2 = new ZipInputStream(new BufferedInputStream(fis));
			int nn = 0;
			int exedidos = 0;
			long tamanoMaximo = Long.parseLong(rb
					.getString("fotografia.maximoTamano")) * 1024;
			while ((entry = zis2.getNextEntry()) != null) {
				if (entry.getSize() > tamanoMaximo) {
					exedidos++;
					continue;
				}
				nn++;
				if (entry.isDirectory()) {
					// System.out
					// .println("IMPORTARFOTOGRAFIA"
					// +
					// ": El archivo contiene carpetas y no es posible importarlo.");
					throw new Exception(
							"El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				String entryName = entry.getName();
				// System.out.println(""+entryName);
				if (entryName.indexOf("/") != -1
						|| entryName.indexOf("\\") != -1) {
					System.out
							.println("IMPORTARFOTOGRAFIA"
									+ ": El archivo contiene carpetas y no es posible importarlo.");
					throw new Exception(
							"El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				if (!entryName.toLowerCase().endsWith(".jpg")) {
					System.out
							.println("IMPORTARFOTOGRAFIA"
									+ ": El archivo contiene archivos que no son imagenes jpg");
					throw new Exception(
							"El archivo contiene archivos que no son imagenes jpg, eliminelas e importe de nuevo");
				}
			}
			if (nn == 0 && exedidos == 0) {
				System.out.println("IMPORTARFOTOGRAFIA"
						+ ": El archivo zip esta vacio o esta danado");
				throw new Exception(
						"El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip'");
			}
			if (nn == 0 && exedidos > 0) {
				System.out
						.println("IMPORTARFOTOGRAFIA"
								+ ": El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("
								+ rb.getString("fotografia.maximoTamano")
								+ " Kb)");
				throw new Exception(
						"El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("
								+ rb.getString("fotografia.maximoTamano")
								+ " Kb). \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip' y que el tamano de las fotos no se exceda");
			}
			zis2.close();
			fis.close();
			fis = new FileInputStream(f);
			zis = new ZipInputStream(new BufferedInputStream(fis));
			System.out.println("IMPORTARFOTOGRAFIA"
					+ ": descomprimiendo en disco");
			/*
			 * if(zis!=null){
			 * System.out.println("ARCHIVO SIP ZIS DIFERNETE NULLLL"); }else
			 * System.out.println("ARCHIVO SIP ZIS ES NULO");
			 */
			while ((entry = zis.getNextEntry()) != null) {
				String nombreFoto = entry.getName();
				// System.out.println("*****************************************************************************FOTOGRAFIA ACTUAL: "+nombreFoto);
				if (entry.getSize() > tamanoMaximo) {
					continue;
				}
				String entryName = entry.getName();
				if (!entry.isDirectory()) {
					String destFN = archivo[0] + File.separator + entryName;
					fos = new FileOutputStream(destFN);
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);

					while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
						dest.write(data, 0, count);
					}
					o = new String[2];
					o[0] = archivo[0];
					o[1] = entryName;
					// System.out.println(archivo[0]+" _ "+entryName);
					files.add(o);
					dest.flush();
					dest.close();
				}
			}
			System.out.println("IMPORTARFOTOGRAFIA"
					+ ": descompresion completada");
			fis.close();
			zis2.close();
			zis.close();
		} catch (IOException e) {
			System.out.println("IMPORTARFOTOGRAFIA IO"
					+ ": error descomprimiendo " + e.getMessage());
			e.printStackTrace();
			throw new Exception(
					"Error descomprimiendo archivo ZIP, verifique que el archivo contenga solamente imngenes JPG y que el nombre de las mismas no contenga espacios ni caracteres especiales.");
		} catch (Exception e) {
			System.out
					.println("IMPORTARFOTOGRAFIA" + ": error descomprimiendo");
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

	private void borrarArchivos(List files) throws Exception {
		File f;
		String o[];
		try {
			if (files != null && files.size() > 0) {
				for (int i = 0; i < files.size(); i++) {
					o = (String[]) files.get(i);
					f = new File(o[0] + o[1]);
					if (f.exists()) {
						// FileUtils.forceDelete(f);
						f.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/*
	 * public List descomprimirFile(String[] archivo) throws Exception { File f
	 * = null; String o[]; List lFiles = new ArrayList(); long
	 * tamanoMaximo=Long.
	 * parseLong(rb.getString("fotografia.maximoTamano"))*1024; int nn = 0; int
	 * exedidos=0; try { f = new File(archivo[0] + archivo[1]); java.io.File
	 * files[]=f.listFiles(); if(files!=null){ for(int i=0;i<files.length;i++){
	 * String entryName = files[i].getName();
	 * if(files[i].length()>(tamanoMaximo)){ exedidos++; continue; } nn++;
	 * if(files[i].isDirectory()){ System.out.println("IMPORTARFOTOGRAFIA"+
	 * ": El archivo contiene carpetas y no es posible importarlo."); throw new
	 * Exception(
	 * "El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas."
	 * ); } if(!entryName.toLowerCase().endsWith(".jpg")){
	 * System.out.println("IMPORTARFOTOGRAFIA"
	 * +": El archivo contiene archivos que no son imagenes jpg"); throw new
	 * Exception(
	 * "El archivo contiene archivos que no son imagenes jpg, eliminelas e importe de nuevo"
	 * ); } o = new String[2]; o[0] = archivo[0]; o[1] = entryName;
	 * lFiles.add(o); } if (nn == 0 && exedidos==0) {
	 * System.out.println("IMPORTARFOTOGRAFIA"
	 * +": El archivo zip esta vacio o esta danado"); throw new Exception(
	 * "El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip'"
	 * ); } if (nn == 0 && exedidos>0) {
	 * System.out.println("IMPORTARFOTOGRAFIA"+
	 * ": El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("
	 * +rb.getString("fotografia.maximoTamano")+" Kb)"); throw new Exception(
	 * "El archivo zip esta vacio, esta danado o todas las fotografias exceden el tamano maximo permitido ("
	 * +rb.getString("fotografia.maximoTamano")+
	 * " Kb). \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip' y que el tamano de las fotos no se exceda"
	 * ); } File f2=new File(archivo[0]); f.archiveCopyAllTo(f2);
	 * File.umount(f,true); }
	 * System.out.println("IMPORTARFOTOGRAFIA"+": descompresion completada"); }
	 * catch (Exception e) {
	 * System.out.println("IMPORTARFOTOGRAFIA"+": error descomprimiendo");
	 * e.printStackTrace(); throw new Exception(e.getMessage()); } finally { try
	 * { FileUtils.forceDelete(new java.io.File(archivo[0] + archivo[1])); }
	 * catch (Exception e)
	 * {System.out.println("IMPORTARFOTOGRAFIA"+": error borrando ZIP");} }
	 * return lFiles; }
	 */
}
