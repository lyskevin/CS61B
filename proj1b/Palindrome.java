public class Palindrome {

    /**
     * Returns a deque comprising of the given string's individual characters
     * @param word
     */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> wordDeque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    } /** End wordToDeque */

    /**
     * Returns true if the given word is a palindrome, else false
     * @param word
     */
    public boolean isPalindrome(String word) {
        Deque wordDeque = wordToDeque(word);
        return isPalindromeRecursive(wordDeque);
    } /** End isPalindrome */

    /**
     * Returns true if the given word is a palindrome, else false
     * @param wordDeque
     */
    public boolean isPalindrome(Deque wordDeque) {
        return isPalindromeRecursive(wordDeque);
    } /** End isPalindrome */

    /**
     * Returns true if the given word is a palindrome according to
     * the character comparison test provided by the Character
     * Comparator passed in as an argument, else false
     * @param word
     * @param cc
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(word.charAt(i),
                               word.charAt(word.length() - 1 - i))) {
                return false;
            }
        }
        return true;
    } /** End isPalindrome */

    /**
     * Helper method for isPalindrome(String word) and
     * isPalindrome(Deque wordDeque). Returns true if the
     * given word (in the form of a deque) is a palindrome,
     * else false.
     * @param wordDeque
     */
    private boolean isPalindromeRecursive(Deque wordDeque) {
        if (wordDeque.size() <= 1) {
            return true;
        } else {
            if (wordDeque.removeFirst() != wordDeque.removeLast()) {
                return false;
            }
            return isPalindromeRecursive(wordDeque);
        }
    } /** End isPalindromeRecursive */

} /** End Palindrome class */
