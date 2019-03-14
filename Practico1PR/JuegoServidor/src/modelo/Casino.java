package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Casino {
	
	public final static int TOTAL_MAZO = 52;
	
	
	
	private Carta[] listadoCartas;
	
	public Casino(){
		listadoCartas = new Carta[TOTAL_MAZO];
	}
	
	//Carga la informacion de los perros
		public void cargarInformacionCaninos(File archivo) throws IOException{
			
			try {
				
				FileReader reader = new FileReader(archivo);	
				BufferedReader br = new BufferedReader(reader);
				
				String linea = "";
				
				int contador = 0;
				
				while((linea = br.readLine()) != null){
                    
					//Primero leo el tipo de carta y luego las 13 cartas por tipo
					
					if (linea.charAt(0)!='#') {
						
					String []split = linea.split(" ");
					
					String tipo = split[0];
					
					for (int i = 1; i < split.length; i++) {
						Carta carta = new Carta(split[i], tipo);
						listadoCartas[contador] = carta;
						//System.out.println("--"+carta.toString());
						contador++;
					}
					
						
					}
					
				}
				
				br.close();
				System.out.println("Se cargaron exitosamente la informacion de las cartas");
				
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}
		}
	
	
		//Generar Carta aleatoria
		
		public Carta generarCartaAleatoria(){
			
			Carta carta = null;
			
			int numAleatorio = numeroAleatorio();
			
			while (listadoCartas[numAleatorio]==null) {
				numAleatorio = numeroAleatorio();
			}
			
			carta = listadoCartas[numAleatorio];
			
			return carta;
		}
		
		
		public int numeroAleatorio(){
			int numero = (int) (Math.random() * TOTAL_MAZO) + 1;
			return numero ;
		}
		

}
