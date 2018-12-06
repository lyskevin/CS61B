public class OffByOne implements CharacterComparator {

    /**
     * Returns true if the two given characters are off by one, else false.
     * @param x
     * @param y
     */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    } /** End equalChars */

} /** End OffByOne class */
