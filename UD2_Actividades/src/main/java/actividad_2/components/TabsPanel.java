/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.components;

import actividad_2.dialogs.DialogCloseConfirm;
import actividad_2.dialogs.DialogManager;
import actividad_2.model.TreeFile;
import actividad_2.model.TreeLeaf;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Gonzalo
 */
public class TabsPanel extends JTabbedPane {

    private ArrayList<TreeLeaf> openFiles = new ArrayList<>();
    private HashMap<String, Integer> openMap = new HashMap<>();
    private FileChangeListener fileChangeListener;
    private int lastIndex = -1;

    MouseListener wheel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {
                int i = TabsPanel.this.indexAtLocation(e.getX(), e.getY());
                if (i > -1) {
                    TreeLeaf file = openFiles.get(i);
                    DialogCloseConfirm.CLOSE_OPTION closeOption = file.isModified()
                            ? DialogManager.showCloseWithoutSave((JFrame) SwingUtilities.getWindowAncestor(TabsPanel.this))
                            : DialogCloseConfirm.CLOSE_OPTION.DISCARD;
                    if (closeOption == DialogCloseConfirm.CLOSE_OPTION.DISCARD) {
                        file.setModified(false);
                        openFiles.remove(i);
                        TabsPanel.this.remove(i);
                    } else if (closeOption == DialogCloseConfirm.CLOSE_OPTION.SAVE && saveCurrentTab()) {
                        file.setModified(false);
                        openFiles.remove(i);
                        TabsPanel.this.remove(i);
                    }
                }
            }
        }
    };

    ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            int index = getSelectedIndex();
            if (Objects.isNull(fileChangeListener)) {
                return;
            }
            if (lastIndex != index) {
                lastIndex = index;
                fileChangeListener.fileChanged(index == -1 ? null : openFiles.get(index));
            }
        }
    };

    public TabsPanel() {
        this(JTabbedPane.TOP);
    }

    private TabsPanel(int tabPlacement) {
        super(tabPlacement);
        this.addMouseListener(wheel);
        this.addChangeListener(changeListener);
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
            DialogManager.showFileTypeError((JFrame) SwingUtilities.getWindowAncestor(this));
        }
    }

    public void updateName(TreeFile file) {
        if (file instanceof TreeLeaf treeLeaf) {
            int index = getFileIndex(treeLeaf);
            if (index != -1) {
                super.setTitleAt(index, file.getName());
            }
        }
    }

    public boolean saveCurrentTab() {
        int index = this.getSelectedIndex();
        if (index > -1) {
            JScrollPane scrollPane = (JScrollPane) this.getComponentAt(index);
            JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
            TreeLeaf file = this.openFiles.get(index);
            return file.save(textArea.getText());
        }
        return true;
    }

    public void removeFile(TreeLeaf file) {
        int index = getFileIndex(file);
        if (index > -1) {
            this.remove(index);
            this.openFiles.remove(index);
        }
    }

    public void setFileChangeListener(FileChangeListener listener) {
        this.fileChangeListener = listener;
    }

    private int getFileIndex(TreeLeaf file) {
        int index = openFiles.indexOf(file);
        if (index != -1) {
            return index;
        }
        var found = openFiles.stream().filter((f) -> f.getAbsolutePath().equals(file.getAbsolutePath())).findFirst();
        if (found.isPresent()) {
            index = openFiles.indexOf(found.get());
            openFiles.set(index, file);
            return index;
        }

        return -1;
    }

    private void createTab(TreeLeaf file) {
        this.openFiles.add(file);
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(true);
        textArea.setText(file.getContent());
        textArea.getDocument().addDocumentListener(new DocumentChangeListener(file));
        this.addTab(file.toString(), file.getIcon(), scrollPane);
        this.setSelectedComponent(scrollPane);
    }

    private class DocumentChangeListener implements DocumentListener {

        private final TreeLeaf file;

        public DocumentChangeListener(TreeLeaf file) {
            this.file = file;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            onChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            onChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            onChange();
        }

        private void onChange() {
            file.setModified(true);
            if (Objects.nonNull(fileChangeListener)) {
                fileChangeListener.fileChanged(file);
            }
        }
    }
}
