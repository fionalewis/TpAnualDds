package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.geoLocation.Transformador;
import modelo.repositories.DispositivoRepository;
import modelo.users.Cliente;

public class DAOJson {
	
	public static class ListOfJson<T> implements ParameterizedType
	{
	  private Class<?> wrapped;

	  public ListOfJson(Class<T> wrapper)
	  {
	    this.wrapped = wrapper;
	  }

	  @Override
	  public Type[] getActualTypeArguments()
	  {
	      return new Type[] { wrapped };
	  }

	  @Override
	  public Type getRawType()
	  {
	    return List.class;
	  }

	  @Override
	  public Type getOwnerType()
	  {
	    return null;
	  }
	}
	
	public static <T> List<T> toList(JsonReader reader, Class<T> clase)
	{
		Gson gson = new Gson();
	    return gson.fromJson(reader, new ListOfJson<T>(clase));
	}
	
	public static <T> List<T> deserializarLista(Class<T> clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<T> objetos = new ArrayList<T>();	
		JsonReader reader = new JsonReader(new FileReader(ruta));
		objetos = toList(reader, clase);
		
		return objetos;
	}
	
	public static <T> Object buscarIndexEnLista(Class<T> clase, int n,String rutaArch) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Object objeto = clase.newInstance();
		
		objeto = (deserializarLista(clase,rutaArch)).get(n);
		
