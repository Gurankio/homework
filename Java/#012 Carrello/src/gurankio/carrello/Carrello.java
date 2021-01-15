package gurankio.carrello;

import java.util.ArrayList;

public class Carrello {

    private ArrayList<Merce> contenutoCarrello;

    public Carrello() {
        contenutoCarrello = new ArrayList<>();
    }

    public void inserisciProdotto(Merce prodotto) {
        contenutoCarrello.add(prodotto);
    }

    public double calcolaPrezzoTotale() {
        return contenutoCarrello.stream()
                .map(Merce::calcolaImporto)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    public double calcolaCalorieTotali() {
        return contenutoCarrello.stream()
                .filter(m -> m instanceof Alimentari)
                .map(x -> ((Alimentari) x).calcolaCalorie())
                .reduce(Double::sum)
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return contenutoCarrello.toString();
    }
}
