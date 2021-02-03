package gurankio;

import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;
import gurankio.menu.io.ConsoleOutput;

import static java.lang.Math.sqrt;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static void main(String[] args) {
		Menu.hide.add(Double.class);
		Menu.hide.add(Double[].class);
		new Menu(Main::new).console();
	}

	public static Double[] equazioneGestita(double a, double b, double c) {
		try {
			return equazioneDiSecondoGrado(a, b, c);
		} catch (IllegalDegreeException illegalDegreeException) {
			try {
				return new Double[] { equazioneDiPrimoGrado(b, c) };
			} catch (UnknownSolutionException | NoSolutionException exception) {
				ConsoleOutput.println(exception.getMessage());
				return new Double[0];
			}
		} catch (NoSolutionException | IllegalDeltaException exception) {
			ConsoleOutput.println(exception.getMessage());
			return new Double[0];
		}
	}

	@MenuOptions.Hide
	public static Double[] equazioneDiSecondoGrado(double a, double b, double c) throws IllegalDegreeException, NoSolutionException, IllegalDeltaException{
		if (a == 0) {
			throw new IllegalDegreeException();
		}

		double delta = b * b - 4 * a * c;

		if (delta < 0) {
			throw new NoSolutionException();
		}

		if (delta >= 0) {
			Double[] r = new Double[2];
			r[0] = - b + sqrt(delta) / 2 * a;
			r[1] = - b - sqrt(delta) / 2 * a;
			return r;
		}

		throw new IllegalDeltaException();
	}

	@MenuOptions.Hide
	public static Double equazioneDiPrimoGrado(double b, double c) throws UnknownSolutionException, NoSolutionException {
		if (b == 0) {
			if (c == 0) throw new UnknownSolutionException();
			else throw new NoSolutionException();
		}
		return -c / b;
	}

}
