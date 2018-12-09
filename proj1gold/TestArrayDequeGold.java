import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {

        ArrayDequeSolution<Integer> deque = new ArrayDequeSolution();
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque();
        String errorMessage = "\n";

        for (int i = 0; i < 10; i++) {
            int randomNumber = StdRandom.uniform(100);
            if (i % 2 == 0) {
                deque.addFirst(randomNumber);
                studentDeque.addFirst(randomNumber);
                errorMessage += "addFirst(" + randomNumber + ")\n";
            } else {
                deque.addLast(randomNumber);
                studentDeque.addLast(randomNumber);
                errorMessage += "addLast(" + randomNumber + ")\n";
            }
        }

        for (int i = 0; i < 10; i++) {
            Integer expected;
            Integer actual;
            if (i % 2 == 0) {
                expected = deque.removeFirst();
                actual = studentDeque.removeFirst();
                errorMessage += "removeFirst()\n";
                assertEquals(errorMessage, expected, actual);
            } else {
                expected = deque.removeLast();
                actual = studentDeque.removeLast();
                errorMessage += "removeLast()\n";
                assertEquals(errorMessage, expected, actual);
            }
        }

    }
}
