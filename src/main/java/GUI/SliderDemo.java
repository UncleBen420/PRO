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

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;

/*
 * SliderDemo.java requires all the files in the images/doggy
 * directory.
 */
public class SliderDemo extends JPanel
                        implements ActionListener,
                                   WindowListener {
    //Set up animation parameters.
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 30;
    static final int FPS_INIT = 15;    //initial frames per second
    int frameNumber = 0;
    int NUM_FRAMES = 0;
    ImageIcon[] images;
    int delay;
    Timer timer;
    boolean frozen = false;
    String directory;
    ArrayList<String> imagesPath = new ArrayList<String>();

    //This label uses ImageIcon to show the doggy pictures.
    JLabel picture;

    public SliderDemo() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        delay = 1000 / FPS_INIT;

        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.LEFT);
        picture.setAlignmentX(Component.LEFT_ALIGNMENT);
        //updatePicture(0); //display first frame

        //Put everything together.
        add(picture);

        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
                                          //by restarting the timer
        timer.setCoalesce(true);
    }

    /** Add a listener for window events. */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    public void windowIconified(WindowEvent e) {
        stopAnimation();
    }
    public void windowDeiconified(WindowEvent e) {
        startAnimation();
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public void startAnimation() {
        //Start (or restart) animating!
        timer.start();
        frozen = false;
    }

    public void stopAnimation() {
        //Stop the animating thread.
        timer.stop();
        frozen = true;
    }

    //Called when the Timer fires.
    public void actionPerformed(ActionEvent e) {
       nextPicture();
    }

    /** Update the label to display the image for the current frame. */
    public void nextPicture() {
        
        frameNumber = (frameNumber + 1) % (images.length-1);
        //Set the image.
        if (images[frameNumber] != null) {
            picture.setIcon(images[frameNumber]);
        } else { //image not found
            images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
            picture.setIcon(images[frameNumber]);
        }
    }
    
    /** Update the label to display the image for the current frame. */
    public void prevPicture() {
        
        frameNumber--;
        if(frameNumber < 0){
            frameNumber = (images.length-1);
        }
        //Set the image.
        if (images[frameNumber] != null) {
            picture.setIcon(images[frameNumber]);
        } else { //image not found
            images[frameNumber] = createImageIcon(imagesPath.get(frameNumber));
            picture.setIcon(images[frameNumber]);
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        if (path != null) {
            return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(910, 450, java.awt.Image.SCALE_FAST));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDot = fileName.lastIndexOf('.');
        return fileName.substring(lastDot + 1);
    }
    
    public void addImage(String path){
        int i = 0;
        if (path != null) {
        	
            if(!imagesPath.contains(path)){
                imagesPath.clear();
                File file = new File(path);
                file = file.getParentFile();
                directory = file.getAbsolutePath();
                File[] files = file.listFiles();
                Arrays.sort(files);
                for(File image : files){
                    if(getFileExtension(image).equals("jpg")){
                        if(image.getAbsolutePath().equals(path)){
                            frameNumber = i;
                        }
                        imagesPath.add(image.getAbsolutePath());
                        i++;
                    }
                }

                images = new ImageIcon[i+1];
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
    
    public String getImage(){
        return imagesPath.get(frameNumber);
    }
    
    public String getDirectory(){
        return directory;
    }
    
    

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SliderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SliderDemo animator = new SliderDemo();
                
        //Add content to the window.
        frame.add(animator, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        animator.startAnimation(); 
    }
}

