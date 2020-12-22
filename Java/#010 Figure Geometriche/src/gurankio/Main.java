package gurankio;

import gurankio.figura.FiguraGeometrica;
import gurankio.figura.piana.Cerchio;
import gurankio.figura.piana.FiguraPiana;
import gurankio.figura.piana.Rettangolo;
import gurankio.figura.piana.Trapezio;
import gurankio.figura.solida.Cilindro;
import gurankio.figura.solida.Cono;
import gurankio.figura.solida.FiguraSolida;
import gurankio.figura.solida.Parallelepipedo;
import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;

import java.util.ArrayList;
import java.util.List;

public class Main {

	@MenuOptions.Hide
	public static boolean DEBUG = false;

	public static List<FiguraGeometrica> figure = new ArrayList<>();

	public static void main(String[] args) {
		figure.add(new Rettangolo(12, 4));
		figure.add(new Cerchio(12));
		figure.add(new Trapezio(12, 4, 65, 1,8));
		figure.add(new Cilindro(12, 4));
		figure.add(new Cono(12, 4));
		figure.add(new Parallelepipedo(12, 4, 7));
		new Menu(Main::new).console();
	}

	public static void stampa() {
		System.out.println("Piane...");
		figure.stream()
				.filter(f -> f instanceof FiguraPiana)
				.forEach(f -> System.out.printf("%-20s | Area: %7.2f | Perimetro: %7.2f%n", f.getClass().getSimpleName(), f.area(), ((FiguraPiana) f).perimetro()));
		System.out.println("Solide...");
		figure.stream()
				.filter(f -> f instanceof FiguraSolida)
				.forEach(f -> System.out.printf("%-20s | Area: %7.2f |    Volume: %7.2f%n", f.getClass().getSimpleName(), f.area(), ((FiguraSolida) f).volume()));
	}

}
