package gurankio;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Main {

	private static Scanner scanner = new Scanner(System.in);

	private static Double readDouble(String prompt) {
		Double r = null;
		do {
			try {
				System.out.print(prompt);
				r = scanner.nextDouble();
			} catch (Exception e) {
				System.out.println("Errore.");
			}
		} while (r == null);
		return r;
	}

	public static void main(String[] args) {
		System.out.println("Equazione di secondo grado con eccezioni.");
		Double a = readDouble("Inserire a: ");
		Double b = readDouble("Inserire b: ");
		Double c = readDouble("Inserire c: ");
		System.out.println(Arrays.toString(equazioneGestita(a, b, c)));
	}

	public static Double[] equazioneGestita(double a, double b, double c) {
		try {
			return equazioneDiSecondoGrado(a, b, c);
		} catch (IllegalDegreeException illegalDegreeException) {
			try {
				return new Double[] { equazioneDiPrimoGrado(b, c) };
			} catch (UnknownSolutionException | NoSolutionException exception) {
				System.out.println(exception.getMessage());
				return new Double[0];
			}
		} catch (NoSolutionException | IllegalDeltaException exception) {
			System.out.println(exception.getMessage());
			return new Double[0];
		}
	}

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

	public static Double equazioneDiPrimoGrado(double b, double c) throws UnknownSolutionException, NoSolutionException {
		if (b == 0) {
			if (c == 0) throw new UnknownSolutionException();
			else throw new NoSolutionException();
		}
		return -c / b;
	}

}
