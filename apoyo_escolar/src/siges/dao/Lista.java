/*
 * Creado el 16/12/2005
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
package siges.dao;

import java.util.Collection;
import java.util.Iterator;

import siges.exceptions.InternalErrorException;

/**
 * @author USUARIO
 *
 * Ventana - Preferencias - Java - Estilo de cndigo - Plantillas de cndigo
 */
public final class Lista {
  	private Lista(){}

  	/**
  	*	Funcinn:  Ejecuta un consulta en la base de datos y devuelte el resultado<br>
  	* @param	Collection lista
  	*	@return	String[]	
  	**/
  	public static String[] getFiltroArray(Collection a)throws InternalErrorException{
  		if(a!=null && !a.isEmpty()){
  			String[] m=new String[a.size()];
  			String z;
  			int i=0;
  			Iterator iterator =a.iterator();
  			Object[] o;
  			while (iterator.hasNext()){
  				z="";
  				o=(Object[])iterator.next();
  				for(int j=0;j<o.length;j++)
  					z+=(o[j]!=null?o[j]:"")+"|";
  				m[i++]=z.substring(0,z.lastIndexOf("|"));
  			}
  			return m;	
  		}
  		return null;
  	}
  	/**
  	*	Funcinn:  Ejecuta un consulta en la base de datos y devuelte el resultado<br>
  	* @param	Collection lista
  	* @param	int[] zz
  	*	@return	String[]	
  	**/
  	public static String[] getFiltroArray(Collection a,int[] zz)throws InternalErrorException{
  		if(!a.isEmpty()){
  			String[] m=new String[a.size()];
  			String l="";
  			int i=0;
  			Object[] o;
  			Iterator iterator =a.iterator();
  			while (iterator.hasNext()){
  				l="";
  				o=(Object[])iterator.next();
  				for(int j=0;j<zz.length;j++)
  					l+=(o[zz[j]]!=null?o[zz[j]]:"")+"|";
  				m[i++]=l.substring(0,l.lastIndexOf("|"));
  			}
  			return m;	
  		}
  		return null;
  	}
  	/**
  	*	Funcinn:  Ejecuta un consulta en la base de datos y devuelte el resultado como una matriz<br>
  	*	@param	Collection lista
  	*	@return	String[][]	
  	**/
  	public static String[][] getFiltroMatriz(Collection a)throws InternalErrorException{
  		Object[] o;
  		if(!a.isEmpty()){
  			int i=0,j=-1;
  			Iterator iterator =a.iterator();
  			o=((Object[])iterator.next());
  			String[][] m=new String[a.size()][o.length];
  			iterator =a.iterator();
  			while (iterator.hasNext()){
  				j++;
  				o=((Object[])iterator.next());
  				for(i=0;i<o.length;i++){
  					m[j][i]=(String)o[i];
  				}
  			}
  			return m;	
  		}
  		return null;
  	}
  	
}
