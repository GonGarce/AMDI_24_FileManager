/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.components;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Gonzalo
 */
public class ContextMenu extends JPopupMenu {

    public enum Options {
        RENAME("Rename"),
        REFRESH("Refresh"),
        NEW_FOLDER("New Folder"),
        NEW_FILE("New File"),
        DELETE("Delete");
        public final String label;

        private Options(String label) {
            this.label = label;
        }

    }

    public ContextMenu(ActionListener listener) {
        for (Options o : Options.values()) {
            JMenuItem menuItem = new JMenuItem(o.label);
            menuItem.addActionListener(listener);
            this.add(menuItem);
        }
    }
}
