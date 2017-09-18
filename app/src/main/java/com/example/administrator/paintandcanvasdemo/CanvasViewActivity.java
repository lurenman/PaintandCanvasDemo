package com.example.administrator.paintandcanvasdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.administrator.paintandcanvasdemo.views.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CanvasViewActivity extends BaseActivity {
    @BindView(R.id.cv_canvasView)
    CanvasView cv_CanvasView;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_canvasview);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p);
        cv_CanvasView.setBitmap(bitmap);

        Intent intent = getIntent();
        if(intent != null){
            CanvasView.DrawMode drawMode = CanvasView.DrawMode.valueOf(intent.getIntExtra("drawMode", 0));
            //设置绘制模式
            cv_CanvasView.setDrawMode(drawMode);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cv_CanvasView 不是静态的做不做这块感觉貌似无所谓的样子
        if(cv_CanvasView != null){
            cv_CanvasView.destroy();
            cv_CanvasView = null;
        }
    }
}
