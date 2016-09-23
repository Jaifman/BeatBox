package practicaJavaSound;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MiniMiniMiniMusicApp {
	
	public static void main(String[] args) {
		
		//Creamos objeto de nuestra clase para probar el programa
		MiniMiniMiniMusicApp mini = new MiniMiniMiniMusicApp();
		
		//Reproducimos el objeto creado
		mini.reproducir();
		
	}
	
	//Creamos el m�todo que reproducir� los sonidos
	public void reproducir(){
		
		//Bloque try, porque la clase no depende de nuestro c�digo y puede sar error
		try {
			
			//Creamos el secuenciador y lo iniciamos para poder usarlo
			Sequencer reproductor = MidiSystem.getSequencer();
			reproductor.open();
			
			//Creamos la secuencia a reproducir
			Sequence secuencia = new Sequence(Sequence.PPQ,4);
			
			//Se pide una pista a la secuencia
			Track pista = secuencia.createTrack();
			
			//Creamos un mensaje MIDI y le pasamos par�metros
			ShortMessage a = new ShortMessage();
			a.setMessage(144,1,44,100);
			
			//Creamos el evento MIDI y le pasamos el mensaje anterior
			MidiEvent notaOn = new MidiEvent(a,1);
			
			//A�adimos el evento a la pista
			pista.add(notaOn);
						
			//Creamos un mensaje MIDI y le pasamos par�metros
			ShortMessage b = new ShortMessage();
			b.setMessage(128,1,44,100);
			
			//Creamos el evento MIDI y le pasamos el mensaje anterior
			MidiEvent notaOff = new MidiEvent(b,16);
			
			//A�adimos el evento a la pista
			pista.add(notaOff);
			
			//Pasa la secuencia al secuenciador
			reproductor.setSequence(secuencia);
			
			//Inicia la reproducci�n
			reproductor.start();
			
		//Manejo de la posible excepci�n				
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
