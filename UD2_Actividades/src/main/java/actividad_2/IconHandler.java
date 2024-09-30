/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2;

import javax.swing.ImageIcon;

/**
 *
 * @author Gonzalo
 */
public class IconHandler {

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    public static ImageIcon createImageIcon(String path, String description) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        java.net.URL imgURL = classLoader.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
