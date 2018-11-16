/** Performs some basic array deque tests. */
public class ArrayDequeTest {
    
    public static void main(String[] args) {
        
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
        }
        
        deque.printDeque();
        
        for (int i = 0; i < 3; i++) {
            deque.addLast(i);
        }
        
        deque.printDeque();
        
        System.out.println(deque.get(4));
        
        for (int i = 0; i < 2; i++) {
            deque.removeLast();
        }
        for (int i = 0; i < 2; i++) {
            deque.removeFirst();
        }
        
        System.out.println(deque.size());
        deque.printDeque();
        
        for (int i = 0; i < 2; i++) {
            deque.removeFirst();
        }
        
        System.out.println(deque.isEmpty());
        
    }
}