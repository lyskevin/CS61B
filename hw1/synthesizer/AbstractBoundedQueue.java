package synthesizer;

/**
 * Implements the BoundedQueue interface.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public int capacity() {
        return capacity;
    }

} // End AbstractBoundedQueue class
