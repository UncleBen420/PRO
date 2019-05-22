/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package GUI;

import Tag.CsvParser;
import Tag.Parser;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Classe implementant le slider d'images
 *
 * @author Groupe PRO B-9
 */
public class SliderDemo extends JPanel
        implements ActionListener,
        WindowListener {

    private static final long serialVersionUID = -2296941579498067629L;
    //Set up animation parameters.
    private static final int FPS_INIT = 15;    //initial frames per second
    private int frameNumber = 0;
    private ImageIcon[] images;
    private final int delay;
    private final Timer timer;
    private String directory;
    private final ArrayList<String> imagesPath;
    private final Parser parserTag;
    private ViewerTable table;

    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;

    /**
     * Constructeur
     */
    public SliderDemo() {
        this.parserTag = new Parser();
        this.imagesPath = new ArrayList<>();
        this.images = new ImageIcon[0];
        this.directory = "";
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        delay = 1000 / FPS_INIT;

        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.LEFT);
        picture.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Put everything together.
        add(picture);

        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
        //by restarting the timer
        timer.setCoalesce(true);
    }

    /**
     * Add a listener for window events.
     */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    @Override
    public void windowIconified(WindowEvent e) {
        stopAnimation();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        startAnimation();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Demarre l'animation
     */
    public void startAnimation() {
        //Start (or restart) animating!
        timer.start();
    }

    /**
     * Stoppe l'animation
     */
    public void stopAnimation() {
        //Stop the animating thread.
        timer.stop();
    }

    //Called when the Timer fires.
    @Override
    public void actionPerformed(ActionEvent e) {
        nextPicture();
    }

    /**
     * Update the label to display the image for the current frame.
     */
    public void nextPicture() {

        if(images.length > 0){
            frameNumber = (frameNumber + 1) % (images.length - 1);
            //Set the image.
            if (images[frameNumber] != null) {
                picture.setIcon(images[frameNumber]);
            } else { //image not found
                images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
                picture.setIcon(images[frameNumber]);
            }

            try {
                table.setTags(CsvParser.getTag(parserTag.getTag(imagesPath.get(frameNumber))));
            } catch (IOException ex) {
                Logger.getLogger(SliderDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Update the label to display the image for the current frame.
     */
    public void prevPicture() {

        if(images.length > 0){
            frameNumber--;
            if (frameNumber < 0) {
                frameNumber = (images.length - 1);
            }
            //Set the image.
            if (images[frameNumber] != null) {
                picture.setIcon(images[frameNumber]);
            } else { //image not found
                images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
                picture.setIcon(images[frameNumber]);
            }

            try {
                table.setTags(CsvParser.getTag(parserTag.getTag(imagesPath.get(frameNumber))));
            } catch (IOException ex) {
                Logger.getLogger(SliderDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path Chemin de l'image
     * @return Icone de l'image
     */
    protected static ImageIcon createImageIcon(String path) {
        if (path != null) {
            return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(1250, 550, java.awt.Image.SCALE_FAST));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Retourne l'extension d'un fichier
     * 
     * @param file fichier
     * @return extension du fichier
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        return fileName.substring(lastDot + 1);
    }

    /**
     * Ajoute les images au slider
     * 
     * @param path chemin de l'image
     */
    public void addImage(String path) {
        int i = 0;
        if (path != null) {

            if (!imagesPath.contains(path)) {
                imagesPath.clear();
                File file = new File(path);
                file = file.getParentFile();
                directory = file.getAbsolutePath();
                File[] files = file.listFiles();
                Arrays.sort(files);
                for (File image : files) {
                    if (getFileExtension(image).equals("jpg")) {
                        if (image.getAbsolutePath().equals(path)) {
                            frameNumber = i;
                        }
                        imagesPath.add(image.getAbsolutePath());
                        i++;
                    }
                }

                images = new ImageIcon[i + 1];
                images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
                picture.setIcon(images[frameNumber]);
            } else {
                frameNumber = imagesPath.indexOf(path);
                //Set the image.
                if (images[frameNumber] != null) {
                    picture.setIcon(images[frameNumber]);
                } else { //image not found
                    images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
                    picture.setIcon(images[frameNumber]);
                }
            }

        } else {
            System.err.println("Couldn't find file: " + path);
        }
    }

    /**
     * Retourne le chemin de l'image actuelle
     * 
     * @return chemin de l'image actuelle
     */
    public String getImage() {
        if(imagesPath.size() > 0){
            return imagesPath.get(frameNumber);
        }
        return null;
    }

    /**
     * Retourne le chemin du dossier de l'image actuelle
     * 
     * @return chemin du dossier de l'image actuelle
     */
    public String getDirectory() {
        if(directory.equals("")){
            return null;
        }
        return directory;
    }

    /**
     * Setteur du tableau de tags
     * 
     * @param table tableau de tags
     */
    public void setTable(ViewerTable table) {
        this.table = table;
    }
}
