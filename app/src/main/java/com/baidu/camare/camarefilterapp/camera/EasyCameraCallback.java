/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.camare.camarefilterapp.camera;

public interface EasyCameraCallback {
    void onCameraStart(boolean result);

    void onCameraSwitch(boolean result);

    void onFlashOpen(boolean result);

    void onFlashClose(boolean result);

    void onCameraStop(boolean result);
}
