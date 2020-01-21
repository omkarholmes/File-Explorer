package com.omkarmhatre.fileexplorer.FileExplorer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omkarmhatre.fileexplorer.AsynLoader.AsyncLoader;
import com.omkarmhatre.fileexplorer.AsynLoader.SubscriptionBlock;
import com.omkarmhatre.fileexplorer.R;

import java.util.List;


@SuppressLint("ValidFragment")
public class FileExplorerActivity extends FragmentActivity implements PathChangedListeners{



    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 110;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 120;

    RecyclerView recyclerView;
    DirectoryItemAdapter adapter;
    List<DirectoryItem> dir;

    private void makePermissionRequest() {
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                requestPermissions( new String[]{permission}, requestCode);

            } else {

                requestPermissions(new String[]{permission}, requestCode);
            }
        }
        else {
            setupRecyclerView();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION &&
                ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            setupRecyclerView();

        }


    }

    private void setupRecyclerView() {
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Explorer e =Explorer.getInstance();
        e.addToListeners(this);
        dir = e.getDirectories(e.getPath());
        adapter = new DirectoryItemAdapter(this,dir);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_file_explorer);
        AsyncLoader.load(new SubscriptionBlock() {
            @Override
            public void subscribe() throws Exception {
                makePermissionRequest();
            }
        });
    }

    @Override
    public void onPathChanged() {
        Explorer e =Explorer.getInstance();
        dir = e.getDirectories(e.getPath());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Explorer e =Explorer.getInstance();
        if(e.root.equals(e.getPath())){
            super.onBackPressed();
        }else {
            e.setPath(e.changePath(e.getPath(),"",0));
            dir = e.getDirectories(e.getPath());
            adapter.notifyDataSetChanged();
        }
    }
}
