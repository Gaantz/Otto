package glup.com.otto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;


public class Otto extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otto);

        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusHolder.getInstance().post(new ButtonEvent());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this,OttoService.class));
        BusHolder.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusHolder.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        BusHolder.getInstance().post(new KillEvent());
        super.onDestroy();
    }

    @Subscribe
    public void anotherButtonPress(ButtonEvent event) {
        Toast.makeText(this, "Activity Llamado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_otto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
