package gurankio;

import gurankio.io.file.XmlFile;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		XmlFile xml = new XmlFile();

		File file = new File("triangolo.xml");
		Triangolo t = new Triangolo(
				new Punto(-1.87, 0),
				new Punto(5, 1.5),
				new Punto(1.54, 5)
		);

		System.out.println("Prima di salvare: ");
		System.out.println(t);
		try {
			xml.save(t, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Triangolo caricato = null;
		try {
			caricato = xml.load(file, Triangolo.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Caricato da file: ");
		System.out.println(caricato);

	}

}
