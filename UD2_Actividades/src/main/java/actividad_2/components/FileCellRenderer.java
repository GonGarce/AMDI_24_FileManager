/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.components;

import actividad_2.model.TreeIcon;
import actividad_2.model.TreeLeaf;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Gonzalo
 */
public class FileCellRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (expanded) {
            setIcon(TreeIcon.OPEN.getIcon());
        } else if (node instanceof TreeLeaf file) {
            setIcon(file.getIcon());
            //setToolTipText("This book is in the Tutorial series.");
        } else {
            setIcon(TreeIcon.CLOSED.getIcon());
        }

        return this;
    }
}
