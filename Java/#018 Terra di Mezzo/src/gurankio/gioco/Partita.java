package gurankio.gioco;

import gurankio.dati.Combattente;
import gurankio.dati.Eroe;
import gurankio.dati.Personaggio;
import gurankio.dati.Schieramento;

import java.util.*;

public class Partita {

    public static Schieramento partita(List<Combattente> combattenti) {
        Random random = new Random();

        int size = (int) Math.floor(Math.sqrt(combattenti.size() * 1)) + random.nextInt(2);
        Combattente[][] griglia = new Combattente[size][size];

        Collections.shuffle(combattenti);
        combattenti.forEach(c -> griglia[random.nextInt(size)][random.nextInt(size)] = c);

        // System.out.println(Arrays.deepToString(griglia));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (griglia[i][j] != null) {
                    if (i != 0 && combatti(griglia, i, j,i - 1, j)) continue;
                    if (i != size - 1 && combatti(griglia, i, j, i + 1, j)) continue;
                    if (j != 0 && combatti(griglia, i, j,  i, j - 1)) continue;
                    if (j != size - 1 && combatti(griglia, j, j,  i, j + 1)) continue;
                }
            }
        }

        // System.out.println(Arrays.deepToString(griglia));

        return forzaSchieramento(griglia, Schieramento.BENE) >= forzaSchieramento(griglia, Schieramento.MALE) ? Schieramento.BENE : Schieramento.MALE;
    }

    private static boolean combatti(Combattente[][] griglia, int ai, int aj, int bi, int bj) {
        final Combattente a = griglia[ai][aj];
        final Combattente b = griglia[bi][bj];

        if (a == null || b == null) return false;

        if (!a.getSchieramento().equals(b.getSchieramento())) {
            if (a.compareTo(b) > 0) {
                a.incrementExp();
                if (!(b instanceof Eroe) || ((Eroe) b).decrementVita()) griglia[bi][bj] = null;
            } else {
                b.incrementExp();
                if (!(a instanceof Eroe) || ((Eroe) a).decrementVita()) griglia[ai][aj] = null;
            }
            if (a instanceof Personaggio) return true;
        }
        return false;
    }

    private static int forzaSchieramento(Combattente[][] griglia, Schieramento s) {
        return Arrays.stream(griglia).flatMap(Arrays::stream).filter(Objects::nonNull).filter(c -> c.getSchieramento() == s).mapToInt(Combattente::getForza).sum();
    }

}
