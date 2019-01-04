package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {

        ArrayRingBuffer arb = new ArrayRingBuffer(4);

        arb.enqueue(33.1);
        arb.enqueue(44.8);
        arb.enqueue(62.3);
        arb.enqueue(-3.4);
        assertEquals(33.1, arb.peek());

        try {
            arb.enqueue(5.0);
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e);
        }

        assertEquals(33.1, arb.dequeue());
        assertEquals(44.8, arb.dequeue());
        arb.enqueue(9.8);
        arb.enqueue(41.2);
        assertEquals(62.3, arb.peek());

        assertEquals(62.3, arb.dequeue());
        assertEquals(-3.4, arb.dequeue());
        assertEquals(9.8, arb.dequeue());
        assertEquals(41.2, arb.dequeue());

        try {
            arb.dequeue();
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e);
        }

        try {
            arb.peek();
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e);
        }

    }

    @Test
    public void testIterable() {

        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);

        for (Integer i : arb) {
            System.out.println(i);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
