All the classes and their functions are working properly. My code has passed all the test cases given on Moodle. I have used @SuppressWarnings("unchecked") to remove the warnings as they were not allowing compilation on moodle.

Firstly I completed the Node definition - 
public int getPriority() {
		return this.priority;	//returns priority of the node 
	}

	public V getValue() {
		return this.value;	//returns value of the node item
	}

Then I made the queue and priority queue
Queue<V> implements QueueInterface<V> 

constructor - 
public Queue(int capacity) {    
    	this.capacity = capacity;	//maximum possible size of the queue
    	this.currentSize = 0;		//current size of the queue
    	this.front = -1;		//front and rear to keep track of the indices at which insertion and deletion is taking place
    	this.rear = -1;			//front - deletion index, rear - insertion index
    	queue = new NodeBase[capacity] ;	//weak typing to define a generic array of type NodeBase<V>
    }


I have implemented a circular queue for the inventory. As it makes insertion of elements easier. 
enqueue(Node<V> node) - to add elements to the queue 
public void enqueue(Node<V> node) {
    	if(this.isFull()) {				//if queue is full do nothing
//   		System.out.println("Queue is full");
    	}
    	else {
    		if(rear == capacity -1) {		//if rear = capacity - 1 and the queue is not full, then it means that some elements have been deleted
    			rear = 0;			//from the start of the queue, and we can insert at those positions.
    			queue[rear] = node;		//so we have to make rear = 0 and then insert at that position.
    			currentSize++;			//and increase the queue size.
    		}
    		else if (rear == -1){			//whenever rear = -1 the queue is empty and we have to change both rear and front to 0.
    			rear++;
    			front++;
    			queue[rear] = node;
    			currentSize++;			//and increase the queue size.
    		}
    		else{
    			rear++;				//in all other cases just increase rear++ 
    			queue[rear] = node;		//and insert the new node at that position
    			currentSize++;			//and increase the queue size.
    		}
    	}
    }


public NodeBase<V> dequeue() {
    	if(this.isEmpty()) {				//if queue is empty return null
//    		System.out.println("Queue is empty");
//    		Item i = new Item(null, 0);
//    		return (NodeBase<V>) new Node<Item>(0, i);
    		return null;
    	}
    	else {
    		if(front == rear) {						
    			Node<Item> element = (Node<Item>) queue[front];		//if front = rear that means the queue is about to get empty, i.e. this is the 
    			front = rear = -1;					//last element in the queue, make both rear and front = -1
    			currentSize--;						//decrease the current size
    			return (NodeBase<V>) element;				//return the node element				
    		}
    		else if(front == capacity - 1) {
    			NodeBase<Item> element = (NodeBase<Item>) queue[front];	//when front = capacity - 1 and the queue is not empty
    			front = 0;						//that means there are still elements that can be deleted
    			currentSize--;						//make front = 0 and decrease the size by 1
    			return (NodeBase<V>) element;				//return the node element
    		}
    		else {
    			NodeBase<Item> element = (NodeBase<Item>) queue[front];	// in all other cases do front++
    			front++;						// decrease current size by 1
    			currentSize--;	
    			return (NodeBase<V>) element;				//return node element
    		}
    	}
    	
    }


PriorityQueue<V> implements QueueInterface<V>
All the Queue and Priority queue functions are the same the only difference is in enqueue(Node<V> node). When implementing a priority queue we can either take care to insert all the elements according to their priority or we can take care to remove elements according to their priority. I have implemented a circular priority queue and taken care of inserting the elements at their proper positions, so my enqueue() is of O(n) and my dequeue is of O(1).

In insertion sort we start at the last index of the array and start moving towards the 0 index looking for the correct position to insert the element. In case of circular priority queue we have to take care that after 1 complete rotation of the queue if rear<front then we have to take rear+capacity as our last index and not rear.


public void enqueue(Node<V> node) {
    	if(this.isFull()) { 				//if the queue is full - then do nothing
//    		System.out.println("Queue is full");
    	}
    	else {
    		if(rear == -1) {			//first element insertion in the queue
    			rear++;				//we have to change both rear and front to 0
    			front++;
    			queue[rear] = node; 		//insert element at rear position
    			currentSize++;			//increase size by 1
    		}
    		else {
	    		int k = rear;			//I have implemented an approach similar to insertion sort to add elements at their correct positions 
	    		int i = 0;			//in the queue.
	    		if(k < front) {			//When inserting elements if the rear < front (index) that means the queue has completed 1 rotation
	    			i = k + capacity;	//in case of circular queue. So in that case consider the last index as rear + capacity.
	    		}
	    		else {
	    			i = k;			//if rear>front then consider the last index as rear.
	    		}
	    		while(i >= front && queue[(i) % capacity].getPriority() > node.getPriority() ) {	//trying to find the first node having priority
	    			queue[(i+1) % capacity] = queue[i % capacity];				//less than the node to be inserted.
	    			i--;
    			}
	    		queue[(i+1) % capacity] = node;			//then insert that element at the found positon, we have to take modulo because 
	    		rear = (rear + 1) % capacity;			//if we have taken i = capacity + rear it is an index out of bounds for the queue
	    		currentSize++;					//increase size by 1
    		}
    	}
    }

