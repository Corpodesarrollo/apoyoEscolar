/**
 * 
 */
package siges.subirFotografia.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import siges.dao.Ruta;
import siges.subirFotografia.dao.SubirFotografiaDAO;

/**
 * 24/09/2008
 * 
 * @author Latined
 * @version 1.2
 */
public class SubirFotografiaIO {
	private SubirFotografiaDAO subirFotografiaDAO;

	ResourceBundle rb = ResourceBundle.getBundle("siges.subirFotografia.bundle.subirFotografia");

	private final int BUFFER_SIZE = 8192;

	/**
	 * Constructor
	 * 
	 * @param importarFotografiaDAO
	 */
	public SubirFotografiaIO(SubirFotografiaDAO subirFotografiaDAO) {
		this.subirFotografiaDAO = subirFotografiaDAO;
	}

	public List subirFile(FileItem item, String path, long usuario) throws Exception {
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
			descomprimirFile(archivo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		// return new String[]{pathSubir,fileName};
		return resultado;
	}

	public void descomprimirFile(String[] archivo) throws Exception {
		File f = null, f2 = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		ZipInputStream zis = null, zis2 = null;
		Iterator iterator;
		Object o[];
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
			while ((entry = zis2.getNextEntry()) != null) {
				nn++;
				if (entry.isDirectory()) {
					throw new Exception("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				String entryName = entry.getName();
				// System.out.println(""+entryName);
				if (entryName.indexOf("/") != -1 || entryName.indexOf("\\") != -1) {
					throw new Exception("El archivo contiene carpetas y no es posible importarlo. \nColoque los archivos de imagen directamente en la raiz del archivo comprimido y no deje carpetas.");
				}
				if (!entryName.endsWith(".jpg")) {
					throw new Exception("El archivo contiene archivos que no son imagenes jpg, eliminelas e importe de nuevo");
				}
			}
			if (nn == 0) {
				throw new Exception("El archivo zip esta vacio o esta danado. \n  Por favor compruebe que la compresinn sea de tipo estnndar 'zip'");
			}
			zis2.close();
			fis.close();
			fis = new FileInputStream(f);
			zis = new ZipInputStream(new BufferedInputStream(fis));
			while ((entry = zis.getNextEntry()) != null) {
				String entryName = entry.getName();
				if (!entry.isDirectory()) {
					String destFN = archivo[0] + File.separator + entryName;
					fos = new FileOutputStream(destFN);
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);
					while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
						dest.write(data, 0, count);
					}
					o = new Object[2];
					o[0] = archivo[0];
					o[1] = entryName;
					System.out.println(archivo[0]+" _ "+entryName);
					files.add(o);
					dest.flush();
					dest.close();
				}
			}
			fis.close();
			zis2.close();
			zis.close();
		} catch (Exception e) {
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
	}

}
