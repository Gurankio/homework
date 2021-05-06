package gurankio;

import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	static Semaphore cassaSemaphore = new Semaphore(1);
	static Semaphore camerieriSemaphore = new Semaphore(1);
	static Semaphore togliSemaphore = new Semaphore(0);
	static Semaphore mettiSemaphore = new Semaphore(20);
	static Semaphore[] buffer = Stream.generate(() -> new Semaphore(1)).limit(20).toArray(Semaphore[]::new);

	static int metti = 0;
	static int togli = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			new Thread(Main::cameriere).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(Main::cassa).start();
		}
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.print(Arrays.stream(buffer).map(Semaphore::availablePermits).map(Objects::toString).collect(Collectors.joining(", ")));
				System.out.print("\n");
			}
		}, 100, 100);
	}

	private static void cassa() {
		while (true) {
			try {
				mettiSemaphore.acquire();
				cassaSemaphore.acquire();
				new Thread(() -> cliente(metti)).start();
				metti = (metti + 1) % 20;
				cassaSemaphore.release();
				togliSemaphore.release();
				Thread.sleep(150);
			} catch (InterruptedException e) {
			}
		}
	}

	private static void cliente(int metti) {
		try {
			buffer[metti].acquire();
			// dati[metti] = 2;
			Thread.sleep(500);
			// System.out.println();
			buffer[metti].release();
		} catch (InterruptedException e){
		}
	}

	private static void cameriere() {
		while (true) {
			try {
				togliSemaphore.acquire();
				int temp = togli;
				camerieriSemaphore.release();
				togli = (togli + 1) % 20;
				camerieriSemaphore.release();
				buffer[temp].acquire();
				// pulisci()
				// dati[temp] = 0;
				// System.out.printf("Pulito tavolo %d\n", temp);
				buffer[temp].release();
				mettiSemaphore.release();
				Thread.sleep(150);
			} catch (InterruptedException e) {

			}
		}
	}
}