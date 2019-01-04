package synthesizer;

import java.util.Iterator;

/**
 * Implements an array ring buffer.
 */
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements Iterable<T> {
//
    private int first; // Index for the next dequeue or peek
    private int last; // Index for the next enqueue
    private T[] rb; // Array for storing the buffer data

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    } // End ArrayRingBuffer constructor

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        } else {
            rb[last] = x;
            fillCount++;
            last = wrapAroundIfNecessary(last + 1);
        }
    } // End enqueue

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            T item = rb[first];
            fillCount--;
            first = wrapAroundIfNecessary(first + 1);
            return item;
        }
    } // End dequeue

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Cannot peek into empty ring buffer");
        } else {
            return rb[first];
        }
    } // End peek

    /**
     * Wraps the given pointer around the ring array if necessary.
     */
    private int wrapAroundIfNecessary(int pointer) {
        if (pointer == capacity) {
            return 0;
        } else if (pointer == -1) {
            return capacity - 1;
        } else {
            return pointer;
        }
    } // End wrapAroundIfNecessary

    /**
     * Allows for instances of ArrayRingBuffer to be iterated through.
     */
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    } // End iterator

    /**
     * Implements the Iterator interface for ArrayRingBuffer.
     */
    private class ArrayRingBufferIterator implements Iterator<T> {

        private int index;
        private int itemsCounted;

        public ArrayRingBufferIterator() {
            index = first;
            itemsCounted = 0;
        } // End ArrayRingBufferIterator constructor

        public boolean hasNext() {
            return itemsCounted < fillCount;
        } // End hasNext

        public T next() {
            T item = rb[index];
            index = wrapAroundIfNecessary(index + 1);
            itemsCounted++;
            return item;
        } // End next

    } // End ArrayRingBufferIterator class

} // End ArrayRingBuffer class
