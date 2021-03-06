package com.example.progresstransformer.VideoLoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progresstransformer.Database.DBHelper;
import com.example.progresstransformer.Fragment.VideoFragment;
import com.example.progresstransformer.PlayVideo.PlayerVideo;
import com.example.progresstransformer.R;
import com.example.progresstransformer.SendVideo.SendVideo;
import com.example.progresstransformer.SendVideo.SendVideoToAnotherDevice;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnNoteListner mOnNoteListner;

    private DBHelper dbHelper;

    public RecyclerViewAdapter(Context mContext,OnNoteListner onNoteListner2){

        this.mOnNoteListner=onNoteListner2;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list,parent,false);


        return new FileLayoutHolder(view,mOnNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((FileLayoutHolder)holder).videoTitle.setText(Constant.allMediaList.get(position).getName());
        //we will load thumbnail using glid library
        Uri uri = Uri.fromFile(Constant.allMediaList.get(position));




        Glide.with(mContext)
                .load(uri).thumbnail(0.1f).into(((FileLayoutHolder)holder).thumbnail);
        ((FileLayoutHolder) holder).ic_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, SendVideo.class);
                Uri uri = Uri.fromFile(Constant.allMediaList.get(position));
                intent1.putExtra("uri",position);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Constant.allMediaList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView videoTitle;
        ImageView ic_more_btn;
        OnNoteListner onNoteListner;
        LinearLayout linearLayout;

        public FileLayoutHolder(@NonNull View itemView,OnNoteListner onNoteListner1) {
            super(itemView);

            this.onNoteListner=onNoteListner1;
            thumbnail = itemView.findViewById(R.id.thumbnail);
            videoTitle = itemView.findViewById(R.id.videotitle);
            ic_more_btn = itemView.findViewById(R.id.ic_more_btn);
            linearLayout=itemView.findViewById(R.id.VideoLoad);

            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListner.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListner{
        void onNoteClick(int position);
    }

}
