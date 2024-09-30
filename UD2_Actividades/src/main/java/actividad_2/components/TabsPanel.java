/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.components;

import actividad_2.dialogs.DialogFileTypeError;
import actividad_2.model.TreeFile;
import actividad_2.model.TreeLeaf;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gonzalo
 */
public class TabsPanel extends JTabbedPane {

    private ArrayList<TreeLeaf> openFiles = new ArrayList<>();

    MouseListener wheel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {
                int i = TabsPanel.this.indexAtLocation(e.getX(), e.getY());
                if (i > -1) {
                    openFiles.remove(i);
                    TabsPanel.this.remove(i);
                }
            }
        }
    };

    public TabsPanel() {
        this(JTabbedPane.TOP);
    }

    private TabsPanel(int tabPlacement) {
        super(tabPlacement);
        this.addMouseListener(wheel);
    }

    public void openFile(TreeLeaf file) {
        int index = getFileIndex(file);
        if (index != -1) {
            super.setSelectedIndex(index);
            return;
        }

        if (file.isAllowed()) {
            createTab(file);
        } else {
            new DialogFileTypeError((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        }
    }

    public boolean isOpen(TreeFile file) {
        return openFiles.indexOf(file) != -1;
    }

    public void updateName(TreeFile file) {
        if (file instanceof TreeLeaf treeLeaf) {
            int index = getFileIndex(treeLeaf);
            if (index != -1) {
                super.setTitleAt(index, file.getName());
            }
        }
    }

    private int getFileIndex(TreeLeaf file) {
        return openFiles.indexOf(file);
    }

    private void createTab(TreeLeaf file) {
        this.openFiles.add(file);
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(true);
        textArea.setText(file.getContent());
        this.addTab(file.toString(), file.getIcon(), scrollPane);
        this.setSelectedComponent(scrollPane);
    }
}
