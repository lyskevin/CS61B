package synthesizer;

/**
 * Uses the Karplus-Strong algorithm to simulate the sounds made by a guitar string
 */
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round((SR / frequency));
        buffer = new ArrayRingBuffer<Double>(capacity);
    } // End GuitarString constructor

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        // Dequeue everything in the buffer
        int bufferSize = buffer.fillCount();
        for (int i = 0; i < bufferSize; i++) {
            buffer.dequeue();
        }

        // Fill buffer with white noise
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(Math.random() - 0.5);
        }

    } // End pluck

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double frontSample = buffer.dequeue();
        double nextSample = sample();
        double newSample = 0.5 * (frontSample + nextSample) * DECAY;
        buffer.enqueue(newSample);
    } // End tic

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (buffer.fillCount() == 0) {
            return 0.0;
        } else {
            return buffer.peek();
        }
    } // End sample

} // End GuitarString class
