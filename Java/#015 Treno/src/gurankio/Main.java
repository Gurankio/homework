package gurankio;

import gurankio.io.data.Persistent;
import gurankio.menu.io.MenuIO;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Main {

	private static Treno carica() {
		return Persistent.list(Treno.class).get(0);
	}

	private static Object salva(Object o) {
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

	public static void main(String[] args) throws IOException {
		Treno t = new Treno(Persistent.path("main", Treno.class));
		System.out.println(t);
		/*
		ArrayList<Vagone> v = new ArrayList<>();
		int i = 0;
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Merci("asd", ++i, "1", ++i, ++i, ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		v.add(new Passeggeri("asd", ++i, "1", ++i, "1", ++i, ++i));
		t.setVagoni(v);
		t.save();


		 */
		// System.out.println(Persistent.list(Treno.class));

		//System.out.println(Persistent.load(Treno.class));
		//MenuIO.registerOverride(Vagone.class, Main::creaVagone);
		//Menu.ignore(Object[].class);
		//new Menu(Main::carica).onClose(Main::salva).run();
	}

}
