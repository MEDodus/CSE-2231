

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PPMExample {

    public static void main(String[] args) throws IOException {

        // Not Part of the project... Java FileWriter instead of command line.

        PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter("image.ppm")));

        // Image

        final int imageWidth = 256;
        final int imageHeight = 256;

        // Render

//        System.out.println("P3");
//        System.out.println(imageWidth + " " + imageHeight);
//        System.out.println("255");

        // Output to file instead of console

        out.println("P3");
        out.println(imageWidth + " " + imageHeight);
        out.println("255");

        for (int j = imageHeight - 1; j >= 0; j--) {
            System.err.println("\rScanlines remaining: " + j);
            for (int i = 0; i < imageWidth; i++) {
                double r = ((double) i) / (imageWidth - 1);
                double g = ((double) j) / (imageHeight - 1);
                double b = 0.25;

                int ir = (int) (255.999 * r);
                int ig = (int) (255.999 * g);
                int ib = (int) (255.999 * b);

//                System.out.println(ir + " " + ig + " " + ib);
//                System.out.println();

                out.println(ir + " " + ig + " " + ib);
                out.flush();
            }
        }
        out.close();
    }

}
