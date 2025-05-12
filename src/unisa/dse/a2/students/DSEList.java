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
		
		if (head != null) {
			Node newNode = new Node(null, null, head_.getString());
			this.head = newNode;
			Node currentOther = head_.next;
			Node prev = newNode;
			
			while (currentOther != null) {
				Node copy = new Node(null, prev, currentOther.getString());
				prev.next = copy;
				prev = copy;
				currentOther = currentOther.next;
			}
			
			tail = prev;
			size = computeSize();
		}
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
		
		Node current = head;
		int index = 0;
		
		while (current != null) {
			if (current.getString().equals(obj)) {
				return index;
			}
			current = current.next;
			index++;
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
		else {
			Node current = head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			
			newNode.next = current.next;
			if (current.next != null) {
				current.next.prev = newNode;
			}
			current.next = newNode;
			newNode.prev = current;
			
			if(newNode.next == null) {
				tail = newNode; // If added at the tail
			}
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
	
	// Helper method to compute size
	private int computeSize() {
		int count = 0;
		Node current = head;
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
