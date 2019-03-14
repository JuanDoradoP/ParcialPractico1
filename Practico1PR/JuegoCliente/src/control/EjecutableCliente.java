package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import vista.VentanaJuegoUsuario;
import vista.VentanaPrincipal;

public class EjecutableCliente {


	//Importo del paquete vista las clases que voy a usar

	private static VentanaPrincipal ventanaPrincipal;
	private static VentanaJuegoUsuario ventanaJuegoUsuario;

	private static TCPConecctionCliente conexionCliente;

    private final static int TOTAL_CARTAS = 5;

	//Hilo Principal//Ejecutable

	public static void main(String[] args) {


		//Establezco la conexion del cliente con el servidor por el puerto 5000 y la ip local

		//String adress = JOptionPane.showInputDialog("Escribe la direcci�n IP donde se encuenta el servidor");


		try {
			conexionCliente = conexionCliente.getInstance();
			//conexionCliente.setIp(adress);//LOCALHOST
			conexionCliente.setIp("127.0.0.1");
			conexionCliente.setPuerto(5000);
			conexionCliente.conectar();
		} catch (Exception e) {
			System.out.println("Conexion Erronea");
		}

		//Activo el receptor de cliente, para recibir loque me manda el serrvidor
		conexionCliente.activarReceptor();

		//Inicializo la ventana principal
		ventanaPrincipal = new VentanaPrincipal();
		escucharVentanaPrincipal();
		escucharBotonIniciarPartida();


		ventanaPrincipal.setVisible(true);



	}

	//-------------------
	public static void escucharVentanaPrincipal(){
		ventanaPrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}	
		});
	}

	public static void escucharBotonIniciarPartida(){
		ventanaPrincipal.getBtnIniciar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Cierro la ventana actual e inicialio la otra
				ventanaPrincipal.setVisible(false);
				ventanaJuegoUsuario = new VentanaJuegoUsuario();
				conexionCliente.setObserver(ventanaJuegoUsuario);
				ventanaJuegoUsuario.getTxtJugadorActual().setText(conexionCliente.getIp());
				ventanaJuegoUsuario.setVisible(true);


				//La pregunta se debe de hacer por cada ronda osea 7 veces si por cada ronda
				//se reparte una carta
				
				int count =0;

				while(count < TOTAL_CARTAS){
					String stringLlegada = conexionCliente.getMensajeLlegada();
					if (stringLlegada.contains("Pregunta")) {
						String answer = ventanaJuegoUsuario.respuestaPreguntaDeseaContinuar();
						conexionCliente.send(answer);
						System.out.println("Se envio la respuesta " + answer + " desde el cliente");
						count++;
					}else{
						conexionCliente.send(stringLlegada);
						System.out.println("Se envio, string llegada con: "+ stringLlegada);
					}
	
				}




			}
		});
	}




}
