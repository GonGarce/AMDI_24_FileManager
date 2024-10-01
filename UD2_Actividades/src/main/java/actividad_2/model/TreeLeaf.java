/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad_2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.swing.ImageIcon;

/**
 *
 * @author Gonzalo
 */
public class TreeLeaf extends TreeFile {

    private static final String[] ALLOWED_TYPES = {"text/plain"};

    private String mimeType;
    private boolean modified;

    public TreeLeaf(File file) {
        super(file, false);
        try {
            this.mimeType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            this.mimeType = "unk";
        }
        this.modified = false;
    }

    public String getContent() {
        if (Objects.nonNull(file)) {
            try {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());
                String content = scanner.useDelimiter("\\A").next();
                scanner.close();
                return content;
            } catch (FileNotFoundException e) {
                // TODO: Dialog error
                e.printStackTrace();
            }
        }

        return new StringBuilder("File Content could not be read (")
                .append(new Date()).append(")").toString();
    }

    public ImageIcon getIcon() {
        if (Objects.isNull(this.mimeType)) {
            return TreeIcon.FILE_UNK.getIcon();
        }
        return switch (this.mimeType) {
            case "text/plain" ->
                TreeIcon.FILE_TEXT.getIcon();
            default ->
                TreeIcon.FILE_UNK.getIcon();
        };
    }

    public boolean isAllowed() {
        return Stream.of(ALLOWED_TYPES).anyMatch((t) -> t.equals(this.mimeType));
    }

    public boolean save(String content) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        } catch (IOException ex) {
            return false;
        }
        this.modified = false;
        return true;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public String getMimeType() {
        return this.mimeType;
    }
}
