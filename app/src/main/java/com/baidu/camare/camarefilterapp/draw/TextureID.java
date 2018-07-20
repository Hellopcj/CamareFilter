package com.baidu.camare.camarefilterapp.draw;

import android.graphics.Bitmap;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

/**
 * Created by puchunjie .
 */

public class TextureID {
    /**
     * @param textureTarget Texture类型。
     *                      1. 相机用 GLES11Ext.GL_TEXTURE_EXTERNAL_OES
     *                      2. 图片用 GLES20.GL_TEXTURE_2D
     * @param count         创建纹理数量
     * @param minFilter     缩小过滤类型 (1.GL_NEAREST ; 2.GL_LINEAR)
     * @param magFilter     放大过滤类型
     * @param wrapS         X方向边缘环绕
     * @param wrapT         Y方向边缘环绕
     * @return 返回创建的 Texture ID
     */
    public static int[] createTextures(int textureTarget, int count, int minFilter, int magFilter, int wrapS,
                                       int wrapT) {
        int[] textureHandles = new int[count];
        for (int i = 0; i < count; i++) {
            GLES20.glGenTextures(1, textureHandles, i);
//            checkGLError("createTextures", "glGenTextures");
            GLES20.glBindTexture(textureTarget, textureHandles[i]);
//            checkGLError("createTextures", "glBindTexture " + textureHandles[i]);
            GLES20.glTexParameterf(textureTarget, GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
            GLES20.glTexParameterf(textureTarget, GLES20.GL_TEXTURE_MAG_FILTER, magFilter); // 线性插值
            GLES20.glTexParameteri(textureTarget, GLES20.GL_TEXTURE_WRAP_S, wrapS);
            GLES20.glTexParameteri(textureTarget, GLES20.GL_TEXTURE_WRAP_T, wrapT);
        }
        return textureHandles;
    }

    public static int createTexture(boolean cameraTexture) {
        int textureTarget = GLES20.GL_TEXTURE_2D;
        if (cameraTexture) {
            textureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
        }
        return createTexture(textureTarget, null, GLES20.GL_LINEAR, GLES20.GL_LINEAR,
                GLES20.GL_CLAMP_TO_EDGE, GLES20.GL_CLAMP_TO_EDGE);
    }

    public static int createTexture(int textureTarget, Bitmap bitmap, int minFilter,
                                    int magFilter, int wrapS, int wrapT) {
        int[] textureHandle = createTextures(textureTarget, 1, minFilter, magFilter, wrapS, wrapT);
        if (bitmap != null) {
            android.opengl.GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        }
//        checkGLError("createTextures", "glTexParameter");
        return textureHandle[0];
    }

}
