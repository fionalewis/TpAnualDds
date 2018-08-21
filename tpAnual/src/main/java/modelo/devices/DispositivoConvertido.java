package modelo.devices;

public class DispositivoConvertido extends DispositivoInteligente {
	
	private DispositivoEstandar dispOriginal;

	public DispositivoConvertido(DispositivoEstandar disp) {
		super();
		//setTipoDisp(disp.getTipoDisp());
		//setDescrip(disp.getDescrip());
		setkWh(disp.getkWh());
		setkWhAhorro(disp.getkWh());
		setDispOriginal(disp);
		setHorasUsoMin(disp.getHorasUsoMin());
		setHorasUsoMax(disp.getHorasUsoMax());
		setEsInteligente(true); //no estoy segura de si esto corresponde
		setEsBajoConsumo(disp.getEsBajoConsumo());	
	}
	
	public DispositivoConvertido(DispositivoEstandar disp,int y,int m,int d,int h,int min,int s) {
		super(y,m,d,h,min,s);
		//setTipoDisp(disp.getTipoDisp());
		//setDescrip(disp.getDescrip());
		setkWh(disp.getkWh());
		setkWhAhorro(disp.getkWh());
		setDispOriginal(disp);
		setHorasUsoMin(disp.getHorasUsoMin());
		setHorasUsoMax(disp.getHorasUsoMax());
		setEsInteligente(true);
		setEsBajoConsumo(disp.getEsBajoConsumo());	
	}

	public DispositivoEstandar getDispOriginal() {
		return dispOriginal;
	}
	public void setDispOriginal(DispositivoEstandar dispOriginal) {
		this.dispOriginal = dispOriginal;
	}

}