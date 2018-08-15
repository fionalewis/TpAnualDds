package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import modelo.geoLocation.GeoLocation;
import modelo.geoLocation.Transformador;
import modelo.geoLocation.Zona;

/* ¡¡¡Agreguen las rutas de sus jsons en comentarios asi los pueden usar siempre!!!
 *  sino les va a tirar error cuando cualquier otro metodo llame a los de aca */

@SuppressWarnings("unused")
public class JsonManager {
		
	public static GeoLocation conseguirCoord(String direcc) throws IOException {
		
		GeoLocation coordDirecc = new GeoLocation(direcc);
		
		/*
		 * Lo dejo comentado para que no se gasten las consultas diarias de la API, cuando necesiten usarlo borran lo
		 * comentado abajo y cambian en la linea TAL reader por str, y comentan la linea del jsonreader
		 */
		
		/*
		String s = "http://maps.google.com/maps/api/geocode/json?" + "sensor=false&address=";
		
		// Pasar la direccion al formato correcto
	    s += URLEncoder.encode(direcc, "UTF-8");
	    URL url = new URL(s);
	 
	    // Leer del URL
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	   */
		
		JsonParser parser = new JsonParser();
			JsonReader reader = new JsonReader(new FileReader("\\JSONs\\JsonsParaPruebas\\belgrano.json")); //Comentar esta linea si se prueba la API
		JsonElement jsonTree = parser.parse(reader); //Donde dice reader va str en las consultas --> CAMBIAR CUANDO SE HAGAN LOS REQ
		
		JsonObject jsonObject = jsonTree.getAsJsonObject(); //Obtengo el domicilio del json como un objeto
		
		if (!jsonObject.get("status").getAsString().equals("OK")){ //Si no dice ok es o porque no reconoce el string o no encontro la direccion
	       System.out.println("No hay resultados."); //Faltaria hacer manejo de excepciones aca
		   return coordDirecc;
		} else {
		   System.out.println("Resultado encontrado."); 
		   
	   }
		
	   // Nos quedamos con el primer resultado de la lista en el json (porque puede haber varios en lo que encuentra la API)
	    JsonElement resultado = jsonObject.get("results").getAsJsonArray().get(0); //creamos una raiz con ese resultado
	    
	    JsonObject geoLoc = resultado.getAsJsonObject()
	    					.get("geometry")
	    					.getAsJsonObject()
	    					.get("location")
	    					.getAsJsonObject();
	    
	    double lat = geoLoc.get("lat").getAsDouble();coordDirecc.setLatitude(lat);
	    double lng = geoLoc.get("lng").getAsDouble();coordDirecc.setLongitude(lng);
	    return coordDirecc;
	}
		
	public static void asignarTransfAZonas() throws IOException, InstantiationException, IllegalAccessException {
		
		resetTransf();

		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,"\\JSONs\\transformadores.json");

		Gson gson = new Gson();

		BufferedReader buffReader = new BufferedReader(new FileReader("\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json")); //Tiene que coincidir con linea 101
	    Zona[] zonas = gson.fromJson(buffReader, Zona[].class);
	
		for(int i = 0;i<zonas.length;i++) {
			for(Transformador t : transformadores) {
				if(t.getZona().equals(zonas[i].getNombreZona())) {
					zonas[i].getTransformadores().add(t);
					String newJson = gson.toJson(zonas);
					//Resultado con pretty print
					gson = new GsonBuilder().setPrettyPrinting().create();
					FileWriter writer = null;
					try {
						writer = new FileWriter("\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json"); // Tiene que coincidir con linea 89
						writer.write(newJson);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (writer != null) {
							try {
								writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							System.out.println("Se produjo un error en la escritura del json.");
						}
					}
				}
			}
		}

	}
	
	public static void resetTransf() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,"\\JSONs\\transformadores.json");
		Gson gson = new Gson();
		BufferedReader buffReader = new BufferedReader(new FileReader("\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json"));
	    Zona[] zonas = gson.fromJson(buffReader, Zona[].class);
		for(int i = 0;i<zonas.length;i++) {
			zonas[i].getTransformadores().clear();
			String newJson = gson.toJson(zonas);
			gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = null;
			try {
				writer = new FileWriter("\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json");
				writer.write(newJson);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			}
		}
		
	}
		
    public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException
    {
    	//asignarTransfAZonas();
    	resetTransf();
    }
}