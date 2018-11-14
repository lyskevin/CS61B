/** Implements a double ended queue (hereby deque) using linked lists*/

public class LinkedListDeque<T> {
    
    // Dictates the behaviour of each of the queue's individual nodes
    private class Node {
        private T data;
        private Node previous, next;
        private Node(Node p, T d, Node n) { // Node constructor
            previous = p;
            data = d;
            next = n;
        }  
    } // End Node class
    
    private Node sentinel;
    private int size;
    
    // Creates an empty deque
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    } // End LinkedListDeque constructor
    
    // Adds an item to the front of the deque
    public void addFirst(T item) {
        Node node = new Node(sentinel, item, sentinel.next);
        sentinel.next.previous = node;
        sentinel.next = node;
        size++;
    } // End addFirst
    
    // Adds an item to the back of the deque
    public void addLast(T item) {
        Node node = new Node(sentinel.previous, item, sentinel);
        sentinel.previous.next = node;
        sentinel.previous = node;
        size++;
    } // End addLast
    
    // Returns true if the deque is empty, else false
    public boolean isEmpty() {
        return size == 0;
    } // End isEmpty
    
    // Returns the number of items in the deque
    public int size() {
        return size;
    } // End size
    
    // Prints the items in the deque from first to last, separated by a space
    public void printDeque() {
        Node currentNode = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(currentNode.data);
            if (i < size - 1) {
                System.out.print(" ");
            }
            currentNode = currentNode.next;
        }
        System.out.println();
    } // End printDeque
    
    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else {
            T firstItem = sentinel.next.data;
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            size--;
            return firstItem;
        }
    } // End removeFirst
    
    // Removes and returns the item at the back of the deque.
    // If no such item exists, returns null
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else {
            T lastItem = sentinel.previous.data;
            sentinel.previous = sentinel.previous.previous;
            sentinel.previous.next = sentinel;
            size--;
            return lastItem;
        }
    } // End removeLast
    
    // Returns the item at the given index, where 0 is the front,
    // 1 is the next item, and so forth. If no such item exists,
    // returns null
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            Node currentNode = sentinel.next;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.data;
        }
    } // End get
    
    // Returns the item at the given index, where 0 is the front,
    // 1 is the next item, and so forth. If no such item exists,
    // returns null (recursive implementation)
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index <= size / 2) {
            return getRecursiveFront(index, sentinel.next);
        } else {
            return getRecursiveBack(size - index - 1, sentinel.previous);
        }
    } // End getRecursive
    
    // Returns the item at the given index by searching from the front of the deque
    public T getRecursiveFront(int index, Node node) {
        if (index == 0) {
            return node.data;
        } else {
            return getRecursiveFront(index - 1, node.next);
        }
    } // End getRecursiveFront
    
    // Returns the item at the given index by searching from the back of the deque
    public T getRecursiveBack(int index, Node node) {
        if (index == 0) {
            return node.data;
        } else {
            return getRecursiveBack(index - 1, node.previous);
        }
    } // End getRecursiveBack
    
} // End LinkedListDeque class
