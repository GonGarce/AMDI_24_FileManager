/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.model;

import javax.swing.ImageIcon;

/**
 *
 * @author Gonzalo
 */
public enum TreeIcon {
    OPEN("images/folder_open.png"),
    CLOSED("images/folder_close.png"),
    FILE_UNK("images/file_unk.png"),
    FILE_TEXT("images/file_text.png");

    private final String path;
    private final ImageIcon icon;

    private TreeIcon(String path) {
        this.path = path;
        this.icon = TreeIcon.createImageIcon(path, "");
    }

    public ImageIcon getIcon() {
        return icon;
    }
    
        /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    private static ImageIcon createImageIcon(String path, String description) {
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
