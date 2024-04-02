package com.cookandroid.project8_2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Button btnPrev, btnNext;
    myPictureView myPicture;
    int curNum = 0;
    List<File> imageFiles = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        myPicture = findViewById(R.id.myPictureView1);

        checkPermissions();

        btnPrev.setOnClickListener(v -> navigateImages(false));
        btnNext.setOnClickListener(v -> navigateImages(true));
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            loadImageFiles();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImageFiles();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageFiles() {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        File[] files = storageDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().matches(".*\\.(jpg|jpeg|png)$")) {
                    imageFiles.add(file);
                }
            }
            updateImage();
        }
    }

    private void navigateImages(boolean next) {
        if (next && curNum < imageFiles.size() - 1) {
            curNum++;
        } else if (!next && curNum > 0) {
            curNum--;
        } else {
            Toast.makeText(this, next ? "마지막 사진입니다." : "첫번째 사진입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        updateImage();
    }

    private void updateImage() {
        if (!imageFiles.isEmpty() && curNum < imageFiles.size()) {
            String imagePath = imageFiles.get(curNum).getAbsolutePath();
            myPicture.setImagePath(imagePath);
        }
    }
}
