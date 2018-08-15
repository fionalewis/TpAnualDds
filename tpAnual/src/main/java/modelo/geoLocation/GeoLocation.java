package modelo.geoLocation;

import java.io.FileNotFoundException;
import java.util.List;

import modelo.DAOJson;

public class GeoLocation {
	
	/* Cod. de: https://introcs.cs.princeton.edu/java/44st/Location.java.html */
	
	    private String name = "";
	    private double longitude;
	    private double latitude;   
	   
	    // create and initialize a point with given name and (latitude, longitude) specified in degrees
	    
	    public GeoLocation(String direcc) {
	    	this.name = direcc;
	    }
	    
	    public GeoLocation(double lat,double lng) {
	    	this.latitude = lat;
	    	this.longitude = lng;
	    }
	    
	    public GeoLocation(String name, double latitude, double longitude) {
	        this.name = name;
	        this.latitude  = latitude;
	        this.longitude = longitude;
	    }
	    
	    public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		// return distance between this location and that location measured in statute miles
		// A nosotros nos sirve en metros, asi que lo cambie y agregue el metodo en km tambien por las dudas
	    public double distanceTo(GeoLocation that) {
	        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	        double lat1 = Math.toRadians(this.latitude);
	        double lon1 = Math.toRadians(this.longitude);
	        double lat2 = Math.toRadians(that.latitude);
	        double lon2 = Math.toRadians(that.longitude);

	        // great circle distance in radians, using law of cosines formula
	        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
	                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

	        // each degree on a great circle of Earth is 60 nautical miles
	        double nauticalMiles = 60 * Math.toDegrees(angle);
	        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
	        return statuteMiles*1609.344; //en metros
	    }
	    
	    public double toKm(double distanceInMeters) {
	    	return distanceInMeters/1000;
	    }
	    
	    // return string representation of this point
	    public String toString() {
	        return name + " (" + latitude + ", " + longitude + ")";
	    }
	    
		public static Zona ubicacionDe(Object obj) { //Para ubicar la zona a la que pertenece el cliente o un transformador
			List<Zona> zonas = null;
			Zona unaZona = null;
			try {
				zonas = DAOJson.deserializarLista(Zona.class,"\\JSONs\\zonas.json");
				} catch (Exception e) {
			}
			GeoLocation aEvaluar = (obj instanceof GeoLocation) ? (GeoLocation) obj : ((Transformador) obj).getUbicacion();
			for(Zona z: zonas) {
				if(z.includesPoint(aEvaluar)) {
					unaZona = z;
				}
			}
			return unaZona;
		}
		
		public static Transformador transfMasCercanoA(GeoLocation unPunto)  {
			List<Transformador> transformadores = null;
			try {
				transformadores = DAOJson.deserializarLista(Transformador.class,"\\JSONs\\transformadores.json");
			} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			Transformador result = transformadores.get(0);	
			for(int i = 0; i<transformadores.size();i++) {
				if(unPunto.distanceTo(transformadores.get(i).getUbicacion())<unPunto.distanceTo(result.getUbicacion())) {
					result = transformadores.get(i);
				}
			}
			return result;
		}

}