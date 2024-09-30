/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2;

import java.io.File;

/**
 *
 * @author gag
 */
public class FileTreeNode {
    private File file;

    public FileTreeNode(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName();
    }
    
}
