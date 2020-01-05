
public class PriorityQueue<V> implements QueueInterface<V>{

    private NodeBase<V>[] queue;
    private int capacity, currentSize;
    protected int front, rear;
    protected int cycles = 0;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    @SuppressWarnings("unchecked")
	public PriorityQueue(int capacity) {    
    	this.capacity = capacity;
    	this.currentSize = 0;
    	this.front = -1;
    	this.rear = -1;
    	queue = new NodeBase[this.capacity];
    }

    public int size() {
    	return this.currentSize;
    }

    public boolean isEmpty() {
    	return (this.currentSize == 0);
    }
	
    public boolean isFull() {
    	return (this.currentSize == capacity);
    }

    public void enqueue(Node<V> node) {
    	if(this.isFull()) {
//    		System.out.println("Queue is full");
    	}
    	else {
    		if(rear == -1) {
    			rear++;
    			front++;
    			queue[rear] = node; 
    			currentSize++;
    		}
    		else {
	    		int k = rear;
	    		int i = 0;
	    		if(k < front) {
	    			i = k + capacity;
	    		}
	    		else {
	    			i = k;
	    		}
	    		while(i >= front && queue[(i) % capacity].getPriority() > node.getPriority() ) {
	    			queue[(i+1) % capacity] = queue[i % capacity];
	    			i--;
    			}
	    		queue[(i+1) % capacity] = node;
	    		rear = (rear + 1) % capacity;
	    		currentSize++;
    		}
    	}
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    @SuppressWarnings("unchecked")
	public NodeBase<V> dequeue() {
    	if(this.isEmpty()) {
//    		System.out.println("Queue is empty");
//    		Item i = new Item(null, 0);
//    		return (NodeBase<V>) new Node<Item>(0, i);
    		return null;
    	}
    	else {
    		if(front == rear) {
    			Node<Item> element = (Node<Item>) queue[front];
    			front = rear = -1;
    			currentSize--;
    			return (NodeBase<V>) element;
    		}
    		else if(front == capacity - 1) {
    			NodeBase<Item> element = (NodeBase<Item>) queue[front];
    			front = 0;
    			currentSize--;
    			return (NodeBase<V>) element;
    		}
    		else {
    			NodeBase<Item> element = (NodeBase<Item>) queue[front];
    			front++;
    			currentSize--;
    			return (NodeBase<V>) element;
    		}
    	}
    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize; i++) {
            queue[i].show();
	}
    }
}

