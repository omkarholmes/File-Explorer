package com.omkarmhatre.fileexplorer.FileExplorer;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Explorer {

    File root = new File(Environment.getExternalStorageDirectory() + "");

    private File path = new File(Environment.getExternalStorageDirectory() + "");
    private List<DirectoryItem> dirs = new ArrayList<>();

    private static Set<PathChangedListeners> listeners = new HashSet<>();

    private void notifyPathChanged(){
        for (PathChangedListeners listener :listeners)
        {
            listener.onPathChanged();
        }
    }

    void addToListeners(PathChangedListeners listener)
    {
        listeners.add(listener);
    }

    File getPath() {
        return path;
    }

    void setPath(File path) {
        this.path = path;
        notifyPathChanged();
    }

    private static final String TAG = "F_PATH";
    private FilenameFilter filter;

    // Singleton Class
    private static Explorer instance;
    private Explorer(){

    }
    static Explorer getInstance(){
        if(instance == null){
            instance =new Explorer();
        }
        return instance;
    }

    List<DirectoryItem> getDirectories(File path){
        this.path=path;
        dirs.clear();
        DirectoryItem dir = getDirectory(path);
        for (String fd:dir.getFileNames())
        {
            File f = new File(dir.getPath(),fd);
            dirs.add(getDirectory(f));
        }
        return dirs;

    }

    private DirectoryItem getDirectory(File path)
    {
        DirectoryItem di= null;
        if(checkIfPathExists(path)) {
            di = new DirectoryItem(path);
            di.setDir(path.isDirectory());
            if (di.isDir()){
                di.setFileNames(Arrays.asList(path.list(getFileNameFilter())));
            }
            di.setItemName(getFileName(path));
        }
        return di ;
    }

    private FilenameFilter getFileNameFilter()
    {
        if(filter == null)
        {
            //A File Filter to filter the hidden Files
            return filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    // Filters based on whether the file is hidden or not
                    return (sel.isFile() || sel.isDirectory()) && !sel.isHidden();
                }
            };
        }
        else return filter;
    }

    private boolean checkIfPathExists(File path)
    {
        try{
            path.mkdirs();
        }
        catch(Exception e){
            Log.e(TAG, "unable to write on the sd card ");
        }
        return path.exists();
    }

    private  String getFileName(File file)
    {
        String[] filePath = file.toString().split("/");
        int index = filePath.length;

        return filePath[index-1];
    }


    File changePath(File path, String folderName, int openClose)
    {
        File changedPath = null;
        if(openClose == 1)
        {
            // Open the given folder
            changedPath = new File(path,folderName);
        }
        else if(openClose == 0){
            String[] folders = path.toString().split("/");
            StringBuilder newPath = new StringBuilder();
            for(int i =0 ; i < folders.length-1; i++){
                newPath.append("/").append(folders[i]);
            }
            changedPath = new File(newPath.toString());

        }
        return  changedPath;
    }

}
