package com.example.administrator.paintandcanvasdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public abstract class BaseActivity extends Activity
{
    protected Context mContext;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mContext=this;

        initVariables();
        initViews();
        initEnvent();
        loadData();
    }

    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract void loadData();
    protected void initEnvent()
    {

    }

}
