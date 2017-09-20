package com.example.administrator.paintandcanvasdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.paintandcanvasdemo.views.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/15.
 * http://blog.csdn.net/iispring/article/details/49770651
 */

public class CanvasActivity extends BaseActivity {
    @BindView(R.id.tv_DrawAxis)
    TextView tv_DrawAxis;
    @BindView(R.id.tv_DrawARGB)
    TextView tv_DrawARGB;
    @BindView(R.id.tv_DrawText)
    TextView tv_DrawText;
    @BindView(R.id.tv_DrawPoint)
    TextView tv_DrawPoint;
    @BindView(R.id.tv_DrawLine)
    TextView tv_DrawLine;
    @BindView(R.id.tv_DrawRect)
    TextView tv_DrawRect;
    @BindView(R.id.tv_DrawCircle)
    TextView tv_DrawCircle;
    @BindView(R.id.tv_DrawOval)
    TextView tv_DrawOval;
    @BindView(R.id.tv_DrawArc)
    TextView tv_DrawArc;
    @BindView(R.id.tv_DrawPath)
    TextView tv_DrawPath;
    @BindView(R.id.tv_DrawBitmap)
    TextView tv_DrawBitmap;
    @BindView(R.id.tv_layer)
    TextView tv_Layer;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_canvas);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.tv_DrawAxis, R.id.tv_DrawARGB, R.id.tv_DrawText, R.id.tv_DrawPoint,
            R.id.tv_DrawLine, R.id.tv_DrawRect, R.id.tv_DrawCircle, R.id.tv_DrawOval,
            R.id.tv_DrawArc, R.id.tv_DrawPath, R.id.tv_DrawBitmap, R.id.tv_layer})
    public void onViewClicked(View view) {
        CanvasView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.tv_DrawAxis:
                drawMode = CanvasView.DrawMode.AXIS;
                break;
            case R.id.tv_DrawARGB:
                drawMode = CanvasView.DrawMode.ARGB;
                break;
            case R.id.tv_DrawText:
                drawMode = CanvasView.DrawMode.TEXT;
                break;
            case R.id.tv_DrawPoint:
                drawMode = CanvasView.DrawMode.POINT;
                break;
            case R.id.tv_DrawLine:
                drawMode = CanvasView.DrawMode.LINE;
                break;
            case R.id.tv_DrawRect:
                drawMode = CanvasView.DrawMode.RECT;
                break;
            case R.id.tv_DrawCircle:
                drawMode = CanvasView.DrawMode.CIRCLE;
                break;
            case R.id.tv_DrawOval:
                drawMode = CanvasView.DrawMode.OVAL;
                break;
            case R.id.tv_DrawArc:
                drawMode = CanvasView.DrawMode.ARC;
                break;
            case R.id.tv_DrawPath:
                drawMode = CanvasView.DrawMode.PATH;
                break;
            case R.id.tv_DrawBitmap:
                drawMode = CanvasView.DrawMode.BITMAP;
                break;
            case R.id.tv_layer:
                drawMode = CanvasView.DrawMode.LAYER;
                break;
        }
        Intent intent = new Intent(this, CanvasViewActivity.class);
        intent.putExtra("drawMode", drawMode.value());
        startActivity(intent);
    }
}
