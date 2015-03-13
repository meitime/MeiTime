package info.meitime.meitime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private boolean isInFront=false;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mainview).setBackgroundResource(R.drawable.defaultbackground);
    }
    @Override
    public void onResume()
    {
        isInFront=true;
        thread=new Thread(new ProgressRunable());
        thread.start();
        super.onResume();
        startService(new Intent(this, MyService.class));

    }
    @Override
    public void onPause()
    {
        isInFront=false;
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class ProgressRunable implements Runnable
    {
        @Override
        public void run() {
            long seconds=getApplicationContext().getSharedPreferences("time",MODE_PRIVATE).getLong("TODAY_TIME_USED",0)/1000;
            int screenon_frequency=getApplicationContext().getSharedPreferences("time",MODE_PRIVATE).getInt("screenon_frequency",0);
            MainView mainView=(MainView)findViewById(R.id.mainview);

            while(isInFront)
            {
                mainView.setTime(seconds,screenon_frequency);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seconds++;
            }
        }
    }
}
