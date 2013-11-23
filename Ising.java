import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Dylan Howey <dhowey@cord.edu>
 * @since  2012-5-2
 */
class Ising {

    public static Random randGen = new Random();
    public static final int SIZE = 400; //The side length of the array
    public static int[][] ferro = new int[SIZE][SIZE];
    public static BufferedImage image = new BufferedImage(SIZE, SIZE,
            BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {
        int n = 0;
        double T = 1;
        try {
            n = Integer.parseInt(args[0]); //The number of iterations
            T = Double.parseDouble(args[1]); //The temperature, units of ε/k
        } catch (Exception e) {
            System.out.println("Usage: Ising.jar [number of iterations] [temperature]");
            System.exit(0);
        }
        initializeArray(); //Randomize the array
        simulate(n, T); //Perform the simulation on the array
    }

    /**
     * Randomizes the orientation of the dipoles in the array.
     */
    public static void initializeArray() {
        int row, col;
        for (row = 0; row < SIZE; row++) {
            for (col = 0; col < SIZE; col++) {
                ferro[row][col] = random(); //Random orientation, either 1 or -1
            }
        }

    }

    /**
     * Writes the current iteration to a gif type image file.
     *
     * @param a The number of the iteration.
     * @param T The temperature of the simulation.
     */
    public static void printImage(int a, double T) {
        WritableRaster raster = image.getRaster();
        int[] rgb = new int[3]; //Index 0 - red; Index 1 - green; Index 2 - blue
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                //If the dipole is pointing one way, paint it a certain color
                if (ferro[row][col] > 0) {
                    rgb[0] = 255;
                    rgb[1] = 255;
                    rgb[2] = 0;
                } //Else, paint it a different color
                else {
                    rgb[0] = 0;
                    rgb[1] = 0;
                    rgb[2] = 255;

                }
                raster.setPixel(row, col, rgb);
            }
        }
        try {
            //Writes the image to a file
            ImageIO.write(image, "gif", new File("itr" + a + "T" + T + ".gif"));
        } catch (IOException e) {
            System.out.println("Error saving file 'itr" + a + "T" + T + ".gif'");
        }
    }

    /**
     * Performs the Metropolis algorithm on the array.
     *
     * @param n The number of iterations to perform.
     * @param T The value of the temperature in units of ε/k, where ε is the
     * energy of an antiparallel pair and k is the Boltzmann constant.
     */
    public static void simulate(int n, double T) {
        int row, col, deltaU, top, bottom, left, right, i = 0;
        final double E = Math.E; //E = 2.7182...

        while (i != n) {
            row = randGen.nextInt(SIZE); //Pick a random dipole
            col = randGen.nextInt(SIZE);

            //If the dipole is at the edge, pick the neighbor on the other side
            if (row == 0) {
                top = ferro[SIZE - 1][col];
            } else {
                top = ferro[row - 1][col];
            }

            if (col == 0) {
                left = ferro[row][SIZE - 1];
            } else {
                left = ferro[row][col - 1];
            }

            if (row == SIZE - 1) {
                bottom = ferro[0][col];
            } else {
                bottom = ferro[row + 1][col];
            }

            if (col == SIZE - 1) {
                right = ferro[row][0];
            } else {
                right = ferro[row][col + 1];
            }

            /*
             * Calculates the change in energy if the dipole were flipped. If
             * two dipoles are pointing in the same direction, they have energy
             * -ε. If they are pointing in opposite directions (antiparallel),
             * they have energy ε.
             */
            deltaU = 2 * ferro[row][col] * (top + bottom + left + right);
            //If the energy would decrease, flip the dipole
            if (deltaU <= 0) {
                ferro[row][col] *= -1;
            } //Else the probability of a flip is exp[-U/kT]
            else if (randGen.nextDouble() < Math.pow(E, -deltaU / T)) {
                ferro[row][col] *= -1;
            }
            i++;
            //Print every 500000th frame of the iteration
            if (i % 500000 == 0) {
                printImage(i, T);
            }

        }
        printImage(i, T);
    }

    /**
     * Gets a random integer that is equal to either 1 or -1.
     *
     * @return Either 1 or -1.
     */
    public static int random() {
        return (int) Math.pow(-1, randGen.nextInt(2)); //Either -1^0 or -1^1
    }
}
