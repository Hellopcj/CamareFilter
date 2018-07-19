package com.baidu.camare.camarefilterapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

import com.baidu.camare.camarefilterapp.camera.EasyCamera;
import com.baidu.camare.camarefilterapp.draw.CamareRander;

import java.util.ArrayList;
import java.util.List;

/**
 * camare 界面
 */
public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mSurfaceView;
    private CamareRander mRander;

    private TextureView mSourceView;//
    private TextureView mTargitView;

    private EasyCamera mCamare;

    private boolean mIsDenyAllPermission = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 154;
    private static final String[] ALL_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        mRander = new CamareRander();
        mCamare = EasyCamera.getInstance();
    }

    private void initData() {
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(mRander);
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

    /**    permisson     **/
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
        }
    }

}
