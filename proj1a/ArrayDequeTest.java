/** Performs some basic array deque tests. */
public class ArrayDequeTest {
    
    public static void main(String[] args) {
        
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        
        System.out.println(deque.size());
        System.out.println(deque.size());
        deque.addFirst(2);
        deque.addFirst(3);
        deque.printDeque();
        System.out.println(deque.size());
        
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.addFirst(8);
        deque.addFirst(9);
        deque.addLast(10);
        deque.printDeque();
        System.out.println(deque.size());
        deque.addLast(11);
        deque.printDeque();
 
    }
} // End ArrayDequeTest class
