package imageparser;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Cette classe permet de faire des operations d'analyse sur une image
 *
 * @author Groupe PRO B-9
 */
public class ImageParser {

    /**
     * Recupere un pixel
     *
     * @param path chemin de l'image
     * @param x indice x du pixel
     * @param y indice y du pixel
     * @return le pixel au format entier
     * @throws IOException
     */
    public int getPixel(String path, int x, int y) throws IOException {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        // Getting pixel color by position x and y

        return image.getRGB(x, y);

    }

    /**
     * Recupere un buffer de pixel
     *
     * @param path chemin de l'image
     * @return retourne un tableau de byte contenant les pixel
     * @throws IOException
     */
    public byte[] getPixelBuffer(String path) throws IOException {

        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

    }

    /**
     * Compare deux images et retourne un ratio de correspondance
     * @param path1 chemin de l'image 1
     * @param path2 chemin de l'image 2
     * @param hCut coupure dans la hauteur
     * @param wCut coupure dans la largeur
     * @param correctness pourcentage de ressemblance autorise
     * @param precision nombre de pixel analyse dans l'image
     * @return retourne le pourcentage de ressemblance de l'image
     * @throws Exception
     */
    public int compareImageRatioOpti(String path1, String path2, int hCut, int wCut, int correctness, int precision) throws Exception {

        File file = new File(path1);
        BufferedImage image1 = ImageIO.read(file);
        file = new File(path2);
        BufferedImage image2 = ImageIO.read(file);

        if (image1.getWidth() != image2.getTileWidth() || image1.getHeight() != image2.getHeight()) {
            throw new Exception("The images are not the same size");
        }

        double ratio = 0;

        int incrementI = image1.getHeight() / precision;
        int incrementJ = image1.getWidth() / precision;
        int counter = 0;

        for (int i = 0; i < image1.getHeight() - hCut; i += incrementI) {
            for (int j = 0; j < image1.getWidth() - wCut; j += incrementJ) {

                counter++;

                ratio += comparePixelTolerance(image1.getRGB(j, i), image2.getRGB(j, i), correctness);
            }
        }
        ratio = ratio / counter * 100;

        return (int) ratio;

    }

    /**
     * Compare deux pixels et renvoie leur différence
     * @param pixel1 premier pixel
     * @param pixel2 deuxieme pixel
     * @param correctness taux de ressemblance accepter
     * @return pourcentage de ressemblance
     */
    public int comparePixelTolerance(int pixel1, int pixel2, int correctness) {

        int match = 1;

        for (int i = 0; i <= 16; i += 8) {

            int color1 = pixel1 & 0x000000ff;
            int color2 = pixel2 & 0x000000ff;

            double min = Math.min(color1, color2);
            double max = Math.max(color1, color2);

            int percent = (int) (min / max * 100);

            if (percent < correctness) {
                match = 0;
            }

        }

        return match;

    }


}
