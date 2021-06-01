package gurankio;

public class Macchina extends Thread {

    private final String nome;
    private final Ponte ponte;
    private final Direzione direzione;

    public Macchina(String nome, Ponte ponte, Direzione direzione) {
        this.nome = nome;
        this.ponte = ponte;
        this.direzione = direzione;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (direzione) {
                    direzione.count++;
                    if (direzione.count == 1) {
                        synchronized (ponte) {
                            while (!ponte.disponibile) ponte.wait();
                            ponte.disponibile = false;
                        }
                    }
                    if (direzione.count >= 100) direzione.wait();
                }

                // uso la risorsa.
                System.out.println("-> " + nome + " : (" + ponte.dato + ")");
                sleep(500);
                System.out.println("<- " + nome);

                synchronized (direzione) {
                    direzione.count--;
                    if (direzione.count == 0) {
                        synchronized (ponte) {
                            ponte.disponibile = true;
                            ponte.notify();
                        }
                    }
                    direzione.notify();
                }

                // cool-down
                sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    enum Direzione {
        DESTRA,
        SINISTRA;

        int count = 0;
    }

}
