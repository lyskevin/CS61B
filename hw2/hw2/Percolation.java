package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Models a percolation system, which can be used in a Monte Carlo simulation
 * to estimate the percolation threshold.
 *
 * Each site contains the following bitwise information about itself:
 * 0 => Closed site, 1 => Open site
 * 0 => Not connected to grid's top row, 1 => Connected to grid's top row
 * 0 => Not connected to grid's bottom row, 1 => Connected to grid's bottom row
 *
 * The information should be read from right to left. For example, a site
 * represented by the number 011 is an open site which is connected to the
 * grid's top row, but not connected to the grid's bottom row. A site is
 * considered to be full if it is both open and connected to the grid's top
 * row. Each system of connected sites will have its own root site, which is
 * the component site returned by WeightedQuickUnionUF's find method.
 * The system percolates if there is at least one root site which is
 * connected to the grid's top and bottom rows. Decimal numbers are used
 * throughout for bitwise operations for better readability.
 *
 * @author lyskevin
 */

public class Percolation {

    /** System's attributes */
    private WeightedQuickUnionUF grid;
    private int[][] siteStatus;
    private int size; /** Grid's width and height */
    private int numOpenSites;
    private boolean hasPercolated;

    /**
     * Create an n-by-n grid, with all sites initially blocked
     * @param n The size of the grid
     */
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("N should be > 0");
        }

        grid = new WeightedQuickUnionUF(n * n);
        siteStatus = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                siteStatus[row][col] = 0;
                if (row == 0) { /** Top row site */
                    siteStatus[row][col] |= 2;
                }
                if (row == n - 1) { /** Bottom row site */
                    siteStatus[row][col] |= 4;
                }
            }
        }
        size = n;
        numOpenSites = 0;
        hasPercolated = false;

    } /** End Percolation constructor */

    /**
     * Open the site (row, col) if it is not open
     * @param row The site's row number
     * @param col The site's column number
     */
    public void open(int row, int col) {

        if (!isValid(row) || !isValid(col)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }

        /** Only open site if it is closed */
        if (!isOpen(row, col)) {

            /** Open site */
            siteStatus[row][col] |= 1;
            numOpenSites++;
            int site = rowAndColToSite(row, col);
            boolean leftConnected = false;
            boolean rightConnected = false;
            boolean topConnected = false;
            boolean bottomConnected = false;

            /** Connect left site if necessary */
            int leftCol = col - 1;
            if (isValid(leftCol) && isOpen(row, leftCol)) {
                connectSites(row, col, row, col - 1);
                updateConnections(row, leftCol, row, col);
                leftConnected = true;
            }

            /** Connect right site if necessary */
            int rightCol = col + 1;
            if (isValid(rightCol) && isOpen(row, rightCol)) {
                connectSites(row, col, row, rightCol);
                updateConnections(row, rightCol, row, col);
                rightConnected = true;
            }

            /** Connect top site if necessary */
            int topRow = row - 1;
            if (isValid(topRow) && isOpen(topRow, col)) {
                connectSites(row, col, topRow, col);
                updateConnections(topRow, col, row, col);
                topConnected = true;
            }

            /** Connect bottom site if necessary */
            int bottomRow = row + 1;
            if (isValid(bottomRow) && isOpen(bottomRow, col)) {
                connectSites(row, col, bottomRow, col);
                updateConnections(bottomRow, col, row, col);
                bottomConnected = true;
            }

            /** Update root site */
            int rootSite = grid.find(site);
            int rootRow = siteToRow(rootSite);
            int rootCol = siteToCol(rootSite);
            updateConnections(row, col, rootRow, rootCol);

            /** Update adjacent connected sites according to root site */
            if (leftConnected) {
                updateConnections(rootRow, rootCol, row, leftCol);
            }
            if (rightConnected) {
                updateConnections(rootRow, rootCol, row, rightCol);
            }
            if (topConnected) {
                updateConnections(rootRow, rootCol, topRow, col);
            }
            if (bottomConnected) {
                updateConnections(rootRow, rootCol, bottomRow, col);
            }

            /** Check if the system percolates */
            if (siteStatus[rootRow][rootCol] == 7) {
                hasPercolated = true;
            }

        }

    } /** End open */

    /**
     * Is the site (row, col) open?
     * @param row The site's row number
     * @param col The site's column number
     */
    public boolean isOpen(int row, int col) {

        if (!isValid(row) || !isValid(col)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }

        return siteStatus[row][col] % 2 == 1;

    } /** End isOpen */

    /**
     * Is the site (row, col) full?
     * @param row The site's row number
     * @param col The site's column number
     */
    public boolean isFull(int row, int col) {

        if (!isValid(row) || !isValid(col)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }

        int site = rowAndColToSite(row, col);
        int rootSite = grid.find(site);
        int rootRow = siteToRow(rootSite);
        int rootCol = siteToCol(rootSite);
        updateConnections(rootRow, rootCol, row, col);
        return siteStatus[row][col] % 4 == 3;

    } /** End isFull */

    /** Number of open sites */
    public int numberOfOpenSites() {
        return numOpenSites;
    } /** End numberOfOpenSites */

    /** Does the system percolate? */
    public boolean percolates() {
        return hasPercolated;
    } /** End percolates */

    /** Runs some unit tests */
    public static void main(String[] args) {

    } /** End main */

    /**************************** Helper Methods ****************************/

    /**
     * Returns true if the given row or column number is valid, else false
     * @param rowOrCol The given row or column number
     */
    private boolean isValid(int rowOrCol) {
        return rowOrCol >= 0 && rowOrCol < size;
    } /** End isValid */

    /**
     * Converts the given row and column number into the site number
     * @param row The site's row number
     * @param col The site's column number
     */
    private int rowAndColToSite(int row, int col) {
        return row * size + col;
    } /** End rowAndColToSite */

    /**
     * Returns the given site's row number
     * @param site The given site
     */
    private int siteToRow(int site) {
        return site / size;
    } /** End siteToRow */

    /**
     * Returns the given site's column number
     * @param site The given site
     */
    private int siteToCol(int site) {
        return site % size;
    } /** End siteToCol */

    /**
     * Connects the two given sites
     * Both sites must be open
     * @param site1Row Site 1's row number
     * @param site1Col Site 1's column number
     * @param site2Row Site 2's row number
     * @param site2Col Site 2's column number
     */
    private void connectSites(int site1Row, int site1Col,
                              int site2Row, int site2Col) {
        if (isValid(site1Row) && isValid(site1Col)
                && isValid(site2Row) && isValid(site2Col)) {
            int site1 = rowAndColToSite(site1Row, site1Col);
            int site2 = rowAndColToSite(site2Row, site2Col);
            if (!grid.connected(site1, site2)) {
                grid.union(site1, site2);
            }
        }
    } /** End connectSites */

    /**
     * Updates site 2's connections according to those of site 1
     * Sites 1 and 2 must be connected
     * @param site1Row Site 1's row number
     * @param site1Col Site 1's column number
     * @param site2Row Site 2's row number
     * @param site2Col Site 2's column number
     */
    private void updateConnections(int site1Row, int site1Col,
                                   int site2Row, int site2Col) {

        if (isValid(site1Row) && isValid(site1Col)
                && isValid(site2Row) && isValid(site2Col)) {

            /** Check that site 1 is not the same as site 2 */
            if (!(site1Row == site2Row && site1Col == site2Col)) {

                /** Update connection to grid's top row */
                if (siteStatus[site1Row][site1Col] % 4 == 2
                        || siteStatus[site1Row][site1Col] % 4 == 3) {
                    siteStatus[site2Row][site2Col] |= 2;
                }

                /** Update connection to grid's bottom row */
                if (siteStatus[site1Row][site1Col] >= 4) {
                    siteStatus[site2Row][site2Col] |= 4;
                }

            }

        }

    } /** End updateConnections */

    /************************ End of Helper Methods *************************/

} /** End Percolation class */
