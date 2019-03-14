package control;

import java.io.BufferedInputStream;
//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import control.TCPConnectionServidor.MessageObserver;
import control.TCPConnectionServidor.Receptor;
import modelo.Casino;


public class TCPConnectionServidor {

	public final static int NUMERO_DE_CARTAS= 7;

	private static Casino casino;

	private  int ronda = 1;

	//Indica cuantas cartas lleva el jugador
	private int numCartas;

	//Atributo
	private String nombreDireccionCliente;

	//Puerta hacia el otro lado;
	private Socket socket;


	//Instancia observer
	private MessageObserver observer;


	//Instancia de receptor
	private Receptor receptor;

	public TCPConnectionServidor(Socket socket) {

		this.socket = socket;

		this.receptor = new Receptor(socket);

		numCartas = 0;

		casino = new Casino();

		cargarArchivo();

		nombreDireccionCliente = this.socket.getInetAddress().getHostAddress();
	}

	public static void cargarArchivo(){
		File archivo = new File("./Mazo/cartas.txt");
		try {
			casino.cargarInformacionCaninos(archivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//Patron observer ////////////////////////////////////

	public interface MessageObserver{
		public void onMessageRecive(String mensaje);
	}


	public void setObserver(MessageObserver observer) {
		this.observer = observer;
	}

	/////////////////////////////////////////////////////////////////

	//Receptor de mensajes: Es un Hilo


	public class Receptor extends Thread{

		Socket socket;

		public Receptor(Socket socket) {
			this.socket = socket;
		}


		//@Override
		public void run() {
			try {

				//Todo esto se puede en una linea
				InputStream is = socket.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				InputStreamReader isr = new InputStreamReader(bis);
				BufferedReader bufReader = new BufferedReader(isr);

				//Para leer siempre lo que entra
				while(true) {

					while(numCartas<NUMERO_DE_CARTAS){
						String pregunta = "Pregunta";
						send(pregunta);

						String lineaRespuesta = bufReader.readLine();//Contiene la respuesta del cliente.
						observer.onMessageRecive("El usuario con    InetAdress    "+ socket.getInetAddress().getHostName()+ "Respondio: "+lineaRespuesta+"\n"); //Manda mensajes a la ventana

						if (lineaRespuesta.contains("Si")) {
							observer.onMessageRecive("El usuario con    InetAdress    "+ socket.getInetAddress().getHostName()+" ha decidido abandonar la partida :/");
						}else if (lineaRespuesta.contains("No")) {
							observer.onMessageRecive("El usuario con    InetAdress    "+ socket.getInetAddress().getHostName()+" Continua Online en la partida");

							//Envio la carta aleatoria

							if (ronda == 1) {
								String carta1 = casino.generarCartaAleatoria().toString();
								String carta2 = casino.generarCartaAleatoria().toString();
								String cartaAEnviar = "Ronda1"+" "+carta1+" "+carta2;
								send(cartaAEnviar);
								System.out.println("se envio la carta "+ cartaAEnviar.toString());
								numCartas+=2;
								ronda+=1;
							}else if (ronda == 2) {
								String carta1 = casino.generarCartaAleatoria().toString();
								String carta2 = casino.generarCartaAleatoria().toString();
								String carta3 = casino.generarCartaAleatoria().toString();
								String cartaAEnviar = carta1+" "+carta2+" "+carta3;
								send(cartaAEnviar);
								System.out.println("se envio la carta "+ cartaAEnviar.toString());
								numCartas+=3;
								ronda+=1;
							}else if (ronda == 3) {
								String cartaAEnviar = casino.generarCartaAleatoria().toString();
								send(cartaAEnviar);
								System.out.println("se envio la carta "+ cartaAEnviar.toString());
								numCartas++;
								ronda+=1;
							}else if (ronda == 4) {
								String cartaAEnviar = casino.generarCartaAleatoria().toString();
								send(cartaAEnviar);
								System.out.println("se envio la carta "+ cartaAEnviar.toString());
								numCartas++;
								ronda+=1;
							}

						}else{
							observer.onMessageRecive("El usuario con    InetAdress    "+ socket.getInetAddress().getHostName()+"No ha escrito una respuesta valida");
							System.out.println("Respuesta invalida");
						}


					}

				}


			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}


	}

	//inical el hilo del receptor
	public void activarRecepcion() {
		receptor.start();
	}


	public void send(String mensaje){

		try {

			System.out.println("Enviando mensaje Desde el servidor a: "+socket.getInetAddress().getHostAddress());
			//Importante

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(mensaje);
			pw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	public String getNombreDireccionCliente(){
		return nombreDireccionCliente;
	}



}
