package com.example.progresstransformer.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progresstransformer.MainActivity;
import com.example.progresstransformer.PdfLoder.PdfRecyclerViewAdapter;
import com.example.progresstransformer.PlayVideo.PlayerVideo;
import com.example.progresstransformer.R;
import com.example.progresstransformer.VideoLoder.Constant;
import com.example.progresstransformer.VideoLoder.RecyclerViewAdapter;
import com.example.progresstransformer.ViewPdf.ViewPdf;

public class PdfFragment extends Fragment implements PdfRecyclerViewAdapter.OnNoteListner {
    View mView;

    private RecyclerView recyclerView;
    private PdfRecyclerViewAdapter pdfrecyclerViewAdapter;
    private Uri fileUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView= inflater.inflate(R.layout.fragment_pdf_bottomnevigation,container,false);

        recyclerView = mView.findViewById(R.id.recyclerViewPdf);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.getContextOfApplication()));

        //if you face lack in scrolling then add following lines
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);

        pdfrecyclerViewAdapter = new PdfRecyclerViewAdapter(MainActivity.getContextOfApplication(),this);

        recyclerView.setAdapter(pdfrecyclerViewAdapter);

        return mView;
    }
    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), ViewPdf.class);
        Uri uri = Uri.fromFile(Constant.allpdfList.get(position));
        intent.putExtra("uri",position);
        startActivity(intent);
    }
}
