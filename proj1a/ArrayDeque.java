/** Implements a deque using arrays */
public class ArrayDeque<T> {
    
    private T[] array;
    private int nextFirst, nextLast;
    private int size;
    
    // Creates an deque (default size is 8)
    // Front and back pointers arbitrarily start from the middle
    public ArrayDeque() {
        array = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    } // End ArrayDeque constructor
    
    // Adds an item of type T to the front of the deque
    public void addFirst(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextFirst] = item;
        nextFirst = checkLoop(nextFirst - 1);
        size++;
    } // End addFirst
    
    // Adds an item of type T to the back of the deque
    public void addLast(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextLast] = item;
        nextLast = checkLoop(nextLast + 1);
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
        int index = checkLoop(nextFirst + 1);
        for (int i = 0; i < size; i++) {
            System.out.print(array[index]);
            if (i < size - 1) {
                System.out.print(" ");
            }
            index = checkLoop(index + 1);
        }
        System.out.println();
    } // End printDeque
    
    // Removes and returns the item at the front of the deque.
    // If no such item exists, return null
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else {
            if (size < array.length / 2) {
                resize(array.length / 2);
            }
            nextFirst = checkLoop(nextFirst + 1);
            size--;
            return array[nextFirst];
        }
    } // End removeFirst
    
    // Removes and returns the item at the back of the deque.
    // If no such item exists, returns null
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else {
            if (size < array.length / 2) {
                resize(array.length / 2);
            }
            nextLast = checkLoop(nextLast - 1);
            size--;
            return array[nextLast];
        }
    } // End removeLast
    
    // Gets the item at the given index, where 0 is the front,
    // 1 is the next item, and so forth. If no such item exists,
    // returns null
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            index = checkLoop(index + nextFirst + 1);
            return array[index];
        }
    } // End get
    
    // Resizes the array to the given capacity
    // Limits the minimum array size to 8
    private void resize(int capacity) {
        if (capacity >= 8) {
            
            T[] newArray = (T[]) new Object[capacity];
            int first = checkLoop(nextFirst + 1);
            int last = checkLoop(nextLast - 1);
            System.arraycopy(array, first, newArray, 0, array.length - first);
            System.arraycopy(array, 0, newArray, size - (array.length - first), last + 1);
            
            // Reset front and back pointers
            array = newArray;
            nextFirst = array.length - 1;
            nextLast = size;
        }
    } // End resize
    
    // Loops the given pointer around the array if necessary
    private int checkLoop(int pointer) {
        if (pointer < 0) {
            return array.length - 1;
        } else if (pointer >= array.length) {
            return 0;
        } else {
            return pointer;
        }
    } // End checkLoop
    
} // End ArrayDeque class
