package com.example.administrator.paintandcanvasdemo;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.paintandcanvasdemo.views.XfermodeView;

/**
 * Created by Administrator on 2017/9/19.
 */

public class PorterDuffXfermodeActivity extends AppCompatActivity {
    private static final String TAG = "PorterDuffXfermodeActiv";
    private XfermodeView xfermodeView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porterduffxfermode);
        initViews();

    }
    private void initViews() {
        xfermodeView = (XfermodeView) findViewById(R.id.xfermodeView);
    }
    private void loadData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_porterduff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        setTitle(title);
        Log.e(TAG,"----------"+title);
        PorterDuff.Mode mode = PorterDuff.Mode.valueOf(title);
        if (null!=mode)
        {
            xfermodeView.setXfermode(mode);
        }

        return super.onOptionsItemSelected(item);
    }


}
