package com.baidu.camare.camarefilterapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.camare.camarefilterapp.camera.CameraParams;
import com.baidu.camare.camarefilterapp.camera.EasyCamera;
import com.baidu.camare.camarefilterapp.camera.EasyCameraCallback;
import com.baidu.camare.camarefilterapp.draw.CamareRender;
import com.baidu.camare.camarefilterapp.draw.TextureID;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * camare 界面
 */
public class MainActivity extends AppCompatActivity implements Camera.PreviewCallback, EasyCameraCallback {
    private GLSurfaceView mSurfaceView;
    private CamareRender mRender;

    private SurfaceTexture mSourceView;//
    private SurfaceTexture mTargitView;
    private int mSurfaceTextureID = -1;

    private EasyCamera mCamare;
    private CameraParams mCamareParams;

    private boolean mIsDenyAllPermission = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 154;
    private static final String[] ALL_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.gl_view);
        mRender = new CamareRender();
        mCamareParams = new CameraParams();
        mCamareParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mCamare = EasyCamera.getInstance();
        mSurfaceTextureID = TextureID.createTexture(true);
    }

    private void initData() {
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(mRender);
        // mSurfaceView.setRenderer(mRender);
        mSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
                mSourceView = new SurfaceTexture(mSurfaceTextureID);
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int i, int i1) {

            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                if (mSourceView != null) {
                    //更新纹理图像
                    mSourceView.updateTexImage();
                    //获取外部纹理的矩阵，用来确定纹理的采样位置，没有此矩阵可能导致图像翻转等问题
                 //   mSourceView.getTransformMatrix(i);
                }
            }
        });
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 请求权限
        requestAllPermissions(REQUEST_CODE_ASK_ALL_PERMISSIONS);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * permisson
     **/
    private void requestAllPermissions(int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                List<String> permissionsList = getRequestPermissions(this);
                if (permissionsList.size() == 0) {
                    return;
                }
                if (!mIsDenyAllPermission) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            requestCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getRequestPermissions(Activity activity) {
        List<String> permissionsList = new ArrayList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : ALL_PERMISSIONS) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsList.add(permission);
                }
            }
        }
        return permissionsList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_ALL_PERMISSIONS) {
            mIsDenyAllPermission = false;
            for (int i = 0; i < permissions.length; i++) {
                if (i >= grantResults.length || grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    mIsDenyAllPermission = true;
                    break;
                }
            }
            //获取到权限后 开启相机
            startCamre();
        }
    }

    /**
     * 开启相机
     */
    private void startCamre() {
        mCamare.startCamera(mCamareParams, mSourceView, this, this);
    }

    /**
     * 释放相机
     */
    private void closeCamare() {
        mCamare.stopCamera();
    }

    /**
     * 切换相机
     */
    private void switchCamare() {
        mCamare.switchCamera();
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (mSurfaceView != null) {
            mSurfaceView.requestRender();
        }
    }

    @Override
    public void onCameraStart(boolean result) {

    }

    @Override
    public void onCameraSwitch(boolean result) {

    }

    @Override
    public void onFlashOpen(boolean result) {

    }

    @Override
    public void onFlashClose(boolean result) {

    }

    @Override
    public void onCameraStop(boolean result) {

    }
}
