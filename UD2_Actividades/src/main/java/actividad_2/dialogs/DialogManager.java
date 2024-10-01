/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.dialogs;

import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Gonzalo
 */
public class DialogManager {

    private static enum Errors {
        SAVE("No se pudo guardar el  archivo"),
        RENAME("No se pudo renombrar el archivo"),
        FILE_TYPE("El tipo de archivo que intenta abrir no está permitido");
        private final String description;

        private Errors(String description) {
            this.description = description;
        }
    }

    public static File showFileChooser(JFrame parent) {
        JFileChooser jf = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (!getSelectedFile().isFile()) {
                    super.approveSelection();
                }
            }
        };
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int selection = jf.showOpenDialog(parent);
        if (selection == JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            // if the user accidently click a file, then select the parent directory.
            if (!f.isDirectory()) {
                f = f.getParentFile();
            }
            return f;
        }
        return null;
    }

    public static DialogCloseConfirm.CLOSE_OPTION showCloseWithoutSave(JFrame parent) {
        DialogCloseConfirm dialog = new DialogCloseConfirm(parent);
        return dialog.openConfirmNoSave();
    }
    
    public static String showRenameDialog(JFrame parent, String currentName) {
        DialogEnterName dialog = new DialogEnterName(parent, currentName);
        return dialog.openForRename();
    }
    
    public static String showNewFileDialog(JFrame parent) {
        DialogEnterName dialog = new DialogEnterName(parent);
        return dialog.openForCreate();
    }

    public static void showSaveError(JFrame parent) {
        showError(parent, Errors.SAVE.description);
    }

    public static void showRenameError(JFrame parent) {
        showError(parent, Errors.RENAME.description);
    }

    public static void showFileTypeError(JFrame parent) {
        showError(parent, Errors.FILE_TYPE.description);
    }

    public static void showError(JFrame parent, String description) {
        JDialog dialog = new DialogError(parent, description);
        dialog.setVisible(true);
    }
}
