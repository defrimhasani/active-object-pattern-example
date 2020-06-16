import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ActiveObject {

    private double value = 0.0;
    private final BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingDeque<Runnable>();

    public ActiveObject() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dispatchQueue.take().run();
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    void increase() throws InterruptedException {
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                value++;
            }
        });
    }

    void decrease() throws InterruptedException {
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                value--;
            }
        });
    }

}
