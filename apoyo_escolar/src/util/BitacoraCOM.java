package util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BitacoraCOM {
	
	private final String PREFIX = "\"";
	private ResourceBundle rb;
	
	public BitacoraCOM(){
		rb = ResourceBundle.getBundle("common");
	}
	
	public String insertarBitacora(long colegio,int jornada,int modulo,String perfil,int sede,int submodulo,int tipoLog,String usuario, String descripcion){
		String retorno = "";
		try {
			String[] loginBit = usuario.split("-");						
			String token = this.getToken(loginBit[0], loginBit[1]);
			
			String URL = rb.getString("ruta.bitacora")+"/api/apoyo/consultas/insertarBitacora/";
		    Client client = ClientBuilder.newClient();
		    WebTarget target = client.target(URL);
		    Invocation.Builder solicitud = target.request();
			BitacoraDto request = new BitacoraDto();
			request.setColegio(colegio);
			request.setDescripcion(descripcion);
			request.setJornada(jornada);
			request.setModulo(modulo);
			request.setPerfil(perfil);
			request.setSede(sede);
			request.setSubmodulo(submodulo);
			request.setTipoLog(tipoLog);
			request.setUsuario(loginBit[0]);
		    Gson gson = new Gson();
		    String jsonString = gson.toJson(request);
		    System.out.println("FER: "+jsonString+" :FER");
		    Response post = solicitud.header("Authorization", "Bearer " + token).post(Entity.json(jsonString));
		    retorno = post.readEntity(String.class);
			
		} catch (Exception e) { 
		    e.printStackTrace();  
			System.out.println(e.getMessage());
		}
		return retorno;
	}
	
	private String getToken(String usuario, String contrasenia){
		String token = "";
		try {
			String tokenURL = rb.getString("ruta.bitacora")+"/api/apoyo/seguridad/login";
			Client tokenClient = ClientBuilder.newClient();
			WebTarget tokenTarget = tokenClient.target(tokenURL);
			Invocation.Builder tokenSolicitud = tokenTarget.request();
			Map<String, String> loginBitacora = new HashMap<String, String>();
			loginBitacora.put("usuario", usuario);
			loginBitacora.put("contrasenia", contrasenia);
			Gson tokenGson = new Gson();
			String tokenEntity = tokenGson.toJson(loginBitacora);
			Response tokenPost = tokenSolicitud.post(Entity.json(tokenEntity));
			String responseJson = tokenPost.readEntity(String.class);
			
			JsonObject jobjUsuario = new Gson().fromJson(responseJson, JsonObject.class);
			String xusuario = jobjUsuario.get("usuario").toString();
			JsonObject jobjUsuarioDetails = new Gson().fromJson(xusuario, JsonObject.class);
			String userDetails = jobjUsuarioDetails.get("userDetails").toString();
			JsonObject jobjToken = new Gson().fromJson(userDetails, JsonObject.class);
			token = jobjToken.get("token").toString().replace(PREFIX, "");
			System.out.println("FER: "+token+" :FER");
		} catch (Exception e) { 
		    e.printStackTrace();  
			System.out.println(e.getMessage());
		}
		return token;
	}
	

}
