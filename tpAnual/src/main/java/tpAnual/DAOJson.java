package tpAnual;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class DAOJson {
	
	public static Object deserializarObjeto(Class clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(ruta));
		
		Object objeto = clase.newInstance();
		
		objeto = gson.fromJson(reader, objeto.getClass());
		return objeto;
	}
	
	public static <T> List<T> deserializarLista(Class<T> clase, String ruta) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(ruta));
		
		Type listType = new TypeToken<ArrayList<T>>(){}.getType();
		List<T> objetos = gson.fromJson(reader, listType);
		
		return objetos;
	}
}