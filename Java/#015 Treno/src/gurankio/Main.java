package gurankio;

import gurankio.io.data.Persistent;
import gurankio.menu.io.MenuIO;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class Main {

	/*
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

	//Treno t = new Treno(Persistent.path("main", Treno.class));
    //System.out.println(t);


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

	// System.out.println(Persistent.list(Treno.class));

	//System.out.println(Persistent.load(Treno.class));
	//MenuIO.registerOverride(Vagone.class, Main::creaVagone);
	//Menu.ignore(Object[].class);
	//new Menu(Main::carica).onClose(Main::salva).run();
	*/

	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();

		SwingTerminal terminal = new SwingTerminal();
		terminal.setCursor(24, 12);
		terminal.setColor(Color.red);
		terminal.println("asdfopiaso0fsaoifjaspojopasjsajopfsaojpfjoasp");

		timer.scheduleAtFixedRate(new TimerTask() {
			Random random = new Random();

			@Override
			public void run() {
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.print("  gay  ");
				terminal.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				terminal.println("  gay  ");
			}
		}, 100, 100);
	}

}
