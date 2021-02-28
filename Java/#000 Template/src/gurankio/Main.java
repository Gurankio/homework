package gurankio;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Pila<Integer> pila = new Pila<>(0);
		pila.push(1);
		pila.push(2);
		pila.push(3);
		pila.push(4);
		pila.push(5);
		pila.push(6);

		for (Integer x : pila) {
			System.out.println(x);
		}

		System.out.println(pila.toString());
		System.out.println(pila.size());
		pila.pop();
		System.out.println(pila);

		System.out.println(pila.stream().reduce(Integer::sum).orElse(0));
	}

}