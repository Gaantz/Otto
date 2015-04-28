package glup.com.otto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import junit.framework.Test;

/**
 * Created by usuario on 28/04/15.
 */
public class OttoTwo extends ActionBarActivity {

    @Override
    protected void onResume() {
        super.onResume();
        BusHolder.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusHolder.getInstance().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytwo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame,new PlaceholderFragment()).commit();
        }
    }

    @Subscribe
    public void getMessage(String s) {
        Toast.makeText(this, s+"!", Toast.LENGTH_LONG).show();
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
            TestData t = new TestData();
            t.message="Hello from the activity";
            BusHolder.getInstance().post(t);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            BusHolder.getInstance().register(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_otto, container, false);
            View button = rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    BusHolder.getInstance().post("Hello from the Fragment");
                }
            });
            return rootView;
        }

        @Subscribe
        public void getMessage(TestData data) {
            Toast.makeText(getActivity(), data.message+"-",Toast.LENGTH_LONG).show();
        }

        @Produce
        public TestData produceEvent() {
            TestData t = new TestData();
            t.message = "STARTING FRAGMENT";
            return t;
        }
    }

    public static class TestData {
        public String message;
    }
}
