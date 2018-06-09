package modelo.devices;

public class DispositivoConvertido extends DispositivoInteligente {
	
	private DispositivoEstandar dispOriginal;

	public DispositivoConvertido(DispositivoEstandar disp) {
		super();
		setNombreDisp(disp.getNombreDisp());
		setkWh(disp.getkWh());
		setDispOriginal(disp);
	}

	public DispositivoEstandar getDispOriginal() {
		return dispOriginal;
	}
	public void setDispOriginal(DispositivoEstandar dispOriginal) {
		this.dispOriginal = dispOriginal;
	}

}
