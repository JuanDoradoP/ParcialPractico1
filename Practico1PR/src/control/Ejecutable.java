package control;

import vista.MiVentana;

public class Ejecutable {

	/**
	 * @param args
	 */

	// El control debe tener instancias del modelo y de la vista

	public static void main(String[] args) {
		
		MiVentana ven = new MiVentana();
		ven.isVisible();
	    
		
	}
}
