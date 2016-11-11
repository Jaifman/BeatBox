package practicaJavaSound;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
//Importaci�n de paquetes necesarios
=======
//Importación de paquetes necesarios
>>>>>>> branch 'master' of https://github.com/Jaifman/BeatBox.git
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BeatBox {
	
	//Declaración de variables sin inicializar
	JPanel laminaPrincipal;
	ArrayList<JCheckBox> listaCheckBox;
	Sequencer secuenciador;
	Sequence secuencia;
	Track pista;
	JFrame miMarco;
	
	//Declaración de array iniicalizado con los nombres de los instrumentos
	String[] nombreInstrumentos = {
			
			"Bass Drum",
			"Closed Hi-Hat",
			"Open Hi-Hat",
			"Acosutic Snare",
			"Crash Cymbal",
			"Hand Clap",
			"High Top",
			"Hi Bongo",
			"Maracas",
			"Whistle",
			"Low Conga",
			"Cowbell",
			"Vibraslap",
			"Low-Mid Tom",
			"High Agogo",
			"Open Hi Conga"
			
	};
	
	//Cada número del array es una nota distinta
	int[] instrumentos = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	//Método main que creará la instancia de la clase con llamada al constructor de la GUI
	public static void main(String[] args) {
		
		new BeatBox().construyeInterface();
		
	}
	
	//Método Constructor de la GUI
	public void construyeInterface(){
		
		//Creamos el Marco y su modo de cierre
		miMarco = new JFrame("Cyber BeatBox");
		miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creamos la lamina para el fondo y le ponemos un borde vacío por estética
		JPanel laminaFondo = new JPanel(new BorderLayout());
		laminaFondo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//Creamos los checkboxes
		listaCheckBox = new ArrayList<JCheckBox>();
		
		//Creamos una caja para botones Play/Stop/Tempo
		Box botonCaja = new Box(BoxLayout.Y_AXIS);
		
		//Creamos y añadimos botón de Play a la caja y lo ponemos a la escucha
		JButton play = new JButton("Play");
		play.addActionListener(new MyPlayListener());
		botonCaja.add(play);
		
		//Creamos y añadimos botón de Stop a la caja y lo ponemos a la escucha
		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		botonCaja.add(stop);
		
		//Creamos y añadimos botón de +Tempo a la caja y lo ponemos a la escucha
		JButton masTempo = new JButton("+Tempo");
		masTempo.addActionListener(new MyUpTempoListener());
		botonCaja.add(masTempo);
		
		//Creamos y añadimos botón de +Tempo a la caja y lo ponemos a la escucha
		JButton menosTempo = new JButton("-Tempo");
		menosTempo.addActionListener(new MyDownTempoListener());
		botonCaja.add(menosTempo);
		
		////Creamos y a�adimos bot�n de Guardar a la caja y lo ponemos a la escucha
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new MySendListener());
		botonCaja.add(guardar);
		
		//Creamos caja y le añadimos etiquetas con los nombres de los instrumentos
		Box cajasNombres = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < instrumentos.length; i++) {
			
			cajasNombres.add(new Label(nombreInstrumentos[i]));
			
		}
		
		//Añadimos las cajas a la lámina de fondo y las situamos
		laminaFondo.add(BorderLayout.EAST,botonCaja);
		laminaFondo.add(BorderLayout.WEST,cajasNombres);
		
		//Añadimos la lamina de fondo al marco
		miMarco.getContentPane().add(laminaFondo);
		
		//Creamos una disposicion en cuadricula para los checkboxes
		GridLayout cuadricula = new GridLayout(16,16);
		cuadricula.setVgap(1);
		cuadricula.setHgap(2);
		
		//Creamos la lámina principal que contendrá a la cuadricula y la añadimos a la lámina de fondo
		laminaPrincipal = new JPanel(cuadricula);
		laminaFondo.add(BorderLayout.CENTER,laminaPrincipal);
		
		//Creamos los 256 CheckBox y los añadimos al ArrayList y a la lámina
		for (int i = 0; i < 256; i++) {
			
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			listaCheckBox.add(c);
			laminaPrincipal.add(c);
			
		}
		
		//Llamada al método que se encarga de lo relacionado con los Midi
		setUpMidi();
		
		//Establecemos tamaño del marco, su posición, lo hacemos flexible y lo hacemos visible
		miMarco.setBounds(50,50,300,300);
		miMarco.pack();		
		miMarco.setVisible(true);
		
	}
	
	//Creamos método que maneja todo lo relacionado con Midi
	public void setUpMidi(){
		
		try {
			
			//Obtenemos un secuenciador y lo abrimos
			secuenciador = MidiSystem.getSequencer();
			secuenciador.open();
			
			//Creamos una secuencia a partir de la cual creamos la pista
			secuencia = new Sequence(Sequence.PPQ,4);
			pista = secuencia.createTrack();
			
			//Establecemos tempo por defecto del secuenciador
			secuenciador.setTempoInBPM(120);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//Método que compone las pistas
	public void componerPista(){
		
		//Array que manejará los intrumentos a reproducir
		int[] trackList = null;
		
		//Borrar pista anterior y crear nueva
		secuencia.deleteTrack(pista);
		pista = secuencia.createTrack();
		
		//Manejo de las 16 filas de instrumentos
		for (int i = 0; i < 16; i++) {
			
			trackList = new int [16];
			
			//Variable para almacenar Instrumentos seleccionados
			int notaInstrumento = instrumentos[i];
			
			//Manejo de las 16 columnas de notas
			for (int j = 0; j < 16; j++) {
				
				JCheckBox jc = listaCheckBox.get(j+16*i);
				
				//Código para saber si esa nota está seleccionado o hay silencio
				if (jc.isSelected()) {
					
					trackList[j] = notaInstrumento;
					
				}else{
					
					trackList[j] = 0;
					
				}
				
			}
			
			//Construimos la pista con lo que haya seleccionado y la añadimos a la pista que estamos creando
			construyePistas(trackList);			
			pista.add(construyeEvento(176,1,127,0,16));
			
		}
		
		//Para asegurar que hay siempre un evento en la nota 16
		pista.add(construyeEvento(192,9,1,0,15));
		
		//Establecemos que sea un bucle continuo
		try {
			
			secuenciador.setSequence(secuencia);
			secuenciador.setLoopCount(secuenciador.LOOP_CONTINUOUSLY);
			secuenciador.start();
			secuenciador.setTempoInBPM(120);
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
	//3 Clases internas para manejar los eventos de los botones
	public class MyPlayListener implements ActionListener{

		public void actionPerformed(ActionEvent a) {
			
			componerPista();
			
		}		
		
	}
	
	public class MyStopListener implements ActionListener{

		public void actionPerformed(ActionEvent a) {
			
			secuenciador.stop();
			
		}		
		
	}
	
	public class MyUpTempoListener implements ActionListener{

		public void actionPerformed(ActionEvent a) {
			
			float tempoFactor = secuenciador.getTempoFactor();
			secuenciador.setTempoFactor((float)(tempoFactor*1.03));
			
		}		
		
	}
	
	public class MyDownTempoListener implements ActionListener{

		public void actionPerformed(ActionEvent a) {
			
			float tempoFactor = secuenciador.getTempoFactor();
			secuenciador.setTempoFactor((float)(tempoFactor*.97));
			
		}		
		
	}
	
	public class MySendListener implements ActionListener{

		public void actionPerformed(ActionEvent a) {
			
			boolean [] estadoCheckBox = new boolean[256];
			
			for (int i = 0; i < 256; i++) {
				
				JCheckBox check = (JCheckBox) listaCheckBox.get(i);
				
				if(check.isSelected()){
					
					estadoCheckBox[i] = true;
					
				}
				
<<<<<<< HEAD
			}
			try {
=======
			}try {
>>>>>>> branch 'master' of https://github.com/Jaifman/BeatBox.git
				
				FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		}
		
	}
	
	//Construye eventos para cada uno de los 16 instrumentos
	public void construyePistas(int[]lista){
		
		for (int i = 0; i < 16; i++) {
			
			int notaInstrumentos = lista[i];
			
			if (notaInstrumentos != 0) {
				
				pista.add(construyeEvento(144,9,notaInstrumentos,100,i));
				pista.add(construyeEvento(128,9,notaInstrumentos,100,i+1));
				
			}
			
		}
		
	}
	
	public MidiEvent construyeEvento(int comd,int chan,int one,int two,int tick){
		
		MidiEvent evento = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			evento = new MidiEvent(a,tick);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return evento;
		
	}

}
