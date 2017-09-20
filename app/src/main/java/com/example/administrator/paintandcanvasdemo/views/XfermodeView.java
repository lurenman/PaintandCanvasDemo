package com.example.administrator.paintandcanvasdemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.paintandcanvasdemo.R;

/**
 * Created by lurenman on 17/9/19.
 */
public class XfermodeView extends View {
    private static final String TAG = "XfermodeView";
    private int width, height;
    private int bitmapSize;
    private int offset;
    private int paddingTop;
    private Paint xfermodePaint;
    private Paint paint;
    //PorterDuff模式常量 可以在此更改不同的模式测试
    private static final PorterDuff.Mode MODE = PorterDuff.Mode.CLEAR;
    private Bitmap srcBitmap, dstBitmap;
    private Bitmap xfermodeBitmap;
    private PorterDuffXfermode porterDuffXfermode;

    public XfermodeView(Context context) {
        super(context);
        init();
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        xfermodePaint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        setXfermode(MODE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "--------" + "onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "--------" + "onSizeChanged");
        width = w;
        Log.e(TAG, "width--------" + Integer.toString(width));
        height = h;
        Log.e(TAG, "height--------" + Integer.toString(height));
        bitmapSize = w / 2;
        offset = bitmapSize / 3;
        Log.e(TAG, "offset--------" + Integer.toString(offset));
        //创建原图和目标图
        srcBitmap = makeSrc(bitmapSize, bitmapSize);
        dstBitmap = makeDst(bitmapSize, bitmapSize);
        xfermodeBitmap = createXfermodeBitmap();
    }

    // 创建一个矩形bitmap，作为src图
    private Bitmap makeSrc(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.color_titlestate1));
        c.drawRect(width / 3, height / 3, width, height, p);
        return bm;
    }

    //创建一个圆形bitmap，作为dst图
    private Bitmap makeDst(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.bule_overlay));
        c.drawOval(new RectF(0, 0, width / 3 * 2, height / 3 * 2), p);
        return bm;
    }

    private Bitmap createBitamp(int w, int h) {
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "--------" + "onDraw");
        canvas.save();
        canvas.translate(-offset / 2, 0);

        canvas.drawBitmap(srcBitmap, 0, -offset, null);
        canvas.drawText("src", offset, offset, paint);

        canvas.drawBitmap(dstBitmap, 4 * offset, 0, null);
        canvas.drawText("dst", 4 * offset, offset, paint);

        canvas.restore();

        canvas.drawBitmap(xfermodeBitmap, 0, bitmapSize, null);
    }

    public void setXfermode(PorterDuff.Mode mode) {
        if (mode == null) {
            return;
        }
        porterDuffXfermode = new PorterDuffXfermode(mode);
        xfermodePaint.setXfermode(porterDuffXfermode);
        xfermodeBitmap = createXfermodeBitmap();
        invalidate();
    }

    private Bitmap createXfermodeBitmap() {
        if (bitmapSize <= 0) {
            return null;
        }
        Bitmap bitmap = createBitamp(bitmapSize, bitmapSize);
        Canvas canvas = new Canvas(bitmap);

        // 两个图片大小一致，且重叠？
        canvas.drawBitmap(dstBitmap, 0, 0, null);
        canvas.drawBitmap(srcBitmap, 0, 0, xfermodePaint);
        return bitmap;
    }

}
