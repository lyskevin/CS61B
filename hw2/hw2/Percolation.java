package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Models a percolation system.
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
 * the component site returned by WeightedQuickUnionUF's find operation.
 * The system percolates if there is at least one root site which is
 * connected to the grid's top and bottom rows.
 */
public class Percolation {

    // Grid's attributes
    private WeightedQuickUnionUF grid;
    private int[][] siteStatus;
    private int N;
    private int numOpenSites;
    private boolean hasPercolated;

    // Create an N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be > 0");
        }
        grid = new WeightedQuickUnionUF(N * N);
        siteStatus = new int[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                siteStatus[row][col] = 0;
                if (row == 0) { // Top row site
                    siteStatus[row][col] |= 2;
                }
                if (row == N - 1) { // Bottom row site
                    siteStatus[row][col] |= 4;
                }
            }
        }
        this.N = N;
        numOpenSites = 0;
        hasPercolated = false;
    } // End Percolation constructor

    // Open the site (row, col) if it is not open
    public void open(int row, int col) {

        if ((row < 0 || row >= N) || (col < 0 || col >= N)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }

        // Check that site is closed
        if (!isOpen(row, col)) {

            // Open site
            siteStatus[row][col] |= 1;
            numOpenSites++;
            int site = rowAndColToInt(row, col);

            // Connect left site if necessary
            int leftCol = col - 1;
            if (leftCol >= 0 && isOpen(row, leftCol)) {
                int leftSite = rowAndColToInt(row, leftCol);
                if (!grid.connected(site, leftSite)) {
                    grid.union(site, leftSite);
                    updateConnections(row, leftCol, row, col);
                }
            }

            // Connect right site if necessary
            int rightCol = col + 1;
            if (rightCol < N && isOpen(row, rightCol)) {
                int rightSite = rowAndColToInt(row, rightCol);
                if (!grid.connected(site, rightSite)) {
                    grid.union(site, rightSite);
                    updateConnections(row, rightCol, row, col);
                }
            }

            // Connect top site if necessary
            int topRow = row - 1;
            if (topRow >= 0 && isOpen(topRow, col)) {
                int topSite = rowAndColToInt(topRow, col);
                if (!grid.connected(site, topSite)) {
                    grid.union(site, topSite);
                    updateConnections(topRow, col, row, col);
                }
            }

            // Connect bottom site if necessary
            int bottomRow = row + 1;
            if (bottomRow < N && isOpen(bottomRow, col)) {
                int bottomSite = rowAndColToInt(bottomRow, col);
                if (!grid.connected(site, bottomSite)) {
                    grid.union(site, bottomSite);
                    updateConnections(bottomRow, col, row, col);
                }
            }

            // Update root site
            int rootSite = grid.find(site);
            int rootRow = intToRow(rootSite);
            int rootCol = intToCol(rootSite);
            updateConnections(row, col, rootRow, rootCol);

            // Check if the system has percolated
            if (siteStatus[rootRow][rootCol] == 7) {
                hasPercolated = true;
            }

        }

    } // End open

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 0 || row >= N) || (col < 0 || col >= N)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }
        return siteStatus[row][col] % 2 == 1;
    } // End isOpen

    // Is the site (row, col) full?

    /**
     * 011 3
     * 111 7
     */
    public boolean isFull(int row, int col) {
        if ((row < 0 || row >= N) || (col < 0 || col >= N)) {
            throw new IndexOutOfBoundsException("Invalid row or col index");
        }
        int site = rowAndColToInt(row, col);
        int rootSite = grid.find(site);
        int rootRow = intToRow(rootSite);
        int rootCol = intToCol(rootSite);
        updateConnections(rootRow, rootCol, row, col);
        return siteStatus[row][col] % 4 == 3;
    } // End isFull

    // Number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    } // End numOfOpenSites

    // Does the system percolate?
    public boolean percolates() {
        return hasPercolated;
    } // End percolates

    // For unit testing
    private void printGrid() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (isFull(i, j)) {
                    System.out.print(2 + " ");
                } else if (isOpen(i, j)) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
        if (percolates()) {
            System.out.println("The system percolates");
        } else {
            System.out.println("The system does not percolate");
        }
    } // End printGrid

    // Converts 2D coordinates into a single number
    private int rowAndColToInt(int row, int col) {
        return row * N + col;
    } // End rowAndColToInt

    // Returns the row in the grid that the given number belongs to
    private int intToRow(int num) {
        return num / N;
    } // End intToRow

    // Returns the column in the grid that the given number belongs to
    private int intToCol(int num) {
        return num % N;
    } // End intToCol

    // Updates site 2's connections after checking site 1's connections
    // Sites 1 and 2 must be connected
    private void updateConnections(int site1Row, int site1Col,
                                   int site2Row, int site2Col) {

        // Check that site 1 is not the same as site 2
        if (!(site1Row == site2Row && site1Col == site2Col)) {

            // Update connection to grid's top row
            if (siteStatus[site1Row][site1Col] % 4 == 2
                || siteStatus[site1Row][site1Col] % 4 == 3) {
                siteStatus[site2Row][site2Col] |= 2;
            }

            // Update connections to grid's bottom row
            if (siteStatus[site1Row][site1Col] >= 4) {
                siteStatus[site2Row][site2Col] |= 4;
            }

        }

    } // End updateConnections

    // Runs some unit tests
    public static void main(String[] args) {
        Percolation system = new Percolation(2);
        system.open(0, 1);
        system.open(1, 0);
        system.open(1, 1);
        system.printGrid();
    } // End main

} // End Percolation class
