/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Gonzalo
 */
public class TreeBranch extends TreeFile {

    public static enum ADD_FILE_RESULT {
        NAME_IN_USE, ERROR, ADDED;
    }

    private boolean isLoaded;

    public TreeBranch(File file) {
        this(file, false);
    }

    public TreeBranch(File file, boolean isLoaded) {
        super(file, true);
        this.isLoaded = isLoaded;
        if (!isLoaded) {
            resetChildren();
        }
    }

    public final void resetChildren() {
        this.isLoaded = false;
        removeAllChildren();
        if (this.file.list().length > 0) {
            this.add(new DefaultMutableTreeNode("PLACEHOLDER"));
        }
    }

    public boolean loadChildren() {
        if (!this.isLoaded) {
            reloadChildren();
            return true;
        }
        return false;
    }

    public void reloadChildren() {
        removeAllChildren();
        DefaultMutableTreeNode node;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                node = new TreeBranch(f);
            } else {
                node = new TreeLeaf(f);
            }
            this.add(node);
        }
        this.isLoaded = true;
    }

    @NotNull
    public ADD_FILE_RESULT addNewFile(String name, boolean isDirectory) {
        File newFile = new File(file, name);
        try {
            if (!createFile(newFile, isDirectory)) {
                return ADD_FILE_RESULT.NAME_IN_USE;
            }
        } catch (Exception ex) {
            return ADD_FILE_RESULT.ERROR;
        }
        TreeFile treeFile = newFile.isDirectory() ? new TreeBranch(newFile) : new TreeLeaf(newFile);
        this.add(treeFile);
        this.reloadChildren();
        return ADD_FILE_RESULT.ADDED;
    }

    private boolean createFile(File file, boolean isDirectory) throws IOException {
        if (isDirectory) {
            return file.mkdir();
        }
        return file.createNewFile();
    }
}
