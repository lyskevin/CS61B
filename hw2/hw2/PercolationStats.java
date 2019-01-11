package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * Performs the Monte Carlo simulation to estimate the percolation threshold.
 */
public class PercolationStats {

    private Percolation system;
    private double[] percolationThresholds;
    private double mean;
    private double standardDeviation;
    private int numExperiments;

    // Perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should both be > 0");
        }
        percolationThresholds = new double[T];
        for (int i = 0; i < T; i++) {
            system = pf.make(N);
            percolationThresholds[i] = computePercolationThreshold(N);
        }
        mean = StdStats.mean(percolationThresholds);
        standardDeviation = StdStats.stddev(percolationThresholds);
        numExperiments = T;
    } // End PercolationStats constructor

    // Sample mean of percolation threshold
    public double mean() {
        return this.mean;
    } // End mean

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return standardDeviation;
    } // End stddev

    // Low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - ((1.96 * standardDeviation) / Math.sqrt(numExperiments));
    } // End confidenceLow

    // High endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + ((1.96 * standardDeviation) / Math.sqrt(numExperiments));
    } // End confidenceHigh

    // Computes the percolation threshold for a given grid of size N
    private double computePercolationThreshold(int N) {
        while (!system.percolates()) {
            int randomSite = StdRandom.uniform(N * N);
            int rowToOpen = randomSite / N;
            int colToOpen = randomSite % N;
            system.open(rowToOpen, colToOpen);
        }
        return system.numberOfOpenSites() * 1.0 / (N * N);
    } // End computePercolationThreshold

} // End PercolationStats class
