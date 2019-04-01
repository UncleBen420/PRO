package ShowImage;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;


public class ShowImage {

     public static void main(String[] arguments) throws IOException {

        JPanel panel = new JPanel();

        BufferedImage image = ImageIO.read(new File("src/main/java/ShowImage/images/ftl.jpg"));
        JLabel label = new JLabel(new ImageIcon(image));
        panel.add(label);

        // main window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("JPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the Jpanel to the main window
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

    }
}
