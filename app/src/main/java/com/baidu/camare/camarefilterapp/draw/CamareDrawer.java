package com.baidu.camare.camarefilterapp.draw;

import android.opengl.GLES20;

/**
 * camare  渲染器
 * Created by puchunjie .
 */

public class CamareDrawer {

    private int mProgram;


    // 根据不同的type获取到不同的shader  顶点着色器vertrix  片元着色器fragment
    private int loadShader(int type, String ShaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(type, ShaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
