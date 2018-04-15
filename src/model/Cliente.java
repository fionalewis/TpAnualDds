package model;

import java.util.List;

public class Cliente extends Usuario {
	
	private String tipoDoc;
	private int dni;
	private int telefono;
	private Categoria categoria = new Categoria();
	private List<Dispositivo> dispositivos;
	
	public void asignarCategoria() {
		int i = calcularConsumo(); 
		
		if(i <= 150) {
			categoria = new Categoria("R1", 18.76, 0.644);
			}
		if(150<i && i<=325) {
			categoria = new Categoria("R2", 35.32, 0.644);
			}
		if(325<i && i<=400) {
			categoria = new Categoria("R3", 60.71, 0.681);
			}
		if(400<i && i<=450) {
			categoria = new Categoria("R4", 71.74, 0.738);
			}
		if(450<i && i<=500) {
			categoria = new Categoria("R5", 110.38, 0.794);
			}
		if(500<i && i<=600) {
			categoria = new Categoria("R6", 220.75, 0.832);
			}
		if(600<i && i<=700) {
			categoria = new Categoria("R7", 443.59, 0.851);
			}
		if(700<i && i<=1400) {
			categoria = new Categoria("R8", 545.96, 0.851);
			}
		if(1400<i) {
			categoria = new Categoria("R9", 887.19, 0.851);
			}
	}

	private int calcularConsumo() {
		return conseguirConsumos().stream().mapToInt(Integer::intValue).sum();
	}

	private List<Integer> conseguirConsumos() {
		return ((List<Integer>) dispositivos.stream().map(e -> e.getConsumoXHora()));
	}

}
