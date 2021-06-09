package gurankio;

import java.util.function.Consumer;

public class Consumatore<T> extends Thread {

    private final Buffer<T> buffer;
    private final int delay;
    private final Consumer<T> data;

    public Consumatore(Buffer<T> buffer, int delay, Consumer<T> consumer) {
        this.buffer = buffer;
        this.delay = delay;
        this.data = consumer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                data.accept(buffer.getData());
                sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
