package com.omkarmhatre.fileexplorer.FileExplorer;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.omkarmhatre.fileexplorer.R;


public class DirectoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private RelativeLayout view;
    private DirectoryItem directoryItem;

    DirectoryItemViewHolder(@NonNull View itemView) {

        super(itemView);
        this.view=(RelativeLayout) itemView;
        view.setOnClickListener(this);

    }

    void initiate(Activity activity, DirectoryItem directoryItem) {

       ImageView iconView = view.findViewById(R.id.dirItemIcon);
       TextView fileName = view.findViewById(R.id.dirItemText);

       int icon = directoryItem.isDir() ? R.drawable.directory_icon : R.drawable.file_icon ;
        iconView.setImageDrawable(activity.getDrawable(icon));

       fileName.setText(directoryItem.getItemName());

       this.directoryItem=directoryItem;

    }

    @Override
    public void onClick(View v) {

        Explorer e = Explorer.getInstance();
        e.setPath(e.changePath(e.getPath(),directoryItem.getItemName(),1));


    }
}
