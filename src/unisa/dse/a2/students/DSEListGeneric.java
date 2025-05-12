package unisa.dse.a2.students;

import unisa.dse.a2.interfaces.ListGeneric;

/**
 * @author simont
 *
 */
public class DSEListGeneric<T> implements ListGeneric<T> {
	
	public NodeGeneric<T> head;
	private NodeGeneric<T> tail;
	private int size;
	
	// Constructor for an empty list
	public DSEListGeneric() {
		head = null;
        tail = null;
        size = 0;
	}
	
	
	public DSEListGeneric(NodeGeneric<T> head_) {
		
		  if (head_ != null) {
		      NodeGeneric<T> newNode = new NodeGeneric<>(null, null, head_.get());
		      this.head = newNode;
		      NodeGeneric<T> currentOther = head_.next;
		      NodeGeneric<T> prev = newNode;

		      while (currentOther != null) {
		          NodeGeneric<T> copy = new NodeGeneric<>(null, prev, currentOther.get());
		          prev.next = copy;
		          prev = copy;
		          currentOther = currentOther.next;
		      }

		      tail = prev;
		      size = computeSize();
		  }
	}
	
	//Takes a list then adds each element into a new list
	public DSEListGeneric(DSEListGeneric<T> other) { // Copy constructor. 
		if (other == null || other.head == null) {
	        head = null;
	        tail = null;
	        size = 0;
	        return;
	    }

	    NodeGeneric<T> otherCurrent = other.head;
	    NodeGeneric<T> newHead = new NodeGeneric<>(null, null, otherCurrent.get());
	    head = newHead;
	    NodeGeneric<T> prev = newHead;
	    otherCurrent = otherCurrent.next;

	    while (otherCurrent != null) {
	        NodeGeneric<T> copy = new NodeGeneric<>(null, prev, otherCurrent.get());
	        prev.next = copy;
	        prev = copy;
	        otherCurrent = otherCurrent.next;
	    }

	    tail = prev;
	    size = other.size;
	}

	//remove and return the item at the parameter's index
	public T remove(int index) {
		if (index < 0 || index >= size) {
	        return null; // Index out of bounds
	    }

	    NodeGeneric<T> current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    if (current.prev != null) {
	        current.prev.next = current.next;
	    } else {
	        head = current.next;
	    }

	    if (current.next != null) {
	        current.next.prev = current.prev;
	    } else {
	        tail = current.prev;
	    }

	    size--;
	    return current.get();
	}

	//returns the index of the String parameter 
	public int indexOf(T obj) {
		NodeGeneric<T> current = head;
	    int index = 0;

	    while (current != null) {
	        if (current.get().equals(obj)) {
	            return index;
	        }
	        current = current.next;
	        index++;
	    }
	    return -1;
	}
	
	//returns item at parameter's index
	public T get(int index) {
		
		if (index < 0 || index >= size) {
	        return null;
	    }

	    NodeGeneric<T> current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    return current.get();
	}

	//checks if there is a list
	public boolean isEmpty() {
		 return head == null;
	}

	//return the size of the list
	public int size() {
		return size;
	}
	
	//Take each element of the list a writes them to a string 
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    NodeGeneric<T> current = head;

	    while (current != null) {
	        sb.append(current.get());
	        if (current.next != null) {
	            sb.append(" ");
	        }
	        current = current.next;
	    }

	    return sb.toString();
	}

	//add the parameter item at of the end of the list
	public boolean add(T obj) {
		if (obj == null) return false;

	    NodeGeneric<T> newNode = new NodeGeneric<>(null, tail, obj);
	    if (isEmpty()) {
	        head = newNode;
	        tail = newNode;
	    } else {
	        tail.next = newNode;
	        tail = newNode;
	    }
	    size++;
	    return true;
	}

	//add item at parameter's index
	public boolean add(int index, T obj) {
		if (index < 0 || index > size) {
	        return false; // Index out of bounds
	    }

	    NodeGeneric<T> newNode = new NodeGeneric<>(null, null, obj);

	    if (index == 0) {
	        newNode.next = head;
	        if (head != null) {
	            head.prev = newNode;
	        }
	        head = newNode;
	        if (size == 0) {
	            tail = newNode; // If the list was empty
	        }
	    } else {
	        NodeGeneric<T> current = head;
	        for (int i = 0; i < index - 1; i++) {
	            current = current.next;
	        }

	        newNode.next = current.next;
	        if (current.next != null) {
	            current.next.prev = newNode;
	        }
	        current.next = newNode;
	        newNode.prev = current;

	        if (newNode.next == null) {
	            tail = newNode; // If added at the tail
	        }
	    }

	    size++;
	    return true;
	}

	//searches list for parameter's String return true if found
	public boolean contains(T obj) {
		return indexOf(obj) != -1;
	}

	//removes the parameter's item form the list
	public boolean remove(T obj) {
		int index = indexOf(obj);
	    if (index == -1) {
	        return false; // Not found
	    }
	    remove(index);
	    return true;
	}
	
	// Helper method to compute size
	private int computeSize() {
		int count = 0;
		NodeGeneric<T> current = head;
		while(current != null) {
			count++;
			current = current.next;
		}
			
		return count;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		return true;
	}
	
}
