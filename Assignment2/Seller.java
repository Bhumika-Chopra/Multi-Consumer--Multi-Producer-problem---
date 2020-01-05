import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
	
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.setSleepTime(sleepTime);
        this.inventory = inventory;
        this.catalog = catalog;
        
    }
    
    @SuppressWarnings("unchecked")
	public void sell() throws InterruptedException {
	try {
			lock.lock();
            while(catalog.isFull()) {
            	full.await();
            }
            //System.out.println(Thread.currentThread().getName());
			if(!inventory.isEmpty()) {
				Node<Item> n = (Node<Item>) inventory.dequeue();
				//System.out.println(n.getValue().getName());
				catalog.enqueue((Node<V>) n);
			}
			empty.signalAll();
	} catch(Exception e) {
            e.printStackTrace();
	} finally {
		lock.unlock();
	}		
    }
}
