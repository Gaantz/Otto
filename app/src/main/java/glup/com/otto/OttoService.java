package glup.com.otto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by usuario on 28/04/15.
 */
public class OttoService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        BusHolder.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void serviceButton(ButtonEvent event) {
        Toast.makeText(this, "Service Llamado", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void killSerivce(KillEvent event) {
        Toast.makeText(this, "Service Destruido", Toast.LENGTH_SHORT).show();
        onDestroy();
    }
}
