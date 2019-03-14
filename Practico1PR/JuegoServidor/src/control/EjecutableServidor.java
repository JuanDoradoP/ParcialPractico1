package control;

import vista.VentanaServidor;

public class EjecutableServidor {
	

	private static VentanaServidor ventanaServidor;

	private static ServerSingleton serverSingleton;

	public static void main(String[] args) {


		//Muestro la ventana del Servidor
		ventanaServidor = new VentanaServidor();
		ventanaServidor.setVisible(true);

		//INICIALIZO EL SERVIDOR PARA QUE SIEMPRE ESTE ESPERando la solicitud de un cliente

		// La clase server singleton en todo momento va
		//a estar esperando que alguien se conecte (Pueden ser varios)
		//Esa clase tiene un hilo que siempre espera a un cliente y establece la conexion

		serverSingleton = ServerSingleton.getIntance();
		

		//cambio el observer
		serverSingleton.setObserver(ventanaServidor);

		
		//Pongo a correr el hilo de la clase verser singleton
		serverSingleton.comenzarHilo();
		
	}



}
