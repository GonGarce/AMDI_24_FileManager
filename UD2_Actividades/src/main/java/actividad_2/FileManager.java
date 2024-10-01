/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package actividad_2;

import actividad_2.components.ContextMenu;
import actividad_2.components.FileCellRenderer;
import actividad_2.components.TabsPanel;
import actividad_2.dialogs.DialogManager;
import actividad_2.dialogs.DialogRename;
import actividad_2.model.TreeBranch;
import actividad_2.model.TreeFile;
import actividad_2.model.TreeLeaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Objects;
import static java.util.Objects.isNull;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author gag
 */
public class FileManager extends javax.swing.JFrame implements TreeWillExpandListener {

    private TabsPanel tabs;
    private DefaultTreeModel treeModel;
    private ContextMenu contextMenu;
    private JMenuItem menuSave;

    ActionListener menuAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            if (ContextMenu.Options.RENAME.label.equals(e.getActionCommand())) {
                openRenameDialog((TreeFile) node);
            } else if (ContextMenu.Options.REFRESH.label.equals(e.getActionCommand())) {
                TreeBranch firstBranch;
                if (node instanceof TreeBranch treeBranch) {
                    firstBranch = treeBranch;
                } else {
                    firstBranch = (TreeBranch) node.getParent();
                }
                firstBranch.reloadChildren();
                treeModel.reload(firstBranch);
            }
        }
    };

    MouseListener ml = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int row = jTree1.getClosestRowForLocation(e.getX(), e.getY());
                jTree1.setSelectionRow(row);
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            } else {
                int selRow = jTree1.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTree1.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {
                        //mySingleClick(selRow, selPath);
                    } else if (e.getClickCount() == 2) {
                        onDoubleClick(selPath);
                    }
                }
            }
        }
    };

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
        if (node instanceof TreeBranch treeBranch) {
            expandFolder(treeBranch);
        }
    }

    private void onDoubleClick(TreePath path) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node instanceof TreeLeaf treeLeaf) {
            tabs.openFile(treeLeaf);
        }
    }

    private void expandFolder(TreeBranch node) {
        if (node.loadChildren()) {
            treeModel.reload(node);
        }
    }

    private void openRenameDialog(TreeFile fileTree) {
        String name = DialogManager.showRenameDialog(this, fileTree.getName());
        if (!name.equals(fileTree.getName())) {
            if (fileTree.rename(name)) {
                fileTree.setName(name);
                treeModel.reload(fileTree);
                tabs.updateName(fileTree);
            } else {
                DialogManager.showRenameError(this);
            }
        }
    }

    private void chooseFile() {
        File file = DialogManager.showFileChooser(this);
        if (Objects.nonNull(file)) {
            loadFileTree(file);
        }
    }

    private void loadFileTree(File root) {
        TreeBranch top = new TreeBranch(root);
        top.loadChildren();
        treeModel = new DefaultTreeModel(top);
        jTree1.setModel(treeModel);
    }

    /**
     * Creates new form Ejercicio2
     */
    public FileManager() {
        //FlatDarkLaf.setup();
        initComponents();
        this.setLocationRelativeTo(null);

        jTree1.setModel(new DefaultTreeModel(null));
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        DefaultTreeCellRenderer renderer = new FileCellRenderer();
        jTree1.setCellRenderer(renderer);
        jTree1.addMouseListener(ml);
        jTree1.addTreeWillExpandListener(this);

        // Create Tabs panel
        tabs = new TabsPanel();
        jSplitPane1.setRightComponent(tabs);
        tabs.setFileChangeListener((file) -> {
            menuSave.setEnabled(isNull(file) ? false : file.isModified());
        });

        //Menu
        menuOpen.addActionListener((e) -> {
            chooseFile();
        });
        menuSave = new JMenuItem("Save");
        menuSave.addActionListener((e) -> {
            if (tabs.saveCurrentTab()) {
                menuSave.setEnabled(false);
            } else {
                DialogManager.showSaveError(this);
            }
        });
        menuSave.setEnabled(false);
        KeyStroke ctrlSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        menuSave.setAccelerator(ctrlSave);
        menuSave.setMaximumSize(menuSave.getPreferredSize());
        jMenuBar1.add(menuSave);
        contextMenu = new ContextMenu(menuAction);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setResizeWeight(0.3);

        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jMenu1.setText("File");

        menuOpen.setText("Open ...");
        jMenu1.add(menuOpen);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FileManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JMenuItem menuOpen;
    // End of variables declaration//GEN-END:variables

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
        //
    }
}
