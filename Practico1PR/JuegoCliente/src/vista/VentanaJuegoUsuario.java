package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import control.TCPConecctionCliente;

public class VentanaJuegoUsuario extends JFrame implements TCPConecctionCliente.MessageObserver{
	
	
	public final static int COLUMS_JUGADOR = 2; //Maximo de cartas Jugador
	public final static int COLUMS_EN_COMUN = 5; //Maximo de en comun
	
	
	private int indice =0;
	
	private JLabel imagenBanner = null;
	
	private JLabel jugadorActual = null;
	private JLabel numCartas = null;
	private JLabel otrosJugadores = null;
	
	private JTextField txtJugadorActual = null;
	private JTextField txtTotalCartas= null;
	private JTextField txtOtrosJugadores = null;
	
	private JPanel panelAuxCentro = null;
	private JPanel panelAuxCartas= null;
	
	private JTextField[] cartasJugador = null;
	
	private JTextField[] cartasComunitarias = null;
	
	
	
	public VentanaJuegoUsuario(){
		inicializarVentana();
		//pack();
		indice = 0;
	}
	
	
	public void inicializarVentana(){
		setTitle("Online");
		setSize(1000,300);
		setLayout(new BorderLayout());
		add(inicializarImagen() , BorderLayout.NORTH);
		add(inicializarPanelAuxCentro() , BorderLayout.CENTER);
		add(inicializarPanelAuxCartas() , BorderLayout.SOUTH);
	}
	
	public JLabel inicializarImagen(){
		if(imagenBanner==null){
			imagenBanner = new JLabel();
			ImageIcon foto = new ImageIcon("./Imagenes/banner1.png");
			imagenBanner.setIcon(foto);
		}
		return imagenBanner;
	}
	
	public JPanel inicializarPanelAuxCentro(){
		if (panelAuxCentro==null) {
			panelAuxCentro = new JPanel(new GridLayout(1, 2,6,6));
			
			JPanel aux1 = new JPanel(new GridLayout(3,2,5,5));
			aux1.setBorder(new TitledBorder("Información Jugador:"));
		    jugadorActual = new JLabel("Jugador :");
		    numCartas = new JLabel(" # Cartas: ");
		    otrosJugadores = new JLabel("En partida :");
		    
		    aux1.add(jugadorActual);
		    aux1.add(inicializartxtJugador());
		    aux1.add(numCartas);
		    aux1.add(inicializartxtNumCartas());
		    aux1.add(otrosJugadores);
		    aux1.add(inicializartxtOtrosJugador());
		    
		    JPanel aux2 = new JPanel(new BorderLayout());
		    JLabel img = new JLabel();
		    ImageIcon fot = new ImageIcon("./Imagenes/casino.jpg");
		    img.setIcon(fot);
		    aux2.add(img, BorderLayout.CENTER);
		    
		    panelAuxCentro.add(aux1);
		    panelAuxCentro.add(aux2);
		    
		}
		return panelAuxCentro;
	}
	
	public JTextField inicializartxtJugador(){
		if (txtJugadorActual==null) {
			txtJugadorActual = new JTextField("");
			txtJugadorActual.setEditable(false);
		}
		return txtJugadorActual;
	}
	
	public JTextField inicializartxtNumCartas(){
		if (txtTotalCartas==null) {
			txtTotalCartas = new JTextField("0");
			txtTotalCartas.setEditable(false);
		}
		return txtTotalCartas;
	}
	
