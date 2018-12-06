import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertFalse(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('A', 'a'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('%', '&'));
    }

    @Test
    public void testIsPalindrome() {
        Palindrome palindrome = new Palindrome();
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("nam", offByOne));
        assertFalse(palindrome.isPalindrome("hello", offByOne));
        assertFalse(palindrome.isPalindrome("noon", offByOne));
        assertFalse(palindrome.isPalindrome("abB", offByOne));
        assertFalse(palindrome.isPalindrome("AbZ", offByOne));
        assertFalse(palindrome.isPalindrome("abA", offByOne));
        assertTrue(palindrome.isPalindrome("%a&", offByOne));
        assertFalse(palindrome.isPalindrome("%&&%", offByOne));
    }
}
