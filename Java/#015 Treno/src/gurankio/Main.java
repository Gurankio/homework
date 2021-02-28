package gurankio;

import gurankio.io.file.FileInterface;
import gurankio.io.file.TextFile;
import gurankio.io.file.XmlFile;
import gurankio.io.text.TextParser;
import gurankio.io.text.TextSerializer;
import gurankio.menu.Menu;
import gurankio.menu.io.MenuIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

	private static final File file = new File("./treno.txt");
	private static final FileInterface fileInterface = new TextFile();

	private static Treno carica() {
		Treno treno = null;
		try {
			treno = fileInterface.load(file, Treno.class);
		} catch (FileNotFoundException ignored) {
		}
		if (treno == null) {
			treno = new Treno();
		}
		return treno;
	}

	private static Object salva(Object o) {
		try {
			System.out.println("Saving...");
			fileInterface.save(o, file);
		} catch (FileNotFoundException e) {
			System.out.println("Failed.");
			return o;
		}
		System.out.println("Succeeded.");
		return o;
	}

	private static Vagone creaVagone(MenuIO io) {
		switch (io.choose("Tipo do vagone: ", Arrays.asList("MERCI", "PASSEGGIERI"))) {
			case "MERCI":
				return io.read("", Merci.class);
			case "PASSEGGIERI":
				return io.read("", Passeggeri.class);
			default:
				return null;
		}
	}

	public static void main(String[] args) {
		MenuIO.registerOverride(Vagone.class, Main::creaVagone);
		Menu.ignore(Object[].class);
		new Menu(Main::carica).onClose(Main::salva).run();
	}

}
