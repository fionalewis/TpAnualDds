package modelo.devices;

public class RecomendacionDTO {
		
	public RecomendacionDTO(String key, Double value2) {
		dispositivo = key;
		value = value2;
	}
	String dispositivo;
	double value;
	
	public String getDispositivo() {
		return dispositivo;
	}
	
	public double getValue() {
		return value;
	}
}
