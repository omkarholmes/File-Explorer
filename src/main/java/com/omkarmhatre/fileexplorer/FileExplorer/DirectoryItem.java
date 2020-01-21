package com.omkarmhatre.fileexplorer.FileExplorer;

import java.io.File;
import java.util.List;

public class DirectoryItem {


    private File path;
    private boolean isDir;
    private String itemName;
    private List <String> fileNames;

    DirectoryItem(File path) {
        this.path = path;
    }

    File getPath() {
        return path;
    }

    boolean isDir() {
        return isDir;
    }

    void setDir(boolean dir) {
        isDir = dir;
    }

    String getItemName() {
        return itemName;
    }

    void setItemName(String itemName) {
        this.itemName = itemName;
    }

    List<String> getFileNames() {
        return fileNames;
    }

    void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
