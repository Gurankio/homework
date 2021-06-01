package gurankio;

public class Camion extends Thread {

    private final String nome;
    private final Ponte ponte;

    public Camion(String nome, Ponte ponte) {
        this.nome = nome;
        this.ponte = ponte;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (ponte) {
                    while (!ponte.disponibile) ponte.wait();
                    ponte.disponibile = false;
                }

                // uso la risorsa.
                System.out.println("-> " + nome + " : (" + ponte.dato + ")");
                sleep(500);
                System.out.println("<- " + nome);

                synchronized (ponte) {
                    ponte.disponibile = true;
                    ponte.notify();
                }

                // cool-down
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