Then I completed the Assignment2Driver.java file

	a_driver.inventory = new Queue<Item>(list.size()); 		//made the inventory by putting elements of ArrayList<Item> list into the 
	int i=0;							//inventory queue
	while(i<list.size()) {
	a_driver.inventory.enqueue(list.get(i));
	i++;
	}

while(i<a_driver.numSellers) {						
    	sellers[i] = new Seller(a_driver.sellerSleepTime, a_driver.catalogSize, lock, full, empty, queue, a_driver.inventory);
    	Thread ts = new Thread(sellers[i]);
while(i<a_driver.numSellers) {
    	sellers[i] = new Seller(a_driver.sellerSleepTime, a_driver.catalogSize, lock, full, empty, queue, a_driver.inventory);
    	Thread ts = new Thread(sellers[i]);
//    	ts.setName("Sellor " + (i+1));
    	ts.start();					//started the seller threads	
    	i++;
    }
    int j=0;
    while(j<a_driver.numBuyers) {
    	buyers[j] = new Buyer(a_driver.buyerSleepTime, a_driver.catalogSize, lock, full, empty, queue, iteration);
    	Thread tb = new Thread(buyers[j]);
    	tb.setName("Buyer " + (j+1));
    	tb.start();					//started the buyer threads
//    	tb.join();
    	j++;
    }
    	ts.start();
    	i++;
    }

    while(j<a_driver.numBuyers) {
    	buyers[j] = new Buyer(a_driver.buyerSleepTime, a_driver.catalogSize, lock, full, empty, queue, iteration);
    	Thread tb = new Thread(buyers[j]);
    	tb.setName("Buyer " + (j+1));
    	tb.start();
//    	tb.join();
    	j++;
    }


Then I completed the buyer class
Buyer<V> extends BuyerBase<V>

Constructor - 
public Buyer (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, int iteration) {
        this.setSleepTime(sleepTime);
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.catalog = catalog;
        this.setIteration(iteration);
    }

buy() function - 
public void buy() throws InterruptedException {
	try {
            lock.lock();						//this gives the buyer thread the lock if its free otherwise the buyer thread lies 
            //System.out.println(Thread.currentThread().getName());	//dormant waiting for the lock
            while(catalog.isEmpty()) {					//once the buyer thread gets the lock, it checks if the catalog is empty or not
            	empty.await();						//if the catalog is empty it waits - that is it leaves the lock and goes into 	
            }								//sleep mode
            //System.out.println(Thread.currentThread().getName());
            //System.out.println(n.getValue().getName());
            Node<Item> n = (Node<Item>) catalog.dequeue();		//if the catalog is not empty then the thread dequeues from the catalog.
            System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
            n.show(); // DO NOT REMOVE (For Automated Testing)		//and prints - the priority of the node.
            full.signalAll();						//then it signals all the seller threads to wake up since the catalog now has some 
	} catch (Exception e) {						//empty space to fill up
            e.printStackTrace();			//if any exception had been encountered or if the thread had been interrupted then this would have 
	} finally {					//printed the complete execution stack alongwith the point at which the exceptions was encountered
		lock.unlock();				//finally at the end the buyer thread must release the lock
	}
    }

Then I completed the seller class
Seller<V> extends SellerBase<V>

Constructor - 
public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        this.lock = lock;
        this.full = full;
        this.empty = empty;
        this.setSleepTime(sleepTime);
        this.inventory = inventory;
        this.catalog = catalog;
        
    }

sell() function - 
public void sell() throws InterruptedException {
	try {
			lock.lock();				//this gives the seller thread the lock if its free otherwise the seller thread lies
            while(catalog.isFull()) {				//dormant waiting for the lock
								//once the seller thread gets the lock, it checks if the catalog is full or not
            	full.await();					//if the catalog is full it waits - that is it leaves the lock and goes into
            }							//sleep mode
            //System.out.println(Thread.currentThread().getName());	//if the catalog is not full then,
			if(!inventory.isEmpty()) {			//the seller thread checks if the inventory is empty or not
				Node<Item> n = (Node<Item>) inventory.dequeue();	//if the inventory is not empty it dequeues from inventory and
				//System.out.println(n.getValue().getName());		//enqueues into catalog
				catalog.enqueue((Node<V>) n);
			}
			empty.signalAll();				//then it signals all the buyer threads to wake up as an element has been added to the
	} catch(Exception e) {						//catalog which they can buy
            e.printStackTrace();		//if any exception had been encountered or if the thread had been interrupted then this would have
	} finally {				//printed the complete execution stack alongwith the point at which the exceptions was encountered
		lock.unlock();			//finally at the end the buyer thread must release the lock
	}		
    }

Essentially whenever a buyer thread goes into wait mode it loses the lock, therefore all the other sellers and buyers who are not in wait themselves can get this lock and make changes to the catalog that is why multiple outputs for this problem are possible. Similar statement can be made for the sellers, i.e. whenever a seller goes into wait mode all the other sellers and buyers who are not in wait themselves can get this lock and make changes to the catalog, therefore multiple outputs for this problem are possible.


