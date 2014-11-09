package tests.seamcarver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import hw_seamcarver.SeamCarver;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdRandom;

public class TestSeamCarver {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testWidthHeight() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        assertEquals(4, sc.width());
        assertEquals(6, sc.height());
    }

    @Test
    public void testEnergy() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        double[][] energy = new double[sc.width()][];
        energy[0] = new double[]{195075, 195075, 195075, 195075, 195075,
                                195075};
        energy[1] = new double[]{195075, 75990, 30002, 29515, 73403, 195075};
        energy[2] = new double[]{195075, 30003, 103046, 38273, 35399, 195075};
        energy[3] = new double[]{195075, 195075, 195075, 195075, 195075,
                                195075};
        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                assertEquals(energy[i][j], sc.energy(i, j), 1e-15);
            }
        }
    }

    @Test
    public void testFindVerticalSeam4x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expected = new int[]{1, 2, 1, 1, 2, 1};
        int[] path = sc.findVerticalSeam();
        for (int i = 0; i < expected.length; i++) {
        //for (int i = expected.length - 1; i >= 0; i++) {
            assertEquals("Row " + i + ": " + expected[i] + "!=" + path[i],
                    expected[i], path[i]);
        }
    }

    @Test
    public void testFindVerticalSeam10x12() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expected = new int[]{5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5};
        sc.findHorizontalSeam();
        int[] path = sc.findVerticalSeam();
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Row " + i + ": " + expected[i] + "!=" + path[i],
                    expected[i], path[i]);
        }
    }
    @Test
    public void testFindHorizontalSeam4x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expected = new int[]{1, 2, 1, 0};
        int[] path = sc.findHorizontalSeam();
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Row " + i + ": " + expected[i] + "!=" + path[i],
                    expected[i], path[i]);
        }
    }

    @Test
    public void testFindHorizontalSeam10x12() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] expected = new int[]{8, 9, 10, 10, 10, 9, 10, 10, 9, 8};
        int[] path = sc.findHorizontalSeam();
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Row " + i + ": " + expected[i] + "!=" + path[i],
                    expected[i], path[i]);
        }
        assertEquals(10, sc.width());
        assertEquals(12, sc.height());
    }

    @Test
    public void testRemoveVerticalSeam3x7() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\3x7.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam4x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam5x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\5x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam6x5() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\6x5.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam7x3() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\7x3.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam10x12() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = 9, height = 12;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        //System.out.println("Original picture:");
        //displayPicture(inputImg);
        //System.out.println("Current picture:");
        //displayPicture(result);;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveVerticalSeam12x10() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\12x10.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findVerticalSeam();
        sc.removeVerticalSeam(a);
        int width = inputImg.width() - 1, height = inputImg.height();
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (x >= a[y]) {
                    assertEquals(msg, inputImg.get(x + 1, y), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam3x7() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\3x7.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam4x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\4x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam5x6() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\5x6.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam6x5() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\6x5.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam7x3() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\7x3.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam10x12() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\10x12.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = 10, height = 11;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        //System.out.println("Original picture:");
        //displayPicture(inputImg);
        //System.out.println("Current picture:");
        //displayPicture(result);;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam12x10() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\12x10.png");
        SeamCarver sc = new SeamCarver(inputImg);
        int[] a = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(a);
        int width = inputImg.width(), height = inputImg.height() - 1;
        assertEquals(width, sc.width());
        assertEquals(height, sc.height());
        Picture result = sc.picture();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String msg = "[" + x + ", " + y + "]";
                if (y >= a[x]) {
                    assertEquals(msg, inputImg.get(x, y + 1), result.get(x,y));
                } else {
                    assertEquals(msg, inputImg.get(x, y), result.get(x,y));
                }
            }
        }
    }

    // Check intermixed calls to findHorizontalSeam(), findVerticalSeam(),
    // removeHorizontalSeam(), removeVerticalSeam(). Probabilities of calling
    // each are 0.25, 0.25, 0.25, and 0.25 respectively.
    @Test
    public void testIntermixedCalls7x9() {
        for (int j = 0; j < 50; j++) {
            System.out.println("Test Intermixed Calls 7x9: run #" + (j + 1));
            Picture inputImg = SCUtility.randomPicture(7, 9);
            SeamCarver sc = new SeamCarver(inputImg);
            int calls = 5;
            for (int i = 0; i < calls; i++) {
                int index = StdRandom.uniform(4);
                int[] a;
                switch(index) {
                    case 0:
                        System.out.println("Find vertical seam");
                        sc.findVerticalSeam(); break;
                    case 1:
                        System.out.println("Find horizontal seam");
                        sc.findHorizontalSeam(); break;
                    case 2: a = SCUtility.randomSeam(sc.width(), sc.height());
                            System.out.println("size = " + sc.width() + ", " + sc.height());
                            System.out.println("Horitontal seam:" + Arrays.toString(a));
                            sc.removeHorizontalSeam(a);
                            break;
                    case 3: a = SCUtility.randomSeam(sc.height(), sc.width());
                            System.out.println("size = " + sc.width() + ", " + sc.height());
                            System.out.println("Vertical seam:" + Arrays.toString(a));
                            sc.removeVerticalSeam(a);
                            break;
                }
            }
        }
    }

    // Check intermixed calls to findHorizontalSeam(), findVerticalSeam(),
    // removeHorizontalSeam(), removeVerticalSeam(). Probabilities of calling
    // each are 0.25, 0.25, 0.25, and 0.25 respectively.
    @Test
    public void testIntermixedCalls20x20() {
        for (int j = 0; j < 50; j++) {
            System.out.println("Test Intermixed Calls 20x20: run #" + (j + 1));
            Picture inputImg = SCUtility.randomPicture(20, 20);
            SeamCarver sc = new SeamCarver(inputImg);
            int calls = 15;
            for (int i = 0; i < calls; i++) {
                int index = StdRandom.uniform(4);
                int[] a;
                switch(index) {
                    case 0:
                        System.out.println("Find vertical seam");
                        sc.findVerticalSeam(); break;
                    case 1:
                        System.out.println("Find horizontal seam");
                        sc.findHorizontalSeam(); break;
                    case 2: a = SCUtility.randomSeam(sc.width(), sc.height());
                            System.out.println("size = " + sc.width() + ", " + sc.height());
                            System.out.println("Horitontal seam:" + Arrays.toString(a));
                            sc.removeHorizontalSeam(a);
                            break;
                    case 3: a = SCUtility.randomSeam(sc.height(), sc.width());
                            System.out.println("size = " + sc.width() + ", " + sc.height());
                            System.out.println("Vertical seam:" + Arrays.toString(a));
                            sc.removeVerticalSeam(a);
                            break;
                }
            }
        }
    }

    // Check memory usage
    @Test
    public void testMemory() {
        Picture inputImg = SCUtility.randomPicture(200, 200);
        SeamCarver sc = new SeamCarver(inputImg);
        Runtime runtime = Runtime.getRuntime();
        long freeMemory0 = runtime.freeMemory();
        System.out.println("allocated memory: " + runtime.totalMemory() / 1024);
        System.out.println("free memory: " + freeMemory0 / 1024);
        int n = 5;
        int[] a;
        for (int i = 0; i < n; i++) {
            a = SCUtility.randomSeam(sc.width(), sc.height());
            sc.removeHorizontalSeam(a);
        }
        long freeMemory1 = runtime.freeMemory();
        System.out.println("allocated memory: " + runtime.totalMemory() / 1024);
        System.out.println("free memory: " + freeMemory1 / 1024);
        System.out.println("Used memory: " + (freeMemory0 - freeMemory1));
    }

    @Test
    public void testPerformance() {
        Picture inputImg = new Picture("src\\tests\\seamcarver\\HJocean.png");
        System.out.printf("image is %d columns by %d rows\n",
                inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);

        Stopwatch sw = new Stopwatch();
        int removeRows = 100;
        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }

        int removeColumns = 100;
        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        //Picture outputImg = sc.picture();
        System.out.printf("new image size is %d columns by %d rows\n",
                sc.width(), sc.height());
        System.out.println("Resizing time: " + sw.elapsedTime() + " seconds.");
    }


    //private void displayPicture(Picture pic) {
        //int width = pic.width(), height = pic.height();
        //for (int y = 0; y < height; y++) {
            //for (int x = 0; x < width; x++) {
                //System.out.print(pic.get(x, y) + " ");
            //}
            //System.out.println("");
        //}
    //}


}
