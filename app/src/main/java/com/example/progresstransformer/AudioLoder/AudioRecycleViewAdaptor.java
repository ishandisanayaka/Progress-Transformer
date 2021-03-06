package com.example.progresstransformer.AudioLoder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progresstransformer.Database.DBHelper;
import com.example.progresstransformer.R;
import com.example.progresstransformer.SendVideo.SendVideo;
import com.example.progresstransformer.VideoLoder.Constant;


public class AudioRecycleViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.OnNoteListner mOnNoteListner;

    private DBHelper dbHelper;

    public AudioRecycleViewAdaptor(Context mContext, com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.OnNoteListner onNoteListner2){

        this.mOnNoteListner=onNoteListner2;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list,parent,false);


        return new com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.FileLayoutHolder(view,mOnNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.FileLayoutHolder)holder).videoTitle.setText(Constant.allaudioList.get(position).getName());
        //we will load thumbnail using glid library
        Uri uri = Uri.fromFile(Constant.allaudioList.get(position));




        Glide.with(mContext)
                .load(uri).thumbnail(0.1f).into(((com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.FileLayoutHolder)holder).thumbnail);
        ((com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.FileLayoutHolder) holder).ic_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, SendVideo.class);
                Uri uri = Uri.fromFile(Constant.allaudioList.get(position));
                intent1.putExtra("uri",position);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Constant.allaudioList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView videoTitle;
        ImageView ic_more_btn;
        com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.OnNoteListner onNoteListner;
        LinearLayout linearLayout;

        public FileLayoutHolder(@NonNull View itemView, com.example.progresstransformer.AudioLoder.AudioRecycleViewAdaptor.OnNoteListner onNoteListner1) {
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
