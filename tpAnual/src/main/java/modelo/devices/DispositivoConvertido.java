package modelo.devices;

public class DispositivoConvertido extends DispositivoInteligente {
	
	private Dispositivo dispOriginal;

	//Constructor actualizado para el factory
	
	public DispositivoConvertido(Dispositivo disp) {
		super();
		setNombreDisp(disp.getNombreDisp());
		setEquipoConcreto(disp.getEquipoConcreto());
		setkWh(disp.getkWh());
		setkWhAhorro(disp.getkWh());
		setDispOriginal(disp);
		setHorasUsoMin(disp.getHorasUsoMin());
		setHorasUsoMax(disp.getHorasUsoMax());
		setEsInteligente(true);
		setEsBajoConsumo(disp.getEsBajoConsumo());		
	}
	
	public DispositivoConvertido(Dispositivo disp,int y,int m,int d,int h,int min,int s) {
		super(y,m,d,h,min,s);
		setNombreDisp(disp.getNombreDisp());
		setEquipoConcreto(disp.getEquipoConcreto());
		setkWh(disp.getkWh());
		setkWhAhorro(disp.getkWh());
		setDispOriginal(disp);
		setHorasUsoMin(disp.getHorasUsoMin());
		setHorasUsoMax(disp.getHorasUsoMax());
		setEsInteligente(true);
		setEsBajoConsumo(disp.getEsBajoConsumo());		
	}
	
	public Dispositivo getDispOriginal() {
		return dispOriginal;
	}
	public void setDispOriginal(Dispositivo dispOriginal) {
		this.dispOriginal = dispOriginal;
	}

}