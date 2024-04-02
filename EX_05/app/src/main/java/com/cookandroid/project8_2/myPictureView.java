package com.cookandroid.project8_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class myPictureView extends View {
    private Bitmap bitmap;
    private String imagePath;

    public myPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImagePath(String path) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (path != null && !path.isEmpty()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // Example of reducing image size
            bitmap = BitmapFactory.decodeFile(path, options);
        }
        invalidate();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (bitmap != null && !bitmap.isRecycled()) {
//            canvas.drawBitmap(bitmap, 0, 0, null);
//        }
//    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null && !bitmap.isRecycled()) {
            // 캔버스의 크기에 맞게 스케일링하기 위한 대상 사각형(Rect) 생성
            Rect destRect = new Rect(0, 0, getWidth(), getHeight());
            // 비트맵을 캔버스에 맞게 그리기
            canvas.drawBitmap(bitmap, null, destRect, null);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
