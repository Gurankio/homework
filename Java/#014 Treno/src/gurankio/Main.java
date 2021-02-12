package gurankio;

import gurankio.io.exception.InvalidInputException;
import gurankio.io.file.BinaryFile;
import gurankio.io.file.FileInterface;
import gurankio.io.text.TextParser;
import gurankio.menu.Menu;
import gurankio.menu.io.ConsoleIO;
import gurankio.util.JShellEvalWrapper;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	private static final File dataFile = new File("./treno.bin");
	private static final FileInterface fileInterface = new BinaryFile();

	private static Treno tryLoading() {
		Treno treno = null;
		try {
			treno = fileInterface.load(dataFile, Treno.class);
		} catch (FileNotFoundException ignored) {
		}
		if (treno == null) {
			treno = new Treno();
		}
		return treno;
	}

	private static Object trySaving(Object o) {
		fileInterface.save(o, dataFile);
		System.out.println("Saving...");
		return o;
	}

	private static Vagone creaVagone(String data) {
		switch (data) {
			case "MERCI":
				return Menu.console.read("", Merci.class);
			case "PASSEGGIERI":
				return Menu.console.read("", Passeggeri.class);
			default:
				return null;
		}
	}

	public static void main(String[] args) {
		TextParser.registerSupplier(Vagone.class, Main::creaVagone);
		Menu.ignore(Object[].class);
		new Menu(Main::tryLoading).onClose(Main::trySaving).run();
	}

}
