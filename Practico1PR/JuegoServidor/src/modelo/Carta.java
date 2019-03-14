package modelo;

public class Carta {
	
	public final static String PICAS ="P";
	public final static String CORAZONES ="C";
	public final static String TREBOL ="T";
	public final static String DIAMANTE ="D";
	
	private String tipo;
	private String valor;
	
	//Proximamente
	//private String rutaImg;
	
	public Carta(String valor, String tipo) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String toString(){
		return valor+tipo;
	}
	

}
