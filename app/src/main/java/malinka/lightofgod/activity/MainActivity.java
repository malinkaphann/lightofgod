package malinka.lightofgod.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import malinka.lightofgod.service.PowerOnListener;
import malinka.lightofgod.R;
import malinka.lightofgod.util.FlashLightManager;

public class MainActivity extends AppCompatActivity {

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Log.d(this.getClass().getName(), String.format("send command to %s to start service",
                PowerOnListener.class.getCanonicalName()));

        try {
            // start a service only if camera is supported
            if (FlashLightManager.isFlashSupported(getApplication().getPackageManager())) {
                ComponentName component = startService(new Intent(getBaseContext(),
                        PowerOnListener.class));
                if(component == null) {
                    this.started = false;
                    Toast.makeText(this, String.format("service %s can not be started",
                            PowerOnListener.class.getName()),
                            Toast.LENGTH_SHORT).show();
                } else {
                    this.started = true;
                    Toast.makeText(this,
                            String.format("service %s is started",
                                    component.getClassName()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch(Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        }
    }

    // Method to stop the service
    public void stopService(View view) {
        Log.d(this.getClass().getName(), String.format("service %s is started? %s",
                PowerOnListener.class.getName(), this.started ? "yes" : "no"));
        if(this.started) {
            this.started = false;
            Log.d(this.getClass().getName(), String.format("send command to %s to stop service",
                    PowerOnListener.class.getName()));
            boolean status = stopService(new Intent(getBaseContext(), PowerOnListener.class));
            if(!status) {
                Log.d(this.getClass().getName(), String.format("service %s is not running",
                        PowerOnListener.class.getName()));
            } else {
                Log.d(this.getClass().getName(), String.format("service %s is stopped",
                        PowerOnListener.class.getName()));
                Toast.makeText(this, String.format("service %s is stopped",
                        PowerOnListener.class.getName()),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
