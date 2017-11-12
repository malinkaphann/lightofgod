package malinka.lightofgod.util;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import malinka.lightofgod.exception.CameraNotOpenException;

/**
 * Created by malinka on 11/12/17.
 */

public class FlashLightManager {

    /**
     *
     * @param pm
     * @return
     * @throws Exception
     */
    public static boolean isFlashSupported(PackageManager pm) throws Exception {
        if(pm == null) throw new IllegalArgumentException("input package manager is invalid");
        boolean hasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        return hasFlash;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public static Camera getCamera() throws Exception {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            throw new CameraNotOpenException(e);
        }
        return camera;
    }

    /**
     *
     * @param camera
     * @throws Exception
     */
    public static void turnOnFlash(Camera camera) throws Exception {
        if(camera == null) throw new IllegalArgumentException("input camera is invalid");
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    /**
     *
     * @param camera
     * @throws Exception
     */
    public static void turnOffFlash(Camera camera) throws Exception {
        if(camera == null) throw new IllegalArgumentException("input camera is invalid");
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }
}
