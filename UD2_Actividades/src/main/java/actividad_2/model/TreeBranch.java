/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.model;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Gonzalo
 */
public class TreeBranch extends TreeFile {

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
                // TODO
            } else {
                node = new TreeLeaf(f);
            }
            this.add(node);
        }
        this.isLoaded = true;
    }
}
