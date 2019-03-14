package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame{

	
	private JLabel imagen = null;
	private JButton btnIniciar = null;
	private JPanel aux = null;
	
	public VentanaPrincipal(){
		inicializarVentana();
		pack();
	}
	
	public void inicializarVentana(){
		setTitle("Ventana Casino Principal ::: POKER");
		setLayout(new BorderLayout());
		add(inicializaarImagen(), BorderLayout.CENTER);
		add(inicializarPanelAux(), BorderLayout.SOUTH);
	}
	
	public JLabel inicializaarImagen(){
		if (imagen==null) {
			imagen = new JLabel();
			ImageIcon foto = new ImageIcon("./Imagenes/banner.png");
			imagen.setIcon(foto);
		}
		return imagen;
	}
	
	
	public JPanel inicializarPanelAux(){
		if (aux==null) {
			aux = new JPanel();
			aux.setLayout(new FlowLayout());
			aux.add(inicializarBtnIniciar());
		}
		return aux;
	}
	
	public JButton inicializarBtnIniciar(){
		if (btnIniciar==null) {
			btnIniciar = new JButton("Iniciar Partida");
			btnIniciar.setBounds(new Rectangle(105, 99, 86, 26));
		}
		return btnIniciar;
	}

	public JLabel getImagen() {
		return imagen;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public JPanel getAux() {
		return aux;
	}
	
	
}
