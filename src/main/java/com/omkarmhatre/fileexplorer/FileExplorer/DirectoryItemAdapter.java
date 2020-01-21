package com.omkarmhatre.fileexplorer.FileExplorer;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omkarmhatre.fileexplorer.R;

import java.util.List;

public class DirectoryItemAdapter extends RecyclerView.Adapter<DirectoryItemViewHolder> {

    private List<DirectoryItem> fileNames;
    private Activity activity;


    public DirectoryItemAdapter(Activity activity, List<DirectoryItem> fileNames) {
        this.fileNames=fileNames;
        this.activity=activity;
    }

    @NonNull
    @Override
    public DirectoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =  LayoutInflater.from(viewGroup.getContext());
        View view =inflater.inflate(R.layout.layout_directory_item,null);
        return new DirectoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryItemViewHolder viewHolder, int position) {

        viewHolder.initiate(activity,fileNames.get(position));

    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }
}
