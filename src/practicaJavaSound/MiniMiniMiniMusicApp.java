package practicaJavaSound;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class MusicTest1 {
	
	//Creamos el m�todo que reproducir� los sonisos
	public void reproducir(){
		
		//Bloque try, porque la clase no depende de nuestro c�digo y puede sar error
		try {
			
			//Creamos el secuenciador
			Sequencer secuenciador = MidiSystem.getSequencer();
			
			System.out.println("Hemos obtenido un secuenciador satisfactoriamente");
			
		} catch (MidiUnavailableException e) {//Manejo de la posible excepci�n
			
			System.out.println("Error intentando obtener un secuenciador");
			
		}
		
	}
	
	public static void main(String[] args) {
		
		//Creamos objeto de nuestra clase para probar el programa
		MusicTest1 mt = new MusicTest1();
		
		//Reproducimos el objeto creado
		mt.reproducir();
		
	}

}
