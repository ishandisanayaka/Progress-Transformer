package com.example.progresstransformer.ViewPdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.progresstransformer.Database.DBHelper;
import com.example.progresstransformer.R;
import com.example.progresstransformer.VideoLoder.Constant;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPdf extends AppCompatActivity {
    private DBHelper dbHelper;
    private Uri urlOfPdf;
    private PDFView pdfView;
    private int lastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        dbHelper=new DBHelper(this);


        Intent intent=getIntent();
        int positionOfArray=intent.getIntExtra("uri",0);
        urlOfPdf = Uri.fromFile(Constant.allpdfList.get(positionOfArray));
        pdfView=(PDFView)findViewById(R.id.pdfView);
        if (!Constant.allpdfSendToDB.contains(String.valueOf(urlOfPdf))) {
            lastPage = pdfView.getCurrentPage();
            dbHelper.insertPdfData(Constant.allMediaList.get(positionOfArray).getName(), String.valueOf(urlOfPdf), String.valueOf(lastPage));
            Constant.allpdfSendToDB.add(String.valueOf(urlOfPdf));
        }else{
            ArrayList<HashMap<String, String>> progressAttay=dbHelper.getPdfData(String.valueOf(urlOfPdf));
            lastPage=Integer.parseInt(progressAttay.get(0).get("progress"));
        }
        pdfView.fromUri(urlOfPdf)
                .defaultPage(lastPage)
                .enableSwipe(true)
        .swipeHorizontal(false)
        .onDraw(new OnDrawListener() {
            @Override
            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

            }
        }).onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {

                dbHelper.updatePdfPageNumber(String.valueOf(pdfView.getCurrentPage()), String.valueOf(urlOfPdf));

            }
        }).enableAnnotationRendering(true)
        .load();
    }
}
