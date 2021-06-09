package gurankio;

import java.util.function.Supplier;

public class Produttore<T> extends Thread {

    private final Buffer<T> buffer;
    private final int delay;
    private final Supplier<T> supplier;

    public Produttore(Buffer<T> buffer, int delay, Supplier<T> data) {
        this.buffer = buffer;
        this.delay = delay;
        this.supplier = data;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.setData(supplier.get());
                sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
