// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{

	//TODO Complete the Queue implementation
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
    
    @SuppressWarnings("unchecked")
    public Queue(int capacity) {    
    	this.capacity = capacity;
    	this.currentSize = 0;
    	this.front = -1;
    	this.rear = -1;
    	queue = new NodeBase[capacity] ;
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	return (currentSize == 0);
    }
	
    public boolean isFull() {
    	return (currentSize == capacity);
    }

    public void enqueue(Node<V> node) {
    	if(this.isFull()) {
//    		System.out.println("Queue is full");
    	}
    	else {
    		if(rear == capacity -1) {
    			rear = 0;
    			queue[rear] = node;
    			currentSize++;
    		}
    		else if (rear == -1){
    			rear++;
    			front++;
    			queue[rear] = node;
    			currentSize++;
    		}
    		else{
    			rear++;
    			queue[rear] = node;
    			currentSize++;
    		}
    	}
    }

    @SuppressWarnings("unchecked")    
    public NodeBase<V> dequeue() {
//    	if(this.isEmpty()) {
//    		System.out.println("Underflow");
//    		Item i = new Item(null, 0);
//    		return (NodeBase<V>) new Node<Item>(0, i);
//    	}
//    	else {
//    		Node<Item> element = (Node<Item>) queue[front];
//    		front = (front + 1) % this.capacity;
//    		currentSize--;
//    		return (NodeBase<V>) element;
//    	}
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
}

