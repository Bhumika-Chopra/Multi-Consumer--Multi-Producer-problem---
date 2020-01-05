package src;

public class Nodes<V> extends NodeBase<V> {
	
	public Nodes(int priority, V value) {
		this.priority = priority;
		this.value = value;
	}

	//TODO Complete these methods	
	public int getPriority() {
		return this.priority;
	}

	public V getValue() {
		return this.value;
	}

}
