package com.baidu.camare.camarefilterapp;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

import com.baidu.camare.camarefilterapp.camera.EasyCamera;
import com.baidu.camare.camarefilterapp.draw.CamareRander;

/**
 * camare 界面
 */
public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mSurfaceView;
    private CamareRander mRander;

    private TextureView mSourceView;//
    private TextureView mTargitView;

    private EasyCamera mCamare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.gl_view);
        mRander = new CamareRander();
        mCamare = EasyCamera.getInstance();
    }

    private void initData() {
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(mRander);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