	public JTextField inicializartxtOtrosJugador(){
		if (txtOtrosJugadores==null) {
			txtOtrosJugadores = new JTextField("");
			txtOtrosJugadores.setEditable(false);
		}
		return txtOtrosJugadores;
	}
	
	
	public JPanel inicializarPanelAuxCartas(){
		if (panelAuxCartas==null) {
			panelAuxCartas = new JPanel(new GridLayout(2, 1,6,6));
			
			
			JPanel panelCartaPlayer = new JPanel();
			panelCartaPlayer.setLayout(new GridLayout(1, COLUMS_JUGADOR,7,7));
			panelCartaPlayer.setBorder(new TitledBorder("Cartas Único jugador "));
			
			panelAuxCartas.setBorder(new TitledBorder("Informacion sobre las cartas "));
			
			inicializarTxtCartas();
			
			for (int i = 0; i < cartasJugador.length; i++) {
				panelCartaPlayer.add(cartasJugador[i]);
			}
			
			JPanel panelCartasEnComun = new JPanel();
			panelCartasEnComun.setLayout(new GridLayout(1, COLUMS_EN_COMUN,7,7));
			panelCartasEnComun.setBorder(new TitledBorder("Cartas en común "));
			
			inicializarTxtCartasEnComun();
			
			for (int i = 0; i < cartasComunitarias.length; i++) {
				panelCartasEnComun.add(cartasComunitarias[i]);
			}
			
			panelAuxCartas.add(panelCartaPlayer);
			panelAuxCartas.add(panelCartasEnComun);
			
		}
		return panelAuxCartas;
	}
	
	public JTextField[] inicializarTxtCartas(){
		if (cartasJugador==null) {
			cartasJugador = new JTextField[COLUMS_JUGADOR];
			for (int i = 0; i < cartasJugador.length; i++) {
				JTextField jt = new JTextField("-");
				jt.setEditable(false);
				cartasJugador[i]=jt;
			}
		}
		return cartasJugador;
	}
	
	public JTextField[] inicializarTxtCartasEnComun(){
		if (cartasComunitarias==null) {
			cartasComunitarias = new JTextField[COLUMS_EN_COMUN];
			for (int i = 0; i < cartasComunitarias.length; i++) {
				JTextField jt = new JTextField("-");
				jt.setEditable(false);
				cartasComunitarias[i]=jt;
			}
		}
		return cartasComunitarias;
	}


	@Override
	public void onMessageReciver(String mensaje) {
		
		if (mensaje.contains("Pregunta")) {
			System.out.println("Mostrar pregunta");
		}else if(mensaje.split(" ")[0].contains("Ronda1")){

			//Separo las cartas que me lleguen
			String []split = mensaje.split(" ");
			
			//LAs agrego al arreglo de cartas del jugador
			int posicion = 0;
			
			//indice = posicion;
			
			while(posicion < cartasJugador.length){
				cartasJugador[posicion].setText(split[posicion+1]);
			    posicion++;
			}
				
			actualizarValorNumeroDeCartasJugador();
			
		}
		
//		else if (mensaje.split(" ")[0].contains("IP")) {
//			
//			
//			actualizarTxtOtrosJugadores(mensaje.split(" ")[1]);
			
			
		else{
			//Va para las comunitarias
			
			String []split = mensaje.split(" ");
			
			//LAs agrego al arreglo de cartas del jugador
			int posicion = 0;
			
			while(posicion < split.length){
				cartasComunitarias[indice].setText(split[posicion]);
			    posicion++;
			    indice++;
			    //indice = posicion;
			}

		}
		
		
	}
	
	
	public String respuestaPreguntaDeseaContinuar(){
		String respuesta = JOptionPane.showInputDialog("¿Desea Abandonar la partida?");
		return respuesta;
	}


	public JTextField getTxtJugadorActual() {
		return txtJugadorActual;
	}


	public JTextField getTxtTotalCartas() {
		return txtTotalCartas;
	}


	public JTextField getTxtOtrosJugadores() {
		return txtOtrosJugadores;
	}
	
	public void actualizarValorNumeroDeCartasJugador(){
		int contador = 0;
		for (int i = 0; i < cartasJugador.length; i++) {
			if (!(cartasJugador[i].getText().equals("-"))) {
				contador++;
			}
		}
		txtTotalCartas.setText(contador+"");
	}
	
	
	public void actualizarValorNumeroDeCartasEnComun(){
		int contador = 0;
		for (int i = 0; i < cartasComunitarias.length; i++) {
			if (!(cartasComunitarias[i].getText().equals("-"))) {
				contador++;
			}
		}
		txtTotalCartas.setText(contador+"");
	}

	private int getIndice(){
		return indice;
	}
	
	public void actualizarTxtOtrosJugadores(String ipJugadorNuevo){
		String str = txtOtrosJugadores.getText()+" - ";
		str+=ipJugadorNuevo;
		txtOtrosJugadores.setText(str);
	}
	
}
