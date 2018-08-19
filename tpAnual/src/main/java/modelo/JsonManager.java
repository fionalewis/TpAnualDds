package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import exceptions.ExceptionsHandler;
import modelo.devices.Dispositivo;
import modelo.devices.Dispositivo.tipoDispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.geoLocation.GeoLocation;
import modelo.geoLocation.Transformador;
import modelo.geoLocation.Zona;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

/* ¡¡¡Agreguen las rutas de sus jsons en comentarios asi los pueden usar siempre!!!
 *  sino les va a tirar error cuando cualquier otro metodo llame a los de aca */

@SuppressWarnings("unused")
public class JsonManager {
	
	public static String rutaJsonClientesMari = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonClientes.json";
	public static String rutaJsonClientesSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonClientes.json";
	public static String rutaJsonDispMari = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonDispositivos.json";
	public static String rutaJsonDispSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\dispEntrega2.json";
	public static String rutaJsonAdminMari = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonAdministradores.json";
	public static String rutaJsonAdminSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonAdministradores.json";
	public static String rutaJsonCategMari = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json";
	public static String rutaJsonCategSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json";
	
	public static String rutaClientesLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json";
	public static String rutaDispLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json";
	public static String rutaAdminLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonAdministradores.json";
	public static String rutaCategLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json";
	
	//Metodos que usamos para categorias
	
	public static boolean isBetween(double n,double min,double max) {
			return (n>=min)&&(n<=max);
	}

	public static Categoria categoria(double consumo) {
			List<Categoria> categorias = null;
			Categoria categoria = null;
			try {
				categorias = DAOJson.deserializarLista(Categoria.class,rutaJsonCategSalo);
				} catch (Exception e) {
				ExceptionsHandler.catchear(e);
			}
			for(Categoria c: categorias) {
				if(isBetween(consumo,c.getMin(),c.getMax())) {
					categoria = c;
				}
			}
			return categoria;
		}
	
		
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
			JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\belgrano.json")); //Comentar esta linea si se prueba la API
		JsonElement jsonTree = parser.parse(reader); //Donde dice reader va str en las consultas --> CAMBIAR CUANDO SE HAGAN LOS REQ
		
		JsonObject jsonObject = jsonTree.getAsJsonObject(); //Obtengo el domicilio del json como un objeto
		
		if (!jsonObject.get("status").getAsString().equals("OK")){ //Si no dice ok es o porque no reconoce el string o no encontro la direccion
	       System.out.println("No hay resultados."); //Faltaria hacer manejo de excepciones aca
		   return coordDirecc;
		} else {
		   System.out.println("Resultado encontrado."); 
		   
	   }
		
