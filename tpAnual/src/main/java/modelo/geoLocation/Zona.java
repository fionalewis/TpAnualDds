package modelo.geoLocation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Zona {
	@Id
	private String nombreZona;
	private double radius;
	private GeoLocation center;	
	@Transient
	private List<Transformador> transformadores = new ArrayList<>();
	
	public String getNombreZona() {
		return nombreZona;
	}
	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public GeoLocation getCenter() {
		return center;
	}
	public void setCenter(GeoLocation center) {
		this.center = center;
	}
	public List<Transformador> getTransformadores() {
		return transformadores;
	}
	public void setTransformadores(List<Transformador> transformadores) {
		this.transformadores = transformadores;
	}
	
	//Funcionalidades

	public boolean includesPoint(GeoLocation posAEvaluar) {
		return center.distanceTo(posAEvaluar) < radius;
	}
	
	public boolean isWithinXKm(float km,GeoLocation posAEvaluar) {
		return center.distanceTo(posAEvaluar) < km;
	}
	
	public double consumoActualZona() throws FileNotFoundException {
		double result = transformadores.stream().mapToDouble(unTransf-> unTransf.suministroActual()).sum();
		return result;
	}

}