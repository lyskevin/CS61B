public class OffByN implements CharacterComparator {

    private int difference; // Maximum character difference, or "N"

    public OffByN(int N) {
        difference = N;
    } /** End OffByN constructor */

    /**
     * Returns true if the given characters are off by "N", else false.
     * @param x
     * @param y
     */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) <= difference;
    } /** End equalChars */

} /** End OffByN class */
