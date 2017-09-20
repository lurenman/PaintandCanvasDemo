package com.example.administrator.paintandcanvasdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
 /*
 MyView 是正解
 *
 */

public class MainActivity extends AppCompatActivity {

    private TextView tv_paint;
    private TextView tv_Xfermode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }
    private void initViews() {
        tv_paint = (TextView) findViewById(R.id.tv_paint);
        tv_Xfermode = (TextView) findViewById(R.id.tv_Xfermode);
    }
    private void initEvents() {
        tv_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CanvasActivity.class);
                startActivity(intent);
            }
        });
        tv_Xfermode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PorterDuffXfermodeActivity.class);
                startActivity(intent);
            }
        });

    }

}
