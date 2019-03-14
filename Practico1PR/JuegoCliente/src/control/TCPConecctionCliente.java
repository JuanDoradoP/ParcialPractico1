package control;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import control.TCPConecctionCliente.MessageObserver;
import control.TCPConecctionCliente.Receptor;

public class TCPConecctionCliente {

	private String ip;
	private int puerto;
	private Socket socket;
	
	private String mensajeLlegada;
	
	private Receptor receptor;
	
	//Singleton
	private static TCPConecctionCliente instance;
	
	//Observer
	private MessageObserver observer;
	
	
	public TCPConecctionCliente(){

	}
	
	
	public static TCPConecctionCliente getInstance(){
		if (instance==null) {
			instance = new TCPConecctionCliente();
		}
		return instance;
	}
	
	//Inicializo el socket
	
	public void conectar(){
		
		try {
			socket = new Socket(ip, puerto);
			receptor = new Receptor(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Metodo que envia los mensajes al servidor
	
	public void send(String mensaje){
		
		try {
			
			System.out.println("Enviando mensaje Desde el cliente");
			//Importante
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(mensaje);
			pw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//Patron observer
	
	public interface MessageObserver{
		public void onMessageReciver(String mensaje);
	}
	
	public void setObserver(MessageObserver obs){
		this.observer = obs;
	}
	
	
	
	//Este receptor lo creo para recibir los mensajes que me mande el servidor de vuelta.
	
	public class Receptor extends Thread{
		
		Socket socket;
		
		public Receptor(Socket socket){
			this.socket = socket;
		}
		
		//@Override
		public void run(){
			
			try {
				
				BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				InputStreamReader reader = new InputStreamReader(bis);
				BufferedReader buffReader = new BufferedReader(reader);
				
				while(true){
					String infoCarta = buffReader.readLine(); //Lee el mensaje
					mensajeLlegada = infoCarta;
					
				//	System.out.println(mensajeLlegada+" estooooooooooooooo");
					
					if (infoCarta.equals("Pregunta")) {
						System.out.println("Hacer Pregunta desea continuar");
						//observer.onMessageReciver(infoCarta); //Lo manda a la interfaz VentanaJuegoUsuario
					}
//					else if (infoCarta.split(" ")[0].equals("IP")) {
//						observer.onMessageReciver(infoCarta.split(" ")[1]);
					else{
						System.out.println("Manda info carta");
//						String ronda = buffReader.readLine();
//						System.out.println(ronda);
						observer.onMessageReciver(infoCarta); //Lo manda a la interfaz VentanaJuegoUsuario
					}
					
				}	
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}
	
	
	
	public void activarReceptor(){
		receptor.start();
	}
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public String getMensajeLlegada(){
		return mensajeLlegada;
	}
	
}
