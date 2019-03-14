package vista;

import java.awt.GridLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import control.TCPConnectionServidor;

public class VentanaServidor extends JFrame implements TCPConnectionServidor.MessageObserver{
	
	
private JTextArea textArea;
	
	private JScrollPane scroll;
	
	
	public VentanaServidor(){
	
		
		setTitle("Vista Servidor::");
		
		setLayout(new GridLayout(0, 1));
		setSize(500, 400);
		
		textArea = new JTextArea(4,1);
	
		//textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
		
		
		scroll = new JScrollPane(textArea);
		add(scroll);
		textArea.setText("Mensajes Recibidos mandados desde el Servidor:"+"\n");
		
		try {
			InetAddress adressLocal = InetAddress.getLocalHost();
			textArea.append("La direccion ip del Servidor es: "+adressLocal.getHostAddress()+"\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		textArea.setEditable(false);
		
		
	}


	@Override
	public void onMessageRecive(String mensaje) {
		// TODO Auto-generated method stub
		
		//Recibo los mensajes que llegan del servidor
		//Los guardo en la variable botonSeleccionado
		textArea.append("Ha ocurrido lo siguiente: "+mensaje+"\n");
		
	}
	

}
