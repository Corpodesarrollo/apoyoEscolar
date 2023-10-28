package util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

public class BitacoraCOM {

	public static String insertarBitacora(long colegio,int jornada,int modulo,String perfil,int sede,int submodulo,int tipoLog,String usuario, String descripcion){
		String responseJson = "";
		String URL = "http://localhost:8080/api/apoyo/consultas/insertarBitacora/";
		try {
		//Creamos el cliente de conexión al API Restful
		      Client client = ClientBuilder.newClient();

		//Creamos el target lo cuál es nuestra URL
		      WebTarget target = client.target(URL);

		//Creamos nuestra solicitud que realizará el request
		      Invocation.Builder solicitud = target.request();

		//Creamos y llenamos nuestro objeto BaseReq con los datos que solicita el API
		      BitacoraDto request = new BitacoraDto();
		      request.setColegio(colegio);
		      request.setDescripcion(descripcion);
		      request.setJornada(jornada);
		      request.setModulo(modulo);
		      request.setPerfil(perfil);
		      request.setSede(sede);
		      request.setSubmodulo(submodulo);
		      request.setTipoLog(tipoLog);
		      request.setUsuario(usuario);

		//Convertimos el objeto req a un json
		      Gson gson = new Gson();
		      String jsonString = gson.toJson(request);
		      System.out.println(jsonString);

		//Enviamos nuestro json vía post al API Restful
		      Response post = solicitud.post(Entity.json(jsonString));

		//Recibimos la respuesta y la leemos en una clase de tipo String, en caso de que el json sea tipo json y no string, debemos usar la clase de tipo JsonObject.class en lugar de String.class
		      responseJson = post.readEntity(String.class);

		//Imprimimos el status de la solicitud
		      System.out.println("Estatus: " + post.getStatus());

		} catch (Exception e) { 
		//En caso de un error en la solicitud, llenaremos res con la exceptión para verificar que sucedió
		      System.out.print(e.toString());
		}
		return responseJson;
	}
}
