package gurankio;

import gurankio.dati.*;
import gurankio.gioco.Partita;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	public static void main(String[] args) {
		Random rand = new Random();
		List<Combattente> list = new ArrayList<>();

		for (int i=0; i<5; i++) {
			for (Razza r : Razza.values()) {
				list.add(new Personaggio(rand, r));
			}
		}

		list.add(new Eroe(rand, Schieramento.BENE));
		list.add(new Eroe(rand, Schieramento.MALE));

		ConcurrentHashMap<Schieramento, Integer> vittorie = new ConcurrentHashMap<>();
		vittorie.put(Schieramento.BENE, 0);
		vittorie.put(Schieramento.MALE, 0);

		int size = 100000;
		for (int i = 0; i < size; i++) {
			vittorie.compute(Partita.partita(new ArrayList<>(list)), (s, x) -> x+1);
		}

		System.out.printf("BENE: %.4f%%\n", vittorie.get(Schieramento.BENE) * 100.0 / size);
		System.out.printf("MALE: %.4f%%\n", vittorie.get(Schieramento.MALE) * 100.0 / size);
	}

}
