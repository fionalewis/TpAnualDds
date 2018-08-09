package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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

}