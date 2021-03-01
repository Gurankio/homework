package gurankio;

import gurankio.io.data.Persistent;
import gurankio.menu.Menu;
import gurankio.menu.io.MenuIO;

import java.util.Arrays;

public class Main {

	private static Treno carica() {
		return (Treno) new Treno().load();
	}

	private static Object salva(Object o) {
		Persistent p = (Persistent) o;
		p.save();
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
