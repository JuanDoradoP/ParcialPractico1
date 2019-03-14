package control;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSingleton extends Thread{
	
	
	private static ServerSingleton instance;
	
	public final static int NUMERO_DE_JUGADORES = 3;
	
	private ServerSocket serverSocket;
	
	//Declaro la interface observer
	private TCPConnectionServidor.MessageObserver observer;
	
	//Declaro un arraylist de conexiones servidor
	private ArrayList<TCPConnectionServidor> conexionesServidor;
	

	//Me retorna la instancia de serverSingleton
	public static ServerSingleton getIntance(){
		if (instance == null) {
			instance = new ServerSingleton();
		}
		return instance;
	}
	
	//Inicializo la clase Server singleton
	public ServerSingleton() {
		
		try {
			conexionesServidor = new ArrayList<TCPConnectionServidor>();
			serverSocket = new ServerSocket(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try {
			
			//Siempre me agrega conexios del cliente a la contenedora de conexiones de servidor
			
			int numJugadores = 0;
			
			while(true){
				
				if (numJugadores < NUMERO_DE_JUGADORES) {
					
					System.out.println("Esperando conexi�n");
					Socket socket = serverSocket.accept();
					System.out.println("Conexi�n establecida con: "+ socket.getInetAddress().getHostAddress());
					
					TCPConnectionServidor conexion = new TCPConnectionServidor(socket);
					conexion.setObserver(observer);
					conexion.activarRecepcion();
					
					//Lo agrego a la contenedora
					conexionesServidor.add(conexion);
					
					//Env�o la IP de la nueva persona Conectada 
					//conexionesServidor.get(numJugadores).send("IP "+socket.getInetAddress().toString());
					
					numJugadores++;
					
					System.out.println("Se conecto  ::------"+socket.getInetAddress().toString());
					
					System.out.println("Hay " + numJugadores +" conectados");
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			observer.onMessageRecive("Un cliente se desconecto:");
		}
	}
	
	
	//set observer
	
	public void setObserver( TCPConnectionServidor.MessageObserver observer){
		this.observer = observer;
	}
	
	public void comenzarHilo(){
		this.start();
	}

	public ArrayList<TCPConnectionServidor> getConexionesClientes(){
		return conexionesServidor;
	}
	
	
}
