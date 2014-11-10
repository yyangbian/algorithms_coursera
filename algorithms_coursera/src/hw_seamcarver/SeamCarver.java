package hw_seamcarver;

import java.awt.Color;

import edu.princeton.cs.introcs.Picture;

public class SeamCarver {
    private static final int BORDER_ENERGY = 195075;
    private int[][] img;
    private int width, height;
    private double[][] energy;
    private double[][] distTo;
    private int[][] pixelTo;
    private boolean transposed;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        transposed = false;
        img = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img[y][x] = picture.get(x, y).getRGB();
            }
        }
        energy = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                energy[y][x] = energy(x, y);
            }
        }
    }

    // Returns the current picture
    public Picture picture() {
        if (transposed) {
            transpose();
            transposed = false;
        }
        Picture pic = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pic.set(x, y, new Color(img[y][x]));
            }
        }
        return pic;
    }

    // width of current picture
    public int width() {
        if (transposed) {
            return height;
        }
        return width;
    }
    // height of current picture
    public int height() {
        if (transposed) {
            return width;
        }
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException(
                    "x = " + x + ", width = " + width + ";y = " + y
                    + ", height = " + height);
        }
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            return BORDER_ENERGY;
        } else {
            return energyx(x, y) + energyy(x, y);
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!transposed) {
            transpose();
            transposed = true;
        }
        return findSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (transposed) {
            transpose();
            transposed = false;
        }
        return findSeam();
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] a) {
        if (!validSeam(a, height() - 1)) {
            throw new IllegalArgumentException("Input seam is not valid");
        }
        if (a.length != width()) {
            throw new IllegalArgumentException(
                    "Input seam is not equal to picture width");
        }
        if (height() <= 1) {
            throw new IllegalArgumentException("Picture height <= 1");
        }
        if (!transposed) {
            transpose();
            transposed = true;
        }
        removeSeam(a);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] a) {
        if (!validSeam(a, width() - 1)) {
            throw new IllegalArgumentException("Input seam is not valid");
        }
        if (a.length != height()) {
            throw new IllegalArgumentException(
                    "Input seam is not equal to picture height");
        }
        if (width() <= 1) {
            throw new IllegalArgumentException("Picture width <= 1");
        }
        if (transposed) {
            transpose();
            transposed = false;
        }
        removeSeam(a);
    }

    // Calculate internal pixel energy in x direction
    // Assume pixel (x, y) is not on the border of the picture
    private double energyx(int x, int y) {
        Color cleft = new Color(img[y][x - 1]);
        Color cright = new Color(img[y][x + 1]);
        double dred = cleft.getRed() - cright.getRed();
        double dgreen = cleft.getGreen() - cright.getGreen();
        double dblue = cleft.getBlue() - cright.getBlue();
        return dred * dred + dgreen * dgreen + dblue * dblue;
    }

    // Calculate internal pixel energy in y direction
    // Assume pixel (x, y) is not on the border of the picture
    private double energyy(int x, int y) {
        Color ctop = new Color(img[y - 1][x]);
        Color cbottom = new Color(img[y + 1][x]);
        double dred = ctop.getRed() - cbottom.getRed();
        double dgreen = ctop.getGreen() - cbottom.getGreen();
        double dblue = ctop.getBlue() - cbottom.getBlue();
        return dred * dred + dgreen * dgreen + dblue * dblue;
    }

    // sequence of indices for vertical seam from the current energy matrix
    private int[] findSeam() {
        distTo = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0) {
                    // initialize the first row
                    distTo[y][x] = energy[y][x];
                } else {
                    distTo[y][x] = Double.POSITIVE_INFINITY;
                }
            }
        }
        pixelTo = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                relax(x, y);
            }
        }
        return findPath();
    }

    // remove vertical seam from the current img array
    // assume input seam is valid
    private void removeSeam(int[] a) {
        width = width - 1;
        int[][] oldimg = img;
        img = new int[height][width];
        for (int y = 0; y < height; y++) {
            int p = a[y];
            System.arraycopy(oldimg[y], 0, img[y], 0, p);
            System.arraycopy(oldimg[y], p + 1, img[y], p, width - p);
        }
        double[][] oldenergy = energy;
        energy = new double[height][width];
        for (int y = 0; y < height; y++) {
            int p = a[y];
            System.arraycopy(oldenergy[y], 0, energy[y], 0, p);
            System.arraycopy(oldenergy[y], p + 1, energy[y], p, width - p);
            // not at left boundary
            if (p != 0) {
                energy[y][p - 1] = energy(p - 1, y);
            }
            // not at right boundary
            if (p < width) {
                energy[y][p] = energy(p, y);
            }
        }
    }

    // Relax pixel at of index i at (i % width, i / width)
    //private void relax(int i) {
    private void relax(int x, int y) {
        if (y >= height - 1) {
            return;
        }
        if (x > 0) {
            updateAdj(x, y, -1);
        }
        if (x < width - 1) {
            updateAdj(x, y, 1);
        }
        updateAdj(x, y, 0);
    }

    // Update distTo[x + direction][y + 1] which is one neighbor of [x, y]
    private void updateAdj(int x, int y, int direction) {
        int xadj = x + direction, yadj = y + 1;
        if (distTo[yadj][xadj] > distTo[y][x] + energy[yadj][xadj]) {
            distTo[yadj][xadj] = distTo[y][x] + energy[yadj][xadj];
            pixelTo[yadj][xadj] = x;
        }
    }

    // Find the seam path based on current distTo and pixelTo array
    private int[] findPath() {
        int end = 0;
        for (int x = 0; x < width; x++) {
            if (distTo[height - 1][end] > distTo[height - 1][x]) {
                end = x;
            }
        }
        int[] path = new int[height];
        path[height - 1] = end;
        for (int i = height - 2; i >= 0; i--) {
            path[i] = pixelTo[i + 1][path[i + 1]];
        }
        pixelTo = null;
        distTo = null;
        return path;
    }

    // Transpose the current picture
    private void transpose() {
        double[][] energyt = new double[width][height];
        int[][] imgt = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                energyt[x][y] = energy[y][x];
                imgt[x][y] = img[y][x];
            }
        }
        energy = energyt;
        img = imgt;
        energyt = null;
        imgt = null;
        int tmp = width;
        width = height;
        height = tmp;
    }

    // Check if the input seam is valid
    // Return false if two adjacent entries differ by more than one or the
    // entry is not within the prescribed range [0, max]
    private boolean validSeam(int[] a, int max) {
        for (int i = 1; i < a.length; i++) {
            if (Math.abs(a[i - 1] - a[i]) > 1) {
                return false;
            }
            if (a[i] < 0 || a[i] > max) {
                return false;
            }
        }
        return true;
    }
}