	   // Nos quedamos con el primer resultado de la lista en el json (porque puede haber varios en lo que encuentra la API)
	    JsonElement resultado = jsonObject.get("results").getAsJsonArray().get(0);
	    
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

		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,"C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\transformadores.json");

		Gson gson = new Gson();

		BufferedReader buffReader = new BufferedReader(new FileReader("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json")); //Tiene que coincidir con linea 101
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
						writer = new FileWriter("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json"); // Tiene que coincidir con linea 89
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
		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,"C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\transformadores.json");
		Gson gson = new Gson();
		BufferedReader buffReader = new BufferedReader(new FileReader("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json"));
	    Zona[] zonas = gson.fromJson(buffReader, Zona[].class);
		for(int i = 0;i<zonas.length;i++) {
			zonas[i].getTransformadores().clear();
			String newJson = gson.toJson(zonas);
			gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = null;
			try {
				writer = new FileWriter("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\jsonZonasParaPruebas.json");
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
	
	public static void parseardisp() {
		//Cliente c = new Cliente("Juan","Pérez","juancito","1234",2018,8,19,TipoDocumento.DNI,"40111222","4789456","Avenida Cabildo y Avenida Congreso",null);
		
		DispositivoInteligente di = new DispositivoInteligente(tipoDispositivo.AireAcondicionado,"3500 frigorías",1.613,2018,8,19,14,0,0,90,360,false);
		DispositivoInteligente di1 = new DispositivoInteligente(tipoDispositivo.AireAcondicionado,"2200 frigorías",1.013,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di2 = new DispositivoInteligente(tipoDispositivo.Televisor,"LED 24'",0.04,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di3 = new DispositivoInteligente(tipoDispositivo.Televisor,"LED 32'",0.055,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di4 = new DispositivoInteligente(tipoDispositivo.Televisor,"LED 40'",0.08,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di5 = new DispositivoInteligente(tipoDispositivo.Heladera,"Con freezer",0.09,2018,8,19,14,0,0,-1,-1,true);
		DispositivoInteligente di6 = new DispositivoInteligente(tipoDispositivo.Heladera,"Sin freezer",0.075,2018,8,19,14,0,0,-1,-1,true);
		DispositivoInteligente di7 = new DispositivoInteligente(tipoDispositivo.Lavarropas,"Automático de 5kg",0.175,2018,8,19,14,0,0,6,30,true);
		DispositivoInteligente di8 = new DispositivoInteligente(tipoDispositivo.Ventilador,"De techo",0.06,2018,8,19,14,0,0,6,30,true);
		DispositivoInteligente di9 = new DispositivoInteligente(tipoDispositivo.Lámpara,"Halógena de 40W",0.04,2018,8,19,14,0,0,90,360,false);
		DispositivoInteligente di10 = new DispositivoInteligente(tipoDispositivo.Lámpara,"Halógena de 60W",0.06,2018,8,19,14,0,0,90,360,false);
		DispositivoInteligente di11 = new DispositivoInteligente(tipoDispositivo.Lámpara,"Halógena de 100W",0.015,2018,8,19,14,0,0,90,360,false);
		DispositivoInteligente di12 = new DispositivoInteligente(tipoDispositivo.Lámpara,"De 11W",0.011,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di13 = new DispositivoInteligente(tipoDispositivo.Lámpara,"De 15W",0.015,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di14 = new DispositivoInteligente(tipoDispositivo.Lámpara,"De 20W",0.02,2018,8,19,14,0,0,90,360,true);
		DispositivoInteligente di15 = new DispositivoInteligente(tipoDispositivo.PC,"De escritorio",0.4,2018,8,19,14,0,0,60,360,true);
		
		DispositivoEstandar de = new DispositivoEstandar(tipoDispositivo.Televisor,"Color de tubo fluorescente de 21'",0.075,2018,8,19,14,0,0,2,90,360,false);
		DispositivoEstandar de1 = new DispositivoEstandar(tipoDispositivo.Televisor,"Color de tubo fluorescente de 29' a 34'",0.175,2018,8,19,14,0,0,2,90,360,false);
		DispositivoEstandar de2 = new DispositivoEstandar(tipoDispositivo.Televisor,"LCD de 40'",0.18,2018,8,19,14,0,0,2,90,360,false);
		DispositivoEstandar de3 = new DispositivoEstandar(tipoDispositivo.Lavarropas,"Automático de 5 kg con calentamiento de agua",0.875,2018,8,19,14,0,0,2,6,30,false);
		DispositivoEstandar de4 = new DispositivoEstandar(tipoDispositivo.Lavarropas,"Semi-automático de 5kg",0.1275,2018,8,19,14,0,0,2,6,30,true);
		DispositivoEstandar de5 = new DispositivoEstandar(tipoDispositivo.Ventilador,"De pie",0.09,2018,8,19,14,0,0,2,120,360,true);
		DispositivoEstandar de6 = new DispositivoEstandar(tipoDispositivo.Microondas,"Convencional",0.64,2018,8,19,14,0,0,2,3,15,true);
		DispositivoEstandar de7 = new DispositivoEstandar(tipoDispositivo.Plancha,"A vapor",0.75,2018,8,19,14,0,0,2,3,30,true);
		
		List<Dispositivo> lista = new ArrayList<>();
		lista.add(di);lista.add(di1);lista.add(de);lista.add(de1);lista.add(de2);lista.add(di2);lista.add(di3);lista.add(di4);lista.add(di5);
		lista.add(di6);lista.add(de3);lista.add(di7);lista.add(de4);lista.add(de5);lista.add(di8);lista.add(di9);lista.add(di10);lista.add(di11);
		lista.add(di12);lista.add(di13);lista.add(di14);lista.add(di15);lista.add(de6);lista.add(de7);
		
		}
		
		
    public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException
    {	/* 
    	Cómo probar este código:
    	
    	1. Ver el contenido del archivo zonas.json en la carpeta del proyecto \\JSONs\\zonas.json
    	Ese json tiene cargadas 13 zonas dentro las cuales vamos a hacer las pruebas, ninguna de las zonas de este json tiene
    	transformadores asignados, ya que éstos se asignan a principio de mes y cambian todos los meses, es decir
    	ningún transformador "es parte de" una zona para siempre.
    	Al ejecutar esta línea, se van a asignar los transformadores precargados en transformadores.json, también en la carpeta
    	del proyecto \\JSONs\\transformadores.json, en este archivo hay 5 transformadores modelados, esto sería un ejemplo de lo
    	que asumimos que el ENRE nos enviaría una vez al mes, un archivo .json con una lista de transformadores activos.
    	*/
    	//asignarTransfAZonas();
    	/*
    	2. Una vez ejecutada la línea anterior, recargar el archivo y ver cómo se asignaron los transformadores a su zona correspondiente
    	en jsonZonasParaPruebas.json en la carpeta \JsonsParaPruebas\jsonZonasParaPruebas.json
    	*/
    	/*
    	Transformador t = new Transformador("Almagro",-34.5986062,-58.41989940000001); //un transformador ubicado en la facultad
    
    	
    	Zona z = GeoLocation.ubicacionDe(t); //objeto almagro*/
    	/*System.out.println(t.suministroActual());
    	//System.out.println(z.consumoActualZona());
    	*/
    	resetTransf();
    	parseardisp();
    	
    }
}