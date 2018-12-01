import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    /** Performs a few arbitrary tests to find the bug
     *  (if it exists) in Flik's library.
     */

    @Test
    public void testFlik() {
        Integer a = new Integer(2);
        Integer b = new Integer(3);
        Integer c = new Integer(2);
        int d = 127;
        int e = 5;
        int f = 127;
        int g = 128;
        int h = 128;
        assertEquals(false, Flik.isSameNumber(a, b));
        assertEquals(false, Flik.isSameNumber(a, c));
        assertEquals(false, Flik.isSameNumber(d, e));
        assertEquals(true, Flik.isSameNumber(d, f));
        assertEquals(false, Flik.isSameNumber(g, h));
    }
}
