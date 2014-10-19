package hw_percolation;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private final int T;
    private double [] noOfOpenSites;

// perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("T or N is not larger than 0!");
        this.T = T;
        noOfOpenSites = new double [T];
        for (int k = 0; k < T; k++) {
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, N+1); // row index
                int j = StdRandom.uniform(1, N+1); // column index
                if (!perc.isOpen(i, j)) {
                    noOfOpenSites[k] += 1.0 / (N * N);
                    perc.open(i, j);
                }
            }
        }
    }

// sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(noOfOpenSites);
    }
// sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1) return Double.NaN;
        else return StdStats.stddev(noOfOpenSites);
    }

// returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        if (T == 1) return mean();
        else return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

// returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        if (T == 1) return mean();
        else return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

// test client, described below
    public static void main(String[] args) {
        final int N = Integer.parseInt(args[0]);
        final int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " + percStats.confidenceLo()
                        + ", " + percStats.confidenceHi());
    }
}
