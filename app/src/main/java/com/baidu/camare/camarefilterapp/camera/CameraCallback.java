/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.camare.camarefilterapp.camera;

public interface CameraCallback {
    void onCameraSetup(boolean result);

    void onCameraRelease(boolean result);

    void onCameraReopen(boolean result);

    void onSurfaceTextureSet(boolean result);

    void onSurfaceHolderSet(boolean result);

    void onPreviewCallbackSet(boolean result);

    void onPreviewStart(boolean result);

    void onPreviewStop(boolean result);

    void onFlashOpen(boolean result);

    void onFlashClose(boolean result);
}
