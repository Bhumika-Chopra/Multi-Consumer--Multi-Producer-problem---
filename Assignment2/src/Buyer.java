import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {
    public Buyer (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, int iteration) {
        this.setSleepTime(sleepTime);
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.catalog = catalog;
        this.setIteration(iteration);
    }
    @SuppressWarnings("unchecked")
    public void buy() throws InterruptedException {
	try {
            lock.lock();
            //System.out.println(Thread.currentThread().getName());
            while(catalog.isEmpty()) {
            	empty.await();
            }
            //System.out.println(Thread.currentThread().getName());
            //System.out.println(n.getValue().getName());
            Node<Item> n = (Node<Item>) catalog.dequeue();
            System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
            n.show(); // DO NOT REMOVE (For Automated Testing)
            full.signalAll();
	} catch (Exception e) {
            e.printStackTrace();
	} finally {
		lock.unlock();
	}
    }
}
