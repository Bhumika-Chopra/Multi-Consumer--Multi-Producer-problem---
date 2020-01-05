// Use this driver for the testing the correctness of your priority queue implementation
// You can change the add, remove sequence to test for various scenarios.
public class PriorityQueueTestDriver {
    public static void main(String[] args) {
	PriorityQueue<String> pq = new PriorityQueue<String>(8);
	pq.enqueue(new Node<String> (1, "A"));
	pq.enqueue(new Node<String> (7, "B"));
	pq.enqueue(new Node<String> (8, "C"));
	pq.enqueue(new Node<String> (2, "E"));
	pq.enqueue(new Node<String> (3, "F"));
	
	//pq.display();
	int size = pq.size();
	for (int i=0; i<size; i++) {
            Node<String> n = (Node<String>) pq.dequeue();
            n.show();
	}
    }
}
