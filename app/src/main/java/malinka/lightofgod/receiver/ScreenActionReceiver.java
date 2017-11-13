package malinka.lightofgod.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.Date;
import malinka.lightofgod.service.PowerOnListener;

public class ScreenActionReceiver extends BroadcastReceiver {

    private boolean light;
    private int counterPowerPressed;
    private Date pressedAt;
    private Date previousPressedAt;

    private final int INTERVAL = 5;

    public ScreenActionReceiver() {
        this.light = true;
        this.counterPowerPressed = 0;
        this.pressedAt = null;
        this.previousPressedAt = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        this.counterPowerPressed++;

        Log.d(this.getClass().getName(), String.format("power pressed = %s times",
                this.counterPowerPressed));

        Log.d(this.getClass().getName(),
                String.format("last pressed at = %s", this.pressedAt));

        this.previousPressedAt = this.pressedAt;
        this.pressedAt = new Date();

        if (this.counterPowerPressed == 2) {

            Date now = new Date();
            Long duration = now.getTime() - this.previousPressedAt.getTime();

            Log.d(this.getClass().getName(), String.format("less than %s seconds ? %s",
                    this.INTERVAL, duration <= this.INTERVAL * 1000 ? "yes" : "no"));

            if ((duration <= this.INTERVAL * 1000) &&
                    (this.counterPowerPressed >= 2)) {

                Log.d(this.getClass().getName(),
                        String.format("power pressed: %s times in %s seconds",
                                this.counterPowerPressed, duration / 1000));

                Intent i = new Intent(context, PowerOnListener.class);
                i.putExtra("flash_light", this.light);

                Log.d(this.getClass().getName(), String.format("sending light status to service as flash_light = %s", this.light));
                context.startService(i);

                this.light = !this.light;

            }
        }

        if (this.counterPowerPressed >= 2) this.counterPowerPressed = 0;
    }
}
