package modelo.devices;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class IntervaloDispositivo {
	public enum modo {AHORRO, NORMAL};

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	@Enumerated(EnumType.STRING)
	private modo modo;

	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="dispositivo_id", referencedColumnName="dispositivo_id", nullable=true, unique=false)
    private Dispositivo dispositivo;
	
	public IntervaloDispositivo() {}
	
	public IntervaloDispositivo(LocalDateTime inicioInt,modo mod) {
		setInicio(inicioInt);
		setModo(mod);
	}
	public IntervaloDispositivo(int y,int m,int d,int h,int min,int sec,modo mod) {
		setInicio(LocalDateTime.of(y,m,d,h,min,sec));
		setModo(mod);
	}
	public Long getId() {
		return id;
	}
	public LocalDateTime getInicio() {
		return inicio;
	}
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	public LocalDateTime getFin() {
		return fin;
	}
	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	public modo getModo() {
		return modo;
	}
	public void setModo(modo modo) {
		this.modo = modo;
	}
	
	public double calculoDeHoras() {
		if(fin != null){
		Duration period = Duration.between(inicio,fin);
        double periodSeconds = period.getSeconds();
        double horasDeUso = periodSeconds/3600;
        return horasDeUso;
		} else {
			Duration period = Duration.between(inicio,LocalDateTime.now());
	        double periodSeconds = period.getSeconds();
	        double horasDeUso = periodSeconds/3600;
	        return horasDeUso;
			
		}
	}
	
}