/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package actividad_2.model;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Gonzalo
 */
public abstract class TreeFile extends DefaultMutableTreeNode {

    protected File file;

    public TreeFile(File file, boolean allowsChildren) {
        super(file.getName(), allowsChildren);
        this.file = file;
    }

    public boolean rename(String newName) {
        File newFile = new File(file.getParent(), newName);
        boolean result = file.renameTo(newFile);
        if (result) {
            this.file = newFile;
        }
        return result;
    }

    public boolean delete() {
        if (file.isFile()) {
            return file.delete();
        } else if (file.list().length == 0) {
            return file.delete();
        }
        return false;
    }

    public void setName(String name) {
        this.setUserObject(name);
    }

    public String getName() {
        return file.getName();
    }
}
