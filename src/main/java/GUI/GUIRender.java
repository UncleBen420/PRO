/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import JTreeManager.JTreeManager;
import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Groupe PRO B-9
 */
public class GUIRender {
    
    private static final Color CRAPA_VIOLET = new Color(53,33,89);
    private static final Color BUTTON_VIOLET = new Color(38,19,66);
    private static final Color BUTTON_VIOLET_SEL = new Color(105, 43, 160);
    private static Font mainTitle;
    private static Font sectionTitle;
    private static Font element;
    private static Font elementSelected;

    public GUIRender() {
        try {
            String path1 = "/fonts/OpenSans-Regular.ttf";
            InputStream is1 = this.getClass().getResourceAsStream(path1);
            Font f1 = Font.createFont(Font.TRUETYPE_FONT, is1);
            
            String path2 = "/fonts/OpenSans-Bold.ttf";
            InputStream is2 = this.getClass().getResourceAsStream(path2);
            Font f2 = Font.createFont(Font.TRUETYPE_FONT, is2);

            mainTitle = f2.deriveFont(Font.PLAIN, 20);
            sectionTitle = f1.deriveFont(Font.PLAIN, 18);
            element = f1.deriveFont(Font.PLAIN, 16);
            elementSelected = f1.deriveFont(Font.BOLD, 16);

        } catch (Exception ex) {
            Logger.getLogger(JTreeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Color getForeColor() {
        return Color.WHITE;
    }

    public static Color getBackColor() {
        return CRAPA_VIOLET;
    }

    public static Color getButtonColor() {
        return BUTTON_VIOLET;
    }

    public static Color getButtonSelectedColor() {
        return BUTTON_VIOLET_SEL;
    }

    public static Font getSectionTitle() {
        return sectionTitle;
    }

    public static Font getMainTitle() {
        return mainTitle;
    }

    public static Font getElementSelected() {
        return elementSelected;
    }

    public static Font GetElement() {
        return element;
    }


}
