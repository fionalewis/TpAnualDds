package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import exceptions.ExceptionsHandler;
import modelo.deviceState.Encendido;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
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
	
	//public static List<Dispositivo> dispositivosEnGral = new ArrayList<>();
	
	public static String rutaJsonClientesMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonClientes.json";
	public static String rutaJsonClientesSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonClientes.json";
	public static String rutaJsonDispMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonDispositivos.json";
	public static String rutaJsonDispSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonDispositivos.json";
	public static String rutaJsonAdminMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonAdministradores.json";
	public static String rutaJsonAdminSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonAdministradores.json";
	public static String rutaJsonCategMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json";
	public static String rutaJsonCategSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json";
	
	public static String rutaTransfSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\transformadores.json";
	public static String rutaTransfMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\transformadores.json";
	public static String rutaZonasSalo = "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\zonas.json";
	public static String rutaZonasMaru = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs\\zonas.json";
	
	public static String rutaClientesLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json";
	public static String rutaDispLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json";
	public static String rutaAdminLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonAdministradores.json";
	public static String rutaCategLinux = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json";
	
	//RUTAS PRINCIPALES
	public static String rutaJsonClientes = rutaJsonClientesMaru;
	public static String rutaJsonDisp = rutaJsonDispMaru;
	public static String rutaJsonAdmin = rutaJsonAdminMaru;
	public static String rutaJsonCateg = rutaJsonCategMaru;
	public static String rutaTransf = rutaTransfMaru;
	public static String rutaZonas = rutaZonasMaru;
	
	//Metodos que usamos para categorias
	
	public static boolean isBetween(double n,double min,double max) {
			return (n>=min)&&(n<=max);
	}

	public static Categoria categoria(double consumo) {
			List<Categoria> categorias = null;
			Categoria categoria = null;
			try {
				categorias = DAOJson.deserializarLista(Categoria.class,rutaJsonCateg);
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
	
	//Metodos que usamos para GeoLocation, transformadores y zonas
	
	public static GeoLocation conseguirCoord(String direcc) throws IOException {
		
		GeoLocation coordDirecc = new GeoLocation(direcc);
		
		/*
		 * Suelo dejar lo marcado comentado para que no se gasten las consultas diarias de la API, cuando necesiten usarlo si esta comentado
		 * lo borran y cambian en la linea TAL reader por str, y comentan la linea del jsonreader
		 */
		
		//-------------------------------De aca
		
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
	   
		//-------------------------------A aca
	    
		JsonParser parser = new JsonParser();
		//--> borrar este coment si no usan la API	//JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\belgrano.json")); //Comentar esta linea si se prueba la API
		JsonElement jsonTree = parser.parse(str); //Donde dice reader va str o viceversa en las consultas --> CAMBIAR CUANDO SE HAGAN LOS REQ
		
		JsonObject jsonObject = jsonTree.getAsJsonObject(); //Obtengo el domicilio del json como un objeto
		
		if (!jsonObject.get("status").getAsString().equals("OK")){ //Si no dice ok es o porque no reconoce el string o no encontro la direccion
	       System.out.println("No hay resultados."); //Faltaria hacer manejo de excepciones aca
		   return coordDirecc;
		} else {
		   System.out.println("Resultado encontrado. Asignando transformador. . ."); 
		   
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

		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,rutaTransf);

		Gson gson = new Gson();

		BufferedReader buffReader = new BufferedReader(new FileReader(rutaZonas)); //Tiene que coincidir con linea 156
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
						writer = new FileWriter(rutaZonas); // Tiene que coincidir con linea 144
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
		List<Transformador> transformadores = DAOJson.deserializarLista(Transformador.class,rutaTransf);
		Gson gson = new Gson();
		BufferedReader buffReader = new BufferedReader(new FileReader(rutaZonas));//Tiene que coincidir con linea 191
	    Zona[] zonas = gson.fromJson(buffReader, Zona[].class);
		for(int i = 0;i<zonas.length;i++) {
			zonas[i].getTransformadores().clear();
			String newJson = gson.toJson(zonas);
			gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = null;
			try {
				writer = new FileWriter(rutaZonas);//Tiene que coincidir con linea 183
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
	
	//Metodos que usamos para generar dispositivos con DeviceFactory
	
	public static void inicializarFactory() {
		
		JsonParser parserFactory = new JsonParser();
		JsonReader readerFactory = null;
		try {
			readerFactory = new JsonReader(new FileReader(JsonManager.rutaJsonDisp));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		JsonElement jsonTree = parserFactory.parse(readerFactory);
		JsonArray arrayDisp = jsonTree.getAsJsonArray();
		for(int i = 0;i<arrayDisp.size();i++) {
			String tipo = arrayDisp.get(i).getAsJsonObject().get("nombreDisp").getAsString();
			String descrip = arrayDisp.get(i).getAsJsonObject().get("equipoConcreto").getAsString();
			if (DeviceFactory.cumpleCondInteligente(tipo,descrip)) {
					//arrayDisp.get(i).getAsJsonObject().get("esInteligente").getAsBoolean()){
				DispositivoInteligente dAAgregar = new DispositivoInteligente();
				DispositivoInteligente dActual = null;
				try {
					dActual = (DispositivoInteligente) DAOJson.buscarIndexEnLista(DispositivoInteligente.class,i,JsonManager.rutaJsonDisp);
				} catch (Exception e) {
					ExceptionsHandler.catchear(e);
				}
				LocalDateTime fechaactual = LocalDateTime.now();
				dAAgregar.setNombreDisp(dActual.getNombreDisp());dAAgregar.setEquipoConcreto(dActual.getEquipoConcreto());
				dAAgregar.setFechaRegistro(fechaactual);dAAgregar.setEsBajoConsumo(dActual.getEsBajoConsumo());
				dAAgregar.setEsInteligente(true);dAAgregar.setkWhAhorro(dActual.getkWh());dAAgregar.setkWh(dActual.getkWh());
				dAAgregar.setIntervalo(new IntervaloDispositivo(fechaactual,modo.NORMAL));dAAgregar.setEstadoDisp(new Encendido());
				
				if(dAAgregar.getNombreDisp().equalsIgnoreCase("Heladera")) {
					dAAgregar.setHorasUsoMax(-1);dAAgregar.setHorasUsoMin(-1);
				} else {dAAgregar.setHorasUsoMax(dActual.getHorasUsoMax());dAAgregar.setHorasUsoMin(dActual.getHorasUsoMin());}
				
				DeviceFactory.tablaDI.add(dAAgregar);
			} else {
				DispositivoEstandar dAAgregar = new DispositivoEstandar();
				DispositivoEstandar dActual = null;
				try {
					dActual = (DispositivoEstandar) DAOJson.buscarIndexEnLista(DispositivoEstandar.class,i,JsonManager.rutaJsonDisp);
				} catch (Exception e) {
					ExceptionsHandler.catchear(e);
				}
				dAAgregar.setNombreDisp(dActual.getNombreDisp());dAAgregar.setEquipoConcreto(dActual.getEquipoConcreto());
				dAAgregar.setFechaRegistro(LocalDateTime.now());dAAgregar.setEsBajoConsumo(dActual.getEsBajoConsumo());
				dAAgregar.setEsInteligente(false);dAAgregar.setHorasUsoMax(dActual.getHorasUsoMax());dAAgregar.setHorasUsoDiarias(0);
				dAAgregar.setHorasUsoMin(dActual.getHorasUsoMin());dAAgregar.setkWh(dActual.getkWh());
				DeviceFactory.tablaDE.add(dAAgregar);
			}
		}
	}

	public static Dispositivo buscarPrimerMatch(String tipo) {
		Dispositivo unDisp = null;
		for(Dispositivo d : DeviceFactory.DGral) {
			if(unDisp.getNombreDisp().equals(tipo)) {
				unDisp = d;
			}
		}return unDisp;	
	}

	public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException {
		/* 
    	Cómo probar este código:
    	
    	1. Ver el contenido del archivo zonas.json en la carpeta del proyecto \\JSONs\\zonas.json
    	Ese json tiene cargadas 13 zonas dentro las cuales vamos a hacer las pruebas, ninguna de las zonas de este json tiene
    	transformadores asignados, ya que éstos se asignan a principio de mes y cambian todos los meses, es decir
    	ningún transformador "es parte de" una zona para siempre.
    	Al ejecutar esta línea, se van a asignar los transformadores precargados en transformadores.json, también en la carpeta
    	del proyecto \\JSONs\\transformadores.json, en este archivo hay 5 transformadores modelados, esto sería un ejemplo de lo
    	que asumimos que el ENRE nos enviaría una vez al mes, un archivo .json con una lista de transformadores activos.
    	*/
    	asignarTransfAZonas();
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
    	
    	DeviceFactory f = new DeviceFactory();
    	
    	//Disp Inteligente
    	
    	Dispositivo d1 = f.crearDisp("Aire Acondicionado","3500 frigorias");
    	LocalDateTime fechaReg = LocalDateTime.of(2018,8,21,0,30,0);
    	d1.setFechaRegistro(fechaReg);
    	System.out.println("Disp inteligente:\n" + d1.getNombreDisp() + " " + d1.getEquipoConcreto());
    	((DispositivoInteligente) d1).setEstadoDisp(new Encendido());
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,3,0,0));
    	((DispositivoInteligente) d1).encender(LocalDateTime.of(2018,8,21,4,0,0));
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,8,30,0));
    	//estuvo prendido aprox 7hs -> de 0.30am a 3am y de 4am a 8.30am = 7hs (tiempo mio)
    	//su consumo seria aprox 2.5*1.613 + 4.5*1.613 = 11.291kwh (va a ser un poco mas porque el consumo entre fechas siempre pasa del limite)
    	System.out.println("Cant de intervalos que se generaron: " + ((DispositivoInteligente) d1).getIntervalos().size());
    	System.out.println("Consumo entre la creacion de d1 y la fecha establecida: " + ((DispositivoInteligente) d1).consumoTotalEntre(d1.getFechaRegistro(),LocalDateTime.of(2018,8,21,8,31,0)));
    	    	
    	//Disp Estandar
    	
    	Dispositivo d2 = f.crearDisp("Plancha","A vapor");
    	d2.setFechaRegistro(2018, 8, 20, 6, 0, 0);
    	((DispositivoEstandar)d2).setHorasUsoDiarias(0.5); //Como ya paso un dia al ser estandar su consumo actual seria aprox 0.5hsDiarias*kwhAprox = 0.5*0.75 = 0.375kwh
    	System.out.println("\nDisp Estandar:\n" +d2.getNombreDisp()+ " " + d2.getEquipoConcreto());
    	System.out.println("Consumo entre la creacion de d2 y la fecha: " + d2.consumoTotal());
    	System.out.println("Sus horas de uso diarias aproximadas: " + d2.getHorasDeUso());
    	
    	List<Dispositivo> disp = new ArrayList<>(); disp.add(d1);disp.add(d2);
    	Cliente c = new Cliente("pepe","argento","pepe123","12345",2018,8,21,TipoDocumento.DNI,"40123456","12345678","Avenida Medrano 986",disp);
    	Transformador t = c.getTransformadorActual();
    	System.out.println("Domicilio del cliente: " + c.getDomicilio());
    	System.out.println("Zona del transformador correspondiente al domicilio del cliente: " + c.getTransformadorActual().getZona());
    	System.out.println("Id del transformador: " + c.getTransformadorActual().getIdTransformador());
    	System.out.println("Coordenadas del transformador: " + c.getTransformadorActual().getUbicacion().toString()); //El primer valor es null ya que no le asignamos un nombre a esta ubicacion
    	
    	resetTransf();
    }
}