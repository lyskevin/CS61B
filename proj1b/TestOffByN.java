import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static CharacterComparator offByN = new OffByN(2);

    @Test
    public void testOffByN() {
        assertTrue(offByN.equalChars('a', 'b'));
        assertTrue(offByN.equalChars('a', 'c'));
        assertTrue(offByN.equalChars('%', '&'));
        assertFalse(offByN.equalChars('a', 'd'));
    } /** End testOffByN */

} /** End TestOffByN class */
