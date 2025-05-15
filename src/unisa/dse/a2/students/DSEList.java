package unisa.dse.a2.students;

import unisa.dse.a2.interfaces.List;

/**
 * @author simont
 *
 */
public class DSEList implements List {
	
	public Node head;
	private Node tail;
	private int size;

	// Blank Constructor 
	public DSEList() {
		
		head = null;
		tail = null;
		size = 0;
	}
	
	// Constructor with one Node 
	public DSEList(Node head_) {
		
		if(head_ == null) {
			this.head = null;
			this.tail = null;
			this.size = 0;
			return;
		}
		
		this.head = head_;
		Node current = head_;
		this.size = 1;
		
		while(current.next != null) {
			current = current.next;
			this.size++;
		}
		this.tail = current;
	}
	
	//Takes a list then adds each element into a new list
	public DSEList(DSEList other) { // Copy constructor. 
		
		if (other == null || other.head == null) {
			head = null;
			tail = null;
			size = 0;
			return;
		}
		
		Node otherCurrent = other.head;
		Node newHead = new Node(null,null, otherCurrent.getString());
		head = newHead;
		Node prev = newHead;
		otherCurrent = otherCurrent.next;
		
		while(otherCurrent != null) {
			Node copy = new Node(null, prev, otherCurrent.getString());
			prev.next = copy;
			prev = copy;
			otherCurrent = otherCurrent.next;
		}
		
		tail = prev;
		size = other.size;
	}

	//remove the String at the parameter's index
	public String remove(int index) {
		
		if (index < 0 || index >= size) {
			return null;  // index out of bound
		}
		
		Node current = head;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		
		// remove the node
		if (current.prev != null) {
			current.prev.next = current.next;
		}
		else {
			head = current.next; // removing the head
		}
		
		if (current.next != null) {
			current.next.prev = current.prev;
		}
		else {
			tail = current.prev; // removing tail
		}
		
		size--;
		return current.getString();
	}

	//returns the index of the String parameter 
	public int indexOf(String obj) {
		
		if (obj == null) return -1;
		// Try to start from the closer end (head or tail)
		Node current = head;
		int index = 0;
		// If index is closer to head, traverse from head, otherwise from tail
		if (size / 2 > index) {
			while (current != null) {
				if (current.getString().equals(obj)) return index; 
				current = current.next;
				index++;
			}
		}else {
			current = tail;
			index = size - 1;
			while(current != null) {
				if(current.getString().equals(obj)) return index;
				current = current.prev;
				index--;
			}
		}
		
		return -1; // not found
	}
	
	//returns String at parameter's index
	public String get(int index) {
		
		if (index < 0 || index >= size) {
			return null; //index out of bound
		}
		
		Node current = head;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		
		return current.getString();
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
		Node current = head;
		
		while( current != null) {
			sb.append(current.getString());
			if (current.next != null) {
				sb.append(" ");
			}
			current = current.next;
		}
		
		return sb.toString();
	}

	//add the parameter String at of the end of the list
	public boolean add(String obj) {
		
		if (obj == null) return false;
		
		Node newNode = new Node(null, tail, obj);
		if(isEmpty()) {
			head = newNode;
			tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;
		}
		
		size++;
		return true;
	}

	//add String at parameter's index
	public boolean add(int index, String obj) {
		
		if (index < 0 || index > size) {
			return false; // Index out bound
		}
		
		Node newNode = new Node(null, null, obj);
		
		// adding at the head
		if(index == 0) {
			newNode.next = head;
			if( head != null) {
				head.prev = newNode;
			}
			head = newNode;
			if(size == 0) {
				tail = newNode; // If the list was empty
			}
		}
		// adding at the tail
		else if(index == size){
			Node current = tail;
			current.next = newNode;
			newNode.prev = current;
			tail = newNode;
		}
		// Adding in the middle
		else{
			Node current = head;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			
			newNode.next = current.next;
			if(current.next != null) {
				current.next.prev = newNode;
			}
			
			current.next = newNode;
			newNode.prev = current;
		}
		size++;
		return true;
	}

	//searches list for parameter's String return true if found
	public boolean contains(String obj) {
		
		return indexOf(obj) != -1;
	}

	//removes the parameter's String form the list
	public boolean remove(String obj) {
		
		int index = indexOf(obj);
		if(index == -1) {
			return false; // not found
		}
		remove(index);
		return true;
	}
		
	@Override
	public int hashCode() {
		int result = 1;
		Node current = head;
		while(current != null) {
			result = 31 * result + (current.getString() != null ? current.getString().hashCode() : 0);
			current = current.next;
		}
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true; // Same Object
		// Null or different type
		if (other == null || getClass() != other.getClass()) return false; 
		DSEList otherList = (DSEList) other;
		
		if (this.size != otherList.size) return false; // Different size
		
		Node current = this.head;
		Node otherCurrent = otherList.head;
		
		while(current != null) {
			if(!current.getString().equals(otherCurrent.getString())) {
				return false; // Elements do not match
			}
			current = current.next;
			otherCurrent = otherCurrent.next;
		}
		return true; // All elements match
	}
	
}