		return objeto;
	}
	
	public static <T> List<Cliente> toListTransf(JsonReader reader, Class<Cliente> clase)
	{
		Gson gson = new Gson();
	    return gson.fromJson(reader, new ListOfJson<Cliente>(clase));
	}
	
	public static <T> List<Cliente> deserializarListaTransf(Class<Cliente> clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<Cliente> objetos = new ArrayList<Cliente>();	
		List<T> objetos2 = new ArrayList<T>();
		
		JsonReader reader = new JsonReader(new FileReader(ruta));
		objetos = toListTransf(reader, clase);
		objetos2 = deserializarDispositivosEnLista(clase,ruta);
		
		for( Cliente p : objetos){
			p.setDispositivos( (List<Dispositivo>) objetos2.get(0));
			
		}
			
		return objetos;
	}
	
	public static <T> List<T> deserializarDispositivosEnLista(Class<Cliente> clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<T> objetos = new ArrayList<T>();	
		
		JsonReader reader = new JsonReader(new FileReader(ruta));		
		JsonElement aux1 = new JsonParser().parse(reader);
		JsonArray jsonProfile2 = (aux1).getAsJsonArray();
		for(int i = 0; i < jsonProfile2.size();i++){
			JsonArray p = jsonProfile2.get(i).getAsJsonObject().getAsJsonArray("dispositivos");
			List<Dispositivo> objeto = new ArrayList<>();
			for(JsonElement j : p){
				JsonParser parser = new JsonParser();
				JsonObject o = parser.parse(j.toString()).getAsJsonObject();
				if (o.get("esInteligente").getAsBoolean()){
					DispositivoInteligente d = new DispositivoInteligente();
					d.setNombreDisp(o.get("nombreDisp").getAsString());
					d.setEquipoConcreto(o.get("equipoConcreto").getAsString());
					d.setEsInteligente(true);
					d.setFechaRegistro(LocalDateTime.parse(o.get("fechaRegistro") == null? LocalDateTime.now().toString() : o.get("fechaRegistro").getAsString()));
					d.setEsBajoConsumo(o.get("esBajoConsumo").getAsBoolean());
					d.setkWh(o.get("kWh").getAsDouble());
					d.setHorasUsoMax(o.get("horasUsoMax").getAsDouble());
					d.setHorasUsoMin(o.get("horasUsoMin").getAsDouble());
					if(o.get("intervalos") != null){
						List<IntervaloDispositivo> listaints = new ArrayList<>();
						JsonArray arr = o.get("intervalos").getAsJsonArray();
						for (JsonElement obj :arr){
							IntervaloDispositivo intd = new IntervaloDispositivo();
							intd.setInicio( LocalDateTime.parse( obj.getAsJsonObject().get("inicio").getAsString() ) );
							intd.setFin( LocalDateTime.parse( obj.getAsJsonObject().get("fin").getAsString() ) );							
							intd.setModo( modo.valueOf(obj.getAsJsonObject().get("modo").getAsString()) );
							d.setIntervalo(intd);
							listaints.add(intd);
						}
						d.setIntervalos(listaints);
					}
					objeto.add(d);
				}else{
					DispositivoEstandar d = new DispositivoEstandar();
					d.setNombreDisp(o.get("nombreDisp").toString());
					d.setEquipoConcreto(o.get("equipoConcreto").toString());
					d.setEsInteligente(false);
					d.setEsBajoConsumo(o.get("esBajoConsumo").getAsBoolean());
					d.setkWh(o.get("kWh").getAsDouble());
					d.setHorasUsoMax(o.get("horasUsoMax").getAsDouble());
					d.setHorasUsoMin(o.get("horasUsoMin").getAsDouble());
					objeto.add(d);
				}									
			}
			objetos.add((T) objeto);			
		}	
		return objetos;
	}

	public static List<Dispositivo> deserializarDispositivos(Class<Dispositivo> clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {		
		JsonReader reader = new JsonReader(new FileReader(ruta));		
		JsonElement aux1 = new JsonParser().parse(reader);
		JsonArray jsonProfile2 = (aux1).getAsJsonArray();
		//for(int i = 0; i < jsonProfile2.size();i++){
		//JsonArray p = jsonProfile2.get(i).getAsJsonObject().getAsJsonArray();
		List<Dispositivo> objeto = new ArrayList<>();
		for(JsonElement j : jsonProfile2){
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(j.toString()).getAsJsonObject();
			if (o.get("esInteligente").getAsBoolean()){
				DispositivoInteligente d = new DispositivoInteligente();
				d.setNombreDisp(o.get("nombreDisp").getAsString());
				d.setEquipoConcreto(o.get("equipoConcreto").getAsString());
				d.setEsInteligente(true);
				d.setFechaRegistro(LocalDateTime.parse(o.get("fechaRegistro") == null? LocalDateTime.now().toString() : o.get("fechaRegistro").getAsString()));
				d.setEsBajoConsumo(o.get("esBajoConsumo").getAsBoolean());
				d.setkWh(o.get("kWh").getAsDouble());
				if (o.get("horasUsoMax") != null) { //heladeras 
					d.setHorasUsoMax(o.get("horasUsoMax").getAsDouble());
					d.setHorasUsoMin(o.get("horasUsoMin").getAsDouble());
				}
				if(o.get("intervalos") != null){
					List<IntervaloDispositivo> listaints = new ArrayList<>();
					JsonArray arr = o.get("intervalos").getAsJsonArray();
					for (JsonElement obj :arr){
						IntervaloDispositivo intd = new IntervaloDispositivo();
						intd.setInicio( LocalDateTime.parse( obj.getAsJsonObject().get("inicio").getAsString() ) );
						intd.setFin( LocalDateTime.parse( obj.getAsJsonObject().get("fin").getAsString() ) );							
						intd.setModo( modo.valueOf(obj.getAsJsonObject().get("modo").getAsString()) );
						d.setIntervalo(intd);
						listaints.add(intd);
					}
					d.setIntervalos(listaints);
				}
				objeto.add(d);
			}else{
				DispositivoEstandar d = new DispositivoEstandar();
				d.setNombreDisp(o.get("nombreDisp").toString());
				d.setEquipoConcreto(o.get("equipoConcreto").toString());
				d.setEsInteligente(false);
				d.setEsBajoConsumo(o.get("esBajoConsumo").getAsBoolean());
				d.setkWh(o.get("kWh").getAsDouble());
				d.setHorasUsoMax(o.get("horasUsoMax").getAsDouble());
				d.setHorasUsoMin(o.get("horasUsoMin").getAsDouble());
				objeto.add(d);
			}									
		}

		return objeto;
	}
	
	public static void serializar_disp(Dispositivo disp) throws IOException, InstantiationException, IllegalAccessException{
		
		List<Dispositivo> dispositivos = new ArrayList<>();
		dispositivos = deserializarDispositivos(Dispositivo.class,JsonManager.rutaJsonDisp);
		dispositivos.add(disp);
		System.out.println(dispositivos);
		
		try(Writer writer = new FileWriter(JsonManager.rutaJsonDisp)){
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(dispositivos,writer);
		}
		
	}
	
	public static void serializarListDisp(List<Dispositivo> disp){
		disp.forEach(d-> {
			try {
				serializar_disp(d);
			} catch (InstantiationException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}