package malinka.lightofgod.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import malinka.lightofgod.activity.MainActivity;
import malinka.lightofgod.receiver.ScreenActionReceiver;
import malinka.lightofgod.util.FlashLightManager;

public class PowerOnListener extends Service {

    private final IBinder binder = new LocalBinder();

    BroadcastReceiver screenActionReceiver = new ScreenActionReceiver();

    private boolean isFlashLightOn = false;

    public class LocalBinder extends Binder {

        PowerOnListener getService() {

            // Return this instance of PowerOnListener so clients can call public methods
            return PowerOnListener.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null) return super.onStartCommand(intent, flags, startId);

        Log.d(this.getClass().getName(), String.format("received command to start as %s", startId));

        this.isFlashLightOn = intent.getBooleanExtra("flash_light", false);

        Log.d(this.getClass().getName(), String.format("flash light should be turned %s", this.isFlashLightOn ? "on" : "off"));

        try {

            Camera camera = FlashLightManager.getCamera();

            if (this.isFlashLightOn) {

                Log.d(this.getClass().getName(), "to turn on the flash");

                FlashLightManager.turnOnFlash(camera);

            } else {

                Log.d(this.getClass().getName(), "to turn off the flash");

                FlashLightManager.turnOffFlash(camera);
            }

        } catch(Exception e) {

            Log.e(this.getClass().getName(), e.getMessage(), e);

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
       unregisterReceiver(screenActionReceiver);
    }

    @Override
    public void onCreate() {

        Log.d(this.getClass().getName(), "received request to create itself");

        // register screenActionReceiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);

        filter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(screenActionReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
