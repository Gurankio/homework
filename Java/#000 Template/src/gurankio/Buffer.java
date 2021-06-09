package gurankio;

public class Buffer<T> {

    private int togli;
    private int metti;
    private final T[] buffer;

    public Buffer(T[] buffer) {
        this.togli = 0;
        this.metti = 0;
        this.buffer = buffer;
    }

    public synchronized T getData() throws InterruptedException {
        while (buffer[togli] == null) wait();
        T data = buffer[togli];
        buffer[togli] = null;
        togli = (togli+1) % buffer.length;
        System.out.println("*) Letto: " + data);
        notify();
        return data;
    }

    public synchronized void setData(T data) throws InterruptedException {
        while (buffer[metti] != null) wait();
        buffer[metti] = data;
        metti = (metti+1) % buffer.length;
        System.out.println("*] Scritto: " + data);
        notify();
    }
}
