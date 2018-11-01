package modelo.users;

import modelo.FilterPredicates;
import modelo.JsonManager;
import modelo.MetodoSimplex;
import modelo.deviceState.AhorroEnergia;
import modelo.deviceState.Encendido;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.repositories.*;
import modelo.geoLocation.GeoLocation;
import modelo.geoLocation.Transformador;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.*;

import java.util.function.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import org.apache.commons.math3.optim.PointValuePair;

//Ojo las rutas de json!!! (ver JsonManager)

@Entity
public class Reporte {

	@Id @GeneratedValue
	public Long id;

	private String fecha;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="cliente_id", referencedColumnName="cliente_id", nullable=true, unique=false)
	private Cliente cliente;

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="transformador_id", referencedColumnName="transformador_id", nullable=true, unique=false)
	private Transformador transf;

	//@ManyToOne(cascade=CascadeType.ALL)
    //@JoinColumn(name="dispositivo", referencedColumnName="TIPO", nullable=true, unique=false)
	private String tipo_dispositivo;
	
	private double consumo = 0;

	public double getConsumo() {
		return consumo;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Reporte(){
		this.fecha = LocalDate.now().toString();		
	}

	public List<Cliente> listaParaCalcularConsumosClientes() {
		return ClienteRepository.getTodosLosClientes();
	}

	public List<Double> listaParaCalcularConsumosTransformadores() {
		List<Transformador> transformadores = TransformadorRepository.getListaTranformadores();
		List<Double> lista = new ArrayList<Double>(Collections.nCopies(transformadores.size(), 0.0));
		return lista;
	}

	public long CalculoConsumoDispsEstandares() {
		List<Dispositivo> dispositivos = DispositivoRepository.getListaDispositivos("estandares");
		return dispositivos.stream().map(Dispositivo::consumoTotal).count();
	}

	public long CalculoConsumoDispsInteligentes() {
		List<Dispositivo> dispositivos = DispositivoRepository.getListaDispositivos("inteligentes"); // tambien trae los convertidos
		return dispositivos.stream().map(Dispositivo::consumoTotal).count();
	}

	public void jobReporte(int intervalo){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		@Override
		public void run() {
			List<Cliente> cli = listaParaCalcularConsumosClientes();
			List<Double> consumosTransformadores = listaParaCalcularConsumosTransformadores();

			for (Cliente c : cli) {				
				ReporteRepository.addReporte(new Reporte(), c);	

				if (c.getTransformadorActual() != null){ 
					int i = c.getTransformadorActual().getIdTransformador() - 1;
					consumosTransformadores.set( i, consumosTransformadores.get(i) + c.calcularConsumo());
				}
			}

			for (int j = 0; j<consumosTransformadores.size(); j++) {				
				ReporteRepository.addReporte(new Reporte(), j+1, consumosTransformadores.get(j));
			}

			ReporteRepository.addReporte(new Reporte(), "estandares", CalculoConsumoDispsEstandares());
			ReporteRepository.addReporte(new Reporte(), "inteligentes", CalculoConsumoDispsInteligentes());
			//System.out.println("estoy corriendo");
		}
		}, 0, intervalo); // es en milisegundos 
	}
	
}