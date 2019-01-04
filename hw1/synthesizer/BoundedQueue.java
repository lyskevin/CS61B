package synthesizer;

import java.util.Iterator;

/**
 * BoundedQueue interface for sound synthesis.
 */
public interface BoundedQueue<T> extends Iterable<T> {

    int capacity(); // Return size of the buffer
    int fillCount(); // Return number of items currently in the buffer
    void enqueue(T x); // Add item x to the end
    T dequeue(); // Delete and return item from the front
    T peek(); // Return (but do not delete) item from the front
    Iterator<T> iterator(); // To support iteration

    // Returns true if the buffer is empty, else false
    default boolean isEmpty() {
        return fillCount() == 0;
    } // End isEmpty

    // Returns true if the buffer is full, else false
    default boolean isFull() {
        return fillCount() == capacity();
    } // End isFull

} // End BoundedQueue interface
